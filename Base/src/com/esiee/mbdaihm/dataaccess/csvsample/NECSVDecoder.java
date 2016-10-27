package com.esiee.mbdaihm.dataaccess.csvsample;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.File;
import java.io.IOException;

/**
 * Sample class showing the use of Jackson to parse CSV data.
 */
public class NECSVDecoder
{
    private static final String COUNTRIES_FILE = "./data/ne_10m_admin_0_countries.csv";

    public static void main(String[] args) throws IOException
    {
        CsvSchema headerSchema = CsvSchema.emptySchema().withHeader();

        CsvMapper mapper = new CsvMapper();

        MappingIterator<CSVCountry> readValues = mapper.reader(CSVCountry.class).with(headerSchema).
                without(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).
                readValues(new File(COUNTRIES_FILE));

        while (readValues.hasNext())
        {
            CSVCountry country = readValues.next();

            System.err.println("Country : " + country.SOVEREIGNT + " -- code : " + country.SOV_A3);
        }

    }
}
