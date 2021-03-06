package com.sohu.sohuvideo.ui.view;

import com.cmmobi.railwifi.R;
import com.cmmobi.railwifi.utils.DisplayUtil;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.MeasureSpec;
//import com.android.sohu.sdk.common.a.e;
//import com.sohu.sohuvideo.a.a;
import android.view.WindowManager;

public class BatteryView extends View
{
  private static int DEFAULT_HEIGHT = 60;
  private static int DEFAULT_WIDTH = 100;
  int batteryHeadHeight = 0;
  int batteryHeadWidth = 0;
  int batteryHeight = 60;
  int batteryInsideMargin = 0;
  int batteryLeft = 0;
  int batteryTop = 0;
  int batteryWidth = 100;
  boolean charging;
  Bitmap electricizeBitmap;
  int electricizeHeight = 0;
  int electricizeWidth = 0;
  protected Context mContext;
  private int mPower = 100;
  int paintColorCharging = 0;
  int paintColorNoCharging = 0;
  Paint paintOfBattery;
  Paint paintOfBatteryHeader;
  Paint paintOfOutRect;
  Rect rectOfBattery;
  Rect rectOfBatteryHeader;
  Rect rectOfOutRect;
  int strokeWidth = 0;


  public BatteryView(Context paramContext)
  {
    super(paramContext);
    init(paramContext);
  }

  public BatteryView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.sohu);
    this.batteryWidth = (int)localTypedArray.getDimension(1, DEFAULT_WIDTH);
    this.batteryHeight = (int)localTypedArray.getDimension(0, DEFAULT_HEIGHT);
    this.batteryHeadHeight = (this.batteryHeight / 3);
    this.batteryHeadWidth = (this.batteryWidth / 8);
    this.paintColorNoCharging = localTypedArray.getInteger(2, -1);
    localTypedArray.recycle();
    init(paramContext);
  }

  private void init(Context paramContext)
  {
    this.mContext = paramContext;
    this.batteryHeadWidth = a(this.mContext, 2.0F);//e.a(this.mContext, 2.0F);
    this.batteryHeadHeight = a(this.mContext, 3.5F);//e.a(this.mContext, 3.5F);
    this.batteryInsideMargin = a(this.mContext, 1.5F);//e.a(this.mContext, 1.5F);
    this.strokeWidth = a(this.mContext, 1.5F);//e.a(this.mContext, 1.5F);
    this.paintColorCharging = Color.rgb(131, 177, 29);
    this.paintOfOutRect = new Paint();
    this.paintOfOutRect.setColor(this.paintColorNoCharging);
    this.paintOfBattery = new Paint(this.paintOfOutRect);
    this.paintOfBattery.setStyle(Paint.Style.FILL);
    this.paintOfBatteryHeader = new Paint(this.paintOfBattery);
    this.rectOfOutRect = new Rect(this.batteryLeft, this.batteryTop, this.batteryLeft + this.batteryWidth, this.batteryTop + this.batteryHeight);
    this.rectOfBattery = new Rect();
    this.rectOfBatteryHeader = new Rect();
    this.electricizeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player_icon_electricize);
    this.electricizeWidth = this.electricizeBitmap.getWidth();
    this.electricizeHeight = this.electricizeBitmap.getHeight();
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    paramCanvas.save();
    paramCanvas.clipRect(0, 0, this.batteryWidth + this.batteryHeadWidth, this.batteryWidth + this.batteryHeadWidth);
    paramCanvas.translate(0.0F, this.batteryWidth + this.batteryHeadWidth);
    paramCanvas.rotate(-90.0F);
    this.paintOfOutRect.setAntiAlias(true);
    this.paintOfOutRect.setStrokeWidth(this.strokeWidth);
    this.paintOfOutRect.setStyle(Paint.Style.STROKE);
    paramCanvas.drawRect(this.rectOfOutRect, this.paintOfOutRect);
    float f = this.mPower / 100.0F;
    if (this.charging){
      this.paintOfBattery.setColor(this.paintColorCharging);  
    }
    if (f != 0.0F)
    {
      int i1 = this.batteryLeft + this.batteryInsideMargin;
      int i2 = this.batteryTop + this.batteryInsideMargin;
      int i3 = i1 - this.batteryInsideMargin + (int)(f * (this.batteryWidth - this.batteryInsideMargin));
      int i4 = i2 + this.batteryHeight - (2 * this.batteryInsideMargin);
      this.rectOfBattery.set(i1, i2, i3, i4);
      paramCanvas.drawRect(this.rectOfBattery, this.paintOfBattery);
    }
    int i = this.batteryLeft + this.batteryWidth;
    int j = this.batteryTop + this.batteryHeight / 2 - (this.batteryHeadHeight / 2);
    int k = i + this.batteryHeadWidth;
    int l = j + this.batteryHeadHeight;
    this.rectOfBatteryHeader.set(i, j, k, l);
    paramCanvas.drawRect(this.rectOfBatteryHeader, this.paintOfBatteryHeader);
    paramCanvas.restore();
    if (this.charging){
      paramCanvas.drawBitmap(this.electricizeBitmap, (this.batteryHeight - this.electricizeWidth) / 2, (this.batteryWidth - this.electricizeHeight) / 2 + this.batteryHeadWidth, this.paintOfOutRect);
    }else{
        this.paintOfBattery.setColor(this.paintColorNoCharging);
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    View.MeasureSpec.getSize(paramInt1);
    View.MeasureSpec.getSize(paramInt2);
    int i = this.batteryHeight;
    int j = this.batteryWidth + this.batteryHeadWidth;
    setMeasuredDimension(resolveSize(i, paramInt1), resolveSize(j, paramInt2));
  }

  public void setPower(int percent, boolean charging)
  {
    this.mPower = percent;
    if (this.mPower < 0)
      this.mPower = 0;
    this.charging = charging;
    invalidate();
  }
  
  public static int a(Context paramContext, float paramFloat)
  {
    Display localDisplay = ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay();
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    localDisplay.getMetrics(localDisplayMetrics);
    return (int)(0.5F + paramFloat * localDisplayMetrics.density);
  }
}

/* Location:           D:\android\crack\workspace\sohuTV\libs\classes_dex2jar.jar
 * Qualified Name:     com.sohu.sohuvideo.ui.view.BatteryView
 * Java Class Version: 6 (50.0)
 * JD-Core Version:    0.5.3
 */