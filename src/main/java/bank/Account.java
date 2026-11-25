package bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account {
    private final String accountNumber;
    private final String holderName;
    private double balance;
    private final List<String> history = new ArrayList<>();

    public Account(String accountNumber, String holderName, double openingBalance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = openingBalance;
        history.add(String.format("Account opened with %.2f", openingBalance));
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        balance += amount;
        history.add(String.format("Deposited %.2f | Balance: %.2f", amount, balance));
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (amount > balance) {
            throw new IllegalStateException("Insufficient funds.");
        }
        balance -= amount;
        history.add(String.format("Withdrew %.2f | Balance: %.2f", amount, balance));
    }

    public double getBalance() { return balance; }
    public String getAccountNumber() { return accountNumber; }
    public String getHolderName() { return holderName; }
    public List<String> getHistory() { return Collections.unmodifiableList(history); }
}
