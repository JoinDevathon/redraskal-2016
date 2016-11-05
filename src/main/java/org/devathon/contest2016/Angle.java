package org.devathon.contest2016;

import org.bukkit.World;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Redraskal_2 on 11/5/2016.
 */
public class Angle {

    private static Map<Integer, Float> cache = new HashMap<Integer, Float>();

    static {
        float cur = -5f;
        for(int i=0; i<=100; i++) {
            cache.put(i, cur);
            cur+=.1f;
        }
    }

    public static int percent(World world) {
        double time = (double) world.getFullTime();
        if(time > 24000) {
            time = time / 24000;
        }
        return convert(((time/12000)*100));
    }

    public static int convert(double toConvert) {
        return Integer.valueOf(new String(new DecimalFormat("#").format(toConvert)));
    }

    // -5 -> 5
    public static float calc(World world) {
        try {
            float t = cache.get(percent(world));
            if(world.getFullTime() > 6000
                    && world.getFullTime() < 11500) {
                return -t;
            } else {
                return t;
            }
        } catch (Exception e) { return 5f; }
    }
}