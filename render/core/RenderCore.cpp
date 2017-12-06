#include <stdexcept>
#include <sstream>
#include <iostream>
#include <vector>
#include <cstring>
#include "RenderCore.h"

const std::vector<const char *> validationLayers = { // NOLINT
    "VK_LAYER_LUNARG_standard_validation"
};
const std::vector<const char *> deviceExtensions = { // NOLINT
    VK_KHR_SWAPCHAIN_EXTENSION_NAME
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
  initVulkan_createSurface();
  initVulkan_selectPhysicalDevice();
  initVulkan_createLogicalDevice();
  initVulkan_createSwapChain();
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
  checkResult(result, "Создание инстанции вулкана");
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

  std::cout << "Доступные расширения инстанции вулкана:" << std::endl;

  for (const auto &extension : availableExtensions) {
    std::cout << "    " << extension.extensionName << std::endl;
  }

  std::cout << "Необходимые расширения инстанции вулкана:" << std::endl;

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
  checkResult(result, "Установка Debug Callback");
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
  for (const auto &aPhysicalDevice : devices) {
    if (isDeviceSuitable(aPhysicalDevice, index++)) {
      physicalDevice = aPhysicalDevice;
    }
  }

  if (physicalDevice == VK_NULL_HANDLE) {
    throw std::runtime_error("Не найдено подходящих физических устройств для вулкана");
  }
}

bool RenderCore::isDeviceSuitable(VkPhysicalDevice aPhysicalDevice, int deviceIndex) {

  VkPhysicalDeviceProperties deviceProperties; // NOLINT
  vkGetPhysicalDeviceProperties(aPhysicalDevice, &deviceProperties);

  VkPhysicalDeviceFeatures deviceFeatures; // NOLINT
  vkGetPhysicalDeviceFeatures(aPhysicalDevice, &deviceFeatures);

  std::cout << "Физическое устройство " << deviceIndex << ": (physicalDevice = " << aPhysicalDevice << ")" << std::endl;
  std::cout << "    deviceName = " << deviceProperties.deviceName << std::endl;
  std::cout << "    limits.maxImageDimension1D = " << deviceProperties.limits.maxImageDimension1D << std::endl;
  std::cout << "    limits.maxImageDimension2D = " << deviceProperties.limits.maxImageDimension2D << std::endl;
  std::cout << "    limits.maxImageDimension3D = " << deviceProperties.limits.maxImageDimension3D << std::endl;
  std::cout << "    geometryShader = " << (deviceFeatures.geometryShader ? "TRUE" : "FALSE") << std::endl;

  if (deviceProperties.deviceType != VK_PHYSICAL_DEVICE_TYPE_DISCRETE_GPU
      || !deviceFeatures.geometryShader)
    return false;

  {
    auto indices = findQueueFamilyIndicesIn(aPhysicalDevice);
    if (!indices.allFound()) return false;
  }

  {
    bool extensionsSupported = checkDeviceExtensionSupport(aPhysicalDevice);
    if (!extensionsSupported) return false;
  }

  {
    SwapChainSupportDetails scs = querySwapChainSupport(aPhysicalDevice, surface);
    if (scs.notAdequate()) return false;
  }

  return true;
}

QueueFamilyIndices RenderCore::findQueueFamilyIndicesIn(VkPhysicalDevice aPhysicalDevice) {

  uint32_t queueFamilyCount = 0;
  vkGetPhysicalDeviceQueueFamilyProperties(aPhysicalDevice, &queueFamilyCount, nullptr);

  std::vector<VkQueueFamilyProperties> queueFamilies(queueFamilyCount);
  vkGetPhysicalDeviceQueueFamilyProperties(aPhysicalDevice, &queueFamilyCount, queueFamilies.data());

  QueueFamilyIndices ret = {};

  int index = 0;
  for (const auto &queueFamily : queueFamilies) {
    if (queueFamily.queueCount > 0) {

      if (queueFamily.queueFlags & VK_QUEUE_GRAPHICS_BIT) {
        ret.goodGraphicsIndex(index);
      }

      VkBool32 presentSupport = VK_FALSE;
      vkGetPhysicalDeviceSurfaceSupportKHR(aPhysicalDevice, static_cast<uint32_t>(index), surface, &presentSupport);

      if (presentSupport) {
        ret.goodPresentIndex(index);
      }
    }

    if (ret.allFound()) break;

    index++;
  }

  return ret;
}

#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wreturn-stack-address"
#pragma ide diagnostic ignored "OCDFAInspection"

void RenderCore::initVulkan_createLogicalDevice() {

  queueFamilyIndices = findQueueFamilyIndicesIn(physicalDevice);
  if (!queueFamilyIndices.allFound()) throw std::runtime_error("Не найдены все подходящие группы очередей");

  auto uniqueQueueFamilyIndices = queueFamilyIndices.uniqueQueueFamilies();
  std::vector<VkDeviceQueueCreateInfo> queueCreateInfoVector;

  float queuePriority = 1.0f;

  for (auto queueFamilyIndex : uniqueQueueFamilyIndices) {
    VkDeviceQueueCreateInfo queueCreateInfo = {};
    queueCreateInfo.sType = VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO;
    queueCreateInfo.queueFamilyIndex = queueFamilyIndex;
    queueCreateInfo.queueCount = 1;
    queueCreateInfo.pQueuePriorities = &queuePriority;
    queueCreateInfoVector.push_back(queueCreateInfo);
    std::cout << "queueFamilyIndex = " << queueFamilyIndex << std::endl;
  }

  VkPhysicalDeviceFeatures deviceFeatures = {};

  VkDeviceCreateInfo createInfo = {};
  createInfo.sType = VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO;
  createInfo.pEnabledFeatures = &deviceFeatures;
  createInfo.pQueueCreateInfos = queueCreateInfoVector.data();
  createInfo.queueCreateInfoCount = static_cast<uint32_t>(queueCreateInfoVector.size());

  createInfo.enabledExtensionCount = 0;
  if (enableValidationLayers) {
    createInfo.enabledLayerCount = static_cast<uint32_t>(validationLayers.size());
    createInfo.ppEnabledLayerNames = validationLayers.data();
  } else {
    createInfo.enabledLayerCount = 0;
  }

  createInfo.enabledExtensionCount = static_cast<uint32_t>(deviceExtensions.size());
  createInfo.ppEnabledExtensionNames = deviceExtensions.data();

  VkResult result = vkCreateDevice(physicalDevice, &createInfo, nullptr, device.replace());
  checkResult(result, "Создание логического устройства");

  vkGetDeviceQueue(device, queueFamilyIndices.presentIndex(), 0, &presentQueue);
  std::cout << "presentQueue = " << presentQueue << std::endl;
}

#pragma clang diagnostic pop

void RenderCore::initVulkan_createSurface() {
  VkResult result = glfwCreateWindowSurface(instance, window, nullptr, surface.replace());
  checkResult(result, "Создания поверхности окна");
}

bool RenderCore::checkDeviceExtensionSupport(VkPhysicalDevice aPhysicalDevice) {
  uint32_t extensionCount;
  vkEnumerateDeviceExtensionProperties(aPhysicalDevice, nullptr, &extensionCount, nullptr);

  std::vector<VkExtensionProperties> availableExtensions(extensionCount);
  vkEnumerateDeviceExtensionProperties(aPhysicalDevice, nullptr, &extensionCount, availableExtensions.data());

  std::set<std::string> requiredExtensions(deviceExtensions.begin(), deviceExtensions.end());

  std::cout << "Требуемые расширения девайса:" << std::endl;
  for (auto &de: deviceExtensions) {
    std::cout << "    " << de << std::endl;
  }

  std::cout << "Имеющиеся расширения девайса:" << std::endl;
  for (const auto &extension : availableExtensions) {
    std::cout << "    " << extension.extensionName << std::endl;
    requiredExtensions.erase(extension.extensionName);
  }

  return requiredExtensions.empty();
}

#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wreturn-stack-address"

void RenderCore::initVulkan_createSwapChain() {
  SwapChainSupportDetails swapChainSupport = querySwapChainSupport(physicalDevice, surface);

  VkSurfaceFormatKHR surfaceFormat = chooseSwapSurfaceFormat(swapChainSupport.formats);
  VkPresentModeKHR presentMode = chooseSwapPresentMode(swapChainSupport.presentModes);
  VkExtent2D extent = chooseSwapExtent(swapChainSupport.capabilities, getWidth(), getHeight());

  uint32_t imageCount = swapChainSupport.capabilities.minImageCount + 1;
  if (swapChainSupport.capabilities.maxImageCount > 0 && imageCount > swapChainSupport.capabilities.maxImageCount) {
    imageCount = swapChainSupport.capabilities.maxImageCount;
  }

  VkSwapchainCreateInfoKHR createInfo = {};
  createInfo.sType = VK_STRUCTURE_TYPE_SWAPCHAIN_CREATE_INFO_KHR;
  createInfo.surface = surface;

  createInfo.minImageCount = imageCount;
  createInfo.imageFormat = surfaceFormat.format;
  createInfo.imageColorSpace = surfaceFormat.colorSpace;
  createInfo.imageExtent = extent;
  createInfo.imageArrayLayers = 1;
  createInfo.imageUsage = VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT;

  const auto &qfi = queueFamilyIndices;
  uint32_t pQueueFamilyIndices[] = {qfi.graphicsIndex(), qfi.graphicsIndex()};

  if (qfi.graphicsIndex() != qfi.graphicsIndex()) {
    createInfo.imageSharingMode = VK_SHARING_MODE_CONCURRENT;
    createInfo.queueFamilyIndexCount = 2;
    createInfo.pQueueFamilyIndices = pQueueFamilyIndices;
  } else {
    createInfo.imageSharingMode = VK_SHARING_MODE_EXCLUSIVE;
    createInfo.queueFamilyIndexCount = 0; // Optional
    createInfo.pQueueFamilyIndices = nullptr; // Optional
  }

  createInfo.preTransform = swapChainSupport.capabilities.currentTransform;

  createInfo.compositeAlpha = VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR;
  createInfo.presentMode = presentMode;
  createInfo.clipped = VK_TRUE;
  createInfo.oldSwapchain = VK_NULL_HANDLE;

  VkResult result = vkCreateSwapchainKHR(device, &createInfo, nullptr, swapChain.replace());
  checkResult(result, "Создание Swap Chain");

  vkGetSwapchainImagesKHR(device, swapChain, &imageCount, nullptr);
  swapChainImages.resize(imageCount);
  vkGetSwapchainImagesKHR(device, swapChain, &imageCount, swapChainImages.data());

  swapChainImageFormat = surfaceFormat.format;
  swapChainExtent = extent;
}

#pragma clang diagnostic pop

