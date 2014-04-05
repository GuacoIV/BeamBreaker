package com.lsu.beambreaker;

import android.os.Bundle;
import android.app.Activity;
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
		//myCanvas.setLayoutParams(params);
		parentLayout.addView(myCanvas, 0, params);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.beam_breaker, menu);
		return true;
	}

}
