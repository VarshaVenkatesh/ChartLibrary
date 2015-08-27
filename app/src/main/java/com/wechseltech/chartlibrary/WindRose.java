
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
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.Toast;

public class WindRose extends ChartView{

	int mLabelWidth,
	mLabelHeight;
	String[] chosenitem;
	Context context;
	Map<String[],Double[]> anglevalues=
			new HashMap<String[],Double[]>();
			
	Map<String[],Double[]> posvalues=
					new HashMap<String[],Double[]>();
	Map<String[],Double> binnedvalues;
	Map<String,Double> sumvalues;
	float diameter;
	
	RectF coordbounds,
	boxbounds,legendBox,Viewport;
	List<Integer> exponent;
	String [] unique;
	float xpoint,ypoint;
	Paint gPaint,dPaint,
	tPaint,lPaint,legendPaint;
	private float mScaleFactor = 1.f;
	
	float mPosX,mPosY,diffX=0,diffY=0;
	
	Double [] values,sums,concat ;
	
	
	//double[] angles;
	public Point ScrollableSurface = new Point();
	
	 public boolean mEdgeEffectTopActive;
	 public boolean mEdgeEffectBottomActive;
	 public boolean mEdgeEffectLeftActive;
	 public boolean mEdgeEffectRightActive;
	 
	 
	 public EdgeEffectCompat mEdgeEffectTop;
	 public EdgeEffectCompat mEdgeEffectBottom;
	 public EdgeEffectCompat mEdgeEffectLeft;
	 public EdgeEffectCompat mEdgeEffectRight;
	 
	 public ScaleGestureDetector mScaleGestureDetector=null;
	 public GestureDetectorCompat mGestureDetector=null;
	
	float AXIS_H_MAX,
	AXIS_H_MIN,
	AXIS_V_MAX,AXIS_V_MIN;
	
	
	String[] seriesname;
	AxisStops hstops;
	boolean invalidate =false;
	
	int drawBoxSize;
	int min =0;
	
	 Map<String,Double> angles;
	double hInterval;
	int hRTicks;
	float verticesoffset;
	Map<String,RectF> seriesrects
	;
	Map<String,Integer> seriescolor;
	double innerradii,outerradii;
	boolean showLegend;
	
	List<CategoryItem> series;
	
	
	public WindRose(Context context) {
		super(context);
		
		System.out.println("INIT C");
		this.context=context;
		
		mScaleGestureDetector = new ScaleGestureDetector(context,mScaleGestureListener);
		
		
		
	}
	
	
	
	
	public WindRose(Context context,AttributeSet attrs) {
		super(context,attrs);
		System.out.println("INIT At");
		this.context=context;
		
		
		
		
		
		mScaleGestureDetector = new ScaleGestureDetector(context,mScaleGestureListener);
		
		mEdgeEffectTop = new EdgeEffectCompat(context);
		mEdgeEffectLeft = new EdgeEffectCompat(context);
		mEdgeEffectRight = new EdgeEffectCompat(context);
		mEdgeEffectBottom = new EdgeEffectCompat(context);
		
		
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.Charts,0,0);
		
		try
		{
			
			
			mLabelSeparation = a.getDimension(R.styleable.Charts_labelSeparation, 8);
			mTextSize = a.getDimension(R.styleable.Charts_textSize, 12);
			textcolor = a.getColor(R.styleable.Charts_textColor, Color.BLACK);
			gridcolor = a.getColor(R.styleable.Charts_gridColor, Color.GRAY);
			legendSize= a.getDimension(R.styleable.Charts_legendBoxSize,30);
			accentprimarycolor = a.getColor(R.styleable.Charts_accentprimaryColor, Color.BLACK);
			assignSeriesColors = a.getBoolean(R.styleable.Charts_assignSeriesColors, true);
			drawSpace = a.getDimension(R.styleable.Charts_drawSpace, 3);
			useSeriesPrimary =a.getBoolean(R.styleable.Charts_useSeriesPrimary, true);
			showLegend=a.getBoolean(R.styleable.Charts_showLegend, true);
			
			
			
			
		}
		
		finally
		{
			
			a.recycle();
		}
		
		
	}
	
	
	
	
	public WindRose(Context context,AttributeSet attrs,int defStyle) {
		
		
		super(context,attrs,defStyle);
		System.out.println("INIT DF");
		
		this.context=context;
		
		mEdgeEffectTop = new EdgeEffectCompat(context);
		mEdgeEffectLeft = new EdgeEffectCompat(context);
		mEdgeEffectRight = new EdgeEffectCompat(context);
		mEdgeEffectBottom = new EdgeEffectCompat(context);
		
		
		mScaleGestureDetector = new ScaleGestureDetector(context,mScaleGestureListener);
		
		
		TypedArray a = context.getTheme().
				obtainStyledAttributes(attrs, R.styleable.Charts, defStyle, defStyle);
		
		try
		{
			
			mLabelSeparation = a.getDimension(R.styleable.Charts_labelSeparation, 8);
			mTextSize = a.getDimension(R.styleable.Charts_textSize, 12);
			textcolor = a.getColor(R.styleable.Charts_textColor, Color.BLACK);
			legendSize= a.getDimension(R.styleable.Charts_legendBoxSize, 30);
			
			gridcolor = a.getColor(R.styleable.Charts_gridColor, Color.GRAY);
			drawSpace = a.getDimension(R.styleable.Charts_drawSpace, 3);
			accentprimarycolor = a.getColor(R.styleable.Charts_accentprimaryColor, Color.BLACK);
			assignSeriesColors = a.getBoolean(R.styleable.Charts_assignSeriesColors, true);
			showLegend=a.getBoolean(R.styleable.Charts_showLegend, true);
			useSeriesPrimary =a.getBoolean(R.styleable.Charts_useSeriesPrimary, true);
			
			
			
		}
		
		finally
		{
			
			a.recycle();
		}
		
		
		
	}
	
	
	private void init()
{
	 hstops  = new AxisStops();
	
	 tPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
     tPaint.setColor(textcolor);
     tPaint.setTextSize(mTextSize);
     tPaint.setTextAlign(Paint.Align.CENTER);
     
     
     gPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
     gPaint.setStyle(Paint.Style.STROKE);
     gPaint.setStrokeWidth(2);
     gPaint.setColor(gridcolor);
     
     lPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
     lPaint.setStyle(Paint.Style.FILL_AND_STROKE);
     
     

     dPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
     dPaint.setStyle(Paint.Style.FILL_AND_STROKE);
     //dPaint.setStyle(Paint.Style.STROKE);
     
     
     legendPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
     legendPaint.setColor(textcolor);
     legendPaint.setTextSize(mTextSize);
     legendPaint.setTextAlign(Paint.Align.LEFT);
     
    
     

     mLabelHeight =(int) Math.abs(tPaint.getFontMetrics().top);
        
     drawBoxSize = mLabelHeight;
        
   
     mLabelWidth = (int) Math.abs(tPaint.measureText("000000"));
}

	
	public void setSeries(List<CategoryItem> series)
	{
		this.series=series;
	}
	@Override 
	 public void onDraw(Canvas canvas)
	 
	 {
		super.onDraw(canvas);
		
		canvas.save();
		
		canvas.translate(mPosX, mPosY);
	    canvas.scale(mScaleFactor, mScaleFactor);
		
		drawGrid(canvas);
		
		
	
		drawData(canvas);
		
		drawText(canvas);
		
		
		
		
		

		drawLegend(canvas);

		
		
		
		
		canvas.restore();
		
		
		

	 
	 }
	 
	



	public void drawLegend(Canvas canvas) {

        System.out.println("DRAW LEGEND");

        int [] labelwidths = new int[seriesname.length];
        int row=1;
        int a=0;
        float lastleft=0,top=0,bottom=0;


        for (int i=0; i<labelwidths.length; i++)

        {
            labelwidths[i]= (int) Math.abs(legendPaint.measureText(seriesname[i]+"0"));

        }




		for (int i=0; i<seriesname.length; i++)

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




            legendPaint.setColor(seriescolor.get(seriesname[i]));

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





            canvas.drawRect(left,top,right,
                    bottom, legendPaint);

            System.out.println(seriesname[i]);
            canvas.drawText(seriesname[i],right+drawSpace,
                    bottom, legendPaint);



		}



	}




	private float getCentre(double value,double startpos)
	{
			
		
		float pos = (float) (startpos + (outerradii-innerradii)*
				(float)(value-hstops.pointstops[(int)hstops.midpoint])/
				(hstops.pointstops[hstops.pointstops.length-1]
						-hstops.pointstops[(int)hstops.midpoint]));
				
				System.out.println("HSTOP" + (hstops.pointstops[hstops.pointstops.length-1]
						-hstops.pointstops[(int)hstops.midpoint]));
				System.out.println("POSITION" + pos);
				return pos;
	}
	
	private void drawData(Canvas canvas) {
		
		
			tPaint.setTextAlign(Paint.Align.CENTER);
			canvas.drawText(textdesc,coordbounds.centerX(),boxbounds.top+2*mLabelSeparation, tPaint);
			
			tPaint.setTextAlign(Paint.Align.LEFT);
		
		  int nstops = hstops.axisstops.length/2;
		  
		  double radii = (diameter/2)/nstops;
		  
		  innerradii = radii;
		  
		  outerradii = nstops*radii;
		  
		  //float space = (float) ((nstops*radii)-(nstops-1)*radii);
		  String[][] keys = binnedvalues.keySet().toArray(new 
				  String[binnedvalues.size()][2]);
		  for(int m=0; m<binnedvalues.size();m++)
		  {
		  }
		  
			 
			  for(int j=0; j< unique.length;j++)
			  {
				  
				  double startpos = innerradii;
				  
				  for(int i=0; i<seriesname.length;i++)
				  {
					  dPaint.setColor(seriescolor.get(seriesname[i]));
					  
					  String [] data = new String[2];
					  
					  data[0] = unique[j];
					  data[1]= seriesname[i]; 
					  
					  Double val=0.0;
				  
				  
					  for(int x=0;x<keys.length;x++)
					  {
						  if(keys[x][0]==unique[j]&keys[x][1]==seriesname[i])
						  {
							  System.out.println("ARRAY" + data[0]+data[1]+ binnedvalues.get(keys[x]));
							  val= binnedvalues.get(keys[x]);
							  
						  }
					  }
				  
				 
				  
				  
				  
				
				  
				  float pos = getCentre(val,startpos);
				  
				  RectF seriesrect = new RectF();
						
				  float space = (float) Math.abs(startpos -pos);
				  
				  
				  
				  seriesrect.set((float)coordbounds.centerX()-(pos-space/2),
						  (float)coordbounds.centerY()-(pos-space/2),(float)coordbounds.centerX()+(pos-space/2),
						 (float) coordbounds.centerY()+ (pos-space/2));
				  
				  
				  
				  
				  dPaint.setStrokeWidth(space);
				  
				 
				  double degree= (angles.get(unique[j])*180)/Math.PI;
				
				  System.out.println("DEG" + degree);
				  canvas.drawArc(seriesrect,(float)degree,
							20 ,false, dPaint);
				  
				  System.out.println("STARTPOS"+ startpos);
				  System.out.println("POS"+ pos);
				  anglevalues.put(new String[]{unique[j],seriesname[i]},new Double[]{degree,degree+20});
				  posvalues.put(new String[]{unique[j],seriesname[i]},new Double[]{startpos,(double) pos});
				 
				  
				  startpos=pos;
				  
			  }
			  
			  
		  }
		  
		  
			
		  
		
		
		
	}


	private void drawText(Canvas canvas) {
		
		
		/*//Draw Category
		 for(int i=0; i<unique.length;i++)
		 {
			 drawAngle(canvas,angles[i],unique[i]);
		 }*/
		
		String[] labels = angles.keySet().toArray(new String[angles.size()]);
		
		System.out.println("ANGLE SIZE" + angles.size());
		
		//double rad = (10*Math.PI)/180;
		
		 for(int i=0; i<angles.size();i++)
		 {
			 //drawAngle(canvas,angles[i],unique[i]);
			drawAngle(canvas,(angles.get(labels[i])),labels[i]);
			System.out.println("ANGLE" + angles.get(labels[i])+"LABEL" + labels[i]);
		 }
		 
		 drawNumbers(canvas,hstops.pointstops.length/2);
		
	}

	
private void drawNumbers(Canvas canvas,int nstops) {
		
	 
	  double radii = (diameter/2)/nstops;
	  
	  
	  double tradii = (diameter/2)/(nstops/2);
	  
	  
	  System.out.println("DIAMETER"+ diameter);
	  

	  System.out.println("RADII"+ radii +"STOPS" + nstops);
	  
	  exponent = new ArrayList<Integer>();
		
		
		
		for(int i=1; i< nstops;i++ )
		{	
			 
			 
			 
			 CalculateExp(nstops+(i-1),tradii);

			  
		 }
		
		Integer [] exps = exponent.toArray(new Integer[exponent.size()]);
		Arrays.sort(exps);
		if(exps.length>0)
		min = exps[0];
		
		double angle = 45*Math.PI/180;
		
		String [] keys = angles.keySet().toArray(new String[angles.size()]);
		
		for(int i=2; i<=nstops;i++ )
		{	
			 float x = (float) (coordbounds.centerX()+ (radii*(i))*Math.cos(angle));
			 float y = (float) (coordbounds.centerY()+ (radii*(i))*Math.sin(angle));
			 
			 
			 if(i%2==0 &i!=nstops)
			  {
				  
			  }
			 else
			 DrawTextNum(canvas,x,y,nstops+(i-1),radii);

			  
		 }
		
		
		if(min!=0)
		{
			tPaint.setTextAlign(Paint.Align.CENTER);
			canvas.drawText("x10", coordbounds.centerX(),
					coordbounds.centerY(), tPaint);
			canvas.drawText(Integer.toString(min), coordbounds.centerX()+(tPaint.measureText("x10")/2),
				coordbounds.centerY()+tPaint.ascent(), tPaint);
		}
	
	}

	
	
		private void CalculateExp(int i, double radii) {
			
			String[] outstring = formatfloat((float)hstops.pointstops[i],
					hInterval,tPaint,radii);
			
			
			if(outstring[1]!=null)
			{
				exponent.add(Integer.parseInt(outstring[1]));
				
			}
			
		}



	public void DrawTextNum(Canvas canvas,float x,float y,int pos,double radii)
	{
		
	
		 tPaint.setTextAlign(Paint.Align.LEFT);
		float value = (float) (hstops.pointstops[pos]/Math.pow(10,min));
	 	
		System.out.println("VAL" + value +"MIN" + min);
		String outstring = formatfloatValue(value,
				hInterval,tPaint,radii);
		System.out.println("OUTSTRING" + outstring);
		
		if(x>=coordbounds.left & y<=coordbounds.bottom)
		{
			System.out.println(hstops.pointstops[pos]);
			
			canvas.drawText(outstring,0,outstring.length(), 
							 x,y, tPaint);

		}	 
		 	
		   

	}
	

	
	private void drawAngle(Canvas canvas,double d,String label) {
		
		
		
			System.out.println("ANGLEDRAW" + d);
			
			double angle = ((d*180)/Math.PI)+10;
			
			System.out.println("CONV" + angle);
			
			double dconv = angle * Math.PI/180;
			
			
			if(angle>=0 && angle<=90)
			{
				System.out.println(">=0");
				tPaint.setTextAlign(Paint.Align.LEFT);
				float x = (float) (coordbounds.centerX()+(coordbounds.width()/2) * Math.cos(dconv))
						+mLabelSeparation;
				float y = (float) (coordbounds.centerY()+(coordbounds.height()/2)* Math.sin(dconv));
				canvas.drawText(label,x,y, tPaint);
			}
			
			else if(angle>90 && angle<=180)
			{
				System.out.println(">=90");
				tPaint.setTextAlign(Paint.Align.RIGHT);
				float x = (float) (coordbounds.centerX()+(coordbounds.width()/2) * Math.cos(dconv))
						-mLabelSeparation;
				float y = (float) (coordbounds.centerY()+(coordbounds.height()/2)* Math.sin(dconv));
				canvas.drawText(label,x,y, tPaint);
			}
			
			else if(angle>180 && angle<=270)
			{
				System.out.println(">=180");
				tPaint.setTextAlign(Paint.Align.RIGHT);
				float x = (float) (coordbounds.centerX()+(coordbounds.width()/2) * Math.cos(dconv))
						-mLabelSeparation;
				float y = (float) (coordbounds.centerY()+(coordbounds.height()/2)* Math.sin(dconv));
				canvas.drawText(label,x,y, tPaint);
			}
			
			 else if(angle>270 && angle<=360)
			 {
				
				 	tPaint.setTextAlign(Paint.Align.LEFT);
					float x = (float) (coordbounds.centerX()+(coordbounds.width()/2) * Math.cos(dconv))
							+mLabelSeparation;
					float y = (float) (coordbounds.centerY()+(coordbounds.height()/2)* Math.sin(dconv));
					canvas.drawText(label,x,y, tPaint);
			 }
			
			
			
//		   if(d<=90||d>=270)
//	     		
//	     	{
//			 	tPaint.setTextAlign(Paint.Align.LEFT);
//	         	float x = (float) (coordbounds.centerX()+(coordbounds.width()/2 * Math.cos(d)));
//	         	float y = (float) (coordbounds.centerY()+(coordbounds.height()/2)* Math.sin(d));
//	         	System.out.println("x"+x+"y" +y+label);
//	         	canvas.drawText(label,x,y, tPaint);
//	     		
//			   	
//	     	}
//		   
//		   
//		   if(d>=90||d<=270)
//	     		
//	     	{
//	     		
//			   	tPaint.setTextAlign(Paint.Align.RIGHT);
//	         	float x = (float) (coordbounds.centerX()-(coordbounds.width()/2 * Math.cos(d)));
//	         	float y = (float) (coordbounds.centerY()-(coordbounds.height()/2)* Math.sin(d));
//	         	System.out.println("x"+x+"y" +y+label);
//	         	canvas.drawText(label,x,y, tPaint);
//	     	}
		
	       
       
		
	}

	private void drawGrid(Canvas canvas) {
		 
		  int nstops = hstops.axisstops.length/2;
		  double radii = (diameter/2)/nstops;
		  
		  float [] posstops = new float[nstops];
		  
		  
		  hstops.posstops=posstops;
		  for(int i=1; i<=nstops;i++ )
		  {
			  if(i%2==0 &i!=nstops)
			  {
				  
			  }
			  else
			  canvas.drawCircle(coordbounds.centerX(),coordbounds.centerY(),
					  (float) (i*radii), gPaint);	
			  posstops[i-1]= (float) ((i)*radii);
			  
			  
		  }
		  
		  
		 
	}
	
	
	
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		
		float xpad = (float)(getPaddingLeft()+ getPaddingRight());
		float ypad = (float)(getPaddingTop() +  getPaddingBottom());
		
		init();
		
		float coww =0.0f,cohh=0.0f,coll =0.0f;
		
		coww = (float) w-xpad
				-2*mLabelWidth-2*mLabelSeparation;
		cohh = (float) h-ypad-4*mLabelHeight-
				(legendSize+2*mLabelSeparation)-mLabelHeight-2*mLabelSeparation;
				

		float bxww = (float) w-xpad;
		float bxhh = (float) h-ypad;
		
		
		boxbounds = new RectF(getPaddingLeft(),getPaddingTop(),
				getPaddingLeft()+bxww,getPaddingTop()+bxhh);
		
		coordbounds = new RectF(0.0f,0.0f,
				coww,cohh);
		
		coll = bxhh-legendSize-mLabelSeparation;
		
		diameter = Math.min(coww, cohh);
	    coordbounds = new RectF(
	                0.0f,
	                0.0f,
	                diameter,
	                diameter);
	     float xoffset = mLabelWidth+mLabelSeparation;
	     
	     float toffset = mLabelHeight+mLabelSeparation;
		    
	     
	     
	     //coordbounds.offsetTo(getPaddingLeft()+ xoffset, getPaddingTop()+mLabelSeparation);
	     coordbounds.offsetTo(boxbounds.centerX()-(diameter/2), boxbounds.centerY()-(diameter/2));
	     
	     legendBox = new RectF(0.0f,0.0f,coww,legendSize );
	   	 legendBox.offset(getPaddingLeft()+xoffset,coordbounds.bottom+toffset);
	   	 
	   	 getXYTicks();
	   	 
	 	System.out.println("COHH INIT" +cohh+"DIA INIT" + diameter);
		
	}
	
	
	private void getXYTicks() {
		
		
		 getCategory();
		 verticesoffset = 360/unique.length;
		 
		 /*for(int i=0;i<unique.length;i++)
		 {
			 angles[i]= ((i*verticesoffset)* Math.PI/180);
			 
		 }*/
		 
		 for(int i=0;i<unique.length;i++)
		 {
			
			 
			 angles.put(unique[i],((i*verticesoffset)* Math.PI/180));
			 
			 
		 }
		  
		 seriesrects= new HashMap<String,RectF>();
		 
		 Map<String[],Double> num = new HashMap<String[],Double>();
		 Map<String,Double> values = new HashMap<String,Double>();
		 
		 
			 
			 for(int m=0;m<unique.length;m++)
			 {
				 
				 double numval =0;
				 
					 
					 
					 for(int j=0; j< series.size();j++)
					 {
						 if(series.get(j).getCategory()==unique[m])
						 {
							 System.out.println(unique[m]);
							 numval=numval+series.get(j).getNumberOf();
							 System.out.println("NUMVAL SUM" + numval);
						 }
					 
					 }
					 
					 
				 
				 
				 System.out.println("NUMVAL" + numval);
				 values.put(unique[m],numval);
				 
			 }
			 
			 
		 
		 
		 for(int n=0; n< seriesname.length;n++)
		 {
			 
			 for(int m=0;m<unique.length;m++)
			 {
				 String [] data = new String[2];
				 double c=0;
				 
				 for(int j=0; j< series.size();j++)
				 {
					 if(series.get(j).getCategory()==unique[m] & 
							 series.get(j).getSeriesName()==seriesname[n] )
					 {
						 c=c+series.get(j).getNumberOf();
					 }
				 
				 }
				 
				 
				 data[0] = unique[m];
				 data[1]= seriesname[n];
				 
				 num.put(data, c);
				 
				 System.out.println(data[0] + data[1]+"VAL" +c);
			 }
			 
			 RectF seriesrect = new RectF();
			 
			 seriesrects.put(seriesname[n], seriesrect);
		 }
		 
		 
		 
		
		 getNumericalStops(num,values);
		 
		
	}


	private void getNumericalStops(Map<String[],Double> binnedvalues,Map<String,Double> sumvalues) {
		
		this.binnedvalues=binnedvalues;
		this.sumvalues=sumvalues;
		values = new Double[binnedvalues.size()];
		values = binnedvalues.values().toArray(new Double[binnedvalues.size()]);
		sums = new Double[sumvalues.size()];
		sums = sumvalues.values().toArray(new Double[sumvalues.size()]);
		concat = new Double[binnedvalues.size()+sumvalues.size()];
		
		
		int a =0;
		for(int i=0;i< binnedvalues.size();i++)
		{
			concat[i] = values[i];
			a++;
		}
		
		for(int i=0;i< sumvalues.size();i++)
		{
			concat[a] = sums[i];
			a++;
		}
		
		for(int i=0;i< concat.length;i++)
		{
			System.out.println("CONCAT"+concat[i]);
		}
		
			
	
		
		/*if(values!=null)
		Arrays.sort(values);*/
		
		if(concat!=null)
			Arrays.sort(concat);
		
		
		for(int i=0;i< concat.length;i++)
		{
			System.out.println("SORTED" + concat[i]);
		}
		
		IntervalStats stats = determineReqTicks
				(concat[concat.length-1],concat[0],concat);
		
		
		hInterval = stats.hinterval;
		hRTicks = stats.hRticks;
		
		
		MinMax Data =computeMaxMinRange(concat,hInterval,stats.hRticks);
		
		System.out.println("GRAPH_MIN"+ Data.graphMin);
		System.out.println("GRAPH_MAX"+ Data.graphMax);
		
		computeStopsNumRose(stats.hRticks,hstops,Data.graphMin,Data.graphMax,hInterval);
	
		
	
		Viewport = new RectF((float)0,
				0,(float)hstops.pointstops.length,hstops.axisstops.length);
		
		AXIS_H_MIN = (float)(hstops.axisstops[0]);
		AXIS_H_MAX = (float)(hstops.axisstops[hstops.axisstops.length-1]+1);
		AXIS_V_MIN = (float)(hstops.axisstops[0]);
		AXIS_V_MAX = (float)(hstops.axisstops[hstops.axisstops.length-1]+1);
		
	}
	
	
	
	public IntervalStats determineReqTicks(
			Double GraphMax, Double GraphMin,Double[] val)
	{
		
		
		  double avgsepH=0;
	
		   IntervalStats stats = determineAvgSeparation(val);
		
		
			
			avgsepH= niceNumeral(stats.xavgdiff);
			
			
			System.out.println("AVGH"+ avgsepH);
			
			
			
			double min = (Math.floor(GraphMin/avgsepH)*avgsepH);
			double max = (Math.ceil(GraphMax/avgsepH)*avgsepH);
			
			double hrange = max -min;
			int hRTicks = (int)Math.ceil(hrange/niceNumeral(avgsepH));

			stats.hRticks = hRTicks;
			
			stats.hinterval=niceNumeral(hrange/(stats.hRticks));
			
			hInterval = stats.hinterval;
			
			System.out.println("H INterval" + stats.hinterval+"HRTICKS"+(stats.hRticks)+"");
			
		
		
			return stats;
		
		
	}

	
	
	
	
	public void computeStopsNumRose(int length,AxisStops stops,
			double graphMin,double graphMax,
			double interval)
	
	{
		
		double[] outstops = new double[2*length+2];
		double[] pointstops = new double[2*length+2];
		
		/*float[] outstops = new float[Llength+Rlength+2];
		float[] pointstops = new float[Llength+Rlength+2];*/
		
		 
		
		 int a=0,c=0,e=0;
		 
		 
		
		 
		 for(double x =(graphMax);x>=(graphMin);x=x-interval)
		 {
			
			
			
			outstops[a] = (float)x;
			
			pointstops[c]=(float)a;
			
			
			a++;
			c++;
			e++;
		 }
		 
		 
		 //outstops[a]=(float)(graphMin-interval);
		 
		 //outstops[a]=(float) (LgraphMin-Linterval);
		 //pointstops[c]=(float)a+1;
		 
		// pointstops[c]=(float)a;
		 
		 
		 //float midpoint = a+1;
		 
		 float midpoint = a;
		 
		 //pointstops[a]=a+1;
		 
		 pointstops[a]=a;
		 
		 
		 
		 
		 
		 //int b=a+2;
		 //int b=a;
		 
		 //int b=a+1;
		
		 int b=a;
		 
		 int d=0;
		
		 
		 for(double x = graphMin; x<=
				 graphMax ;x=
				 x+interval)
		 {
			
			//System.out.println("AXIS STOPS X" + x);
			outstops[b] = (float)x;
		   // rightstops[d]= (float)x;
			//pointstops[b]=(float)b+1;
			pointstops[b]=(float)b;
			
			b++;
			d++;
		 }
		
		 
		 
		 stops.pointstops=outstops;
		 stops.axisstops = pointstops;
		 stops.midpoint=midpoint;
		//Includes the leftmost stop - i.e Viewport.left
		 stops.numstops=b-1;
		 
		 /*for(int y=0; y<outstops.length;y++)
		 {
			 System.out.println("POINTSTOP" + outstops[y]);
			 System.out.println("AXISSTOP" + pointstops[y]);
		 }*/
		 
		 System.out.println("MIDPOINT" + midpoint);
	}
	public void computeStopsNum(int length,AxisStops stops,
			double graphMin,double graphMax,double interval)
	{
		
		
		
		

		double[] outstops = new double[2*length+2];
		double[] pointstops = new double[2*length+2];
		
		
		 int a=0,c=0;
		 
		 
		 for(double x=graphMax+(0.5*interval); x<=(graphMin);x=x-interval)
		 {
			System.out.println("AXIS STOPS X" + x);
			outstops[a] = (float)x;
			pointstops[c]=(float)a+1;
			System.out.println("AXIS STOPS " + outstops[a]);
			a++;
			c++;
		 }
		 
		 
		 outstops[a]=(float) (graphMin-interval);
		 pointstops[c]=(float)a+1;
		 
		 float midpoint = a+1;
		 float midpointvalue = (float) (graphMin-interval);
		 
		 int b=a+2;
		 
		 for(double x = graphMin; x<=
				 (graphMax + (0.5*interval));x=
				 x+interval)
		 {
			System.out.println("AXIS STOPS R" + x);
			outstops[b] = (float)x;
			pointstops[b]=(float)b;
			System.out.println("AXIS STOPS " + outstops[a]);
			b++;
		 }
		 
		 
		 stops.pointstops=outstops;
		 stops.axisstops = pointstops;
		 stops.midpoint=midpoint;
		 stops.midpointvalue=midpointvalue;
		//Includes the leftmost stop - i.e Viewport.left
		 stops.numstops=b-1;
		
	}
	
	public IntervalStats determineAvgSeparation(Double[] val)
	{
		

		
		
		double sumdiff =0;
			
		for(int i=0; i< val.length-1;i++)
		{
			sumdiff = (sumdiff + Math.abs(val[i+1]-val[i]));
			
		}
			
		double avgdiff =sumdiff/(val.length-1);
		
		IntervalStats stats = new IntervalStats();
		stats.xavgdiff = avgdiff;
		
			
		return stats;
	}

	
	public MinMax computeMaxMinRange(Double[] array,double interval,int visTicks)
	{
		

		System.out.println("ARRAY LEN MAX"+ array[array.length-1]);
		System.out.println("ARRAY_LEN_MIN"+ array[0]);
		

		MinMax  values = new MinMax();
		double graphMin = (Math.floor(array[0]/hInterval)*hInterval);
		double graphMax = (Math.ceil(array[array.length-1]/hInterval)*hInterval);
		
		values.graphMax=graphMax;
		values.graphMin=graphMin;
		
		
		return values;
	}
	
	private double niceNumeral(double calrange)
	 
	 {
		 double nf;
		 int exp = (int)Math.floor(Math.log10(calrange));
		 double frac = calrange/Math.pow(10,exp);
		 
		 if(frac<1.5)
			 nf=1;
		 else if(frac<3)
			 nf=2;
		 else if(frac<7)
			 nf=5;
		 else 
			 nf=10;
		 
		 System.out.println("EXP" + nf*Math.pow(10,exp));
		 return nf*Math.pow(10,exp);
		 
	 }
	
	
	
	
	private String[] getCategory()
	
 	{
			 List<String> uni= new ArrayList<String>();
			  
			 
			 for(int i=0; i<series.size();i++)
			 {
			
				uni.add(series.get(i).getCategory());
			 }		
			Set<String> uyVal= new HashSet<String>(uni);
			unique = uyVal.toArray(new String[uyVal.size()]);
			
			
			List<String> uniseries= new ArrayList<String>();
			 
			for(int i=0; i<series.size();i++)
			 {
					
				uniseries.add(series.get(i).getSeriesName());
			 }		
			Set<String> usVal= new HashSet<String>(uniseries);
			seriesname = usVal.toArray(new String[usVal.size()]);
			
			seriescolor = new HashMap<String,Integer>();
			if(assignSeriesColors)
				calculateSeriesColors(seriesname.length,
					useSeriesPrimary,accentprimarycolor,seriescolor);
			else 
				getSeriesColors();
			
			
			//angles = new double[unique.length];
			
			
			angles = new HashMap<String,Double>();
			
			
			
			return unique;
	}
	
	
	private void getSeriesColors() {
		
		for(int i=0; i<seriesname.length;i++)
		 {
			int color=0;
			for(int j=0;j<series.size();j++)
			{
				if(seriesname[i]==series.get(j).getSeriesName())
				{
					color = series.get(j).getSeriesColor();
				}
			}
			
			seriescolor.put(seriesname[i],color);
			
		 }	
		
	}




	public void calculateSeriesColors(int numseries,Boolean useSeriesPrimary,
			int accentprimarycolor,Map<String,Integer> seriescolor) {
		
		
		System.out.println("USE SERIES PRIMARY" + useSeriesPrimary);
		
		int[] colors = new int[numseries];
		ColorCode sercolor = new ColorCode();
		if(useSeriesPrimary)
			colors = sercolor.setPrimaryColor(numseries,60,0.8f,0.9f);
		else
			colors = sercolor.setHSVAccent(accentprimarycolor,numseries,0.6f,1.0f, 20);
		for(int i=1; i<=numseries;i++)
		{
			seriescolor.put(seriesname[i-1], colors[i-1]);
			
		}
		
		for(int j=0; j<series.size();j++)
		{
			
			series.get(j).
			setSeriesColor(seriescolor.get(series.get(j).getSeriesName()));
		}
		
	}







	 private void redrawLegendBox(float coww,int drawBoxSize,int mLabelWidth) {
			
		 
		 
			
			//float coll = (float) boxbounds.height()-mLabelSeparation-(legendSize+(drawBoxSize));
			
			float cohh = (float) boxbounds.height()-4*mLabelHeight-
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
			
			float toffset = mLabelHeight+mLabelSeparation;
		    
			
			legendBox.offset(getPaddingLeft()+xoffset,coordbounds.bottom+toffset);
			
			System.out.println("LG" + legendBox);
			
			invalidate =true;
			
			invalidate();
		}
		
		
	 private String formatfloatValue(float value, double interval,
				Paint tPaint, double nLabelWidth) {
			
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
			
			 String append = "#.";
			 
			 String nodec = "#";
			 String negdec = "##.";
			 
			 for(int i=0;i<numsig;i++)
				 
			 {
				 System.out.println("Calculating Append"+ append);
				 
				 append+="#";
				 
			 }
			 
			 if(value<0)
			 {
				 for(int i=0;i<numsig;i++)
					 
				 {
					 System.out.println("Calculating Append"+ append);
					 
					 negdec+="#";
					 
				 }
			 }
			 
			 System.out.println("APPEND"+ append);
			 
			 System.out.println("FLOATVAL" + value);
			 //TODO Find an alternative toDecimalFormat
			 if(value%1>0)
				 numstring = new DecimalFormat(append).format(value);
			 else if(value%1<0)
			 {
				 numstring = new DecimalFormat(negdec).format(value);
				 
			 }
			 else
				 numstring = new DecimalFormat(nodec).format(value);
			 System.out.println("FLVAL" + numstring);
			return numstring;
		}
	 
	 
	 private String[] formatfloat(float floatvalue, double interval, 
				Paint tPaint,double radii) {
			
			String numstring ;
			int numsig=0;
			if(interval<1)
				
			{
				if(interval!=0)
				numsig =(int)Math.ceil(-Math.log10(interval));
				
			}
			
			 String append = "#.";
			 
			 String nodec = "#";
			 
			 for(int i=0; i<numsig;i++)
				 
				 append +="#";
			 
			 System.out.println("FLOATVAL" + floatvalue);
			 
			 System.out.println(append);
			 //TODO Find an alternative toDecimalFormat
			 if(floatvalue%1>0)
				 numstring = new DecimalFormat(append).format(floatvalue);
			 else
				 numstring = new DecimalFormat(nodec).format(floatvalue);
			 
			 
			 System.out.println("NUMSTR" + numstring);
			  float minLength = tPaint.measureText("0");
			 
			    int numchars = (int) Math.floor(radii/minLength);
			    
			    int trimsig =0;
			    int exp=0;
				if(numstring.length()>numchars)
				{
					
					 if(floatvalue!=0)
							exp =(int) Math.floor(Math.log10
									(floatvalue));
							else
							exp=0;
					 
					 
					float value  = (float)(floatvalue
							/Math.pow(10,exp));
					float sigint = (float) (interval/Math.pow(10,exp));
					if(sigint<1)
					{
						if(sigint!=0)
						trimsig =(int)Math.ceil(-Math.log10(sigint));
						
					}
					
					if(sigint>1)
					{
						if(sigint!=0)
						trimsig =(int)Math.ceil(Math.log10(sigint));
						
						
					}
					
					String pattern = "#.";
					 for(int i=0; i<trimsig;i++)
						 pattern+="#";
					numstring = new DecimalFormat(pattern).format(value);
					
					
				}
			
			String [] strings = new String[2];
			strings[0]=numstring;
			System.out.println("EXP" + exp);
			if(exp!=0)
				strings [1] = Integer.toString(exp);
			return strings;
			
			
		}
		
	 @Override 
	 public boolean onTouchEvent(MotionEvent event)
	 {
		 
		 //mGestureDetector.onTouchEvent(event);
		 
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
	 
	 private String[] whereIsInWindRose(float x, float y) {
			
		 String[] chosen =null;
		 float hyp = (float) (Math.pow((x-coordbounds.centerX()),2) + 
				 Math.pow((y-coordbounds.centerY()),2)) ;
		
		 
		  String[][] keyvalues = posvalues.keySet().toArray(new 
				  String[posvalues.size()][2]);
		  for(int m=0; m<posvalues.size();m++)
		  {
			  System.out.println("KEY VAL" + keyvalues[m][0]+ keyvalues[m][1]);
			  System.out.println("POS FIRST VAL" + posvalues.get(keyvalues[m])[0]+
					  
					  "POS SECOND VAL" + posvalues.get(keyvalues[m])[1]);
		  }
		  
		  
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
			
			 String[][] keys = posvalues.keySet().toArray(new 
					  String[posvalues.size()][2]);
			 String[][] angleskeys = anglevalues.keySet().toArray(new 
					  String[anglevalues.size()][2]);
			 
			 System.out.println("DEGREE"+ degree);
			 
			 System.out.println("HYP"+ Math.sqrt(hyp));
			 
			 for(int i=0; i<anglevalues.size();i++)
			 {
				 
				 
				 if(degree>=anglevalues.get(angleskeys[i])[0] & degree<=anglevalues.get(angleskeys[i])[1])
				 {
					 Boolean pos = false;
					 String [] chosenpos=null;
					 
					 for(int j=0; j<posvalues.size();j++)
					 {
						 if(keys[j][0]== angleskeys[i][0] & keys[j][1]== angleskeys[i][1])
						 {
							 chosenpos = new String[]{keys[j][0],keys[j][1]};
							 System.out.println("CHOSEN POS" + posvalues.get(keys[j])[0]+"CHOSEN"
							 +posvalues.get(keys[j])[1]);
							 
							 	if(posvalues.get(keys[j])[0]>=
									 posvalues.get(keys[j])[1])
								 {
					 				 pos =true;
					 				 System.out.println("POS TRUE");
								 }
								 else
								 {
									 pos=false;
									 System.out.println("POS FALSE");
								 }
								 if(pos)
								 {
									 if(Math.sqrt(hyp)>= posvalues.get(keys[j])[1]& Math.sqrt(hyp) <= posvalues.get(keys[j])[0])
									 {
										chosen = keys[j];
									 }
								 }
								 else
								 {
									 
									 if(Math.sqrt(hyp)>= posvalues.get(keys[j])[0]&Math.sqrt(hyp)<= posvalues.get(keys[j])[1])
									 {
										 System.out.println("POS VALUES SELECTED");
										 chosen = keys[j];
									 }
								 }
						 }
					 }
					 
					 
					 
					 
					 
				 }
				 
			 }
		 }
		 
		 
		 
		return chosen;
	}
	 
	  public void getChosenPoint()
	    {
	    	chosenitem = whereIsInWindRose((float)xpoint,(float)ypoint);
	    	
	    	
	    	
	    	if(chosenitem!=null)
	    	{
	    		String[][] keys = binnedvalues.keySet().toArray(new 
						  String[binnedvalues.size()][2]);
	    	 for(int i=0;i<binnedvalues.size();i++)
	    	 {
	    		 if(keys[i][0]==chosenitem[0] & keys[i][1]==chosenitem[1])
	    		 {
	    			 Toast toast = Toast.makeText(context,Double.toString(binnedvalues.get(keys[i])), Toast.LENGTH_SHORT);
	    		    	toast.setGravity(Gravity.TOP|Gravity.LEFT,(int)(boxbounds.left+ xpoint),
	    		    			(int)(boxbounds.top+ypoint));
	    		    	toast.show();
	    		 }
	    		 
	    	 }
	    	
	    	}
	    	;
	    	
	    	
	    	
	    }
}
