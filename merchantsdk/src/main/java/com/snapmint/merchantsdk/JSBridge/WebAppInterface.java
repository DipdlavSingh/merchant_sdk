package com.snapmint.merchantsdk.JSBridge;

import android.app.Activity;
import android.webkit.JavascriptInterface;

import com.snapmint.merchantsdk.constants.ApiConstant;

public class WebAppInterface {

    Activity mContext;

    /**
     * Instantiate the interface and set the context
     */
    public WebAppInterface(Activity c) {
        mContext = c;
    }

    /**
     * Show a toast from the web page
     */
    @JavascriptInterface
    public void handlePaymentResponse(String code, String message) {
        ApiConstant.snapmintPaymentresponse.handlePaymentResponse(code, message, mContext);
    }
}
