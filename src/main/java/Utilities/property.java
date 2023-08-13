package Utilities;

public class property {
    private static final PropertyReader propertiesReader = new PropertyReader();
    public static final String baseUri = propertiesReader.getBaseUri();
}
