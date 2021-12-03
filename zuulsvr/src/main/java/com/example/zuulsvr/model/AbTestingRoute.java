package com.example.zuulsvr.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbTestingRoute {
    String serviceName;
    String active; 
    String endpoint;
    Integer weight;
}
