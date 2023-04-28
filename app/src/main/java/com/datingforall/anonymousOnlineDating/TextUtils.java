package com.datingforall.anonymousOnlineDating;

import java.util.Random;

public class TextUtils {
    public static int randomize() {
        int min = 0;
        int max = 9;

        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }
}
