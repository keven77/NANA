package com.juran.index.mdm.app.resource.v1;

import com.juran.core.exception.bean.ErrorMsgBean;
import com.juran.core.log.contants.LoggerName;
import com.juran.core.log.eventlog.aop.RestLog;
import com.juran.index.mdm.app.service.PopulateService;
import com.juran.index.mdm.app.utils.ApiResponseCustom;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Api(value = "主材索引服务类")
@RestLog
@Component
@Path("/v1/populatecata")
@Produces(MediaType.APPLICATION_JSON)
public class PopulateResource {

    protected final transient Logger LOGGER = LoggerFactory.getLogger(LoggerName.INFO);

    @Autowired
    private PopulateService populateService;

    @ApiOperation(value = "构建全部索引", notes = "构建全部索引")
    @ApiResponses({@ApiResponse(code = 200, message = "成功", response = Map.class),
            @ApiResponse(code = 400, message = "异常", response = ErrorMsgBean.class)})
    @GET
    @Path("/")
    public Response fullPopulateCatalog() {
        return ApiResponseCustom.convert(populateService.fullPopulate());
    }

    @ApiOperation(value = "增量索引", notes = "增量索引")
    @ApiResponses({@ApiResponse(code = 200, message = "成功", response = Map.class),
            @ApiResponse(code = 400, message = "异常", response = ErrorMsgBean.class)})
    @GET
    @Path("/deltaBuild")
    public Response deltaBuild() {
        return ApiResponseCustom.convert(populateService.deltaCatentry());
    }

}
