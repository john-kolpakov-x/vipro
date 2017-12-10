#include "vertex_data.h"

std::vector<Vertex> getVertices() {
  const std::vector<Vertex> vertices = {
      {{0.0f, -0.5f}, {1.0f, 1.0f, 1.0f}},
      {{0.5f, 0.5f}, {0.0f, 1.0f, 0.0f}},
      {{-0.5f, 0.5f}, {0.0f, 0.0f, 1.0f}}
  };

  return vertices;
}
