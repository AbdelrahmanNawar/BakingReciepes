package bakingreciepes;

import android.content.Context;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

class SetGridLayout extends GridLayoutManager {
    private int myColumnWidth;
    private boolean ifWidthChanged = true;

    SetGridLayout(@NonNull Context context) {
        super(context, 1);
        setColumnWidth(checkColumnWidth(context, 600));
    }

    private int checkColumnWidth(@NonNull Context context, int columnWidth) {
        if (columnWidth <= 0) { /* Set default columnWidth value (48dp here). It is better to move this constant to static constant on top, but we need context to convert it to dp, so can't really do so. */
            columnWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, context.getResources().getDisplayMetrics());
        }
        return columnWidth;

    }

    private void setColumnWidth(int newWidth) {
        if (newWidth > 0 && newWidth != myColumnWidth) {
            myColumnWidth = newWidth;
            ifWidthChanged = true;
        }

    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (ifWidthChanged && myColumnWidth > 0) {
            int totalSpace = 0;
            if (getOrientation() == RecyclerView.VERTICAL) {
                totalSpace = getWidth() - getPaddingRight() - getPaddingLeft();
            }
            if (getOrientation() == HORIZONTAL) {
                totalSpace = getHeight() - getPaddingTop() - getPaddingBottom();
            }
            int spanCount = Math.max(1, totalSpace / myColumnWidth);
            setSpanCount(spanCount);
            ifWidthChanged = false;
        }
        super.onLayoutChildren(recycler, state);
    }
}
