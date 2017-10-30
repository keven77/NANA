package com.juran.index.mdm.app.resource.v1;

import com.juran.core.exception.bean.ErrorMsgBean;
import com.juran.core.log.eventlog.aop.RestLog;
import com.juran.index.mdm.app.service.IndexMmService;
import com.juran.index.mdm.app.utils.ApiResponseCustom;
import com.juran.index.mdm.client.bean.response.IndexMmIndexRespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api(value = "索引服务类")
@RestLog
@Component
@Path("/v1/index/notify")
@Produces(MediaType.APPLICATION_JSON)
public class IndexMmResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(IndexMmResource.class);

	@Autowired
	private IndexMmService indexMmService;

	@ApiOperation(value = "主材同步", notes = "主材同步")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "jsonParam", value = "json参数数组(catentryKey:主材key,action:操作编号)", required = true, dataType = "string", paramType = "query", example = "[{\"action\":\"add\",\"catentryKey\":\"55148067275407360\"}]") })
	@ApiResponses({ @ApiResponse(code = 200, message = "成功", response = IndexMmIndexRespBean.class),
			@ApiResponse(code = 400, message = "参数错误", response = ErrorMsgBean.class) })
	@POST
	@Path("/catentry")
	public Response catentry(@ApiParam(hidden = true) @QueryParam("jsonParam") String jsonParam) {
		LOGGER.info("--catentry--jsonParam={}",jsonParam);
		Long timeStart=System.currentTimeMillis();
		Object object=indexMmService.notifyCatentry(jsonParam);
		LOGGER.info("--catentry--jsonParam={},time={}",jsonParam,System.currentTimeMillis()-timeStart);
		return ApiResponseCustom.convert(object) ;

	}

	@ApiOperation(value = "品牌同步", notes = "品牌同步")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "jsonParam", value = "json参数数组(brandId:品牌id,action:操作编号)", required = true, dataType = "string", paramType = "query", example = "[{\"action\":\"add\",\"brandId\":\"55148067275407360\"}]") })
	@ApiResponses({ @ApiResponse(code = 200, message = "成功", response = IndexMmIndexRespBean.class),
			@ApiResponse(code = 400, message = "参数错误", response = ErrorMsgBean.class) })
	@POST
	@Path("/brand")
	public Response brand(@ApiParam(hidden = true) @QueryParam("jsonParam") String jsonParam) {
		LOGGER.info("--brand--jsonParam={}",jsonParam);
		Long timeStart=System.currentTimeMillis();
		Object object=indexMmService.notifyBrand(jsonParam);
		LOGGER.info("--brand--jsonParam={},time={}",jsonParam,System.currentTimeMillis()-timeStart);
		return ApiResponseCustom.convert(object) ;

	}
}
