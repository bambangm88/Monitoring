package com.rsradjakhospital.monitoring.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rsradjakhospital.monitoring.Model.ResponseEntityDoctor;
import com.rsradjakhospital.monitoring.R;

import java.util.List;

import butterknife.ButterKnife;

import static com.rsradjakhospital.monitoring.DetailRoom.TAG_DOCTOR;
import static com.rsradjakhospital.monitoring.DetailRoom.TAG_DOCTOR_ID;


public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.AdapterHolder>{

    List<ResponseEntityDoctor> AllPaymentItemList;
    Context mContext;


    public DoctorAdapter(Context context, List<ResponseEntityDoctor> paymentList){
        this.mContext = context;
        AllPaymentItemList = paymentList;
    }

    @Override
    public AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor, parent, false);
        return new AdapterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterHolder holder, int position) {
        final ResponseEntityDoctor responsePaymentMethod = AllPaymentItemList.get(position);

        String firstName = responsePaymentMethod.getFirstName();
        String id = responsePaymentMethod.getDoctorID();



        holder.doctor.setText(firstName);

        holder.btnItemRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TAG_DOCTOR = firstName ;
                TAG_DOCTOR_ID = id ;
                ((Activity)mContext).finish();

            }
        });



    }

    @Override
    public int getItemCount() {
        return AllPaymentItemList.size();
    }

    public class AdapterHolder extends RecyclerView.ViewHolder{



        TextView tvDetail;
        TextView tvDStatus;
        TextView doctor;
        ImageView imageRoom ;
        LinearLayout bgImage ;
        RelativeLayout btnItemRow;
        CardView cv_row ;

        AlertDialog.Builder dialog;


        public AdapterHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

//            bgImage = itemView.findViewById(R.id.bgImage);
            doctor = itemView.findViewById(R.id.tvDoctor);

            imageRoom = itemView.findViewById(R.id.ivTextDrawable);
            btnItemRow = itemView.findViewById(R.id.btn_itemRow);
            cv_row = itemView.findViewById(R.id.card_view);

            dialog = new AlertDialog.Builder(mContext);



        }
    }






}
