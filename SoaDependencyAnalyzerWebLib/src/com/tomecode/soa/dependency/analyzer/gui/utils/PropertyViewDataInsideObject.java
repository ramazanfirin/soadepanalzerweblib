package com.tomecode.soa.dependency.analyzer.gui.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * 
 * this is special annotation which redirect to target object wich contains data
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD, ElementType.METHOD })
public @interface PropertyViewDataInsideObject {

}
