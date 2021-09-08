package com.viral.snapmintmerchantapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.viral.utils.ResponseObject;

public class MainActivity extends AppCompatActivity {

    Button btn_check_out;
    Button btn_invalid_data;
    EditText et_phone_no;
    ResponseObject responseObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_check_out = findViewById(R.id.btn_check_out);
        btn_invalid_data = findViewById(R.id.btn_invalid_data);

        et_phone_no = findViewById(R.id.et_phone_no);

        responseObject = new ResponseObject();

        btn_check_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CheckSum and All Parameter, context
                final String phoneNumber = et_phone_no.getText().toString();
                String data1 = "utf8=?&co_source=sdk&authenticity_token=jnpr0Kea3obVQyC5N6OAVKA8rmdbfxCyGFADxGP8LT8hxltV5SCARcigKyZD6Ix/NRv8S8YiqGPwofAj3qrSrw==&source=&user_type=old_user&mobile=";
                String data2 = "&merchant_id=1&store_id=1&order_id=1&order_value=2000&merchant_confirmation_url=uat.sodelsolutions.com/magento/magento/success&merchant_failure_url=uat.sodelsolutions.com/magento/magento/failed&shipping_fees=10000&udf1=test&udf2=test&udf3=test&full_name=GIRIDHAR+m+MAMIDIPALLY&email=rahul@snapmint.com&billing_first_name=GIRIDHAR&billing_middle_name=ravi&billing_last_name=MAMIDIPALLY&billing_full_name=GIRIDHAR+ravi+MAMIDIPALLY&billing_address_line1=GIRIDHAR+EVENT+ORGANRING&billing_address_line2=8-2-268/1/D/2+ROAD+NO+3&billing_city=Mumbai&billing_zip=400076&shipping_first_name=GIRIDHAR&shipping_middle_name=ravi&shipping_last_name=MAMIDIPALLY&shipping_full_name=GIRIDHAR+ravi+MAMIDIPALLY&shipping_address_line1=GIRIDHAR+EVENT+ORGANRING&shipping_address_line2=8-2-268/1/D/2+ROAD+NO+3&shipping_city=Mumbai&shipping_zip=400076&products[][sku]=abdx123&products[][name]=Product+2&products[][unit_price]=1000&products[][quantity]=5&products[][item_url]=https://google.com/test&products[][udf1]=udf1&products[][udf2]=udf2&products[][udf3]=udf3&products[][sku]=abdx123&products[][name]=Product+2&products[][unit_price]=1000&products[][quantity]=5&products[][item_url]=https://google.com/test&products[][udf1]=udf1&products[][udf2]=udf2&products[][udf3]=udf3&checksum_hash=119c97050ad1740d18afaa588bd1363b5f1e9de2b5d650d19e8bce15f1a2d71be996a3e200e313c20e3a0e72cb3aea3b33de4db7baf5c85d794bf211b8726352";
                String finalData = data1 + phoneNumber + data2;
                Intent intent = new Intent(MainActivity.this, com.viral.snapmintsdk.MainActivity.class);
//                intent.putExtra("data", "utf8=?&co_source=sdk&authenticity_token=jnpr0Kea3obVQyC5N6OAVKA8rmdbfxCyGFADxGP8LT8hxltV5SCARcigKyZD6Ix/NRv8S8YiqGPwofAj3qrSrw==&source=&user_type=old_user&mobile=6547989250&merchant_id=1&store_id=1&order_id=1&order_value=20000&merchant_confirmation_url=uat.sodelsolutions.com/magento/magento/success&merchant_failure_url=uat.sodelsolutions.com/magento/magento/failed&shipping_fees=10000&udf1=test&udf2=test&udf3=test&full_name=GIRIDHAR+m+MAMIDIPALLY&email=rahul@snapmint.com&billing_first_name=GIRIDHAR&billing_middle_name=ravi&billing_last_name=MAMIDIPALLY&billing_full_name=GIRIDHAR+ravi+MAMIDIPALLY&billing_address_line1=GIRIDHAR+EVENT+ORGANRING&billing_address_line2=8-2-268/1/D/2+ROAD+NO+3&billing_city=Mumbai&billing_zip=400076&shipping_first_name=GIRIDHAR&shipping_middle_name=ravi&shipping_last_name=MAMIDIPALLY&shipping_full_name=GIRIDHAR+ravi+MAMIDIPALLY&shipping_address_line1=GIRIDHAR+EVENT+ORGANRING&shipping_address_line2=8-2-268/1/D/2+ROAD+NO+3&shipping_city=Mumbai&shipping_zip=400076&products[][sku]=abdx123&products[][name]=Product+2&products[][unit_price]=1000&products[][quantity]=5&products[][item_url]=https://google.com/test&products[][udf1]=udf1&products[][udf2]=udf2&products[][udf3]=udf3&products[][sku]=abdx123&products[][name]=Product+2&products[][unit_price]=1000&products[][quantity]=5&products[][item_url]=https://google.com/test&products[][udf1]=udf1&products[][udf2]=udf2&products[][udf3]=udf3&checksum_hash=119c97050ad1740d18afaa588bd1363b5f1e9de2b5d650d19e8bce15f1a2d71be996a3e200e313c20e3a0e72cb3aea3b33de4db7baf5c85d794bf211b8726352");
                intent.putExtra("data", finalData);
                intent.putExtra("option_clicked", "check_out");
                intent.putExtra("callback", responseObject);
                startActivity(intent);
            }
        });

        btn_invalid_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.viral.snapmintsdk.MainActivity.class);
                intent.putExtra("data", "utf8=?&co_source=sdk&authenticity_token=jnpr0Kea3obVQyC5N6OAVKA8rmdbfxCyGFADxGP8LT8hxltV5SCARcigKyZD6Ix/NRv8S8YiqGPwofAj3qrSrw==&source=&user_type=old_user&mobile=9987026671&merchant_id=1&store_id=1&order_id=1&order_value=20000&merchant_confirmation_url=uat.sodelsolutions.com/magento/magento/success&merchant_failure_url=uat.sodelsolutions.com/magento/magento/failed&shipping_fees=10000&udf1=test&udf2=test&udf3=test&full_name=GIRIDHAR+m+MAMIDIPALLY&email=rahul@snapmint.com&billing_first_name=GIRIDHAR&billing_middle_name=ravi&billing_last_name=MAMIDIPALLY&billing_full_name=GIRIDHAR+ravi+MAMIDIPALLY&billing_address_line1=GIRIDHAR+EVENT+ORGANRING&billing_address_line2=8-2-268/1/D/2+ROAD+NO+3&billing_city=Mumbai&billing_zip=400076&shipping_first_name=GIRIDHAR&shipping_middle_name=ravi&shipping_last_name=MAMIDIPALLY&shipping_full_name=GIRIDHAR+ravi+MAMIDIPALLY&shipping_address_line1=GIRIDHAR+EVENT+ORGANRING&shipping_address_line2=8-2-268/1/D/2+ROAD+NO+3&shipping_city=Mumbai&shipping_zip=400076&products[][sku]=abdx123&products[][name]=Product+2&products[][unit_price]=1000&products[][quantity]=5&products[][item_url]=https://google.com/test&products[][udf1]=udf1&products[][udf2]=udf2&products[][udf3]=udf3&products[][sku]=abdx123&products[][name]=Product+2&products[][unit_price]=1000&products[][quantity]=5&products[][item_url]=https://google.com/test&products[][udf1]=udf1&products[][udf2]=udf2&products[][udf3]=udf3&checksum_hash=119c97050ad1740d18afaa588bd1363b5f1e9de2b5d650d19200e313c20e3a0e72cb3aea3jsdbfjbnbdshacfbdhjsbcvb33de4db7baf5c85d794bf211b8726352");
                intent.putExtra("option_clicked", "check_out");
                intent.putExtra("callback", responseObject);
                startActivity(intent);
            }
        });
    }
}