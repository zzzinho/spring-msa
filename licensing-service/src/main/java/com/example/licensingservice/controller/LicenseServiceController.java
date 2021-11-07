package com.example.licensingservice.controller;

import java.util.List;

import com.example.licensingservice.model.License;
import com.example.licensingservice.service.LicenseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {
    @Autowired
    private LicenseService licenseService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<License> getLicenses(@PathVariable("organizationId") String organizationId){
        return licenseService.getLicensesByOrg(organizationId);
    }

    @RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
    public License getLicense(@PathVariable("organizationId") String organizationId, @PathVariable("licenseId") String licenseId){
        return licenseService.getLicense(organizationId, licenseId, "rest");
    }

    @RequestMapping(value = "{licenseId}", method = RequestMethod.PUT)
    public String updateLicenses(@PathVariable("licenseId") String licenseId){
        return String.format("this is the put");
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<License> saveLicense(@PathVariable("organizationId") String organizationId, @RequestBody License license){
        license.withOrganizationId(organizationId);
        licenseService.saveLicense(license);
        return ResponseEntity.ok().body(license);
    }

    @RequestMapping(value = "{licenseId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteLicense(@PathVariable("licenseId") String licenseId){
        return String.format("this is delete");
    }

    @RequestMapping(path = "/{licenseId}/[clientType]", method = RequestMethod.GET)
    public License getLicenseWithClient(@PathVariable("organizationId") String organizationId, @PathVariable("licenseId") String licenseId, @PathVariable("clientType") String clientType){
        return licenseService.getLicense(organizationId, licenseId, clientType);
    }
}
