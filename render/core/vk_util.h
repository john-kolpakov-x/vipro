#ifndef VIPRO_RENDER_CORE_VK_UTIL_H
#define VIPRO_RENDER_CORE_VK_UTIL_H

#include "use_glfw3.h"

void checkResult(VkResult result, const char *placeMessage);

std::string translateVkResult(VkResult result);

#endif //VIPRO_RENDER_CORE_VK_UTIL_H
