package com.Matsogeum.kakaoBot;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

public class NewEditText extends android.widget.EditText{
    private Rect rect;
    private Paint paint;

    public NewEditText(Context context, AttributeSet attrs){
        super(context,attrs);
        init();
    }
    public NewEditText(Context context){
        super(context);
        init();
    }
    private void init(){
       rect = new Rect();
       paint = new Paint();
       paint.setStyle(Paint.Style.FILL);
       paint.setColor(Color.BLACK);
       paint.setTextSize(35);

    }
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        int baseline = getBaseline();
        for (int i=0;i<getLineCount();i++){
            canvas.drawText(""+(i+1),rect.left,baseline,paint);
            baseline += getLineHeight();
        }

    }
}
