package com.example.tpapap1.service;

import com.example.tpapap1.dao.LokasiMapper;
import com.example.tpapap1.model.KecamatanModel;
import com.example.tpapap1.model.KelurahanModel;
import com.example.tpapap1.model.KotaModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LokasiServiceDatabase implements LokasiService {
    @Autowired
    private LokasiMapper lokasiMapper;

    @Override
    public List<KotaModel> selectKotas(){

        log.info("select all kota");
        return lokasiMapper.selectKotas();

    }

    @Override
    public List<KecamatanModel> selectKecamatans(String id_kota){
        log.info("select all kecamatan");
        return lokasiMapper.selectKecamatans(id_kota);
    }

    @Override
    public List<KelurahanModel> selectKelurahans(String id_kecamatan){
        return    lokasiMapper.selectKelurahans(id_kecamatan);
    }

    @Override
    public String selectNamaKota(String id_kota){
        return lokasiMapper.selectNamaKota(id_kota);
    }


    @Override
    public String selectNamaKecamatan(String id_kecamatan){
        return lokasiMapper.selectNamaKecamatan(id_kecamatan);
    }


    @Override
    public String selectNamaKelurahan(String id_kelurahan){
        return lokasiMapper.selectNamaKelurahan(id_kelurahan);
    }

}
