#ifndef VIPRO_RENDER_CORE_RENDER_CORE_H
#define VIPRO_RENDER_CORE_RENDER_CORE_H

#include "use_glfw3.h"
#include "VDeleter.h"

class RenderCore {
public:
  RenderCore();

  int getWidth();

  int getHeight();

  void initialize();

  void mainLoop();

private:
  int width, height;

  GLFWwindow* window;
  VDeleter<VkInstance> instance{vkDestroyInstance};

  void initWindow();

  void initVulkan();
};

#endif //VIPRO_RENDER_CORE_RENDER_CORE_H
