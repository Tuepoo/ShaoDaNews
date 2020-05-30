package com.tuepoo.network.http;

/**
 * @function: 所有请求相关地址
 */
public class HttpConstants {

    private static final String ROOT_URL = "http://ShaoDaNews.com/api";



    /**
     * 本地更新时间措请求
     */
    public static String LATESAT_UPDATE = ROOT_URL + "/fund/upsearch.php";

    /**
     * 登陆接口
     */
    public static String LOGIN = ROOT_URL + "/user/login_phone.php";

    /**
     * 检查更新接口
     */
    public static String CHECK_UPDATE = ROOT_URL + "/config/check_update.php";

    /**
     * 首页新鲜事请求接口
     */
    public static String HOME_RECOMMAND = ROOT_URL + "/news/home_recommand.php";

    /**
     * 新鲜事详情接口
     */
    public static String NEWS_DETAIL = ROOT_URL + "/product/news_detail.php";

    /**
     * 请求新鲜事评论列表
     */
    public static String NEWS_COMMENT= ROOT_URL + "/fund/news_commment.php";

}


