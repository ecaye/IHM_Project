package com.esiee.mbdaihm.dataaccess.geojson;

import java.util.List;

/**
 * Sample model for Jackson library parsing, inner level for countries geometry.
 *
 * @author Nicolas M.
 */
public class RawGeometry
{
    public String type;

    public List<List<Object>> coordinates;
}
