/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_leleliu008_newton_base_audio_PCM2MP3 */

#ifndef _Included_com_leleliu008_newton_base_audio_PCM2MP3
#define _Included_com_leleliu008_newton_base_audio_PCM2MP3
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_leleliu008_newton_base_audio_PCM2MP3
 * Method:    init
 * Signature: (IIII)Z
 */
JNIEXPORT jboolean JNICALL Java_com_leleliu008_newton_base_audio_PCM2MP3_init
  (JNIEnv *, jobject, jint, jint, jint, jint);

/*
 * Class:     com_leleliu008_newton_base_audio_PCM2MP3
 * Method:    encode
 * Signature: ([SI)[B
 */
JNIEXPORT jbyteArray JNICALL Java_com_leleliu008_newton_base_audio_PCM2MP3_encode
  (JNIEnv *, jobject, jshortArray, jint);

/*
 * Class:     com_leleliu008_newton_base_audio_PCM2MP3
 * Method:    destroy
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_leleliu008_newton_base_audio_PCM2MP3_destroy
  (JNIEnv *, jobject);

/*
 * Class:     com_leleliu008_newton_base_audio_PCM2MP3
 * Method:    convert
 * Signature: (Ljava/lang/String;Ljava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_com_leleliu008_newton_base_audio_PCM2MP3_convert
  (JNIEnv *, jclass, jstring, jstring);

#ifdef __cplusplus
}
#endif
#endif