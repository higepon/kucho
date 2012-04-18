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
import com.twitter.tokyo.kucho.VentilationModule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

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


    SeatingList mySeatingList = new SeatingList() {
        public List<VentilationModule> getVentilationModules(String userName) {
            List<VentilationModule> modules = new ArrayList<VentilationModule>();
            if ("yusukey".equals(userName)) {
                modules.add(new VentilationModule("E81"));
                modules.add(new VentilationModule("E82"));
            }
            return modules;
        }
    };
}
