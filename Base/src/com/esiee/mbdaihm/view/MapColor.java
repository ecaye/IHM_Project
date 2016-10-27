/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esiee.mbdaihm.view;

import com.esiee.mbdaihm.datamodel.DataManager;
import com.esiee.mbdaihm.datamodel.indicators.EIndicatorType;
import java.awt.Color;

/**
 *
 * @author student
 */
public class MapColor {
    public static final Color LIGHT_BLUE = new Color(116,196,118);
    public static final Color MIDDLE_BLUE = new Color(49,163,84);
    public static final Color DARK_BLUE = new Color(0,109,44);
 
    
    public static Color getColor(Double value){ 
        EIndicatorType dataType = DataManager.INSTANCE.getCurrentIndicator().getType();
        double max = (dataType == EIndicatorType.VALUES) ? DataManager.INSTANCE.getMax() : 100;
        double first = DataManager.INSTANCE.getMedian(1);
        double second = DataManager.INSTANCE.getMedian(2);
        if(value == null || value.isNaN())
            return Color.WHITE;
        if(value <= first)
            return LIGHT_BLUE;
        if(value <= second)
            return MIDDLE_BLUE;
        if(value <= max)
            return DARK_BLUE;
        return Color.BLACK;
    }
}
