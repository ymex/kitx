package cn.ymex.kitx.widget.nav;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class StateNavHostFragment extends NavHostFragment {
    @Override
    protected void onCreateNavController(@NonNull NavController navController) {
        super.onCreateNavController(navController);
        StateFragmentNavigator navigator = new StateFragmentNavigator(requireContext(), getChildFragmentManager(), getId());
        navController.getNavigatorProvider().addNavigator(navigator);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                //应用进行后台， 非销毁。
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
            }
        });
    }
}
