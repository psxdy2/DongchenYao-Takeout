package com.demo.takeoutapp.ui.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.takeoutapp.R;
import com.demo.takeoutapp.ui.BaseActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class DataGlanceActivity extends BaseActivity {

    private LinearLayout headGroup;
    private ImageView headLefticon;
    private TextView headText;
    private ImageView headRighticon;
    private LineChart linechart1;
    private LineChart linechart2;
    private LineChart linechart3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_glance);
        initView();
        initChart1();
        initChart2();
        initChart3();
    }

    private void initView() {
        headGroup = (LinearLayout) findViewById(R.id.head_group);
        headLefticon = (ImageView) findViewById(R.id.head_lefticon);
        headText = (TextView) findViewById(R.id.head_text);
        headRighticon = (ImageView) findViewById(R.id.head_righticon);
        linechart1 = (LineChart) findViewById(R.id.linechart1);
        linechart2 = (LineChart) findViewById(R.id.linechart2);
        linechart3 = (LineChart) findViewById(R.id.linechart3);

        headLefticon.setImageResource(R.mipmap.arrowleft_back);
        headLefticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        headText.setText("平台数据一览");
    }

    /**
     * 初始化图表
     */
    private void initChart1() {
        List<Entry> yVals = new ArrayList<Entry>();
        List<String> xVals = new ArrayList<String>();

        for (int i = 0; i < Calendar.getInstance().get(Calendar.DAY_OF_MONTH); i++) {
            xVals.add(i + "号");
            yVals.add(new Entry(new Random().nextInt(10000), i));
        }


        float scale = (float) (xVals.size() / 6.0);
        linechart1.zoom(scale, 0, 0, 0);
        linechart1.animateXY(2000, 2000);
        linechart1.setBackgroundColor(0xffffffff);
        linechart1.getAxisRight().setDrawAxisLine(false);
        linechart1.getAxisRight().setDrawLabels(false);
        linechart1.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        linechart1.getLegend().setEnabled(false);
        linechart1.setDescription("");
        LineDataSet set1 = new LineDataSet(yVals, "Content");
        set1.setDrawValues(true);
        set1.setDrawFilled(true);
        LineData lineData = new LineData(xVals, set1);
        lineData.setDrawValues(true);
        linechart1.setData(lineData);
    }

    private void initChart2() {
        List<Entry> yVals = new ArrayList<Entry>();
        List<String> xVals = new ArrayList<String>();

        for (int i = 0; i < Calendar.getInstance().get(Calendar.DAY_OF_MONTH); i++) {
            xVals.add(i + "号");
            yVals.add(new Entry(new Random().nextInt(10000), i));
        }

        float scale = (float) (xVals.size() / 6.0);
        linechart2.zoom(scale, 0, 0, 0);
        linechart2.animateXY(2000, 2000);
        linechart2.setBackgroundColor(0xffFFB6C1);
        linechart2.getAxisRight().setDrawAxisLine(false);
        linechart2.getAxisRight().setDrawLabels(false);
        linechart2.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        linechart2.getLegend().setEnabled(false);
        linechart2.setDescription("");
        LineDataSet set1 = new LineDataSet(yVals, "Content");
        set1.setDrawValues(true);
        set1.setDrawFilled(true);
        LineData lineData = new LineData(xVals, set1);
        lineData.setDrawValues(true);
        linechart2.setData(lineData);
    }

    private void initChart3() {
        List<Entry> yVals = new ArrayList<Entry>();
        List<String> xVals = new ArrayList<String>();

        for (int i = 0; i < Calendar.getInstance().get(Calendar.DAY_OF_MONTH); i++) {
            xVals.add(i + "号");
            yVals.add(new Entry(new Random().nextInt(100000), i));
        }

        float scale = (float) (xVals.size() / 6.0);
        linechart3.zoom(scale, 0, 0, 0);
        linechart3.animateXY(2000, 2000);
        linechart3.setBackgroundColor(0xffF5DEB3);
        linechart3.getAxisRight().setDrawAxisLine(false);
        linechart3.getAxisRight().setDrawLabels(false);
        linechart3.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        linechart3.getLegend().setEnabled(false);
        linechart3.setDescription("");
        LineDataSet set1 = new LineDataSet(yVals, "Content");
        set1.setDrawValues(true);
        set1.setDrawFilled(true);
        LineData lineData = new LineData(xVals, set1);
        lineData.setDrawValues(true);
        linechart3.setData(lineData);
    }
}