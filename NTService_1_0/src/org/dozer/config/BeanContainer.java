/*
 * Copyright 2005-2009 the original author or authors.
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
package org.dozer.config;

import org.dozer.util.DozerClassLoader;
import org.dozer.util.DozerProxyResolver;
import org.dozer.util.DefaultClassLoader;
import org.dozer.util.DefaultProxyResolver;

/**
 * @author dmitry.buzdin
 */
public class BeanContainer {

  private static final BeanContainer instance = new BeanContainer();

  public static BeanContainer getInstance() {
    return instance;
  }

  DozerClassLoader classLoader = new DefaultClassLoader();
  DozerProxyResolver proxyResolver = new DefaultProxyResolver();

  public DozerClassLoader getClassLoader() {
    return classLoader;
  }

  public void setClassLoader(DozerClassLoader classLoader) {
    this.classLoader = classLoader;
  }

  public DozerProxyResolver getProxyResolver() {
    return proxyResolver;
  }

  public void setProxyResolver(DozerProxyResolver proxyResolver) {
    this.proxyResolver = proxyResolver;
  }

}
