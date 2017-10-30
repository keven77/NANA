package com.juran.index.mdm.app.utils;


import com.juran.core.exception.bean.ErrorMsgBean;

import javax.ws.rs.core.Response;

public class ApiResponseCustom {
    public static Response convert(Object obj) {
        if (obj instanceof Boolean) {
            return Response.status(Response.Status.OK).entity(obj).build();
        } else if (obj instanceof ErrorMsgBean) {
            return Response.status(Response.Status.BAD_REQUEST).entity(obj).build();
        } else {
            return Response.status(Response.Status.OK).entity(obj).build();
        }
    }
}
