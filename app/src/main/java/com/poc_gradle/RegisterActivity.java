package com.poc_gradle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import main.communicator.DiagnosticsSDK;
import main.communicator.RegistrationResult;


public class RegisterActivity extends AppCompatActivity {
    TextView textView;
    Button button_next;
    Disposable mDisposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button button = findViewById(R.id.register_button);
        button_next = findViewById(R.id.next_button);

        textView =  findViewById(R.id.result_camera);
        getSupportActionBar().setTitle(getString(R.string.lable_auto_back_camera));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
        button_next.setVisibility(View.INVISIBLE);
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        initSdk();
    }

    private void initSdk() {

        DiagnosticsSDK.get().Registrationresults().subscribe(new Observer<RegistrationResult>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("@@####Caller", " onSubscribe " );

            }

            @Override
            public void onNext(RegistrationResult result) {
                Log.d("@@####Caller", " onNext " );
            }

            @Override
            public void onError(Throwable e) {
                Log.d("@@####Caller", " onError " );
            }

            @Override
            public void onComplete() {
                Log.d("@@####Caller", " onComplete " );
                button_next.setVisibility(View.VISIBLE);

            }
        });




    }

    public void register(){
        DiagnosticsSDK.get().Register(this);
    }
}
