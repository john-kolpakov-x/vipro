#include <iostream>
#include <locale>
#include <codecvt>
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
    std::wstring_convert<std::codecvt_utf8_utf16<char16_t>, char16_t> conversion;
    auto s = conversion.from_bytes(e.what());
    jstring message = env->NewString((const jchar *) s.c_str(), static_cast<jsize>(s.length()));
    env->CallVoidMethod(renderCoreObject, throwMessage_RenderCore, message);
  }

}

#pragma clang diagnostic pop