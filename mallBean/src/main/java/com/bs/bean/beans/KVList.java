package com.bs.bean.beans;

public class KVList<K, V> extends KVMap<K, DataNode<K, V>> {

    private DataNode<K, V> head, tail;
    private int size;
    private int lruCapacity;

    public KVList(){
        this(1000);
    }
    public KVList(int lruCapacity) {
        this(1000, 300);
    }
    public KVList(int lruCapacity, int mapCapacity) {
        super();
        size = 0;
        this.lruCapacity = lruCapacity;
        head = new DataNode();
        tail = new DataNode();

        head.setNext(tail);
        tail.setPre(head);
    }
    public void putKV(K key, V value, long cid) {
        DataNode<K, V> newNode = new DataNode<>(key, value);
        insertToHeadAfter(newNode);
        super.put(key, newNode, cid);
        size ++;
        lru();
    }

    public V getV(K key) {
        DataNode<K, V> node = super.get(key);
        if (node == null ) {
            return null;
        }
        node.remove();
        insertToHeadAfter(node);
        return node.getVal();
    }

    private void lru(){
        if (size <= lruCapacity){
            return;
        }
        DataNode<K, V> tailBefore = tail.getPre();
        tailBefore.remove();
        remove(tailBefore.getKey());
    }

    private void insertToHeadAfter(DataNode<K, V> node){
        node.setPre(head);
        node.setNext(head.getNext());
        head.getNext().setPre(node);
        head.setNext(node);
    }

    public String list() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        DataNode n = head.getNext() ;
        // n必须是实际存储数据的节点，因此判断需要排除tail节点
        while(n!=null && n!=tail){
            sb.append(n.getKey())
                    .append("=")
                    .append(n.getVal())
                    .append(",");
            n=n.getNext();
        }
        return  sb.toString().substring(0,sb.lastIndexOf(","))+"}";
    }

    public static void main(String[] args) {
        KVList<String,Integer> lruCache = new KVList<>(5);
        lruCache.putKV("a",1, 1);
        lruCache.putKV("b",2, 1);
        lruCache.putKV("c",3, 1);
        lruCache.putKV("d",4, 1);
        lruCache.putKV("e",5, 1);
        lruCache.get("a");//命中a，给a"续命"，将a放到链表的头部
        lruCache.putKV("f",6, 1);//默认缓存容量是5，再添加一个数据，就需要触发LRU机制移除尾节点（b）了
        System.out.println("for linkedlist:"+lruCache.list());

    }
}
