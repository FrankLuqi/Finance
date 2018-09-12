package com.example.finance.Fragment;

import android.app.Fragment;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.finance.R;
import com.example.finance.UI.RoundProgress;
import com.example.finance.bean.News;
import com.example.finance.utils.UIUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018.9.4.
 */

public class ProductListFragment extends Fragment {
    @BindView(R.id.lv)
    ListView lv;
    Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = UIUtils.getXmlView(R.layout.fragment_product_list);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        List<News> newsList = new ArrayList<>();

        News news = new News();
        news.Title = "Chrome 已称王，IE 今何在？";
        news.Content = "谷歌的亲儿子 Chrome 推出已十年，依然制霸着全球的浏览器市场——而这种统治地位也让人们越来越担心它会成为新时代的 IE。";
        news.ImgUrl = "https://ss.csdn.net/p?https://mmbiz.qpic.cn/mmbiz_png/Pn4Sm0RsAug6drE1vAicNtogq2CVfmbkA76l0H0m4oxOX2DhBpX3VqpZEQvJkOibu0ISBibZIr9U7LEsA1gHyToow/640?wx_fmt=png";
        news.percent = 65;
        newsList.add(news);
        news = new News();
        news.Title = "快播科技正式破产，王欣：我放下过天地，却从未放下过你";
        news.Content = "昨天，快播这款有着昔日“宅男神器”之称的、国内自主研发的基于准视频点播（QVOD）内核、多功能、个性化的播放器软件的背后公司深圳市快播科技有限公司，终于还是告别网民，即将被埋进滚滚互联网史的烟尘之中。";
        news.ImgUrl = "https://ss.csdn.net/p?https://mmbiz.qpic.cn/mmbiz/Pn4Sm0RsAug6drE1vAicNtogq2CVfmbkAHm7pv5GChwIuNfiau5FdC7X9dEKibcRvLPicVJFf9CcFVfEPV0GpPPicdA/640?wx_fmt=other";
        news.percent = 86;
        newsList.add(news);
        news = new News();
        news.ImgUrl = "http://pic2.nipic.com/20090414/220113_034817027_2.jpg";
        newsList.add(news);
        news = new News();
        news.Title = "Google推出数据集搜索！百度，你怎么看？";
        news.Content = "继 Google Scholar（Google 学术搜索）之后，Google 又为科研工作者推出了一款重磅产品—— Google Dataset Search（Google 数据集搜索）。";
        news.ImgUrl = "https://img-blog.csdn.net/20180906160241316?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2RRQ0ZLeVFEWFltM0Y4ckIw/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70";
        news.percent = 95;
        newsList.add(news);
        lv.setAdapter(new MyAdapter(newsList));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private class MyAdapter extends BaseAdapter {

        List<News> newsList = new ArrayList<>();

        MyAdapter(List<News> newsList) {
            this.newsList = newsList;
        }

        @Override
        public int getCount() {
            return newsList.size();
        }

        @Override
        public Object getItem(int i) {
            return newsList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            if (position < 2)
                return 1;
            else if (position == 2)
                return 2;
            else
                return 1;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ViewHolder1 viewHolder1 = null;
            ViewHolder2 viewHolder2 = null;
            int type = getItemViewType(i);
            //当类型为1时，返回新闻列表
            if (convertView == null) {

                if (type == 1) {
                    convertView = View.inflate(getContext(), R.layout.item_product_list1, null);
                    viewHolder1 = new ViewHolder1(convertView);
                    convertView.setTag(viewHolder1);
                }
                //否则返回广告
                else {
                    convertView = View.inflate(getContext(), R.layout.item_product_list2, null);
                    viewHolder2 = new ViewHolder2(convertView);
                    convertView.setTag(viewHolder2);
                }
            }
            else
            {
                if (type==1)
                    viewHolder1 = (ViewHolder1)convertView.getTag();
                else
                    viewHolder2 = (ViewHolder2)convertView.getTag();
            }
            if (type==1)
            {
                viewHolder1.newsTitle.setText(newsList.get(i).Title);
                viewHolder1.newsContent.setText(newsList.get(i).Content);
                viewHolder1.newsImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Picasso.with(getContext()).load(newsList.get(i).ImgUrl).into(viewHolder1.newsImg);
                viewHolder1.newsProgress.setProgress(newsList.get(i).percent);
            }
            else
            {
                Picasso.with(getContext()).load("https://tse4-mm.cn.bing.net/th?id=OIP.j1tNvWzqGJXJcSO-uvgcFgHaFj&pid=Api").into(viewHolder2.advertiseImg);
            }
            return convertView;
        }


    }

    static class ViewHolder1 {
        @BindView(R.id.newsImg)
        ImageView newsImg;
        @BindView(R.id.newsTitle)
        TextView newsTitle;
        @BindView(R.id.newsContent)
        TextView newsContent;
        @BindView(R.id.newsProgress)
        RoundProgress newsProgress;


        ViewHolder1(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolder2 {
        @BindView(R.id.advertiseImg)
        ImageView advertiseImg;

        ViewHolder2(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
