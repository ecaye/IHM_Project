package com.esiee.mbdaihm.dataaccess.wdi;

import com.esiee.mbdaihm.datamodel.DataManager;
import com.esiee.mbdaihm.datamodel.indicators.EIndicatorType;
import com.esiee.mbdaihm.datamodel.indicators.Indicator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class decoding data contained into the WDI_Series CSV file.
 * <b>File content must be UTF-8 encoded for Jackson MappingIterator.</b>
 *
 * @author N. Mortier
 */
public class WDIIndicatorsDecoder
{
//    private static final String FILE_NAME = "WDI_Series(utf-8).csv";
    private static final String FILE_NAME = "WDI_Series-utf8.csv";

    private static final String NO_SUB_TOPIC = "General";

    private static final String EMPTY_TOPIC = "General";

    public static List<Indicator> decode(String directoryPath)
    {
        long in = System.nanoTime();

        List<Indicator> result = new ArrayList<>();

        CsvSchema headerSchema = CsvSchema.emptySchema().withHeader();

        CsvMapper mapper = new CsvMapper();

        File file = new File(directoryPath + "/" + FILE_NAME);

        MappingIterator<RawWDIIndicator> readValues;

        try
        {
            readValues = mapper.reader(RawWDIIndicator.class).with(headerSchema).
                    without(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).
                    readValues(file);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            return result;
        }

        Indicator indicator;
        String topic;
        String subTopic;

        while (readValues != null && readValues.hasNext())
        {
            RawWDIIndicator indic = readValues.next();

            String[] split = indic.topic.split(":");

            topic = split[0];

            if ("".equals(topic))
            {
                topic = EMPTY_TOPIC;
            }
            subTopic = split.length == 2 ? split[1] : NO_SUB_TOPIC;
            subTopic = subTopic.trim();

            EIndicatorType type;

            if (indic.name.contains("%"))
            {
                type = EIndicatorType.PERCENTAGE;
            }
            else
            {
                type = EIndicatorType.VALUES;
            }

            indicator = new Indicator.Builder()
                    .withTopic(topic)
                    .withSubTopic(subTopic)
                    .withName(indic.name)
                    .withCode(indic.code)
                    .withDescription(indic.description)
                    .withType(type)
                    .build();

            result.add(indicator);
        }

        long out = System.nanoTime();

        System.err.println("Indicators decoding time = " + (out - in) / (1000 * 1000) + " ms.");

        return result;

    }

    /**
     * Browse through the given indicators and place them in proper order in data manager.
     *
     * @param input
     */
    public static void categoriseIndicators(List<Indicator> input)
    {
        Map<String, Map<String, List<Indicator>>> indicatorsMap = DataManager.INSTANCE.getIndicatorsMap();

        Map<String, List<Indicator>> topic;

        List<Indicator> subtopic;

        for (Indicator ind : input)
        {
            // Retrieve the map for the topic
            topic = indicatorsMap.get(ind.getTopic());

            // Create if null
            if (topic == null)
            {
                topic = new TreeMap<>();
                indicatorsMap.put(ind.getTopic(), topic);
            }

            // Next retrieve the sub topic list
            subtopic = topic.get(ind.getSubTopic());

            // Create if null
            if (subtopic == null)
            {
                subtopic = new ArrayList<>();
                topic.put(ind.getSubTopic(), subtopic);
            }

            subtopic.add(ind);
        }
    }
}
