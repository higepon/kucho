/*
 * Copyright 2012 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.twitter.tokyo.kucho.daemon;

import com.twitter.tokyo.kucho.SeatingList;
import twitter4j.*;

import java.io.File;
import java.util.List;

/*package*/ final class KuchoController extends StatusAdapter implements Constants {
    private final EHills ehills;
    private final SeatingList seatingList;
    /*package*/ boolean dryRun = false;

    /*package*/ KuchoController(EHills ehills, SeatingList seatingList) {
        this.ehills = ehills;
        this.seatingList = seatingList;
    }

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(KuchoController.class);

    @Override
    public void onStatus(Status status) {
        String screenName = status.getUser().getScreenName();
        // lookup modules for the user's seat
        List<String> modules = seatingList.getVentilationModules(status.getUser().getScreenName());
        if (modules.size() == 0) {
            // user is not in the list. do nothing
            return;
        }

        String text = status.getText().toLowerCase().replaceAll("＃","#");
        // does the tweet contain room name?
        for (String roomName : seatingList.getRooms()) {
            if (text.contains(roomName)) {
                modules = seatingList.getVentilationModulesIn(roomName);
                break;
            }
        }

        int degree = 2;
        if (text.matches(".*(わっぜ|なまちゃこ|たんげ|なんぼ|なまら|のれそれ|こじゃんと|岐阜|max|マックス|テラ|めっさ|バリ|ばり|どえりゃぁ|まじ|ﾏｼﾞ|マジ" +
                "|ごっつ|チョー|でら|超|めちゃ|とても|とっても|すごく|すんごい|めっちゃ|えらい|えれー|すげー" +
                "|too|very|extremely|intensively).*")) {
            degree = 4;
        }
        for (String hot : HOT) {
            if (text.contains(hot)) {
                degree = degree * -1;
                break;
            }
        }

        String message = null;
        String imagePath = null;
        String powerfully = Math.abs(degree) == 4 ? "いっぱい" : "";
        if (degree < 0) {
            if (ehills.adjust(degree, modules)) {
                message = "@" + screenName + " " + powerfully + "涼しくしたよ！ " + Message.getMessage();
                imagePath = "/atsui.jpg";
            } else {
                message = "@" + screenName + " もう十分涼しいはずなんだけど・・";
                imagePath = "/kucho.jpg";
            }
        } else {
            if (ehills.adjust(degree, modules)) {
                message = "@" + screenName + "  "+ powerfully + "暖かくしたよ！ " + Message.getMessage();
                imagePath = "/samui.jpg";
            } else {
                message = "@" + screenName + " もう十分あたたかいはずなんだけど・・";
                imagePath = "/kucho.jpg";
            }
        }
        System.out.println("messaage:" + message + " " + imagePath);
        try {
            if (!dryRun) {
                imagePath = "src/main/resources" + imagePath;
                if (!new File(".").getAbsolutePath().contains("kucho-daemon")) {
                    imagePath = "kucho-daemon/" + imagePath;
                }
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    TwitterFactory.getSingleton().updateStatus(new StatusUpdate(message).media(imageFile)
                            .inReplyToStatusId(status.getId()));
                } else {
                    TwitterFactory.getSingleton().updateStatus(new StatusUpdate(message)
                            .inReplyToStatusId(status.getId()));
                }
            }
        } catch (TwitterException e) {
            logger.error("failed to update status", e);
        }
    }

    @Override
    public void onException(Exception e) {
        e.printStackTrace();
        logger.error("error", e);
    }
}
