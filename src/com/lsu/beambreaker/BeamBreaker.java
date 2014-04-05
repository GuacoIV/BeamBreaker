package com.lsu.beambreaker;

import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.view.Menu;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class BeamBreaker extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		MyCanvas myCanvas = new MyCanvas(this);
		setContentView(R.layout.activity_beam_breaker);
		RelativeLayout parentLayout = (RelativeLayout) findViewById(R.id.ParentLayout);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		parentLayout.addView(myCanvas, 0, params);
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.beam_breaker, menu);
		return true;
	}
	// navigation bar (at the bottom of the screen)
	public static int getNavigationBarHeight(Context context) {
	    Resources resources = context.getResources();
	    int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
	    if (resourceId > 0) {
	        return resources.getDimensionPixelSize(resourceId);
	    }
	    return 0;
	}
}