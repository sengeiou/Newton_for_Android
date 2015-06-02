#include "lame-3.98.4_libmp3lame/lame.h"
#include "pcm2mp3.h"

#define BUFFER_SIZE 4096

lame_t lame;

JNIEXPORT jboolean JNICALL Java_com_leleliu008_newton_base_audio_PCM2MP3_init
  (JNIEnv * env, jobject obj, jint pcmSampleRate, jint pcmSampleBit, jint pcmChannels, jint quality) {
	lame = lame_init();

	//设置通道数量
	lame_set_num_channels(lame, pcmChannels);

	//设置采样率
	lame_set_in_samplerate(lame, pcmSampleRate);

	//计算速率
	int byteRate = pcmSampleRate * pcmSampleBit * pcmChannels / 8;

	//设置速率
	lame_set_brate(lame, byteRate);

	lame_set_mode(lame, 1);

	//设置MP3质量
	lame_set_quality(lame, quality);

	lame_init_params(lame);

	return JNI_TRUE;
}

JNIEXPORT jbyteArray JNICALL Java_com_leleliu008_newton_base_audio_PCM2MP3_encode
  (JNIEnv * env, jobject obj, jshortArray buffer, jint length) {

	int nb_write = 0;

	char output[BUFFER_SIZE];

	// 转换为本地数组
	jshort *input = (*env)->GetShortArrayElements(env, buffer, NULL);

	// 压缩mp3
	nb_write = lame_encode_buffer(lame, input, input, length, output, BUFFER_SIZE);

	// 局部引用，创建一个byte数组
	jbyteArray result = (*env)->NewByteArray(env, nb_write);

	// 给byte数组设置值
	(*env)->SetByteArrayRegion(env, result, 0, nb_write, (jbyte *)output);

	// 释放本地数组(避免内存泄露)
	(*env)->ReleaseShortArrayElements(env, buffer, input, 0);

	return result;
}

JNIEXPORT jboolean JNICALL Java_com_leleliu008_newton_base_audio_PCM2MP3_destroy
  (JNIEnv * env, jobject obj) {
	lame_close(lame);
	return JNI_TRUE;
}
