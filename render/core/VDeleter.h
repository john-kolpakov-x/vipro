#ifndef VIPRO_RENDER_CORE_V_DELETER_H
#define VIPRO_RENDER_CORE_V_DELETER_H

#include <functional>
#include "use_glfw3.h"

template<typename T>
class VDeleter {

private:
  T object{VK_NULL_HANDLE};
  std::function<void(T)> deleter;

  void cleanup() {
    if (object != VK_NULL_HANDLE) {
      deleter(object);
    }
    object = VK_NULL_HANDLE;
  }

public:
  VDeleter() : VDeleter([](T, VkAllocationCallbacks *) {}) {}

  VDeleter(std::function<void(T, VkAllocationCallbacks *)> deleteFunc) {
    this->deleter = [=](T obj) { deleteFunc(obj, nullptr); };
  }

  VDeleter(const VDeleter<VkInstance> &instance,
           std::function<void(VkInstance, T, VkAllocationCallbacks *)> deleteFunc) {
    this->deleter = [&instance, deleteFunc](T obj) { deleteFunc(instance, obj, nullptr); };
  }

  VDeleter(const VDeleter<VkDevice> &device, std::function<void(VkDevice, T, VkAllocationCallbacks *)> deleteFunc) {
    this->deleter = [&device, deleteFunc](T obj) { deleteFunc(device, obj, nullptr); };
  }

  ~VDeleter() {
    cleanup();
  }

  const T *operator&() const {
    return &object;
  }

  T *replace() {
    cleanup();
    return &object;
  }

  operator T() const {
    return object;
  }

  void operator=(T rhs) {
    cleanup();
    object = rhs;
  }

  template<typename V>
  bool operator==(V rhs) {
    return object == T(rhs);
  }

};

#endif //VIPRO_RENDER_CORE_V_DELETER_H
