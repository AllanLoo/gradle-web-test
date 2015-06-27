package com.isp.common.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;


/**
 *  编码解码工具类
 *  1.Commons-codec的hex/base64 编码
 *  2.自制的base62编码
 *  3.Commons-Lang的xml/html 编码
 *  4.JDK提供的URLEncoder
 * Created by allan on 15-6-20.
 */
public class Encodes {
    private static final String DEAULT_URL_ENCODING = "UTF-8";
    private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    /**
     * Hex编码
     * @param input
     * @return
     */
    public static String encodeHex(byte[] input){
        return new String(Hex.encodeHex(input));
    }

    /**
     * Hex解码
     * @param input
     * @return
     */
    public static byte[] decodeHex(String input){
        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException e) {
           throw Exceptions.unchecked(e);
        }
    }

    /**
     * Base64编码
     * @param input
     * @return
     */
    public static String encodeBase64(byte[]input){
        return new String(Base64.encodeBase64(input));
    }

    /**
     * Base64解码
     * @param input
     * @return
     */
    public static  byte[] decodeBase64(String input){
        return Base64.decodeBase64(input.getBytes());
    }

    /**
     * Base62编码
     * @param input
     * @return
     */
    public static String encodeBase62(byte[] input){
        char[] chars = new char[input.length];
        for(int i=0;i<input.length;i++){
            chars[i] = BASE62[((input[i] & 0xFF) % BASE62.length)];
        }
        return new String(chars);
    }

    /**
     * Html 转码
     * @param html
     * @return
     */
    public static String escapeHtml(String html){
        return StringEscapeUtils.escapeHtml4(html);
    }

    /**
     * Html 解码
     * @param htmlEscaped
     * @return
     */
    public static String unescapeHtml(String htmlEscaped){
        return StringEscapeUtils.unescapeHtml4(htmlEscaped);
    }

    /**
     * Xml 转码
     * @param xml
     * @return
     */
    public static String escapeXml(String xml){
        return StringEscapeUtils.escapeXml10(xml);
    }

    /**
     * Xml 解码
     * @param xmlEscaped
     * @return
     */
    public static String unescapeXml(String xmlEscaped){
        return StringEscapeUtils.unescapeXml(xmlEscaped);
    }

    /**
     * URL 编码，Encode默认为UTF-8
     * @param part
     * @return
     */
    public static String urlEncode(String part){
        try {
            return URLEncoder.encode(part,DEAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw Exceptions.unchecked(e);
        }
    }

    /**
     * URL 解码，Endcode默认为UTF-8
     * @param part
     * @return
     */
    public static String urlDecode(String part){
        try {
            return URLDecoder.decode(part,DEAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw Exceptions.unchecked(e);
        }
    }
}
