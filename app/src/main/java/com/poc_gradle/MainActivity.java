package com.poc_gradle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.e.mymathlib.Sdk;

public class MainActivity extends AppCompatActivity {

    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);

        Sdk sdk = new Sdk();
        result.setText("Addition is :- "+sdk.Calculate(5,10));


    }
}
