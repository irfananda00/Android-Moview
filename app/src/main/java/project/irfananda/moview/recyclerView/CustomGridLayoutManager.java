package project.irfananda.moview.recyclerView;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

/**
 * Created by irfananda on 13/04/16.
 */
public class CustomGridLayoutManager extends GridLayoutManager {

    private boolean isScrollEnable = true;

    public CustomGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public void setScrollEnable(boolean scrollEnable) {
        isScrollEnable = scrollEnable;
    }

    @Override
    public boolean canScrollVertically() {
        return super.canScrollVertically() && isScrollEnable ;
    }
}
