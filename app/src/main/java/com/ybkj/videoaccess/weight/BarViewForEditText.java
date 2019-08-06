package com.ybkj.videoaccess.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.util.ViewUtil;

/**
 * Created by HH
 *
 * 横向条布局(中间为EditText)自定义view
 *
 */

public class BarViewForEditText extends RelativeLayout {

    private TextView leftTextView;
    private EditText editText;
    private ImageView leftImg;
    private View lineView;
    private ImageButton rightImg;

    private String leftText;
    private String hintText;
    private String inputType; //输入框输入类型
    private Drawable leftDrawable;
    private Drawable rightDrawable;
    private boolean isLine;  //是否显示下划线
    private int leftTextMinWidth; //左侧文字最小宽度，方便右侧对齐
    private int inputMaxLength; //输入框最多输入字符数
    private int textSize; //文字大小


    public BarViewForEditText(Context context) {
        super(context);

        initView(context);
    }

    public BarViewForEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BarViewForEditText);
        leftText = typedArray.getString(R.styleable.BarViewForEditText_leftTexts);
        inputType = typedArray.getString(R.styleable.BarViewForEditText_inputType);
        hintText = typedArray.getString(R.styleable.BarViewForEditText_editHints);
        leftDrawable = typedArray.getDrawable(R.styleable.BarViewForEditText_leftImgs);
        rightDrawable = typedArray.getDrawable(R.styleable.BarViewForEditText_rightImgs);
        isLine = typedArray.getBoolean(R.styleable.BarViewForEditText_isLines, true);
        leftTextMinWidth = (int)typedArray.getDimension(R.styleable.BarViewForEditText_leftTextMinWidth, 80);
        inputMaxLength = typedArray.getInt(R.styleable.BarViewForEditText_inputMaxLength, 1000);
        textSize = typedArray.getInt(R.styleable.BarViewForEditText_inputTextSize, 14);
        typedArray.recycle();

        initView(context);
    }

    /**
     * 初始化View
     */
    public void initView(Context context) {
        View view = View.inflate(context, R.layout.base_view_edit_bar, null);

        //输入框样式初始化
        editText = (EditText) view.findViewById(R.id.editInput);
        editText.setHint(hintText);
        editText.setTextSize(textSize);
        //设置最大字数
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(inputMaxLength)});
        //设置输入框输入类型
        if(inputType == null){
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
        }else if(inputType.equals("password")){
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }else if(inputType.equals("number")){
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        }else{
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
        }

        //左侧文字与左侧图标
        leftTextView = (TextView) view.findViewById(R.id.leftText);
        leftTextView.setMinWidth(leftTextMinWidth);
        leftTextView.setText(leftText);
        leftTextView.setTextSize(textSize);
        leftImg = (ImageView) view.findViewById(R.id.leftImg);
        if(leftDrawable == null){
            leftImg.setVisibility(View.GONE);
        }else{
            leftImg.setImageDrawable(leftDrawable);
        }

        rightImg = (ImageButton) view.findViewById(R.id.rightImg);
        if(rightDrawable == null){
            rightImg.setVisibility(GONE);
        }else{
            rightImg.setImageDrawable(rightDrawable);
            editText.addTextChangedListener(textWatcher);
        }

        //下划线
        lineView =  view.findViewById(R.id.lineView);
        if(!isLine){
            //不显示下划线
            lineView.setVisibility(View.INVISIBLE);
        }

        setInputValue("");
        addView(view);
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(charSequence.toString().length() > 0){
                rightImg.setVisibility(VISIBLE);
            }else{
                rightImg.setVisibility(GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    };

    /**
     * 获取输入框内容
     */
    public String getInputValue(){
        return ViewUtil.getEtText(editText);
    }

    /**
     * 设置输入框内容
     * @param text
     */
    public void setInputValue(String text){
        editText.setText(text);
    }

    /**
     * 设置输入框不可编辑
     */
    public void isInputEable(){
        editText.setOnTouchListener( (View v, MotionEvent event) ->  {
            getParent().requestDisallowInterceptTouchEvent(true);
            return true;
        });
        editText.setFocusable(false);
    }

    /**
     * 设置输入框字体颜色
     */
    public void setInputColor(int color){
        editText.setTextColor(color);
    }

    /**
     * 设置左边Text字体颜色
     */
    public void setLeftTextColor(int color){
        leftTextView.setTextColor(color);
    }

    /**
     * 设置右边图片的点击事件
     * @param onClickListener
     */
    public void setOnRightImgClickListener(OnClickListener onClickListener){
        rightImg.setOnClickListener(onClickListener);
    }

    /**
     * 移除editText文本监听,显示右边图片
     * @return
     */
    public BarViewForEditText removeEditTextChangedListener(){
        editText.removeTextChangedListener(textWatcher);
        rightImg.setVisibility(View.VISIBLE);
        return this;
    }

    /**
     * 设置光标位置
     * @param index
     */
    public void setEditTextSelection(int index){
        editText.setSelection(index);
    }

    /**
     * EditText 改变监听
     */
    public void setEditTextChangeListener(TextChangedListener textChangeListener){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (textChangeListener!=null)
                textChangeListener.isTextChanged();
            }
        });
    }

    /**
     * EditText 改变监听接口
     */
    protected TextChangedListener textChangeListener;
    public interface TextChangedListener {
        void isTextChanged();
    }

}