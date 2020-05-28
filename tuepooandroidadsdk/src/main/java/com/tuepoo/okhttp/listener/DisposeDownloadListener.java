package com.tuepoo.okhttp.listener;

/**
 * @function 监听下载进度
 */
public interface DisposeDownloadListener extends DisposeDataListener {
	public void onProgress(int progrss);
}
