package com.example.licensingservice.service;

import java.util.List;
import java.util.UUID;

import com.example.licensingservice.clients.OrganizationDiscoveryClient;
import com.example.licensingservice.clients.OrganizationFeignClient;
import com.example.licensingservice.clients.OrganizationRestTemplateClient;
import com.example.licensingservice.config.ServiceConfig;
import com.example.licensingservice.model.License;
import com.example.licensingservice.model.Organization;
import com.example.licensingservice.repository.LicenseRepository;

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

    @Autowired
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Organization retrieveOrgInfo(String organizationId, String clientType) {
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
        Organization org = retrieveOrgInfo(organizationId, clientType);
        
        return license
            .withOrganizationName(org.getName())
            .withContactName(org.getContactName())
            .withContactEmail(org.getContactEmail())
            .withContactPhone(org.getContactPhone())
            .withCommnet(config.getExampleProperty());
    }

    public List<License> getLicensesByOrg(String organizationId){
        return licenseRepository.findByOrganizationId(organizationId);   
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
