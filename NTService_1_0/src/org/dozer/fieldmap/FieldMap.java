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

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.classmap.ClassMap;
import org.dozer.classmap.RelationshipType;
import org.dozer.propertydescriptor.DozerPropertyDescriptor;
import org.dozer.propertydescriptor.GetterSetterPropertyDescriptor;
import org.dozer.propertydescriptor.PropertyDescriptorFactory;
import org.dozer.util.DozerConstants;
import org.dozer.util.MappingUtils;

/**
 * Internal class that represents a field mapping definition. Holds all of the information about a single field mapping
 * definition. Only intended for internal use.
 * 
 * @author garsombke.franz
 * @author sullins.ben
 * @author tierney.matt
 * @author johnsen.knut-erik
 * 
 */
public abstract class FieldMap implements Cloneable {
  
  private static final Log log = LogFactory.getLog(FieldMap.class);

  private ClassMap classMap;
  private DozerField srcField;
  private DozerField destField;
  private HintContainer srcHintContainer;
  private HintContainer destHintContainer;
  private HintContainer srcDeepIndexHintContainer;
  private HintContainer destDeepIndexHintContainer;
  private String type;
  private boolean copyByReference;
  private boolean copyByReferenceOveridden;
  private String mapId;
  private String customConverter;
  private String customConverterId;
  private String customConverterParam;
  private RelationshipType relationshipType;
  private boolean removeOrphans;

  private final Map<Class<?>, DozerPropertyDescriptor> srcPropertyDescriptorMap = new HashMap<Class<?>, DozerPropertyDescriptor>(); // For Caching Purposes
  private final Map<Class<?>, DozerPropertyDescriptor> destPropertyDescriptorMap = new HashMap<Class<?>, DozerPropertyDescriptor>();

  public FieldMap(ClassMap classMap) {
    this.classMap = classMap;
  }

  public ClassMap getClassMap() {
    return classMap;
  }

  public void setClassMap(ClassMap classMap) {
    this.classMap = classMap;
  }

  public Object getSrcFieldValue(Object runtimeSrcObj) {
    return getSrcPropertyDescriptor(runtimeSrcObj.getClass()).getPropertyValue(runtimeSrcObj);
  }

  public void writeDestValue(Object runtimeDestObj, Object destFieldValue) {
    if (log.isDebugEnabled()) {
      log.debug("Getting ready to invoke write method on the destination object.  Dest Obj: "
          + MappingUtils.getClassNameWithoutPackage(runtimeDestObj.getClass()) + ", Dest value: " + destFieldValue);
    }
    DozerPropertyDescriptor propDescriptor = getDestPropertyDescriptor(runtimeDestObj.getClass());
    propDescriptor.setPropertyValue(runtimeDestObj, destFieldValue, this);
  }

  public Class<?> getDestHintType(Class<?> runtimeSrcClass) {
    if (getDestHintContainer() != null) {
      if (getSrcHintContainer() != null) {
        return getDestHintContainer().getHint(runtimeSrcClass, getSrcHintContainer().getHints());
      } else {
        return getDestHintContainer().getHint();
      }
    } else {
      return runtimeSrcClass;
    }
  }

  public Class<?> getDestFieldType(Class<?> runtimeDestClass) {
    Class<?> result = null;
    if (isDestFieldIndexed()) {
      result = destHintContainer != null ? destHintContainer.getHint() : null;
    }
    if (result == null) {
      result = getDestPropertyDescriptor(runtimeDestClass).getPropertyType();
    }
    return result;
  }

  public Class<?> getSrcFieldType(Class<?> runtimeSrcClass) {
    return getSrcPropertyDescriptor(runtimeSrcClass).getPropertyType();
  }

  /**
   * @deprecated As of 3.2 release
   */
  @Deprecated
  public Method getDestFieldWriteMethod(Class<?> runtimeDestClass) {
    // 4-07 mht: The getWriteMethod was removed from the prop descriptor interface. This was done as part of
    // refactoring effort to clean up the prop descriptor stuff. The underlying write method should not be exposed.
    // For now, just explicitly cast to the only prop descriptor(getter/setter) that could have been used in this
    // context. The other types of prop descriptors would have failed.
    DozerPropertyDescriptor dpd = getDestPropertyDescriptor(runtimeDestClass);
    Method result = null;
    try {
      result = ((GetterSetterPropertyDescriptor) dpd).getWriteMethod();
    } catch (Exception e) {
      MappingUtils.throwMappingException(e);
    }
    return result;
  }

  public Object getDestValue(Object runtimeDestObj) {
    return getDestPropertyDescriptor(runtimeDestObj.getClass()).getPropertyValue(runtimeDestObj);
  }

  public HintContainer getDestHintContainer() {
    return destHintContainer;
  }

  public void setDestHintContainer(HintContainer destHint) {
    this.destHintContainer = destHint;
  }

  public HintContainer getSrcHintContainer() {
    return srcHintContainer;
  }

  public void setSrcHintContainer(HintContainer sourceHint) {
    this.srcHintContainer = sourceHint;
  }

  public String getSrcFieldMapGetMethod() {
    return !MappingUtils.isBlankOrNull(srcField.getMapGetMethod()) ? srcField.getMapGetMethod() : classMap
        .getSrcClassMapGetMethod();
  }

  public String getSrcFieldMapSetMethod() {
    return !MappingUtils.isBlankOrNull(srcField.getMapSetMethod()) ? srcField.getMapSetMethod() : classMap
        .getSrcClassMapSetMethod();
  }

  public String getDestFieldMapGetMethod() {
    return !MappingUtils.isBlankOrNull(destField.getMapGetMethod()) ? destField.getMapGetMethod() : classMap
        .getDestClassMapGetMethod();
  }

  public String getDestFieldMapSetMethod() {
    return !MappingUtils.isBlankOrNull(destField.getMapSetMethod()) ? destField.getMapSetMethod() : classMap
        .getDestClassMapSetMethod();
  }

  public String getSrcFieldName() {
    return srcField.getName();
  }

  public String getDestFieldName() {
    return destField.getName();
  }

  public String getDestFieldType() {
    return destField.getType();
  }

  public String getSrcFieldType() {
    return srcField.getType();
  }

  public String getSrcFieldDateFormat() {
    return getDateFormat();
  }

  public String getDestFieldDateFormat() {
    return getDateFormat();
  }

  public String getDateFormat() {
    if (!MappingUtils.isBlankOrNull(destField.getDateFormat())) {
      return destField.getDateFormat();
    } else if (!MappingUtils.isBlankOrNull(srcField.getDateFormat())) {
      return srcField.getDateFormat();
    } else {
      return classMap.getDateFormat();
    }
  }

  public String getDestFieldCreateMethod() {
    return destField.getCreateMethod();
  }

  public String getSrcFieldCreateMethod() {
    return srcField.getCreateMethod();
  }

  public boolean isDestFieldIndexed() {
    return destField.isIndexed();
  }

  public boolean isSrcFieldIndexed() {
    return srcField.isIndexed();
  }

  public int getSrcFieldIndex() {
    return srcField.getIndex();
  }

  public int getDestFieldIndex() {
    return destField.getIndex();
  }

  public String getSrcFieldTheGetMethod() {
    return srcField.getTheGetMethod();
  }

  public String getDestFieldTheGetMethod() {
    return destField.getTheGetMethod();
  }

  public String getSrcFieldTheSetMethod() {
    return srcField.getTheSetMethod();
  }

  public String getDestFieldTheSetMethod() {
    return destField.getTheSetMethod();
  }

  public String getSrcFieldKey() {
    return srcField.getKey();
  }

  public String getDestFieldKey() {
    return destField.getKey();
  }

  public boolean isDestFieldAccessible() {
    return destField.isAccessible();
  }

  public boolean isSrcFieldAccessible() {
    return srcField.isAccessible();
  }

  public void setSrcField(DozerField sourceField) {
    this.srcField = sourceField;
  }

  public void setDestField(DozerField destField) {
    this.destField = destField;
  }

  public HintContainer getDestDeepIndexHintContainer() {
    return destDeepIndexHintContainer;
  }

  public void setDestDeepIndexHintContainer(HintContainer destDeepIndexHintHint) {
    this.destDeepIndexHintContainer = destDeepIndexHintHint;
  }

  public HintContainer getSrcDeepIndexHintContainer() {
    return srcDeepIndexHintContainer;
  }

  public void setSrcDeepIndexHintContainer(HintContainer srcDeepIndexHint) {
    this.srcDeepIndexHintContainer = srcDeepIndexHint;
  }

  @Override
  public Object clone() {
    Object result = null;
    try {
      result = super.clone();
    } catch (CloneNotSupportedException e) {
      MappingUtils.throwMappingException(e);
    }
    return result;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public boolean isCopyByReference() {
    return copyByReference;
  }

  public void setCopyByReference(boolean copyByReference) {
    this.copyByReference = copyByReference;
    this.copyByReferenceOveridden = true;
  }

  /**
   * Return true if is self referencing. Is considered self referencing where no other sources are specified, i.e., no
   * source properties or #CDATA in the xml def.
   */
  protected boolean isSrcSelfReferencing() {
    return getSrcFieldName().equals(DozerConstants.SELF_KEYWORD);
  }

  protected boolean isDestSelfReferencing() {
    return getDestFieldName().equals(DozerConstants.SELF_KEYWORD);
  }

  public boolean isCopyByReferenceOveridden() {
    return copyByReferenceOveridden;
  }

  public String getMapId() {
    return mapId;
  }

  public void setMapId(String mapId) {
    this.mapId = mapId;
  }

  public String getCustomConverter() {
    return customConverter;
  }

  public void setCustomConverter(String customConverter) {
    this.customConverter = customConverter;
  }

  public RelationshipType getRelationshipType() {
    return relationshipType != null ? relationshipType : classMap.getRelationshipType();
  }

  public void setRelationshipType(RelationshipType relationshipType) {
    this.relationshipType = relationshipType;
  }

  public void validate() {
    if (srcField == null) {
      MappingUtils.throwMappingException("src field must be specified");
    }
    if (destField == null) {
      MappingUtils.throwMappingException("dest field must be specified");
    }
  }

  protected DozerPropertyDescriptor getSrcPropertyDescriptor(Class<?> runtimeSrcClass) {
    DozerPropertyDescriptor result = this.srcPropertyDescriptorMap.get(runtimeSrcClass);
    if (result == null) {
      String srcFieldMapGetMethod = getSrcFieldMapGetMethod();
      String srcFieldMapSetMethod = getSrcFieldMapSetMethod();
      DozerPropertyDescriptor descriptor = PropertyDescriptorFactory.getPropertyDescriptor(runtimeSrcClass,
              getSrcFieldTheGetMethod(), getSrcFieldTheSetMethod(),
              srcFieldMapGetMethod, srcFieldMapSetMethod, isSrcFieldAccessible(), isSrcFieldIndexed(), getSrcFieldIndex(),
              getSrcFieldName(), getSrcFieldKey(), isSrcSelfReferencing(), getDestFieldName(), getSrcDeepIndexHintContainer(),
              getDestDeepIndexHintContainer(), classMap.getSrcClassBeanFactory());
      this.srcPropertyDescriptorMap.put(runtimeSrcClass, descriptor);
      result = descriptor;
    }
    return result;
  }

  protected DozerPropertyDescriptor getDestPropertyDescriptor(Class<?> runtimeDestClass) {
    DozerPropertyDescriptor result = this.destPropertyDescriptorMap.get(runtimeDestClass);
    if (result == null) {
      DozerPropertyDescriptor descriptor = PropertyDescriptorFactory.getPropertyDescriptor(runtimeDestClass,
            getDestFieldTheGetMethod(), getDestFieldTheSetMethod(), getDestFieldMapGetMethod(),
            getDestFieldMapSetMethod(), isDestFieldAccessible(), isDestFieldIndexed(), getDestFieldIndex(),
            getDestFieldName(), getDestFieldKey(), isDestSelfReferencing(), getSrcFieldName(),
            getSrcDeepIndexHintContainer(), getDestDeepIndexHintContainer(), classMap.getDestClassBeanFactory());

      this.destPropertyDescriptorMap.put(runtimeDestClass, descriptor);
      result = descriptor;
    }
    return result;
  }

  public DozerField getSrcFieldCopy() {
    return srcField.copyOf();
  }

  public DozerField getDestFieldCopy() {
    return destField.copyOf();
  }

  protected DozerField getSrcField() {
    return srcField;
  }

  protected DozerField getDestField() {
    return destField;
  }

  public String getCustomConverterId() {
    return customConverterId;
  }

  public void setCustomConverterId(String customConverterId) {
    this.customConverterId = customConverterId;
  }

  public boolean isRemoveOrphans() {
    return removeOrphans;
  }

  public void setRemoveOrphans(boolean removeOrphans) {
    this.removeOrphans = removeOrphans;
  }

  public boolean isDestMapNull() {
    return classMap.isDestMapNull();
  }

  public boolean isDestMapEmptyString() {
    return classMap.isDestMapEmptyString();
  }

  public boolean isTrimStrings() {
    return classMap.isTrimStrings();
  }

  public boolean isStopOnErrors() {
    return classMap.isStopOnErrors();
  }

  public boolean isNonCumulativeRelationship() {
    return RelationshipType.NON_CUMULATIVE.equals(relationshipType);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("source field", srcField).append("destination field",
        destField).append("type", type).append("customConverter", customConverter).append("relationshipType", relationshipType)
        .append("removeOrphans", removeOrphans).append("mapId", mapId).append("copyByReference", copyByReference).append(
            "copyByReferenceOveridden", copyByReferenceOveridden).append("srcTypeHint", srcHintContainer).append("destTypeHint",
            destHintContainer).toString();
  }

  public String getCustomConverterParam() {
	  return customConverterParam;
  }

  public void setCustomConverterParam(String customConverterParam) {
	  this.customConverterParam = customConverterParam;
  }

}
