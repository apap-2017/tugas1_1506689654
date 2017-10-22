package com.example.tpapap1.service;

import com.example.tpapap1.model.KecamatanModel;
import com.example.tpapap1.model.KelurahanModel;
import com.example.tpapap1.model.KotaModel;

import java.util.List;


public interface LokasiService {

    List<KotaModel> selectKotas();

    List<KecamatanModel> selectKecamatans(String id_kota);

    List<KelurahanModel> selectKelurahans(String id_kecamatan);

    String selectNamaKota(String id_kota);

    String selectNamaKecamatan(String id_kecamatan);
    String selectNamaKelurahan(String id_kelurahan);
}
