package uz.jl.ui;

import uz.jl.BaseUtils;
import uz.jl.Colors;

public class TestUI {

    public static void getAll() {

    }

    public static void create() {

    }

    public static void delete() {

    }

    public static void update() {

    }

    public static void testCrud() {
        BaseUtils.println("\nCreate test -> 1");
        BaseUtils.println("Delete test -> 2");
        BaseUtils.println("Update test -> 3");
        BaseUtils.println("Show test list-> 4");
        BaseUtils.println("Go back -> 0");

        String choice = BaseUtils.readText("?:");
        switch (choice) {
            case "1" -> TestUI.create();
            case "2" -> TestUI.delete();
            case "3" -> TestUI.update();
            case "4" -> TestUI.getAll();
            default -> BaseUtils.println("Wrong Choice", Colors.RED);
        }
    }
}
