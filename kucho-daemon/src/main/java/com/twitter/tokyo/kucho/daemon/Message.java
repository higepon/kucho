package com.twitter.tokyo.kucho.daemon;

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
