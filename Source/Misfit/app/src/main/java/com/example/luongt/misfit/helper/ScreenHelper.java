package com.example.luongt.misfit.helper;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Point;
import android.view.Display;

import java.util.Arrays;

/**
 * Created by luongt on 3/18/2016.
 */
public class ScreenHelper {
    private final static int DeviceSizeThreshold = 600;

    private static float _density;
    private static float _width;
    private static float _height;
    private static float _navigationHeight;
    private static float _statusHeight;
    private static boolean _isPortrait;
    private static boolean _isTablet;

    public static float getWidth() {
        return _width;
    }

    public static float getHeight() {
        return _height;
    }

    public static float getNavigationHeight() {
        return _navigationHeight;
    }

    public static float getStatusHeight() {
        return _statusHeight;
    }

    public static boolean isPortrait() {
        return _isPortrait;
    }

    public static boolean isTablet() {
        return _isTablet;
    }

    public static void initVariables(Activity activity) {
        _density = activity.getResources().getDisplayMetrics().density;

        int statusBarId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int navigationBarId = activity.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        _navigationHeight = navigationBarId > 0 ? activity.getResources().getDimensionPixelSize(navigationBarId) : 0;
        _statusHeight = statusBarId > 0 ? activity.getResources().getDimensionPixelSize(statusBarId) : 0;

        RefreshStaticVariable(activity);

        float minValue = _width < _height ? _width : _height;

        if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            minValue += _navigationHeight;
        }

        _isTablet = minValue >= toPixel(DeviceSizeThreshold);
    }

    public static void RefreshStaticVariable(Activity  activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        _width = size.x;
        _height = size.y;

        _isPortrait = _height > _width;
    }

    public static int toPixel(double dp) {
        return (int) (dp * _density + 0.5);
    }

    public static int[] addElement(int[] org, int added) {
        int[] result = Arrays.copyOf(org, org.length + 1);
        result[org.length] = added;
        return Arrays.copyOfRange(result, 1, result.length);
    }
}
