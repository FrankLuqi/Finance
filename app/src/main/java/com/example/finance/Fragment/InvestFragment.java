package com.example.finance.Fragment;

import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finance.R;
import com.example.finance.common.BaseFragment;
import com.example.finance.utils.MyFragmentPagerAdapter;
import com.example.finance.utils.UIUtils;
import com.loopj.android.http.RequestParams;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018.8.9.
 */

public class InvestFragment extends BaseFragment {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_setting)
    ImageView titleSetting;
    @BindView(R.id.tab_indictor)
    TabPageIndicator tabIndictor;
    @BindView(R.id.pager)
    ViewPager pager;
    Unbinder unbinder;

    private List<android.app.Fragment> fragmentList = new ArrayList<>();

    @Override
    protected RequestParams getParams() {
        return new RequestParams();
    }

    @Override
    protected String getUrl() {
        return "";
    }

    @Override
    protected void initData(String content) {
        initFragment();

        pager.setAdapter(new MyAdapter(getFragmentManager()));
        tabIndictor.setViewPager(pager);
    }

    //如果是图片的适配器则使用pageradapter，如果是碎片的适配器则使用FragmentPagerAdapter
    private class MyAdapter extends MyFragmentPagerAdapter{


        public MyAdapter(android.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.app.Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return UIUtils.getStringArray(R.array.touzi_tab)[position];
        }

    }

    private void initFragment() {
        ProductListFragment productListFragment = new ProductListFragment();
        ProductRecommendFragment productRecommendFragment = new ProductRecommendFragment();
        ProductHotFragment productHotFragment = new ProductHotFragment();
        fragmentList.add(productListFragment);
        fragmentList.add(productRecommendFragment);
        fragmentList.add(productHotFragment);
    }

    @Override
    protected void initTitle() {
        titleBack.setVisibility(View.INVISIBLE);
        titleSetting.setVisibility(View.INVISIBLE);
        titleTv.setText("我要投资");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_invest;
    }


}
