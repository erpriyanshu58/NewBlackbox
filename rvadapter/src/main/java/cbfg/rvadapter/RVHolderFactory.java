package cbfg.rvadapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class RVHolderFactory {
    @NonNull
    public abstract RVHolder<?> createViewHolder(@Nullable ViewGroup parent, int viewType, @Nullable Object item);

    protected View inflate(int layoutId, @Nullable ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
    }
}
