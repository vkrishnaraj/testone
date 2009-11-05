/*
 * Copyright 2005-2010 the original author or authors.
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
package org.dozer.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Internal class used to perform various validations. Validates mapping requests, field mappings, URL's, etc. Only
 * intended for internal use.
 * 
 * @author tierney.matt
 * @author garsombke.franz
 */
public final class MappingValidator {

  private MappingValidator() {}

  public static void validateMappingRequest(Object srcObj) {
    if (srcObj == null) {
      MappingUtils.throwMappingException("source object must not be null");
    }
  }

  public static void validateMappingRequest(Object srcObj, Object destObj) {
    validateMappingRequest(srcObj);
    if (destObj == null) {
      MappingUtils.throwMappingException("destination object must not be null");
    }
  }

  public static void validateMappingRequest(Object srcObj, Class<?> destClass) {
    validateMappingRequest(srcObj);
    if (destClass == null) {
      MappingUtils.throwMappingException("destination class must not be null");
    }
  }

  public static URL validateURL(String fileName) {
    ResourceLoader loader = new ResourceLoader();
    URL url = loader.getResource(fileName);
    if (url == null) {
      MappingUtils.throwMappingException("Unable to locate dozer mapping file [" + fileName + "] in the classpath!!!");
    }

    InputStream stream = null;
    try {
      stream = url.openStream();
    } catch (IOException e) {
      MappingUtils.throwMappingException("Unable to open URL input stream for dozer mapping file [" + url + "]");
    } finally {
      if (stream != null) {
        try {
          stream.close();
        } catch (IOException e) {
          MappingUtils.throwMappingException("Unable to close input stream for dozer mapping file [" + url + "]");
        }
      }
    }

    return url;
  }
}
