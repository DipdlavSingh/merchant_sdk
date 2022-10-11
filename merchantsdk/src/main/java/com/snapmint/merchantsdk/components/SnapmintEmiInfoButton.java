package com.snapmint.merchantsdk.components;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.snapmint.merchantsdk.R;
import com.snapmint.merchantsdk.api.ApiBuilder;
import com.snapmint.merchantsdk.api.ApiServices;
import com.snapmint.merchantsdk.models.EmiModel;
import com.snapmint.merchantsdk.utils.Utility;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SnapmintEmiInfoButton extends FrameLayout implements View.OnClickListener {

    private TextView tvPayment;
    private TextView tvCredit;
    private ImageView ivSnapmint;
    private ImageView ivSnapmintLogo;
    private ImageView ivSnapmintText;
    private ImageView ivReadMore;
    private Double amountPay;
    private View view;
    private TextView tvAmount;
    private TextView tvPaymentText2;
    private TextView tvPaymentText3;
    private TextView tvPaymentText4;
    private TextView tvDisableText1;
    private TextView tvDisableText2;
    private TextView tvDisableText3;
    private TextView tvDisableText4;
    private TextView tvDisableText5;
    private TextView tvDisableText6;
    private TextView tvDisableText8;
    private String orderValue;
    private String merchantLink;
    private boolean isEnable;
    private Double firstEmiAmount;
    private Double secondEmiAmount;
    private LinearLayout llEnableView;
    private LinearLayout llDisableView;
    private Double amountPayDisabled;

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

    public void showSnapmintEmiInfo(String orderValue, String merchantLink, boolean iEnable) {
        this.orderValue = orderValue;
        this.merchantLink = merchantLink;
        this.isEnable = iEnable;
        try {
            getEmiInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void init(Context context) {
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view = LayoutInflater.from(context).inflate(R.layout.snapmint_info_layout, this, true);
        tvPayment = findViewById(R.id.tvPayment);
//        tvAmount = findViewById(R.id.tvAmount);
        tvPaymentText2 = findViewById(R.id.tvPaymentText2);
        tvPaymentText3 = findViewById(R.id.tvPaymentText3);
        tvPaymentText4 = findViewById(R.id.tvPaymentText4);
        tvDisableText1 = findViewById(R.id.tvDisableText1);
//        tvDisableText2 = findViewById(R.id.tvDisableText2);
//        tvDisableText3 = findViewById(R.id.tvDisableText3);
        tvDisableText4 = findViewById(R.id.tvDisableText4);
//        tvDisableText5 = findViewById(R.id.tvDisableText5);
        tvDisableText6 = findViewById(R.id.tvDisableText6);
        tvDisableText8 = findViewById(R.id.tvDisableText8);
        tvCredit = findViewById(R.id.tvCredit);
        ivSnapmint = findViewById(R.id.ivSnapmint);
        ivSnapmintLogo = findViewById(R.id.ivSnapmintLogo);
        ivSnapmintText = findViewById(R.id.ivSnapmintText);
        ivReadMore = findViewById(R.id.ivReadMore);
        llEnableView = findViewById(R.id.llEnableView);
        llDisableView = findViewById(R.id.llDisableView);
//        getEmiInfo();
        setOnClickListener(this);
        llEnableView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.llEnableView) {
            openSnapmintDialog();
        }
    }

    private void openSnapmintDialog() {
        final Dialog dialog = new Dialog(view.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dilog_snapmint);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        TextView txtDownpayment = dialog.findViewById(R.id.txtDownpayment);
        TextView tvFirstEmi = dialog.findViewById(R.id.tvFirstEmi);
        TextView tvSecondEmi = dialog.findViewById(R.id.tvSecondEmi);
        TextView tvFirstMonth = dialog.findViewById(R.id.tvFirstMonth);
        TextView tvSecondMonth = dialog.findViewById(R.id.tvSecondMonth);
        RelativeLayout relCross = dialog.findViewById(R.id.relCross);
        txtDownpayment.setText(Utility.setSingleDynamicValue(dialog.getContext(), R.string.rs_amount, String.valueOf(amountPay.intValue())));
        if (firstEmiAmount != null)
            tvFirstEmi.setText(Utility.setSingleDynamicValue(dialog.getContext(), R.string.rs_amount, String.valueOf(firstEmiAmount.intValue())));
        if (secondEmiAmount != null)
            tvSecondEmi.setText(Utility.setSingleDynamicValue(dialog.getContext(), R.string.rs_amount, String.valueOf(secondEmiAmount.intValue())));
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        String nextMonth;
        String secondMonth;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MMM");
            nextMonth = String.valueOf(monthYearFormatter.format(YearMonth.now().getMonth().plus(1)));
            secondMonth = String.valueOf(monthYearFormatter.format(YearMonth.now().getMonth().plus(2)));
        } else {
            nextMonth = String.valueOf(calendar.get(Calendar.MONTH) + 2);
            secondMonth = String.valueOf(calendar.get(Calendar.MONTH) + 3);
        }
        tvFirstMonth.setText(Html.fromHtml(Utility.setSingleDynamicValue(dialog.getContext(), R.string.third_month, nextMonth)));
        tvSecondMonth.setText(Html.fromHtml(Utility.setSingleDynamicValue(dialog.getContext(), R.string.third_month, secondMonth)));
        relCross.setOnClickListener(v -> dialog.dismiss());
    }


    private void getEmiInfo() {
        double totalOrder = Double.parseDouble(orderValue);
        ApiServices retrofitAPI = ApiBuilder.create(ApiServices.class);
        Call<EmiModel> call = retrofitAPI.getMerchantDetail(merchantLink);
        call.enqueue(new Callback<EmiModel>() {
            @Override
            public void onResponse(Call<EmiModel> call, Response<EmiModel> response) {
                try {
                    EmiModel model = response.body();
                    if (model != null) {
                        if (!TextUtils.isEmpty(model.getPayNowPercentage())) {
                            amountPay = (totalOrder * Double.parseDouble(model.getPayNowPercentage()) / 100);
                            double input = Math.floor(amountPay);
                            double afterDecimal = amountPay - input;
                            if (afterDecimal > 0) {
                                amountPay = amountPay + 1;
                            }
                            Log.e("TAG", "onResponse: helo" + afterDecimal);
                        }
                        if (!TextUtils.isEmpty(model.getPayNowPercentagePopUpDisable())) {
                            amountPayDisabled = (totalOrder * Double.parseDouble(model.getPayNowPercentagePopUpDisable()) / 100);
                            double input = Math.floor(amountPayDisabled);
                            double afterDecimal = amountPayDisabled - input;
                            if (afterDecimal > 0) {
                                amountPayDisabled = amountPayDisabled + 1;
                            }
                            Log.e("TAG", "onResponse: helo" + afterDecimal);
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
                        Double emiDisabledAmount = (totalOrder * Double.parseDouble(model.getEmiRatesPercentagePopUpDisable()) / 100);
                        double emiDesInput = Math.floor(emiDisabledAmount);
                        double secDesAfterDecimal = emiDisabledAmount - emiDesInput;
                        if (secDesAfterDecimal > 0) {
                            emiDisabledAmount = emiDisabledAmount + 1;
                        }
                        tvPayment.setText(model.getPayNowText1Part1());
//                        tvAmount.setText(Utility.setSingleDynamicValue(view.getContext(), R.string.rs_amount, String.valueOf(amountPay.intValue())));
                        tvPaymentText2.setText(model.getPayNowText1Part2().replace("pay_now", Utility.setSingleDynamicValue(view.getContext(), R.string.rs_amount, String.valueOf(amountPay.intValue()))));
                        tvPaymentText3.setText(model.getPayNowText1Part3());
                        tvPaymentText4.setText(model.getPayNowText1Part4());
                        tvDisableText1.setText(model.getPayNowText1PopUpDisable());
                        tvDisableText4.setText(model.getPayNowText2PopUpDisable().replace("pay_now", Utility.setSingleDynamicValue(view.getContext(), R.string.rs_amount, String.valueOf(amountPayDisabled.intValue()))));
                        tvDisableText6.setText(model.getPayNowText3PopUpDisable().replace("emi_rate",String.valueOf(emiDisabledAmount.intValue())));
                        tvDisableText8.setText(model.getPayNowText4PopUpDisable());
                        Glide.with(view.getContext()).load(model.getPayNowText2()).into(ivSnapmint);
                        Glide.with(view.getContext()).load(model.getPayNowImagePopUpDisable()).into(ivSnapmintLogo);
                        Glide.with(view.getContext()).load(model.getPayNowImage1PopUpDisable()).into(ivSnapmintText);
                        Glide.with(view.getContext()).load("https://assets.snapmint.com/assets/merchant/emitxt/green_dark_button.png").into(ivReadMore);
                        tvCredit.setText(model.getPayNowText3());
                        llEnableView.setVisibility(isEnable ? View.VISIBLE : View.GONE);
                        llDisableView.setVisibility(isEnable ? View.GONE : View.VISIBLE);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<EmiModel> call, Throwable t) {

            }
        });
    }
}
