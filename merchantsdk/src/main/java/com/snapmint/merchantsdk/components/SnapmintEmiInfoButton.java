package com.snapmint.merchantsdk.components;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.snapmint.merchantsdk.R;
import com.snapmint.merchantsdk.adapter.TermsAndConditionsAdapter;
import com.snapmint.merchantsdk.api.ApiBuilder;
import com.snapmint.merchantsdk.api.ApiServices;
import com.snapmint.merchantsdk.constants.AppConstants;
import com.snapmint.merchantsdk.constants.SnapmintConfiguration;
import com.snapmint.merchantsdk.models.EmiModel;
import com.snapmint.merchantsdk.utils.Utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
            AppConstants.BASE_URL = AppConstants.QA;
        } else if (environment.equalsIgnoreCase(SnapmintConfiguration.PRE)) {
            AppConstants.BASE_URL = AppConstants.PRE;
        } else if (environment.equalsIgnoreCase(SnapmintConfiguration.PROD)) {
            AppConstants.BASE_URL = AppConstants.PROD;
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
                        llEnableView.setVisibility(isEnable ? View.VISIBLE : View.GONE);
                        llDisableView.setVisibility(isEnable ? View.GONE : View.VISIBLE);
                        termsList = model.getOfferTermsAndConditions();
                        llOfferView.setVisibility(!TextUtils.isEmpty(model.getOfferPercentage()) && !TextUtils.isEmpty(model.getOfferPercentage()) ? VISIBLE : GONE);
                        if (!TextUtils.isEmpty(model.getOfferPercentage()) && !TextUtils.isEmpty(model.getAvailableOffer())) {
                            tvFlatOffer.setText(model.getOfferPercentage());
                            ivReadMore.setVisibility(GONE);
                            tvCashbackUpTo.setText(model.getAvailableOffer().replace("T&C", ""));
                        }else{
                            ivReadMore.setVisibility(VISIBLE);
                        }

                    }

                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(@NonNull Call<EmiModel> call, @NonNull Throwable t) {
            }
        });
    }
}
