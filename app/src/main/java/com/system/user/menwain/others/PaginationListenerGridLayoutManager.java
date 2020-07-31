package com.system.user.menwain.others;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PaginationListenerGridLayoutManager extends RecyclerView.OnScrollListener {
    public static final int PAGE_START = 1;
    @NonNull
//    private LinearLayoutManager layoutManager;
    private GridLayoutManager gridLayoutManager;
    /**
     * Set scrolling threshold here (for now i'm assuming 10 item in one page)
     */
  //  private static final int PAGE_SIZE = 15;

    public PaginationListenerGridLayoutManager(@NonNull GridLayoutManager gridLayoutManager) {
        this.gridLayoutManager = gridLayoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int visibleItemCount = gridLayoutManager.getChildCount();
        int totalItemCount = gridLayoutManager.getItemCount();
        int firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();
        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0/*&& totalItemCount >= PAGE_SIZE*/) {
                loadMoreItems();
            }
        }
    }

    protected abstract void loadMoreItems();

    protected abstract boolean isLastPage();

    protected abstract boolean isLoading();
}
