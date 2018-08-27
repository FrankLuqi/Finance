package com.example.finance.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finance.R;

/**
 * Created by Administrator on 2018.8.9.
 */

public class InvestFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(container.getContext(), R.layout.fragment_invest,null);
        return view;
    }
}
