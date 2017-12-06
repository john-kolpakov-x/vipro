#ifndef VIPRO_RENDER_CORE_VK_UTIL_H
#define VIPRO_RENDER_CORE_VK_UTIL_H

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

#endif //VIPRO_RENDER_CORE_VK_UTIL_H
