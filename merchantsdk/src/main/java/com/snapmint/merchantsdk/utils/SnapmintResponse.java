package com.snapmint.merchantsdk.utils;

import android.app.Activity;

public interface SnapmintResponse {

    public void handlePaymentResponse(String code, String message, Activity context);
}
