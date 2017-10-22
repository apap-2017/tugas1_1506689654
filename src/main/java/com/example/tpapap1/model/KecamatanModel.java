package com.example.tpapap1.model;

import lombok.Data;

@Data
public class KecamatanModel {
    private String kode_kecamatan;
    private KotaModel kota;
    private String nama_kecamatan;

}
