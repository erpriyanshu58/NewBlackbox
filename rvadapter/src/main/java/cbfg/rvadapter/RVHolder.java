package cbfg.rvadapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class RVHolder<T> extends RecyclerView.ViewHolder {
    protected T item;

    public RVHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void setContent(T item, boolean isSelected, Object payload);

    public T getItem() {
        return item;
    }
}
