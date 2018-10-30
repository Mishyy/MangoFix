package me.idarkyy.mangofix.tasks;

import me.idarkyy.mangofix.MangoFix;
import org.zencode.mango.Mango;
import org.zencode.mango.factions.Faction;

import java.io.IOException;

public class AutoSaveTask implements Runnable {
    @Override
    public void run() {
        int saved = 0;

        for (Faction faction : Mango.getInstance().getFactionManager().getFactions()) {
            try {
                faction.save();
                saved++;
            } catch (IOException e) {
                MangoFix.getInstance().getLogger().severe("Could not save faction " + faction.getName() + ":");
                e.printStackTrace();
            }
        }
        System.out.println("[AUTO SAVE] Saved " + saved + " factions.");
    }
}
