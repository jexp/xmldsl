package org.xmldsl;

/**
 * @author Michael Hunger
 * @since 28.05.2009
 */
public class Size {
    private final int size;
    private static final int MIN_SIZE = 30;

    public Size(final int size) {
        if (size < MIN_SIZE) throw new IllegalArgumentException("Person too small");
        this.size = size;
    }
    public static Size valueOf(final String value) {
        return new Size(Integer.valueOf(value));
    }

    @Override
    public String toString() {
        return String.valueOf(size);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        if (size != ((Size) o).size) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return size;
    }
}
