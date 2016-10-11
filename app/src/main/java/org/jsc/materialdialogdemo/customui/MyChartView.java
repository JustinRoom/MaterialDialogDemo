package org.jsc.materialdialogdemo.customui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jsc on 2016/9/29.
 */

public class MyChartView extends View {

    private Paint mPaint;
    //文字画笔
    private Paint mTextPaint;
    Rect bounds = new Rect();

    //view背景色
    private int viewBackgroundColor = Color.parseColor("#ffffff");

    //Y轴注解
    private String yAnnotation = "注解(单位)";
    //X轴注解
    private String xAnnotation = "注解(单位)";
    //数据源
    private List<DataModel> dataModels;
    //Y轴标签
    private List<String> yRawLabels;
    //X轴标签
    private List<String> xRawLabels;
    //数据坐标点
    private List<FloatPoint> points = new ArrayList<>();

    //上边预留高度
    private int preHeightTop;
    //左边预留宽度
    private int preWidthLeft;
    //下边预留高度
    private int preHeightBottom;
    //右边预留宽度
    private int preWidthRight;
    //X轴每一格宽度
    private int xSpaceWidth;
    //Y轴每一格宽度
    private int ySpaceWidth;
    //分割线的高度
    private float spaceLineHeight = 10;

    private float dp5;
    private LineStyle lineStyle = LineStyle.LINE;

    public enum LineStyle{
        LINE (0),
        CUBIC (1);

        LineStyle(int style) {
            this.style = style;
        }

        int style;
    }

    public MyChartView(Context context) {
        super(context);
        init(context);
    }

    public MyChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        mTextPaint.setStrokeWidth(1);
        float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, context.getResources().getDisplayMetrics());
        mTextPaint.setTextSize(textSize);

        preHeightTop = preHeightBottom = preWidthRight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20.0f, context.getResources().getDisplayMetrics());
        preWidthLeft = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30.0f, context.getResources().getDisplayMetrics());
        spaceLineHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.0f, context.getResources().getDisplayMetrics());
        dp5 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.0f, context.getResources().getDisplayMetrics());
        ySpaceWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20.0f, context.getResources().getDisplayMetrics());
        xSpaceWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30.0f, context.getResources().getDisplayMetrics());
    }

    /**
     * 设置数据源
     * @param dataModels
     */
    public void setDataModels(List<DataModel> dataModels) {
        this.dataModels = dataModels;
        if (this.dataModels == null)
            return;

        if (xRawLabels == null)
            xRawLabels = new ArrayList<>();
        xRawLabels.clear();
        for (DataModel model:dataModels){
            xRawLabels.add(model.getLabel());
        }

        if (yRawLabels == null){
            yRawLabels = new ArrayList<>();
            yRawLabels.add("20");
            yRawLabels.add("40");
            yRawLabels.add("60");
            yRawLabels.add("80");
            yRawLabels.add("100");
        }
        invalidate();
    }

    /**
     * 设置view的背景颜色
     * @param viewBackgroundColor
     */
    public void setViewBackgroundColor(int viewBackgroundColor) {
        this.viewBackgroundColor = viewBackgroundColor;
        invalidate();
    }

    /**
     *设置XY轴每一格的宽度
     * @param xSpaceWidthDip dip
     * @param ySpaceWidthDip dip
     */
    public void setXYSpaceWidth(float xSpaceWidthDip, float ySpaceWidthDip){
        xSpaceWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, xSpaceWidthDip, getContext().getResources().getDisplayMetrics());
        ySpaceWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ySpaceWidthDip, getContext().getResources().getDisplayMetrics());

        invalidate();
    }

    /**
     * 设置曲线style
     * @see @link LineStyle.LINE
     * @param lineStyle
     */
    public void setLineStyle(LineStyle lineStyle) {
        this.lineStyle = lineStyle;
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));

        int xSpaceCount = getXSpaceCount();
        int ySpaceCount = getYSpaceCount();
        int childWidthSize = xSpaceCount * xSpaceWidth + preWidthLeft + preWidthRight;
        int childHeightSize = ySpaceCount * ySpaceWidth + preHeightTop + preHeightBottom;
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        drawViewBackground(canvas);
        drawXY(canvas);
        drawLabels(canvas);
        drawAnnotations(canvas);
        drawData(canvas);
    }

    private int getXSpaceCount(){
        int xSpaceCount = 0;
        if (xRawLabels == null || xRawLabels.size() < 5){
            xSpaceCount = 10;
        } else {
            xSpaceCount = xRawLabels.size();
        }
        return xSpaceCount;
    }

    private int getYSpaceCount(){
        int ySpaceCount = 0;
        if (yRawLabels == null || yRawLabels.size() < 5){
            ySpaceCount = 5;
        } else {
            ySpaceCount = yRawLabels.size();
        }
        return ySpaceCount;
    }

    private void drawViewBackground(Canvas canvas){
        mPaint.setColor(viewBackgroundColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
    }

    /**
     * 画XY轴线、分割线
     * @param canvas
     */
    private void drawXY(Canvas canvas){
        mPaint.setColor(Color.parseColor("#333333"));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1);

        int xSpaceCount = getXSpaceCount();
        int ySpaceCount = getYSpaceCount();

        float x1 = preWidthLeft;
        float y1 = preHeightTop;

        float x2 = x1;
        float y2 = ySpaceWidth * ySpaceCount + y1;

        float x3 = x1 + xSpaceWidth * xSpaceCount;
        float y3 = y2;

        //画X轴线
        canvas.drawLine(x1, y1, x2, y2, mPaint);
        //画Y轴线
        canvas.drawLine(x2, y2, x3, y3, mPaint);

        //画X轴分割线
        for (int i = 0 ; i < xSpaceCount; i++ ){
            float x = x1 + (i + 1) * xSpaceWidth;
            float y = y2 + spaceLineHeight;
            canvas.drawLine(x, y2, x, y, mPaint);
        }

        //画Y轴分割线
        for (int i = 0 ; i < ySpaceCount; i++ ){
            float x = x1 - spaceLineHeight;
            float y = y2 - (i + 1) * ySpaceWidth;
            canvas.drawLine(x, y, x1, y, mPaint);
        }
    }

    /**
     * 画XY轴上的标签文字
     * @param canvas
     */
    private void drawLabels(Canvas canvas){
        mTextPaint.setColor(Color.parseColor("#333333"));

        int ySpaceCount = getYSpaceCount();
        float y = ySpaceWidth * ySpaceCount + preHeightTop + spaceLineHeight;
        float x;

        //画X轴标签
        if (xRawLabels != null && xRawLabels.size() > 0){
            for (int i = 0; i < xRawLabels.size(); i++ ){
                x = preWidthLeft + (i + 1) * xSpaceWidth;
                String label = xRawLabels.get(i);
                drawXLabel(canvas, label, x, y);
            }
        }

        //画y轴标签
        x = preWidthLeft - spaceLineHeight;
        if (yRawLabels != null && yRawLabels.size() > 0){
            for (int i = 0; i < yRawLabels.size(); i++ ){
                y = (ySpaceCount - i - 1) * ySpaceWidth + preHeightTop;
                String label = yRawLabels.get(i);
                drawYLabel(canvas, label, x, y);
            }
        }
    }

    private void drawAnnotations(Canvas canvas){
        mTextPaint.setColor(Color.parseColor("#669900"));

        //画Y轴注解
        if (!isEmpty(yAnnotation)){
            mTextPaint.getTextBounds(yAnnotation, 0, yAnnotation.length(), bounds);
            Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
            float startX = preWidthLeft - bounds.width() / 2;
            float startY = preHeightTop - fontMetrics.descent;

            //向上移动10px
            startY -= dp5 * 2;
            canvas.drawText(yAnnotation, startX, startY, mTextPaint);
        }

        //画X轴注解
//        if (!isEmpty(xAnnotation)){
//            mTextPaint.getTextBounds(xAnnotation, 0, xAnnotation.length(), bounds);
//            Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
//            float startX = preWidthLeft - bounds.width() / 2;
//            float startY = preHeightTop - fontMetrics.descent;
//            canvas.drawText(xAnnotation, startX, startY, mTextPaint);
//        }
    }

    private void drawData(Canvas canvas){
        if (dataModels == null)
            dataModels = new ArrayList<>();

        mPaint.setColor(Color.parseColor("#800080"));
        mPaint.setStrokeWidth(1);

        float startY = preHeightTop + getYSpaceCount() * ySpaceWidth;

        if (points == null)
            points = new ArrayList<>();
        else
            points.clear();

        for (int i = 0; i < dataModels.size(); i++){
            DataModel model = dataModels.get(i);

            float x = preWidthLeft + ( i + 1) * xSpaceWidth;
            float y = startY - model.getValue() / 100 * (getYSpaceCount() * ySpaceWidth);
            FloatPoint point = new FloatPoint(x, y);

            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(x, y, 5.0f, mPaint);

            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(x, y, 8.0f, mPaint);

            points.add(point);
        }

        int len = points.size();
        switch (lineStyle){
            case LINE:
                if (len >= 2){
                    mPaint.setStyle(Paint.Style.STROKE);
                    mPaint.setColor(Color.parseColor("#800080"));
                    Path path = new Path();
                    for (int j = 0; j < len; j++){
                        FloatPoint point = points.get(j);
                        if (j == 0)
                            path.moveTo(point.getX(), point.getY());
                        else
                            path.lineTo(point.getX(), point.getY());
                    }
                    canvas.drawPath(path, mPaint);
                } else {
                    if (!isInEditMode())
                        throw new IllegalArgumentException("至少需要两条数据");
                }
                break;
            case CUBIC:
                if (len >= 3){
                    mPaint.setStyle(Paint.Style.STROKE);
                    mPaint.setColor(Color.parseColor("#800080"));
                    Path path = new Path();

                    int cubicCount = len / 3;
                    int remainder = len % 3;

                    for (int i = 0; i < cubicCount; i++){
                        int pos1 = i * 3;
                        FloatPoint point1 = points.get(pos1);
                        FloatPoint point2 = points.get(pos1 + 1);
                        FloatPoint point3 = points.get(pos1 + 2);

                        if (i == 0)
                            path.moveTo(point1.getX(), point1.getY());
                        else {
                            FloatPoint point = points.get(pos1 - 1);
                            path.moveTo(point.getX(), point.getY());
                        }
                        path.cubicTo(point1.getX(), point1.getY(),
                                point2.getX(), point2.getY(),
                                point3.getX(), point3.getY());
                        canvas.drawPath(path, mPaint);
                    }

                    if (remainder == 1){
                        FloatPoint pointStart = points.get(len - 2);
                        FloatPoint pointEnd = points.get(len - 1);
                        path.moveTo(pointStart.getX(), pointStart.getY());
                        path.quadTo(pointStart.getX(), pointStart.getY(),
                                pointEnd.getX(), pointEnd.getY());
                        canvas.drawPath(path, mPaint);
                    } else if (remainder == 2){
                        FloatPoint pointStart = points.get(len - 3);
                        FloatPoint pointCenter = points.get(len - 2);
                        FloatPoint pointEnd = points.get(len - 1);

                        path.moveTo(pointStart.getX(), pointStart.getY());
                        path.cubicTo(pointStart.getX(), pointStart.getY(),
                                pointCenter.getX(), pointCenter.getY(),
                                pointEnd.getX(), pointEnd.getY());
                        canvas.drawPath(path, mPaint);
                    }
                } else {
                    if (!isInEditMode())
                        throw new IllegalArgumentException("至少需要三条数据");
                }
                break;
        }


    }

    private void drawXLabel(Canvas canvas, String text, float x, float y) {
        mTextPaint.getTextBounds(text, 0, text.length(), bounds);
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
        float startX = x - bounds.width() / 2;
        float startY = y + Math.abs(fontMetrics.leading) + Math.abs(fontMetrics.ascent);

        //文字向下移动5px
        startY += dp5;
        canvas.drawText(text, startX, startY, mTextPaint);
    }

    private void drawYLabel(Canvas canvas, String text, float x, float y) {
        mTextPaint.getTextBounds(text, 0, text.length(), bounds);
        float startX = x - bounds.width();
        float startY = y + bounds.height() / 2;

        //文字向左移动5px
        startX -= dp5;
        canvas.drawText(text, startX, startY, mTextPaint);
    }

    private boolean isEmpty(String txt){
        return txt == null || txt.length() == 0;
    }
}
