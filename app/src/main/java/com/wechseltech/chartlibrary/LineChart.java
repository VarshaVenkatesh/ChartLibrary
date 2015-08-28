

package com.wechseltech.chartlibrary;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.LauncherActivity.ListItem;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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



public class LineChart extends ChartView{

	
	//************************************//
	
	
	
	List<LineItem> series;
	Paint gPaint,aPaint,fPaint,
	tPaint,bPaint,lPaint,rPaint,ltPaint,vPaint,cPaint
	,labelPaint;
	
	float xpoint,ypoint;
	Integer [] cols ;
	//************************************//
	List<Integer> hexponent,vexponent;
	int hmin =0,vmin=0;
	float mVLabelWidth;
	DrawGridLines lines;
	private float AXIS_H_MIN;
	int nVticks;int nHticks;
	private float AXIS_H_MAX;
	private float AXIS_V_MIN;
	private Point ScrollableSurface = new Point();
	private float AXIS_V_MAX;
	
	Map<String,Integer> seriescolor= new HashMap<String,Integer>();
	
	double hInterval,vInterval;
	 Map<Integer,LineItem[]> seritems;
	 Map<Integer,lineposition[]> linepositions;
	//************************************//
	
	private ScaleGestureDetector mScaleGestureDetector=null;
	private GestureDetectorCompat mGestureDetector=null;
	
	RectF boxbounds,coordbounds,legendBox;
	AxisStops hstops,vstops;
	
	Path cubicPath = new Path();
	 RectF Viewport;
	 
	 
	 BarPosition[] barposition;
	 
	 private EdgeEffectCompat mEdgeEffectTop;
	 private EdgeEffectCompat mEdgeEffectBottom;
	 private EdgeEffectCompat mEdgeEffectLeft;
	 private EdgeEffectCompat mEdgeEffectRight;
	 
	 private boolean mEdgeEffectTopActive;
	 private boolean mEdgeEffectBottomActive;
	 private boolean mEdgeEffectLeftActive;
	 private boolean mEdgeEffectRightActive;
	 Boolean invalidate=false;
	//************************************//
	 
	 int mLabelWidth,mAxisLabelHeight,mLabelHeight
	 ,mAxisLabelWidth;
	 
	 
	 
	
	 float axisLabelSpace;
	 
	 int minLabelHeight,minLabelWidth;
	 
	 Context context;
	
		
		
	 Boolean isHFlipped;
	 OptimizeTicks tasks;
	 ReturnIntervalsTicks ticks;
	 String[] uniqueY,uniqueX;
	 String xLabelName,yLabelName;
	 String[] seriesname;
	 int numseries;
	 RectF enclosingBox = new RectF();
	 int drawBoxSize;
	 
	
	 //***********************************************//
	 
	 float [] AxisVGridLines,AxisHGridLines;
	 
	 
	 public LineChart(Context context) {
			super(context);
			
			System.out.println("INIT C");
			series = new ArrayList<LineItem>();
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
		
		
		public LineChart(Context context,AttributeSet attrs) {
			super(context,attrs);
			System.out.println("INIT At");
			
			series = new ArrayList<LineItem>();
			hstops = new AxisStops();
			vstops = new AxisStops();
			
			this.context=context;
			
			mEdgeEffectTop = new EdgeEffectCompat(context);
			mEdgeEffectLeft = new EdgeEffectCompat(context);
			mEdgeEffectRight = new EdgeEffectCompat(context);
			mEdgeEffectBottom = new EdgeEffectCompat(context);
			
			mScaleGestureDetector = new ScaleGestureDetector(context,mScaleGestureListener);
			mGestureDetector = new GestureDetectorCompat(context,mGestureListener);
			
			TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Charts, 0, 0);
			
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
		
		
		
		
		public LineChart(Context context,AttributeSet attrs,int defStyle) {
			
			
			super(context,attrs,defStyle);
			System.out.println("INIT DF");
			series = new ArrayList<LineItem>();
			hstops = new AxisStops();
			vstops = new AxisStops();
			
			this.context=context;
			
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
				assignSeriesColors= a.getBoolean(R.styleable.Charts_assignSeriesColors, true);
				legendSize= a.getDimension(R.styleable.Charts_legendBoxSize, 30);
				drawSpace = a.getDimension(R.styleable.Charts_drawSpace, 5);
				showgrid= a.getBoolean(R.styleable.Charts_showGrid, false);
				drawLabels = a.getBoolean(R.styleable.Charts_drawLabels, false);
				isHorizontal =a.getBoolean(R.styleable.Charts_isHorizontal, false);
				rectcolor =a.getColor(R.styleable.Charts_boundingboxColor, Color.BLACK);
				useSeriesPrimary= a.getBoolean(R.styleable.Charts_useSeriesPrimary, true);
				accentprimarycolor =a.getColor(R.styleable.Charts_accentprimaryColor, Color.RED);
				
				XisNum = a.getBoolean(R.styleable.Charts_XisNum, false);
				YisNum = a.getBoolean(R.styleable.Charts_YisNum, false);
				YisCategory = a.getBoolean(R.styleable.Charts_YisCategory, false);
				XisCategory = a.getBoolean(R.styleable.Charts_XisCategory, false);
				
				
			}
			
			finally
			{
				
				a.recycle();
			}
			
			
			
		}
		
	
	 /*********************************************************/
	 private class lineposition
	 {
		 float x;
		 float y;
		 
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
		
	
		
	 private void getXCategory()
	{
		 List<String> uniX= new ArrayList<String>();
		 
		 Collections.sort(series, new LineOrderComparator());
		 
		 
		 
		 
		 for(int i=0; i<series.size();i++)
		 {
					
			    System.out.println("SERIES UNIQ" + series.get(i).order+series.get(i).getCatXLabel());
				uniX.add(series.get(i).getCatXLabel());
		 }		
		 
		
		 
		 
		 
		LinkedHashSet<String> uxVal= new LinkedHashSet<String>(uniX);
		
		
		uniqueX = uxVal.toArray(new String[uxVal.size()]);
		
		for(int j=0; j<uniqueX.length;j++)
		{
			 
			 System.out.println("UNIQ" + uniqueX[j]);
		}
		 
		
			
	}
	 
	 
	 private String[] getYCategory()
	{
		 
		 
		 
		   Collections.sort(series, new LineOrderComparator());
			 List<String> uniY= new ArrayList<String>();
			 for(int i=0; i<series.size();i++)
			 {
					
				uniY.add(series.get(i).getCatYLabel());
			 }		
			LinkedHashSet<String> uyVal= new LinkedHashSet<String>(uniY);
			
			
			uniqueY = uyVal.toArray(new String[uyVal.size()]);
			
			
			for(int j=0; j<uniqueY.length;j++)
			{
				 
				 System.out.println("UNIQ" + uniqueY[j]);
			}
			
			
			return uniqueY;
	}
	 
		public Boolean isGridShown()
		{
			return showgrid;
		}
		
		public void showGridLines(Boolean showgrid)
		{
			this.showgrid=showgrid;
		}
		
		//***********************************************//
		
		private void assignColors()
		{
			calculateSeriesItems();
			getSeriesNames();
			
			
				
			
			if(assignSeriesColors)
			{
				calculateSeriesColors();
				
			}
			else 
			{
				System.out.println("SERIES COLOR NOT ASSIGNED");
				calculateColorsFromSeries();
				
			}
		}
		public void setSeries(List<LineItem> series,int numseries)
		{
					this.series=series;
					this.numseries=numseries;
					
					
					
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
				
				 System.out.println("SERIES"+ series.get(i).getSeriesName()+
						 
						 series.get(i).getColor());
				 seriescolor.put(series.get(i).getSeriesName(),series.get(i).getColor());
				
				 
			 }
			 
			 String [] keys = seriescolor.keySet().toArray(new String[seriescolor.size()]);
			 for(int m=0;m<keys.length;m++)
				 System.out.println("COLS"+seriescolor.get(keys[m]));
			
		}
		
		private void getSeriesNames() {
			
			
			List<String> names = new ArrayList<String>();
			for(int i=0;i<series.size();i++)
			{
				names.add(series.get(i).getSeriesName());
			}
			
			 seriesname= new HashSet<String>(names).toArray(new String[numseries]);
			
		}

		private void calculateSeriesColors() {
			
			
			int[] colors = new int[numseries];
			ColorCode sercolor = new ColorCode();
			if(useSeriesPrimary)
				colors = sercolor.setPrimaryColor(numseries,100,0.8f,0.9f);
			else
				//colors = sercolor.setHSVAccent(accentprimarycolor,numseries,60,0.8f, 20);
				colors = sercolor.setHSVAccent(accentprimarycolor,numseries,0.5f,1.0f, 20);
			for(int i=0; i<colors.length;i++)
			{
				System.out.println("SERIESNAME" + seriesname[i] + colors[i]);
				seriescolor.put(seriesname[i], colors[i]);
				
			}
		}
		
		

		
		

		public void drawLegend(Canvas canvas) {



            int row=1;
            int a=0;
            float lastleft=0;

            int [] labelwidths = new int[numseries];


            for (int i=0; i<labelwidths.length; i++)

            {
                labelwidths[i]= (int) Math.abs(ltPaint.measureText(seriesname[i]+"0"));

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
				ltPaint.setColor(seriescolor.get(seriesname[i]));
			
		
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

                if(bottom>legendBox.bottom) {
                    System.out.println("BOTTOM");
                    redrawLegendBox(coordbounds.width(), drawBoxSize);
                }


                System.out.println("TOP" + top + "BT" +
                        bottom + "left" + left + "right" + right);
                canvas.drawRect(left,top,right,
                        bottom, lPaint);

                System.out.println(seriesname[i]);
                canvas.drawText(seriesname[i], right + drawSpace,
                        bottom, ltPaint);
				
			}
			
			
			
		}




		private void redrawLegendBox(float coww,int drawBoxSize) {
			
			
			System.out.println("REDRAW LEGEND");
						
			/*float coll= (float) boxbounds.height()-mLabelHeight-(3*mAxisLabelHeight+aPaint.ascent()+5*mLabelSeparation)
					-(legendSize+mLabelSeparation+drawBoxSize);*/
			
			
			float coll = (float) boxbounds.height()- ticks.subLabelHeight-
					(2*mAxisLabelHeight+3*mLabelSeparation)-(-aPaint.ascent())
					-(legendSize+drawBoxSize);
			
			//float coll = (float) boxbounds.height()-mLabelSeparation-(legendSize+(drawBoxSize));
			
			coordbounds = new RectF(0.0f,0.0f,
					coww,coll);
			
			float xoffset = ticks.nvLabelWidth+(2*mAxisLabelHeight)+3*mLabelSeparation-(-aPaint.ascent());
			
			
			//float xoffset = mLabelWidth+mLabelSeparation;
	        coordbounds.offsetTo(getPaddingLeft()+ xoffset, getPaddingTop()+mLabelSeparation);
			
			legendBox = new RectF(0.0f,0.0f,coww,legendSize + drawBoxSize);
			
			legendSize  = legendSize+ drawBoxSize;
			
			
			
			legendBox.offset(getPaddingLeft()+xoffset,coordbounds.bottom+ticks.subLabelHeight+(mAxisLabelHeight-aPaint.ascent()));
		
			invalidate =true;
			
			invalidate();
		
		}
		
		
		private void calculateSeriesItems()
		{
			seritems = new HashMap<Integer,
					 LineItem[]>();
			linepositions = new HashMap<Integer,lineposition[]>(); 
			
			 
			 for(int i=0; i<numseries;i++)
			 {	
				 List<LineItem> list = new ArrayList<LineItem>();
				 List<lineposition> pos = new ArrayList<lineposition>();
				 
				
					for(int m=0; m<series.size();m++)
					{
						System.out.println("SIZE"+m);
						
						
						if(series.get(m).getSeries()==i+1)
						{
							System.out.println("SER"+ series.get(m).getXVal());
							list.add(series.get(m));
							lineposition position = new lineposition();
							position.x=0.0f;
							position.y=0.0f;
							
							pos.add(position);
							
							
						}
							
					}
					
					for(int j=0;j< list.size();j++)
					{
						System.out.println("LIST" + list.get(j).order+list.get(j).getCatXLabel());
					}
					
//					if(!isHorizontal)
//					{
						if(XisNum & YisNum)
						{
							Collections.sort(list, new LineValueComparator());
						}
						if(XisCategory & YisNum)
						{
							Collections.sort(list, new LineOrderComparator());
							
							for(int j=0;j< list.size();j++)
							{
								System.out.println(" ORDERED LIST" + list.get(j).order+list.get(j).getCatXLabel());
							}
						}
						
						if(YisCategory & XisNum)
						{
							Collections.sort(list, new LineOrderComparator());
						}
						
						
						if(XisCategory & YisCategory)
						{
							Collections.sort(list, new LineOrderComparator());
						}
						
						
						
//						else if(YisNum)
//						{
//							Collections.sort(list, new LineValueComparator());
//						}
						
					//}
//					else
//					{
//						if(YisNum)
//						{
//							Collections.sort(list, new LineValueComparator());
//						}
//						else if(YisCategory)
//						{
//							Collections.sort(list, new LineOrderComparator());
//						}
//					}
					//Collections.sort(list);
					LineItem[] items =list.toArray(new LineItem[list.size()]);
					
					seritems.put(i,items);
					
					linepositions.put(i, pos.toArray(new lineposition[pos.size()]));
			 }
		}
		
		private void init()
		{
			
			lines = new DrawGridLines();
			
			 bPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     bPaint.setStyle(Paint.Style.FILL);
		     
		     
		     cPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     cPaint.setStyle(Paint.Style.STROKE);
		     cPaint.setStrokeWidth(3);
		     
		     lPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		     lPaint.setStyle(Paint.Style.FILL_AND_STROKE);
			
			// Set up the paint for the label text
	        tPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	        tPaint.setColor(textcolor);
	        tPaint.setTextSize(mTextSize);
	        tPaint.setTextAlign(Paint.Align.CENTER);
	        
	        labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	        labelPaint.setColor(textcolor);
	        labelPaint.setTextSize(mLabelSize);
	        labelPaint.setTextAlign(Paint.Align.CENTER);
	        
	        
	    	fPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	        fPaint.setColor(textcolor);
	        fPaint.setTextSize(mTextSize);
	        fPaint.setTextAlign(Paint.Align.LEFT);
	        
	        vPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	        vPaint.setColor(textcolor);
	        vPaint.setTextSize(mTextSize);
	        vPaint.setTextAlign(Paint.Align.RIGHT);
	        
	        
	        
	        gPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	        gPaint.setColor(gridcolor);
	       
	        aPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	        aPaint.setColor(textcolor);
	        aPaint.setTextSize(mAxisLabelSize);
	        aPaint.setTextAlign(Paint.Align.CENTER);
	       
	        rPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	        rPaint.setColor(rectcolor);
	       
	        rPaint.setStyle(Paint.Style.STROKE);
	        
	       
	        ltPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	        ltPaint.setTextSize(mTextSize);
		    ltPaint.setStyle(Paint.Style.STROKE);
	        
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
				
			
				
				coww = (float)  w-xpad
						-mLabelWidth-2*mAxisLabelHeight-4*mLabelSeparation-(-aPaint.ascent());
				
				cohh = (float)  h-ypad-mLabelHeight-
						(2*mAxisLabelHeight)-(-aPaint.ascent())-(legendSize) - 3 * mLabelSeparation;

				
				float bxww = (float) w-xpad;
				float bxhh = (float) h-ypad;
				
				mAxisLabelWidth = (int) (coww/2);
				
				boxbounds = new RectF(getPaddingLeft(),getPaddingTop(),
						getPaddingLeft()+bxww,getPaddingTop()+bxhh);
					
				coll = bxhh-legendSize-mLabelSeparation;
				
				coordbounds = new RectF(0.0f,0.0f,
						coww,coll);
				
				
				float xoffset = mLabelWidth+ (2*mAxisLabelHeight) - aPaint.ascent();
				
				System.out.println("COWW" + coww + "BXWW" + boxbounds.width()+"XOFFSET"+ xoffset);
				
				
				//axisLabelSpace = (2*mAxisLabelHeight+aPaint.ascent());
				
				axisLabelSpace = (2*mAxisLabelHeight-aPaint.ascent())+3*mLabelSeparation;
				
				
				coordbounds.offset(getPaddingLeft()+ xoffset,getPaddingTop());
				
				
				legendBox = new RectF(0.0f,0.0f,coww,legendSize);
				
				legendBox.offset(getPaddingLeft()+xoffset,coordbounds.bottom+ mLabelSeparation);
				
				
				System.out.println("COORD_SIZE" + coordbounds);
				
				System.out.println("COORD" + coordbounds.bottom + "LEGEND BOX" + legendBox.top
						+ "LGBOX BOTTOM" + legendBox.bottom);
				
				
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
			 
			 
			 enclosingBox.set(coordbounds.left,coordbounds.top,coordbounds.right,
					 coordbounds.bottom+ticks.subLabelHeight);
			 
		 }
		 
		 
	@Override 
	 public void onDraw(Canvas canvas)
	 
	 {
		super.onDraw(canvas);
		
		int clipRestoreCount = canvas.save();
		canvas.clipRect(coordbounds);
		
		drawAxes(canvas);
	
		drawData(canvas);
		
		drawEdgeEffectsUnclipped(canvas);
		
		
		
		canvas.restoreToCount(clipRestoreCount);
		
		drawText(canvas);
		
		//drawLegend(canvas);
		
		/*if(!invalidate)
		drawLegend(canvas);
		else
		drawInvalidateLegend(canvas);*/

		 drawLegend(canvas);
		
		canvas.drawRect(coordbounds,rPaint);
		
		
		
		 
		 
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
	
	private float getCentreHCategory(double value)
	{
		float pos = coordbounds.left + barspace*
				(float)(value-Viewport.left);
				
				return pos;
	}
	
	private float getCentreVCategory(double value)
	{
		float pos = coordbounds.bottom - barspace*
				(float)(value-Viewport.top);
				
				return pos;
	}
	 
	
	private void getVerticalCentres(float centreh,float centrev,LineItem item,lineposition pos)
	{
	
		if(XisCategory)
			
		{
			for(int n=0; n<uniqueX.length;n++)
				
			{
				if(item.getStringXVal().equals(uniqueX[n]))
				 centrev = getCentreVCategory(n+1);
			}
			
			
		}
		
		else
			centrev = getCentreV(item.getXVal());
		
		if(YisCategory)
			
		{
			for(int r=0; r<uniqueY.length;r++)
				
			{
				if(item.getStringYVal().equals(uniqueY[r]))
				 centreh = getCentreHCategory(r+1);
			}
			
			
		}
		
		else
			centreh = getCentreH(item.getYVal());
		
		pos.x=centreh;
		pos.y=centrev;
		
		
		
		
	}
	
	private void getHorizontalCentres(float centreh,float centrev,LineItem item,lineposition pos)
	{
		
		
		
		if(XisCategory)
			
		{
			for(int n=0; n<uniqueX.length;n++)
				
			{
				if(item.getStringXVal().equals(uniqueX[n]))
				 centreh = getCentreHCategory(n+1);
			}
			
			
		}
		
		else
			centreh = getCentreH(item.getXVal());
		
		if(YisCategory)
			
		{
			for(int r=0; r<uniqueY.length;r++)
				
			{
				if(item.getStringYVal().equals(uniqueY[r]))
				 centrev = getCentreVCategory(r+1);
			}
			
			
		}
		
		else
			centrev = getCentreV(item.getYVal());
		
		pos.x=centreh;
		pos.y=centrev;
		
		
	}
	 

	 private void drawData(Canvas canvas) {
		
		 
		 
		 barposition = new BarPosition[series.size()];
		 
		 int p=0;
		 if(!isHorizontal)
		 {
			 
			 
			 
			 System.out.println("DRAW DATA HORIZ"+seritems.size());
			 
			 for(int i=0; i<seritems.size();i++) 
				 
				 
			 {	
				 
				 
				 
				 
				 
				 	LineItem[] items = seritems.get(i);	
				 	
				 	System.out.println(seritems.get(i)+"ITEM");
				 	
				 	
				 	lineposition[]position = linepositions.get(i);
				 	
					for(int m=0; m<items.length;m++)
					{
						
						
	
						float centreh=0.0f,centrev=0.0f;
						
						
						if(items[m].getSeries()==i+1)
						{
							
							System.out.println("ITEM SER DATA" + items[m].getXVal());
							getHorizontalCentres(centreh,centrev,items[m],position[m]);
							
							barposition[p]= new BarPosition();
							
							barposition[p].setLeftEdge(position[m].x-15);
							barposition[p].setRightEdge(position[m].x+15);
							barposition[p].setTopEdge(position[m].y-15);
							barposition[p].setBottomEdge(position[m].y+15);
							
							if(XisNum)
								barposition[p].setXValue(items[m].getXVal());
							
							if(YisNum)
								barposition[p].setYValue(items[m].getYVal());
								
								
							bPaint.setColor(seriescolor.get(items[m].getSeriesName()));
							
							cPaint.setColor(seriescolor.get(items[m].getSeriesName()));
							
							
							
							if(drawLabels)
							{
								
								canvas.drawCircle(position[m].x,position[m].y,
										markerradii, bPaint);
								System.out.println("DRAW LABELS");
								canvas.drawText(items[m].getLabel(),0,
										items[m].getLabel().length(),
										position[m].x,position[m].y, labelPaint);
								barposition[p].setLabel(items[m].getLabel());
								
								
							}
							
							else
							{
								System.out.println("POS X"+ position[m].x);
								System.out.println("POS Y"+ position[m].y);
								
								canvas.drawCircle(position[m].x,position[m].y,
										markerradii, bPaint);	
								
								
							}
						
						}
						
						p++;
					}
					
					
					if(position.length>0 & type.equals(LineType.CUBIC_LINE))
					{
						cubicPath.moveTo(position[0].x,position[0].y);
					}
					
					
					for(int a=0;a<position.length;a++)
					{
						
						lineposition cp1 = new lineposition();
						lineposition cp2 = new lineposition();
						lineposition cp3 = new lineposition();
						
						
						if(type.equals(LineType.CUBIC_LINE))
						{
							if(a<=position.length-3)
							{
								
								System.out.println("SIZE"+ position.length+"A"+a);
								CalculateControlPoints(position[a].x,position[a].y,position[a+1].x,
										position[a+1].y,position[a+2].x,position[a+2].y,cp1,cp2,cp3);
								cubicPath.cubicTo(cp1.x,cp1.y,cp2.x,cp2.y,cp3.x, cp3.y);
							}
							
							if(a<position.length& a+1>=position.length)
							{
								CalculateControlPoints(position[a].x,position[a].y,position[a].x,
										position[a].y,position[a].x,position[a].y,cp1,cp2,cp3);
								cubicPath.cubicTo(cp1.x,cp1.y,cp2.x,cp2.y,cp3.x, cp3.y);
							}
							
							if(a+2>=position.length& a+1<position.length)
							{
								CalculateControlPoints(position[a].x,position[a].y,position[a+1].x,
										position[a+1].y,position[a+1].x,position[a+1].y,cp1,cp2,cp3);
								cubicPath.cubicTo(cp1.x,cp1.y,cp2.x,cp2.y,cp3.x, cp3.y);
//								
//								
							}
							
							
							
							
						}
						
					}
					
					
					
				
					
					float [] pts = new float[position.length*2];
					for(int a=0;a<position.length;a++)
					{
						System.out.println("POS X LINE"+ position[a].x);
						System.out.println("POS Y LINE"+ position[a].y);
						
						
						 
						 
						/*pts[a] = position[a].x;
						pts[a+1]=position[a].y;*/
						
						
						
						
						
							if(type.equals(LineType.LINE))
							{
								if(a!=position.length-1)
								
									canvas.drawLine(position[a].x,position[a].y,position[a+1].x,position[a+1].y, bPaint);
							
							}
							
		
						
					}
					

					if(type.equals(LineType.CUBIC_LINE))
					{
					canvas.drawPath(cubicPath, cPaint);
					cubicPath.reset();
					}
					
					
			 }

				
				
			}
			
			else
			{
				
				System.out.println("DRAW DATA");
					
				 for(int i=0; i<seritems.size();i++)	 
				 {	
					 	
					 	LineItem[] items = seritems.get(i);	
					 	
					 	lineposition[]position = linepositions.get(i);
					 	
						for(int m=0; m<items.length;m++)
						{
		
							float centreh=0.0f,centrev=0.0f;
							
							
							if(items[m].getSeries()==(i+1))
							{
								
								getVerticalCentres(centreh,centrev,items[m],position[m]);
								
								barposition[p]= new BarPosition();
								
								barposition[p].setLeftEdge(position[m].x-15);
								barposition[p].setRightEdge(position[m].x+15);
								
								System.out.println("POSITION Y" + position[m].y);
								barposition[p].setTopEdge(position[m].y-15);
								barposition[p].setBottomEdge(position[m].y+15);
								
								if(XisNum)
									barposition[p].setXValue(items[m].getXVal());
								
								if(YisNum)
									barposition[p].setYValue(items[m].getYVal());
									
								
								
								bPaint.setColor(seriescolor.get(items[m].getSeriesName()));
								
								cPaint.setColor(seriescolor.get(items[m].getSeriesName()));
								
								if(drawLabels)
								{
									canvas.drawCircle(position[m].x,position[m].y,
											markerradii, bPaint);
									barposition[p].setLabel(items[m].getLabel());
									System.out.println("DRAW LABELS"+ items[m].getLabel());
									canvas.drawText(items[m].getLabel(),0,items[m].
											getLabel().length(),
											position[m].x,position[m].y, labelPaint);
								}
								
								else
								{
									canvas.drawCircle(position[m].x,position[m].y,
											markerradii, bPaint);	
										
								}
							
							}
							
							p++;
						}
					
						
						/*float [] pts = new float[position.length*2];
						for(int a=0;a<position.length;a++)
						{
							pts[a] = position[a].x;
							pts[a+1]=position[a].y;
							
						}
						
						canvas.drawLines(pts,0,position.length*2, bPaint);*/
						
						
						if(position.length>0 & type.equals(LineType.CUBIC_LINE))
						{
							cubicPath.moveTo(position[0].x,position[0].y);
						}
						
						
						for(int a=0;a<position.length;a++)
						{
							
							lineposition cp1 = new lineposition();
							lineposition cp2 = new lineposition();
							lineposition cp3 = new lineposition();
							
							
							if(type.equals(LineType.CUBIC_LINE))
							{
								if(a<=position.length-3)
								{
									
									System.out.println("SIZE"+ position.length+"A"+a);
									CalculateControlPoints(position[a].x,position[a].y,position[a+1].x,
											position[a+1].y,position[a+2].x,position[a+2].y,cp1,cp2,cp3);
									cubicPath.cubicTo(cp1.x,cp1.y,cp2.x,cp2.y,cp3.x, cp3.y);
								}
								
								if(a<position.length& a+1>=position.length)
								{
									CalculateControlPoints(position[a].x,position[a].y,position[a].x,
											position[a].y,position[a].x,position[a].y,cp1,cp2,cp3);
									cubicPath.cubicTo(cp1.x,cp1.y,cp2.x,cp2.y,cp3.x, cp3.y);
								}
								
								if(a+2>=position.length& a+1<position.length)
								{
									CalculateControlPoints(position[a].x,position[a].y,position[a+1].x,
											position[a+1].y,position[a+1].x,position[a+1].y,cp1,cp2,cp3);
									cubicPath.cubicTo(cp1.x,cp1.y,cp2.x,cp2.y,cp3.x, cp3.y);
//									
//									
								}
								
								
								
								
							}
							
						}
						
						float [] pts = new float[position.length*2];
						for(int a=0;a<position.length;a++)
						{
							System.out.println("POS X LINE"+ position[a].x);
							System.out.println("POS Y LINE"+ position[a].y);
							/*pts[a] = position[a].x;
							pts[a+1]=position[a].y;*/
							
								if(type.equals(LineType.LINE))
								{
									if(a!=position.length-1)
										canvas.drawLine(position[a].x,position[a].y,position[a+1].x,position[a+1].y, bPaint);
								}
						}
						
						if(type.equals(LineType.CUBIC_LINE))
						{
						canvas.drawPath(cubicPath, cPaint);
						cubicPath.reset();
						}
						
						
						
				 }
			}
			
			
		 
		
	}


	private void CalculateControlPoints(float x1, float y1, float x2, float y2,
			float x3, float y3,lineposition cp1, lineposition cp2, lineposition cp3) {
		
		float diffx = x2-x1;
		float diffy= y2-y1;
		
		cp1.x = x1 + (diffx*second);
		cp1.y = y1 + (diffy*second);
		
		cp2.x= x2;
		cp2.y=y2;
		
		float diffxc = x3-x2;
		float diffyc= y3-y2;
		
		cp3.x = x2 + (diffxc*first);
		cp3.y = y2 + (diffyc*first);
		
		
	}


	private void drawAxes(Canvas canvas) {
		
		 
		 
		 	float [] hposstops = new float[hstops.axisstops.length];
			float [] vposstops= new float[vstops.axisstops.length];
			
		
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
			/*for(int i=0; i< hstops.axisstops.length;i++)
			{
				hposstops [i] = (float) (coordbounds.left + coordbounds.width()*
						(hstops.axisstops[i]-Viewport.left)/Viewport.width());
				System.out.println(hposstops[i]+"HposStops");
			}
			
			
			for(int j=0;j<vstops.axisstops.length;j++)
			{
				vposstops [j] = (float) (coordbounds.bottom - coordbounds.height()*
						(vstops.axisstops[j]-Viewport.top)/Viewport.height());
				
				
				System.out.println(vposstops[j]+"VposStops");
			}
//			
*/			 hstops.posstops=hposstops;
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
					coordbounds.left, coordbounds.width(),true,isHFlipped); 
					
					
				}
		
			
				else if(XisNum)
			
				{
					
					
//					DrawTextNum(canvas,getHLabelName(),boxbounds.bottom,i,hstops 
//							
//						,coordbounds.left,coordbounds.width(),hInterval,true);
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
					coordbounds.left, coordbounds.width(),true,isHFlipped); 
		
			
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
		   ViewCompat.postInvalidateOnAnimation(LineChart.this);
		 
		   lastSpanX = spanX;
		   lastSpanY = spanY;
			return true;
		 }
	 };
	 
	 
	 
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
	
	
private void CalculateExp(double [] stops , int i,Paint tPaint,int nLabelWidth,double interval,Boolean isH) {
		
		
		
	
		
		String[] outstring = formatfloat((float)stops[i],
				interval,tPaint,nLabelWidth);
		
		System.out.println("EXPONENT"+outstring[1]+"STOPS"+ stops[i]);
		
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



