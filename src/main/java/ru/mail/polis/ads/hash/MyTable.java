package ru.mail.polis.ads.hash;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyTable<Key, Value> implements HashTable<Key, Value> {
    private static final double FILL_FACTOR = 0.65;

    private static class Elem <K, V> {
        K key;
        V value;
        Elem<K, V> nextElem;

        public Elem(K key, V value, Elem nextElem) {
            this.key = key;
            this.value = value;
            this.nextElem = nextElem;
        }
    }

    private Elem[] array;

    private int count = 0;
    private int capacity = 16;


    public MyTable(int capacity) {
        this.array = new Elem[capacity];
    }

    public MyTable() {
        this(16);
    }

    @Nullable
    @Override
    public Value get(@NotNull Key key) {
        int hash = hash(key);
        Elem<Key, Value> current = array[hash];
        while ((current != null) && (!current.key.equals(key))) {
            current = current.nextElem;
        }

        return current == null ? null : current.value;
    }

    @Override
    public void put(@NotNull Key key, @NotNull Value value) {
        int hash = hash(key);
        Elem<Key, Value> current = array[hash];
        if (current == null) {
            array[hash] = new Elem<Key, Value>(key, value, null);
            count++;
            return;
        }

        while ( (current.nextElem != null) && (!current.key.equals(key)) ) {
            current = current.nextElem;
        }


        if (!current.key.equals(key)) {
            current.nextElem = new Elem<Key, Value>(key, value, null);
            count++;
        } else {
            current.value = value;
        }

        if (count > FILL_FACTOR * capacity) {
            resize();
        }
    }

    @Nullable
    @Override
    public Value remove(@NotNull Key key) {
        count--;
        int hash = hash(key);
        Value result = null;
        Elem<Key, Value> current = array[hash];

        if (current == null) {
            count++;
            return null;
        }

        if (current.key.equals(key)) {
            result = current.value;
            array[hash] = null;
            return result;
        }

        while ((current.nextElem != null) && (!current.nextElem.key.equals(key))) {
            current = current.nextElem;
        }

        if (current.nextElem != null) {
            result = current.nextElem.value;
            current.nextElem = current.nextElem.nextElem;
            return result;
        }

        count++;
        return null;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % capacity;
    }

    private void resize() {
        capacity *= 2;
        count = 0;
        Elem[] oldArray = array;
        array = new Elem[capacity];
        for (Elem elem : oldArray) {
            Elem<Key, Value> current = elem;
            while (current != null) {
                put(current.key, current.value);
                current = current.nextElem;
            }
        }
    }
}
