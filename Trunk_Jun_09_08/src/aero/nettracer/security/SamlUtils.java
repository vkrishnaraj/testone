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

import org.apache.log4j.Logger;
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
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.SignatureValidator;
import org.opensaml.xml.validation.ValidationException;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;


public class SamlUtils implements SsoUtils{
	private static Logger logger = Logger.getLogger(SamlUtils.class);
	
	
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
			
			boolean validAssertion = (Boolean)attr.get("signatureValidation");
			node.setValidAssertion(validAssertion);

			Map<String, String> keyMap = getKeyMap(companycode);

			if(keyMap != null){
				node.setUsername(((List<String>)attr.get(keyMap.get("username"))).get(0));
				node.setGroup(((List<String>)attr.get(keyMap.get("group"))).get(0));
				node.setStation(((List<String>)attr.get(keyMap.get("stationcode"))).get(0));
				node.setFirstname(((List<String>)attr.get(keyMap.get("firstname"))).get(0));
				node.setLastname(((List<String>)attr.get(keyMap.get("lastname"))).get(0));
			}
		}
		logger.debug(node.toString());
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
	       map.put("signatureValidation", true);
	       
	       Issuer issuer = message.getIssuer();
	       map.put("issuerName", issuer.getValue());
	       
	       Signature signature = message.getSignature();
	       map.put("signatureAlgorithm", signature.getSignatureAlgorithm());
	       
	       KeyInfo keyInfo = signature.getKeyInfo();
	       map.put("X509Cert", keyInfo.getX509Datas().get(0).getX509Certificates().get(0).getDOM().getFirstChild().getTextContent());
	       
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

	/**
	 * Currently the only implementation support for signature validation is X509 with no password provided.
	 * 
	 * Consider abstracting to support other authentication types in the future.
	 * 
	 * @param message
	 * @return
	 */
	public static Boolean validate(ResponseImpl  message){
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
			try
			{
				signatureValidator.validate(signature);
				return true;
			}
			catch (ValidationException ve)
			{
				logger.error(ve.getMessage());
				ve.printStackTrace();
			}

		} catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}


}
