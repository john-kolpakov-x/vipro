#ifndef VIPRO_RENDER_CORE_RENDER_CORE_H
#define VIPRO_RENDER_CORE_RENDER_CORE_H

#include <vector>
#include "use_glfw3.h"
#include "VDeleter.h"
#include "vk_util.h"

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

  void initWindow();

  void initVulkan();

  void initVulkan_createInstance();

  bool checkValidationLayerSupport();

  std::vector<const char *> getRequiredExtensions();

  void checkExtensions(std::vector<const char *> requiredExtensions);

  void initVulkan_setupDebugCallback();

  friend VKAPI_ATTR VkBool32 VKAPI_CALL debugCallbackStatic( // NOLINT
      VkDebugReportFlagsEXT flags, VkDebugReportObjectTypeEXT objType,
      uint64_t obj, size_t location, int32_t code,
      const char *layerPrefix, const char *msg, void *userData);

  bool debugCallback(
      VkDebugReportFlagsEXT flags, VkDebugReportObjectTypeEXT objType,
      uint64_t obj, size_t location, int32_t code,
      const char *layerPrefix, const char *msg);
};

#endif //VIPRO_RENDER_CORE_RENDER_CORE_H
