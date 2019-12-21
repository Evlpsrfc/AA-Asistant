package com.example.aa.ui.individuation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.aa.R;

public class IndividuationFragment extends Fragment {

    private IndividuationViewModel individuationViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        individuationViewModel =
                ViewModelProviders.of(this).get(IndividuationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_individuation, container, false);
        final TextView textView = root.findViewById(R.id.text_individuation);
        individuationViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}