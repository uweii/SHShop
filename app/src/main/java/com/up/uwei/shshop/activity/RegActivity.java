package com.up.uwei.shshop.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.up.uwei.shshop.Configs;
import com.up.uwei.shshop.R;
import com.up.uwei.shshop.event.Event;
import com.up.uwei.shshop.pojo.Status;
import com.up.uwei.shshop.pojo.User;
import com.up.uwei.shshop.service.RetrofitService;
import com.up.uwei.shshop.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.up.uwei.shshop.Configs.EMAIL_ALREADY_EXIST;

/**
 * A login screen that offers login via email/password.
 */
public class RegActivity extends AppCompatActivity implements View.OnClickListener{

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private String mVeryifyCode = null;  //返回的验证码
    private boolean codeTimeOver = true;  //验证码等待时间
    private int codeTime = 60;
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mTvCode;
    private View mProgressView;
    private TextView mTvGetCode;
    private LinearLayout mLinearLayoutBack;
    private TextView mTvRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        EventBus.getDefault().register(this);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mLinearLayoutBack = findViewById(R.id.ll_back);
        mTvGetCode = findViewById(R.id.tv_get_code);
        mTvCode = findViewById(R.id.tv_code);
        mTvGetCode.setOnClickListener(this);
        mLinearLayoutBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mTvRegister = (TextView) findViewById(R.id.tv_register);
        mTvRegister.setOnClickListener(this);

        mProgressView = findViewById(R.id.login_progress);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.requestFocus();
            mPasswordView.setError(getString(R.string.error_invalid_password));
            return;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.requestFocus();
            mEmailView.setError(getString(R.string.error_field_required));
            return;
        } else if (!isEmailValid(email)) {
            mEmailView.requestFocus();
            mEmailView.setError(getString(R.string.error_invalid_email));
            return;
        }else {
                registe(email, mTvCode.getText().toString(),password);
        }

    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }


    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(RegActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_get_code:
                if(!isEmailValid(mEmailView.getText().toString())){
                    mEmailView.setError(getString(R.string.error_invalid_email));
                    mEmailView.requestFocus();
                    return;
                }
                if(codeTimeOver){
                    codeTimeOver = false;
                    getCode(mEmailView.getText().toString());
                }else {
                    Toast.makeText(this, ""+codeTime + "秒后再试", Toast.LENGTH_SHORT).show();
                }
                    break;
            case R.id.tv_register:
                attemptLogin();
                break;
        }
    }

    private void registe(String email, String code, String pwd){
        if(!code.equals(mVeryifyCode)){
            mTvCode.requestFocus();
            mTvCode.setError("验证码错误");
            return;
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configs.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        retrofitService.register(email, pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Gson gson = new Gson();
                        try {
                            String res = responseBody.string();
                            User user = gson.fromJson(res, User.class);
                            if (user.getEmail() == null){
                                Toast.makeText(RegActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            //注册成功，启动activity
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(RegActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @SuppressLint("CheckResult")
    private void getCode(String eMail){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configs.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        retrofitService.getCode(eMail)
                .subscribeOn(Schedulers.io())   //IO线程加载数据
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        String res = null;
                        try {
                             res = responseBody.string();
                             mTvCode.setText(res);
                             if(String.valueOf(EMAIL_ALREADY_EXIST).equals(res)){  //表示邮箱存在
                                 mEmailView.requestFocus();
                                 mEmailView.setError("此邮箱已注册");
                                 codeTimeOver = true;
                                 return;
                             }
                             if(null != res && !"0".equals(res)){
                                 mVeryifyCode = res;
                                 codeTime = 60;
                                 new Timer().schedule(new TimerTask() {
                                     @Override
                                     public void run() {
                                        if(codeTime != 0){
                                            EventBus.getDefault().post(new Event("setTime", codeTime));
                                            codeTime --;
                                        }else {
                                            EventBus.getDefault().post(new Event("recover",0));
                                            cancel();
                                        }
                                     }
                                 },0,1000);
                             }else {
                                 mEmailView.requestFocus();
                                 mEmailView.setError("请输入有效邮箱");
                                 codeTimeOver = true;
                             }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeCodeLastTime(Event event){
        if (event.getMessage().equals("setTime")){
            mTvGetCode.setText(""+event.getTime()+"秒后再试");
        }else {
            mTvGetCode.setText("获取验证码");
            codeTimeOver = true;
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

