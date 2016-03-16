package com.example.luongt.misfithelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.misfit.misfitlinksdk.MFLSession;
import com.misfit.misfitlinksdk.publish.MFLCallBack;
import com.misfit.misfitlinksdk.publish.MFLCommand;
import com.misfit.misfitlinksdk.publish.MFLError;
import com.misfit.misfitlinksdk.publish.MFLGestureType;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "my_message";
    Button button ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //onButtonClickListenner();
        //Log.i(TAG, "onCreate");
    }

    public void onButtonClickListenner(){
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MFLSession.sharedInstance().enable("yourAppId", "yourAppSecret", new MFLCallBack() {
                            @Override
                            public void onResponse(final Map<String, Map<MFLGestureType, MFLCommand>> commandMapping, final List<MFLCommand> supportedCommands, final MFLError error) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (error != null) {
                                            Log.e(TAG, error.getLocalizedMessage());
                                            return;
                                        }
                                    }
                                });

                            }
                        });
                    }
                }
        );
    }
}
