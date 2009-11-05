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
package org.dozer.cache;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Internal class that represents one entry in the cache. Holds the cache value, unique key for lookup, and creation
 * time. Only intended for internal use.
 * 
 * @author tierney.matt
 * @author dmitry.buzdin
 */
public class CacheEntry <KeyType, ValueType> {

  private final KeyType key;
  private final ValueType value;

  public CacheEntry(KeyType key, ValueType value) {
    this.key = key;
    this.value = value;
  }

  public KeyType getKey() {
    return key;
  }

  public ValueType getValue() {
    return value;
  }

  @Override
  public int hashCode() {
    return key.hashCode();
  }

  @Override
  public boolean equals(Object object) {
    if ((this == object)) {
      return true;
    }
    if (!(object instanceof CacheEntry)) {
      return false;
    }
    CacheEntry entry = (CacheEntry) object;
    return this.getKey().equals(entry.getKey());
  }

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}
