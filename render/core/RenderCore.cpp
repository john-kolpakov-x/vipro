#include <stdexcept>
#include "RenderCore.h"
#include "vk_util.h"

RenderCore::RenderCore() : width(800), height(600)// NOLINT
{}

int RenderCore::getWidth() {
  return width;
}

int RenderCore::getHeight() {
  return height;
}

void RenderCore::initialize() {
  VkApplicationInfo appInfo = {};
  appInfo.sType = VK_STRUCTURE_TYPE_APPLICATION_INFO;
  appInfo.pApplicationName = "Hello Triangle";
  appInfo.applicationVersion = VK_MAKE_VERSION(1, 0, 0);
  appInfo.pEngineName = "No Engine";
  appInfo.engineVersion = VK_MAKE_VERSION(1, 0, 0);
  appInfo.apiVersion = VK_API_VERSION_1_0;

  VkInstanceCreateInfo createInfo = {};
  createInfo.sType = VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO;
  createInfo.pApplicationInfo = &appInfo;

  unsigned int glfwExtensionCount = 0;
  const char **glfwExtensions;

  glfwExtensions = glfwGetRequiredInstanceExtensions(&glfwExtensionCount);

  createInfo.enabledExtensionCount = glfwExtensionCount;
  createInfo.ppEnabledExtensionNames = glfwExtensions;

  createInfo.enabledLayerCount = 0;

  VkResult result = vkCreateInstance(&createInfo, nullptr, instance.replace());\
  checkResult(result, "vkCreateInstance");
}
