package controller;

import model.account.Function;
import model.history.EditableObject;

/**
 * A simple functional factory interface for creating controllers
 */
public interface ControllerFactory<T extends EditableObject> {

    AbstractController<T> create(Function function);
}
