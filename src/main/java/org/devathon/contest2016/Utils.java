package org.devathon.contest2016;

import org.bukkit.Location;

/**
 * Created by Redraskal_2 on 11/5/2016.
 */
public class Utils {

    public static Location center(Location toCenter) {
        if(toCenter.getX() >= 0) {
            toCenter.add(.5, 0, 0);
        } else {
            toCenter.subtract(.5, 0, 0);
        }

        if(toCenter.getZ() >= 0) {
            toCenter.add(0, 0, .5);
        } else {
            toCenter.subtract(0, 0, .5);
        }

        return toCenter;
    }
}