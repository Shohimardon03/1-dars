package uz.jl.settings;/*
  @author "Abdurashitov Shohimardon"
  @since 22/06/2022 23:11 (Wednesday)
  trello_V/IntelliJ IDEA
*/

import com.darkprograms.speech.translator.GoogleTranslate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import uz.jl.enums.Language;
import uz.jl.vo.auth.Session;

import java.io.IOException;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Lang {
    static Lang instance;


    public static Lang getInstance() {
        if (instance == null) {
            return instance = new Lang();
        }
        return instance;
    }

    @SneakyThrows
    public String lang(String text) {
        Language lang;
        if (!Objects.isNull(Session.sessionUser)) {
            lang = Session.sessionUser.getLang();

        } else lang = Language.UZ;

        String res = switch (lang) {
            case RU -> GoogleTranslate.translate("ru", text);
            case UZ -> GoogleTranslate.translate("uz", text);
            case ENG -> GoogleTranslate.translate("en", text);
        };
        return StringUtils.capitalize(res);
    }
}
