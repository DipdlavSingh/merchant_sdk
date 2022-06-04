package com.viral.snapmintmerchantapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.viral.utils.Helper;
import com.viral.utils.SnapmintResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class ResponseActivity extends AppCompatActivity {

    TextView checksum_response;
    TextView response_code;
    TextView response_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        checksum_response = findViewById(R.id.tv_checksum_response);
        response_code = findViewById(R.id.tv_response_code);
        response_message = findViewById(R.id.tv_response_message);

        Bundle bundle = getIntent().getExtras();
        String code = bundle.getString("code");
        String message = bundle.getString("message");

        getChecksum(message);

        response_code.setText(code);
        response_message.setText(message);
    }

    private void getChecksum(String message) {
        String checkSum = "";
        String status = "";

        try {
            JSONObject jsonObject = new JSONObject(message);

            if (jsonObject.has("merchant_data")) {
                JSONObject merchantDataJsonObj = jsonObject.getJSONObject("merchant_data");

                if (merchantDataJsonObj.has("merchant_params")) {
                    JSONObject merchantParamsJsonObj = jsonObject.getJSONObject("merchant_params");

                    if (merchantParamsJsonObj.has("checksum_hash"))
                        checkSum = merchantParamsJsonObj.getString("checksum_hash");
                    if (merchantParamsJsonObj.has("status"))
                        status = merchantParamsJsonObj.getString("status");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        String validatedChecksum = "";
//        validatedChecksum = Helper.validateChecksum(status, checkSum);
//
//        checksum_response.setText(validatedChecksum);
    }
}
