package com.example.organizationservice.service;

import java.util.Optional;
import java.util.UUID;

import com.example.organizationservice.events.source.SimpleSourceBean;
import com.example.organizationservice.model.Organization;
import com.example.organizationservice.repository.OrganizationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {
    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private SimpleSourceBean simpleSourceBean;

    public Organization getOrg(String organizationId) {
        Optional<Organization> organization = organizationRepository.findById(organizationId);
        if(!organization.isPresent()){
            throw new NullPointerException("organizationId-" + organizationId);
        }
        return organization.get();
    }

    public void saveOrg(Organization org){
        org.withId(UUID.randomUUID().toString());
        organizationRepository.save(org);
        simpleSourceBean.publishOrgChange("SAVE", org.getId());
    }
    
    public void updateOrg(Organization org){
        organizationRepository.save(org);
        simpleSourceBean.publishOrgChange("UPDATE", org.getId());
    }

    public void delelteOrg(String orgId){
        organizationRepository.deleteById(orgId);
        simpleSourceBean.publishOrgChange("DELETE", orgId);
    }
}
