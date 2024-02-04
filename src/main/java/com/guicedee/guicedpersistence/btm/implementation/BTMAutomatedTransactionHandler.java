package com.guicedee.guicedpersistence.btm.implementation;

import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.jndi.BitronixContext;
import com.google.common.base.Strings;
import com.guicedee.guicedpersistence.services.ITransactionHandler;
import lombok.Getter;
import lombok.extern.java.Log;
import org.hibernate.jpa.boot.internal.ParsedPersistenceXmlDescriptor;

import jakarta.persistence.EntityManager;

import java.util.logging.Level;

import static jakarta.persistence.PersistenceUnitTransactionType.JTA;

@Log
@SuppressWarnings("unused")
public class BTMAutomatedTransactionHandler
        implements ITransactionHandler<BTMAutomatedTransactionHandler> {
    private static final BitronixContext bc = new BitronixContext();
    private static final String UserTransactionReference = "java:comp/UserTransaction";
    private static boolean active = false;
    
    @Getter
    private static boolean enabled = true;
    
    @Override
    public boolean enabled(ParsedPersistenceXmlDescriptor persistenceUnit) {
        if (persistenceUnit.getTransactionType() == null) {
            return false;
        }
        if (persistenceUnit.getTransactionType().toString()
                .equalsIgnoreCase(JTA.toString())) {
            return enabled;
        }
        if (!Strings.isNullOrEmpty(persistenceUnit.getJtaDataSource().toString())) {
            return enabled;
        }
        return enabled;
    }

    public static boolean isActive() {
        return BTMAutomatedTransactionHandler.active;
    }

    public static void setActive(boolean active) {
        BTMAutomatedTransactionHandler.active = active;
    }

    /**
     * What to do when beginning a transaction, always called
     *
     * @param createNew     If create new was specified
     * @param entityManager The entity manager associated
     */
    @Override
    public void beginTransacation(boolean createNew, EntityManager entityManager, ParsedPersistenceXmlDescriptor persistenceUnit) {
        try {
            BitronixTransactionManager userTransaction = (BitronixTransactionManager) bc.lookup(UserTransactionReference);
            userTransaction.begin();
        }
        catch (Exception e) {
            BTMAutomatedTransactionHandler.log.log(Level.WARNING, "Unable to being a transaction for BTM", e);
        }
    }

    /**
     * What to do when committing a transaction, always called
     *
     * @param createNew     If the transaction already exists
     * @param entityManager The entity manager associated
     */
    @Override
    public void commitTransacation(boolean createNew, EntityManager entityManager, ParsedPersistenceXmlDescriptor persistenceUnit) {
        try {
            BitronixTransactionManager userTransaction = (BitronixTransactionManager) bc.lookup(UserTransactionReference);
            userTransaction.commit();
        } catch (Exception e) {
            BTMAutomatedTransactionHandler.log.log(Level.WARNING, "Unable to automatically commit the transaction", e);
        }
    }

    @Override
    public void setTransactionTimeout(int timeout, EntityManager entityManager, ParsedPersistenceXmlDescriptor persistenceUnit) {
        try {
            BitronixTransactionManager userTransaction = (BitronixTransactionManager) bc.lookup(UserTransactionReference);
            userTransaction.setTransactionTimeout(timeout);
        } catch (Exception e) {
            BTMAutomatedTransactionHandler.log.log(Level.WARNING, "Unable to automatically start the transaction", e);
        }
    }

    /**
     * What to do when committing a transaction, always called
     *
     * @param createNew     If the transaction already exists
     * @param entityManager The entity manager associated
     */
    @Override
    public void rollbackTransacation(boolean createNew, EntityManager entityManager, ParsedPersistenceXmlDescriptor persistenceUnit) {
        try {
            BitronixTransactionManager userTransaction = (BitronixTransactionManager) bc.lookup(UserTransactionReference);
            userTransaction.rollback();
        } catch (Exception e) {
            BTMAutomatedTransactionHandler.log.log(Level.WARNING, "Unable to rollback start the transaction", e);
        }
    }

    @Override
    public boolean transactionExists(EntityManager entityManager, ParsedPersistenceXmlDescriptor persistenceUnit) {
        try {
            BitronixTransactionManager userTransaction = (BitronixTransactionManager) bc.lookup(UserTransactionReference);
            return userTransaction.getStatus() == 0;
        } catch (Exception e) {
            BTMAutomatedTransactionHandler.log.log(Level.SEVERE, "BTM Cannot be fetched!", e);
            return false;
        }
    }

    @Override
    public boolean active(ParsedPersistenceXmlDescriptor persistenceUnit) {
        return enabled(persistenceUnit) && active;
    }
    
    public static void setEnabled(boolean enabled) {
        BTMAutomatedTransactionHandler.enabled = enabled;
    }
}
