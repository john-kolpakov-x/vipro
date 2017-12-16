#include "draw_data.h"

std::vector<Vertex> getVertices() {

  const std::vector<Vertex> vertices = {
      {{-0.5f, -0.5f, 0.0f}, {1.0f, 0.0f, 0.0f}},
      {{+0.5f, -0.5f, 0.0f}, {0.0f, 1.0f, 0.0f}},
      {{+0.5f, +0.5f, 0.0f}, {0.0f, 0.0f, 1.0f}},
      {{-0.5f, +0.5f, 0.0f}, {1.0f, 1.0f, 1.0f}},
  };

  return vertices;
}

std::vector<uint16_t> getIndices() {
  const std::vector<uint16_t> indices = {
      0, 2, 3, 0, 1, 2,
  };

  return indices;
}
