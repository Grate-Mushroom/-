import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class CheckJavaFXVersion {
    public static void main(String[] args) {
        System.out.println("Java version: " + System.getProperty("java.version"));
        System.out.println("JavaFX version check:");

        try {
            Class<?> versionClass = Class.forName("javafx.application.Application");
            System.out.println("JavaFX found in classpath");

            // Получаем версию из пакета
            Package pkg = versionClass.getPackage();
            System.out.println("JavaFX Package: " + pkg);
            System.out.println("JavaFX Implementation Version: " + pkg.getImplementationVersion());
            System.out.println("JavaFX Specification Version: " + pkg.getSpecificationVersion());

        } catch (ClassNotFoundException e) {
            System.out.println("JavaFX NOT found in classpath");
        }

        // Проверяем системные свойства
        System.out.println("\nSystem properties:");
        System.getProperties().entrySet().stream()
                .filter(e -> e.getKey().toString().toLowerCase().contains("javafx") ||
                        e.getKey().toString().toLowerCase().contains("prism") ||
                        e.getKey().toString().toLowerCase().contains("glass"))
                .forEach(e -> System.out.println(e.getKey() + " = " + e.getValue()));
    }
}