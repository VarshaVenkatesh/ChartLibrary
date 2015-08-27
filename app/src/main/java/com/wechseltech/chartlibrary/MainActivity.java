package com.wechseltech.chartlibrary;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ListView list = new ListView(this);
        
        List<String> array = new ArrayList<String>();
        array.add("Pie Chart");
        array.add("Doughnut Chart");
        array.add("Bubble Chart");
        array.add("Range Chart");
        array.add("Wind Rose");
        array.add("Negative Stack Bar Chart");
        array.add("Multiple Series Bar Chart New");
        array.add("Multiple Series Bar Chart");
        array.add("Line Chart ");
        array.add("Line Chart XML");
        array.add("Gauge Chart");
        array.add("Area Chart");
        array.add("Scatter Plot");
        array.add("Spider Web");
        array.add("Stacked Bar Chart");
        array.add("Stacked Bar Chart Horizontal");
        array.add("Polar Chart");
        
        //array.add("Combination Chart");
      //array.add("Heat Map");
      //array.add("Pyramid Chart");
      //array.add("Map Plot");
      //array.add("Box Plot");
        
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        		android.R.layout.simple_list_item_1,
        		array);
        
        list.setAdapter(adapter);
        
        System.out.println("LISTING");
        
        LinearLayout root = (LinearLayout) findViewById(R.id.Root);
        
        root.addView(list);
        
        
        System.out.println("LISTING");
        
        
        list.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				String clicked = parent.getItemAtPosition(position).toString();
				System.out.println("Clicked" + clicked);
				
				if(parent.getItemAtPosition(position).toString().equals("Polar Chart"))
					
					
				{
					
					 
					 
					getPolarPlot();
					
				}
				
				 if(parent.getItemAtPosition(position).toString().equals("Bubble Chart"))
				 {
					 
					 
					 	getBubblePlot();
				 
				 }
				 
				 if(parent.getItemAtPosition(position).toString().equals("Pie Chart"))
				 {
					 System.out.println("Clicked Pie");
					 getPiePlot();
					 
				 }
				 
				 
				 if(parent.getItemAtPosition(position).toString().equals("Doughnut Chart"))
				 {
					  getDoughnutPlot();
					  
				 }
//				 
				 if(parent.getItemAtPosition(position).toString().equals("Range Chart"))
					 getRangePlot();
//				 
				 if(parent.getItemAtPosition(position).toString().equals("Wind Rose"))
					 getWindRosePlot();
//				 
				 if(parent.getItemAtPosition(position).toString().equals("Negative Stack Bar Chart"))
				 getNegativeStackBarPlot();
//				 
				 if(parent.getItemAtPosition(position).toString().equals("Multiple Series Bar Chart"))
					 getMultipleSeriesBarPlot();
				 
				 if(parent.getItemAtPosition(position).toString().equals("Multiple Series Bar Chart New"))
					 getMultipleSeriesBarNewPlot();
//				 
			 if(parent.getItemAtPosition(position).toString().equals("Stacked Bar Chart"))
				 getStackBarPlot();
			 
			 
			 if(parent.getItemAtPosition(position).toString().equals("Stacked Bar Chart Horizontal"))
				 getStackBarPlotHorizontal();
//				 
				 if(parent.getItemAtPosition(position).toString().equals("Line Chart"))
					 getLinePlot();
				 
				 if(parent.getItemAtPosition(position).toString().equals("Line Chart XML"))
					 getLinePlotNew();
//				 
				 if(parent.getItemAtPosition(position).toString().equals("Area Chart"))
				 	getAreaPlot();
//				 
				 if(parent.getItemAtPosition(position).toString().equals("Scatter Plot"))
					  getScatterPlot();
//				 
				 if(parent.getItemAtPosition(position).toString().equals("Gauge Chart"))
					 getGaugePlot();
//				 
				 if(parent.getItemAtPosition(position).toString().equals("Spider Web"))
				 {
					getSpiderWeb();
					
				 }
				 
				
			}
			
        });
    
    
    }
    
    protected void getLinePlotNew() {
		
		
		Intent intent = new Intent(this,GenericChartActivity.class);
		
		intent.putExtra("type", Type.LINECHARTXML);
		
		startActivity(intent);
	
}






	protected void getMultipleSeriesBarNewPlot() {
	
		
		Intent intent = new Intent(this,GenericChartActivity.class);
		
		intent.putExtra("type", Type.MULTIPLESERIESBARCHARTNEW);
		
		startActivity(intent);
	
}






	protected void getMultipleSeriesBarPlot() {
		
		Intent intent = new Intent(this,GenericChartActivity.class);
		
		intent.putExtra("type", Type.MULTIPLESERIESBARCHART);
		
		startActivity(intent);
		
		
		
	}
	
	private void getAreaPlot() {
		
		
		Intent intent = new Intent(this,GenericChartActivity.class);
		
		intent.putExtra("type", Type.AREA);
		
		startActivity(intent);
		
	}
	

	protected void getPolarPlot() {
		
		System.out.println("Calling Activity SPIDER");
		Intent intent = new Intent(this,GenericChartActivity.class);
		
		intent.putExtra("type", Type.POLAR);
		
		startActivity(intent);
		
		
		
	}

	
	protected void getSpiderWeb() {
		
		//CategoryItem(String seriesname, String category, double val,int series)
		
		
		System.out.println("Calling Activity SPIDER");
		Intent intent = new Intent(this,GenericChartActivity.class);
		
		intent.putExtra("type", Type.SPIDERWEB);
		
		startActivity(intent);
		
		
		
		
	}

	protected void getGaugePlot() {
		
		Intent intent = new Intent(this,GenericChartActivity.class);
		
		intent.putExtra("type", Type.GAUGE);
		
		startActivity(intent);
		
	}

	protected void getScatterPlot() {
		
		
		Intent intent = new Intent(this,GenericChartActivity.class);
		
		intent.putExtra("type", Type.SCATTERPLOT);
		
		startActivity(intent);
		
		
	}

	protected void getLinePlot() {
		
		
		Intent intent = new Intent(this,GenericChartActivity.class);
		
		intent.putExtra("type", Type.LINECHART);
		
		startActivity(intent);
		
		
	}

	protected void getStackBarPlot() {
		
		Intent intent = new Intent(this,GenericChartActivity.class);
		
		intent.putExtra("type", Type.STACKEDBAR);
		
		startActivity(intent);
		
	}
	
	protected void getStackBarPlotHorizontal() {
		
		Intent intent = new Intent(this,GenericChartActivity.class);
		
		intent.putExtra("type", Type.STACKEDBARHORIZONTAL);
		
		startActivity(intent);
		
	}

	protected void getNegativeStackBarPlot() {
		
		
		Intent intent = new Intent(this,GenericChartActivity.class);
		
		intent.putExtra("type", Type.NEGATIVESTACKBARCHART);
		
		startActivity(intent);
		
		
		
	}

	//TODO FIX THIS
	protected void getWindRosePlot() {
		
		
		
		Intent intent = new Intent(this,GenericChartActivity.class);
		
		intent.putExtra("type", Type.WINDROSE);
		
		startActivity(intent);
	
		
	}

	protected void getRangePlot() {
		
		
		Intent intent = new Intent(this,GenericChartActivity.class);
		
		intent.putExtra("type", Type.RANGE);
		
		startActivity(intent);
		
		
		
	}

	protected void getDoughnutPlot() {
		
		
		
		Intent intent = new Intent(this,GenericChartActivity.class);
		
		intent.putExtra("type", Type.DOUGHNUT);
		
		startActivity(intent);
		
		
		
		
	}

	protected void getPiePlot() {
		
		
		
			System.out.println("Calling Activity Pie");
			Intent intent = new Intent(this,GenericChartActivity.class);
			
			intent.putExtra("type", Type.PIE);
			
			startActivity(intent);
			
//			PieChart plot =(PieChart)findViewById(R.id.Chart);
//	        
//	        List<PieItem> series = new ArrayList<PieItem> ();
//	        
//	        series.add(new PieItem("Zeus", 400));
//	        series.add(new PieItem("Thor",150));
//	        series.add(new PieItem("Hera",200));
//	        series.add(new PieItem("David",350));
//	        
//	        plot.setSeries(series);
//	       
	        
	        
		
	}

	protected void getBubblePlot() {
		
		
		
		Intent intent = new Intent(this,GenericChartActivity.class);
		
		intent.putExtra("type", Type.BUBBLE);
		
		startActivity(intent);
		
		
	        
//	      series.add(new Item(50, "Heart",20,"Don"));
//	      series.add(new Item(150,"Brain",10,"Do"));
//	      series.add(new Item(200,"Liver",35,"Don"));
//	      series.add(new Item(350,"Spleen",45,"Do"));
//	      
//	      series.add(new Item(700,"Heart",20,"Don"));
//	      
//	      series.add(new Item(1000,"Small Intestine",40,"Do"));
//	      series.add(new Item(56,"Small Intestine",80,"Don"));
//	        
//	       series.add(new Item(50,5,20,"Don"));
//	      series.add(new Item(150,20,10,"Do"));
//	      series.add(new Item(200,40,35,"Don"));
//	      series.add(new Item(350,180,45,"Do"));
//	      
//	      series.add(new Item(700,460,20,"Don"));
//	      
//	      series.add(new Item(1000,1800,40,"Do"));
//	      series.add(new Item(56,200,80,"Don"));
//	      
	        
	        
	        //LinearLayout activitybubble = (LinearLayout)findViewById(R.id.root);
	        
	        //BubblePlot plot = new BubblePlot(this);
	        
	        
//	        plot.setAsHorizontal(false);
//	        plot.setAxisLabelSize(14);
//	       
//	        plot.setBubbleNegativeColor(Color.BLUE);
//	        plot.setBubblePositiveColor(Color.YELLOW);
//	        plot.setBubbleZeroColor(Color.RED);
//	        plot.showDrawLabels(false);
//	        plot.setGridColor(Color.BLACK);
//	       
//	        plot.showGridLines(true);
//	        plot.setTextSize(14);
//	        plot.setTextColor(Color.BLACK);
//	        plot.setXNum(true);
//	        plot.setYNum(true);
	        
	        System.out.println("set series");
	       // activitybubble.addView(plot);
		
	}

}
