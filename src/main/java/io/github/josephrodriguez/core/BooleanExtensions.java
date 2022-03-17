package io.github.josephrodriguez.core;

/**
 * Function to extend the use of @boolean
 */
public class BooleanExtensions {

    /**
     * Hide the implicit public constructor
     */
    private BooleanExtensions(){
    }

    /**
     * @param value Input value
     * @return The negation (!) of the parameter
     */
    public static boolean not(boolean value) {
        return !value;
    }
}
