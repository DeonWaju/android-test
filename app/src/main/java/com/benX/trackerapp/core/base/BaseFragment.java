package com.benX.trackerapp.core.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.benX.trackerapp.util.ProgressUtil;

public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment {


    private View viewRoot;
    private T viewDataBinding;

    protected Bundle bundle;
    protected int sourceNavId;

    protected Context context;
    private Toolbar toolbar;

    private ProgressUtil progressUtil;

    /**
     * @return layout id
     */
    public abstract
    @LayoutRes
    int getLayoutId();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        viewRoot = viewDataBinding.getRoot();
        return viewRoot;
    }
}
