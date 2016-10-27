package com.esiee.mbdaihm.dataaccess.wdi;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Raw class used to retrieve indicators for the WDB WDI dedicated file.
 * @author N. Mortiers
 */
public class RawWDIIndicator
{
    
    @JsonProperty("Series Code")
    public String code;
    
    @JsonProperty("Topic")
    public String topic;
    
    @JsonProperty("Indicator Name")
    public String name;
    
    @JsonProperty("Long definition")
    public String description;
    
}
