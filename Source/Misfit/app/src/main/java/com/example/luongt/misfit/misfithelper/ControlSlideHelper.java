package com.example.luongt.misfit.misfithelper;

/**
 * Created by luongt on 3/24/2016.
 */
public class ControlSlideHelper implements MisfitHelper {
    @Override
    public String getName() {
        return "Control slide";
    }

    @Override
    public void onSinglePress() {

    }

    @Override
    public String getSinglePressTitle() {
        return "Go to next slide";
    }

    @Override
    public void onDoublePress() {

    }

    @Override
    public String getDoublePressTitle() {
        return  "Back to previous slide";
    }

    @Override
    public void onTripplePress() {

    }

    @Override
    public String getTriplePressTitle() {
        return null;
    }
}
