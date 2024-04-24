package com.poly.moneylover.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanphamdemo.R;
import com.example.sanphamdemo.interfaceall.Interface_HoaDonCongTy;
import com.example.sanphamdemo.interfaceall.Interface_UngVien;
import com.example.sanphamdemo.interfaceall.Interface_XoaYeuCau;
import com.example.sanphamdemo.interfaceall.Interfave_AddHoaDon;
import com.example.sanphamdemo.user.AddHoaDon;
import com.example.sanphamdemo.user.ConfirmationRequest;
import com.example.sanphamdemo.user.Delete_YeuCau;
import com.example.sanphamdemo.user.HoaDonCongTyy;
import com.example.sanphamdemo.user.RequestModel;
import com.example.sanphamdemo.user.ThongBao;
import com.example.sanphamdemo.user.UngVien;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {


    Interfave_AddHoaDon apiService;

     Context context;
    ThongBao currentItem;
    private ArrayList<RequestModel> arrayList;
    RequestModel request;
    public RequestAdapter(Context context) {

        this.context = context;
    }
    public void setData(ArrayList<RequestModel> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged(); // Cập nhật RecyclerView khi dữ liệu thay đổi
    }
    private List<ThongBao> datathongbao;


    public RequestAdapter(List<ThongBao> datathongbao) {
        this.datathongbao = datathongbao;
    }

    public void setDataList(ArrayList<ThongBao> datathongbao) {
        this.datathongbao = datathongbao;
        notifyDataSetChanged(); // Cập nhật RecyclerView khi dữ liệu thay đổi
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


         request = arrayList.get(position);
        if (request != null) {
            // Thực hiện xử lý khi request không null
            // Ví dụ: holder.masothuexacnhan.setText("Mã Số Thuế : " + request.getIdHoaDonCongTy());
            holder.masothuexacnhan.setText("Mã Yêu Cầu : " + request.getRequestId());
                holder.tencongtyxacnhan.setText(request.getTencongty());
                holder.thanhtienxacnhan.setText(request.getGiatien());
           holder.detail_item.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

               }
           });
            int red = ContextCompat.getColor(context, R.color.red);
            int green = ContextCompat.getColor(context, R.color.green);
            // Xác nhận yêu cầu khi nhấn nút
           
            holder.confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Yêu cầu của id " + request.getRequestId() + " bạn đang chờ xác nhận. Chấp nhận hoặc từ chối?")
                            .setPositiveButton("Chấp nhận", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Người dùng chọn "Chấp nhận", gửi yêu cầu xác nhận lên server
                                    sendConfirmationRequest(request.getRequestId(), true);
                                    holder.confirm.setText("Yêu Cầu Đã Được Chấp Nhận");
                                    holder.confirm.setBackgroundColor(green);
                                }
                            })
                            .setNegativeButton("Từ chối", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String a = String.valueOf(request.getRequestId());


                                    holder.confirm.setText("Yêu Cầu Bị Từ Chối");
                                    holder.confirm.setBackgroundColor(red);
                                    // Người dùng chọn "Từ chối", gửi yêu cầu từ chối lên server
                                    // Tạo Retrofit instance
                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl("http://10.24.4.190:3000/")
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();

                                    Interface_XoaYeuCau interfaceDelete = retrofit.create(Interface_XoaYeuCau.class);
                                    Call<Delete_YeuCau> call = interfaceDelete.deleteYeucau(a);

                                    call.enqueue(new Callback<Delete_YeuCau>() {
                                        @Override
                                        public void onResponse(Call<Delete_YeuCau> call, Response<Delete_YeuCau> response) {
                                            Delete_YeuCau svrResponseDelete = response.body(); // lay kq tu serrverr
                                            Toast.makeText(context, "xóa thành công " + svrResponseDelete.getMessage(), Toast.LENGTH_SHORT).show();
                                            // inteloadData.loadData();
                                        }

                                        @Override
                                        public void onFailure(Call<Delete_YeuCau> call, Throwable t) {
                                            Toast.makeText(context, "xóa thất bại " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    Retrofit retrofit9 = new Retrofit.Builder()
                                            .baseUrl("http://10.24.4.190:3000/") // Địa chỉ của server
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();

                                    Interface_HoaDonCongTy apiService9 = retrofit9.create(Interface_HoaDonCongTy.class);

                                    Call<HoaDonCongTyy> call19 = apiService9.deletecongty(Integer.parseInt(request.getRequestId()));
                                    call19.enqueue(new Callback<HoaDonCongTyy>() {
                                        @Override
                                        public void onResponse(Call<HoaDonCongTyy> call19, Response<HoaDonCongTyy> response) {
                                            if (response.isSuccessful()) {


                                            } else {
                                                // Xử lý lỗi khi xác nhận từ server
                                                showToast("Có lỗi xảy ra khi xác nhận yêu cầu.");
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<HoaDonCongTyy> call19, Throwable t) {
                                            // Xử lý lỗi kết nối
                                            showToast("Lỗi kết nối đến server.");
                                        }
                                    });

                                    // Gọi sendConfirmationRequest với isAccepted là false

                                }
                            });
                    builder.create().show();
                }
            });
        }
    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

       LinearLayout detail_item;
         LinearLayout linearHeaderPost;
         TextView masothuexacnhan;
         RelativeLayout bgIsNewView;
         TextView tencongtyxacnhan;
         TextView thanhtienxacnhan;
         TextView confirm;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);




         //   detailItem = (LinearLayout) itemView.findViewById(R.id.detail_item);
            linearHeaderPost = (LinearLayout) itemView.findViewById(R.id.linearHeader_post);
            detail_item = (LinearLayout) itemView.findViewById(R.id.detail_item);
            masothuexacnhan = (TextView) itemView.findViewById(R.id.masothuexacnhan);
            bgIsNewView = (RelativeLayout) itemView.findViewById(R.id.bg_isNewView);
            tencongtyxacnhan = (TextView) itemView.findViewById(R.id.tencongtyxacnhan);
            thanhtienxacnhan = (TextView) itemView.findViewById(R.id.thanhtienxacnhan);
            confirm = (TextView) itemView.findViewById(R.id.confirm);

        }


    }

    // Hàm hiển thị dialog xác nhận



    private void sendConfirmationRequest(String requestId, boolean isAccepted) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.24.4.190:3000/") // Địa chỉ của server
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(Interfave_AddHoaDon.class);
        ConfirmationRequest confirmationRequest = new ConfirmationRequest(requestId, isAccepted);
        Call<RequestModel> call = apiService.confirmRequest(confirmationRequest);
        call.enqueue(new Callback<RequestModel>() {
            @Override
            public void onResponse(Call<RequestModel> call, Response<RequestModel> response) {
                if (response.isSuccessful()) {
                    // Xử lý khi xác nhận từ server thành công
                    //      handleConfirmationResponse(response.body().getMessage());

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://10.24.4.190:3000/") // Địa chỉ của server
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                   Interface_UngVien apiService = retrofit.create(Interface_UngVien.class);

                    Call<UngVien> call1 = apiService.updateHoaDongCongTychoUngVien(request.getIdUngVien(),request.getRequestId());
                    call1.enqueue(new Callback<UngVien>() {
                        @Override
                        public void onResponse(Call<UngVien> call1, Response<UngVien> response) {
                            if (response.isSuccessful()) {


                            } else {
                                // Xử lý lỗi khi xác nhận từ server

                            }
                        }

                        @Override
                        public void onFailure(Call<UngVien> call1, Throwable t) {
                            // Xử lý lỗi kết nối

                        }
                    });
                    ///////////////
                    Retrofit retrofit9 = new Retrofit.Builder()
                            .baseUrl("http://10.24.4.190:3000/") // Địa chỉ của server
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    Interface_HoaDonCongTy apiService9 = retrofit9.create(Interface_HoaDonCongTy.class);

                    Call<HoaDonCongTyy> call19 = apiService9.update1(Integer.parseInt(request.getRequestId()));
                    call19.enqueue(new Callback<HoaDonCongTyy>() {
                        @Override
                        public void onResponse(Call<HoaDonCongTyy> call19, Response<HoaDonCongTyy> response) {
                            if (response.isSuccessful()) {


                            } else {
                                // Xử lý lỗi khi xác nhận từ server

                            }
                        }

                        @Override
                        public void onFailure(Call<HoaDonCongTyy> call19, Throwable t) {
                            // Xử lý lỗi kết nối

                        }
                    });
                    /////////////////


                    Retrofit retrofit2 = new Retrofit.Builder()
                            .baseUrl("http://10.24.4.190:3000/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    Interface_XoaYeuCau interfaceDelete = retrofit2.create(Interface_XoaYeuCau.class);
                    Call<Delete_YeuCau> call5 = interfaceDelete.deleteYeucau(request.getRequestId());

                    call5.enqueue(new Callback<Delete_YeuCau>() {
                        @Override
                        public void onResponse(Call<Delete_YeuCau> call5, Response<Delete_YeuCau> response) {
                            Delete_YeuCau svrResponseDelete = response.body(); // lay kq tu serrverr

                            // inteloadData.loadData();
                        }

                        @Override
                        public void onFailure(Call<Delete_YeuCau> call, Throwable t) {

                        }
                    });


                } else {
                    // Xử lý lỗi khi xác nhận từ server

                }
            }

            @Override
            public void onFailure(Call<RequestModel> call5, Throwable t) {
                // Xử lý lỗi kết nối

            }
        });
    }






    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private void handleErrorResponse(Response<AddHoaDon> response) {
        // Xử lý lỗi khi thêm mới đối tượng
        if (response.code() == 400) {
            // Lỗi Bad Request - Yêu cầu không hợp lệ
            Toast.makeText(context, "Yêu cầu không hợp lệ.", Toast.LENGTH_SHORT).show();
        } else {
            // Xử lý các mã lỗi khác tùy theo yêu cầu của bạn
            Toast.makeText(context, "Có lỗi xảy ra khi thêm mới đối tượng.", Toast.LENGTH_SHORT).show();
        }
    }

}
