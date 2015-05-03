package com.leleliu008.newton.framework.ui.dialog;

/**
 * 上传时判定网络的提示
 * @author wangzhichao@datatang.com 2015.1.6
 *
 */
public interface JudgeDialogOnclick {
	/**
	 * 
	 * @param isUpload true 上传  false取消
	 * @return 
	 */
	void dialogOnclick(boolean isUpload);
}
