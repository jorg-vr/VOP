package model.history;

import model.account.User;

import java.time.LocalDateTime;


public class EditEvent implements java.io.Serializable {
    private LocalDateTime editTime;
    private User user;
    private EditableObject newObject;
    private EditableObject oldObject;

    public EditEvent() {
    }

    public EditEvent(User user, EditableObject newObject, EditableObject oldObject) {
        editTime=LocalDateTime.now();
        this.user = user;
        this.newObject = newObject;
        this.oldObject = oldObject;
        History.getHistory().addEditEvent(this);
    }

    public LocalDateTime getEditTime() {
        return editTime;
    }

    public void setEditTime(LocalDateTime editTime) {
        this.editTime = editTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EditableObject getNewObject() {
        return newObject;
    }

    public void setNewObject(EditableObject newObject) {
        this.newObject = newObject;
    }

    public EditableObject getOldObject() {
        return oldObject;
    }

    public void setOldObject(EditableObject oldObject) {
        this.oldObject = oldObject;
    }
}
