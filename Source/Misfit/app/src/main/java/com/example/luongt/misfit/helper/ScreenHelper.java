package com.example.luongt.misfit.helper;

import android.content.Context;

import java.util.Arrays;

/**
 * Created by luongt on 3/18/2016.
 */
public class ScreenHelper {

    private static float _density;
    public static void InitVariables(Context context)
    {
        _density = context.getResources().getDisplayMetrics().density;
    }

    public static int toPixel(double dp)
    {
        return (int)(dp * _density + 0.5);
    }

    public static int[] addElement(int[] org, int added) {
        int[] result = Arrays.copyOf(org, org.length + 1);
        result[org.length] = added;
        return Arrays.copyOfRange(result, 1, result.length);
    }
}
