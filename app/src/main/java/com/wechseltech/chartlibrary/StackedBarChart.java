


package com.wechseltech.chartlibrary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Cap;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;

public class StackedBarChart extends GraphView {

	
	int mLabelWidth,mAxisLabelHeight,
	mLabelHeight,mAxisLabelWidth;
	Paint aPaint,rPaint,tPaint,bPaint,lPaint,vPaint,
	fPaint,
	labelPaint,labPaint;
	float axisLabelSpace;
	BarPosition[] barposition;
	
	int minLabelHeight,minLabelWidth;
	Integer[] cols;
	OptimizeTicks tasks;
	
	String[] uniqueX,uniqueY;
	
	Context context;
	
	int numseries;
	float mVLabelWidth;
	
	float xpoint,ypoint;
	
	ReturnIntervalsTicks ticks;
	
	
	Map<String,Integer> seriescolor;
	
	
	
	List<MultipleSeriesItem> series;
	String[] seriesname;
	String xLabelName,yLabelName;
	
	Map<String,Float> positions = new HashMap<String,Float>();
	
	
	
	
	public StackedBarChart(Context context) {
		super(context);
		this.context=context;
		 hstops = new AxisStops();
		 vstops = new AxisStops();
		
		mEdgeEffectTop = new EdgeEffectCompat(context);
		mEdgeEffectLeft = new EdgeEffectCompat(context);
		mEdgeEffectRight = new EdgeEffectCompat(context);
		mEdgeEffectBottom = new EdgeEffectCompat(context);
		
		mScaleGestureDetector = new ScaleGestureDetector(context,mScaleGestureListener);
		mGestureDetector = new GestureDetectorCompat(context,mGestureListener);
		
		
	}

	
	public StackedBarChart(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		
		
		
		 hstops = new AxisStops();
		 vstops = new AxisStops();
			
			
		mEdgeEffectTop = new EdgeEffectCompat(context);
		mEdgeEffectLeft = new EdgeEffectCompat(context);
		mEdgeEffectRight = new EdgeEffectCompat(context);
		mEdgeEffectBottom = new EdgeEffectCompat(context);
		
		mScaleGestureDetector = new ScaleGestureDetector(context,mScaleGestureListener);
		mGestureDetector = new GestureDetectorCompat(context,mGestureListener);
		
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.Charts,0,0);
		
		try
		{
			
			mAxisLabelSize = a.getDimension(R.styleable.Charts_axisLabelSize, 12);
			mLabelSeparation = a.getDimension(R.styleable.Charts_labelSeparation, 8);
			mTextSize = a.getDimension(R.styleable.Charts_textSize, 14);
			
			legendSize= a.getDimension(R.styleable.Charts_legendBoxSize, 30);
			drawSpace = a.getDimension(R.styleable.Charts_drawSpace, 5);
			textcolor = a.getColor(R.styleable.Charts_textColor, Color.BLACK);
			gridcolor = a.getColor(R.styleable.Charts_gridColor, Color.BLACK);
			assignSeriesColors= a.getBoolean(R.styleable.Charts_assignSeriesColors, true);
			showgrid= a.getBoolean(R.styleable.Charts_showGrid, true);
			drawLabels = a.getBoolean(R.styleable.Charts_drawLabels, false);
			isHorizontal =a.getBoolean(R.styleable.Charts_isHorizontal, false);
			rectcolor =a.getColor(R.styleable.Charts_boundingboxColor, Color.BLACK);
			useSeriesPrimary= a.getBoolean(R.styleable.Charts_useSeriesPrimary, true);
			accentprimarycolor =a.getColor(R.styleable.Charts_accentprimaryColor, Color.RED);
			
			XisNum = a.getBoolean(R.styleable.Charts_XisNum, false);
			YisNum = a.getBoolean(R.styleable.Charts_YisNum, false);
			YisCategory = a.getBoolean(R.styleable.Charts_YisCategory,false);
			XisCategory = a.getBoolean(R.styleable.Charts_XisCategory,false);
			
			
			
		}
		
		finally
		{
			
			a.recycle();
		}
		
		
	}


	public StackedBarChart(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
		 hstops = new AxisStops();
		 vstops = new AxisStops();
			
		 this.context=context;
			
		mEdgeEffectTop = new EdgeEffectCompat(context);
		mEdgeEffectLeft = new EdgeEffectCompat(context);
		mEdgeEffectRight = new EdgeEffectCompat(context);
		mEdgeEffectBottom = new EdgeEffectCompat(context);
		
		mScaleGestureDetector = new ScaleGestureDetector(context,mScaleGestureListener);
		mGestureDetector = new GestureDetectorCompat(context,mGestureListener);
		
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.Charts,0,0);
		
		try
		{
			
			mAxisLabelSize = a.getDimension(R.styleable.Charts_axisLabelSize, 12);
			mLabelSeparation = a.getDimension(R.styleable.Charts_labelSeparation, 8);
			mTextSize = a.getDimension(R.styleable.Charts_textSize, 14);
			legendSize= a.getDimension(R.styleable.Charts_legendBoxSize, 30);
			drawSpace = a.getDimension(R.styleable.Charts_drawSpace, 5);
			textcolor = a.getColor(R.styleable.Charts_textColor, Color.BLACK);
			gridcolor = a.getColor(R.styleable.Charts_gridColor, Color.BLACK);
			assignSeriesColors= a.getBoolean(R.styleable.Charts_assignSeriesColors, true);
			showgrid= a.getBoolean(R.styleable.Charts_showGrid, true);
			drawLabels = a.getBoolean(R.styleable.Charts_drawLabels, false);
			isHorizontal =a.getBoolean(R.styleable.Charts_isHorizontal, false);
			rectcolor =a.getColor(R.styleable.Charts_boundingboxColor, Color.BLACK);
			useSeriesPrimary= a.getBoolean(R.styleable.Charts_useSeriesPrimary, true);
			accentprimarycolor =a.getColor(R.styleable.Charts_accentprimaryColor, Color.RED);
			
			XisNum = a.getBoolean(R.styleable.Charts_XisNum, false);
			YisNum = a.getBoolean(R.styleable.Charts_YisNum, false);
			YisCategory = a.getBoolean(R.styleable.Charts_YisCategory,false);
			XisCategory = a.getBoolean(R.styleable.Charts_XisCategory,false);
			
			
			
		}
		
		finally
		{
			
			a.recycle();
		}
		
		
	}
	
	
	


	private void init()
	{
		
			lines = new DrawGridLines();

			gPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			gPaint.setColor(gridcolor);
			
			
			 lPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     lPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		     
		     bPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     bPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		     
		     
			 aPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     aPaint.setColor(textcolor);
		     aPaint.setTextSize(mAxisLabelSize);
		     aPaint.setTextAlign(Paint.Align.CENTER);
		        
		     
		     	vPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		        vPaint.setColor(textcolor);
		        vPaint.setTextSize(mTextSize);
		        vPaint.setTextAlign(Paint.Align.RIGHT);
		        
		    	fPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		        fPaint.setColor(textcolor);
		        fPaint.setTextSize(mTextSize);
		        fPaint.setTextAlign(Paint.Align.LEFT);
		     
		     
		     tPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     tPaint.setColor(textcolor);
		     tPaint.setTextSize(mTextSize);
		     tPaint.setTextAlign(Paint.Align.CENTER);
		     
		     
		     labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     labelPaint.setColor(textcolor);
		     labelPaint.setTextSize(mTextSize);
		     labelPaint.setTextAlign(Paint.Align.LEFT);
		     
		     
		     	labPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		        labPaint.setColor(textcolor);
		        labPaint.setTextSize(mLabelSize);
		        labPaint.setTextAlign(Paint.Align.CENTER);
		     
		     rPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     rPaint.setColor(rectcolor);
		     rPaint.setStyle(Paint.Style.STROKE);
		     
		     
		        
			 mLabelHeight =(int) Math.abs(tPaint.getFontMetrics().top);
		        
		     drawBoxSize = mLabelHeight;
		        
		     minLabelHeight = (int) Math.abs(tPaint.getFontMetrics().bottom);
		    
		     minLabelWidth = (int) Math.abs(tPaint.measureText("000"));
		     mLabelWidth = (int) Math.abs(tPaint.measureText("000000"));
		     mAxisLabelHeight = (int) Math.abs(aPaint.getFontMetrics().top);
		     
		     
		     mVLabelWidth = (int) Math.abs(tPaint.measureText("00000"));
		     
		     
			
	}
	
	
	
	
	
	
	
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		

		
		
		float xpad = (float)(getPaddingLeft()+ getPaddingRight());
		float ypad = (float)(getPaddingTop() +  getPaddingBottom());
		
		float coww =0.0f,cohh=0.0f,coll =0.0f;
		
		init();
		
		coww = (float) w-xpad
				-mLabelWidth-(2*mAxisLabelHeight+aPaint.ascent())-2*mLabelSeparation;
		cohh = (float) h-ypad-mLabelHeight-(3*mAxisLabelHeight+aPaint.ascent()+3*mLabelSeparation)
				-(legendSize+mLabelSeparation);

		float bxww = (float) w-xpad;
		float bxhh = (float) h-ypad;
		
		mAxisLabelWidth = (int) (coww/2);
		
		boxbounds = new RectF(getPaddingLeft(),getPaddingTop(),
				getPaddingLeft()+bxww,getPaddingTop()+bxhh);
		
		
		//coll = bxhh-legendSize-mLabelSeparation;
		
		coordbounds = new RectF(0.0f,0.0f,
				coww,cohh);
		
		float xoffset = mLabelWidth+ (2*mAxisLabelHeight+aPaint.ascent())+mLabelSeparation;
		axisLabelSpace = (2*mAxisLabelHeight+aPaint.ascent())+2*mLabelSeparation;
		
		
		coordbounds.offset(getPaddingLeft()+ xoffset,getPaddingTop()+mLabelSeparation);
		
		
		legendBox = new RectF(0.0f,0.0f,coww,legendSize);
		
		legendBox.offset(getPaddingLeft()+xoffset,coordbounds.bottom+(mAxisLabelHeight-aPaint.ascent()));
		
		
		tasks = new OptimizeTicks(mLabelWidth, mLabelHeight,coordbounds.width(),
				coordbounds.height(), minLabelWidth, minLabelHeight,
				bxhh,bxww,axisLabelSpace,legendSize);
		
		assignColors();
		
		getXYTicks(boxbounds.width(),boxbounds.height(),coordbounds.width(),axisLabelSpace);
		
		
	}
	
	
	 private void getXYTicks(float bxw,float bxh,float coww,float axisLabelSpace) {
			
		 
		  ticks=null;
		 
		  System.out.println("LGSIZE" + legendSize);
		 //barspace = (mBarWidth)+(2*mBarSpacing);			
		 
		 HandleXYTicks handles = new HandleXYTicks(coordbounds,
					getPaddingLeft(),getPaddingTop(),mAxisLabelHeight, 
					mLabelSeparation,aPaint,mBarWidth,mBarSpacing,0,legendSize,mVLabelWidth);
		 
		
		
		 if(XisNum & YisCategory)
		 {
			 

			double [] maxvalX = new double[series.size()];
			getYCategory();
			double [] sums = new double[uniqueY.length];
			
			
			

			for(int j=0;j<uniqueY.length;j++)
			{
				for(int i=0;i<series.size();i++)
				{
						maxvalX[i]=series.get(i).getXVal();
						if(uniqueY[j]==series.get(i).getYLabel())
							sums[j]+=series.get(i).getXVal();	
				
				}
				
				System.out.println("SUM"+ sums[j]);
			
			}
			
			
			
			
			double [] concatX = new double[maxvalX.length+sums.length];
			concatX = Arrays.copyOf(maxvalX, maxvalX.length+sums.length);
			int m=0;
			for(int n=maxvalX.length;n<maxvalX.length+sums.length;n++)
			{
				concatX[n]=sums[m];
				m++;
			}
			
			Arrays.sort(concatX);
			
			
			
					
			ticks = handles.getYCategoryXNumericBar(isHorizontal,
					concatX[concatX.length-1],concatX[0], concatX, hstops, vstops, 
					uniqueY, bxh,bxw, coww, axisLabelSpace,tasks,1);
			
			 coordbounds = ticks.coordbounds;
			 Viewport=ticks.Viewport;
			 legendBox = ticks.legendBox;
			 AXIS_H_MAX = ticks.AXIS_H_MAX;
			 AXIS_H_MIN = ticks.AXIS_H_MIN;
			 AXIS_V_MAX = ticks.AXIS_V_MAX;
			 AXIS_V_MIN = ticks.AXIS_V_MIN;
			 
			 System.out.println("AXIS-H-MAX" + ticks.AXIS_H_MAX);
			 System.out.println("AXIS-H-MIN" + ticks.AXIS_H_MIN);
			 System.out.println("AXIS-V-MAX" + ticks.AXIS_V_MAX);
			 
			 System.out.println("AXIS-V-MIN" + ticks.AXIS_V_MIN);
			 System.out.println(Viewport);
			 
			 hstops = ticks.hstops;
			 hInterval = ticks.hInterval;
			 vstops = ticks.vstops;
			 vInterval = ticks.vInterval;
			 isVFlipped = ticks.isVflipped;
			 
			 System.out.println("Flipped" +isVFlipped);
			 nHticks = ticks.nHticks;
			 nVticks = ticks.nVticks;
			 
			   if(isHorizontal)
				{
				int visTicks =(int)Math.floor(coordbounds.width()/barspace);
				
				System.out.println("VIS" + visTicks);
				if(hstops.axisstops.length>visTicks)
				Viewport.right=visTicks;
				}
				
				else
				{
					int visTicks =(int)Math.floor(coordbounds.height()/barspace);
					
					if(vstops.axisstops.length>visTicks)
						Viewport.bottom=visTicks;
				}
			 
			
			
			 
		 }
		 
		 if(YisNum & XisCategory)
		 {
			 

			double [] maxvalY = new double[series.size()];
			getXCategory();
			double [] sums = new double[uniqueX.length];
			
			/*for(int i=0;i<series.size();i++)
			{
					maxvalY[i]=series.get(i).getYVal();
					for(int j=0;j<uniqueX.length;j++)
					{
						if(uniqueX[j]==series.get(i).getXLabel())
							sums[j]=series.get(i).getYVal();	
						
					}
					
			}*/
			
			

			
					for(int j=0;j<uniqueX.length;j++)
					{
						for(int i=0;i<series.size();i++)
						{
								maxvalY[i]=series.get(i).getYVal();
								if(uniqueX[j]==series.get(i).getXLabel())
									sums[j]+=series.get(i).getYVal();	
						
						}
						
						System.out.println("SUM"+ sums[j]);
					
					}
			
			double [] concatY = new double[maxvalY.length+sums.length];
			concatY = Arrays.copyOf(maxvalY, maxvalY.length+sums.length);
			int m=0;
			for(int n=maxvalY.length;n<maxvalY.length+sums.length;n++)
			{
				concatY[n]=sums[m];
				m++;
			}
			
			Arrays.sort(concatY);
			
			
			System.out.println("CONCATZero" + concatY[0] + 
					"CONCATLENG" + concatY[concatY.length-1]);
					
			ticks = handles.getXCategoryYNumericBar(isHorizontal, 
					concatY[concatY.length-1],concatY[0], concatY, hstops, 
					vstops, uniqueX, bxh, bxw,coww, axisLabelSpace,tasks,1);
			
			
			 coordbounds = ticks.coordbounds;
			 Viewport=ticks.Viewport;
			 legendBox = ticks.legendBox;
			 AXIS_H_MAX = ticks.AXIS_H_MAX;
			 AXIS_H_MIN = ticks.AXIS_H_MIN;
			 AXIS_V_MAX = ticks.AXIS_V_MAX;
			 AXIS_V_MIN = ticks.AXIS_V_MIN;
			 
			 
			 
			 System.out.println("AXIS-H-MAX" + ticks.AXIS_H_MAX);
			 System.out.println("AXIS-H-MIN" + ticks.AXIS_H_MIN);
			 System.out.println("AXIS-V-MAX" + ticks.AXIS_V_MAX);
			 
			 System.out.println("AXIS-V-MIN" + ticks.AXIS_V_MIN);
			 System.out.println(Viewport);
			 
			 hstops = ticks.hstops;
			 hInterval = ticks.hInterval;
			 vstops = ticks.vstops;
			 vInterval = ticks.vInterval;
			 isHFlipped = ticks.isHflipped;
			 
			 System.out.println("Flipped" +isHFlipped);
			 nHticks = ticks.nHticks;
			 nVticks = ticks.nVticks;
			 
			   if(!isHorizontal)
				{
				int visTicks =(int)Math.floor(coordbounds.width()/barspace);
				
				System.out.println("VIS" + visTicks);
					if(hstops.axisstops.length>visTicks)
						Viewport.right=visTicks;
				}
				
				else
				{
					int visTicks =(int)Math.floor(coordbounds.height()/barspace);
					
					if(vstops.axisstops.length>visTicks)
						Viewport.bottom=visTicks;
				}
			 
			
			 
		 }
		 
		
		
		 
		 
		 
		 
		 
		 
	 }
	 
	 
	 @Override 
	 public void onDraw(Canvas canvas)
	 
	 {
		super.onDraw(canvas);
		
		int clipRestoreCount = canvas.save();
		canvas.clipRect(coordbounds);
		
		//drawAxes(canvas,showgrid);
		
		drawAxesStack(canvas);
	
		drawData(canvas);
		
		drawEdgeEffectsUnclipped(canvas);
		
		
		
				 
		
		canvas.restoreToCount(clipRestoreCount);
		
		
		
		if(!invalidate)
			drawLegend(canvas, labelPaint, lPaint,
					numseries,seriesname,seriescolor,mLabelWidth,ticks,mAxisLabelHeight,aPaint);
			
			else
			drawInvalidateLegend(canvas);
		
		
		
		
		drawTextBar(canvas,tPaint,aPaint,ticks,getXLabelName(),getYLabelName(),mAxisLabelHeight,
				 mAxisLabelWidth,isHorizontal, XisCategory,
				 YisCategory,XisNum, YisNum,uniqueX,uniqueY,vPaint,fPaint);
		
		
		canvas.drawRect(coordbounds,rPaint);
		
		
		
		
		 
		 
	 }
	 
	 
	 private void drawInvalidateLegend(Canvas canvas) {
			System.out.println("LEGEND" + legendBox);
			 
			
			int row=1;
			int a=0;
			
			
			
			
			for (int i=0; i<seriesname.length; i++)
				
			{
				int lLabelWidth = 0;
				
				if(i!=0)
				lLabelWidth = (int) Math.abs(labelPaint.measureText(seriesname[i-1]+"0"));
				
				float checkleft = legendBox.left+ ((a+1)*drawSpace)+ (a*lLabelWidth) +(a*drawBoxSize);
				float checkright = checkleft+drawBoxSize;
				
				
			
				//lPaint.setColor(seriescolor.get(i+1));
				

				
				lPaint.setColor(seriescolor.get(seriesname[i]));
				labelPaint.setColor(seriescolor.get(seriesname[i]));
			
			
		
				if(((checkright + lLabelWidth)<legendBox.right))
				{
					
					System.out.println(seriesname[i]+"<RIGHT");
					
					float left = legendBox.left+ ((a+1)*drawSpace)+ (a*lLabelWidth) +(a*drawBoxSize);
					float right = left+drawBoxSize;
					float top = legendBox.top+((row)*drawSpace)+(row-1)*drawBoxSize;
					float bottom = top+drawBoxSize;
					
					canvas.drawRect(left,top,right,
							bottom, lPaint);
					System.out.println(seriesname[i]);
					canvas.drawText(seriesname[i],right+drawSpace,
								bottom, labelPaint);
					
					a++;
					
				}
				else
				{
					row =row+1;
					System.out.println(seriesname[i]+">RIGHT");
		     		
					a=0;	
					float left = legendBox.left+ ((a+1)*drawSpace)+ (a*lLabelWidth) +(a*drawBoxSize);
					float right = left+drawBoxSize;
					float top = legendBox.top+((row)*drawSpace)+(row-1)*drawBoxSize;
					float bottom = top+drawBoxSize;
						
					System.out.println("A" + a + "ROW"+row);
					
					canvas.drawRect(left,top,right,
							bottom, lPaint);
					System.out.println(seriesname[i]);
					canvas.drawText(seriesname[i],right+drawSpace,
								bottom, labelPaint);
					
					
					a++;
				}
				
		
			}
			
			
		}
	 
	     private void drawAxesStack(Canvas canvas)
		{
			
			float [] hposstops = new float[hstops.axisstops.length];
			float [] vposstops= new float[vstops.axisstops.length];
			
			System.out.println("COW" + coordbounds.width());
		
			
			if(!isHorizontal)
			{
				
				if(XisCategory & YisNum)
				{
					for(int i=0; i< hstops.axisstops.length;i++)
					{
						hposstops [i] = (float) (coordbounds.left + barspace*
								(hstops.axisstops[i]-Viewport.left));
						System.out.println(hposstops[i]+"HposStops");
					}
					
					for(int j=0;j<vstops.axisstops.length;j++)
					{
						vposstops [j] = (float) (coordbounds.bottom - coordbounds.height()*
								(vstops.axisstops[j]-Viewport.top)/Viewport.height());
						
						
						System.out.println(vposstops[j]+"VposStops");
					}
				}
				
				
				if(YisCategory & XisNum)
				{
					for(int i=0; i< hstops.axisstops.length;i++)
					{
						hposstops [i] = (float) (coordbounds.left + coordbounds.width()*
								(hstops.axisstops[i]-Viewport.left)/Viewport.width());
						System.out.println(hposstops[i]+"HposStops");
					}
					
					for(int j=0;j<vstops.axisstops.length;j++)
					{
						vposstops [j] = (float) (coordbounds.bottom - barspace*
								(vstops.axisstops[j]-Viewport.top));
						
						
						System.out.println(vposstops[j]+"VposStops");
					}
				}
				
				
					
			
			}
			
			else
				
			{
				if(YisCategory & XisNum)
				{
					for(int i=0; i< hstops.axisstops.length;i++)
					{
						hposstops [i] = (float) (coordbounds.left + barspace*
								(hstops.axisstops[i]-Viewport.left));
						System.out.println(hposstops[i]+"HposStops");
					}
					
					for(int j=0;j<vstops.axisstops.length;j++)
					{
						vposstops [j] = (float) (coordbounds.bottom - coordbounds.height()*
								(vstops.axisstops[j]-Viewport.top)/Viewport.height());
						
						
						System.out.println(vposstops[j]+"VposStops");
					}
					
				}
				
				if(XisCategory & YisNum)
				{
					for(int i=0; i< hstops.axisstops.length;i++)
					{
						hposstops [i] = (float) (coordbounds.left + coordbounds.width()*
								(hstops.axisstops[i]-Viewport.left)/Viewport.width());
						System.out.println(hposstops[i]+"HposStops");
					}
					
					for(int j=0;j<vstops.axisstops.length;j++)
					{
						vposstops [j] = (float) (coordbounds.bottom - barspace*
								(vstops.axisstops[j]-Viewport.top));
						
						
						System.out.println(vposstops[j]+"VposStops");
					}
					
				}
				
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
			 
				//canvas.drawLines(AxisHGridLines, 0,(hstops.posstops.length-1)*4,gPaint);
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
	 
	 private float getCentreH(double value)
	{
			float pos = coordbounds.left + coordbounds.width()*
			(float)(value-Viewport.left)/Viewport.width();
			
			return pos;
	}
	 

	 
	 private float getCentreH(double value,double startpos)
		{
				float pos = (float) (coordbounds.left + coordbounds.width()*
				(float)(value-Viewport.left)/Viewport.width());
				
				return pos;
		}

	 
	 private float getCentreV(double value,double startpos)
		{
				
				float pos = (float) (coordbounds.bottom - coordbounds.height()*
				(float)(value-Viewport.top)/Viewport.height());
				
				System.out.println("VIEWPORT H" + Viewport.height() + 
						"COORDH"+ coordbounds.height()+ "COORDTOP" + coordbounds.top+"VALUE" + value + "VTOP" + Viewport.top+
						"STPOS"+startpos);
				
				return pos;
		}
	
	private float getCentreV(double value)
	{
			float pos = coordbounds.bottom - coordbounds.height()*
			(float)(value-Viewport.top)/Viewport.height();
			
			System.out.println("VIEWPORT H" + Viewport.height() + 
					"COORDH"+ coordbounds.height()+ "COORDTOP" + coordbounds.top+"VALUE" + value + "VTOP" + Viewport.top);
			
			return pos;
	}
		

	private float getCentreVCategory(double value)
	{
		float pos = 0;float bpos =0;
		

		
		pos = coordbounds.bottom - barspace*
				(float)(value-Viewport.top);
		
		return pos;
		
	}
	
	private float getCentreHCategory(double value)
	{
		
		
		
		
		float pos = 0;
		float bpos=0;
		
		
		
		pos = coordbounds.left + barspace*
				(float)(value-Viewport.left);
		
		return pos;
		
	}
	
	
private void drawData(Canvas canvas) {
		
		
	barposition = new BarPosition[series.size()];
	int a =0;
		
		if(!isHorizontal)
		{
				
				if(XisCategory)
					
				{
					
					Map<String,Double> pos = new HashMap<String,Double>();
					
					for(int n=0; n<uniqueX.length;n++)
						
					{
						float startpos = coordbounds.bottom;
						 String value="";
						 double startvalue=0;
						 
						
						
						for(int i=1; i<=numseries;i++)
						{
							float centreh=0.0f,centrev=0.0f;
						
						for(int m=0;m<series.size();m++)
						{
							
							Boolean xtrue=false; Boolean ytrue=false;
							
							if(series.get(m).getSeries()==i)
							{
								if(YisNum)
								{
									ytrue=true;
								}
								
								
								bPaint.setColor(series.get(m).getColor());
								
								if(series.get(m).getXLabel().equals(uniqueX[n]))
							    {
										//centreh = getCentreH(n+1);
										centreh = getCentreHCategory(n+1);
										value=uniqueX[n];
										
										
										
										if(ytrue)
										{
											
											
										
											centrev = getCentreV(startvalue+series.get(m).getYVal(),startpos);
											
											
											barposition[a]= new BarPosition();
											
											barposition[a].setLeftEdge(centreh-mBarWidth/2);
											barposition[a].setRightEdge(centreh+mBarWidth/2);
											barposition[a].setTopEdge(centrev);
											barposition[a].setBottomEdge(startpos);
											barposition[a].setValue(series.get(m).getYVal());
											
											drawBar(canvas, centreh,centrev,series.get(m).getColor(),startpos);
											
											if(drawLabels)
											{
												if(XisCategory)
													   canvas.drawText(series.get(m).getLabel(),0,
																series.get(m).getLabel().length(),
																centreh,centrev-labPaint.ascent(),
																labPaint);
												 
											}
											
											
											pos.put(uniqueX[n],series.get(m).getYVal());
										}
										
										
										a++;
										
							    }
						
						
							}
							
							
							
							
							
						}
						startpos = centrev;
						startvalue= pos.get(uniqueX[n]);
						
						
						}
					}
					
					
				}
				
				
				if(YisCategory)
				{
					
					
					Map<String,Double> pos = new HashMap<String,Double>();
					
					
					for(int n=0; n<uniqueY.length;n++)
					{
						
					 float startpos = coordbounds.left;
					 String value="";
					 double startvalue=0;
					 
					 System.out.println("SERVALUE" + uniqueY[n]);
							
					  for(int i=1; i<=numseries;i++)
					  {
						 float centreh=0.0f,centrev=0.0f;
						
						for(int m=0;m<series.size();m++)
						{
							
							Boolean xtrue=false; Boolean ytrue=false;
							
							if(series.get(m).getSeries()==i)
							{
								
								if(XisNum)
								{
									xtrue=true;
								}
								
								bPaint.setColor(series.get(m).getColor());
								
								if(series.get(m).getYLabel().equals(uniqueY[n]))
							    {
										//centrev = getCentreV(n+1);
										centrev = getCentreVCategory(n+1);
										value=uniqueY[n];
										
										
										if(xtrue)
										{
											
											System.out.println("SERIES" + series.get(m).getXVal());
											centreh = getCentreH(startvalue+series.get(m).getXVal(),startpos);
											
											
											barposition[a]= new BarPosition();

											barposition[a].setTopEdge(centrev-mBarWidth/2);
											barposition[a].setBottomEdge(centrev+mBarWidth/2);
											barposition[a].setLeftEdge(startpos);
											barposition[a].setRightEdge(centreh);
											barposition[a].setValue(series.get(m).getXVal());
											
											drawBar(canvas, centreh,centrev,series.get(m).getColor(),startpos);
											
											if(drawLabels)
												 
											{
												
													
													   System.out.println("DRAW LABEL");
													   canvas.save();
													   canvas.rotate((float)(90));
													   canvas.drawText(series.get(m).getLabel(),0,
																series.get(m).getLabel().length(),
																centrev,-(centreh+labPaint.ascent()), labPaint);
													   canvas.restore();
												   
												   
											}
											
											pos.put(uniqueY[n],series.get(m).getXVal());
											
										}
										
										
										a++;
										
							    }
								
								
								
								
								
								
						
						
							}
							
							
							
							
						}
						
						
						startpos = centreh;
						startvalue= pos.get(uniqueY[n]);
					
						
						}
					}
					
				}
				
				
				
			
				
			
		
		
		}
		
		else
		{
			
			
			if(YisCategory)
				
			{
				
				Map<String,Double> pos = new HashMap<String,Double>();
				
				for(int n=0; n<uniqueY.length;n++)
					
				{
					float startpos = coordbounds.bottom;
					 String value="";
					 double startvalue=0;
					 
					
					
					for(int i=1; i<=numseries;i++)
					{
						float centreh=0.0f,centrev=0.0f;
					
					for(int m=0;m<series.size();m++)
					{
						
						Boolean xtrue=false; Boolean ytrue=false;
						
						if(series.get(m).getSeries()==i)
						{
							if(XisNum)
							{
								xtrue=true;
							}
							
							
							bPaint.setColor(series.get(m).getColor());
							
							if(series.get(m).getYLabel().equals(uniqueY[n]))
						    {
									//centreh = getCentreH(n+1);
									centreh = getCentreHCategory(n+1);
									value=uniqueY[n];
									
									
									if(xtrue)
									{
										
										
										
										centrev = getCentreV(startvalue+series.get(m).getXVal(),
												startpos);
										
										
										barposition[a]= new BarPosition();
										
										barposition[a].setLeftEdge(centreh-mBarWidth/2);
										barposition[a].setRightEdge(centreh+mBarWidth/2);
										barposition[a].setTopEdge(centrev);
										barposition[a].setBottomEdge(startpos);
										barposition[a].setValue(series.get(m).getXVal());
										
										
										if(drawLabels)
											 
										{
											
												
												   canvas.drawText(series.get(m).getLabel(),0,
															series.get(m).getLabel().length(),
															centreh,centrev-labPaint.ascent(), labPaint);
											  
											   
										}
										drawBar(canvas, centreh,centrev,series.get(m).getColor(),startpos);
										
										System.out.println("CENT" + centrev);
										
										pos.put(uniqueY[n],series.get(m).getXVal());
									}
									
									a++;
						    }
					
					
						}
						
						
						
						
						
					}
					startpos = centrev;
					System.out.println("Y" + uniqueY[n]);
					System.out.println("pos" + pos);
					startvalue= pos.get(uniqueY[n]);
					
					
					}
				}
				
				
			}
			
			if(XisCategory)
			{
				
				
				Map<String,Double> pos = new HashMap<String,Double>();
				
				
				for(int n=0; n<uniqueX.length;n++)
				{
					
				 float startpos = coordbounds.left;
				 String value="";
				 double startvalue=0;
				 
				 System.out.println("SERVALUE" + uniqueX[n]);
						
				  for(int i=1; i<=numseries;i++)
				  {
					 float centreh=0.0f,centrev=0.0f;
					
					for(int m=0;m<series.size();m++)
					{
						
						Boolean xtrue=false; Boolean ytrue=false;
						
						if(series.get(m).getSeries()==i)
						{
							
							if(YisNum)
							{
								ytrue=true;
							}
							
							bPaint.setColor(series.get(m).getColor());
							
							if(series.get(m).getXLabel().equals(uniqueX[n]))
						    {
									//centrev = getCentreV(n+1);
									centrev = getCentreVCategory(n+1);
									value=uniqueX[n];
									
									
									if(ytrue)
									{
										
										System.out.println("SERIES" + series.get(m).getYVal());
										centreh = getCentreH(startvalue+series.get(m).getYVal(),
												startpos);
										drawBar(canvas, centreh,centrev,series.get(m).getColor(),
												startpos);
										
										if(drawLabels)
											 
										{
											
												
											
											   canvas.save();
											   canvas.rotate((float)(90));
											   canvas.drawText(series.get(m).getLabel(),0,
														series.get(m).getLabel().length(),
														centrev,-(centreh+labPaint.ascent()), labPaint);
											   canvas.restore();
											  
											   
										}
										
										barposition[a]= new BarPosition();

										barposition[a].setTopEdge(centrev-mBarWidth/2);
										barposition[a].setBottomEdge(centrev+mBarWidth/2);
										barposition[a].setLeftEdge(startpos);
										barposition[a].setRightEdge(centreh);
										barposition[a].setValue(series.get(m).getYVal());
										
										
										pos.put(uniqueX[n],series.get(m).getYVal());
										
									}
									
									a++;
									
						    }
							
							
							
							
							
							
					
					
						}
						
						
						
						
					}
					
					
					startpos = centreh;
					startvalue= pos.get(uniqueX[n]);
				
					
					}
				}
				
			}
		}
		
	}

	private void drawBar(Canvas canvas, float centreh,float centrev,int color,float pos)
	{
		
		if(!isHorizontal)
		{
			if(YisCategory)
				canvas.drawRect(pos,centrev-mBarWidth/2,centreh,centrev+(mBarWidth/2),bPaint);
			
			if(XisCategory)
			{
				
				System.out.println("POS" + centrev + "POS2"+pos + "CB" + coordbounds.bottom);
				
				canvas.drawRect(centreh-mBarWidth/2,centrev,centreh+mBarWidth/2,pos,bPaint);
				
			}
			
				
		}
		else
		{
			if(YisCategory)
				
				canvas.drawRect(centreh-mBarWidth/2,centrev,centreh+mBarWidth/2,pos,bPaint);
			
			if(XisCategory)
				canvas.drawRect(pos,centrev-mBarWidth/2,centreh,centrev+(mBarWidth/2),bPaint);
			
		}
	}

	@Override 
	 public boolean onTouchEvent(MotionEvent event)
	 {
		
		if(event.getAction()==MotionEvent.ACTION_UP)
		{
			
			xpoint = event.getX();
			ypoint = event.getY();
			
			System.out.println("XPOINT" + xpoint);
			System.out.println("YPOINT" + ypoint);
			
			getChosenPoint();
			
			
		    
			
		}
		
		
		 boolean retVal = mScaleGestureDetector.onTouchEvent(event);
		 retVal = mGestureDetector.onTouchEvent(event)||retVal;
		 return retVal || super.onTouchEvent(event);
	 }
	
	
	//*********************************************//
	 private void getXCategory()
		{
			 List<String> uniX= new ArrayList<String>();
			 for(int i=0; i<series.size();i++)
			 {
						
					uniX.add(series.get(i).getXLabel());
			 }		
			Set<String> uxVal= new HashSet<String>(uniX);
			uniqueX = uxVal.toArray(new String[uxVal.size()]);
			
				
		}
		 
		 
		 private String[] getYCategory()
		{
				 List<String> uniY= new ArrayList<String>();
				 for(int i=0; i<series.size();i++)
				 {
						
					uniY.add(series.get(i).getYLabel());
				 }		
				Set<String> uyVal= new HashSet<String>(uniY);
				uniqueY = uyVal.toArray(new String[uyVal.size()]);
				
				return uniqueY;
		}
		 
		 //**************************************************//
		 
		public void setSeriesColors(Map<String,Integer> seriescolor)
		 {
		 		this.seriescolor=seriescolor;
		 		//setColors();
		 		getSeriesNames();
		 }
		
		
		 	
		public void setSeries(List<MultipleSeriesItem> series,int numseries)
		{
					this.series=series;
					this.numseries=numseries;
					
					seriescolor = new HashMap<String,Integer> ();
					
					
					
					
		}
		
		private void assignColors()
		{
			getSeriesNames();
			if(assignSeriesColors)
				calculateSeriesColors(numseries,
					useSeriesPrimary,accentprimarycolor,seriescolor,seriesname);
			//setColors();
			else
				calculateColorsFromSeries();
			
			
			setColors();
		}
		
		private void calculateColorsFromSeries() {
			
			
			List<Integer> colors = new ArrayList<Integer>();
			for(int i=0;i<series.size();i++)
			{
				
				colors.add(series.get(i).getColor());
			}
			
			 cols= new HashSet<Integer>(colors).toArray(new Integer[numseries]);
			
			 
			 
			
			 for(int i=0;i<series.size();i++)
			 {
				
				 System.out.println("SERIES"+ series.get(i).getSeries()+
						 
						 series.get(i).getColor());
				 
				 
				 seriescolor.put(series.get(i).getSeriesName(),series.get(i).getColor());
				
				 
			 }
			 
			 
			 
		
			
		}
		
		private void setColors()
		{
			 for(int i=1;i<=seriesname.length;i++)
				{
					for(int j=0; j< series.size();j++)
					{
						if(series.get(j).getSeriesName()==seriesname[i-1])
						{
							series.get(j).setColor(seriescolor.get(series.get(j).getSeriesName()));
						}
					}
					
					
				}
		}
		
		private void getSeriesNames() {
			
			
			List<String> names = new ArrayList<String>();
			for(int i=0;i<series.size();i++)
			{
				if(series.get(i).getSeriesName()!=null)
					names.add(series.get(i).getSeriesName());
			}
			
			
			 Set<String> sernames =new HashSet<String>(names);
			 if(sernames.size()==numseries)
			 {
				 seriesname = new String[numseries];
				 seriesname= sernames.
					 toArray(new String[numseries]);
			 }
			 else
			 {
				 seriesname = new String[numseries];
				 for(int i=0;i<numseries;i++)
					 seriesname[i]="";
			 }
			 
			 	
		}
			

			
			
			/**
			 * 
			 * @param xLabelName set label for x-axis
			 */
			public void setXLabelName(String xLabelName)
			{
				this.xLabelName =xLabelName;
			}
			
			public String getXLabelName()
			{
				return xLabelName;
			}
			
			/**
			 * 
			 * @param yLabelName set label for y-axis
			 */
			public void setYLabelName(String yLabelName)
			{
				this.yLabelName =yLabelName;
			}
			
			
			
			public String getYLabelName()
			{
				return yLabelName;
			}
			
			
			public void getChosenPoint()
		    {
		    	
				for(int i = 0; i< barposition.length;i++)
				{
					System.out.println("LEFT" + barposition[i].getLeftEdge()+ 
							"RIGHT"+barposition[i].getRightEdge());
					
					System.out.println("TOP" + barposition[i].getTopEdge()+ 
							"BOTTOM"+barposition[i].getBottomEdge());
					
					
					if(xpoint>=barposition[i].getLeftEdge()& 
							xpoint<=barposition[i].getRightEdge()& ypoint >=barposition[i].getTopEdge()& ypoint <= barposition[i].getBottomEdge())
					{
						
						System.out.println("TRUE");
						Toast toast = Toast.makeText(context,Double.toString(barposition[i].getValue()) , Toast.LENGTH_SHORT);
				    	toast.setGravity(Gravity.TOP|Gravity.LEFT,(int)
				    			(boxbounds.left+ xpoint),
				    			(int)(boxbounds.top+ ypoint));
				    	toast.show();
				    	;
				    	
					}
				}
		    	
		    	
		    	
		    	
		    }
			
}
