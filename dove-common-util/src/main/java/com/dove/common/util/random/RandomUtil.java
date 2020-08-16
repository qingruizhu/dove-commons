package com.dove.common.util.random;


import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

public final class RandomUtil {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");
//    private static final ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

    public static StringBuilder generateUniqCode() {
        StringBuilder sb = new StringBuilder(dateFormat.format(new Date()));
        sb.append(generateNum(4));
        return sb;
    }

    /**
     * 数字组合
     */
    public static final String RANDOM_TYPE_NUM = "NUM";
    /**
     * 字母组合
     */
    public static final String RANDOM_TYPE_AZ = "AZ";
    /**
     * 字母+数字
     */
    public static final String RANDOM_TYPE_ALL = "ALL";
    public static final String RANDOM_TYPE_DEFAULT = RANDOM_TYPE_ALL;
    /**
     * 随机数长度
     */
    public static final int RANDOM_LENGTH_4 = 4;
    public static final int RANDOM_LENGTH_6 = 6;
    public static final int RANDOM_LENGTH_DEFAULT = RANDOM_LENGTH_4;

    private static final char[] NUM_CHARS = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
            /*,'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                '~', '!', '@', '#', '$', '%', '^', '-', '+', '&', '_'*/
    };
    private static final char[] AZ_CHARS = new char[]{
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'/*,
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '~', '!', '@', '#', '$', '%', '^', '-', '+', '&', '_'*/
    };
    private static final char[] ALL_CHARS = new char[]{
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'/*,
                '~', '!', '@', '#', '$', '%', '^', '-', '+', '&', '_'*/
    };


    private RandomUtil() {
    }

    public static String generateRandom(int length, String type) {
        if (0 == length) {
            length = RANDOM_LENGTH_DEFAULT;
        }
        if (StringUtils.isEmpty(type)) {
            type = RANDOM_TYPE_DEFAULT;
        }
        switch (type) {
            case RANDOM_TYPE_NUM:
                return generateNum(length);
            case RANDOM_TYPE_AZ:
                return generateAZ(length);
            default:
                return generateALL(length);
        }
    }


    public static String generateNum(int length) {
        if (length == 0) {
            length = RANDOM_LENGTH_DEFAULT;
        }
        List<String> list = new ArrayList<String>(NUM_CHARS.length);
        for (int i = 0; i < NUM_CHARS.length; i++) {
            list.add(String.valueOf(NUM_CHARS[i]));
        }
        return randomGenerate(list, length);
    }

    public static String generateAZ(int length) {
        if (length == 0) {
            length = RANDOM_LENGTH_DEFAULT;
        }
        List<String> list = new ArrayList<String>(AZ_CHARS.length);
        for (int i = 0; i < AZ_CHARS.length; i++) {
            list.add(String.valueOf(AZ_CHARS[i]));
        }
        return randomGenerate(list, length);
    }

    public static String generateALL(int length) {
        if (length == 0) {
            length = RANDOM_LENGTH_DEFAULT;
        }
        List<String> list = new ArrayList<String>(ALL_CHARS.length);
        for (int i = 0; i < ALL_CHARS.length; i++) {
            list.add(String.valueOf(ALL_CHARS[i]));
        }
        return randomGenerate(list, length);
    }

    private static String randomGenerate(List<String> lst, int length) {
        Collections.shuffle(lst);
        int count = 0;
        StringBuffer sb = new StringBuffer();
        Random random = new Random(System.nanoTime());
        while (count < length) {
            int i = random.nextInt(lst.size());
            sb.append(lst.get(i));
            count++;
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        System.out.println(generateALL(20));
    }
}
