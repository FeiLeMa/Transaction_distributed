package com.alag.test.business.module.a.feign.controller;

import com.alag.test.business.module.a.api.AController;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RestController;

@FeignClient("app-a")
@RestController
public interface AServiceFeign extends AController {
}
