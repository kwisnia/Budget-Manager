package budget;

public enum Category {
    Food,
    Clothes,
    Entertainment,
    Other;

    public static Category parseString(String string) {
        switch (string) {
            case "Food":
                return Food;
            case "Clothes":
                return Clothes;
            case "Entertainment":
                return Entertainment;
            default:
                return Other;
        }
    }
}
