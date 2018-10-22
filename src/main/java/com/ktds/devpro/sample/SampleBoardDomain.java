package com.ktds.devpro.sample;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

import groovy.transform.ToString;
import lombok.Data;

@Data
@ToString
@Alias("sampleBoardDomain")
public class SampleBoardDomain {
    
    
	private int idx;
    private int parentIdx;
    private int hitCnt;
    private String title;
    private String contents;
    private String delGb;
    private Timestamp creaDtm;
    private String creaId;


	
}
