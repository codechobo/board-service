package com.example.boardservice.domain.type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Address {

    @Column(name = "road_addr")
    private String roadAddr; // 전체 도로명주소

    @Column(name = "jibun_addr")
    private String jibunAddr; // Y	지번주소

    @Column(name = "zip_no")
    private String zipNo; // Y	우편번호

    @Column(name = "si_num")
    private String siNm; // Y	시도명

    @Column(name = "sgg_nm")
    private String sggNm; // Y	시군구명

    @Column(name = "emd_nm")
    private String emdNm; // Y	읍면동명

    @Column(name = "li_nm")
    private String liNm; // N	법정리명

    @Column(name = "rm")
    private String rn; // Y	도로명

}
