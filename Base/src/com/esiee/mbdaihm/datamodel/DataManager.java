package com.esiee.mbdaihm.datamodel;

import com.esiee.mbdaihm.datamodel.countries.Country;
import com.esiee.mbdaihm.datamodel.countries.WorldRegions;
import com.esiee.mbdaihm.datamodel.indicators.Indicator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Singleton, following enum pattern, used to easily store and retrieve data.
 *
 * @author Nicolas M.
 */
public enum DataManager
{
    INSTANCE;

    // --------------------------------------------
    // ATTRIBUTES
    // --------------------------------------------
    //<editor-fold defaultstate="expanded" desc="...">
    private List<Country> countries;

    private Map<String, Country> countriesMap;

    private WorldRegions regions;

    // Topic, subtopic and indicators.
    private final Map<String, Map<String, List<Indicator>>> indicatorsMap = new TreeMap<>();

    //@TODO : To remove depending on choices made for the applications
    private Indicator currentIndicator;
    
    private int currentYear;

    public int getCurrentYear(){
        return currentYear;
    }
    public void setCurrentYear(int value) {
        currentYear = value;
    }
    //</editor-fold>
    // --------------------------------------------
    // METHODS
    // --------------------------------------------
    //<editor-fold defaultstate="expanded" desc="...">
    /**
     * Retrieve the list of countries.
     *
     * @return
     */
    public List<Country> getCountries()
    {
        return countries;
    }

    public WorldRegions getWorldRegions()
    {
        return regions;
    }

    /**
     *
     * @param countries
     */
    public void setCountries(List<Country> countries)
    {
        countriesMap = countries.stream().
                filter(c -> !c.getIsoCode().equals("-99")).
                collect(Collectors.toMap(c -> c.getIsoCode(), Function.identity()));

        long validCountryCodes = countries.stream().filter(c -> !(c.getIsoCode().equals("-99"))).count();

        System.err.println("Stored " + validCountryCodes + " countries with valid code");

        this.countries = countries;
        regions = new WorldRegions(countries);
    }

    public Map<String, Map<String, List<Indicator>>> getIndicatorsMap()
    {
        return indicatorsMap;
    }

    public Country getCountryByCode(String code)
    {
        return countriesMap.get(code);
    }

    public Indicator getCurrentIndicator()
    {
        return currentIndicator;
    }

    public void setCurrentIndicator(Indicator currentIndicator)
    {
        this.currentIndicator = currentIndicator;
    }
    
    public double getMax()
    {
        double max = 0.;
        if(countries == null){
            return Double.NaN;
        }
        
        for (Country country : countries) {
            if(!country.getIndicatorValues().isEmpty()){
                if(max < country.getIndicatorValues().get(currentYear))
                    max = country.getIndicatorValues().get(currentYear);
                
            }
        }
        return max;
    }
    
    public double getMedian(int num){
        ArrayList<Double> list = new ArrayList<>();
        double max = 0.;
        if(countries == null){
            return Double.NaN;
        }
        
        for (Country country : countries) {
            if(!country.getIndicatorValues().isEmpty()){
                if(!country.getIndicatorValues().get(currentYear).isNaN())
                    list.add((double)country.getIndicatorValues().get(currentYear));            
            }
        }
        Collections.sort(list);
        int index = 0;
        if(num == 1)
            index = list.size()/3;
        else
            index = (list.size()/3)*2;
        return list.get(index);
    }
}
