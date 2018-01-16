package fr.rodtus.api.Rank;

import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Florian on 30/12/2017.
 */
public enum Rank {

    JOUEUR(0, "§7", "§7JOUEUR", ChatColor.GRAY),
    VIP(5, "§3VIP ▪", "§3VIP", ChatColor.WHITE),
    MODO(50, "§6§lMODO ▪", "§6§lMODO", ChatColor.BOLD.WHITE),
    ADMIN(100, "§4§lADMIN ▪", "§4§lADMIN", ChatColor.BOLD.WHITE);

    private int power;
    private String tag;
    private String tagScoreboard;
    private ChatColor tagColor;
    public static Map<Integer, Rank> grade = new HashMap<>();

    Rank(int power, String tag, String tagScoreboard, ChatColor tagColor) {
        this.power = power;
        this.tag = tag;
        this.tagScoreboard = tagScoreboard;
        this.tagColor = tagColor;
    }

    static {

        for (Rank r : Rank.values()) {
            grade.put(r.getPower(), r);
        }
    }

    public int getPower() {
        return power;
    }

    public String getTag() {
        return tag;
    }

    public String getTagScoreboard() {
        return tagScoreboard;
    }

    public ChatColor getTagColor() {
        return tagColor;
    }

    public static Rank powerToRank(int power) {
        return grade.get(power);
    }
}
