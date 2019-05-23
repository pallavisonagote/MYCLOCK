package com.example.myclock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.Calendar;

public class ClockView extends View {

	private int height,width=0;
	private int padding=0;
	private int fontsize=5;
	private int numeralSpacing;
	private int handTruncation,hrhandTruncation=0;
	private int radius=0;
	private Paint paint;
	private boolean isInit;
	private  int[] numbers={3,6,9,12};
	private Rect rect=new Rect();


	public ClockView(Context context) {
		super(context);
	}

	public ClockView(Context context,  AttributeSet attrs) {
		super(context, attrs);
	}

	public ClockView(Context context,  AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	private void inItClock(){
		height=getHeight();
		width=getWidth();
		padding= numeralSpacing=0;
		fontsize=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,15,
				getResources().getDisplayMetrics());

		int min=Math.min(height,width);
		radius=min/3-padding;
		handTruncation=min/10;
		hrhandTruncation=min/15;
		paint= new Paint();
		isInit=true;


	}

	@Override
	protected void onDraw(Canvas canvas){
		if (!isInit){
			inItClock();
		}

		canvas.drawColor(Color.BLACK);
		drawCircle(canvas);
		drawCenter(canvas);
		drawNumeral(canvas);
		drawHands(canvas);
		postInvalidateDelayed(500);
		invalidate();
	}
	private void drawHand(Canvas canvas,double loc,boolean isHour){
		double angle=Math.PI*loc/30-Math.PI/2;
		int handRadius=isHour ? radius-handTruncation-hrhandTruncation:radius-handTruncation;
		canvas.drawLine(width/2,height/2,
				(float)(width/2+Math.cos(angle)*handRadius),
				(float)(height/2+Math.sin(angle)*handRadius),paint);
	}


	private void drawHands(Canvas canvas) {

		Calendar calendar=Calendar.getInstance();
		float hr=calendar.get(Calendar.HOUR_OF_DAY);
		hr=hr>12?hr-12:hr;
		drawHand(canvas,(hr+calendar.get(Calendar.SECOND)/3600)*5f,true);
		drawHand(canvas,calendar.get(Calendar.MINUTE),false);
		drawHand(canvas,calendar.get(Calendar.SECOND),false);


	}

	private void drawNumeral(Canvas canvas) {

		paint.setTextSize(fontsize);

		for (int number:numbers){
			String tmp=String.valueOf(number);
			paint.getTextBounds(tmp ,0,tmp.length(),rect);
			double angle =Math.PI/6*(number-3);
			int x=(int)(width/2+Math.cos(angle)*radius-rect.width()/2);
			int y=(int)(height/2+Math.sin(angle)*radius+rect.height()/2);
			canvas.drawText(tmp,x,y,paint);


		}
	}

	private void drawCenter(Canvas canvas) {

		paint.setStyle(Paint.Style.FILL);
		canvas.drawCircle(width/2,height/2,12,paint);

	}

	private void drawCircle(Canvas canvas) {
			paint.reset();
			paint.setColor(getResources().getColor(android.R.color.white));
			paint.setStrokeWidth(5);
			paint.setStyle(Paint.Style.STROKE);
			paint.setAntiAlias(true);

	}
}

