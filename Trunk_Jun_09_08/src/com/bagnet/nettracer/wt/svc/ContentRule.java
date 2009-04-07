package com.bagnet.nettracer.wt.svc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.wt.WorldTracerException;

public class ContentRule extends BasicRule {
    
	

    private int contentLines = 2;
    private static final int CATEGORY_LENGTH = 12;
    private static final int CONTENT_LENGTH = 43;
    private static final Format CONTENT_FORMAT = Format.CONTENT_FIELD;
    private static final int MAX_CATEGORIES = 10;
    
    public ContentRule() {
        super();
    }

    public ContentRule(int minLength, int maxLength, int maxAllowed, Format format) {
        super(minLength, maxLength, maxAllowed, format);
        

    }
    
    public ContentRule(int minLength, int maxLength, int maxAllowed, Format format, int contentLines) {
        super(minLength, maxLength, maxAllowed, format);
        this.contentLines = contentLines;
    }


    private static String formatField(String field, int maxLength, String replaceChar) {
        if(field == null) return null;
        
        //replace bad characters
        String result = field.replaceAll(CONTENT_FORMAT.replaceChars(), replaceChar);
        
        //replace space groups with one space
        result = result.trim().replaceAll("\\s+", " ");

        if(result.length() < 1) {
            return null;
        }
        if(result.length() > maxLength) {
            return result.substring(0, maxLength);
        }
        return result;
    }
    
    private static String formatFieldContents(String field, int maxLength, String replaceChar) {
        if(field == null) return null;
        
        String temp = null;
        String temp1 = null;
        String temp2 = null;
        
        //replace bad characters
        temp = field.replaceAll(CONTENT_FORMAT.replaceChars(), replaceChar);
        
        //replace space groups with one space
        temp = temp.trim().replaceAll("\\s+", " ");

        if(temp.length() < 1) {
            return null;
        }
        if(temp.length() > maxLength) {
            temp1 = temp.substring(0, maxLength);
            temp2 = temp.substring(maxLength);
            if(temp2.length() > maxLength) {
                temp2 = temp2.substring(0, maxLength);
            }
            return temp1 + DefaultWorldTracerService.FIELD_SEP + DefaultWorldTracerService.CONTINUATION + " " + DefaultWorldTracerService.ENTRY_SEP + temp2;
        }
        else {
            return temp;
        }
        
    }
    
    
    public static String buildEntry(Map<String, List<String>> categories, int bagNum) {
        List<String> temp1 = new ArrayList<String>();
        for(Map.Entry<String, List<String>>    cat : categories.entrySet()) {
            String category = formatField(cat.getKey(), CATEGORY_LENGTH, "");
            String contents = formatFieldContents(StringUtils.join(cat.getValue(), DefaultWorldTracerService.ENTRY_SEP), CONTENT_LENGTH, " ");
            if(category != null && contents != null) {
                temp1.add(String.format(category + "/" + contents));
                if(temp1.size() >= MAX_CATEGORIES) break;
            }
        }
        if(temp1.size() > 0) {
            if(bagNum > 0) {
                return String.format("%02d %s", bagNum, StringUtils.join(temp1, DefaultWorldTracerService.FIELD_SEP + DefaultWorldTracerService.CONTINUATION + " "));    
            }
            else {
                return StringUtils.join(temp1, DefaultWorldTracerService.FIELD_SEP + DefaultWorldTracerService.CONTINUATION + " ");    
            }
            
        }
        return null;
    }
    
    @Override
    public String formatEntry(String entry) throws WorldTracerException {
        if(entry == null) return null;
        
        String result = entry.trim().replaceAll("\\s+", " ");

        if(result.length() < this.getMinLength()) {
            throw new WorldTracerException("Field entry too short");
        }
        return result;
    }
}

