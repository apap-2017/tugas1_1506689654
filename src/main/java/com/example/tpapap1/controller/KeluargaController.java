package com.example.tpapap1.controller;


import com.example.tpapap1.model.KeluargaModel;
import com.example.tpapap1.model.PendudukModel;
import com.example.tpapap1.service.KeluargaService;
import com.example.tpapap1.service.PendudukService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class KeluargaController {

    @Autowired
    KeluargaService keluargaDAO;
    @Autowired
    PendudukService pendudukDAO;

    @RequestMapping(method = RequestMethod.GET, value="/keluarga")
    public  String searchKeluarga  (
            @RequestParam(value = "nkk", required = true) String nkk,
            Model model)
    {
        KeluargaModel keluarga = keluargaDAO.selectKeluarga(nkk);
        if( keluarga != null) {
            model.addAttribute("keluarga", keluarga);
            return "view-keluarga";
        }else{
            model.addAttribute("nkk", nkk);
            return "keluarga-not-found";
        }
    }

    @RequestMapping(method = RequestMethod.GET,value = "/keluarga/tambah")
    public String addKeluarga(Model model){
        KeluargaModel keluarga = new KeluargaModel();
        model.addAttribute("keluarga",keluarga);
        return "form-keluarga";
    }

    @RequestMapping(method = RequestMethod.POST, value="/keluarga/tambah")
    public String addSubmit (
            @RequestParam (value = "nama_kelurahan", required = false) String nama_kelurahan,
            Model model,KeluargaModel keluarga) throws Exception

    {
        keluargaDAO.addKeluarga(keluarga,keluargaDAO.selectIdKelurahan(nama_kelurahan));
        model.addAttribute("keluarga",keluarga);
        return "keluarga-success-add";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/keluarga/ubah/{nkk}")
    public String updatePenduduk(@PathVariable (value = "nkk") String nkk, Model model){
        KeluargaModel keluarga = keluargaDAO.selectKeluarga(nkk);
        if(keluarga != null){

            String nama_kelurahan = keluarga.getKelurahan().getNama_kelurahan();
            String nama_kecamatan = keluarga.getKelurahan().getKecamatan().getNama_kecamatan();
            String nama_kota = keluarga.getKelurahan().getKecamatan().getKota().getNama_kota();
            model.addAttribute("nama_kelurahan",nama_kelurahan);
            model.addAttribute("nama_kecamatan",nama_kecamatan);
            model.addAttribute("nama_kota",nama_kota);
            model.addAttribute("keluarga",keluarga);
            return "form-keluarga-update";
        }
        model.addAttribute("nkk",nkk);
        return "not-found";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/keluarga/ubah/{nkk}")
    public String updateSubmit( KeluargaModel keluarga,
            @PathVariable (value = "nkk") String nkk,@RequestParam(value = "nama_kelurahan") String nama_kelurahan
    )throws Exception {

        String id_kelurahan = keluargaDAO.selectIdKelurahan(nama_kelurahan);
        ArrayList<PendudukModel> penduduks = new ArrayList<PendudukModel>(keluargaDAO.selectKeluarga(nkk).getPenduduks());
        keluargaDAO.updateKeluarga(nkk, keluarga,id_kelurahan);
        if (penduduks.size()>0) {
            log.info("nik {}" , penduduks.get(0).getNik());
            String id_keluarga = pendudukDAO.selectIdKeluarga(penduduks.get(0).getNik());
            for (PendudukModel penduduk : penduduks
                    ) {
                pendudukDAO.updatePenduduk(penduduk.getNik(), penduduk, id_keluarga);

            }
        }
        return "keluarga-success-update";
    }

}
