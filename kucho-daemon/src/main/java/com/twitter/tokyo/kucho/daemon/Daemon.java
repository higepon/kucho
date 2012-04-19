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
import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public final class Daemon implements Constants {
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(KuchoController.class);

    public static void main(String[] args) {
        TwitterStream stream = TwitterStreamFactory.getSingleton();
        SeatingList seatingList = new SeatingList();
        stream.addListener(new KuchoController(EHillsImpl.getInstance(), seatingList));
        String[] trackTags = new String[COLD.length + HOT.length];
        System.arraycopy(COLD,0,trackTags,0,COLD.length);
        System.arraycopy(HOT,0,trackTags,COLD.length,HOT.length);
        FilterQuery query = new FilterQuery().track(trackTags);
        logger.info("Starting.");
        stream.filter(query);
        logger.info("Started.");
        while (true) {
            try {
                Thread.sleep(1000000);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
}
