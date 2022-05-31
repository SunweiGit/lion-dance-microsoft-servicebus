package cn.liondance.microsoft.servicebus.function;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;

@Slf4j
public class Test {

    @SneakyThrows
    public static void main(String[] args) {
        log.error("{}", Locale.forLanguageTag("ZH"));
    }

}
