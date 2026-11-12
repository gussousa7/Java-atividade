import java.util.*;

interface Bank {
    Account find(String id) throws AccountNotFoundException;

    void deposit(String id, double amount) throws AccountNotFoundException;

    void withdraw(String id, double amount) throws AccountNotFoundException, InsufficientFundsException;

    void transfer(String fromId, String toId, double amount) throws AccountNotFoundException, InsufficientFundsException;
}

class InsufficientFundsException extends Exception {
    private double missing;
    public InsufficientFundsException(double missing) {
        super("insufficient funds");
        this.missing = missing;
    }
    public double getMissing() {
        return missing;
    }
}
class AccountNotFoundException extends Exception {
    public AccountNotFoundException(String message) {
        super(message);
    }
}

class Account{
    private String id;
    private double balance;

    public Account(String id, double balance){
        this.id = id;
        this.balance = balance;
    }
    public String getId(){
        return this.id;
    }
    public double getBalance(){
        return this.balance;
    }
    public void setBalance(double balance){
        this.balance = balance;
    }
}

class SimpleBank implements Bank {
    private Account[] accounts;

    public SimpleBank(Account[] accounts) {
        this.accounts = accounts;
    }

    @Override
    public Account find(String id) throws AccountNotFoundException {
        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                return account;
            }
        }
        throw new AccountNotFoundException("account not found: " + id);
    }

    @Override
    public void deposit(String id, double amount) throws AccountNotFoundException {
        Account account = find(id);

        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        account.setBalance(account.getBalance() + amount);
    }

    @Override
    public void withdraw(String id, double amount) throws AccountNotFoundException, InsufficientFundsException {
        Account account = find(id);
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        if (amount > account.getBalance()) {
            throw new InsufficientFundsException(amount - account.getBalance());
        }
        account.setBalance(account.getBalance() - amount);
    }

    @Override
    public void transfer(String fromId, String toId, double amount) throws AccountNotFoundException, InsufficientFundsException {
        Account fromAccount = find(fromId);
        Account toAccount = find(toId);
        if (fromId.equals(toId)) {
            throw new AccountNotFoundException("same account");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        if (amount > fromAccount.getBalance()) {
            throw new InsufficientFundsException(amount - fromAccount.getBalance());
        }
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);
    }
}

public class Solution02 {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);
        int m = Integer.parseInt(scanner.nextLine());
        Account[] accounts = new Account[m];

        for (int i = 0; i < m; i++) {
            String[] parts = scanner.nextLine().split(" ");
            accounts[i] = new Account(parts[0], Double.parseDouble(parts[1]));
        }

        int n = Integer.parseInt(scanner.nextLine());
        String[] operations = new String[n];

        for (int i = 0; i < n; i++) {
            operations[i] = scanner.nextLine();
        }

        Bank bank = new SimpleBank(accounts);

        for (int i = 0; i < n; i++) {
            String[] parts = operations[i].split(" ");
            String type = parts[0];
            try {
                if (type.equals("DEPOSIT")) {
                    String id = parts[1];
                    double amount = Double.parseDouble(parts[2]);
                    bank.deposit(id, amount);
                    System.out.printf("deposit: %s %.2f%n", id, bank.find(id).getBalance());
                } else if (type.equals("WITHDRAW")) {
                    String id = parts[1];
                    double amount = Double.parseDouble(parts[2]);
                    bank.withdraw(id, amount);
                    System.out.printf("withdraw: %s %.2f%n", id, bank.find(id).getBalance());
                } else if (type.equals("TRANSFER")) {
                    String fromId = parts[1];
                    String toId = parts[2];
                    double amount = Double.parseDouble(parts[3]);
                    bank.transfer(fromId, toId, amount);
                    System.out.printf("transfer: %s %.2f %s %.2f%n", fromId, bank.find(fromId).getBalance(), toId, bank.find(toId).getBalance());
                } else {
                    throw new IllegalArgumentException("unknown operation");
                }
            } catch (NumberFormatException e) {
                System.out.println("error: invalid amount");
            } catch (AccountNotFoundException e) {
                System.out.println("error: " + e.getMessage());
            } catch (InsufficientFundsException e) {
                System.out.printf(
                        "error: %s, missing %.2f%n",
                        e.getMessage(), e.getMissing());
            } catch (IllegalArgumentException e) {
                System.out.println("error: " + e.getMessage());
            }
        }

        for (Account account : accounts) {
            System.out.printf(
                    "final balance %s: %.2f%n",
                    account.getId(), account.getBalance());
        }
    }
}


