

package com.wechseltech.chartlibrary;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public abstract class GraphView extends View
{
	
	float mLabelSeparation=convertDptoPixel(8);
	int textcolor=Color.BLACK;
	
	int gridcolor=Color.BLACK;
	int rectcolor=Color.BLACK;
	int barcolor =Color.YELLOW;
	float mTextSize=convertDptoPixel(12),mAxisLabelSize=convertDptoPixel(12);
	float mLabelSize=convertDptoPixel(10);
	float legendSize=convertDptoPixel(20);
	Boolean isHorizontal=false,showgrid=true;
	float mBarWidth=30;
	float mBarSpacing=convertDptoPixel(10);
	
	float barspace=80;
	
	Boolean XisNum=false,YisNum=false, XisCategory=false,YisCategory=false;
	
	float drawSpace=convertDptoPixel(5);
	
	Boolean assignSeriesColors=true;
	Boolean useSeriesPrimary=true;
	int accentprimarycolor=Color.RED;
	
	
	
	
	 public EdgeEffectCompat mEdgeEffectTop;
	 public EdgeEffectCompat mEdgeEffectBottom;
	 public EdgeEffectCompat mEdgeEffectLeft;
	 public EdgeEffectCompat mEdgeEffectRight;
	 
	 
	 RectF boxbounds;
	 
	 public AxisStops hstops;
	 public AxisStops vstops;
	
	 public float[] AxisVGridLines,AxisHGridLines;
	 public RectF Viewport,coordbounds,legendBox;
	 
	 public ScaleGestureDetector mScaleGestureDetector=null;
	 public GestureDetectorCompat mGestureDetector=null;
		
	 public Point ScrollableSurface = new Point();
		
	 public Paint gPaint;
	 DrawGridLines lines;
	 
	 //int min=0;
	 
	 boolean invalidate=false;
	 
	// List<Integer> exponent;
	 
	 int hmin=0,vmin=0;
	 List<Integer> hexponent,vexponent;
	 
	 double hInterval,vInterval;
	 int  nHticks ,nVticks;
	 
	 Boolean drawLabels=false;
	 
	 Boolean isHFlipped=false,isVFlipped=false;
		
	 public boolean mEdgeEffectTopActive;
	 public boolean mEdgeEffectBottomActive;
	 public boolean mEdgeEffectLeftActive;
	 public boolean mEdgeEffectRightActive;
	 
	 
	 public int drawBoxSize;
	 public float AXIS_H_MIN;
	
	 public float AXIS_H_MAX;
	 public float AXIS_V_MIN;
	 
	 public  float AXIS_V_MAX;
	 
	public GraphView(Context context) {
		super(context);
		
		
	}
	
	
	public GraphView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		
	}


	public GraphView(Context context, AttributeSet attrs, int defStyleAttr) {
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
	 * @param barcolor set bar color for Range Chart
	 */
	public void setBarColor(int barcolor)
	{
		this.barcolor =barcolor;
	}
	
	public int getBarColor()
	{
		return barcolor;
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
	 * @param YisNum  set y-axis values as numeric
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
	 * @param legendSeparation set separation between legend Box and border of graph
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
	 * @param drawLabels assign label to the point and display it
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
	public void setAccentStartingColor(int accentprimaryColor)
	{
		this.accentprimarycolor=accentprimaryColor;
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
	 * @param mBarWidth width of bar
	 */
	public void setBarWidth(int mBarWidth)
	{
		this.mBarWidth = mBarWidth;
	}
	
	public float getBarWidth()
	{
		return  mBarWidth;
	}
	
	/**
	 * 
	 * @param mBarSpacing Spacing between multiple series bars in a category 
	 * 
	 */
	public void setBarSpacing(int mBarSpacing)
	{
		this.mBarSpacing=mBarSpacing;
	}
	
	public float getBarSpacing()
	{
		return  mBarSpacing;
	}
	

	
	/***************************************************************/
	

	
	public void drawText(Canvas canvas,
			Paint tPaint,Paint aPaint,ReturnIntervalsTicks ticks,String xlabel,
			String ylabel,
			int mAxisLabelHeight,
			int mAxisLabelWidth,Boolean isHorizontal,Boolean XisCategory,Boolean
			YisCategory,Boolean XisNum, Boolean YisNum,
			String[]uniqueX,String[] uniqueY,Paint vPaint,Paint fPaint) {
		
		
		
		 
		
		DrawTextValues values = new DrawTextValues(tPaint,aPaint,
				mAxisLabelHeight, mAxisLabelWidth,coordbounds,ticks.subLabelHeight,
				ticks.nLabelWidth,mLabelSeparation,vPaint
				,fPaint,ticks.nhLabelWidth,ticks.nvLabelWidth);
		
	 
		hexponent = new ArrayList<Integer>();
		vexponent = new ArrayList<Integer>();
	 
		if(!isHorizontal)
		{
			System.out.println(hstops.axisstops.length);
			
			
			for(int j=0;j<hstops.axisstops.length;j++)
			{
				if(XisNum)
					
				{
						 CalculateExp(hstops.axisstops,j,tPaint,ticks.nvLabelWidth,hInterval,true);
					
					
				}
				
			}
			
			for(int j=0;j<vstops.axisstops.length;j++)
			{
				if(YisNum)
					
				{
					CalculateExp(vstops.axisstops,j,tPaint,ticks.nvLabelWidth,vInterval,false);
					
					
				}
				
			}
			
			Integer [] hexps = hexponent.toArray(new Integer[hexponent.size()]);
			Arrays.sort(hexps);
			if(hexps.length>0)
			 hmin = hexps[0];
			
			System.out.println("MIN"+hmin);
			
			
			
			Integer [] vexps = vexponent.toArray(new Integer[vexponent.size()]);
			Arrays.sort(vexps);
			if(vexps.length>0)
			 vmin = vexps[0];
			
			System.out.println("MIN"+vmin);
			
			
			for(int i=1; i<hstops.axisstops.length;i++)
			{
			
				if(XisCategory)
				{
					
					
					/*values.DrawTextCategory(uniqueX[i-1],canvas,hlabel,boxbounds.bottom,i,hstops,
					coordbounds.left, coordbounds.width(),true,isHFlipped); */
					
					values.DrawTextCategory(uniqueX[i-1],canvas,coordbounds.bottom,i,hstops,
							coordbounds.left, coordbounds.width(),true,ticks.isHflipped); 
					
					
				}
		
			
				else if(XisNum)
			
				{
					
					
					if(i%2==0)
					values.DrawTextNumHExponent(canvas,xlabel,coordbounds.bottom,i,hstops 
								
								,coordbounds.left,coordbounds.width(),hInterval,true,ticks.isHflipped,hmin);
					
					
					
				}
				
				
				
				
				
			}
			
			
			
			
			System.out.println("AXIS" + mAxisLabelHeight);
			
			canvas.drawText(xlabel,0,xlabel.length(),
					coordbounds.left+coordbounds.width()/2,boxbounds.bottom-(mAxisLabelHeight/2), aPaint);
			
			if(YisNum)
			{
				
				if(vmin!=0)
					values.drawExponentVertical(canvas,coordbounds.bottom,vmin,boxbounds.left,coordbounds.height());
			}
			
			if(XisNum)
			{
				
				if(hmin!=0)
					values.drawExponentHorizontal(canvas,coordbounds.bottom,hmin,coordbounds.left,coordbounds.width()); 
			}
			
			
			
			
			for(int j=1;j<vstops.axisstops.length;j++)
			{
				
				if(YisCategory)
					
				{
					
					
					values.DrawTextCategory(uniqueY[j-1],canvas,coordbounds.bottom,j,
							vstops,boxbounds.left, coordbounds.height(),false,ticks.isVflipped);
					
					
					
				}
		
				else if(YisNum)
					
				{
				
					if(j%2==0)
					values.DrawTextNumVExponent(canvas,ylabel,coordbounds.bottom,j,vstops 
						,boxbounds.left,coordbounds.height(),vInterval,false,ticks.isVflipped,vmin);
					
					 
					
				}
				
			}
			
			
			
			
			
			
			canvas.save();
			canvas.rotate((float)(90));
			System.out.println("CANVAS ");
			canvas.drawText(ylabel,0,ylabel.length(),
				coordbounds.bottom-coordbounds.height()/2,boxbounds.left-mLabelSeparation/2, aPaint);
			canvas.restore();
			
	
			
			
			
			
		}
		
		
		
		
		else
		{
			
			for(int j=0;j<hstops.axisstops.length;j++)
			{
				if(YisNum)
					
				{
				
					 CalculateExp(hstops.axisstops,j,tPaint,ticks.nvLabelWidth,hInterval,true);
					
				}
				
			}
			
			for(int j=0;j<vstops.axisstops.length;j++)
			{
				if(XisNum)
					
				{
					CalculateExp(vstops.axisstops,j,tPaint,ticks.nvLabelWidth,vInterval,false);
					
					
				}
				
			}
			
			
			
			Integer [] hexps = hexponent.toArray(new Integer[hexponent.size()]);
			Arrays.sort(hexps);
			if(hexps.length>0)
			 hmin = hexps[0];
			
			System.out.println("MIN"+hmin);
			
			
			
			Integer [] vexps = vexponent.toArray(new Integer[vexponent.size()]);
			Arrays.sort(vexps);
			if(vexps.length>0)
			 vmin = vexps[0];
			
	
			
			System.out.println(vstops.axisstops.length);
			
			for(int i=1; i<vstops.axisstops.length;i++)
			{
				System.out.println(vstops.axisstops[i]);
			
				if(XisCategory)
				{
					values.DrawTextCategory(uniqueX[i-1],canvas,coordbounds.bottom,
							i,vstops,
							boxbounds.left, coordbounds.height(),false,ticks.isVflipped); 
				}
				
				else if(XisNum)
				{
					if(i%2==0)
					values.DrawTextNumVExponent(canvas,xlabel,coordbounds.bottom,i,vstops 
							,boxbounds.left,coordbounds.height(),vInterval,false,ticks.isVflipped,vmin);
				}
				
			}
			
			
			canvas.drawText(ylabel,0,ylabel.length(),
					coordbounds.left+coordbounds.width()/2,boxbounds.bottom-(mAxisLabelHeight/2), aPaint);
			
			canvas.save();
			canvas.rotate((float)(90));
			System.out.println("CANVAS ");
			canvas.drawText(ylabel,0,ylabel.length(),
				coordbounds.bottom-coordbounds.height()/2,boxbounds.left-mLabelSeparation/2, aPaint);
			canvas.restore();
			
			
			
			
			
			
			
			
			if(YisNum)
			{
				
				if(hmin!=0)
					values.drawExponentHorizontal(canvas,coordbounds.bottom,hmin,coordbounds.left,coordbounds.width());
			}
			
			if(XisNum)
			{
				
				if(vmin!=0)
					values.drawExponentVertical(canvas,coordbounds.bottom,vmin,boxbounds.left,coordbounds.height());
					
			}
			
			for(int i=1; i< hstops.axisstops.length;i++)
			{
			
				if(YisCategory)
			
					
				
					values.DrawTextCategory(uniqueY[i-1],canvas,coordbounds.bottom,i,hstops,
					coordbounds.left, coordbounds.width(),true,ticks.isHflipped); 
		
			
				else if(YisNum)
			
					if(i%2==0)
					values.DrawTextNumHExponent(canvas,ylabel,coordbounds.bottom,i,hstops 
						,coordbounds.left,coordbounds.width(),hInterval,true,ticks.isHflipped,hmin);
				
			}
		
		}
		
	}
	
	
	
	
private void CalculateExp(double [] stops , int i,Paint tPaint,int nLabelWidth,double interval,Boolean isH) {
		
		
		
		
	String[] outstring = formatfloat((float)stops[i],
				interval,tPaint,nLabelWidth);
		
	
	if(outstring[1]!=null)
	{
		if(isH)
		hexponent.add(Integer.parseInt(outstring[1]));
		else
		vexponent.add(Integer.parseInt(outstring[1]));
		
		
	}
	
	
		
	}

private String[] formatfloat(float floatvalue, double interval, 
		Paint tPaint,int nLabelWidth) {
	
	String numstring ;
	int numsig=0;
	
	if(interval<1)
		
	{
		if(interval!=0)
		numsig =(int)Math.ceil(-Math.log10(interval));
		
	}
	
	if(interval>1)
		
	{
		if(interval!=0)
		numsig =(int)Math.ceil(Math.log10(interval));
		
	}
	
	System.out.println("NUMSIG"+ numsig);
	
	 String append = "#.";
	 
	 String nodec = "#";
	 
	 for(int i=0; i<numsig;i++)
		 
		 append+="#";
	 
	 System.out.println("FLOATVAL" + floatvalue);
	 //TODO Find an alternative toDecimalFormat
	 if(floatvalue%1>0)
		 numstring = new DecimalFormat(append).format(floatvalue);
	 else
		 numstring = new DecimalFormat(nodec).format(floatvalue);
	 
	 
	 System.out.println("NUMSTR" + numstring);
	  float minLength = tPaint.measureText("0");
	 
	  
	  System.out.println("MINLEN" + minLength);
	  
	  
	  
	    int numchars = (int) Math.floor(nLabelWidth/minLength);
//	    
	    int trimsig =0;
	    int exp=0;
		if(numstring.length()>numchars)
		{
			if(floatvalue!=0)
			exp =(int) Math.floor(Math.log10
					(floatvalue));
			else
			exp=0;
			
			System.out.println("EXP" + exp);
			
			float value  = (float)(floatvalue
					/Math.pow(10,exp));
			float sigint = (float) (interval/Math.pow(10,exp));
			if(sigint<1)
			{
				if(sigint!=0)
				trimsig =(int)Math.ceil(-Math.log10(sigint));
				
			}
//			
			if(sigint>1)
			{
				if(sigint!=0)
				trimsig =(int)Math.ceil(Math.log10(sigint));
				
				
			}
			
			String pattern = "#.";
			 for(int i=0; i<trimsig;i++)
				 pattern.concat("#");
			numstring = new DecimalFormat(pattern).format(value);
			
			
		}
//	
	String [] strings = new String[2];
	strings[0]=numstring;
	System.out.println("EXP" + exp);
	if(exp!=0)
		strings [1] = Integer.toString(exp);
	return strings;
	
	
	
	
	
}

public void drawTextBar(Canvas canvas,Paint tPaint,Paint aPaint,
		 ReturnIntervalsTicks ticks,String xlabel,String ylabel,int mAxisLabelHeight,
		 int mAxisLabelWidth,Boolean isHorizontal,Boolean XisCategory,Boolean
		 YisCategory,Boolean XisNum, Boolean YisNum,String[]uniqueX,String[] uniqueY,Paint vPaint,Paint fPaint) {
		

	 
	 
	DrawTextValues values = new DrawTextValues(tPaint,aPaint,
			mAxisLabelHeight, mAxisLabelWidth,coordbounds,ticks.subLabelHeight,
			ticks.nLabelWidth,mLabelSeparation,vPaint
			,fPaint,ticks.nhLabelWidth,ticks.nvLabelWidth);
	
	
	 
	
		hexponent = new ArrayList<Integer>();
		vexponent = new ArrayList<Integer>();
	 
	 
		if(!isHorizontal)
		{
			System.out.println(hstops.axisstops.length);
			
			

			for(int j=0;j<hstops.axisstops.length;j++)
			{
				if(XisNum)
					
				{
						 CalculateExp(hstops.axisstops,j,tPaint,ticks.nvLabelWidth,hInterval,true);
					
					
				}
				
			}
			
			for(int j=0;j<vstops.axisstops.length;j++)
			{
				if(YisNum)
					
				{
					CalculateExp(vstops.axisstops,j,tPaint,ticks.nvLabelWidth,vInterval,false);
					
					
				}
				
			}
			
			
			Integer [] hexps = hexponent.toArray(new Integer[hexponent.size()]);
			Arrays.sort(hexps);
			if(hexps.length>0)
			 hmin = hexps[0];
			
			System.out.println("MIN"+hmin);
			
			
			
			Integer [] vexps = vexponent.toArray(new Integer[vexponent.size()]);
			Arrays.sort(vexps);
			if(vexps.length>0)
			 vmin = vexps[0];
			
			
			if(YisNum)
			{
				
				if(vmin!=0)
					values.drawExponentVertical(canvas,coordbounds.bottom,vmin,boxbounds.left,coordbounds.height());
			}
			
			if(XisNum)
			{
				
				if(hmin!=0)
					values.drawExponentHorizontal(canvas,coordbounds.bottom,hmin,coordbounds.left,coordbounds.width()); 
			}
			
			
			for(int i=1; i<hstops.axisstops.length;i++)
			{
			
				if(XisCategory)
				{
					
					
					
					
					values.DrawTextCategory(uniqueX[i-1],canvas,
					coordbounds.bottom,i,hstops,
					coordbounds.left, coordbounds.width(),true,ticks.isHflipped); 
					
					
				}
		
			
				else if(XisNum)
			
				{
					
					
					if(i%2==0)
					values.DrawTextNumHExponent(canvas,xlabel,coordbounds.bottom,i,hstops 
							
							,coordbounds.left,coordbounds.width(),hInterval,true,ticks.isHflipped,hmin);
					
				}
				
			}
			
			
			
			System.out.println("XLABEL" + xlabel);
			canvas.drawText(xlabel,0,xlabel.length(),
					coordbounds.left+coordbounds.width()/2,boxbounds.bottom-(mAxisLabelHeight/2), aPaint);
			
			System.out.println("LABEL START" + (boxbounds.bottom-(mAxisLabelHeight/2)));
			
			for(int j=1;j<vstops.axisstops.length;j++)
			{
				
				if(YisCategory)
					
				{
					System.out.println("Pos" + j);
					System.out.println(uniqueY[j-1]);
					
					values.DrawTextCategoryVerticalBar(uniqueY[j-1],canvas,ylabel,coordbounds.bottom,j,
							vstops,boxbounds.left, coordbounds.height(),false,ticks.isVflipped);
					
				}
		
				else if(YisNum)
					
				{
					/*values.DrawTextNumVerticalBar(canvas,vlabel,coordbounds.bottom,j,vstops 
						,boxbounds.left,coordbounds.height(),vInterval,false,ticks.isVflipped);*/
					
					if(j%2==0)
					values.DrawTextNumVExponent(canvas,ylabel,coordbounds.bottom,j,vstops 
							,boxbounds.left,coordbounds.height(),vInterval,false,ticks.isVflipped,vmin);
				
				}
				
			}
			
			
			
			canvas.save();
			canvas.rotate((float)(90));
			System.out.println("YLABEL" + ylabel);
			canvas.drawText(ylabel,0,ylabel.length(),
				coordbounds.bottom-coordbounds.height()/2,boxbounds.left-mLabelSeparation/2, aPaint);
			canvas.restore();
			
	
		}
		else
		{
			
			
			for(int j=0;j<hstops.axisstops.length;j++)
			{
				if(YisNum)
					
				{
				
					 CalculateExp(hstops.axisstops,j,tPaint,ticks.nvLabelWidth,hInterval,true);
					
				}
				
			}
			
			for(int j=0;j<vstops.axisstops.length;j++)
			{
				if(XisNum)
					
				{
					CalculateExp(vstops.axisstops,j,tPaint,ticks.nvLabelWidth,vInterval,false);
					
					
				}
				
			}
			
			
			Integer [] hexps = hexponent.toArray(new Integer[hexponent.size()]);
			Arrays.sort(hexps);
			if(hexps.length>0)
			 hmin = hexps[0];
			
			System.out.println("MIN"+hmin);
			
			
			
			Integer [] vexps = vexponent.toArray(new Integer[vexponent.size()]);
			Arrays.sort(vexps);
			if(vexps.length>0)
			 vmin = vexps[0];
			
			
			for(int i=1; i< vstops.axisstops.length;i++)
			{
			
				if(XisCategory)
				{
					values.DrawTextCategoryVerticalBar(uniqueX[i-1],canvas,xlabel,coordbounds.bottom,
							i,vstops,
							boxbounds.left, coordbounds.height(),false,ticks.isVflipped); 
				}
				
				else if(XisNum)
				{
					
					if(i%2==0)
					values.DrawTextNumVExponent(canvas,xlabel,coordbounds.bottom,i,vstops 
							,boxbounds.left,coordbounds.height(),vInterval,false,ticks.isVflipped,vmin);
				}
				
			}
			
			
			
			canvas.save();
			canvas.rotate((float)(90));
			System.out.println("CANVAS ");
			canvas.drawText(xlabel,0,xlabel.length(),
				coordbounds.bottom-coordbounds.height()/2,
				boxbounds.left-mLabelSeparation/2, aPaint);
			canvas.restore();
			
			
			
			if(YisNum)
			{
				
				if(hmin!=0)
					values.drawExponentHorizontal(canvas,coordbounds.bottom,hmin,coordbounds.left,coordbounds.width());
			}
			
			if(XisNum)
			{
				
				if(vmin!=0)
					values.drawExponentVertical(canvas,coordbounds.bottom,vmin,boxbounds.left,coordbounds.height());
					
			}
			
			
			for(int i=1; i< hstops.axisstops.length;i++)
			{
			
				if(YisCategory)
			
					
				
					values.DrawTextCategory(uniqueY[i-1],canvas,coordbounds.bottom,i,hstops,
					coordbounds.left, coordbounds.width(),true,ticks.isHflipped); 
		
			
				else if(YisNum)
			
					
					if(i%2==0)
					values.DrawTextNumHExponent(canvas,ylabel,coordbounds.bottom,i,hstops 
						,coordbounds.left,coordbounds.width(),hInterval,true,
						ticks.isHflipped,hmin);
				
			}
			
			
			canvas.drawText(ylabel,0,ylabel.length(),
					coordbounds.left+coordbounds.width()/2,boxbounds.bottom-(mAxisLabelHeight/2), aPaint);
			
		}
				
	
}




	 
	public void drawLegend(Canvas canvas,Paint legendPaint,
			Paint lPaint,int numseries,String [] seriesname,
			Map<String,Integer> seriescolor,int mLabelWidth,ReturnIntervalsTicks ticks,float mAxisLabelHeight, Paint aPaint ) {
		
		
		
		int row=1;
		int a=0;
        float lastleft=0;
		int [] labelwidths = new int[numseries];


        for (int i=0; i<labelwidths.length; i++)

        {
            labelwidths[i]= (int) Math.abs(legendPaint.measureText(seriesname[i]+"0"));

        }



		for (int i=0; i<numseries; i++)
			
		{

            float left=0; float right=0;

            if(i==0) {
                left = legendBox.left + drawSpace;
                lastleft = left;
            }
            else {

                if(lastleft!=0)
                    left = lastleft + labelwidths[i-1]+drawSpace+drawBoxSize;
                else
                    left=legendBox.left+drawSpace;
                lastleft=left;

            }

            right = left+drawBoxSize;

            lPaint.setColor(seriescolor.get(seriesname[i]));
			legendPaint.setColor(seriescolor.get(seriesname[i]));
		
	
			if(((right + labelwidths[i])<legendBox.right))
			{

			}
			else
			{
				row =row+1;
                lastleft=0;
                left = legendBox.left +drawSpace;
                lastleft=left;
                right = left+drawBoxSize;
				
				

			}


            float top = legendBox.top+((row)*drawSpace)+(row-1)*drawBoxSize;
            float bottom = top+drawBoxSize;

            if(bottom>legendBox.bottom)
            {
                redrawLegendBox(coordbounds.width(), drawBoxSize, mLabelWidth,
                        ticks, mAxisLabelHeight, aPaint);

            }

            canvas.drawRect(left,top,right,
                    bottom, lPaint);


            canvas.drawText(seriesname[i],right+drawSpace,
                    bottom, legendPaint);

		}
		
		
		
	}
	
	
	
	private void redrawLegendBox(float coww,int drawBoxSize,int mLabelWidth,ReturnIntervalsTicks ticks,float mAxisLabelHeight,Paint aPaint) {
		
			
		float coll = (float) boxbounds.height()- ticks.subLabelHeight-
				(2*mAxisLabelHeight+3*mLabelSeparation)-(-aPaint.ascent())
				-(legendSize+drawBoxSize);

		coordbounds = new RectF(0.0f,0.0f,
				coww,coll);
		
		float xoffset = ticks.nvLabelWidth+(2*mAxisLabelHeight)+3*mLabelSeparation-(-aPaint.ascent());		
		
		coordbounds.offsetTo(getPaddingLeft()+ xoffset, getPaddingTop()+mLabelSeparation);
		
		legendBox = new RectF(0.0f,0.0f,coww,legendSize + drawBoxSize);
		
		legendSize  = legendSize+ drawBoxSize;
		
		
		
		legendBox.offset(getPaddingLeft()+xoffset,coordbounds.bottom+ticks.subLabelHeight+(mAxisLabelHeight-aPaint.ascent()));
	
		invalidate =true;
		
		invalidate();
		
		
		
		
	}
	
	
	
	
	public  void drawAxes(Canvas canvas,Boolean showgrid) {
		
		 
		 
	 	float [] hposstops = new float[hstops.axisstops.length];
		float [] vposstops= new float[vstops.axisstops.length];
		
		
		System.out.println(Viewport.left+"VL");
		System.out.println(Viewport.width()+"VW");
		System.out.println(coordbounds.width()+"CW");
		System.out.println(coordbounds.left+"CL");
		
		
	
		for(int i=0; i< hstops.axisstops.length;i++)
		{
			hposstops [i] = (float) (coordbounds.left + coordbounds.width()*
					(hstops.axisstops[i]-Viewport.left)/Viewport.width());
			
			
			System.out.println(hposstops[i]+"HposStops");
			System.out.println(hstops.axisstops[i]+"HAxisStops");
			
		}
		
		
		for(int j=0;j<vstops.axisstops.length;j++)
		{
			vposstops [j] = (float) (coordbounds.bottom - coordbounds.height()*
					(vstops.axisstops[j]-Viewport.top)/Viewport.height());
			
			
			System.out.println(vposstops[j]+"VposStops");
		}
//		
		 	hstops.posstops=hposstops;
		 	vstops.posstops=vposstops;
		 
		
		 	if(!isHorizontal)
			{
				if(XisCategory)
				{
					AxisHGridLines = lines.drawHorizontalGridCategory(hstops,coordbounds);
					
				}
				
				else if (XisNum)
				{
					if(isGridShown())
					AxisHGridLines = lines.drawHorizontalGrid(hstops,coordbounds,true);
					else
					AxisHGridLines = lines.drawHorizontalGrid(hstops,coordbounds,false);
				}
				
			}
			
			else
			{
				if(YisCategory)
				{
					AxisHGridLines = lines.drawHorizontalGridCategory(hstops,coordbounds);
					
				}
				
				else if (YisNum)
				{
					if(isGridShown())
					AxisHGridLines = lines.drawHorizontalGrid(hstops,coordbounds,true);
					else
					AxisHGridLines = lines.drawHorizontalGrid(hstops,coordbounds,false);
				}
				
			}
			
		 
		 
			
			canvas.drawLines(AxisHGridLines, 0,AxisHGridLines.length,gPaint);
			
			
			if(isGridShown())
				
			{
					

	
					//canvas.drawLines(AxisVGridLines, 0,(vstops.posstops.length-1)*4,gPaint);
					
				if(!isHorizontal)
				{
					if(YisCategory)
					{
						AxisVGridLines = lines.drawVerticalGridCategory(vstops, coordbounds, false);
						
					}
					
					else if (YisNum)
					{
						AxisVGridLines = lines.drawVerticalGrid(vstops, coordbounds, true);
					}
					
				}
				
				else
				{
					if(XisCategory)
					{
						AxisVGridLines = lines.drawVerticalGridCategory(vstops, coordbounds, false);
						
					}
					
					else if (XisNum)
					{
						AxisVGridLines = lines.drawVerticalGrid(vstops, coordbounds, true);
					}
					
				}
					
					
					canvas.drawLines(AxisVGridLines, 0,AxisVGridLines.length,gPaint);
			}
			
			else
				
			{
				
				if(!isHorizontal)
				{
					if(YisCategory)
					{
						AxisVGridLines = lines.drawVerticalGridCategory(vstops, coordbounds, false);
						
					}
					
					else if (YisNum)
					{
						AxisVGridLines = lines.drawVerticalGrid(vstops, coordbounds, false);
					}
					
				}
				
				else
				{
					if(XisCategory)
					{
						AxisVGridLines = lines.drawVerticalGridCategory(vstops, coordbounds, false);
						
					}
					
					else if (XisNum)
					{
						AxisVGridLines = lines.drawVerticalGrid(vstops, coordbounds, false);
					}
					
				}
					

					//canvas.drawLines(AxisVGridLines, 0,(vstops.posstops.length-1)*4,gPaint);
					canvas.drawLines(AxisVGridLines, 0,AxisVGridLines.length,gPaint);
			}	
			
	
}

	
	public final ScaleGestureDetector.OnScaleGestureListener mScaleGestureListener
	  = new ScaleGestureDetector.SimpleOnScaleGestureListener()
	 {
		 
		 private PointF viewportFocus = new PointF();
		 private float lastSpanX;
		 private float lastSpanY;
		 
		 @Override 
		 public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector)
		 {
			 lastSpanX = ScaleGestureDetectorCompat.getCurrentSpanX(scaleGestureDetector);
			 lastSpanY = ScaleGestureDetectorCompat.getCurrentSpanY(scaleGestureDetector);
			 
			
			return true;
		 }
		 
		 @Override
		 public boolean onScale(ScaleGestureDetector scaleGestureDetector)
		 {
			 float spanX =ScaleGestureDetectorCompat.getCurrentSpanX(scaleGestureDetector);
			 float spanY =ScaleGestureDetectorCompat.getCurrentSpanY(scaleGestureDetector);
		
			 float newWidth = (lastSpanX/spanX) * Viewport.width();
			 float newHeight = (lastSpanY/spanY) * Viewport.height();
	 
			 float focusX = scaleGestureDetector.getFocusX();
			 float focusY = scaleGestureDetector.getFocusY();
			 

			 hitTest(focusX,focusY,viewportFocus);

//			 
			 Viewport.set(viewportFocus.x - newWidth *
				 (focusX-coordbounds.left)/coordbounds.width(),viewportFocus.y- 
				 newHeight * (coordbounds.bottom-focusY)/coordbounds.height(),0,0);
		 	Viewport.right = Viewport.left+newWidth;
		 	Viewport.bottom = Viewport.top + newHeight;
		
		   constrainViewport();
		   ViewCompat.postInvalidateOnAnimation(GraphView.this);
		 
		   lastSpanX = spanX;
		   lastSpanY = spanY;
			return true;
		 }
	 };
	 
	 
	
	  public final GestureDetector.SimpleOnGestureListener mGestureListener =
			  new GestureDetector.SimpleOnGestureListener()
	  {
		  
		  @Override
		  
		  public boolean onDown(MotionEvent e)
		  {
			 releaseEdgeEffects();
			 //ViewCompat.postInvalidateOnAnimation(BubblePlot.this);
			
			return true;
		
		
		  }
		  @Override
		  
		  public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,float distanceY)
		  {
			 
			  
			  float viewportOffsetH = distanceX*(Viewport.width()/coordbounds.width());
			 
			  
			  float viewportOffsetV= -distanceY*Viewport.height()/coordbounds.height();
			  
			 
			  
			  computeScrollSurfaceSize(ScrollableSurface);
			  
			  int ScrolledH =(int) (ScrollableSurface.x * (Viewport.left + 
					  (viewportOffsetH-AXIS_H_MIN))/(AXIS_H_MAX-AXIS_H_MIN));
			  
			  int ScrolledV =(int) ((ScrollableSurface.y * (AXIS_V_MAX - Viewport.bottom -viewportOffsetV))/
					  (AXIS_V_MAX-AXIS_V_MIN));
			  
			  boolean canScrollH = Viewport.left>AXIS_H_MIN || Viewport.right<AXIS_H_MAX;
			  boolean canScrollV= Viewport.top > AXIS_V_MIN || Viewport.bottom<AXIS_V_MAX;
			  
			  setViewportBottomLeft(Viewport.left+viewportOffsetH,Viewport.bottom + viewportOffsetV);
			  
			  if(canScrollH && ScrolledH<0)
			  {
				  mEdgeEffectLeft.onPull(ScrolledH/(float)coordbounds.width());
				  mEdgeEffectLeftActive=true;
			  }
					  
			  
			  if(canScrollV && ScrolledV<0)
			  {
				  mEdgeEffectTop.onPull(ScrolledV/(float)coordbounds.height());
				  mEdgeEffectTopActive=true;
			  }
			  
			  if(canScrollH && ScrolledH > ScrollableSurface.x - coordbounds.width())
			  {
				  mEdgeEffectRight.onPull((ScrolledH-ScrollableSurface.x+ coordbounds.width())
						  /(float)coordbounds.width());
				  mEdgeEffectRightActive=true;
			  }
			  
			  if(canScrollV && ScrolledV > ScrollableSurface.y - coordbounds.height())
			  {
				  mEdgeEffectBottom.onPull((ScrolledV-ScrollableSurface.y+ coordbounds.height())
						  /(float)coordbounds.height());
				  mEdgeEffectBottomActive=true;
			  }
			return true;
		  }

		
	  };
		
		
	  private void computeScrollSurfaceSize(Point out) {
		  out.set(
				  
				  (int)(coordbounds.width()*((AXIS_H_MAX-AXIS_H_MIN))/Viewport.width()),
				  
				  (int)(coordbounds.height()*((AXIS_V_MAX-AXIS_V_MIN)/Viewport.height()))
				  
				  );
		 		
		}
	
	
	
	  protected void constrainViewport() {
			
		  Viewport.left = Math.max(AXIS_H_MIN,Viewport.left);
		  Viewport.top = Math.max(AXIS_V_MIN,Viewport.top);
		  Viewport.bottom = Math.max(Math.nextUp(Viewport.top),Math.min(AXIS_V_MAX,Viewport.bottom));
		  Viewport.right = Math.max(Math.nextUp(Viewport.left),Math.min(AXIS_H_MAX,Viewport.right));
	}

	  
	  
	protected boolean hitTest(float x, float y, PointF viewportFocus) {
		
		  if(!coordbounds.contains((int)x,(int)y))
		  {
			  return false;
		  }
		  
		
		  
		  viewportFocus.set(Viewport.left+(Viewport.width() *
				  (x - coordbounds.left)/coordbounds.width()),
				 Viewport.top + (Viewport.height() * (y - coordbounds.bottom)/-coordbounds.height()));
		 
		  return true;
		  
		
	}

	protected void releaseEdgeEffects() {
		
		  mEdgeEffectLeftActive = 
				  mEdgeEffectRightActive=
				  mEdgeEffectTopActive=
				  mEdgeEffectBottomActive=
				  false;
		  
		  mEdgeEffectLeft.onRelease();
		  mEdgeEffectRight.onRelease();
		  mEdgeEffectTop.onRelease();
		  mEdgeEffectBottom.onRelease();
				  
		
	}


	private void setViewportBottomLeft(float x,float y) {
			
		  
		  float curWidth = Viewport.width();
		 
		  float curHeight = Viewport.height();
		  x= Math.max(AXIS_H_MIN,Math.min(x, AXIS_H_MAX-curWidth));
	      y = Math.max(AXIS_V_MIN +curHeight,Math.min(y, AXIS_V_MAX));
	      
	      Viewport.set(x,y-curHeight,x + curWidth,y);
	      ViewCompat.postInvalidateOnAnimation(this);
		}
	
	
	
	public void drawEdgeEffectsUnclipped(Canvas canvas)
	{
		boolean needsInvalidate = false;
		
		if(!mEdgeEffectTop.isFinished())
		{
			final int restoreCount = canvas.save();
			canvas.translate(coordbounds.left, coordbounds.top);
			mEdgeEffectTop.setSize((int)coordbounds.width(),(int)coordbounds.height());
			if(mEdgeEffectTop.draw(canvas))
			{
				needsInvalidate = true;
			}
			
			canvas.restoreToCount(restoreCount);
			
		}
		
		
		
		if(!mEdgeEffectBottom.isFinished())
		{
			final int restoreCount = canvas.save();
			canvas.translate(2*coordbounds.left - coordbounds.right, coordbounds.bottom);
			canvas.rotate(180, coordbounds.width(), 0);
			mEdgeEffectBottom.setSize((int)coordbounds.width(),(int)coordbounds.height());
			if(mEdgeEffectBottom.draw(canvas))
			{
				needsInvalidate = true;
			}
			
			canvas.restoreToCount(restoreCount);
			
		}
		
		if(!mEdgeEffectLeft.isFinished())
		{
			final int restoreCount = canvas.save();
			canvas.translate(coordbounds.left, coordbounds.bottom);
			canvas.rotate(-90,0,0);
			mEdgeEffectLeft.setSize((int)coordbounds.height(),(int)coordbounds.width());
			if(mEdgeEffectLeft.draw(canvas))
			{
				needsInvalidate = true;
			}
			
			canvas.restoreToCount(restoreCount);
			
		}
		
		if(!mEdgeEffectRight.isFinished())
		{
			final int restoreCount = canvas.save();
			canvas.translate(coordbounds.right, coordbounds.top);
			canvas.rotate(90,0,0);
			mEdgeEffectRight.setSize((int)coordbounds.height(),(int)coordbounds.width());
			if(mEdgeEffectRight.draw(canvas))
			{
				needsInvalidate = true;
			}
			
			canvas.restoreToCount(restoreCount);
			
		}
		
		if(needsInvalidate)
		{
			ViewCompat.postInvalidateOnAnimation(this);
		}
	}
	
	
	public void calculateSeriesColors(int numseries,Boolean useSeriesPrimary,
			int accentprimarycolor,Map<String,Integer> seriescolor,String[] seriesname) {
		
		System.out.println("Series Color");
		
		int[] colors = new int[numseries];
		ColorCode sercolor = new ColorCode();
		if(useSeriesPrimary)
		{
			colors = sercolor.setPrimaryColor(numseries,60,0.8f,0.9f);
			
			
		}
		else
			colors = sercolor.setHSVAccent(accentprimarycolor,numseries,0.5f,1.0f, 20);
		
		for(int i=1; i<=numseries;i++)
		{
			seriescolor.put(seriesname[i-1], colors[i-1]);
			System.out.println("SP" + seriesname[i-1]+
					seriescolor.get(seriesname[i-1]));
			
		}
	}
	
	
	
	public  float convertDptoPixel(float dp)
	{
		
		
	float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,getResources().getDisplayMetrics());
	
	return px;
	
	}
	
	
	
}

