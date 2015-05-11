package com.tomecode.soa.dependency.analyzer.gui.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * data for {@link PropertyGroupView}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE })
public @interface PropertyGroupView {

	/**
	 * group title
	 * 
	 * @return
	 */
	String title() default "";

	/**
	 * method that return title of group
	 * 
	 * @return
	 */
	String titleMethod() default "";

	/**
	 * default name
	 * 
	 * @return
	 */
	String type() default "";

	/**
	 * method that return name
	 * 
	 * @return
	 */
	String typeMethod() default "";

	/**
	 * method for get parent object
	 * 
	 * @return
	 */
	String parentMethod() default "";

}
