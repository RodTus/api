package fr.rodtus.api.TitleTabBossBar;

import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

/**
 * Created by Florian on 15/01/2018.
 */
public class BossBarSendEvent {

    public static void sendToPlayer(BossBar bar, Player player){
        bar.addPlayer(player);
    }

    public static void sendAllPlayers(BossBar bar){
        for (Player players : Bukkit.getOnlinePlayers()) {
            bar.addPlayer(players);
        }
    }

}
