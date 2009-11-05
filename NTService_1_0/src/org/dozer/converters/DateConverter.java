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
package org.dozer.converters;

import java.lang.reflect.Constructor;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.sql.Timestamp;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.beanutils.Converter;

/**
 * Internal convertor for handling Date/Time conversions.
 *
 * Supported source data types include java.util.Date,
 * java.sql.Date, java.sql.Time, java.sql.Timestamp, java.util.Calendar, javax.xml.datatype.XMLGregorianCalendar,
 * java.lang.String and any objects that return a number of milliseconds applicable to java.lang.Long
 * format in their toString() form.
 *
 * Supported return data types are all Date/Time types, which are based on a timestamp constructor
 * (e.g. new MyDate(new Long(1))). Calendar return type is also supported.
 *
 * Only intended for internal use.
 * 
 * @author tierney.matt
 * @author dmitry.buzdin
 */
public class DateConverter implements Converter {

  private DateFormat dateFormat;

  public DateConverter(DateFormat dateFormat) {
    this.dateFormat = dateFormat;
  }

  public Object convert(Class destClass, Object srcObj) {
    final Class srcFieldClass = srcObj.getClass();

    long time;
    int nanos = 0;
    if (Calendar.class.isAssignableFrom(srcFieldClass)) {
      Calendar inVal = (Calendar) srcObj;
      time = inVal.getTime().getTime();
    } else if (Timestamp.class.isAssignableFrom(srcFieldClass)) {
      Timestamp timestamp = (Timestamp) srcObj;
      time = timestamp.getTime();
      nanos = timestamp.getNanos();
    } else if (java.util.Date.class.isAssignableFrom(srcFieldClass)) {
      time = ((java.util.Date) srcObj).getTime();
    } else if (XMLGregorianCalendar.class.isAssignableFrom(srcFieldClass)) {
      time = ((XMLGregorianCalendar) srcObj).toGregorianCalendar().getTimeInMillis();
    } else if (dateFormat != null && String.class.isAssignableFrom(srcObj.getClass())) {
      try {
        if ("".equals(srcObj)) {
          return null;
        }
        time = dateFormat.parse((String) srcObj).getTime();
      } catch (ParseException e) {
        throw new ConversionException("Unable to parse source object using specified date format", e);
      }
      // Default conversion
    } else {
      try {
        time = Long.parseLong(srcObj.toString());
      } catch (NumberFormatException e) {
        throw new ConversionException("Unable to determine time in millis of source object", e);
      }
    }

    try {
      if (Calendar.class.isAssignableFrom(destClass)) {
        Constructor constructor = destClass.getConstructor();
        Calendar result = (Calendar) constructor.newInstance();
        result.setTimeInMillis(time);
        return result;
      }
      Constructor constructor = destClass.getConstructor(Long.TYPE);
      Object result = constructor.newInstance(time);
      if (nanos != 0 && (Timestamp.class.isAssignableFrom(destClass))) {
        ((Timestamp) result).setNanos(nanos);
      }
      return result;
    } catch (Exception e) {
      throw new ConversionException(e);
    }
  }

}
