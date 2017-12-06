#ifndef VIPRO_RENDER_CORE_VK_UTIL_H
#define VIPRO_RENDER_CORE_VK_UTIL_H

#include <set>
#include "use_glfw3.h"

void checkResult(VkResult result, const char *placeMessage);

std::string translateVkResult(VkResult result);

VkResult createDebugReportCallbackEXT(VkInstance instance,
                                      const VkDebugReportCallbackCreateInfoEXT *pCreateInfo,
                                      const VkAllocationCallbacks *pAllocator,
                                      VkDebugReportCallbackEXT *pCallback);

void destroyDebugReportCallbackEXT(VkInstance instance, VkDebugReportCallbackEXT callback,
                                   const VkAllocationCallbacks *pAllocator);

VKAPI_ATTR VkBool32 VKAPI_CALL debugCallbackStatic(
    VkDebugReportFlagsEXT flags, VkDebugReportObjectTypeEXT objType,
    uint64_t obj, size_t location, int32_t code,
    const char *layerPrefix, const char *msg, void *userData);

struct QueueFamilyIndices {
private:
  int _graphicsIndex = -1, _presentIndex = -1;

public:

  uint32_t graphicsIndex() {
    return static_cast<uint32_t>(_graphicsIndex);
  }

  uint32_t presentIndex() {
    return static_cast<uint32_t>(_presentIndex);
  }

  void goodGraphicsIndex(int index) {
    if (_graphicsIndex < 0) _graphicsIndex = index;
  }

  void goodPresentIndex(int index) {
    if (_presentIndex < 0) _presentIndex = index;
  }

  bool allFound() {
    return _graphicsIndex >= 0 && _presentIndex >= 0;
  }

  std::set<uint32_t> uniqueQueueFamilies() {
    std::set<uint32_t> ret = {graphicsIndex(), presentIndex()};
    return ret;
  }
};

#endif //VIPRO_RENDER_CORE_VK_UTIL_H
