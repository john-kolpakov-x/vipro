#include <stdexcept>
#include <sstream>
#include <iostream>
#include <vector>
#include <cstring>
#include "RenderCore.h"

const std::vector<const char *> validationLayers = { // NOLINT
    "VK_LAYER_LUNARG_standard_validation"
};

#ifdef NDEBUG
const bool enableValidationLayers = false;
#else
const bool enableValidationLayers = true;
#endif

RenderCore::RenderCore() : width(800), height(600)// NOLINT
{}

int RenderCore::getWidth() {
  return width;
}

int RenderCore::getHeight() {
  return height;
}

void RenderCore::initialize() {
  initWindow();
  initVulkan();
}

void error_callback(int error, const char *description) {
  std::ostringstream out;
  out << "ERR CODE " << error << "; message " << description;
  throw std::runtime_error(out.str());
}

void RenderCore::initWindow() {
  glfwInit();

  glfwWindowHint(GLFW_CLIENT_API, GLFW_NO_API);
  glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

  glfwSetErrorCallback(error_callback);

  window = glfwCreateWindow(width, height, "Vipro", nullptr, nullptr);
}

void RenderCore::mainLoop() {
  while (!glfwWindowShouldClose(window)) {
    glfwPollEvents();
  }
}

void RenderCore::initVulkan() {
  initVulkan_createInstance();
  initVulkan_setupDebugCallback();
  initVulkan_selectPhysicalDevice();
}

#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCSimplifyInspection"
#pragma clang diagnostic ignored "-Wreturn-stack-address"
#pragma ide diagnostic ignored "OCDFAInspection"

void RenderCore::initVulkan_createInstance() {

  if (enableValidationLayers && !checkValidationLayerSupport()) {
    throw std::runtime_error("Validation Layers нужны, но не доступны!");
  }

  VkApplicationInfo appInfo = {};
  appInfo.sType = VK_STRUCTURE_TYPE_APPLICATION_INFO;
  appInfo.pApplicationName = "Vipro";
  appInfo.applicationVersion = VK_MAKE_VERSION(1, 0, 0);
  appInfo.pEngineName = "No Engine";
  appInfo.engineVersion = VK_MAKE_VERSION(1, 0, 0);
  appInfo.apiVersion = VK_API_VERSION_1_0;

  VkInstanceCreateInfo createInfo = {};
  createInfo.sType = VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO;
  createInfo.pApplicationInfo = &appInfo;

  auto extensions = getRequiredExtensions();
  checkExtensions(extensions);

  createInfo.enabledExtensionCount = static_cast<uint32_t>(extensions.size());
  createInfo.ppEnabledExtensionNames = extensions.data();

  if (enableValidationLayers) {
    createInfo.enabledLayerCount = static_cast<uint32_t>(validationLayers.size());
    createInfo.ppEnabledLayerNames = validationLayers.data();
  } else {
    createInfo.enabledLayerCount = 0;
  }

  VkResult result = vkCreateInstance(&createInfo, nullptr, instance.replace());
  checkResult(result, "vkCreateInstance");
}

#pragma clang diagnostic pop

bool RenderCore::checkValidationLayerSupport() {
  uint32_t layerCount;
  vkEnumerateInstanceLayerProperties(&layerCount, nullptr);

  std::vector<VkLayerProperties> availableLayers(layerCount);
  vkEnumerateInstanceLayerProperties(&layerCount, availableLayers.data());

  for (const char *layerName : validationLayers) {
    bool layerFound = false;

    for (const auto &layerProperties : availableLayers) {
      if (strcmp(layerName, layerProperties.layerName) == 0) {
        layerFound = true;
        break;
      }
    }

    if (!layerFound) {
      return false;
    }
  }

  return true;
}

#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCSimplifyInspection"

std::vector<const char *> RenderCore::getRequiredExtensions() {
  std::vector<const char *> extensions;

  unsigned int glfwExtensionCount = 0;
  const char **glfwExtensions;
  glfwExtensions = glfwGetRequiredInstanceExtensions(&glfwExtensionCount);

  for (unsigned int i = 0; i < glfwExtensionCount; i++) {
    extensions.push_back(glfwExtensions[i]);
  }

  if (enableValidationLayers) {
    extensions.push_back(VK_EXT_DEBUG_REPORT_EXTENSION_NAME);
  }

  return extensions;
}

#pragma clang diagnostic pop

void RenderCore::checkExtensions(std::vector<const char *> requiredExtensions) {
  uint32_t extensionCount = 0;
  vkEnumerateInstanceExtensionProperties(nullptr, &extensionCount, nullptr);
  std::vector<VkExtensionProperties> availableExtensions(extensionCount);
  vkEnumerateInstanceExtensionProperties(nullptr, &extensionCount, availableExtensions.data());

  std::cout << "Доступные расширения:" << std::endl;

  for (const auto &extension : availableExtensions) {
    std::cout << "    " << extension.extensionName << std::endl;
  }

  std::cout << "Необходимые расширения:" << std::endl;

  for (const auto &extension : requiredExtensions) {
    std::cout << "    " << extension << std::endl;
  }

  for (const auto &requiredExtension : requiredExtensions) {
    bool found = false;
    for (const auto &availableExtension : availableExtensions) {
      if (strcmp(availableExtension.extensionName, requiredExtension) == 0) {
        found = true;
        break;
      }
    }

    if (!found) {
      std::ostringstream out;
      out << "Не доступно необходимое расширение " << requiredExtension;
      throw std::runtime_error(out.str());
    }
  }
}

void RenderCore::initVulkan_setupDebugCallback() {
#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCSimplifyInspection"
#pragma ide diagnostic ignored "OCDFAInspection"
  if (!enableValidationLayers) return;
#pragma clang diagnostic pop

  VkDebugReportCallbackCreateInfoEXT createInfo = {};
  createInfo.sType = VK_STRUCTURE_TYPE_DEBUG_REPORT_CALLBACK_CREATE_INFO_EXT;
  createInfo.flags = VK_DEBUG_REPORT_ERROR_BIT_EXT | VK_DEBUG_REPORT_WARNING_BIT_EXT;
  createInfo.pfnCallback = debugCallbackStatic;
  createInfo.pUserData = this;

  VkResult result = createDebugReportCallbackEXT(instance, &createInfo, nullptr, callback.replace());
  checkResult(result, "Ну получилось установить Debug Callback");
}

bool RenderCore::debugCallback(VkDebugReportFlagsEXT flags, VkDebugReportObjectTypeEXT objType, uint64_t obj,
                               size_t location, int32_t code, const char *layerPrefix, const char *msg) {

  std::cerr << "Validation layer: " << msg << std::endl;
  std::cerr << "    flags: " << flags << std::endl;
  std::cerr << "    objType: " << objType << std::endl;
  std::cerr << "    obj: " << obj << std::endl;
  std::cerr << "    location: " << location << std::endl;
  std::cerr << "    code: " << code << std::endl;
  std::cerr << "    layerPrefix: " << layerPrefix << std::endl;
  std::cerr << std::endl;

  return false;
}

RenderCore::~RenderCore() {
  glfwDestroyWindow(window);
  glfwTerminate();
}

void RenderCore::initVulkan_selectPhysicalDevice() {
  uint32_t deviceCount = 0;
  vkEnumeratePhysicalDevices(instance, &deviceCount, nullptr);

  if (deviceCount == 0) throw std::runtime_error("Нет физических устройств для вулкана");

  std::vector<VkPhysicalDevice> devices(deviceCount);
  vkEnumeratePhysicalDevices(instance, &deviceCount, devices.data());

  int index = 1;
  for (const auto &device : devices) {
    if (isDeviceSuitable(device, index++)) {
      physicalDevice = device;
    }
  }

  if (physicalDevice == VK_NULL_HANDLE) {
    throw std::runtime_error("Не найдено подходящих физических устройств для вулкана");
  }
}

bool RenderCore::isDeviceSuitable(VkPhysicalDevice physicalDevice, int index) {

  VkPhysicalDeviceProperties deviceProperties; // NOLINT
  vkGetPhysicalDeviceProperties(physicalDevice, &deviceProperties);

  VkPhysicalDeviceFeatures deviceFeatures; // NOLINT
  vkGetPhysicalDeviceFeatures(physicalDevice, &deviceFeatures);

  std::cout << "Физическое устройство " << index << ": (physicalDevice = " << physicalDevice << ")" << std::endl;
  std::cout << "    deviceName = " << deviceProperties.deviceName << std::endl;
  std::cout << "    limits.maxImageDimension1D = " << deviceProperties.limits.maxImageDimension1D << std::endl;
  std::cout << "    limits.maxImageDimension2D = " << deviceProperties.limits.maxImageDimension2D << std::endl;
  std::cout << "    limits.maxImageDimension3D = " << deviceProperties.limits.maxImageDimension3D << std::endl;
  std::cout << "    geometryShader = " << (deviceFeatures.geometryShader ? "TRUE" : "FALSE") << std::endl;

  return deviceProperties.deviceType == VK_PHYSICAL_DEVICE_TYPE_DISCRETE_GPU
         && deviceFeatures.geometryShader;
}
