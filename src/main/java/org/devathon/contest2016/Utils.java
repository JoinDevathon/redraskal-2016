package org.devathon.contest2016;

import org.bukkit.Location;

/**
 * Created by Redraskal_2 on 11/5/2016.
 */
public class Utils {

    public static Location center(Location toCenter) {
        toCenter.setX(Math.floor(toCenter.getX()));
        toCenter.setZ(Math.floor(toCenter.getZ()));

        if(toCenter.getX() < 0) {
            toCenter.setX((toCenter.getX() + -.5));
        } else {
            toCenter.setX((toCenter.getX() + .5));
        }

        if(toCenter.getZ() < 0) {
            toCenter.setZ((toCenter.getZ() + -.5));
        } else {
            toCenter.setZ((toCenter.getZ() + .5));
        }

        return toCenter;
    }
}