package com.blog.admin.server.service.feign;

import com.blog.base.client.service.IBaseAccountServiceClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(value = "blog-cloud-base-server")
public interface BaseAccountServiceClient extends IBaseAccountServiceClient {

}
