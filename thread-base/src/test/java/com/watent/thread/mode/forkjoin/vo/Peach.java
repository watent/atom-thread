package com.watent.thread.mode.forkjoin.vo;

public class Peach {

    private final Color color;

    private final Size size;

    private final Integer year;

    public Peach(Color color, Size size, Integer year) {
        this.color = color;
        this.size = size;
        this.year = year;
    }

    public Color getColor() {
        return color;
    }

    public Size getSize() {
        return size;
    }

    public Integer getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "PanTao{" +
                "color=" + color +
                ", size=" + size +
                ", year=" + year +
                '}';
    }
}
