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

import java.util.List;
import java.util.Map;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class VentilationMapBuilderTest {
  private static final String SAMPLE = "{\"version\":\"1.0\",\"encoding\":\"UTF-8\",\"feed\":{\"xmlns\":\"http://www.w3.org/2005/Atom\",\"xmlns$openSearch\":\"http://a9.com/-/spec/opensearchrss/1.0/\",\"xmlns$gs\":\"http://schemas.google.com/spreadsheets/2006\",\"xmlns$batch\":\"http://schemas.google.com/gdata/batch\",\"id\":{\"$t\":\"https://spreadsheets.google.com/feeds/cells/0ApJM4Av2i1wSdDlWNjM0YWlJdUI4SktZdUtFOFZscnc/od6/public/basic\"},\"updated\":{\"$t\":\"2012-04-18T05:21:07.991Z\"},\"category\":[{\"scheme\":\"http://schemas.google.com/spreadsheets/2006\",\"term\":\"http://schemas.google.com/spreadsheets/2006#cell\"}],\"title\":{\"type\":\"text\",\"$t\":\"Sheet1\"},\"link\":[{\"rel\":\"alternate\",\"type\":\"text/html\",\"href\":\"https://spreadsheets.google.com/pub?key\\u003d0ApJM4Av2i1wSdDlWNjM0YWlJdUI4SktZdUtFOFZscnc\"},{\"rel\":\"http://schemas.google.com/g/2005#feed\",\"type\":\"application/atom+xml\",\"href\":\"https://spreadsheets.google.com/feeds/cells/0ApJM4Av2i1wSdDlWNjM0YWlJdUI4SktZdUtFOFZscnc/od6/public/basic\"},{\"rel\":\"http://schemas.google.com/g/2005#batch\",\"type\":\"application/atom+xml\",\"href\":\"https://spreadsheets.google.com/feeds/cells/0ApJM4Av2i1wSdDlWNjM0YWlJdUI4SktZdUtFOFZscnc/od6/public/basic/batch\"},{\"rel\":\"self\",\"type\":\"application/atom+xml\",\"href\":\"https://spreadsheets.google.com/feeds/cells/0ApJM4Av2i1wSdDlWNjM0YWlJdUI4SktZdUtFOFZscnc/od6/public/basic?alt\\u003djson\"}],\"author\":[{\"name\":{\"$t\":\"kentaro.kawamoto\"},\"email\":{\"$t\":\"kentaro.kawamoto@gmail.com\"}}],\"openSearch$totalResults\":{\"$t\":\"4\"},\"openSearch$startIndex\":{\"$t\":\"1\"},\"entry\":[{\"id\":{\"$t\":\"https://spreadsheets.google.com/feeds/cells/0ApJM4Av2i1wSdDlWNjM0YWlJdUI4SktZdUtFOFZscnc/od6/public/basic/R1C1\"},\"updated\":{\"$t\":\"2012-04-18T05:21:07.991Z\"},\"category\":[{\"scheme\":\"http://schemas.google.com/spreadsheets/2006\",\"term\":\"http://schemas.google.com/spreadsheets/2006#cell\"}],\"title\":{\"type\":\"text\",\"$t\":\"A1\"},\"content\":{\"type\":\"text\",\"$t\":\"kentaro\"},\"link\":[{\"rel\":\"self\",\"type\":\"application/atom+xml\",\"href\":\"https://spreadsheets.google.com/feeds/cells/0ApJM4Av2i1wSdDlWNjM0YWlJdUI4SktZdUtFOFZscnc/od6/public/basic/R1C1\"}]},{\"id\":{\"$t\":\"https://spreadsheets.google.com/feeds/cells/0ApJM4Av2i1wSdDlWNjM0YWlJdUI4SktZdUtFOFZscnc/od6/public/basic/R1C2\"},\"updated\":{\"$t\":\"2012-04-18T05:21:07.991Z\"},\"category\":[{\"scheme\":\"http://schemas.google.com/spreadsheets/2006\",\"term\":\"http://schemas.google.com/spreadsheets/2006#cell\"}],\"title\":{\"type\":\"text\",\"$t\":\"B1\"},\"content\":{\"type\":\"text\",\"$t\":\"E-13, E-14\"},\"link\":[{\"rel\":\"self\",\"type\":\"application/atom+xml\",\"href\":\"https://spreadsheets.google.com/feeds/cells/0ApJM4Av2i1wSdDlWNjM0YWlJdUI4SktZdUtFOFZscnc/od6/public/basic/R1C2\"}]},{\"id\":{\"$t\":\"https://spreadsheets.google.com/feeds/cells/0ApJM4Av2i1wSdDlWNjM0YWlJdUI4SktZdUtFOFZscnc/od6/public/basic/R2C1\"},\"updated\":{\"$t\":\"2012-04-18T05:21:07.991Z\"},\"category\":[{\"scheme\":\"http://schemas.google.com/spreadsheets/2006\",\"term\":\"http://schemas.google.com/spreadsheets/2006#cell\"}],\"title\":{\"type\":\"text\",\"$t\":\"A2\"},\"content\":{\"type\":\"text\",\"$t\":\"yusukey\"},\"link\":[{\"rel\":\"self\",\"type\":\"application/atom+xml\",\"href\":\"https://spreadsheets.google.com/feeds/cells/0ApJM4Av2i1wSdDlWNjM0YWlJdUI4SktZdUtFOFZscnc/od6/public/basic/R2C1\"}]},{\"id\":{\"$t\":\"https://spreadsheets.google.com/feeds/cells/0ApJM4Av2i1wSdDlWNjM0YWlJdUI4SktZdUtFOFZscnc/od6/public/basic/R2C2\"},\"updated\":{\"$t\":\"2012-04-18T05:21:07.991Z\"},\"category\":[{\"scheme\":\"http://schemas.google.com/spreadsheets/2006\",\"term\":\"http://schemas.google.com/spreadsheets/2006#cell\"}],\"title\":{\"type\":\"text\",\"$t\":\"B2\"},\"content\":{\"type\":\"text\",\"$t\":\"E-01\"},\"link\":[{\"rel\":\"self\",\"type\":\"application/atom+xml\",\"href\":\"https://spreadsheets.google.com/feeds/cells/0ApJM4Av2i1wSdDlWNjM0YWlJdUI4SktZdUtFOFZscnc/od6/public/basic/R2C2\"}]}]}}";

  @Test
  public void testBuild() {
    VentilationMapBuilder builder = new VentilationMapBuilder(SAMPLE);
    Map<String, List<String>> map = builder.build();
    assertNotNull(map);

    List<String> vents0 = map.get("kentaro");
    assertNotNull(vents0);
    assertEquals(2, vents0.size());
    assertEquals("E-13", vents0.get(0));
    assertEquals("E-14", vents0.get(1));

    List<String> vents1 = map.get("yusukey");
    assertNotNull(vents1);
    assertEquals(1, vents1.size());
    assertEquals("E-01", vents1.get(0));
  }
}
