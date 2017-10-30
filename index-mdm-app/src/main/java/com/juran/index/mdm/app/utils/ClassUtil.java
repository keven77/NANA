package com.juran.index.mdm.app.utils;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created by dell on 2017/6/9.
 */
public class ClassUtil {

	private ClassUtil (){}

	public static Set<Class<?>> findAllClassByPackage(String fullPackageName) {
		Set<Class<?>> classNames = Sets.newHashSet();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		String packagePath = fullPackageName.replace(".", "/");

		URL url = loader.getResource(packagePath);
		if (url != null) {
			String protocol = url.getProtocol();
			if (protocol.equals("file")) {
				classNames = getClassNameFromDir(url.getPath(), fullPackageName);
			}
		}
		return classNames;
	}

	private static Set<Class<?>> getClassNameFromDir(String filePath, String fullPackageName) {
		Set<Class<?>> classes = Sets.newHashSet();
		File file = new File(filePath);
		File[] files = file.listFiles();
		for (File childFile : files) {
			String fileName = childFile.getName();
			if (fileName.endsWith(".class") && !fileName.contains("$")) {
				try {
					classes.add(Class.forName(fullPackageName + "." + fileName.replace(".class", "")));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		return classes;
	}

	/**
	 * 构建属性--值
	 * 
	 * @param object
	 * @param ignoreDate
	 *            是否忽略时间类型
	 * @return
	 */
	public static Map<String, Object> getClassFieldAndValue(Object object, Boolean ignoreDate) {
		Map<String, Object> resMap = Maps.newHashMap();
		if (object == null)
			return resMap;
		Class objectClass = object.getClass();
		Field[] fs = objectClass.getDeclaredFields();
		for (int i = 0; i < fs.length; i++) {
			Field f = fs[i];
			if (ignoreDate && Date.class.equals(f.getType()))
				continue;
			f.setAccessible(true); // 设置些属性是可以访问的
			Object val = null;// 得到此属性的值
			try {
				val = f.get(object);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			if (!("serialVersionUID".equals(f.getName()) || "limit".equals(f.getName())
					|| "offset".equals(f.getName())) && null != val) {
				resMap.put(f.getName(), val);
			}
		}
		return resMap;
	}
	
	
}
