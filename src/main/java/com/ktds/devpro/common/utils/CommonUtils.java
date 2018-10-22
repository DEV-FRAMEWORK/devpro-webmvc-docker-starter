package com.ktds.devpro.common.utils;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CommonUtils {
    Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static String getRandomString(){ //????????? ???????????? ?????????..
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
//	
//	public static void printMap(Map<String,Object> map){
//		Iterator<Entry<String,Object>> iterator = map.entrySet().iterator();
//		Entry<String,Object> entry = null;
//		log.debug("--------------------printMap--------------------\n");
//		while(iterator.hasNext()){
//			entry = iterator.next();
//			log.debug("key : "+entry.getKey()+",\tvalue : "+entry.getValue());
//		}
//		log.debug("");
//		log.debug("------------------------------------------------\n");
//	}
}
