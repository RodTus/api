package fr.rodtus.api;

import fr.rodtus.api.SQL.SqlConnection;
import fr.rodtus.api.Utils.ConsoleColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Florian on 15/01/2018.
 */
public class main extends JavaPlugin{

    public SqlConnection sql;

    @Override
    public void onEnable() {
        System.out.println(ConsoleColor.GREEN + "RodTus est ON");
    }

    @Override
    public void onDisable() {
        System.out.println(ConsoleColor.RED + "RodTus est OFF");

    }
}
