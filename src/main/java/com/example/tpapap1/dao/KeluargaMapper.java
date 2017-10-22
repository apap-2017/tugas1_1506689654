package com.example.tpapap1.dao;

import com.example.tpapap1.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface KeluargaMapper {
    @Select("Select * from keluarga where nomor_kk = #{nkk}")
    @Results( value = {
            @Result(property = "nomor_kk", column = "nomor_kk" ),
            @Result(property = "alamat", column = "alamat" ),
            @Result(property = "rt", column = "rt" ),
            @Result(property = "rw", column = "rw" ),
            @Result(property = "kelurahan", column = "id_kelurahan",
                    javaType = KelurahanModel.class,
                    one = @One(select = "selectKelurahan")),
            @Result(property = "penduduks",column = "nomor_kk",
                    javaType = List.class, many = @Many(select = "selectPenduduks"))
    })
    KeluargaModel selectKeluarga (@Param("nkk") String nkk);

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

    @Select("Select * from penduduk p join keluarga k on p.id_keluarga = k.id where k.nomor_kk = #{nkk} ")
    @Results(value = {
            @Result(property = "nik", column = "nik"),
            @Result(property = "nama", column = "nama"),
            @Result(property = "tempat_lahir", column = "tempat_lahir"),
            @Result(property = "tanggal_lahir", column = "tanggal_lahir"),
            @Result(property = "jenis_kelamin", column = "jenis_kelamin"),
            @Result(property = "golongan_darah", column = "golongan_darah"),
            @Result(property = "agama", column = "agama"),
            @Result(property = "status_perkawinan", column = "status_perkawinan"),
            @Result(property = "pekerjaan", column = "pekerjaan"),
            @Result(property = "status_dalam_keluarga", column = "status_dalam_keluarga"),
            @Result(property = "is_wni", column = "is_wni")
    })
    List<PendudukModel> selectPenduduks (@Param("nkk") String nkk);

    @Insert("insert into keluarga (nomor_kk,alamat, rt, rw, id_kelurahan) " +
            "values (#{keluarga.nomor_kk},#{keluarga.alamat},#{keluarga.rt},#{keluarga.rw},#{id_kelurahan})")
    void addKeluarga (@Param("keluarga") KeluargaModel keluarga, @Param("id_kelurahan") String id_kelurahan);

    @Select("select id from kelurahan where nama_kelurahan = #{nama_kelurahan}")
    String selectIdKelurahan(@Param ("nama_kelurahan") String nama_kelurahan);

    @Select("select nomor_kk from keluarga where nomor_kk like #{digit}")
    List<String> selectNKKs(@Param("digit") String digit);

    @Update("update keluarga set nomor_kk = #{keluarga.nomor_kk}, alamat = #{keluarga.alamat}, rt = #{keluarga.rt}, " +
            "rw = #{keluarga.rw}, id_kelurahan= #{id_kelurahan} where nomor_kk = #{nkk}")
    void updateKeluarga (@Param("nkk") String nkk, @Param("keluarga") KeluargaModel keluarga, @Param("id_kelurahan") String id_kelurahan);

    @Select("select id_kelurahan from keluarga where nomor_kk = #{nkk}")
    String findIdKelurahan(@Param("nkk") String nkk);

    @Update("update keluarga set is_tidak_berlaku = 1 where nomor_kk = #{nkk}")
    void updateIsBerlaku(@Param("nkk") String nkk);

    @Select("select nomor_kk from keluarga where id= #{id_keluarga}")
    String selectNKK(@Param("id_keluarga")String id_keluarga);
}
