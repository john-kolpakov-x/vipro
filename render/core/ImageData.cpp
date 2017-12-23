#include <malloc.h>
#include <iostream>
#include "ImageData.h"

void ImageData::reallocate(int width, int height) {
  if (_width == width && _height == height) return; // NOLINT

  clean();

  if (width <= 0 || height <= 0) return;

  _width = width;
  _height = height;

  size_t length = static_cast<size_t>(width) * static_cast<size_t>(height) * 4;
  _data = malloc(length);
  if (!_data) {
    std::cerr << "Cannot allocate memory " << length << " bytes" << std::endl;
    clean();
  }
}

void ImageData::clean() {
  _width = _height = 0;
  if (_data) free(_data);
  _data = nullptr;
}
