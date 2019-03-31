package eeepay.androidmorefunctiondemo.bohao;

/**
 * @author :xqf
 * @date :2019/4/1  上午12:46
 * @desc :
 * @update :
 */
public class CallLogInfo {
    public String number;
    public long date;
    public int type;
    public String durtion;
    public CallLogInfo(String number, long date, int type,String durtion) {
        super();
        this.number = number;
        this.date = date;
        this.type = type;
        this.durtion = durtion;
    }
    public CallLogInfo() {
        super();
    }

}
