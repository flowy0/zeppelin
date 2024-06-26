/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.zeppelin.helium;

import static org.apache.commons.text.StringEscapeUtils.escapeHtml4;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

class HeliumPackageTest {

  @Test
  void parseSpellPackageInfo() {
    String examplePackage = "{\n" +
        "  \"type\" : \"SPELL\",\n" +
        "  \"name\" : \"echo-spell\",\n" +
        "  \"description\" : \"'%echo' - return just what receive (example)\",\n" +
        "  \"artifact\" : \"./zeppelin-examples/zeppelin-example-spell-echo\",\n" +
        "  \"license\" : \"Apache-2.0\",\n" +
        "  \"icon\" : \"<i class='fa fa-repeat'></i>\",\n" +
        "  \"spell\": {\n" +
        "    \"magic\": \"%echo\",\n" +
        "    \"usage\": \"%echo <TEXT>\"\n" +
        "  }\n" +
        "}";

    HeliumPackage p = HeliumPackage.fromJson(examplePackage);
    assertEquals("%echo", p.getSpellInfo().getMagic());
    assertEquals(escapeHtml4("%echo <TEXT>"), p.getSpellInfo().getUsage());
  }

  @Test
  void parseConfig() {
    String examplePackage = "{\n" +
        "  \"type\" : \"SPELL\",\n" +
        "  \"name\" : \"translator-spell\",\n" +
        "  \"description\" : \"Translate langauges using Google API (examaple)\",\n" +
        "  \"artifact\" : \"./zeppelin-examples/zeppelin-example-spell-translator\",\n" +
        "  \"license\" : \"Apache-2.0\",\n" +
        "  \"icon\" : \"<i class='fa fa-globe '></i>\",\n" +
        "  \"config\": {\n" +
        "    \"access-token\": {\n" +
        "      \"type\": \"string\",\n" +
        "      \"description\": \"access token for Google Translation API\",\n" +
        "      \"defaultValue\": \"EXAMPLE-TOKEN\"\n" +
        "    }\n" +
        "  },\n" +
        "  \"spell\": {\n" +
        "    \"magic\": \"%translator\",\n" +
        "    \"usage\": \"%translator <source>-<target> <access-key> <TEXT>\"\n" +
        "  }\n" +
        "}";

    HeliumPackage p = HeliumPackage.fromJson(examplePackage);
    Map<String, Object> config = p.getConfig();
    Map<String, Object> accessToken = (Map<String, Object>) config.get("access-token");

    assertEquals("string", accessToken.get("type"));
    assertEquals("access token for Google Translation API", accessToken.get("description"));
    assertEquals("EXAMPLE-TOKEN", accessToken.get("defaultValue"));
  }
}
