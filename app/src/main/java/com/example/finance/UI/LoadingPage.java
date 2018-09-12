package com.example.finance.UI;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.finance.R;
import com.example.finance.utils.UIUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

/**
 * 通过建立loadingPage对一个界面的不同状态进行管理
 * 加载状态，请求数据出错状态，请求数据为空的状态和请求成功的状态
 * Created by Administrator on 2018.9.3.
 */

public abstract class LoadingPage extends FrameLayout{

    private Context mContext;

    //正在加载状态
    private static final int PAGE_LOADING_STATE = 1;
    //出错状态
    private static final int PAGE_ERROR_STATE = 2;
    //请求到的数据为空
    private static final int PAGE_EMPTY_STATE = 3;
    //请求成功
    private static final int PAGE_SUCCESS_STATE = 4;
    //当前状态
    private int PAGE_CURRENT_STATE = 1;

    private View loadingView;

    private View errorView;

    private View emptyView;

    private View successView;

    private LayoutParams lp;

    private ResultState resultState;

    private AsyncHttpClient client = new AsyncHttpClient();

    public LoadingPage(@NonNull Context context) {
        this(context,null);
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context,null,0);
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        //状态初始化
        init();
    }

    /**
     * 对状态进行初始化
     */
    private void init() {
        //设置layout布局信息
        lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        //初始化三种布局
        if (loadingView==null)
        {
            //将layout转化为view
            loadingView = UIUtils.getXmlView(R.layout.page_loading);
            //添加view
            addView(loadingView);
        }
        if (errorView==null)
        {
            errorView = UIUtils.getXmlView(R.layout.page_error);
            addView(errorView);
        }
        if (emptyView==null)
        {
            emptyView = UIUtils.getXmlView(R.layout.page_empty);
            addView(emptyView);
        }
        //在线程安全的前提下展示对应的界面
        showSafePage();
    }

    //在线程安全的前提下展示对应界面
    private void showSafePage() {
        UIUtils.RunOnUIThread(new Runnable() {
            @Override
            public void run() {
                ShowPage();
            }
        });


    }

    private void ShowPage() {

        loadingView.setVisibility(PAGE_CURRENT_STATE==PAGE_LOADING_STATE ? View.VISIBLE:View.GONE);
        errorView.setVisibility(PAGE_CURRENT_STATE == PAGE_ERROR_STATE ? View.VISIBLE : View.GONE);
        emptyView.setVisibility(PAGE_CURRENT_STATE == PAGE_EMPTY_STATE ? View.VISIBLE : View.GONE);
        //因为不同的界面调用的成功界面不相同，所以这里通过添加抽象方法，不同的界面单独重写方法来获取传入的layout
        if (successView==null)
        {
            //这里之所以没有使用UIUtiles中的getxmlView方法
            //因为UIUtils中的context是application的context
            //而loadingPage的mcontext是从container中获得的，是当前activity中的context
            //因为要设置theme，而application是没有主题的，设置application的主题本质上是设置application中的
            //所有activity的主题
            successView = View.inflate(mContext,LayoutId(),null);
            addView(successView,lp);
        }
        successView.setVisibility(PAGE_CURRENT_STATE == PAGE_SUCCESS_STATE?View.VISIBLE:View.GONE);

    }

    //具体界面实现该抽象方法，设置不同的成功界面
    public abstract int LayoutId();


    /**
     * 当需要调用网络请求时，调用LoadingPage的show方法
     */
    public void show()
    {
        //当前状态初始化
        PAGE_CURRENT_STATE = PAGE_LOADING_STATE;

        //处理那些不需要请求url来获取数据的界面
        //即此时url为空
        String url = url();
        if (TextUtils.isEmpty(url))
        {
            resultState = ResultState.SUCCESS;
            resultState.setContent("");
            loadPage();
        }
        //处理那些需要url请求的情况
        else
        {
            client.get(url, params(), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    //请求到的数据为空时
                    if (TextUtils.isEmpty(new String(bytes)))
                    {
                        resultState = ResultState.EMPTY;
                        resultState.setContent("");
                    }
                    //请求成功时
                    else
                    {
                        resultState = ResultState.SUCCESS;
                        resultState.setContent(new String(bytes));
                    }
                    loadPage();
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    resultState = ResultState.ERROR;
                    resultState.setContent("");
                    loadPage();
                }
            });
        }
    }



    //根据网络请求的结果更改当前的状态
    private void loadPage() {
        switch (resultState) {
            case ERROR:
                //当前状态设置为2，显示错误界面
                PAGE_CURRENT_STATE = 2;
                break;
            case EMPTY:
                //当前状态设置为3，显示空界面
                PAGE_CURRENT_STATE = 3;
                break;
            case SUCCESS:
                //当前状态设置为4，显示成功界面
                PAGE_CURRENT_STATE = 4;
                break;
        }
        showSafePage();
        if (PAGE_CURRENT_STATE == 4) {
            OnSuccess(resultState, successView);
        }
    }

    //网络数据请求成功后，将成功请求到的数据和成功后的界面传入onSuccess方法，通过重写该方法对数据和界面进行具体操作
    protected abstract void OnSuccess(ResultState resultState, View successView);

    //通过不同界面实现该抽象方法访问不同url
    protected abstract String url();

    //通过不同的界面实现该抽象方法传入不同的网络请求参数
    protected abstract RequestParams params();

    //建立枚举类来管理网络请求结果状态
    public enum ResultState
    {
        ERROR(2),EMPTY(3),SUCCESS(4);


        ResultState(int state)
        {
            this.state = state;
        }

        private String content;
        private int state;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }



        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }



    }
}
