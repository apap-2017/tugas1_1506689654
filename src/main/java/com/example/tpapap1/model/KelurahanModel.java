package com.example.tpapap1.model;

import lombok.Data;

@Data
public class KelurahanModel {
    private String kode_kelurahan;
    private KecamatanModel kecamatan;
    private String nama_kelurahan;
    private String kode_pos;
}
