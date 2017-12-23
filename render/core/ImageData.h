#ifndef VIPRO_RENDER_CORE_IMAGE_DATA_H
#define VIPRO_RENDER_CORE_IMAGE_DATA_H

class ImageData {
private:
  int _width, _height;
  void *_data;

  void clean();

public:
  ImageData() : _width(0), _height(0), _data(nullptr) {}

  ~ImageData() { clean(); }

  void reallocate(int width, int height);

  int width() { return _width; }

  int height() { return _height; }

  void *data() { return _data; };

  bool hasData() { return _data != nullptr; }
};

#endif //VIPRO_RENDER_CORE_IMAGE_DATA_H
