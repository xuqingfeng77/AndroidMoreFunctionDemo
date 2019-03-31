package eeepay.androidmorefunctiondemo.bohao;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :xqf
 * @date :2019/4/1  上午12:46
 * @desc :
 * @update :
 */
public class ContactsMsgUtils {
    public List<CallLogInfo> getCallLog(Context context) {
        List<CallLogInfo> infos = new ArrayList<CallLogInfo>();
        ContentResolver cr = context.getContentResolver();
        Uri uri = CallLog.Calls.CONTENT_URI;
        String[] projection = new String[] { CallLog.Calls.NUMBER, CallLog.Calls.DATE,
                CallLog.Calls.TYPE,CallLog.Calls.CACHED_NAME,CallLog.Calls.DURATION };
        Cursor cursor = cr.query(uri, projection, null, null, null);
        while (cursor.moveToNext()) {
            String number = cursor.getString(0);
            long date = cursor.getLong(1);
            int type = cursor.getInt(2);
            String durtion = cursor.getString(4);
            infos.add(new CallLogInfo(number, date, type,durtion));
        }
        cursor.close();
        return infos;
    }
}
