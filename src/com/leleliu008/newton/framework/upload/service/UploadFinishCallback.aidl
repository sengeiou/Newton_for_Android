package com.leleliu008.newton.framework.upload.service;

import com.leleliu008.newton.framework.upload.UploadResult;
 
/**
 * 上传完成的回调
 */
interface UploadFinishCallback {
    
    /**
     * 上传完成
     */
    void onFinish(in UploadResult uploadResult);  
}
