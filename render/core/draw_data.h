#ifndef VIPRO_RENDER_CORE_DRAW_DATA_H
#define VIPRO_RENDER_CORE_DRAW_DATA_H

#include "use_glfw3.h"
#include <glm/glm.hpp>
#include <vector>
#include <array>

struct Vertex {
  glm::vec3 pos;
  glm::vec3 color;
  glm::vec2 texCoord;

  static VkVertexInputBindingDescription getBindingDescription() {
    VkVertexInputBindingDescription bindingDescription = {};
    bindingDescription.binding = 0;
    bindingDescription.stride = sizeof(Vertex);
    bindingDescription.inputRate = VK_VERTEX_INPUT_RATE_VERTEX;
    return bindingDescription;
  }

  static std::array<VkVertexInputAttributeDescription, 3> getAttributeDescriptions() {
    std::array<VkVertexInputAttributeDescription, 3> attributeDescriptions = {};

    attributeDescriptions[0].binding = 0;
    attributeDescriptions[0].location = 0;
    attributeDescriptions[0].format = VK_FORMAT_R32G32B32_SFLOAT;
    attributeDescriptions[0].offset = static_cast<uint32_t>(offsetof(Vertex, pos));

    attributeDescriptions[1].binding = 0;
    attributeDescriptions[1].location = 1;
    attributeDescriptions[1].format = VK_FORMAT_R32G32B32_SFLOAT;
    attributeDescriptions[1].offset = static_cast<uint32_t>(offsetof(Vertex, color));

    attributeDescriptions[2].binding = 0;
    attributeDescriptions[2].location = 2;
    attributeDescriptions[2].format = VK_FORMAT_R32G32_SFLOAT;
    attributeDescriptions[2].offset = static_cast<uint32_t>(offsetof(Vertex, texCoord));

    return attributeDescriptions;
  }
};

#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCUnusedGlobalDeclarationInspection"

struct UniformBufferObject {
  glm::mat4 model;
  glm::mat4 view;
  glm::mat4 projection;
  glm::mat4 projection2;
};

#pragma clang diagnostic pop

struct Figure {
  std::vector<Vertex> vertices;
  std::vector<uint32_t> indices;

  void append(Figure figure, float colorOffset = 0.0f);
};

Figure getTotalFigure();

#endif //VIPRO_RENDER_CORE_DRAW_DATA_H
