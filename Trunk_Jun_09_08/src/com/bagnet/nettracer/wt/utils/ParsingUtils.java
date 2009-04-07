package com.bagnet.nettracer.wt.utils;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.EnumMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasChildFilter;
import org.htmlparser.filters.RegexFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;
import com.bagnet.nettracer.wt.WorldTracerException;

public class ParsingUtils {

	private static final Pattern AHL_PATT = Pattern.compile("(?:\\bAHL\\s+|A/|FILE\\s+)(\\w{5}\\d{5})\\b", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	private static final Pattern OHD_PATT = Pattern.compile("(?:\\bOHD\\s+|O/|ON-HAND\\s+)(\\w{5}\\d{5})\\b", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	private static final Pattern PERCENT_PATT = Pattern.compile("SCORE\\s*-\\s*(\\d+(\\.\\d{1,2})?)");
	public static EnumMap<ActionFileType, int[]> parseActionFileCounts(InputStream inStream, String encoding) throws UnsupportedEncodingException, ParserException {

		Parser parser = new Parser(new Lexer(new Page(inStream, encoding)));
		EnumMap<ActionFileType, int[]> result = new EnumMap<ActionFileType, int[]>(ActionFileType.class);
		//we are looking for TableRow fields fields
		NodeFilter tagFilter = new TagNameFilter("tr");
		// it has to have a link on that row, otherwise all the entries are 0
		NodeFilter childFilter = new HasChildFilter(new TagNameFilter("a"), true);
		
		for(ActionFileType afType : ActionFileType.values()) {
			// that have an id attribute matching the current action file category
			NodeFilter attrFilter = new HasAttributeFilter("id", afType.htmlId());
			
			NodeFilter totalFilter = new AndFilter(new NodeFilter[] {tagFilter, attrFilter, childFilter});
			
			NodeList nodeList = parser.extractAllNodesThatMatch(totalFilter);
			
			if(nodeList.size() == 1) {
				
				System.out.println("found a node for " + afType.name());
				int[] counts = parseNode(nodeList.elementAt(0));
				result.put(afType, counts);
			}
			
			parser.reset();
			
		}
		
		
		return result;
	}
	
	private static int[] parseNode(Node trNode) {
		int[] result = new int[7];
		
		NodeFilter nf = new RegexFilter("(\\&nbsp;|\\d+)", RegexFilter.FIND);
		NodeList tmp = new NodeList();
		trNode.collectInto(tmp, nf);
		int i = 0;
		for(Node txtNode : tmp.toNodeArray()) {
			if("&nbsp;".equals(txtNode.getText().trim())) {
				result[i] = 0;
			}
			else {
				result[i] = Integer.parseInt(txtNode.getText().trim());
			}
			i++;
			if(i >= result.length) break;
		}
		return result;
	}

	public static String parseAhlId(String content) {
		Matcher m = AHL_PATT.matcher(content);
		if(m.find()) {
			return m.group(1);
		}
		return "";
	}

	public static String parseOhdId(String content) {
		Matcher m = OHD_PATT.matcher(content);
		if(m.find()) {
			return m.group(1);
		}
		return "";
	}

	public static String[] parseActionFileDetail(InputStream inStream,
			String encoding) throws Exception {
		Parser parser = new Parser(new Lexer(new Page(inStream, encoding)));
		NodeFilter inputTagFilter = new TagNameFilter("input");
		NodeFilter nameAttrFilter = new HasAttributeFilter("name", "detailedViewVO.searchMessageId");
		NodeFilter idFilter = new AndFilter(new NodeFilter[] {inputTagFilter, nameAttrFilter});
		NodeList idResult = parser.extractAllNodesThatMatch(idFilter);
		if(idResult.size() < 0) {
			throw new WorldTracerException("unable to parse message id in action file detail display");
		}
		String itemNum = ((TagNode)idResult.elementAt(0)).getAttribute("value");
		
		parser.reset();
		NodeFilter taTagFilter = new TagNameFilter("textarea");
		NodeFilter f2 = new HasAttributeFilter("id", "messageText");
		NodeFilter contentFilter = new AndFilter(new NodeFilter[] {taTagFilter, f2});
		NodeList contentResult = parser.extractAllNodesThatMatch(contentFilter);
		if(contentResult.size() < 0) {
			throw new WorldTracerException("unable to parse content in action file detail display");
		}
		String content = contentResult.elementAt(0).getChildren().elementAt(0).getText();
		
		return new String[] {itemNum, content};
	}

	public static Double parsePercentMAtch(String content) {
		Matcher m = PERCENT_PATT.matcher(content);
		if(m.find()) {
			return Double.parseDouble(m.group(1));
		}
		return 0.0D;
	}
}
