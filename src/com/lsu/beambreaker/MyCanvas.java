package com.lsu.beambreaker;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MyCanvas extends View
{
	Paint paint = new Paint();
	public static Context baseContext;
	final int width = getResources().getDisplayMetrics().widthPixels;
	final int height;
	float points[];
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
		// canvas.drawText("Yo ho ho!", 100, 100, paint);
		paint.setStrokeWidth(3);
		points = new float[]{0, 0, width, height, width/3, 0, width/3 + width/5, height, width, 0, 0, height};
		///canvas.drawLine(0, 0, width, height, paint);
		//canvas.drawLine(width / 3, 0, width / 3 + width / 5, height, paint);
		//canvas.drawLine(width, 0, 0, height, paint);
		canvas.drawLines(points, paint);
		super.onDraw(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			//Is it on a line?
			float y;
			float deltaY;
			float deltaX;
			float slope;
			for (int i = 0; i < points.length; i+=4)
			{
				 deltaY = points[i+1] - points[i+3];
				 deltaX = points[i] - points[i+2];
				 slope = deltaY/deltaX;
				 for (int j = 0; j < width; j++)
				 {
					 y = slope * (j - points[i]) + points[i+1];
					 //Point to check is (j, y)
					 if (Math.abs(event.getX() - j) < 5 && Math.abs(event.getY() - y) < 5)
						 Log.d("touch", "You touched line " + i/4);
				 }
			}
		}
		return super.onTouchEvent(event);
	}

}
