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

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.google.common.collect.ImmutableList;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeatingList {
  private static final Logger LOGGER = LoggerFactory.getLogger(SeatingList.class);
  private static final String PROPERTIES_NAME = "SeatingList.properties";

  private final String peopleResourceUrl;
  private final String roomsResourceUrl;
  private static final long UPDATE_INTERVAL_IN_MS = 5 * 60 * 1000;

  private Map<String, List<String>> peopleMap;
  private Map<String, List<String>> roomsMap;
  private long lastUpdated = 0;

  public SeatingList() {
    InputStream is = this.getClass().getClassLoader().getResourceAsStream(PROPERTIES_NAME);
    Properties props = new Properties();
    try {
      props.load(is);
    } catch (IOException e) {
      throw new RuntimeException("Properties file doesn't seem correct: " + PROPERTIES_NAME);
    }
    peopleResourceUrl = props.getProperty("peopleMap");
    roomsResourceUrl = props.getProperty("roomsMap");

    if (!isValidUrl(peopleResourceUrl) || !isValidUrl(roomsResourceUrl)) {
      throw new UnsupportedOperationException("Unsupported protocol: "
           + peopleResourceUrl + ", or " + roomsResourceUrl);
    }
  }

  private boolean isValidUrl(String url) {
    return url.startsWith("http://") || url.startsWith("https://");
  }

  String loadJSON(String url) throws IOException {
    HttpClient client = new HttpClient();
    HttpMethod getMethod = new GetMethod(url);
    int statusCode = client.executeMethod(getMethod);
    if (statusCode != HttpStatus.SC_OK) {
      throw new IOException("Server returned " + statusCode);
    }

    return getMethod.getResponseBodyAsString();
  }

  private synchronized void updateSeatingListIfNeeded() {
    if (roomsMap != null &&
        lastUpdated + UPDATE_INTERVAL_IN_MS > new Date().getTime()) {
      return;
    }

    try {
      LOGGER.info("updating people mapping...");
      String json = loadJSON(peopleResourceUrl);
      VentilationMapBuilder builder = new VentilationMapBuilder(json);
      peopleMap = builder.build();

      LOGGER.info("updated people mapping");
    } catch (IOException e) {
      LOGGER.error("Failed to fetch JSON string from " + peopleResourceUrl, e);
    }

    try {
      LOGGER.info("updating rooms mapping...");
      String json = loadJSON(roomsResourceUrl);
      VentilationMapBuilder builder = new VentilationMapBuilder(json);
      roomsMap = builder.build();

      LOGGER.info("updated rooms mapping");
    } catch (IOException e) {
      LOGGER.error("Failed to fetch JSON string from " + roomsResourceUrl, e);
    }

    lastUpdated = new Date().getTime();
  }

  public List<String> getVentilationModules(String userName) {
    updateSeatingListIfNeeded();

    List<String> vents = peopleMap.get(userName);
    if (vents == null) {
      LOGGER.info("user \"" + userName + "\" not found. returning an empty list.");
      return ImmutableList.of();
    } else {
      LOGGER.info("user \"" + userName + "\" found. returning a list of "
          + vents.size() + " ventilation modules");
      return vents;
    }
  }

  public List<String> getRooms() {
    return ImmutableList.copyOf(roomsMap.keySet());
  }

  public List<String> getVentilationModulesIn(String roomName) {
    updateSeatingListIfNeeded();

    List<String> vents = roomsMap.get(roomName);
    if (vents == null) {
      LOGGER.info("room \"" + roomName + "\" not found. returning an empty list.");
      return ImmutableList.of();
    } else {
      LOGGER.info("room \"" + roomName + "\" found. returning a list of "
          + vents.size() + " ventilation modules");
      return vents;
    }
  }
}
