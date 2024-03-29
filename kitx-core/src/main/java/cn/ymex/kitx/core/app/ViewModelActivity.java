package cn.ymex.kitx.core.app;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ViewModelActivity extends AppCompatActivity implements ViewConstraint {
    private View rootView ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetContentView(savedInstanceState);
        int layoutId = onCreateView(savedInstanceState);
        if (layoutId != 0) {
            rootView  = LayoutInflater.from(this).inflate(layoutId, null);
            setContentView(rootView);
        } else {
            rootView = onCreateView(LayoutInflater.from(this), null, savedInstanceState);
            if (rootView != null) {
                setContentView(rootView);
            }
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (rootView != null) {
            onViewCreated(rootView,savedInstanceState);
        }else {
            View view = this.getWindow().getDecorView().findViewById(android.R.id.content);
            if (view instanceof ViewGroup && ((ViewGroup) view).getChildCount() > 0) {
                onViewCreated(((ViewGroup) view).getChildAt(0), savedInstanceState);
            } else {
                onViewCreated(view, savedInstanceState);
            }
        }
        observeViewModel();
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    @Override
    public int onCreateView(@Nullable Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public View getView() {
        if (rootView != null) {
            return rootView;
        }
        View view = this.getWindow().getDecorView().findViewById(android.R.id.content);
        if (view instanceof ViewGroup && ((ViewGroup) view).getChildCount() > 0) {
            return ((ViewGroup) view).getChildAt(0);
        }
        return view;
    }

    @Override
    public void observeViewModel() {

    }


    @Override
    public void beforeSetContentView(Bundle savedInstanceState) {

    }

}
