package com.max.framework.util.webservice;

import javax.xml.ws.WebServiceException;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

/**
 * 文档webservice客户端工具类
 * @author wy
 */
public class WebServiceConnector {

    /**
     * 客户端工具类发送webService工具类
     * @param <T> 指定类型
     * @param type 指定类型
     * @param methodName webservice方法名
     * @param params 参数列表
     * @return T 对应类型返回
     * @throws WebServiceException 异常
     */
    public static String sendWebServiceRequest(String webserviceUri, String methodName, Object... params) {

        try {
            JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();
            Client client = clientFactory.createClient(webserviceUri);

            // 设置超时单位为毫秒
            HTTPConduit http = (HTTPConduit) client.getConduit();
            HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
            // 连接超时 120秒
            httpClientPolicy.setConnectionTimeout(120000);
            // 取消块编码
            httpClientPolicy.setAllowChunking(false);
            // 响应超时 120秒
            httpClientPolicy.setReceiveTimeout(120000);
            http.setClient(httpClientPolicy);

            Object[] result = client.invoke(methodName, params);
            return result[0].toString();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "";
    }
}
