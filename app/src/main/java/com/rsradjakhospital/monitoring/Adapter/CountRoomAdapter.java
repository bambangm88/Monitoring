package com.rsradjakhospital.monitoring.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.rsradjakhospital.monitoring.Model.ResponseEntityCountRoomByCategory;
import com.rsradjakhospital.monitoring.R;

import java.util.List;

import butterknife.ButterKnife;


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


    }

    @Override
    public int getItemCount() {
        return AllPaymentItemList.size();
    }

    public class AdapterHolder extends RecyclerView.ViewHolder{




        TextView doctor;
        TextView TVjumlah;

        AlertDialog.Builder dialog;


        public AdapterHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

//            bgImage = itemView.findViewById(R.id.bgImage);
            doctor = itemView.findViewById(R.id.tvDoctor);

           TVjumlah = itemView.findViewById(R.id.tvJumlah);





        }
    }






}
