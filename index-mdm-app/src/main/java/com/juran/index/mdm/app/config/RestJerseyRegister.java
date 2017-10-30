package com.juran.index.mdm.app.config;

import com.juran.index.mdm.app.resource.v1.CaseIndexResource;
import com.juran.index.mdm.app.resource.v1.IndexMmResource;
import com.juran.index.mdm.app.resource.v1.PopulateResource;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author 王云龙
 * @date 2017年4月21日 上午11:56:08
 * @version 1.0
 * @description jersey服务注册
 *
 */
public class RestJerseyRegister extends ResourceConfig {

	public RestJerseyRegister() {
		register(RequestContextFilter.class);
		//Jersey2和SpringBoot集成有bug,Jersey无法加载scanPackage获得结果
//		packages("com.juran.example.app.resource");
		registerClasses(findRegisterV1Resources());
		
	}
	
	@PostConstruct
	public void initSwagger(){
		register(ApiListingResource.class);
		register(SwaggerSerializers.class);
	}
	
	private Set<Class<?>> findRegisterV1Resources(){
		Set<Class<?>> registerResources=new HashSet<Class<?>>();
		registerResources.add(IndexMmResource.class);
		registerResources.add(PopulateResource.class);
		registerResources.add(CaseIndexResource.class);
		return registerResources;
	}
}
