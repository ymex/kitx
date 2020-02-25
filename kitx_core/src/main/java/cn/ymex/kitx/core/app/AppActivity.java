package cn.ymex.kitx.core.app;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AppActivity extends AppCompatActivity implements UiView {
    private View _rootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = onCreateView(savedInstanceState);
        if (layoutId != 0) {
            _rootView = View.inflate(this, layoutId, null);
            setContentView(_rootView);
        } else {
            _rootView = onCreateView(LayoutInflater.from(this), null, savedInstanceState);
            if (_rootView != null) {
                setContentView(_rootView);
            }
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        onViewCreated(_rootView,savedInstanceState);
//        View view = this.getWindow().getDecorView().findViewById(android.R.id.content);
//        if (view instanceof ViewGroup && ((ViewGroup) view).getChildCount() > 0) {
//            onViewCreated(((ViewGroup) view).getChildAt(0), savedInstanceState);
//        } else {
//            onViewCreated(view, savedInstanceState);
//        }
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
        return _rootView;
    }
}
