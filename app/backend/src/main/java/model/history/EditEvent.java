package src.main.java.model.history;

import src.main.java.model.account.Account;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by jorg on 3/5/17.
 */
public class EditEvent {
    private LocalDateTime editTime;
    private Account account;
    private EditableObject newObject;
    private EditableObject oldObject;

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

    public Account getAccount() {
        return account;
    }

    public EditableObject getNewObject() {
        return newObject;
    }

    public EditableObject getOldObject() {
        return oldObject;
    }
}
