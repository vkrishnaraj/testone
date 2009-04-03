package com.bagnet.nettracer.wt.utils;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.EnumMap;

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
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;

public class ParsingUtils {

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
}
