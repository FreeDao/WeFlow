// Copyright 2013 Square, Inc.

package com.squareup.timessquare;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.etoc.weflow.R;
import com.etoc.weflow.utils.DisplayUtil;
import com.squareup.timessquare.MonthCellDescriptor.RangeState;

public class CalendarCellView extends TextView {
  private static final int[] STATE_SELECTABLE = {
      R.attr.state_selectable
  };
  private static final int[] STATE_CURRENT_MONTH = {
      R.attr.state_current_month
  };
  private static final int[] STATE_TODAY = {
      R.attr.state_today
  };
  private static final int[] STATE_HIGHLIGHTED = {
      R.attr.state_highlighted
  };
  private static final int[] STATE_RANGE_FIRST = {
      R.attr.state_range_first
  };
  private static final int[] STATE_RANGE_MIDDLE = {
      R.attr.state_range_middle
  };
  private static final int[] STATE_RANGE_LAST = {
      R.attr.state_range_last
  };

  private boolean isSelectable = false;
  private boolean isCurrentMonth = false;
  private boolean isToday = false;
  private boolean isHighlighted = false;
  private RangeState rangeState = RangeState.NONE;
  
  private Paint datePaint;
  private Paint todayPaint;

  @SuppressWarnings("UnusedDeclaration")
  public CalendarCellView(Context context, AttributeSet attrs) {
    super(context, attrs);
    
    datePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    datePaint.setColor(0xffff0000);
    
    todayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    todayPaint.setColor(0xffffffff);
  }

  public void setSelectable(boolean isSelectable) {
    this.isSelectable = isSelectable;
    refreshDrawableState();
  }

  public void setCurrentMonth(boolean isCurrentMonth) {
    this.isCurrentMonth = isCurrentMonth;
    refreshDrawableState();
  }

  public void setToday(boolean isToday) {
    this.isToday = isToday;
    refreshDrawableState();
  }

  public void setRangeState(MonthCellDescriptor.RangeState rangeState) {
    this.rangeState = rangeState;
    refreshDrawableState();
  }
  
  public void setHighlighted(boolean highlighted) {
    isHighlighted = highlighted;
    refreshDrawableState();
  }

  @Override protected int[] onCreateDrawableState(int extraSpace) {
    final int[] drawableState = super.onCreateDrawableState(extraSpace + 5);

    if (isSelectable) {
      mergeDrawableStates(drawableState, STATE_SELECTABLE);
    }
    

    if (isCurrentMonth) {
      mergeDrawableStates(drawableState, STATE_CURRENT_MONTH);
    }

    if (isToday) {
      mergeDrawableStates(drawableState, STATE_TODAY);
    }

    if (isHighlighted) {
      mergeDrawableStates(drawableState, STATE_HIGHLIGHTED);
    }
    

    if (rangeState == MonthCellDescriptor.RangeState.FIRST) {
      mergeDrawableStates(drawableState, STATE_RANGE_FIRST);
    } else if (rangeState == MonthCellDescriptor.RangeState.MIDDLE) {
      mergeDrawableStates(drawableState, STATE_RANGE_MIDDLE);
    } else if (rangeState == RangeState.LAST) {
      mergeDrawableStates(drawableState, STATE_RANGE_LAST);
    }

    return drawableState;
  }
  
  @Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
	  	if (isToday) {
			canvas.drawCircle(getWidth() / 2 + DisplayUtil.getSize(getContext(), 3), DisplayUtil.getSize(getContext(), 34), DisplayUtil.getSize(getContext(), 32), datePaint);
			
			canvas.drawCircle(getWidth() / 2 + DisplayUtil.getSize(getContext(), 3), getHeight() - DisplayUtil.getSize(getContext(), 30), DisplayUtil.getSize(getContext(), 8), todayPaint);
		}
		super.onDraw(canvas);
		/*if (isSelectable) {
			int height = getHeight();
			FontMetrics fm = datePaint.getFontMetrics();   
	//        Log.d("=AAA=","leading = " + fm.leading + " ascent = " + fm.ascent + " descent = " + fm.descent);
			canvas.drawText(priceStr + "元", DisplayUtil.getSize(getContext(), 6), height - fm.descent - DisplayUtil.getSize(getContext(), 3), datePaint);
		}*/
		
		
		
		if (isSelected() && !isToday) {
			canvas.drawCircle(getWidth() / 2 + DisplayUtil.getSize(getContext(), 3), getHeight() - DisplayUtil.getSize(getContext(), 30), DisplayUtil.getSize(getContext(), 8), datePaint);
		}
	}
}
