package com.bhc.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClassType {
	@SuppressWarnings("unchecked")
	public static Object setFieldValue(Object obj, int index, Object value,
			int type) {
		Class cls = obj.getClass();
		Field[] objFields = cls.getDeclaredFields();
		Field objField = objFields[index];
		String fieldName = objField.getName();
		String setterName = "set" + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
		Method setter = null;
		try {
			setter = cls.getDeclaredMethod(setterName, getClassType(type));
			return setter.invoke(obj, value);
		} catch (Exception e) {
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static Object getFieldValue(Object obj, int index) {
		Class cls = obj.getClass();
		Field[] objFields = cls.getDeclaredFields();
		Field objField = objFields[index];
		String fieldName = objField.getName();
		String getterName = "get" + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
		Method getter = null;
		try {
			getter = cls.getDeclaredMethod(getterName);
			return getter.invoke(obj);
		} catch (Exception e) {
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static Object getFieldValue(Object obj, String name) {
		Class cls = obj.getClass();
		Field[] objFields = cls.getDeclaredFields();
		for (int i = 0; i < objFields.length; i++) {
			Field objField = objFields[i];
			String fieldName = objField.getName();
			if (fieldName.equals(name)) {
				String getterName = "get"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				Method getter = null;
				try {
					getter = cls.getDeclaredMethod(getterName);
					return getter.invoke(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Object getFieldValue(Object obj, String name, Object value, int type) {
		Class cls = obj.getClass();
		Field[] objFields = cls.getDeclaredFields();
		for (int i = 0; i < objFields.length; i++) {
			Field objField = objFields[i];
			String fieldName = objField.getName();
			if (fieldName.equals(name)) {
				String setterName = "set"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				Method setter = null;
				try {
					setter = cls.getDeclaredMethod(setterName,
							getClassType(type));
					return setter.invoke(obj, value);
				} catch (Exception e) {
					try {
						if (getClassType(type) == char.class) {
							setter = cls.getDeclaredMethod(setterName,
									String.class);
							return setter.invoke(obj, value);
						} else if (getClassType(type) == double.class) {
							setter = cls.getDeclaredMethod(setterName,
									Double.class);
							return setter.invoke(obj, value);
						} else if (getClassType(type) == int.class) {
							try {
								setter = cls.getDeclaredMethod(setterName,
										Integer.class);
								return setter.invoke(obj, value);
							} catch (Exception e2) {
								setter = cls.getDeclaredMethod(setterName,
										String.class);
								return setter.invoke(obj, value.toString());
							}
						}
					} catch (Exception e1) {
					}
				}
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static Class getClassType(int v) {
		Class c = null;
		switch (v) {
		case Types.ARRAY:
			c = List.class;
			break;
		case Types.BIGINT:
			c = Long.class;
			break;
		case Types.LONGVARBINARY:
		case Types.VARBINARY:
		case Types.BINARY:
			c = byte.class;
			break;
		// case Types.LONGNVARCHAR:
		case Types.LONGVARCHAR:
			// case Types.NCLOB:
			// case Types.NVARCHAR:
		case Types.VARCHAR:
		case Types.BLOB:
			c = String.class;
			break;
		case Types.BIT:
		case Types.BOOLEAN:
			c = Boolean.class;
			break;
		// case Types.NCHAR:
		case Types.CHAR:
			c = char.class;
			break;
		case Types.TIME:
		case Types.TIMESTAMP:
		case Types.DATE:
			c = Date.class;
			break;
		case Types.DOUBLE:
			c = double.class;
			break;
		case Types.FLOAT:
			c = Float.class;
			break;
		case Types.INTEGER:
			c = int.class;
			break;
		case Types.NULL:
			c = null;
			break;
		case Types.NUMERIC:
			c = int.class;
			break;
		case Types.SMALLINT:
			c = short.class;
			break;
		case Types.TINYINT:
			c = short.class;
			break;
		}
		return c;
	}

	public Object getRSValueByType(ResultSet rs, int i, int v) {
		Object c = null;
		try {
			switch (v) {
			case Types.BIGINT:
				c = rs.getLong(i);
				break;
			case Types.LONGVARBINARY:
			case Types.VARBINARY:
			case Types.BINARY:
				c = rs.getByte(i);
				break;
			// case Types.NCHAR:
			case Types.CHAR:
				// case Types.LONGNVARCHAR:
			case Types.LONGVARCHAR:
				// case Types.NCLOB:
				// case Types.NVARCHAR:
			case Types.VARCHAR:
			case Types.BLOB:
				c = rs.getString(i);
				break;
			case Types.BIT:
			case Types.BOOLEAN:
				c = rs.getBoolean(i);
				break;
			case Types.TIME:
			case Types.TIMESTAMP:
			case Types.DATE:
				c = rs.getDate(i);
				break;
			case Types.DOUBLE:
				c = rs.getDouble(i);
				break;
			case Types.FLOAT:
				c = rs.getFloat(i);
				break;
			case Types.NULL:
				c = null;
				break;
			case Types.INTEGER:
			case Types.NUMERIC:
				c = rs.getInt(i);
				break;
			case Types.TINYINT:
			case Types.SMALLINT:
				c = rs.getShort(i);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

	public static Object getInstance(Object o) {
		try {
			return Class.forName(
					o.getClass().toString().replace("class", "").replace(" ",
							"")).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
