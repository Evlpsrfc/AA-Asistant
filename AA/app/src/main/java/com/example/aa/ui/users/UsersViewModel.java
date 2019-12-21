package com.example.aa.ui.users;


import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.example.aa.R;
import com.example.aa.database.UsersSQLiteAdapter;

import java.util.ArrayList;

public class UsersViewModel extends AndroidViewModel {

    private UsersSQLiteAdapter usersSQLiteAdapter;
    private BindDictionary<String> usersDict;
    private FunDapter usersAdapter;
    private Context context;

    private void updateUsersAdapter(){
        ArrayList<String> usersItems = usersSQLiteAdapter.getData();
        usersAdapter = new FunDapter(getApplication(), usersItems, R.layout.users_item, usersDict);
    }

    public UsersViewModel(Application application) {
        super(application);
        context = getApplication().getApplicationContext();
        usersSQLiteAdapter = new UsersSQLiteAdapter(context);
        usersDict = new BindDictionary<>();
        usersDict.addStringField(R.id.users_name_item, new StringExtractor<String>() {
            @Override
            public String getStringValue(String usersItem, int position) {
                return usersItem;
            }
        });
        updateUsersAdapter();
    }

    public void addUserItem(String name){
        long id = usersSQLiteAdapter.insertData(name);
        if (id <= 0) {
            Toast.makeText(context, "添加失败", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "添加成功", Toast.LENGTH_LONG).show();
        }
        updateUsersAdapter();
    }

    FunDapter getUsersAdapter() {
        if (usersAdapter != null) {
            return usersAdapter;
        }
        else{
            updateUsersAdapter();
            return usersAdapter;
        }
    }

    void deleteAll(){
        Toast.makeText(context, "成功删除 " + usersSQLiteAdapter.deleteAll() + " 条数据", Toast.LENGTH_LONG).show();
        updateUsersAdapter();
    }
}