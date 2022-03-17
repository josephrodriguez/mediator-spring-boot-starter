package io.github.josephrodriguez.core;

import java.util.function.Supplier;

/**
 * Provide the feature of evaluate lazy a function
 * @param <T> The type of result from the lazy evaluation
 */
public class Lazy<T> implements Supplier<T> {

    private final Supplier<T> supplier;
    private T value;

    /**
     * @param supplier Supplier that returns an object of type T
     */
    public Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    /**
     * @return The result of evaluate the supplier.
     * The supplier expression is evaluated only one time, for subsequent invocations the cached value is returned
     */
    @Override
    public T get() {

        if( value != null) return value;
        
        synchronized (this) {
            if (value == null)
                value = supplier.get();
        }

        return value;
    }

    /**
     * Static method to create an instance from a supplier instance
     * @param supplier The supplier that will be evaluated lazy
     * @param <T> The type of supplier result
     * @return The instance of the Lazy class
     */
    public static <T> Lazy<T> of(Supplier<T> supplier) {
        return new Lazy<>(supplier);
    }
}
