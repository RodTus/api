package fr.rodtus.api.Scoreboard;

import fr.rodtus.api.Rank.Rank;
import fr.rodtus.api.SQL.SqlConnection;
import fr.rodtus.api.Utils.Tools;
import fr.rodtus.api.Utils.setItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;

/**
 * Created by Florian on 01/01/2018.
 */
public class Update {

    private setItem item = new setItem();

    private SqlConnection sql;

    public Update(SqlConnection sql) {
        sql = this.sql;
    }

    public void updateScoreboardCoins(Player player, int newBalance, Map<Player, ScoreboardSign> boards) {
        if (boards.containsKey(player)) {
            boards.get(player).setLine(2, "§e§lCOINS :§r " + newBalance + " §e" + Tools.getMoneySign(newBalance));
        }
    }

    public void updateScoreboardRank(Player player, Rank newRank, Map<Player, ScoreboardSign> boards) {
        if (boards.containsKey(player)) {
            boards.get(player).setLine(1, "§lGRADE :§r " + newRank.getTagColor() + newRank.getTagScoreboard());
        }
    }

    public void updateScoreboardPlayer(boolean disconect, Map<Player, ScoreboardSign> boards) {
        if (disconect) {
            for (Map.Entry<Player, ScoreboardSign> sign : boards.entrySet()) {
                sign.getValue().setLine(4, "§lJOUEUR :§r " + (Bukkit.getOnlinePlayers().size() - 1));
            }
        } else {
            for (Map.Entry<Player, ScoreboardSign> sign : boards.entrySet()) {
                sign.getValue().setLine(4, "§lJOUEUR :§r " + Bukkit.getOnlinePlayers().size());
            }
        }
    }
}

