package com.example.organizationservice.service;

import java.util.Optional;

import com.example.organizationservice.model.Organization;
import com.example.organizationservice.repository.OrganizationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {
    @Autowired
    private OrganizationRepository organizationRepository;

    public Organization getOrg(String organizationId) {
        Optional<Organization> organization = organizationRepository.findById(organizationId);
        if(!organization.isPresent()){
            throw new NullPointerException("organizationId-" + organizationId);
        }
        return organization.get();
    }

    public void saveOrg(Organization org){
        organizationRepository.save(org);
    }
    
    public void updateOrg(Organization org){
        organizationRepository.save(org);
    }

    public void delelteOrg(Organization org){
        organizationRepository.delete(org);
    }
}
