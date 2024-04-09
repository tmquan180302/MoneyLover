package com.poly.moneylover.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.moneylover.R;
import com.poly.moneylover.interfaces.ItemOnclickService;
import com.poly.moneylover.models.Category;
import com.poly.moneylover.models.OtherItem;
import com.poly.moneylover.models.Service;
import com.poly.moneylover.utils.Convert;

import java.util.ArrayList;
import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {

    private List<Service> list;
    private ItemOnclickService itemOnclickService;

    public ServiceAdapter(List<Service> list, ItemOnclickService itemOnclickService) {
        this.list = list;
        this.itemOnclickService = itemOnclickService;
    }


    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, parent, false);
        return new ServiceAdapter.ServiceViewHolder(itemView, itemOnclickService);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        final Service service = list.get(position);
        holder.tvServiceName.setText(service.getName());
        holder.tvServiceDes.setText(service.getDescription());
        holder.tvServicePrice.setText("Giá:" + Convert.formatNumberCurrent(String.valueOf(service.getPrice())) + "VNĐ");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ServiceViewHolder extends RecyclerView.ViewHolder {
        private TextView tvServiceName, tvServiceDes, tvServicePrice;

        private LinearLayout linearLayout;

        public ServiceViewHolder(@NonNull View itemView, final ItemOnclickService itemOnclickService) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.backGround);
            tvServiceName = itemView.findViewById(R.id.tv_serviceName);
            tvServiceDes = itemView.findViewById(R.id.tv_serviceDes);
            tvServicePrice = itemView.findViewById(R.id.tv_servicePrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && itemOnclickService != null) {
                        itemOnclickService.onSelectedService(list.get(position));
                        itemOnclickService.onChangeBg(list.get(position), linearLayout);

                    }
                }
            });

        }
    }
}
