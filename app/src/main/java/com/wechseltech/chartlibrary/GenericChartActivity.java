package com.wechseltech.chartlibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class GenericChartActivity extends Activity {

	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Starting Activity");
        
        
        
        Type type = (Type) getIntent().getExtras().getSerializable("type");
        
       /* if(type.equals(Type.GAUGE))
        {
        	
        	setContentView(R.layout.activity_gauge_chart);
        	
        	
        	GaugeChart plot = (GaugeChart) findViewById(
        			R.id.Chart);
			
		//GaugeItem item = new GaugeItem(43,0,100,10,
			//	70,100);
			
        	//TODO
        	GaugeItem item = new GaugeItem(0.03,0.00,0.1,10,0.07,0.1);
           
        	//GaugeItem item = new GaugeItem(0.004,0.00,0.01,10,0.002,0.006);
        	
        	//GaugeItem item = new GaugeItem(400000,0.0,1000000,10,200000,600000);
			
			plot.setSeriesName("Memory");
			
			plot.setGaugeItem(item);
        }*/
        
        if(type.equals(Type.GAUGE))
        {
        	
        	
			setContentView(R.layout.activity_main);
    		
    		
    		LinearLayout plot =(LinearLayout)findViewById(R.id.Root);
    		
    		System.out.println("PLOT"+plot);
    		
    		GaugeChart chart = new  GaugeChart(this);
			
		
    		
        	GaugeItem item = new GaugeItem(0.03,0.00,0.1,10,0.07,0.1);
           
        	
			
        	chart.setSeriesName("Memory");
			
			chart.setGaugeItem(item);
			
			plot.addView(chart);
        }
        
        if(type.equals(Type.DOUGHNUT))
        	
        	
        	
        {
        	
        	setContentView(R.layout.activity_doughnut);
        	
        	
        	DoughnutChart plot =(DoughnutChart)findViewById(R.id.Chart);
			
			List<CategoryMultipleSeries> series = new ArrayList<CategoryMultipleSeries> ();
			
			series.add(new CategoryMultipleSeries("Germany",1000,1,
					"Wages",Color.GREEN));
			series.add(new CategoryMultipleSeries("USA",2000,1,
					"Wages",Color.RED));
			series.add(new CategoryMultipleSeries("UK",500,1,
					"Wages",Color.BLUE));
			series.add(new CategoryMultipleSeries("France",200,1,
					"Wages",Color.CYAN));

			series.add(new CategoryMultipleSeries("Sweden",700,1,
					"Wages",Color.MAGENTA));

            series.add(new CategoryMultipleSeries("Norway",1000,1,
                    "Wages",Color.LTGRAY));


			series.add(new CategoryMultipleSeries("Germany",15000,2,
					"PPP",Color.GREEN));
			series.add(new CategoryMultipleSeries("USA",2000,2,
					"PPP",Color.RED));
			series.add(new CategoryMultipleSeries("UK",10000,2,
					"PPP",Color.BLUE));
			series.add(new CategoryMultipleSeries("France",5000,2,
					"PPP",Color.CYAN));

			series.add(new CategoryMultipleSeries("Sweden",3000,2,
					"PPP",Color.MAGENTA));

            series.add(new CategoryMultipleSeries("Norway",7000,2,
                    "PPP",Color.LTGRAY));


			plot.setSeries(series,2);
			plot.setDescription("Country Status");
        	
        	
        }
        
        
       /* if(type.equals(Type.DOUGHNUT))
        	
        	
        	
        {
        	
        	setContentView(R.layout.activity_linear);
        	
        	System.out.println("Starting Pie");
        	
        	
        	LinearLayout plot =(LinearLayout)findViewById(R.id.Root);
        	
        	DoughnutChart chart = new DoughnutChart(this);
        	
        	
			
			List<CategoryMultipleSeries> series = new ArrayList<CategoryMultipleSeries> ();
			
			series.add(new CategoryMultipleSeries("Germany",1000,1,
					"Wages"));
			series.add(new CategoryMultipleSeries("USA",2000,1,
					"Wages"));
			series.add(new CategoryMultipleSeries("UK",500,1,
					"Wages"));
			series.add(new CategoryMultipleSeries("France",200,1,
					"Wages"));
			series.add(new CategoryMultipleSeries("Germany",15000,2,
					"PPP"));
			series.add(new CategoryMultipleSeries("USA",2000,2,
					"PPP"));
			series.add(new CategoryMultipleSeries("UK",10000,2,
					"PPP"));
			series.add(new CategoryMultipleSeries("France",5000,2,
					"PPP"));
			chart.setColorsAsPrimary(false);
			chart.setAccentStartingColor(Color.RED);
			chart.setSeries(series,2);
			plot.addView(chart);
        	
        	
        }*/
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        if(type.equals(Type.NEGATIVESTACKBARCHART))
        {
        	
        	setContentView(R.layout.activity_negative_stackbar);
        	
        	NegativeStackBarChart plot = (NegativeStackBarChart)findViewById(R.id.Chart);
			
			List<MultipleSeriesItem> series = new ArrayList<MultipleSeriesItem> ();
			
			//String xlabel, double y,
			//String seriesname,boolean negative
			
			
			
			/*series.add(new MultipleSeriesItem("Germany",45,"Wages",true));
			series.add(new MultipleSeriesItem("USA",60,"Wages",true));
			series.add(new MultipleSeriesItem("UK",250,"Wages",true));
			series.add(new MultipleSeriesItem("France",390,"Wages",true));
			series.add(new MultipleSeriesItem("Norway",150,"Wages",true));
			series.add(new MultipleSeriesItem("Finland",200,"Wages",true));
			
			series.add(new MultipleSeriesItem("Germany",145,"Stocks",false));
			series.add(new MultipleSeriesItem("USA",62,"Stocks",false));
			series.add(new MultipleSeriesItem("UK",400,"Stocks",false));
			series.add(new MultipleSeriesItem("France",620,"Stocks",false));
			series.add(new MultipleSeriesItem("Norway",300,"Stocks",false));
			series.add(new MultipleSeriesItem("Finland",200,"Stocks",false));*/
			
			
			series.add(new MultipleSeriesItem(45,"Germany","Wages",true,Color.BLUE,"45"));
			series.add(new MultipleSeriesItem(60,"USA","Wages",true,Color.BLUE,"60"));
			series.add(new MultipleSeriesItem(250,"UK","Wages",true,Color.BLUE,"250"));
			series.add(new MultipleSeriesItem(390,"France","Wages",true,Color.BLUE,"390"));
			series.add(new MultipleSeriesItem(150,"Norway","Wages",true,Color.BLUE,"150"));
			series.add(new MultipleSeriesItem(200,"Finland","Wages",true,Color.BLUE,"200"));
			
			series.add(new MultipleSeriesItem(145,"Germany","Life-threatening",false,Color.GREEN,"145"));
			series.add(new MultipleSeriesItem(62,"USA","Life-threatening",false,Color.GREEN,"62"));
			series.add(new MultipleSeriesItem(400,"UK","Life-threatening",false,Color.GREEN,"400"));
			series.add(new MultipleSeriesItem(620,"France","Life-threatening",false,Color.GREEN,"620"));
			series.add(new MultipleSeriesItem(300,"Norway","Life-threatening",false,Color.GREEN,"300"));
			series.add(new MultipleSeriesItem(200,"Finland","Life-threatening",false,Color.GREEN,"200"));
			
			
			
			plot.setXLabelName("Median");
			plot.setYLabelName("Data Value");
			plot.setSeries(series);
        }
        
        
        if(type.equals(Type.LINECHART))
        	
        {
        	
        	setContentView(R.layout.activity_main);
    		
    		
    		
    		LinearLayout plot =(LinearLayout)findViewById(R.id.Root);
    		
    		
    		List<LineItem> series = new ArrayList<LineItem> ();
    		
    		LineChart chart = new LineChart(this);
    		
    		series.add(new LineItem(4,10,1,"Wages"));
			series.add(new LineItem(3.5,20,1,"Wages"));
			series.add(new LineItem(7.5,35,1,"Wages"));
			series.add(new LineItem(25,100,1,"Wages"));
			series.add(new LineItem(75,30,1,"Wages"));
			series.add(new LineItem(100,200,1,"Wages"));
			
			series.add(new LineItem(14,70,2,"Stocks"));
			series.add(new LineItem(36,350,2,"Stocks"));
			series.add(new LineItem(100,64,2,"Stocks"));
			series.add(new LineItem(78,154,2,"Stocks"));
			series.add(new LineItem(64,250,2,"Stocks"));
			series.add(new LineItem(10,220,2,"Stocks"));
			
			series.add(new LineItem(120,450,3,"GDP"));
			series.add(new LineItem(100,900,3,"GDP"));
			series.add(new LineItem(150,650,3,"GDP"));
			
			//NOTE:  IMPORTANT !!!!! ALWAYS SET XNUM,YNUM,XCAT,YCAT before setting series
			
			chart.setXNum(true);
			chart.setYNum(true);
			chart.setSeries(series,3);
			chart.setXLabelName("Median");
			chart.setYLabelName("Data Value");
			chart.setAsHorizontal(false);
			chart.showGridLines(true);
			
			
			plot.addView(chart);
			
			
			
			
			
			
			
			
        	
        }
        
        if(type.equals(Type.MULTIPLESERIESBARCHARTNEW))
        {
        	
        	setContentView(R.layout.activity_main);
    		
        	List<MultipleSeriesItem> series = new ArrayList<MultipleSeriesItem> ();
    		
    		
    		LinearLayout plot =(LinearLayout)findViewById(R.id.Root);
    		
        	
    		MultipleSeriesBarChart chart = new MultipleSeriesBarChart(this);
    		
    		series.add(new MultipleSeriesItem(1,45,"Germany","Wages",Color.BLUE,"45"));
			series.add(new MultipleSeriesItem(1,60,"USA","Wages",Color.BLUE,"60"));
			series.add(new MultipleSeriesItem(1,250,"UK","Wages",Color.BLUE,"250"));
			series.add(new MultipleSeriesItem(1,390,"France","Wages",Color.BLUE,"390"));
			series.add(new MultipleSeriesItem(1,400,"Norway","Wages",Color.BLUE,"400"));
			series.add(new MultipleSeriesItem(1,100,"India","Wages",Color.BLUE,"100"));
			series.add(new MultipleSeriesItem(1,410,"Sweden","Wages",Color.BLUE,"410"));
			series.add(new MultipleSeriesItem(1,150,"Finland","Wages",Color.BLUE,"150"));
			
			series.add(new MultipleSeriesItem(2,145,"Germany","Life-threatening",Color.GREEN,"145"));
			series.add(new MultipleSeriesItem(2,62,"USA","Life-threatening",Color.GREEN,"62"));
			series.add(new MultipleSeriesItem(2,400,"UK","Life-threatening",Color.GREEN,"400"));
			series.add(new MultipleSeriesItem(2,620,"France","Life-threatening",Color.GREEN,"620"));
			series.add(new MultipleSeriesItem(2,50,"Norway","Life-threatening",Color.GREEN,"50"));
			series.add(new MultipleSeriesItem(2,62,"India","Life-threatening",Color.GREEN,"62"));
			series.add(new MultipleSeriesItem(2,300,"Sweden","Life-threatening",Color.GREEN,"300"));
			series.add(new MultipleSeriesItem(2,550,"Finland","Life-threatening",Color.GREEN,"550"));
			
			
			/*series.add(new MultipleSeriesItem(3,78,"Germany","GDP"));
			series.add(new MultipleSeriesItem(3,600,"USA","GDP"));
			series.add(new MultipleSeriesItem(3,340,"UK","GDP"));
			series.add(new MultipleSeriesItem(3,478,"France","GDP"));
			series.add(new MultipleSeriesItem(3,500,"Norway","GDP"));
			series.add(new MultipleSeriesItem(3,620,"India","GDP"));
			series.add(new MultipleSeriesItem(3,360,"Sweden","GDP"));
			series.add(new MultipleSeriesItem(3,500,"Finland","GDP"));*/
    		
    		/*series.add(new MultipleSeriesItem(1,"Germany",45,"Wages"));
			series.add(new MultipleSeriesItem(1,"USA",60,"Wages"));
			series.add(new MultipleSeriesItem(1,"UK",250,"Wages"));
			series.add(new MultipleSeriesItem(1,"France",390,"Wages"));
			
			series.add(new MultipleSeriesItem(2,"Germany",145,"Stocks"));
			series.add(new MultipleSeriesItem(2,"USA",62,"Stocks"));
			series.add(new MultipleSeriesItem(2,"UK",400,"Stocks"));
			series.add(new MultipleSeriesItem(2,"France",620,"Stocks"));*/
			
			/*series.add(new MultipleSeriesItem(3,"Germany",78,"GDP"));
			series.add(new MultipleSeriesItem(3,"USA",600,"GDP"));
			series.add(new MultipleSeriesItem(3,"UK",340,"GDP"));
			series.add(new MultipleSeriesItem(3,"France",478,"GDP"));*/
			
			
			chart.setYCategory(true);
			chart.setXNum(true);
			chart.setXLabelName("Data Value");
			chart.setYLabelName("Country");
			chart.setAssignSeriesColors(false);
			chart.setSeries(series,2);
			chart.showDrawLabels(true);
			chart.setAsHorizontal(false);
			chart.showGridLines(true);
			
			
			plot.addView(chart);
        	
        	
        	
        }
        
//        if(type.equals(Type.MULTIPLESERIESBARCHARTNEW))
//        {
//        	setContentView(R.layout.activity_multiple_series_bar_chart_new);
//        	
//        	MultipleSeriesBarChartNew plot = (MultipleSeriesBarChartNew)findViewById(R.id.Chart);
//			
//			List<MultipleSeriesItem> series = new ArrayList<MultipleSeriesItem> ();
//			
//			//int series,String xlabel, double y,String seriesname
//			/*series.add(new MultipleSeriesItem(1,"Germany",45,"Wages"));
//			series.add(new MultipleSeriesItem(1,"USA",60,"Wages"));
//			series.add(new MultipleSeriesItem(1,"UK",250,"Wages"));
//			series.add(new MultipleSeriesItem(1,"France",390,"Wages"));
//			
//			series.add(new MultipleSeriesItem(2,"Germany",145,"Stocks"));
//			series.add(new MultipleSeriesItem(2,"USA",62,"Stocks"));
//			series.add(new MultipleSeriesItem(2,"UK",400,"Stocks"));
//			series.add(new MultipleSeriesItem(2,"France",620,"Stocks"));*/
//			
//			
//			series.add(new MultipleSeriesItem(1,45,"Germany","Wages"));
//			series.add(new MultipleSeriesItem(1,60,"USA","Wages"));
//			series.add(new MultipleSeriesItem(1,250,"UK","Wages"));
//			series.add(new MultipleSeriesItem(1,390,"France","Wages"));
//			series.add(new MultipleSeriesItem(1,400,"Norway","Wages"));
//			series.add(new MultipleSeriesItem(1,100,"India","Wages"));
//			series.add(new MultipleSeriesItem(1,410,"Sweden","Wages"));
//			series.add(new MultipleSeriesItem(1,150,"Finland","Wages"));
//			
//			series.add(new MultipleSeriesItem(2,145,"Germany","Stocks"));
//			series.add(new MultipleSeriesItem(2,62,"USA","Stocks"));
//			series.add(new MultipleSeriesItem(2,400,"UK","Stocks"));
//			series.add(new MultipleSeriesItem(2,620,"France","Stocks"));
//			series.add(new MultipleSeriesItem(2,50,"Norway","Stocks"));
//			series.add(new MultipleSeriesItem(2,62,"India","Stocks"));
//			series.add(new MultipleSeriesItem(2,300,"Sweden","Stocks"));
//			series.add(new MultipleSeriesItem(2,550,"Finland","Stocks"));
//	
//			
////			series.add(new MultipleSeriesItem(1,"Germany",45,"Wages"));
////			series.add(new MultipleSeriesItem(1,"USA",60,"Wages"));
////			series.add(new MultipleSeriesItem(1,"UK",250,"Wages"));
////			series.add(new MultipleSeriesItem(1,"France",390,"Wages"));
////			series.add(new MultipleSeriesItem(1,"Norway",400,"Wages"));
////			series.add(new MultipleSeriesItem(1,"India",100,"Wages"));
////			series.add(new MultipleSeriesItem(1,"Sweden",410,"Wages"));
////			series.add(new MultipleSeriesItem(1,"Finland",150,"Wages"));
////			
////			series.add(new MultipleSeriesItem(2,"Germany",145,"Stocks"));
////			series.add(new MultipleSeriesItem(2,"USA",62,"Stocks"));
////			series.add(new MultipleSeriesItem(2,"UK",400,"Stocks"));
////			series.add(new MultipleSeriesItem(2,"France",620,"Stocks"));
////			series.add(new MultipleSeriesItem(2,"Norway",50,"Stocks"));
////			series.add(new MultipleSeriesItem(2,"India",62,"Stocks"));
////			series.add(new MultipleSeriesItem(2,"Sweden",300,"Stocks"));
////			series.add(new MultipleSeriesItem(2,"Finland",550,"Stocks"));
//			
//			
////			series.add(new MultipleSeriesItem(1,"Germany",.0045,"Wages"));
////			series.add(new MultipleSeriesItem(1,"USA",.0060,"Wages"));
////			series.add(new MultipleSeriesItem(1,"UK",.0050,"Wages"));
////			series.add(new MultipleSeriesItem(1,"France",0.30,"Wages"));
////			series.add(new MultipleSeriesItem(1,"Norway",0.0400,"Wages"));
////			series.add(new MultipleSeriesItem(1,"India",.0100,"Wages"));
////			series.add(new MultipleSeriesItem(1,"Sweden",.00410,"Wages"));
////			series.add(new MultipleSeriesItem(1,"Finland",.0150,"Wages"));
////			
////			series.add(new MultipleSeriesItem(2,"Germany",.00145,"Stocks"));
////			series.add(new MultipleSeriesItem(2,"USA",-.0062,"Stocks"));
////			series.add(new MultipleSeriesItem(2,"UK", -.0400,"Stocks"));
////			series.add(new MultipleSeriesItem(2,"France",.0620,"Stocks"));
////			series.add(new MultipleSeriesItem(2,"Norway",.050,"Stocks"));
////			series.add(new MultipleSeriesItem(2,"India",.062,"Stocks"));
////			series.add(new MultipleSeriesItem(2,"Sweden",.0300,"Stocks"));
////			series.add(new MultipleSeriesItem(2,"Finland",.0050,"Stocks"));
//			
//		/*	series.add(new MultipleSeriesItem(1,0.01,"Germany","Wages"));
//			series.add(new MultipleSeriesItem(1,0.001,"USA","Wages"));
//			series.add(new MultipleSeriesItem(1,0.005,"UK","Wages"));
//			series.add(new MultipleSeriesItem(1,0.02,"France","Wages"));
//			series.add(new MultipleSeriesItem(1,0.006,"Norway","Wages"));
//			series.add(new MultipleSeriesItem(1,0.002,"India","Wages"));
//			
//			series.add(new MultipleSeriesItem(2,0.015,"Germany","Stocks"));
//			series.add(new MultipleSeriesItem(2,0.004,"USA","Stocks"));
//			series.add(new MultipleSeriesItem(2,0.018,"UK","Stocks"));
//			series.add(new MultipleSeriesItem(2,0.022,"France","Stocks"));
//			
//			series.add(new MultipleSeriesItem(2,0.021,"Norway","Stocks"));
//			series.add(new MultipleSeriesItem(2,0.011,"India","Stocks"));*/
//			
//			
//			
//			
//			
//			plot.setXLabelName("Median");
//			plot.setYLabelName("Data Value");
//			plot.setSeries(series,2);
//        	
//        	
//        }
       
        if(type.equals(Type.LINECHARTXML))
        {
        	
        	setContentView(R.layout.activity_line_chart_new);
        	
        	
        	LineChart plot =(LineChart)findViewById(R.id.Chart);
			
			List<LineItem> series = new ArrayList<LineItem> ();
			
			/*series.add(new LineItem(4,10,1,"Wages"));
			series.add(new LineItem(3.5,20,1,"Wages"));
			series.add(new LineItem(7.5,35,1,"Wages"));
			series.add(new LineItem(25,100,1,"Wages"));
			series.add(new LineItem(75,30,1,"Wages"));
			series.add(new LineItem(100,200,1,"Wages"));
			
			series.add(new LineItem(-14,70,2,"Stocks"));
			series.add(new LineItem(36,350,2,"Stocks"));
			series.add(new LineItem(100,64,2,"Stocks"));
			series.add(new LineItem(78,154,2,"Stocks"));
			series.add(new LineItem(64,250,2,"Stocks"));
			series.add(new LineItem(10,220,2,"Stocks"));
			
			series.add(new LineItem(120,450,3,"GDP"));
			series.add(new LineItem(100,900,3,"GDP"));
			series.add(new LineItem(150,650,3,"GDP"));*/
			
			
			

		/*	series.add(new LineItem(4,10,1,"Wages"));
			series.add(new LineItem(3.5,20,1,"Wages"));
			series.add(new LineItem(7.5,35,1,"Wages"));
			series.add(new LineItem(25,40,1,"Wages"));
			
			series.add(new LineItem(14,70,2,"Stocks"));
			series.add(new LineItem(36,350,2,"Stocks"));
			series.add(new LineItem(100,64,2,"Stocks"));
			series.add(new LineItem(78,154,2,"Stocks"));*/
			
			series.add(new LineItem("Germany",10,1,"Germany",1,"Life-threatening",Color.BLUE));
			series.add(new LineItem("France",20,2,"France",1,"Life-threatening",Color.BLUE));
			series.add(new LineItem("USA",35,3,"USA",1,"Life-threatening",Color.BLUE));
			series.add(new LineItem("UK",100,4,"UK",1,"Life-threatening",Color.BLUE));
			series.add(new LineItem("Norway",150,5,"Norway",1,"Life-threatening",Color.BLUE));
			series.add(new LineItem("India",200,6,"India",1,"Life-threatening",Color.BLUE));
			series.add(new LineItem("Sweden",400,7,"Sweden",1,"Life-threatening",Color.BLUE));
			series.add(new LineItem("Finland",100,8,"Finland",1,"Life-threatening",Color.BLUE));
			series.add(new LineItem("Romania",30,9,"Romania",1,"Life-threatening",Color.BLUE));
//			
//			
			series.add(new LineItem("Germany",70,1,"Germany",2,"Stocks",Color.GREEN));
			series.add(new LineItem("France",350,2,"France",2,"Stocks",Color.GREEN));
			series.add(new LineItem("USA",64,3,"USA",2,"Stocks",Color.GREEN));
			series.add(new LineItem("UK",154,4,"UK",2,"Stocks",Color.GREEN));
			series.add(new LineItem("Norway",250,5,"Norway",2,"Stocks",Color.GREEN));
			series.add(new LineItem("India",300,6,"India",2,"Stocks",Color.GREEN));
			series.add(new LineItem("Sweden",200,7,"Sweden",2,"Stocks",Color.GREEN));
			series.add(new LineItem("Finland",90,8,"Finland",2,"Stocks",Color.GREEN));
			series.add(new LineItem("Romania",45,9,"Romania",2,"Stocks",Color.GREEN));
			
			
			
			
			/*series.add(new LineItem(10,"Germany",1,1,"Wages"));
			series.add(new LineItem(20,"France",2,1,"Wages"));
			series.add(new LineItem(35,"USA",3,1,"Wages"));
			series.add(new LineItem(100,"UK",4,1,"Wages"));
			series.add(new LineItem(150,"Norway",5,1,"Wages"));
			series.add(new LineItem(200,"India",6,1,"Wages"));
			series.add(new LineItem(400,"Sweden",7,1,"Wages"));
			series.add(new LineItem(50,"Finland",8,1,"Wages"));
			series.add(new LineItem(30,"Romania",9,1,"Wages"));
			
			series.add(new LineItem(70,"Germany",1,2,"Stocks"));
			series.add(new LineItem(350,"France",2,2,"Stocks"));
			series.add(new LineItem(64,"USA",3,2,"Stocks"));
			series.add(new LineItem(154,"UK",4,2,"Stocks"));
			series.add(new LineItem(250,"Norway",5,2,"Stocks"));
			series.add(new LineItem(300,"India",6,2,"Stocks"));
			series.add(new LineItem(200,"Sweden",7,2,"Stocks"));
			series.add(new LineItem(30,"Finland",8,2,"Stocks"));
			series.add(new LineItem(32,"Romania",9,2,"Stocks"));*/
			
			
			/*series.add(new LineItem("Bayer","Germany",1,1,"Wages"));
			series.add(new LineItem("Dassault","France",2,1,"Wages"));
			series.add(new LineItem("Pfizer","USA",3,1,"Wages"));
			series.add(new LineItem("Astra","UK",4,1,"Wages"));
			series.add(new LineItem("Caterpillar","Norway",5,1,"Wages"));
			series.add(new LineItem("Ericsson","Finland",6,1,"Wages"));
			
			series.add(new LineItem("ABB","Germany",1,2,"Stocks"));
			series.add(new LineItem("Plasse","France",2,2,"Stocks"));
			series.add(new LineItem("Sachs","USA",3,2,"Stocks"));
			series.add(new LineItem("Fin","UK",4,2,"Stocks"));
			series.add(new LineItem("Morgan","Norway",5,2,"Stocks"));
			series.add(new LineItem("Skype","Finland",6,2,"Stocks"));*/
			
			
			

			plot.setXLabelName("Median");
			plot.setYLabelName("Data Value");
			plot.setChartType(LineType.LINE);
			
			//SMOOTHNESS - 0 to 0.5 - 0.5 most smooth - points may be left out .. 
			//0 lest smooth incorparate all points
			plot.setSmoothness(0.2f);
			
			plot.setSeries(series,2);	
        	
        	
        }
        
        if(type.equals(Type.SCATTERPLOT))
        {
        	
        	setContentView(R.layout.activity_scatter);
        	ScatterPlot plot = (ScatterPlot)findViewById(R.id.Chart);
			
			List<Item> series = new ArrayList<Item> ();
		
			
			series.add(new Item(4,10,"Germany",1,"Life-threatening",Color.GREEN));
			series.add(new Item(3.5,20,"France",1,"Life-threatening",Color.GREEN));
			series.add(new Item(7.5,35,"UK",1,"Life-threatening",Color.GREEN));
			series.add(new Item(25,100,"USA",1,"Life-threatening",Color.GREEN));
			
			series.add(new Item(14,70,"Germany",2,"Stocks",Color.BLUE));
			series.add(new Item(36,350,"France",2,"Stocks",Color.BLUE));
			series.add(new Item(100,64,"UK",2,"Stocks",Color.BLUE));
			series.add(new Item(78,154,"USA",2,"Stocks",Color.BLUE));
			
			
			/*series.add(new Item("Germany",10,1,"Wages"));
			series.add(new Item("France",20,1,"Wages"));
			series.add(new Item("USA",35,1,"Wages"));
			series.add(new Item("UK",100,1,"Wages"));
			series.add(new Item("Norway",150,1,"Wages"));
			series.add(new Item("Finland",200,1,"Wages"));
			series.add(new Item("Sweden",250,1,"Wages"));
			series.add(new Item("India",175,1,"Wages"));
			
			
			series.add(new Item("Germany",70,2,"Stocks"));
			series.add(new Item("France",350,2,"Stocks"));
			series.add(new Item("USA",64,2,"Stocks"));
			series.add(new Item("UK",154,2,"Stocks"));
			series.add(new Item("Norway",270,2,"Stocks"));
			series.add(new Item("Finland",300,2,"Stocks"));
			series.add(new Item("Sweden",30,2,"Stocks"));
			series.add(new Item("India",320,2,"Stocks"));*/
			
			
			
			/*series.add(new Item(10,"Germany",1,"Wages"));
			series.add(new Item(20,"France",1,"Wages"));
			series.add(new Item(35,"USA",1,"Wages"));
			series.add(new Item(100,"UK",1,"Wages"));
			series.add(new Item(150,"Norway",1,"Wages"));
			series.add(new Item(200,"Finland",1,"Wages"));
			series.add(new Item(250,"Sweden",1,"Wages"));
			series.add(new Item(175,"India",1,"Wages"));
			
			series.add(new Item(70,"Germany",2,"Stocks"));
			series.add(new Item(350,"France",2,"Stocks"));
			series.add(new Item(64,"USA",2,"Stocks"));
			series.add(new Item(154,"UK",2,"Stocks"));
			series.add(new Item(270,"Norway",2,"Stocks"));
			series.add(new Item(300,"Finland",2,"Stocks"));
			series.add(new Item(30,"Sweden",2,"Stocks"));
			series.add(new Item(320,"India",2,"Stocks"));*/
			
//			series.add(new Item("ABB","Germany",1,"Wages"));
//			series.add(new Item("Dassault","France",1,"Wages"));
//			series.add(new Item("Eli Lilly","USA",1,"Wages"));
//			series.add(new Item("Astra","UK",1,"Wages"));
//			series.add(new Item("Caterpillar","Norway",1,"Wages"));
//			series.add(new Item("Ericsson","Finland",1,"Wages"));
//			
//			series.add(new Item("Bayer","Germany",2,"Stocks"));
//			series.add(new Item("Plasse","France",2,"Stocks"));
//			series.add(new Item("Pfizer","USA",2,"Stocks"));
//			series.add(new Item("Fin","UK",2,"Stocks"));
//			series.add(new Item("Morgan","Norway",2,"Stocks"));
//			series.add(new Item("Skype","Finland",2,"Stocks"));

			
			/*series.add(new LineItem("Bayer","Germany",1,1,"Wages"));
			series.add(new LineItem("Dassault","France",2,1,"Wages"));
			series.add(new LineItem("Pfizer","USA",3,1,"Wages"));
			series.add(new LineItem("Astra","UK",4,1,"Wages"));
			series.add(new LineItem("Caterpillar","Norway",5,1,"Wages"));
			series.add(new LineItem("Ericsson","Finland",6,1,"Wages"));
			
			series.add(new LineItem("ABB","Germany",1,2,"Stocks"));
			series.add(new LineItem("Plasse","France",2,2,"Stocks"));
			series.add(new LineItem("Sachs","USA",3,2,"Stocks"));
			series.add(new LineItem("Fin","UK",4,2,"Stocks"));
			series.add(new LineItem("Morgan","Norway",5,2,"Stocks"));
			series.add(new LineItem("Skype","Finland",6,2,"Stocks"));*/
			
			
			
			plot.setXLabelName("Median");
			plot.setYLabelName("Data Value");
			plot.setSeries(series,2);
			
        }
        
       /* if(type.equals(Type.PIE))
        {
        	
        	setContentView(R.layout.activity_pie);
        	
        	System.out.println("Starting Pie");
        	
        	
        	PieChart plot =(PieChart)findViewById(R.id.Chart);
        	
        	//PiePlot plot = (PiePlot)findViewById(R.id.Chart);
        	
	        
	        List<PieItem> series = new ArrayList<PieItem> ();
	        
	        series.add(new PieItem("Zeus", 400));
	        series.add(new PieItem("Ajax", 50));
	        series.add(new PieItem("Thor",150));
	        series.add(new PieItem("Hera",200));
	        series.add(new PieItem("David",350));
	        
	        plot.setSeries(series);
	        
	        
        }*/
        
        if(type.equals(Type.PIE))
        {
        	
        	setContentView(R.layout.activity_linear);
        	
        	System.out.println("Starting Pie");
        	
        	
        	
        	final LinearLayout plot =(LinearLayout)findViewById(R.id.Root);
    		
        	
    		PieChart chart = new PieChart(this);
        	
        	//PiePlot plot = (PiePlot)findViewById(R.id.Chart);
        	
	        
	        List<PieItem> series = new ArrayList<PieItem> ();
	        
	        series.add(new PieItem("Zeus", 400,Color.RED));
	        series.add(new PieItem("Ajax", 50,Color.YELLOW));
	        series.add(new PieItem("Thor",150,Color.BLUE));
	        series.add(new PieItem("Hera",200,Color.GREEN));
	        series.add(new PieItem("David",350,Color.CYAN));
	        chart.setAssignSeriesColors(false);
	        chart.setColorsAsPrimary(false);
	        chart.setAccentStartingColor(Color.RED);
	        chart.setSeries(series);
	        chart.setDescription("Greek Gods");
	        
	        chart.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					
					System.out.println("CLICKED");
					
					PieChart pie = (PieChart)v;
					
//					View view = inflate(th)
//					PopupWindow popup  = new PopupWindow();
//					popup.showAtLocation(plot.getRootView(),Gravity.LEFT, (int)item.getAnchorX(), (int)item.getAnchorY());
					
				}});
	        
	        plot.addView(chart);
	        
	        
        }
        
        
        /*if(type.equals(Type.SPIDERWEB))
        {
        	
        	System.out.println("Starting SPIDER");
        	setContentView(R.layout.activity_spiderweb);
        	
        	SpiderWeb plot = (SpiderWeb)findViewById(R.id.Chart);
			List<CategoryItem> series = new ArrayList<CategoryItem> ();
			
			series.add(new CategoryItem("Wages","Germany",45,1));
			series.add(new CategoryItem("Wages","USA",60,1));
			series.add(new CategoryItem("Wages","UK",250,1));
			series.add(new CategoryItem("Wages","France",390,1));
			
			series.add(new CategoryItem("Stocks","Germany",325,2));
			series.add(new CategoryItem("Stocks","USA",200,2));
			series.add(new CategoryItem("Stocks","UK",400,2));
			series.add(new CategoryItem("Stocks","France",620,2));
			
			
			plot.setSeries(series, 2);
        }*/
        
        
        
         if(type.equals(Type.RANGE))
        {
        	
        	setContentView(R.layout.activity_main);
     		
         	List<RangeItem> series = new ArrayList<RangeItem> ();
     		
     		
     		LinearLayout plot =(LinearLayout)findViewById(R.id.Root);
     		
         	
     		RangeChart chart = new RangeChart(this);
     		
			
			
			
//			series.add(new RangeItem(30,45,100,"Germany"));
//			series.add(new RangeItem(200,400,600,"USA"));
//			series.add(new RangeItem(350,500,750,"UK"));
//			
//			series.add(new RangeItem(350,500,1200,"Norway"));
//			series.add(new RangeItem(20,45,80,"France"));
			
			
				series.add(new RangeItem(-0.08,0.05,0.1,"Germany"));
				series.add(new RangeItem(0.12,0.1,0.2,"USA"));
				series.add(new RangeItem(0.0,0.05,0.1,"UK"));
////			
////			series.add(new RangeItem(350,500,1200,"Norway"));
				series.add(new RangeItem(0.03,0.06,0.15,"France"));
			
			
//			
			
//			series.add(new RangeItem(0.00003,0.0,0.0001,"Germany"));
//			series.add(new RangeItem(0.00001,0.00001,0.0002,"USA"));
//			series.add(new RangeItem(0.00005,0.00004,0.00008,"UK"));
//////		
//////		series.add(new RangeItem(350,500,1200,"Norway"));
//			series.add(new RangeItem(0.00006,0.0003,0.0005,"France"));
			
			
/*			series.add(new RangeItem(100000,300000,1000000,"Germany"));
			series.add(new RangeItem(100000,150000,200000,"USA"));
			series.add(new RangeItem(60000,200000,300000,"UK"));
////		
////		series.add(new RangeItem(350,500,1200,"Norway"));
			series.add(new RangeItem(300000,600000,800000,"France"));*/
			
			chart.setXLabelName("Country");
			chart.setYLabelName("Wages");
			
			chart.setAsHorizontal(true);
			chart.setBarColor(Color.YELLOW);
			chart.setSeries(series);
			
			plot.addView(chart);
        }
        
     	if(type.equals(Type.AREA))
        {
			
           setContentView(R.layout.activity_area_chart);
			AreaChart plot = (AreaChart)findViewById(R.id.Chart);
			
			List<LineItem> series = new ArrayList<LineItem> ();
			
			/*series.add(new LineItem(4,10,1,"Wages"));
			series.add(new LineItem(3.5,20,1,"Wages"));
			series.add(new LineItem(7.5,35,1,"Wages"));
			series.add(new LineItem(25,40,1,"Wages"));
			
			series.add(new LineItem(14,70,2,"Stocks"));
			series.add(new LineItem(36,350,2,"Stocks"));
			series.add(new LineItem(100,64,2,"Stocks"));
			series.add(new LineItem(78,154,2,"Stocks"));*/
			
		/*	series.add(new LineItem("Germany",10,1,1,"Wages"));
			series.add(new LineItem("France",20,2,1,"Wages"));
			series.add(new LineItem("USA",35,3,1,"Wages"));
			series.add(new LineItem("UK",100,4,1,"Wages"));
			series.add(new LineItem("Norway",150,5,1,"Wages"));
			series.add(new LineItem("India",200,6,1,"Wages"));
			series.add(new LineItem("Sweden",400,7,1,"Wages"));
			series.add(new LineItem("Finland",100,8,1,"Wages"));
			series.add(new LineItem("Romania",30,9,1,"Wages"));
			
			series.add(new LineItem("Germany",70,1,2,"Stocks"));
			series.add(new LineItem("France",350,2,2,"Stocks"));
			series.add(new LineItem("USA",64,3,2,"Stocks"));
			series.add(new LineItem("UK",154,4,2,"Stocks"));
			series.add(new LineItem("Norway",250,5,2,"Stocks"));
			series.add(new LineItem("India",300,6,2,"Stocks"));
			series.add(new LineItem("Sweden",200,7,2,"Stocks"));
			series.add(new LineItem("Finland",90,8,2,"Stocks"));
			series.add(new LineItem("Romania",45,9,2,"Stocks"));*/
			
			/*series.add(new LineItem(10,"Germany",1,1,"Wages"));
			series.add(new LineItem(20,"France",2,1,"Wages"));
			series.add(new LineItem(35,"USA",3,1,"Wages"));
			series.add(new LineItem(100,"UK",4,1,"Wages"));
			series.add(new LineItem(150,"Norway",5,1,"Wages"));
			series.add(new LineItem(200,"India",6,1,"Wages"));
			series.add(new LineItem(400,"Sweden",7,1,"Wages"));
			series.add(new LineItem(50,"Finland",8,1,"Wages"));
			series.add(new LineItem(30,"Romania",9,1,"Wages"));
			
			series.add(new LineItem(70,"Germany",1,2,"Stocks"));
			series.add(new LineItem(350,"France",2,2,"Stocks"));
			series.add(new LineItem(64,"USA",3,2,"Stocks"));
			series.add(new LineItem(154,"UK",4,2,"Stocks"));
			series.add(new LineItem(250,"Norway",5,2,"Stocks"));
			series.add(new LineItem(300,"India",6,2,"Stocks"));
			series.add(new LineItem(200,"Sweden",7,2,"Stocks"));
			series.add(new LineItem(30,"Finland",8,2,"Stocks"));
			series.add(new LineItem(32,"Romania",9,2,"Stocks"));*/
			
			series.add(new LineItem("Bayer","Germany",1,"Germany",1,"Life-threatening",Color.BLUE));
			series.add(new LineItem("Dassault","France",2,"France",1,"Life-threatening",Color.BLUE));
			series.add(new LineItem("Pfizer","USA",3,"USA",1,"Life-threatening",Color.BLUE));
			series.add(new LineItem("Astra","UK",4,"UK",1,"Life-threatening",Color.BLUE));
			series.add(new LineItem("Caterpillar","Norway",5,"Norway",1,"Life-threatening",Color.BLUE));
			series.add(new LineItem("Ericsson","Finland",6,"Finland",1,"Life-threatening",Color.BLUE));
			
			series.add(new LineItem("ABB","Germany",1,"Germany",2,"Stocks",Color.GREEN));
			series.add(new LineItem("Plasse","France",2,"France",2,"Stocks",Color.GREEN));
			series.add(new LineItem("Sachs","USA",3,"USA",2,"Stocks",Color.GREEN));
			series.add(new LineItem("Fin","UK",4,"UK",2,"Stocks",Color.GREEN));
			series.add(new LineItem("Morgan","Norway",5,"Norway",2,"Stocks",Color.GREEN));
			series.add(new LineItem("Skype","Finland",6,"Finland",2,"Stocks",Color.GREEN));
			

			plot.setXLabelName("Median");
			plot.setYLabelName("Data Value");
			plot.setSmoothness(0.2f);
			plot.setChartType(LineType.LINE);
			plot.setSeries(series,2);
			
			
			

			
		}
      /*  if(type.equals(Type.RANGE))
        {
        	
        	setContentView(R.layout.activity_range_chart);
        	
        	
        	RangeChart plot =(RangeChart)findViewById(R.id.Chart);
			
			List<RangeItem> series = new ArrayList<RangeItem> ();
			
//			series.add(new RangeItem(30,45,100,"Germany"));
//			series.add(new RangeItem(200,400,600,"USA"));
//			series.add(new RangeItem(350,500,750,"UK"));
//			
//			series.add(new RangeItem(350,500,1200,"Norway"));
//			series.add(new RangeItem(20,45,80,"France"));
			
			
				series.add(new RangeItem(-0.08,0.05,0.1,"Germany"));
				series.add(new RangeItem(0.12,0.1,0.2,"USA"));
				series.add(new RangeItem(0.0,0.05,0.1,"UK"));
////			
////			series.add(new RangeItem(350,500,1200,"Norway"));
				series.add(new RangeItem(0.03,0.06,0.15,"France"));
			
			
//			
			
//			series.add(new RangeItem(0.00003,0.0,0.0001,"Germany"));
//			series.add(new RangeItem(0.00001,0.00001,0.0002,"USA"));
//			series.add(new RangeItem(0.00005,0.00004,0.00008,"UK"));
//////		
//////		series.add(new RangeItem(350,500,1200,"Norway"));
//			series.add(new RangeItem(0.00006,0.0003,0.0005,"France"));
			
			
			series.add(new RangeItem(100000,300000,1000000,"Germany"));
			series.add(new RangeItem(100000,150000,200000,"USA"));
			series.add(new RangeItem(60000,200000,300000,"UK"));
////		
////		series.add(new RangeItem(350,500,1200,"Norway"));
			series.add(new RangeItem(300000,600000,800000,"France"));
			
			plot.setXLabelName("Country");
			plot.setYLabelName("Wages");
			
			
			
			plot.setSeries(series);
        }*/
        if(type.equals(Type.WINDROSE))
        {
        
        	setContentView(R.layout.activity_linear);
        	
        	System.out.println("Starting Pie");
        	
        	
        	
        	LinearLayout plot =(LinearLayout)findViewById(R.id.Root);
    		
        	
    		WindRose chart = new WindRose(this);
			
			
			List<CategoryItem> series = new ArrayList<CategoryItem>();
			
			series.add(new CategoryItem("10-20","Germany", 5));
			series.add(new CategoryItem("20-30","Germany", 10));
			series.add(new CategoryItem("30-50","Germany", 60));
			series.add(new CategoryItem("40-60","Germany", 50));
            series.add(new CategoryItem("60-80","Germany", 100));
			
			series.add(new CategoryItem("10-20","USA", 10));
			series.add(new CategoryItem("20-30","USA", 20));
			series.add(new CategoryItem("30-50","USA", 40));
			series.add(new CategoryItem("40-60","USA", 15));
            series.add(new CategoryItem("60-80","USA", 30));
			
			series.add(new CategoryItem("10-20","UK", 2));
			series.add(new CategoryItem("20-30","UK", 16));
			series.add(new CategoryItem("30-50","UK", 30));
			series.add(new CategoryItem("40-60","UK", 35));
            series.add(new CategoryItem("60-80","UK", 70));
			
			 chart.setAssignSeriesColors(true);
			 chart.setColorsAsPrimary(false);
		     chart.setAccentStartingColor(Color.RED);
		     chart.setDescription("Country Status");
			
			chart.setSeries(series);
			
			plot.addView(chart);
        	
        	
        	
        }
        
  /*      if(type.equals(Type.WINDROSE))
        {
        
        	setContentView(R.layout.activity_wind_rose);
        	
        	WindRose plot = (WindRose)findViewById(R.id.Chart);
			
			
			List<CategoryItem> series = new ArrayList<CategoryItem>();
			
			series.add(new CategoryItem("10-20","Germany", 5));
			series.
			add(new CategoryItem("20-30","Germany", 10));
			series.add(new CategoryItem("30-50","Germany", 60));
			series.add(new CategoryItem("40-60","Germany", 50));
			
			series.add(new CategoryItem("10-20","USA", 10));
			series.add(new CategoryItem("20-30","USA", 20));
			series.add(new CategoryItem("30-50","USA", 40));
			series.add(new CategoryItem("40-60","USA", 15));
			
			series.add(new CategoryItem("10-20","UK", 2));
			series.add(new CategoryItem("20-30","UK", 16));
			series.add(new CategoryItem("30-50","UK", 30));
			series.add(new CategoryItem("40-60","UK", 35));
			
		
			
			plot.setSeries(series);
        	
        	
        	
        }*/
        
        
        if(type.equals(Type.SPIDERWEB))
        {
        	
        	
        	setContentView(R.layout.activity_linear);
        	
        	      	
        	
        	
        	LinearLayout plot =(LinearLayout)findViewById(R.id.Root);
    		
        	
    		SpiderWeb chart = new SpiderWeb(this);
        	
			List<CategoryItem> series = new ArrayList<CategoryItem> ();
			
			series.add(new CategoryItem("Life-threatening",Color.BLUE,"Germany",45,1));
			series.add(new CategoryItem("Life-threatening",Color.BLUE,"USA",60,1));
			series.add(new CategoryItem("Life-threatening",Color.BLUE,"UK",250,1));
			series.add(new CategoryItem("Life-threatening",Color.BLUE,"France",390,1));
			
			series.add(new CategoryItem("Stocks",Color.GREEN,"Germany",325,2));
			series.add(new CategoryItem("Stocks",Color.GREEN,"USA",200,2));
			series.add(new CategoryItem("Stocks",Color.GREEN,"UK",400,2));
			series.add(new CategoryItem("Stocks",Color.GREEN,"France",620,2));
			
			chart.setAssignSeriesColors(false);
			chart.setColorsAsPrimary(false);
			chart.setAccentStartingColor(Color.RED);
			chart.setSeries(series, 2);
			chart.setDescription("Country Status");
			plot.addView(chart);
        }
        
        
         if(type.equals(Type.POLAR))
    	
    	
        {
        	
        	 setContentView(R.layout.activity_linear);
         	
         	System.out.println("Starting Polar");
         	
         	
         	
         	LinearLayout plot =(LinearLayout)findViewById(R.id.Root);
     		
         	
     		PolarChart chart = new PolarChart(this);
			
			
     		List<CategoryItem> series = new ArrayList<CategoryItem> ();
			
//			series.add(new CategoryItem("Wages",30,45000,1));
//			series.add(new CategoryItem("Wages",60,60000,1));
//			series.add(new CategoryItem("Wages",145,250000,1));
//			series.add(new CategoryItem("Wages",230,390000,1));
//			
//			series.add(new CategoryItem("Stocks",30,200000,2));
//			series.add(new CategoryItem("Stocks",60,20000,2));
//			series.add(new CategoryItem("Stocks",145,400000,2));
//			series.add(new CategoryItem("Stocks",230,620000,2));
			
			
			
		/*	series.add(new CategoryItem("Wages",30,45,1));
			series.add(new CategoryItem("Wages",60,60,1));
			series.add(new CategoryItem("Wages",145,250,1));
			series.add(new CategoryItem("Wages",230,390,1));
			
			series.add(new CategoryItem("Stocks",30,200,2));
			series.add(new CategoryItem("Stocks",60,20,2));
			series.add(new CategoryItem("Stocks",145,400,2));
			series.add(new CategoryItem("Stocks",230,620,2));
			*/
//				series.add(new CategoryItem("Wages",30,0.04,1));
//				series.add(new CategoryItem("Wages",60,0.2,1));
//				series.add(new CategoryItem("Wages",145,0.06,1));
//				series.add(new CategoryItem("Wages",230,0.11,1));
//				
//				series.add(new CategoryItem("Stocks",30,0.15,2));
//				series.add(new CategoryItem("Stocks",60,0.08,2));
//				series.add(new CategoryItem("Stocks",145,0.16,2));
//				series.add(new CategoryItem("Stocks",230,0.22,2));
//		
			
			
			
//			series.add(new CategoryItem("Wages",30,0.004,1));
//			series.add(new CategoryItem("Wages",60,0.02,1));
//			series.add(new CategoryItem("Wages",145,0.006,1));
//			series.add(new CategoryItem("Wages",230,0.01,1));
//			
//			series.add(new CategoryItem("Stocks",30,0.015,2));
//			series.add(new CategoryItem("Stocks",60,0.008,2));
//			series.add(new CategoryItem("Stocks",145,0.016,2));
//			series.add(new CategoryItem("Stocks",230,0.022,2));
			
			
			series.add(new CategoryItem("Life-threatening",Color.GREEN,"Germany",45,1));
			series.add(new CategoryItem("Life-threatening",Color.GREEN,"USA",60,1));
			series.add(new CategoryItem("Life-threatening",Color.GREEN,"UK",250,1));
			series.add(new CategoryItem("Life-threatening",Color.GREEN,"France",390,1));
			
			series.add(new CategoryItem("Stocks",Color.BLUE,"Germany",145,2));
			series.add(new CategoryItem("Stocks",Color.BLUE,"USA",62,2));
			series.add(new CategoryItem("Stocks",Color.BLUE,"UK",400,2));
			series.add(new CategoryItem("Stocks",Color.BLUE,"France",620,2));
			
			chart.setAssignSeriesColors(false);
			chart.setColorsAsPrimary(false);
			chart.setAccentStartingColor(Color.RED);
			chart.setCategoryAsTrue(true);
			chart.setSeries(series, 2);
		   	Map<String,ChartType> types = new HashMap<String,ChartType>();
		   	types.put("Wages", ChartType.CURVE);
		   	types.put("Stocks", ChartType.AREA);
			;
			//ChartType [] types = new ChartType[]{ChartType.LINE,ChartType.AREA};
			chart.setSeriesType(types);
			chart.setDescription("Country Status");
			
			plot.addView(chart);
        }
        
       /* if(type.equals(Type.POLAR))
        	
        	
        {
        	
        	setContentView(R.layout.activity_polar);
        	PolarChart plot = (PolarChart)findViewById(R.id.Chart);
			List<CategoryItem> series = new ArrayList<CategoryItem> ();
			
			
			
//			series.add(new CategoryItem("Wages",30,45000,1));
//			series.add(new CategoryItem("Wages",60,60000,1));
//			series.add(new CategoryItem("Wages",145,250000,1));
//			series.add(new CategoryItem("Wages",230,390000,1));
//			
//			series.add(new CategoryItem("Stocks",30,200000,2));
//			series.add(new CategoryItem("Stocks",60,20000,2));
//			series.add(new CategoryItem("Stocks",145,400000,2));
//			series.add(new CategoryItem("Stocks",230,620000,2));
			
			
			
			series.add(new CategoryItem("Wages",30,45,1));
			series.add(new CategoryItem("Wages",60,60,1));
			series.add(new CategoryItem("Wages",145,250,1));
			series.add(new CategoryItem("Wages",230,390,1));
			
			series.add(new CategoryItem("Stocks",30,200,2));
			series.add(new CategoryItem("Stocks",60,20,2));
			series.add(new CategoryItem("Stocks",145,400,2));
			series.add(new CategoryItem("Stocks",230,620,2));
			
//				series.add(new CategoryItem("Wages",30,0.04,1));
//				series.add(new CategoryItem("Wages",60,0.2,1));
//				series.add(new CategoryItem("Wages",145,0.06,1));
//				series.add(new CategoryItem("Wages",230,0.11,1));
//				
//				series.add(new CategoryItem("Stocks",30,0.15,2));
//				series.add(new CategoryItem("Stocks",60,0.08,2));
//				series.add(new CategoryItem("Stocks",145,0.16,2));
//				series.add(new CategoryItem("Stocks",230,0.22,2));
//		
			
			
			
//			series.add(new CategoryItem("Wages",30,0.004,1));
//			series.add(new CategoryItem("Wages",60,0.02,1));
//			series.add(new CategoryItem("Wages",145,0.006,1));
//			series.add(new CategoryItem("Wages",230,0.01,1));
//			
//			series.add(new CategoryItem("Stocks",30,0.015,2));
//			series.add(new CategoryItem("Stocks",60,0.008,2));
//			series.add(new CategoryItem("Stocks",145,0.016,2));
//			series.add(new CategoryItem("Stocks",230,0.022,2));
			
			
			series.add(new CategoryItem("Wages","Germany",45,1));
			series.add(new CategoryItem("Wages","USA",60,1));
			series.add(new CategoryItem("Wages","UK",250,1));
			series.add(new CategoryItem("Wages","France",390,1));
			
			series.add(new CategoryItem("Stocks","Germany",145,2));
			series.add(new CategoryItem("Stocks","USA",62,2));
			series.add(new CategoryItem("Stocks","UK",400,2));
			series.add(new CategoryItem("Stocks","France",620,2));
			
			
			
			plot.setCategoryAsTrue(true);
			plot.setSeries(series, 2);
		   	Map<String,ChartType> types = new HashMap<String,ChartType>();
		   	types.put("Wages", ChartType.CURVE);
		   	types.put("Stocks", ChartType.AREA);
			
			//ChartType [] types = new ChartType[]{ChartType.LINE,ChartType.AREA};
			plot.setSeriesType(types);
        }*/
        

        if(type.equals(Type.STACKEDBAR))
        	
        {
        	
        		setContentView(R.layout.activity_stacked_bar);
        		
        		
        		System.out.println("STACKED BAR");
        		
        		StackedBarChart plot = (StackedBarChart)findViewById(R.id.Chart);
    			
    			List<MultipleSeriesItem> series = new ArrayList<MultipleSeriesItem> ();
    			
    			//int series,String xlabel, double y,String seriesname
    			
    			
    			//FOR X IS CAT, Y IS NUM 
    			
    			series.add(new MultipleSeriesItem(1,"Germany",45,"Life-threatening",Color.YELLOW,"45"));
    			series.add(new MultipleSeriesItem(1,"USA",60,"Life-threatening",Color.YELLOW,"60"));
    			series.add(new MultipleSeriesItem(1,"UK",250,"Life-threatening",Color.YELLOW,"250"));
    			series.add(new MultipleSeriesItem(1,"France",390,"Life-threatening",Color.YELLOW,"390"));
    			
    			series.add(new MultipleSeriesItem(2,"Germany",145,"Stocks",Color.GREEN,"145"));
    			series.add(new MultipleSeriesItem(2,"USA",62,"Stocks",Color.GREEN,"62"));
    			series.add(new MultipleSeriesItem(2,"UK",400,"Stocks",Color.GREEN,"400"));
    			series.add(new MultipleSeriesItem(2,"France",620,"Stocks",Color.GREEN,"620"));
    			
    			plot.setXLabelName("Country");
    			plot.setYLabelName("Median Value");
    			
    			//FOR Y IS CAT, X IS NUM 
    			
    			/*series.add(new MultipleSeriesItem(1,45,"Germany","Wages"));
    			series.add(new MultipleSeriesItem(1,60,"USA","Wages"));
    			series.add(new MultipleSeriesItem(1,250,"UK","Wages"));
    			series.add(new MultipleSeriesItem(1,390,"France","Wages"));
    			
    			series.add(new MultipleSeriesItem(2,145,"Germany","Stocks"));
    			series.add(new MultipleSeriesItem(2,62,"USA","Stocks"));
    			series.add(new MultipleSeriesItem(2,400,"UK","Stocks"));
    			series.add(new MultipleSeriesItem(2,620,"France","Stocks"));*/
    			
    			
    			/*plot.setXLabelName("Median Value");
    			plot.setYLabelName("Country");*/
    			
    			
    			plot.setSeries(series,2);
    			//plot.setNumSeries(2);
		        
		        
		        
        }
        
    	if(type.equals(Type.BUBBLE))
        	
    	{
    	
    		setContentView(R.layout.activity_linear);
    		
    		
    		System.out.println("BUBBLE");
    		LinearLayout plot =(LinearLayout)findViewById(R.id.Root);
    		
    		
    		BubbleChart chart = new BubbleChart(this);
	        
	        List<MultipleSeriesItem> series = new ArrayList<MultipleSeriesItem> ();
	        

	        
	        
		      series.add(new MultipleSeriesItem(1,50, "Heart",20,"Germany","Life-threatening",Color.BLUE));
		      series.add(new MultipleSeriesItem(1,150,"Brain",10,"France","Life-Threatening",Color.BLUE));
		      series.add(new MultipleSeriesItem(1,200,"Liver",35,"UK","Life-threatening",Color.BLUE));
		      series.add(new MultipleSeriesItem(1,350,"Spleen",45,"USA","Life-threatening",Color.BLUE));
		      
		      series.add(new MultipleSeriesItem(1,700,"Heart",20,"Finland","Life-threatening",Color.BLUE));
		      
		      series.add(new MultipleSeriesItem(1,1000,"SI",40,"Sweden","Life-threatening",Color.BLUE));
		      series.add(new MultipleSeriesItem(1,56,"SI",80,"Italy","Life-threatening",Color.BLUE));
		      
		      
		      series.add(new MultipleSeriesItem(2,450, "Heart",30,"Germany","Weight",Color.GREEN));
		      series.add(new MultipleSeriesItem(2,200,"Brain",40,"France","Weight",Color.GREEN));
		      series.add(new MultipleSeriesItem(2,800,"Liver",90,"UK","Weight",Color.GREEN));
		      series.add(new MultipleSeriesItem(2,45,"Spleen",100,"USA","Weight",Color.GREEN));
		      
		      series.add(new MultipleSeriesItem(2,92,"Heart",45,"Finland","Weight",Color.GREEN));
		      
		      series.add(new MultipleSeriesItem(2,750,"SI",60,"Sweden","Weight",Color.GREEN));
		      series.add(new MultipleSeriesItem(2,900,"SI",55,"Italy","Weight",Color.GREEN));
	        

	        
	        
	        
	        chart.setXLabelName("Vital Values");
	        chart.setYLabelName("Number Values");
	        chart.setAssignSeriesColors(false);
	        chart.setSeries(series,2);
	        chart.setAsHorizontal(true);
	        chart.setLabelSeparation(8);
	        chart.setTextSize(12);
	        chart.setAxisLabelSize(12);
	        chart.setGridColor(Color.GRAY);
	        chart.setTextColor(Color.BLACK);
	        chart.setXNum(true);
	        chart.setYCategory(true);
	        chart.showGridLines(true);
	        chart.showDrawLabels(true);
	        chart.setBoundingBoxColor(Color.BLACK);
	        
	        
	        
	        plot.addView(chart);
	        
	        
	        
    }
        
       
        
	}
}
