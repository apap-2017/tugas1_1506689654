package com.example.tpapap1.service;


import com.example.tpapap1.model.PendudukModel;

import java.util.List;

public interface PendudukService {
    PendudukModel selectPenduduk (String nik);

    void addPenduduk (PendudukModel penduduk, String id_keluarga);

    void updatePenduduk (String nik, PendudukModel penduduk, String id_keluarga);

    String selectIdKeluarga(String nik);

    void pendudukWafat(String nik);

    List<PendudukModel> selectPenduduksByLocation(String id_kota, String id_kecamatan, String id_kelurahan);
}
