package com.example.aa.ui.bill;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.example.aa.R;
import com.example.aa.database.BillSQLiteAdapter;
import com.example.aa.database.UsersSQLiteAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class BillViewModel extends AndroidViewModel {

    private BillSQLiteAdapter billSQLiteAdapter;
    private UsersSQLiteAdapter usersSQLiteAdapter;
    private FunDapter billAdapter;
    private FunDapter computeAdapter;
    private Context context;
    private BindDictionary<BillItem> billDict;
    private BindDictionary<GiveItem> giveDict;

    void deleteAll(){
        Toast.makeText(context, "成功删除 " + billSQLiteAdapter.deleteAll() + " 条数据", Toast.LENGTH_LONG).show();
        updateBillAdapter();
    }

    public BillViewModel(Application application) {
        super(application);
        context = getApplication().getApplicationContext();
        billSQLiteAdapter = new BillSQLiteAdapter(context);
        usersSQLiteAdapter = new UsersSQLiteAdapter(context);

        billDict = new BindDictionary<>();
        billDict.addStringField(R.id.spend_name_item, new StringExtractor<BillItem>() {
            @Override
            public String getStringValue(BillItem billItem, int position) {
                return billItem.getSpendName();
            }
        });
        billDict.addStringField(R.id.spend_money_item, new StringExtractor<BillItem>() {
            @Override
            public String getStringValue(BillItem billItem, int position) {
                return String.format(Locale.CHINA, "￥%+.2f", billItem.getSpendMoney());
            }
        });

        updateBillAdapter();

        giveDict = new BindDictionary<>();
        giveDict.addStringField(R.id.give_name_from_item, new StringExtractor<GiveItem>() {
            @Override
            public String getStringValue(GiveItem giveItem, int position) {
                return giveItem.getNameFrom();
            }
        });
        giveDict.addStringField(R.id.give_name_to_item, new StringExtractor<GiveItem>() {
            @Override
            public String getStringValue(GiveItem giveItem, int position) {
                return giveItem.getNameTo();
            }
        });
        giveDict.addStringField(R.id.give_money_item, new StringExtractor<GiveItem>() {
            @Override
            public String getStringValue(GiveItem giveItem, int position) {
                return String.format(Locale.CHINA, "￥%.2f", giveItem.getGiveMoney());
            }
        });
    }

    void addBillItem(String name, double money, String date, String remark) {
        long id = billSQLiteAdapter.insertData(name, money, date, remark);
        if (id <= 0) {
            Toast.makeText(context, "添加失败", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "添加成功", Toast.LENGTH_LONG).show();
        }
        updateBillAdapter();
    }

    private void updateBillAdapter() {
        ArrayList<BillItem> billItems = billSQLiteAdapter.getData();
        billAdapter = new FunDapter(getApplication(), billItems, R.layout.bill_item, billDict);
    }

    ArrayList<String> getNameList(){
        return usersSQLiteAdapter.getData();
    }

    FunDapter getBillAdapter() {
        if (billAdapter != null) {
            return billAdapter;
        } else {
            updateBillAdapter();
            return billAdapter;
        }
    }

    FunDapter getComputeAdapter() {
        if (computeAdapter != null) {
            return computeAdapter;
        }
        else{
            ArrayList<BillItem> _billItems = billSQLiteAdapter.getData();
            ArrayList<String> allNames = usersSQLiteAdapter.getData();
            ArrayList<BillItem> billItems = new ArrayList<>();
            ArrayList<String> userNames = new ArrayList<>();

            for (BillItem billItem: _billItems){
                final String spendName = billItem.getSpendName();
                if (userNames.contains(spendName)){
                    for (BillItem billItem1: billItems){
                        if (billItem1.getSpendName().equals(spendName)){
                            billItem1.addSpendMoney(billItem.getSpendMoney());
                            break;
                        }
                    }
                }
                else{
                    userNames.add(spendName);
                    billItems.add(new BillItem(spendName, billItem.getSpendMoney(), "", ""));
                }
            }

            for (String userName: allNames){
                if (!userNames.contains(userName)){
                    billItems.add(new BillItem(userName, 0., "", ""));
                    userNames.add(userName);
                }
            }

            ArrayList<GiveItem> giveItems = new ArrayList<>();

            int number = billItems.size();
            Collections.sort(billItems);
            double average = 0;
            for(BillItem billItem : billItems) {
                average += billItem.getSpendMoney();
            }
            average /= number;
            for (int i = 0; i < number; ++i){
                for (int j = number - 1; j > i; --j){
                    if (billItems.get(i).getSpendMoney() == average){
                        break;
                    }
                    if (billItems.get(j).getSpendMoney() > average){
                        double giveMoney = Math.min(average - billItems.get(i).getSpendMoney(), billItems.get(j).getSpendMoney() - average);
                        giveItems.add(new GiveItem(billItems.get(i).getSpendName(), billItems.get(j).getSpendName(), giveMoney));
                        billItems.get(i).addSpendMoney(giveMoney);
                        billItems.get(j).addSpendMoney(-giveMoney);
                    }
                }
            }

            computeAdapter = new FunDapter(getApplication(), giveItems, R.layout.give_item, giveDict);
            return computeAdapter;
        }
    }

}