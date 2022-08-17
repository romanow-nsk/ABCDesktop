package romanow.abc.desktop;

import lombok.Getter;
import lombok.Setter;
import romanow.abc.core.constants.ConstValue;
import romanow.abc.core.entity.Entity;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class ChoiceConsts  {
    @Getter private ArrayList<ConstValue> list = new ArrayList<>();
    @Getter private Choice choice;
    @Getter @Setter private ItemListener onSelect=null;
    private boolean busy=false;
    public ChoiceConsts(Choice choice, ArrayList<ConstValue> list0,ItemListener lsn0) {
        list = list0;
        this.choice = choice;
        choice.removeAll();
        for(ConstValue cc : list)
            choice.add(cc.title());
        choice.select(0);
        onSelect = lsn0;
        choice.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (busy || onSelect!=null)
                    onSelect.itemStateChanged(e);
                }
            });
        }
    public  String getTitle(){
        return list.get(choice.getSelectedIndex()).title();
        }
    public  int getValue(){
        return list.get(choice.getSelectedIndex()).value();
        }
    public  ConstValue get(){
        return list.get(choice.getSelectedIndex());
        }
    public ConstValue selectByValue(int value) {
        ConstValue out = null;
        busy=true;
        choice.select(0);
        for (int i = 0; i < list.size(); i++) {
            ConstValue cc = list.get(i);
            if (cc.value() == value) {
                choice.select(i);
                busy=false;
                return cc;
                }
            }
        busy=false;
        return null;
        }


}

