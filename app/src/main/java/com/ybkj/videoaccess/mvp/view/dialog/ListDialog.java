package com.ybkj.videoaccess.mvp.view.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ybkj.videoaccess.R;
import com.ybkj.videoaccess.mvp.base.BaseDialog;

public class ListDialog extends BaseDialog implements View.OnClickListener {
    private Button button_1;
    private Button button_2;
    private Button button_3;
    private Button button_4;
    private Button button_5;
    private View v;
    private ConfirmDialog.OnConfirm onConfirm;

    public ListDialog(Context context) {
        super(context);
        findView(context);
        setCancelable(true);
    }

    /**
     * 初始化
     */
    private void findView(Context context) {
        v = View.inflate(context, R.layout.dialog_list, null);
        button_1 = (Button) v.findViewById(R.id.button_1);
        button_2 = (Button) v.findViewById(R.id.button_2);
        button_3 = (Button) v.findViewById(R.id.button_3);
        button_4 = (Button) v.findViewById(R.id.button_4);
        button_5 = (Button) v.findViewById(R.id.button_5);
        setContentView(v);
    }

    @Override
    public void onClick(View view) {

    }

    public void onItemClick(int value){
        dismiss();
        switch (value){
            case 1:
                // 人脸注册
                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;
        }
    }
}
