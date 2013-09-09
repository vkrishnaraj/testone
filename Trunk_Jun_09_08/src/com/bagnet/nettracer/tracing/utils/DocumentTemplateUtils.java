package com.bagnet.nettracer.tracing.utils;

import java.util.Date;
import java.util.LinkedHashSet;

import org.springframework.beans.BeanUtils;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.templates.DocumentTemplate;
import com.bagnet.nettracer.tracing.db.templates.DocumentTemplateVar;
import com.bagnet.nettracer.tracing.forms.templates.DocumentTemplateForm;
import com.bagnet.nettracer.tracing.forms.templates.DocumentTemplateSearchForm;
import com.bagnet.nettracer.tracing.service.impl.DocumentTemplateSearchDTO;
/**
 * This class is used to provide basic tasks related to Document Template management 
 * (ie: converting between DocumentTemplate and DocmentTemplateForm, etc...).
 * 
 * @author Mike
 *
 */
public class DocumentTemplateUtils {
	
	public static void toForm(DocumentTemplate template, DocumentTemplateForm form) {
		if (template != null) {
			BeanUtils.copyProperties(template, form);
			form.setCommand(TracingConstants.COMMAND_UPDATE);
		} else {
			Date now = DateUtils.convertToGMTDate(new Date());
			form.setCreateDate(now);
			form.setLastUpdated(now);
			form.setId(0);
			form.setVariables(new LinkedHashSet<DocumentTemplateVar>());
			form.setActive(false);
			form.setName("");
			form.setDescription("");
			form.setData("");
			form.setCommand(TracingConstants.COMMAND_CREATE);
		}
	}
	
	public static DocumentTemplate fromForm(DocumentTemplateForm form) {
		DocumentTemplate template = new DocumentTemplate();
		template.setId(form.getId());
		template.setActive(form.isActive());
		template.setName(form.getName());
		template.setDescription(form.getDescription());
		template.setData(form.getData());
		return template;
	}
	
	public static DocumentTemplateSearchDTO fromForm(DocumentTemplateSearchForm dtsf) {
		DocumentTemplateSearchDTO dto = new DocumentTemplateSearchDTO();
		dto.setId(dtsf.getId());
		dto.setName(dtsf.getName());
		dto.setActive(dtsf.getActive());
		
		if (dtsf.getS_createtime() != null && !dtsf.getS_createtime().isEmpty()) {
			dto.setStartCreateDate(DateUtils.convertToGMTDate(dtsf.getS_createtime(), dtsf.get_DATEFORMAT()));
		}
		
		if (dtsf.getE_createtime() != null && !dtsf.getE_createtime().isEmpty()) {
			dto.setEndCreateDate(DateUtils.convertToGMTDate(dtsf.getE_createtime(), dtsf.get_DATEFORMAT()));
		}
		
		return dto;
	}
	
}
