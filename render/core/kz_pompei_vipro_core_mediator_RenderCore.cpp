#include <iostream>
#include "kz_pompei_vipro_core_mediator_RenderCore.h"
#include "RenderCore.h"

#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCUnusedGlobalDeclarationInspection"

jfieldID reference_RenderCore;
jclass class_RenderCore;

/*
 * Class:     kz_pompei_vipro_core_mediator_RenderCore
 * Method:    init0
 * Signature: ()V
 */
extern "C"
JNIEXPORT void JNICALL Java_kz_pompei_vipro_core_mediator_RenderCore_init0
    (JNIEnv *env, jclass renderCoreClass) {

  //
  // class RenderCore
  //

  class_RenderCore = renderCoreClass;

  reference_RenderCore = env->GetFieldID(class_RenderCore, "reference", "J");
  if (!reference_RenderCore) {
    std::cerr << R"(env->GetFieldID(class_RenderCore, "reference", "J"))" << std::endl;
    return;
  }

}

/*
 * Class:     kz_pompei_vipro_core_mediator_RenderCore
 * Method:    initReference
 * Signature: ()V
 */
extern "C"
JNIEXPORT void JNICALL Java_kz_pompei_vipro_core_mediator_RenderCore_initReference
    (JNIEnv *env, jobject renderCoreObject) {

  auto core = new RenderCore();

  env->SetLongField(renderCoreObject, reference_RenderCore, (jlong) core);
}

RenderCore *getReference(JNIEnv *env, jobject renderCoreObject) {
  return (RenderCore *) env->GetLongField(renderCoreObject, reference_RenderCore);
}

/*
 * Class:     kz_pompei_vipro_core_mediator_RenderCore
 * Method:    destroyReference
 * Signature: ()V
 */
extern "C" JNIEXPORT void JNICALL
Java_kz_pompei_vipro_core_mediator_RenderCore_destroyReference
    (JNIEnv *env, jobject renderCoreObject) {
  delete getReference(env, renderCoreObject);
  env->SetLongField(renderCoreObject, reference_RenderCore, 0);
}

/*
 * Class:     kz_pompei_vipro_core_mediator_RenderCore
 * Method:    putSize
 * Signature: ([I)V
 */
extern "C" JNIEXPORT void JNICALL
Java_kz_pompei_vipro_core_mediator_RenderCore_putSize
    (JNIEnv *env, jobject renderCoreObject, jintArray out) {

  auto core = getReference(env, renderCoreObject);

  jint size[2];
  size[0] = core->getWidth();
  size[1] = core->getHeight();

  env->SetIntArrayRegion(out, 0, 2, size);

}

/*
 * Class:     kz_pompei_vipro_core_mediator_RenderCore
 * Method:    getSize
 * Signature: ()Lkz/pompei/vipro/core/mediator/Size;
 */
extern "C" JNIEXPORT jobject JNICALL
Java_kz_pompei_vipro_core_mediator_RenderCore_getSize
    (JNIEnv *env, jobject renderCoreObject) {

  jclass class_Size = env->FindClass("kz/pompei/vipro/core/mediator/Size");
  if (!class_Size) {
    std::cerr << "Cannot load class Size" << std::endl;
    return nullptr;
  }

  jfieldID width_Size = env->GetFieldID(class_Size, "width", "I");
  if (!width_Size) {
    std::cerr << R"(env->GetFieldID(class_Size, "width", "I"))" << std::endl;
    return nullptr;
  }

  jfieldID height_Size = env->GetFieldID(class_Size, "height", "I");
  if (!height_Size) {
    std::cerr << R"(env->GetFieldID(class_Size, "height", "I"))" << std::endl;
    return nullptr;
  }

  jmethodID constructor_Size = env->GetMethodID(class_Size, "<init>", "()V");
  if (!constructor_Size) {
    std::cerr << R"(env->GetMethodID(class_Size, "<init>", "()V"))" << std::endl;
    return nullptr;
  }

  auto core = getReference(env, renderCoreObject);

  jobject size = env->NewObject(class_Size, constructor_Size);
  if (!size) {
    std::cerr << R"(env->NewObject(class_Size, constructor_Size))" << std::endl;
    return nullptr;
  }

  env->SetIntField(size, width_Size, core->getWidth());

  env->SetIntField(size, height_Size, core->getHeight());

  return size;
}

#pragma clang diagnostic pop