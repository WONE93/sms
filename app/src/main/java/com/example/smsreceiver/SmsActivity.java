package com.example.smsreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SmsActivity extends AppCompatActivity {

    TextView textView, textView2, textView3;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        Intent intent = getIntent();
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);

        String sender = intent.getStringExtra("sender");
        textView.setText(sender);
        String contents = intent.getStringExtra("contents");
        textView2.setText(contents);
        String recievedDate = intent.getStringExtra("recievedDate");
        textView3.setText(recievedDate);


        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}
