package com.twitter.tokyo.kucho.daemon;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: yusuke
 * Date: 4/18/12
 * Time: 6:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class Message {
    static String[] messages = {
            "がんばってピ",
            "あきらめないで！",
            "ファイト一発",
            "今日も仕事がんばるピヨ",
            "これで快適ピヨ",
    };

    public static String getMessage() {
        return messages[(int) (System.currentTimeMillis() % messages.length)];
    }
}
