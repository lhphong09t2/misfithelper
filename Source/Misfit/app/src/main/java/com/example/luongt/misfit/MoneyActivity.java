package com.example.luongt.misfit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.example.luongt.misfit.fragment.MoneyPaymentFragment;

public class MoneyActivity extends FragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private Button _paymentButton;
    private Button _statisticButton;
    private Button _settingButton;

    private PagerAdapter _pagerAdapter;
    private ViewPager _viewPager;

    private View _paymentView;
    private View _statisticView;
    private View _settingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);

        _paymentButton = (Button) findViewById(R.id.paymentButton);
        _statisticButton = (Button) findViewById(R.id.statisticButton);
        _settingButton = (Button) findViewById(R.id.settingButton);

        _paymentButton.setOnClickListener(this);
        _statisticButton.setOnClickListener(this);
        _settingButton.setOnClickListener(this);

        _pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        _viewPager = ((ViewPager) findViewById(R.id.container));
        _viewPager.setAdapter(_pagerAdapter);
        _viewPager.addOnPageChangeListener(this);

        _paymentView = findViewById(R.id.paymentView);
        _statisticView = findViewById(R.id.statisticView);
        _settingView = findViewById(R.id.settingView);

        showPaymentFragment();
    }

    @Override
    public void onClick(View v) {
        if (v == _paymentButton) {
            handlePaymentFragmentChanged();
            showPaymentFragment();

        } else if (v == _statisticButton) {
            handleStatisticFragmentChanged();
            showStatisticFragment();

        } else if (v == _settingButton) {
            handleSettingFragmentChanged();
            showSettingFragment();
        }
    }

    private void showPaymentFragment() {
        _viewPager.setCurrentItem(0);
    }

    private void showStatisticFragment() {
        _viewPager.setCurrentItem(1);
    }

    private void showSettingFragment() {
        _viewPager.setCurrentItem(2);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        switch (position) {
            case 0:
                handlePaymentFragmentChanged();
                break;
            case 1:
                handleStatisticFragmentChanged();
                break;
            default:
                handleSettingFragmentChanged();
        }
    }

    @Override
    public void onPageSelected(int position) {}

    @Override
    public void onPageScrollStateChanged(int state) {}

    private void handlePaymentFragmentChanged() {
        _paymentView.setVisibility(View.VISIBLE);
        _statisticView.setVisibility(View.INVISIBLE);
        _settingView.setVisibility(View.INVISIBLE);
    }

    private void handleStatisticFragmentChanged() {
        _paymentView.setVisibility(View.INVISIBLE);
        _statisticView.setVisibility(View.VISIBLE);
        _settingView.setVisibility(View.INVISIBLE);
    }

    private void handleSettingFragmentChanged() {
        _paymentView.setVisibility(View.INVISIBLE);
        _statisticView.setVisibility(View.INVISIBLE);
        _settingView.setVisibility(View.VISIBLE);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    MoneyPaymentFragment fragment = new MoneyPaymentFragment();
                    //TODO: event
                    return fragment;
                case 1:
                    return new Fragment();
                default:
                    return new Fragment();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
