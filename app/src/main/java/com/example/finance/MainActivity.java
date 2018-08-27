package com.example.finance;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.finance.Fragment.HomeFragment;
import com.example.finance.Fragment.InvestFragment;
import com.example.finance.Fragment.MoreFragment;
import com.example.finance.Fragment.UserFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends FragmentActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.ll_home)
    LinearLayout llHome;
    @BindView(R.id.iv_invest)
    ImageView ivInvest;
    @BindView(R.id.tv_invest)
    TextView tvInvest;
    @BindView(R.id.ll_invest)
    LinearLayout llInvest;
    @BindView(R.id.iv_user)
    ImageView ivUser;
    @BindView(R.id.tv_user)
    TextView tvUser;
    @BindView(R.id.ll_user)
    LinearLayout llUser;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.ll_more)
    LinearLayout llMore;
    @BindView(R.id.content)
    FrameLayout content;

    private FragmentTransaction ft;
    private HomeFragment homeFragment;
    private InvestFragment investFragment;
    private UserFragment userFragment;
    private MoreFragment moreFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSelection(0);
    }

    @OnClick({R.id.ll_home, R.id.ll_invest, R.id.ll_more, R.id.ll_user})
    public void changeTab(View view) {
        switch (view.getId()) {
            case R.id.ll_home:
                setSelection(0);
                break;
            case R.id.ll_invest:
                setSelection(1);
                break;
            case R.id.ll_user:
                setSelection(2);
                break;
            case R.id.ll_more:
                setSelection(3);
                break;
        }
    }

    private void setSelection(int i) {
        FragmentManager fm = getFragmentManager();
        ft = fm.beginTransaction();
        hideFragment();
        reSetTab();
        switch (i) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    ft.add(R.id.content,homeFragment);
                }
                ivHome.setImageResource(R.drawable.bid01);
                tvHome.setTextColor(getResources().getColor(R.color.home_back_selected));
                ft.show(homeFragment);
                break;
            case 1:
                if (investFragment == null) {
                    investFragment = new InvestFragment();
                    ft.add(R.id.content,investFragment);
                }
                ivInvest.setImageResource(R.drawable.bid03);
                tvInvest.setTextColor(getResources().getColor(R.color.home_back_selected));
                ft.show(investFragment);
                break;
            case 2:
                if (userFragment == null) {
                    userFragment = new UserFragment();
                    ft.add(R.id.content,userFragment);
                }
                ivUser.setImageResource(R.drawable.bid05);
                tvUser.setTextColor(getResources().getColor(R.color.home_back_selected));
                ft.show(userFragment);
                break;
            case 3:
                if (moreFragment == null) {
                    moreFragment = new MoreFragment();
                    ft.add(R.id.content,moreFragment);
                }
                ivMore.setImageResource(R.drawable.bid07);
                tvMore.setTextColor(getResources().getColor(R.color.home_back_selected));
                ft.show(moreFragment);
                break;
        }
        ft.commit();
    }

    private void reSetTab() {
        ivHome.setImageResource(R.drawable.bid02);
        ivInvest.setImageResource(R.drawable.bid04);
        ivUser.setImageResource(R.drawable.bid06);
        ivMore.setImageResource(R.drawable.bid08);
        tvHome.setTextColor(getResources().getColor(R.color.home_back_unselected));
        tvInvest.setTextColor(getResources().getColor(R.color.home_back_unselected));
        tvMore.setTextColor(getResources().getColor(R.color.home_back_unselected));
        tvUser.setTextColor(getResources().getColor(R.color.home_back_unselected));
    }

    private void hideFragment() {
        if (homeFragment != null)
            ft.hide(homeFragment);
        if (investFragment != null)
            ft.hide(investFragment);
        if (userFragment != null)
            ft.hide(userFragment);
        if (moreFragment != null)
            ft.hide(moreFragment);
    }
}
