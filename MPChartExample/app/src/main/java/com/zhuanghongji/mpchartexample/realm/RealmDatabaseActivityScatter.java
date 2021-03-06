package com.zhuanghongji.mpchartexample.realm;

import android.os.Bundle;
import android.view.WindowManager;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.realm.implementation.RealmScatterDataSet;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.zhuanghongji.mpchartexample.R;
import com.zhuanghongji.mpchartexample.custom.RealmDemoData;

import java.util.ArrayList;

import io.realm.RealmResults;

/**
 * Created by Philipp Jahoda on 21/10/15.
 */
public class RealmDatabaseActivityScatter extends RealmBaseActivity {

    private ScatterChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mChart = (ScatterChart) findViewById(R.id.chart1);
        setup(mChart);

        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getXAxis().setDrawGridLines(false);
        mChart.setPinchZoom(true);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_scatterchart_noseekbar;
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
        writeToDB(45);

        // add data to the chart
        setData();
    }

    private void setData() {

        RealmResults<RealmDemoData> result = mRealm.where(RealmDemoData.class).findAll();

        RealmScatterDataSet<RealmDemoData> set = new RealmScatterDataSet<RealmDemoData>(result, "xValue", "yValue");
        set.setLabel("Realm ScatterDataSet");
        set.setScatterShapeSize(9f);
        set.setColor(ColorTemplate.rgb("#CDDC39"));
        set.setScatterShape(ScatterChart.ScatterShape.CIRCLE);

        ArrayList<IScatterDataSet> dataSets = new ArrayList<IScatterDataSet>();
        dataSets.add(set); // add the dataset

        // create a data object with the dataset list
        ScatterData data = new ScatterData(dataSets);
        styleData(data);

        // set data
        mChart.setData(data);
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuart);
    }
}
