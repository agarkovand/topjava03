package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import javax.jws.soap.SOAPBinding;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 700),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 700),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 700)
        );

        List<UserMealWithExceed> withExceedList = getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
        withExceedList.forEach(System.out::println);
    }

    public static List<UserMealWithExceed>  getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return List with correctly exceeded field

        Map<LocalDate, Integer> dateCalories =
        mealList.stream()
                .collect(Collectors.groupingBy(um -> um.getDateTime().toLocalDate(),
                         Collectors.summingInt(UserMeal::getCalories)));

        return  mealList.stream()
                .filter(um -> TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime))
                .map(um -> new UserMealWithExceed(
                        um.getDateTime(),
                        um.getDescription(),
                        um.getCalories(),
                        dateCalories.get(um.getDateTime().toLocalDate()) > caloriesPerDay
                        )
                )
                .collect(Collectors.toList());
    }
}
