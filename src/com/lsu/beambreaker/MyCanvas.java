package com.lsu.beambreaker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.view.View;

public class MyCanvas extends View
{

	public MyCanvas(Context context)
	{
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setTextSize(50);
		paint.setTextAlign(Align.CENTER);
		canvas.drawText("Yo ho ho!", 100, 100, paint);
		super.onDraw(canvas);
	}

}
