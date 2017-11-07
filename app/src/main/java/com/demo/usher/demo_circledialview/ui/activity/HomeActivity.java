package com.demo.usher.demo_circledialview.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.demo.usher.demo_circledialview.R;

/**
 * Created by ChenJianCong on 2017-11-6.
 * <p>
 * Ps:
 */

public class HomeActivity extends AppCompatActivity {

    private ImageView mIvMain;
    private Animation mAlphaAnimation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mIvMain = (ImageView) findViewById(R.id.iv_main);

        mAlphaAnimation = new AlphaAnimation(0f, 1f);
        mAlphaAnimation.setDuration(1 * 1000);
        mAlphaAnimation.setRepeatCount(Animation.INFINITE);
        mAlphaAnimation.setRepeatMode(Animation.REVERSE);
        mAlphaAnimation.setInterpolator(new LinearInterpolator());

        mIvMain.post(new Runnable() {
            @Override
            public void run() {
                if (mIvMain.getAnimation() == null) {
                    mIvMain.startAnimation(mAlphaAnimation);
                }
            }
        });

    }

}
