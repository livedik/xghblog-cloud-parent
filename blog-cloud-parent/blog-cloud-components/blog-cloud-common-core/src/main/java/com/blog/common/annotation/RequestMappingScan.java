package com.blog.common.annotation;


import com.blog.common.constants.QueueConstant;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;


/**
 * 自定义注解扫描
 *
 * @author xiaoguanhua
 */
@Slf4j
public class RequestMappingScan implements ApplicationListener<ApplicationReadyEvent> {


    private AmqpTemplate amqpTemplate;

    public RequestMappingScan(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();

        ConfigurableEnvironment environment = applicationContext.getEnvironment();

        // 所有接口映射
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = mapping.getHandlerMethods();

//        api 容器
        List<Map<String,String>> list = new ArrayList<>();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> map : handlerMethods.entrySet()) {
            RequestMappingInfo info = map.getKey();
            HandlerMethod method = map.getValue();

            if (method.getMethodAnnotation(ApiIgnore.class) != null) {
                // 忽略的接口不扫描
                continue;
            }

            Map<String,String> api = new HashMap<>();

            RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();

            Set<RequestMethod> methods = methodsCondition.getMethods();

//          服务ID
            String serviceId = environment.getProperty("spring.application.name");
//            请求方法
            String requestMethod = getMethods(methods);
//            请求路径
            Set<String> urls = info.getPatternsCondition().getPatterns();
//            请求路径
            String url = getUrls(urls);
//            类名
            String className = method.getMethod().getDeclaringClass().getName();
//            方法名
            String methodName = method.getMethod().getName();
            // md5码
            String md5 = DigestUtils.md5Hex(serviceId + url + methodName);
//            方法描述
            String desc = null;
//            接口名称
            String name = null;
            ApiOperation apiOperation = method.getMethodAnnotation(ApiOperation.class);
            if (apiOperation != null)
            {
                name = apiOperation.value();
                desc = apiOperation.notes();
            }else {
                name = "该接口没有名称";
                desc = "该接口没描述";
            }

            api.put("apiCode",md5);
            api.put("path",url);
            api.put("serverId",serviceId);
            api.put("apiDesc",desc);
            api.put("requestMethod",requestMethod);
            api.put("className",className);
            api.put("methodName",methodName);
            api.put("apiName",name);

            list.add(api);


        }

        Map resource = new HashMap<String,Object>();
        resource.put("mapping",list);
        amqpTemplate.convertAndSend(QueueConstant.QUEUE_SCAN_API_RESOURCE,resource);

    }

    private String getMediaTypes(Set<MediaType> mediaTypes) {
        StringBuilder sbf = new StringBuilder();
        for (MediaType mediaType : mediaTypes) {
            sbf.append(mediaType.toString()).append(",");
        }
        if (mediaTypes.size() > 0) {
            sbf.deleteCharAt(sbf.length() - 1);
        }
        return sbf.toString();
    }

    private String getMethods(Set<RequestMethod> requestMethods) {
        StringBuilder sbf = new StringBuilder();
        for (RequestMethod requestMethod : requestMethods) {
            sbf.append(requestMethod.toString()).append(",");
        }
        if (requestMethods.size() > 0) {
            sbf.deleteCharAt(sbf.length() - 1);
        }
        return sbf.toString();
    }


    private String getUrls(Set<String> urls)
    {
        StringBuilder sbd = new StringBuilder();
        for (String url : urls) {
            sbd.append(url).append(",");
        }
        if (urls.size() > 0)
        {
//            去掉 最后的,号
            sbd.deleteCharAt(sbd.length() - 1);
        }
        return sbd.toString();
    }
}
