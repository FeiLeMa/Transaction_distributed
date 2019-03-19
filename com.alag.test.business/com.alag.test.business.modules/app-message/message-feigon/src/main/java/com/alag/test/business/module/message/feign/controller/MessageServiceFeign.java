package com.alag.test.business.module.message.feign.controller;

import com.alag.test.business.module.message.api.MessageController;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RestController;

@FeignClient("message-api")
@RestController
public interface MessageServiceFeign extends MessageController {
}
