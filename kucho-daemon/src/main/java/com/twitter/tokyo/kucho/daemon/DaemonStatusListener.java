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
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/*package*/ final class DaemonStatusListener extends StatusAdapter implements Constants {
    private final EHills ehills;
    private final SeatingList seatingList;

    /*package*/ DaemonStatusListener(EHills ehills, SeatingList seatingList) {
        this.ehills = ehills;
        this.seatingList = seatingList;
    }
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(DaemonStatusListener.class);

    @Override
    public void onStatus(Status status) {
        String screenName = status.getUser().getScreenName();
        String message = null;
        InputStream profileImageResource = null;
        String[] modules = getModules(screenName);
        if (status.getText().contains(HOT)) {
            if (ehills.cooler(modules)) {
                message = "@" + screenName + " 涼しくしたよ！ "+Message.getMessage();
                profileImageResource = DaemonStatusListener.class.getResourceAsStream("/atsui.jpg");
            } else {
                message = "@" + screenName + " もう十分涼しいはずなんだけど・・";
            }
        } else if (status.getText().contains(COLD)) {
            if (ehills.warmer(modules)) {
                message = "@" + screenName + " 暖かくしたよ！ "+Message.getMessage();
                profileImageResource = DaemonStatusListener.class.getResourceAsStream("/samui.jpg");
            } else {
                message = "@" + screenName + " もう十分あたたかいはずなんだけど・・";
            }

        }
        if (message != null) {
            try {
                TwitterFactory.getSingleton().updateStatus(new StatusUpdate(message + " " + new Date().toString()).media("image",profileImageResource));
            } catch (TwitterException e) {
                logger.error("failed to update status", e);
            }
        }
    }

    private String[] getModules(String screenName) {
        List<String> ventilationModules = seatingList.getVentilationModules(screenName);
        return ventilationModules.toArray(new String[0]);
    }

    @Override
    public void onException(Exception e) {
        logger.error("error", e);
    }
}
