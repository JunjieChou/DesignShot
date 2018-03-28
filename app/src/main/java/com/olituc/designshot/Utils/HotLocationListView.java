package com.olituc.designshot.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by olituc on 3/13/18.
 * All Rights Reserved by olituc
 */

public class HotLocationListView extends ListView {
    public HotLocationListView(Context context) {
        super(context);
    }

    public HotLocationListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HotLocationListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
