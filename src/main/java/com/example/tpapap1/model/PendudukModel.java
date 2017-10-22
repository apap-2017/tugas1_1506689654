package com.example.tpapap1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
@AllArgsConstructor
@Data
public class PendudukModel {
    private String nik;
    private String nama;
    private String tempat_lahir;
    private Date tanggal_lahir;
    private int jenis_kelamin;
    private String agama;
    private KeluargaModel keluarga;
    private String pekerjaan;
    private String golongan_darah;
    private String status_perkawinan;
    private String status_dalam_keluarga;
    private int is_wni;
    private int is_wafat;

    public PendudukModel() {
        this.is_wafat = 0;
        this.is_wni = 1;

    }
}
