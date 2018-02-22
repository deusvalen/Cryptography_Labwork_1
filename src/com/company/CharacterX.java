package com.company;

/**
 * Created by User on 20.02.2018.
 */
public class CharacterX implements Comparable<CharacterX> {
    private Character character;
    private Double priority;

    public CharacterX(Character character, double priority) {
        this.character = character;
        this.priority = priority;
    }

    public Character getCharacter() {
        return character;
    }

    public double getPriority() {
        return priority;
    }

    @Override
    public int compareTo(CharacterX o) {
        return o.priority.compareTo(priority);
    }

    @Override
    public String toString() {
        return character + " " + priority;
    }
}

