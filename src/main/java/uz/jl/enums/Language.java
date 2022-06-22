package uz.jl.enums;/*
  @author "Abdurashitov Shohimardon"
  @since 22/06/2022 15:24 (Wednesday)
  Quiz/IntelliJ IDEA
*/

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Language {
    UZ(100),
    RU(10),
    ENG(50);

    private final int priority;
}
