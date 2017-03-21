package model.history;

import java.time.LocalDateTime;
import java.util.*;


public class History {
    private static History HISTORY;

    private Map<UUID,List<EditEvent>> history;

    private History(){
        history=new HashMap<>();
    }

    public static History getHistory(){
        return HISTORY;
    }

    public void addEditEvent(EditEvent e){
        history.putIfAbsent(e.getOldObject().getUuid(),new ArrayList<>());
        history.get(e.getOldObject().getUuid()).add(e);
    }

    public List<EditEvent> getObjectHistory(UUID uuid){
        return history.get(uuid);
    }

    public  EditableObject getObjectAtDate(UUID uuid, LocalDateTime date){
        EditEvent result=null;
        if(getObjectHistory(uuid)!=null) { //TODO use efficient search algoritm
            for(EditEvent e:getObjectHistory(uuid)){
                if(date.isAfter(e.getEditTime())){
                    result=e;
                }
            }
        }
        return result.getNewObject();
    }
}
