package com.example.licensingservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.example.licensingservice.clients.OrganizationDiscoveryClient;
import com.example.licensingservice.clients.OrganizationFeignClient;
import com.example.licensingservice.clients.OrganizationRestTemplateClient;
import com.example.licensingservice.config.ServiceConfig;
import com.example.licensingservice.model.License;
import com.example.licensingservice.model.Organization;
import com.example.licensingservice.repository.LicenseRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LicenseService {
    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    ServiceConfig config;
    
    @Autowired
    private OrganizationFeignClient organizationFeignClient;

    @Autowired
    private OrganizationRestTemplateClient organizationRestTemplateClient;

    @Autowired
    private OrganizationDiscoveryClient organizationDiscoveryClient;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private void randomlyRunLong(){
        Random rand = new Random();
        int randomNum = rand.nextInt((3-1)+1)+1;
        if(randomNum == 3) sleep();
    }
    private void sleep(){
        try{
            Thread.sleep(1100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @HystrixCommand
    private Organization getOrganization(String organizationId, String clientType) {
        Organization organization = null;
        switch(clientType){
            case "feign":
                logger.info("i am using the feign client");
                organization = organizationFeignClient.getOrganization(organizationId);
                break;
            case "rest":
                logger.info("i am using the rest client");
                organization = organizationRestTemplateClient.getOrganization(organizationId);
                break;
            case "discovery":
                logger.info("i am using the discovery clietn");
                organization = organizationDiscoveryClient.getOrganization(organizationId);
                break;
            default:
                organization = organizationRestTemplateClient.getOrganization(organizationId);
        }
        return organization;
    }

    public License getLicense(String organizationId, String licenseId, String clientType){
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        Organization org = getOrganization(organizationId, clientType);
        
        return license
            .withOrganizationName(org.getName())
            .withContactName(org.getContactName())
            .withContactEmail(org.getContactEmail())
            .withContactPhone(org.getContactPhone())
            .withCommnet(config.getExampleProperty());
    }
    
    @HystrixCommand(
        commandProperties = {
            // @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "75"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"),
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
            @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5"),
        },
        fallbackMethod = "buildFallbackLicenseList",
        threadPoolKey = "licenseByOrgThreadPool",
        threadPoolProperties = {
            @HystrixProperty(name="coreSize", value = "30"),
            @HystrixProperty(name = "maxQueueSize", value = "10")
        }
    )
    public List<License> getLicensesByOrg(String organizationId){
        // logger.debug("LicenseService.getLicenseByOrg Correlation id: {}", UserContextHolder.getContext().get);
        // randomlyRunLong();
        return licenseRepository.findByOrganizationId(organizationId);   
    }

    private List<License> buildFallbackLicenseList(String organizationId) {
        List<License> fallbackList = new ArrayList<>();
        License license = new License()
            .withId("0000000-00-00000")
            .withOrganizationId(organizationId)
            .withProductName(
                "Sorry no licensing information currently available"
            );
            fallbackList.add(license);
        return fallbackList;
    }
    public void saveLicense(License license){
        license.withId(UUID.randomUUID().toString());
        licenseRepository.save(license);
    }
    
    public void updateLicese(License license){
        licenseRepository.save(license);
    }

    public void deleteLicense(License license){
        licenseRepository.delete(license);
    }
}
