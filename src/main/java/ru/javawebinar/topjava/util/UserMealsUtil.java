package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500)
        );

        List<UserMealWithExceed> withExceedList = getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);

        int countMealsWithExceed = 0;
        for (UserMealWithExceed meal : withExceedList) {
            if (meal.isExceed()) countMealsWithExceed++;
        }

        System.out.println("there are " + countMealsWithExceed + " meals with exceed.");

    }

    public static List<UserMealWithExceed>  getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return List with correctly exceeded field

        // init map to store Days and Calories
        HashMap<LocalDate, Integer> mapDaysToCalories = new HashMap<>();

        // calculate calories for each day and store them in the map
        for (UserMeal meal : mealList) {
            LocalDate dateOfMeal = meal.getDateTime().toLocalDate();
            if (mapDaysToCalories.containsKey(dateOfMeal)) {
                int accumulatedCalories = mapDaysToCalories.get(dateOfMeal);
                accumulatedCalories += meal.getCalories();
                mapDaysToCalories.put(dateOfMeal, accumulatedCalories);
            } else {
                mapDaysToCalories.put(meal.getDateTime().toLocalDate(), meal.getCalories());
            }
        }



        // init list for meals with exceeded calories
        ArrayList<UserMealWithExceed> exceededMeals = new ArrayList<>();

        // extracting meals within the time range and from days with exceeded calories
        for (UserMeal meal : mealList) {

            LocalDate dateOfMeal = meal.getDateTime().toLocalDate();
            LocalTime timeOfMeal = meal.getDateTime().toLocalTime();

            if (mapDaysToCalories.get(dateOfMeal) > caloriesPerDay // exceeded calories check
                    && TimeUtil.isBetween(timeOfMeal, startTime, endTime)) { // time range check
                exceededMeals.add(
                        new UserMealWithExceed(
                                meal.getDateTime(),
                                meal.getDescription(),
                                meal.getCalories(),
                                true
                        )
                );
            }  else {
                exceededMeals.add(
                        new UserMealWithExceed(
                                meal.getDateTime(),
                                meal.getDescription(),
                                meal.getCalories(),
                                false
                        )
                );
            }
        }

        return exceededMeals;
    }
}
