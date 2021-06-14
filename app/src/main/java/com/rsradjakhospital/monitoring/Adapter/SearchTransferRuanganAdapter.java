package com.rsradjakhospital.monitoring.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rsradjakhospital.monitoring.Model.ResponseEntityRoom;
import com.rsradjakhospital.monitoring.R;

import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;

import static com.rsradjakhospital.monitoring.TransferActivity.TAG_TRF_DST;

public class SearchTransferRuanganAdapter extends RecyclerView.Adapter<SearchTransferRuanganAdapter.AdapterHolder>{

    List<ResponseEntityRoom> AllPaymentItemList;
    Context mContext;

    private static final int LAYOUT_HEADER= 0;
    private static final int LAYOUT_CHILD= 1;


    public SearchTransferRuanganAdapter(Context context, List<ResponseEntityRoom> paymentList){
        this.mContext = context;
        AllPaymentItemList = paymentList;
    }

    @Override
    public int getItemViewType(int position)
    {

        if(AllPaymentItemList.get(position).getCatatan().equals("HEADER")) {
            return LAYOUT_HEADER;
        }else
            return LAYOUT_CHILD;



    }

    @Override
    public AdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        if(viewType==LAYOUT_HEADER){
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_header, parent, false);
        }else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_search_transfer, parent, false);
        }

        return new AdapterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterHolder holder, int position) {
        final ResponseEntityRoom responsePaymentMethod = AllPaymentItemList.get(position);



        if(holder.getItemViewType()== LAYOUT_HEADER)
        {
            holder.tvRoom.setText(responsePaymentMethod.getRoomHeader());
        }
        else {


            if (responsePaymentMethod.getOfficeName() == null) {

            }


            String category = responsePaymentMethod.getRoomHeader();
            String bed = responsePaymentMethod.getBed();
            String nama = responsePaymentMethod.getNama();


            holder.bedRoom.setText("BED "+bed);

            holder.tvDetail.setText(nama);

            holder.tvDStatus.setText(responsePaymentMethod.getPatientStatus());

            holder.imageRoom.setImageResource(R.drawable.room);

            holder.bgImage.setBackgroundColor(getRandomColorCode());

            if (responsePaymentMethod.getKelamin().equals("1")) {
                holder.bgImage.setBackgroundResource(R.color.colorP);
            } else {

                holder.bgImage.setBackgroundResource(R.color.colorL);
            }


            if (responsePaymentMethod.getPatientStatus().equals("KOSONG")) {
                holder.tvDetail.setText("");
                holder.cv_row.setBackgroundResource(R.color.colorKosong);
                holder.bgImage.setBackgroundResource(R.color.colorKosong);
            } else if (responsePaymentMethod.getPatientStatus().equals("RUSAK")) {
                holder.cv_row.setBackgroundResource(R.color.colorRusak);
                holder.tvDetail.setText("");
                holder.tvDStatus.setTextColor(Color.parseColor("#FFFFFF"));
            } else if (responsePaymentMethod.getPatientStatus().equals("TITIPAN")) {
                holder.cv_row.setBackgroundResource(R.color.colorTitipan);
            } else if (responsePaymentMethod.getPatientStatus().equals("RENCANA PULANG")) {
                holder.cv_row.setBackgroundResource(R.color.colorRencanaPulang);
            } else {
                holder.cv_row.setBackgroundResource(R.color.colorIsi);
            }


            holder.btnItemRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (responsePaymentMethod.getPatientStatus() == null) {
                        responsePaymentMethod.setPatientStatus("");
                    }

                    if (responsePaymentMethod.getPatientStatus().equals("ISI")) {
                        Toast.makeText(mContext, "STATUS RUANGAN ISI", Toast.LENGTH_LONG).show();
                    } else if (responsePaymentMethod.getPatientStatus().equals("RUSAK")) {
                        Toast.makeText(mContext, "STATUS RUANGAN RUSAK", Toast.LENGTH_LONG).show();
                    } else {
                        TAG_TRF_DST = responsePaymentMethod.getOfficeName();
                        ((Activity) mContext).finish();
                    }

                }
            });


        }





    }

    @Override
    public int getItemCount() {
        return AllPaymentItemList.size();
    }

    public class AdapterHolder extends RecyclerView.ViewHolder{



        TextView tvDetail;
        TextView bedRoom;
        ImageView imageRoom ;
        LinearLayout bgImage ;
        RelativeLayout btnItemRow;
        TextView tvDStatus;
        AlertDialog.Builder dialog;
        CardView cv_row ;
        TextView tvRoom;


        public AdapterHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            bgImage = itemView.findViewById(R.id.bgImage);
            bedRoom = itemView.findViewById(R.id.bedRoom);
            tvDetail = itemView.findViewById(R.id.tvDetail);
            imageRoom = itemView.findViewById(R.id.ivTextDrawable);
            btnItemRow = itemView.findViewById(R.id.btn_itemRow);
            tvRoom = itemView.findViewById(R.id.roomID);

            dialog = new AlertDialog.Builder(mContext);
            cv_row = itemView.findViewById(R.id.card_view);

            tvDStatus = itemView.findViewById(R.id.tvStatus);

        }
    }


    public int getRandomColorCode(){

        Random random = new Random();

        return Color.argb(255, random.nextInt(256), random.nextInt(256),     random.nextInt(256));

    }



}
