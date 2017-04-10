package aas.androidlibs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by $ wxy on 2017/4/7.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                });
    }
}
