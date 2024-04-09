    package com.poly.moneylover.adapters;

    import android.annotation.SuppressLint;
    import android.content.Context;
    import android.content.res.Resources;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.core.content.ContextCompat;
    import androidx.recyclerview.widget.RecyclerView;

    import com.poly.moneylover.R;
    import com.poly.moneylover.interfaces.ItemOnClickBudget;
    import com.poly.moneylover.models.Budget;

    import java.util.ArrayList;
    import java.util.List;

    public class ItemBudgetAdapter extends RecyclerView.Adapter<ItemBudgetAdapter.ItemBudgetViewHolder> {

        private List<Budget> list = new ArrayList<>();
        private int positionSelected = 0;

        private final ItemOnClickBudget onclick;
        private Context context;
        private boolean isLongClick = false;
        private OnLongClickListener onLongClickListener;

        public interface OnLongClickListener {
            void onLongClick(Budget budget);
        }

        public void setOnLongClickListener(OnLongClickListener listener) {
            this.onLongClickListener = listener;
        }

        public ItemBudgetAdapter(ItemOnClickBudget onclick, Context context) {
            this.onclick = onclick;
            this.context = context;
        }


        @SuppressLint("NotifyDataSetChanged")
        public void setPositionSelected(int index) {
            notifyItemChanged(positionSelected);
            notifyItemChanged(index);
            notifyDataSetChanged();
            positionSelected = index;
            Budget budget = list.get(index);
            onclick.onSelectdBudgetCategory(budget);
        }

        @SuppressLint("NotifyDataSetChanged")
        public void setList(List<Budget> data) {
            list.clear();
            list.addAll(data);
            notifyDataSetChanged();
        }


        @NonNull
        @Override
        public ItemBudgetViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_budget, viewGroup, false);
            return new ItemBudgetViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemBudgetViewHolder itemViewHolder, int i) {
            Budget item = list.get(i);
            Resources resources = context.getResources();

            if (item.getCategory().getIcon() != 0) {
                itemViewHolder.imgIcon.setVisibility(View.VISIBLE);
                itemViewHolder.imgIcon.setImageResource(item.getCategory().getIcon());
                int colorId = list.get(i).getCategory().getColor();
                if (isValidColorResource(resources, colorId)) {
                    int color = ContextCompat.getColor(context, colorId);
                    itemViewHolder.imgIcon.setColorFilter(color);
                } else {
                    itemViewHolder.imgIcon.setColorFilter(item.getCategory().getColor());
                }

            } else {
                itemViewHolder.imgIcon.setVisibility(View.GONE);
            }
            itemViewHolder.tvTitle.setText(item.getNote());
            itemViewHolder.tvDate.setText(item.getCategory().getName() + "/" + getDisplayText(item.getFrequency()));
            itemViewHolder.tvPrice.setText(String.valueOf(item.getPrice()) + "đ");

            itemViewHolder.itemView.setSelected(positionSelected == i);
            itemViewHolder.itemView.setOnClickListener(v -> {
                setPositionSelected(i);
            });
            itemViewHolder.itemView.setOnLongClickListener(v -> {
                if (onLongClickListener != null) {
                    onLongClickListener.onLongClick(item);
                }
                return true;
            });
        }
        private boolean isValidColorResource(Resources resources, int colorId) {
            try {
                resources.getColor(colorId);
                return true;
            } catch (Resources.NotFoundException e) {
                return false;
            }
        }
        public String getDisplayText(String frequency) {
            switch (frequency) {
                case "Never":
                    return "Không bao giờ";
                case "Daily":
                    return "Hàng ngày";
                case "Weekly":
                    return "Hàng tuần";
                case "Biweekly":
                    return "Hàng hai tuần";
                case "Triweekly":
                    return "Hàng ba tuần";
                case "Quadweekly":
                    return "Hàng bốn tuần";
                case "Monthly":
                    return "Hàng tháng";
                case "Bimonthly":
                    return "Hàng hai tháng";
                case "Trimonthly":
                    return "Hàng ba tháng";
                case "Quadmonthly":
                    return "Hàng bốn tháng";
                case "Semiannually":
                    return "Hàng nửa năm";
                case "Annually":
                    return "Hàng năm";
                default:
                    return frequency;
            }
        }
        @Override
        public int getItemCount() {
            return list != null ? list.size() : 1;
        }

        public static class ItemBudgetViewHolder extends RecyclerView.ViewHolder {
            private final ImageView imgIcon;
            private final TextView tvTitle, tvDate, tvPrice;

            public ItemBudgetViewHolder(@NonNull View itemView) {
                super(itemView);
                imgIcon = itemView.findViewById(R.id.img_view);
                tvTitle = itemView.findViewById(R.id.tv_title);
                tvDate = itemView.findViewById(R.id.tv_date);
                tvPrice = itemView.findViewById(R.id.tv_price);
            }
        }
    }