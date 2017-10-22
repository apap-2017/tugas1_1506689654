package com.example.tpapap1.dao;

import com.example.tpapap1.model.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface PendudukMapper {
    @Select("select * from penduduk where nik = #{nik}")
    @Results(value = {
            @Result(property = "nik", column = "nik"),
            @Result(property = "nama", column = "nama"),
            @Result(property = "tempat_lahir", column = "tempat_lahir"),
            @Result(property = "tanggal_lahir", column = "tanggal_lahir"),
            @Result(property = "golongan_darah", column = "golongan_darah"),
            @Result(property = "agama", column = "agama"),
            @Result(property = "status_perkawinan", column = "status_perkawinan"),
            @Result(property = "pekerjaan", column = "pekerjaan"),
            @Result(property = "is_wni", column = "is_wni"),
            @Result(property = "is_wafat", column = "is_wafat"),
            @Result(property = "keluarga", column = "id_keluarga",
                    javaType = KeluargaModel.class,
                    one = @One(select = "selectKeluarga"))

    })
    PendudukModel selectPenduduk (@Param("nik") String nik);

    @Select("select * from keluarga where id = #{id_keluarga}")
    @Results(value= {
            @Result(property = "alamat", column ="alamat"),
            @Result(property = "rt", column ="rt"),
            @Result(property = "rw", column ="rw"),
            @Result(property = "kelurahan", column = "id_kelurahan",
                    javaType = KelurahanModel.class,
                    one = @One(select = "selectKelurahan"))
    })

    KeluargaModel selectKeluarga (@Param("id_keluarga") String id_keluarga);

    @Select("select * from kelurahan where id = #{id_kelurahan}")
    @Results(value= {
            @Result(property = "nama_kelurahan", column ="nama_kelurahan"),
            @Result(property = "kecamatan", column = "id_kecamatan",
                    javaType = KecamatanModel.class,
                    one = @One(select = "selectKecamatan"))
    })

    KelurahanModel selectKelurahan (@Param("id_kelurahan") String id_kelurahan);

    @Select("select * from kecamatan where id = #{id_kecamatan}")
    @Results(value= {
            @Result(property = "nama_kecamatan", column ="nama_kecamatan"),
            @Result(property = "kode_kecamatan", column ="kode_kecamatan"),
            @Result(property = "kota", column = "id_kota",
                    javaType = KotaModel.class,
                    one = @One(select = "selectKota"))
    })

    KecamatanModel selectKecamatan (@Param("id_kecamatan") String id_kecamatan);

    @Select("select * from kota where id = #{id_kota}")
    @Results(value= {
            @Result(property = "nama_kecamatan", column ="nama_kecamatan")
    })

    KotaModel selectKota (@Param("id_kota") String id_kota);


    @Insert("insert into penduduk (nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, golongan_darah, agama, status_perkawinan, pekerjaan, is_wni, is_wafat, id_keluarga, status_dalam_keluarga) " +
            "values (#{penduduk.nik},#{penduduk.nama}, #{penduduk.tempat_lahir}, #{penduduk.tanggal_lahir}, #{penduduk.jenis_kelamin}, #{penduduk.golongan_darah}, #{penduduk.agama}, #{penduduk.status_perkawinan}, #{penduduk.pekerjaan}, #{penduduk.is_wni}, #{penduduk.is_wafat}, #{id_keluarga},#{penduduk.status_dalam_keluarga})")
    void addPenduduk(@Param("penduduk") PendudukModel penduduk, @Param("id_keluarga") String id_keluarga);

    @Select("select nik from penduduk where nik like #{digit}")
    List<String> selectNIKs(@Param("digit") String digit);


    @Update("update penduduk set nik = #{penduduk.nik}, nama = #{penduduk.nama}, tempat_lahir = #{penduduk.tempat_lahir}, tanggal_lahir = #{penduduk.tanggal_lahir}, jenis_kelamin = #{penduduk.jenis_kelamin}," +
            " is_wni = #{penduduk.is_wni}, id_keluarga = #{id_keluarga}, agama= #{penduduk.agama}, pekerjaan = #{penduduk.pekerjaan}, status_perkawinan = #{penduduk.status_perkawinan}, status_dalam_keluarga = #{penduduk.status_dalam_keluarga}, golongan_darah = #{penduduk.golongan_darah}, " +
            "is_wafat = #{penduduk.is_wafat} where nik = #{nik}")
    void updatePenduduk(@Param("nik") String nik, @Param("penduduk") PendudukModel penduduk, @Param("id_keluarga") String id_keluarga);

    @Select("Select id_keluarga from penduduk where nik = #{nik} ")
    String selectIdKeluarga(@Param("nik") String nik);

    @Update("update penduduk set is_wafat = 1 where nik = #{nik}")
    void pendudukWafat(@Param("nik") String nik);

    @Select("select p.nik, p.nama, p.jenis_kelamin from penduduk p join keluarga k on k.id = p.id_keluarga join kelurahan kl on kl.id = k.id_kelurahan join kecamatan kc on kc.id = kl.id_kecamatan " +
            "join kota kt on kt.id = kc.id_kota where kt.id = #{id_kota} and kc.id = #{id_kecamatan} and kl.id = #{id_kelurahan}")
    List<PendudukModel> selectPenduduksByLocation(@Param("id_kota") String id_kota, @Param("id_kecamatan") String id_kecamatan,
                                                  @Param("id_kelurahan") String id_kelurahan);
}
