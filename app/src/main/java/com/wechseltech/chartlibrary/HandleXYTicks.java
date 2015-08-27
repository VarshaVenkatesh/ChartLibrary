package com.wechseltech.chartlibrary;



import android.graphics.Paint;
import android.graphics.RectF;

public class HandleXYTicks {

	//OptimizeTicksMultipleSeries tasks;
	int nLabelWidth,subLabelHeight;
	ReturnIntervalsTicks ticks;
	RectF coordbounds;
	float lpad,tpad;
	Paint aPaint;
	float mAxisLabelHeight,mLabelSeparation;
	float mBarWidth
	,mBarSpacing,iBarSpacing;
	float legendSize;
	float mVLabelWidth;
	
	
	public HandleXYTicks(RectF coordbounds,
			float lpad,float tpad,float mAxisLabelHeight, 
			float mLabelSeparation,Paint aPaint,float mBarWidth
			,float mBarSpacing, float iBarSpacing,float legendSize,float mVLabelWidth)
	{
		
		this.coordbounds=coordbounds;
		this.lpad=lpad;
		this.tpad=tpad;
		this.mAxisLabelHeight=mAxisLabelHeight;
		this.mLabelSeparation = mLabelSeparation;
		this.aPaint=aPaint;
		this.mBarWidth=mBarWidth;
		this.mBarSpacing=mBarSpacing;
		this.iBarSpacing=iBarSpacing;
		this.legendSize=legendSize;
		this.mVLabelWidth=mVLabelWidth;
		
		
	}
	
	
	
	
	
	public HandleXYTicks(RectF coordbounds,
			float lpad,float tpad,float mAxisLabelHeight, 
			float mLabelSeparation,Paint aPaint,float legendSize,float mVLabelWidth)
	{
		
		this.coordbounds=coordbounds;
		this.lpad=lpad;
		this.tpad=tpad;
		this.mAxisLabelHeight=mAxisLabelHeight;
		this.mLabelSeparation = mLabelSeparation;
		this.aPaint=aPaint;
		this.legendSize=legendSize;
		this.mVLabelWidth=mVLabelWidth;
		
		
		
	}
	
	public HandleXYTicks(RectF coordbounds,
			float lpad,float tpad,float mAxisLabelHeight, 
			float mLabelSeparation,Paint aPaint,float mVLabelWidth)
	{
		
		this.coordbounds=coordbounds;
		this.lpad=lpad;
		this.tpad=tpad;
		this.mAxisLabelHeight=mAxisLabelHeight;
		this.mLabelSeparation = mLabelSeparation;
		this.aPaint=aPaint;
		this.mVLabelWidth=mVLabelWidth;
		
		
		
		
	}
	
	
	
	
	
	private void getVTicks(float bxh,float axisLabelSpace,OptimizeTicks otasks)
	{
		ticks.isVflipped=otasks.determineNumVTicks(bxh-subLabelHeight-axisLabelSpace);
		ticks.nLabelHeight = otasks.getLabelHeight();
		ticks.nvLabelWidth=ticks.nLabelHeight;
	
	}
	
	private void computeWidthAndHeight(int lwidth,int lheight,float bxh, float coww)
	{
		float cohh = (float) bxh-lheight-
				(2*mAxisLabelHeight+aPaint.ascent()+mLabelSeparation);
		
		ticks.coordbounds = new RectF(0.0f,0.0f,
				coww,cohh);
		
		float xoffset = lwidth+(2*mAxisLabelHeight+aPaint.ascent());
		ticks.coordbounds.offset(lpad+xoffset,tpad);
	}
	
	private void computeWidthAndHeightMultiple(int lwidth,int lheight,float bxh, float bxw)
	{

		
			System.out.println("NLABELWIDTH" + lwidth + "NLABELHEIGHT" + lheight+"BXH" + bxh
				
				+"BXW" + bxw +"NLABEL" + ticks.nLabelWidth+ "SUBLABEL"+ticks.subLabelHeight);
		
		
		
		float coww = (float) bxw-lwidth-2*mAxisLabelHeight-4*mLabelSeparation-(-aPaint.ascent());
		
		float cohh = (float) bxh-lheight-
				(2*mAxisLabelHeight)-(-aPaint.ascent())-(legendSize) - 3 * mLabelSeparation;
		
		
		
		ticks.coordbounds = new RectF(0.0f,0.0f,
				coww,cohh);
		
	
		
		float xoffset = lwidth+(2*mAxisLabelHeight)+3*mLabelSeparation-(-aPaint.ascent());
		ticks.coordbounds.offset(lpad+xoffset,tpad+mLabelSeparation);
		
		System.out.println("COORD"+ ticks.coordbounds);
		
		ticks.legendBox = new RectF(0.0f,0.0f,coww,legendSize);
		
		System.out.println("CWBB"+ ticks.coordbounds.bottom+"COHH" + cohh +"tpad" + (tpad+mLabelSeparation));
		
		
		ticks.legendBox.offset(lpad+xoffset,ticks.coordbounds.bottom+lheight+ mLabelSeparation+(mAxisLabelHeight-aPaint.ascent()));
		
		System.out.println("LGTOP"+ ticks.legendBox.top);
	}
	
	
	
	private void computeWidthAndHeightBar(int lwidth,int lheight,float bxh, float bxw)
	{
		
	
		
		System.out.println("NLABELWIDTH" + lwidth + "NLABELHEIGHT" + lheight+"BXH" + bxh
				
				+"BXW" + bxw);
		
		
		//float coww = (float) bxw-lwidth-(2*mAxisLabelHeight+aPaint.ascent()+2*mLabelSeparation);
		
		
		float coww = (float) bxw-lwidth-2*mAxisLabelHeight-4*mLabelSeparation-(-aPaint.ascent());
		
//		float cohh = (float) bxh-lheight-
//				(2*mAxisLabelHeight)-(-aPaint.ascent())-(legendSize) - 3 * mLabelSeparation;
		
		
		float cohh = (float) bxh-lheight-(2*mAxisLabelHeight)-
				(-aPaint.ascent())-(legendSize) - 3 * mLabelSeparation;
		
		ticks.coordbounds = new RectF(0.0f,0.0f,
				coww,cohh);
		
		float xoffset = lwidth+(2*mAxisLabelHeight)+3*mLabelSeparation-(-aPaint.ascent());
		
		//float xoffset = lwidth+(2*mAxisLabelHeight+aPaint.ascent())+mLabelSeparation;
		
		System.out.println("XOFFSET" + xoffset + "LWIDTH" + lwidth + "AXIS LABEL" + mAxisLabelHeight
				+ "APAINT" + aPaint.ascent() );
		ticks.coordbounds.offset(lpad+xoffset,tpad+mLabelSeparation);
		
		ticks.legendBox = new RectF(0.0f,0.0f,coww,legendSize);
		
		System.out.println("CWBB"+ ticks.coordbounds.bottom+ "CWTT" + ticks.coordbounds.top+
				"LHT"+ lheight+"MAXIS" + mAxisLabelHeight+"APAINT" + aPaint.ascent()
				+"mLabelSeparation"+ mLabelSeparation 
				+"tpad"+ tpad +"COWW" + coww + "COHH"+cohh +"LSIZE"+ legendSize);
		
		ticks.legendBox.offset(lpad+xoffset,ticks.
				coordbounds.bottom+lheight+mLabelSeparation+
				(mAxisLabelHeight-aPaint.ascent()));
		
		System.out.println("LGBOX TOP" + ticks.legendBox.top );
		
		System.out.println("COLL" + ticks.coordbounds.left + "COWW"+ticks.coordbounds.width()+"CORR" + ticks.coordbounds.right);
		
		
	}
	
	private void computeWidthAndHeightBarMultiple(int lwidth,int lheight,float bxh, float bxw)
	{
		
		
		
		System.out.println("NLABELWIDTH" + lwidth + "NLABELHEIGHT" + lheight+"BXH" + bxh);
		
		
		//float coww = (float) bxw-lwidth-(2*mAxisLabelHeight+aPaint.ascent()+2*mLabelSeparation);
		
		
		float coww = (float) bxw-lwidth-(3*mAxisLabelHeight-aPaint.ascent())-mLabelSeparation;
		
		float cohh = (float) bxh-lheight-
				(2*mAxisLabelHeight+aPaint.ascent()+5*mLabelSeparation)-(legendSize+mLabelSeparation);
		
		ticks.coordbounds = new RectF(0.0f,0.0f,
				coww,cohh);
//		
//		
//		
		float xoffset = lwidth+(2*mAxisLabelHeight)-(aPaint.ascent());
		
		System.out.println("MLABELSEPARATION"+ mLabelSeparation);
		
		//float xoffset = lwidth+(2*mAxisLabelHeight+aPaint.ascent())+mLabelSeparation;
		
		System.out.println("XOFFSET" + xoffset + "LWIDTH" + lwidth + "AXIS LABEL" + mAxisLabelHeight
				+ "APAINT" + aPaint.ascent());
		ticks.coordbounds.offset(lpad+xoffset,tpad+mLabelSeparation);
		
		ticks.legendBox = new RectF(0.0f,0.0f,coww,legendSize);
		
		System.out.println("CWBB"+ ticks.coordbounds.bottom+"COHH" + cohh +"tpad" + (tpad+mLabelSeparation));
		
		
		ticks.legendBox.offset(lpad+xoffset,ticks.coordbounds.bottom+lheight+mLabelSeparation+(mAxisLabelHeight-aPaint.ascent()));	
	
		System.out.println("LGBOX TOP" + ticks.legendBox.top );
	
	}
	
	private void getHTicks(OptimizeTicks otasks)
	{
		
		ticks.isHflipped=otasks.determineNumHTicks();
		ticks.nLabelWidth = otasks.getLabelWidth();
		ticks.subLabelHeight = otasks.getNewLabelHeight();
	}
	
	private void getHTicksBar(OptimizeTicks  otasks,int numseries,float mBarWidth, 
			float mBarSpacing,float iBarSpacing)
	{
		
		ticks.isHflipped = otasks.determineHTicksBar(numseries,mBarWidth, mBarSpacing, iBarSpacing);
		ticks.nhLabelWidth = otasks.getNewLabelWidth();
		ticks.nLabelWidth = otasks.getNewLabelWidth();
		ticks.subLabelHeight = otasks.getNewLabelHeight();
		
		System.out.println("HBAR");
		System.out.println("HLABEL" + ticks.nhLabelWidth );
		System.out.println("NLABEL" + ticks.nLabelWidth );
		//ticks.isHflipped=false;
		
	}
	
	
	private void getVTicksBar(OptimizeTicks otasks,int numseries,float mBarWidth, 
			float mBarSpacing, float iBarSpacing)
	{
		ticks.isVflipped=otasks.determineVTicksBar(numseries,mBarWidth, mBarSpacing, iBarSpacing);
		//ticks.nLabelHeight = otasks.getLabelHeight();
		ticks.nLabelHeight = otasks.getNewLabelHeight();
		ticks.newLabelWidth= otasks.getNewLabelWidth();
		ticks.nvLabelWidth=ticks.nLabelHeight;
		
		System.out.println("isV" + ticks.isVflipped+ ticks.nLabelHeight+ticks.newLabelWidth);
	}
	
	
	
	public ReturnIntervalsTicks getXCategoryYCategory(Boolean isHorizontal,
			AxisStops hstops,AxisStops vstops,String[] uniqueY,
			String[] uniqueX,float bxh, float bxw,float axisLabelSpace,OptimizeTicks otasks)
	{
		
		  ticks = new ReturnIntervalsTicks();
		  
		  ComputeStops cmp = new ComputeStops();
		  
		  
				
				getHTicks(otasks);
				
				getVTicks(bxh,axisLabelSpace,otasks);
				
				ticks.nvLabelWidth=(int) mVLabelWidth;
				
				
				computeWidthAndHeightMultiple(ticks.nvLabelWidth,ticks.subLabelHeight, bxh, bxw);
				ticks.nHticks =otasks.getViewportWidth();
				ticks.nVticks=otasks.getViewportHeight();
				
				ticks.isVflipped=true;
				
				
				 if(!isHorizontal)
					{
						
						//X is Horizontal , Y is vertical
						
						cmp.computeStopsCategory(uniqueX.length,hstops);
						cmp.computeStopsCategory(uniqueY.length,vstops);
					
					}
					
					else
					{
						
						//Y is Horizontal , X is vertical
						cmp.computeStopsCategory(uniqueY.length,hstops);
						cmp.computeStopsCategory(uniqueX.length,vstops);
						
						 
					}
					
					

					ticks.Viewport = new RectF(0,
							0,hstops.axisstops.length,vstops.axisstops.length);
					
					
					ticks.AXIS_H_MIN = (float)(hstops.axisstops[0]);
					ticks.AXIS_H_MAX = (float)(hstops.axisstops[hstops.axisstops.length-1]+1);
					
					ticks.AXIS_V_MIN = (float)(vstops.axisstops[0]);
					ticks.AXIS_V_MAX = (float)(vstops.axisstops[vstops.axisstops.length-1]+1);
				
					ticks.hstops=hstops;
					ticks.vstops=vstops;
				
				
			
		 
		 
		  
		  return ticks;
		 
	}
	
	
	public ReturnIntervalsTicks getXCategoryYNumeric(Boolean isHorizontal,
			double Graphmax, double Graphmin,double[] maxvalY,AxisStops hstops,AxisStops vstops,
			String[] uniqueX,float bxh, float bxw,float axisLabelSpace,OptimizeTicks otasks)
	{
		
		 ticks = new ReturnIntervalsTicks();
		
		 ComputeStops cmp = new ComputeStops();
		
		 if(!isHorizontal)
			
			
		{
			
			getHTicks(otasks);
			
			ticks.nHticks =otasks.getViewportWidth();
			
			ticks.nvLabelWidth=(int) mVLabelWidth;
			
			
			
			
			
			IntervalStats stats = otasks.determineReqTicksY
					(true,Graphmax,Graphmin,maxvalY);
			
			
			ticks.nVticks =stats.nVticks;
			ticks.isHflipped=stats.hFlipped;
			ticks.nLabelHeight = otasks.getLabelHeight();
			
			computeWidthAndHeightMultiple(ticks.nvLabelWidth,ticks.subLabelHeight, bxh, bxw);
			
			cmp.computeStopsCategory(uniqueX.length,hstops);
		
			//MinMax YData = cmp.computeMaxMinRange(maxvalY,stats.vinterval,ticks.nVticks);
			
			MinMax YData = cmp.computeMaxMinRangeSpider(maxvalY,stats.vinterval,ticks.nVticks);
	
			//cmp.computeStopsNum(stats.vRticks,vstops,YData.graphMin,YData.graphMax,stats.vinterval);
			
			cmp.computeStopsNumRoundedBar(stats.vRticks,vstops,YData.graphMin,YData.graphMax,stats.vinterval);
			ticks.vInterval = stats.vinterval;
			
			ticks.Viewport = new RectF(0,
					(float)(YData.graphMin),hstops.axisstops.length,
					(float)(YData.graphMax+ticks.vInterval));
			
			
			
			ticks.AXIS_H_MIN = (float)(hstops.axisstops[0]);
			ticks.AXIS_H_MAX = (float)(hstops.axisstops[hstops.axisstops.length-1]+1);
			ticks.AXIS_V_MIN = (float)(vstops.axisstops[0]);
			ticks.AXIS_V_MAX = (float)(vstops.axisstops[vstops.axisstops.length-1]+ticks.vInterval);
			
			ticks.hstops=hstops;
			ticks.vstops=vstops;
					
		}
		else
		{
			
			
			IntervalStats stats = otasks.determineReqTicksY(false, 
					maxvalY[maxvalY.length-1],maxvalY[0],maxvalY);
			/*ticks.subLabelHeight = otasks.getNewLabelHeight();
			ticks.nLabelWidth = otasks.getLabelWidth(); */
			
			//ticks.isHflipped=stats.hFlipped;
			
			ticks.isHflipped=true;
			
			
			ticks.subLabelHeight = 
					otasks.getLabelWidth(); 
			ticks.nLabelWidth = otasks.getNewLabelHeight();
			
			
			
			
			
			System.out.println("SUBLBL" + ticks.subLabelHeight+"NBLBL"+ticks.nLabelWidth +ticks.nvLabelWidth);
			getVTicks(bxh,axisLabelSpace,otasks);
			
			ticks.nvLabelWidth=(int) mVLabelWidth;
			
			ticks.subLabelHeight=ticks.nvLabelWidth;
			ticks.nVticks = otasks.getViewportHeight();
			
			computeWidthAndHeightMultiple(ticks.nvLabelWidth,ticks.nvLabelWidth, bxh, bxw);
			
			System.out.println("TICKS" + ticks.nvLabelWidth);
			
			ticks.nHticks = stats.nHticks;
			
			//MinMax YData =cmp.computeMaxMinRange(maxvalY,stats.hinterval,ticks.nHticks);
			
		
			
			MinMax YData =cmp.computeMaxMinRangeSpider(maxvalY,stats.hinterval,ticks.nHticks);
			cmp.computeStopsCategory(uniqueX.length,vstops);
			//cmp.computeStopsNum(stats.hRticks,hstops,YData.graphMin,YData.graphMax,stats.hinterval);
			
			
			cmp.computeStopsNumRoundedBar(stats.hRticks,hstops,YData.graphMin,YData.graphMax,stats.hinterval);
			double hInterval= stats.hinterval;
			
			ticks.hInterval=hInterval;
			
			ticks.isVflipped=true;
			
			
		
			ticks.Viewport = new RectF((float)(YData.graphMin),
					0,(float)(YData.graphMax+stats.hinterval),vstops.axisstops.length);
			
			ticks.AXIS_H_MIN = (float)(hstops.axisstops[0]);
			ticks.AXIS_H_MAX = (float)(hstops.axisstops[hstops.axisstops.length-1]+ticks.hInterval);
			
			for(int i=0; i < hstops.axisstops.length; i++)
			{
				System.out.println("AXIS_STOP HANDLE XY "+ hstops.axisstops[i]);
			}
			
			System.out.println("AXIS_STOP LAST"+hstops.axisstops[hstops.axisstops.length-1]);
			
			ticks.AXIS_V_MIN = (float)(vstops.axisstops[0]);
			ticks.AXIS_V_MAX = (float)(vstops.axisstops[vstops.axisstops.length-1]+1);
			
			ticks.hstops=hstops;
			ticks.vstops=vstops;
			
			
		}
		return ticks;
	}
	
	
	public ReturnIntervalsTicks getXCategoryYNumericBar(Boolean isHorizontal,
			double Graphmax, double Graphmin,double[] maxvalY,AxisStops hstops,AxisStops vstops,
			String[] uniqueX,float bxh, float bxw,float coww,float axisLabelSpace,OptimizeTicks otasks,
			int numseries)
	{
		
		 ticks = new ReturnIntervalsTicks();
		
		ComputeStops cmp = new ComputeStops();
		if(!isHorizontal)
			
			
		{
			
			getHTicksBar(otasks,numseries,mBarWidth, mBarSpacing, iBarSpacing);
			
			ticks.nHticks =otasks.getViewportWidth();
			
			
			ticks.nvLabelWidth=(int) mVLabelWidth;
			
			
			//computeWidthAndHeightBar(ticks.nvLabelWidth,ticks.subLabelHeight, bxh, bxw);
			
			computeWidthAndHeightBar(ticks.nvLabelWidth,ticks.subLabelHeight, bxh, bxw);
			
			System.out.println(ticks.coordbounds);
			
			IntervalStats stats = otasks.determineReqTicksYBar
					(true,Graphmax,Graphmin,maxvalY);
			
			
			ticks.nVticks =stats.nVticks;
			
			
			ticks.nLabelHeight = otasks.getLabelHeight();
			
			cmp.computeStopsCategory(uniqueX.length,hstops);
		
			MinMax YData = cmp.computeMaxMinRangeSpider(maxvalY,stats.vinterval,ticks.nVticks);
	
			cmp.computeStopsNumRoundedBar(stats.vRticks,vstops,YData.graphMin,YData.graphMax,stats.vinterval);
			ticks.vInterval = stats.vinterval;
			
			/*ticks.Viewport = new RectF(0,
					(float)(YData.graphMin-stats.vinterval),hstops.axisstops.length,
					(float)(YData.graphMax+stats.vinterval));*/
			
			
			ticks.Viewport = new RectF(0,
					(float)(YData.graphMin),hstops.axisstops.length,
					(float)(YData.graphMax));
			
			ticks.AXIS_H_MIN = (float)(hstops.axisstops[0]);
			ticks.AXIS_H_MAX = (float)(hstops.axisstops[hstops.axisstops.length-1]+1);
			ticks.AXIS_V_MIN = (float)(vstops.axisstops[0]);
			ticks.AXIS_V_MAX = (float)(vstops.axisstops[vstops.axisstops.length-1]+ticks.vInterval);
			
			ticks.hstops=hstops;
			ticks.vstops=vstops;
			
			ticks.isVflipped=false;
					
		}
		else
		{
			//DONE
			
			
			getVTicksBar(otasks,numseries,mBarWidth, mBarSpacing, iBarSpacing);
			
			ticks.nVticks = otasks.getViewportHeight();
			
			
			//computeWidthAndHeightBar(ticks.newLabelWidth,ticks.nLabelHeight, bxh, bxw);
			
			IntervalStats stats = otasks.determineReqTicksYBar(false, 
					maxvalY[maxvalY.length-1],maxvalY[0],maxvalY);
			
			/*ticks.subLabelHeight = otasks.getNewLabelHeight();
			ticks.nLabelWidth = otasks.getNewLabelWidth(); */
			
			ticks.nvLabelWidth=(int) mVLabelWidth;
			
			
			ticks.nHticks = stats.nHticks;
			
			MinMax YData =cmp.computeMaxMinRangeSpider(maxvalY,stats.hinterval,ticks.nHticks);
			cmp.computeStopsCategory(uniqueX.length,vstops);
			cmp.computeStopsNumRoundedBar(stats.hRticks,hstops,YData.graphMin,YData.graphMax,stats.hinterval);
			double hInterval= stats.hinterval;
			
			ticks.hInterval=hInterval;
		
			//ticks.isHflipped=false;
			
			ticks.isHflipped=true;
			
			ticks.subLabelHeight=otasks.getNewLabelWidth();
			ticks.nLabelWidth=otasks.getNewLabelHeight();
			
			ticks.nhLabelWidth=otasks.getNewLabelHeight();
			
			//computeWidthAndHeightBarMultiple(ticks.newLabelWidth,ticks.subLabelHeight, bxh, bxw);
		
			ticks.subLabelHeight= ticks.nvLabelWidth;
			System.out.println("SUBLB" + ticks.subLabelHeight+"NBLB" + ticks.nLabelWidth);
			
			computeWidthAndHeightBar(ticks.nvLabelWidth,ticks.nvLabelWidth
			, bxh, bxw);
			
			
			ticks.isVflipped=true;
			/*ticks.Viewport = new RectF((float)(YData.graphMin-stats.hinterval),
					0,(float)(YData.graphMax+stats.hinterval),vstops.axisstops.length);*/
			
			ticks.Viewport = new RectF((float)(YData.graphMin),
					0,(float)(YData.graphMax),vstops.axisstops.length);
			
			ticks.AXIS_H_MIN = (float)(hstops.axisstops[0]);
			ticks.AXIS_H_MAX = (float)(hstops.axisstops[hstops.axisstops.length-1]+hInterval);
			ticks.AXIS_V_MIN = (float)(vstops.axisstops[0]);
			ticks.AXIS_V_MAX = (float)(vstops.axisstops[vstops.axisstops.length-1]+1);
			
			ticks.hstops=hstops;
			ticks.vstops=vstops;
			
			
		}
		
		return ticks;
	}
	
	
	
	
	
	
	public ReturnIntervalsTicks getYCategoryXNumericBar(Boolean isHorizontal,
			double Graphmax, double Graphmin,double[] maxvalX,AxisStops hstops,AxisStops vstops,
			String[] uniqueY,float bxh,float bxw, float coww,
			float axisLabelSpace,OptimizeTicks otasks,int numseries)
	{
	
	 
	

		 ticks = new ReturnIntervalsTicks();
		
		ComputeStops cmp = new ComputeStops();
	
	if(!isHorizontal)
	{
		
		getVTicksBar(otasks,numseries,mBarWidth, mBarSpacing, iBarSpacing);
		
		ticks.nVticks = otasks.getViewportHeight();
		
		
		
		
		
		
		IntervalStats stats = otasks.determineReqTicksXBar
				(true, maxvalX[maxvalX.length-1], maxvalX[0],maxvalX);
		
		
		ticks.nHticks =stats.nHticks;
		
	
		/*ticks.subLabelHeight = otasks.getNewLabelHeight();
		ticks.nLabelWidth = otasks.getNewLabelWidth(); */
	
		
		ticks.nLabelWidth = otasks.getNewLabelHeight();
		
		ticks.nhLabelWidth=otasks.getNewLabelHeight();
		
		
		ticks.subLabelHeight = otasks.getNewLabelWidth(); 
		
		ticks.nvLabelWidth=(int) mVLabelWidth;
		
		ticks.subLabelHeight=ticks.nvLabelWidth;
		
		
		System.out.println(" XCAT Y NUM SUBLABELHT"+ ticks.subLabelHeight+"nLABEL" + ticks.nhLabelWidth);
		//computeWidthAndHeightBarMultiple(ticks.newLabelWidth,ticks.subLabelHeight, bxh, bxw);
		
		
		computeWidthAndHeightBar(ticks.nvLabelWidth,ticks.nvLabelWidth, bxh, bxw);
		
		ticks.hInterval = stats.hinterval;
		
		
		MinMax XData =cmp.computeMaxMinRangeSpider(maxvalX,ticks.hInterval,ticks.nHticks);
		
		cmp.computeStopsNumRoundedBar(stats.hRticks,hstops,XData.graphMin,XData.graphMax,ticks.hInterval);
		cmp.computeStopsCategory(uniqueY.length,vstops);
	
		
		
		
		
		/*ticks.Viewport = new RectF((float)(XData.graphMin-ticks.hInterval),
				0,(float)(XData.graphMax+ticks.hInterval),vstops.axisstops.length);*/
		
		ticks.Viewport = new RectF((float)(XData.graphMin),
				0,(float)(XData.graphMax),vstops.axisstops.length);
		
		for(int i=0; i< hstops.axisstops.length;i++)
		{
			System.out.println("hstops" + hstops.axisstops[i]);
		}
		
		
		ticks.AXIS_H_MIN = (float)(hstops.axisstops[0]);
		ticks.AXIS_H_MAX = (float)(hstops.axisstops[hstops.axisstops.length-1]);
		
		ticks.AXIS_V_MIN = (float)(vstops.axisstops[0]);
		ticks.AXIS_V_MAX = (float)(vstops.axisstops[vstops.axisstops.length-1]+1);
		
		
		//ticks.isHflipped=false;
		
		ticks.isHflipped=true;
		
		ticks.hstops=hstops;
		ticks.vstops=vstops;
		
	}
	else
	{
		
		getHTicksBar(otasks, numseries,mBarWidth, mBarSpacing, iBarSpacing);
		
		
		
		IntervalStats stats = otasks.determineReqTicksXBar(false,
				maxvalX[maxvalX.length-1], maxvalX[0],maxvalX);
		
		ticks.nVticks = stats.nVticks;
		ticks.nHticks =otasks.getViewportWidth();
		ticks.nLabelHeight =otasks.getLabelHeight();
		
		ticks.vInterval = stats.vinterval;
		ticks.nvLabelWidth=(int) mVLabelWidth;
		
		
		System.out.println("NVLABELWIDTH"+ ticks.nvLabelWidth);
		
		computeWidthAndHeightBar(ticks.nvLabelWidth,ticks.subLabelHeight, bxh, bxw);
		
		MinMax XData =cmp.computeMaxMinRangeSpider(maxvalX,ticks.vInterval,ticks.nVticks);
		
		cmp.computeStopsNumRoundedBar(stats.vRticks,vstops,XData.graphMin,XData.graphMax,ticks.vInterval);
		cmp.computeStopsCategory(uniqueY.length,hstops);
		
		/*ticks.Viewport = new RectF(0,
				(float)(XData.graphMin-ticks.vInterval),hstops.axisstops.length,
				(float)(XData.graphMax+ticks.vInterval));*/
		
		ticks.Viewport = new RectF(0,
				(float)(XData.graphMin),hstops.axisstops.length,
				(float)(XData.graphMax));
		
		ticks.AXIS_H_MIN = (float)(hstops.axisstops[0]);
		ticks.AXIS_H_MAX = (float)(hstops.axisstops[hstops.axisstops.length-1]+1);
		
		ticks.AXIS_V_MIN = (float)(vstops.axisstops[0]);
		ticks.AXIS_V_MAX = (float)(vstops.axisstops[vstops.axisstops.length-1]);
		
		ticks.hstops=hstops;
		ticks.vstops=vstops;
		
		ticks.isVflipped=false;
		
	}
	
			return ticks;
	}
	
	
	
	public ReturnIntervalsTicks getXNumericYNumeric(Boolean isHorizontal,
			double XGraphmax, double XGraphmin,double YGraphmax, double YGraphmin,
			double[] maxvalX,double[] maxvalY,AxisStops hstops,AxisStops vstops,
			float bxh, float bxw,float axisLabelSpace,OptimizeTicks otasks)
	{
		
		 ticks = new ReturnIntervalsTicks();
			
		 ComputeStops cmp = new ComputeStops();
		
		 IntervalStats stats;

		 	if(!isHorizontal)
			{
				
				otasks.setHGraphMinMax(maxvalX[0], maxvalX[maxvalX.length-1]);
				otasks.setVGraphMinMax(maxvalY[0], maxvalY[maxvalY.length-1]);
				//stats = otasks.determineReqTicks(true,maxvalX,maxvalY);
				stats = otasks.determineReqTicksRounded(true,maxvalX,maxvalY);
				
			}
			else
			{
				otasks.setHGraphMinMax(maxvalY[0], maxvalY[maxvalY.length-1]);
				otasks.setVGraphMinMax(maxvalX[0], maxvalX[maxvalX.length-1]);
				//stats = otasks.determineReqTicks(false,maxvalX,maxvalY);
				stats = otasks.determineReqTicksRounded(false,maxvalX,maxvalY);
				
			}
		 	
		 	
		 	

			ticks.nVticks = stats.nVticks;
			ticks.nHticks =stats.nHticks;
			ticks.hInterval = stats.hinterval;
			ticks.vInterval = stats.vinterval;
			ticks.isHflipped= stats.hFlipped;
			
			ticks.isHflipped= true;
			
			
			ticks.isVflipped=stats.vFlipped;
//			ticks.subLabelHeight = otasks.getNewLabelHeight();
//			ticks.nLabelWidth = otasks.getLabelWidth(); 
			
			ticks.subLabelHeight =otasks.getLabelWidth(); 
			ticks.nLabelWidth = otasks.getNewLabelHeight();
			
			ticks.nvLabelWidth=(int) mVLabelWidth;
			ticks.nhLabelWidth=ticks.nLabelWidth;
			
			ticks.nLabelHeight = otasks.getLabelHeight();
			
			System.out.println("NVLABELWIDTH"+ ticks.nvLabelWidth);
			
			ticks.subLabelHeight =ticks.nvLabelWidth;
			
			computeWidthAndHeightMultiple(ticks.nvLabelWidth,ticks.nvLabelWidth, bxh, bxw);
			
			if(!isHorizontal)
			{
				
				//MinMax XData =cmp.computeMaxMinRange(maxvalX,stats.hinterval,ticks.nHticks);
				//MinMax YData =cmp.computeMaxMinRange(maxvalY,stats.vinterval,ticks.nVticks);
				
				MinMax XData =cmp.computeMaxMinRangeSpider(maxvalX,stats.hinterval,ticks.nHticks);
				MinMax YData =cmp.computeMaxMinRangeSpider(maxvalY,stats.vinterval,ticks.nVticks);
				
				
				
				/*cmp.computeStopsNum(stats.hRticks,hstops,XData.graphMin,XData.graphMax,ticks.hInterval);
				cmp.computeStopsNum(stats.vRticks,vstops,YData.graphMin,YData.graphMax,ticks.vInterval);*/
				
				/*cmp.computeStopsNumRounded(stats.hRticks,hstops,XData.graphMin,XData.graphMax,ticks.hInterval);
				cmp.computeStopsNumRounded(stats.vRticks,vstops,YData.graphMin,YData.graphMax,ticks.vInterval);*/
				
				
				cmp.computeStopsNumRoundedBar(stats.hRticks,hstops,XData.graphMin,XData.graphMax,ticks.hInterval);
				cmp.computeStopsNumRoundedBar(stats.vRticks,vstops,YData.graphMin,YData.graphMax,ticks.vInterval);
				
				ticks.hstops=hstops;
				ticks.vstops=vstops;
				
				
				/*ticks.Viewport = new RectF((float)(XData.graphMin-ticks.hInterval),
						(float)(YData.graphMin-ticks.vInterval),(float)(XData.graphMax+
								ticks.hInterval),(float)(YData.graphMax+ticks.vInterval));*/
				
				
				ticks.Viewport = new RectF((float)(XData.graphMin),
						(float)(YData.graphMin),(float)(XData.graphMax+ticks.hInterval),(float)(YData.graphMax+ticks.vInterval));
				
			
				
				ticks.AXIS_H_MIN = (float)(hstops.axisstops[0]);
				ticks.AXIS_H_MAX = (float)(hstops.axisstops[hstops.axisstops.length-1]+ticks.hInterval);
				
				//ticks.AXIS_H_MAX = (float)(hstops.axisstops[hstops.axisstops.length-1]);
				ticks.AXIS_V_MIN = (float)(vstops.axisstops[0]);
				ticks.AXIS_V_MAX = (float)(vstops.axisstops[vstops.axisstops.length-1]+ticks.vInterval);
				//ticks.AXIS_V_MAX = (float)(vstops.axisstops[vstops.axisstops.length-1]);
				
				
		
			}
			else
			{
						
				//MinMax XData =cmp.computeMaxMinRange(maxvalX,ticks.vInterval,ticks.nVticks);
				//MinMax YData =cmp.computeMaxMinRange(maxvalY,ticks.hInterval,ticks.nHticks);
				
				MinMax XData =cmp.computeMaxMinRangeSpider(maxvalX,ticks.vInterval,ticks.nVticks);
				MinMax YData =cmp.computeMaxMinRangeSpider(maxvalY,ticks.hInterval,ticks.nHticks);
	
				
				//cmp.computeStopsNum(stats.hRticks,hstops,YData.graphMin,YData.graphMax,ticks.hInterval);
				//cmp.computeStopsNum(stats.vRticks,vstops,XData.graphMin,XData.graphMax,ticks.vInterval);
				
				cmp.computeStopsNumRoundedBar(stats.hRticks,hstops,YData.graphMin,YData.graphMax,ticks.hInterval);
				cmp.computeStopsNumRoundedBar(stats.vRticks,vstops,XData.graphMin,XData.graphMax,ticks.vInterval);
				
				
				
				ticks.hstops=hstops;
				ticks.vstops=vstops;
				
				
				ticks.Viewport = new RectF((float)(YData.graphMin),
						(float)(XData.graphMin),(float)(YData.graphMax+ticks.hInterval),
						(float)(XData.graphMax + ticks.vInterval));			
				
				ticks.AXIS_H_MIN = (float)(hstops.axisstops[0]);
				ticks.AXIS_H_MAX = (float)(hstops.axisstops[hstops.axisstops.length-1]+ticks.hInterval);
				ticks.AXIS_V_MIN = (float)(vstops.axisstops[0]);
				ticks.AXIS_V_MAX = (float)(vstops.axisstops[vstops.axisstops.length-1]+ticks.vInterval);
				
				
				
			}
			
		
		return ticks;
		
		
	}
	
	
	
	
	
	public ReturnIntervalsTicks getYCategoryXNumeric(Boolean isHorizontal,
			double Graphmax, double Graphmin,double[] maxvalX,AxisStops hstops,AxisStops vstops,
			String[] uniqueY,float bxh, float bxw,float axisLabelSpace,OptimizeTicks otasks)
	{
	
	 
	

		 ticks = new ReturnIntervalsTicks();
		
		ComputeStops cmp = new ComputeStops();
	
	if(!isHorizontal)
	{
		IntervalStats stats = otasks.determineReqTicksX
				(true, maxvalX[maxvalX.length-1], maxvalX[0],maxvalX);
		
		
		ticks.nHticks =stats.nHticks;
//		ticks.isHflipped=stats.hFlipped;
//	
//		ticks.subLabelHeight = otasks.getNewLabelHeight();
//		ticks.nLabelWidth = otasks.getLabelWidth(); 
		
		ticks.isHflipped=true;
		
		ticks.subLabelHeight =otasks.getLabelWidth(); 
		ticks.nLabelWidth = otasks.getNewLabelHeight();
		
		
		getVTicks(bxh,axisLabelSpace,otasks);
		
		ticks.nVticks = otasks.getViewportHeight();
		
		//ticks.nvLabelWidth=(int) mVLabelWidth;
		
		ticks.nvLabelWidth=(int) mVLabelWidth;
		
		System.out.println("NVLABELWIDTH"+ ticks.nvLabelWidth);
		
		ticks.subLabelHeight= ticks.nvLabelWidth;
		
		computeWidthAndHeightMultiple(ticks.nvLabelWidth,ticks.nvLabelWidth, bxh, bxw);
		ticks.hInterval = stats.hinterval;
		
		
		//MinMax XData =cmp.computeMaxMinRange(maxvalX,ticks.hInterval,ticks.nHticks);
		
		MinMax XData =cmp.computeMaxMinRangeSpider(maxvalX,ticks.hInterval,ticks.nHticks);
		
		//cmp.computeStopsNum(stats.hRticks,hstops,XData.graphMin,XData.graphMax,ticks.hInterval);
		
		cmp.computeStopsNumRoundedBar(stats.hRticks,hstops,XData.graphMin,XData.graphMax,ticks.hInterval);
		cmp.computeStopsCategory(uniqueY.length,vstops);
	
		//ticks.Viewport = new RectF((float)(XData.graphMin-ticks.hInterval),
			//	0,(float)(XData.graphMax+ticks.hInterval),vstops.axisstops.length);
		
		ticks.Viewport = new RectF((float)(XData.graphMin),
				0,(float)(XData.graphMax+ticks.hInterval),vstops.axisstops.length);
		
		ticks.AXIS_H_MIN = (float)(hstops.axisstops[0]);
		ticks.AXIS_H_MAX = (float)(hstops.axisstops[hstops.axisstops.length-1] + ticks.hInterval);
		
		ticks.AXIS_V_MIN = (float)(vstops.axisstops[0]);
		
		ticks.AXIS_V_MAX = (float)(vstops.axisstops[vstops.axisstops.length-1]+1);
		
		
		ticks.hstops=hstops;
		ticks.vstops=vstops;
		
		ticks.isVflipped=true;
		
		
		
	}
	else
	{
		
		ticks.isHflipped=otasks.determineNumHTicks();
		ticks.nLabelWidth = otasks.getLabelWidth();
		ticks.nHticks =otasks.getViewportWidth();
		ticks.subLabelHeight = otasks.getNewLabelHeight();	
		
		
		ticks.nvLabelWidth=(int) mVLabelWidth;
		
		
		computeWidthAndHeightMultiple(ticks.nvLabelWidth,ticks.subLabelHeight, bxh, bxw);
		
		IntervalStats stats = otasks.determineReqTicksX(false,
				maxvalX[maxvalX.length-1], maxvalX[0],maxvalX);
		
		ticks.nVticks = stats.nVticks;
		
		ticks.nLabelHeight =otasks.getLabelHeight();
		
		ticks.vInterval = stats.vinterval;
		
		ticks.isVflipped=stats.vFlipped;
		
		//MinMax XData =cmp.computeMaxMinRange(maxvalX,ticks.vInterval,ticks.nVticks);
		
		//cmp.computeStopsNum(stats.vRticks,vstops,XData.graphMin,XData.graphMax,ticks.vInterval);
		
		MinMax XData =cmp.computeMaxMinRangeSpider(maxvalX,ticks.vInterval,ticks.nVticks);
		
		cmp.computeStopsNumRoundedBar(stats.vRticks,vstops,XData.graphMin,XData.graphMax,ticks.vInterval);
		cmp.computeStopsCategory(uniqueY.length,hstops);
		
		/*ticks.Viewport = new RectF(0,
				(float)(XData.graphMin-ticks.vInterval),hstops.axisstops.length,
				(float)(XData.graphMax+ticks.vInterval));*/
		
		ticks.Viewport = new RectF(0,
				(float)(XData.graphMin),hstops.axisstops.length,
				(float)(XData.graphMax+ticks.vInterval));
		
		ticks.AXIS_H_MIN = (float)(hstops.axisstops[0]);
		ticks.AXIS_H_MAX = (float)(hstops.axisstops[hstops.axisstops.length-1]+1);
		
		ticks.AXIS_V_MIN = (float)(vstops.axisstops[0]);
		ticks.AXIS_V_MAX = (float)(vstops.axisstops[vstops.axisstops.length-1]+ticks.vInterval);
		
		ticks.hstops=hstops;
		ticks.vstops=vstops;
		
	}
	return ticks;
	
	}
	
	
	
	public ReturnIntervalsTicks getYCategoryXNumericNegativeBar(Boolean isHorizontal,
			double RGraphmax, double RGraphmin,double[] RmaxvalX,
			double LGraphmax, double LGraphmin,double[] LmaxvalX,
			AxisStops hstops,AxisStops vstops,
			String[] uniqueY,float bxh,float bxw, float coww,
			float axisLabelSpace,float xoffset,OptimizeTicks otasks,int numseries)
	{
		
		ticks = new ReturnIntervalsTicks();
		
		ComputeStops cmp = new ComputeStops();
		
		if(!isHorizontal)
		{
		
			
			IntervalStatsNegative stats = otasks.determineReqTicksXNegative
					(true, LmaxvalX[LmaxvalX.length-1],LmaxvalX[0],LmaxvalX,
							RmaxvalX[RmaxvalX.length-1],RmaxvalX[0],RmaxvalX,
							xoffset,bxw);
	
			ticks.nHticks =stats.nHticks;
			
		
			
			
			
			getVTicksBar(otasks,1,mBarWidth, mBarSpacing,0);
			
			ticks.nVticks = otasks.getViewportHeight();
			
			
			
//			ticks.subLabelHeight = otasks.getNewLabelHeight();
//			ticks.nLabelWidth = otasks.getNewLabelWidth(); 
			
			ticks.isHflipped=true;
			
			//ticks.subLabelHeight =otasks.getNewLabelWidth();
					
			ticks.nLabelWidth = otasks.getNewLabelHeight();
			
			ticks.nhLabelWidth=otasks.getNewLabelHeight();
			
			
			ticks.subLabelHeight = otasks.getNewLabelWidth();
			
			ticks.nvLabelWidth=(int) mVLabelWidth;
			
			ticks.subLabelHeight=ticks.nvLabelWidth;
			
			//computeWidthAndHeightBar(ticks.newLabelWidth,ticks.nLabelHeight, bxh, bxw);
			
			computeWidthAndHeightBar(ticks.nvLabelWidth,ticks.nvLabelWidth, bxh, bxw);
			
		
			ticks.hLinterval = stats.hLinterval;
			ticks.hRinterval = stats.hRinterval;
			
			ticks.isVflipped=true;
			
			
			MinMax XLData =cmp.computeMaxMinRangeSpider(LmaxvalX,ticks.hLinterval,ticks.nHticks/2);
			MinMax XRData =cmp.computeMaxMinRangeSpider(RmaxvalX,ticks.hRinterval,ticks.nHticks/2);
			
			
			ticks.LGraphMax=XLData.graphMax;
			ticks.LGraphMin=XLData.graphMin;
			ticks.RGraphMax=XRData.graphMax;
			ticks.RGraphMin=XRData.graphMin;
			
			
			cmp.computeStopsNumNegative(stats.hLRticks,hstops,
					XLData.graphMin,XLData.graphMax,ticks.hLinterval,
					stats.hRRticks,
					XRData.graphMin,XRData.graphMax,ticks.hRinterval);
			
			cmp.computeStopsCategory(uniqueY.length,vstops);
		
			ticks.Viewport = new RectF(0,
					0,hstops.pointstops.length-1,vstops.axisstops.length);
			
			ticks.AXIS_H_MIN = (float)(hstops.axisstops[0]);
			ticks.AXIS_H_MAX = (float)(hstops.axisstops[hstops.axisstops.length-1]);
			
			ticks.AXIS_V_MIN = (float)(vstops.axisstops[0]);
			ticks.AXIS_V_MAX = (float)(vstops.axisstops[vstops.axisstops.length-1]+1);
			
			
			ticks.hstops=hstops;
			ticks.vstops=vstops;
		}
		
		else
		{
			
			
			
			getHTicksBar(otasks,1,mBarWidth, mBarSpacing, 0);
			
			
			ticks.nvLabelWidth=(int) mVLabelWidth;
			
			
			computeWidthAndHeightBar(ticks.nvLabelWidth,ticks.subLabelHeight, bxh, bxw);
			
			
			
			ticks.nHticks =otasks.getViewportWidth();
			
			
			
			//computeWidthAndHeightBar(ticks.nLabelWidth,ticks.subLabelHeight, bxh, bxw);
			
			
			
			IntervalStatsNegative stats = otasks.determineReqTicksXNegative(false,
					LmaxvalX[LmaxvalX.length-1],LmaxvalX[0],LmaxvalX,
					RmaxvalX[RmaxvalX.length-1],RmaxvalX[0],RmaxvalX,
					xoffset,bxw);
			
			ticks.nVticks = stats.nVticks;
			
			ticks.nLabelHeight =otasks.getLabelHeight();
			
			ticks.vLinterval = stats.vLinterval;
			ticks.vRinterval = stats.vRinterval;
			
			ticks.isVflipped=stats.vFlipped;
			
		
			System.out.println("TICKS L" + ticks.vLinterval);
			
			System.out.println("TICKS R" + ticks.vRinterval);
			
			MinMax XLData =cmp.computeMaxMinRangeSpider(LmaxvalX,ticks.vLinterval,ticks.nHticks/2);
			MinMax XRData =cmp.computeMaxMinRangeSpider(RmaxvalX,ticks.vRinterval,ticks.nHticks/2);
		
			
			ticks.LGraphMax=XLData.graphMax;
			ticks.LGraphMin=XLData.graphMin;
			ticks.RGraphMax=XRData.graphMax;
			ticks.RGraphMin=XRData.graphMin;
			
			
			cmp.computeStopsNumNegative(stats.vLRticks,vstops,
					XLData.graphMin,XLData.graphMax,ticks.vLinterval,
					stats.vRRticks,
					XRData.graphMin,XRData.graphMax,ticks.vRinterval);
			
			cmp.computeStopsCategory(uniqueY.length,hstops);
			
			ticks.Viewport = new RectF(0,
					(float)0,hstops.axisstops.length,
					(float)vstops.pointstops.length-1);
			
			ticks.AXIS_H_MIN = (float)(hstops.axisstops[0]);
			ticks.AXIS_H_MAX = (float)(hstops.axisstops[hstops.axisstops.length-1]+1);
			
			ticks.AXIS_V_MIN = (float)(vstops.axisstops[0]);
			ticks.AXIS_V_MAX = (float)(vstops.axisstops[vstops.axisstops.length-1]);
			
			ticks.hstops=hstops;
			ticks.vstops=vstops;
			
			
			ticks.isVflipped=false;
		}
		
		
		return ticks;
		
	}
	
	
	
	public ReturnIntervalsTicks getXCategoryYNumericNegativeBar(Boolean isHorizontal,
			double RGraphmax, double RGraphmin,double[] RmaxvalY,
			double LGraphmax, double LGraphmin,double[] LmaxvalY,
			AxisStops hstops,AxisStops vstops,
			String[] uniqueX,float bxh, float bxw,float coww,
			float axisLabelSpace,float xoffset,OptimizeTicks otasks,
			int numseries)
	{
		
		 ticks = new ReturnIntervalsTicks();
		
		ComputeStops cmp = new ComputeStops();
		
		
		if(!isHorizontal)
			
			
		{
			
			getHTicksBar(otasks,1,mBarWidth, mBarSpacing, 0);
			
			ticks.nHticks =otasks.getViewportWidth();
			
			
			ticks.nvLabelWidth=(int) mVLabelWidth;
			
			computeWidthAndHeightBar(ticks.nvLabelWidth,ticks.subLabelHeight, bxh, bxw);
			
			
			IntervalStatsNegative stats = otasks.determineReqTicksYNegative(true, 
					LGraphmax, LGraphmin, LmaxvalY, 
					RGraphmax, RGraphmin, RmaxvalY, xoffset, bxw);
			
			
			ticks.nVticks =stats.nVticks;
			
			ticks.isVflipped=stats.vFlipped;
			
			ticks.nLabelHeight = otasks.getLabelHeight();
			
			cmp.computeStopsCategory(uniqueX.length,hstops);
		
			MinMax YLData = cmp.computeMaxMinRangeSpider(LmaxvalY,stats.vLinterval,ticks.nVticks/2);
			MinMax YRData = cmp.computeMaxMinRangeSpider(RmaxvalY,stats.vRinterval,ticks.nVticks/2);
	
			
			ticks.vLinterval = stats.vLinterval;
			ticks.vRinterval = stats.vRinterval;
			
			
			ticks.LGraphMax=YLData.graphMax;
			ticks.LGraphMin=YLData.graphMin;
			ticks.RGraphMax=YRData.graphMax;
			ticks.RGraphMin=YRData.graphMin;
			
			cmp.computeStopsNumNegative(
					stats.vLRticks,vstops,
					YLData.graphMin,YLData.graphMax,ticks.vLinterval,
					stats.vRRticks,
					YRData.graphMin,YRData.graphMax,ticks.vRinterval);
			
			
			
			
			
			/*ticks.Viewport = new RectF(0,
					(float)0,hstops.axisstops.length,
					(float)vstops.pointstops.length);*/
			
			
			/*ticks.Viewport = new RectF(0,
					(float)0,hstops.axisstops.length,
					(float)vstops.axisstops.length-1);*/
			
			ticks.Viewport = new RectF(0,
					(float)0,hstops.axisstops.length,
					(float)vstops.axisstops.length-1);
			
			ticks.AXIS_H_MIN = (float)(hstops.axisstops[0]);
			ticks.AXIS_H_MAX = (float)(hstops.axisstops[hstops.axisstops.length-1]+1);
			ticks.AXIS_V_MIN = (float)(vstops.axisstops[0]);
			//ticks.AXIS_V_MAX = (float)(vstops.axisstops[vstops.axisstops.length-1]);
			//ticks.AXIS_V_MAX = (float)(vstops.axisstops[vstops.axisstops.length-1]+1);
			//ticks.AXIS_V_MAX = (float)(vstops.axisstops[vstops.axisstops.length-1]+vLinterval);
			
			ticks.AXIS_V_MAX = (float)(vstops.axisstops[vstops.axisstops.length-1]);
			ticks.hstops=hstops;
			ticks.vstops=vstops;
					
		}
		else
		{
			//DONE
			
			IntervalStatsNegative stats = otasks.determineReqTicksYNegative(false, 
					LGraphmax, LGraphmin,LmaxvalY, 
					RGraphmax, RGraphmin,RmaxvalY,0, bxh);
			
			
			
			ticks.nHticks = stats.nHticks;
			
			
			getVTicksBar(otasks,1,mBarWidth, mBarSpacing,0);
			
			ticks.nVticks = otasks.getViewportHeight();
			
			
//			ticks.subLabelHeight = otasks.getNewLabelHeight();
//			ticks.nLabelWidth = otasks.getNewLabelWidth(); 
			
			ticks.subLabelHeight = otasks.getNewLabelWidth(); 
			ticks.nLabelWidth = otasks.getNewLabelHeight();
			
			//ticks.nvLabelWidth= (int) mVLabelWidth;
			
			ticks.isHflipped=true;
			
			
			ticks.nvLabelWidth=(int) mVLabelWidth;
			
			ticks.subLabelHeight=ticks.nvLabelWidth;
			//computeWidthAndHeightBar(ticks.newLabelWidth,ticks.nLabelHeight, bxh, bxw);
			
			computeWidthAndHeightBar(ticks.nvLabelWidth,ticks.nvLabelWidth, bxh, bxw);
			
			ticks.hLinterval = stats.hLinterval;
			ticks.hRinterval = stats.hRinterval;
			
			
			ticks.isVflipped=true;
			
			MinMax YLData =cmp.computeMaxMinRangeSpider(LmaxvalY,stats.hLinterval,ticks.nHticks/2);
			MinMax YRData =cmp.computeMaxMinRangeSpider(RmaxvalY,stats.hRinterval,ticks.nHticks/2);
			
			
			ticks.LGraphMax=YLData.graphMax;
			ticks.LGraphMin=YLData.graphMin;
			ticks.RGraphMax=YRData.graphMax;
			ticks.RGraphMin=YRData.graphMin;
			
			
			cmp.computeStopsCategory(uniqueX.length,vstops);
			cmp.computeStopsNumNegative(
					stats.hLRticks,hstops,
					YLData.graphMin,YLData.graphMax,ticks.hLinterval,
					stats.hRRticks,
					YRData.graphMin,YRData.graphMax,ticks.hRinterval);
			
			//double hInterval= stats.hinterval;
			
			ticks.hLinterval= stats.hLinterval;
			ticks.hRinterval= stats.hRinterval;
		
			
		
			/*ticks.Viewport = new RectF((float)0,
					0,(float)hstops.pointstops.length,vstops.axisstops.length);*/
			
			/*ticks.Viewport = new RectF((float)0,
					0,(float)hstops.axisstops.length-1,vstops.axisstops.length);*/
			
			
			ticks.Viewport = new RectF((float)0,
					0,(float)hstops.axisstops.length-1,vstops.axisstops.length);
			
			ticks.AXIS_H_MIN = (float)(hstops.axisstops[0]);
			//ticks.AXIS_H_MAX = (float)(hstops.axisstops[hstops.axisstops.length-1]+1);
			ticks.AXIS_H_MAX = (float)(hstops.axisstops[hstops.axisstops.length-1]);
			ticks.AXIS_V_MIN = (float)(vstops.axisstops[0]);
			ticks.AXIS_V_MAX = (float)(vstops.axisstops[vstops.axisstops.length-1]+1);
			
			ticks.hstops=hstops;
			ticks.vstops=vstops;
			
			
		}
		
		return ticks;
	}
	
	
	public ReturnIntervalsTicks getRange(Boolean isHorizontal,
			double graphMax,double graphMin,double[] val, AxisStops hstops, AxisStops vstops, 
			String[] unique, float bxh, float bxw, float coww, float axisLabelSpace,
			OptimizeTicks tasks,float mBarWidth,float mBarSpacing)
	
	{
		 ticks = new ReturnIntervalsTicks();
			
			ComputeStops cmp = new ComputeStops();
			if(!isHorizontal)
				
				
			{
				
				getHTicksBar(tasks,1,mBarWidth,mBarSpacing,0);
				
				ticks.nHticks =tasks.getViewportWidth();
				
				
				ticks.nvLabelWidth=(int)mVLabelWidth;
				
				computeWidthAndHeightBar(ticks.nvLabelWidth,ticks.subLabelHeight, bxh, bxw);
				
				
				IntervalStats stats = tasks.determineReqTicksYBar
						(true,graphMax,graphMin,val);
				
				
				ticks.nVticks =stats.nVticks;
				
				
				ticks.nLabelHeight = tasks.getLabelHeight();
				
				cmp.computeStopsCategory(unique.length,hstops);
			
				MinMax YData = cmp.computeMaxMinRangeDouble(val,stats.vinterval,ticks.nVticks);
		
				//cmp.computeStopsNum(stats.vRticks,vstops,YData.graphMin,YData.graphMax,stats.vinterval);
				
				cmp.computeStopsNumRoundedBar(stats.vRticks,vstops,YData.graphMin,YData.graphMax,stats.vinterval);
				ticks.vInterval = stats.vinterval;
				
				ticks.Viewport = new RectF(0,
						(float)(YData.graphMin),hstops.axisstops.length,
						(float)(YData.graphMax));
				
				ticks.isVflipped=false;
				
				
				ticks.AXIS_H_MIN = (float)(hstops.axisstops[0]);
				ticks.AXIS_H_MAX = (float)(hstops.axisstops[hstops.axisstops.length-1]+1);
				ticks.AXIS_V_MIN = (float)(vstops.axisstops[0]);
				ticks.AXIS_V_MAX = (float)(vstops.axisstops[vstops.axisstops.length-1]);
				
				ticks.hstops=hstops;
				ticks.vstops=vstops;
						
			}
			else
			{
				//DONE
				
				
				getVTicksBar(tasks,1,mBarWidth,mBarSpacing,0);
				
				ticks.nVticks = tasks.getViewportHeight();
				
				
				ticks.nvLabelWidth=(int)mVLabelWidth;
				
				
				
				IntervalStats stats = tasks.determineReqTicksYBar(false, 
						graphMax,graphMin,val);
				
				ticks.subLabelHeight = tasks.getNewLabelHeight();
				ticks.nLabelWidth = tasks.getNewLabelWidth(); 
				
				computeWidthAndHeightBar(ticks.nvLabelWidth,ticks.nvLabelWidth, bxh, bxw);
				
				ticks.nHticks = stats.nHticks;
				MinMax YData =cmp.computeMaxMinRangeDouble(val,stats.hinterval,ticks.nHticks);
				
				
				//MinMax YData =cmp.computeMaxMinRange(val,stats.hinterval,ticks.nHticks);
				cmp.computeStopsCategory(unique.length,vstops);
				//cmp.computeStopsNum(stats.hRticks,hstops,YData.graphMin,YData.graphMax,stats.hinterval);
				
				cmp.computeStopsNumRoundedBar(stats.hRticks,hstops,YData.graphMin,YData.graphMax,stats.hinterval);
				
				double hInterval= stats.hinterval;
				
				ticks.hInterval=hInterval;
			
				
				ticks.isHflipped=true;
				
				ticks.isVflipped=true;
				
				ticks.Viewport = new RectF((float)(YData.graphMin),
						0,(float)(YData.graphMax),vstops.axisstops.length);
				
				ticks.AXIS_H_MIN = (float)(hstops.axisstops[0]);
				ticks.AXIS_H_MAX = (float)(hstops.axisstops[hstops.axisstops.length-1]);
				ticks.AXIS_V_MIN = (float)(vstops.axisstops[0]);
				ticks.AXIS_V_MAX = (float)(vstops.axisstops[vstops.axisstops.length-1]+1);
				
				ticks.hstops=hstops;
				ticks.vstops=vstops;
				
				for(int i=0; i< ticks.hstops.axisstops.length;i++)
				{
					System.out.println("HSTOP"+ hstops.axisstops[i]);
				}
				
				for(int i=0; i< ticks.vstops.axisstops.length;i++)
				{
					System.out.println("VSTOP"+ vstops.axisstops[i]);
				}
				
				
				
			}
			
			return ticks;
		
	}
	
}

   