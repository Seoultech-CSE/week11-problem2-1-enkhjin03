import java.util.Scanner;
import java.util.InputMismatchException;

// 1. Custom Checked Exception Class
// Exception ангийг удамшуулснаар Checked Exception болно.
class InsufficientBalanceException extends Exception {
    private double balance;
    private double amount;

    public InsufficientBalanceException(double balance, double amount) {
        // Эх ангийн (superclass) конструкторыг алдааны мэдээлэлтэйгээр дуудав.
        super("Error: Insufficient balance. Current balance: $" + balance + ", Requested amount: $" + amount);
        this.balance = balance;
        this.amount = amount;
    }

    public double getBalance() {
        return balance;
    }

    public double getAmount() {
        return amount;
    }
}

// 2. Integrated Core Class
public class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        // Оролтын утгыг шалгаж, 0 буюу түүнээс бага бол IllegalArgumentException шиднэ.
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero.");
        }
        balance += amount;
        System.out.println("$" + amount + " successfully deposited.");
    }

    // Checked Exception шидэж байгаа тул 'throws' ашиглан зарлаж өгнө.
    public void withdraw(double amount) throws InsufficientBalanceException {
        // Сөрөг дүн авахаас сэргийлэх шалгалт (Нэмэлтээр)
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
        }
        // Үлдэгдэл хүрэлцэж буйг шалгаж, хүрэлцэхгүй бол өөрсдийн үүсгэсэн алдааг шиднэ.
        if (amount > balance) {
            throw new InsufficientBalanceException(balance, amount);
        }
        balance -= amount;
        System.out.println("$" + amount + " successfully withdrawn.");
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        BankAccount account = new BankAccount(500.0);

        System.out.println("=== Welcome to the Interactive Banking System ===");
        System.out.println("Initial Balance: $500.0");

        // --- DEPOSIT PROCESS ---
        // try-catch-finally бүтцээр орлого хийх хэсгийг ороов.
        try {
            System.out.print("\nEnter the amount to DEPOSIT: ");
            double depositAmount = input.nextDouble();
            account.deposit(depositAmount);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a valid numerical amount.");
            input.nextLine(); // Scanner-ийн буфер цэвэрлэх
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } finally {
            // Дансны үлдэгдлийг үргэлж хэвлэнэ.
            System.out.println("Current Balance: $" + account.getBalance());
        }

        // --- WITHDRAWAL PROCESS ---
        // try-catch-finally бүтцээр зарлага гаргах хэсгийг ороов.
        try {
            System.out.print("\nEnter the amount to WITHDRAW: ");
            double withdrawAmount = input.nextDouble();
            account.withdraw(withdrawAmount);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a valid numerical amount.");
            input.nextLine(); // Scanner-ийн буфер цэвэрлэх
        } catch (InsufficientBalanceException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } finally {
            // Дансны үлдэгдлийг үргэлж хэвлэнэ.
            System.out.println("Current Balance: $" + account.getBalance());
        }

        System.out.println("\n=== Thank you for using our service ===");
        input.close();
    }
}
