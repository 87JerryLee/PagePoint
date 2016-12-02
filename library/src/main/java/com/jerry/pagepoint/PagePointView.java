package com.jerry.pagepoint;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Description:
 * Author: jerry Lee
 * Date: 2016/12/02 17:19
 */

public class PagePointView extends View {
    //未聚焦的点颜色
    private int unfocusPointColor = 0xff00ff00;
    //聚焦的点的颜色
    private int focusPointColor = 0xffff0000;
    //默认没有边
    private boolean hasBorder = false;
    //边宽度
    private int borderWidth = 0 ;
    //点半径
    private int pointRadius;
    //点的个数
    private int pointCount = 0;
    //点与点之间的间隙
    private int gapWidth;

    private Paint focusPointPaint;
    private Paint unfocusPointPaint;

    private ViewPager pager;

    private int currentPosition = 0;
    private float currentPositionOffset = 0f;

    public PagePointView(Context context) {
        this(context,null);
    }

    public PagePointView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PagePointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    private void init(AttributeSet attrs){
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PagePointView);

        unfocusPointColor = a.getColor(R.styleable.PagePointView_ppv_unfocusPointColor,unfocusPointColor);
        focusPointColor = a.getColor(R.styleable.PagePointView_ppv_focusPointColor,focusPointColor);
        pointCount = a.getInteger(R.styleable.PagePointView_ppv_pointCount,pointCount);
        hasBorder = a.getBoolean(R.styleable.PagePointView_ppv_hasBorder,hasBorder);
        pointRadius = a.getDimensionPixelSize(R.styleable.PagePointView_ppv_pointRadius, 6);
        borderWidth = a.getDimensionPixelSize(R.styleable.PagePointView_ppv_borderWidth,pointRadius/3);

        a.recycle();

        focusPointPaint = new Paint();
        unfocusPointPaint = new Paint();
        focusPointPaint.setAntiAlias(true);
        unfocusPointPaint.setAntiAlias(true);
        focusPointPaint.setColor(focusPointColor);
        unfocusPointPaint.setColor(unfocusPointColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (pager == null)return;

        int width = getWidth();
        int height = getHeight();

        //计算点与点之间的间隙
        gapWidth = (width - (pointRadius*2))/(pointCount-1);

        //原点位置
        int cy = (height - (pointRadius*2)) / 2 + pointRadius;
        //先画N个未聚焦的圆点 和 边
        unfocusPointPaint.setStyle(Paint.Style.FILL);
        if (hasBorder){
            focusPointPaint.setStyle(Paint.Style.STROKE);
            focusPointPaint.setStrokeWidth(borderWidth);
        }
        for (int i = 0; i < pointCount; i++) {
            int cx = gapWidth * i + pointRadius;
            //画点
            canvas.drawCircle(cx ,cy,pointRadius,unfocusPointPaint);

            //画边
            if (hasBorder){
                canvas.drawCircle(cx ,cy,pointRadius-(borderWidth/2f),focusPointPaint);
            }
        }

        //画聚焦的点
        float radius = hasBorder?pointRadius-borderWidth:pointRadius;
        focusPointPaint.setStyle(Paint.Style.FILL);
        focusPointPaint.setStrokeWidth(0);
        canvas.drawCircle(pointRadius + gapWidth*currentPosition + (gapWidth * currentPositionOffset) ,cy,radius,focusPointPaint);


    }

    public void setViewPager(ViewPager pager){
        this.pager = pager;
        if (pager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        currentPosition = pager.getCurrentItem();
        pager.addOnPageChangeListener(pageListener);
        if (pager.getAdapter() != null){
            int count = pager.getAdapter().getCount();
            if (count != pointCount){
                pointCount = count;
                invalidate();
            }
        }
    }

    private ViewPager.OnPageChangeListener pageListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            currentPosition = position;
            currentPositionOffset = positionOffset;
            invalidate();
        }

        @Override
        public void onPageSelected(int position) {
            currentPosition = position;
            currentPositionOffset = 0;
            invalidate();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        //设置宽高
        setMeasuredDimension(width, height);
    }

    //根据xml的设定获取宽度
    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        //fill_parent或者精确值
        if (specMode == MeasureSpec.EXACTLY){
            return specSize;
        }
        //wrap_content
        else {
            if(pointCount != 0){
                //默认间隔两个点的距离
                int width = pointRadius * 2 *pointCount + ((pointCount-1)*pointRadius*4);
                return width < specSize ? width : specSize;
            }
        }
        return specSize;
    }

    //根据xml的设定获取高度
    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        //fill_parent或者精确值
        if (specMode == MeasureSpec.EXACTLY) {
            return specSize;
        }
        //wrap_content
        else {
            return pointRadius*2;
        }
    }
}
