package com.juran.index.mdm.app.utils;

import com.juran.core.log.contants.LoggerName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * 作者:xy<br>
 * 功能描述:str handle
 */
public class StrHandleUtil {
    private final static transient Logger LOGGER = LoggerFactory.getLogger(LoggerName.INFO);
    /**
     * 作者:xy<br>
     * 功能描述:InputStream :String
     */
    public static String in2Str(String filePath, String charset) {

        InputStream in = null;
        BufferedInputStream bis = null;
        InputStreamReader isr = null;
        StringBuffer sb = new StringBuffer();
        try {
            in = StrHandleUtil.class.getResourceAsStream(filePath);
            bis = new BufferedInputStream(in);
            isr = new InputStreamReader(bis, Charset.forName(charset));
            BufferedReader br = new BufferedReader(isr);
            while (br.ready()) {
                sb.append(br.readLine());
            }
        } catch (Exception e) {
            LOGGER.error("--in2Str--error={}", e);
        } finally {
            try {
                if(in!=null){
                    in.close();
                }
                if (bis != null) {
                    bis.close();
                }
                if (isr != null) {
                    isr.close();
                }
            } catch (Exception e) {
                LOGGER.error("--in2Str--error={}", e);
            }
        }
        LOGGER.debug("--in2Str--str={}", sb.toString());
        return sb.toString();
    }
}
