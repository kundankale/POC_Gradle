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

public class TouchTestGridActivity extends AppCompatActivity {
    public static final String TAG = TouchTestGridActivity.class.getSimpleName();

    private TextView textView;
    Switch simpleSwitch;
    boolean switchState=true;
    Result mResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_test_grid);
        Button button = findViewById(R.id.camera_button);
        Button button_next = findViewById(R.id.next_button);
        textView =  findViewById(R.id.result_camera);
        simpleSwitch = (Switch) findViewById(R.id.switchView_mode);

        simpleSwitch.setTextOn("Manual");
        simpleSwitch.setTextOff("Auto");

        simpleSwitch.setVisibility(View.INVISIBLE);
        initLibrary();
        getSupportActionBar().setTitle(getString(R.string.lable_touchgridscreen));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  switchState = simpleSwitch.isChecked();
                scheduleTest();
            }
        });

       // button_next.setVisibility(View.GONE);
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TouchTestGridActivity.this, VibrationActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    /**
     * Initiates Library.
     * <p>
     * This method must get executed from OnCreate of Activity
     * or before executing any Test.This method will initiate DiagnosticsLibrary
     * object.
     * OnComplete method will get called when Test is completed.
     * OnProgress method will get called when Test Library updates progress about Test.
     * OnError method will get called when Test Library throws Exception.
     */
    private void initLibrary() {
        /*diagnosticsSDK = new CommonSdk(TouchTestGridActivity.this) {


            @Override
            public void OnComplete(Result result) {

                Log.d("@@####Caller", " TouchTest App OnComplete: " );
                Log.d("@@####Caller", " TouchTest OnComplete: id " + result.getTestID() );
                Log.d("@@####Caller", " TouchTest OnComplete: result " + result.isPass() );

                if(result.isPass()){
                    textView.setText(getString(R.string.test_result_pass));
                }else{
                    try{
                        int result_fail_reason =(int) result.getTestFailDiscription();

                        if(result_fail_reason== Constant.USER_FAIL_TEST){
                            textView.setText(getString(R.string.test_result_fail_user_fail));
                        }else if(result_fail_reason== Constant.TOUCH_TEST_TIMER_ENDS){
                            textView.setText("Test Result:- Test time out");
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
                Log.d("@@####Caller", " Touch App OnProgress: ");
            }

            @Override
            public void OnError(Result result) {
                Log.d("@@####Caller", " Touch App OnError: ");

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
                Log.d("@@####Caller Touch", " onNext " );
                mResult = result;
            }

            @Override
            public void onError(Throwable e) {
                Log.d("@@####Caller Touch", " onError " );
                textView.setText(getString(R.string.test_result_fail));

                Toast.makeText(TouchTestGridActivity.this,mResult.getException().getMessage(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onComplete() {
                Log.d("@@####Caller Touch", " onComplete " );
                if(mResult!=null){
                    if(mResult.isPass()){
                        textView.setText(getString(R.string.test_result_pass));
                    }else{
                        try{
                            int result_fail_reason =(int) mResult.getTestFailDiscription();

                            if(result_fail_reason== Constant.USER_FAIL_TEST){
                                textView.setText(getString(R.string.test_result_fail_user_fail));
                            }else if(result_fail_reason== Constant.TOUCH_TEST_TIMER_ENDS){
                                textView.setText("Test Result:- Test time out");
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
                    Log.d("@@####Caller Touch", " onComplete mResult is null " );
                }


            }
        });



    }

    /**
     * Start Test Based on Id
     * <p>
     * This method Initiates Test by passing TestID to DiagnosticLibrary Object.
     * TestResult will get updated in OnComplete and OnProgress method of initLibrary method.
     */
    private void scheduleTest() {
        /*TestInput testInput = new TestInput();
        testInput.setTestId(Constant.TOUCH_ID);
        testInput.setWithUI(switchState);
*/
        try {
            DiagnosticsSDK.get().StartTest(this,"102");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
