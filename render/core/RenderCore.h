#ifndef VIPRO_RENDER_CORE_RENDER_CORE_H
#define VIPRO_RENDER_CORE_RENDER_CORE_H

#include "use_glfw3.h"
#include <vector>
#include "VDeleter.h"
#include "vk_util.h"
#include "options.h"

class RenderCore {
public:
  RenderCore();

  ~RenderCore();

  int getWidth();

  int getHeight();

  void initialize();

  void mainLoop();

private:
  int width, height;

  GLFWwindow *window;
  VDeleter<VkInstance> instance{vkDestroyInstance};
  VDeleter<VkDebugReportCallbackEXT> callback{instance, destroyDebugReportCallbackEXT};
  VDeleter<VkSurfaceKHR> surface{instance, vkDestroySurfaceKHR};
  VkPhysicalDevice physicalDevice = VK_NULL_HANDLE;
  QueueFamilyIndices queueFamilyIndices;
  VkQueue presentQueue;
  VDeleter<VkDevice> device{vkDestroyDevice};
  VDeleter<VkSwapchainKHR> swapChain{device, vkDestroySwapchainKHR};
  std::vector<VkImage> swapChainImages;
  VkFormat swapChainImageFormat;
  VkExtent2D swapChainExtent;
  std::vector<VDeleter<VkImageView>> swapChainImageViews;

  void initWindow();

  void initVulkan();

  void initVulkan_createInstance();

  void initVulkan_setupDebugCallback();

  void initVulkan_createSurface();

  void initVulkan_selectPhysicalDevice();

  void initVulkan_createLogicalDevice();

  void initVulkan_createSwapChain();

  void initVulkan_createImageViews();

  QueueFamilyIndices findQueueFamilyIndicesIn(VkPhysicalDevice aPhysicalDevice);

  bool isDeviceSuitable(VkPhysicalDevice aPhysicalDevice, int deviceIndex);

  bool checkValidationLayerSupport();

  std::vector<const char *> getRequiredExtensions();

  void checkExtensions(std::vector<const char *> requiredExtensions);

  friend VKAPI_ATTR VkBool32 VKAPI_CALL debugCallbackStatic( // NOLINT
      VkDebugReportFlagsEXT flags, VkDebugReportObjectTypeEXT objType,
      uint64_t obj, size_t location, int32_t code,
      const char *layerPrefix, const char *msg, void *userData);

  bool debugCallback(
      VkDebugReportFlagsEXT flags, VkDebugReportObjectTypeEXT objType,
      uint64_t obj, size_t location, int32_t code,
      const char *layerPrefix, const char *msg);

  bool checkDeviceExtensionSupport(VkPhysicalDevice aPhysicalDevice);

  void createShaderModule(ShaderCode shaderCode, VDeleter<VkShaderModule> &shaderModule, const std::string &name);

  void initVulkan_createGraphicsPipeline();
};
#endif //VIPRO_RENDER_CORE_RENDER_CORE_H
