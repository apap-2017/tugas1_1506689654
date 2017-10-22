package com.example.tpapap1.service;

import com.example.tpapap1.model.KeluargaModel;
import com.example.tpapap1.model.KelurahanModel;

public interface KeluargaService {
    KeluargaModel selectKeluarga (String nkk);

    void addKeluarga(KeluargaModel keluarga, String id_kelurahan);

    void updateKeluarga(String nkk, KeluargaModel keluarga, String id_kelurahan);

    String selectIdKelurahan(String nama_kelurahan);

    String findIdKelurahan(String nkk);

    void updateIsBerlaku(String id_keluarga);
}
