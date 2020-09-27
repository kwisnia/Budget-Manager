package budget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PurchaseRepository {
    private final List<Purchase> purchaseList = new ArrayList<>();
    public PurchaseRepository() {
    }

    public void addPurchase(Purchase p) {
        purchaseList.add(p);
    }

    public float calculateTotal() {
        float total = 0;
        for (Purchase purchase : purchaseList) {
            total += purchase.getPrice();
        }
        return total;
    }
    public float calculateTotal(Category category) {
        float total = 0;
        for (Purchase purchase : purchaseList) {
            if (purchase.getCategory().equals(category)) {
                total += purchase.getPrice();
            }
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        if (purchaseList.isEmpty()) {
            output.append("Purchase list is empty");
        } else {
            for (Purchase purchase : purchaseList) {
                output.append(purchase.getPurchaseInfo())
                        .append('\n');
            }
            output.deleteCharAt(output.length() - 1);
        }
        return output.toString();
    }
    public String toString(Category category) {
        StringBuilder output = new StringBuilder();
        output.append(category).append(":\n");
        if (purchaseList.isEmpty()) {
            output.append("Purchase list is empty");
        } else {
            purchaseList.stream()
                    .filter(p -> category.equals(p.getCategory()))
                    .forEach(purchase -> output.append(purchase.getPurchaseInfo()).append('\n'));
            output.deleteCharAt(output.length() - 1);
        }
        return output.toString();
    }

    public void clear() {
        purchaseList.clear();
    }

    public void sort() {
        purchaseList.sort(Comparator.comparing(Purchase::getPrice).reversed());
    }
}
