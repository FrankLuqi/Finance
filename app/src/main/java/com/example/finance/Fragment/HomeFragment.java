package com.example.finance.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finance.R;
import com.example.finance.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018.8.9.
 */

public class HomeFragment extends Fragment {
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_setting)
    ImageView titleSetting;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //View view = View.inflate(container.getContext(), R.layout.fragment_home, null);
        View view = UIUtils.getXmlView(R.layout.fragment_home);//这里我们使用工具类中封装的方法
        unbinder = ButterKnife.bind(this, view);
        titleBack.setVisibility(View.INVISIBLE);
        titleSetting.setVisibility(View.INVISIBLE);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
