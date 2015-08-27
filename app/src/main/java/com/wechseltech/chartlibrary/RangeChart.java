
package com.wechseltech.chartlibrary;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.Toast;

public class RangeChart extends GraphView {
	
	//TODO Highlighting max and min doesn't work
	

	int mLabelWidth,mAxisLabelHeight,
	mLabelHeight,mAxisLabelWidth;
	
	float axisLabelSpace;
	BarPosition[] barposition;
	
	OptimizeTicks tasks;
	Context context;
	
	Paint aPaint;
	ReturnIntervalsTicks ticks;
	List<RangeItem> series;
	
	String[] unique;
	float mVLabelWidth;
	Paint vPaint,fPaint;
	Paint rPaint,
	tPaint,bPaint,mPaint;
	Boolean showgrid;
	int rangecolor;
	float alpha;
	float xpoint,ypoint;
	
	Boolean setHFlipped=false,setVFlipped=false;
	
	
	String xLabelName,yLabelName;
	
	int minLabelHeight,minLabelWidth;
	
	
	
	
	
	
	
	public RangeChart(Context context) {
		super(context);
		
		 hstops = new AxisStops();
		 vstops = new AxisStops();
		
		 this.context=context;
		mEdgeEffectTop = new EdgeEffectCompat(context);
		mEdgeEffectLeft = new EdgeEffectCompat(context);
		mEdgeEffectRight = new EdgeEffectCompat(context);
		mEdgeEffectBottom = new EdgeEffectCompat(context);
		
		mScaleGestureDetector = new ScaleGestureDetector(context,mScaleGestureListener);
		mGestureDetector = new GestureDetectorCompat(context,mGestureListener);
		
		
	}

	
	public RangeChart(Context context, AttributeSet attrs) {
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
			mTextSize = a.getDimension(R.styleable.Charts_textSize, 12);
			
			
			textcolor = a.getColor(R.styleable.Charts_textColor, Color.BLACK);
			gridcolor = a.getColor(R.styleable.Charts_gridColor, Color.BLACK);
			barcolor = a.getColor(R.styleable.Charts_barColor, Color.BLUE);
			showgrid= a.getBoolean(R.styleable.Charts_showGrid, true);
			
			isHorizontal =a.getBoolean(R.styleable.Charts_isHorizontal, false);
			rectcolor =a.getColor(R.styleable.Charts_boundingboxColor, Color.BLACK);
			
			
			
			
		}
		
		finally
		{
			
			a.recycle();
		}
		
		
	}
	
	
	public RangeChart(Context context, AttributeSet attrs,int defStyleAttr) {
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
			mTextSize = a.getDimension(R.styleable.Charts_textSize, 12);
			
			barcolor = a.getColor(R.styleable.Charts_barColor, Color.BLUE);
			textcolor = a.getColor(R.styleable.Charts_textColor, Color.BLACK);
			gridcolor = a.getColor(R.styleable.Charts_gridColor, Color.BLACK);
			
			showgrid= a.getBoolean(R.styleable.Charts_showGrid, true);
			
			isHorizontal =a.getBoolean(R.styleable.Charts_isHorizontal, false);
			rectcolor =a.getColor(R.styleable.Charts_boundingboxColor, Color.BLACK);
			
			
			
			
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
			
			
		     
		     bPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     bPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		     bPaint.setAlpha(175);
		     bPaint.setColor(barcolor);
		     
		     mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		     mPaint.setColor(gridcolor);
		    // mPaint.setColor(barcolor);
		     
		     
			 aPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     aPaint.setColor(textcolor);
		     aPaint.setTextSize(mAxisLabelSize);
		     aPaint.setTextAlign(Paint.Align.CENTER);
		        
		     
		     
		     tPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     tPaint.setColor(textcolor);
		     tPaint.setTextSize(mTextSize);
		     tPaint.setTextAlign(Paint.Align.CENTER);
		     
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
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		
		float xpad = (float)(getPaddingLeft()+ getPaddingRight());
		float ypad = (float)(getPaddingTop() +  getPaddingBottom());

		float coww =0.0f,cohh=0.0f,coll =0.0f;
		
		init();
		
		
		
		
		
		
		coww = (float)w-xpad-2*mAxisLabelHeight-4*mLabelSeparation-(-aPaint.ascent());
		
		cohh = (float) h-ypad-
				(2*mAxisLabelHeight)-(-aPaint.ascent())-(legendSize) - 3 * mLabelSeparation;
		
		coordbounds = new RectF(0.0f,0.0f,
				coww,cohh);
		
		float xoffset = mLabelWidth+(2*mAxisLabelHeight)+3*mLabelSeparation-(-aPaint.ascent());

		float bxww = (float) w-xpad;
		float bxhh = (float) h-ypad;
		
		mAxisLabelWidth = (int) (coww/2);
		
		boxbounds = new RectF(getPaddingLeft(),getPaddingTop(),
				getPaddingLeft()+bxww,getPaddingTop()+bxhh);
		
		
		//coll = bxhh-legendSize-mLabelSeparation;
		
		coordbounds = new RectF(0.0f,0.0f,
				coww,cohh);
		
		axisLabelSpace = (2*mAxisLabelHeight-aPaint.ascent())+3*mLabelSeparation;
		
	
		//axisLabelSpace = (2*mAxisLabelHeight+aPaint.ascent());
		
		//axisLabelSpace = (2*mAxisLabelHeight+aPaint.ascent()+2*mLabelSeparation);
		
		System.out.println("COWW" + coww + "BXWW" + boxbounds.width()+"XOFFSET"+ xoffset);
		
		coordbounds.offset(getPaddingLeft()+ xoffset,getPaddingTop()+mLabelSeparation);
		
		
		legendBox = new RectF(0.0f,0.0f,coww,legendSize);
		
		legendBox.offset(getPaddingLeft()+xoffset,coordbounds.bottom+mLabelSeparation);
		
		
		
		tasks = new OptimizeTicks(mLabelWidth, mLabelHeight,coordbounds.width(),
				coordbounds.height(), minLabelWidth, minLabelHeight,
				bxhh,bxww,axisLabelSpace,0);
		
		
		
		getXYTicks(boxbounds.width(),boxbounds.height(),coordbounds.width(),axisLabelSpace);
		
		
	}
	
	private void getXYTicks(float bxw,float bxh,float coww,float axisLabelSpace) {
		
		 
		  ticks=null;
		 
					
					
		 HandleXYTicks handles = new HandleXYTicks(coordbounds,
					getPaddingLeft(),getPaddingTop(),mAxisLabelHeight, 
					mLabelSeparation,aPaint,mVLabelWidth);
		 
		
		 	double [] val = new double[series.size()*2];
		 	
			getCategory();
			
			int a=0;
			
			for(RangeItem range: series)
			{
					
					val[a]=range.getMaxValue();
					
					a++;
					val[a]=range.getMinValue();
				
					a++;
					
					
					
			}
			
			/*for(int i=0;i<(series.size()*2);i++)
			{
					
					val[i]=series.get(i).getMaxValue();
					val[i+1]=series.get(i).getMinValue();
				
					System.out.println("SER MAX" + val[i]);
					System.out.println("SER MIN" + val[i+1]);
					
					
			}*/
			
			
		
			Arrays.sort(val);
			
			
			
			System.out.println("VAL0" + val[0] + "VALL"+val[val.length-1] );
			
			
					
			ticks = handles.getRange(isHorizontal,
					val[val.length-1],val[0],val, hstops, vstops, 
					unique, bxh,bxw, coww, axisLabelSpace,tasks,mBarWidth,mBarSpacing);
		 
		
		 
		 coordbounds = ticks.coordbounds;
		 Viewport=ticks.Viewport;
		 AXIS_H_MAX = ticks.AXIS_H_MAX;
		 AXIS_H_MIN = ticks.AXIS_H_MIN;
		 AXIS_V_MAX = ticks.AXIS_V_MAX;
		 AXIS_V_MIN = ticks.AXIS_V_MIN;
		 
		 hstops = ticks.hstops;
		 hInterval = ticks.hInterval;
		 vstops = ticks.vstops;
		 vInterval = ticks.vInterval;
		 
		 
		 
		 System.out.println("SET H FLIPPED" + isHFlipped);
		 
		 
		 if(!setHFlipped)
		 {
		 isHFlipped = ticks.isHflipped;
		 }
		 else
			 isHFlipped	=true; 
		 if(!setVFlipped)
		 {
		 isVFlipped = ticks.isVflipped;
		 }
		 else
		 {
			 isVFlipped	=true; 
			 
		 }
		 
		 System.out.println("TICKS" + ticks.isHflipped);
		 
		 nHticks = ticks.nHticks;
		 nVticks = ticks.nVticks;
		 
		 
		 
		 
		 
	 }
	
	
	@Override 
	 public void onDraw(Canvas canvas)
	 
	 {
		super.onDraw(canvas);
		
		int clipRestoreCount = canvas.save();
		canvas.clipRect(coordbounds);
		
		drawAxesRange(canvas,showgrid);
	
		drawData(canvas);
		
		drawEdgeEffectsUnclipped(canvas);
	
		
		
		
		canvas.restoreToCount(clipRestoreCount);
		
		
		drawText(canvas,tPaint, aPaint,ticks,getXLabelName(),getYLabelName(),mAxisLabelHeight,
				mAxisLabelWidth,isHorizontal,true,false,false,true,
					unique,new String[]{""},vPaint,fPaint);
	
		canvas.drawRect(coordbounds,rPaint);
		
		canvas.drawRect(boxbounds,rPaint);
		 
		
		 
	 }
	
	//***************************************//
	
	 private void drawAxesRange(Canvas canvas, Boolean showgrid2) {
		
		
				
			 
			 
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
						
							AxisHGridLines = lines.drawHorizontalGridCategory(hstops,coordbounds);
							
						
						
						
						
					}
					
					else
					{
						
						
						
							if(isGridShown())
							AxisHGridLines = lines.drawHorizontalGrid(hstops,coordbounds,true);
							else
							AxisHGridLines = lines.drawHorizontalGrid(hstops,coordbounds,false);
						
						
					}
					
				 
				 
					
					canvas.drawLines(AxisHGridLines, 0,AxisHGridLines.length,gPaint);
					
					
					if(!isHorizontal)
					{
						
							if(isGridShown())
							AxisVGridLines = lines.drawVerticalGrid(vstops,coordbounds,true);
							else
							AxisVGridLines = lines.drawVerticalGrid(vstops,coordbounds,false);
							
							
						
						
						
						
					}
					
					else
					{
						
						
						AxisVGridLines = lines.drawVerticalGridCategory(vstops,coordbounds,false);
							
						
						
					}
					
					canvas.drawLines(AxisVGridLines, 0,AxisVGridLines.length,gPaint);
					
			
		
		
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
	
	 	private void drawData(Canvas canvas) {
		
	 		
	 		barposition = new BarPosition[series.size()];
	 		
	 		if(!isHorizontal)
			{
	 			
	 		
		 		for(int m=0;m<series.size();m++)
				{
		 			float centreh=0.0f,centremin=0.0f,centremax=0.0f;
		 			String value="";
		 			
		 			barposition[m]= new BarPosition();
		 			
		 			
		 			for(int n=0; n<unique.length;n++)
						
					{
						if(series.get(m).getLabel().equals(unique[n]))
						{
							centreh = getCentreH(n+1);
							value=unique[n];
							
							
						}
						
						
					}
		 			
		 			
		 			
		 			
		 			
		 			
		 			centremin = getCentreV(series.get(m).getMinValue());
					centremax = getCentreV(series.get(m).getMaxValue());
					
					barposition[m].setLeftEdge(centreh-mBarWidth/2);
		 			barposition[m].setRightEdge(centreh+mBarWidth/2);
		 			barposition[m].setTopEdge(centremax);
		 			barposition[m].setBottomEdge(centremin);
		 			
		 			barposition[m].setMinValue(series.get(m).getMinValue());
		 			barposition[m].setMaxValue(series.get(m).getMaxValue());
		 			
					
					canvas.drawRect(centreh-mBarWidth/2,centremax,centreh+mBarWidth/2, centremin, bPaint);
					canvas.drawRect(centreh-mBarWidth/2,centremin,centreh+mBarWidth/2, centremin+4, mPaint);
					canvas.drawRect(centreh-mBarWidth/2,centremax-4,centreh+mBarWidth/2, centremax, mPaint);
					
		 			
				}
		 		
			}
	 		else
	 		{
	 			for(int m=0;m<series.size();m++)
				{
	 				
	 				barposition[m]= new BarPosition();
		 			
	 				float centrev=0.0f,centremin=0.0f,centremax=0.0f;
		 			String value="";
		 			
		 			
		 			
		 			for(int n=0; n<unique.length;n++)
						
					{
						if(series.get(m).getLabel().equals(unique[n]))
						{
							centrev = getCentreV(n+1);
							value=unique[n];
							
							
						}
						
						
					}
		 			
		 			
		 			centremin = getCentreH(series.get(m).getMinValue());
					centremax = getCentreH(series.get(m).getMaxValue());
					
					
					barposition[m].setLeftEdge(centremin);
		 			barposition[m].setRightEdge(centremax);
		 			barposition[m].setTopEdge(centrev-mBarWidth/2);
		 			barposition[m].setBottomEdge(centrev+mBarWidth/2);
		 			
		 			barposition[m].setMinValue(series.get(m).getMinValue());
		 			barposition[m].setMaxValue(series.get(m).getMaxValue());
		 			
					
					canvas.drawRect(centremin,centrev-mBarWidth/2, centremax,centrev+mBarWidth/2 ,bPaint);
					canvas.drawRect(centremin-2,centrev-mBarWidth/2, centremin+2,centrev+mBarWidth/2, mPaint);
					canvas.drawRect(centremax-2,centrev-mBarWidth/2, centremax+2,centrev+mBarWidth/2, mPaint);
		 			
				}
	 			
	 		}
	 		
		
	}

		private String[] getCategory()
		
	 	{
				 List<String> uni= new ArrayList<String>();
				 for(int i=0; i<series.size();i++)
				 {
						
					uni.add(series.get(i).getLabel());
				 }		
				Set<String> uyVal= new HashSet<String>(uni);
				unique = uyVal.toArray(new String[uyVal.size()]);
				
				return unique;
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
					Toast toast = Toast.makeText(context,Double.toString(barposition[i].getMinValue())+"-" + 
							Double.toString(barposition[i].getMaxValue()), Toast.LENGTH_SHORT);
			    	toast.setGravity(Gravity.TOP|Gravity.LEFT,(int)
			    			(boxbounds.left+ xpoint),
			    			(int)(boxbounds.top+ ypoint));
			    	toast.show();
			    	;
			    	
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
		
		public void setSeries(List<RangeItem> series)
		{
			this.series=series;
		}
		
		//********************************************//

}
