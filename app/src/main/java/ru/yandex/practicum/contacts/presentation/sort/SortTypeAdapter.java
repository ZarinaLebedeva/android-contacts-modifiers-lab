package ru.yandex.practicum.contacts.presentation.sort;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.AdapterListUpdateCallback;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.function.Consumer;

import ru.yandex.practicum.contacts.R;
import ru.yandex.practicum.contacts.databinding.ItemSortBinding;
import ru.yandex.practicum.contacts.presentation.base.BaseListDiffCallback;
import ru.yandex.practicum.contacts.presentation.sort.model.SortType;

public class SortTypeAdapter extends RecyclerView.Adapter<SortTypeAdapter.ViewHolder> {

    private final AsyncListDiffer<SortTypeUI> differ = new AsyncListDiffer<>(
            new AdapterListUpdateCallback(this),
            new AsyncDifferConfig.Builder<>(new BaseListDiffCallback<SortTypeUI>()).build()
    );

    private final Consumer<SortTypeUI> clickListener;

    public SortTypeAdapter(Consumer<SortTypeUI> clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final ItemSortBinding binding = ItemSortBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(differ.getCurrentList().get(position));
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public void setItems(List<SortTypeUI> items) {
        differ.submitList(items);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemSortBinding binding;

        private SortTypeUI data;

        public ViewHolder(@NonNull ItemSortBinding binding, Consumer<SortTypeUI> clickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(v -> clickListener.accept(data));
        }

        public void bind(SortTypeUI data) {
            this.data = data;
            final int sortResId = resource(data.getSortType());
            binding.text.setText(sortResId);
            binding.selected.setVisibility(data.isSelected() ? View.VISIBLE : View.GONE);
        }

        private int resource(String sortType) {
            switch (sortType) {
                case SortType.BY_NAME:
                    return R.string.sort_by_name;
                case SortType.BY_NAME_REVERSED:
                    return R.string.sort_by_name_reversed;
                case SortType.BY_SURNAME:
                    return R.string.sort_by_surname;
                case SortType.BY_SURNAME_REVERSED:
                    return R.string.sort_by_surname_reversed;
                default:
                    throw new IllegalArgumentException("Not supported SortType");
            }
        }
    }


}
