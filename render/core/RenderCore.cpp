#include "RenderCore.h"
#include "vertex_data.h"
#include <sstream>
#include <iostream>
#include <cstring>
#include <fstream>
#include <chrono>

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

static void onWindowChangeSize(GLFWwindow *window, int width, int height) {
  if (width == 0 || height == 0) return;

  auto a = reinterpret_cast<RenderCore *>(glfwGetWindowUserPointer(window));
  a->onResize(width, height);
}

void RenderCore::initWindow() {
  glfwInit();

  glfwWindowHint(GLFW_CLIENT_API, GLFW_NO_API);
  glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

  glfwSetErrorCallback(error_callback);

  window = glfwCreateWindow(width, height, "Vipro", nullptr, nullptr);

  glfwSetWindowUserPointer(window, this);
  glfwSetWindowSizeCallback(window, onWindowChangeSize);
}

void RenderCore::initVulkan() {
  initVulkan_createInstance();
  initVulkan_setupDebugCallback();
  initVulkan_createSurface();
  initVulkan_selectPhysicalDevice();
  initVulkan_createLogicalDevice();
  initVulkan_createSwapChain();
  initVulkan_createImageViews();
  initVulkan_createRenderPass();
  initVulkan_createGraphicsPipeline();
  initVulkan_createFrameBuffers();
  initVulkan_createCommandPool();
  initVulkan_createVertexBuffer();
  initVulkan_createCommandBuffers();
  initVulkan_createSemaphores();
}

void RenderCore::mainLoop() {
  while (!glfwWindowShouldClose(window)) {
    glfwPollEvents();
    drawFrame();
  }

  vkDeviceWaitIdle(device);
}

void RenderCore::recreateSwapChain() {
  vkDeviceWaitIdle(device);

  initVulkan_createSwapChain();
  initVulkan_createImageViews();
  initVulkan_createRenderPass();
  initVulkan_createGraphicsPipeline();
  initVulkan_createFrameBuffers();
  initVulkan_createCommandBuffers();
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

#ifdef TRACE
  std::cout << "Доступные расширения инстанции вулкана:" << std::endl;
  for (const auto &extension : availableExtensions) {
    std::cout << "    " << extension.extensionName << std::endl;
  }
  std::cout << "Необходимые расширения инстанции вулкана:" << std::endl;
  for (const auto &extension : requiredExtensions) {
    std::cout << "    " << extension << std::endl;
  }
#endif

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

#ifdef TRACE
  std::cout << "Физическое устройство " << deviceIndex << ": (physicalDevice = " << aPhysicalDevice << ")" << std::endl;
  std::cout << "    deviceName = " << deviceProperties.deviceName << std::endl;
  std::cout << "    limits.maxImageDimension1D = " << deviceProperties.limits.maxImageDimension1D << std::endl;
  std::cout << "    limits.maxImageDimension2D = " << deviceProperties.limits.maxImageDimension2D << std::endl;
  std::cout << "    limits.maxImageDimension3D = " << deviceProperties.limits.maxImageDimension3D << std::endl;
  std::cout << "    geometryShader = " << (deviceFeatures.geometryShader ? "TRUE" : "FALSE") << std::endl;
#endif

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
#ifdef TRACE
    std::cout << "queueFamilyIndex = " << queueFamilyIndex << std::endl;
#endif
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
  vkGetDeviceQueue(device, queueFamilyIndices.graphicsIndex(), 0, &graphicsQueue);

#ifdef TRACE
  std::cout << "presentQueue  = " << presentQueue << std::endl;
  std::cout << "graphicsQueue = " << graphicsQueue << std::endl;
#endif
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

#ifdef TRACE
  std::cout << "Требуемые расширения девайса:" << std::endl;
  for (auto &de: deviceExtensions) {
    std::cout << "    " << de << std::endl;
  }
  std::cout << "Имеющиеся расширения девайса:" << std::endl;
  for (const auto &extension : availableExtensions) {
    std::cout << "    " << extension.extensionName << std::endl;
  }
#endif

  for (const auto &extension : availableExtensions) {
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

#ifdef TRACE
  std::cout << "queueFamilyIndices" << std::endl;
  std::cout << "    .graphicsIndex = " << qfi.graphicsIndex() << std::endl;
  std::cout << "    .presentIndex  = " << qfi.presentIndex() << std::endl;
#endif

  if (qfi.graphicsIndex() != qfi.graphicsIndex()) {
    createInfo.imageSharingMode = VK_SHARING_MODE_CONCURRENT;
    createInfo.queueFamilyIndexCount = 2;
    uint32_t pQueueFamilyIndices[] = {qfi.graphicsIndex(), qfi.presentIndex()};
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

  VkSwapchainKHR oldSwapChain = swapChain;
  createInfo.oldSwapchain = oldSwapChain;

  VkSwapchainKHR newSwapChain;
  VkResult result = vkCreateSwapchainKHR(device, &createInfo, nullptr, &newSwapChain);
  checkResult(result, "Создание Swap Chain");

  swapChain = newSwapChain;

  vkGetSwapchainImagesKHR(device, swapChain, &imageCount, nullptr);
  swapChainImages.resize(imageCount);
  vkGetSwapchainImagesKHR(device, swapChain, &imageCount, swapChainImages.data());

  swapChainImageFormat = surfaceFormat.format;
  swapChainExtent = extent;
#ifdef TRACE
  std::cout << "swapChainImageFormat = " << surfaceFormatName(swapChainImageFormat) << std::endl;
  std::cout << "swapChainExtent      = " << VkExtent2D_to_str(swapChainExtent) << std::endl;
#endif
}

#pragma clang diagnostic pop

void RenderCore::initVulkan_createImageViews() {
  swapChainImageViews.resize(swapChainImages.size(), VDeleter<VkImageView>{device, vkDestroyImageView});

  for (uint32_t i = 0; i < swapChainImages.size(); i++) {
    VkImageViewCreateInfo createInfo = {};
    createInfo.sType = VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO;
    createInfo.image = swapChainImages[i];
    createInfo.format = swapChainImageFormat;
    createInfo.viewType = VK_IMAGE_VIEW_TYPE_2D;
    createInfo.components.r = VK_COMPONENT_SWIZZLE_IDENTITY;
    createInfo.components.g = VK_COMPONENT_SWIZZLE_IDENTITY;
    createInfo.components.b = VK_COMPONENT_SWIZZLE_IDENTITY;
    createInfo.components.a = VK_COMPONENT_SWIZZLE_IDENTITY;
    createInfo.subresourceRange.aspectMask = VK_IMAGE_ASPECT_COLOR_BIT;
    createInfo.subresourceRange.baseMipLevel = 0;
    createInfo.subresourceRange.levelCount = 1;
    createInfo.subresourceRange.baseArrayLayer = 0;
    createInfo.subresourceRange.layerCount = 1;

    VkResult result = vkCreateImageView(device, &createInfo, nullptr, swapChainImageViews[i].replace());
    std::ostringstream out;
    out << "Создание Image View " << i + 1 << " из " << swapChainImages.size();
    checkResult(result, out.str());
  }
}

void RenderCore::createShaderModule(const ShaderCode shaderCode,
                                    VDeleter<VkShaderModule> &shaderModule,
                                    const std::string &name) {

  VkShaderModuleCreateInfo createInfo = {};
  createInfo.sType = VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO;
  createInfo.codeSize = shaderCode.len;
  createInfo.pCode = (uint32_t *) shaderCode.data;

  VkResult result = vkCreateShaderModule(device, &createInfo, nullptr, shaderModule.replace());
  checkResult(result, name);

}

#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wreturn-stack-address"

void RenderCore::initVulkan_createRenderPass() {
  VkAttachmentDescription colorAttachment = {};
  colorAttachment.format = swapChainImageFormat;
  colorAttachment.samples = VK_SAMPLE_COUNT_1_BIT;

  colorAttachment.loadOp = VK_ATTACHMENT_LOAD_OP_CLEAR;
  colorAttachment.storeOp = VK_ATTACHMENT_STORE_OP_STORE;

  colorAttachment.stencilLoadOp = VK_ATTACHMENT_LOAD_OP_DONT_CARE;
  colorAttachment.stencilStoreOp = VK_ATTACHMENT_STORE_OP_DONT_CARE;

  colorAttachment.initialLayout = VK_IMAGE_LAYOUT_UNDEFINED;
  colorAttachment.finalLayout = VK_IMAGE_LAYOUT_PRESENT_SRC_KHR;

  VkAttachmentReference colorAttachmentRef = {};
  colorAttachmentRef.attachment = 0;
  colorAttachmentRef.layout = VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL;

  VkSubpassDescription subPass = {};
  subPass.pipelineBindPoint = VK_PIPELINE_BIND_POINT_GRAPHICS;

  subPass.colorAttachmentCount = 1;
  subPass.pColorAttachments = &colorAttachmentRef;

  VkSubpassDependency dependency = {};
  dependency.srcSubpass = VK_SUBPASS_EXTERNAL;
  dependency.dstSubpass = 0;

  dependency.srcStageMask = VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT;
  dependency.srcAccessMask = 0;

  dependency.dstStageMask = VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT;
  dependency.dstAccessMask = VK_ACCESS_COLOR_ATTACHMENT_READ_BIT | VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT;

  VkRenderPassCreateInfo renderPassInfo = {};
  renderPassInfo.sType = VK_STRUCTURE_TYPE_RENDER_PASS_CREATE_INFO;
  renderPassInfo.attachmentCount = 1;
  renderPassInfo.pAttachments = &colorAttachment;
  renderPassInfo.subpassCount = 1;
  renderPassInfo.pSubpasses = &subPass;
  renderPassInfo.dependencyCount = 1;
  renderPassInfo.pDependencies = &dependency;

  VkResult result = vkCreateRenderPass(device, &renderPassInfo, nullptr, renderPass.replace());
  checkResult(result, "Создание Render Pass");
}

void RenderCore::initVulkan_createGraphicsPipeline() {
  VDeleter<VkShaderModule> vertShaderModule{device, vkDestroyShaderModule};
  VDeleter<VkShaderModule> fragShaderModule{device, vkDestroyShaderModule};
  createShaderModule(getTriVertShaderCode(), vertShaderModule, "Создание шейдер-модуля tri.vert");
  createShaderModule(getTriFragShaderCode(), fragShaderModule, "Создание шейдер-модуля tri.frag");

  VkPipelineShaderStageCreateInfo vertShaderStageInfo = {};
  vertShaderStageInfo.sType = VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO;
  vertShaderStageInfo.stage = VK_SHADER_STAGE_VERTEX_BIT;
  vertShaderStageInfo.module = vertShaderModule;
  vertShaderStageInfo.pName = "main";

  VkPipelineShaderStageCreateInfo fragShaderStageInfo = {};
  fragShaderStageInfo.sType = VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO;
  fragShaderStageInfo.stage = VK_SHADER_STAGE_FRAGMENT_BIT;
  fragShaderStageInfo.module = fragShaderModule;
  fragShaderStageInfo.pName = "main";

  VkPipelineShaderStageCreateInfo shaderStages[] = {vertShaderStageInfo, fragShaderStageInfo};

  VkPipelineVertexInputStateCreateInfo vertexInputInfo = {};
  vertexInputInfo.sType = VK_STRUCTURE_TYPE_PIPELINE_VERTEX_INPUT_STATE_CREATE_INFO;
  auto bindingDescription = Vertex::getBindingDescription();
  vertexInputInfo.vertexBindingDescriptionCount = 1;
  vertexInputInfo.pVertexBindingDescriptions = &bindingDescription;
  auto attributeDescriptions = Vertex::getAttributeDescriptions();
  vertexInputInfo.vertexAttributeDescriptionCount = attributeDescriptions.size();
  vertexInputInfo.pVertexAttributeDescriptions = attributeDescriptions.data();

  VkPipelineInputAssemblyStateCreateInfo inputAssembly = {};
  inputAssembly.sType = VK_STRUCTURE_TYPE_PIPELINE_INPUT_ASSEMBLY_STATE_CREATE_INFO;
  inputAssembly.topology = VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST;
  inputAssembly.primitiveRestartEnable = VK_FALSE;

  VkViewport viewport = {};
  viewport.x = 0.0f;
  viewport.y = 0.0f;
  viewport.width = (float) swapChainExtent.width;
  viewport.height = (float) swapChainExtent.height;
  viewport.minDepth = 0.0f;
  viewport.maxDepth = 1.0f;

  VkRect2D scissor = {};
  scissor.offset = {0, 0};
  scissor.extent = swapChainExtent;

  VkPipelineViewportStateCreateInfo viewportState = {};
  viewportState.sType = VK_STRUCTURE_TYPE_PIPELINE_VIEWPORT_STATE_CREATE_INFO;
  viewportState.viewportCount = 1;
  viewportState.pViewports = &viewport;
  viewportState.scissorCount = 1;
  viewportState.pScissors = &scissor;

  VkPipelineRasterizationStateCreateInfo rasterizer = {};
  rasterizer.sType = VK_STRUCTURE_TYPE_PIPELINE_RASTERIZATION_STATE_CREATE_INFO;
  rasterizer.depthClampEnable = VK_FALSE;
  rasterizer.rasterizerDiscardEnable = VK_FALSE;
  rasterizer.polygonMode = VK_POLYGON_MODE_FILL;
  rasterizer.lineWidth = 1.0f;

  rasterizer.cullMode = VK_CULL_MODE_BACK_BIT;
  rasterizer.frontFace = VK_FRONT_FACE_CLOCKWISE;

  rasterizer.depthBiasEnable = VK_FALSE;
  rasterizer.depthBiasConstantFactor = 0.0f; // Optional
  rasterizer.depthBiasClamp = 0.0f; // Optional
  rasterizer.depthBiasSlopeFactor = 0.0f; // Optional

  VkPipelineMultisampleStateCreateInfo multiSampling = {};
  multiSampling.sType = VK_STRUCTURE_TYPE_PIPELINE_MULTISAMPLE_STATE_CREATE_INFO;
  multiSampling.sampleShadingEnable = VK_FALSE;
  multiSampling.rasterizationSamples = VK_SAMPLE_COUNT_1_BIT;
  multiSampling.minSampleShading = 1.0f; // Optional
  multiSampling.pSampleMask = nullptr; /// Optional
  multiSampling.alphaToCoverageEnable = VK_FALSE; // Optional
  multiSampling.alphaToOneEnable = VK_FALSE; // Optional

  VkPipelineColorBlendAttachmentState colorBlendAttachment = {};
  colorBlendAttachment.colorWriteMask = 0
                                        | VK_COLOR_COMPONENT_R_BIT
                                        | VK_COLOR_COMPONENT_G_BIT
                                        | VK_COLOR_COMPONENT_B_BIT
                                        | VK_COLOR_COMPONENT_A_BIT;
  colorBlendAttachment.blendEnable = VK_FALSE;
  colorBlendAttachment.srcColorBlendFactor = VK_BLEND_FACTOR_ONE; // Optional
  colorBlendAttachment.dstColorBlendFactor = VK_BLEND_FACTOR_ZERO; // Optional
  colorBlendAttachment.colorBlendOp = VK_BLEND_OP_ADD; // Optional
  colorBlendAttachment.srcAlphaBlendFactor = VK_BLEND_FACTOR_ONE; // Optional
  colorBlendAttachment.dstAlphaBlendFactor = VK_BLEND_FACTOR_ZERO; // Optional
  colorBlendAttachment.alphaBlendOp = VK_BLEND_OP_ADD; // Optional

  VkPipelineColorBlendStateCreateInfo colorBlending = {};
  colorBlending.sType = VK_STRUCTURE_TYPE_PIPELINE_COLOR_BLEND_STATE_CREATE_INFO;
  colorBlending.logicOpEnable = VK_FALSE;
  colorBlending.logicOp = VK_LOGIC_OP_COPY; // Optional
  colorBlending.attachmentCount = 1;
  colorBlending.pAttachments = &colorBlendAttachment;
  colorBlending.blendConstants[0] = 0.0f; // Optional
  colorBlending.blendConstants[1] = 0.0f; // Optional
  colorBlending.blendConstants[2] = 0.0f; // Optional
  colorBlending.blendConstants[3] = 0.0f; // Optional

  /*
  VkDynamicState dynamicStates[] = {
      VK_DYNAMIC_STATE_VIEWPORT,
      VK_DYNAMIC_STATE_LINE_WIDTH
  };

  VkPipelineDynamicStateCreateInfo dynamicState = {};
  dynamicState.sType = VK_STRUCTURE_TYPE_PIPELINE_DYNAMIC_STATE_CREATE_INFO;
  dynamicState.dynamicStateCount = 2;
  dynamicState.pDynamicStates = dynamicStates;
  */

  VkPipelineLayoutCreateInfo pipelineLayoutInfo = {};
  pipelineLayoutInfo.sType = VK_STRUCTURE_TYPE_PIPELINE_LAYOUT_CREATE_INFO;
  pipelineLayoutInfo.setLayoutCount = 0; // Optional
  pipelineLayoutInfo.pSetLayouts = nullptr; // Optional
  pipelineLayoutInfo.pushConstantRangeCount = 0; // Optional
  pipelineLayoutInfo.pPushConstantRanges = nullptr; // Optional

  VkResult result = vkCreatePipelineLayout(device, &pipelineLayoutInfo, nullptr, pipelineLayout.replace());
  checkResult(result, "Создание PipelineLayout");

  VkGraphicsPipelineCreateInfo pipelineInfo = {};
  pipelineInfo.sType = VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO;
  pipelineInfo.stageCount = 2;
  pipelineInfo.pStages = shaderStages;
  pipelineInfo.pVertexInputState = &vertexInputInfo;
  pipelineInfo.pInputAssemblyState = &inputAssembly;
  pipelineInfo.pViewportState = &viewportState;
  pipelineInfo.pRasterizationState = &rasterizer;
  pipelineInfo.pMultisampleState = &multiSampling;
  pipelineInfo.pDepthStencilState = nullptr; // Optional
  pipelineInfo.pColorBlendState = &colorBlending;
  pipelineInfo.pDynamicState = nullptr; // Optional

  pipelineInfo.layout = pipelineLayout;

  pipelineInfo.renderPass = renderPass;
  pipelineInfo.subpass = 0;

  pipelineInfo.basePipelineHandle = VK_NULL_HANDLE;
  pipelineInfo.basePipelineIndex = -1; // Optional

  result = vkCreateGraphicsPipelines(device, VK_NULL_HANDLE, 1, &pipelineInfo, nullptr, graphicsPipeline.replace());
  checkResult(result, "Создание контэйнера");
}

void RenderCore::initVulkan_createFrameBuffers() {
  swapChainFrameBuffers.resize(swapChainImageViews.size(), VDeleter<VkFramebuffer>{device, vkDestroyFramebuffer});
  for (size_t i = 0; i < swapChainImageViews.size(); i++) {
    VkImageView attachments[] = {
        swapChainImageViews[i]
    };

    VkFramebufferCreateInfo frameBufferInfo = {};
    frameBufferInfo.sType = VK_STRUCTURE_TYPE_FRAMEBUFFER_CREATE_INFO;
    frameBufferInfo.renderPass = renderPass;
    frameBufferInfo.attachmentCount = 1;
    frameBufferInfo.pAttachments = attachments;
    frameBufferInfo.width = swapChainExtent.width;
    frameBufferInfo.height = swapChainExtent.height;
    frameBufferInfo.layers = 1;

    VkResult result = vkCreateFramebuffer(device, &frameBufferInfo, nullptr, swapChainFrameBuffers[i].replace());
    std::ostringstream out;
    out << "Создание Frame Buffer " << (i + 1) << " из " << swapChainImageViews.size();
    checkResult(result, out.str());
  }
}

#pragma clang diagnostic pop

void RenderCore::initVulkan_createCommandPool() {
  VkCommandPoolCreateInfo poolInfo = {};
  poolInfo.sType = VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO;
  poolInfo.queueFamilyIndex = queueFamilyIndices.graphicsIndex();
  poolInfo.flags = 0; // Optional

  VkResult result = vkCreateCommandPool(device, &poolInfo, nullptr, commandPool.replace());
  checkResult(result, "Создание пула команд");

}

#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wreturn-stack-address"

void RenderCore::initVulkan_createCommandBuffers() {
  if (!commandBuffers.empty()) {
    vkFreeCommandBuffers(device, commandPool, static_cast<uint32_t>(commandBuffers.size()), commandBuffers.data());
  }

  commandBuffers.resize(swapChainFrameBuffers.size());

  VkCommandBufferAllocateInfo allocInfo = {};
  allocInfo.sType = VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO;
  allocInfo.commandPool = commandPool;
  allocInfo.level = VK_COMMAND_BUFFER_LEVEL_PRIMARY;
  allocInfo.commandBufferCount = (uint32_t) commandBuffers.size();

  VkResult result = vkAllocateCommandBuffers(device, &allocInfo, commandBuffers.data());
  checkResult(result, "Выделение CommandBuffers");

  for (size_t i = 0; i < commandBuffers.size(); i++) {
    VkCommandBufferBeginInfo beginInfo = {};
    beginInfo.sType = VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO;
    beginInfo.flags = VK_COMMAND_BUFFER_USAGE_SIMULTANEOUS_USE_BIT;
    beginInfo.pInheritanceInfo = nullptr; // Optional

    vkBeginCommandBuffer(commandBuffers[i], &beginInfo);

    VkRenderPassBeginInfo renderPassInfo = {};
    renderPassInfo.sType = VK_STRUCTURE_TYPE_RENDER_PASS_BEGIN_INFO;
    renderPassInfo.renderPass = renderPass;
    renderPassInfo.framebuffer = swapChainFrameBuffers[i];

    renderPassInfo.renderArea.offset = {0, 0};
    renderPassInfo.renderArea.extent = swapChainExtent;

    VkClearValue clearColor = {0.0f, 0.0f, 0.0f, 1.0f};
    renderPassInfo.clearValueCount = 1;
    renderPassInfo.pClearValues = &clearColor;

    vkCmdBeginRenderPass(commandBuffers[i], &renderPassInfo, VK_SUBPASS_CONTENTS_INLINE);

    vkCmdBindPipeline(commandBuffers[i], VK_PIPELINE_BIND_POINT_GRAPHICS, graphicsPipeline);

    VkBuffer vertexBuffers[] = {vertexBuffer};
    VkDeviceSize offsets[] = {0};
    vkCmdBindVertexBuffers(commandBuffers[i], 0, 1, vertexBuffers, offsets);

    vkCmdDraw(
        commandBuffers[i],
        /*vertexCount*/ static_cast<uint32_t>(getVertices().size()),
        1,
        /*firstVertex*/0,
        /*firstInstance*/0
    );

    vkCmdEndRenderPass(commandBuffers[i]);

    result = vkEndCommandBuffer(commandBuffers[i]);
    std::ostringstream out;
    out << "Формирование командного буфера " << (i + 1) << " из " << commandBuffers.size();
    checkResult(result, out.str());
  }
}

#pragma clang diagnostic pop

void RenderCore::initVulkan_createSemaphores() {
  VkSemaphoreCreateInfo semaphoreInfo = {};
  semaphoreInfo.sType = VK_STRUCTURE_TYPE_SEMAPHORE_CREATE_INFO;

  VkResult result;

  result = vkCreateSemaphore(device, &semaphoreInfo, nullptr, imageAvailableSemaphore.replace());
  checkResult(result, "Создание семаформа imageAvailableSemaphore");

  result = vkCreateSemaphore(device, &semaphoreInfo, nullptr, renderFinishedSemaphore.replace());
  checkResult(result, "Создание семаформа renderFinishedSemaphore");
}

#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wreturn-stack-address"

void RenderCore::drawFrame() {

  static long tickCount = 0;
  static auto time = std::chrono::high_resolution_clock::now();
  auto cur_time = std::chrono::high_resolution_clock::now();

  tickCount++;

  long duration = std::chrono::duration_cast<std::chrono::nanoseconds>(cur_time - time).count();
  if (duration > 4e9) {
    double seconds = (double) duration / 1e9;
    double ticksPerSecond = (double) tickCount / seconds;
    std::cout << "--- FPS " << ticksPerSecond << std::endl;
    time = cur_time;
    tickCount = 0;
  }

  uint32_t imageIndex;
  VkResult result = vkAcquireNextImageKHR(device, swapChain,
                                          std::numeric_limits<uint64_t>::max(),
                                          imageAvailableSemaphore,
                                          VK_NULL_HANDLE, &imageIndex);

  if (result == VK_ERROR_OUT_OF_DATE_KHR) {
    recreateSwapChain();
    return;
  }
  if (result != VK_SUCCESS && result != VK_SUBOPTIMAL_KHR) {
    throw std::runtime_error("Не могу запросить подходящую swap chain image!");
  }

  VkSubmitInfo submitInfo = {};
  submitInfo.sType = VK_STRUCTURE_TYPE_SUBMIT_INFO;

  VkSemaphore waitSemaphores[] = {imageAvailableSemaphore};
  VkPipelineStageFlags waitStages[] = {VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT};
  submitInfo.waitSemaphoreCount = 1;
  submitInfo.pWaitSemaphores = waitSemaphores;
  submitInfo.pWaitDstStageMask = waitStages;

  submitInfo.commandBufferCount = 1;
  submitInfo.pCommandBuffers = &commandBuffers[imageIndex];

  VkSemaphore signalSemaphores[] = {renderFinishedSemaphore};
  submitInfo.signalSemaphoreCount = 1;
  submitInfo.pSignalSemaphores = signalSemaphores;

  result = vkQueueSubmit(graphicsQueue, 1, &submitInfo, VK_NULL_HANDLE);
  checkResult(result, ".Запуск очереди graphicsQueue");

  VkPresentInfoKHR presentInfo = {};
  presentInfo.sType = VK_STRUCTURE_TYPE_PRESENT_INFO_KHR;

  presentInfo.waitSemaphoreCount = 1;
  presentInfo.pWaitSemaphores = signalSemaphores;

  VkSwapchainKHR swapChains[] = {swapChain};
  presentInfo.swapchainCount = 1;
  presentInfo.pSwapchains = swapChains;

  presentInfo.pImageIndices = &imageIndex;

  presentInfo.pResults = nullptr; // Optional

  result = vkQueuePresentKHR(presentQueue, &presentInfo);
  if (result == VK_ERROR_OUT_OF_DATE_KHR || result == VK_SUBOPTIMAL_KHR) {
    recreateSwapChain();
  } else if (result != VK_SUCCESS) {
    throw std::runtime_error("failed to present swap chain image!");
  }
}

#pragma clang diagnostic pop

void RenderCore::onResize(int newWidth, int newHeight) {
  width = newWidth;
  height = newHeight;
#ifdef TRACE
  std::cout << "RESIZING newWidth = " << newWidth << ", newHeight = " << newHeight << std::endl;
#endif
  recreateSwapChain();
}

uint32_t RenderCore::findMemoryTypeIndex(uint32_t typeFilter, VkMemoryPropertyFlags properties) {
  VkPhysicalDeviceMemoryProperties memProperties; // NOLINT
  vkGetPhysicalDeviceMemoryProperties(physicalDevice, &memProperties);

  for (uint32_t i = 0; i < memProperties.memoryTypeCount; i++) {
    if ((typeFilter & (1 << i)) && (memProperties.memoryTypes[i].propertyFlags & properties) == properties) {
      return i;
    }
  }

  throw std::runtime_error("failed to find suitable memory type!");
}

void RenderCore::initVulkan_createVertexBuffer() {

  auto vertices = getVertices();

  VkBufferCreateInfo bufferInfo = {};
  bufferInfo.sType = VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO;
  bufferInfo.size = sizeof(vertices[0]) * vertices.size();
  bufferInfo.usage = VK_BUFFER_USAGE_VERTEX_BUFFER_BIT;
  bufferInfo.sharingMode = VK_SHARING_MODE_EXCLUSIVE;

  VkResult result = vkCreateBuffer(device, &bufferInfo, nullptr, vertexBuffer.replace());
  checkResult(result, "Создание буфера вершин");

  VkMemoryRequirements memRequirements; // NOLINT
  vkGetBufferMemoryRequirements(device, vertexBuffer, &memRequirements);

  VkMemoryAllocateInfo allocInfo = {};
  allocInfo.sType = VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO;
  allocInfo.allocationSize = memRequirements.size;
  allocInfo.memoryTypeIndex = findMemoryTypeIndex(memRequirements.memoryTypeBits,
                                                  VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT |
                                                  VK_MEMORY_PROPERTY_HOST_COHERENT_BIT);

  result = vkAllocateMemory(device, &allocInfo, nullptr, vertexBufferMemory.replace());
  checkResult(result, "Выделение памяти для буфера вершин");

  result = vkBindBufferMemory(device, vertexBuffer, vertexBufferMemory, 0);
  checkResult(result, "Связывание буфера вершин с памятью, выделенной для него");

  void *data;
  vkMapMemory(device, vertexBufferMemory, 0, bufferInfo.size, 0, &data);

  memcpy(data, vertices.data(), (size_t) bufferInfo.size);

  vkUnmapMemory(device, vertexBufferMemory);


}
