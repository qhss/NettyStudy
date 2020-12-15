package com.atguigu.linkedList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 算法相关代码
 */
public class Algorithm {
    public static void main(String[] args) {
        int[] array = {6, 4, -3, 5, -2, -1, 0, 1, -9,9};
        System.out.println(Arrays.toString(changeInt(array)));
    }

    /**
     * 正数往左移动
     * @param array
     * @return
     */
    private static int[] changeInt(int[] array) {

        int left = 0;
        int right = array.length - 1;
        int temp = 0;
        while(left!=right){
            if (array[left] <0){
                if (array[right] > 0){
                    temp=array[left];
                    array[left]=array[right];
                    array[right] = temp;
                    left++;
                }
                right--;
                continue;

            }
            left++;

        }

        return array;

    }

    /**
     * 移动0
     * @param nums
     */
    public void moveZeroes(int[] nums) {
        if(nums==null) {
            return;
        }
        //第一次遍历的时候，j指针记录不等于0的个数，只要是不等于0的统统都赋给nums[j]
        int j = 0;
        for(int i=0;i<nums.length;++i) {
            //只要往数组前面插入一个非0数，就要在当前index下赋值一个0
            if(nums[i]!=0) {
                nums[j] = nums[i];
                if(i!=j){
                    nums[i]=0;
                }
                j++;
            }
        }
    }
    /**
     * 移动0 ，交换数据方式
     */
//     public void moveZeroes(int[] nums) {
//     int j = 0;
//     for(int i = 0; i < nums.length; i++) {
//         if(nums[i] != 0) {
//             int temp = nums[j];
//             nums[j] = nums[i];
//             nums[i] = temp;
//             j++;
//         }
//     }
// }

    /**
     * 三数求和问题（leetcode第十五题）
     *暴力求解方式
     *时间复杂度O(n^3)
     *（有重复数据问题暂未解决）
     **/

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) { // 每个人
            for (int j = i + 1; j < nums.length - 1; j++) { // 依次拉上其他每个人
                for (int k = j + 1; k < nums.length; k++) { // 去问剩下的每个人
                    if (nums[i] + nums[j] + nums[k] == 0) { // 我们是不是可以一起组队
                        List<Integer> res=new ArrayList<>();
                        res.add(nums[i]);
                        res.add(nums[j]);
                        res.add(nums[k]);
                        result.add(res);
                    }
                }
            }
        }
        return result;
    }
    /**
     * 三数求和问题（leetcode第十五题）
     *双指针方式
     *时间复杂度为O(n^2)
     *i为最左侧元素（最小数）lo为左指针，hi为右指针
     **/
//    public List<List<Integer>> threeSum2(int[] num) {
//        Arrays.sort(num);
//        List<List<Integer>> res = new LinkedList<>();
//        for (int i = 0; i < num.length-2; i++) {
//            //判断i值与上一个i值是否重复
//            if (i == 0 || (i > 0 && num[i] != num[i-1])) {
//                int lo = i+1, hi = num.length-1, sum = 0 - num[i];
//                while (lo < hi) {
//                    if (num[lo] + num[hi] == sum) {
//                        res.add(Arrays.asList(num[i], num[lo], num[hi]));
//                        while (lo < hi && num[lo] == num[lo+1]) lo++;
//                        while (lo < hi && num[hi] == num[hi-1]) hi--;
//                        lo++; hi--;
//                    }
//                    else if (num[lo] + num[hi] < sum) lo++;
//                    else hi--;
//                }
//            }
//        }
//        return res;
//    }

    /**复杂度O(n^2)
     * 三数之和
     *
     * @param num
     * @return
     */
    public List<List<Integer>> threeSums(int[] num){
        Arrays.sort(num);
        List<List<Integer>> res = new LinkedList<>();
        for (int i = 0; i< num.length-2; i++){
            if (i==0||(i>0 && num[i] != num[i-1])){
                int lo = i+1,hi = num.length-1,sum=0-num[i];
                while (lo!=hi){
                    if (num[lo]+num[hi]==sum){
                       res.add(Arrays.asList(num[i],num[lo],num[hi]));
                       while(lo < hi && num[lo] == num[lo+1]) lo++;
                       while(lo < hi && num[hi] == num[hi-1]) hi--;
                       lo++;
                       hi--;
                    }
                    else if (num[lo] + num[hi] < sum){
                        lo++;
                    }
                    else
                        hi--;
                }
            }

        }

        return res;
    }
}
