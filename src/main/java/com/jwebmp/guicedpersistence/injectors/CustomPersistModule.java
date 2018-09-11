package com.jwebmp.guicedpersistence.injectors;

import com.google.inject.AbstractModule;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.UnitOfWork;
import org.aopalliance.intercept.MethodInterceptor;

public abstract class CustomPersistModule
		extends AbstractModule
{

	@Override
	protected final void configure()
	{
		configurePersistence();

		requireBinding(PersistService.class);
		requireBinding(UnitOfWork.class);
	}

	protected abstract void configurePersistence();

	protected abstract MethodInterceptor getTransactionInterceptor();
}
