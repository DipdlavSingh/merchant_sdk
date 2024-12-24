package com.snapmint.merchantsdk.constants;


import com.snapmint.merchantsdk.utils.SnapmintResponse;

public class ApiConstant {

    //public static final String BASE_URL = "https://qa2.snapmint.com/api/loan_applications";
    //https://qaapi.snapmint.com/v1/public/online_checkout
    //https://preapi.snapmint.com/v1/public/online_checkout
    //https://api.snapmint.com/v1/public/online_checkout
    /** PLEASE UNCOMMENT THIS FOR QA ENVIRONMENT*/
//    public static final String LOG_BASE_URL = "https://checkout.qa.snapmint.com";
    public static final String LOG_BASE_URL = "https://checkout.snapmint.com";
    public static final String BASE_URL = "https://qaapi.snapmint.com/v1/public/online_checkout";
    public static final String DATA = "data";
    public static final String MERCHANT_CALLBACK = "callback";

    public static SnapmintResponse snapmintPaymentresponse = null;
}
