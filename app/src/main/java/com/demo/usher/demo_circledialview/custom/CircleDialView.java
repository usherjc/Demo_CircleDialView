package com.demo.usher.demo_circledialview.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ChenJianCong on 2017-11-6.
 * <p>
 * Ps:
 */

public class CircleDialView extends View {

    private static final double RADIAN = 180 / Math.PI;
    //布局长宽
    private int mH, mW;
    //圆周半径
    private int mCircleR;
    //圆心坐标
    private int mCircleX, mCircleY;
    //划过角度
    private float mAngle = 0;
    //圆点半径
    private int mDotR = 20;
    //圆点的圆心坐标
    private float dotX, dotY;

    public CircleDialView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mH = getHeight();
        mW = getWidth();
        mCircleX = mW / 2;
        mCircleY = mH / 2;
        mCircleR = (mW < mH ? mW / 2 : mH / 2) - mDotR;
        drawDial(
                canvas,
                mDotR,
                mCircleR,
                mW / 2, mH / 2
        );
    }

    private void drawDial(Canvas canvas, int dot_r, int r, int... coordinate) {
        int x = coordinate[0];
        int y = coordinate[1];
        //————————————————————绘制背景————————————————————
        Paint bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(Color.BLACK);
        bgPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(
                x, y,
                r,
                bgPaint
        );
        //————————————————————绘制主要————————————————————
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        canvas.drawCircle(
                x, y,
                r,
                paint
        );
        //————————————————————绘制刻度————————————————————
        Paint turnPaint = new Paint();
        turnPaint.setAntiAlias(true);
        turnPaint.setStyle(Paint.Style.STROKE);
        turnPaint.setStrokeWidth(3);
        for (int i = 0; i < 180; i++) {
            if (i > 0 && mAngle > 0 && (int) (mAngle / 2) < i) {
                turnPaint.setColor(Color.YELLOW);
            } else {
                turnPaint.setColor(Color.BLUE);
            }
            if (i % 12 == 0) {
                canvas.drawLine(
                        x, y - r,
                        x, y - r + 60,
                        turnPaint
                );
            } else {
                canvas.drawLine(
                        x, y - r,
                        x, y - r + 30,
                        turnPaint
                );
            }
            canvas.rotate(360 / 180, x, y);
        }
        //————————————————————绘制圆点————————————————————
        //首次在头部
        if (mAngle <= 0) {
            dotX = x;
            dotY = y - r;
        }
        Paint dotPaint = new Paint();
        dotPaint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(
                dotX, dotY,
                dot_r,
                paint
        );
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            float cos = (float) ((x - mCircleX) / Math.sqrt(Math.pow((x - mCircleX), 2) + Math.pow((y - mCircleY), 2)));
            float angle = (float) (Math.acos(cos) * RADIAN);
            if (x < mCircleX) {
                if (y > mCircleY) {
                    //第三象限
                    mAngle = angle;
                    dotY = (float) (mCircleY + mCircleR * (Math.sqrt(1 - cos * cos)));
                } else {
                    //第二象限
                    mAngle = 360 - angle;
                    dotY = (float) (mCircleY - mCircleR * (Math.sqrt(1 - cos * cos)));
                }
            } else {
                if (y > mCircleY) {
                    //第四象限
                    mAngle = angle;
                    dotY = (float) (mCircleY + mCircleR * (Math.sqrt(1 - cos * cos)));
                } else {
                    //第一象限
                    mAngle = 360 - angle;
                    dotY = (float) (mCircleY - mCircleR * (Math.sqrt(1 - cos * cos)));
                }
            }
            dotX = mCircleX + mCircleR * cos;
            if (mAngle < 270) {
                mAngle = mAngle + 90;
            } else {
                mAngle = mAngle - 270;
            }
            postInvalidate();
            return true;
        } else {
            return super.onTouchEvent(event);
        }

    }
}
