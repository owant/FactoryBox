package com.owant.createcode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

/**
 * created by Kyle.Zhong on 2020-03-05
 */

public class Test {

    class Point {

        public float x;
        
        public float y;
    }

    public static void main(String[] args) {
//        ActionFactory.create("event").onClick();

        System.out.printf("%s\n", new Date().getTime() / 1000);

        String sql = "INSERT OR IGNORE INTO `humidity_record`(`timestamp`,`humidity_value`,`mac_address`) VALUES (%s,%s,\"%s\");";

        for (int i = 0; i < 1000; i++) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String beginDate = "2020-03-21 08:00";
            String endDate = "2020-03-20 23:00";
            try {
                Date start = format.parse(beginDate);
                Date end = format.parse(endDate);
                long begin = start.getTime();
                long last = end.getTime();
                long rtn = begin + (long) (Math.random() * (last - begin));

                float v = new Random().nextInt(1000);
                System.out.printf(sql + "\n", (int) (rtn / 1000), v + "", "8a:85:b7:ea:b1:e5");

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

    }


    public LinkedList<Integer> fillDate(LinkedList<Integer> linkedList) {
        //计算出缺失的点
        //计算出缺失的两点间的差距
        //填充数据
        //到数据纠正完毕结束


        return null;
    }
}
