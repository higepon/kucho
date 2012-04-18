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

import twitter4j.internal.logging.Logger;

import java.io.*;

public class EHillsImpl implements EHills {
    static final Logger logger = Logger.getLogger(EHillsImpl.class);
    private static final EHills ehills = new EHillsImpl();

    private EHillsImpl() {
    }

    public static EHills getInstance() {
        return ehills;
    }

    public boolean warmer(String[] areas) {
        try {
            ProcessBuilder pb = new ProcessBuilder("myCommand", join(areas," ")).redirectErrorStream(true);
            Process p = pb.start();
            p.waitFor();
            InputStream is = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String result = br.readLine();
            p.waitFor();
            return "ok".equals(result);
        } catch (IOException e) {
            logger.error("failed:", e);
            return false;
        } catch (InterruptedException e) {
            logger.error("failed:", e);
            return false;
        }
    }

    public boolean cooler(String[] areas) {
        return true;
    }

    private static String join(String[] strs, String separator){
        StringBuilder joined = new StringBuilder();
        boolean first = true;
        for(String str : strs){
            if(first){
                first = false;
            }else{
                joined.append(separator);
            }
            joined.append(str);
        }
        return joined.toString();
    }
}
