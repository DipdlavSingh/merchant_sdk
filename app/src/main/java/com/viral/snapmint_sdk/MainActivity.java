package com.viral.snapmint_sdk;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.snapmint.merchantsdk.components.SnapmintEmiInfoButton;
import com.snapmint.merchantsdk.constants.AppConstants;
import com.snapmint.merchantsdk.constants.SnapmintConfiguration;
import com.viral.snapmint_sdk.utils.Helper;
import com.viral.snapmint_sdk.utils.ResponseObject;


public class MainActivity extends AppCompatActivity {

    Button btn_check_out;
    Button btn_invalid_data;
    EditText et_phone_no, storeIdEdt, orderIdEdt, orderValueEdt;
    EditText merchantIdEdt, merchantTokenEdt, merchantKeyEdt, merchantConfirmUrlEdt, merchantFailUrlEdt;
    EditText fullNameEdt, emailEdt, billingFullNameEdt, billingAddressLIne1Edt, billingCityEdt, billingZipEdt;
    EditText shippingFullNameEdt, shippingAddressLIne1Edt, shippingCityEdt, shippingZipEdt;
    EditText proSKUEdt, unitPriceEdt, quantityEdt;
    ResponseObject responseObject;

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

                    final String phoneNumber = et_phone_no.getText().toString();
                    String data1 = "utf8=?&co_source=sdk&authenticity_token=jnpr0Kea3obVQyC5N6OAVKA8rmdbfxCyGFADxGP8LT8hxltV5SCARcigKyZD6Ix/NRv8S8YiqGPwofAj3qrSrw==&source=&user_type=old_user&mobile=";
                    String data2 = "&merchant_id=1&store_id=1&order_id=1&order_value=2000&merchant_confirmation_url=uat.sodelsolutions.com/magento/magento/success&merchant_failure_url=uat.sodelsolutions.com/magento/magento/failed&shipping_fees=10000&udf1=test&udf2=test&udf3=test&full_name=GIRIDHAR+m+MAMIDIPALLY&email=rahul@snapmint.com&billing_first_name=GIRIDHAR&billing_middle_name=ravi&billing_last_name=MAMIDIPALLY&billing_full_name=GIRIDHAR+ravi+MAMIDIPALLY&billing_address_line1=GIRIDHAR+EVENT+ORGANRING&billing_address_line2=8-2-268/1/D/2+ROAD+NO+3&billing_city=Mumbai&billing_zip=400076&shipping_first_name=GIRIDHAR&shipping_middle_name=ravi&shipping_last_name=MAMIDIPALLY&shipping_full_name=GIRIDHAR+ravi+MAMIDIPALLY&shipping_address_line1=GIRIDHAR+EVENT+ORGANRING&shipping_address_line2=8-2-268/1/D/2+ROAD+NO+3&shipping_city=Mumbai&shipping_zip=400076&products[][sku]=abdx123&products[][name]=Product+2&products[][unit_price]=1000&products[][quantity]=5&products[][item_url]=https://google.com/test&products[][udf1]=udf1&products[][udf2]=udf2&products[][udf3]=udf3&products[][sku]=abdx123&products[][name]=Product+2&products[][unit_price]=1000&products[][quantity]=5&products[][item_url]=https://google.com/test&products[][udf1]=udf1&products[][udf2]=udf2&products[][udf3]=udf3&checksum_hash=119c97050ad1740d18afaa588bd1363b5f1e9de2b5d650d19e8bce15f1a2d71be996a3e200e313c20e3a0e72cb3aea3b33de4db7baf5c85d794bf211b8726352";

                    data2 = "&merchant_id=" + merchantIdEdt.getText().toString().trim();
                    data2 = data2 + "&store_id=" + storeIdEdt.getText().toString().trim();
                    data2 = data2 + "&order_id=" + orderIdEdt.getText().toString().trim();
                    data2 = data2 + "&order_value=" + orderValueEdt.getText().toString().trim();
                    data2 = data2 + "&merchant_confirmation_url=" + merchantConfirmUrlEdt.getText().toString().trim();
                    data2 = data2 + "&merchant_failure_url=" + merchantFailUrlEdt.getText().toString().trim();

                    data2 = data2 + "&full_name=" + fullNameEdt.getText().toString().trim();
                    data2 = data2 + "&email=" + emailEdt.getText().toString().trim();

                    data2 = data2 + "&billing_full_name=" + billingFullNameEdt.getText().toString().trim();
                    data2 = data2 + "&billing_address_line1=" + billingAddressLIne1Edt.getText().toString().trim();
                    data2 = data2 + "&billing_city=" + billingCityEdt.getText().toString().trim();
                    data2 = data2 + "&billing_zip=" + billingZipEdt.getText().toString().trim();

                    data2 = data2 + "&shipping_full_name=" + shippingFullNameEdt.getText().toString().trim();
                    data2 = data2 + "&shipping_address_line1=" + shippingAddressLIne1Edt.getText().toString().trim();
                    data2 = data2 + "&shipping_city=" + shippingCityEdt.getText().toString().trim();
                    data2 = data2 + "&shipping_zip=" + shippingZipEdt.getText().toString().trim();

                    data2 = data2 + "&products[][sku]=" + proSKUEdt.getText().toString().trim();
                    data2 = data2 + "&products[][unit_price]=" + unitPriceEdt.getText().toString().trim();
                    data2 = data2 + "&products[][quantity]=" + quantityEdt.getText().toString().trim();

                    try {
                        //checksum format merchant_key|order_id|order_value|full_name|email|token
                        String checkSumStr = Helper.generateCheckSum(merchantKeyEdt.getText().toString().trim() + "|" +
                                orderIdEdt.getText().toString().trim() + "|" +
                                orderValueEdt.getText().toString().trim() + "|" +
                                fullNameEdt.getText().toString().trim() + "|" +
                                emailEdt.getText().toString().trim() + "|" +
                                merchantTokenEdt.getText().toString().trim());

                        //data2 = data2+"&checksum_hash="+"119c97050ad1740d18afaa588bd1363b5f1e9de2b5d650d19e8bce15f1a2d71be996a3e200e313c20e3a0e72cb3aea3b33de4db7baf5c85d794bf211b8726352";
                        data2 = data2 + "&checksum_hash=" + checkSumStr;

                        String finalData = data1 + phoneNumber + data2;
                        Intent intent = new Intent(MainActivity.this, com.snapmint.merchantsdk.snapmintsdk.MainActivity.class);
                        //intent.putExtra("data", "utf8=?&co_source=sdk&authenticity_token=jnpr0Kea3obVQyC5N6OAVKA8rmdbfxCyGFADxGP8LT8hxltV5SCARcigKyZD6Ix/NRv8S8YiqGPwofAj3qrSrw==&source=&user_type=old_user&mobile=6547989250&merchant_id=1&store_id=1&order_id=1&order_value=20000&merchant_confirmation_url=uat.sodelsolutions.com/magento/magento/success&merchant_failure_url=uat.sodelsolutions.com/magento/magento/failed&shipping_fees=10000&udf1=test&udf2=test&udf3=test&full_name=GIRIDHAR+m+MAMIDIPALLY&email=rahul@snapmint.com&billing_first_name=GIRIDHAR&billing_middle_name=ravi&billing_last_name=MAMIDIPALLY&billing_full_name=GIRIDHAR+ravi+MAMIDIPALLY&billing_address_line1=GIRIDHAR+EVENT+ORGANRING&billing_address_line2=8-2-268/1/D/2+ROAD+NO+3&billing_city=Mumbai&billing_zip=400076&shipping_first_name=GIRIDHAR&shipping_middle_name=ravi&shipping_last_name=MAMIDIPALLY&shipping_full_name=GIRIDHAR+ravi+MAMIDIPALLY&shipping_address_line1=GIRIDHAR+EVENT+ORGANRING&shipping_address_line2=8-2-268/1/D/2+ROAD+NO+3&shipping_city=Mumbai&shipping_zip=400076&products[][sku]=abdx123&products[][name]=Product+2&products[][unit_price]=1000&products[][quantity]=5&products[][item_url]=https://google.com/test&products[][udf1]=udf1&products[][udf2]=udf2&products[][udf3]=udf3&products[][sku]=abdx123&products[][name]=Product+2&products[][unit_price]=1000&products[][quantity]=5&products[][item_url]=https://google.com/test&products[][udf1]=udf1&products[][udf2]=udf2&products[][udf3]=udf3&checksum_hash=119c97050ad1740d18afaa588bd1363b5f1e9de2b5d650d19e8bce15f1a2d71be996a3e200e313c20e3a0e72cb3aea3b33de4db7baf5c85d794bf211b8726352");
                        intent.putExtra("data", finalData);
                        intent.putExtra("option_clicked", "check_out");
                        intent.putExtra("callback", responseObject);
                        startActivity(intent);
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
                startActivity(intent);
            }
        });
    }

    private void inItView() {
        SnapmintEmiInfoButton snapmintButton = findViewById(R.id.snapmintButton);
        snapmintButton.showSnapmintEmiInfo("791", "1616/snap_ketch.json", true, SnapmintConfiguration.PROD);
        et_phone_no = findViewById(R.id.et_phone_no);
        merchantIdEdt = findViewById(R.id.et_merchant_id);
        merchantKeyEdt = findViewById(R.id.et_merchant_key);
        merchantTokenEdt = findViewById(R.id.et_merchant_token);
        storeIdEdt = findViewById(R.id.et_store_id);
        orderIdEdt = findViewById(R.id.et_order_id);
        orderValueEdt = findViewById(R.id.et_order_value);
        merchantConfirmUrlEdt = findViewById(R.id.et_merchant_confirmation_url);
        merchantFailUrlEdt = findViewById(R.id.et_merchant_failure_url);

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

        merchantIdEdt.setText("83");
        merchantTokenEdt.setText("pR6AZlBb");
        merchantKeyEdt.setText("0WYLBdpB");
        storeIdEdt.setText("1");
        orderIdEdt.setText("1");
        orderValueEdt.setText("20000");
        merchantConfirmUrlEdt.setText("http://www.vijaysales.com/success");
        merchantFailUrlEdt.setText("http://www.vijaysales.com/failed");

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