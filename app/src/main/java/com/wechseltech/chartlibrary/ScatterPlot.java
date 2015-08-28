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
import android.graphics.Paint.Cap;
import android.graphics.RectF;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;

public class ScatterPlot extends GraphView {

	//TODO Not tested category yet.. incorporate spider changes in computing intervals to cat also....
	
	
	
	OptimizeTicks tasks;
	
	
	
	List<Item> series;
	float axisLabelSpace;
	int mLabelWidth,mLabelHeight,mAxisLabelHeight,
	minLabelWidth,minLabelHeight ,mAxisLabelWidth;
	
	Paint vPaint,fPaint;
	float mVLabelWidth;
	Integer[] cols;
	Context context;
	
	ReturnIntervalsTicks ticks;
	
	Map<String,Integer> seriescolor;

	String xLabelName,yLabelName;
	Paint aPaint,legendPaint,
	tPaint,lPaint,rPaint,pointPaint,labelPaint;
	
	float xpoint,ypoint;
	
	
	BarPosition[] barposition;
	
	
	String[] uniqueX,uniqueY;
	String[] seriesname;
	
	//Map<Integer,String> seriesname;
	int numseries;
	
	public ScatterPlot(Context context) {
		super(context);
		
		 hstops = new AxisStops();
		 vstops = new AxisStops();
		 
		 this.context=context;
		 
	    System.out.println("SCATTER");
		
		mEdgeEffectTop = new EdgeEffectCompat(context);
		mEdgeEffectLeft = new EdgeEffectCompat(context);
		mEdgeEffectRight = new EdgeEffectCompat(context);
		mEdgeEffectBottom = new EdgeEffectCompat(context);
		
		mScaleGestureDetector = new ScaleGestureDetector(context,mScaleGestureListener);
		mGestureDetector = new GestureDetectorCompat(context,mGestureListener);
		
	}

	
	public ScatterPlot(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		System.out.println("SCATTER AT");
		
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
			mTextSize = a.getDimension(R.styleable.Charts_textSize, 12);
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


	public ScatterPlot(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
		
		 System.out.println("SCATTER DF");
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
			mTextSize = a.getDimension(R.styleable.Charts_textSize, 12);
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
		
			System.out.println("INIT");
		
			lines = new DrawGridLines();

			gPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			gPaint.setColor(gridcolor);
			
			
			 lPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     lPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		     
		     
			 aPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     aPaint.setColor(textcolor);
		     aPaint.setTextSize(mAxisLabelSize);
		     aPaint.setTextAlign(Paint.Align.CENTER);
		        
		     labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     labelPaint.setColor(textcolor);
		     labelPaint.setTextSize(mLabelSize);
		     labelPaint.setTextAlign(Paint.Align.CENTER);
		     
		     tPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     tPaint.setColor(textcolor);
		     tPaint.setTextSize(mTextSize);
		     tPaint.setTextAlign(Paint.Align.CENTER);
		     
		     
		     legendPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     legendPaint.setColor(textcolor);
		     legendPaint.setTextSize(mTextSize);
		     legendPaint.setTextAlign(Paint.Align.LEFT);
		     
		     rPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     rPaint.setColor(rectcolor);
		     rPaint.setStyle(Paint.Style.STROKE);
		     
		   
		     
		     
		     	vPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		        vPaint.setColor(textcolor);
		        vPaint.setTextSize(mTextSize);
		        vPaint.setTextAlign(Paint.Align.RIGHT);
		        
		    	fPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		        fPaint.setColor(textcolor);
		        fPaint.setTextSize(mTextSize);
		        fPaint.setTextAlign(Paint.Align.LEFT);
		        
		     
		     pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		    
		     pointPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		        
			 mLabelHeight =(int) Math.abs(tPaint.getFontMetrics().top);
		        
		     drawBoxSize = mLabelHeight;
		        
		     minLabelHeight = (int) Math.abs(tPaint.getFontMetrics().bottom);
		    
		     minLabelWidth = (int) Math.abs(tPaint.measureText("000"));
		     mLabelWidth = (int) Math.abs(tPaint.measureText("000000"));
		     mAxisLabelHeight = (int) Math.abs(aPaint.getFontMetrics().top);
		     
		     mVLabelWidth = (int) Math.abs(tPaint.measureText("00000"));
		     
		    
			
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
		
	
	
	@Override 
	 public void onDraw(Canvas canvas)
	 
	 {
		super.onDraw(canvas);
		
		int clipRestoreCount = canvas.save();
		canvas.clipRect(coordbounds);
		
		drawAxesScatter(canvas);
		
		//drawAxes(canvas,showgrid);
	
		drawData(canvas);
		
		drawEdgeEffectsUnclipped(canvas);
		
		
		
		
		canvas.restoreToCount(clipRestoreCount);
		
		
		/*if(!invalidate)
		drawLegend(canvas, legendPaint, lPaint,numseries,
				seriesname,seriescolor,mLabelWidth,ticks,mAxisLabelHeight,aPaint);
		else
            drawInvalidateLegend(canvas);*/

         drawLegend(canvas, legendPaint, lPaint,numseries,
                 seriesname,seriescolor,mLabelWidth,ticks,mAxisLabelHeight,aPaint);
		
		drawText(canvas,tPaint,aPaint,ticks,getXLabelName(),
				getYLabelName(),
				mAxisLabelHeight,mAxisLabelWidth,isHorizontal,XisCategory,
				YisCategory,XisNum, YisNum,uniqueX,uniqueY,vPaint,fPaint) ;
		
		
		
		canvas.drawRect(coordbounds,rPaint);
		 
		 
	 }
	
	
	
	
	private void drawInvalidateLegend(Canvas canvas) {
		System.out.println("LEGEND" + legendBox);
		 



        int row=1;
        int a=0;
        float lastleft=0;
        int [] labelwidths = new int[numseries];


        for (int i=0; i<labelwidths.length; i++)

        {
            labelwidths[i]= (int) Math.abs(legendPaint.measureText(seriesname[i]+"0"));

        }



        for (int i=0; i<seriesname.length; i++)
			
		{
			float left=0, right =0;

			
			lPaint.setColor(seriescolor.get(seriesname[i]));
			legendPaint.setColor(seriescolor.get(seriesname[i]));


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

            canvas.drawRect(left, top, right,
                    bottom, lPaint);
            System.out.println(seriesname[i]);
            canvas.drawText(seriesname[i],right+drawSpace,
                    bottom, legendPaint);
			
	
		}
		
		
	}
	private void drawAxesScatter(Canvas canvas)
	{
		
		float [] hposstops = new float[hstops.axisstops.length];
		float [] vposstops= new float[vstops.axisstops.length];
		
		System.out.println("COW" + coordbounds.width());
	
		
		if(!isHorizontal)
		{
			
			if(XisNum & YisNum)
			{
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
			}
			
			if(XisCategory & YisCategory)
			{
				for(int i=0; i< hstops.axisstops.length;i++)
				{
					hposstops [i] = (float) (coordbounds.left + barspace*
							(hstops.axisstops[i]-Viewport.left));
					System.out.println(hposstops[i]+"HposStops");
				}
				
				for(int j=0;j<vstops.axisstops.length;j++)
				{
					vposstops [j] = (float) (coordbounds.bottom - barspace*
							(vstops.axisstops[j]-Viewport.top));
					
					
					System.out.println(vposstops[j]+"VposStops");
				}
			}
			
			
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
			
			if(XisCategory & YisCategory)
			{
				for(int i=0; i< hstops.axisstops.length;i++)
				{
					hposstops [i] = (float) (coordbounds.left + barspace*
							(hstops.axisstops[i]-Viewport.left));
					System.out.println(hposstops[i]+"HposStops");
				}
				
				for(int j=0;j<vstops.axisstops.length;j++)
				{
					vposstops [j] = (float) (coordbounds.bottom - barspace*
							(vstops.axisstops[j]-Viewport.top));
					
					
					System.out.println(vposstops[j]+"VposStops");
				}
			}
			
			
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
			
			if(XisNum & YisNum)
			{
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
					if(showgrid & !YisNum)
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
					if(showgrid & !XisNum)
					AxisHGridLines = lines.drawHorizontalGrid(hstops,coordbounds,true);
					else
					AxisHGridLines = lines.drawHorizontalGrid(hstops,coordbounds,false);
				}
				
			}
			
		 
		 
			
			canvas.drawLines(AxisHGridLines, 0,AxisHGridLines.length,gPaint);
			
			
			if(showgrid)
				
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
	
	private float getCentreHCategory(double value)
	{
		
		
		
		float pos = coordbounds.left + barspace*
				(float)(value-Viewport.left);
		
		return pos;
		
		
	}
	
	private float getCentreVCategory(double value)
	{
		float pos = 0;
		
//		pos = coordbounds.bottom - coordbounds.height()*
//				(float)(value-Viewport.top)/Viewport.height();
		
		pos = coordbounds.bottom - barspace*
				(float)(value-Viewport.top);
		
		return pos;
		
	}
	private float getCentreH(double value)
	{
		float pos = coordbounds.left + coordbounds.width()*
		(float)(value-Viewport.left)/Viewport.width();
		
		return pos;
	}
	
	private float getCentreV(double value)
	{
		float pos = coordbounds.bottom - coordbounds.height()*
		(float)(value-Viewport.top)/Viewport.height();
		
		return pos;
	}
	
	private void drawData(Canvas canvas)
	{
		
		barposition = new BarPosition[series.size()];
		
		
		if(!isHorizontal)
		{
			
				for(int m=0; m<series.size();m++)
				{
					
					float centreh=0.0f,centrev=0.0f;
					
					
					if(XisCategory)
						
					{
						for(int n=0; n<uniqueX.length;n++)
							
						{
							if(series.get(m).getStringXVal().equals(uniqueX[n]))
							{
							 //centreh = getCentreH(n+1);
								centreh = getCentreHCategory(n+1);
								
								barposition[m]= new BarPosition();
								
								barposition[m].setLeftEdge(centreh-15);
								barposition[m].setRightEdge(centreh+15);
								
								
							}
							
						}
						
						
					}
					
					else
					{
						centreh = getCentreH(series.get(m).getXVal());
						
						barposition[m]= new BarPosition();
						
						barposition[m].setLeftEdge(centreh-15);
						barposition[m].setRightEdge(centreh+15);
						barposition[m].setXValue(series.get(m).getXVal());
						
					}
					
					if(YisCategory)
						
					{
						for(int r=0; r<uniqueY.length;r++)
							
						{
							if(series.get(m).getStringYVal().equals(uniqueY[r]))
							// centrev = getCentreV(r+1);
							{
								centrev = getCentreVCategory(r+1);
								
								barposition[m].setTopEdge(centrev-15);
								barposition[m].setBottomEdge(centrev+15);
								
							}
						}
						
						
					}
					
					else
					{
						centrev = getCentreV(series.get(m).getYVal());
						
						barposition[m].setTopEdge(centrev-15);
						barposition[m].setBottomEdge(centrev+15);
						barposition[m].setYValue(series.get(m).getYVal());
					}
						
						System.out.println("SERIES" + seriescolor.get(series.get(m).getSeries()));
						/*if(seriescolor.containsKey(series.get(m).getSeries()))
							pointPaint.setColor(seriescolor.get(series.get(m).getSeries()));
						else
							pointPaint.setColor(Color.GRAY);*/
							
							pointPaint.setColor(series.get(m).getSeriesColor());
						
						if(drawLabels)
						{
							
							if(series.get(m).getLabel()!=null)
							canvas.drawText(series.get(m).getLabel(),
									centreh,centrev-10, labelPaint);
							//canvas.drawPoint(centreh, centrev, pointPaint);
							barposition[m].setLabel(series.get(m).getLabel());
							canvas.drawCircle(centreh, centrev,10, pointPaint);
						}
						else
							//canvas.drawPoint(centreh, centrev, pointPaint);
							canvas.drawCircle(centreh, centrev,10, pointPaint);
							
					
				}
			
			
			

			
			
		}
		
		else
		{
			for(int m=0; m<series.size();m++)
			{
				
				
				float centreh=0.0f,centrev=0.0f;
				
				
				if(XisCategory)
					
				{
					for(int n=0; n<uniqueX.length;n++)
						
					{
						if(series.get(m).getStringXVal().equals(uniqueX[n]))
						{
						// centrev = getCentreV(n+1);
							centrev = getCentreVCategory(n+1);
							barposition[m]= new BarPosition();
							
							barposition[m].setTopEdge(centrev-15);
							barposition[m].setBottomEdge(centrev+15);
							
							
						}
					}
					
					
				}
				
				else
				{
					centrev = getCentreV(series.get(m).getXVal());
					
					barposition[m]= new BarPosition();
					
					barposition[m].setTopEdge(centrev-15);
					barposition[m].setBottomEdge(centrev+15);
					barposition[m].setXValue(series.get(m).getXVal());
				}
				
				if(YisCategory)
					
				{
					for(int r=0; r<uniqueY.length;r++)
						
					{
						if(series.get(m).getStringYVal().equals(uniqueY[r]))
						// centreh = getCentreH(r+1);
						{
							centreh = getCentreHCategory(r+1);
							barposition[m].setLeftEdge(centreh-15);
							barposition[m].setRightEdge(centreh+15);
							
						}
					}
					
					
				}
				
				else
				{
					centreh = getCentreH(series.get(m).getYVal());
					barposition[m].setLeftEdge(centreh-15);
					barposition[m].setRightEdge(centreh+15);
					barposition[m].setYValue(series.get(m).getYVal());
				}
				
					/*if(seriescolor.containsKey(series.get(m).getSeries()))
					pointPaint.setColor(seriescolor.get(series.get(m).getSeries()));
					else
					pointPaint.setColor(Color.GRAY);*/
					
					pointPaint.setColor(series.get(m).getSeriesColor());
					
					if(drawLabels)
					{	
						if(series.get(m).getLabel()!=null)
						canvas.drawText(series.get(m).getLabel(),centreh,centrev-10, labelPaint);
						//canvas.drawPoint(centreh, centrev, pointPaint);
						
						barposition[m].setLabel(series.get(m).getLabel());
						canvas.drawCircle(centreh, centrev, 10, pointPaint);
					}
					else
						//canvas.drawPoint(centreh, centrev, pointPaint);
						canvas.drawCircle(centreh, centrev, 10, pointPaint);
				
			}
		}
		
	}
	
	 @Override
	 protected void onSizeChanged(int w, int h, int oldw, int oldh)
		{
		 
		 
		 	   System.out.println("SIZE CHANGED");
			
				float xpad = (float)(getPaddingLeft()+ getPaddingRight());
				float ypad = (float)(getPaddingTop() +  getPaddingBottom());
				
				float coww =0.0f,cohh=0.0f,coll =0.0f;
				
				
				init();
				
				coww = (float)  w-xpad
						-mLabelWidth-2*mAxisLabelHeight-4*mLabelSeparation-(-aPaint.ascent());
				
				cohh = (float)  h-ypad-mLabelHeight-
						(2*mAxisLabelHeight)-(-aPaint.ascent())-(legendSize) - 3 * mLabelSeparation;
				
				

				float bxww = (float) w-xpad;
				float bxhh = (float) h-ypad;
				
				mAxisLabelWidth = (int) (coww/2);
				
				boxbounds = new RectF(getPaddingLeft(),getPaddingTop(),
						getPaddingLeft()+bxww,getPaddingTop()+bxhh);
					
				//coll = bxhh-legendSize-mLabelSeparation;
				
				coordbounds = new RectF(0.0f,0.0f,
						coww,cohh);
				
				
				
				float xoffset = mLabelWidth+ (2*mAxisLabelHeight) - aPaint.ascent();
				
				
				axisLabelSpace = (2*mAxisLabelHeight-aPaint.ascent())+3*mLabelSeparation;
				
				
				
				//coordbounds.offset(getPaddingLeft()+ xoffset,getPaddingTop());
				
				coordbounds.offset(boxbounds.top+ xoffset,boxbounds.top+mLabelSeparation);
				
				legendBox = new RectF(0.0f,0.0f,coww,legendSize);
				
				legendBox.offset(getPaddingLeft()+xoffset,coordbounds.bottom+mLabelSeparation);
				
				tasks = new OptimizeTicks(mLabelWidth, mLabelHeight,coordbounds.width(),
						coordbounds.height(), minLabelWidth, minLabelHeight,
						bxhh,bxww,axisLabelSpace,legendSize);
				
				assignColors();
				
				getXYTicks(boxbounds.width(),boxbounds.height(),coordbounds.width(),axisLabelSpace);
			
			
		}


	 private void getXYTicks(float bxw,float bxh,float coww,float axisLabelSpace) {
			
		 
		  ticks=null;
		 
			
		 HandleXYTicks handles = new HandleXYTicks(coordbounds,
					getPaddingLeft(),getPaddingTop(),mAxisLabelHeight, 
					mLabelSeparation,aPaint,legendSize,mVLabelWidth);
		 
		 if(XisNum & YisNum)
		 {
			 	
				
				 double [] maxvalX = new double[series.size()];
				 double [] maxvalY = new double[series.size()];
					
				 
				for(int i=0;i<series.size();i++)
				{
						maxvalX[i]=series.get(i).getXVal();
						maxvalY[i]=series.get(i).getYVal();
				}
				 Arrays.sort(maxvalX);
				 Arrays.sort(maxvalY);
				    
				    
				ticks = handles.getXNumericYNumeric(isHorizontal,
					maxvalX[maxvalX.length-1],maxvalX[0],
					maxvalY[maxvalY.length-1],maxvalY[0],
					maxvalX,maxvalY,hstops,vstops,
					boxbounds.height(),bxw,
					axisLabelSpace,tasks);
		 }
		
		 if(XisNum & YisCategory)
		 {
			 

			double [] maxvalX = new double[series.size()];
			getYCategory();
			
			for(int i=0;i<series.size();i++)
			{
					maxvalX[i]=series.get(i).getXVal();
					
			}
			Arrays.sort(maxvalX); 
			
			ticks = handles.getYCategoryXNumeric(isHorizontal,
					maxvalX[maxvalX.length-1],maxvalX[0], maxvalX, hstops, vstops, 
					uniqueY, bxh, bxw, axisLabelSpace,tasks);
			
			 
		 }
		 
		 if(YisNum & XisCategory)
		 {
			 

			double [] maxvalY = new double[series.size()];
			getXCategory();
			
			for(int i=0;i<series.size();i++)
			{
					maxvalY[i]=series.get(i).getYVal();
					
			}
			Arrays.sort(maxvalY); 
			
			ticks = handles.getXCategoryYNumeric(isHorizontal, 
					maxvalY[maxvalY.length-1],maxvalY[0], maxvalY, hstops, 
					vstops, uniqueX, bxh,bxw, axisLabelSpace,tasks);
			
			 
		 }
		 
		 if(XisCategory & YisCategory)
		 {
			 getXCategory();
			 getYCategory();
			 
			 ticks = handles.getXCategoryYCategory(isHorizontal, hstops, vstops, uniqueY, 
					 uniqueX, bxh,bxw, axisLabelSpace,tasks);
			 
			
		 }
		 
		 coordbounds = ticks.coordbounds;
		 Viewport=ticks.Viewport;
		 legendBox=ticks.legendBox;
		 AXIS_H_MAX = ticks.AXIS_H_MAX;
		 AXIS_H_MIN = ticks.AXIS_H_MIN;
		 AXIS_V_MAX = ticks.AXIS_V_MAX;
		 AXIS_V_MIN = ticks.AXIS_V_MIN;
		 
		 hstops = ticks.hstops;
		 hInterval = ticks.hInterval;
		 vstops = ticks.vstops;
		 vInterval = ticks.vInterval;
		 isHFlipped = ticks.isHflipped;
		 
		 nHticks = ticks.nHticks;
		 nVticks = ticks.nVticks;
		 
		 if(XisCategory & YisCategory)
		 {
			
			int visHTicks =(int)Math.floor(coordbounds.width()/barspace);
			int visVTicks =(int)Math.floor(coordbounds.height()/barspace);
			
			System.out.println("VIS" + visHTicks+ visVTicks);
			if(hstops.axisstops.length>=visHTicks)
			 Viewport.right=visHTicks;
			
			
				
			if(vstops.axisstops.length>=visVTicks)
				Viewport.bottom=visVTicks;
			
			System.out.println("VIS" + visHTicks+ visVTicks + "VPR" + Viewport.right +"VPB" + Viewport.bottom);
		 
		 }
		 
		 if(XisCategory & YisNum)
		 {
			
			if(!isHorizontal)
			{
			int visHTicks =(int)Math.floor(coordbounds.width()/barspace);
			
			
			System.out.println("VIS" + visHTicks);
			if(hstops.axisstops.length>=visHTicks)
			 Viewport.right=visHTicks;
			
			}
			else
			{
				
			int visVTicks =(int)Math.floor(coordbounds.height()/barspace);
			
			if(vstops.axisstops.length>=visVTicks)
				Viewport.bottom=visVTicks;
			
			}
		 }	
		 
		 if(YisCategory & XisNum)
		 {
			
			if(!isHorizontal)
			{
			int visVTicks =(int)Math.floor(coordbounds.height()/barspace);
			
			
			System.out.println("VIS" + visVTicks);
			if(vstops.axisstops.length>=visVTicks)
			 Viewport.bottom=visVTicks;
			
			}
			else
			{
				
			int visHTicks =(int)Math.floor(coordbounds.width()/barspace);
			
			if(hstops.axisstops.length>= visHTicks)
				Viewport.right=visHTicks;
			
			}
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
		
		
		
		public int getNumSeries()
		{
			return numseries;
			
		}
		
		
		 private void getXCategory()
			{
				 List<String> uniX= new ArrayList<String>();
				 for(int i=0; i<series.size();i++)
				 {
							
						uniX.add(series.get(i).getCatXLabel());
				 }		
				Set<String> uxVal= new HashSet<String>(uniX);
				uniqueX = uxVal.toArray(new String[uxVal.size()]);
				
					
			}
			 
			 
			 private String[] getYCategory()
			{
					 List<String> uniY= new ArrayList<String>();
					 for(int i=0; i<series.size();i++)
					 {
							
						uniY.add(series.get(i).getCatYLabel());
					 }		
					Set<String> uyVal= new HashSet<String>(uniY);
					uniqueY = uyVal.toArray(new String[uyVal.size()]);
					
					return uniqueY;
			}
			 
			 
			 	public void setSeries(List<Item> series,int numseries)
				{
			 		
			 				seriescolor = new HashMap<String,Integer>();
			 				this.numseries=numseries;
							this.series=series;
							
							System.out.println("SAC");
							
							
							
							
							
				}

			 	
			 	private void assignColors()
			 	{
			 		getSeriesNames();
					
					
					if(assignSeriesColors)
					{
						
						System.out.println("SC");
						
						calculateSeriesColors(numseries,
							useSeriesPrimary,accentprimarycolor,
							seriescolor,seriesname);
						
					}
					
					else
						calculateColorsFromSeries();

					
					setColors();
			 	}

			private void calculateColorsFromSeries() {
				
				
				List<Integer> colors = new ArrayList<Integer>();
				for(int i=0;i<series.size();i++)
				{
					
					colors.add(series.get(i).getSeriesColor());
				}
				
				 cols= new HashSet<Integer>(colors).toArray(new Integer[numseries]);
				
				
				 for(int i=0;i<series.size();i++)
				 {
					
					 System.out.println("SERIES"+ series.get(i).getSeries()+
							 
							 series.get(i).getSeriesColor());
					 
					 
					 seriescolor.put(series.get(i).getSeriesName(),series.get(i).getSeriesColor());
					
					 
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
								series.get(j).setSeriesColor(seriescolor.get(series.get(j).getSeriesName()));
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
					 
					 /*	for(int i=1;i<=seriesname.length;i++)
						{
							for(int j=0; j< series.size();j++)
							{
								if(series.get(j).getSeriesName()==seriesname[i-1])
								{
									series.get(j).setSeriesColor(seriescolor.get(i));
								}
							}
							
							System.out.println("SC"+ seriescolor.get(i));
						}*/
				}
		
			 	
		//*****************************************//
	
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
							if(drawLabels)
							{
								Toast toast=null;
								if(XisNum & YisNum)
								{
								toast = Toast.makeText(context,barposition[i].getLabel()+"-"+Double.toString(barposition[i].getXValue())+" , "+Double.toString(barposition[i].getYValue()) , Toast.LENGTH_SHORT);
								}
								else if(XisNum& ! YisNum)
								{
									toast = Toast.makeText(context,barposition[i].getLabel()+"-"+Double.toString(barposition[i].getXValue()) , Toast.LENGTH_SHORT);
								}
								else if(YisNum& ! XisNum)
								{
									toast = Toast.makeText(context,barposition[i].getLabel()+"-"+Double.toString(barposition[i].getYValue()) , Toast.LENGTH_SHORT);
								}
							
								if(toast!=null)
								{
										toast.setGravity(Gravity.TOP|Gravity.LEFT,(int)
					    			(boxbounds.left+ xpoint),
					    			(int)(boxbounds.top+ ypoint));
										toast.show();
								}
							
							}
							else
							{
								
							Toast toast=null;
							if(XisNum & YisNum)
							{
							toast = Toast.makeText(context,Double.toString(barposition[i].getXValue())+" , "+Double.toString(barposition[i].getYValue()) , Toast.LENGTH_SHORT);
							}
							else if(XisNum& ! YisNum)
							{
								toast = Toast.makeText(context,Double.toString(barposition[i].getXValue()) , Toast.LENGTH_SHORT);
							}
							else if(YisNum& ! XisNum)
							{
								toast = Toast.makeText(context,Double.toString(barposition[i].getYValue()) , Toast.LENGTH_SHORT);
							}
							if(toast!=null)
							{
								toast.setGravity(Gravity.TOP|Gravity.LEFT,(int)
					    			(boxbounds.left+ xpoint),
					    			(int)(boxbounds.top+ ypoint));
								toast.show();
							}
							
							}
							
							
					    	;
					    	
						}
					}
			    	
			    	
			    	
			    	
			    }
	
	
}
