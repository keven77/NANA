/*
 * FileName: SolrdumpException.java
 * Author:   liubb
 * Date:     2016年5月16日 下午9:50:30
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.juran.index.mdm.client.execption;

/**
 * 〈一句话功能简述〉<br>
 * 〈solrdump异常〉
 *
 * @author zhangyihang
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SolrdumpException extends RuntimeException {

    /**
     */
    private static final long serialVersionUID = 1507193782272776036L;

    public SolrdumpException() {
        super();
    }

    public SolrdumpException(String msg) {
        super(msg);
    }

    public SolrdumpException(Throwable cause) {
        super(cause);
    }

    public SolrdumpException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
