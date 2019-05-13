package com.example.shadowlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 尝试一个自定义阴影View
 */
public class MaskTextView extends View {
    private Context mContext;
    //发光的paint
    private Paint mMaskPaint;

    //背景的paint
    private Paint mBgPaint;
    //字体Paint
    private Paint mTextPaint;

    private BlurMaskFilter mBlurMaskFilter;
    //文字字号
    private int mTextSize;
    //文字颜色
    private int mTextColor;
    //文字显示内容
    private String mTextContent = "";
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
    //背景选中颜色
    private int mSelectStartColor;
    private int mSelectEndColor;


    private int mBgShowStartColor;
    private int mBgShowEndColor;
    private int mWidth;
    private int mHight;

    private boolean isShowShadow = true;

    public MaskTextView(Context context) {
        this(context, null);
    }

    public MaskTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaskTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        mTextSize = a.getColor(R.styleable.ShadowTextView_shadowTextSize, 15);
        mRadius = a.getDimension(R.styleable.ShadowTextView_shadowTextCornerRadius, 25);
        mMaskRadius = a.getDimension(R.styleable.ShadowTextView_shadowTextRadius, 10);
        mStartColor = a.getColor(R.styleable.ShadowTextView_shadowBgStartColor, getResources().getColor(R.color.my_bg_start));
        mEndColor = a.getColor(R.styleable.ShadowTextView_shadowBgEndColor, getResources().getColor(R.color.my_bg_end));
        mSelectEndColor = a.getColor(R.styleable.ShadowTextView_shadowSelectEndColor, getResources().getColor(R.color.my_bg_selected_end));
        mSelectStartColor = a.getColor(R.styleable.ShadowTextView_shadowSelectStartColor, getResources().getColor(R.color.my_bg_selected_start));
        mMaskStartColor = a.getColor(R.styleable.ShadowTextView_shadowStartColor, getResources().getColor(R.color.my_bg_start));
        mMaskEndColor = a.getColor(R.styleable.ShadowTextView_shadowEndColor, getResources().getColor(R.color.my_bg_end));
        mTextColor = a.getColor(R.styleable.ShadowTextView_shadowTextColor, getResources().getColor(R.color.colorWhite));
        mTextContent = a.getString(R.styleable.ShadowTextView_shadowText);
        a.recycle();
        setViewSelected(false);

        setTextPaint();
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

    private void initPaint() {
        mMaskPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHight = h;
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
     * 初始化文字paint
     */
    private void setTextPaint() {
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
        mTextPaint.setTypeface(font);
        mTextPaint.setTextSize(sp2px(mTextSize));
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
        mBlurMaskFilter = new BlurMaskFilter(mMaskRadius, BlurMaskFilter.Blur.NORMAL);
        setLayerType(View.LAYER_TYPE_SOFTWARE, mMaskPaint);
        mMaskPaint.setMaskFilter(mBlurMaskFilter);
    }

    /***
     * 初始化背景paint
     */
    private void setBgPaint() {
        LinearGradient bgLinearGradient = new LinearGradient(0, 0, dp2px(mWidth - 2 * mMaskRadius), 0
                , new int[]{mBgShowStartColor, mBgShowEndColor}
                , new float[]{0, .9F}
                , Shader.TileMode.CLAMP);
        mBgPaint.setShader(bgLinearGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBgPaint();
        setMaskPaint();
        RectF rectF = new RectF(dp2px(mMaskRadius), dp2px(mMaskRadius), mWidth - dp2px(mMaskRadius), mHight - dp2px(mMaskRadius));
        if (isShowShadow) {
            canvas.drawRoundRect(rectF, dp2px(mRadius), dp2px(mRadius), mMaskPaint);
        }
        canvas.drawRoundRect(rectF, dp2px(mRadius), dp2px(mRadius), mBgPaint);
        if (!TextUtils.isEmpty(mTextContent)) {
            drawMyText(rectF, canvas);
        }
    }

    private void drawMyText(RectF rectF, Canvas canvas) {
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
