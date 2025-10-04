package com.caliban.helper;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.FindFailed;
import org.sikuli.basics.Settings;

public class ImageMatcher {

    public static Rectangle findImage(String imagePath, Rectangle searchArea) {
        Pattern pattern = new Pattern(imagePath);
        Region searchRegion = new Region(searchArea.x, searchArea.y, searchArea.width, searchArea.height);
        Settings.MinSimilarity = 0.9;
        Match match = searchRegion.exists(pattern);
        if (match != null) {
            //match.highlight();
            return new Rectangle(match.getX(), match.getY(), pattern.getImage().getW(), pattern.getImage().getH());
        }
        return null;
    }

    public static Rectangle findImageParital(String imagePath, Rectangle searchArea) {
        Pattern pattern = new Pattern(imagePath);
        Region searchRegion = new Region(searchArea.x, searchArea.y, searchArea.width, searchArea.height);
        Settings.MinSimilarity = 0.7;
        Match match = searchRegion.exists(pattern);
        if (match != null) {
            //match.highlight();
            return new Rectangle(match.getX(), match.getY(), pattern.getImage().getW(), pattern.getImage().getH());
        }
        return null;
    }

    public static int findMatchCount(String imagePath, Rectangle searchArea) {
        Pattern pattern = new Pattern(imagePath);
        Region searchRegion = new Region(searchArea.x, searchArea.y, searchArea.width, searchArea.height);

        Iterator<Match> it = null;
        try {
            it = searchRegion.findAll(pattern);
        } catch (FindFailed e) {
            // TODO: handle exception
            System.out.println("FindFailed: " + e);
        }
 
        int count = 0;

        while(it.hasNext()){
            Match match = it.next();
            //match.highlight();
            count++;
        }

        return count;
    }

    public static ArrayList<Rectangle> findMatchAll(String imagePath, Rectangle searchArea) {
        Pattern pattern = new Pattern(imagePath);
        Region searchRegion = new Region(searchArea.x, searchArea.y, searchArea.width, searchArea.height);

        Iterator<Match> it = null;
        try {
            it = searchRegion.findAll(pattern);
        } catch (FindFailed e) {
            // TODO: handle exception
            System.out.println("FindFailed: " + e);
        }
 
        ArrayList<Rectangle> matches = new ArrayList<>();

        while(it.hasNext()){
            Match match = it.next();
            matches.add(new Rectangle(match.getX(), match.getY(), pattern.getImage().getW(), pattern.getImage().getH()));
        }

        return matches;
    }
}
