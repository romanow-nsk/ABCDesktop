package romanow.abc.desktop;

import lombok.Getter;
import lombok.Setter;
import romanow.abc.core.entity.Entity;
import romanow.abc.core.entity.EntityRefList;

import java.awt.*;

public class ChoiceList<T extends Entity>  {                // Исходники
    @Getter private EntityRefList<T> list = new EntityRefList<>();
    @Getter private Choice choice;
    @Getter private int idx=-1;                             // Сохраненная позиция (-1 - нет такой)
    public ChoiceList(Choice choice0) {
        choice = choice0;
        }
    public int size(){
        return list.size();
        }
    public void savePos(){
        idx = choice.getItemCount()==0 ? -1 : choice.getSelectedIndex();
        }
    public T get(){
        return choice.getItemCount()==0 ? null : list.get(choice.getSelectedIndex());
        }
    public void clear(){
        list.clear();
        choice.removeAll();
        }
    public void createMap(){
        list.createMap();
        }
    public void refresh(){
        choice.removeAll();
        for(Entity entity : list)
            choice.add(entity.getTitle());
        }
    public void sortByTitle(){
        list.sortByTitle();
        refresh();
        }
    public void restorePos(){
        if (idx==-1 || choice.getItemCount()==0){
            idx=-1;
            if (choice.getItemCount()!=0)
                choice.select(0);
            return;
            }
        select(idx);
        }
    public void clearPos(){
        idx=-1;
        }
    public void add(T elem){
        choice.add(elem.getTitle());
        list.add(elem);
        }
    public void add(T elem, String prefix){             // При сортировке префикс теряется
        choice.add(prefix+" "+elem.getTitle());
        list.add(elem);
        }
    public void remove(int idx0){
        list.remove(idx0);
        choice.remove(idx0);
        }
    public void select(int idx0){
        idx=-1;
        if (choice.getItemCount()==0){
            return;
            }
        if (idx0>=choice.getItemCount())
            idx0 = choice.getItemCount()-1;
        choice.select(idx0);
        }
    public void toNewElement(){
        idx = choice.getItemCount()==0 ? 0 : choice.getItemCount();
    }
    public void toPrevElement(){
        idx = idx==-1 ? -1 : idx-1;
    }
}

