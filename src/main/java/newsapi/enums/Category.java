package newsapi.enums;

public enum Category {
    health, business, entertainment, general, science, sports, technology;

    public static String[] names() {
        Category[] categories = values();
        String[] names = new String[categories.length];

        for (int i = 0; i < categories.length; i++) {
            names[i] = categories[i].name();
        }

        return names;
    }
}
