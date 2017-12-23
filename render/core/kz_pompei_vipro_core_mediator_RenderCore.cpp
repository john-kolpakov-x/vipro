#include <iostream>
#include <locale>
#include <codecvt>
#include <sstream>
#include "kz_pompei_vipro_core_mediator_RenderCore.h"
#include "RenderCore.h"

#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCUnusedGlobalDeclarationInspection"

jfieldID reference_RenderCore;
jclass class_RenderCore;
jmethodID throwMessage_RenderCore;


/*
 * Class:     kz_pompei_vipro_core_mediator_RenderCore
 * Method:    init0
 * Signature: ()V
 */
class wide_string;

static void throw_error_to_java(JNIEnv *env, _jobject *renderCoreObject, const std::runtime_error &e) {
  std::__cxx11::wstring_convert<std::codecvt_utf8_utf16<char16_t>, char16_t> conversion;
  auto s = conversion.from_bytes(e.what());
  jstring message = env->NewString((const jchar *) s.c_str(), static_cast<jsize>(s.length()));
  env->CallVoidMethod(renderCoreObject, throwMessage_RenderCore, message);
}

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

  throwMessage_RenderCore = env->GetMethodID(renderCoreClass, "throwMessage", "(Ljava/lang/String;)V");
  if (!reference_RenderCore) {
    std::cerr << R"(env->GetMethodID(renderCoreClass, "throwMessage", "(Ljava/lang/String;)V")" << std::endl;
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
 * Method:    putTextureImageData
 * Signature: (II[B)V
 */
extern "C" JNIEXPORT void JNICALL
Java_kz_pompei_vipro_core_mediator_RenderCore_putTextureImageData
    (JNIEnv *env, jobject renderCoreObject, jint width, jint height, jbyteArray pixelsR8G8B8A8) {

  try {

    jsize length = env->GetArrayLength(pixelsR8G8B8A8);
    if (width * height * 4 != length) {
      std::ostringstream out;
      out << "putTextureImageData: width * height * 4 = " << width * height * 4
          << ", length = " << length
          << ": А они должны быть равны"
          << std::endl;

      throw std::runtime_error(out.str());
    }

    auto &textureImageData = getReference(env, renderCoreObject)->textureImageData;
    textureImageData.reallocate(width, height);

    env->GetByteArrayRegion(pixelsR8G8B8A8, 0, length, (jbyte *) textureImageData.data());
  }
  catch (const std::runtime_error &e) {
    throw_error_to_java(env, renderCoreObject, e);
  }
}


/*
 * Class:     kz_pompei_vipro_core_mediator_RenderCore
 * Method:    initialize
 * Signature: ()V
 */
extern "C" JNIEXPORT void JNICALL
Java_kz_pompei_vipro_core_mediator_RenderCore_initialize
    (JNIEnv *env, jobject renderCoreObject) {

  try {
    getReference(env, renderCoreObject)->initialize();
  }
  catch (const std::runtime_error &e) {
    throw_error_to_java(env, renderCoreObject, e);
  }

}

/*
 * Class:     kz_pompei_vipro_core_mediator_RenderCore
 * Method:    mainLoop
 * Signature: ()V
 */
extern "C" JNIEXPORT void JNICALL
Java_kz_pompei_vipro_core_mediator_RenderCore_mainLoop
    (JNIEnv *env, jobject renderCoreObject) {

  try {
    getReference(env, renderCoreObject)->mainLoop();
  }
  catch (const std::runtime_error &e) {
    std::wstring_convert<std::codecvt_utf8_utf16<char16_t>, char16_t> conversion;
    auto s = conversion.from_bytes(e.what());
    jstring message = env->NewString((const jchar *) s.c_str(), static_cast<jsize>(s.length()));
    env->CallVoidMethod(renderCoreObject, throwMessage_RenderCore, message);
  }

}

#pragma clang diagnostic pop