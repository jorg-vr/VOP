package dao.interfaces;

public interface Filter<T> {

    // Using hibernate, it should be able to use lambda for filtering
    public void filter();
}
