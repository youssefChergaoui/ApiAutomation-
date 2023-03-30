package com.example.swaggerprj.models;

import com.example.swaggerprj.utils.Constants;

import static com.example.swaggerprj.utils.Constants.THROTTLING_POLICIES;

public class AdditionalProperties {

    private String name;
    private String context;
    private String version;
    private boolean isDefaultVersion;
    private String[] policies = Constants.THROTTLING_POLICIES;
    private EndpointConfig endpointConfig;


    public String[] getPolicies() {
        return policies;
    }

    public AdditionalProperties(String name, String context, String version) {
        this.name = name;
        this.context = context;
        this.version = version;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setContext(String context) {
        this.context = context;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public void setPolicies(String[] policies) { this.policies = policies; }
    public void setEndpointConfig(EndpointConfig endpointConfig){ this.endpointConfig=endpointConfig; }
    public String getName() {
        return name;
    }
    public String getContext() {
        return context;
    }
    public String getVersion() {
        return version;
    }
    public EndpointConfig getEndpointConfig(){ return endpointConfig; }


}
