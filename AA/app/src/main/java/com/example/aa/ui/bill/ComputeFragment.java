package com.example.aa.ui.bill;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.aa.R;

public class ComputeFragment extends Fragment {

    private BillViewModel billViewModel;
    private ListView computeListView;
    private final Fragment fragment = this;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_compute, container, false);
        billViewModel =
                ViewModelProviders.of(this).get(BillViewModel.class);
        computeListView = root.findViewById(R.id.compute_list);
        Button ensureCloseBillButton = root.findViewById(R.id.ensure_close_bill_btn);
        ensureCloseBillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billViewModel.deleteAll();
                Navigation.findNavController(v).navigate(R.id.nav_bill);
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        computeListView.setAdapter(billViewModel.getComputeAdapter());
    }

}
