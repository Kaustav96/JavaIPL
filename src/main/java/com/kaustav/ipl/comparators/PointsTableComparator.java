package com.kaustav.ipl.comparators;

import com.kaustav.ipl.model.PointsTable;

import java.util.Comparator;

public class PointsTableComparator implements Comparator<PointsTable> {
    @Override
    public int compare(PointsTable o1, PointsTable o2) {
        // sort based on points
        int points = Integer.compare(o2.getPts(),o1.getPts());
        if(points!=0){
            return points;
        }
        // sort based on net run rate
        double netRunRate = Double.compare(o2.getNrr(),o1.getNrr());
        if(netRunRate!=0.0){
            return netRunRate>0?1:-1;
        }
        return 0;
    }
}
