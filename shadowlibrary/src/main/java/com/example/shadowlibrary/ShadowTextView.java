package com.example.shadowlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 尝试一个自定义阴影View
 */
public class ShadowTextView extends View {
    private  Context mContext;
    //发光的paint
    private Paint mMaskPaint;

    //背景的paint
    private Paint mBgPaint;
    //字体Paint
    private Paint mTextPaint;

    //文字字号
    private int mTextSize;
    //文字颜色
    private int mTextColor;
    //文字显示内容
    private String mTextContent="";
    //背景圆角和散射的圆角
    private float mRadius;

    //背景渐变开始颜色
    private int mStartColor;
    //背景渐变结束颜色
    private int mEndColor;
    //散射半径
    private float mMaskRadius;
    //阴影渐变开始颜色
    private int mMaskStartColor;
    //阴影渐变结束颜色
    private int mMaskEndColor;
    private int mWidth;
    private int mHight;
    //是否显示阴影
    private boolean isShowShadow = true;
    //阴影偏移
    private int dx;
    private int dy;
    //背景选中颜色
    private int mSelectStartColor;
    private int mSelectEndColor;

    private int mBgShowStartColor;
    private int mBgShowEndColor;
    public ShadowTextView(Context context) {
        this(context, null);
    }

    public ShadowTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView(attrs);
    }

    /**
     * 初始化信息变量
     */
    private void initView(AttributeSet attrs) {
        initPaint();
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.ShadowTextView);
        mTextSize = a.getColor(R.styleable.ShadowTextView_shadowTextSize, sp2px(15));
        mRadius = a.getDimension(R.styleable.ShadowTextView_shadowTextCornerRadius, dp2px(25));
        mMaskRadius =  a.getDimensionPixelSize(R.styleable.ShadowTextView_shadowTextRadius, dp2px(10));
        mStartColor =  a.getColor(R.styleable.ShadowTextView_shadowBgStartColor, getResources().getColor(R.color.my_bg_start));
        mEndColor =  a.getColor(R.styleable.ShadowTextView_shadowBgEndColor, getResources().getColor(R.color.my_bg_end));
        mEndColor =  a.getColor(R.styleable.ShadowTextView_shadowBgEndColor, getResources().getColor(R.color.my_bg_end));
        mMaskStartColor =  a.getColor(R.styleable.ShadowTextView_shadowStartColor, getResources().getColor(R.color.my_bg_start));
        mMaskEndColor =  a.getColor(R.styleable.ShadowTextView_shadowEndColor, getResources().getColor(R.color.my_bg_end));
        mTextColor =  a.getColor(R.styleable.ShadowTextView_shadowTextColor, getResources().getColor(R.color.colorWhite));
        mSelectEndColor = a.getColor(R.styleable.ShadowTextView_shadowSelectEndColor, getResources().getColor(R.color.my_bg_selected_end));
        mSelectStartColor = a.getColor(R.styleable.ShadowTextView_shadowSelectStartColor, getResources().getColor(R.color.my_bg_selected_start));
        dx =  a.getInt(R.styleable.ShadowTextView_shadowTextColor, 0);
        dy =  a.getInt(R.styleable.ShadowTextView_shadowTextColor, (int)mMaskRadius/4);
        mTextContent =  a.getString(R.styleable.ShadowTextView_shadowText);
        Log.e("ljc",mMaskRadius+"");
        a.recycle();
        setViewSelected(false);
        setTextPaint();
    }

    private void initPaint() {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMaskPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHight = h;
    }

    /**
     * 初始化文字paint
     */
    private void setTextPaint() {
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        Typeface font = Typeface.create( Typeface.SANS_SERIF,Typeface.BOLD);
        mTextPaint.setTypeface(font);
        mTextPaint.setTextSize(mTextSize);
    }

    /***
     * 初始化阴影paint
     */
    private void setMaskPaint() {
        LinearGradient maskLinearGradient = new LinearGradient(0, 0, dp2px(mWidth), 0
                , new int[]{mMaskStartColor, mMaskEndColor}
                , new float[]{0, .9F}
                , Shader.TileMode.CLAMP);
        mMaskPaint.setShader(maskLinearGradient);
        mMaskPaint.setShadowLayer(mMaskRadius/2,dx,dy,getResources().getColor(R.color.colorWhite));
    }

    /***
     * 初始化背景paint
     */
    private void setBgPaint() {
        LinearGradient bgLinearGradient = new LinearGradient(0, 0, mWidth - 2 * mMaskRadius, 0
                , new int[]{mBgShowStartColor, mBgShowEndColor}
                , new float[]{0, .9F}
                , Shader.TileMode.CLAMP);
        mBgPaint.setShader(bgLinearGradient);
    }
    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //业务代码
                setViewSelected(true);
                return true;
            case MotionEvent.ACTION_UP:
                //业务代码
                performClick();
                setViewSelected(false);
                return true;
            case MotionEvent.ACTION_CANCEL:
                setViewSelected(false);
                return true;
        }
        return super.onTouchEvent(event);
    }
    /**
     * 是否被按下
     * @param selected
     */
    private void setViewSelected(boolean selected) {
        if(selected){
            isShowShadow = false;
            mBgShowStartColor = mSelectStartColor;
            mBgShowEndColor = mSelectEndColor;
        }else{
            isShowShadow = true;
            mBgShowStartColor = mStartColor;
            mBgShowEndColor = mEndColor;
        }
        postInvalidate();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBgPaint();
        setMaskPaint();
        RectF rectF = new RectF(mMaskRadius, mMaskRadius, mWidth - mMaskRadius, mHight - mMaskRadius);
        if(isShowShadow){
            canvas.drawRoundRect(rectF, mRadius, mRadius, mMaskPaint);
        }
        canvas.drawRoundRect(rectF, mRadius, mRadius, mBgPaint);
        if(!TextUtils.isEmpty(mTextContent)){
            drawMyText(rectF,canvas);
        }
    }

    private void drawMyText(RectF rectF ,Canvas canvas) {
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
        int baseLineY = (int) (rectF.centerY() - top / 2 - bottom / 2);//基线中间点的y轴计算公式
        canvas.drawText(mTextContent, rectF.centerX(), baseLineY, mTextPaint);
    }

    private int dp2px(float dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public int px2dip(float pxValue) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
