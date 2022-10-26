package com.tangzhangss.commonutils.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ListUtil{

    public List list=new ArrayList();//默认arraylist

    public static ListUtil createArrayList(){
        ListUtil listUtil = new ListUtil();
        listUtil.list = new ArrayList();
        return listUtil;
    }
    public static ListUtil createArrayList(int capacity){
        ListUtil listUtil = new ListUtil();
        listUtil.list = new ArrayList(capacity);
        return listUtil;
    }
    public static ListUtil createArrayList(Collection c){
        ListUtil listUtil = new ListUtil();
        listUtil.list = new ArrayList(c);
        return listUtil;
    }
    public static ListUtil createLinkedList(){
        ListUtil listUtil = new ListUtil();
        listUtil.list = new LinkedList();
        return listUtil;
    }
    public static ListUtil createLinkedList(Collection c){
        ListUtil listUtil = new ListUtil();
        listUtil.list = new LinkedList(c);
        return listUtil;
    }

    public ListUtil add(Object obj){
        list.add(obj);
        return this;
    }
    public ListUtil addAll(Collection collection){
        list.addAll(collection);
        return this;
    }

    public List get(){
        return list;
    }

    public static void main(String[] args) {
        List t =  ListUtil.createArrayList().add("ssss").add(1).add(null).add(false).get();

        t.forEach(i->{
            System.out.println(i.getClass().getSimpleName());
        });
        System.out.println(t);
    }
}
