#include "draw_data.h"

std::vector<Vertex> getVertices() {

  const std::vector<Vertex> vertices = {
      {{-0.5f,  -0.5f,  0.0f}, {1.0f, 0.0f, 0.0f}},
      {{+0.5f,  -0.5f,  0.0f}, {0.0f, 1.0f, 0.0f}},
      {{+0.5f,  +0.5f,  0.0f}, {0.0f, 0.0f, 1.0f}},
      {{-0.5f,  +0.5f,  0.0f}, {1.0f, 1.0f, 1.0f}},

      {{-0.25f, -0.25f, 0.1f}, {1.0f, 1.0f, 1.0f}},//04
      {{+0.25f, -0.25f, 0.1f}, {1.0f, 1.0f, 1.0f}},//05
      {{+0.25f, +0.25f, 0.1f}, {1.0f, 1.0f, 1.0f}},//06
      {{-0.25f, +0.25f, 0.1f}, {1.0f, 1.0f, 1.0f}},//07
      {{-0.25f, -0.25f, 0.6f}, {1.0f, 0.0f, 0.0f}},//08
      {{+0.25f, -0.25f, 0.6f}, {0.0f, 1.0f, 0.0f}},//09
      {{+0.25f, +0.25f, 0.6f}, {0.0f, 0.0f, 1.0f}},//10
      {{-0.25f, +0.25f, 0.6f}, {0.0f, 1.0f, 1.0f}},//11

  };

  return vertices;
}

std::vector<uint16_t> getIndices() {
  const std::vector<uint16_t> indices = {


      4, 5, 7, 5, 6, 7,
      8, 9, 11, 9, 10, 11,
      4, 5, 9, 4, 9, 8,
      5, 10, 9, 5, 6, 10,
      6, 11, 10, 6, 7, 11,
      7, 8, 11, 7, 4, 8,

      0, 2, 3, 0, 1, 2,
  };

  return indices;
}