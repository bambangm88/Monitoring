package com.rsradjakhospital.monitoring.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
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


import com.rsradjakhospital.monitoring.DetailRoom;
import com.rsradjakhospital.monitoring.Helper.Helper;
import com.rsradjakhospital.monitoring.MainActivity;
import com.rsradjakhospital.monitoring.Model.ResponseEntityRoom;
import com.rsradjakhospital.monitoring.R;
import com.rsradjakhospital.monitoring.UbahStatusBed;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;


import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;
import static com.rsradjakhospital.monitoring.DetailRoom.TAG_CASES_ID;
import static com.rsradjakhospital.monitoring.DetailRoom.TAG_DIAGNOSA_AWAL;
import static com.rsradjakhospital.monitoring.DetailRoom.TAG_DOCTOR_ID;
import static com.rsradjakhospital.monitoring.DetailRoom.TAG_DPJP;

import static com.rsradjakhospital.monitoring.DetailRoom.TAG_KUNJUNGAN;
import static com.rsradjakhospital.monitoring.DetailRoom.TAG_LAMA_INAP;
import static com.rsradjakhospital.monitoring.DetailRoom.TAG_NAMA_BED;
import static com.rsradjakhospital.monitoring.DetailRoom.TAG_NAMA_PASIEN;
import static com.rsradjakhospital.monitoring.DetailRoom.TAG_NOMR;
import static com.rsradjakhospital.monitoring.DetailRoom.TAG_PENJAMIN;
import static com.rsradjakhospital.monitoring.DetailRoom.TAG_ROOM;
import static com.rsradjakhospital.monitoring.DetailRoom.TAG_ROOM_HEADER;
import static com.rsradjakhospital.monitoring.DetailRoom.TAG_STATUS_KELAS;
import static com.rsradjakhospital.monitoring.DetailRoom.TAG_STATUS_RUANGAN;
import static com.rsradjakhospital.monitoring.DetailRoom.TAG_TANGGAL_MASUK;
import static com.rsradjakhospital.monitoring.UbahStatusBed.TAG_OFFICE_NAME;
import static com.rsradjakhospital.monitoring.UbahStatusBed.TAG_ROOM_STATUS;


public class RuanganAdapter extends RecyclerView.Adapter<RuanganAdapter.AdapterHolder>{

    List<ResponseEntityRoom> AllPaymentItemList;
    Context mContext;

    private static final int LAYOUT_HEADER= 0;
    private static final int LAYOUT_CHILD= 1;




    public RuanganAdapter(Context context, List<ResponseEntityRoom> paymentList){
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
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        }

        return new AdapterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterHolder holder, int position) {
        final ResponseEntityRoom responsePaymentMethod = AllPaymentItemList.get(position);


        if(holder.getItemViewType()== LAYOUT_HEADER)
        {
            holder.tvRoom.setText(responsePaymentMethod.getRoomHeader());
            holder.bedKelas.setText(responsePaymentMethod.getKelas());
        }
        else {


        if(responsePaymentMethod.getOfficeName() == null){

        }


        String category = responsePaymentMethod.getRoomHeader();
        String bed = responsePaymentMethod.getBed();
        String nama = responsePaymentMethod.getNama();

            String tempNote = responsePaymentMethod.getTempNote();
            String[] parts = tempNote.split("/");




        holder.bedRoom.setText("BED "+bed);


        holder.tvDetail.setText(nama);

        holder.tvDStatus.setText(responsePaymentMethod.getPatientStatus());




        holder.imageRoom.setImageResource(R.drawable.room);


        if(responsePaymentMethod.getKelamin().equals("1")){
            holder.bgImage.setBackgroundResource(R.color.colorP);
            holder.bgImage2.setBackgroundResource(R.color.colorP);
        }else{

            holder.bgImage.setBackgroundResource(R.color.colorL);
            holder.bgImage2.setBackgroundResource(R.color.colorL);
        }


        if(responsePaymentMethod.getPatientStatus() == null){
            responsePaymentMethod.setPatientStatus("");
        }

        if(responsePaymentMethod.getPatientStatus().equals("KOSONG")){
            //hitung jumlah kosong


            holder.tvDetail.setText("");

            holder.tvCloseDate.setText("");
            holder.tvCloseTime.setText("");
            holder.tvPatient.setText("");

            holder.cv_row.setBackgroundResource(R.color.colorKosong);
            holder.bgImage.setBackgroundResource(R.color.colorKosong);
            holder.bgImage2.setBackgroundResource(R.color.colorKosong);


        } else if(responsePaymentMethod.getPatientStatus().equals("BOOKING")){
            //hitung jumlah kosong


            int lengthParam = parts.length;

            String partName = "" ; //
            String partTanggal = "" ; //
            String partTime = "" ; //
            String partTelp = "" ;; //

            if (lengthParam == 1 ) {

                partName = parts[0]; //

            }

            if (lengthParam == 2 ) {

                partName = parts[0]; //
                partTanggal = parts[1]; //

            }

            if (lengthParam == 3 ) {

                partName = parts[0]; //
                partTanggal = parts[1]; //
                partTime = parts[2]; //

            }

            if (lengthParam == 4 ) {

                partName = parts[0]; //
                partTanggal = parts[1]; //
                partTime = parts[2]; //
                partTelp = parts[3]; //

            }

            holder.tvDetail.setText(partName);

            holder.tvCloseDate.setText(partTanggal);
            holder.tvCloseTime.setText(partTime);
            holder.tvPatient.setText(partTelp);

            holder.cv_row.setBackgroundResource(R.color.colorBooking);
            holder.bgImage.setBackgroundResource(R.color.colorBooking);
            holder.bgImage2.setBackgroundResource(R.color.colorBooking);

        }else if(responsePaymentMethod.getPatientStatus().equals("RUSAK")){
            holder.tvCloseDate.setText("");
            holder.tvCloseTime.setText("");
            holder.tvPatient.setText("");
            holder.cv_row.setBackgroundResource(R.color.colorRusak);
            holder.tvDetail.setText("");
            holder.tvDStatus.setTextColor(Color.parseColor("#FFFFFF"));
            holder.bgImage.setBackgroundResource(R.color.colorRusak);
            holder.bgImage2.setBackgroundResource(R.color.colorRusak);
        }else if(responsePaymentMethod.getPatientStatus().equals("TITIPAN")){

            if(responsePaymentMethod.getCasesPatientStatus().contains("CLOSED")) {
                holder.tvCloseDate.setText(responsePaymentMethod.getCloseDate() + "  ");
                holder.tvCloseTime.setText(responsePaymentMethod.getCloseTime());
                holder.tvPatient.setText(responsePaymentMethod.getCasesPatientStatus());
                holder.cv_row.setBackgroundResource(R.color.CLOSED);


            }else {


                holder.tvCloseDate.setText("");
                holder.tvCloseTime.setText("");
                holder.tvPatient.setText("");
                holder.cv_row.setBackgroundResource(R.color.colorTitipan);
                holder.bgImage.setBackgroundResource(R.color.colorTitipan);
                holder.bgImage2.setBackgroundResource(R.color.colorTitipan);


            }
        }else if(responsePaymentMethod.getPatientStatus().equals("RENCANA PULANG")){


            if(responsePaymentMethod.getCasesPatientStatus().contains("CLOSED")) {
                holder.tvCloseDate.setText(responsePaymentMethod.getCloseDate() + "  ");
                holder.tvCloseTime.setText(responsePaymentMethod.getCloseTime());
                holder.tvPatient.setText(responsePaymentMethod.getCasesPatientStatus());
                holder.cv_row.setBackgroundResource(R.color.CLOSED);


            }else{

                String tempNoteDate = responsePaymentMethod.getTempNote();
                String DateNow = Helper.dateTimeNow();


                holder.tvCloseDate.setText("");
                holder.tvCloseTime.setText("");
                holder.tvPatient.setText("");
                holder.cv_row.setBackgroundResource(R.color.colorRencanaPulang);
                holder.bgImage.setBackgroundResource(R.color.colorRencanaPulang);
                holder.bgImage2.setBackgroundResource(R.color.colorRencanaPulang);

                holder.tglMasuk.setText("IN : "+responsePaymentMethod.getTglMasuk().replace("00:00:00","") +" "+responsePaymentMethod.getTimeMasuk()+ " / " + responsePaymentMethod.getLamaInap() +" day");
                holder.cvtglMasuk.setVisibility(View.VISIBLE);


            }


        }




        else {

            if(responsePaymentMethod.getCasesPatientStatus().contains("CLOSED")) {
                holder.tvCloseDate.setText(responsePaymentMethod.getCloseDate() + "  ");
                holder.tvCloseTime.setText(responsePaymentMethod.getCloseTime());
                holder.tvPatient.setText(responsePaymentMethod.getCasesPatientStatus());

                holder.cv_row.setBackgroundResource(R.color.CLOSED);
            }else{
                holder.tvCloseDate.setText("");
                holder.tvCloseTime.setText("");
                holder.tvPatient.setText("");
                holder.cv_row.setBackgroundResource(R.color.colorIsi);
                holder.tglMasuk.setText("IN : "+responsePaymentMethod.getTglMasuk().replace("00:00:00","") +" "+responsePaymentMethod.getTimeMasuk()+ " / " + responsePaymentMethod.getLamaInap() +" day");
                holder.cvtglMasuk.setVisibility(View.VISIBLE);
            }



        }

        holder.btnItemRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(responsePaymentMethod.getPatientStatus().equals("KOSONG") || responsePaymentMethod.getPatientStatus().equals("SELESAI PERBAIKAN") ||  responsePaymentMethod.getPatientStatus().equals("BOOKING") ||  responsePaymentMethod.getPatientStatus().equals("RUSAK") ||  responsePaymentMethod.getPatientStatus().equals("TITIPAN")){

                    TAG_OFFICE_NAME  = responsePaymentMethod.getOfficeName() ;
                    TAG_ROOM_STATUS  = responsePaymentMethod.getPatientStatus();

                    Intent intent = new Intent(mContext, UbahStatusBed.class);
                    mContext.startActivity(intent);

                }else {

                    Log.e(TAG, "onClick: "+responsePaymentMethod.getRoomHeader() );
                    TAG_ROOM_HEADER = responsePaymentMethod.getRoomHeader();
                    TAG_NAMA_BED  = responsePaymentMethod.getOfficeName() ;
                    TAG_NAMA_PASIEN  = responsePaymentMethod.getNama() ;
                    TAG_DOCTOR_ID = responsePaymentMethod.getIDDokter();
                    TAG_KUNJUNGAN = responsePaymentMethod.getNomorKunjungan();
                    TAG_STATUS_KELAS = responsePaymentMethod.getKelas();
                    TAG_STATUS_RUANGAN = responsePaymentMethod.getPatientStatus();
                    TAG_NOMR = responsePaymentMethod.getKodePasien();
                    TAG_ROOM = category+" BED "+bed ;
                    TAG_TANGGAL_MASUK = responsePaymentMethod.getTanggalMasuk()  ;
                    TAG_LAMA_INAP= responsePaymentMethod.getLamaInap() + " day" ;
                    TAG_PENJAMIN = responsePaymentMethod.getNamaPenjamin();
                    TAG_DIAGNOSA_AWAL = responsePaymentMethod.getPresenting_problem();
                    TAG_DPJP = responsePaymentMethod.getNamaDokter();
                    TAG_CASES_ID = responsePaymentMethod.getCaseID();



                    Intent intent = new Intent(mContext, DetailRoom.class);
                    mContext.startActivity(intent);

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
        TextView tvRoom;
        TextView tvDStatus;
        TextView bedRoom;
        TextView bedKelas;
        TextView tglMasuk;

        TextView tvPatient , tvCloseDate , tvCloseTime ;

        ImageView imageRoom ;
        LinearLayout bgImage ;
        LinearLayout bgImage2 ;
        LinearLayout cvtglMasuk ;
        RelativeLayout btnItemRow;
        CardView cv_row ;

        AlertDialog.Builder dialog;


        public AdapterHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            bgImage = itemView.findViewById(R.id.bgImage);
            bgImage2 = itemView.findViewById(R.id.bgImage2);
            bedRoom = itemView.findViewById(R.id.bedRoom);
            tvDetail = itemView.findViewById(R.id.tvDetail);
            imageRoom = itemView.findViewById(R.id.ivTextDrawable);
            btnItemRow = itemView.findViewById(R.id.btn_itemRow);
            cv_row = itemView.findViewById(R.id.card_view);
            tvRoom = itemView.findViewById(R.id.roomID);
            bedKelas = itemView.findViewById(R.id.tvKelas);

            tvCloseDate = itemView.findViewById(R.id.closeDate);
            cvtglMasuk = itemView.findViewById(R.id.cvtglMasuk);
            tvCloseTime = itemView.findViewById(R.id.closeTime);
            tvPatient = itemView.findViewById(R.id.casesPatientStatus);
            tglMasuk = itemView.findViewById(R.id.tglMasuk);

            dialog = new AlertDialog.Builder(mContext);

            tvDStatus = itemView.findViewById(R.id.tvStatus);

        }
    }


    public int getRandomColorCode(){

        Random random = new Random();

        return Color.argb(255, random.nextInt(256), random.nextInt(256),     random.nextInt(256));

    }



}
