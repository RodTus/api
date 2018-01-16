package fr.rodtus.api.Utils;

/**
 * Created by Florian on 02/01/2018.
 */
public class Tools {

    public static char getMoneySign(int balance) {
        char moneySign;
        if (balance >= 1000) {
            moneySign = '⛃';
        } else {
            moneySign = '⛂';
        }
        return moneySign;
    }
}
