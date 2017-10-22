package com.example.tpapap1.dao;

import com.example.tpapap1.model.KecamatanModel;
import com.example.tpapap1.model.KelurahanModel;
import com.example.tpapap1.model.KotaModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LokasiMapper {

    @Select("select * from kelurahan where id_kecamatan = #{id_kecamatan}")
    @Results(value = {@Result(property = "kode_kelurahan", column = "id")})
    List<KelurahanModel> selectKelurahans(@Param("id_kecamatan")String id_kecamatan);

    @Select("select * from kecamatan where id_kota  = #{id_kota}")
    @Results(value = {@Result(property = "kode_kecamatan", column = "id")})
    List<KecamatanModel> selectKecamatans(@Param("id_kota")String id_kota);

    @Select("select * from kota")
    @Results(value = {
            @Result(property = "kode_kota", column = "id")})
    List<KotaModel> selectKotas();

    @Select("Select nama_kota from kota where id = #{id_kota}")
    String selectNamaKota( String id_kota);


    @Select("Select nama_kecamatan from kecamatan where id = #{id_kecamatan}")
    String selectNamaKecamatan( String id_kecamatan);

    @Select("Select nama_kelurahan from kelurahan where id = #{id_kelurahan}")
    String selectNamaKelurahan( String id_kelurahan);


}
