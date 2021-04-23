package com.bs.bean.beans;


public class DataTable<K, V> {

	private KVList<K, V>[] dt;

	private int rehashIndex;

	private boolean isRehash;

	@SuppressWarnings("unchecked")
	public DataTable(int lruCapacity, int mapCapacity) {
		this.isRehash = false;
		this.rehashIndex = -1;

		dt = new KVList[2];
		dt[0] = new KVList<>(lruCapacity, mapCapacity);
	}

	public int size() {
		return dt[0].capacity();
	}

	public V get(K key) {
		V e;
		if (isRehash) {
			if ((e = dt[1].getV(key)) == null) {
				e = dt[0].getV(key);
			}
			rehash();
		} else {
			e = dt[0].getV(key);
		}
		return e == null ? null : e;
	}

	public KVMap.Node<K, DataNode<K, V> > getIndex(int index) {
		KVMap.Node<K, DataNode<K, V>> e = dt[0].getIndex(index);
		return e;
	}

	public void put(K key, V value, long cid) {
		if (!isRehash) {
			if (dt[0].isReSize()) {
				isRehash = true;
				rehashIndex = 0;
				resize();
			}
		}

		if (isRehash) {
			dt[1].putKV(key, value, cid);
			rehash();
		} else {
			dt[0].putKV(key, value, cid);
		}
	}

	private void resize() {
		int capacity = dt[0].capacity() << 1;
		if (capacity >= Integer.MAX_VALUE) {
			throw new ArrayIndexOutOfBoundsException();
		}
		capacity = capacity > Integer.MAX_VALUE ? Integer.MAX_VALUE : capacity;
		dt[1] = new KVList<>(capacity);
	}

	private void rehash() {
		if (rehashIndex < dt[0].capacity()) {
			KVMap.Node<K, DataNode<K, V> > e = dt[0].getIndex(rehashIndex);
			if (e != null) {
				dt[1].putNode(e);
			}
			rehashIndex++;
		} else {
			dt[0] = dt[1];
			dt[1] = null;

			isRehash = false;
			rehashIndex = -1;
		}
	}

	public DataNode<K, V>  remove(K key) {
		DataNode<K, V>  e;
		if (isRehash) {
			dt[0].remove(key);
			e = dt[1].remove(key);
			rehash();
		} else {
			e = dt[0].remove(key);
		}
		return e == null ? null : e;
	}

	public void reset() {
		dt[0] = null;
		dt[1] = null;
		dt = null;
	}

}
