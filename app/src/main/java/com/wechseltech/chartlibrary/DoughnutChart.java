
package com.wechseltech.chartlibrary;

import java.text.DecimalFormat;
import java.util.ArrayList;
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
import android.graphics.Paint.Align;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;

public class DoughnutChart extends ChartView {
	
	//************************************//
	
	Map<String,Integer> seriescolor;
	
	AxisStops hstops,vstops;
	
	List<CategoryMultipleSeries> series;
	Paint tPaint,aPaint,rPaint,
	gPaint,bPaint,lPaint,cPaint,sPaint,legendPaint,
	innerPaint,seriesPaint;
	 CategoryMultipleSeries  chosenitem;
	
	 Context context;
	//************************************//
	
	RectF boxbounds,coordbounds,legendBox;
	
	//************************************//
	
	float mPosX,mPosY,diffX=0,diffY=0;
	
	int numseries;
	
	int minLabelWidth,minLabelHeight,mLabelWidth,
	mLabelHeight,nLabelWidth,subLabelHeight,nLabelHeight;
	
	
	float axisLabelSpace;
	
	float xpoint,ypoint;
	
	
	float [] StrtAngle;
	RectF[] concentrics;
	Map<String,Integer> uniqcolors =
			new HashMap<String,Integer>();
	
	float diameter;
	//*********************************************//
	
	private ScaleGestureDetector mScaleGestureDetector=null;
	private GestureDetectorCompat mGestureDetector=null;
	private float mScaleFactor = 1.f;
	
	double [] mSum;
	String [] categoryUniq,seriesname;
	
	Boolean usePrimary;
	
	Path[] circPath;
	int drawBoxSize;
	
	
	Boolean invalidate =false;
	//*********************************************//
	public DoughnutChart(Context context) {
		super(context);
		this.context=context;
		System.out.println("INIT C");
		series = new ArrayList<CategoryMultipleSeries>();
		seriescolor = new HashMap<String,Integer>();
		mScaleGestureDetector = new ScaleGestureDetector(context,mScaleGestureListener);
		
		
		
		
	
	}
	
	
	
	
	public DoughnutChart(Context context,AttributeSet attrs) {
		super(context,attrs);
		System.out.println("INIT At");
		this.context=context;
		series = new ArrayList<CategoryMultipleSeries>();
		seriescolor = new HashMap<String,Integer>();
		
		
		
		mScaleGestureDetector = new ScaleGestureDetector(context,mScaleGestureListener);
		
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Charts, 0, 0);
		
		try
		{
			
			
			mLabelSeparation = a.getDimension(R.styleable.Charts_labelSeparation, 8);
			mTextSize = a.getDimension(R.styleable.Charts_textSize, 12);
			textcolor = a.getColor(R.styleable.Charts_textColor, Color.BLACK);
			accentprimarycolor = a.getColor(R.styleable.Charts_accentprimaryColor, Color.RED);
			assignColors= a.getBoolean(R.styleable.Charts_assignColors, true);
			useSeriesPrimary= a.getBoolean(R.styleable.Charts_useSeriesPrimary, false);
			usePrimary =a.getBoolean(R.styleable.Charts_usePrimary, true);
			legendSize = a.getDimension(R.styleable.Charts_legendBoxSize,30);
			
			
			
			
		}
		
		finally
		{
			
			a.recycle();
		}
		
		
	}
	
	
	
	
	public DoughnutChart(Context context,AttributeSet attrs,int defStyle) {
		
		
		super(context,attrs,defStyle);
		this.context=context;
		System.out.println("INIT DF");
		series = new ArrayList<CategoryMultipleSeries>();
		seriescolor = new HashMap<String,Integer>();
		
		mScaleGestureDetector = new ScaleGestureDetector(context,mScaleGestureListener);
		
		
		TypedArray a = context.getTheme().
				obtainStyledAttributes(attrs,R.styleable.Charts,defStyle,defStyle);
		
		try
		{
			
			mLabelSeparation = a.getDimension(R.styleable.Charts_labelSeparation, 8);
			mTextSize = a.getDimension(R.styleable.Charts_textSize, 12);
			textcolor = a.getColor(R.styleable.Charts_textColor, Color.BLACK);
			accentprimarycolor = a.getColor(R.styleable.Charts_accentprimaryColor, Color.RED);
			assignColors= a.getBoolean(R.styleable.Charts_assignColors, true);
			useSeriesPrimary= a.getBoolean(R.styleable.Charts_useSeriesPrimary, false);
			usePrimary =a.getBoolean(R.styleable.Charts_usePrimary, true);
			legendSize = a.getDimension(R.styleable.Charts_legendBoxSize,30);
			
			
			
		}
		
		finally
		{
			
			a.recycle();
		}
		
		
		
}
	
	private void init()
	{
		
		
		
		
		 tPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	     tPaint.setColor(textcolor);
	     tPaint.setTextSize(mTextSize);
	     tPaint.setTextAlign(Align.CENTER);
		
	    
	     
	     bPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	     bPaint.setStyle(Paint.Style.STROKE);
	     
	     innerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	     innerPaint.setStyle(Paint.Style.FILL);
	     
	     cPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	     cPaint.setStyle(Paint.Style.STROKE);
	     
	     sPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	     sPaint.setStyle(Paint.Style.FILL);
	     sPaint.setTextSize(mTextSize);
	     
	     seriesPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	     seriesPaint.setStyle(Paint.Style.FILL);
	     seriesPaint.setTextSize(16);
	     
	     lPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	     lPaint.setStyle(Paint.Style.FILL);
	     
	     legendPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	     legendPaint.setColor(textcolor);
	     legendPaint.setTextSize(mTextSize);
	     legendPaint.setTextAlign(Align.LEFT);
       
        
        mLabelHeight =(int) Math.abs(tPaint.getFontMetrics().top);
        
        drawBoxSize = mLabelHeight;
        minLabelHeight = (int) Math.abs(tPaint.getFontMetrics().bottom);
    
        minLabelWidth = (int) Math.abs(tPaint.measureText("000"));
        mLabelWidth = (int) Math.abs(tPaint.measureText("00000"));
        
       
  
        
	}
	
	
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		
		float xpad = (float)(getPaddingLeft()+ getPaddingRight());
		float ypad = (float)(getPaddingTop() +  getPaddingBottom());
		
		float coww =0.0f,cohh=0.0f,coll =0.0f;
		
		
		init();
		
		coww = (float) w-xpad
				-2*mLabelWidth;
		cohh = (float) h-ypad-legendSize-2*mLabelSeparation-mLabelHeight-2*mLabelSeparation;
				

		float bxww = (float) w-xpad;
		float bxhh = (float) h-ypad;
		
		
		boxbounds = new RectF(getPaddingLeft(),getPaddingTop(),
				getPaddingLeft()+bxww,getPaddingTop()+bxhh);
		
		coordbounds = new RectF(0.0f,0.0f,
				coww,cohh);
		
		//coll = bxhh-legendSize-mLabelSeparation;
		
		 diameter = Math.min(coww, cohh);
	        coordbounds = new RectF(
	                0.0f,
	                0.0f,
	                diameter,
	                diameter);
	        float xoffset = mLabelWidth+mLabelSeparation;
	        //coordbounds.offsetTo(getPaddingLeft()+ xoffset, getPaddingTop());
	        
	        
	        float toffset = mLabelSeparation;
	        
	        
	        coordbounds.offsetTo(boxbounds.centerX()-(diameter/2), boxbounds.centerY()-(diameter/2));
	        
	        

	        legendBox = new RectF(0.0f,0.0f,coww,legendSize );
			

			
			legendBox.offset(getPaddingLeft()+xoffset,coordbounds.bottom);
			
			assignColors();
		
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
		 mScaleGestureDetector.onTouchEvent(event);
		 return true;
	 }
		
	 private final ScaleGestureDetector.OnScaleGestureListener mScaleGestureListener
	  = new ScaleGestureDetector.SimpleOnScaleGestureListener()
	 {
		 
		 
		 @Override 
		 public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector)
		 {
			 
			return true;
			 
		 }
		 
		 @Override
		 public boolean onScale(ScaleGestureDetector scaleGestureDetector)
		 {
			 
			   mScaleFactor *=  mScaleGestureDetector.getScaleFactor();

		        // Don't let the object get too small or too large.
			    
			    mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));
			    
			    if (mScaleFactor < 5f) {
			    	   // 1 Grabbing
			    	   final float centerX = mScaleGestureDetector.getFocusX();
			    	   final float centerY = mScaleGestureDetector.getFocusY();
			    	   // 2 Calculating difference
			    	   diffX = centerX - mPosX;
			    	   diffY = centerY - mPosY;
			    	   // 3 Scaling difference
			    	   diffX = diffX * mScaleGestureDetector.getScaleFactor() - diffX;
			    	   diffY = diffY * mScaleGestureDetector.getScaleFactor() - diffY;
			    	   // 4 Updating image origin
			    	   mPosX -= diffX;
			    	   mPosY -= diffY;
			    	}

		       invalidate();
		        
		        return true;
		 }
	 };
	
	 @Override 
	 public void onDraw(Canvas canvas)
	 
	 {
		super.onDraw(canvas);
		
		canvas.save();
		
		canvas.translate(mPosX, mPosY);
	    canvas.scale(mScaleFactor, mScaleFactor);
	   
	    
	    drawData(canvas);
	    

	    drawLegend(canvas);


	    
	    canvas.restore();
	 
	 }
	 
	
	
	
	 public void drawLegend(Canvas canvas) {

            int [] labelwidths = new int[uniqcolors.size()];
			int row=1;
			int a=0;
            float lastleft=0,top=0,bottom=0;
			
			String [] keys = uniqcolors.keySet().toArray(new String[uniqcolors.size()]);
            for (int i=0; i<uniqcolors.size(); i++)

            {
                labelwidths[i]= (int) Math.abs(legendPaint.measureText(keys[i]+"0"));
            }
			for (int i=0; i<uniqcolors.size(); i++)
				
			{
				
				int lLabelWidth = 0; float left=0; float right=0;


                System.out.println(keys[i]+"DRAW SPACE"+ drawSpace+"DRAW BOX SIZE"+ drawBoxSize
                );
				if(i!=0)
				lLabelWidth = (int) Math.abs(legendPaint.measureText(keys[i-1]+"0"));

                System.out.println("LAST LEFT" + lastleft);

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


                System.out.println("LEFT" + left + "RIGHT" + right + "I" + i + keys[i] +
                        "LABELWIDTH" + labelwidths[i]);


                legendPaint.setColor(uniqcolors.get(keys[i]));


                System.out.println("RIGHT"+legendBox.right);

				if(((right + labelwidths[i])<=legendBox.right))
				{
                            System.out.println("LABEL"+labelwidths[i] + "RIGHT LABEL"
                                    +(right+labelwidths[i]));
				}
				else
				{
					row =row+1;
                    lastleft=0;
                    left = legendBox.left +drawSpace;
                    lastleft=left;
                    right = left+drawBoxSize;



                }


                top = legendBox.top+((row)*drawSpace)+(row-1)*drawBoxSize;
                bottom = top+drawBoxSize;

                if(bottom>legendBox.bottom)
                {
                    redrawLegendBox(legendBox.width(), drawBoxSize, mLabelWidth);
                }


                canvas.drawRect(left, top, right,
                        bottom, legendPaint);


                canvas.drawText(keys[i], right + drawSpace,
                        bottom, legendPaint);


			}
			
			
			
		}

	 
	 
	 



	 private void redrawLegendBox(float coww,int drawBoxSize,int mLabelWidth) {
			
		 
		 
			
			//float coll = (float) boxbounds.height()-mLabelSeparation-(legendSize+(drawBoxSize));
			
			float cohh = (float) boxbounds.height()-
					(legendSize+2*mLabelSeparation+drawBoxSize)-mLabelHeight-2*mLabelSeparation;
			
			
			
			
			
			diameter = Math.min(coww, cohh);
			
			System.out.println("COHH" +cohh+"DIA" + diameter);
			
			coordbounds = new RectF(0.0f,0.0f,
					diameter,diameter);
			
			float xoffset = mLabelWidth+mLabelSeparation;
	       // coordbounds.offsetTo(getPaddingLeft()+ xoffset, getPaddingTop());
			 coordbounds.offsetTo(boxbounds.centerX()-(diameter/2), boxbounds.centerY()-(diameter/2));
			
			legendBox = new RectF(0.0f,0.0f,coww,legendSize + drawBoxSize);
			
			legendSize  = legendSize+ drawBoxSize;
			
			float toffset = mLabelSeparation;
		    
			
			legendBox.offset(getPaddingLeft()+xoffset,coordbounds.bottom+toffset);
			
			System.out.println("LG" + legendBox);
			
			invalidate =true;
			
			invalidate();
		}




	private void drawData(Canvas canvas) {
		
		
		tPaint.setTextAlign(Align.CENTER);
		canvas.drawText(textdesc,coordbounds.centerX(),boxbounds.top+2*mLabelSeparation, tPaint);
		
		tPaint.setTextAlign(Align.LEFT);
		
		float centerX = (coordbounds.left+coordbounds.right)/2;
		float centerY = (coordbounds.top+coordbounds.bottom)/2;
		getRectangles(canvas);
		
		
		
		
		
		float radii = (diameter/2)/(numseries+1);
		bPaint.setColor(Color.RED);
		
		  
		
		
		
		System.out.println("RADII"+radii + "DIAMETER" + diameter);
		
		StrtAngle = new float[numseries];
		
			
					
			for(int i=0;i<numseries;i++)
			{
				
				 
				
				for (CategoryMultipleSeries it : series) {

					
					if(it.getSeries()==(i+1))
					{
						
						bPaint.setColor(it.getColor());
						
						
						bPaint.setStrokeWidth((i+1)*radii-(i)*radii);
						
						
						System.out.println("ANGLE" + it.getAngle() + concentrics[i] + "COLOR" + it.getColor());
						
						/*if(i==0)
						canvas.drawRect(concentrics[i], tPaint);*/
						
						canvas.drawArc(concentrics[i],
			                    StrtAngle[i],
			                    it.getAngle(),
			                    false, bPaint);
						
						double arcd = it.getAngle()*Math.PI/180;
						
						it.setStartAngle(StrtAngle[i]);
						
						
							it.setInnerRadii((i)*radii);
							it.setOuterRadii((i+1)*radii);
						
						

						float [] pos = getText(canvas,StrtAngle[i],it.getAngle(),
								(i+1)*radii,(i+1)*radii,(i+1)*radii-(i)*radii);
						
						
						DecimalFormat format = new DecimalFormat("#.##");
						String per = format.format(it.getPercentage()) +"%";
						
						String name = it.getSeriesname();
						
						float arcradii = ((i+1)*radii-(i)*radii)/2+ i*radii;
						
						float arclength = (float) (arcd*arcradii) ;
						
						
						StrtAngle[i] = StrtAngle[i]+ it.getAngle();
						
						
						it.setEndAngle(StrtAngle[i]);
						
						if(arclength<sPaint.measureText(per))
						{
						System.out.println("PER"+per);
						float tx = (float) ((sPaint.getTextSize()*(arclength))/sPaint.measureText(per));
						canvas.drawText(per,pos[0],pos[1],sPaint);
						sPaint.setTextSize(tx);
						
						}
						else
						{
							sPaint.setTextSize(mTextSize);
							canvas.drawText(per,pos[0],pos[1],sPaint);
							
							
						}
						sPaint.setColor(Color.BLACK);
						sPaint.setTextAlign(Align.CENTER);
						
						
						
						it.setXTextOffset(pos[0]);
						
						it.setYTextOffset(pos[1]);
						
					}
					
					
					
				
				}
				
				float y = (float) (coordbounds.centerY()+((i+1)*radii)* Math.sin(90));
		        
		        
			     float  x = (float) (coordbounds.centerX()+((i+1)*radii)* Math.cos(90));
				 
				
			     canvas.drawText(seriesname[i],x, y,seriesPaint);
				
			}
            
 
		
		
	}


	private void drawText(Canvas canvas,float StartAngle,float sweepangle,String label,float radii) {
		
		
		tPaint.setTextAlign(Align.CENTER);
		canvas.drawText(textdesc,coordbounds.centerX(),boxbounds.top+2*mLabelSeparation, tPaint);
		
		tPaint.setTextAlign(Align.LEFT);
		
		
		double d = ((StartAngle + sweepangle)/2 * Math.PI/180);
		
		 double medianangle = (StartAngle + (sweepangle/2) );
		 
		 
		 
		
        float y = (float) (coordbounds.centerY()+
     		   (radii)* Math.sin(d));
        float x =0.0f;
        
        x = (float) (coordbounds.centerX()+
     			(radii * Math.cos(d)));
     	canvas.drawText(label,x,y, tPaint);
     	
     	if(medianangle<=90||medianangle>=270)
     	{
     		
     		x = (float) (coordbounds.centerX()-
         			(radii * Math.cos(d)));
         	canvas.drawText(label,x,y, tPaint);
     	}
     		
     		
   
         
		
	}




	private void getSeriesNames() {
		
		
		List<String> names = new ArrayList<String>();
		for(int i=0;i<series.size();i++)
		{
			names.add(series.get(i).getSeriesname());
		}
		
		 seriesname= new HashSet<String>(names).toArray(new String[numseries]);
		
	}

	private float[] getText(Canvas canvas,
			float StartAngle,float sweepangle,float radii,float outerradii,float thickness)
	{
		double d = ((StartAngle + (sweepangle/2) )* Math.PI/180);
        
        double medianangle = (StartAngle + (sweepangle/2) );
        
        
        float rad =radii;
    	
		   double x = 0,y=0;
		   
		   if(medianangle<=0||medianangle>=270)
	     		
	     	{
			   		//rad = radii+thickness/2;
	     		
			   		x = (float) (coordbounds.centerX()-
	         			(rad * Math.cos(d)));
	         	
	         	
	         	    y = (float) (coordbounds.centerY()-
	         		   ((rad)* Math.sin(d)));
	     	}
		   
		    if(medianangle>=0||medianangle<=90)
	     		
	     	{
			   		//rad = radii+(thickness/2);
	     		
			   		x = (float) (coordbounds.centerX()-
	         			(rad * Math.cos(d)));
	         	
	         	
	         	    y = (float) (coordbounds.centerY()-
	         		   ((rad)* Math.sin(d)));
	     	}
		   
		   
		   if(medianangle>=90||medianangle<=270)
	     		
	     	{
			   		//rad =radii+(thickness/2);
	     		
			   		x = (float) (coordbounds.centerX()+
	         			(rad * Math.cos(d)));
	         	
	         	
	         	    y = (float) (coordbounds.centerY()+
	         		   ((rad)* Math.sin(d)));
	     	}
		   
		   
		   
		   
           /*double y = (float) (coordbounds.centerY()+
        		   ((radii +(thickness/2))* Math.sin(d)));*/
           
           
           float [] pos = new float[2];
           
          
           /*	double x = (float) (coordbounds.centerX()+
           			((radii +(thickness/2) )* Math.cos(d)));*/
           	pos[0]=(float)x;
           	pos[1]=(float)y;
           	
           return pos;
		
	}

	
		private void assignColors()
		{
			System.out.println("NUMSER" + numseries);
			if(assignColors)
			calculateAnglesAndColors();
			else
				getColors();
			
			circPath = new Path[numseries];
			for(int i=0; i<numseries;i++)
			{
				circPath[i]= new Path();
			}
			getSeriesNames();
			/*if(assignSeriesColors)
				calculateSeriesColors();*/
		}
	//***********************************************//
		public void setSeries(List<CategoryMultipleSeries> series,int numseries)
		{
			this.series=series;
			this.numseries=numseries;
			
			
		
			
		}
		
		private void getColors()
		{
			
			
			List<String> ser = new ArrayList<String>();
			for(int m=0;m<series.size();m++)
			{
				ser.add(series.get(m).getXVal());
			
			}
			
			Set<String> uniqueCat = new HashSet<String>(ser);	
			
			categoryUniq = uniqueCat.toArray(new String[uniqueCat.size()]);
			
			ColorCode colors = new ColorCode();
			int [] setcolors =new int[categoryUniq.length];
			
			
			mSum = new double[numseries];
			
			for(int j=0; j<numseries;j++)
			{
				double sum =0;
				
				System.out.println("SUMS VALUE" + sum +"NUMSERIES" + j);
				
				
				for(int i=0;i<series.size();i++)
				{
					if(series.get(i).getSeries()==(j+1))
					{
						System.out.println("SUM CREATE" + mSum[j]);
						sum= sum+series.get(i).getYVal();
						
					}		
					
					
				}
				
				System.out.println("SUM FULL" + sum);
				
				
				mSum[j] = sum;
				
				System.out.println("SUM FULL ARRAY " + mSum[j]);
			}
			
			
			Map<String,Integer> cats = new HashMap<String, Integer>();
			for(int j=0;j<categoryUniq.length;j++)
			{
				for(int m=0; m < series.size();m++)
				{
					if(series.get(m).getXVal().equals(categoryUniq[j]))
					{
						cats.put(categoryUniq[j],series.get(m).getColor());
					}
				}
				
			
			}
			
			this.uniqcolors=cats;
			int j=0;
			
			for (CategoryMultipleSeries it: series)
			{
				
				for(int i=0; i< numseries;i++)
				{
					if(it.getSeries()==(i+1))
						
					{
							
						float percent = (float)((Math.abs(it.getYVal())/mSum[i])*100);
						
						float angle = (float)((Math.abs(it.getYVal())/mSum[i])*360f);
						
				
						
						it.setPercentage(percent);
						
						it.setAngle(angle);
							
					}
					
					
					
					
				}
				
				j++;
				
			}
			
			
		}
		
		private void calculateSeriesColors() {
			
				
			int[] colors = new int[numseries];
			ColorCode sercolor = new ColorCode();
			if(useSeriesPrimary)
				colors = sercolor.setPrimaryColor(numseries,20,0.8f,0.9f);
			else
				colors = sercolor.setHSVAccent(accentprimarycolor,numseries,0.3f,1.0f, 60);
			for(int i=0; i< colors.length;i++)
			{
				seriescolor.put(seriesname[i], colors[i]);
				
			}
		}




		public void setSeriesColors(Map<String,Integer> seriescolor)
		{
			this.seriescolor=seriescolor;
			
		}
		
		public Map<String,Integer> getSeriesColors()
		{
			return seriescolor;
		}
		
		public Integer getSeriesColor(String seriesname)
		{
			return seriescolor.get(seriesname);
		}
		
		private void calculateAnglesAndColors() {
			
				List<String> ser = new ArrayList<String>();
				for(int m=0;m<series.size();m++)
				{
					ser.add(series.get(m).getXVal());
				
				}
				
			Set<String> uniqueCat = new HashSet<String>(ser);	
			
			categoryUniq = uniqueCat.toArray(new String[uniqueCat.size()]);
			
			ColorCode colors = new ColorCode();
			int [] setcolors =new int[categoryUniq.length];
			
			
			if(useSeriesPrimary)
				setcolors = colors.setPrimaryColor(categoryUniq.length,30,0.8f,0.9f);
			else
				setcolors = colors.setHSVAccent(accentprimarycolor,categoryUniq.length,0.4f,0.8f, 20);
			
			
			
			System.out.println("NUMSERIES" + numseries);
			
			mSum = new double[numseries];
			
			for(int j=0; j<numseries;j++)
			{
				double sum =0;
				
				System.out.println("SUMS VALUE" + sum +"NUMSERIES" + j);
				
				
				for(int i=0;i<series.size();i++)
				{
					if(series.get(i).getSeries()==(j+1))
					{
						System.out.println("SUM CREATE" + mSum[j]);
						sum= sum+series.get(i).getYVal();
						
					}		
					
					
				}
				
				System.out.println("SUM FULL" + sum);
				
				
				mSum[j] = sum;
				
				System.out.println("SUM FULL ARRAY " + mSum[j]);
			}
			
			Map<String,Integer> cats = new HashMap<String, Integer>();
			for(int j=0;j<categoryUniq.length;j++)
			{
				
				cats.put(categoryUniq[j],setcolors[j]);
			
			}
			
			
			this.uniqcolors=cats;
			int j=0;
			
			for (CategoryMultipleSeries it: series)
			{
				
				for(int i=0; i< numseries;i++)
				{
					if(it.getSeries()==(i+1))
						
					{
						
						
						
						
						float percent = (float)((Math.abs(it.getYVal())/mSum[i])*100);
						
						float angle = (float)((Math.abs(it.getYVal())/mSum[i])*360f);
						
				
						
						it.setPercentage(percent);
						
						it.setAngle(angle);
						
						
						if(cats.containsKey(it.getXVal()))
							it.setColor(cats.get(it.getXVal()));
							
						
					}
					
					
					
					
				}
				
				j++;
				
			}
			
			
			System.out.println("SUM1" + mSum[0] + "SUM2" + mSum[1]);
		}




		public List<CategoryMultipleSeries> getSeries()
		{
			return series;
		}
		
		
		//*************************************************************//


	private void getRectangles(Canvas canvas) {
		
		
		float radii = (diameter/2)/(numseries+1);
		
		
		//float radii = (diameter/2)/(numseries);
		
		System.out.println("DIAMETER" + diameter+"Radii" + radii);
		
		concentrics = new RectF[numseries];
		  
//		innerPaint.setColor(Color.BLACK);
//		canvas.drawCircle(coordbounds.centerX(),
//				coordbounds.centerY(),radii, innerPaint);
		
		
		System.out.println("CONCENTRIC X" + coordbounds.centerX()+"CONCENTRIC Y " + coordbounds.centerY());
		
		for(int i=1; i<=numseries; i++)
		{
			//concentrics[i] = new RectF(0,0,(i+1)*radii,(i+1)*radii);
			
			concentrics[i-1] = new RectF(coordbounds.centerX()-(i)*radii,coordbounds.centerY()-(i)*radii,
					coordbounds.centerX()+(i)*radii,coordbounds.centerY()+(i)*radii);
			
			
			
			
			System.out.println("CON" + concentrics[i-1]);
			
				
		}
		
		
		
		
	}
	
	 private CategoryMultipleSeries whereIsInDoughnut(float x, float y) {
			
		 CategoryMultipleSeries  chosen =null;
		 float hyp = (float) (Math.pow((x-coordbounds.centerX()),2) + 
				 Math.pow((y-coordbounds.centerY()),2)) ;
		
		 if(Math.sqrt(hyp)<=diameter/2)
		 {
			 float diffx = x- coordbounds.centerX();
			 float diffy = -(y- coordbounds.centerY());
			 double rad = Math.atan2(diffy, diffx);
			 if(rad<0)
			 {
				 rad = Math.abs(rad);
			 }
			 else
			 {
				 rad = 2* Math.PI-rad;
			 }
			 
			 double degree =Math.toDegrees(rad);
			
			 
			 System.out.println("HYP"+ Math.sqrt(hyp) + "DEGREE"+ degree);
			 
			 for(CategoryMultipleSeries item : series)
			 {
				 System.out.println("START ANGLE" + item.getStartAngle()+"END ANGLE"
						 + item.getEndAngle()
						);
				 
				 
				 if(degree>= item.getStartAngle()& degree <= item.getEndAngle()& 
						 Math.sqrt(hyp)<=item.getOuterRadii() &   Math.sqrt(hyp)>=item.getInnerRadii()
						)
				 { 
					 System.out.println("HYP"+ Math.sqrt(hyp));
					 chosen = item;
				 
				 }
				 
			 }
		 }
		 
		 
		return chosen;
	}
	
	 public Selected getChosenPoint()
	    {
		 	Selected  selected = new Selected();
		 	chosenitem = whereIsInDoughnut((float)xpoint,(float)ypoint);
		 
		 	if(chosenitem!=null)
		 	{
	    	
	    	
	    	selected.setSelected(chosenitem.getXVal());
	    	selected.setValue(chosenitem.getYVal());
	    	selected.setAnchorX((float)xpoint);
	    	selected.setAnchorY((float)ypoint);
	    	
	    	System.out.println("CHOSENITEM" + chosenitem.getXVal());
	    	
	    	Toast toast = Toast.makeText(context, chosenitem.getXVal()+"-"+Double.toString(chosenitem.getYVal()), Toast.LENGTH_SHORT);
	    	toast.setGravity(Gravity.TOP|Gravity.LEFT,(int)(boxbounds.left+chosenitem.getXTextOffset()),
	    			(int)(boxbounds.top+chosenitem.getYTextOffset()));
	    	toast.show();
	    	;
	    	
		 	}
	    	
	    	return selected;
	    }

}
