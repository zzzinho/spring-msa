package com.example.licensingservice.controller;

import com.example.licensingservice.model.License;
import com.example.licensingservice.service.LicenseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {
    @Autowired
    private LicenseService LicenseService;

    @RequestMapping(value = "/{licenseId}", method = RequestMethod.GET)
    public License getLicenses(@PathVariable("organizationId") String organizationId, @PathVariable("licenseId") String licenseId){
        return new License()
            .withId(licenseId)
            .withOrganizationId(organizationId)
            .withProductName("Telco")
            .withLicenseType("Seat");
    }

    @RequestMapping(value = "{licenseId}", method = RequestMethod.PUT)
    public String updateLicenses(@PathVariable("licenseId") String licenseId){
        return String.format("this is the put");
    }

    @RequestMapping(value = "{licenseId}", method = RequestMethod.POST)
    public String saveLicense(@PathVariable("licenseId") String licenseId){
        return String.format("this is post");
    }

    @RequestMapping(value = "{licenseId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteLicense(@PathVariable("licenseId") String licenseId){
        return String.format("this is delete");
    }

    @RequestMapping(path = "/{licenseId}/[clientType]", method = RequestMethod.GET)
    public License getLicenseWithClient(@PathVariable("organizationId") String organizationId, @PathVariable("licenseId") String licenseId, @PathVariable("clientType") String clientType){
        return LicenseService.getLicense(organizationId, licenseId, clientType);
    }
}
