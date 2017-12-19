#include "draw_data.h"

static Figure getPlane() {
  Figure ret = {};
  ret.vertices = {
      {{-0.5f, -0.5f, 0.0f}, {1.0f, 0.0f, 0.0f}},
      {{+0.5f, -0.5f, 0.0f}, {0.0f, 1.0f, 0.0f}},
      {{+0.5f, +0.5f, 0.0f}, {0.0f, 0.0f, 1.0f}},
      {{-0.5f, +0.5f, 0.0f}, {1.0f, 1.0f, 1.0f}},
  };
  ret.indices = {0, 2, 3, 0, 1, 2,};

  return ret;
}

static Figure getCube() {
  Figure ret = {};
  ret.vertices = {
      {{-0.25f, -0.25f, 0.1f}, {1.0f, 1.0f, 1.0f}},
      {{+0.25f, -0.25f, 0.1f}, {1.0f, 1.0f, 1.0f}},
      {{+0.25f, +0.25f, 0.1f}, {1.0f, 1.0f, 1.0f}},
      {{-0.25f, +0.25f, 0.1f}, {1.0f, 1.0f, 1.0f}},
      {{-0.25f, -0.25f, 0.6f}, {1.0f, 0.0f, 0.0f}},
      {{+0.25f, -0.25f, 0.6f}, {0.0f, 1.0f, 0.0f}},
      {{+0.25f, +0.25f, 0.6f}, {0.0f, 0.0f, 1.0f}},
      {{-0.25f, +0.25f, 0.6f}, {0.0f, 1.0f, 1.0f}},
  };
  ret.indices = {
      0, 1, 3, 1, 2, 3,
      4, 5, 7, 5, 6, 7,
      0, 1, 5, 0, 5, 4,
      1, 6, 5, 1, 2, 6,
      2, 7, 6, 2, 3, 7,
      3, 4, 7, 3, 1, 4,
  };

  return ret;
}

static Figure getRect() {
  Figure ret = {};
  ret.vertices = {
      {{+0.80f, +0.80f, +0.8f}, {0.0f, 0.5f, 1.0f}},
      {{+0.85f, +0.80f, +0.8f}, {0.0f, 0.5f, 1.0f}},
      {{+0.85f, +0.85f, +0.8f}, {0.0f, 0.7f, 0.0f}},
      {{+0.80f, +0.85f, +0.8f}, {0.0f, 1.0f, 1.0f}},
  };
  ret.indices = {1, 0, 2, 2, 0, 3,};

  return ret;
}

Figure getTotalFigure() {
  Figure ret = {};
  ret.append(getCube());
  ret.append(getPlane());
  ret.append(getRect(), +2);
  return ret;
}

void Figure::append(Figure figure, float colorOffset) {
  auto indexOffset = (uint32_t) vertices.size();

  for (auto &ref : figure.vertices) {
    Vertex v = ref;
    v.color.r += colorOffset;
    v.color.g += colorOffset;
    v.color.b += colorOffset;
    vertices.push_back(v);
  }

  for (auto index : figure.indices) {
    indices.push_back(index + indexOffset);
  }
}
