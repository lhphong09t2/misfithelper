package com.example.tuyetluong.systemintent;

import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText)findViewById(R.id.editText);
    }

    public void OnSendClick(View v){
//        Intent sendIntent = new Intent();
//        sendIntent.setAction(Intent.ACTION_MEDIA_BUTTON);
//        sendBroadcast(sendIntent);

//        Uri number = Uri.parse("tel:0935154166");
//        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
//        startActivity(callIntent);

//        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
//        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
//        startActivityForResult(pickContactIntent, 1);

        //TODO: cai ni mo radio len roi play/pause
//        Intent upIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
//        KeyEvent upEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE);
//        upIntent.putExtra(Intent.EXTRA_KEY_EVENT, upEvent);
//        sendBroadcast(upIntent);

//        editText.dispatchKeyEvent(new KeyEvent(0, 0, KeyEvent.ACTION_DOWN,
//                KeyEvent.KEYCODE_VOLUME_DOWN, 0));
//        editText.dispatchKeyEvent(new KeyEvent(0, 0, KeyEvent.ACTION_UP,
//                KeyEvent.KEYCODE_VOLUME_DOWN, 0));

//        Instrumentation inst = new Instrumentation();
//        inst.sendKeyDownUpSync(KeyEvent.KEYCODE_VOLUME_DOWN);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Instrumentation inst = new Instrumentation();
                    for ( int i = 0; i < 10; ++i ) {
                        inst.sendKeyDownUpSync(KeyEvent.KEYCODE_B);
                        Thread.sleep(2000);
                        inst.sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_LEFT);
                        Thread.sleep(2000);
                    }
                }
                catch(InterruptedException e){
                }
            }
        }).start();
    }

    public void OnButtonClick(View v){}

}
