package romanow.abc.desktop;

import lombok.Getter;
import lombok.Setter;
import romanow.abc.core.entity.Entity;

import java.awt.*;
import java.util.ArrayList;

public class ChoiceList<T extends Entity>  {
    @Getter
    private ArrayList<T> list = new ArrayList<>();
    @Getter @Setter private Choice choice;
    @Getter private int idx=-1;
    public ChoiceList(Choice choice0) {
        choice = choice0;
        }
    public int size(){
        return list.size();
        }
    public void savePos(){
        idx = choice.getItemCount()==0 ? -1 : choice.getSelectedIndex();
    }
    public void restorePos(){
        if (idx==-1 && choice.getItemCount()!=0)
            idx=0;
        select(idx);
    }
    public void clearPos(){
        idx=-1;
        }
    public void clear(){
        list.clear();
        choice.removeAll();
        }
    public void savePos(boolean withPos){
        if (withPos)
            savePos();
        }
    public void add(T elem){
        choice.add(elem.getTitle());
        list.add(elem);
        }
    public void add(T elem, String prefix){
        choice.add(prefix+" "+elem.getTitle());
        list.add(elem);
        }
    public void remove(int idx0){
        list.remove(idx0);
        choice.remove(idx0);
        }
    public void withPos(boolean force){
        if (force)
            restorePos();
        else
            select(0);
        }
    public void select(int idx0){
        if (choice.getItemCount()==0){
            idx=-1;
            return;
        }
        if (idx0>=choice.getItemCount())
            idx = choice.getItemCount()-1;
        else{
            idx=idx0;
            }
        choice.select(idx);
        }
    public void toNewElement(){
        idx = choice.getItemCount()==0 ? 0 : choice.getItemCount();
    }
    public void toPrevElement(){
        idx = idx==-1 ? -1 : idx-1;
    }
    public T get(){
        return choice.getItemCount()==0 ? null : list.get(choice.getSelectedIndex());
        }
}

