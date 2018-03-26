import java.nio.file.Path;

public class Country {
    private String name;
    private String iconPath;

    private Country() {}

    public Country(Path iconPath) {
        this.iconPath = iconPath.toString();
        String[] lines = iconPath.toString().split("/");
        String[] elements = lines[lines.length-1].split("_");
        this.name = elements[1];
    }

    public Country(String name, String iconPath) {
        this.name = name;
        this.iconPath = iconPath;
    }

    public String getName() {
        return name;
    }

    public String getIconPath() {
        return iconPath;
    }

    @Override
    public String toString() {
        return name;
    }
}
