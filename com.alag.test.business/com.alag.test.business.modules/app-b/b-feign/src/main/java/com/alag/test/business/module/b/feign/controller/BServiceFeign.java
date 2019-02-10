package com.alag.test.business.module.b.feign.controller;

import com.alag.test.business.module.b.api.BController;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FeignClient("app-b")
public interface BServiceFeign extends BController {
}
