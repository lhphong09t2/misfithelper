package com.example.luongt.misfit;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.luongt.misfit.helper.ScreenHelper;

/**
 * Created by luongt on 4/5/2016.
 */
public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenHelper.initVariables(this);

        new DelayStart().execute();
    }

    private class DelayStart extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            SplashScreen.this.startActivity(new Intent(SplashScreen.this, MainActivity.class));
        }
    }
}
