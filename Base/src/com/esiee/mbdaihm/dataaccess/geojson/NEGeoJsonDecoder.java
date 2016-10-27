package com.esiee.mbdaihm.dataaccess.geojson;

import com.esiee.mbdaihm.datamodel.countries.Country;
import com.esiee.mbdaihm.datamodel.countries.Geometry;
import com.esiee.mbdaihm.datamodel.countries.Polygon;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class dedicated to parse GeoJSON data. Data from Natural Earth : http://www.naturalearthdata.com Conversion to JSON
 * format done online with : http://newconverter.mygeodata.eu
 *
 * @author Nicolas M.
 */
public final class NEGeoJsonDecoder
{

    // --------------------------------------------
    // CONSTRUCTOR
    // --------------------------------------------
    //<editor-fold defaultstate="expanded" desc="...">
    /**
     * Utility class empty private constructor.
     */
    private NEGeoJsonDecoder()
    {
    }
    //</editor-fold>

    // --------------------------------------------
    // STATIC METHODS
    // --------------------------------------------
    //<editor-fold defaultstate="expanded" desc="...">
    /**
     * Parse a input file with Jackson and create a list of RawCountry objects.
     *
     * @param input the JSON file to parse
     * @return the list of parsed data or null if a problem occurred
     */
    public static List<RawCountry> parseFile(File input)
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        List<RawCountry> result = null;

        try
        {
            result = mapper.readValue(input, new TypeReference<List<RawCountry>>()
                              {
                              });
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return result;
    }

    /**
     * Convert Jackson library decoded data into more application friendly model.
     *
     * @param in the list of RawCountry decoded instances
     * @return a list of converted Country instances
     */
    public static List<Country> convert(List<RawCountry> in)
    {
        return in.stream()
                // .filter(c -> c.properties.scalerank < 10) Not very useful considering data
                .map(c ->
                        {
                            Country country = new Country.Builder()
                                    .withName(c.properties.admin)
                                    .withIsoCode(c.properties.iso_a3)
                                    .withRegion(c.properties.region_wb)
                                    .withSubRegion(c.properties.subregion)
                                    .build();

                            if (c.geometry.type.equals("Polygon"))
                            {
                                // Only one polygon, safely take index 0
                                List<Object> jsonPoly = c.geometry.coordinates.get(0);

                                Polygon poly = new Polygon(jsonPoly);

                                Geometry geo = new Geometry();
                                geo.addPolygon(poly);
                                country.setGeometry(geo);
                            }
                            else // Multi polygons
                            {
                                int count = c.geometry.coordinates.size();
                                Geometry geo = new Geometry();

                                for (int i = 0; i < count; i++)
                                {
                                    // Count lists of 1 element !
                                    List<Object> jsonPoly = (List<Object>) c.geometry.coordinates.
                                            get(i).get(0);

                                    Polygon poly = new Polygon(jsonPoly);
                                    geo.addPolygon(poly);
                                }

                                country.setGeometry(geo);
                            }
                            return country;

                        }).collect(Collectors.toList());
    }

    //</editor-fold>
}
