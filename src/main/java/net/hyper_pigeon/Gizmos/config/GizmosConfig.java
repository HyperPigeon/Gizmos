package net.hyper_pigeon.Gizmos.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "gizmos")
public class GizmosConfig implements ConfigData {
    public boolean slingShot = true;
    public boolean soulFireSpitter = true;
    public boolean igniteBlocks = true;
    public boolean fireworkStarBlock = true;
    public boolean chorusGourdAndCultivatedShulkers = true;
    public boolean horseshoes = true;
    public boolean rideableRavagers = true;
    public boolean pillagerFireworks = true;
    public boolean statusInfusedSnowGolems = true;
    public boolean seeker_arrows = true;

    public double ravagerTamingHealthProportion = .25;
}
