package com.ybkj.videoaccess.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.mvp.data.bean.VedioInfo;
import com.ybkj.videoaccess.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Li on 2018/11/27.
 */
public class VedioHorizontalScorllView extends HorizontalScrollView {
    private Context context;
    private LayoutInflater inflater;
    private int chedkedId;

    public VedioHorizontalScorllView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void initData(List<VedioInfo> dataList, IOnItemCheck iOnItemCheck){
        removeAllViews();

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        for(int i=0;i<dataList.size();i++){
            VedioInfo vedioInfo = dataList.get(i);
            LinearLayout view = (LinearLayout) inflater.inflate(R.layout.item_vedio_list,null);
            ImageView imageView = (ImageView)view.findViewById(R.id.imgVedio);
            TextView tvVedioTitle = (TextView)view.findViewById(R.id.tvVedioTitle);
            LinearLayout layoutVedio = (LinearLayout)view.findViewById(R.id.layoutVedio);

            tvVedioTitle.setText(vedioInfo.getContent());
            ImageUtil.loadImg(context,imageView,vedioInfo.getPath(),R.mipmap.p1);

            layoutVedio.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(iOnItemCheck != null){
                        iOnItemCheck.onItemCheck(vedioInfo);
                    }
                }
            });

            linearLayout.addView(view);
        }
        addView(linearLayout);
    }

    public interface IOnItemCheck{
        void onItemCheck(VedioInfo vedioInfo);
    }
}
