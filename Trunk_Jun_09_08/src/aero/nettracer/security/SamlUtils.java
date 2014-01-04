package aero.nettracer.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.opensaml.DefaultBootstrap;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.BasicSAMLMessageContext;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.Status;
import org.opensaml.saml2.core.impl.ResponseImpl;
import org.opensaml.ws.transport.http.HttpServletRequestAdapter;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.parse.BasicParserPool;
import org.opensaml.xml.schema.impl.XSAnyImpl;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.signature.Signature;
import org.opensaml.saml2.binding.decoding.HTTPPostDecoder;


public class SamlUtils implements SsoUtils{
	
	private static Map<String, String> issuerMap = new LinkedHashMap<String, String>();
	private static Map<String, String> wnKeyMap = new LinkedHashMap<String, String>();
	
	static {
		issuerMap.put("swacorp.com", 		"WN");
	}
	
	static {
		wnKeyMap.put("username", 		"Username");
		wnKeyMap.put("group",	 		"SWA user groups");
		wnKeyMap.put("stationcode", 	"airport/station code");
		wnKeyMap.put("firstname", 		"first name");
		wnKeyMap.put("lastname", 		"last name");
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public SsoNode getSsoNode(HttpServletRequest request) {
		SsoNode node = new SsoNode();
		
		Map<String, Object> attr = getSamlAttributeMap(request);
		
		String companycode = mapIssuer((String)attr.get("issuerName"));
		node.setCompanycode(companycode);
		
		Map<String, String> keyMap = getKeyMap(companycode);
		
		if(keyMap != null){
			node.setUsername(((List<String>)attr.get(keyMap.get("username"))).get(0));
			node.setGroup(((List<String>)attr.get(keyMap.get("group"))).get(0));
			node.setStation(((List<String>)attr.get(keyMap.get("stationcode"))).get(0));
			node.setFirstname(((List<String>)attr.get(keyMap.get("firstname"))).get(0));
			node.setLastname(((List<String>)attr.get(keyMap.get("lastname"))).get(0));
			node.setValidAssertion(validAssertion((Map<String,Object>)attr.get("auth")));
		}
		return node;
	}
	
	private Map<String,String> getKeyMap(String companycode){
		if("WN".equals(companycode)){
			return wnKeyMap;
		} else {
			return null;
		}
		
	}
	
	protected String mapIssuer(String issuer){
		if(issuer != null && issuerMap.containsKey(issuer)){
			return issuerMap.get(issuer);
		}
		return null;
	}
	
	protected static boolean validAssertion(Map<String,Object> params){
		return true;
	}
	
	protected static Map<String, Object> getSamlAttributeMap(HttpServletRequest request){
		
		HashMap<String, Object> map = new HashMap<String, Object>();
			
		try{
	       DefaultBootstrap.bootstrap();

	       HTTPPostDecoder decode = new HTTPPostDecoder(
	               new BasicParserPool());
	       BasicSAMLMessageContext<ResponseImpl, ?, ?> messageContext = new BasicSAMLMessageContext<ResponseImpl, SAMLObject, SAMLObject>();
	       messageContext
	               .setInboundMessageTransport(new HttpServletRequestAdapter(
	                       request));
	       decode.decode(messageContext);
	       
	       XMLObject c = messageContext.getInboundMessage();
	       
	       ResponseImpl  message = messageContext.getInboundSAMLMessage();
	       
	       Issuer issuer = message.getIssuer();
	       map.put("issuerName", issuer.getValue());
	       
	       Signature signature = message.getSignature();
	       map.put("signatureAlgorithm", signature.getSignatureAlgorithm());
	       
	       KeyInfo keyInfo = signature.getKeyInfo();
	       map.put("X509Cert", keyInfo.getX509Datas().get(0).getX509Certificates().get(0).getDOM().getFirstChild().getTextContent());
	       
	       Credential signingCredential = signature.getSigningCredential();
	       
	       
	       Status status = message.getStatus();
	       
	       List<Assertion> assertions = message.getAssertions();
	       if(assertions.size() > 0){
	    	   //there should only be one assertion
	    	   Assertion assertion = assertions.get(0);
	    	   List<AttributeStatement> statements = assertion.getAttributeStatements();
	    	   if(statements != null){
	    		   for(AttributeStatement statement:statements){
	    			   List<Attribute> attributes = statement.getAttributes();
	    			   if(attributes != null){
	    				   for(Attribute attribute:attributes){
	    					   String key = attribute.getName();
	    					   ArrayList<String> values = new ArrayList<String>();
	    					   List<XMLObject> attributeValues = attribute.getAttributeValues();
	    					   if(attributeValues != null){
	    						   for(XMLObject attributeValue:attributeValues){
	    							   XSAnyImpl xs = (XSAnyImpl)attributeValue;
	    							   values.add(xs.getTextContent());
	    						   }
	    					   }
	    					   map.put(key, values);
	    				   }
	    			   }
	    		   }
	    	   }
	       }
	      
	       
	       
		}catch (Exception e){
			e.printStackTrace();
		}
		return map;
	}


	
}
