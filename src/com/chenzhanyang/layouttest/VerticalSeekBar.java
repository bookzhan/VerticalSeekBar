package com.chenzhanyang.layouttest;

import com.chenzhanyang.layouttest.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * 
 * @author chenzhanyang
 *
 */
public class VerticalSeekBar extends View {

	private int mThumbColor = Color.YELLOW;
	private int mBgColor = Color.WHITE;
	private int mProgressColor = Color.YELLOW;
	private Paint mPaint;
	private int mHeight;
	private int mWidth;
	private int mLeftMargin;
	private int mTopMargin;
	private int mRightMargin;
	private int mBottomMargin;
	private Path mPath;
	private int mTotalHeight;
	private int mTotalWidth;
	private int mBgWidth;
	private RectF mRectF;
	private float mCurrentY;
	private int mMax = 100;
	private int mProgress = 0;

	public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	public VerticalSeekBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public VerticalSeekBar(Context context) {
		super(context);
		init(context, null);
	}

	private void init(Context context, AttributeSet attrs) {
		mPaint = new Paint();
		mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		mPath = new Path();
		mRectF = new RectF();

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VerticalSeekBar);
		mMax = typedArray.getInt(R.styleable.VerticalSeekBar_maxprogress, 100);
		mProgress = typedArray.getInt(R.styleable.VerticalSeekBar_progress, 0);
		mBgColor = typedArray.getColor(R.styleable.VerticalSeekBar_bgcolor, Color.WHITE);
		mProgressColor = typedArray.getColor(R.styleable.VerticalSeekBar_progresscolor, Color.YELLOW);
		mThumbColor = typedArray.getColor(R.styleable.VerticalSeekBar_thumbcolor, Color.YELLOW);

		typedArray.recycle();

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 画背�?
		drawBg(canvas);

		drawProgress(canvas);

		drawThumb(canvas);
	}

	private void drawProgress(Canvas canvas) {
		prosessOutSide();
		mRectF.top = mCurrentY;
		mPaint.setColor(mProgressColor);
		canvas.drawRect(mRectF, mPaint);

		canvas.drawCircle(mRectF.left + mBgWidth / 2, mRectF.bottom, mBgWidth / 2, mPaint);
	}

	private void drawThumb(Canvas canvas) {
		mPaint.setColor(mThumbColor);
		prosessOutSide();
		canvas.drawCircle(mWidth / 2, mCurrentY, mWidth / 2, mPaint);
	}

	private void prosessOutSide() {
		if (mCurrentY <= mWidth / 2) {
			mCurrentY = mWidth / 2;
		}
		if (mCurrentY >= mHeight - mWidth / 2) {
			mCurrentY = mHeight - mWidth / 2;
		}
	}

	private void drawBg(Canvas canvas) {
		mPaint.setColor(mBgColor);
		mRectF.left = mLeftMargin + (mWidth - mBgWidth) / 2;
		mRectF.top = mTopMargin + mBgWidth / 2;
		mRectF.right = mRectF.left + mBgWidth;
		mRectF.bottom = mHeight - mBgWidth / 2;
		canvas.drawRect(mRectF, mPaint);

		canvas.drawCircle(mRectF.left + mBgWidth / 2, mRectF.top, mBgWidth / 2, mPaint);

		canvas.drawCircle(mRectF.left + mBgWidth / 2, mRectF.bottom, mBgWidth / 2, mPaint);
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_OUTSIDE || event.getAction() == MotionEvent.ACTION_CANCEL) {
			return false;
		}
		mCurrentY = event.getY();
		invalidate();
		Log.i("1111", "mCurrentY==============>" + event.getY());
		return true;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mTotalHeight = h;
		mTotalWidth = w;

		if (getLayoutParams() instanceof LinearLayout.LayoutParams) {
			LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) getLayoutParams();
			mLeftMargin = lp.leftMargin;
			mTopMargin = lp.topMargin;
			mRightMargin = lp.rightMargin;
			mBottomMargin = lp.bottomMargin;
		}
		if (getLayoutParams() instanceof RelativeLayout.LayoutParams) {
			RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) getLayoutParams();
			mLeftMargin = lp.leftMargin;
			mTopMargin = lp.topMargin;
			mRightMargin = lp.rightMargin;
			mBottomMargin = lp.bottomMargin;
		}
		if (getLayoutParams() instanceof FrameLayout.LayoutParams) {
			FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) getLayoutParams();
			mLeftMargin = lp.leftMargin;
			mTopMargin = lp.topMargin;
			mRightMargin = lp.rightMargin;
			mBottomMargin = lp.bottomMargin;
		}

		mHeight = mTotalHeight - mTopMargin - mBottomMargin;
		mWidth = mTotalWidth - mLeftMargin - mRightMargin;
		mBgWidth = mWidth / 2;

		mCurrentY = getProgressY();

	}

	/**
	 * �������ֵ
	 * 
	 * @param max
	 */
	public void setMax(int max) {
		this.mMax = max;
	}

	/**
	 * ���ý���
	 * 
	 * @param progress
	 */
	public void setProgress(int progress) {
		this.mProgress = progress;
		mCurrentY = getProgressY();
		invalidate();
	}

	/**
	 * ��ȡ����
	 * 
	 * @return
	 */
	public int getProgress() {
		return mProgress;
	}

	/**
	 * ������ת��Ӧ��Y
	 * 
	 * @return
	 */
	private float getProgressY() {
		float everyProgressPx = (mHeight - mWidth) / mMax;
		return mWidth / 2 + mProgress * everyProgressPx;
	}

}
