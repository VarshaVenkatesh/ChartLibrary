package com.wechseltech.chartlibrary;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public abstract class ChartView extends View  {
	
	float mLabelSeparation=convertDptoPixel(8);
	int textcolor=Color.BLACK;
	int arrowcolor = Color.RED;
	int gridcolor=Color.BLACK;
	int rectcolor=Color.BLACK;
	int boundarycolor= Color.YELLOW;
	int highlightcolor= Color.RED;
	float mLabelSize=convertDptoPixel(10);
	int gaugecolor= Color.BLACK;
	Boolean isHorizontal=false,showgrid=true;
	float barspace=80;
	float legendSize=convertDptoPixel(20);
	
	String textdesc ="";
	Boolean XisNum=false,YisNum=false, XisCategory=false,YisCategory=false;
	
	float axisTextSize=convertDptoPixel(12);
	float mTextSize=convertDptoPixel(12),mAxisLabelSize=convertDptoPixel(12);
	float markerradii=5;
	Boolean drawLabels=false;
	Boolean assignColors=true;
	Boolean assignSeriesColors=true;
	Boolean useSeriesPrimary=true;
	
	int accentprimarycolor=Color.RED;
	LineType type = LineType.LINE;
	float smoothness =0.33f;
	float first=0.33f;
	float second = 1-first;
	
	 float drawSpace=convertDptoPixel(5);
	 
	
	public ChartView(Context context) {
		super(context);
		
		
	}
	
	
	public ChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		
	}


	public ChartView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
		
	}
	

	
	/**
	 * 
	 * @param mLabelSeparation set Separation between axis and text labels
	 */
	public void setLabelSeparation(int mLabelSeparation)
	{
		this.mLabelSeparation=convertDptoPixel(mLabelSeparation);
	}
	
	public float getLabelSeparation()
	{
		return mLabelSeparation;
	}
	
	/**
	 * 
	 * @param textcolor set color of textlabels, axislabels
	 */
	public void setTextColor(int textcolor)
	{
		this.textcolor =textcolor;
	}
	
	public int getTextColor()
	{
		return textcolor;
	}
	
	/**
	 * 
	 * @param gridcolor  set color of gridlines
	 */
	public void setGridColor(int gridcolor)
	{
		this.gridcolor =gridcolor;
	}
	
	public int getGridColor()
	{
		return gridcolor;
	}
	
	/**
	 * 
	 * @param rectcolor set color of rectangular box
	 */
	public void setBoundingBoxColor(int rectcolor)
	{
		this.rectcolor =rectcolor;
	}
	
	public int getBoundingBoxColor()
	{
		return rectcolor;
	}
	
	/**
	 * 
	 * @param textsize size of textlabels
	 */
	public void setTextSize(float textsize)
	{
	
		this.mTextSize =convertDptoPixel(textsize);
	}
	
	public float getTextSize()
	{
		return mTextSize;
	}
	
	/**
	 * 
	 * @param axistextsize  size of axislabels
	 */
	public void setAxisTextSize(float axistextsize)
	{
	
		this.axisTextSize =convertDptoPixel(axistextsize);
	}
	
	public float getAxisTextSize()
	{
		return axisTextSize;
	}
	
	
	/**
	 * 
	 * @param legendsize size of legend box
	 */
	public void setLegendSize(float legendsize)
	{
		this.legendSize =convertDptoPixel(legendsize);
	}
	
	public float getLegendSize()
	{
		return legendSize;
	}
	
	/**
	 * 
	 * @param mAxisLabelSize set size of axis labels
	 */
	public void setAxisLabelSize(float mAxisLabelSize)
	{
		this.mAxisLabelSize =convertDptoPixel(mAxisLabelSize);
	}
	
	public float getAxisLabelSize()
	{
		return mAxisLabelSize;
	}
	
	public Boolean isXNum()
	{
		return XisNum;
	}
	
	/**
	 * 
	 * @param XisNum  set x-axis values as numeric
	 */
	public void setXNum(Boolean XisNum)
	{
		this.XisNum=XisNum;
	}
	
	public Boolean isYNum()
	{
		return YisNum;
	}
	
	/**
	 * 
	 * @param YisNum set y-axis values as numeric
	 */
	public void setYNum(Boolean YisNum)
	{
		this.YisNum=YisNum;
	}
	
	
	public Boolean isYCategory()
	{
		return YisCategory;
	}
	
	/**
	 * 
	 * @param YisCategory set y-axis as category
	 */
	public void setYCategory(Boolean YisCategory)
	{
		this.YisCategory=YisCategory;
	}
	
	public Boolean isXCategory()
	{
		return XisCategory;
	}
	
	/**
	 * 
	 * @param XisCategory set x-axis as category
	 */
	public void setXCategory(Boolean XisCategory)
	{
		this.XisCategory=XisCategory;
	}
	
	public Boolean isHorizontal()
	{
		return isHorizontal;
	}
	
	/**
	 * 
	 * @param isHorizontal set isHorizontal to true to flip axes
	 */
	public void setAsHorizontal(Boolean isHorizontal)
	{
		this.isHorizontal=isHorizontal;
	}
	
	public Boolean isGridShown()
	{
		return showgrid;
	}
	
	/**
	 * 
	 * @param showgrid set true if gridlines should be shown
	 */
	public void showGridLines(Boolean showgrid)
	{
		this.showgrid=showgrid;
	}
	
	
	/**
	 * 
	 * @param markerradii size of line point for line chart and area chart
	 */
	public void setMarkerRadii(float markerradii)
	{
		this.markerradii= markerradii;
	}
	
	public float getMarkerRadii()
	{
		return markerradii;
	}
	

	/**
	 * 
	 * @param type for line chart and area chart - cubic line or ordinary line
	 * 
	 */
	public void setChartType(LineType type)
	{
		this.type=type;
	}
	
	public LineType getChartType()
	{
		return type;
	}
	
	
	/**
	 * 
	 * @param textdesc for Spider Web, Polar Chart, Pie Chart and Doughnut Chart
	 * description of the graph
	 */
	public void setDescription(String textdesc)
	{
		this.textdesc=textdesc;
	}
	
	public String getDescription()
	{
		return textdesc;
	}
	
	/**
	 * 
	 * @param smoothness for cubic line - float value  0 to 0.5 - 
	 * 0.5 is the most smooth - points may be left out
	 */
	public void setSmoothness(float smoothness)
	{
		this.smoothness=smoothness;
		this.first=smoothness;
		second = 1-first;
	}
	
	public float getSmoothness()
	{
		return smoothness;
	}
	
	
	/**
	 * 
	 * @param space set spacing between category text labels
	 */
	public void setSpacing(float space)
	{
		this.barspace=space;
	}
	
	public float getSpacing()
	{
		return barspace;
	}
	
	/**
	 * 
	 * @param legendSeparation separation between legend values
	 */
	
	public void setLegendSeparation(float legendSeparation)
	{
		this.drawSpace=convertDptoPixel(legendSeparation);
	}
	
	public float getLegendSeparation()
	{
		return drawSpace;
	}
	
	
	
	public Boolean getDrawLabels()
	{
		return drawLabels;
	}
	
	/**
	 * 
	 * @param drawLabels assign label to each point
	 */
	public void showDrawLabels(Boolean drawLabels)
	{
		this.drawLabels=drawLabels;
	}
	
	
	
	public Boolean getAssignSeriesColors()
	{
		return assignSeriesColors;
	}
	
	/**
	 * 
	 * @param assignSeriesColors -assign colors to each series in a plot automatically
	 * - choice is between primary colors and accentcolors, false if series colors are going
	 *  to be specfied in the constructor
	 */ 
	public void setAssignSeriesColors(Boolean assignSeriesColors)
	{
		this.assignSeriesColors=assignSeriesColors;
	}
	
	public Boolean getColorsAsPrimary()
	{
		return useSeriesPrimary;
	}
	
	/**
	 * 
	 * @param useSeriesPrimary  - set if true if  primary colors are needed,
	 *  false to have accent colors
	 */
	public void setColorsAsPrimary(Boolean useSeriesPrimary)
	{
		this.useSeriesPrimary=useSeriesPrimary;
	}
	
	public int getAccentStartingColor()
	{
		return accentprimarycolor;
	}
	
	/**
	 * 
	 * @param accentColor - set the tint for the accent color 
	 */
	public void setAccentStartingColor(int accentColor)
	{
		this.accentprimarycolor=accentColor;
	}
	
	
	
	
	public  float convertDptoPixel(float dp)
	{
		
		
	float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,getResources().getDisplayMetrics());
	
	return px;
	
	}

}
