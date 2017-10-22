package com.example.tpapap1.service;

import com.example.tpapap1.dao.PendudukMapper;
import com.example.tpapap1.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
@Service
public class PendudukServiceDatabase implements PendudukService{
    @Autowired
    private PendudukMapper pendudukMapper;

    @Override
    public PendudukModel selectPenduduk(String nik) {
        log.info ("select penduduk with nik {}", nik);
        return pendudukMapper.selectPenduduk(nik);
    }

    @Override
    public void addPenduduk(PendudukModel penduduk, String id_keluarga)
    {
        penduduk.setNik(generateNIK(penduduk, id_keluarga));
        pendudukMapper.addPenduduk(penduduk, id_keluarga);
    }

    public String digitDT(PendudukModel penduduk, String id_keluarga){
        log.info("Select keluarga with id_keluarga {}", id_keluarga);
        KeluargaModel keluarga = pendudukMapper.selectKeluarga(id_keluarga);
        String digit1 = keluarga.getKelurahan().getKecamatan().getKode_kecamatan().substring(0,6);
        DateFormat df = new SimpleDateFormat("ddMMyy");
        String tggllahir = df.format(penduduk.getTanggal_lahir());
        if(penduduk.getJenis_kelamin() == 1){
            int tgl =  Integer.parseInt(tggllahir.substring(0,2));
            int res = tgl + 40;
            String newdigit = Integer.toString(res);
            tggllahir = newdigit + tggllahir.substring(2) ;
        }
        String digit = digit1 + tggllahir ;
        return digit;
    }

    public String generateNIK(PendudukModel penduduk, String id_keluarga) {
        String digit= digitDT(penduduk,id_keluarga) + '%';
        List<String> niks = pendudukMapper.selectNIKs(digit);
        digit = digit.substring(0,digit.length()-1);
        if(niks.size() == 0){
            digit += "0001";
        }else{
            Long nikMAX= (long) 0;
            for (int i=0;i<niks.size();i++){
                Long tmp = Long.parseLong(niks.get(i));
                if(nikMAX < tmp){
                    nikMAX = tmp;
                }
            }
            digit = Long.toString(nikMAX + 1);
        }
        log.info("nik {}",digit);
        return digit;

    }

    @Override
    public String selectIdKeluarga(String nik){
        return pendudukMapper.selectIdKeluarga(nik);
    }


    @Override
    public void updatePenduduk(String nik, PendudukModel penduduk, String id_keluarga){

        String newDigitDT= digitDT(penduduk,id_keluarga);
        String oldDigitDT = nik.substring(0,12);
        if(!newDigitDT.equals(oldDigitDT)){
            penduduk.setNik(generateNIK(penduduk,id_keluarga));
        }
        pendudukMapper.updatePenduduk(nik,penduduk,id_keluarga);
    }

    @Override
    public void pendudukWafat(String nik){

        log.info("niknya {}", nik);
        pendudukMapper.pendudukWafat(nik);
    }

    @Override
    public List<PendudukModel> selectPenduduksByLocation(String id_kota, String id_kecamatan, String id_kelurahan){
        return pendudukMapper.selectPenduduksByLocation(id_kota,id_kecamatan,id_kelurahan);
    }


}
