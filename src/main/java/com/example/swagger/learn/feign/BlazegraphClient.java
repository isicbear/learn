package com.example.swagger.learn.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "blazegraph",url = "${blaze.url}")
public interface BlazegraphClient {

    @RequestMapping(value ="/namespace/scistor/sparql",method = RequestMethod.GET)
    String read(@RequestParam("query") String query, @RequestHeader Map<String,String> header);

}
