package aero.nettracer.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.opensaml.DefaultBootstrap;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.BasicSAMLMessageContext;
import org.opensaml.saml2.binding.decoding.HTTPPostDecoder;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.impl.ResponseImpl;
import org.opensaml.ws.transport.http.HttpServletRequestAdapter;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.parse.BasicParserPool;
import org.opensaml.xml.schema.impl.XSAnyImpl;
import org.opensaml.xml.security.x509.BasicX509Credential;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.SignatureValidator;
import org.opensaml.xml.validation.ValidationException;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;


public class SamlUtils implements SsoUtils{
	private static final Logger authenlog = Logger.getLogger("authentication");
	
	
	/**
	 * To identify Company Code, Saml will provide an issuer.  This map exists to map the provided issuer string to a Nettracer companycode (ex: swacorp.com -> WN)
	 */
	private static Map<String, String> issuerMap = new LinkedHashMap<String, String>();
	
	static {
		issuerMap.put("swacorp.com", 		"WN");
	}
	
	/**
	 * Since there is no standard attribute names in a Saml assertion, we need a map on a per company basis to normalize the attributes
	 */
	private static Map<String, String> wnKeyMap = new LinkedHashMap<String, String>();
	
	static {
		wnKeyMap.put("username", 		"Username");
		wnKeyMap.put("group",	 		"SWA user groups");
		wnKeyMap.put("stationcode", 	"airport/station code");
		wnKeyMap.put("firstname", 		"first name");
		wnKeyMap.put("lastname", 		"last name");
	}
	

	/* (non-Javadoc)
	 * @see aero.nettracer.security.SsoUtils#getSsoNode(javax.servlet.http.HttpServletRequest)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public SsoNode getSsoNode(HttpServletRequest request) {
		SsoNode node = new SsoNode();
		if(request.getParameter("SAMLResponse") != null){
			Map<String, Object> attr = getSamlAttributeMap(request);

			String companycode = mapIssuer((String)attr.get("issuerName"));
			node.setCompanycode(companycode);

			Map<String, String> keyMap = getKeyMap(companycode);
			
			boolean validAssertion = (Boolean)attr.get("signatureValidation");
			boolean validAttributes = true;

			
			if(keyMap != null){
				List<String> username = ((List<String>)attr.get(keyMap.get("username"))); 
				if(username != null && username.size() == 1){
					node.setUsername(username.get(0));
				} else {
					validAttributes = false;
					authenlog.debug("Incorrect number of username attribute values");
				}

				List<String> group = ((List<String>)attr.get(keyMap.get("group"))); 
				if(group != null){
					node.setOriginalGroup(StringUtils.join(group, ", "));
					if(group.size() == 1){
						node.setGroup(group.get(0));
					} else {
						validAttributes = false;
						authenlog.debug("Incorrect number of group attribute values");
					}
				}

				List<String> stationcode = ((List<String>)attr.get(keyMap.get("stationcode"))); 
				if(stationcode != null && stationcode.size() == 1){
					node.setStation(stationcode.get(0));
				} else {
					validAttributes = false;
					authenlog.debug("Incorrect number of stationcode attribute values");
				}
				
				List<String> firstname = ((List<String>)attr.get(keyMap.get("firstname"))); 
				if(firstname != null && firstname.size() == 1){
					node.setFirstname(firstname.get(0));
				} else {
					validAttributes = false;
					authenlog.debug("Incorrect number of firstname attribute values");
				}
				
				List<String> lastname = ((List<String>)attr.get(keyMap.get("lastname"))); 
				if(lastname != null && lastname.size() == 1){
					node.setLastname(lastname.get(0));
				} else {
					validAttributes = false;
					authenlog.debug("Incorrect number of lastname attribute values");
				}
			}
			node.setValidAssertion(validAssertion && validAttributes);
			authenlog.info(node.toString());
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
	
	
	/**
	 * Using OpenSaml framework to parse the Saml assertion.
	 * 
	 * @param request
	 * @return
	 */
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
	       
	       ResponseImpl  message = messageContext.getInboundSAMLMessage();
	       
	       map.put("signatureValidation", validate(message));
	       
	       Issuer issuer = message.getIssuer();
	       map.put("issuerName", issuer.getValue());
	       
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
			map.put("signatureValidation", false);
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * Currently the only implementation support for signature validation is X509 with no password provided.
	 * 
	 * Consider abstracting to support other authentication types in the future.
	 * 
	 * @param message
	 * @return
	 */
	public static Boolean validate(ResponseImpl  message){
		boolean valid = true;
		
		String fileLocation = PropertyBMO.getValue(PropertyBMO.SAML_X509_WN);//TODO consider abstracting
		if(fileLocation == null){
			return false;
		}
		
		File signatureVerificationPublicKeyFile = new File(fileLocation);

		try{          

			InputStream inputStream2 = new FileInputStream(signatureVerificationPublicKeyFile);
			CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
			X509Certificate certificate = (X509Certificate)certificateFactory.generateCertificate(inputStream2);
			inputStream2.close();

			//pull out the public key part of the certificate into a KeySpec
			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(certificate.getPublicKey().getEncoded());

			//get KeyFactory object that creates key objects, specifying RSA
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");

			//generate public key to validate signatures
			PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

			//create credentials
			BasicX509Credential publicCredential = new BasicX509Credential();

			//add public key value
			publicCredential.setPublicKey(publicKey);

			//create SignatureValidator
			SignatureValidator signatureValidator = new SignatureValidator(publicCredential);

			//get the signature to validate from the response object
			Signature signature = message.getSignature();

			//try to validate
			try {
				/** validate is a void method, if the signature is invalid, a validation exception is thrown **/
				signatureValidator.validate(signature);
				
				/** checking the condition to determine if the assertion was receive in the appropriate time window **/
				if(message.getAssertions() != null && message.getAssertions().size() == 1){//there should only ever be one assertion provided
					if(message.getAssertions().get(0).getConditions() != null){
						DateTime condition = message.getAssertions().get(0).getConditions().getNotOnOrAfter();
						if(condition.isBeforeNow()){
							authenlog.error("assertion past condition notOnOrAfter datetime: " + condition.toString());
							valid = false;
						}
					} else {
						authenlog.error("No assertion conditions provided");
						valid = false;
					}
				} else {
					authenlog.error("Expecting single assertion, either 0 or more than one assertion provided");
					valid = false;
				}
			} catch (ValidationException ve) {
				authenlog.error(ve.getMessage());
				ve.printStackTrace();
				valid = false;
			}

		} catch (Exception e){
			e.printStackTrace();
			valid = false;
		}
		return valid;
	}


}
