package com.tangzhangss.commonutils.utils;

import java.util.*;

public class HashMapUtil {

    public HashMap hashMap;

    public static HashMapUtil createHashMap(){
        HashMapUtil listUtil = new HashMapUtil();
        listUtil.hashMap = new HashMap();
        return listUtil;
    }
    public static HashMapUtil createLinkedHashMap(){
        HashMapUtil listUtil = new HashMapUtil();
        listUtil.hashMap = new LinkedHashMap();
        return listUtil;
    }

    public HashMapUtil put(Object key,Object value){
        hashMap.put(key,value);
        return this;
    }
    public HashMapUtil putAll(Map map){
        hashMap.putAll(map);
        return this;
    }
    public HashMapUtil put(String[][] arr){
        for (int i = 0; i < arr.length; i++) {
            hashMap.put(arr[i][0],arr[i][1]);
        }
        return this;
    }

    public HashMap get(){
        return hashMap;
    }

    public static void main(String[] args) {
        HashMap t =  HashMapUtil.createHashMap().put("a","1").put("a1",false).put("a2",null).put("a3",4).get();

        Set keys = t.keySet();
        keys.forEach(item->{
            System.out.println(t.get(item));
        });
    }
}
