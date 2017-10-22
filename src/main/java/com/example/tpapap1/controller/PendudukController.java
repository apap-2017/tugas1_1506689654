package com.example.tpapap1.controller;

import com.example.tpapap1.model.*;
import com.example.tpapap1.service.KeluargaService;
import com.example.tpapap1.service.LokasiService;
import com.example.tpapap1.service.PendudukService;
import com.sun.javafx.binding.StringFormatter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
public class PendudukController {
    @Autowired
    PendudukService pendudukDAO;

    @Autowired
    LokasiService lokasiDAO;


    @Autowired
    KeluargaService keluargaDAO;

    @RequestMapping("/")
    public  String index () { return "index";}

    @RequestMapping(method = RequestMethod.GET, value="/penduduk")
    public  String searchPenduduk  (
            @RequestParam(value = "nik", required = true) String nik,
            Model model)
    {
        PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
       if( penduduk != null) {
           model.addAttribute("penduduk", penduduk);
           return "view-penduduk";
       }else{
           model.addAttribute("nik", nik);
           return "penduduk-not-found";
       }
    }

    @RequestMapping(method = RequestMethod.GET,value = "/penduduk/tambah")
    public String addPenduduk(Model model){
        PendudukModel penduduk = new PendudukModel();
        model.addAttribute("penduduk",penduduk);
        return "form-penduduk";
    }

    @RequestMapping(method = RequestMethod.POST, value="/penduduk/tambah")
    public String addSubmit (
            @RequestParam (value = "id_keluarga", required = false) String id_keluarga,
            @RequestParam(value = "tgl_lahir", required = false) String tgl_lahir,
            Model model,PendudukModel penduduk) throws Exception

    {

        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(tgl_lahir);
        penduduk.setTanggal_lahir(date);
        pendudukDAO.addPenduduk (penduduk, id_keluarga);
        model.addAttribute("penduduk",penduduk);
        return "penduduk-success-add";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/penduduk/ubah/{nik}")
    public String updatePenduduk(@PathVariable (value = "nik") String nik, Model model) {
        PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
        if (penduduk != null) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String tgl_lahir = df.format(penduduk.getTanggal_lahir());
            model.addAttribute("tgl_lahir",tgl_lahir);

            String goldar = penduduk.getGolongan_darah();
            if(goldar.charAt(goldar.length()-1) == '−'){
                penduduk.setGolongan_darah(goldar.substring(0,goldar.length()-1)+"-");
            }

            String id_keluarga = pendudukDAO.selectIdKeluarga(nik);
            model.addAttribute("id_keluarga",id_keluarga);

            model.addAttribute("penduduk", penduduk);
            return "form-penduduk-update";
        }
        model.addAttribute("nik",nik);
        return "not-found";
    }

    @RequestMapping(value = "/penduduk/ubah/{nik}", method = RequestMethod.POST)
    public String updateSubmit (
            PendudukModel penduduk,
            @RequestParam(value = "tgl_lahir", required = false) String tgl_lahir,
            @PathVariable (value = "nik") String nik,
            @RequestParam (value = "id_keluarga", required = false) String id_keluarga
            ) throws Exception{
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(tgl_lahir);
        penduduk.setTanggal_lahir(date);
        pendudukDAO.updatePenduduk(nik, penduduk, id_keluarga);
        return "penduduk-success-update";
    }

    @RequestMapping (value = "/penduduk/mati", method = RequestMethod.POST)
    public String pendudukMati(@RequestParam(value = "nik") String nik, RedirectAttributes redirectAttributes){
        pendudukDAO.pendudukWafat(nik);
        keluargaDAO.updateIsBerlaku(pendudukDAO.selectIdKeluarga(nik));
        redirectAttributes.addFlashAttribute("wafat","true");
        return "redirect:/penduduk?nik="+ nik;
    }

    @RequestMapping (value = "/penduduk/cari", method = RequestMethod.GET)
    public String cariData(
            @RequestParam(value= "kt", required = false) String id_kota,
            @RequestParam(value= "kc", required = false) String id_kecamatan,
            @RequestParam(value= "kl",required = false) String id_kelurahan,
            Model model){

        List<KotaModel> kotas = lokasiDAO.selectKotas();
        model.addAttribute("flagkota","false");
        model.addAttribute("kotas",kotas);
        if(id_kota != null) {
            String nama_kota = lokasiDAO.selectNamaKota(id_kota);
            model.addAttribute("flagkota","true");
            List<KecamatanModel> kecamatans = lokasiDAO.selectKecamatans(id_kota);
            model.addAttribute("id_kota", id_kota);
            model.addAttribute("nama_kota",nama_kota);

            model.addAttribute("flagkecamatan","false");
            model.addAttribute("kecamatans",kecamatans);

            log.info("id_kecamatan {}", id_kecamatan);
            if(id_kecamatan != null){
                String nama_kecamatan = lokasiDAO.selectNamaKecamatan(id_kecamatan);
                model.addAttribute("flagkota","true");
                model.addAttribute("flagkecamatan","true");
                List<KelurahanModel> kelurahans = lokasiDAO.selectKelurahans(id_kecamatan);

                model.addAttribute("id_kota", id_kota);
                model.addAttribute("nama_kota",nama_kota);
                model.addAttribute("id_kecamatan", id_kecamatan);
                model.addAttribute("nama_kecamatan",nama_kecamatan);

                model.addAttribute("flagkelurahan","false");
                model.addAttribute("kelurahans",kelurahans);

                if(id_kelurahan != null){
                    String nama_kelurahan = lokasiDAO.selectNamaKelurahan(id_kelurahan);
                    model.addAttribute("nama_kelurahan",nama_kelurahan);
                    List<PendudukModel> penduduks = pendudukDAO.selectPenduduksByLocation(id_kota,id_kecamatan,id_kelurahan);
                    model.addAttribute("penduduks",penduduks);
                    return "view-penduduk-lokasi";
                }

            }

        }
        return "form-cari-data";
    }
}
