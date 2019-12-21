package com.example.aa.ui.users;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.aa.R;


public class UsersFragment extends Fragment {

    private UsersViewModel usersViewModel;
    private ListView usersListView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        usersViewModel =
                ViewModelProviders.of(this).get(UsersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_users, container, false);
        usersListView = root.findViewById(R.id.users_list);

        Button addUserBtn = root.findViewById(R.id.add_user_btn);
        addUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View promptsUserView = layoutInflater.inflate(R.layout.prompts_users, null);
                final EditText nameInput = promptsUserView.findViewById(R.id.name_input);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setView(promptsUserView);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        String spendNameInput = nameInput.getText().toString();
                                        if (spendNameInput.length() == 0) {
                                            Toast.makeText(getActivity(), "姓名不可为空！", Toast.LENGTH_LONG).show();
                                        } else {
                                            usersViewModel.addUserItem(spendNameInput);
                                            usersListView.setAdapter(usersViewModel.getUsersAdapter());
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

        Button clearButton = root.findViewById(R.id.clear_users_btn);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usersViewModel.deleteAll();
                usersListView.setAdapter(usersViewModel.getUsersAdapter());
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        usersListView.setAdapter(usersViewModel.getUsersAdapter());
    }
}