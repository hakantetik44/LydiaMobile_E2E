package utils;

public class OS {
    public static String OS = System.getProperty("platformName");

    public static boolean isAndroid() {
        if (OS == null) {
            throw new RuntimeException("Plateforme non spécifiée ! Veuillez exécuter avec -DplatformName=android ou -DplatformName=ios");
        }
        return OS.equalsIgnoreCase("android");
    }

    public static boolean isIOS() {
        if (OS == null) {
            throw new RuntimeException("Plateforme non spécifiée ! Veuillez exécuter avec -DplatformName=android ou -DplatformName=ios");
        }
        return OS.equalsIgnoreCase("ios");
    }

    public static boolean isMobile() {
        return isAndroid() || isIOS();
    }
}