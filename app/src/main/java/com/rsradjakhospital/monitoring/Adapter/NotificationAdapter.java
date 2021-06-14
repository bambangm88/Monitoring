package com.rsradjakhospital.monitoring.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.rsradjakhospital.monitoring.Model.ResponseEntityNotification;
import com.rsradjakhospital.monitoring.R;
import com.rsradjakhospital.monitoring.sessionManager.SessionManager;

import java.util.List;

import butterknife.ButterKnife;




public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.AdapterHolder>{

    List<ResponseEntityNotification> AllPaymentItemList;
    Context mContext;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    // The minimum amount of items to have below your current scroll position
// before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;



    public NotificationAdapter(Context context, List<ResponseEntityNotification> paymentList) {
        this.mContext = context;
        AllPaymentItemList = paymentList;



    }

    @Override
    public AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new AdapterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterHolder holder, int position) {
        final ResponseEntityNotification responseEntityRiwayat = AllPaymentItemList.get(position);

        String pengirim = responseEntityRiwayat.getMessageOrigin();
        String body = responseEntityRiwayat.getMessageBody();
        String tanggal = responseEntityRiwayat.getC_MessageDate();


        String Patient = responseEntityRiwayat.getFirstName();
        String Room = responseEntityRiwayat.getParam3();
        String CaseId = responseEntityRiwayat.getParam1();
        String Status = responseEntityRiwayat.getParam2();
        String StatusNotif = responseEntityRiwayat.getStatus();

        holder.tvNewest.setText("");

        if (StatusNotif.equals("01")){
            holder.tvNewest.setText("New");
            holder.lyAtas.setBackgroundResource(R.color.colorRencanaPulang);
            holder.lyBawah.setBackgroundResource(R.color.colorRencanaPulang);
        }else{

            holder.lyAtas.setBackgroundResource(R.color.colorUtama);
            holder.lyBawah.setBackgroundResource(R.color.colorUtama);

        }


        if (Status.equals("KOSONG") || Status.equals("BOOKING") || Status.equals("TITIPAN") || Status.equals("RUSAK")  ){

            holder.tvHeader.setText(pengirim);
            holder.tvBody.setText(body+ " " + Status);
            holder.tvDate.setText(tanggal);

            holder.layPatient.setVisibility(View.GONE);
            holder.layCaseId.setVisibility(View.GONE);

            holder.tvRoom.setText(": "+Room);
            holder.tvCaseid.setText(": "+CaseId);
            holder.tvStatus.setText(": "+Status);

        }


        if (Status.equals("RENCANA PULANG") || Status.equals("ISI") || Status.equals("CLOSED")   ){

            holder.tvHeader.setText(pengirim);
            holder.tvBody.setText(body+ " " + Status);
            holder.tvDate.setText(tanggal);

            holder.layPatient.setVisibility(View.VISIBLE);
            holder.layCaseId.setVisibility(View.VISIBLE);

            holder.tvRoom.setText(": "+Room);
            holder.tvPatient.setText(": "+Patient);
            holder.tvCaseid.setText(": "+CaseId);
            holder.tvStatus.setText(": "+Status);

        }


    }

    @Override
    public int getItemCount() {
        return AllPaymentItemList.size();
    }




    public class AdapterHolder extends RecyclerView.ViewHolder{



        TextView tvHeader , tvBody , tvDate  , tvNewest   ;
        TextView tvPatient , tvRoom , tvStatus  , tvCaseid   ;

        LinearLayout layPatient , layCaseId , lyAtas , lyBawah ;



        SessionManager session;



        AlertDialog.Builder dialog;


        public AdapterHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            tvHeader = itemView.findViewById(R.id.tvHeader);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvNewest = itemView.findViewById(R.id.tvNewest);

            layCaseId = itemView.findViewById(R.id.lay_caseId);
            layPatient = itemView.findViewById(R.id.lay_patient);


            tvPatient = itemView.findViewById(R.id.tvPatient);
            tvCaseid = itemView.findViewById(R.id.CaseID);
            tvStatus = itemView.findViewById(R.id.Status);
            tvRoom = itemView.findViewById(R.id.officeRoom);
            lyAtas = itemView.findViewById(R.id.lyAtas);
            lyBawah = itemView.findViewById(R.id.lyBawah);

            session = new SessionManager(mContext);




        }
    }






}
