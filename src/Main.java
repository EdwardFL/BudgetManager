import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    private static final int EXIT = 0;
    private static final int ADD = 1;
    private static final int PURCHASE = 2;
    private static final int SHOW = 3;
    private static final int BALANCE = 4;
    private static final int SAVE = 5;
    private static final int LOAD = 6;
    private static final String FILE_NAME = "purchases.txt";
    private static final String[] CATEGORIES = {"Food", "Clothes", "Entertainment", "Other"};
    private static double balance;
    private static Map<String, Purchase> purchases = new HashMap<>();

    private static Scanner input = new Scanner(System.in);

    public static double getBalance() {
        return balance;
    }

    public static void setBalance(double balance) {
        Main.balance += balance;
    }

    public static void main(String[] args) {

        while (true) {
            System.out.println("Choose your action:");
            displayMenuOptions();

            int choice = input.nextInt();
            input.nextLine();

            System.out.println();

            switch (choice) {
                case ADD:
                    addIncome();
                    break;
                case PURCHASE:
                    addPurchase();
                    break;
                case SHOW:
                    showPurchases();
                    break;
                case BALANCE:
                    displayBalance();
                    break;
                case SAVE:
                    savePurchasesToFile();
                    break;
                case LOAD:
                    loadPurchasesFromFile();
                    break;
                case EXIT:
                    System.out.println("Bye!");
                    input.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid integer option.");
                    break;
            }
        }
    }

    public static void addIncome() {
        System.out.println("\nEnter income:");
        double amount = input.nextDouble();
        input.nextLine();
        System.out.println("Income was added!\n");
        setBalance(amount);
    }

    public static void displayMenuOptions() {
        System.out.println("1) Add income");
        System.out.println("2) Add purchase");
        System.out.println("3) Show list of purchases");
        System.out.println("4) Balance");
        System.out.println("5) Save");
        System.out.println("6) Load");
        System.out.println("0) Exit");
    }

    public static void savePurchasesToFile() {
        try (PrintWriter printWriter = new PrintWriter(FILE_NAME)) {
            printWriter.println(String.format("%.2f", getBalance())); // Save the balance with 2 decimal places
            for (Map.Entry<String, Purchase> entry : purchases.entrySet()) {
                String purchaseName = entry.getKey();
                double purchasePrice = entry.getValue().getPrice();
                String category = entry.getValue().getCategory();
                printWriter.println(purchaseName + "," + purchasePrice + "," + category);
            }
            System.out.println("Purchases were saved!\n");
        }catch (IOException e) {
            System.out.println("Error saving purchases to file: " + e.getMessage());
        }
    }

    public static void loadPurchasesFromFile() {
        try (Scanner fileScanner = new Scanner(new File(FILE_NAME))) {
            purchases.clear();
            setBalance(Double.parseDouble(fileScanner.nextLine())); // Load the balance
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String purchaseName = parts[0];
                    double purchasePrice = Double.parseDouble(parts[1]);
                    String category = parts[2];
                    purchases.put(purchaseName, new Purchase(purchasePrice, category));
                }
            }
            System.out.println("Purchases were loaded!\n");
        } catch (IOException e) {
            System.out.println("Error loading purchases from file: " + e.getMessage());
        }
    }

    public static void addPurchase() {
        while (true) {
            System.out.println("Choose the type of purchase");
            for (int i = 0; i < CATEGORIES.length; i++) {
                System.out.println((i + 1) + ") " + CATEGORIES[i]);
            }
            System.out.println((CATEGORIES.length + 1) + ") Back");

            int categoryChoice = input.nextInt();
            input.nextLine();

            if (categoryChoice >= 1 && categoryChoice <= CATEGORIES.length) {
                String category = CATEGORIES[categoryChoice - 1];
                addPurchaseByCategory(category);
            } else if (categoryChoice == CATEGORIES.length + 1) {
                System.out.println();
                break;
            } else {
                System.out.println("Invalid category choice.");
            }
        }
    }

    public static void addPurchaseByCategory(String category) {
        System.out.println("\nEnter purchase name: ");
        String purchaseName = input.nextLine();

        System.out.println("Enter its price: ");
        double purchasePrice = input.nextDouble();
        input.nextLine();

        purchases.put(purchaseName, new Purchase(purchasePrice, category));

        setBalance(-purchasePrice);
        System.out.println("Purchase was added!\n");
    }

    public static void showPurchases() {
        if (purchases.isEmpty()) {
            System.out.println("The purchase list is empty!\n");
            return;
        }

        while (true) {
            System.out.println("Choose the type of purchases");
            for (int i = 0; i < CATEGORIES.length; i++) {
                System.out.println((i + 1) + ") " + CATEGORIES[i]);
            }
            System.out.println((CATEGORIES.length + 1) + ") All");
            System.out.println((CATEGORIES.length + 2) + ") Back");

            int showChoice = input.nextInt();
            input.nextLine(); // Consume the newline character left by nextInt()

            if (showChoice >= 1 && showChoice <= CATEGORIES.length) {
                String category = CATEGORIES[showChoice - 1];
                displayPurchases(false, category);
            } else if (showChoice == CATEGORIES.length + 1) {
                displayPurchases(true, null);
            } else if (showChoice == CATEGORIES.length + 2) {
                System.out.println();
                break; // Back to main menu
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }
    public static void displayPurchases(boolean displayAll, String category) {
        System.out.println("\n" + (displayAll ? "All" : category) + ":");

        double totalAmount = 0.0;
        boolean purchasesFound = false;

        for (Map.Entry<String, Purchase> entry : purchases.entrySet()) {
            Purchase purchase = entry.getValue();
            if (displayAll || purchase.getCategory().equalsIgnoreCase(category)) {
                String purchaseName = entry.getKey();
                System.out.println(purchaseName + " $" + String.format("%.2f", purchase.getPrice()));
                totalAmount += purchase.getPrice();
                purchasesFound = true;
            }
        }

        if (!purchasesFound) {
            System.out.println("No purchases found in this category.\n");
        } else {
            System.out.println("Total sum: $" + String.format("%.2f", totalAmount) + "\n");
        }
    }


    public static void displayBalance() {
        System.out.println("Balance: $" + String.format("%.2f", getBalance()) + "\n");
    }
}
