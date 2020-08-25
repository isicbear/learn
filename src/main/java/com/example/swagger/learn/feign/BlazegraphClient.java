package com.example.swagger.learn.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "blazegraph",url = "${blaze.url}")
public interface BlazegraphClient {

    @RequestMapping(value ="/namespace/{namespace}/sparql",method = RequestMethod.GET)
    String read(@RequestParam("query") String query,@PathVariable String namespace, @RequestHeader Map<String,String> header);

}
