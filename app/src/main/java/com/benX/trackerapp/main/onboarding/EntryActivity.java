package com.benX.trackerapp.main.onboarding;

import androidx.annotation.Nullable;

import android.os.Bundle;
import android.os.CountDownTimer;

import com.benX.trackerapp.R;
import com.benX.trackerapp.core.base.BaseActivity;
import com.benX.trackerapp.core.constant.AppConstant;
import com.benX.trackerapp.databinding.ActivityEntryBinding;
import com.benX.trackerapp.main.home.HomeActivity;

public class EntryActivity extends BaseActivity<ActivityEntryBinding> {

    ActivityEntryBinding binding;

    @Override
    public int getLayoutId() {
        return R.layout.activity_entry;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();

        CountDownTimer count = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) { }
            @Override
            public void onFinish() {
                initApp();
            }
        };

        count.start();
    }

    public void initApp() {
        launchActivity(this, HomeActivity.class);
    }

}
