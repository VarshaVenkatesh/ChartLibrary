package com.wechseltech.chartlibrary;

import java.util.Arrays;
import java.util.List;

public class OptimizeTicks {

	int mLabelWidth,mLabelHeight,
	minHLabelSize,minVLabelSize;
	float width,height;
	int viewPortWidth,viewPortHeight;
	float fullHeight;
	double hGraphMin,hGraphMax;
	double vGraphMin,vGraphMax;
	
	float hInterval,vInterval,space;
	int newLabelHeight,newLabelWidth;
	float axisLabelSpace,fullwidth;
	
	
	public OptimizeTicks(int mLabelWidth, int mLabelHeight,float width,
			float height,int minHLabelSize,int minVLabelSize,
			float fullHeight,float axisLabelSpace,List<Item> series)
	
	{
		this.mLabelWidth=mLabelWidth;
		this.mLabelHeight=mLabelHeight;
		this.minHLabelSize=minHLabelSize;
		this.minVLabelSize=minVLabelSize;
		this.width=width;
		this.height=height;
		this.fullHeight=fullHeight-axisLabelSpace;
		
	}
	
	public OptimizeTicks(int mLabelWidth, int mLabelHeight,float width,
			float height,int minHLabelSize,int minVLabelSize,
			float fullHeight,float fullwidth,float axisLabelSpace,float legendSize)
	
	{
		this.mLabelWidth=mLabelWidth;
		this.mLabelHeight=mLabelHeight;
		this.minHLabelSize=minHLabelSize;
		this.minVLabelSize=minVLabelSize;
		this.width=width;
		this.height=height;
		this.fullHeight=fullHeight-axisLabelSpace-legendSize;
		this.fullwidth = fullwidth;
		
	}
	
	
	
	public boolean determineVTicksBar(int numseries,float mBarWidth, float mBarSpacing,float iBarSpacing)
	{
		space = (numseries*mBarWidth)+(numseries*iBarSpacing)+(2*mBarSpacing);
		int nVticks = (int)Math.floor((height-(space/2))/space);
		//viewPortHeight=nVticks+1;
		
		viewPortHeight=nVticks+1;
		
		System.out.println("SPACE" + space+"TICKS"+ nVticks +"HT" +height);
		boolean vFlipped = determineVLabelWidth();
		
		return vFlipped;
	}
	
	public boolean determineVTicksNegative(int numseries,float mBarWidth, float mBarSpacing,float iBarSpacing)
	{
		space = (numseries*mBarWidth)+(numseries*iBarSpacing)+(2*mBarSpacing);
		int nVticks = (int)Math.floor((height-(space/2))/space);
		viewPortHeight=nVticks+1;
		
		boolean vFlipped = false;
		
		return vFlipped;
	}
	
	public boolean determineHTicksBar(int numseries ,float mBarWidth, float mBarSpacing,float iBarSpacing)
	{
		
		
		space = (numseries*mBarWidth)+(numseries*iBarSpacing)+(2*mBarSpacing);

		System.out.println("SPACE" + space + width);
		
		int nHticks = (int)Math.floor((width-(space/2))/space);
		viewPortWidth=nHticks+1;
		
		System.out.println("nHTICKS" + nHticks);
		
		boolean isHFlipped = determineHLabelWidth();
		
		return isHFlipped;
	}
	
	
	
	
	
	
	public boolean determineVLabelWidth()
	{
		if(space>mLabelWidth)
		{
			
			System.out.println("SPACE");
			int tLabelWidth = mLabelHeight;
			//Flip Label
			
			int hLabelWidth=mLabelWidth;
			int hLabelHeight=tLabelWidth;
			
			
			
			float nWidth = fullwidth-hLabelWidth;
			determineNumHTicks(nWidth);
			
			newLabelWidth = hLabelWidth;
			newLabelHeight = mLabelHeight;
			
			return true;
	
		}
		
		
		else
			
		{
			
			newLabelWidth = mLabelHeight;
			newLabelHeight = mLabelWidth;
			
			System.out.println("FLIP SPACE" + newLabelHeight + newLabelWidth);
			//return false;
			
			return true;
			
		}

	}
	
	public void determineNumHTicks(float width)
	{
		int nHticks = (int)Math.floor((width-(mLabelWidth/2))/(mLabelWidth));
		
		
	
		viewPortWidth = nHticks+1;
			

	}
	
	public boolean determineHLabelWidth()
	{
		if(space>mLabelWidth)
		{
			
				System.out.println("MLABELWIDTH" + mLabelWidth + "SPACE" + space);
				int tLabelWidth = mLabelWidth;
				//Flip Label
				
				int hLabelWidth=mLabelHeight;
				int hLabelHeight=tLabelWidth;
				
				
				
				System.out.println("HLABELHEIGHT" + tLabelWidth + "HLABELWIDTH" +hLabelWidth +"FULLHT"
						+ fullHeight);
				
				float nHeight = fullHeight-hLabelHeight;
				if(!determineNumVTicks(nHeight))
				{
					newLabelWidth = hLabelWidth;
					newLabelHeight = hLabelHeight;
					return true;
					
				
				}
				else
				{
					
					newLabelHeight = mLabelHeight;
					newLabelWidth = mLabelWidth;
					
					return false;
					
				}
				
			
			
			
		}
		
		else
		{
			
			System.out.println("NEWLABELWIDTH" + mLabelWidth + "SPACE" + space);
			
			newLabelHeight = mLabelHeight;
			newLabelWidth = mLabelWidth;
			return false;
		}
		
	}
	
	public IntervalStats determineAvgSeparation(double[] xval,double[] yval)
	
	{
//		double[] xval = new double[series.size()];
//		double [] yval = new double[series.size()];
//		for(int i=0; i<series.size();i++)
//		{
//			xval[i] = series.get(i).getXVal();
//			yval[i] = series.get(i).getYVal();
//			
//		}
//		
		Arrays.sort(xval);
		Arrays.sort(yval);
		
		
		
			
			double sumdiffX =0;
			double sumdiffY =0;
			for(int i=0; i< xval.length-1;i++)
			{
				sumdiffX = (sumdiffX + Math.abs(xval[i+1]-xval[i]));
				sumdiffY = (sumdiffY + Math.abs(yval[i+1]-yval[i]));
			}
			
			double avgdiffX =sumdiffX/(xval.length-1);
			
			double avgdiffY = sumdiffY/(yval.length-1);
			
			
			IntervalStats stats = new IntervalStats();
			stats.xavgdiff = avgdiffX;
			stats.yavgdiff=avgdiffY;
			
			return stats;
		
	}
	
	
	public IntervalStats determineAvgSeparationY(double[] yval)
	{
		
//		double[] yval = new double[series.size()];
//		
//		for(int i=0; i<series.size();i++)
//		{
//			yval[i] = series.get(i).getYVal();	
//		}
//		
		Arrays.sort(yval);
		
		double sumdiffY =0;
			
		for(int i=0; i< yval.length-1;i++)
		{
			sumdiffY = (sumdiffY + Math.abs(yval[i+1]-yval[i]));
			
		}
		
		System.out.println("SUM" + sumdiffY);
			
		double avgdiffY =sumdiffY/(yval.length-1);
		
		IntervalStats stats = new IntervalStats();
		stats.yavgdiff = avgdiffY;
		
			
		System.out.println("YAVGDIFF" + sumdiffY);
		return stats;
	}
	
	public IntervalStats determineAvgSeparationX(double [] xval)
	{
		
//		double[] xval = new double[series.size()];
//		
//		for(int i=0; i<series.size();i++)
//		{
//			xval[i] = series.get(i).getXVal();	
//		}
//		
		Arrays.sort(xval);
		
		double sumdiffX =0;
			
		for(int i=0; i< xval.length-1;i++)
		{
			sumdiffX = (sumdiffX + Math.abs(xval[i+1]-xval[i]));
			
		}
			
		double avgdiffX =sumdiffX/(xval.length-1);
		
		IntervalStats stats = new IntervalStats();
		stats.xavgdiff = avgdiffX;
		
			
		return stats;
	}

	
	
	
	
	
	
	/*public IntervalStats determineReqTicksX(boolean XisHorizontal,
			double XGraphMax, double XGraphMin,double[] xval)
	{

		IntervalStats stats = determineAvgSeparationX(xval);
		double avgsepH=0;
		double avgsepV=0;
		
		if(XisHorizontal)
		{
			double hrange = niceNumeral(XGraphMax -XGraphMin);
			avgsepH= stats.xavgdiff;
			int hRTicks = (int)(hrange/niceNumeral(avgsepH))+1;
			stats.hRticks = hRTicks;
			stats.hFlipped=determineNumHTicks();
			stats.nHticks=getViewportWidth()-1;
			stats.hinterval=niceNumeral(hrange/(stats.hRticks-1));
			System.out.println("H INterval" + stats.hinterval);
			
		
		}
		else
		{
			double vrange = niceNumeral(XGraphMax -XGraphMin);
			avgsepV= stats.xavgdiff;
			int vRTicks = (int)(vrange/niceNumeral(avgsepV))+1;
			stats.vRticks = vRTicks;
			determineNumVTicks(fullHeight-newLabelHeight);
			stats.nVticks=getViewportHeight()-1;
			stats.vinterval=niceNumeral(vrange/(stats.vRticks-1));
			
			System.out.println("V INterval" + stats.vinterval);
			
			
		
		}

		return stats;
		
		
		
	}*/
	
	
	public IntervalStats determineReqTicksX(boolean XisHorizontal,
			double XGraphMax, double XGraphMin,double[] xval)
	{

		IntervalStats stats = determineAvgSeparationX(xval);
		double avgsepH=0;
		double avgsepV=0;
		
		if(XisHorizontal)
		{
			
			avgsepH= niceNumeral(stats.xavgdiff);
			
			
			double xgraphMin =  (Math.floor(XGraphMin/avgsepH)*avgsepH);
			double xgraphMax = (Math.ceil(XGraphMax/avgsepH)*avgsepH);
			
			
			
			double hrange =xgraphMax -xgraphMin;
			int hRTicks = (int)Math.ceil(hrange/avgsepH);
			stats.hRticks = hRTicks;
			stats.hFlipped=determineNumHTicks();
			stats.nHticks=getViewportWidth()-1;
			stats.hinterval=niceNumeral(hrange/(stats.hRticks));
			System.out.println("H INterval" + stats.hinterval);
			
		
		}
		else
		{
			
			avgsepV= niceNumeral(stats.xavgdiff);
			
			
			double xgraphMin = (Math.floor(XGraphMin/avgsepV)*avgsepV);
			double xgraphMax = (Math.ceil(XGraphMax/avgsepV)*avgsepV);
			
			double vrange = xgraphMax -xgraphMin;
			int vRTicks = (int)Math.ceil(vrange/avgsepV);
			stats.vRticks = vRTicks;
			stats.vFlipped=determineNumVTicks(fullHeight-newLabelHeight);
			stats.nVticks=getViewportHeight()-1;
			stats.vinterval=niceNumeral(vrange/(stats.vRticks));
			
			System.out.println("V INterval" + stats.vinterval);
			
			
		
		}

		return stats;
		
		
		
	}
	
	
	/*public class IntervalStatsNegative
	{
		double hLinterval,hRinterval;
		double vLinterval,vRinterval;
		double xavgdiff;
		double yavgdiff;
		int hLRticks,hRRticks;
		int vLRticks,vRRticks;
		int nHticks;
		int nVticks;
		boolean hFlipped,vFlipped;
		
	
	}*/
	
	public IntervalStatsNegative determineReqTicksYNegative(boolean YisVertical,
			double LGraphMax, 
			double LGraphMin,double[] lval,
			double RGraphMax,
			double RGraphMin,double[] rval,
			float offset,float height)
	{

		IntervalStats lstats = determineAvgSeparationY(lval);
		IntervalStats rstats = determineAvgSeparationY(rval);
		double avgsepLH=0,avgsepRH=0;
		double avgsepLV=0,avgsepRV=0;
		
		IntervalStatsNegative negstats = new IntervalStatsNegative();
		
		if(!YisVertical)
		{
			
			negstats.hFlipped=determineNumHTicks();
			negstats.nHticks=getViewportWidth()-1;
			
			
			avgsepLH= niceNumeral(lstats.yavgdiff);
			avgsepRH= niceNumeral(rstats.yavgdiff);
			
			
			//avgsepH= niceNumeral(stats.yavgdiff);
			double lgraphMin = (float) (Math.floor(LGraphMin/avgsepLH)*avgsepLH);
			double lgraphMax = (float)(Math.ceil(LGraphMax/avgsepLH)*avgsepLH);
			
			double rgraphMin = (float) (Math.floor(RGraphMin/avgsepRH)*avgsepRH);
			double rgraphMax = (float)(Math.ceil(RGraphMax/avgsepRH)*avgsepRH);
			
			double lrange = lgraphMax -lgraphMin;
			int hLRTicks = (int)(lrange/avgsepLH);
			
			double rrange = rgraphMax -rgraphMin;
			int hRRTicks = (int)(rrange/avgsepRH);
			
			negstats.hLRticks = hLRTicks;
			negstats.hRRticks = hRRTicks;
			negstats.hLinterval=niceNumeral(lrange/(negstats.hLRticks));
			negstats.hRinterval=niceNumeral(rrange/(negstats.hRRticks));
			
			

			System.out.println("HL" + negstats.hLRticks + negstats.hLinterval );
			System.out.println("HR" + negstats.hRRticks + negstats.hRinterval );
			
		/*	double lrange = niceNumeral(LGraphMax -LGraphMin);
			double rrange = niceNumeral(RGraphMax -RGraphMin);
			avgsepLH= lstats.yavgdiff;
			avgsepRH= rstats.yavgdiff;
			int hLRTicks = (int)(lrange/niceNumeral(avgsepLH))+1;
			int hRRTicks = (int)(rrange/niceNumeral(avgsepRH))+1;
			negstats.hLRticks = hLRTicks;
			negstats.hRRticks = hRRTicks;
			negstats.hLinterval=niceNumeral(lrange/(negstats.hLRticks-1));
			negstats.hRinterval=niceNumeral(rrange/(negstats.hRRticks-1));*/
			
			
		
		}
		else
		{
			/*double lrange = niceNumeral(LGraphMax -LGraphMin);
			double rrange = niceNumeral(RGraphMax -RGraphMin);
			
			avgsepLV= lstats.yavgdiff;
			avgsepRV= rstats.yavgdiff;
	
			int vLRTicks = (int)(lrange/niceNumeral(avgsepLV))+1;
			int vRRTicks = (int)(rrange/niceNumeral(avgsepRV))+1;
			negstats.vLRticks = vLRTicks;
			negstats.vRRticks = vRRTicks;
			determineNumVTicks(height-offset-newLabelHeight);
			negstats.nVticks=getViewportHeight()-1;
			
			
			negstats.vLinterval=niceNumeral(lrange/(negstats.vLRticks-1));
			negstats.vRinterval=niceNumeral(rrange/(negstats.vRRticks-1));*/
			
	/*		negstats.hFlipped=determineNumHTicks();
			negstats.nHticks=getViewportWidth()-1;*/
			
			
			avgsepLV= niceNumeral(lstats.yavgdiff);
			avgsepRV= niceNumeral(rstats.yavgdiff);
			
			
			//avgsepH= niceNumeral(stats.yavgdiff);
			double lgraphMin = (float) (Math.floor(LGraphMin/avgsepLV)*avgsepLV);
			double lgraphMax = (float)(Math.ceil(LGraphMax/avgsepLV)*avgsepLV);
			
			double rgraphMin = (float) (Math.floor(RGraphMin/avgsepRV)*avgsepRV);
			double rgraphMax = (float)(Math.ceil(RGraphMax/avgsepRV)*avgsepRV);
			
			double lrange = lgraphMax -lgraphMin;
			int vLRTicks = (int)(lrange/avgsepLV);
			
			double rrange = rgraphMax -rgraphMin;
			int vRRTicks = (int)(rrange/avgsepRV);
			
			negstats.vLRticks = vLRTicks;
			negstats.vRRticks = vRRTicks;
			negstats.vLinterval=niceNumeral(lrange/(negstats.vLRticks));
			negstats.vRinterval=niceNumeral(rrange/(negstats.vRRticks));
			
			negstats.vFlipped=determineNumVTicks(height-offset-newLabelHeight);
			negstats.nVticks=getViewportHeight()-1;
			
			
			System.out.println("VL" + negstats.vLRticks + negstats.vLinterval );
			System.out.println("VR" + negstats.vRRticks + negstats.vRinterval );
		
		}

		return negstats;
		
		
		
	}
	
	public IntervalStatsNegative determineReqTicksXNegative(boolean XisHorizontal,
			double LGraphMax, 
			double LGraphMin,double[] lval,
			double RGraphMax,
			double RGraphMin,double[] rval,
			float xoffset,float Width)
	{

		
		IntervalStats lstats = determineAvgSeparationX(lval);
		IntervalStats rstats = determineAvgSeparationX(rval);
		
		IntervalStatsNegative negstats = new IntervalStatsNegative();
		double avgsepLH,avgsepRH=0;
		double avgsepLV=0,avgsepRV=0;
		
		if(XisHorizontal)
		{
			
			
			avgsepLH= niceNumeral(lstats.xavgdiff);
			avgsepRH= niceNumeral(rstats.xavgdiff);
			
			
			double lgraphMin = (float) (Math.floor(LGraphMin/avgsepLH)*avgsepLH);
			double lgraphMax = (float)(Math.ceil(LGraphMax/avgsepLH)*avgsepLH);
			
			double rgraphMin = (float) (Math.floor(RGraphMin/avgsepRH)*avgsepRH);
			double rgraphMax = (float)(Math.ceil(RGraphMax/avgsepRH)*avgsepRH);
			
		
			double lrange = lgraphMax -lgraphMin;
			double rrange = rgraphMax -rgraphMin;
			
			int hLRTicks = (int)(lrange/avgsepLH);
			int hRRTicks = (int)(rrange/avgsepRH);
			negstats.hLRticks = hLRTicks;
			negstats.hRRticks = hRRTicks;
			negstats.hFlipped=determineNumHTicksNegative(Width-2*xoffset);
			negstats.nHticks=getViewportWidth()-1;
			negstats.hLinterval=niceNumeral(lrange/(negstats.hLRticks));
			negstats.hRinterval=niceNumeral(rrange/(negstats.hRRticks));
			
			System.out.println("H INterval" + negstats.hLinterval);
			
		
		}
		else
		{
			
			avgsepLV= niceNumeral(lstats.xavgdiff);
			avgsepRV= niceNumeral(rstats.xavgdiff);
			
			
			double lgraphMin = (float) (Math.floor(LGraphMin/avgsepLV)*avgsepLV);
			double lgraphMax = (float)(Math.ceil(LGraphMax/avgsepLV)*avgsepLV);
			
			double rgraphMin = (float) (Math.floor(RGraphMin/avgsepRV)*avgsepRV);
			double rgraphMax = (float)(Math.ceil(RGraphMax/avgsepRV)*avgsepRV);
			
			double lrange = lgraphMax -lgraphMin;
			double rrange = rgraphMax -rgraphMin;
			
			int vLRTicks = (int)(lrange/avgsepLV);
			int vRRTicks = (int)(rrange/avgsepRV);
			negstats.vLRticks = vLRTicks;
			negstats.vRRticks = vRRTicks;
			float space=fullHeight-(2*axisLabelSpace);
			determineNumVTicks(space-newLabelHeight);
			negstats.nVticks=getViewportHeight()-1;
			negstats.vLinterval=niceNumeral(lrange/(negstats.vLRticks));
			negstats.vRinterval=niceNumeral(rrange/(negstats.vRRticks));
			
			System.out.println("V INterval" + negstats.vLinterval);
			System.out.println("LGMIN" + lgraphMin + lgraphMax);
			System.out.println("RGMIN" + rgraphMin + rgraphMax);
			
			
		
		}

		return negstats;
		
		
		
	}
	
	private boolean determineNumHTicksNegative(float width) {
		
		int nHticks = (int)Math.floor((width-(mLabelWidth/2))/(mLabelWidth));
		
		
		if(nHticks>=4)
		{
			viewPortWidth = nHticks+1;
			newLabelHeight = mLabelHeight;
			return true;
		}
		
		//Atleast get 4 ticks in to start with by changing the label width
		else 
		{
			int nLabelWidth = (int) (2*width/9);
			if(nLabelWidth<minHLabelSize)
			{
				int tLabelWidth = mLabelWidth;
				//Flip Label
				
				int hLabelWidth=mLabelHeight;
				int hLabelHeight=tLabelWidth;
				
				
				
				float nHeight = fullHeight-hLabelHeight;
				if(determineNumVTicks(nHeight))
				{
					mLabelWidth = hLabelWidth;
					newLabelWidth = nLabelWidth;
					newLabelHeight = hLabelHeight;
					int hticks = (int)Math.floor((width-(mLabelWidth/2))/(mLabelWidth));
					viewPortWidth = hticks+1;
					return true;
				}
				else
				{
					viewPortWidth = nHticks+1;
					newLabelHeight = mLabelHeight;
					return false;
				}
				
			
			}
			
			else
			{
				mLabelWidth = nLabelWidth;
				newLabelHeight = mLabelHeight;
				return true;
				
			}
			
		}
		
	
	}

	public IntervalStats determineReqTicksYBar(boolean YisVertical,
			double YGraphMax, double YGraphMin,double[] yval)
	{

		
		IntervalStats stats = determineAvgSeparationY(yval);
		double avgsepH=0;
		double avgsepV=0;
		
		
		if(!YisVertical)
		{
			
			determineNumHTicks(fullwidth-newLabelWidth);
			stats.nHticks=getViewportWidth()-1;
			avgsepH= niceNumeral(stats.yavgdiff);
//			double hgraphMin = (float) (Math.floor(YGraphMin/avgsepH)*avgsepH);
//			double hgraphMax = (float)(Math.ceil(YGraphMax/avgsepH)*avgsepH);
			
			double hgraphMin = (Math.floor(YGraphMin/avgsepH)*avgsepH);
			double hgraphMax = (Math.ceil(YGraphMax/avgsepH)*avgsepH);
			
			System.out.println("GMin" + hgraphMin+"GMax" +hgraphMax  + "AVG" + avgsepH);
			
			
			double hrange = hgraphMax -hgraphMin;
			int hRTicks = (int)(hrange/avgsepH);
			
			stats.hRticks = hRTicks;
			
			System.out.println("hRTICKS" + stats.hRticks);
			stats.hinterval=niceNumeral(hrange/(stats.hRticks));
			
			/*double hrange = niceNumeral(YGraphMax -YGraphMin);
			avgsepH= stats.yavgdiff;
			int hRTicks = (int)(hrange/niceNumeral(avgsepH))+1;
			stats.hRticks = hRTicks;
			stats.hinterval=niceNumeral(hrange/(stats.hRticks-1));*/
			
			
			
			
		
		}
		else
		{
			
			
			
			
			
			avgsepV= niceNumeral(stats.yavgdiff);
			
			/*double vgraphMin = (float) (Math.floor(YGraphMin/avgsepV)*avgsepV);
			double vgraphMax = (float)(Math.ceil(YGraphMax/avgsepV)*avgsepV);*/
			
			double vgraphMin = (Math.floor(YGraphMin/avgsepV)*avgsepV);
			double vgraphMax = (Math.ceil(YGraphMax/avgsepV)*avgsepV);
			
			System.out.println("GMin" + vgraphMin+"GMax" +vgraphMax  + "AVG" + avgsepV);
			double vrange = (vgraphMax -vgraphMin);
			int vRTicks = (int)Math.ceil((double)vrange/avgsepV);
			
			System.out.println("VRANGE" + vrange);
			
			stats.vRticks = vRTicks;
			
			System.out.println("Ticks" + stats.vRticks);
			
			
			determineNumVTicks(fullHeight-newLabelHeight);
			
			stats.nVticks=getViewportHeight()-1;
			
			
			stats.vinterval=niceNumeral(vrange/(stats.vRticks));
			
			System.out.println("Interval" + stats.vinterval);
			
		/*	double vrange = niceNumeral(YGraphMax -YGraphMin);
			
			avgsepV= stats.yavgdiff;
			
			System.out.println("AVG SEP" + avgsepV + "RANGE" + (YGraphMax -YGraphMin));
			
			int vRTicks = (int)(vrange/niceNumeral(avgsepV))+1;
			stats.vRticks = vRTicks;
			determineNumVTicks(fullHeight-newLabelHeight);
			stats.nVticks=getViewportHeight()-1;
			
			
			stats.vinterval=niceNumeral(vrange/(stats.vRticks-1));*/
		
		}

		return stats;
		
		
		
	}
	
	public IntervalStats determineReqTicksXBar(boolean XisHorizontal,
			double XGraphMax, double XGraphMin,double[] xval)
	{

		IntervalStats stats = determineAvgSeparationX(xval);
		double avgsepH=0;
		double avgsepV=0;
		
		if(XisHorizontal)
		{
			
			avgsepH= niceNumeral(stats.xavgdiff);
			
			
			double hgraphMin = (Math.floor(XGraphMin/avgsepH)*avgsepH);
			double hgraphMax = (Math.ceil(XGraphMax/avgsepH)*avgsepH);
			
			
			System.out.println("GMAX"+ hgraphMax);
			System.out.println("GMIN"+ hgraphMin);
			System.out.println("GMIN"+ "SEP" +  niceNumeral(hgraphMax -hgraphMin));
			
			

			
			
			//double hrange = niceNumeral(hgraphMax -hgraphMin);
			
			double hrange = hgraphMax -hgraphMin;
			
			System.out.println("RANGE"+ hrange);
			System.out.println("SEP"+ avgsepH);
			
			
			int hRTicks = (int)Math.ceil((hrange/avgsepH));
			stats.hRticks = hRTicks;
			
			System.out.println("HR"+ hRTicks + "NEWLB" + newLabelWidth);
			
			
			determineNumHTicks(fullwidth-newLabelWidth);
			stats.nHticks=getViewportWidth()-1;
			stats.hinterval=niceNumeral(hrange/(stats.hRticks));
			
			
		
		}
		else
		{
			
			
			
			avgsepV= niceNumeral(stats.xavgdiff);
			
			double vgraphMin = (Math.floor(XGraphMin/avgsepV)*avgsepV);
			double vgraphMax = (Math.ceil(XGraphMax/avgsepV)*avgsepV);
			
			
			
			System.out.println("GMAX"+ vgraphMax);
			System.out.println("GMIN"+ vgraphMin);
			
			
			//double vrange = niceNumeral(vgraphMax -vgraphMin);
			
			
			double vrange = vgraphMax -vgraphMin;
			System.out.println("RANGE"+ vrange);
			
			
			int vRTicks = (int)Math.ceil((vrange/avgsepV));
			stats.vRticks = vRTicks;
			determineNumVTicks(fullHeight-newLabelHeight);
			stats.nVticks=getViewportHeight()-1;
			stats.vinterval=niceNumeral(vrange/(stats.vRticks));
			
			
			
			
		
		}

		return stats;
		
		
		
	}
	
	
	public IntervalStats determineReqTicksY(boolean YisVertical,
			double YGraphMax, double YGraphMin,double[] yval)
	{

		IntervalStats stats = determineAvgSeparationY(yval);
		double avgsepH=0;
		double avgsepV=0;
		
		if(!YisVertical)
		{
			
			
			avgsepH= niceNumeral(stats.yavgdiff);
			
			double ygraphMin = (Math.floor(YGraphMin/avgsepH)*avgsepH);
			double ygraphMax = (Math.ceil(YGraphMax/avgsepH)*avgsepH);
			
			
			
			stats.hFlipped=determineNumHTicks();
			stats.nHticks=getViewportWidth()-1;
			double hrange = ygraphMax -ygraphMin;
			
			
			
			int hRTicks = (int)Math.ceil(hrange/avgsepH);
			stats.hRticks = hRTicks;
			stats.hinterval=niceNumeral(hrange/(stats.hRticks));
			
			
		
		}
		else
		{
			
			
			avgsepV= niceNumeral(stats.yavgdiff);
			
			double ygraphMin =  (Math.floor(YGraphMin/avgsepV)*avgsepV);
			double ygraphMax = (Math.ceil(YGraphMax/avgsepV)*avgsepV);
			
			double vrange = ygraphMax -ygraphMin;
	
			int vRTicks = (int)Math.ceil(vrange/avgsepV);
			stats.vRticks = vRTicks;
			stats.vFlipped=determineNumVTicks(fullHeight-newLabelHeight);
			stats.nVticks=getViewportHeight()-1;
			
			
			stats.vinterval=niceNumeral(vrange/(stats.vRticks));
		
		}

		return stats;
		
		
		
	}
	
	
	
	/*public IntervalStats determineReqTicksY(boolean YisVertical,
			double YGraphMax, double YGraphMin,double[] yval)
	{

		IntervalStats stats = determineAvgSeparationY(yval);
		double avgsepH=0;
		double avgsepV=0;
		
		if(!YisVertical)
		{
			
			stats.hFlipped=determineNumHTicks();
			stats.nHticks=getViewportWidth()-1;
			double hrange = niceNumeral(YGraphMax -YGraphMin);
			avgsepH= stats.yavgdiff;
			int hRTicks = (int)(hrange/niceNumeral(avgsepH))+1;
			stats.hRticks = hRTicks;
			stats.hinterval=niceNumeral(hrange/(stats.hRticks-1));
			
			
		
		}
		else
		{
			double vrange = niceNumeral(YGraphMax -YGraphMin);
			
			avgsepV= stats.yavgdiff;
	
			int vRTicks = (int)(vrange/niceNumeral(avgsepV))+1;
			stats.vRticks = vRTicks;
			determineNumVTicks(fullHeight-newLabelHeight);
			stats.nVticks=getViewportHeight()-1;
			
			
			stats.vinterval=niceNumeral(vrange/(stats.vRticks-1));
		
		}

		return stats;
		
		
		
	}*/
	
	
	
	/*public IntervalStats determineReqTicks(boolean XisHorizontal,double[]xval,double[] yval)
	
	{
		
		double hrange = niceNumeral(hGraphMax -hGraphMin);
//		
		double vrange = niceNumeral(vGraphMax -vGraphMin);
		
//		double hrange = hGraphMax -hGraphMin;
//		
//		double vrange = vGraphMax -vGraphMin;
		
		
		IntervalStats stats = determineAvgSeparation(xval,yval);
		
		double avgsepH=0;
		double avgsepV=0;
		if(XisHorizontal)
		{
		avgsepH= stats.xavgdiff;
		avgsepV = stats.yavgdiff;
		}
		else
		{
		avgsepH= stats.yavgdiff;
		avgsepV = stats.xavgdiff;
		}
	
		int hRTicks = (int)(hrange/niceNumeral(avgsepH))+1;
	
		int vRTicks = (int)(vrange/niceNumeral(avgsepV))+1;
		
//		int hRTicks = (int)(hrange/avgsepH)+1;
//		
//		int vRTicks = (int)(vrange/avgsepV)+1;
		
		stats.hRticks = hRTicks;
		stats.vRticks = vRTicks;
		
	
		
		boolean hflipped = determineNumHTicks();
		
		stats.nHticks=getViewportWidth()-1;
		
		boolean vFlipped = determineNumVTicks(fullHeight-newLabelHeight);
	
		stats.nVticks=getViewportHeight()-1;
		
		stats.hinterval=niceNumeral(hrange/(stats.hRticks-1));
		stats.vinterval=niceNumeral(vrange/(stats.vRticks-1));
		stats.hFlipped=hflipped;
		stats.vFlipped = vFlipped;
		return stats;
	}*/
	
	
	
public IntervalStats determineReqTicks(boolean XisHorizontal,double[]xval,double[] yval)
	
	{
		
		
		
//		double hrange = hGraphMax -hGraphMin;
//		
//		double vrange = vGraphMax -vGraphMin;
		
		
		IntervalStats stats = determineAvgSeparation(xval,yval);
		
		double avgsepH=0;
		double avgsepV=0;
		if(XisHorizontal)
		{
		avgsepH= niceNumeral(stats.xavgdiff);
		avgsepV = niceNumeral(stats.yavgdiff);
		}
		else
		{
		avgsepH= niceNumeral(stats.yavgdiff);
		avgsepV = niceNumeral(stats.xavgdiff);
		}
		
		
		double hgraphMin = (float) (Math.floor(hGraphMin/avgsepH)*avgsepH);
		double hgraphMax = (float)(Math.ceil(hGraphMax/avgsepH)*avgsepH);
		
		double vgraphMin = (float) (Math.floor(vGraphMin/avgsepV)*avgsepV);
		double vgraphMax = (float)(Math.ceil(vGraphMax/avgsepV)*avgsepV);
		
		
		
		double hrange = hgraphMax -hgraphMin;
//		
		double vrange = vgraphMax -vgraphMin;
	
		int hRTicks = (int)(hrange/avgsepH);
	
		int vRTicks = (int)(vrange/avgsepV);
		
//		int hRTicks = (int)(hrange/avgsepH)+1;
//		
//		int vRTicks = (int)(vrange/avgsepV)+1;
		
		stats.hRticks = hRTicks;
		stats.vRticks = vRTicks;
		
	
		
		boolean hflipped = determineNumHTicks();
		
		stats.nHticks=getViewportWidth()-1;
		
		boolean vFlipped = determineNumVTicks(fullHeight-newLabelHeight);
	
		stats.nVticks=getViewportHeight()-1;
		
		stats.hinterval=niceNumeral(hrange/(stats.hRticks));
		stats.vinterval=niceNumeral(vrange/(stats.vRticks));
		stats.hFlipped=hflipped;
		stats.vFlipped = vFlipped;
		return stats;
	}
	public IntervalStats determineReqTicksRounded(boolean XisHorizontal,double[]xval,double[] yval)
	
	{
		
		
		IntervalStats stats = determineAvgSeparation(xval,yval);
		
		double avgsepH=0;
		double avgsepV=0;
		if(XisHorizontal)
		{
		avgsepH= niceNumeral(stats.xavgdiff);
		avgsepV = niceNumeral(stats.yavgdiff);
		}
		else
		{
		avgsepH= niceNumeral(stats.yavgdiff);
		avgsepV = niceNumeral(stats.xavgdiff);
		}
	
		double hgraphMin = (Math.floor(hGraphMin/avgsepH)*avgsepH);
		double hgraphMax = (Math.ceil(hGraphMax/avgsepH)*avgsepH);
		
		double vgraphMin = (Math.floor(vGraphMin/avgsepV)*avgsepV);
		double vgraphMax = (Math.ceil(vGraphMax/avgsepV)*avgsepV);
		
		double hrange = hgraphMax -hgraphMin;
//	
		double vrange = vgraphMax -vgraphMin;
		
		
		int hRTicks = (int)Math.ceil(hrange/avgsepH);
	
		int vRTicks = (int)Math.ceil(vrange/avgsepV);
		

		
		stats.hRticks = hRTicks;
		stats.vRticks = vRTicks;
		
	
		
		boolean hflipped = determineNumHTicks();
		
		stats.nHticks=getViewportWidth()-1;
		
		determineNumVTicks(fullHeight-newLabelHeight);
	
		stats.nVticks=getViewportHeight()-1;
		
		stats.hinterval=niceNumeral(hrange/(stats.hRticks));
		stats.vinterval=niceNumeral(vrange/(stats.vRticks));
		stats.hFlipped=hflipped;
		return stats;
	}
	
	
	public void setHGraphMinMax(double graphmin,double graphmax)
	{
		this.hGraphMax=graphmax;
		this.hGraphMin=graphmin;
		
	}
	
	public void setVGraphMinMax(double graphmin,double graphmax)
	{
		this.vGraphMax=graphmax;
		this.vGraphMin=graphmin;
		
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
	
	public boolean determineNumHTicks()
	{
		int nHticks = (int)Math.floor((width-(mLabelWidth/2))/(mLabelWidth));
		
		
		if(nHticks>=4)
		{
			viewPortWidth = nHticks+1;
			newLabelHeight = mLabelHeight;
			
			return false;
		}
		
		//Atleast get 4 ticks in to start with by changing the label width
		else 
		{
			int nLabelWidth = (int) (2*width/9);
			if(nLabelWidth<minHLabelSize)
			{
				int tLabelWidth = mLabelWidth;
				//Flip Label
				
				int hLabelWidth=mLabelHeight;
				int hLabelHeight=tLabelWidth;
				
				
				
				float nHeight = fullHeight-hLabelHeight;
				if(determineNumVTicks(nHeight))
				{
					mLabelWidth = hLabelWidth;
					newLabelWidth = nLabelWidth;
					newLabelHeight = hLabelHeight;
					int hticks = (int)Math.floor((width-(mLabelWidth/2))/(mLabelWidth));
					viewPortWidth = hticks+1;
					return true;
				}
				else
				{
					viewPortWidth = nHticks+1;
					newLabelHeight = mLabelHeight;
					return false;
				}
				
			
			}
			
			else
			{
				mLabelWidth = nLabelWidth;
				newLabelHeight = mLabelHeight;
				return false;
				
			}
			
		}
		
	}
//	//For category
//	public boolean determineNumHTicks()
//	{
//		int nHticks = (int)Math.floor((width-(mLabelWidth/2))/(mLabelWidth));
//		
//		
//		if(nHticks>=4)
//		{
//			viewPortWidth = nHticks+1;
//			newLabelHeight = mLabelHeight;
//			return true;
//		}
//		
//		//Atleast get 4 ticks in to start with by changing the label width
//		else 
//		{
//			int nLabelWidth = (int) (2*width/9);
//			if(nLabelWidth<minHLabelSize)
//			{
//				int tLabelWidth = mLabelWidth;
//				//Flip Label
//				
//				int hLabelWidth=mLabelHeight;
//				int hLabelHeight=tLabelWidth;
//				
//				
//				
//				float nHeight = fullHeight-hLabelHeight;
//				if(determineNumVTicks(nHeight))
//				{
//					mLabelWidth = hLabelWidth;
//					newLabelHeight = hLabelHeight;
//					viewPortWidth = 5;
//					return true;
//				}
//				else
//				{
//					viewPortWidth = nHticks+1;
//					newLabelHeight = mLabelHeight;
//					return false;
//				}
//				
//			
//			}
//			
//			else
//			{
//				mLabelWidth = nLabelWidth;
//				newLabelHeight = mLabelHeight;
//				return true;
//				
//			}
//			
//		}
//		
//	}
	
	public boolean determineNumVTicks(float ht)
	{
		//We choose 4*labelheight because this a bubble plot 
		//and we dont want the bubbles to overlap - if we need to have tags 
		//then we cut the max bubble radius to 1.5 and use the last label heigh to draw the tag
		
		
		int nVticks = (int)Math.floor((ht-(4*mLabelHeight/2))/(4*mLabelHeight));
		
		
		if(nVticks>=4)
		{
			viewPortHeight = nVticks+1;
			return false;
		}
		
		//Atleast get 4 ticks in to start with by changing the label width
		else 
		{
			int nLabelHeight = (int) (ht/18);
			if(nLabelHeight>=minVLabelSize)
			{
				mLabelHeight = nLabelHeight;
				int vticks = (int)Math.floor((width-(mLabelHeight/2))/(mLabelHeight));
				viewPortHeight = vticks+1;
				return true;
			}
			else
			{
				viewPortHeight = nVticks+1;
				
				return false;
			}
			
		}
		
		
		
	}
	
	
	
//	public boolean determineNumVTicks(float ht)
//	{
//		//We choose 4*labelheight because this a bubble plot 
//		//and we dont want the bubbles to overlap - if we need to have tags 
//		//then we cut the max bubble radius to 1.5 and use the last label heigh to draw the tag
//		
//		
//		int nVticks = (int)Math.floor((ht-(4*mLabelHeight/2))/(4*mLabelHeight));
//		
//		
//		if(nVticks>=4)
//		{
//			viewPortHeight = nVticks+1;
//			return true;
//		}
//		
//		//Atleast get 4 ticks in to start with by changing the label width
//		else 
//		{
//			int nLabelHeight = (int) (ht/18);
//			if(nLabelHeight>=minVLabelSize)
//			{
//				mLabelHeight = nLabelHeight;
//				viewPortHeight = 5;
//				return true;
//			}
//			else
//			{
//				viewPortHeight = nVticks+1;
//				return false;
//			}
//			
//		}
//		
//		
//		
//	}
	
	
	public void calculateNewHViewport()
	{
		
	}
	
	public void calculateNewVViewport()
	{
		
	}
	
	public int getViewportWidth()
	{
		return viewPortWidth;
	}
	
	public int getViewportHeight()
	{
		return viewPortHeight;
	}
	
	public int getLabelHeight()
	{
		return mLabelHeight;
	}
	
	public int getLabelWidth()
	{
		return mLabelWidth;
	}
	
	public int getNewLabelHeight()
	{
		return newLabelHeight;
	}
	
	public int getNewLabelWidth()
	{
		return newLabelWidth;
	}
	
	
	
}
