package com.juran.index.mdm.client.feign;

import com.juran.core.log.contants.LoggerName;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class IndexMmFeignClientFallbackFactory implements FallbackFactory<IndexMmFeignClient> {

	protected final Logger LOGGER = LoggerFactory.getLogger(LoggerName.INFO);

	@Override
	public IndexMmFeignClient create(Throwable cause) {
		IndexMmFeignClient indexMmFeignClient=new IndexMmFeignClient() {
			@Override
			public Boolean catentry(String jsonParam) {
				LOGGER.error("--catentry--jsonParam={},error",jsonParam,cause);
				return null;
			}

			@Override
			public Boolean brand(String jsonParam) {
				LOGGER.error("--catentry--jsonParam={},error",jsonParam,cause);
				return null;
			}

		};
		return indexMmFeignClient;
	}

}
