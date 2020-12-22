package cn.ymex.kitx.core.app;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ViewModelFragment extends Fragment implements ViewConstraint {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutID = onCreateView(savedInstanceState);
        if (layoutID != 0) {
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
        List<ViewModel> vms = getViewModels();
        for (ViewModel vm : vms) {
            setCommonObserver(vm);
        }
        observeViewModel();
    }

    @Override
    public void setCommonObserver(ViewModel viewModel) {

    }

    @Override
    public void observeViewModel() {

    }

    @Override
    public List<ViewModel> getViewModels() {
        return new ArrayList<>();
    }

}
