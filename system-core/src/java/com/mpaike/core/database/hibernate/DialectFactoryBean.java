/*
 * Copyright (C) 2009-2010 WWF Software Limited.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.

 * As a special exception to the terms and conditions of version 2.0 of 
 * the GPL, you may redistribute this Program in connection with Free/Libre 
 * and Open Source Software ("FLOSS") applications as described in WWF's 
 * FLOSS exception.  You should have received a copy of the text describing 
 * the FLOSS exception, and it is also available here: 
 * http://www.42y.net/legal/licensing"
 */
package com.mpaike.core.database.hibernate;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.resolver.DialectFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

/**
 * Factory for the Hibernate dialect. Allows dialect detection logic to be centralized and the dialect to be injected
 * where required as a singleton from the container.
 */
public class DialectFactoryBean implements FactoryBean
{

    /** The local session factory. */
    private LocalSessionFactoryBean localSessionFactory;

    /**
     * Sets the local session factory.
     * 
     * @param localSessionFactory
     *            the new local session factory
     */
    public void setLocalSessionFactory(LocalSessionFactoryBean localSessionFactory)
    {
        this.localSessionFactory = localSessionFactory;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    @SuppressWarnings("deprecation")
    public Object getObject() throws SQLException
    {
        Session session = ((SessionFactory) this.localSessionFactory.getObject()).openSession();
        Configuration cfg = this.localSessionFactory.getConfiguration();
        Connection con = null;
        try
        {
            // make sure that we AUTO-COMMIT
            con = session.connection();
            con.setAutoCommit(true);
            Dialect dialect = DialectFactory.buildDialect(cfg.getProperties(), con);
            dialect = changeDialect(cfg, dialect);
            return dialect;
        }
        finally
        {
            try
            {
                con.close();
            }
            catch (Exception e)
            {
            }
        }
    }

    /**
     * Substitute the dialect with an alternative, if possible.
     * 
     * @param cfg
     *            the configuration
     * @param dialect
     *            the dialect
     * @return the dialect
     */
    private Dialect changeDialect(Configuration cfg, Dialect dialect)
    {
        String dialectName = cfg.getProperty(Environment.DIALECT);
        if (dialectName == null || dialectName.length() == 0)
        {
            // Fix the dialect property to match the detected dialect
            cfg.setProperty(Environment.DIALECT, dialect.getClass().getName());
        }
        return dialect;
        // TODO: https://issues.42y.net/jira/browse/ETHREEOH-679
        // else if (dialectName.equals(Oracle9Dialect.class.getName()))
        // {
        // String subst = WWFOracle9Dialect.class.getName();
        // LogUtil.warn(logger, WARN_DIALECT_SUBSTITUTING, dialectName, subst);
        // cfg.setProperty(Environment.DIALECT, subst);
        // }
        // else if (dialectName.equals(MySQLDialect.class.getName()))
        // {
        // String subst = MySQLInnoDBDialect.class.getName();
        // LogUtil.warn(logger, WARN_DIALECT_SUBSTITUTING, dialectName, subst);
        // cfg.setProperty(Environment.DIALECT, subst);
        // }
        // else if (dialectName.equals(MySQL5Dialect.class.getName()))
        // {
        // String subst = MySQLInnoDBDialect.class.getName();
        // LogUtil.warn(logger, WARN_DIALECT_SUBSTITUTING, dialectName, subst);
        // cfg.setProperty(Environment.DIALECT, subst);
        // }
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    public Class<?> getObjectType()
    {
        return Dialect.class;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.beans.factory.FactoryBean#isSingleton()
     */
    public boolean isSingleton()
    {
        return true;
    }
}
