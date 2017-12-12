#ifndef VIPRO_RENDER_CORE_DRAW_DATA_H
#define VIPRO_RENDER_CORE_DRAW_DATA_H

#include "use_glfw3.h"
#include <glm/glm.hpp>
#include <vector>
#include <array>

struct Vertex {
  glm::vec2 pos;
  glm::vec3 color;

  static VkVertexInputBindingDescription getBindingDescription() {
    VkVertexInputBindingDescription bindingDescription = {};
    bindingDescription.binding = 0;
    bindingDescription.stride = sizeof(Vertex);
    bindingDescription.inputRate = VK_VERTEX_INPUT_RATE_VERTEX;

    return bindingDescription;
  }

  static std::array<VkVertexInputAttributeDescription, 2> getAttributeDescriptions() {
    std::array<VkVertexInputAttributeDescription, 2> attributeDescriptions = {};

    attributeDescriptions[0].binding = 0;
    attributeDescriptions[0].location = 0;
    attributeDescriptions[0].format = VK_FORMAT_R32G32_SFLOAT;
    attributeDescriptions[0].offset = static_cast<uint32_t>(offsetof(Vertex, pos));

    attributeDescriptions[1].binding = 0;
    attributeDescriptions[1].location = 1;
    attributeDescriptions[1].format = VK_FORMAT_R32G32B32_SFLOAT;
    attributeDescriptions[1].offset = static_cast<uint32_t>(offsetof(Vertex, color));

    return attributeDescriptions;
  }

};

std::vector<Vertex> getVertices();

std::vector<uint16_t> getIndices();

struct UniformBufferObject {
  glm::mat4 model;
  glm::mat4 view;
  glm::mat4 projection;
};

#endif //VIPRO_RENDER_CORE_DRAW_DATA_H
