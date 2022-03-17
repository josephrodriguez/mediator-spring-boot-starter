package io.github.josephrodriguez.core;

/**
 * Function to extend the use of @boolean
 */
public class BooleanExtensions {

    private BooleanExtensions(){
    }

    /**
     * @param value Input value
     * @return The boolean negation (!) of the parameter
     */
    public static boolean not(boolean value) {
        return !value;
    }
}
