/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop;

import romanow.abc.core.Utils;
import romanow.abc.core.constants.ValuesBase;
import romanow.abc.core.entity.Entity;
import romanow.abc.core.entity.baseentityes.JBoolean;
import romanow.abc.core.entity.baseentityes.JLong;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;


/**
 *
 * @author romanow
 */
public abstract class EntityPopupPanel extends EntityBasePanel {
    long currentOid=0;
    private String currentName="";
    private boolean fullMode;
    private MainBaseFrame main;
    private PopupList popup;
    private boolean needUpdate=false;
    public Choice listBox(){ return null; }
    public void clearPopup(){
        currentName="";
        currentOid=0;
        current=null;
        popup.setText("");
        }
    public void getById(long oid){
        currentOid=oid;
        current=null;
        getById();
        }

    public void setUpdateState(boolean ss){
        needUpdate = ss;
        Update.setVisible(ss);
        Add.setVisible(!ss);
        Delete.setVisible(!ss);
        }
    public JButton Add(){ return Add; }
    public EntityPopupPanel(int x0, int y0, String name0, MainBaseFrame base0, boolean fullMode0) {
        initComponents();
        fullMode = fullMode0;
        Delete.setVisible(fullMode);
        Update.setVisible(fullMode);
        entityName = name0;
        entityTitleName = ValuesBase.EntityFactory().getEntityNameBySimpleClass(entityName);
        main = base0;
        setBounds(x0,y0,600,35);
        Title.setText(name0);
        setUpdateState(false);
        setVisible(true);
        popup = new PopupList(main, this, 100, 10, 80, 25, entityTitleName, new I_ListSelectedFull() {
            @Override
            public void onSelect(long idx, String name) {
                currentOid=idx;
                currentName=name;
                Name.setText(name);
                getById();
                }
            @Override
            public void onCancel() {
                }
            });
        popup.setVisible(true);
        add(popup);
        }
    public EntityPopupPanel() {
        initComponents();
    }
    //-------------------------------- Читать по id ----------------------------------------
    public void getById(){
        if (currentOid==0)
            return;
        Response res7 = null;
        try {
            res7 = apiFunGetById();
            if (!res7.isSuccessful()) {
                System.out.println("Ошибка запроса  " + Utils.httpError(res7));
                return;
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                return;
                }
        current = (Entity) res7.body();
        showRecord();
        }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton3 = new javax.swing.JButton();
        Update = new javax.swing.JButton();
        Add = new javax.swing.JButton();
        Delete = new javax.swing.JButton();
        Title = new javax.swing.JLabel();
        Name = new javax.swing.JTextField();

        jButton3.setText("jButton3");

        setLayout(null);

        Update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/save.png"))); // NOI18N
        Update.setBorderPainted(false);
        Update.setContentAreaFilled(false);
        Update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateActionPerformed(evt);
            }
        });
        add(Update);
        Update.setBounds(570, 5, 30, 30);

        Add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/add.png"))); // NOI18N
        Add.setBorderPainted(false);
        Add.setContentAreaFilled(false);
        Add.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AddMouseClicked(evt);
            }
        });
        Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddActionPerformed(evt);
            }
        });
        add(Add);
        Add.setBounds(490, 5, 30, 30);

        Delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/remove.png"))); // NOI18N
        Delete.setBorderPainted(false);
        Delete.setContentAreaFilled(false);
        Delete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DeleteMouseClicked(evt);
            }
        });
        Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteActionPerformed(evt);
            }
        });
        add(Delete);
        Delete.setBounds(530, 5, 30, 30);

        Title.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Title.setText("Наименование");
        add(Title);
        Title.setBounds(0, 10, 100, 30);
        add(Name);
        Name.setBounds(190, 10, 290, 25);
    }// </editor-fold>//GEN-END:initComponents

    public void updateAction(KeyEvent evt){
        try {
            if (current==null)
                return;
            updateRecord();
            Response<String> res7 = apiFunUpdate();
            if (!res7.isSuccessful()) {
                main.viewUpdate(evt,false);
                System.out.println("Ошибка запроса  " + Utils.httpError(res7));
                return;
                }
            //System.out.println("Изменен "+res7.body());
            long id = current.getOid();
            setUpdateState(false);
            main.viewUpdate(evt,true);
        } catch (IOException ex) {
            main.viewUpdate(evt,false);
            System.out.println(ex.getMessage());
            return;
            }
        }
    private void UpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateActionPerformed
        updateAction(null);
    }//GEN-LAST:event_UpdateActionPerformed

    private I_Button okAppend = new I_Button() {
        @Override
        public void onPush() {
            try {
                Response<JLong> res7 = apiFunAdd();
                if (!res7.isSuccessful()) {
                    System.out.println("Ошибка запроса  " + Utils.httpError(res7));
                    return;
                    }
                long id = res7.body().getValue();
                currentOid = id;
                getById();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                return;
                }
            }
        };
    private void AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddActionPerformed
        new OK(400,300,"Добавить "+entityName+" ?",okAppend);
    }//GEN-LAST:event_AddActionPerformed

    private I_Button okDelete = new I_Button() {
        @Override
        public void onPush() {
            try {
                Response<JBoolean> res = main.getService().deleteById(main.getDebugToken(),entityName,current.getOid()).execute();
                if (!res.isSuccessful()) {
                    System.out.println("Ошибка запроса  " + Utils.httpError(res));
                    return;
                }
                if (res.body().value()){

                }
                else
                    System.out.println("Ошибка удаления");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                return;
                }
            }
        };
    private void DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteActionPerformed
        //-------------------------------- Удалить ----------------------------------------
        if (current==null)
            return;
        new OK(400,300,"Удалить: "+current.getTitle()+" ?",okDelete);
        }//GEN-LAST:event_DeleteActionPerformed


    private void AddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddMouseClicked
        main.onRightButton(main,Add,Client.PanelOffsetY+this.getY(),evt,"Добавить "+entityName);
    }//GEN-LAST:event_AddMouseClicked

    private void DeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DeleteMouseClicked
        main.onRightButton(main,Delete,Client.PanelOffsetY+this.getY(),evt,"Удалить "+entityName);
    }//GEN-LAST:event_DeleteMouseClicked

    public JButton getAddButton(){
        return Add; }
    public JButton getDeleteButton(){
        return Delete; }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Add;
    private javax.swing.JButton Delete;
    private javax.swing.JTextField Name;
    private javax.swing.JLabel Title;
    private javax.swing.JButton Update;
    private javax.swing.JButton jButton3;
    // End of variables declaration//GEN-END:variables
}
