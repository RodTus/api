package fr.rodtus.api.SQL;

import fr.rodtus.api.Rank.Rank;
import fr.rodtus.api.Utils.ConsoleColor;
import org.bukkit.entity.Player;

import java.sql.*;

/**
 * Created by Florian on 31/10/2017.
 */
public class SqlConnection {

    private Connection connection;
    private String urlbase, host, database, user, pass;


    public SqlConnection(String urlbase, String host, String database, String user, String pass) {
        this.urlbase = urlbase;
        this.host = host;
        this.database = database;
        this.user = user;
        this.pass = pass;
    }

    public void connected() { //Permet de connecter notre database
        if (!isConnected()) {
            try {
                connection = DriverManager.getConnection(urlbase + host + "/" + database, user, pass);
                System.out.println(ConsoleColor.CYAN + "DataBase Connected on RodTus");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void disconnected() { //Permet de déconnecté notre database
        if (isConnected()) {
            try {
                connection.close();
                System.out.println(ConsoleColor.RED + "DataBase Disconnected on RodTus");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnected() { //Permet de savoir si la database est connecté ou pas
        return connection != null;
    }

    public void createAccount(Player player) { //Crée le compte banquaire du joueur
        if (!hasAccount(player)) {
            try {
                PreparedStatement querry = connection.prepareStatement("INSERT INTO JOUEUR(uuid,coins,grade) VALUES (?,?,?)");
                querry.setString(1, player.getUniqueId().toString());
                querry.setInt(2, 0);
                querry.setInt(3, Rank.JOUEUR.getPower());
                querry.execute();
                querry.close();
                System.out.println("Le compte du joueur " + player + "a bien ete cree");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean hasAccount(Player player) { //Permet de vérifier si le joueur a déjà un compte

        try {
            PreparedStatement querry = connection.prepareStatement("SELECT uuid FROM JOUEUR WHERE uuid=?");
            querry.setString(1, player.getUniqueId().toString());
            ResultSet resultat = querry.executeQuery();
            boolean hasAccount = resultat.next();
            querry.close();

            return hasAccount;
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return false;
    }

    public int getBalance(Player player) { //Permet de récupérer le nombre de coins d'un joueur

        try {
            PreparedStatement querry = connection.prepareStatement("SELECT coins FROM JOUEUR WHERE uuid=?");
            querry.setString(1, player.getUniqueId().toString());

            int balance = 0;
            ResultSet resultat = querry.executeQuery();

            while (resultat.next()) {
                balance = resultat.getInt("coins");
            }

            return balance;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public void addMoney(Player player, int amount) { //Permet d'ajouter des coins a un joueur

        int balance = getBalance(player);
        int newBalance = balance + amount;

        try {
            PreparedStatement querry = connection.prepareStatement("UPDATE JOUEUR SET coins=? WHERE uuid=?");
            querry.setInt(1, newBalance);
            querry.setString(2, player.getUniqueId().toString());
            querry.executeUpdate();
            querry.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delMoney(Player player, int amount) {  //Permet d'enlever des coins a un joueur

        int balance = getBalance(player);

        if (amount > balance) {
            return;
        }
        addMoney(player, -amount);
    }

    public void setRank(Player player, Rank rank){

        try {
            PreparedStatement querry = connection.prepareStatement("UPDATE JOUEUR set grade=? WHERE uuid=?");
            querry.setInt(1, rank.getPower());
            querry.setString(2,player.getUniqueId().toString());
            querry.executeUpdate();
            querry.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Rank getRank(Player player){

        try {
            PreparedStatement querry = connection.prepareStatement("SELECT grade FROM JOUEUR WHERE uuid=?");
            querry.setString(1, player.getUniqueId().toString());

            int power = 0;
            ResultSet resultat = querry.executeQuery();

            while (resultat.next()) {
                power = resultat.getInt("grade");
            }

            querry.close();
            return Rank.powerToRank(power);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Rank.JOUEUR;
    }
}
