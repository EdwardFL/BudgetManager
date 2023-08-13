import java.util.*;

public class Main {
    private static final int EXIT = 0;
    private static final int ADD = 1;
    private static final int PURCHASE = 2;
    private static final int SHOW = 3;
    private static final int BALANCE = 4;
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
        System.out.println("0) Exit");
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

        setBalance(getBalance() - purchasePrice);
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
            if (displayAll || purchase.getCategory().equals(category)) {
                String purchaseName = entry.getKey();
                double purchasePrice = purchase.getPrice();

                if (!Arrays.asList(CATEGORIES).contains(purchaseName) && purchasePrice > 0) {
                    System.out.println(purchaseName + " $" + String.format("%.2f", purchasePrice));
                    totalAmount += purchasePrice;
                    purchasesFound = true;
                }
            }
        }

        if (!purchasesFound) {
            System.out.println("The purchase list is empty!\n");
        } else {
            System.out.println("Total sum: $" + String.format("%.2f", totalAmount) + "\n");
        }
    }

    public static void displayBalance() {
        System.out.println("\nBalance: $" + String.format("%.2f", getBalance()) + "\n");
    }
}
