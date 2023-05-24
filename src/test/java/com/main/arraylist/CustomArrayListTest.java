package com.main.arraylist;

import com.main.arraylist.util.CustomArrayList;
import com.main.arraylist.util.SortMethod;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

public class CustomArrayListTest {
    private CustomArrayList<Integer> list;

    @Before
    public void setUp() throws Exception {
        list = new CustomArrayList<>();
    }


    @Test
    public void testAdd() {
        list.add(5);
        assertEquals(Integer.valueOf(5), list.get(0));
    }

    @Test
    public void testGet() {
        list.add(10);
        assertEquals(Integer.valueOf(10), list.get(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetIndexOutOfBounds() {
        list.get(0);
    }

    @Test
    public void testInsert() {
        list.add(15);
        list.insert(0, 20);
        assertEquals(Integer.valueOf(20), list.get(0));
        assertEquals(Integer.valueOf(15), list.get(1));
    }

    @Test
    public void testRemove() {
        list.add(25);
        list.remove(0);
        assertEquals(0, list.size());
    }

    @Test
    public void testSize() {
        assertEquals(0, list.size());
        list.add(30);
        assertEquals(1, list.size());
    }

    @Test
    public void testClear() {
        list.add(35);
        list.clear();
        assertEquals(0, list.size());
    }

    @Test
    public void testSort() {
        list.add(5);
        list.add(3);
        list.add(7);
        list.sort();
        assertEquals(Integer.valueOf(3), list.get(0));
        assertEquals(Integer.valueOf(5), list.get(1));
        assertEquals(Integer.valueOf(7), list.get(2));
    }

    @Test
    public void testSortWithComparator() {
        list.add(5);
        list.add(3);
        list.add(7);
        list.sort(Comparator.reverseOrder());
        assertEquals(Integer.valueOf(7), list.get(0));
        assertEquals(Integer.valueOf(5), list.get(1));
        assertEquals(Integer.valueOf(3), list.get(2));
    }

    @Test
    public void testSortWithCustomMethodAndComparator() {
        list.add(5);
        list.add(3);
        list.add(7);
        list.sort(SortMethod.MERGE_SORT, Comparator.reverseOrder());
        assertEquals(Integer.valueOf(7), list.get(0));
        assertEquals(Integer.valueOf(5), list.get(1));
        assertEquals(Integer.valueOf(3), list.get(2));
    }
}
