package controller.insurance;

import controller.AbstractController;
import controller.ControllerManager;
import model.CommissionContainer;
import model.account.Function;
import model.history.EditableObject;

/**
 * A simple functional factory interface for creating controllers
 */
public interface CommissionContainerControllerFactory<T extends CommissionContainer> {

   CommissionContainerController<T> create(ControllerManager manager);
}
