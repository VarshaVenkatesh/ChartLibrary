package com.wechseltech.chartlibrary;

import android.graphics.RectF;

public class ReturnIntervalsTicks {

	double hInterval,hLinterval,hRinterval
	;
	double vInterval,vLinterval,vRinterval;
	RectF Viewport,coordbounds,legendBox;
	int nVticks; int nHticks;
	int nLabelHeight;
	float hspace,vspace;
	int nhLabelWidth,subLabelHeight,newLabelWidth,nvLabelWidth,nLabelWidth;
	float AXIS_H_MAX,AXIS_H_MIN, AXIS_V_MAX,AXIS_V_MIN;
	AxisStops hstops,vstops;
	Boolean isHflipped,isVflipped;
	double LGraphMin,LGraphMax,RGraphMax,RGraphMin;
}
