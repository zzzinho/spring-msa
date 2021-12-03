package com.example.organizationservice.controller;

import com.example.organizationservice.model.Organization;
import com.example.organizationservice.service.OrganizationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/organization/v1")
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);
    
    @RequestMapping(path = "/{organizationId}", method = RequestMethod.GET)
    public Organization getOrganiation(@PathVariable("organizationId") String organizationId){
        return organizationService.getOrg(organizationId);
    }

    @RequestMapping(value="/{organizationId}", method=RequestMethod.PUT)
    public Organization updateOrgnization(@PathVariable("organizationId") String organizationId, @RequestBody Organization org) {
        organizationService.updateOrg(org);
        return org;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Organization svaeOrganization(@RequestBody Organization org){
        organizationService.saveOrg(org);
        return org;
    }

    @RequestMapping(value="/{organizationId}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization(@PathVariable("organizationId") String organizationId, @RequestBody Organization org) {
        organizationService.delelteOrg(org);
    }
    
    @RequestMapping(value = "/world", method = RequestMethod.GET)
    public String world(@RequestParam String world){
        logger.info("organization server: " + world);
        return world.toUpperCase();
    }
}