package com.juran.index.mdm.app.resource.v1;

import com.juran.core.exception.bean.ErrorMsgBean;
import com.juran.core.log.contants.LoggerName;
import com.juran.core.log.eventlog.aop.RestLog;
import com.juran.index.mdm.app.service.CaseindexService;
import com.juran.index.mdm.app.utils.ApiResponseCustom;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

/*
 * FileName: CaseIndexResource.java
 * Author:   zhangyihang
 * Date:    2017年10月17日 09:02:12
 * Description: //模块目的、功能描述 <2D，3D，标签全量接口>  <br>
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
@Api(value = "案列索引服务类")
@RestLog
@Component
@Path("/v1/case")
@Produces(MediaType.APPLICATION_JSON)
public class CaseIndexResource {

    protected final transient Logger LOGGER = LoggerFactory.getLogger(LoggerName.INFO);

    @Autowired
    private CaseindexService caseindexService;

    @ApiOperation(value = "构建标签全部索引", notes = "构建标签全部索引")
    @ApiResponses({@ApiResponse(code = 200, message = "成功", response = Map.class),
            @ApiResponse(code = 400, message = "异常", response = ErrorMsgBean.class)})
    @GET
    @Path("/tags")
    public Response fullDumpTags(){
        return ApiResponseCustom.convert(caseindexService.fullTagsDump());
    }
    @ApiOperation(value = "构建2D案列全部索引", notes = "构建2D案列全部索引")
    @ApiResponses({@ApiResponse(code = 200, message = "成功", response = Map.class),
            @ApiResponse(code = 400, message = "异常", response = ErrorMsgBean.class)})
    @GET
    @Path("/2DCase")
    public Response fullDump2DCase(){
        return ApiResponseCustom.convert(caseindexService.full2DcaseDump());
    }


    @ApiOperation(value = "构建3D案列全部索引", notes = "构建3D案列全部索引")
    @ApiResponses({@ApiResponse(code = 200, message = "成功", response = Map.class),
            @ApiResponse(code = 400, message = "异常", response = ErrorMsgBean.class)})
    @GET
    @Path("/3DCase")
    public Response fullDump3DCase(){
        return ApiResponseCustom.convert(caseindexService.full3DcaseDump());
    }


    @ApiOperation(value = "删除2dcase", notes = "删除2dcase")
    @ApiResponses({@ApiResponse(code = 200, message = "成功", response = Map.class),
            @ApiResponse(code = 400, message = "异常", response = ErrorMsgBean.class)})
    @PUT
    @Path("/2DCaseDelete")
    public Response delete2DCase(@RequestParam("ids") String ids){
        return ApiResponseCustom.convert(caseindexService.delete2Dcase(ids));
    }

    @ApiOperation(value = "删除3dcase", notes = "删除3dcase")
    @ApiResponses({@ApiResponse(code = 200, message = "成功", response = Map.class),
            @ApiResponse(code = 400, message = "异常", response = ErrorMsgBean.class)})
    @PUT
    @Path("/3DCaseDelete")
    public Response delete3DCase(@RequestParam("ids") String ids){
        return ApiResponseCustom.convert(caseindexService.delete3Dcase(ids));
    }

    @ApiOperation(value = "批量更新3dcase", notes = "批量更新3dcase")
    @ApiResponses({@ApiResponse(code = 200, message = "成功", response = Map.class),
            @ApiResponse(code = 400, message = "异常", response = ErrorMsgBean.class)})
    @PUT
    @Path("/3DCaseupdate")
    public Response update3DCase(@RequestParam("ids") String ids){
        return ApiResponseCustom.convert(caseindexService.update3dCase(ids));
    }


    @ApiOperation(value = "批量更新2dcase", notes = "批量更新2dcase")
    @ApiResponses({@ApiResponse(code = 200, message = "成功", response = Map.class),
            @ApiResponse(code = 400, message = "异常", response = ErrorMsgBean.class)})
    @PUT
    @Path("/2DCaseupdate")
    public Response update2DCase(@RequestParam("ids") String ids){
        return ApiResponseCustom.convert(caseindexService.update3dCase(ids));
    }

}
