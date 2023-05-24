package com.main.arraylist;

import com.main.arraylist.util.CustomArrayList;
import com.main.arraylist.util.CustomList;
import com.main.arraylist.util.SortMethod;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        CustomList<Double> customList = new CustomArrayList<>(Arrays.asList(10.0,11.0,2.0,3.3,1.0,2.0));
        System.out.println(customList);
        customList.sort(SortMethod.MERGE_SORT, null);
        System.out.println(customList);
        customList.clear();
    }
}