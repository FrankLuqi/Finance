package com.example.finance.common;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finance.UI.LoadingPage;
import com.example.finance.utils.UIUtils;
import com.loopj.android.http.RequestParams;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 建立类BaseFragment 其他Fragment都继承自该类
 * Created by Administrator on 2018.9.3.
 */

public abstract class BaseFragment extends Fragment{

    private LoadingPage loadingPage;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        loadingPage = new LoadingPage(container.getContext()) {
            
            @Override
            public int LayoutId() {
                return getLayoutId();
            }
            //当请求数据成功后调用
            @Override
            protected void OnSuccess(ResultState resultState, View successView) {
                unbinder = ButterKnife.bind(BaseFragment.this,successView);
                initTitle();
                initData(resultState.getContent());
            }

            @Override
            protected String url() {
                return getUrl();
            }

            @Override
            protected RequestParams params() {
                return getParams();
            }
        };

        return loadingPage;
    }

    protected abstract RequestParams getParams();

    protected abstract String getUrl();

    protected abstract void initData(String content);

    protected abstract void initTitle();

    protected abstract int getLayoutId();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //调用方法请求url
                loadingPage.show();
            }
        },1000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
