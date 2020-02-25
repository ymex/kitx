package cn.ymex.kitx.core.app;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AppFragment extends Fragment implements UiView {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutID = onCreateView(savedInstanceState);
        if ( layoutID != 0) {
            return inflater.inflate(layoutID, null, false);
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public int onCreateView(@Nullable Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
