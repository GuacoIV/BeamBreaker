package com.lsu.beambreaker;

import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.view.View;

public class MyCanvas extends View
{
	Paint paint = new Paint();
	public static Context baseContext;
	final int width = getResources().getDisplayMetrics().widthPixels;
	final int height;
	
	public MyCanvas(Context context)
	{
		super(context);
		Random r = new Random();
		height = getResources().getDisplayMetrics().heightPixels - BeamBreaker.getNavigationBarHeight(context);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		
		paint.setColor(Color.BLUE);
		paint.setTextSize(50);
		paint.setTextAlign(Align.CENTER);
		//canvas.drawText("Yo ho ho!", 100, 100, paint);
		canvas.drawLine(0, 0, width, height, paint);
		canvas.drawLine(width/3, 0, width/3 + width/5, height, paint);
		canvas.drawLine(width, 0, 0, height, paint);
		super.onDraw(canvas);
	}
	


}
