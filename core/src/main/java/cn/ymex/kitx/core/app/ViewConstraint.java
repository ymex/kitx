package cn.ymex.kitx.core.app;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import java.util.List;

public interface ViewConstraint {
    /**
     * layout view
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * layout view
     *
     * @param savedInstanceState
     * @return
     */
    int onCreateView(@Nullable Bundle savedInstanceState);

    /**
     * after create layout view
     *
     * @param view
     * @param savedInstanceState
     */
    void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState);


    /**
     * root view
     *
     * @return
     */
    View getView();


    List<ViewModel> getViewModels();

    /**
     * 设置共用的Observer
     * @param viewModel
     */
    void setCommonObserver(ViewModel viewModel);

    /**
     * ViewModel observe
     */
    void observeViewModel();


    void beforeSetContentView( Bundle savedInstanceState);
}
