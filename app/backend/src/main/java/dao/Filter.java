package dao;

public interface Filter<T> {

    // Using hibernate, it should be able to use lambda for filtering
    public boolean filter(T t);
}
