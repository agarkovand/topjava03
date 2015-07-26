package ru.javawebinar.topjava;

import java.util.function.Consumer;

/**
 * User: gkislin
 * Date: 15.01.2015
 *
 * @link http://javawebinar.ru/topjava/
 */
public class Main {
    public static void main(String[] args) {
        execute(() -> {
            System.out.println("Hello Topjava!");
        });
        consume(System.out::println, "Hello Topjava!");
    }

    private static void execute(Runnable runnable) {
        System.out.println("Start runner");
        runnable.run();
        System.out.println("End runner");
    }

    private static <String> void consume(Consumer<String> consumer, String out) {
        System.out.println("Start consume");
        consumer.accept(out);
        System.out.println("End consume");
    }
}
