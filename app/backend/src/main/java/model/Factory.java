package model;

/**
 * Simple Factory interface that allows the creation of Factories in lambda expressions
 */
public interface Factory<T> {

    T create();
}
