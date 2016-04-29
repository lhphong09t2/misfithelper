package com.example.luongt.misfit.misfithelper;

import android.content.Context;
import android.os.Build;

import com.example.luongt.misfit.R;
import com.onballgroup.cominlan.client.ComInLanClient;
import com.onballgroup.cominlan.model.IServer;

/**
 * Created by luongt on 3/24/2016.
 */
public class ControlSlideHelper extends BaseMisfitHelper {

    private  IServer _iServer;
    private ComInLanClient _comInLanClient;
    private static boolean _isF5 = true;

    public IServer getServer() {
        return _iServer;
    }

    public void setServer(IServer iServer) {
        this._iServer = iServer;
    }

    public ComInLanClient getComInLanClient() {
        return _comInLanClient;
    }

    public boolean isF5() {
        return _isF5;
    }

    public void setF5(boolean isF5) {
        this._isF5 = isF5;
    }

    public ControlSlideHelper(Context context) {
        super(context);
        _comInLanClient = new ComInLanClient(null, Build.MODEL);
        _comInLanClient.start();
    }

    @Override
    public String getKey() {
        return "slide";
    }

    @Override
    public int getIconId() {
        return R.drawable.slide;
    }

    @Override
    Object createDefaultSetting() {
        return null;
    }

    @Override
    String getSettingJson(Object setting) {
        return null;
    }

    @Override
    Object parseJsonSetting(String settingJson) {
        return null;
    }

    @Override
    public String getName() {
        return "Slide";
    }

    @Override
    public void onSinglePress() {
        sendData("n");
    }

    @Override
    public String getSinglePressTitle() {
        return getContext().getString(R.string.next_slide);
    }

    @Override
    public void onDoublePress() {
        sendData("b");
    }

    @Override
    public String getDoublePressTitle() {
        return getContext().getString(R.string.back_slide);
    }

    @Override
    public void onTripplePress() {
        _isF5 = !_isF5;
        if(_isF5) {
            sendData("f");
        }
        else {
            sendData("e");
        }
    }

    @Override
    public String getTriplePressTitle() {
        return null;
    }

    public void sendData(String data){
        if(_comInLanClient != null && _iServer != null){
            _comInLanClient.sendData(_iServer,data);
        }
    }
}
