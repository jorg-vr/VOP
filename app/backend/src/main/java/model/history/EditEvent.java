package model.history;

import model.account.Account;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class EditEvent implements java.io.Serializable {
    private LocalDateTime editTime;
    private Account account;
    private EditableObject newObject;
    private EditableObject oldObject;

    public EditEvent() {
    }

    public EditEvent(Account account, EditableObject newObject, EditableObject oldObject) {
        editTime=LocalDateTime.now();
        this.account = account;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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
