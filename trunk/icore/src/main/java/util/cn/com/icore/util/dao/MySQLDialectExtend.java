package cn.com.icore.util.dao;

import java.sql.Types;

import org.hibernate.Hibernate;

public class MySQLDialectExtend extends org.hibernate.dialect.MySQLDialect {
	public MySQLDialectExtend() {
		super();
        System.out.println("注册方言：MySQLDialectExtend");
		registerHibernateType(Types.LONGVARCHAR, Hibernate.TEXT.getName());
	}

}
