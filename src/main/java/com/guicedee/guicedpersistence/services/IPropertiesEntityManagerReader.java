package com.guicedee.guicedpersistence.services;


import org.hibernate.jpa.boot.internal.ParsedPersistenceXmlDescriptor;

import java.util.Map;
import java.util.Properties;

/**
 * Manages properties passed into the entity manager factory
 */
public interface IPropertiesEntityManagerReader
{
	/**
	 * Manages properties passed into the entity manager factory
	 * <p>
	 * return properties
	 */
	Map<String, String> processProperties(ParsedPersistenceXmlDescriptor persistenceUnit, Properties incomingProperties);
	
	/**
	 * If this class is applicable to the persistence type coming in
	 *
	 * @return true or false if this is the manager that must be used
	 */
	boolean applicable(ParsedPersistenceXmlDescriptor persistenceUnit);
}
