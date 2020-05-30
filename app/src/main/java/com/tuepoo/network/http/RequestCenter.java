package com.tuepoo.network.http;

import com.tuepoo.module.news.BaseNewsModel;
import com.tuepoo.module.recommand.BaseRecommandModel;
import com.tuepoo.module.update.UpdateModel;
import com.tuepoo.module.user.User;
import com.tuepoo.okhttp.CommonOkHttpClient;
import com.tuepoo.okhttp.listener.DisposeDataHandle;
import com.tuepoo.okhttp.listener.DisposeDataListener;
import com.tuepoo.okhttp.listener.DisposeDownloadListener;
import com.tuepoo.okhttp.request.CommonRequest;
import com.tuepoo.okhttp.request.RequestParams;


public class RequestCenter {

    //根据参数发送所有post请求
    public static void postRequest(String url, RequestParams params, DisposeDataListener listener, Class<?> clazz) {
        CommonOkHttpClient.get(CommonRequest.
                createGetRequest(url, params), new DisposeDataHandle(listener, clazz));
    }

    /**
     * 用户登陆请求
     *
     * @param listener
     * @param userName
     * @param passwd
     */
    public static void login(String userName, String passwd, DisposeDataListener listener) {

        RequestParams params = new RequestParams();
        params.put("mb", userName);
        params.put("pwd", passwd);
        RequestCenter.postRequest(HttpConstants.LOGIN, params, listener, User.class);
    }

    /**
     * 应用版本号请求
     *
     * @param listener
     */
    public static void checkVersion(DisposeDataListener listener) {
        RequestCenter.postRequest(HttpConstants.CHECK_UPDATE, null, listener, UpdateModel.class);
    }

    public static void requestRecommandData(DisposeDataListener listener) {
        RequestCenter.postRequest(HttpConstants.HOME_RECOMMAND, null, listener, BaseRecommandModel.class);
    }

    public static void downloadFile(String url, String path, DisposeDownloadListener listener) {
        CommonOkHttpClient.downloadFile(CommonRequest.createGetRequest(url, null),
                new DisposeDataHandle(listener, path));
    }

    /**
     * 请求新鲜事详情
     */
    public static void requestCourseDetail(String newId, DisposeDataListener listener) {
        RequestParams params = new RequestParams();
        params.put("newId", newId);
        RequestCenter.postRequest(HttpConstants.NEWS_DETAIL, params, listener, BaseNewsModel.class);
    }
}
