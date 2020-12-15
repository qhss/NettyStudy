package com.atguigu.linkedList;

import java.util.ArrayList;
import java.util.List;

public class Example {
    public static void main(String[] args) {
        /**
         * i++与++i结果一致
         */
//        for(int i = 0; i<10; ++i) {
//            System.out.println(i);
//        }
//        System.out.println("================");
//        for(int i = 0; i<10; i++) {
//            System.out.println(i);
//        }

//
//        Integer integer = Integer.valueOf("001");
//        System.out.println(integer);//输出为1

        int[] nums={-1, 0, 1, 2, -1, -4};
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) { // 每个人
            for (int j = i + 1; j < nums.length - 1; j++) { // 依次拉上其他每个人
                for (int k = j + 1; k < nums.length; k++) { // 去问剩下的每个人
                    if (nums[i] + nums[j] + nums[k] == 0) { // 我们是不是可以一起组队
                        List<Integer> res=new ArrayList<>();
                        res.add(nums[i]);
                        res.add(nums[j]);
                        res.add(nums[k]);
//                        List<Integer> res2=new ArrayList<>();
//                        res2.add(123);
//                        result.add(res2);
                        result.add(res);

                    }
                }
            }
        }
        System.out.println(result);
    }


}
