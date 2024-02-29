package game.trades;

import game.items.Coin;

/**
 * A class to store the balance of the actor
 */
public class Wallet {

    /**
     * balance of the wallet
     */
    private int balance;
    /**
     * instance of a wallet
     */
    private static Wallet instance;

    /**
     * Constructor for the Wallet. Initialise balance to 1000.
     */
    private Wallet() {
        balance = 1200;
    }

    /**
     * Get an instance of wallet
     * @return a wallet instance
     */
    public static Wallet getInstance() {
        if (instance == null) {
            instance = new Wallet();
        }
        return instance;
    }

    /**
     * Add amount of coin into the wallet
     * @param coin coin to be added
     */
    public void addBalance(Coin coin) {
        this.balance += coin.getAmount();
    }

    /**
     * Add some amount into the wallet
     * @param amount amount to be added
     */
    public void addBalance(int amount) {
        this.balance += amount;
    }

    /**
     * Remove amount of coin from the wallet
     * @param coin coin to be removed
     */
    public void removeBalance(Coin coin) {
        this.balance -= coin.getAmount();
    }

    /**
     * Remove some amount from the wallet
     * @param amount amount to be removed
     */
    public void removeBalance(int amount) {
        this.balance -= amount;
    }

    /**
     * Getter to get balance
     * @return balance of the wallet
     */
    public int getBalance() {
        return this.balance;
    }

    /**
     * Print out the balance in the wallet
     * @return a text that shows the balance
     */
    public String toString() {
        return "wallet: $" + balance +"";
    }

}
