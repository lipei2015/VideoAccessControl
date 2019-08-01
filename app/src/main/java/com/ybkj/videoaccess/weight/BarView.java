package com.ybkj.videoaccess.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.util.ViewUtil;

/**
 * Created by HH on 2016/7/6.
 * <p>
 * 横向条布局自定义view(个人资料，个人中心横向条)
 */

public class BarView extends RelativeLayout {

    private TextView leftTextView;
    private TextView rightTextView;
    private ImageView rightImg;
    private String leftText;
    private String rightText;
    private Drawable leftDrawable;
    private Drawable rightDrawable;
    private int rightTextColor;
    private boolean isLine;  //是否显示下划线
    private boolean isRightImg;  //是否显示右边图标
    private boolean isLeftImg;  //是否显示左边图标
    private int textSize; //文字大小

    private Context context;

    public BarView(Context context) {
        super(context);
        initView(context);
    }

    public BarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BarView);
        leftText = typedArray.getString(R.styleable.BarView_leftText);
        rightText = typedArray.getString(R.styleable.BarView_rightText);
        leftDrawable = typedArray.getDrawable(R.styleable.BarView_leftImg);
        rightDrawable = typedArray.getDrawable(R.styleable.BarView_rightImg);
        rightTextColor = typedArray.getColor(R.styleable.BarView_rightTextColor, ContextCompat.getColor(context, R.color.textColor54));
        isLine = typedArray.getBoolean(R.styleable.BarView_isLine, true);
        isRightImg = typedArray.getBoolean(R.styleable.BarView_isRightImg, true);
        isLeftImg = typedArray.getBoolean(R.styleable.BarView_isLeftImg, true);
        textSize = typedArray.getInt(R.styleable.BarView_textSizes, 14);
        typedArray.recycle();

        initView(context);
    }

    /**
     * 初始化View
     */
    public void initView(Context context) {
        this.context = context;
        View view = View.inflate(context, R.layout.base_view_bar, null);
        leftTextView =  view.findViewById(R.id.leftText);
        rightTextView =  view.findViewById(R.id.rightText);
        rightTextView.setTextColor(rightTextColor);
        ImageView leftImg =  view.findViewById(R.id.leftImg);
        rightImg =  view.findViewById(R.id.rightImg);
        View lineView = view.findViewById(R.id.lineView);

        leftTextView.setText(leftText);
        leftTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        rightTextView.setText(rightText);
        rightTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        if (leftDrawable != null) {
            leftImg.setImageDrawable(leftDrawable);
        }

        if (rightDrawable != null) {
            rightImg.setImageDrawable(rightDrawable);
        }

        if (!isLine) {
            //不显示下划线
            lineView.setVisibility(View.INVISIBLE);
        }
        if (!isRightImg) {
            //不显示右边图标
            rightImg.setVisibility(View.GONE);
        }
        if(!isLeftImg){
            //不显示左边图标
            leftImg.setVisibility(View.GONE);
        }

        addView(view);
    }

    /**
     * 设置右边数据
     */
    public void setRightText(String text) {
        rightTextView.setText(text);
    }

    public void setRightText(CharSequence text) {
        rightTextView.setText(text);
    }

    public void setRightTextPadding(int dp){
        rightTextView.setPadding(0,0, ViewUtil.dip2px(context,dp),0);
    }

    /**
     * 获取右边数据
     */
    public String getRightText() {
        return rightTextView.getText().toString();
    }

    /**
     * 设置左边数据
     */
    public void setLeftText(String text) {
        leftTextView.setText(text);
    }

    /**
     * 设置左边数据
     */
    public void setLeftText(CharSequence text) {
        leftTextView.setText(text);
    }

    public void setRightImg(int resId) {
        rightImg.setImageDrawable(ContextCompat.getDrawable(context, resId));
    }

    public TextView getLeftTextView(){
        return leftTextView;
    }

}