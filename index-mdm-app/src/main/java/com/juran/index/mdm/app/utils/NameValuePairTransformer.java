package com.juran.index.mdm.app.utils;

/*
 *-----------------------------------------------------------------
 * IBM Confidential
 *
 * OCO Source Materials
 *
 * WebSphere Commerce
 *
 * (C) Copyright IBM Corp. 2011, 2014
 *
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has
 * been deposited with the U.S. Copyright Office.
 *-----------------------------------------------------------------
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.solr.handler.dataimport.Context;
import org.apache.solr.handler.dataimport.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.juran.core.log.contants.LoggerName;


public class NameValuePairTransformer extends Transformer {

		private static final String CLASSNAME = NameValuePairTransformer.class
			.getName();

	/**
	 * The constant of the customize attribute of wc-data-config.xml file that
	 * used to distinguish with default split.
	 */
	public static final String MAKE_FIELD_BY_NAME = "makeFieldBy";

	/**
	 * The constant of the split by name.
	 */
	public static final String SPLIT_VALUE_BY_NAME = "splitValueBy";

	/**
	 * The constant of the source column name.
	 */
	public static final String SRC_COL_NAME = "sourceColName";
	protected final transient Logger infologger = LoggerFactory.getLogger(LoggerName.INFO);
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.solr.handler.dataimport.Transformer#transformRow(java.util
	 * .Map, org.apache.solr.handler.dataimport.Context)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object transformRow(Map<String, Object> row, Context context) {
		List<Map<String, String>> fields = context.getAllEntityFields();
		for (Map<String, String> field : fields) {
			// makeFieldBy
			String makeFieldBy = field.get(MAKE_FIELD_BY_NAME);
			// splitBy
			String splitValueBy = field.get(SPLIT_VALUE_BY_NAME);
			if (makeFieldBy == null) {
				continue;
			}
			// sourceColName
			String srcColumnName = field.get(SRC_COL_NAME);
			Object srcColumnValue = row.get(srcColumnName);
			if (srcColumnValue != null && srcColumnValue instanceof List) {
				
				Set<String> inputs = (HashSet<String>) srcColumnValue;
				
				for (String input : inputs) {
					
					if (input != null && input.length() > 0) {
						String[] tokens = input.split(makeFieldBy);
						if (tokens.length == 2) {
							// locate list of values from dynamic column
							HashSet<String> list = (HashSet<String>) row
									.get(tokens[0]);
							if (tokens[1] != null) {
								// break up into multi-values
								if (list == null) {
									list = new HashSet<String>();
								}
								if (splitValueBy != null) {
									String[] values = tokens[1]
											.split(splitValueBy);
									for (String value : values) {
										list.add(value);
									}
								} else {
									list.add(tokens[1]);
								}
								row.put(tokens[0], list);
							}
						}
					}
				}
			}
		}
		return row;
	}
}
