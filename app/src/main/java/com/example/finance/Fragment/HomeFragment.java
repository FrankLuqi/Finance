package com.example.finance.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.finance.R;
import com.example.finance.UI.MyScrollview;
import com.example.finance.UI.RoundProgress;
import com.example.finance.bean.HomeTopImage;
import com.example.finance.common.AppNetConfig;
import com.example.finance.utils.UIUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import org.apache.http.Header;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018.8.9.
 */

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    Unbinder unbinder;
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_setting)
    ImageView titleSetting;
    @BindView(R.id.vp_barner)
    ViewPager vpBarner;
    @BindView(R.id.circle_barner)
    CirclePageIndicator circleBarner;
    @BindView(R.id.textView1)
    TextView textView1;
    @BindView(R.id.p_yearlv)
    TextView pYearlv;
    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.myscrollview)
    MyScrollview myscrollview;


    AsyncHttpClient client = new AsyncHttpClient();

    List<HomeTopImage> homeTopImageList;
    @BindView(R.id.p_progresss)
    RoundProgress pProgresss;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //View view = View.inflate(container.getContext(), R.layout.fragment_home, null);
        View view = UIUtils.getXmlView(R.layout.fragment_home);//这里我们使用工具类中封装的方法将xml布局转化成view
        unbinder = ButterKnife.bind(this, view);

        initTitle();

        initData();

        return view;
    }

    private void initTitle() {
        titleBack.setVisibility(View.INVISIBLE);
        titleSetting.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void initData() {

        client.get(AppNetConfig.getInfoUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.e(TAG, "onFailure: " + throwable.getMessage() + "\n" + AppNetConfig.getInfoUrl);
            }

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                JSONObject jsonObject = JSON.parseObject(new String(bytes));
                String imageArry = jsonObject.getString("image");
                homeTopImageList = JSON.parseArray(imageArry, HomeTopImage.class);
                vpBarner.setAdapter(new MyAdapter());
                //把viewpager传给指示器
                circleBarner.setViewPager(vpBarner);

                //这里我们服务器没有回传progress数据，先用85代替一下
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int tempProgress = 0;
                        try {
                            while (tempProgress <= 85) {
                                pProgresss.setProgress(tempProgress);
                                tempProgress++;
                                Thread.sleep(100);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    private class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return homeTopImageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            String imageUrl = homeTopImageList.get(position).url;
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Picasso.with(getActivity()).load(imageUrl).into(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
