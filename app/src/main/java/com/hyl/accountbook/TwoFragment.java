package com.hyl.accountbook;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

public class TwoFragment extends Fragment {

    double[] values = {500.00, 800.00, 1000.00, 900.00};
    double sumVal = values[0] + values[1] + values[2] + values[3];
    int[] colors = {Color.BLUE, Color.GREEN, Color.MAGENTA, Color.RED};

    private LinearLayout ll_expense_piechart;
    private GraphicalView graphicalView;

    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_two, container, false);

        initPieChart(v);

        return v;
    }

    private void initPieChart(View v){
        CategorySeries dataset = buildCategoryDataset("報表", values);
        DefaultRenderer renderer = buildCategoryRenderer(colors);

        ll_expense_piechart = (LinearLayout) v.findViewById(R.id.ll_expense_piechart);
        ll_expense_piechart.removeAllViews();

        graphicalView = ChartFactory.getPieChartView(getContext(),dataset, renderer);
        graphicalView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        ll_expense_piechart.addView(graphicalView);
    }

    protected CategorySeries buildCategoryDataset(String title, double[] values){
        CategorySeries series = new CategorySeries(title);
        series.add("房租:"+values[0], values[0]/sumVal);
        series.add("伙食費:"+values[1], values[1]/sumVal);
        series.add("生活費:"+values[2], values[2]/sumVal);
        series.add("其它:"+values[3], values[3]/sumVal);
        return series;
    }

    private DefaultRenderer getPieRenderer(){
        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setZoomButtonsVisible(true);
        renderer.setZoomEnabled(true);
        SimpleSeriesRenderer yellowRenderer = new SimpleSeriesRenderer();
        yellowRenderer.setColor(Color.YELLOW);
        SimpleSeriesRenderer blueRenderer = new SimpleSeriesRenderer();
        blueRenderer.setColor(Color.BLUE);
        SimpleSeriesRenderer redRenderer = new SimpleSeriesRenderer();
        redRenderer.setColor(Color.RED);
        renderer.addSeriesRenderer(yellowRenderer);
        renderer.addSeriesRenderer(blueRenderer);
        renderer.addSeriesRenderer(redRenderer);

        renderer.setLabelsTextSize(30);
        renderer.setLegendTextSize(50);
        renderer.setApplyBackgroundColor(true);
        renderer.setBackgroundColor(Color.BLACK);

        return renderer;
    }

    protected DefaultRenderer buildCategoryRenderer(int[] colors){
        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setLegendTextSize(35);
        renderer.setLabelsTextSize(25);
        renderer.setLabelsColor(Color.BLACK);
        renderer.setPanEnabled(false);


        for(int color : colors){
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(color);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }
}

