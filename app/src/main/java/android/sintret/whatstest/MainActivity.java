package android.sintret.whatstest;

import android.content.ComponentName;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.PhoneNumberUtils;
import android.text.Html;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.mega4tech.whatsappapilibrary.WhatsappApi;
import com.mega4tech.whatsappapilibrary.exception.WhatsappNotInstalledException;
import com.mega4tech.whatsappapilibrary.liseteners.GetContactsListener;
import com.mega4tech.whatsappapilibrary.model.*;
import com.mega4tech.whatsappapilibrary.model.WContact;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TableLayout tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tab = (TableLayout) findViewById(R.id.tab);
        LocalBroadcastManager.getInstance(this).registerReceiver(onNotice, new IntentFilter("Msg"));

/*        try {
            WhatsappApi.getInstance().getContacts(getApplicationContext(), new GetContactsListener() {
                @Override
                public void receiveWhatsappContacts(List<WContact> list) {
                    for (int i = 0; i < list.size(); i++) {

                        Log.d("listcontact",list.get(i).getName());
                    }
                    // Log.d("listcontact",list.getName);

                }
            });
        } catch (WhatsappNotInstalledException e) {
            e.printStackTrace();
        }*/






    }


    private BroadcastReceiver onNotice = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String pack = intent.getStringExtra("package");
            String title = intent.getStringExtra("title");
            String text = intent.getStringExtra("text");
            String contact = intent.getStringExtra("contact");


            String content = intent.getStringExtra("content");


            TableRow tr = new TableRow(getApplicationContext());
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            TextView textview = new TextView(getApplicationContext());
            textview.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
            textview.setTextSize(20);
            textview.setTextColor(Color.parseColor("#0B0719"));
            textview.setText(Html.fromHtml(pack + "<br><b>" + title + " : </b>" + text + "<b>" + content + "</b><p>"+contact));
            tr.addView(textview);
            tab.addView(tr);


            PackageManager pm = getPackageManager();
            try {

                Intent waIntent = new Intent(Intent.ACTION_SEND);
                waIntent.setType("text/plain");
                String myText = "..ff,,";

                PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                //Check if package exists or not. If not then code
                //in catch block will be called
                waIntent.setPackage("com.whatsapp");

                waIntent.putExtra(Intent.EXTRA_TEXT, myText);

                //https://stackoverflow.com/questions/36344892/how-can-i-send-message-to-specific-contact-through-whatsapp-from-my-android-app

                waIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.Conversation"));
                //waIntent.putExtra("jid", PhoneNumberUtils.stripSeparators("6285880794533")+"@s.whatsapp.net");
                waIntent.putExtra("title", "@ bot");
                waIntent.putExtra("subject", "bot");
                //waIntent.putExtra("jid", "@ bot@s.whatsapp.net");
                //startActivity(Intent.createChooser(waIntent, "Share with"));
                //startActivity(Intent.createChooser(waIntent, contact));
                startActivity(waIntent);


            } catch (PackageManager.NameNotFoundException e) {
               Log.d("error", e.toString());
            }




/*
           Uri mUri = Uri.parse("smsto:6285880794533"  );
            Intent mIntent = new Intent(Intent.ACTION_SENDTO, mUri);
            mIntent.setPackage("com.whatsapp");
            mIntent.putExtra("sms_body", "The text goes here");
            mIntent.putExtra("chat", true);
            mIntent.putExtra(Intent.EXTRA_TEXT, "The text goes here");
            startActivity(Intent.createChooser(mIntent, ""));*/


        }
    };
}