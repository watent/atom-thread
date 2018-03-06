package com.watent.thread.mode.forkjoin.vo;


import java.util.Random;

public class MakePanTaoArray {

    //数组长度
    public static final int ARRAY_LENGTH = 40000;
    //作为基准的值
    public static final int STANDARD_VAL = 66694523;

    public static Peach[] makeArray() {

        //new三个随机数发生器
        Random rColor = new Random();
        Random rSize = new Random();
        Random rYear = new Random();
        Peach[] result = new Peach[ARRAY_LENGTH];
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            //填充数组
            Peach panTao = new Peach(
                    rColor.nextBoolean() ? Color.RED : Color.GREEN,
                    rSize.nextBoolean() ? Size.BIG : Size.SMALL,
                    rYear.nextInt(9001));
            result[i] = panTao;
        }
        return result;
    }

    public static void main(String[] args) {
        Peach[] panTaos = makeArray();
        for (Peach panTao : panTaos) {
            System.out.println(panTao);
        }
    }

}
