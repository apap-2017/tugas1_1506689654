package com.example.tpapap1.service;

import com.example.tpapap1.dao.KeluargaMapper;
import com.example.tpapap1.dao.PendudukMapper;
import com.example.tpapap1.model.KeluargaModel;
import com.example.tpapap1.model.KelurahanModel;
import com.example.tpapap1.model.PendudukModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class KeluargaServiceDatabase implements KeluargaService {
    @Autowired
    private KeluargaMapper keluargaMapper;

    public KeluargaModel selectKeluarga(String nkk) {
        log.info("select keluarga with nkk {}", nkk);
        return keluargaMapper.selectKeluarga(nkk);
    }

    public void addKeluarga(KeluargaModel keluarga, String id_kelurahan){
        keluarga.setNomor_kk(generateNKK(keluarga,id_kelurahan));
        keluargaMapper.addKeluarga(keluarga, id_kelurahan);
    }

    @Override
    public String selectIdKelurahan(String nama_kelurahan) {
        return keluargaMapper.selectIdKelurahan(nama_kelurahan);

    }

    public String digitDT(KeluargaModel keluarga, String id_kelurahan){
        log.info("Select kelurahan with id_kelurahan {}", id_kelurahan);
        KelurahanModel kelurahan = keluargaMapper.selectKelurahan(id_kelurahan);
        log.info("kelurahan", id_kelurahan);

        String digit1 = kelurahan.getKecamatan().getKode_kecamatan().substring(0,6);
        log.info("digit1",digit1);
        DateFormat dateFormat = new SimpleDateFormat("ddMMyy");
        Date date = new Date();
        String date2 = dateFormat.format(date);
        String digit = digit1 + date2 ;
        return digit;
    }

    public String generateNKK(KeluargaModel keluarga, String id_kelurahan){
        String digit = digitDT(keluarga,id_kelurahan) + '%';
        List<String> nkks = keluargaMapper.selectNKKs(digit);
        digit = digit.substring(0,digit.length()-1);
        if (nkks.size() == 0){
            digit += "0001";
        }else{
            Long nkkMAX= (long) 0;
            for (int i=0;i<nkks.size();i++){
                Long tmp = Long.parseLong(nkks.get(i));
                if(nkkMAX < tmp){
                    nkkMAX = tmp;
                }
            }
            digit = Long.toString(nkkMAX + 1);
        }
        log.info("nkk {}",digit);
        return digit;

    }

    @Override
    public String findIdKelurahan(String nkk){
        return  keluargaMapper.findIdKelurahan(nkk);
    }

    public void updateKeluarga(String nkk, KeluargaModel keluarga, String id_kelurahan){
        String newDigitDT = digitDT(keluarga, id_kelurahan);
        log.info("newDigitDT {}",newDigitDT);
        String oldDigitDT = nkk.substring(0,12);
        if(!newDigitDT.equals(oldDigitDT)){
            keluarga.setNomor_kk(generateNKK(keluarga,id_kelurahan));

        }
        keluargaMapper.updateKeluarga(nkk,keluarga,id_kelurahan);
    }

    @Override
    public void updateIsBerlaku(String id_keluarga){
        String nkk = keluargaMapper.selectNKK(id_keluarga);
        KeluargaModel keluarga = keluargaMapper.selectKeluarga(nkk);
        List<PendudukModel> penduduks = keluarga.getPenduduks();
        int count = 0;
        for (int i=0;i<penduduks.size();i++){
            if(penduduks.get(i).getIs_wafat() == 1){
                count++;
            }
        }
        if(count == penduduks.size()){
            keluargaMapper.updateIsBerlaku(nkk);
        }
    }

}

