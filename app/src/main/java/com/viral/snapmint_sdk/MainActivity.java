package com.viral.snapmint_sdk;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.snapmint.merchantsdk.components.SnapmintEmiInfoButton;
import com.snapmint.merchantsdk.constants.SnapmintConstants;
import com.snapmint.merchantsdk.constants.SnapmintConfiguration;
import com.viral.snapmint_sdk.utils.Helper;
import com.viral.snapmint_sdk.utils.ResponseObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    Button btn_check_out;
    Button btn_invalid_data;
    EditText et_phone_no, storeIdEdt, orderIdEdt, orderValueEdt;
    EditText merchantIdEdt, merchantTokenEdt, merchantKeyEdt, merchantConfirmUrlEdt, merchantFailUrlEdt;
    EditText fullNameEdt, emailEdt, billingFullNameEdt, billingAddressLIne1Edt, billingCityEdt, billingZipEdt;
    EditText shippingFullNameEdt, shippingAddressLIne1Edt, shippingCityEdt, shippingZipEdt;
    EditText proSKUEdt, unitPriceEdt, quantityEdt;
    EditText etBaseUrl;
    ResponseObject responseObject;
    private EditText etOrderValue;
    private Button btnChangeOrderValue;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       try {
           if (requestCode == SnapmintConfiguration.SNAPMINT_PAYMENT) {
               if(data.getStringExtra(SnapmintConfiguration.STATUS).equalsIgnoreCase(SnapmintConfiguration.SUCCESS)){
                   Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show();
               }else{
                   Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
               }
               Log.e("TAG", "onActivityResult: "+data );
           }else{

           }
       }catch (Exception ignored){}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_check_out = findViewById(R.id.btn_check_out);
        btn_invalid_data = findViewById(R.id.btn_invalid_data);

        inItView();

        responseObject = new ResponseObject();

        btn_check_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CheckSum and All Parameter, context

                if (isDataValidate()) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("merchant_key",merchantKeyEdt.getText().toString().trim());
                        jsonObject.put("merchant_token",merchantTokenEdt.getText().toString().trim());
                        jsonObject.put("merchant_id",merchantIdEdt.getText().toString().trim());
                        jsonObject.put("merchant_confirmation_url",merchantConfirmUrlEdt.getText().toString().trim());
                        jsonObject.put("merchant_failure_url",merchantFailUrlEdt.getText().toString().trim());
                        jsonObject.put("mobile",et_phone_no.getText().toString().trim());
                        jsonObject.put("store_id",storeIdEdt.getText().toString().trim());
                        jsonObject.put("order_id",orderIdEdt.getText().toString().trim());
                        jsonObject.put("order_value",orderValueEdt.getText().toString().trim());
                        jsonObject.put("udf1", "1.91");
                        jsonObject.put("udf2", "7147");
                        jsonObject.put("full_name", fullNameEdt.getText().toString().trim());
                        jsonObject.put("email", emailEdt.getText().toString().trim());
                        jsonObject.put("billing_address_line1", billingAddressLIne1Edt.getText().toString().trim());
                        jsonObject.put("billing_zip", billingZipEdt.getText().toString().trim());
                        jsonObject.put("shipping_address_line1", shippingAddressLIne1Edt.getText().toString().trim());
                        jsonObject.put("shipping_zip", shippingZipEdt.getText().toString().trim());
                        jsonObject.put("deviceType", "android");
                        JSONObject product = new JSONObject();
                        product.put("sku",proSKUEdt.getText().toString().trim());
                        product.put("name","Bold Show Diamond Earrings");
                        product.put("quantity",quantityEdt.getText().toString().trim());
                        product.put("unit_price",unitPriceEdt.getText().toString().trim());
                        product.put("udf2","7147");
                        product.put("udf1","1.910 g");

                        JSONArray productJsonArray = new JSONArray();
                        productJsonArray.put(product);
                        jsonObject.put("products",productJsonArray);

                        Intent intent = new Intent(MainActivity.this, com.snapmint.merchantsdk.snapmintsdk.NewCheckoutWebViewActivity.class);
                        intent.putExtra("data", jsonObject.toString());
                        intent.putExtra("option_clicked", "check_out");
                        intent.putExtra("callback", responseObject);
                        intent.putExtra("base_url", etBaseUrl.getText().toString().trim());
                        intent.putExtra("suc_url", merchantConfirmUrlEdt.getText().toString().trim());
                        intent.putExtra("fail_url", merchantFailUrlEdt.getText().toString().trim());
                        startActivityForResult(intent,SnapmintConfiguration.SNAPMINT_PAYMENT);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btn_invalid_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.snapmint.merchantsdk.snapmintsdk.MainActivity.class);
                intent.putExtra("data", "utf8=?&co_source=sdk&authenticity_token=jnpr0Kea3obVQyC5N6OAVKA8rmdbfxCyGFADxGP8LT8hxltV5SCARcigKyZD6Ix/NRv8S8YiqGPwofAj3qrSrw==&source=&user_type=old_user&mobile=9987026671&merchant_id=1&store_id=1&order_id=1&order_value=20000&merchant_confirmation_url=uat.sodelsolutions.com/magento/magento/success&merchant_failure_url=uat.sodelsolutions.com/magento/magento/failed&shipping_fees=10000&udf1=test&udf2=test&udf3=test&full_name=GIRIDHAR+m+MAMIDIPALLY&email=rahul@snapmint.com&billing_first_name=GIRIDHAR&billing_middle_name=ravi&billing_last_name=MAMIDIPALLY&billing_full_name=GIRIDHAR+ravi+MAMIDIPALLY&billing_address_line1=GIRIDHAR+EVENT+ORGANRING&billing_address_line2=8-2-268/1/D/2+ROAD+NO+3&billing_city=Mumbai&billing_zip=400076&shipping_first_name=GIRIDHAR&shipping_middle_name=ravi&shipping_last_name=MAMIDIPALLY&shipping_full_name=GIRIDHAR+ravi+MAMIDIPALLY&shipping_address_line1=GIRIDHAR+EVENT+ORGANRING&shipping_address_line2=8-2-268/1/D/2+ROAD+NO+3&shipping_city=Mumbai&shipping_zip=400076&products[][sku]=abdx123&products[][name]=Product+2&products[][unit_price]=1000&products[][quantity]=5&products[][item_url]=https://google.com/test&products[][udf1]=udf1&products[][udf2]=udf2&products[][udf3]=udf3&products[][sku]=abdx123&products[][name]=Product+2&products[][unit_price]=1000&products[][quantity]=5&products[][item_url]=https://google.com/test&products[][udf1]=udf1&products[][udf2]=udf2&products[][udf3]=udf3&checksum_hash=119c97050ad1740d18afaa588bd1363b5f1e9de2b5d650d19200e313c20e3a0e72cb3aea3jsdbfjbnbdshacfbdhjsbcvb33de4db7baf5c85d794bf211b8726352");
                intent.putExtra("option_clicked", "check_out");
                intent.putExtra("callback", responseObject);
                intent.putExtra("base_url", etBaseUrl.getText().toString().trim());
                intent.putExtra("suc_url", merchantConfirmUrlEdt.getText().toString().trim());
                intent.putExtra("fail_url", merchantFailUrlEdt.getText().toString().trim());
                startActivity(intent);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void inItView() {
        SnapmintEmiInfoButton snapmintButton = findViewById(R.id.snapmintButton);
//        snapmintButton.showSnapmintEmiInfo("1571", "2435/snap_nnow.json", true, SnapmintConfiguration.PROD);
        snapmintButton.showSnapmintEmiInfo("791", "2435/snap_nnow.json", true, SnapmintConfiguration.QA);
        et_phone_no = findViewById(R.id.et_phone_no);
        merchantIdEdt = findViewById(R.id.et_merchant_id);
        merchantKeyEdt = findViewById(R.id.et_merchant_key);
        merchantTokenEdt = findViewById(R.id.et_merchant_token);
        storeIdEdt = findViewById(R.id.et_store_id);
        orderIdEdt = findViewById(R.id.et_order_id);
        orderValueEdt = findViewById(R.id.et_order_value);
        merchantConfirmUrlEdt = findViewById(R.id.et_merchant_confirmation_url);
        merchantFailUrlEdt = findViewById(R.id.et_merchant_failure_url);
        etBaseUrl = findViewById(R.id.etBaseUrl);

        // Billing ID
        fullNameEdt = findViewById(R.id.et_full_name);
        emailEdt = findViewById(R.id.et_email);
        billingFullNameEdt = findViewById(R.id.et_billing_full_name);
        billingAddressLIne1Edt = findViewById(R.id.et_billing_address_line1);
        billingCityEdt = findViewById(R.id.et_billing_city);
        billingZipEdt = findViewById(R.id.et_billing_zip);

        // shipping ID
        shippingFullNameEdt = findViewById(R.id.et_shipping_full_name);
        shippingAddressLIne1Edt = findViewById(R.id.et_shipping_address_line1);
        shippingCityEdt = findViewById(R.id.et_shipping_city);
        shippingZipEdt = findViewById(R.id.et_shipping_zip);

        // product
        proSKUEdt = findViewById(R.id.et_sku);
        unitPriceEdt = findViewById(R.id.et_unit_price);
        quantityEdt = findViewById(R.id.et_quantity);

        etOrderValue = findViewById(R.id.etOrderValue);
        btnChangeOrderValue = findViewById(R.id.btnChangeOrderValue);

        etOrderValue.setText("1571");
        btnChangeOrderValue.setOnClickListener(view -> {
            if (!TextUtils.isEmpty(etOrderValue.getText().toString())&& Integer.parseInt(etOrderValue.getText().toString()) >=200){
                snapmintButton.showSnapmintEmiInfo(etOrderValue.getText().toString(), "2435/snap_nnow.json", true, SnapmintConfiguration.PROD);
            }else{
                Toast.makeText(this, "Please Select value minimum 200 or more ", Toast.LENGTH_SHORT).show();
            }
        });

        /*merchantIdEdt.setText("");
        storeIdEdt.setText("1");
        orderIdEdt.setText("1");
        orderValueEdt.setText("20000");
        merchantConfirmUrlEdt.setText("http://www.vijaysales.com/success");
        merchantFailUrlEdt.setText("http://www.vijaysales.com/failed");

        fullNameEdt.setText("GIRIDHAR m MAMIDIPALLY");
        emailEdt.setText("rahul@snapmint.com");
        billingFullNameEdt.setText("GIRIDHAR m MAMIDIPALLY");
        billingAddressLIne1Edt.setText("GIRIDHAR EVENT ORGANRING");
        billingCityEdt.setText("Mumbai");
        billingZipEdt.setText("400076");

        shippingFullNameEdt.setText("GIRIDHAR m MAMIDIPALLY");
        shippingAddressLIne1Edt.setText("GIRIDHAR EVENT ORGANRING");
        shippingCityEdt.setText("Mumbai");
        shippingZipEdt.setText("400076");

        proSKUEdt.setText("abdx123");
        unitPriceEdt.setText("1000");
        quantityEdt.setText("5");*/

        et_phone_no.setText("8310669856");
        merchantIdEdt.setText("1456");
        merchantTokenEdt.setText("UOYY0R_n");
        merchantKeyEdt.setText("cQ_kvgB0");
        storeIdEdt.setText("1");
        orderIdEdt.setText("MELORRA-"+ new Random().nextInt());
        orderValueEdt.setText("7000");
        merchantConfirmUrlEdt.setText("http://www.vijaysales.com/success");
        merchantFailUrlEdt.setText("http://www.vijaysales.com/failed");
        etBaseUrl.setText("https://sandboxapi.snapmint.com/v1/public/s2s_online_checkout");/*prod*/
//        etBaseUrl.setText("https://qaapi.snapmint.com/v1/public/s2s_online_checkout");/*Qa*/
//        etBaseUrl.setText("https://qaapi.snapmint.com/v1/public/s2s_online_checkout");/*Qa*/

        fullNameEdt.setText("GIRIDHAR Crawley");
        emailEdt.setText("qwerty@gmail.com");
        billingFullNameEdt.setText("GIRIDHAR Crawley");
        billingAddressLIne1Edt.setText("GIRIDHAR EVENT ORGANRING");
        billingCityEdt.setText("Mumbai");
        billingZipEdt.setText("400076");

        shippingFullNameEdt.setText("GIRIDHAR Crawley");
        shippingAddressLIne1Edt.setText("GIRIDHAR EVENT ORGANRING");
        shippingCityEdt.setText("Mumbai");
        shippingZipEdt.setText("400076");

        proSKUEdt.setText("abdx123");
        unitPriceEdt.setText("1000");
        quantityEdt.setText("5");
    }

    private boolean isDataValidate() {
        if (TextUtils.isEmpty(et_phone_no.getText().toString().trim())
                || TextUtils.isEmpty(merchantIdEdt.getText().toString().trim())
                || TextUtils.isEmpty(etBaseUrl.getText().toString().trim())
                || TextUtils.isEmpty(merchantKeyEdt.getText().toString().trim())
                || TextUtils.isEmpty(merchantTokenEdt.getText().toString().trim())
                || TextUtils.isEmpty(storeIdEdt.getText().toString().trim())
                || TextUtils.isEmpty(orderIdEdt.getText().toString().trim())
                || TextUtils.isEmpty(orderValueEdt.getText().toString().trim())
                || TextUtils.isEmpty(merchantConfirmUrlEdt.getText().toString().trim())
                || TextUtils.isEmpty(merchantFailUrlEdt.getText().toString().trim())
                || TextUtils.isEmpty(fullNameEdt.getText().toString().trim())
                || TextUtils.isEmpty(emailEdt.getText().toString().trim())
                || TextUtils.isEmpty(billingFullNameEdt.getText().toString().trim())
                || TextUtils.isEmpty(billingAddressLIne1Edt.getText().toString().trim())
                || TextUtils.isEmpty(billingCityEdt.getText().toString().trim())
                || TextUtils.isEmpty(billingZipEdt.getText().toString().trim())
                || TextUtils.isEmpty(shippingFullNameEdt.getText().toString().trim())
                || TextUtils.isEmpty(shippingAddressLIne1Edt.getText().toString().trim())
                || TextUtils.isEmpty(shippingCityEdt.getText().toString().trim())
                || TextUtils.isEmpty(shippingZipEdt.getText().toString().trim())
                || TextUtils.isEmpty(proSKUEdt.getText().toString().trim())
                || TextUtils.isEmpty(unitPriceEdt.getText().toString().trim())
                || TextUtils.isEmpty(quantityEdt.getText().toString().trim())
                || Integer.valueOf(quantityEdt.getText().toString().trim()) <= 0
        ) {
            return false;
        }
        return true;
    }

    public void showToast(String message) {
        // Set the toast and duration
        Toast mToastToShow = Toast.makeText(this, message, Toast.LENGTH_LONG);
        mToastToShow.show();
    }

    private ProgressDialog progressBar;

    public void showProgress(Context context) {
        try {
            if (progressBar != null) {
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
                progressBar = null;
            }
            progressBar = new ProgressDialog(context);
            progressBar.setCancelable(true);
            progressBar.setMessage("Please wait ...");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            progressBar.setCancelable(false);
            progressBar.setCanceledOnTouchOutside(false);
            progressBar.show();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissProgress() {

        try {
            if (progressBar != null && progressBar.isShowing()) {
                progressBar.dismiss();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}