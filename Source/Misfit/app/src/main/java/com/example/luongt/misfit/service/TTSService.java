package com.example.luongt.misfit.service;

import android.annotation.TargetApi;
import android.app.Service;
import android.os.Build;
import android.speech.tts.TextToSpeech;

import com.example.luongt.misfit.MisfitEventNotifierApplication;

import java.util.Locale;

/**
 * Created by luongt on 3/28/2016.
 */
public abstract class TTSService extends Service {
    private TextToSpeech _textToSpeech;

    @Override
    public void onCreate() {
        super.onCreate();
        _textToSpeech = new TextToSpeech(MisfitEventNotifierApplication.getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    _textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });
    }

    protected void speak(String text){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ttsGreater21(text);
        } else {
            ttsUnder20(text);
        }
    }

    @SuppressWarnings("deprecation")
    private void ttsUnder20(String text) {
        _textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ttsGreater21(String text) {
        _textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

}
