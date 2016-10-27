package com.esiee.mbdaihm.datamodel.countries;

import java.util.HashMap;
import java.util.Map;

/**
 * Model class for countries.
 *
 * @author Nicolas M.
 */
public class Country
{
    // --------------------------------------------
    // ATTRIBUTES
    // --------------------------------------------
    //<editor-fold defaultstate="expanded" desc="...">
    private String name;

    private String region;

    private String subRegion;

    private String isoCode;

    private Geometry geometry;

    private Map<Integer, Double> indicatorValues;
    
    public Map<Integer, Double> getIndicatorValues(){
        return indicatorValues;
    }
    
    public void addIndicatorValue(Integer year, Double data){
        indicatorValues.put(year, data);
    }
    
    public void clearIndicatorValues(){
        indicatorValues.clear();
    }


    //</editor-fold>
    // --------------------------------------------
    // METHODS
    // --------------------------------------------
    //<editor-fold defaultstate="expanded" desc="...">
    public Geometry getGeometry()
    {
        return geometry;
    }

    public void setGeometry(Geometry geometry)
    {
        this.geometry = geometry;
    }

    public String getName()
    {
        return name;
    }

    public String getRegion()
    {
        return region;
    }

    public String getIsoCode()
    {
        return isoCode;
    }

    public String getSubRegion()
    {
        return subRegion;
    }
    //</editor-fold>

    // --------------------------------------------
    // BUILDER
    // --------------------------------------------
    //<editor-fold defaultstate="expanded" desc="...">
    public static final class Builder
    {
        private String name;

        private String region;

        private String subRegion;

        private String isoCode;

        private Geometry geometry;

        public Builder()
        {
        }

        public Builder withName(final String name)
        {
            this.name = name;
            return this;
        }

        public Builder withRegion(final String region)
        {
            this.region = region;
            return this;
        }

        public Builder withSubRegion(final String subRegion)
        {
            this.subRegion = subRegion;
            return this;
        }

        public Builder withIsoCode(final String isoCode)
        {
            this.isoCode = isoCode;
            return this;
        }

        public Builder withGeometry(final Geometry geometry)
        {
            this.geometry = geometry;
            return this;
        }

        public Country build()
        {
            final Country result = new Country();
            result.name = name;
            result.region = region;
            result.subRegion = subRegion;
            result.isoCode = isoCode;
            result.geometry = geometry;
            result.indicatorValues = new HashMap<>();
            return result;
        }

        public Builder mergeFrom(final Country src)
        {
            this.name = src.name;
            this.region = src.region;
            this.subRegion = src.subRegion;
            this.isoCode = src.isoCode;
            this.geometry = src.geometry;
            return this;
        }
    }
    //</editor-fold>

}
