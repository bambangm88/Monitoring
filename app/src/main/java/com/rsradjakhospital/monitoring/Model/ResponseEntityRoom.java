package com.rsradjakhospital.monitoring.Model;

import com.google.gson.annotations.SerializedName;

public class ResponseEntityRoom {


    @SerializedName("OfficeName")
    private String OfficeName;

    @SerializedName("OfficeDescription")
    private String OfficeDescription;

    @SerializedName("TypeOfBed")
    private String TypeOfBed;

    @SerializedName("Rate")
    private String Rate;

    @SerializedName("kelas")
    private String kelas;

    @SerializedName("Floor")
    private String Floor;

    @SerializedName("WHCode")
    private String WHCode;

    @SerializedName("Status")
    private String Status;

    @SerializedName("Category")
    private String Category;

    @SerializedName("Note")
    private String Note;

    @SerializedName("PatientName")
    private String NewNo;

    @SerializedName("UseStatus")
    private String UseStatus;

    @SerializedName("NextArrive")
    private String NextArrive;


    @SerializedName("NextDepart")
    private String NextDepart;

    @SerializedName("Active")
    private String Active;

    @SerializedName("TempNote")
    private String TempNote;

    @SerializedName("PatientStatus")
    private String PatientStatus;

    @SerializedName("RoomStatus")
    private String RoomStatus;

    @SerializedName("RoomBPJS")
    private String RoomBPJS;

    @SerializedName("NamaRuangan")
    private String NamaRuangan;

    @SerializedName("nama")
    private String nama;


    @SerializedName("Description")
    private String Description;

    @SerializedName("caseID")
    private String caseID;

    @SerializedName("RoomHeader")
    private String RoomHeader;

    @SerializedName("bed")
    private String bed;

    @SerializedName("namaDokter")
    private String namaDokter;

    @SerializedName("Kelamin")
    private String Kelamin;

    @SerializedName("KodePasien")
    private String KodePasien;

    @SerializedName("Catatan")
    private String Catatan;

    @SerializedName("CasesPatientStatus")
    private String CasesPatientStatus;

    @SerializedName("CloseDate")
    private String CloseDate;

    @SerializedName("CloseTime")
    private String CloseTime;



    public String getCasesPatientStatus() {
        return CasesPatientStatus;
    }

    public void setCasesPatientStatus(String casesPatientStatus) {
        CasesPatientStatus = casesPatientStatus;
    }

    public String getCloseDate() {
        return CloseDate;
    }

    public void setCloseDate(String closeDate) {
        CloseDate = closeDate;
    }

    public String getCloseTime() {
        return CloseTime;
    }

    public void setCloseTime(String closeTime) {
        CloseTime = closeTime;
    }




    public String getCatatan() {
        return Catatan;
    }

    public void setCatatan(String catatan) {
        Catatan = catatan;
    }

    public String getKodePasien() {
        return KodePasien;
    }

    public void setKodePasien(String kodePasien) {
        KodePasien = kodePasien;
    }


    public String getKelamin() {
        return Kelamin;
    }

    public void setKelamin(String kelamin) {
        Kelamin = kelamin;
    }










    @SerializedName("NomorKunjungan")
    private String NomorKunjungan;

    @SerializedName("TanggalMasuk")
    private String TanggalMasuk;

    @SerializedName("NamaPenjamin")
    private String NamaPenjamin;

    @SerializedName("presenting_problem")
    private String presenting_problem;

    @SerializedName("IDDokter")
    private String IDDokter;

    @SerializedName("lamaInap")
    private String lamaInap;

    @SerializedName("tglMasuk")
    private String tglMasuk;

    @SerializedName("timeMasuk")
    private String timeMasuk;

    public String getTimeMasuk() {
        return timeMasuk;
    }

    public void setTimeMasuk(String timeMasuk) {
        this.timeMasuk = timeMasuk;
    }



    public String getLamaInap() {
        return lamaInap;
    }

    public void setLamaInap(String lamaInap) {
        this.lamaInap = lamaInap;
    }

    public String getTglMasuk() {
        return tglMasuk;
    }

    public void setTglMasuk(String tglMasuk) {
        this.tglMasuk = tglMasuk;
    }





    public String getIDDokter() {
        return IDDokter;
    }

    public void setIDDokter(String IDDokter) {
        this.IDDokter = IDDokter;
    }




    public String getNamaDokter() {
        return namaDokter;
    }

    public void setNamaDokter(String namaDokter) {
        this.namaDokter = namaDokter;
    }


    public String getNomorKunjungan() {
        return NomorKunjungan;
    }

    public void setNomorKunjungan(String nomorKunjungan) {
        NomorKunjungan = nomorKunjungan;
    }

    public String getTanggalMasuk() {
        return TanggalMasuk;
    }

    public void setTanggalMasuk(String tanggalMasuk) {
        TanggalMasuk = tanggalMasuk;
    }

    public String getNamaPenjamin() {
        return NamaPenjamin;
    }

    public void setNamaPenjamin(String namaPenjamin) {
        NamaPenjamin = namaPenjamin;
    }

    public String getPresenting_problem() {
        return presenting_problem;
    }

    public void setPresenting_problem(String presenting_problem) {
        this.presenting_problem = presenting_problem;
    }



    public String getRoomHeader() {
        return RoomHeader;
    }

    public void setRoomHeader(String roomHeader) {
        RoomHeader = roomHeader;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }



    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCaseID() {
        return caseID;
    }

    public void setCaseID(String caseID) {
        this.caseID = caseID;
    }




    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getOfficeName() {
        return OfficeName;
    }

    public void setOfficeName(String officeName) {
        OfficeName = officeName;
    }

    public String getOfficeDescription() {
        return OfficeDescription;
    }

    public void setOfficeDescription(String officeDescription) {
        OfficeDescription = officeDescription;
    }

    public String getTypeOfBed() {
        return TypeOfBed;
    }

    public void setTypeOfBed(String typeOfBed) {
        TypeOfBed = typeOfBed;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getFloor() {
        return Floor;
    }

    public void setFloor(String floor) {
        Floor = floor;
    }

    public String getWHCode() {
        return WHCode;
    }

    public void setWHCode(String WHCode) {
        this.WHCode = WHCode;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getNewNo() {
        return NewNo;
    }

    public void setNewNo(String newNo) {
        NewNo = newNo;
    }

    public String getUseStatus() {
        return UseStatus;
    }

    public void setUseStatus(String useStatus) {
        UseStatus = useStatus;
    }

    public String getNextArrive() {
        return NextArrive;
    }

    public void setNextArrive(String nextArrive) {
        NextArrive = nextArrive;
    }

    public String getNextDepart() {
        return NextDepart;
    }

    public void setNextDepart(String nextDepart) {
        NextDepart = nextDepart;
    }

    public String getActive() {
        return Active;
    }

    public void setActive(String active) {
        Active = active;
    }

    public String getTempNote() {
        return TempNote;
    }

    public void setTempNote(String tempNote) {
        TempNote = tempNote;
    }

    public String getPatientStatus() {
        return PatientStatus;
    }

    public void setPatientStatus(String patientStatus) {
        PatientStatus = patientStatus;
    }

    public String getRoomStatus() {
        return RoomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        RoomStatus = roomStatus;
    }

    public String getRoomBPJS() {
        return RoomBPJS;
    }

    public void setRoomBPJS(String roomBPJS) {
        RoomBPJS = roomBPJS;
    }

    public String getNamaRuangan() {
        return NamaRuangan;
    }

    public void setNamaRuangan(String namaRuangan) {
        NamaRuangan = namaRuangan;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        kelas = kelas;
    }









}
