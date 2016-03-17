package com.example.luongt.misfithelper.control;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by luongt on 3/17/2016.
 */
public class DynamicView extends TextView {
    public DynamicView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicView(Context context) {
        super(context);
    }

    public void blink()
    {
        setBackgroundColor(Color.RED);

        new CustomAsyncTask().execute();
    }

    private class CustomAsyncTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            DynamicView.this.setBackgroundColor(Color.GREEN);
        }
    }
}
