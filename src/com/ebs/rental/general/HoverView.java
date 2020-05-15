package com.ebs.rental.general;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

@SuppressWarnings("ALL")
public class HoverView extends View {
	private final Paint mPaint;
	private int mLeft, mTop, mRight, mBottom;
	
	public HoverView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mPaint = new Paint();
		mPaint.setColor(Color.RED);
		mPaint.setStyle(Paint.Style.STROKE);
	}
	
	public void update(int width, int height) {
		int centerX = width / 2;
		int centerY = height / 2;
		mLeft = centerX - 360;
		mRight = centerX + 360;
		mTop = centerY - 300;
		mBottom = centerY + 250;
		invalidate();
	}
	
	public int getHoverLeft() {
		return mLeft;
	}
	
	public int getHoverTop() {
		return mTop;
	}
	
	public int getHoverAreaWidth() {
		return mRight - mLeft;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		canvas.drawRect(mLeft, mTop, mRight, mBottom, mPaint);
	}
}
