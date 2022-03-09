package com.tangzhangss.commonutils.utils;

public class ArrayUtil {

    /**
     * 反转数组
     */
    public static <T> void reverseArray(T[] arr) {
        T temp;
        int n = arr.length;
        for (int i = 0; i < n / 2; i++) {
            temp = arr[i];
            arr[i] = arr[n - 1 - i];
            arr[n - 1 - i] = temp;
        }
    }
}
