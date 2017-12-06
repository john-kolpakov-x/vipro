#include <sstream>
#include <iostream>
#include "vk_util.h"
#include "RenderCore.h"

void checkResult(VkResult result, const char *placeMessage) {
  if (result == VK_SUCCESS) {
    std::cout << placeMessage << " - OK" << std::endl;
    return;
  }
  std::ostringstream out;
  out << placeMessage << " - ERROR : " << translateVkResult(result);
  throw std::runtime_error(out.str());
}

std::string translateVkResult(VkResult result) {
  switch (result) {
    // Success codes
    case VK_SUCCESS:
      return "Command successfully completed.";
    case VK_NOT_READY:
      return "A fence or query has not yet completed.";
    case VK_TIMEOUT:
      return "A wait operation has not completed in the specified time.";
    case VK_EVENT_SET:
      return "An event is signaled.";
    case VK_EVENT_RESET:
      return "An event is unsignaled.";
    case VK_INCOMPLETE:
      return "A return array was too small for the result.";
    case VK_SUBOPTIMAL_KHR:
      return "A swapchain no longer matches the surface properties exactly, but can still be used to present to the surface successfully.";

      // Error codes
    case VK_ERROR_OUT_OF_HOST_MEMORY:
      return "A host memory allocation has failed.";
    case VK_ERROR_OUT_OF_DEVICE_MEMORY:
      return "A device memory allocation has failed.";
    case VK_ERROR_INITIALIZATION_FAILED:
      return "Initialization of an object could not be completed for implementation-specific reasons.";
    case VK_ERROR_DEVICE_LOST:
      return "The logical or physical device has been lost.";
    case VK_ERROR_MEMORY_MAP_FAILED:
      return "Mapping of a memory object has failed.";
    case VK_ERROR_LAYER_NOT_PRESENT:
      return "A requested layer is not present or could not be loaded.";
    case VK_ERROR_EXTENSION_NOT_PRESENT:
      return "A requested extension is not supported.";
    case VK_ERROR_FEATURE_NOT_PRESENT:
      return "A requested feature is not supported.";
    case VK_ERROR_INCOMPATIBLE_DRIVER:
      return "The requested version of Vulkan is not supported by the driver or is otherwise incompatible for implementation-specific reasons.";
    case VK_ERROR_TOO_MANY_OBJECTS:
      return "Too many objects of the type have already been created.";
    case VK_ERROR_FORMAT_NOT_SUPPORTED:
      return "A requested format is not supported on this device.";
    case VK_ERROR_SURFACE_LOST_KHR:
      return "A surface is no longer available.";
    case VK_ERROR_NATIVE_WINDOW_IN_USE_KHR:
      return "The requested window is already connected to a VkSurfaceKHR, or to some other non-Vulkan API.";
    case VK_ERROR_OUT_OF_DATE_KHR:
      return "A surface has changed in such a way that it is no longer compatible with the swapchain, and further presentation requests using the swapchain will fail. Applications must query the new surface properties and recreate their swapchain if they wish to continue presenting to the surface.";
    case VK_ERROR_INCOMPATIBLE_DISPLAY_KHR:
      return "The display used by a swapchain does not use the same presentable image layout, or is incompatible in a way that prevents sharing an image.";
    case VK_ERROR_VALIDATION_FAILED_EXT:
      return "A validation layer found an error.";
    default:
      std::ostringstream out;
      out << "Unknown VkResult = ";
      out << result;
      return out.str();
      //return String.format("%s [%d]", "Unknown", Integer.valueOf(result));
  }
}


VkResult createDebugReportCallbackEXT(VkInstance instance,
                                      const VkDebugReportCallbackCreateInfoEXT *pCreateInfo,
                                      const VkAllocationCallbacks *pAllocator,
                                      VkDebugReportCallbackEXT *pCallback) {
  auto func = (PFN_vkCreateDebugReportCallbackEXT) vkGetInstanceProcAddr(instance, "vkCreateDebugReportCallbackEXT");
  if (func != nullptr) {
    return func(instance, pCreateInfo, pAllocator, pCallback);
  } else {
    return VK_ERROR_EXTENSION_NOT_PRESENT;
  }
}

void destroyDebugReportCallbackEXT(VkInstance instance, VkDebugReportCallbackEXT callback,
                                   const VkAllocationCallbacks *pAllocator) {
  auto func = (PFN_vkDestroyDebugReportCallbackEXT) vkGetInstanceProcAddr(instance, "vkDestroyDebugReportCallbackEXT");
  if (func != nullptr) {
    func(instance, callback, pAllocator);
  }
}

VKAPI_ATTR VkBool32 VKAPI_CALL debugCallbackStatic(
    VkDebugReportFlagsEXT flags, VkDebugReportObjectTypeEXT objType,
    uint64_t obj, size_t location, int32_t code,
    const char *layerPrefix, const char *msg, void *userData) {

  bool ret = ((RenderCore *) userData)->debugCallback(flags, objType, obj, location, code, layerPrefix, msg);

  return ret ? VK_TRUE : VK_FALSE;
}

SwapChainSupportDetails querySwapChainSupport(VkPhysicalDevice device, VkSurfaceKHR surface) {
  SwapChainSupportDetails details;

  vkGetPhysicalDeviceSurfaceCapabilitiesKHR(device, surface, &details.capabilities);

  uint32_t formatCount;
  vkGetPhysicalDeviceSurfaceFormatsKHR(device, surface, &formatCount, nullptr);

  if (formatCount != 0) {
    details.formats.resize(formatCount);
    vkGetPhysicalDeviceSurfaceFormatsKHR(device, surface, &formatCount, details.formats.data());
  }

  uint32_t presentModeCount;
  vkGetPhysicalDeviceSurfacePresentModesKHR(device, surface, &presentModeCount, nullptr);

  if (presentModeCount != 0) {
    details.presentModes.resize(presentModeCount);
    vkGetPhysicalDeviceSurfacePresentModesKHR(device, surface, &presentModeCount, details.presentModes.data());
  }

  return details;
}