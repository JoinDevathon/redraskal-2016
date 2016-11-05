package org.devathon.contest2016;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Redraskal_2 on 11/5/2016.
 */
public class Utils {

    public static String[] read(World world) throws IOException {
        File saveFile = getSaveFile(world);
        List<String> lines = new ArrayList<String>();
        try(BufferedReader br = new BufferedReader(new FileReader(saveFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(!line.isEmpty()
                        && !line.equalsIgnoreCase(" ")) {
                    lines.add(line);
                }
            }
        }
        return lines.toArray(new String[lines.size()]);
    }

    public static void load(World world, JavaPlugin javaPlugin) throws IOException {
        String[] lines = read(world);
        for(String line : lines) {
            String[] s = trim(line).split(",");
            new SolarPanel(new Location(Bukkit.getWorld(s[0]),
                    Double.valueOf(s[1]),
                    Double.valueOf(s[2]),
                    Double.valueOf(s[3]))
                    .getBlock(), UUID.randomUUID(), javaPlugin);
        }
    }

    private static String trim(String line) {
        line = line.replace("}", "");
        line = line.replace("x=", "");
        line = line.replace("y=", "");
        line = line.replace("z=", "");
        int c = 0;
        int p = 0;
        boolean found = false;
        for(int i=0; i<line.length(); i++) {
            if(!found && line.charAt(i) == '=') {
                if(c == 1) {
                    p = i;
                }
                c++;
            }
        }
        return line.substring(p, line.length());
    }

    public static void save(SolarPanel solarPanel) throws IOException {
        File saveFile = getSaveFile(solarPanel.getBaseLocation().getWorld());
        FileWriter fileWriter = new FileWriter(saveFile.getPath(), true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.printf("%s" + "%n", solarPanel.getBaseLocation().toString());
        printWriter.close();
        fileWriter.close();
    }

    public static void remove(SolarPanel solarPanel) throws IOException {
        String[] lines = read(solarPanel.getBaseLocation().getWorld());
        File saveFile = getSaveFile(solarPanel.getBaseLocation().getWorld());
        RandomAccessFile randomAccessFile = new RandomAccessFile(saveFile, "rw");
        randomAccessFile.setLength(0);
        randomAccessFile.close();
        FileWriter fileWriter = new FileWriter(saveFile.getPath(), true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for(String line : lines) {
            printWriter.printf("%s" + "%n", line);
        }
        printWriter.close();
        fileWriter.close();
    }

    public static File getSaveFile(World world) throws IOException {
        File saveFile = new File(world.getWorldFolder().getPath()
                + "solarPanels.dat");
        if(!saveFile.exists()) { saveFile.createNewFile(); }
        return saveFile;
    }

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