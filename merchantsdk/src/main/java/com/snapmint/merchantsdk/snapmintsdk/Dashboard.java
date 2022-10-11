package com.snapmint.merchantsdk.snapmintsdk;//package com.viral.snapmintsdk;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.LinearLayout;
//
//public class Dashboard extends AppCompatActivity {
//
//    private Button btn_open_form, btn_pay_now;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_dashboard);
//
////        btn_open_form = findViewById(R.id.btn_open_form);
////        btn_pay_now = findViewById(R.id.btn_pay_now);
//
//        LinearLayout linearLayout = new LinearLayout(Dashboard.this);
//        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//
//        btn_open_form = new Button(Dashboard.this);
//        btn_open_form.setText("Open Form");
//
//        btn_pay_now = new Button(Dashboard.this);
//        btn_pay_now.setText("Pay Now");
//
//        linearLayout.addView(btn_open_form);
//        linearLayout.addView(btn_pay_now);
//
//        setContentView(linearLayout);
//
//        btn_open_form.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Dashboard.this, MainActivity.class);
//                intent.putExtra("option_clicked", "form");
//                startActivity(intent);
//            }
//        });
//
//        btn_pay_now.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Dashboard.this, MainActivity.class);
//                intent.putExtra("option_clicked", "pay");
//                startActivity(intent);
//            }
//        });
//    }
//}
