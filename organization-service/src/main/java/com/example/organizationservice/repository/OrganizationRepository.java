package com.example.organizationservice;

import com.example.organizationservice.model.Organization;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, String> {
    
}
