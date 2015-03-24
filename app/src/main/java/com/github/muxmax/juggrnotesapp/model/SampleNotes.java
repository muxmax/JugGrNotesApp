package com.github.muxmax.juggrnotesapp.model;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generates sample notes.
 */
public class SampleNotes {

    static int[] colors = new int[]{Color.parseColor("#ff4444"),
            Color.parseColor("#ffbb33"),
            Color.parseColor("#99cc00"),
            Color.parseColor("#33b5e5")};
    static String[] titles = new String[]{"Cat Ipsum",
            "short title",
            "Di. 24.03.2015, 18:07 Uhr",
            "JUG",
            "Prepare JUG",
            "Way too much content to be a title. But this should also be handled.",
            "Buy concert cards",
            "Remember, today is Java User Group"};
    static String[] contents =
            new String[]{"Make meme, make cute face. If it fits, i sits bathe private parts with " +
                    "tongue then lick owner's face bathe private parts with tongue then lick " +
                    "owner's face. I like big cats and i can not lie.",
                    "Have secret plans sleep on keyboard, for ears back wide eyed.",
                    "Sweet beast. Has closed eyes but still sees you inspect anything brought into " +
                            "the house jump launch to pounce upon little yarn mouse, bare fangs at" +
                            " toy run hide litter box until treats are fed. \n",
                    "Favor packaging over toy curl into a furry donut for claw drapes.",
                    "Chew iPad power cord see owner, run in terror but burrow under covers, " +
                            "or spit up on light gray carpet instead of adjacent linoleum " +
                            "yet claws in your leg caticus cuteicus.",
                    "The app should be able to manage long contents in the overview. " +
                            "Only a short part of those texts should be displayed, " +
                            "followed by three dots. In the detail view the complete " +
                            "text of the content should be visualized.",
                    "Only short content. You did not expect that, did you?",
                    "foo bar",
                    "Remember birthdays"};

    public static List<Note> generate(int number) {

        List<Note> notes = new ArrayList<>();
        Random random = new Random();
        for (int i = 1; i <= number; i++) {
            Note note = new Note();
            if (random.nextBoolean()) {
                note.setTitle(titles[random.nextInt(titles.length)]);
            }
            if (random.nextBoolean() || note.getTitle().isEmpty()) {
                note.setContent(contents[random.nextInt(contents.length)]);
            }
            if (random.nextBoolean()) {
                note.setColor(colors[random.nextInt(colors.length)]);
            }
            notes.add(note);
        }
        return notes;
    }
}
