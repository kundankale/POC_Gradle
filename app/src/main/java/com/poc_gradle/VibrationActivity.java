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


public class VibrationActivity extends AppCompatActivity {


    private TextView textView;
    Switch simpleSwitch;
    boolean switchState=true;

    Result mResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibration);
        Button button = findViewById(R.id.vibration_button);
        Button button_next = findViewById(R.id.next_button);
        textView =  findViewById(R.id.result_vibration);
        simpleSwitch = (Switch) findViewById(R.id.switchView_mode);

        simpleSwitch.setTextOn("Manual");
        simpleSwitch.setTextOff("Auto");

        simpleSwitch.setVisibility(View.INVISIBLE);
        initSDK();




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // switchState = simpleSwitch.isChecked();

                scheduleTest();

            }
        });
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VibrationActivity.this, SpeakerMicrophoneActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }



        private void initSDK() {

        /*diagnosticsSDK = new CommonSdk(VibrationActivity.this) {


            @Override
            public void OnComplete(Result result) {

                Log.d("@@####Caller", " Vibration App OnComplete: " );
                Log.d("@@####Caller", " Vibration OnComplete: id " + result.getTestID() );
                Log.d("@@####Caller", " Vibration OnComplete: result " + result.isPass() );

                if(result.isPass()){
                    textView.setText(getString(R.string.test_result_pass));
                }else{
                    try{
                        int result_fail_reason =(int) result.getTestFailDiscription();

                        if(result_fail_reason== Constant.USER_FAIL_TEST){
                            textView.setText(getString(R.string.test_result_fail_user_fail));
                        }else if(result_fail_reason== Constant.CONFIG_ERROR){
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
                Log.d("@@####Caller", " vibration App OnProgress: ");
            }

            @Override
            public void OnError(Result result) {
                Log.d("@@####Caller", " vibration App OnError: ");
                textView.setText(getString(R.string.test_result_fail));
            }
        };*/

            DiagnosticsSDK.get().TestResults().subscribe(new Observer<Result>() {
                @Override
                public void onSubscribe(Disposable d) {
                    Log.d("@@####Caller Touch", " onSubscribe " );
                }

                @Override
                public void onNext(Result result) {
                    Log.d("@@####Caller Vibration", " onNext " );
                    mResult = result;
                }

                @Override
                public void onError(Throwable e) {
                    Log.d("@@####Caller Vibration", " onError " );
                    textView.setText(getString(R.string.test_result_fail));

                    Toast.makeText(VibrationActivity.this,mResult.getException().getMessage(),Toast.LENGTH_LONG).show();
                }

                @Override
                public void onComplete() {
                    Log.d("@@####Caller Vibration", " onComplete " );
                    if(mResult!=null){
                        if(mResult.isPass()){
                            textView.setText(getString(R.string.test_result_pass));
                        }else{
                            try{
                                int result_fail_reason =(int) mResult.getTestFailDiscription();

                                if(result_fail_reason== Constant.USER_FAIL_TEST){
                                    textView.setText(getString(R.string.test_result_fail_user_fail));
                                }else if(result_fail_reason== Constant.CONFIG_ERROR){
                                    textView.setText(getString(R.string.test_result_fail_congig));
                                }else{
                                    textView.setText(getString(R.string.test_result_fail));
                                }

                            }catch(NullPointerException e){
                                e.printStackTrace();
                            }

                        }
                    }
                    else{
                        Log.d("@@####Caller Vibration", " onComplete mResult is null " );
                    }


                }
            });








        }

    private void scheduleTest() {
        /*TestInput testInput = new TestInput();
        testInput.setTestId(Constant.VIBRATION_ID);
        testInput.setInput(4);
        testInput.setWithUI(switchState);*/

        try {
            DiagnosticsSDK.get().StartTest(this,"103");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
