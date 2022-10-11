package com.viral.snapmint_sdk.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.snapmint.merchantsdk.utils.SnapmintResponse;
import com.viral.snapmint_sdk.ResponseActivity;

public class ResponseObject implements SnapmintResponse, Parcelable {

    @Override
    public void handlePaymentResponse(String code, String message, Activity context) {
        Intent mIntent = new Intent(context, ResponseActivity.class);
        mIntent.putExtra("code", code);
        mIntent.putExtra("message", message);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(mIntent);
        context.finish();
    }

    public static final Creator<ResponseObject> CREATOR = new Creator<ResponseObject>() {
        @Override
        public ResponseObject createFromParcel(Parcel in) {
            return new ResponseObject();
        }

        @Override
        public ResponseObject[] newArray(int size) {
            return new ResponseObject[size];
        }
    };

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub
    }
}
