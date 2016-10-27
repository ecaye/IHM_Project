package com.esiee.mbdaihm.datamodel.indicators;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;

/**
 * Data model class representing a World Data Index indicator.
 *
 * @author N. Mortier
 */
public class Indicator
{
    // --------------------------------------------
    // ATTRIBUTES
    // --------------------------------------------
    //<editor-fold defaultstate="expanded" desc="...">
    private String topic;

    private String subTopic;
    
    private String description;

    private String name;

    private String code;

    private EIndicatorType type;

    private DoubleSummaryStatistics stats;
    
    private Map<Integer, Double> statsByYear;
    //</editor-fold>

    // --------------------------------------------
    // GETTERS
    // --------------------------------------------
    //<editor-fold defaultstate="expanded" desc="...">
    public String getTopic()
    {
        return topic;
    }

    public String getSubTopic()
    {
        return subTopic;
    }

    public String getName()
    {
        return name;
    }

    public String getCode()
    {
        return code;
    }

    public EIndicatorType getType()
    {
        return type;
    }
    
    public Map<Integer, Double> getStatsMap(){
        return statsByYear;
    }

    /**
     * Retrieve statistics related to this indicator.
     *
     * @return
     */
    public DoubleSummaryStatistics getStats()
    {
        return stats;
    }

    //</editor-fold>
    @Override
    public String toString()
    {
        return code + " : " + name;
    }

    // --------------------------------------------
    // SETTERS
    // --------------------------------------------
    //<editor-fold defaultstate="expanded" desc="...">
    public void setStats(DoubleSummaryStatistics stats)
    {
        this.stats = stats;
    }
    //</editor-fold>

    public String getDescription() {
        return description;
    }

    // --------------------------------------------
    // INNER CLASS -- BUILDER
    // --------------------------------------------
    //<editor-fold defaultstate="expanded" desc="..."> 
    public static final class Builder
    {
        private String topic;

        
        private String subTopic;

        private String name;

        private String description;

        private String code;

        private EIndicatorType type;

        public Builder()
        {
        }

        public Builder withTopic(final String topic)
        {
            this.topic = topic;
            return this;
        }

        public Builder withSubTopic(final String subTopic)
        {
            this.subTopic = subTopic;
            return this;
        }

        public Builder withName(final String name)
        {
            this.name = name;
            return this;
        }

        public Builder withCode(final String code)
        {
            this.code = code;
            return this;
        }
        
        public Builder withDescription(final String description)
        {
            this.description = description;
            return this;
        }
        public Builder withType(final EIndicatorType type)
        {
            this.type = type;
            return this;
        }

        public Indicator build()
        {
            final Indicator result = new Indicator();
            result.topic = topic;
            result.subTopic = subTopic;
            result.description = description;
            result.name = name;
            result.code = code;
            result.type = type;
            return result;
        }

        public Builder mergeFrom(final Indicator src)
        {
            this.topic = src.topic;
            this.subTopic = src.subTopic;
            this.name = src.name;
            this.code = src.code;
            return this;
        }
    }
    //</editor-fold>·

}
