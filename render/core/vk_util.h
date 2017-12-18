#ifndef VIPRO_RENDER_CORE_VK_UTIL_H
#define VIPRO_RENDER_CORE_VK_UTIL_H

#include <set>
#include <vector>
#include "use_glfw3.h"
#include "options.h"
#include "VDeleter.h"
#include "shaders/shader_data.h"

void checkResult(VkResult result, const std::string &placeMessage);

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

  uint32_t graphicsIndex() const {
    return static_cast<uint32_t>(_graphicsIndex);
  }

  uint32_t presentIndex() const {
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

struct SwapChainSupportDetails {
  VkSurfaceCapabilitiesKHR capabilities;
  std::vector<VkSurfaceFormatKHR> formats;
  std::vector<VkPresentModeKHR> presentModes;

  bool notAdequate() {
    return formats.empty() || presentModes.empty();
  }
};

const char *surfaceFormatName(VkFormat format);

const char *colorSpaceName(VkColorSpaceKHR colorSpace);

std::string VkExtent2D_to_str(const VkExtent2D &value);

SwapChainSupportDetails querySwapChainSupport(VkPhysicalDevice device, VkSurfaceKHR surface);

VkSurfaceFormatKHR chooseSwapSurfaceFormat(const std::vector<VkSurfaceFormatKHR> &availableFormats);

VkPresentModeKHR chooseSwapPresentMode(const std::vector<VkPresentModeKHR> &availablePresentModes);

VkExtent2D chooseSwapExtent(const VkSurfaceCapabilitiesKHR &capabilities, int width, int height);

bool hasStencilComponent(VkFormat format);

#endif //VIPRO_RENDER_CORE_VK_UTIL_H
