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
import org.apache.log4j.Logger;

/**
 * @author Ken Kawamoto
 */
public class SeatingList {
  private static final Logger logger = Logger.getLogger(SeatingList.class.getName());

  private final String resourceUrl;
  private static final long UPDATE_INTERVAL_IN_MS = 5 * 60 * 1000;

  private Map<String, List<VentilationModule>> ventMap;
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

  private class JSONFormatException extends Exception {
    JSONFormatException(String message) {
      super(message);
    }
  }

  private void updateSeatingListIfNeeded() {
    if (ventMap != null &&
        lastUpdated + UPDATE_INTERVAL_IN_MS > new Date().getTime()) {
      return;
    }

    try {
      String json = loadJSON();
      VentilationMapBuilder builder = new VentilationMapBuilder(json);
      ventMap = builder.build();
      lastUpdated = new Date().getTime();
    } catch (IOException e) {
      logger.error("Failed to fetch JSON string from " + resourceUrl, e);
    }
  }

  public List<VentilationModule> getVentilationModules(String userName) {
    updateSeatingListIfNeeded();

    List<VentilationModule> vents = ventMap.get(userName);
    if (vents == null) {
      return ImmutableList.of();
    } else {
      return vents;
    }
  }

  public static void main(String[] args) {
  }

}
