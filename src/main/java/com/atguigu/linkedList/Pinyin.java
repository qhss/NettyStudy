package com.atguigu.linkedList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.atguigu.dto.FirstLetter;
import com.atguigu.dto.FirstLetterList;
import com.atguigu.util.FirstLetterUtil;

import java.util.*;

public class Pinyin {

    /**
     * 根据一个包含汉字的字符串数组 返回一个汉字拼音首字母和一个以此首字母为首的汉字数组
     *
     * @param characters 字符串数组
     * @return List<FirstLetterList>
     */
    public static List<FirstLetterList> getFirstLetterList(String[] characters) {

        //定义一个对象数组，用来存储这些汉字的首字母和汉字本身
        List<FirstLetter> firstLetters = new ArrayList<>();
        for (String character : characters) {
            FirstLetter firstLetter = new FirstLetter();
            firstLetter.setCharacters(character);
            //使用 获取汉字首字母工具类 获取汉字首字母
            firstLetter.setFirstLetter(FirstLetterUtil.getFirstLetter(character));
            firstLetters.add(firstLetter);
        }

        //对数组进行排序，以 A-Z 的顺序
        //字典排序
        firstLetters.sort(new Comparator<FirstLetter>() {
            @Override
            public int compare(FirstLetter o1, FirstLetter o2) {
                char[] chars1 = o1.getFirstLetter().toCharArray();
                char[] chars2 = o2.getFirstLetter().toCharArray();
                int i = 0;
                while (i < chars1.length && i < chars2.length) {
                    if (chars1[i] > chars2[i]) {
                        return 1;
                    } else if (chars1[i] < chars2[i]) {
                        return -1;
                    } else {
                        i++;
                    }
                }
                //o1到头
                if (i == chars1.length) {
                    return -1;
                }
                //o2到头
                if (i == chars2.length) {
                    return 1;
                }
                return 0;
            }
        });

        //定义类似[["firstWord": "7", [{"characters": "7号房的礼物", "firstLetter": "7hfdlw"}]}]的数组
        List<FirstLetterList> firstLetterLists = new ArrayList<>();

        //定义类似 "A": [{"characters": "7号房的礼物", "firstLetter": "7hfdlw"}]}] 的map
        Map<String, List<FirstLetter>> map = new LinkedHashMap<>();

        //把第一个字母相同的对象加到对应 map 的对象数组里
        for (FirstLetter firstLetter : firstLetters) {

            //取第一个字母转为大写
            String first = firstLetter.getFirstLetter().substring(0, 1).toUpperCase();

            //取第一个字母对应的对象数组
            List<FirstLetter> chooseFirstLetterList = map.get(first);

            //判断第一个字母对应的对象数组是否已经存在
            List<FirstLetter> firstLetterList;
            if (chooseFirstLetterList == null || chooseFirstLetterList.size() == 0) {
                firstLetterList = new ArrayList<>();
            } else {
                firstLetterList = map.get(first);
            }
            firstLetterList.add(firstLetter);
            map.put(first, firstLetterList);
        }

        //把 map 的 key 和 value 处理一下，变成对象数组的形式
        for (String in : map.keySet()) {
            FirstLetterList firstLetterList = new FirstLetterList();
            firstLetterList.setFirstWord(in);
            firstLetterList.setFirstLetterList(map.get(in));
            firstLetterLists.add(firstLetterList);
        }

        return firstLetterLists;
    }

    public static void main(String[] s) {

        //需要做首字母定位的数组
        String[] strs = {"霸王别姬",
                "肖申克的救赎",
                "罗马假日",
                "这个杀手不太冷",
                "教父",
                "泰坦尼克号",
                "唐伯虎点秋香",
                "千与千寻",
                "魂断蓝桥",
                "乱世佳人",
                "天空之城",
                "喜剧之王",
                "辛德勒的名单",
                "大闹天宫",
                "音乐之声",
                "剪刀手爱德华",
                "春光乍泄",
                "美丽人生",
                "海上钢琴师",
                "黑客帝国",
                "哈利·波特与魔法石",
                "加勒比海盗",
                "指环王3：王者无敌",
                "无间道",
                "射雕英雄传之东成西就",
                "楚门的世界",
                "蝙蝠侠：黑暗骑士",
                "教父2",
                "指环王2：双塔奇兵",
                "机器人总动员",
                "天堂电影院",
                "活着",
                "拯救大兵瑞恩",
                "哈尔的移动城堡",
                "阿凡达",
                "盗梦空间",
                "忠犬八公的故事",
                "幽灵公主",
                "搏击俱乐部",
                "东邪西毒",
                "风之谷",
                "疯狂原始人",
                "当幸福来敲门",
                "V字仇杀队",
                "十二怒汉",
                "放牛班的春天",
                "三傻大闹宝莱坞",
                "勇敢的心",
                "黑客帝国3：矩阵革命",
                "速度与激情5",
                "驯龙高手",
                "神偷奶爸",
                "少年派的奇幻漂流",
                "闻香识女人",
                "断背山",
                "飞屋环游记",
                "大话西游之月光宝盒",
                "飞越疯人院",
                "怦然心动",
                "美国往事",
                "致命魔术",
                "鬼子来了",
                "无敌破坏王",
                "美丽心灵",
                "蝙蝠侠：黑暗骑士崛起",
                "夜访吸血鬼",
                "倩女幽魂",
                "哈利·波特与死亡圣器（下）",
                "本杰明·巴顿奇事",
                "钢琴家",
                "触不可及",
                "熔炉",
                "初恋这件小事",
                "大话西游之大圣娶亲",
                "新龙门客栈",
                "甜蜜蜜",
                "素媛",
                "小鞋子",
                "萤火之森",
                "时空恋旅人",
                "穿条纹睡衣的男孩",
                "窃听风暴",
                "7号房的礼物",
                "借东西的小人阿莉埃蒂",
                "恐怖直播",
                "海豚湾",
                "忠犬八公物语",
                "上帝之城",
                "辩护人",
                "七武士",
                "英雄本色",
                "一一",
                "完美的世界",
                "海洋",
                "爱·回家",
                "黄金三镖客",
                "我爱你",
                "迁徙的鸟",
                "阿飞正传",
                "龙猫"};

        //进行首字母定位
        List<FirstLetterList> firstLetterLists = getFirstLetterList(strs);
//
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(firstLetterLists));
        System.out.println(jsonArray.toJSONString());

    }

}
