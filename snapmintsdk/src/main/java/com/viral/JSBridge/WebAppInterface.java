package com.viral.JSBridge;

import android.app.Activity;
import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.viral.constants.ApiConstant;
import com.viral.snapmintsdk.MainActivity;

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
