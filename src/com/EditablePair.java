package com;

public class EditablePair<K, V> extends Pair<K, V> {
    public EditablePair(K key, V value) {
        super(key, value);
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
