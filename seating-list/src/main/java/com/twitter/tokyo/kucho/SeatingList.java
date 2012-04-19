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
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeatingList {
  private static final Logger logger = LoggerFactory.getLogger(SeatingList.class);

  private final String resourceUrl;
  private static final long UPDATE_INTERVAL_IN_MS = 5 * 60 * 1000;

  private Map<String, List<String>> ventMap;
  private long lastUpdated = 0;

  // TODO: use Guice
  public SeatingList(String resourceUrl) {
    this.resourceUrl = resourceUrl;
  }

  String loadJSON() throws IOException {
    if (!resourceUrl.startsWith("http://") && !resourceUrl.startsWith("https://")) {
      throw new UnsupportedOperationException("Unsupported protocol: " + resourceUrl);
    }

    HttpClient client = new HttpClient();
    HttpMethod getMethod = new GetMethod(resourceUrl);
    int statusCode = client.executeMethod(getMethod);
    if (statusCode != HttpStatus.SC_OK) {
      throw new IOException("Server returned " + statusCode);
    }

    return getMethod.getResponseBodyAsString();
  }

  private void updateSeatingListIfNeeded() {
    if (ventMap != null &&
        lastUpdated + UPDATE_INTERVAL_IN_MS > new Date().getTime()) {
      return;
    }

    logger.info("updating seat-ventilation mapping...");
    try {
      String json = loadJSON();
      VentilationMapBuilder builder = new VentilationMapBuilder(json);
      ventMap = builder.build();
      lastUpdated = new Date().getTime();

      logger.info("updated seat-ventilation mapping");
    } catch (IOException e) {
      logger.error("Failed to fetch JSON string from " + resourceUrl, e);
    }
  }

  public List<String> getVentilationModules(String userName) {
    updateSeatingListIfNeeded();

    List<String> vents = ventMap.get(userName);
    if (vents == null) {
      logger.info("user \"" + userName + "\" not found. returning an empty list.");
      return ImmutableList.of();
    } else {
      logger.info("user \"" + userName + "\" found. returning a list of "
          + vents.size() + " ventilation modules");
      return vents;
    }
  }

}
