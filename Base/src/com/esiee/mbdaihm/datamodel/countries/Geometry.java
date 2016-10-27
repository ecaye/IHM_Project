package com.esiee.mbdaihm.datamodel.countries;

import java.util.ArrayList;
import java.util.List;

/**
 * Geometry class which is in fact a list of polygons
 */
public class Geometry
{
    private final List<Polygon> polygons = new ArrayList<>();

    public Geometry()
    {

    }

    public void addPolygon(Polygon toAdd)
    {
        polygons.add(toAdd);
    }

    public List<Polygon> getPolygons()
    {
        return polygons;
    }
}
