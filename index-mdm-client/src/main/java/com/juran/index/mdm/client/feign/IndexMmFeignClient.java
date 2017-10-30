package com.juran.index.mdm.client.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "index-mdm-app", fallbackFactory = IndexMmFeignClientFallbackFactory.class, url = "${index-mdm-app.feign.url:}")
@RequestMapping(path = "/api/v1/index/notify")
public interface IndexMmFeignClient {

    @RequestMapping(path = "/catentry", method = RequestMethod.POST)
    Boolean catentry(@RequestParam(value = "jsonParam") String jsonParam);

    @RequestMapping(path = "/brand", method = RequestMethod.POST)
    Boolean brand(@RequestParam(value = "jsonParam") String jsonParam);

}
