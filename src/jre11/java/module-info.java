module com.guicedee.guicedpersistence {
	exports com.guicedee.guicedpersistence.db;
	exports com.guicedee.guicedpersistence.db.annotations;
	exports com.guicedee.guicedpersistence.db.exceptions;
	exports com.guicedee.guicedpersistence.db.intercepters;
	exports com.guicedee.guicedpersistence.services;

	exports com.guicedee.guicedpersistence.scanners;

	exports com.oracle.jaxb21;

	requires com.google.guice.extensions.persist;
	requires com.guicedee.guicedinjection;
	requires com.guicedee.logmaster;

	requires io.github.classgraph;

	requires java.logging;
	requires com.google.guice;

	requires java.xml.bind;

	requires java.naming;
	requires aopalliance;

	requires java.validation;

	requires com.google.common;
	requires javax.inject;

	requires com.fasterxml.jackson.core;
	requires com.fasterxml.jackson.annotation;
	requires com.fasterxml.jackson.databind;

	requires java.persistence;

	requires org.json;
	requires java.sql;
	requires java.transaction;
	requires net.bytebuddy;

	uses com.guicedee.guicedpersistence.services.IPropertiesConnectionInfoReader;
	uses com.guicedee.guicedpersistence.services.IPropertiesEntityManagerReader;
	uses com.guicedee.guicedpersistence.services.ITransactionHandler;

	provides com.guicedee.guicedinjection.interfaces.IPathContentsScanner with com.guicedee.guicedpersistence.scanners.GuiceInjectionMetaInfScanner;
	provides com.guicedee.guicedinjection.interfaces.IPathContentsBlacklistScanner with com.guicedee.guicedpersistence.scanners.GuiceInjectionMetaInfScannerExclusions;
	provides com.guicedee.guicedinjection.interfaces.IFileContentsScanner with com.guicedee.guicedpersistence.scanners.PersistenceFileHandler;
	provides com.guicedee.guicedinjection.interfaces.IGuiceConfigurator with com.guicedee.guicedpersistence.db.services.PersistenceGuiceConfigurator;
	//provides IGuicePostStartup with AsyncPostStartup;
	provides com.guicedee.guicedinjection.interfaces.IGuiceDefaultBinder with com.guicedee.guicedpersistence.scanners.PersistenceServiceLoadersBinder;

	provides com.guicedee.guicedinjection.interfaces.IGuiceScanModuleExclusions with com.guicedee.guicedpersistence.implementations.GuicedPersistenceJarModuleExclusions;
	provides com.guicedee.guicedinjection.interfaces.IGuiceScanJarExclusions with com.guicedee.guicedpersistence.implementations.GuicedPersistenceJarModuleExclusions;
	provides com.guicedee.guicedinjection.interfaces.IGuicePreDestroy with com.guicedee.guicedpersistence.implementations.GuicedPersistenceDestroyer;

	provides com.guicedee.guicedpersistence.services.IPropertiesConnectionInfoReader with com.guicedee.guicedpersistence.db.intercepters.JPADefaultConnectionBaseBuilder;

	opens com.oracle.jaxb21 to com.fasterxml.jackson.databind;
	opens com.guicedee.guicedpersistence.db to com.fasterxml.jackson.databind;
	opens com.guicedee.guicedpersistence.injectors to com.google.guice;
	opens com.guicedee.guicedpersistence.implementations  to com.google.guice;
}
