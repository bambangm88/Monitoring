package com.rsradjakhospital.monitoring.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.rsradjakhospital.monitoring.MainActivity;
import com.rsradjakhospital.monitoring.Model.ResponseEntityCountRoomByCategory;
import com.rsradjakhospital.monitoring.R;
import com.rsradjakhospital.monitoring.listRuangan;

import java.util.List;

import butterknife.ButterKnife;

import static com.rsradjakhospital.monitoring.CountRoomActivity.TAG_STATUS_CATEGORY;
import static com.rsradjakhospital.monitoring.listRuangan.TAG_CATEGORY;
import static com.rsradjakhospital.monitoring.listRuangan.TAG_KELAS;


public class CountRoomAdapter extends RecyclerView.Adapter<CountRoomAdapter.AdapterHolder>{

    List<ResponseEntityCountRoomByCategory> AllPaymentItemList;
    Context mContext;


    public CountRoomAdapter(Context context, List<ResponseEntityCountRoomByCategory> paymentList){
        this.mContext = context;
        AllPaymentItemList = paymentList;
    }

    @Override
    public AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_count_room, parent, false);
        return new AdapterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterHolder holder, int position) {
        final ResponseEntityCountRoomByCategory responsePaymentMethod = AllPaymentItemList.get(position);

        String room = responsePaymentMethod.getCategory();
        String jumlah = responsePaymentMethod.getJumlah();



        holder.doctor.setText(room);
        holder.TVjumlah.setText(jumlah);
        holder.btnItemRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TAG_CATEGORY = room;
                TAG_KELAS = TAG_STATUS_CATEGORY ;
                Intent intent = new Intent(mContext, listRuangan.class);
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return AllPaymentItemList.size();
    }

    public class AdapterHolder extends RecyclerView.ViewHolder{




        TextView doctor;
        TextView TVjumlah;
        RelativeLayout btnItemRow ;
        AlertDialog.Builder dialog;


        public AdapterHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

//            bgImage = itemView.findViewById(R.id.bgImage);
            doctor = itemView.findViewById(R.id.tvDoctor);

           TVjumlah = itemView.findViewById(R.id.tvJumlah);
           btnItemRow = itemView.findViewById(R.id.btn_itemRow);





        }
    }






}
