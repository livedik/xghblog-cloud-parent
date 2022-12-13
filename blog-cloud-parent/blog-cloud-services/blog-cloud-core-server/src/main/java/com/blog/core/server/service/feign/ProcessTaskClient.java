package com.blog.core.server.service.feign;

import com.blog.bpm.client.service.IProcessTaskClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component(value = "processTask")
@FeignClient("blog-cloud-bpm-server")
public interface ProcessTaskClient extends IProcessTaskClient {
}
