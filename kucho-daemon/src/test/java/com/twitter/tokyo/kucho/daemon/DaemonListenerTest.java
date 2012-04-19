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
import org.junit.Test;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class DaemonListenerTest {
    @Test
    public void testWarmer() {
        EHillsStub ehillsStub = new EHillsStub();
        DaemonStatusListener listener = new DaemonStatusListener(ehillsStub, mySeatingList);
        assertEquals(3, ehillsStub.level);
        listener.onStatus(new StatusSkelton("yusukey", "#さむい"));
        assertEquals(4, ehillsStub.level);
        listener.onStatus(new StatusSkelton("yusukey", "#さむい"));
        assertEquals(5, ehillsStub.level);
        listener.onStatus(new StatusSkelton("yusukey", "#さむい"));
        assertEquals(5, ehillsStub.level);
        listener.onStatus(new StatusSkelton("yusukey", "#あつい"));
        assertEquals(4, ehillsStub.level);
        listener.onStatus(new StatusSkelton("yusukey", "#あつい"));
        assertEquals(3, ehillsStub.level);
        listener.onStatus(new StatusSkelton("yusukey", "#あつい"));
        assertEquals(2, ehillsStub.level);
        listener.onStatus(new StatusSkelton("yusukey", "#あつい"));
        assertEquals(1, ehillsStub.level);
        listener.onStatus(new StatusSkelton("yusukey", "#あつい"));
        assertEquals(1, ehillsStub.level);

    }

    class EHillsStub implements EHills {
        int level = 3;

        @Override
        public boolean warmer(String[] areas) {
            if (level < 5) {
                level++;
                return true;
            }
            return false;
        }

        @Override
        public boolean cooler(String[] areas) {
            if (level > 1) {
                level--;
                return true;
            }
            return false;
        }
    }


    SeatingList mySeatingList = new SeatingListStub();

    @Test
    public void callCooler() throws TwitterException{
        TwitterFactory.getSingleton().updateStatus(new StatusUpdate(new Date().toString()).media("image",DaemonStatusListener.class.getResourceAsStream("/atsui.jpg")));
    }

}
