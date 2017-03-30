package spring.model;

import model.history.EditableObject;

/**
 * Created by jorg on 3/30/17.
 */
public interface RESTModelFactory<R extends RESTAbstractModel<M>,M extends EditableObject> {
    R create(M m);
}
