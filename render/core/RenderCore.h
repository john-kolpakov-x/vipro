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

private:
  int width, height;

  VDeleter<VkInstance> instance{vkDestroyInstance};
};

#endif //VIPRO_RENDER_CORE_RENDER_CORE_H
