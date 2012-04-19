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

import java.io.InputStream;
import java.util.Date;
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
        String message = null;
        InputStream profileImageResource = null;
        List<String> modules = null;
        String text = status.getText();

        // does the tweet contain room name?
        for(String roomName : seatingList.getRooms()){
            if(text.contains(roomName)){
                modules = seatingList.getVentilationModulesIn(roomName);
                break;
            }
        }

        // lookup modules for the user's seat
        if(modules == null){
            modules = seatingList.getVentilationModules(status.getUser().getScreenName());
        }
        if (status.getText().contains(HOT)) {
            if (ehills.adjust(-1, modules)) {
                message = "@" + screenName + " 涼しくしたよ！ " + Message.getMessage();
                profileImageResource = KuchoController.class.getResourceAsStream("/atsui.jpg");
            } else {
                message = "@" + screenName + " もう十分涼しいはずなんだけど・・";
                profileImageResource = KuchoController.class.getResourceAsStream("/kucho.jpg");
            }
        } else if (status.getText().contains(COLD)) {
            if (ehills.adjust(+1, modules)) {
                message = "@" + screenName + " 暖かくしたよ！ " + Message.getMessage();
                profileImageResource = KuchoController.class.getResourceAsStream("/samui.jpg");
            } else {
                message = "@" + screenName + " もう十分あたたかいはずなんだけど・・";
                profileImageResource = KuchoController.class.getResourceAsStream("/kucho.jpg");
            }

        }
        if (message != null) {
            System.out.println("messaage:"+message+" "+ profileImageResource);
            try {
                if (!dryRun) {
//                TwitterFactory.getSingleton().updateStatus(new StatusUpdate(message + " " + new Date().toString()).media("image",profileImageResource));
                    TwitterFactory.getSingleton().updateStatus(message + " " + new Date().toString());
                }
            } catch (TwitterException e) {
                logger.error("failed to update status", e);
            }
        }
    }

    @Override
    public void onException(Exception e) {
        e.printStackTrace();
        logger.error("error", e);
    }
}
