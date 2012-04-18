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
package com.twitter.tokyo.kucho;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VentilationMapBuilder {
  private static final Logger logger = LoggerFactory.getLogger(VentilationMapBuilder.class);

  private final String json;
  VentilationMapBuilder(String json) {
    this.json = json;
  }

  Map<String, List<VentilationModule>> build() {
    Map<String, String> accountMap = new HashMap<String, String>();
    Map<String, String> ventMap = new HashMap<String, String>();

    try {
      JSONObject root = new JSONObject(json);
      JSONObject feed = root.getJSONObject("feed");
      JSONArray entries = feed.getJSONArray("entry");
      for (int i = 0; i < entries.length(); i++) {
        JSONObject entry = entries.getJSONObject(i);
        JSONObject title = entry.getJSONObject("title");
        String address = title.getString("$t");
        String recordId = getRecordId(address);
        JSONObject content = entry.getJSONObject("content");
        String value = content.getString("$t");

        if (isAccountColumn(address)) {
          accountMap.put(recordId, value);
        } else if (isVentilationColumn(address)) {
          ventMap.put(recordId, value);
        }
      }
    } catch (JSONException e) {
      logger.error("Failed to parse JSON: " + json);
      return null;
    }

    ImmutableMap.Builder<String, List<VentilationModule>> builder = ImmutableMap.builder();

    for (String recordId : accountMap.keySet()) {
      String account = accountMap.get(recordId);
      String ventIds = ventMap.get(recordId);
      if (ventIds == null || ventIds.length() == 0) {
        builder.put(account, ImmutableList.<VentilationModule>of());
      } else {
        StringTokenizer st = new StringTokenizer(ventIds, ",");

        ImmutableList.Builder<VentilationModule> listBuilder = ImmutableList.builder();
        while (st.hasMoreTokens()) {
          listBuilder.add(new VentilationModule(st.nextToken().trim()));
        }

        builder.put(account, listBuilder.build());
      }
    }

    return builder.build();
  }

  private boolean isAccountColumn(String str) {
    return str.charAt(0) == 'A';
  }

  private boolean isVentilationColumn(String str) {
    return str.charAt(0) == 'B';
  }

  private String getRecordId(String str) {
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      if (c < '0' || c > '9') continue;
      return str.substring(i);
    }

    throw new IllegalArgumentException(
        "Doesn't seem to be a cell address in Google Spreadsheet: " + str);
  }
}
