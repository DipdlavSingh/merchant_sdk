package com.snapmint.merchantsdk.components;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.snapmint.merchantsdk.JSBridge.CheckoutWebViewInterface;
import com.snapmint.merchantsdk.R;
import com.snapmint.merchantsdk.adapter.TermsAndConditionsAdapter;
import com.snapmint.merchantsdk.api.ApiBuilder;
import com.snapmint.merchantsdk.api.ApiServices;
import com.snapmint.merchantsdk.constants.SnapmintConstants;
import com.snapmint.merchantsdk.constants.SnapmintConfiguration;
import com.snapmint.merchantsdk.models.EmiModel;
import com.snapmint.merchantsdk.snapmintsdk.NewCheckoutWebViewActivity;
import com.snapmint.merchantsdk.utils.Utility;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SnapmintEmiInfoButton extends FrameLayout implements View.OnClickListener {

    private WebView emiWebView;
    private TextView tvPayment;
    private TextView tvCredit;
    private ImageView ivSnapmint;
    private ImageView ivSnapmintLogo;
    private ImageView ivSnapmintText;
    private ImageView ivReadMore;
    private Double amountPay;
    private View view;
    private TextView tvPaymentText2;
    private TextView tvPaymentText3;
    private TextView tvPaymentText4;
    private TextView tvDisableText1;
    private TextView tvCashbackUpTo;
    private TextView tvDisableText4;
    private TextView tvFlatOffer;
    private TextView tvDisableText6;
    private TextView tvDisableText8;
    private String orderValue;
    private String merchantLink;
    private boolean isEnable;
    private Double firstEmiAmount;
    private Double secondEmiAmount;
    private LinearLayout llEnableView;
    private LinearLayout llDisableView;
    private LinearLayout llOfferView;
    private Double amountPayDisabled;
    private List<String> termsList = new ArrayList<>();
    private EmiModel model = new EmiModel();

    public SnapmintEmiInfoButton(@NonNull Context context) {
        super(context);
        init(context);
    }

    public SnapmintEmiInfoButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SnapmintEmiInfoButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void showSnapmintEmiInfo(String orderValue, String merchantLink, boolean iEnable, String environment) {
        this.orderValue = orderValue;
        this.merchantLink = merchantLink;
        this.isEnable = iEnable;
        if (environment.equalsIgnoreCase(SnapmintConfiguration.QA)) {
            SnapmintConstants.BASE_URL = SnapmintConstants.QA;
            SnapmintConstants.CHECKOUT_BASE_URL = SnapmintConstants.QA_CHECKOUT_URL;
        } else if (environment.equalsIgnoreCase(SnapmintConfiguration.PRE)) {
            SnapmintConstants.BASE_URL = SnapmintConstants.PRE;
            SnapmintConstants.CHECKOUT_BASE_URL = SnapmintConstants.PRE_CHECKOUT_URL;
        } else if (environment.equalsIgnoreCase(SnapmintConfiguration.PROD)) {
            SnapmintConstants.BASE_URL = SnapmintConstants.PROD;
            SnapmintConstants.CHECKOUT_BASE_URL = SnapmintConstants.PROD_CHECKOUT_URL;
        }
        try {
            getEmiInfo();
        } catch (Exception e) {
        }

    }

    private void init(Context context) {
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view = LayoutInflater.from(context).inflate(R.layout.snapmint_info_layout, this, true);
        tvPayment = findViewById(R.id.tvPayment);
        tvPaymentText2 = findViewById(R.id.tvPaymentText2);
        tvPaymentText3 = findViewById(R.id.tvPaymentText3);
        tvPaymentText4 = findViewById(R.id.tvPaymentText4);
        tvDisableText1 = findViewById(R.id.tvDisableText1);
        tvCashbackUpTo = findViewById(R.id.tvCashbackUpTo);
        tvDisableText4 = findViewById(R.id.tvDisableText4);
        tvFlatOffer = findViewById(R.id.tvFlatOffer);
        tvDisableText6 = findViewById(R.id.tvDisableText6);
        tvDisableText8 = findViewById(R.id.tvDisableText8);
        tvCredit = findViewById(R.id.tvCredit);
        emiWebView = findViewById(R.id.emiWebView);
        TextView tvTnc = findViewById(R.id.tvTnc);
        ivSnapmint = findViewById(R.id.ivSnapmint);
        ivSnapmintLogo = findViewById(R.id.ivSnapmintLogo);
        ivSnapmintText = findViewById(R.id.ivSnapmintText);
        ivReadMore = findViewById(R.id.ivReadMore);
        llEnableView = findViewById(R.id.llEnableView);
        llDisableView = findViewById(R.id.llDisableView);
        llOfferView = findViewById(R.id.llOfferView);
        setOnClickListener(this);
        llEnableView.setOnClickListener(this);
        tvTnc.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.llEnableView) {
            openSnapmintDialog(!TextUtils.isEmpty(model.getAvailableOffer()) && !TextUtils.isEmpty(model.getOfferPercentage()), false);
        } else if (view.getId() == R.id.tvTnc) {
            openSnapmintDialog(!TextUtils.isEmpty(model.getAvailableOffer()) && !TextUtils.isEmpty(model.getOfferPercentage()), true);
        }
    }

    private String loadHtmlFromAsset(Context context,String fileName) {
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            return new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    @SuppressLint("SetJavaScriptEnabled,SimpleDateFormat")
    private void openSnapmintDialog(boolean isOffer, boolean isTAnC) {
        final Dialog dialog = new Dialog(view.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_snapmint_html_web_view);
//        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        WebView webView = dialog.findViewById(R.id.webView);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        String nextMonth = "";
        String secondMonth = "";
        int nextMonthDay = 0;
        int secondMonthDay = 0;
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM");
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");

        try {
            Calendar cal = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Calculate next month
            cal.add(Calendar.MONTH, day > 23 ? 2 : 1);
            nextMonth = monthFormat.format(cal.getTime());
            nextMonthDay = Integer.parseInt(dayFormat.format(cal.getTime()));

            // Calculate the month after next
            cal2.add(Calendar.MONTH, day > 23 ? 3 : 2);
            secondMonth = monthFormat.format(cal2.getTime());
            secondMonthDay = Integer.parseInt(dayFormat.format(cal2.getTime()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (webView != null) {
            // Set up WebView and load HTML content
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setUseWideViewPort(true);
            String htmlContent;
            if(TextUtils.isEmpty(model.getEmiPopUp())){
                htmlContent = loadHtmlFromAsset(dialog.getContext(), "snapmint_popup_content.html");
            }else{
                htmlContent = model.getEmiPopUp();
            }
            htmlContent = htmlContent.replace("{{down_payment_price}}", String.valueOf(amountPay.intValue()));
            htmlContent = htmlContent.replace("{{first_emi_date}}", String.valueOf(nextMonthDay));
            htmlContent = htmlContent.replace("{{first_emi_month}}", nextMonth);
            htmlContent = htmlContent.replace("{{last_emi_date}}", String.valueOf(secondMonthDay));
            htmlContent = htmlContent.replace("{{last_emi_month}}", secondMonth);
            htmlContent = htmlContent.replace("{{first_emi_price}}", String.valueOf(firstEmiAmount.intValue()));
            htmlContent = htmlContent.replace("{{last_emi_price}}", String.valueOf(secondEmiAmount.intValue()));
            htmlContent = htmlContent.replace("{{first_emi_suffix}}", Utility.getNumberSuffix(nextMonthDay));
            htmlContent = htmlContent.replace("{{last_emi_suffix}}", Utility.getNumberSuffix(secondMonthDay));
            htmlContent = htmlContent.replace("{{total_order_value}}", orderValue);

            webView.addJavascriptInterface(new  MyWebJavaInterFace(dialog.getContext(),dialog),"Android");
            webView.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    // This method is called when a new URL is about to be loaded.
                    // You can add your logic here if needed.
                    return super.shouldOverrideUrlLoading(view, request);
                }

            });

            webView.loadDataWithBaseURL(null, htmlContent, "text/html", "utf-8", null);
        }
        Window window = dialog.getWindow();
        if (window != null) {
            // Set the dialog width to match the screen width
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            window.setAttributes(layoutParams);
        }
        dialog.show();

    }
    public class MyWebJavaInterFace extends AppCompatActivity {
        private final Context mContext;
        private final Dialog mDialog;
        MyWebJavaInterFace(Context context,Dialog dialog){
            mContext = context;
            mDialog = dialog;
        }

        // Other code...

        // This method will be called from JavaScript
        @JavascriptInterface
        public void closePopup() {
            // Add logic to close the popup in your Android code
            runOnUiThread(() -> {
               if(mDialog!=null && mDialog.isShowing()){
                   mDialog.dismiss();
               }
            });
        }
    }
/*
    private void openSnapmintDialog(boolean isOffer, boolean isTAnC) {
        final Dialog dialog = new Dialog(view.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dilog_snapmint);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        TextView txtDownpayment = dialog.findViewById(R.id.txtDownpayment);
        TextView tvFirstEmi = dialog.findViewById(R.id.tvFirstEmi);
        TextView tvSecondEmi = dialog.findViewById(R.id.tvSecondEmi);
        TextView tvFirstMonth = dialog.findViewById(R.id.tvFirstMonth);
        TextView tvSecondMonth = dialog.findViewById(R.id.tvSecondMonth);
        TextView tvTAndC = dialog.findViewById(R.id.tvTAndC);
        TextView tvTAndCTitle = dialog.findViewById(R.id.tvTAndCTitle);
        TextView tvTAndCSubTitle = dialog.findViewById(R.id.tvTAndCSubTitle);
        TextView tvFlatPercentage = dialog.findViewById(R.id.tvFlatPercentage);
        TextView tvCashbackUpTo = dialog.findViewById(R.id.tvCashbackUpTo);
        RelativeLayout relCross = dialog.findViewById(R.id.relCross);
        RelativeLayout relOfferBack = dialog.findViewById(R.id.relOfferBack);
        RelativeLayout relNonOfferBackLayout = dialog.findViewById(R.id.relNonOfferBackLayout);
        RelativeLayout relOfferHeader = dialog.findViewById(R.id.relOfferHeader);
        RelativeLayout relTermsView = dialog.findViewById(R.id.relTermsView);
        ImageView ivTAndCLogo = dialog.findViewById(R.id.ivTAndCLogo);
        LinearLayout llBackToPlans = dialog.findViewById(R.id.llBackToPlans);
        LinearLayout llEmiPlansView = dialog.findViewById(R.id.llEmiPlansView);
        relOfferHeader.setVisibility(isOffer && !isTAnC ? VISIBLE : GONE);
        llEmiPlansView.setVisibility(!isTAnC ? VISIBLE : GONE);
        relTermsView.setVisibility(isOffer && isTAnC ? VISIBLE : GONE);
        relNonOfferBackLayout.setVisibility(!isOffer || isTAnC ? VISIBLE : GONE);
        llBackToPlans.setVisibility(isOffer && isTAnC ? VISIBLE : GONE);
        txtDownpayment.setText(Utility.setSingleDynamicValue(dialog.getContext(), R.string.rs_amount, String.valueOf(amountPay.intValue())));
        if (firstEmiAmount != null)
            tvFirstEmi.setText(Utility.setSingleDynamicValue(dialog.getContext(), R.string.rs_amount, String.valueOf(firstEmiAmount.intValue())));
        if (secondEmiAmount != null)
            tvSecondEmi.setText(Utility.setSingleDynamicValue(dialog.getContext(), R.string.rs_amount, String.valueOf(secondEmiAmount.intValue())));
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        String nextMonth = "";
        String secondMonth = "";
        String outputPattern = "MMM";
        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        cal.add(Calendar.MONTH, day > 23 ? 2 : 1);
        cal2.add(Calendar.MONTH, day > 23 ? 3 : 2);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        try {
            nextMonth = outputFormat.format(cal.getTime());
            secondMonth = outputFormat.format(cal2.getTime());
        } catch (Exception e) {
        }
        tvFirstMonth.setText(Html.fromHtml(Utility.setSingleDynamicValue(dialog.getContext(), R.string.third_month, nextMonth)));
        tvSecondMonth.setText(Html.fromHtml(Utility.setSingleDynamicValue(dialog.getContext(), R.string.third_month, secondMonth)));
        relCross.setOnClickListener(v -> dialog.dismiss());
        relOfferBack.setOnClickListener(v -> dialog.dismiss());
        if (isOffer) {
            tvFlatPercentage.setText(model.getOfferPercentage());
            tvTAndCTitle.setText(model.getTermsAndConditionsTitle());
            tvTAndCSubTitle.setText(model.getTermsAndConditionsSubtitle());
            tvCashbackUpTo.setText(model.getAvailableOffer().replace("T&C", ""));
            if (!TextUtils.isEmpty(model.getTermsAndConditionsSnapmintLogo())) {
                GlideToVectorYou.init().with(dialog.getContext()).load(Uri.parse(model.getTermsAndConditionsSnapmintLogo()), ivTAndCLogo);
            }
        }
        tvTAndC.setOnClickListener(v -> {
            relTermsView.setVisibility(VISIBLE);
            relNonOfferBackLayout.setVisibility(VISIBLE);
            llBackToPlans.setVisibility(VISIBLE);
            relOfferHeader.setVisibility(GONE);
            llEmiPlansView.setVisibility(GONE);
        });
        llBackToPlans.setOnClickListener(v -> {
            relTermsView.setVisibility(GONE);
            relNonOfferBackLayout.setVisibility(GONE);
            llBackToPlans.setVisibility(GONE);
            relOfferHeader.setVisibility(VISIBLE);
            llEmiPlansView.setVisibility(VISIBLE);
        });
        if (isOffer) {
            setTermsAndConditionList(dialog);
        }

    }
*/

    private void setTermsAndConditionList(Dialog dialog) {
        RecyclerView recycleTAndC = dialog.findViewById(R.id.recycleTAndC);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(dialog.getContext());
        recycleTAndC.setLayoutManager(linearLayoutManager);
        TermsAndConditionsAdapter adapter = new TermsAndConditionsAdapter(termsList);
        recycleTAndC.setAdapter(adapter);

    }


    private void getEmiInfo() {
        double totalOrder = Double.parseDouble(orderValue);
        ApiServices retrofitAPI = ApiBuilder.create(ApiServices.class);
        Call<EmiModel> call = retrofitAPI.getMerchantDetail(merchantLink);
        call.enqueue(new Callback<EmiModel>() {
            @Override
            public void onResponse(@NonNull Call<EmiModel> call, @NonNull Response<EmiModel> response) {
                try {
                    model = response.body();
                    if (model != null) {
                        if (!TextUtils.isEmpty(model.getPayNowPercentage())) {
                            amountPay = (totalOrder * Double.parseDouble(model.getPayNowPercentage()) / 100);
                            double input = Math.floor(amountPay);
                            double afterDecimal = amountPay - input;
                            if (afterDecimal > 0) {
                                amountPay = amountPay + 1;
                            }
                        }
                        if (!TextUtils.isEmpty(model.getPayNowPercentagePopUpDisable())) {
                            amountPayDisabled = (totalOrder * Double.parseDouble(model.getPayNowPercentagePopUpDisable()) / 100);
                            double input = Math.floor(amountPayDisabled);
                            double afterDecimal = amountPayDisabled - input;
                            if (afterDecimal > 0) {
                                amountPayDisabled = amountPayDisabled + 1;
                            }
                        }
                        firstEmiAmount = (totalOrder * Double.parseDouble(model.getEmiOnePercentage()) / 100);
                        double input = Math.floor(firstEmiAmount);
                        double afterDecimal = firstEmiAmount - input;
                        if (afterDecimal > 0) {
                            firstEmiAmount = firstEmiAmount + 1;
                        }
                        secondEmiAmount = (totalOrder * Double.parseDouble(model.getEmiSecondPercentage()) / 100);
                        double secInput = Math.floor(secondEmiAmount);
                        double secAfterDecimal = secondEmiAmount - secInput;
                        if (secAfterDecimal > 0) {
                            secondEmiAmount = secondEmiAmount + 1;
                        }
                        double emiDisabledAmount = (totalOrder * Double.parseDouble(model.getEmiRatesPercentagePopUpDisable()) / 100);
                        double emiDesInput = Math.floor(emiDisabledAmount);
                        double secDesAfterDecimal = emiDisabledAmount - emiDesInput;
                        if (secDesAfterDecimal > 0) {
                            emiDisabledAmount = emiDisabledAmount + 1;
                        }
                        setEmiWebView(model.getEmiWidget());
                        tvPayment.setText(model.getPayNowText1Part1());
                        tvPaymentText2.setText(model.getPayNowText1Part2().replace("pay_now", Utility.setSingleDynamicValue(view.getContext(), R.string.rs_amount, String.valueOf(amountPay.intValue()))));
                        tvPaymentText3.setText(model.getPayNowText1Part3());
                        tvPaymentText4.setText(model.getPayNowText1Part4());
                        tvDisableText1.setText(model.getPayNowText1PopUpDisable());
                        tvDisableText4.setText(model.getPayNowText2PopUpDisable().replace("pay_now", Utility.setSingleDynamicValue(view.getContext(), R.string.rs_amount, String.valueOf(amountPayDisabled.intValue()))));
                        tvDisableText6.setText(model.getPayNowText3PopUpDisable().replace("emi_rate", String.valueOf((int) emiDisabledAmount)));
                        tvDisableText8.setText(model.getPayNowText4PopUpDisable());
                        Glide.with(view.getContext()).load(model.getPayNowText2()).into(ivSnapmint);
                        Glide.with(view.getContext()).load(model.getPayNowImagePopUpDisable()).into(ivSnapmintLogo);
                        Glide.with(view.getContext()).load(model.getPayNowImage1PopUpDisable()).into(ivSnapmintText);
                        Glide.with(view.getContext()).load("https://assets.snapmint.com/assets/merchant/emitxt/green_dark_button.png").into(ivReadMore);
                        tvCredit.setText(model.getPayNowText3());
//                        llEnableView.setVisibility(isEnable ? View.VISIBLE : View.GONE);
//                        llDisableView.setVisibility(isEnable ? View.GONE : View.VISIBLE);
                        termsList = model.getOfferTermsAndConditions();
//                        llOfferView.setVisibility(!TextUtils.isEmpty(model.getOfferPercentage()) && !TextUtils.isEmpty(model.getOfferPercentage()) ? VISIBLE : GONE);
                        if (!TextUtils.isEmpty(model.getOfferPercentage()) && !TextUtils.isEmpty(model.getAvailableOffer())) {
                            tvFlatOffer.setText(model.getOfferPercentage());
                            ivReadMore.setVisibility(GONE);
                            tvCashbackUpTo.setText(model.getAvailableOffer().replace("T&C", ""));
                        }else{
                            ivReadMore.setVisibility(VISIBLE);
                        }

                    }

                } catch (Exception e) {
                    Log.e("TAG", "onFailure: "+e );
                                    }
            }

            @Override
            public void onFailure(@NonNull Call<EmiModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure: "+t );
            }
        });
    }

    @SuppressLint({"SetJavaScriptEnabled", "ClickableViewAccessibility"})
    private void setEmiWebView(String emiWidget) {
        WebSettings webSettings = emiWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        if(TextUtils.isEmpty(emiWidget)){
            emiWidget = "<style>.snap_emi_txt{text-align:center;justify-content:center;width:max-content;border-radius:10.9399px;position:relative;margin-bottom:5px;background:#fff;margin-bottom:10px;cursor:pointer}.snap-emi-inst{font-family:Inter,sans-serif;font-weight:700!important;font-size:18px;line-height:16px!important;color:#000!important;padding-top:4px!important;text-align:left;letter-spacing:normal}.snap-emi-inst b{font-weight:700!important}img.info-img{position:relative!important;top:-1px!important}.snap-emi-inst img,.snap-emi-inst span,.snap-emi-slogan img,.snap-emi-slogan span{display:inline-block!important;vertical-align:middle!important}.snap-emi-inst b,.snap-emi-slogan .snap_emi_slogan_text b{font-weight:700}.snap-emi-slogan{font-family:Inter,sans-serif;font-size:12.5px!important;line-height:16px!important;padding-bottom:6px!important;letter-spacing:normal;display:flex;justify-content:space-between;align-items:center;font-weight:400;color:#090909!important}.snap_widget_powered_text img{max-width:100px!important;width:70px!important}.snap_widget_powered_text{margin-left:0;margin-bottom:0;font-size:8px;color:#000;font-weight:500}.snap_emi_txt .snap_widget_powered_text img{margin-left:4px!important}.snap_buy_now_btn{width:62px!important;max-width:90px}.snap_padding_left{padding-left:3px}.snap_grey_dot{background:rgba(52,52,52,1);width:5px;height:5px;border-radius:50%}.snap_powered_text{font-size:8px;color:#878787}.snap_upi_widget_img{width:40px;max-width:90px;margin-bottom:-2px}.snap_emi_txt .snap_text_pink{color:#d90075}</style><div id='sm-widget-btn' class='snap_emi_txt snap_emi_txt_wrapper' onclick='startPop()'><div class='snap-emi-inst'>or 3 Monthly Payments of<span class='snap_text_pink'>₹{{down_payment_price}}</span></div><div class='snap-emi-slogan'><span><span class='snap_emi_slogan_text'><b>0%</b>EMI on</span><img src='https://preemi.snapmint.com/assets/whitelable/UPI-Logo-vector%201.svg' class='snap_upi_widget_img'></span><span><span class='snap_widget_powered_text'><span class='snap_grey_dot'></span><img src='https://assets.snapmint.com/assets/merchant/snapmint_logo_black_text.svg'></span></span><div><img src='https://assets.snapmint.com/assets/merchant/view_more_pink.svg' class='snap_buy_now_btn'></div></div></div>";
        }
        // Replace placeholder with amountPay value
        emiWidget = emiWidget.replace("{{down_payment_price}}", String.valueOf(amountPay.intValue()));

        // Set a WebViewClient to handle page loading events
        emiWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Log.e("WebView", "Error loading webpage: " + description);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("WebView", "Page finished loading: " + url);
            }
        });

        emiWebView.loadDataWithBaseURL(null, emiWidget, "text/html", "UTF-8", null);
        emiWebView.setOnTouchListener(new View.OnTouchListener() {
            private float startX;
            private float startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        float endX = event.getX();
                        float endY = event.getY();
                        float touchSlop = ViewConfiguration.get(v.getContext()).getScaledTouchSlop();
                        if (Math.abs(endX - startX) < touchSlop && Math.abs(endY - startY) < touchSlop) {
                            openSnapmintDialog(!TextUtils.isEmpty(model.getAvailableOffer()) && !TextUtils.isEmpty(model.getOfferPercentage()), false);
                            // Click detected, handle the click event here
                            // For example, you can load a URL or execute JavaScript
                            return true; // Consume the touch event
                        }
                        break;
                }
                return false;
            }
        });

        // Load HTML content into WebView

    }
}
