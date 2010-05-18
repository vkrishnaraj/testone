package com.nettracer.claims.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.persistence.Entity;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.Resource;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.util.ClassUtils;

/**
 * The Class ScanningAnnotationSessionFactoryBean.  
 * Modified from the example found here: http://forum.springframework.org/showthread.php?p=172273#post172053
 * 
 * @author Eric DeLabar
 */
public class ScanningAnnotationSessionFactoryBean extends AnnotationSessionFactoryBean {
	Log log = LogFactory.getLog( ScanningAnnotationSessionFactoryBean.class );
	
	/** The bean class loader. */
	private ClassLoader beanClassLoader;
	
	private Resource[] mappingLocations;

	/* (non-Javadoc)
	 * @see org.springframework.orm.hibernate3.LocalSessionFactoryBean#setBeanClassLoader(java.lang.ClassLoader)
	 */
	@Override
	public void setBeanClassLoader( ClassLoader beanClassLoader ) {
		this.beanClassLoader = beanClassLoader;
		super.setBeanClassLoader( beanClassLoader );
	}

	/* (non-Javadoc)
	 * @see org.springframework.orm.hibernate3.AbstractSessionFactoryBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		log.info( "After Properties Set..." );
		Collection<Class<?>> entities = new ArrayList<Class<?>>();
		ClassPathScanningCandidateComponentProvider scanner = this.createScanner();
		this.findEntities( scanner, entities, "com" );
		this.setAnnotatedClasses( entities.toArray( new Class<?>[entities.size()] ) );
		
		super.afterPropertiesSet();
	}

	/**
	 * Creates the scanner.
	 * 
	 * @return the class path scanning candidate component provider
	 */
	private ClassPathScanningCandidateComponentProvider createScanner() {
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider( false );
		scanner.addIncludeFilter( new AnnotationTypeFilter( Entity.class ) );
		return scanner;
	}

	/**
	 * Find entities.
	 * 
	 * @param scanner the scanner
	 * @param entities the entities
	 * @param basePackage the base package
	 */
	private void findEntities( ClassPathScanningCandidateComponentProvider scanner, Collection<Class<?>> entities, String basePackage ) {
		Set<BeanDefinition> annotatedClasses = scanner.findCandidateComponents( basePackage );
		for ( BeanDefinition bd : annotatedClasses ) {
			String className = bd.getBeanClassName();
			Class<?> type = ClassUtils.resolveClassName( className, this.beanClassLoader );
			log.info( "Adding Class: " + type );
			entities.add( type );
		}
	}
	
	@Override
	@Autowired
	public void setMappingLocations(Resource[] mappingLocations) {
		this.mappingLocations=mappingLocations;
		super.setMappingLocations(mappingLocations);
	}
	
}
