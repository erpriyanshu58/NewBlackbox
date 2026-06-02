package com.github.nukc.stateview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A simple view that switches between content, loading, and empty states.
 * Drop-in replacement for com.github.nukc:StateView JitPack library.
 */
public class StateView extends FrameLayout {

    private View contentView;
    private View loadingView;
    private View emptyView;

    private int loadingLayoutRes = 0;
    private int emptyLayoutRes = 0;

    public StateView(@NonNull Context context) {
        this(context, null);
    }

    public StateView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // Create default loading view
        loadingView = createDefaultLoadingView();
        addView(loadingView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        // Create default empty view
        emptyView = createDefaultEmptyView();
        addView(emptyView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        // Hide both by default
        loadingView.setVisibility(GONE);
        emptyView.setVisibility(GONE);
    }

    private View createDefaultLoadingView() {
        androidx.appcompat.widget.AppCompatImageView iv = new androidx.appcompat.widget.AppCompatImageView(getContext());
        iv.setImageResource(android.R.drawable.ic_menu_rotate);
        iv.setContentDescription("Loading");
        return iv;
    }

    private View createDefaultEmptyView() {
        TextView tv = new TextView(getContext());
        tv.setText("No data");
        tv.setTextColor(0xFF888888);
        tv.setTextSize(14);
        tv.setGravity(android.view.Gravity.CENTER);
        return tv;
    }

    /**
     * Show the content view and hide loading/empty states.
     */
    public void showContent() {
        if (loadingView != null) loadingView.setVisibility(GONE);
        if (emptyView != null) emptyView.setVisibility(GONE);
        if (contentView != null) contentView.setVisibility(VISIBLE);
    }

    /**
     * Show the loading state and hide content/empty views.
     */
    public void showLoading() {
        if (emptyView != null) emptyView.setVisibility(GONE);
        if (contentView != null) contentView.setVisibility(GONE);
        if (loadingView != null) loadingView.setVisibility(VISIBLE);
    }

    /**
     * Show the empty state and hide content/loading views.
     */
    public void showEmpty() {
        if (loadingView != null) loadingView.setVisibility(GONE);
        if (contentView != null) contentView.setVisibility(GONE);
        if (emptyView != null) emptyView.setVisibility(VISIBLE);
    }

    /**
     * Set a custom loading view.
     */
    public void setLoadingView(View view) {
        if (loadingView != null) {
            removeView(loadingView);
        }
        loadingView = view;
        if (loadingView != null) {
            addView(loadingView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            loadingView.setVisibility(GONE);
        }
    }

    /**
     * Set a custom empty view.
     */
    public void setEmptyView(View view) {
        if (emptyView != null) {
            removeView(emptyView);
        }
        emptyView = view;
        if (emptyView != null) {
            addView(emptyView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            emptyView.setVisibility(GONE);
        }
    }

    /**
     * Set the content view. Call this after adding children to this StateView.
     * The first child that is not loadingView or emptyView is treated as content.
     */
    public void setContentView(View view) {
        if (contentView != null) {
            removeView(contentView);
        }
        contentView = view;
        if (contentView != null) {
            addView(contentView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
    }

    @Override
    public void addView(View child) {
        // Treat the first added child that isn't our own as content
        if (child != loadingView && child != emptyView && contentView == null) {
            contentView = child;
        }
        super.addView(child);
    }

    /**
     * Set loading view from a layout resource.
     */
    public void setLoadingLayoutRes(int layoutRes) {
        this.loadingLayoutRes = layoutRes;
        if (layoutRes > 0) {
            View view = LayoutInflater.from(getContext()).inflate(layoutRes, this, false);
            setLoadingView(view);
        }
    }

    /**
     * Set empty view from a layout resource.
     */
    public void setEmptyLayoutRes(int layoutRes) {
        this.emptyLayoutRes = layoutRes;
        if (layoutRes > 0) {
            View view = LayoutInflater.from(getContext()).inflate(layoutRes, this, false);
            setEmptyView(view);
        }
    }
}
