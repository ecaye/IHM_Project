package com.esiee.mbdaihm;

import com.esiee.mbdaihm.dataaccess.geojson.NEGeoJsonDecoder;
import com.esiee.mbdaihm.dataaccess.geojson.RawCountry;
import com.esiee.mbdaihm.dataaccess.wdi.RawWDIData;
import com.esiee.mbdaihm.dataaccess.wdi.WDIDataDecoder;
import com.esiee.mbdaihm.dataaccess.wdi.WDIIndicatorsDecoder;
import com.esiee.mbdaihm.datamodel.DataManager;
import com.esiee.mbdaihm.datamodel.countries.Country;
import com.esiee.mbdaihm.datamodel.indicators.Indicator;
import com.esiee.mbdaihm.view.WorldApp;
import java.io.File;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;

/**
 * Application entry point
 *
 * @author Nicolas M.
 */
public class Launch
{
    private static final String COUNTRIES_FILE = "./data/ne_50m_admin_0_countries.json";

    private static final String WDI_FOLDER = "./data/WDI";

    private static void populateCountries()
    {
        List<RawCountry> rawData = NEGeoJsonDecoder.parseFile(new File(COUNTRIES_FILE));

        System.err.println("Parsed " + rawData.size() + " countries.");
        List<Country> countries = NEGeoJsonDecoder.convert(rawData);
        System.err.println("Converted " + countries.size() + " countries.");

        DataManager.INSTANCE.setCountries(countries);
    }

    private static void populatesIndicators()
    {
        List<Indicator> indicators = WDIIndicatorsDecoder.decode(WDI_FOLDER);

        System.err.println("Parsed " + indicators.size() + " indicators.");

        WDIIndicatorsDecoder.categoriseIndicators(indicators);
    }
    
    private static void populatesData()
    {
        List<Indicator> indicators = WDIIndicatorsDecoder.decode(WDI_FOLDER);
        for (Indicator indicator : indicators) {
            List<RawWDIData> data = WDIDataDecoder.decode(WDI_FOLDER, indicator.getCode());
            System.err.println("Parsed " + data.size() + " datas." + " for indicator : " + indicator.getCode());
        }
    }

    private static double getLifeExpectancyByYear(String year){
        List<RawWDIData> womenExpectancy = WDIDataDecoder.decode(WDI_FOLDER,"SP.DYN.LE00.FE.IN");
        womenExpectancy.stream()
                            .filter( rd-> !rd.countryCode.equals("ITA") )
//                            .map(rd -> rd.getValueForYear(year))
                            .forEach(System.out::println);
        
        //opt.ifPresent(System.out::println);
//        return opt.get();
        
        Country kinderCountry = new Country.Builder().withName("Italy").build();
        return 0;
    }
    
    private static void exo1()
    {
        List<RawWDIData> lifeExpectancyWomen
                = WDIDataDecoder.decode(WDI_FOLDER, "SP.DYN.LE00.FE.IN");

        // Façon simple
        Optional<Double> opt = lifeExpectancyWomen.stream().
                filter(rd -> rd.countryCode.equals("ITA")).
                map((RawWDIData rd) -> rd.getValueForYear("1982"))
                .findFirst();
        opt.ifPresent(System.out::println);

        RawWDIData data = lifeExpectancyWomen.stream().
                filter(rd -> rd.countryCode.equals("ITA")).
                findFirst().
                orElseThrow(IllegalStateException::new);

        /*Country c = new Country.Builder().withName("Italy").build();

        //c.setValues(data.getValuesArray());

        System.err.println("----------------------- EXO 1 ---------------------------------");
        for (int i = 1960; i < 2016; i++)
        {
            System.err.println(i + " -> " + c.getValueForYear(i));
        }*/
    }
    
        private static void exo2()
    {
        // http://data.worldbank.org/indicator/EN.POP.DNST?locations=MO

        List<RawWDIData> density = WDIDataDecoder.decode(WDI_FOLDER, "EN.POP.DNST");

        RawWDIData maxData = density.stream().
                reduce((rw1, rw2) -> rw2.getValueForYear("2014") > rw1.getValueForYear("2014") ? rw2 : rw1).
                orElseThrow(IllegalStateException::new);

        System.err.println("----------------------- EXO 2 ---------------------------------");

        System.err.println("Max found at " + maxData.getValueForYear("2014") + " for country " + maxData.countryCode);

        DoubleSummaryStatistics stats = density.stream().
                mapToDouble(rd -> rd.getValueForYear("2014")).
                filter(d -> !(Double.isNaN(d))).
                summaryStatistics();

        System.err.println("MAX Value by summary stats = " + stats.getMax());
    }
    
    private static double getHighestDensity(String year){
        List<RawWDIData> womenExpectancy = WDIDataDecoder.decode(WDI_FOLDER,"SP.DYN.LE00.FE.IN");
        Optional<Double> opt = womenExpectancy.stream()
                            .filter(rd->rd.countryCode.equals("ITA"))
                            .map(rd -> rd.getValueForYear(year))
                            .findFirst();
        
        //opt.ifPresent(System.out::println);
        return opt.get();
        
        //Country kinderCountry = new Country.Builder().withName("Italy").build();
        
    }
    /**
     * Application entry point.
     *
     * @param args no parameter used
     */
    public static void main(String[] args)
    {
        populateCountries();
        populatesIndicators();
        //populatesData();
        WorldApp app = new WorldApp();
        app.getMenuLeft().init();
        app.setVisible(true);

    }
    
    
}
