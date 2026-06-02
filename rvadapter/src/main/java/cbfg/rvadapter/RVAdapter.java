package cbfg.rvadapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RVAdapter<T> extends RecyclerView.Adapter<RVHolder<?>> {
    private final Context context;
    private final RVHolderFactory factory;
    private final List<T> items = new ArrayList<>();
    private int selectedPosition = -1;
    private OnItemClickListener<T> itemClickListener;
    private OnItemLongClickListener<T> itemLongClickListener;

    public interface OnItemClickListener<T> {
        void onItemClick(T item, int position);
    }

    public interface OnItemLongClickListener<T> {
        boolean onItemLongClick(T item, int position);
    }

    public RVAdapter(@NonNull Context context, @NonNull RVHolderFactory factory) {
        this.context = context;
        this.factory = factory;
    }

    public RVAdapter<T> bind(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(this);
        return this;
    }

    public void setItems(@Nullable List<T> items) {
        this.items.clear();
        if (items != null) {
            this.items.addAll(items);
        }
        selectedPosition = -1;
        notifyDataSetChanged();
    }

    public List<T> getItems() {
        return new ArrayList<>(items);
    }

    public T getItem(int position) {
        if (position >= 0 && position < items.size()) {
            return items.get(position);
        }
        return null;
    }

    public void add(@NonNull T item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void add(int position, @NonNull T item) {
        items.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        if (position >= 0 && position < items.size()) {
            items.remove(position);
            notifyItemRemoved(position);
            if (selectedPosition == position) {
                selectedPosition = -1;
            } else if (selectedPosition > position) {
                selectedPosition--;
            }
        }
    }

    public void clear() {
        items.clear();
        selectedPosition = -1;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener<T> listener) {
        this.itemClickListener = listener;
    }

    public void setOnItemLongClickListener(@Nullable OnItemLongClickListener<T> listener) {
        this.itemLongClickListener = listener;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public T getSelectedItem() {
        if (selectedPosition >= 0 && selectedPosition < items.size()) {
            return items.get(selectedPosition);
        }
        return null;
    }

    public void setSelectedPosition(int position) {
        int oldPosition = selectedPosition;
        selectedPosition = position;
        if (oldPosition >= 0) {
            notifyItemChanged(oldPosition);
        }
        if (position >= 0) {
            notifyItemChanged(position);
        }
    }

    @NonNull
    @Override
    public RVHolder<?> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        T item = null;
        if (viewType >= 0 && viewType < items.size()) {
            item = items.get(viewType);
        }
        return factory.createViewHolder(parent, viewType, item);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(@NonNull RVHolder<?> holder, int position) {
        T item = items.get(position);
        ((RVHolder<T>) holder).item = item;
        boolean isSelected = position == selectedPosition;
        holder.setContent(item, isSelected, null);

        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(item, holder.getAdapterPosition());
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (itemLongClickListener != null) {
                return itemLongClickListener.onItemLongClick(item, holder.getAdapterPosition());
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
