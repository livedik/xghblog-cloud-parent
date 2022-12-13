package com.blog.base.server.listener;


import com.alibaba.fastjson.JSONObject;
import com.blog.base.client.model.entity.BaseApi;
import com.blog.base.client.model.entity.BaseAuthority;
import com.blog.base.server.service.BaseApiService;
import com.blog.base.server.service.BaseAuthorityService;
import com.blog.common.constants.QueueConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.List;
import java.util.Map;

@Configuration
@Slf4j
public class ResourceScanHandler {

    @Autowired
    private BaseApiService baseApiService;

    @Autowired
    private BaseAuthorityService baseAuthorityService;


    /***
     * 资源 API 扫描 持久化
     * @param jsonObject
     */
    @RabbitListener(queues = QueueConstant.QUEUE_SCAN_API_RESOURCE)
    public void resourceScan(@Payload JSONObject jsonObject)
    {

        List<Map<String,Object>> mappingList = (List<Map<String,Object>>)jsonObject.get("mapping");

        for (Map<String, Object> api : mappingList) {
            BaseApi baseApi = new BaseApi();
            try {
                BeanUtils.populate(baseApi,api);
            } catch (Exception e) {
                baseApi = new BaseApi();
            }
//            查询是否已经存在改接口 code
            BaseApi selectApi = baseApiService.getApi(baseApi.getApiCode());
            if (selectApi == null)
            {
                baseApiService.save(baseApi);
//                添加权限数据
                BaseAuthority baseAuthority = new BaseAuthority();
//                Api ID
                baseAuthority.setApiId(baseApi.getApiId());
//                设置激活状态
                baseAuthority.setStatus(1);
                baseAuthorityService.save(baseAuthority);
            }
        }


    }
}
