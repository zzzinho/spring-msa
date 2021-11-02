package com.example.organizationservice.controller;

import com.example.organizationservice.model.Organization;
import com.example.organizationservice.service.OrganizationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "v1/organizations")
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    @RequestMapping(path = "/{organizationId}", method = RequestMethod.GET)
    public Organization getOrganiation(@PathVariable("organizationId") String organizationId){
        return organizationService.getOrg(organizationId);
    }

    @RequestMapping(value="/{organizationId}", method=RequestMethod.PUT)
    public void updateOrgnization(@PathVariable("organizationId") String organizationId, @RequestBody Organization org) {
        organizationService.updateOrg(org);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void svaeOrganization(@RequestBody Organization org){
        organizationService.saveOrg(org);
    }

    @RequestMapping(value="/{organizationId}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void requestMethodName(@PathVariable("organizationId") String organizationId, @RequestBody Organization org) {
        organizationService.delelteOrg(org);
    }
    
    
}
