package com.snapmint.merchantsdk.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import java.text.DecimalFormat;

public class Utility {
    @SuppressLint("StringFormatInvalid")

    public static String setFourDynamicValue(Context context, int textId, String value1, String value2, String value3, String value4,String value5) {
        return String.format(context.getResources().getString(textId), value1, value2, value3, value4,value5);
    }

    public static String setSingleDynamicValue(Context context, int textId, String value1) {
        return String.format(context.getResources().getString(textId), currencyFormat(value1));
    }

    public static String currencyFormat(String amount) {
        try {
            double value = Double.parseDouble(amount);
            DecimalFormat format = new DecimalFormat("0.#");
            DecimalFormat formatter = new DecimalFormat("#,###");
            return formatter.format(Double.parseDouble(format.format(value).replaceAll("[^\\d]", "")));
        } catch (Exception e) {
            return amount;
        }
    }

}
