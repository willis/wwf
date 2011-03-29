package cn.com.icore.test;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;

public class ConfigGenerator {

	public static void main(String[] args) throws Exception {
		String table_name = "system_log";
		String root_path = "E:\\workspace\\wwf\\icore\\src\\main\\java\\util\\cn\\com\\icore\\sys\\model\\bo";
		String pkgName = "cn.com.icore.sys.model";

		DriverManager
				.registerDriver(new com.mysql.jdbc.Driver());
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1:3306/core",
				"root", null);
		System.out.println("connected to database successfully.");
		Statement st = conn.createStatement();
		String sql = "select * from " + table_name + " where 1 = 2";
		ResultSet rs = st.executeQuery(sql);
		ResultSetMetaData meta = rs.getMetaData();
		String hbmFile = getHbmFile(root_path, table_name);
		String beanFile = getBeanFile(root_path, table_name);
		System.out.println("output hbm file: " + hbmFile);
		System.out.println("output bean file: " + beanFile);

		String beanName = getBeanName(table_name);
		FileWriter outHbm = new FileWriter(hbmFile);
		FileWriter outBean = new FileWriter(beanFile);
		outHbm.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		outHbm.write(" ");
		outHbm.write("<!DOCTYPE hibernate-mapping PUBLIC");
		outHbm.write(" ");
		outHbm.write(" \"-//Hibernate/Hibernate Mapping DTD 3.0//EN\"");
		outHbm.write(" ");
		outHbm
				.write(" \"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd\">");
		outHbm.write(" ");
		outHbm.write("<hibernate-mapping>");
		outHbm.write(" ");
		outHbm.write(" <class name=\"" + pkgName + "." + beanName
				+ "\" table=\"" + table_name + "\">");
		outHbm.write(" ");

		outBean.write("package " + pkgName + ";");
		outBean.write(" ");
		outBean.write(" ");
		outBean.write("public class " + beanName);
		outBean.write(" ");
		outBean.write("{");
		outBean.write(" ");

		StringBuffer toStr = new StringBuffer();

		for (int iCol = 1; iCol <= meta.getColumnCount(); iCol++) {
			String colName = meta.getColumnName(iCol);
			int colType = meta.getColumnType(iCol);
			String propName = getPropertyName(colName);
			String javaType = getJavaType(colType).getName();
			outHbm.write(" <property name=" + propName + " column=\"" + colName
					+ "\"/>");
			outHbm.write(" ");

			outBean.write(" private " + javaType + " " + propName + ";");
			outBean.write(" ");
			outBean.write(" ");
			outBean.write(" public " + javaType + " "
					+ getGetterMethod(propName) + "()");
			outBean.write(" ");
			outBean.write(" {");
			outBean.write(" ");
			outBean.write(" return this." + propName + ";");
			outBean.write(" ");
			outBean.write(" }");
			outBean.write(" ");
			outBean.write(" ");

			outBean.write(" public void " + getSetterMethod(propName) + "("
					+ javaType + " " + propName + ")");
			outBean.write(" ");
			outBean.write(" {");
			outBean.write(" ");
			outBean.write(" this." + propName + " = " + propName + ";");
			outBean.write(" ");
			outBean.write(" }");
			outBean.write(" ");
			outBean.write(" ");

			toStr.append("\"" + propName +" = \"" + " + this." + propName + "\n");
			if (iCol != meta.getColumnCount()) {
				toStr.append(" + ");
			}

		}

		outHbm.write(" </class>");
		outHbm.write(" ");
		outHbm.write("</hibernate-mapping>");
		outHbm.flush();
		outHbm.close();
		System.out.println("hbm file generated sucessfully!");

		outBean.write(" @Override");
		outBean.write(" ");
		outBean.write(" public String toString()");
		outBean.write(" ");
		outBean.write(" {");
		outBean.write(" ");
		outBean.write(" return " + toStr.toString() + ";");
		outBean.write(" ");
		outBean.write(" }");
		outBean.write(" ");
		outBean.write("}");
		outBean.flush();
		outBean.close();
		System.out.println("bean file generated sucessfully!");
	}

	private static String getHbmFile(String root_path, String table_name) {
		return root_path + table_name + ".hbm.xml";
	}

	private static String getBeanFile(String root_path, String table_name) {
		String beanName = getBeanName(table_name);
		return root_path + beanName + ".java";
	}

	private static String getBeanName(String table_name) {
		String[] segs = table_name.split("_");
		String beanName = "";
		for (int i = 0; i < segs.length; i++) {
			beanName += segs[i].substring(0, 1).toUpperCase()
					+ segs[i].substring(1, segs[i].length()).toLowerCase();
		}
		return beanName;
	}

	private static String getPropertyName(String col_name) {
		String[] segs = col_name.split("_");
		String propName = "";
		for (int i = 0; i < segs.length; i++) {
			if (i == 0)
				propName += segs[i];
			else
				propName += segs[i].substring(0, 1).toUpperCase()
						+ segs[i].substring(1, segs[i].length()).toLowerCase();
		}
		return propName;
	}

	private static String getGetterMethod(String prop_name) {
		return "get" + prop_name.substring(0, 1).toUpperCase()
				+ prop_name.substring(1, prop_name.length());
	}

	private static String getSetterMethod(String prop_name) {
		return "set" + prop_name.substring(0, 1).toUpperCase()
				+ prop_name.substring(1, prop_name.length());
	}

	@SuppressWarnings("unchecked")
	private static Class getJavaType(int colType) {
		switch (colType) {
		case Types.BIT:
		case Types.TINYINT:
		case Types.SMALLINT:
			return Short.class;
		case Types.INTEGER:
			return Integer.class;
		case Types.BIGINT:
			return Long.class;
		case Types.CHAR:
		case Types.VARCHAR:
		case Types.CLOB:
			return String.class;
		case Types.FLOAT:
			return Float.class;
		case Types.DOUBLE:
			return Double.class;
		case Types.DATE:
		case Types.TIME:
		case Types.TIMESTAMP:
			return Date.class;
		default:
			return Object.class;
		}
	}
}