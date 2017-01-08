package me.wondertwo.caiwenji.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wondertwo on 2016/12/22.
 */

public class Constants {

    public static String[] sourceUrl = {
            "http://f3.market.mi-img.com/download/AppStore/06b27a50e2c094df50f3bda97e98f7b46f65815b5/com.tencent.mobileqq.apk",
            "http://f5.market.mi-img.com/download/AppStore/02f8a64e6538340261af3609ae51be6ed2f6db46a/com.tencent.mm.apk",
            "http://f2.market.xiaomi.com/download/AppStore/061e859e4adac9091f9465205b887a1c7a24345ab/com.cmcm.babyshow.apk",
            "http://f1.market.xiaomi.com/download/AppStore/0eed30564ea2941290ad8b273c5e6673509a1258a/com.tencent.news.apk",
            "http://f1.market.xiaomi.com/download/AppStore/09dbf5d3c603ac5c37ebdd727f8b0f10771407354/com.tencent.qqmusic.apk",
            "http://f2.market.mi-img.com/download/AppStore/07288492448f0bb32836bacfeafacd1715c4145b2/com.jianshu.haruki.apk",
            "http://f2.market.xiaomi.com/download/AppStore/0645f4c8fd7a67fdf2dcd216df90a61751041a8ce/com.sankuai.meituan.apk",
            "http://f5.market.mi-img.com/download/AppStore/0d1175f83ca8e51004d1730027f5f2f2097408925/com.sankuai.movie.apk",
            "http://f2.market.xiaomi.com/download/AppStore/01c755473d4c746fe330dbcfbe470205bb292c791/com.eg.android.AlipayGphone.apk",
            "http://f2.market.mi-img.com/download/AppStore/023094fd0609f822cf1cd36dd86f6b4de3f41b355/com.taobao.taobao.apk",
            "http://f4.market.xiaomi.com/download/AppStore/046dc414d7af1660f9860d83c77df38c10b40abf1/com.sohu.inputmethod.sogou.apk",
            "http://f4.market.xiaomi.com/download/AppStore/0c13a4de70105e7d7a438d5c6dfa26f6c5b409fa3/com.netease.cloudmusic.apk",
            "http://f2.market.mi-img.com/download/AppStore/0fc58437e06aac2e919ebc494d18b3d122540e32b/tv.danmaku.bili.apk",
            "http://f2.market.xiaomi.com/download/AppStore/0dfdf56ab5bd3b3de702fd49c9384f9ff09436a25/com.youku.phone.apk",
            "http://f3.market.mi-img.com/download/AppStore/00bba150d6cf749eb3dff1265aea1b34c79c75dce/com.sina.weibo.apk",
            "http://f2.market.xiaomi.com/download/AppStore/05c495b3411849b5f3a412dfdfa0a73d08d42dcc6/com.netease.newsreader.activity.apk",
            "http://f2.market.mi-img.com/download/AppStore/0f0ccf5a71d714a720ddb631023bbcfe7c696ac49/com.autonavi.minimap.apk",
            "http://f1.market.mi-img.com/download/AppStore/0393d414dfff591f66e2f8b1a49543947c74203f8/com.baidu.searchbox.apk",
            "http://f4.market.xiaomi.com/download/AppStore/0a8f054145336e86e261a7e929f627a2d594281e4/com.baidu.BaiduMap.apk",
            "http://f2.market.mi-img.com/download/AppStore/0b80495c509b24b52356ba96b0bcf14df74704dac/com.baidu.netdisk.apk",
            "http://f4.market.mi-img.com/download/AppStore/0ebe25b7eb5657547d66eb46ec75357b3d442e576/com.cleanmaster.security_cn.apk",
            "http://f1.market.mi-img.com/download/AppStore/0c2ea59b1d4a18d9c07483e080a135ba76f428d50/com.cleanmaster.mguard_cn.apk"
    };

    public static String[] fileName = {
            "移动企鹅.apk",
            "微信.apk",
            "宝宝时光.apk",
            "腾讯新闻.apk",
            "企鹅音乐.apk",
            "简书.apk",
            "美团.apk",
            "猫眼电影.apk",
            "支付宝.apk",
            "淘宝.apk",
            "搜狗输入法.apk",
            "网易云音乐.apk",
            "哔哩哔哩.apk",
            "优酷.apk",
            "微博.apk",
            "网易新闻.apk",
            "高德地图.apk",
            "手机百度.apk",
            "百度地图.apk",
            "百度网盘.apk",
            "猎豹大师.apk",
            "清理大师.apk"
    };

    private static List<SourceFile> downloadList = new ArrayList<>();

    public static synchronized List<SourceFile> getList() {
        return downloadList;
    }

}