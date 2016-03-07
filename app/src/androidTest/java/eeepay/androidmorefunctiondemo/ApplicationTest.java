package eeepay.androidmorefunctiondemo;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.text.TextUtils;
import android.util.Log;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import eeepay.androidmorefunctiondemo.md5.Md5;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
      System.out.print("result=" + test(0.005f,10f,0f,0.003f));
    }
    /**
     *
     * @param n  刷卡费率
     * @param x  固定手续费
     * @param y  提现最低手续费
     * @param z  提现费率
     * @return
     */
    public float test(float n, float x, float y, float z) {
        float m1 = x / (1 - n - z + z * n);
        float m2 = (x + y) / (1 - n);

        float m3 = y / (z - z * n);// 条件

        if (m1 > m3) {
            return m1;
        } else {
            return m2;
        }

    }
    /**
     * "100.0128"; 变成：100.01
     *
     * @param money
     * @return
     */
    public static double string2BigDecimal(String money) {
        if (TextUtils.isEmpty(money)) {
            return 0.00;
        } else {
            BigDecimal resultMoney = new BigDecimal(money);
            resultMoney = resultMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
            return resultMoney.doubleValue();
        }

    }
    public void testSubmit() {
        Map<String, String> params = new HashMap<String, String>();

        params.put("account_no", "9558804000164347615");
        params.put("id_card_no", "430922198306115518");
        params.put("mobile", "13662236671");
        params.put("sn", "DB57556832306007");

        String hmacmd5 = getHMACMD5(params);
    }
    public void testSubmit2() {

        Map<String, String> params = new HashMap<String, String>();

        params.put("account_city", "深圳市");
        params.put("account_name", "李亮");
        params.put("account_no", "9558804000164347615");
        params.put("account_province", "广东省");
        params.put("add_type", "1");
        params.put("address", "深圳市南山区科技园北区朗山路同方信息港A栋10楼");
        params.put("answer", "87654321");
        params.put("appType", "6");
        params.put("bank_name", "中国工商银行");
        params.put("city", "深圳市");
        params.put("cnaps_no", "102584002338");
        params.put("id_card_no", "430922198306115518");
        params.put("mobile", "13662236671");
        params.put("name", "燧人氏");
        params.put("password", "yf123456");
        params.put("pos_type", "4");
        params.put("province", "广东省");
        params.put("querstion", "12345678");
        params.put("ratio_value", "1");
        params.put("sn", "DB57556832306007");

        String hmacmd5 = getHMACM("API","MERADD",params);
    }

    private String getHMACMD5(Map<String, String> params) {

        String prefix = "API";
        String account_no = params.get("account_no");
        String id_card_no = params.get("id_card_no");
        String mobile = params.get("mobile");
        String sn = params.get("sn");
        String suffix = "MERADD";

        StringBuffer sb = new StringBuffer(prefix);

        sb.append("account_no").append(account_no);
        sb.append("id_card_no").append(id_card_no);
        sb.append("mobile").append(mobile);
        sb.append("sn").append(sn);
        sb.append("suffix").append(suffix);
        // MD5两次计算
         String string2 = sb.toString();
         String string3 = Md5.encode(Md5.encode(sb.toString()));
        Log.e("tag",string2);
        Log.e("tag",string3);
        return string3;
    }



    /**
     *  实名认证的HMAC 校验，此方法请不要修改
     *  @param prefix 前缀 固定传API(例如:实名认证传: API)
     *  @param suffix 后缀 报文头大写(例如：实名认证传: REALNAMEAUTH)
     *  @param params 加密数据
     *  @return
     *  @author zengja
     *  @date 2015年12月23日 上午10:41:40
     */
    public String getHMACM(String prefix,String suffix,Map<String,String> params){
        //按字典的键进行升序排序
        params = sortMapByKey(params);
        StringBuffer sb = new StringBuffer();
        sb.append("prefix").append(prefix);
        Set set = params.keySet();
        for (Object key : set) {
            if(!"hmac".equalsIgnoreCase(key.toString())){
                sb.append(key).append(params.get(key));
            }
        }
        sb.append("suffix").append(suffix);
        Log.e("tag", "hmac前=" + sb.toString());
        Log.e("tag","hmac后=" + Md5.encode(Md5.encode(sb.toString())));
        //MD5两次计算
        return Md5.encode(Md5.encode(sb.toString()));
    }
    private Map sortMapByKey(Map map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map sortMap = new TreeMap(new Comparator<String>(){
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2); //升序排列
            }
        });
        sortMap.putAll(map);
        return sortMap;
    }


}