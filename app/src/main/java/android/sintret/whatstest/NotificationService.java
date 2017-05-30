package android.sintret.whatstest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.support.v4.content.LocalBroadcastManager;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static android.R.id.list;
import static android.content.ContentValues.TAG;


public class NotificationService extends NotificationListenerService {

    private static final String LOG_TAG = "LOGTAG";
    Context context;

    @Override

    public void onCreate() {

        super.onCreate();
        context = getApplicationContext();

    }

    @Override

    public void onNotificationPosted(StatusBarNotification sbn) {


        String pack = sbn.getPackageName();
        String ticker = sbn.getNotification().tickerText.toString();
        Bundle extras = sbn.getNotification().extras;
        String title = extras.getString("android.title");
        String text = extras.getCharSequence("android.text").toString();

        String ticker2 = sbn.getNotification().toString();
        String yup = sbn.toString();

        if (sbn.getPackageName().equals("com.whatsapp")) {
            Log.i("Package", pack);
            Log.i("Ticker", ticker);
            Log.i("Title", title);
            Log.i("Text", text);
            Log.i("ticker2", ticker2);
            Log.i("yup", yup);

            Intent msgrcv = new Intent("Msg");
            msgrcv.putExtra("package", pack);
            msgrcv.putExtra("ticker", ticker);
            msgrcv.putExtra("title", title);
            msgrcv.putExtra("text", text);

            if (extras != null) {
                Set<String> keys = extras.keySet();
                Iterator<String> it = keys.iterator();
                while (it.hasNext()) {
                    String key = it.next();
                    Log.e(LOG_TAG, "[" + key + "=" + extras.get(key) + "]");

                    if (key.equals("android.textLines")) {
                        // List<CharSequence> list = Arrays.asList(extras.getChar("android.textLines"));
                        CharSequence[] cs = extras.getCharSequenceArray("android.textLines");

                        int panjang = cs.length - 1;
                        for (int i = 0; i < cs.length; i++) {
                            // Log.e(LOG_TAG, cs[i].toString());
                            Log.i("TEXTNYA", cs[i].toString());

                            if (i == panjang) {
                                String csString = cs[i].toString();
                                msgrcv.putExtra("content", csString);

                                String[] split = csString.split(":");
                                msgrcv.putExtra("contact", split[0]);
                               // StringBuilder sb = new StringBuilder();
                               /* for (int xx = 0; xx < split.length; xx++) {

                                }*/
                            }

                        }

                        Log.e(LOG_TAG, Arrays.toString(cs));
                    }

                   /* if(key.equals("android.rebuild.applicationInfo")){
                        ApplicationInfo appInfo = new ApplicationInfo();
                        appInfo.requiresSmallestWidthDp;
                    }*/
                }
                Log.e(LOG_TAG, "Dumping Intent end");

               /* for (String key : extras.keySet()) {
                    Object value = extras.get(key);
                    Log.d(TAG, String.format("%s %s (%s)", key,
                            value.toString(), value.getClass().getName()));
                }*/
            }

            //Bitmap btm = (Bitmap) sbn.getNotification().extras.get(Notification.EXTRA_PICTURE);


            LocalBroadcastManager.getInstance(context).sendBroadcast(msgrcv);
        }

    }

    @Override

    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("Msg", "Notification Removed");

    }
}