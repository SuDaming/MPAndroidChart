package com.zhuanghongji.mpchartexample.realm;

import android.os.Bundle;
import android.view.WindowManager;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.realm.implementation.RealmLineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.zhuanghongji.mpchartexample.R;
import com.zhuanghongji.mpchartexample.custom.RealmDemoData;

import java.util.ArrayList;

import io.realm.RealmResults;

/**
 * Created by Philipp Jahoda on 21/10/15.
 */
public class RealmDatabaseActivityLine extends RealmBaseActivity {

    private LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mChart = (LineChart) findViewById(R.id.chart1);
        setup(mChart);

        mChart.getAxisLeft().setAxisMaximum(150f);
        mChart.getAxisLeft().setAxisMinimum(0f);
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getXAxis().setDrawGridLines(false);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_linechart_noseekbar;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void onResume() {
        super.onResume(); // setup realm

        // write some demo-data into the realm.io database
        writeToDB(40);

        // add data to the chart
        setData();
    }

    private void setData() {

        RealmResults<RealmDemoData> result = mRealm.where(RealmDemoData.class).findAll();

        RealmLineDataSet<RealmDemoData> set = new RealmLineDataSet<RealmDemoData>(result, "xValue", "yValue");
        set.setDrawCubic(false);
        set.setLabel("Realm LineDataSet");
        set.setDrawCircleHole(false);
        set.setColor(ColorTemplate.rgb("#FF5722"));
        set.setCircleColor(ColorTemplate.rgb("#FF5722"));
        set.setLineWidth(1.8f);
//        set.setCircleSize(3.6f); // TODO

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set); // add the dataset

        // create a data object with the dataset list
        LineData data = new LineData(dataSets);
        styleData(data);

        // set data
        mChart.setData(data);
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuart);
    }
}
