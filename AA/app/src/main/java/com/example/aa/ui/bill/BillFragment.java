package com.example.aa.ui.bill;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.aa.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BillFragment extends Fragment {

    private BillViewModel billViewModel;

    public ListView getBillListView() {
        return billListView;
    }

    private ListView billListView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        billViewModel =
                ViewModelProviders.of(this).get(BillViewModel.class);
        View root = inflater.inflate(R.layout.fragment_bill, container, false);
        billListView = root.findViewById(R.id.bill_list);

        Button addBillBtn = root.findViewById(R.id.add_bill_btn);
        addBillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View promptsBillView = layoutInflater.inflate(R.layout.prompts_bill, null);
                final EditText nameInput = promptsBillView.findViewById(R.id.name_input);
                final EditText moneyInput = promptsBillView.findViewById(R.id.money_input);
                final EditText remarkInput = promptsBillView.findViewById(R.id.remark_input);

                Spinner nameSpinner = promptsBillView.findViewById(R.id.name_spinner);
                nameSpinner.setPrompt("选择姓名");
                ArrayAdapter<String> nameAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, billViewModel.getNameList());
                nameSpinner.setAdapter(nameAdapter);
                nameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String name = parent.getItemAtPosition(position).toString();
                        nameInput.setText(name);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setView(promptsBillView);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        String spendNameInput = nameInput.getText().toString();
                                        String spendMoneyInput = moneyInput.getText().toString();
                                        String spendRemarkInput = remarkInput.getText().toString();
                                        if (spendNameInput.length() == 0){
                                            Toast.makeText(getActivity(), "姓名不可为空！", Toast.LENGTH_LONG).show();
                                        }
                                        else{
                                            if (spendMoneyInput.length() == 0){
                                                Toast.makeText(getActivity(), "金额不可为空！", Toast.LENGTH_LONG).show();
                                            }
                                            else {
                                                try {
                                                    double spendMoney = Double.parseDouble(spendMoneyInput);
                                                    @SuppressLint("SimpleDateFormat")
                                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss yyyy/MM/dd/");
                                                    Date date = new Date(System.currentTimeMillis());
                                                    billViewModel.addBillItem(spendNameInput, spendMoney, simpleDateFormat.format(date), spendRemarkInput);
                                                    billListView.setAdapter(billViewModel.getBillAdapter());
                                                } catch (Exception e) {
                                                    Toast.makeText(getActivity(), "数据非法！", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        }
                                    }
                                })
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        Button closeButton = root.findViewById(R.id.close_bill_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_compute);

            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        billListView.setAdapter(billViewModel.getBillAdapter());
    }
}