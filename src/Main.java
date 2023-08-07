import java.util.*;

public class Main {
    private static final int EXIT = 0;
    private static final int ADD = 1;
    private static final int PURCHASE = 2;
    private static final int SHOW = 3;
    private static final int BALANCE = 4;
    private static double balance;
    private static Scanner input = new Scanner(System.in);


    private static Map<String, Double> menu = new HashMap<>();

    public static double getBalance() {
        return balance;
    }

    public static void setBalance(double balance) {
        Main.balance = balance;
    }

    public static void main(String[] args) {

        while (true) {
            System.out.println("Choose your action:");
            displayMenuOptions();

            int choice = input.nextInt();
            // Consume any reminaing \n because sc.next____ does not consume the \n
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
                    displayPurchases();
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
        System.out.println("Income was added!\n");
        setBalance(getBalance() + amount);
    }

    public static void displayMenuOptions() {
        System.out.println("1) Add income");
        System.out.println("2) Add purchase");
        System.out.println("3) Show list of purchases");
        System.out.println("4) Balance");
        System.out.println("0) Exit");
    }

    public static void addPurchase() {

        System.out.println("\nEnter purchase name: ");
        String purchaseName = input.nextLine();

        System.out.println("Enter its price: ");
        double purchasePrice = input.nextDouble();

        setBalance(getBalance() - purchasePrice);
        menu.put(purchaseName, purchasePrice);
        System.out.println("Purchase was added!\n");

    }

    public static void displayBalance() {
        System.out.println("\nBalance: $" + String.format("%.2f", getBalance()) + "\n");
    }

    public static List<String> readPurchases() {
        List<String> purchases = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String purchase = scanner.nextLine();
            purchases.add(purchase);
        }

        scanner.close();
        return purchases;
    }

    public static void displayPurchases() {
        double totalAmount = 0;
        if (menu.isEmpty()) {
            System.out.println("\nThe purchase list is empty\n");
            return;
        }
        System.out.println();
        for (Map.Entry<String, Double> entry : menu.entrySet()) {
            System.out.println("" + entry.getKey() + " $" + String.format("%.2f", entry.getValue()));
            totalAmount += entry.getValue();
        }
        System.out.println("Total sum: $" + String.format("%.2f", totalAmount) + "\n");
    }

    public static double extractAmount(String purchase) {
        String[] parts = purchase.split("\\$");
        if (parts.length > 1) {
            String amountStr = parts[parts.length - 1].trim();
            try {
                return Double.parseDouble(amountStr);
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }
        return 0.0;
    }

    public static double calculateTotal(List<String> purchases) {
        double totalAmount = 0.0;
        for (String purchase : purchases) {
            double amount = extractAmount(purchase);
            totalAmount += amount;
        }
        return totalAmount;
    }
}
