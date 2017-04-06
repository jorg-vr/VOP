package controller;

import model.account.Function;
import model.history.EditableObject;

/**
 * Created by jorg on 3/30/17.
 */
public interface ControllerFactory<T extends EditableObject> {
    public AbstractController<T> create(Function function);
}
