package eeepay.androidmorefunctiondemo.intentapp;

/**
 * Created by xuqingfeng on 2017/2/22.
 */

public interface IRouterUri {
    @RouterUri(routerUri = "xl://goods:8888/goodsDetail")//请求Url地址
    void jumpToGoodsDetail(@RouterParam("type") String goodsId, @RouterParam("buffer") String des);//参数商品Id 商品描述

}
