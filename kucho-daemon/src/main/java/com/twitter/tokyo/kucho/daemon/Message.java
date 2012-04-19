package com.twitter.tokyo.kucho.daemon;

public class Message {
    static String[] messages = {
            "がんばってピ",
            "あきらめないで！",
            "ファイト一発",
            "疲れたらお砂場で遊んでぴよ",
            "じゃあ巣作りにもどるぴよ",
            "はばないすでーぴよ",
            "かもめはかもめ",
            "今日も仕事がんばるピヨ",
            "ほほほのほーい",
            "これで快適ピヨ",
            "焼き鳥食べたいぴよね",
            "これで快適になるといいぴよ",
            "今日も業務をこなしているぴよ",
            "また何かあったらいってぴよ",
            "じゃあお昼寝行ってくるぴよ",
            "ばいばいさようならー",
            "Dooo youuur bestttt!!!",
    };

    public static String getMessage() {
        return messages[(int) (System.currentTimeMillis() % messages.length)];
    }
}
