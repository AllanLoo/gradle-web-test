package com.isp.common.utils;

import com.isp.common.config.Global;
import org.apache.commons.lang3.Validate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * HttpServlet工具类
 * Created by allan on 15-6-16.
 */
public class HttpServletUtils {
    public static final long  ONE_YEAR_SECONDS = 60 * 60 * 24 * 365;

    private final static String[] staticFiles = StringUtils.split(Global.getConfig("web.staticFile"),",");

    /**
     * 获取根据fields里的值获取HttpServletRequest请求的参数，获取不到存NULL
     * @param fields 需要获取的参数名称
     * @param req
     * @param isContainNull 是否包含空值，true 返回的map里存在空值，false返回的map不存在空值
     * @return
     */
    public static Map<String,Object> parseReqToMap(String[] fields,HttpServletRequest req,boolean isContainNull){
        Map<String,Object> entry = new HashMap<String,Object>();
         if(fields != null){
             for(String field:fields){
                 if(isContainNull){
                     entry.put(field,req.getParameter(field));
                 }else{
                     if(req.getParameter(field)!=null){
                         entry.put(field,req.getParameter(field));
                     }
                 }
             }
         }
        return entry;
    }

    /**
     * 设置客户端缓存过期时间的Header
     * @param response
     * @param expiresSeconds
     */
    public static void setExpireHeader(HttpServletResponse response,long expiresSeconds) {
        response.setDateHeader("Expires",System.currentTimeMillis() + expiresSeconds * 1000);
        response.setHeader("Cache-control","private, max-age=" + expiresSeconds);
    }

    /**
     * 设置禁止客户端缓存
     * @param response
     */
    public static void setNoCacheHeader(HttpServletResponse response) {
        response.setDateHeader("Expires",1L);
        response.addHeader("Pragma","no-cache");
        response.setHeader("Cache-Control","no-cache,no-store,max-age=0");
    }

    /**
     * 设置LastModified Header
     * @param response
     * @param lastModifiedDate
     */
    public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate){
        response.setDateHeader("Last-Modified",lastModifiedDate);
    }

    /**
     * 设置Etag Header
     * @param response
     * @param etag
     */
    public static void setEtag(HttpServletResponse response,String etag){
        response.setHeader("ETag",etag);
    }

    /**
     * 根据浏览器If-Modified-since Header，计算是否已被修改
     * 如果无修改，checkIfModify返回false，设置304 not modify status
     * @param request
     * @param response
     * @param lastModified 内容最后修改的时间
     * @return
     */
    public static boolean checkIfModifiedSince(HttpServletRequest request,HttpServletResponse response,
                                               long lastModified){
        long ifModifiedSince = request.getDateHeader("If-Modified-Since");
        if((ifModifiedSince != -1) && (lastModified < ifModifiedSince + 1000)) {
            response.setStatus(304);
            return  false;
        }
        return true;
    }

    /**
     * 根据浏览器 If-None-Match Header, 计算Etag是否已无效.
     *
     * 如果Etag有效, checkIfNoneMatch返回false, 设置304 not modify status.
     *
     * @param etag 内容的ETag.
     */
    public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag) {
        String headerValue = request.getHeader("If-None-Match");
        if (headerValue != null) {
            boolean conditionSatisfied = false;
            if (!"*".equals(headerValue)) {
                StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");

                while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
                    String currentToken = commaTokenizer.nextToken();
                    if (currentToken.trim().equals(etag)) {
                        conditionSatisfied = true;
                    }
                }
            } else {
                conditionSatisfied = true;
            }

            if (conditionSatisfied) {
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                response.setHeader("ETag", etag);
                return false;
            }
        }
        return true;
    }

    /**
     * 设置让浏览器弹出下载对话框的Header.
     *
     * @param fileName 下载后的文件名.
     */
    public static void setFileDownloadHeader(HttpServletResponse response, String fileName) {
        try {
            // 中文文件名支持
            String encodedfileName = new String(fileName.getBytes(), "ISO8859-1");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");
        } catch (UnsupportedEncodingException e) {
            e.getMessage();
        }
    }

    /**
     * 取得带相同前缀的Request Parameters, copy from spring WebUtils.
     *
     * 返回的结果的Parameter名已去除前缀.
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
        Validate.notNull(request, "Request must not be null");
        Enumeration paramNames = request.getParameterNames();
        Map<String, Object> params = new TreeMap<String, Object>();
        String pre = prefix;
        if (pre == null) {
            pre = "";
        }
        while (paramNames != null && paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            if ("".equals(pre) || paramName.startsWith(pre)) {
                String unprefixed = paramName.substring(pre.length());
                String[] values = request.getParameterValues(paramName);
                if (values == null || values.length == 0) {
                    values = new String[]{};
                    // Do nothing, no values found at all.
                } else if (values.length > 1) {
                    params.put(unprefixed, values);
                } else {
                    params.put(unprefixed, values[0]);
                }
            }
        }
        return params;
    }

    /**
     * 组合Parameters生成Query String的Parameter部分,并在paramter name上加上prefix.
     *
     */
    public static String encodeParameterStringWithPrefix(Map<String, Object> params, String prefix) {
        StringBuilder queryStringBuilder = new StringBuilder();

        String pre = prefix;
        if (pre == null) {
            pre = "";
        }
        Iterator<Map.Entry<String, Object>> it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            queryStringBuilder.append(pre).append(entry.getKey()).append("=").append(entry.getValue());
            if (it.hasNext()) {
                queryStringBuilder.append("&");
            }
        }
        return queryStringBuilder.toString();
    }

    /**
     * 客户端对Http Basic验证的 Header进行编码.
     */
    public static String encodeHttpBasic(String userName, String password) {
        String encode = userName + ":" + password;
        return "Basic " + Encodes.encodeBase64(encode.getBytes());
    }

    /**
     * 是否是Ajax异步请求
     * @param request
     */
    public static boolean isAjaxRequest(HttpServletRequest request){

        String accept = request.getHeader("accept");
        String xRequestedWith = request.getHeader("X-Requested-With");

        // 如果是异步请求，则直接返回信息
        return ((accept != null && accept.indexOf("application/json") != -1
                || (xRequestedWith != null && xRequestedWith.indexOf("XMLHttpRequest") != -1)
                ));
    }

    /**
     * 获取当前请求对象
     * @return
     */
    public static HttpServletRequest getRequest(){
        try{
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        }catch(Exception e){
            return null;
        }
    }

    /**
     * 判断访问URI是否是静态文件请求
     * @throws Exception
     */
    public static boolean isStaticFile(String uri){
        if (staticFiles == null){
            try {
                throw new Exception("检测到“app.properties”中没有配置“web.staticFile”属性。配置示例：\n#静态文件后缀\n"
                        +"web.staticFile=.css,.js,.png,.jpg,.gif,.jpeg,.bmp,.ico,.swf,.psd,.htc,.crx,.xpi,.exe,.ipa,.apk");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (StringUtils.endsWithAny(uri, staticFiles) && !StringUtils.endsWithAny(uri, ".html")
                && !StringUtils.endsWithAny(uri, ".jsp") && !StringUtils.endsWithAny(uri, ".java")){
            return true;
        }
        return false;
    }
}
