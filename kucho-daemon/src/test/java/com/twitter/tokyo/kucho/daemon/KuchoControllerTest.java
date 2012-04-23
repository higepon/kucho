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
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class KuchoControllerTest {
    @Test
    public void testWarmer() {
        EHillsStub ehillsStub = new EHillsStub();
//        KuchoController listener = new KuchoController(ehillsStub, mySeatingList);
        KuchoController listener = new KuchoController(ehillsStub, new SeatingList());
        listener.dryRun = true;
        assertEquals(3, ehillsStub.level);
        listener.onStatus(new StatusSkelton("yusukey", "#さむい"));
        assertEquals(5, ehillsStub.level);
        assertModuleNames(ehillsStub, "VAV17E-13", "VAV17E-14");
        listener.onStatus(new StatusSkelton("yusukey", "#さむい"));
        assertEquals(5, ehillsStub.level);
        listener.onStatus(new StatusSkelton("yusukey", "めちゃ #あつい"));
        assertEquals(1, ehillsStub.level);
        listener.onStatus(new StatusSkelton("yusukey", "#あつい"));
        assertEquals(1, ehillsStub.level);
        listener.onStatus(new StatusSkelton("yusukey", "very #cold"));
        assertEquals(5, ehillsStub.level);
        listener.onStatus(new StatusSkelton("yusukey", "extremely #hot"));
        assertEquals(1, ehillsStub.level);
        listener.onStatus(new StatusSkelton("yusukey", "kiji #さむい"));
        assertEquals(3, ehillsStub.level);
        System.out.println(ehillsStub);
        assertModuleNames(ehillsStub, "VAV17E-05");
        listener.onStatus(new StatusSkelton("marotan", "@kuchosan @ajishiosean TOKI　すごく　＃暑い"));
        assertModuleNames(ehillsStub, "VAV17E-09", "VAV17E-10");
        assertEquals(1, ehillsStub.level);
    }

    private void assertModuleNames(EHillsStub stub, String... modules) {
        int matched = 0;
        for (String module : modules) {
            for (String area : stub.lastAreas) {
                if (area.equals(module)) {
                    matched++;
                    break;
                }
            }
        }
        assertEquals(matched, modules.length);
    }

    class EHillsStub implements EHills {
        int level = 3;
        List<String> lastAreas = null;

        @Override
        public boolean adjust(int value, List<String> areas) {
            lastAreas = areas;
            int oldLevel = level;
            level += value;
            if (level > 5) {
                level = 5;
            } else if (level < 1) {
                level = 1;
            }
            return oldLevel != level;
        }

        @Override
        public String toString() {
            return "EHillsStub{" +
                    "level=" + level +
                    ", lastAreas=" + lastAreas +
                    '}';
        }
    }


    SeatingList mySeatingList = new SeatingListStub();

    @Test
    public void callCooler() throws TwitterException {
        TwitterFactory.getSingleton().updateStatus(new StatusUpdate(new Date().toString()).media("image", KuchoController.class.getResourceAsStream("/atsui.jpg")));
    }

}
