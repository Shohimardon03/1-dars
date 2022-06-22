package uz.jl.settings;/*
  @author "Abdurashitov Shohimardon"
  @since 22/06/2022 15:49 (Wednesday)
  Quiz/IntelliJ IDEA
*/

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.jl.enums.Language;
import uz.jl.vo.auth.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Settings {
    private static Settings instance;

    public static String fields(String name) {

        Settings settings = new Settings();
        List<Map<String, String>> languages = new ArrayList<>();
        name = name.toLowerCase();
        Language lang = Session.sessionUser.getLang();
        languages.add(settings.uz());
        languages.add(settings.ru());
        languages.add(settings.en());

        return switch (lang) {
            case UZ -> languages.get(0).get(name);
            case RU -> languages.get(1).get(name);
            case ENG -> languages.get(2).get(name);
            default -> "!!!!!!!!";
        };
    }

    public Map<String, String> uz() {
        Map<String, String> uz = new TreeMap<>();
        uz.put("login", "Kirish");
        uz.put("register", "Ro'yhatdan o'tish");
        uz.put("quit", "Chiqish");
        uz.put("change lang", "Tilni o'zgartirish");


        return uz;
    }

    public Map<String, String> ru() {
        Map<String, String> ru = new TreeMap<>();
        ru.put("login", "Авторизоваться");
        ru.put("register", "Регистр");
        ru.put("quit", "Покидать");
        ru.put("change lang", "Изменить язык");

        return ru;
    }

    public Map<String, String> en() {
        Map<String, String> en = new TreeMap<>();
        en.put("login", "Login");
        en.put("register", "Register");
        en.put("quit", "Quit");
        en.put("change lang", "Change language ");

        return en;
    }

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }
}
