package com.example.licensingservice.service;

import java.util.List;
import java.util.UUID;

import com.example.licensingservice.config.ServiceConfig;
import com.example.licensingservice.model.License;
import com.example.licensingservice.repository.LicenseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LicenseService {
    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    ServiceConfig config;
    
    public License getLicense(String organizationId, String licenseId){
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        return license.withCommnet(config.getExampleProperty());
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
