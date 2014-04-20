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
import java.awt.*;

public class MyCanvas extends View
{
	Paint paint = new Paint();
	public static Context baseContext;
	public final static int TOUCH_ACCURACY = 7;
	public final static int NUM_LINES = 3;
	final int width = getResources().getDisplayMetrics().widthPixels;
	final int height;
	float points[];
	float vertices[];
	int linesZapped = 0;
	int vIndex = 0;
	Model model;
	public MyCanvas(Context context)
	{
		super(context);
		Random r = new Random();
		height = getResources().getDisplayMetrics().heightPixels - BeamBreaker.getNavigationBarHeight(context) - 40;
		points = new float[]{0, 0, width, height, width/3, 0, width/3 + width/5, height, width, 0, 0, height, 0, height - height/7, width, height - height/7};
		findVertices(vertices);
		model = new Model(context, 1);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		paint.setColor(Color.BLUE);
		paint.setTextSize(50);
		paint.setTextAlign(Align.CENTER);
		// canvas.drawText("Yo ho ho!", 100, 100, paint);
		paint.setStrokeWidth(3);
		
		canvas.drawLines(points, paint);
		paint.setColor(Color.BLACK);
		for (int i = 0; i < vIndex; i+=2)
			canvas.drawCircle(vertices[i], vertices[i+1], 5, paint);
		
		//Draw start and end dots
		paint.setColor(Color.GREEN);
		canvas.drawCircle(width - width/3, height/4, 8, paint);
		paint.setColor(Color.RED);
		canvas.drawCircle(width/13, height - height/10, 8, paint);
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
				if (points[i] != -1)
				{
					 deltaY = points[i+1] - points[i+3];
					 deltaX = points[i] - points[i+2];
					 slope = deltaY/deltaX;
					 for (int j = 0; j < width; j++)
					 {
						 y = slope * (j - points[i]) + points[i+1];
						 //Point to check is (j, y)
						 if (Math.abs(event.getX() - j) < TOUCH_ACCURACY && Math.abs(event.getY() - y) < TOUCH_ACCURACY)
						 {
							 Log.d("touch", "You touched line " + i/4);
							 linesZapped++;
							 //Zap it
							 /*for (int k = i; k < points.length - 4; k++)
							 {
								 points[k] = points[k+4];
							 }
							 for (int k = 0; k < linesZapped; k++)
							 {
								 points[points.length - (4 +(4*k))] = -1;
								 points[points.length - (3 +(4*k))] = -1;
								 points[points.length - (2 +(4*k))] = -1;
								 points[points.length - (1 +(4*k))] = -1;
							 }*/
							 points[i] = -1;
							 points[i+1] = -1;
							 points[i+2] = -1;
							 points[i+3] = -1;
							 
							 //Fuse the nodes in the model
							 model.lookForEdgesToLose((i/4)+1); //+1 because my line names in the model are indexed from 1
							 break;
						 }
					 }
				}
			}
			findVertices(this.vertices);
			invalidate();
		}
		return super.onTouchEvent(event);
	}
	
	public void findVertices(float[] vertices)
	{
		this.vertices = new float[30];
		boolean [][] IJCombos = new boolean[30][30];
		int intersectingLines[] = new int[30];
		vIndex = 0;
		//Pick a line
		for (int i = 0; i < points.length-3; i+=4)
		{
			if (points[i] != -1)
			{
				//Pick a different line
				for (int j = 0; j < points.length-3; j+=4)
				{
					if (i!=j && points[j] != -1 && IJCombos[i][j]==false)
					{
						IJCombos[i][j] = true; 
						IJCombos[j][i] = true;
						if (linesIntersect(points[i], points[i+1], points[i+2], points[i+3], points[j], points[j+1], points[j+2], points[j+3]))
						{
							Point intersection = getLineLineIntersection(points[i], points[i+1], points[i+2], points[i+3], points[j], points[j+1], points[j+2], points[j+3]);
							intersectingLines[vIndex] = i/4;
							this.vertices[vIndex++] = intersection.x;
							intersectingLines[vIndex] = j/4;
							this.vertices[vIndex++] = intersection.y;
						}
					}
				}
			}
		}
		//Count wall touches as a vertex
		for (int i = 0; i < points.length-3; i+=4)
		{
			if (points[i] == 0 || points[i] == width)
			{
				this.vertices[vIndex++] = points[i];
				this.vertices[vIndex++] = points[i+1];
			}
			else if (points[i+1] == 0 || points[i+1] == height)
			{
				this.vertices[vIndex++] = points[i];
				this.vertices[vIndex++] = points[i+1];
			}
			if (points[i+2] == 0 || points[i+2] == width)
			{
				this.vertices[vIndex++] = points[i+2];
				this.vertices[vIndex++] = points[i+3];
			}
			else if (points[i+3] == 0 || points[i+3] == height)
			{
				this.vertices[vIndex++] = points[i+2];
				this.vertices[vIndex++] = points[i+3];
			}				
		}
	}
	
	//http://www.java-gaming.org/index.php?topic=22590.0
	public static boolean linesIntersect(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4){
	      // Return false if either of the lines have zero length
	      if (x1 == x2 && y1 == y2 ||
	            x3 == x4 && y3 == y4){
	         return false;
	      }
	      // Fastest method, based on Franklin Antonio's "Faster Line Segment Intersection" topic "in Graphics Gems III" book (http://www.graphicsgems.org/)
	      double ax = x2-x1;
	      double ay = y2-y1;
	      double bx = x3-x4;
	      double by = y3-y4;
	      double cx = x1-x3;
	      double cy = y1-y3;

	      double alphaNumerator = by*cx - bx*cy;
	      double commonDenominator = ay*bx - ax*by;
	      if (commonDenominator > 0){
	         if (alphaNumerator < 0 || alphaNumerator > commonDenominator){
	            return false;
	         }
	      }else if (commonDenominator < 0){
	         if (alphaNumerator > 0 || alphaNumerator < commonDenominator){
	            return false;
	         }
	      }
	      double betaNumerator = ax*cy - ay*cx;
	      if (commonDenominator > 0){
	         if (betaNumerator < 0 || betaNumerator > commonDenominator){
	            return false;
	         }
	      }else if (commonDenominator < 0){
	         if (betaNumerator > 0 || betaNumerator < commonDenominator){
	            return false;
	         }
	      }
	      if (commonDenominator == 0){
	         // This code wasn't in Franklin Antonio's method. It was added by Keith Woodward.
	         // The lines are parallel.
	         // Check if they're collinear.
	         double y3LessY1 = y3-y1;
	         double collinearityTestForP3 = x1*(y2-y3) + x2*(y3LessY1) + x3*(y1-y2);   // see http://mathworld.wolfram.com/Collinear.html
	         // If p3 is collinear with p1 and p2 then p4 will also be collinear, since p1-p2 is parallel with p3-p4
	         if (collinearityTestForP3 == 0){
	            // The lines are collinear. Now check if they overlap.
	            if (x1 >= x3 && x1 <= x4 || x1 <= x3 && x1 >= x4 ||
	                  x2 >= x3 && x2 <= x4 || x2 <= x3 && x2 >= x4 ||
	                  x3 >= x1 && x3 <= x2 || x3 <= x1 && x3 >= x2){
	               if (y1 >= y3 && y1 <= y4 || y1 <= y3 && y1 >= y4 ||
	                     y2 >= y3 && y2 <= y4 || y2 <= y3 && y2 >= y4 ||
	                     y3 >= y1 && y3 <= y2 || y3 <= y1 && y3 >= y2){
	                  return true;
	               }
	            }
	         }
	         return false;
	      }
	      return true;
	   }
	
	 public static Point getLineLineIntersection(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
	      double det1And2 = det(x1, y1, x2, y2);
	      double det3And4 = det(x3, y3, x4, y4);
	      double x1LessX2 = x1 - x2;
	      double y1LessY2 = y1 - y2;
	      double x3LessX4 = x3 - x4;
	      double y3LessY4 = y3 - y4;
	      double det1Less2And3Less4 = det(x1LessX2, y1LessY2, x3LessX4, y3LessY4);
	      if (det1Less2And3Less4 == 0){
	         // the denominator is zero so the lines are parallel and there's either no solution (or multiple solutions if the lines overlap) so return null.
	         return null;
	      }
	      double x = (det(det1And2, x1LessX2,
	            det3And4, x3LessX4) /
	            det1Less2And3Less4);
	      double y = (det(det1And2, y1LessY2,
	            det3And4, y3LessY4) /
	            det1Less2And3Less4);
	      Point point = new Point();
	      point.set((int)x, (int)y);
	      return point;
	   }
	   protected static double det(double a, double b, double c, double d) {
	      return a * d - b * c;
	   }

}
