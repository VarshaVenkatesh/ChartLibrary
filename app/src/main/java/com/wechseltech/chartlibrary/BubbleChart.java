

package com.wechseltech.chartlibrary;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
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
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;

public class BubbleChart extends ChartView{

	
	 ReturnIntervalsTicks ticks;
	List<Integer> hexponent,vexponent;
	int hmin =0,vmin=0;
	private EdgeEffectCompat mEdgeEffectTop;
	private EdgeEffectCompat mEdgeEffectBottom;
	private EdgeEffectCompat mEdgeEffectLeft;
	private EdgeEffectCompat mEdgeEffectRight;
	
	Context context;
	
	double maxZvalue;
	float xpoint,ypoint;
	BarPosition[] barposition;
	
	String[] seriesname;
	private boolean mEdgeEffectTopActive;
	private boolean mEdgeEffectBottomActive;
	private boolean mEdgeEffectLeftActive;
	private boolean mEdgeEffectRightActive;
	
	private Point ScrollableSurface = new Point();
	
	int numseries;
	
	
	int [] setcolors;
	Integer [] cols ;
	Map<String,Integer>seriescolor;
	
	AxisStops hstops,vstops;
	List<MultipleSeriesItem> series;
	
	int bubbleposcolor,bubblenegcolor,bubblezerocolor;
	Paint tPaint,aPaint,gPaint,rPaint,vPaint,
	pPaint,nPaint,zPaint;
	
	DrawGridLines lines;
	
	//************************************//
	int minLabelWidth,minLabelHeight,mLabelWidth,
	mLabelHeight,nLabelWidth,nLabelHeight,subLabelHeight;
	int mAxisLabelHeight,mAxisLabelWidth;
	float axisLabelSpace;
	int nVticks;int nHticks;
	
	private ScaleGestureDetector mScaleGestureDetector=null;
	private GestureDetectorCompat mGestureDetector=null;
	
	OptimizeTicks tasks;
	RectF boxbounds,coordbounds;
	
	Boolean invalidate =false;
	
	Boolean hFlipped,vFlipped;
	
	Paint bPaint,legendPaint,fPaint,lPaint,labelPaint;
	
	String[] uniqueX,uniqueY;
	double hInterval,vInterval;

	private float AXIS_H_MIN;
	
	private float AXIS_H_MAX;
	private float AXIS_V_MIN;
	
	private float AXIS_V_MAX;
	
	
	RectF legendBox;
	
	private float[] AxisVGridLines;
	private float[] AxisHGridLines;
	
	String xLabelName,yLabelName;
	
	Paint mLabelPaint;
	int drawBoxSize;
	
	int mVLabelWidth;
	
	RectF Viewport;
	
	public BubbleChart(Context context) {
		super(context);
		
		System.out.println("INIT C");
		series = new ArrayList<MultipleSeriesItem>();
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
	
	public BubbleChart(Context context,AttributeSet attrs) {
		super(context,attrs);
		System.out.println("INIT At");
		
		series = new ArrayList<MultipleSeriesItem>();
		hstops = new AxisStops();
		vstops = new AxisStops();
		
		mEdgeEffectTop = new EdgeEffectCompat(context);
		mEdgeEffectLeft = new EdgeEffectCompat(context);
		mEdgeEffectRight = new EdgeEffectCompat(context);
		mEdgeEffectBottom = new EdgeEffectCompat(context);
		
		mScaleGestureDetector = new ScaleGestureDetector(context,mScaleGestureListener);
		mGestureDetector = new GestureDetectorCompat(context,mGestureListener);
		
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.Charts);
		
		try
		{
			assignSeriesColors= a.getBoolean(R.styleable.Charts_assignSeriesColors, true);
			mAxisLabelSize = a.getDimension(R.styleable.Charts_axisLabelSize, 12);
			mLabelSeparation = a.getDimension(R.styleable.Charts_labelSeparation, 8);
			mTextSize = a.getDimension(R.styleable.Charts_textSize, 12);
			textcolor = a.getColor(R.styleable.Charts_textColor, Color.BLACK);
			gridcolor = a.getColor(R.styleable.Charts_gridColor, Color.BLACK);
			bubbleposcolor = a.getColor(R.styleable.Charts_bubblePositiveColor, Color.GREEN);
			bubblenegcolor= a.getColor(R.styleable.Charts_bubbleNegativeColor, Color.RED);
			bubblezerocolor= a.getColor(R.styleable.Charts_bubbleZeroColor, Color.GRAY);
			showgrid= a.getBoolean(R.styleable.Charts_showGrid, true);
			drawLabels = a.getBoolean(R.styleable.Charts_drawLabels, false);
			isHorizontal =a.getBoolean(R.styleable.Charts_isHorizontal, false);
			legendSize = a.getDimension(R.styleable.Charts_legendBoxSize,30);
			XisNum = a.getBoolean(R.styleable.Charts_XisNum, false);
			YisNum = a.getBoolean(R.styleable.Charts_YisNum, false);
			YisCategory = a.getBoolean(R.styleable.Charts_YisCategory,false);
			XisCategory = a.getBoolean(R.styleable.Charts_XisCategory,false);
			rectcolor =a.getColor(R.styleable.Charts_boundingboxColor, Color.BLACK);
			
			
			
		}
		
		finally
		{
			
			a.recycle();
		}
		
		
	}
	
	
public BubbleChart(Context context,AttributeSet attrs,int defStyle) {
		
		
		super(context,attrs,defStyle);
		System.out.println("INIT DF");
		series = new ArrayList<MultipleSeriesItem>();
		hstops = new AxisStops();
		vstops = new AxisStops();
		
		mEdgeEffectTop = new EdgeEffectCompat(context);
		mEdgeEffectLeft = new EdgeEffectCompat(context);
		mEdgeEffectRight = new EdgeEffectCompat(context);
		mEdgeEffectBottom = new EdgeEffectCompat(context);
		
		mScaleGestureDetector = new ScaleGestureDetector(context,mScaleGestureListener);
		mGestureDetector = new GestureDetectorCompat(context,mGestureListener);
		
		TypedArray a = context.getTheme().
				obtainStyledAttributes(attrs,R.styleable.Charts,defStyle,defStyle);
		
		try
		{
			
			mAxisLabelSize = a.getDimension(R.styleable.Charts_axisLabelSize, 12);
			mLabelSeparation = a.getDimension(R.styleable.Charts_labelSeparation, 8);
			mTextSize = a.getDimension(R.styleable.Charts_textSize, 12);
			textcolor = a.getColor(R.styleable.Charts_textColor, Color.BLACK);
			gridcolor = a.getColor(R.styleable.Charts_gridColor, Color.BLACK);
			bubbleposcolor = a.getColor(R.styleable.Charts_bubblePositiveColor, Color.GREEN);
			bubblenegcolor= a.getColor(R.styleable.Charts_bubbleNegativeColor, Color.RED);
			bubblezerocolor= a.getColor(R.styleable.Charts_bubbleZeroColor, Color.GRAY);
			showgrid= a.getBoolean(R.styleable.Charts_showGrid, false);
			drawLabels = a.getBoolean(R.styleable.Charts_drawLabels, false);
			isHorizontal =a.getBoolean(R.styleable.Charts_isHorizontal, false);
			assignSeriesColors= a.getBoolean(R.styleable.Charts_assignSeriesColors, true);
			XisNum = a.getBoolean(R.styleable.Charts_XisNum, false);
			YisNum = a.getBoolean(R.styleable.Charts_YisNum, false);
			YisCategory = a.getBoolean(R.styleable.Charts_YisCategory, false);
			XisCategory = a.getBoolean(R.styleable.Charts_XisCategory, false);
			rectcolor =a.getColor(R.styleable.Charts_boundingboxColor, Color.BLACK);
			legendSize = a.getDimension(R.styleable.Charts_legendBoxSize,30);
			
		}
		
		finally
		{
			
			a.recycle();
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
			System.out.println("VALUE");
			float pos = coordbounds.bottom - barspace*
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

	
	
	
		private void drawCircle(Canvas canvas, float centreh,float centrev,double zval,String label,int color)
		{
			
			
			 	double[] maxvalZ = new double[series.size()];
		        for(int i=0;i<series.size();i++)
				{
		        	
					maxvalZ[i]=series.get(i).getZVal();
					
				}
			    
		       Arrays.sort(maxvalZ);
			    
		       System.out.println(series.size());
			    
			   if(series.size()>0)
				   maxZvalue=maxvalZ[series.size()-1];
			    
			 
			   System.out.println("MAXZVAL"+ maxZvalue);
			   System.out.println("ZVAL"+ zval);
			    
			float maxBubbleRadius;
			if(drawLabels)
				maxBubbleRadius = (float)Math.min(mVLabelWidth/2,1.5*mLabelHeight);
			else
				maxBubbleRadius = (float)Math.min(mVLabelWidth/2,2*mLabelHeight);
			
			
			System.out.println("NLABELWIDTH" + ticks.nLabelHeight);
				
			  float maxZ = (float)(Math.sqrt(Math.abs(maxZvalue)));
			  if(maxZ!=0)
			  {
				
				  System.out.println("MAXZ"+ maxZ);
				  
				// bPaint = pPaint;
				 bPaint.setColor(color);
				 //bPaint.setColor(Color.GREEN);
					  
				  float radii =(float) (maxBubbleRadius*
							Math.sqrt(Math.abs(zval)))/maxZ;
				  
				  System.out.println("RADII"+radii);
				  canvas.drawCircle(centreh,centrev,
						  radii, bPaint);	
				  if(drawLabels)
				  {
					  canvas.drawText(label,0,label.length(),
								centreh,centrev-radii, labelPaint);
				  }
				
				/*if(drawLabels)
				{
					 float minLength = tPaint.measureText("0");
					 
					 float actLength= label.length() * minLength;
					 
					 int numchar=0;
					
					 
					 if(nLabelWidth - actLength<0)
					 {
						numchar =(int) Math.ceil((mLabelWidth-actLength)/minLength);
					 }
					canvas.drawText(label,0,label.length()-numchar,
							centreh,centrev+ radii, tPaint);
				}*/
			  }
			
		}
	 
	 
		
		private void init()
		{
			
			lines = new DrawGridLines();
			
			
			
			 tPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     tPaint.setColor(textcolor);
		     tPaint.setTextSize(mTextSize);
		     tPaint.setTextAlign(Paint.Align.CENTER);
			
		     aPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     aPaint.setColor(textcolor);
		     aPaint.setTextSize(mAxisLabelSize);
		     aPaint.setTextAlign(Paint.Align.CENTER);
		     
		     rPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     rPaint.setColor(rectcolor);
		       
		     rPaint.setStyle(Paint.Style.STROKE);
		     
		     gPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     gPaint.setColor(gridcolor);
		     
		     bPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     bPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		     
		     lPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     lPaint.setStyle(Paint.Style.FILL);
		     
		     labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     labelPaint.setColor(textcolor);
		     labelPaint.setTextSize(mLabelSize);
		     labelPaint.setTextAlign(Paint.Align.CENTER);
		     
		     
		     legendPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     legendPaint.setColor(textcolor);
		     legendPaint.setTextSize(mTextSize);
		     legendPaint.setTextAlign(Paint.Align.LEFT);
		     
		     
		     	vPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		        vPaint.setColor(textcolor);
		        vPaint.setTextSize(mTextSize);
		        vPaint.setTextAlign(Paint.Align.RIGHT);
		        
		    	fPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		        fPaint.setColor(textcolor);
		        fPaint.setTextSize(mTextSize);
		        fPaint.setTextAlign(Paint.Align.LEFT);
		        
	       
	        
		    //drawBoxSize = mLabelHeight;
		     
	        mLabelHeight =(int) Math.abs(tPaint.getFontMetrics().top);
	        
	        drawBoxSize = mLabelHeight;
	        
	        
	        minLabelHeight = (int) Math.abs(tPaint.getFontMetrics().bottom);
	    
	        minLabelWidth = (int) Math.abs(tPaint.measureText("000"));
	        mLabelWidth = (int) Math.abs(tPaint.measureText("000000"));
	        mVLabelWidth = (int) Math.abs(tPaint.measureText("00000"));
	        
	        mAxisLabelHeight = (int) Math.abs(aPaint.getFontMetrics().top);
	        
	       
	        
	        
		}
		
	
		
		
		
		 @Override 
		 public void onDraw(Canvas canvas)
		 
		 {
			super.onDraw(canvas);
			
			System.out.println("Calling onDraw");

			int clipRestoreCount = canvas.save();
			canvas.clipRect(coordbounds);
			
			drawAxes(canvas);
			
			
			
			
			drawData(canvas);
			
			drawEdgeEffectsUnclipped(canvas);
			
			canvas.restoreToCount(clipRestoreCount);
			
			drawText(canvas);
			
			if(!invalidate)
				drawLegend(canvas);
				else
				drawInvalidateLegend(canvas);	
				
			
			canvas.drawRect(coordbounds,rPaint);
			
			//canvas.drawRect(legendBox, rPaint);
			 
			
			 
		 }
		 
			private void redrawLegendBox(float coww,int drawBoxSize,int mLabelWidth) {
				

				
				
				float coll = (float) boxbounds.height()- ticks.subLabelHeight-
						(2*mAxisLabelHeight+3*mLabelSeparation)-(-aPaint.ascent())
						-(legendSize+drawBoxSize);

				
				coordbounds = new RectF(0.0f,0.0f,
						coww,coll);
				
				float xoffset = ticks.nvLabelWidth+(2*mAxisLabelHeight)-(-aPaint.ascent())+3*mLabelSeparation;
				
		         coordbounds.offsetTo(getPaddingLeft()+ xoffset, getPaddingTop()+mLabelSeparation);
				
				legendBox = new RectF(0.0f,0.0f,coww,legendSize + drawBoxSize);
				
				legendSize  = legendSize+ drawBoxSize;
				
				legendBox.offset(getPaddingLeft()+xoffset,coordbounds.bottom+subLabelHeight+(mAxisLabelHeight-aPaint.ascent()));
				
				invalidate =true;
				
				invalidate();
			}
	 
		 
		 public void drawLegend(Canvas canvas) {
				
				
				//int lLabelWidth = (int) Math.abs(legendPaint.measureText("00000000"));
				int row=1;
				int a=0;
				
				System.out.println("LG" + legendBox);
				
				for (int i=0; i<numseries; i++)
					
				{
					
					int lLabelWidth=0;
					if(i!=0)
					lLabelWidth = (int) Math.abs(legendPaint.measureText(seriesname[i-1]+"0"));
					
					
					float left = legendBox.left+ ((i+1)*drawSpace)+ (i*lLabelWidth) +(i*drawBoxSize);
					float right = left+drawBoxSize;
					
					
				
					//lPaint.setColor(seriescolor.get(i+1));
					
					System.out.println("LEGENDPOINT" + seriescolor.get(seriesname[i]) );
					
					
						lPaint.setColor(seriescolor.get(seriesname[i]));
						legendPaint.setColor(seriescolor.get(seriesname[i]));
				
					
						
			
					if(((right + lLabelWidth)<legendBox.right))
					{
						
						System.out.println("LG TOP" + (legendBox.top+drawSpace));
						
						System.out.println("LG BOTTOM" + (legendBox.top+drawSpace+drawBoxSize));
						
						System.out.println("LG LEFT" + left);
						
						System.out.println("LG RIGHT" + right);
						
						canvas.drawRect(left,legendBox.top+drawSpace,right,
								legendBox.top+drawSpace+drawBoxSize,lPaint );
					
						canvas.drawText(seriesname[i],right+drawSpace,legendBox.top+drawSpace+drawBoxSize,legendPaint);
					}
					else
					{
						
						System.out.println("LG BOTTOM");
						row =row+1;
						float top = legendBox.top+((row)*drawSpace)+(row-1)*drawBoxSize;
						float bottom = top+drawBoxSize;
						
						
						float rleft = legendBox.left+ ((a+1)*drawSpace)+ (a*lLabelWidth) +(a*drawBoxSize);
						float rright = rleft+(drawBoxSize);
						
						System.out.println("A" + a);
						
						if(bottom>legendBox.bottom)
						{
							redrawLegendBox(legendBox.width(),drawBoxSize,mLabelWidth);
						}
						else
						{
							
							canvas.drawRect(rleft,top,rright,
									bottom, lPaint);
						
							System.out.println(seriesname[i]);
							canvas.drawText(seriesname[i],rright+drawSpace,
									bottom, legendPaint);
							
						}
						
						a++;
					}
					
				}
				
				
				
			}

			private void drawInvalidateLegend(Canvas canvas) {
				System.out.println("LEGEND" + legendBox);
				 
				
			
				//int lLabelWidth = (int) Math.abs(legendPaint.measureText("000000000"));
				int row=1;
				int a=0;
				
				
				
				
				for (int i=0; i<seriesname.length; i++)
					
					
				{
					int lLabelWidth=0;
					if(i!=0)
					lLabelWidth = (int) Math.abs(legendPaint.measureText(seriesname[i-1]+"0"));
					
					float checkleft = legendBox.left+ ((a+1)*drawSpace)+ (a*lLabelWidth) +(a*drawBoxSize);
					float checkright = checkleft+drawBoxSize;
					
					
				
					//lPaint.setColor(seriescolor.get(i+1));
					

					
					lPaint.setColor(seriescolor.get(seriesname[i]));
					legendPaint.setColor(seriescolor.get(seriesname[i]));
				
				
			
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
									bottom, legendPaint);
						
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
									bottom, legendPaint);
						
						
						a++;
					}
					
				}
				
				
			}
		 

			
			private void drawAxes(Canvas canvas)
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
	 
	 /***********************************************************/
		 
		 
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
				    
				    System.out.println("NUMCHARS" + numchars + "NUMSTRLENGTH" + numstring.length());
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
				  
		 
		 private void CalculateExp(double [] stops , int i,Paint tPaint,int nLabelWidth,double interval,Boolean isH) {
				
				
				
				
				
				String[] outstring = formatfloat((float)stops[i],
						interval,tPaint,nLabelWidth);
				
				System.out.println("EXPONENT"+outstring[1]+"STOPS"+ stops[i]);
				
			if(outstring[1]!=null)
			{
				if(isH)
				hexponent.add(Integer.parseInt(outstring[1]));
				else
				{
				vexponent.add(Integer.parseInt(outstring[1]));
				System.out.println("VEXPONENT"+outstring[1]);
				}
				
				
			}
				
			}
		 
		 
		 private void drawText(Canvas canvas) {
				
			 
				DrawTextValues values = new DrawTextValues(tPaint,aPaint,
						mAxisLabelHeight, mAxisLabelWidth,coordbounds
						,ticks.subLabelHeight,ticks.nLabelWidth,
						mLabelSeparation,vPaint,fPaint,ticks.nhLabelWidth,ticks.nvLabelWidth);
			 
				hexponent = new ArrayList<Integer>();
				vexponent = new ArrayList<Integer>();
			 
				if(!isHorizontal)
				{
					System.out.println("SUBLABELHT"+ ticks.subLabelHeight);
					
					
					for(int j=0;j<hstops.axisstops.length;j++)
					{
						if(XisNum)
							
						{		
							System.out.println("TICKS LABEL WIDTH"+ ticks.nvLabelWidth);
							 CalculateExp(hstops.axisstops,j,tPaint,ticks.nvLabelWidth,hInterval,true);
							
							
						}
						
					}
					
					for(int j=0;j<vstops.axisstops.length;j++)
					{
						if(YisNum)
							
						{
							System.out.println("ticks.nvLabelWidth" + ticks.nvLabelWidth);
							CalculateExp(vstops.axisstops,j,tPaint,ticks.nvLabelWidth,vInterval,false);
							
							
						}
						
					}
					
					Integer [] hexps = hexponent.toArray(new Integer[hexponent.size()]);
					Arrays.sort(hexps);
					if(hexps.length>0)
					hmin = hexps[0];
					
					System.out.println("HMIN"+hmin);
					
					Integer [] vexps = vexponent.toArray(new Integer[vexponent.size()]);
					Arrays.sort(vexps);
					if(vexps.length>0)
					vmin = vexps[0];
					
					System.out.println("VMIN"+vmin);
					
					
					for(int i=1; i<hstops.axisstops.length;i++)
					{
					
						if(XisCategory)
						{
							
							
							values.DrawTextCategory(uniqueX[i-1],canvas,coordbounds.bottom,i,hstops,
							coordbounds.left, coordbounds.width(),true,ticks.isHflipped); 
							
							
						}
				
					
						else if(XisNum)
					
						{
							
							
//							DrawTextNum(canvas,getHLabelName(),boxbounds.bottom,i,hstops 
//									
//								,coordbounds.left,coordbounds.width(),hInterval,true);
							if(i%2==0)
							values.DrawTextNumHExponent(canvas,getXLabelName(),coordbounds.bottom,i,hstops 
						
									,coordbounds.left,coordbounds.width(),hInterval,true,true,hmin);
							
						}
						
					}
					
					
					canvas.drawText(getXLabelName(),0,getXLabelName().length(),
							coordbounds.left+coordbounds.width()/2,boxbounds.bottom-(mAxisLabelHeight/2), aPaint);
					
					
					for(int j=1;j<vstops.axisstops.length;j++)
					{
						
						if(YisCategory)
							
						{
							
							
							values.DrawTextCategory(uniqueY[j-1],canvas,coordbounds.bottom,j,
									vstops,boxbounds.left, coordbounds.height(),false,ticks.isVflipped);
							
						}
				
						else if(YisNum)
							
							if(j%2==0)
							values.DrawTextNumVExponent(canvas,getYLabelName(),coordbounds.bottom,j,vstops 
								,boxbounds.left,coordbounds.height(),vInterval,false,ticks.isVflipped,vmin);
							
						
					}
					
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
					
					
					
					canvas.save();
					canvas.rotate((float)(90));
					System.out.println("CANVAS ");
					canvas.drawText(getYLabelName(),0,getYLabelName().length(),
						coordbounds.bottom-coordbounds.height()/2,boxbounds.left-mLabelSeparation, aPaint);
					canvas.restore();
					
			
				}
				else
				{
					
					
					for(int j=0;j<vstops.axisstops.length;j++)
					{
						if(XisNum)
							
						{
							CalculateExp(vstops.axisstops,j,tPaint,ticks.nvLabelWidth,vInterval,false);
							
							
						}
						
					}
					
					for(int j=0;j<hstops.axisstops.length;j++)
					{
						if(YisNum)
							
						{		
								 CalculateExp(hstops.axisstops,j,tPaint,ticks.nvLabelWidth,hInterval,true);
							
							
						}
						
					}
					
					Integer [] hexps = hexponent.toArray(new Integer[hexponent.size()]);
					Arrays.sort(hexps);
					if(hexps.length>0)
					hmin = hexps[0];
					
					System.out.println("HMIN"+hmin);
					
					Integer [] vexps = vexponent.toArray(new Integer[vexponent.size()]);
					Arrays.sort(vexps);
					if(vexps.length>0)
					vmin = vexps[0];
					
					System.out.println("VMIN"+vmin);
					
					
					System.out.println("VISFLIPPED" + ticks.isVflipped);
					
					
					for(int i=1; i< vstops.axisstops.length;i++)
					{
					
						if(XisCategory)
						{
							
							
							values.DrawTextCategory(uniqueX[i-1],canvas,coordbounds.bottom,
									i,vstops,
									boxbounds.left, coordbounds.height(),false,true); 
						}
						
						else if(XisNum)
						{
							if(i%2==0)
							values.DrawTextNumVExponent(canvas,getXLabelName(),coordbounds.bottom,i,vstops 
									,boxbounds.left,coordbounds.height(),vInterval,false,ticks.isVflipped,vmin);
						}
						
					}
					
					canvas.save();
					canvas.rotate((float)(90));
					System.out.println("CANVAS ");
					canvas.drawText(getXLabelName(),0,getXLabelName().length(),
						coordbounds.bottom-coordbounds.height()/2,boxbounds.left-mLabelSeparation, aPaint);
					canvas.restore();
					
					for(int i=1; i< hstops.axisstops.length;i++)
					{
					
						if(YisCategory)
					
							
						
							values.DrawTextCategory(uniqueY[i-1],canvas,coordbounds.bottom,i,hstops,
							coordbounds.left, coordbounds.width(),true,ticks.isHflipped); 
				
					
						else if(YisNum)
					
							if(i%2==0)
							values.DrawTextNumHExponent(canvas,getYLabelName(),coordbounds.bottom,i,hstops 
								,coordbounds.left,coordbounds.width(),hInterval,true,true,hmin);
						
					}
					
					if(XisNum)
					{
						
						if(vmin!=0)
							values.drawExponentVertical(canvas,coordbounds.bottom,vmin,boxbounds.left,coordbounds.height());
					}
					
					if(YisNum)
					{
						
						if(hmin!=0)
							values.drawExponentHorizontal(canvas,coordbounds.bottom,hmin,coordbounds.left,coordbounds.width()); 
					}
				
					
					canvas.drawText(getYLabelName(),0,getYLabelName().length(),
							coordbounds.left+coordbounds.width()/2,
							boxbounds.bottom-(mAxisLabelHeight/2), aPaint);
				}
				
			}
	 
	 private  void drawData(Canvas canvas)
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
								if(series.get(m).getXLabel().equals(uniqueX[n]))
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
								
								System.out.println("SERIES"+ series.get(m).getYLabel());
								if(series.get(m).getYLabel().equals(uniqueY[r]))
								{
								// centrev = getCentreV(r+1);
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
							
							
							
							if(drawLabels)
							{
								drawCircle(canvas,centreh,centrev,series.get(m).getZVal(),
										series.get(m).getLabel(),series.get(m).getColor());
								barposition[m].setLabel(series.get(m).getLabel());
								barposition[m].setZValue(series.get(m).getZVal());
							}
							else
							{
								System.out.println("ZVALBEFORE"+ series.get(m).getZVal());
								drawCircle(canvas,centreh,centrev,series.get(m).getZVal(),null,series.get(m).getColor());
								barposition[m].setZValue(series.get(m).getZVal());
							}
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
							if(series.get(m).getXLabel().equals(uniqueX[n]))
							 //centrev = getCentreV(n+1);
							{
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
							if(series.get(m).getYLabel().equals(uniqueY[r]))
							 //centreh = getCentreH(r+1);
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
						
					
						if(drawLabels)
						{
							drawCircle(canvas,centreh,centrev,series.get(m).getZVal(),
									series.get(m).getLabel(),series.get(m).getColor());
							barposition[m].setLabel(series.get(m).getLabel());
							barposition[m].setZValue(series.get(m).getZVal());
						}
						else
						{
							drawCircle(canvas,centreh,centrev,series.get(m).getZVal(),null,series.get(m).getColor());
							barposition[m].setZValue(series.get(m).getZVal());
						}
				}
			}
			
		}
	
	 
	
	 private void assignColors()
	 {
			getSeriesNames();
			if(assignSeriesColors)
			{
				calculateColors();
				
			}
			else 
			{
				calculateColorsFromSeries();
				
			}
	 }
	
	public void setSeries(List<MultipleSeriesItem> series,int numseries)
	{
		this.series=series;
		this.numseries=numseries;
		
	
		
		
		
		
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
	
	
	private double[] getYArray()
	{
		double [] maxvalY = new double[series.size()];
		 
		for(int i=0;i<series.size();i++)
		{
				maxvalY[i]=series.get(i).getYVal();
				
		}
		 Arrays.sort(maxvalY);
		 
		 
		return maxvalY;
	}
	
	
	private double[] getXArray()
	{
		double [] maxvalX = new double[series.size()];
		 
		for(int i=0;i<series.size();i++)
		{
				maxvalX[i]=series.get(i).getXVal();
				
		}
		 Arrays.sort(maxvalX);
		return maxvalX;
	}
	
	
	private void getXCategory()
	{
		 List<String> uniX= new ArrayList<String>();
		 for(int i=0; i<series.size();i++)
		 {
				
			uniX.add(series.get(i).getXLabel());
		 }		
		Set<String> uxVal= new HashSet<String>(uniX);
		uniqueX = uxVal.toArray(new String[uxVal.size()]);
		
		System.out.println(uniqueX.length);
		
		
	}
	

	 
	 
	 /***********************************************************/
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		
		float xpad = (float)(getPaddingLeft()+ getPaddingRight());
		float ypad = (float)(getPaddingTop() +  getPaddingBottom());
		
		float coww =0.0f,cohh=0.0f,coll =0.0f;
		
		init();
		
		System.out.println("APAINT"+aPaint.ascent());
		
		coww = (float) w-xpad
				-mLabelWidth-(2*mAxisLabelHeight+aPaint.ascent())
				-4*mLabelSeparation;
//		cohh = (float) h-ypad-mLabelHeight-(2*mAxisLabelHeight+aPaint.ascent()+2*mLabelSeparation)
//				-(legendSize+mLabelSeparation);
		
		cohh = (float) h-ypad-mLabelHeight-
				(2*mAxisLabelHeight+aPaint.ascent()) - 3 * mLabelSeparation
				-(legendSize);

		float bxww = (float) w-xpad;
		float bxhh = (float) h-ypad;
		
		mAxisLabelWidth = (int) (coww/2);
		
		boxbounds = new RectF(getPaddingLeft(),getPaddingTop(),
				getPaddingLeft()+bxww,getPaddingTop()+bxhh);

		
		
		
		coordbounds = new RectF(0.0f,0.0f,
				coww,cohh);
		
		float xoffset = mLabelWidth+ (2*mAxisLabelHeight+aPaint.ascent())+3*mLabelSeparation;
		
		
		
		axisLabelSpace = (2*mAxisLabelHeight-aPaint.ascent())+3*mLabelSeparation;
		coordbounds.offset(getPaddingLeft()+ xoffset,getPaddingTop()+mLabelSeparation);
		
		
		legendBox = new RectF(0.0f,0.0f,coww,legendSize);
		
		//legendBox.offset(getPaddingLeft()+xoffset,coordbounds.bottom+2*mLabelSeparation+mLabelHeight);
		legendBox.offset(getPaddingLeft()+xoffset,coordbounds.bottom+mLabelSeparation+mLabelHeight);
		
		
		tasks = new OptimizeTicks(mLabelWidth,mLabelHeight,coordbounds.width(),
				coordbounds.height(),minLabelWidth,
				minLabelHeight,boxbounds.height(),boxbounds.width(),axisLabelSpace,legendSize);
		
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
					boxbounds.height(),boxbounds.width(),
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
					uniqueY, bxh,bxw, axisLabelSpace,tasks);
			
			 
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
					vstops, uniqueX, bxh, bxw, axisLabelSpace,tasks);
			
			 
		 }
		 
		 if(XisCategory & YisCategory)
		 {
			 getXCategory();
			 getYCategory();
			 
			 ticks = handles.getXCategoryYCategory(isHorizontal, hstops, vstops, uniqueY, 
					 uniqueX, bxh, bxw, axisLabelSpace,tasks);
			 
			 
			
		 }
		 
		 coordbounds = ticks.coordbounds;
		 
		 System.out.println("COORDBDS" + ticks.coordbounds);
		 legendBox = ticks.legendBox;
		 Viewport=ticks.Viewport;
		 AXIS_H_MAX = ticks.AXIS_H_MAX;
		 AXIS_H_MIN = ticks.AXIS_H_MIN;
		 AXIS_V_MAX = ticks.AXIS_V_MAX;
		 AXIS_V_MIN = ticks.AXIS_V_MIN;
		 
		 hstops = ticks.hstops;
		 hInterval = ticks.hInterval;
		 vstops = ticks.vstops;
		 vInterval = ticks.vInterval;
		
		 
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
	
	private void computeWidthAndHeight(int lwidth,int lheight,float bxh,float bxw)
	{
		
		
		
		//float coww = (float) bxw-lwidth-(2*mAxisLabelHeight+aPaint.ascent()+2*mLabelSeparation);
		
		
		float coww = (float) bxw-lwidth-(2*mAxisLabelHeight-aPaint.ascent())-mLabelSeparation;
		
		float cohh = (float) bxh-lheight-
				(3*mAxisLabelHeight+aPaint.ascent()+5*mLabelSeparation);
		
		coordbounds = new RectF(0.0f,0.0f,
				coww,cohh);

		float xoffset = lwidth+(2*mAxisLabelHeight)-(aPaint.ascent());
		
		
		coordbounds.offset(getPaddingLeft()+xoffset,getPaddingTop()+mLabelSeparation);
		
		
	}
	
	

	
private void getSeriesNames() {
		
		
		List<String> names = new ArrayList<String>();
		for(int i=0;i<series.size();i++)
		{
			names.add(series.get(i).getSeriesname());
		}
		
			
		 seriesname= new HashSet<String>(names).toArray(new String[numseries]);
		
	}


	private void calculateColors() {
	
	ColorCode colors = new ColorCode();
	 setcolors =new int[numseries];
	 seriescolor = new HashMap<String,Integer>();
	 
		if(useSeriesPrimary)
			setcolors = colors.setPrimaryColor(numseries,60,0.8f,0.9f);
		else
			setcolors = colors.setHSVAccent(accentprimarycolor,numseries,0.5f,1.0f, 20);
	
	
	
		for(int i=1; i<=numseries;i++)
		{
			for(int j=0; j<series.size();j++)
			{
				if(series.get(j).getSeries()==i)
				{
					System.out.println("SERIESCOLOR"+ setcolors[i-1]);
					series.get(j).setColor(setcolors[i-1]);
					seriescolor.put(series.get(j).getSeriesname(),setcolors[i-1]);
				}
					
			}
			
			
		}
	
	}

	private void calculateColorsFromSeries() {
		
		seriescolor = new HashMap<String,Integer>();
		List<Integer> colors = new ArrayList<Integer>();
		for(int i=0;i<series.size();i++)
		{
			colors.add(series.get(i).getColor());
		}
		
		 cols= new HashSet<Integer>(colors).toArray(new Integer[numseries]);
		
		 
		
		 for(int i=0;i<series.size();i++)
		 {
			
			 
			 seriescolor.put(series.get(i).getSeriesName(),series.get(i).getColor());
			
			 
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
	
	
/**********************************************************/
	
	private final ScaleGestureDetector.OnScaleGestureListener mScaleGestureListener
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
		  ViewCompat.postInvalidateOnAnimation(BubbleChart.this);
		 
		 lastSpanX = spanX;
		 lastSpanY = spanY;
			return true;
		 }
	 };
	 
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
	 /****************************************************************/
	
	
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
		
	  private final GestureDetector.SimpleOnGestureListener mGestureListener =
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
	  
	  private void setViewportBottomLeft(float x,float y) {
			
		  
		  float curWidth = Viewport.width();
		 
		  float curHeight = Viewport.height();
		  x= Math.max(AXIS_H_MIN,Math.min(x, AXIS_H_MAX-curWidth));
	      y = Math.max(AXIS_V_MIN +curHeight,Math.min(y, AXIS_V_MAX));
	      
	      Viewport.set(x,y-curHeight,x + curWidth,y);
	      ViewCompat.postInvalidateOnAnimation(this);
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
	  
	  /***********************************************************************************/
	  	
	  	private void drawEdgeEffectsUnclipped(Canvas canvas)
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
						toast = Toast.makeText(context,barposition[i].getLabel()+"-"+Double.toString(barposition[i].getXValue())+" , "+Double.toString(barposition[i].getYValue())+
								" , "+Double.toString(barposition[i].getZValue()), Toast.LENGTH_SHORT);
						}
						else if(XisNum& ! YisNum)
						{
							toast = Toast.makeText(context,
									barposition[i].getLabel()+"-"+
							Double.toString(barposition[i].getXValue())+
							" , "+Double.toString(barposition[i].getZValue()) , Toast.LENGTH_SHORT);
						}
						else if(YisNum& ! XisNum)
						{
							toast = Toast.makeText(context,barposition[i].getLabel()+"-"+
						Double.toString(barposition[i].getYValue())+ 
						" , "+Double.toString(barposition[i].getZValue()) , Toast.LENGTH_SHORT);
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
					toast = Toast.makeText(context,Double.toString(barposition[i].getXValue())+
							" , "+Double.toString(barposition[i].getYValue())+
							" , "+Double.toString(barposition[i].getZValue()), Toast.LENGTH_SHORT);
					}
					else if(XisNum& ! YisNum)
					{
						toast = Toast.makeText(context,Double.toString(barposition[i].getXValue())+
								" , "+Double.toString(barposition[i].getZValue()), Toast.LENGTH_SHORT);
					}
					else if(YisNum& ! XisNum)
					{
						toast = Toast.makeText(context,Double.toString(barposition[i].getYValue()) 
								+ " , "+Double.toString(barposition[i].getZValue()), Toast.LENGTH_SHORT);
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
