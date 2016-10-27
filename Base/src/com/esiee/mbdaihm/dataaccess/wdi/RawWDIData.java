package com.esiee.mbdaihm.dataaccess.wdi;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.reflect.Field;
import java.util.DoubleSummaryStatistics;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

/**
 * Raw class used to retrieve data content from the world data bank data file.
 *
 * @author N. Mortier
 */
public class RawWDIData
{
    @JsonProperty("Country Code")
    public String countryCode;

    @JsonProperty("Indicator Code")
    public String indicatorCode;

    @JsonProperty("1960")
    public String value1960;

    @JsonProperty("1961")
    public String value1961;

    @JsonProperty("1962")
    public String value1962;

    @JsonProperty("1963")
    public String value1963;

    @JsonProperty("1964")
    public String value1964;

    @JsonProperty("1965")
    public String value1965;

    @JsonProperty("1966")
    public String value1966;

    @JsonProperty("1967")
    public String value1967;

    @JsonProperty("1968")
    public String value1968;

    @JsonProperty("1969")
    public String value1969;

    @JsonProperty("1970")
    public String value1970;

    @JsonProperty("1971")
    public String value1971;

    @JsonProperty("1972")
    public String value1972;

    @JsonProperty("1973")
    public String value1973;

    @JsonProperty("1974")
    public String value1974;

    @JsonProperty("1975")
    public String value1975;

    @JsonProperty("1976")
    public String value1976;

    @JsonProperty("1977")
    public String value1977;

    @JsonProperty("1978")
    public String value1978;

    @JsonProperty("1979")
    public String value1979;

    @JsonProperty("1980")
    public String value1980;

    @JsonProperty("1981")
    public String value1981;

    @JsonProperty("1982")
    public String value1982;

    @JsonProperty("1983")
    public String value1983;

    @JsonProperty("1984")
    public String value1984;

    @JsonProperty("1985")
    public String value1985;

    @JsonProperty("1986")
    public String value1986;

    @JsonProperty("1987")
    public String value1987;

    @JsonProperty("1988")
    public String value1988;

    @JsonProperty("1989")
    public String value1989;

    @JsonProperty("1990")
    public String value1990;

    @JsonProperty("1991")
    public String value1991;

    @JsonProperty("1992")
    public String value1992;

    @JsonProperty("1993")
    public String value1993;

    @JsonProperty("1994")
    public String value1994;

    @JsonProperty("1995")
    public String value1995;

    @JsonProperty("1996")
    public String value1996;

    @JsonProperty("1997")
    public String value1997;

    @JsonProperty("1998")
    public String value1998;

    @JsonProperty("1999")
    public String value1999;

    @JsonProperty("2000")
    public String value2000;

    @JsonProperty("2001")
    public String value2001;

    @JsonProperty("2002")
    public String value2002;

    @JsonProperty("2003")
    public String value2003;

    @JsonProperty("2004")
    public String value2004;

    @JsonProperty("2005")
    public String value2005;

    @JsonProperty("2006")
    public String value2006;

    @JsonProperty("2007")
    public String value2007;

    @JsonProperty("2008")
    public String value2008;

    @JsonProperty("2009")
    public String value2009;

    @JsonProperty("2010")
    public String value2010;

    @JsonProperty("2011")
    public String value2011;

    @JsonProperty("2012")
    public String value2012;

    @JsonProperty("2013")
    public String value2013;

    @JsonProperty("2014")
    public String value2014;

    @JsonProperty("2015")
    public String value2015;

    /**
     * Retrieve the array of indicator values. First index in the array is the 1960 value, last one is 2015.
     *
     * @return the array of indicator values.
     */
    public double[] getValuesArray()
    {
        return new double[]
        {
            getValueForYear("1960"), getValueForYear("1961"), getValueForYear("1962"), getValueForYear("1963"), getValueForYear(
            "1964"), getValueForYear("1965"), getValueForYear("1966"), getValueForYear("1967"), getValueForYear("1968"), getValueForYear(
            "1969"), getValueForYear("1970"), getValueForYear("1971"), getValueForYear("1972"), getValueForYear("1973"), getValueForYear(
            "1974"), getValueForYear("1975"), getValueForYear("1976"), getValueForYear("1977"), getValueForYear("1978"), getValueForYear(
            "1979"), getValueForYear("1980"), getValueForYear("1981"), getValueForYear("1982"), getValueForYear("1983"), getValueForYear(
            "1984"), getValueForYear("1985"), getValueForYear("1986"), getValueForYear("1987"), getValueForYear("1988"), getValueForYear(
            "1989"), getValueForYear("1990"), getValueForYear("1991"), getValueForYear("1992"), getValueForYear("1993"), getValueForYear(
            "1994"), getValueForYear("1995"), getValueForYear("1996"), getValueForYear("1997"), getValueForYear("1998"), getValueForYear(
            "1999"), getValueForYear("2000"), getValueForYear("2001"), getValueForYear("2002"), getValueForYear("2003"), getValueForYear(
            "2004"), getValueForYear("2005"), getValueForYear("2006"), getValueForYear("2007"), getValueForYear("2008"), getValueForYear(
            "2009"), getValueForYear("2010"), getValueForYear("2011"), getValueForYear("2012"), getValueForYear("2013"), getValueForYear(
            "2014"), getValueForYear("2015")
        };
    }

    /**
     * Return the double value for a given year.
     *
     * @param year the year looked for
     * @return the double value or Double.NaN if there is no value for this year
     */
    public double getValueForYear(String year)
    {
        try
        {
            Field valueField = RawWDIData.class.getDeclaredField("value" + year);

            String valueString = (String) valueField.get(this);

            if (valueString == null || valueString.equals(""))
            {
                return Double.NaN;
            }
            else
            {
                return Double.parseDouble(valueString);
            }
        }
        catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex)
        {
            return Double.NaN;
        }
    }

    /**
     * Attributes creation helper method and test area.
     *
     * @param args not used
     */
    public static void main(String[] args)
    {
        for (int i = 1960; i < 2016; i++)
        {
            System.out.println("@JsonProperty(\"" + i + "\")");
            System.out.println("public String value" + i + ";");
        }

        String arrayContent = IntStream.rangeClosed(1960, 2015)
                .mapToObj(i -> "getValueForYear(\"" + i + "\")")
                .collect(Collectors.joining(",", "{", "}"));

        System.out.println(arrayContent);

        // Let's verify the behaviour of DoubleSummaryStatistics with NaN...
        DoubleSummaryStatistics summaryStatistics = DoubleStream.of(12, 24, Double.NaN)
                .summaryStatistics();
        System.out.println("summaryStatistics = " + summaryStatistics);

        summaryStatistics = DoubleStream.of(12, 24, Double.NaN)
                .filter(d -> !Double.isNaN(d))
                .summaryStatistics();
        System.out.println("summaryStatistics = " + summaryStatistics);

    }

}
