package budget;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static PurchaseRepository purchaseRepo = new PurchaseRepository();
    static int income = 0;

    public static void main(String[] args) {
        mainMenu();
        int input = scanner.nextInt();
        int submenuInput;
        Category selectedCategory;
        while (input != 0) {
            switch (input) {
                case 1:
                    int income;
                    System.out.println("\nEnter income: ");
                    income = scanner.nextInt();
                    addIncome(income);
                    System.out.println("Income was added!\n");
                    mainMenu();
                    break;
                case 2:
                    System.out.println();
                    do {
                        addPurchaseCategoryMenu();
                        submenuInput = scanner.nextInt();
                        selectedCategory = categorySelection(submenuInput);
                        if (selectedCategory != null) {
                            String inputName;
                            float inputPrice;
                            scanner.nextLine();
                            System.out.println("\nEnter purchase name:");
                            inputName = scanner.nextLine();
                            System.out.println("Enter its price:");
                            inputPrice = Float.parseFloat(scanner.nextLine());
                            addPurchase(inputName, inputPrice, selectedCategory);
                            System.out.println("Purchase was added!\n");
                        } else {
                            System.out.println();
                        }
                    } while (submenuInput > 0 && submenuInput < 5);
                    mainMenu();
                    break;
                case 3:
                    System.out.println();
                    do {
                        listPurchaseCategoryMenu();
                        submenuInput = scanner.nextInt();
                        System.out.println();
                        selectedCategory = categorySelection(submenuInput);
                        if (selectedCategory != null) {
                            System.out.println(purchaseRepo.toString(selectedCategory));
                            System.out.println("Total sum: $" + String.format("%.2f", purchaseRepo.calculateTotal(selectedCategory)));
                        } else {
                            if (submenuInput == 5) {
                                System.out.println(purchaseRepo.toString());
                                System.out.println("Total sum: $" + String.format("%.2f", purchaseRepo.calculateTotal()));
                            }
                        }
                        System.out.println();
                    } while (submenuInput > 0 && submenuInput < 6);
                    mainMenu();
                    break;
                case 4:
                    System.out.println("\nBalance: $" + String.format("%.2f", calculateBalance()) + "\n");
                    mainMenu();
                    break;
                case 5:
                    if (saveFile()) {
                        System.out.println("\nPurchases were saved\n");
                    } else {
                        System.out.println("\nFailed to save purchases\n");
                    }
                    mainMenu();
                    break;
                case 6:
                    if (loadFile(new File("purchases.txt"))) {
                        System.out.println("\nPurchases were loaded!\n");
                    } else {
                        System.out.println("\nFailed to load purchases!\n");
                    }
                    mainMenu();
                    break;
                case 7:
                    do {
                    sortMenu();
                    submenuInput = scanner.nextInt();
                    System.out.println();
                    switch (submenuInput) {
                        case 1:
                            purchaseRepo.sort();
                            System.out.println(purchaseRepo.toString());
                            break;
                        case 2:
                            System.out.println("Types: ");
                            System.out.println("Food - $" + String.format("%.2f", purchaseRepo.calculateTotal(Category.Food)));
                            System.out.println("Entertainment - $" + String.format("%.2f", purchaseRepo.calculateTotal(Category.Entertainment)));
                            System.out.println("Clothes - $" + String.format("%.2f", purchaseRepo.calculateTotal(Category.Clothes)));
                            System.out.println("Other - $" + String.format("%.2f", purchaseRepo.calculateTotal(Category.Other)));
                            System.out.println("Total sum: $" + String.format("%.2f", purchaseRepo.calculateTotal()));
                            break;
                        case 3:
                            addPurchaseCategoryMenu();
                            submenuInput = scanner.nextInt();
                            System.out.println();
                            selectedCategory = categorySelection(submenuInput);
                            if (selectedCategory != null) {
                                purchaseRepo.sort();
                                System.out.println(purchaseRepo.toString(selectedCategory));
                            } else {
                                System.out.println();
                            }
                            break;
                    } } while (submenuInput > 0 && submenuInput < 4);
                    mainMenu();
                    break;
            }
            input = scanner.nextInt();
        }
        System.out.println("\nBye!");
    }

    public static void addPurchase(String name, float price, Category category) {
        purchaseRepo.addPurchase(new Purchase(name, price, category));
    }

    public static void addIncome(int income) {
        Main.income += income;
    }

    public static void mainMenu() {
        System.out.println("Choose your action:");
        System.out.println("1) Add income");
        System.out.println("2) Add purchase");
        System.out.println("3) Show list of purchases");
        System.out.println("4) Balance");
        System.out.println("5) Save");
        System.out.println("6) Load");
        System.out.println("7) Analyze (Sort)");
        System.out.println("0) Exit");
    }

    public static void listPurchaseCategoryMenu() {
        System.out.println("Choose the type of purchase");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");
        System.out.println("5) All");
        System.out.println("6) Back");
    }

    public static void addPurchaseCategoryMenu() {
        System.out.println("Choose the type of purchases");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");
        System.out.println("5) Back");
    }

    public static float calculateBalance() {
        float balance = income - purchaseRepo.calculateTotal();
        if (balance < 0) {
            return 0;
        } else {
            return balance;
        }
    }

    public static boolean loadFile(File file) {
        try (Scanner scanner = new Scanner(file)) {
            purchaseRepo.clear();
            income = Integer.parseInt(scanner.nextLine());
            while (scanner.hasNext()) {
                String currentPurchase = scanner.nextLine();
                String[] splitPurchase = new String[2];
                splitPurchase[0] = currentPurchase.substring(0, currentPurchase.lastIndexOf('$') - 1);
                splitPurchase[1] = currentPurchase.substring(currentPurchase.lastIndexOf('$') + 1);
                purchaseRepo.addPurchase(new Purchase(splitPurchase[0],
                        Float.parseFloat(splitPurchase[1].substring(0, splitPurchase[1].indexOf(' '))),
                        Category.parseString(splitPurchase[1].substring(splitPurchase[1].indexOf(' ') + 1))));
            }
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + file.getAbsolutePath());
            return false;
        }
    }

    public static boolean saveFile() {
        try (FileWriter writer = new FileWriter(new File("purchases.txt"))) {
            writer.write(String.valueOf(income));
            writer.write('\n');
            writer.write(purchaseRepo.toString());
            return true;
        } catch (IOException e) {
            System.out.printf("An exception occurred %s", e.getMessage());
            return false;
        }
    }

    public static Category categorySelection(int input) {
        switch (input) {
            case 1:
                return Category.Food;
            case 2:
                return Category.Clothes;
            case 3:
                return Category.Entertainment;
            case 4:
                return Category.Other;
        }
        return null;
    }

    public static void sortMenu() {
        System.out.println("\nHow do you want to sort?\n" +
                "1) Sort all purchases\n" +
                "2) Sort by type\n" +
                "3) Sort certain type\n" +
                "4) Back");
    }
}
