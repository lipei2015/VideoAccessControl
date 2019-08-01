package com.ybkj.videoaccess.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import com.ybkj.videoaccess.R;

/**
 * 自定义进度条
 * <p>
 * Created by HH on 2016/8/22.
 */
public class BaseProgress extends View {
    private int backgroundColor, progressStartColor, progressColor;
    private int width, height;
    private int currentProgress;
    private Paint paintBg, paintCurrent;

    public BaseProgress(Context context) {
        super(context);
        initPaint();
    }

    public BaseProgress(Context context, AttributeSet set) {
        super(context, set);

        TypedArray ta = context.obtainStyledAttributes(set, R.styleable.BaseProgress, 0, 0);
        backgroundColor = ta.getColor(R.styleable.BaseProgress_backgroundColor, ContextCompat.getColor(context, R.color.mainColor));
        progressColor = ta.getColor(R.styleable.BaseProgress_progressColor, ContextCompat.getColor(context, R.color.mainColor));
        progressStartColor = ta.getColor(R.styleable.BaseProgress_progressStartColor, ContextCompat.getColor(context, R.color.mainColorTrans));
        initPaint();
        ta.recycle();
    }

    private void initPaint() {
        paintBg = new Paint();
        paintBg.setColor(backgroundColor);
        paintBg.setStyle(Paint.Style.FILL);
        paintCurrent = new Paint();
    }

    public void setProgress(int currentProgress) {
        this.currentProgress = currentProgress;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画背景颜色
        canvas.drawRoundRect(new RectF(0, 0, width, height), height, height, paintBg);

        //画进度颜色
        canvas.drawRoundRect(new RectF(0, 0, width * currentProgress / 100, height), height, height, paintCurrent);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        width = w;
        height = h;
        LinearGradient gradient = new LinearGradient(0, 0, width, height,
                progressStartColor, progressColor, Shader.TileMode.MIRROR);
        paintCurrent.setShader(gradient);

        super.onSizeChanged(w, h, oldW, oldH);
    }
}
