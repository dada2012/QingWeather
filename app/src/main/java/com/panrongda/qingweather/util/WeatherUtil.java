package com.panrongda.qingweather.util;

import com.panrongda.qingweather.R;

/**
 * Create by Dada on 2019/12/17 16:40.
 */
public class WeatherUtil {

    private WeatherUtil(){
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 根据 天气code 获取Res中的背景图
     * @param condCode
     * @return
     */
    public static int getBgRes(String condCode){
        switch (condCode.charAt(0)) {
            //case '1' :return R.mipmap.bg_sun;
            case '2' :return R.mipmap.bg_feng;
            case '3' :return R.mipmap.bg_yu;
            case '4' :return R.mipmap.bg_xue;
            case '5' :return R.mipmap.bg_wu;
            default: return R.mipmap.bg_sun1;
        }
    }

    /**
     * 根据 天气code 获取Res中的图片资源
     *
     * @param condCode 天气code
     * @return 图片资源
     */
    public static int getCondRes(String condCode){
        switch (condCode){
            //case "100": return R.mipmap.w100;
            case "101": return R.mipmap.w101;
            case "102": return R.mipmap.w102;
            case "103": return R.mipmap.w103;
            case "104": return R.mipmap.w104;
            case "200": return R.mipmap.w200;
            case "201": return R.mipmap.w201;
            case "202": return R.mipmap.w202;
            case "203": return R.mipmap.w203;
            case "204": return R.mipmap.w204;
            case "205": return R.mipmap.w205;
            case "206": return R.mipmap.w206;
            case "207": return R.mipmap.w207;
            case "208": return R.mipmap.w208;
            case "209": return R.mipmap.w209;
            case "210": return R.mipmap.w210;
            case "211": return R.mipmap.w211;
            case "212": return R.mipmap.w212;
            case "213": return R.mipmap.w213;
            case "300": return R.mipmap.w300;
            case "301": return R.mipmap.w301;
            case "302": return R.mipmap.w302;
            case "303": return R.mipmap.w303;
            case "304": return R.mipmap.w304;
            case "305": return R.mipmap.w305;
            case "306": return R.mipmap.w306;
            case "307": return R.mipmap.w307;
            case "309": return R.mipmap.w309;
            case "310": return R.mipmap.w310;
            case "311": return R.mipmap.w311;
            case "312": return R.mipmap.w312;
            case "313": return R.mipmap.w313;
            case "314": return R.mipmap.w314;
            case "315": return R.mipmap.w315;
            case "316": return R.mipmap.w316;
            case "317": return R.mipmap.w317;
            case "318": return R.mipmap.w318;
            case "399": return R.mipmap.w399;
            case "400": return R.mipmap.w400;
            case "401": return R.mipmap.w401;
            case "402": return R.mipmap.w402;
            case "403": return R.mipmap.w403;
            case "404": return R.mipmap.w404;
            case "405": return R.mipmap.w405;
            case "406": return R.mipmap.w406;
            case "407": return R.mipmap.w407;
            case "408": return R.mipmap.w408;
            case "409": return R.mipmap.w409;
            case "410": return R.mipmap.w410;
            case "499": return R.mipmap.w499;
            case "500": return R.mipmap.w500;
            case "501": return R.mipmap.w501;
            case "502": return R.mipmap.w502;
            case "503": return R.mipmap.w503;
            case "504": return R.mipmap.w504;
            case "507": return R.mipmap.w507;
            case "508": return R.mipmap.w508;
            case "509": return R.mipmap.w509;
            case "510": return R.mipmap.w510;
            case "511": return R.mipmap.w511;
            case "512": return R.mipmap.w512;
            case "513": return R.mipmap.w513;
            case "514": return R.mipmap.w514;
            case "515": return R.mipmap.w515;
            case "900": return R.mipmap.w900;
            case "901": return R.mipmap.w901;
            case "999": return R.mipmap.w999;
            default: return R.mipmap.w100;
        }
    }
}
