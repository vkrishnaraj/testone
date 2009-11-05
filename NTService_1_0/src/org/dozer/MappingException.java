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
package org.dozer;

/**
 * Runtime exception thrown by Dozer. RuntimeExceptions thrown from custom code called by Dozer during mapping (eg.
 * custom converters) are not wrapped with MappingException.
 * 
 * @author garsombke.franz
 * @author sullins.ben
 * @author tierney.matt
 * 
 */
public class MappingException extends RuntimeException {
  public MappingException(String arg0) {
    super(arg0);
  }

  public MappingException(String arg0, Throwable arg1) {
    super(arg0, arg1);
  }
  
  public MappingException(Throwable arg0) {
    super(arg0);
  }


}