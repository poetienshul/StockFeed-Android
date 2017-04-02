package com.example.ethantien.stockview;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.R.color.black;
import static android.R.color.holo_blue_dark;
import static android.R.color.white;

public class viewGraphs extends AppCompatActivity {
    LineChart chart;
    LineChart chart2;
    TextView lol;

    @Override
    protected void
    onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_graphs);
        lol = (TextView) findViewById(R.id.textView2);
        lol.setText("<Loading>");
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<Entry> entries = new ArrayList<Entry>();

                chart = (LineChart) findViewById(R.id.chart);

                for (DataSnapshot ele : dataSnapshot.child(vars.getInstance().getCurCompany()).getChildren()) {
                    String tmp = ele.child("date").getValue().toString();
                    String meow = tmp.substring(2, tmp.length() - 3);
                    entries.add(new Entry(Float.parseFloat(meow),
                            Float.parseFloat(ele.child("close").getValue().toString())));

                }
                LineDataSet set = new LineDataSet(entries, ""); // add entries to dataset
                set.setLineWidth(2f);
                set.setValueTextColor(white);
                set.setAxisDependency(YAxis.AxisDependency.LEFT);
                set.setColor(ColorTemplate.getHoloBlue());
                set.setCircleColor(Color.BLACK);
//                set.setCircleRadius(4f);
//                set.setFillAlpha(65);
//                set.setFillColor(ColorTemplate.getHoloBlue());
//                set.setHighLightColor(Color.rgb(244, 117, 117));
                set.setValueTextColor(Color.WHITE);
                set.setValueTextSize(9f);
                set.setDrawValues(false);
                chart.getDescription().setText("");
                LineData lineData = new LineData(set);

                chart.invalidate();
                set.setFillFormatter(new IFillFormatter() {
                    @Override
                    public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                        return chart.getAxisLeft().getAxisMinimum();
                    }
                });

                // no description text
                chart.getDescription().setEnabled(false);

                //((LineDataSet) dataSet.get(0)).enableDashedLine(10, 10, 0);

                chart.setData(lineData);
                List<Entry> entries2 = new ArrayList<Entry>();
                chart2 = (LineChart) findViewById(R.id.chart2);

                double nytAvg = 0.0;
                double bingAvg = 0.0;
                for (DataSnapshot ele : dataSnapshot.child("BingData").getChildren()) {
                    if (vars.getInstance().getCurCompany().equals(ele.child("Company").getValue().toString())) {
                        bingAvg = Double.parseDouble(ele.child("Sentiment").getValue().toString());
                    }
                }
                for (DataSnapshot ele : dataSnapshot.child("NYTFinal").getChildren()) {
                    if (vars.getInstance().getCurCompany().equals(ele.child("Company").getValue().toString())) {
                        nytAvg = Double.parseDouble(ele.child("Sentiment").getValue().toString());
                    }
                }
                //double modifier = (bingAvg + nytAvg) / 2;
                for (DataSnapshot ele : dataSnapshot.child("TwitterData").getChildren()) {
                    if (ele.child("Company").getValue().toString().equals(vars.getInstance().getCurCompany())) {
                        float value = (float)(Float.parseFloat(ele.child("Sentiment").getValue().toString()) * .4 +
                                bingAvg * .4 + nytAvg * .2);
                        entries2.add(new Entry(
                                Float.parseFloat(ele.child("Date").getValue().toString()), value));
                    }

                }
                LineDataSet set2 = new LineDataSet(entries2, ""); // add entries to dataset
                set2.setLineWidth(2f);
                set2.setValueTextColor(white);
                set2.setAxisDependency(YAxis.AxisDependency.LEFT);
                set2.setColor(ColorTemplate.getHoloBlue());
                set2.setCircleColor(Color.BLACK);
//                set.setCircleRadius(4f);
//                set.setFillAlpha(65);
//                set.setFillColor(ColorTemplate.getHoloBlue());
//                set.setHighLightColor(Color.rgb(244, 117, 117));
                set2.setValueTextColor(Color.WHITE);
                set2.setValueTextSize(9f);
                set2.setDrawValues(false);
                chart2.getDescription().setText("");
                LineData lineData2 = new LineData(set2);

                chart2.invalidate();

                // no description text
                chart2.getDescription().setEnabled(false);

                //((LineDataSet) dataSet.get(0)).enableDashedLine(10, 10, 0);

                chart2.setData(lineData2);
                lol.setText("Predictive Analysis");


                String str = dataSnapshot.child("MLData").child(vars.getInstance().getCurCompany()).getValue().toString();
                Integer percent = (int)(Double.parseDouble(str) * 100.0);
                TextView ml = (TextView)findViewById(R.id.MLAnalysis);
                TextView ml2 = (TextView) findViewById(R.id.mlPt2);
                ml2.setText("this stock will be NEUTRAL");
                ml.setText("There is a " + percent + "% chance today");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        Button back = (Button) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(viewGraphs.this, ViewCompanies.class));
            }
        });

    }
}
