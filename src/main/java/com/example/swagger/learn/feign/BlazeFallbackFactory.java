package com.example.swagger.learn.feign;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 降级 hystrix 进行容错保护的一种机制 此外还有资源隔离 熔断 缓存 机制
 * 降级是为了返回托底数据
 */
@Slf4j
@Component
public class BlazeFallbackFactory implements FallbackFactory<BlazegraphClient> {

    @Autowired
    private BlazegraphClient blazegraphClient;
    @Value("${blaze.namespace}")
    private String defaultNamespace;

    @Override
    public BlazegraphClient create(Throwable throwable) {

        return new BlazegraphClient() {
            // todo 这样能确保当命名空间找不到时可以 可以返回默认命名空间的数据
            @Override
            public String read(String query, String namespace, Map<String, String> header) {
                log.error("未找到命名空间：" + namespace + "; 返回默认命名空间的数据");
                return blazegraphClient.read(query, defaultNamespace, header);
            }
        };
    }
}
