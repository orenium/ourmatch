package utils;

public class WebDriverFactory {

    private WebDriverFactory instance;

    public WebDriverFactory WebDriverFactory() {
        if (instance == null){
            instance = new WebDriverFactory();
        }
        return instance;
    }
}
