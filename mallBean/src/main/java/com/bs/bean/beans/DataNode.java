package com.bs.bean.beans;

public class DataNode<K, V> {

    private DataNode pre;
    private DataNode next;
    private K key;
    private V val;


    public DataNode() {
    }

    public DataNode(K key, V val) {
        this.key = key;
        this.val = val;
    }

    public DataNode getPre() {
        return pre;
    }

    public void remove(){
        pre.setNext(next);
        next.setPre(pre);
    }

    public void setPre(DataNode pre) {
        this.pre = pre;
    }

    public DataNode getNext() {
        return next;
    }

    public void setNext(DataNode next) {
        this.next = next;
    }

    public V getVal() {
        return val;
    }

    public void setVal(V val) {
        this.val = val;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }
}
