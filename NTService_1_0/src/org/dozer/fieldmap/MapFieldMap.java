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
package org.dozer.fieldmap;

import org.dozer.classmap.ClassMap;
import org.dozer.factory.DestBeanCreator;
import org.dozer.propertydescriptor.DozerPropertyDescriptor;
import org.dozer.propertydescriptor.FieldPropertyDescriptor;
import org.dozer.propertydescriptor.JavaBeanPropertyDescriptor;
import org.dozer.propertydescriptor.MapPropertyDescriptor;
import org.dozer.util.DozerConstants;
import org.dozer.util.MappingUtils;

/**
 * Only intended for internal use. Handles field mapping involving Map Backed properties. Map backed property support
 * includes top level class Map data type, field level Map data type, and custom Map backed objects that provide custom
 * map-get/set methods.
 * 
 * @author garsombke.franz
 * @author sullins.ben
 * @author tierney.matt
 * 
 */
public class MapFieldMap extends FieldMap {

  public MapFieldMap(ClassMap classMap) {
    super(classMap);
  }

  public MapFieldMap(FieldMap fieldMap) {
    //Create from existing field map
    super(fieldMap.getClassMap());
    setCopyByReference(fieldMap.isCopyByReference());
    setCustomConverter(fieldMap.getCustomConverter());
    setCustomConverterId(fieldMap.getCustomConverterId());
    setDestField(fieldMap.getDestField());
    setDestHintContainer(fieldMap.getDestHintContainer());
    setDestDeepIndexHintContainer(fieldMap.getDestDeepIndexHintContainer());
    setMapId(fieldMap.getMapId());
    setRelationshipType(fieldMap.getRelationshipType());
    setRemoveOrphans(fieldMap.isRemoveOrphans());
    setSrcField(fieldMap.getSrcField());
    setSrcHintContainer(fieldMap.getSrcHintContainer());
    setSrcDeepIndexHintContainer(fieldMap.getSrcDeepIndexHintContainer());
    setType(fieldMap.getType());
  }

  @Override
  public void writeDestValue(Object destObj, Object destFieldValue) {
    DozerPropertyDescriptor propDescriptor;
    Object targetObject = destObj;

    if (getDestFieldName().equals(DozerConstants.SELF_KEYWORD)
        || (destFieldValue != null && MappingUtils.isSupportedMap(destFieldValue.getClass()))) {
      // Destination value is already a Map, so just use normal
      propDescriptor = super.getDestPropertyDescriptor(destObj.getClass());
    } else {
      if (getDestFieldMapGetMethod() != null
          || MappingUtils.isSupportedMap(determineActualPropertyType(getDestFieldName(), isDestFieldIndexed(), getDestFieldIndex(),
              destObj, true))) {
        // Need to dig out actual destination Map object and use map property descriptor to set the value on that target object....
        PrepareTargetObjectResult result = prepareTargetObject(destObj);
        targetObject = result.targetObject;
        propDescriptor = result.propDescriptor;
      } else {
        propDescriptor = super.getDestPropertyDescriptor(destObj.getClass());
      }
    }

    propDescriptor.setPropertyValue(targetObject, destFieldValue, this);
  }

  @Override
  public Object getSrcFieldValue(Object srcObj) {
    DozerPropertyDescriptor propDescriptor;
    Object targetObject = srcObj;

    if (getSrcFieldName().equals(DozerConstants.SELF_KEYWORD)) {
      propDescriptor = super.getSrcPropertyDescriptor(srcObj.getClass());
    } else {
      Class<?> actualType = determineActualPropertyType(getSrcFieldName(), isSrcFieldIndexed(), getSrcFieldIndex(), srcObj, false);
      if ((getSrcFieldMapGetMethod() != null)
          || (this.getMapId() == null && MappingUtils.isSupportedMap(actualType) && getSrcHintContainer() == null)) {
        // Need to dig out actual map object by using getter on the field. Use actual map object to get the field value
        targetObject = super.getSrcFieldValue(srcObj);

        String setMethod = MappingUtils.isSupportedMap(actualType) ? "put" : getSrcFieldMapSetMethod();
        String getMethod = MappingUtils.isSupportedMap(actualType) ? "get" : getSrcFieldMapGetMethod();
        String key = getSrcFieldKey() != null ? getSrcFieldKey() : getDestFieldName();

        propDescriptor = new MapPropertyDescriptor(actualType, getSrcFieldName(), isSrcFieldIndexed(), getDestFieldIndex(),
                setMethod, getMethod, key, getSrcDeepIndexHintContainer(), getDestDeepIndexHintContainer());
      } else {
        propDescriptor = super.getSrcPropertyDescriptor(srcObj.getClass());
      }
    }

    Object result = null;
    if (targetObject != null) {
      result = propDescriptor.getPropertyValue(targetObject);
    }

    return result;

  }

  private PrepareTargetObjectResult prepareTargetObject(Object destObj) {
    //  Need to dig out actual destination Map object and use map property descriptor to set the value on that target object....
    DozerPropertyDescriptor pd;
    if (isDestFieldAccessible()) {
      pd = new FieldPropertyDescriptor(destObj.getClass(), getDestFieldName(), isDestFieldIndexed(), getDestFieldIndex(),
          getSrcDeepIndexHintContainer(), getDestDeepIndexHintContainer());
    } else {
      pd = new JavaBeanPropertyDescriptor(destObj.getClass(), getDestFieldName(), isDestFieldIndexed(), getDestFieldIndex(),
          getSrcDeepIndexHintContainer(), getDestDeepIndexHintContainer());
    }

    Class<?> c = pd.getPropertyType();
    Object targetObject = pd.getPropertyValue(destObj);
    if (targetObject == null) {
      // Create new instance of target object that will be populated.
      if (getDestHintContainer() != null) {
        if (MappingUtils.isSupportedMap(c)) {
          if (MappingUtils.isSupportedMap(getDestHintContainer().getHint())) {
            c = getDestHintContainer().getHint();
          }
        } else {
          c = getDestHintContainer().getHint();
        }

      }

      //TODO: add support for custom factory/create method in conjunction with Map backed properties
      targetObject = DestBeanCreator.create(c, destObj.getClass());
      pd.setPropertyValue(destObj, targetObject, this);
    }

    return new PrepareTargetObjectResult(targetObject, new MapPropertyDescriptor(c, getDestFieldName(), isDestFieldIndexed(),
        getDestFieldIndex(), MappingUtils.isSupportedMap(c) ? "put" : getDestFieldMapSetMethod(),
        MappingUtils.isSupportedMap(c) ? "get" : getDestFieldMapGetMethod(), getDestFieldKey() != null ? getDestFieldKey()
            : getSrcFieldName(), getSrcDeepIndexHintContainer(), getDestDeepIndexHintContainer()));

  }

  private Class<?> determineActualPropertyType(String fieldName, boolean isIndexed, int index, Object targetObj, boolean isDestObj) {
    // Dig out actual Map object by calling getter on top level object    
    DozerPropertyDescriptor pd;
    if ((isDestObj && isDestFieldAccessible()) || (!isDestObj && isSrcFieldAccessible())) {
      pd = new FieldPropertyDescriptor(targetObj.getClass(), fieldName, isIndexed, index, getSrcDeepIndexHintContainer(),
          getDestDeepIndexHintContainer());
    } else {
      pd = new JavaBeanPropertyDescriptor(targetObj.getClass(), fieldName, isIndexed, index, getSrcDeepIndexHintContainer(),
          getDestDeepIndexHintContainer());
    }

    return pd.getPropertyType();
  }

  private static class PrepareTargetObjectResult {
    private Object targetObject;
    private MapPropertyDescriptor propDescriptor;
    public PrepareTargetObjectResult(Object targetObject, MapPropertyDescriptor propDescriptor) {
      this.targetObject = targetObject;
      this.propDescriptor = propDescriptor;
    }
  }

}
