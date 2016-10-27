package com.esiee.mbdaihm.datamodel.countries;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple polygon model.
 *
 * @author Nicolas M.
 */
public class Polygon
{
    public GeoPoint[] points;

    /**
     * Create a new polygon from jackson parsed json content.
     *
     * @param jsonContent
     */
    public Polygon(List<Object> jsonContent)
    {
        points = new GeoPoint[jsonContent.size()];
        int count = 0;

        for (Object object : jsonContent)
        {
            ArrayList jsonPoints = (ArrayList) object;
            points[count] = new GeoPoint((Double) jsonPoints.get(0), (Double) jsonPoints.get(1));
            count++;
        }
    }
}
