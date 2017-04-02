package com.example.ethantien.stockview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewCompanies extends AppCompatActivity {

    ArrayList<String> companies;
    ArrayAdapter<String> adapter;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_companies);

        companies = new ArrayList<>();
        companies.add("Apple (AAPL)");
        companies.add("Disney (DIS)");
        companies.add("IBM (IBM)");
        companies.add("Microsoft (MSFT)");
        companies.add("WalMart (WMT)");
        companies.add("Nike (NKE)");
        companies.add("Verizon (VZ)");
        companies.add("Visa (V)");
        companies.add("McDonald's (MCD)");
        companies.add("Intel (INTC)");

        adapter = new ArrayAdapter<>(ViewCompanies.this, android.R.layout.simple_list_item_1, companies);
        list = (ListView) findViewById(R.id.lisp);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                String tmp = "";
                switch (companies.get(position)) {
                    case ("Apple (AAPL)"):
                        tmp = "Apple";
                        break;
                    case ("Disney (DIS)"):
                        tmp = "Disney";
                        break;
                    case ("IBM (IBM)"):
                        tmp = "IBM";
                        break;
                    case ("Microsoft (MSFT)"):
                        tmp = "Microsoft";
                        break;
                    case ("WalMart (WMT)"):
                        tmp = "WalMart";
                        break;
                    case ("Nike (NKE)"):
                        tmp = "Nike";
                        break;
                    case ("Verizon (VZ)"):
                        tmp = "Verizon";
                        break;
                    case ("Visa (V)"):
                        tmp = "Visa";
                        break;
                    case ("McDonald's (MCD)"):
                        tmp = "McDonalds";
                        break;
                    case ("Intel (INTC)"):
                        tmp = "Intel";
                        break;

                }
                vars.getInstance().setCurCompany(tmp);
                startActivity(new Intent(ViewCompanies.this, viewGraphs.class));
            }
        });

    }
}
