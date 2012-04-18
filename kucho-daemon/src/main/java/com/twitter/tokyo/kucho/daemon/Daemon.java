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
import twitter4j.internal.logging.Logger;

public final class Daemon implements Constants {
    static final Logger logger = Logger.getLogger(DaemonStatusListener.class);

    public static void main(String[] args) {
        TwitterStream stream = TwitterStreamFactory.getSingleton();
        stream.addListener(new DaemonStatusListener(EHillsImpl.getInstance(), new SeatingList()));
        FilterQuery query = new FilterQuery().track(new String[]{HOT, COLD});
        logger.info("Starting.");
        stream.filter(query);
        logger.info("Started.");
    }
}
