package dao;

/**
 * Created by sam on 2/26/17.
 */
public interface Filter {
    //Using hibernate, it should be able to use lambda for filtering
    public boolean filter();
}
