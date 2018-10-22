package com.ktds.devpro.sample;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

import groovy.transform.ToString;
import lombok.Data;

@Data
@ToString
@Alias("sampleBoardVo")
public class SampleBoardVo {
    
    private int idx;
    private int parentIdx;
    private int hitCnt;
    private String contents;
    private String delGb;
    private Timestamp creaDtm;
    private String creaId;


	
}
