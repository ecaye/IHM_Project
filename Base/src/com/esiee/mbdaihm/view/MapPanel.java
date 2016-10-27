/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esiee.mbdaihm.view;

import com.esiee.mbdaihm.datamodel.DataManager;
import com.esiee.mbdaihm.datamodel.countries.Country;
import com.esiee.mbdaihm.datamodel.countries.Polygon;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author student
 */
public class MapPanel extends JPanel {
    public static boolean changeColor = false;
    private final double BEST_SCALE = 1.7;
    private final double MAX_SCALE = 20;

    protected Graphics2D g2d;
    
    private double scaleX = BEST_SCALE;
    private double scaleY = -BEST_SCALE;
    
    private double dragX, dragY;
    private Point2D mousePoint = new Point2D.Double(0, 0);
    private double beforeDragX, beforeDragY;
    
    private String currentCountry;
    
    private float sizeStroke = 1;
    
    private boolean onDrag;
    
    private List<Country> countries = Collections.EMPTY_LIST;
    
    public MapPanel(){
        this.countries = DataManager.INSTANCE.getCountries();
        initListeners();
        setBackground(new Color(39,178,255));
    }
    
     private void initListeners()
    {
        MouseWheelListener listener = new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if(e.getPreciseWheelRotation()< 0){
                    mapZoom(1.5);
                }
                else{
                    mapZoom(0.5);
                }
            }
        };
        addMouseWheelListener(listener);
        
        
        MouseMotionListener motionListener;
        motionListener = new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(!onDrag){
                    onDrag = true;
                }
                else{
                    dragX += e.getX() - beforeDragX;
                    dragY += e.getY() - beforeDragY;

                }
                beforeDragX = e.getX();
                beforeDragY = e.getY();
                
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                onDrag = false;
                mousePoint = e.getPoint();
                repaint();
            }
        };
        addMouseMotionListener(motionListener);
    }
    
    protected void mapZoom(double k){
       
       if(scaleX*k < BEST_SCALE){
           scaleX = BEST_SCALE;
           scaleY = -BEST_SCALE;
       }
       else if(scaleX*k > MAX_SCALE ){
           scaleX = MAX_SCALE;
           scaleY = -MAX_SCALE;
       }
       else{
           scaleX = scaleX*k;
           scaleY = scaleY*k;
       }
       sizeStroke = (float) (1.0/scaleX)*2;

       repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        if(countries == null){
            return;
        }
        super.paintComponent(g);

        g2d = (Graphics2D) g;
        
        AffineTransform a = g2d.getTransform();
        a.translate((getWidth()/2)+dragX, (getHeight()/2) +dragY);   
        a.scale(scaleX, scaleY);
        
        g2d.setTransform(a);

        
        Point2D dst = new Point2D.Double(0, 0);
        try {
            a.inverseTransform(mousePoint, dst);
        } catch (NoninvertibleTransformException ex) {
            Logger.getLogger(MapPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        g2d.setStroke(new BasicStroke(sizeStroke));


        for (Country country : countries) {
            for (Polygon polygon : country.getGeometry().getPolygons()) {
                
                GeneralPath countryBorder = new GeneralPath();
                countryBorder.moveTo(polygon.points[0].lon, polygon.points[0].lat);
                for (int i = 1; i < polygon.points.length; i++) {
                    Double distance = Math.sqrt(Math.pow(dst.getX() - polygon.points[i].lon, 2) + Math.pow(dst.getY() - polygon.points[i].lat, 2));
                    if(distance < 3){
                        if(country.getName() != currentCountry){
                            System.out.println(currentCountry); 
                            currentCountry = country.getName();
                        }
                    }
                    countryBorder.lineTo(polygon.points[i].lon, polygon.points[i].lat);
                }
                countryBorder.closePath();
                
                g2d.setPaint(Color.BLACK);
                g2d.draw(countryBorder);
                if(!country.getIndicatorValues().isEmpty()){
                    Color color = Color.WHITE;
                    if(changeColor){
                        color = MapColor.getColor(country.getIndicatorValues().get(DataManager.INSTANCE.getCurrentYear()));
                    }
                    g2d.setPaint(color);                  
                }
                g2d.fill(countryBorder);
                
            }
        }
    }
}
