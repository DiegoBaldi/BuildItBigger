package com.example;

import java.util.Random;

public class Jokes {

    private static String[] jokes = {
            "Did you know a day on mercury lasts for 1,408 hours?\n" + "Same as a Monday on earth.",
            "I would lose weight, but I hate losing.",
            "200 lbs on Earth is only 74 lbs on Mars. I'm not fat, I'm just on the wrong planet.",
            "I don't trust joggers. They're always the ones that find the dead bodies. Just sayin'.",
            "Lazy People Fact #5812672793\n" +"You were too lazy to read that number."
    };

    public static String getJoke(){

        return jokes[new Random().nextInt(jokes.length)];
    }
}
