package com.kuugen.core.util;

import net.sf.json.processors.DefaultValueProcessor;

public class JsonDefaultValueProcessor implements DefaultValueProcessor{
	 @Override
	@SuppressWarnings("unchecked")
	public Object getDefaultValue(Class type) {   
			return null;   
	} 
}
