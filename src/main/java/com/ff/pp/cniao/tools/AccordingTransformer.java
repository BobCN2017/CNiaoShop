package com.ff.pp.cniao.tools;

import android.view.View;

import com.daimajia.slider.library.Transformers.BaseTransformer;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by PP on 2017/3/22.
 */

public class AccordingTransformer extends BaseTransformer {
    @Override
    protected void onTransform(View view, float position) {
        ViewHelper.setPivotX(view, position < 0 ? 0 : view.getWidth());
        ViewHelper.setScaleX(view, position < 0 ? 1f + position : 1f - position);
    }
}
