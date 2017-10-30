package com.juran.index.mdm.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableDiscoveryClient
@ImportResource({"classpath:job.xml","classpath:application-manager.xml","classpath:applicationContext-solr.xml"})
@ComponentScan(basePackages = {"com.juran"})
@EnableCircuitBreaker
public class IndexMdmApplication {
    public static void main(String[] args) {
        SpringApplication.run(IndexMdmApplication.class, args);
    }
}
