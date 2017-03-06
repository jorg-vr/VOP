package src.main.java.dao;

public interface Filter {

    // Using hibernate, it should be able to use lambda for filtering
    boolean filter();
}
