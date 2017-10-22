package com.example.tpapap1.model;

import lombok.Data;

import java.util.List;

@Data
public class KeluargaModel {
    private String nomor_kk;
    private String alamat;
    private String rt;
    private String rw;
    private KelurahanModel kelurahan;
    private int is_tidak_berlaku;
    private List<PendudukModel> penduduks;

    public KeluargaModel() {
        this.is_tidak_berlaku= 0 ;
    }
}
