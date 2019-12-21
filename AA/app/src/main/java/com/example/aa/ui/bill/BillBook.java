package com.example.aa.ui.bill;

import java.util.ArrayList;

public class BillBook {
    private ArrayList<BillItem> billItems;

    public BillBook() {
        billItems = new ArrayList<>();
    }

    public BillBook(ArrayList<BillItem> billItems) {
        this.billItems = billItems;
    }
}
