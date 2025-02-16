package com.example.randomnumbergenerator.model;

public class RandomNumber {
    private int from, to, currentRandomNumber;

    public RandomNumber() {
        setFrom(0);
        setTo(1);
    }

    public RandomNumber(int numberOfValues) {
        setFrom(1);
        setTo(numberOfValues);
    }

    public RandomNumber(int from, int to) {
        this();
        setCurrentRandomNumberToNewlyGenerated();
    }

    private int generateRandomNumber() {
        double rand1 = Math.random();
        double rand2 = rand1 * (1 + (to - from));
        double rand3 = Math.floor(rand2);
        return (int) rand3 + from;
    }

    public final void setCurrentRandomNumberToNewlyGenerated() {
        currentRandomNumber = generateRandomNumber();
    }

    public int getCurrentRandomNumber() {
        return currentRandomNumber;
    }

    public int getFrom() {
        return from;
    }

    private void setFrom(int from) {
        this.from = from;
    }

    public void setFromTo(int from, int to) {
        setFrom(from);
        setTo(to);
        setCurrentRandomNumberToNewlyGenerated();
    }

    public int getTo() {
        return to;
    }

    private void setTo(int to) {
        if (to <= from) {
            throw new RuntimeException("To must be greater than From.");
        }

        this.to = to;
    }
}

