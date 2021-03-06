package com.poc_gradle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import main.common.Constant;

import main.communicator.DiagnosticsSDK;
import main.communicator.Result;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    Switch simpleSwitch;
    boolean switchState=true;
    Result mResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);




        Button button = findViewById(R.id.camera_button);
        Button button_next = findViewById(R.id.next_button);
        textView =  findViewById(R.id.result_camera);
        simpleSwitch = (Switch) findViewById(R.id.switchView_mode);

        simpleSwitch.setTextOn("Manual");
        simpleSwitch.setTextOff("Auto");
        simpleSwitch.setVisibility(View.INVISIBLE);

        initSdk();
        //register();
        getSupportActionBar().setTitle(getString(R.string.lable_auto_back_camera));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // switchState = simpleSwitch.isChecked();
                start();
            }
        });
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TouchTestGridActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }

    private void initSdk() {

         /*diagnosticsSDK = new CommonSdk(this) {
            @Override
            public void OnComplete(Result result) {
                Log.d("@@####Caller", " Camera App OnComplete: " );
                Log.d("@@####Caller", " Camera App OnComplete: id " + result.getTestID() );
                Log.d("@@####Caller", " Camera App OnComplete: result " + result.isPass() );
                if(result.isPass()){
                    textView.setText(getString(R.string.test_result_pass));
                }else{
                    try{
                        int result_fail_reason =(int) result.getTestFailDiscription();

                        if(result_fail_reason==Constant.USER_FAIL_TEST){
                            textView.setText(getString(R.string.test_result_fail_user_fail));
                        }else if(result_fail_reason==Constant.CONFIG_ERROR){
                            textView.setText(getString(R.string.test_result_fail_congig));
                        }else{
                            textView.setText(getString(R.string.test_result_fail));
                        }

                    }catch(NullPointerException e){
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void OnProgress(Result result) {
                Log.d("@@####Caller", " Camera App OnProgress: ");
            }

            @Override
            public void OnError(Result result) {
                Log.d("@@####Caller", " Camera App OnError: ");
                textView.setText(getString(R.string.test_result_fail));

                Toast.makeText(MainActivity.this,result.getException().getMessage(),Toast.LENGTH_LONG).show();
            }
        };*/
        DiagnosticsSDK.get().TestResults().subscribe(new Observer<Result>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("@@####Caller Camera", " onSubscribe " );



            }

            @Override
            public void onNext(Result result) {
                Log.d("@@####Caller Camera", " onNext " );
                mResult = result;
            }

            @Override
            public void onError(Throwable e) {
                Log.d("@@####Caller Camera", " onError " );
                textView.setText(getString(R.string.test_result_fail));

                Toast.makeText(MainActivity.this,mResult.getException().getMessage(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onComplete() {
                Log.d("@@####Caller Camera", " onComplete ");
                if (mResult != null) {
                    if (mResult.isPass()) {
                        textView.setText(getString(R.string.test_result_pass));
                    } else {
                        try {
                            int result_fail_reason = (int) mResult.getTestFailDiscription();

                            if (result_fail_reason == Constant.USER_FAIL_TEST) {
                                textView.setText(getString(R.string.test_result_fail_user_fail));
                            } else if (result_fail_reason == Constant.CONFIG_ERROR) {
                                textView.setText(getString(R.string.test_result_fail_congig));
                            } else {
                                textView.setText(getString(R.string.test_result_fail));
                            }

                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    Log.d("@@####Caller Camera", " onComplete mResult is null ");
                }
            }
        });




    }



    public void start(){
        try {
            DiagnosticsSDK.get().StartTest(this,"101");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
