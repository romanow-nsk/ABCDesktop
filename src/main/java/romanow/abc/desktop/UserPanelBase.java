/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop;

import romanow.abc.core.constants.ConstValue;
import romanow.abc.core.constants.ValuesBase;
import romanow.abc.core.entity.Entity;
import romanow.abc.core.entity.EntityList;
import romanow.abc.core.entity.artifacts.Artifact;
import romanow.abc.core.entity.contacts.Mail;
import romanow.abc.core.entity.contacts.Phone;
import romanow.abc.core.entity.users.User;
import retrofit2.Response;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author romanow
 */
public class UserPanelBase extends BasePanel{
    private EntityPanel userPanel;
    private ActionListener chain;
    private EntityList<Entity> types = new EntityList<>();
    private EntityList<User> userAList = new EntityList<>();
    private ArrayList<ConstValue> userTypes;
    private int selectedType=-1;
    /**
     * Creates new form MainPanel
     */
    public UserPanelBase() {
        initComponents();
        }
    @Override
    public void initPanel(MainBaseFrame main0){
        super.initPanel(main0);
        userPanel = new EntityPanel(10,15,userAList,"User",main,true){
            @Override
            public Response apiFunGetAll() throws IOException {
                return  main.getService().getUserList(main.getDebugToken(), ValuesBase.GetAllModeActual,0).execute();
                }
            @Override
            public Response apiFunGetById() throws IOException {
                return  main.getService().getUserById(main.getDebugToken(),data.get(listBox().getSelectedIndex()).getOid(),1).execute();
                }
            @Override
            public Response apiFunAdd() throws IOException {
                User uu = new User();
                uu.setTypeId(selectedType);
                return main.getService().addUser(main.getDebugToken(),uu).execute();
                }
            @Override
            public Response apiFunUpdate() throws IOException {
                return main.getService().updateUser(main.getDebugToken(),(User)current).execute();
            }
            @Override
            public void showRecord() {
                User uu = (User)current;
                N1.setText(uu.getLastName());
                N2.setText(uu.getFirstName());
                N3.setText(uu.getMiddleName());
                POST.setText(uu.getPost());
                PHONE.setText(uu.getPhone().getValue());
                MAIL.setText(uu.getMail().getValue());
                PHONEMK.setText(uu.getLoginPhone());
                PASS.setText(uu.getPassword());
                Login.setText(uu.getLogin());
                Type.setText(main.userTypes().get(uu.getTypeId()).title());
                ViewPhoto.setVisible(uu.getPhoto().getOid()!=0);
                CardICC.setText(uu.getSimCardICC());
                }
            @Override
            public void updateRecord() {
                User uu = (User)current;
                uu.setLastName(N1.getText());
                uu.setFirstName(N2.getText());
                uu.setMiddleName(N3.getText());
                uu.setPost(POST.getText());
                uu.setPhone(new Phone(PHONE.getText()));
                uu.setLoginPhone(PHONEMK.getText());
                uu.setMail(new Mail(MAIL.getText()));
                uu.setLogin(Login.getText());
                uu.setSimCardICC(CardICC.getText());
                }
            @Override
            public void clearView(){
                N1.setText("");
                N2.setText("");
                N3.setText("");
                POST.setText("");
                PHONE.setText("");
                MAIL.setText("");
                PHONEMK.setText("");
                PASS.setText("");
                Login.setText("");
                Type.setText("");
                ViewPhoto.setVisible(false);
                CardICC.setText("");
                }
            @Override
            public boolean isRecordSelected(Entity ent){
                int idx = UserTypeSelector.getSelectedIndex();
                if (idx==0)
                    return true;
                return userTypes.get(idx-1).value()==((User)ent).getTypeId();
                }
        };
        add(userPanel);
        JButton addButton = userPanel.Add();
        chain = addButton.getActionListeners()[0];
        addButton.removeActionListener(chain);
        addButton.addActionListener(new ActionListener() {      // Перехват цепочки
            @Override
            public void actionPerformed(final ActionEvent e) {
                new ListSelector(200, 200, "Роль пользователя", main.userTypes(), new I_ListSelected() {
                    @Override
                    public void onSelect(int idx) {
                        selectedType = main.userTypes().get(idx).value();
                        chain.actionPerformed(e);
                    }
                });
            }
        });
        userTypes = ValuesBase.constMap().getGroupList("User");
        UserTypeSelector.removeAll();
        UserTypeSelector.add("...");
        for(ConstValue cc : userTypes)
            UserTypeSelector.add(cc.title());
        userPanel.getAllEvent();
        userPanel.getById();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        Type = new javax.swing.JTextField();
        MAIL = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        N1 = new javax.swing.JTextField();
        N2 = new javax.swing.JTextField();
        N3 = new javax.swing.JTextField();
        POST = new javax.swing.JTextField();
        PHONE = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        Login = new javax.swing.JTextField();
        PHONEMK = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        PASS = new javax.swing.JPasswordField();
        jLabel10 = new javax.swing.JLabel();
        ViewPhoto = new javax.swing.JButton();
        UpploadPhoto = new javax.swing.JButton();
        CardICC = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        UserTypeSelector = new java.awt.Choice();

        jTextField1.setText("jTextField1");

        setEnabled(false);
        setLayout(null);
        add(jSeparator1);
        jSeparator1.setBounds(10, 190, 640, 10);

        Type.setEnabled(false);
        add(Type);
        Type.setBounds(90, 150, 110, 25);

        MAIL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MAILKeyPressed(evt);
            }
        });
        add(MAIL);
        MAIL.setBounds(300, 110, 110, 25);

        jLabel1.setText("CardICC");
        add(jLabel1);
        jLabel1.setBounds(310, 155, 60, 16);

        N1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                N1KeyPressed(evt);
            }
        });
        add(N1);
        N1.setBounds(90, 50, 110, 25);

        N2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                N2KeyPressed(evt);
            }
        });
        add(N2);
        N2.setBounds(90, 80, 110, 25);

        N3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                N3KeyPressed(evt);
            }
        });
        add(N3);
        N3.setBounds(90, 110, 110, 25);

        POST.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                POSTKeyPressed(evt);
            }
        });
        add(POST);
        POST.setBounds(300, 50, 110, 25);

        PHONE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PHONEKeyPressed(evt);
            }
        });
        add(PHONE);
        PHONE.setBounds(300, 80, 110, 25);

        jLabel2.setText("Фамилия");
        add(jLabel2);
        jLabel2.setBounds(10, 60, 70, 16);

        jLabel3.setText("Имя");
        add(jLabel3);
        jLabel3.setBounds(10, 90, 70, 16);

        jLabel4.setText("Отчество");
        add(jLabel4);
        jLabel4.setBounds(10, 120, 70, 16);

        jLabel5.setText("Должность");
        add(jLabel5);
        jLabel5.setBounds(220, 60, 70, 16);

        jLabel6.setText("Телефон");
        add(jLabel6);
        jLabel6.setBounds(220, 90, 70, 16);

        Login.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LoginKeyPressed(evt);
            }
        });
        add(Login);
        Login.setBounds(510, 50, 110, 25);

        PHONEMK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PHONEMKKeyPressed(evt);
            }
        });
        add(PHONEMK);
        PHONEMK.setBounds(510, 80, 110, 25);

        jLabel8.setText("Логин");
        add(jLabel8);
        jLabel8.setBounds(430, 60, 70, 16);

        jLabel9.setText("Телефон МК");
        add(jLabel9);
        jLabel9.setBounds(430, 90, 70, 16);

        PASS.setText("jPasswordField1");
        PASS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PASSKeyPressed(evt);
            }
        });
        add(PASS);
        PASS.setBounds(510, 110, 90, 25);

        jLabel10.setText("Пароль");
        add(jLabel10);
        jLabel10.setBounds(430, 120, 70, 16);

        ViewPhoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/camera.png"))); // NOI18N
        ViewPhoto.setBorderPainted(false);
        ViewPhoto.setContentAreaFilled(false);
        ViewPhoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ViewPhotoActionPerformed(evt);
            }
        });
        add(ViewPhoto);
        ViewPhoto.setBounds(260, 145, 30, 30);

        UpploadPhoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/upload.png"))); // NOI18N
        UpploadPhoto.setBorderPainted(false);
        UpploadPhoto.setContentAreaFilled(false);
        UpploadPhoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpploadPhotoActionPerformed(evt);
            }
        });
        add(UpploadPhoto);
        UpploadPhoto.setBounds(220, 140, 40, 40);

        CardICC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CardICCKeyPressed(evt);
            }
        });
        add(CardICC);
        CardICC.setBounds(369, 150, 250, 25);

        jLabel7.setText("E-mail");
        add(jLabel7);
        jLabel7.setBounds(220, 120, 70, 16);

        UserTypeSelector.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                UserTypeSelectorItemStateChanged(evt);
            }
        });
        add(UserTypeSelector);
        UserTypeSelector.setBounds(580, 20, 130, 20);
    }// </editor-fold>//GEN-END:initComponents

    private void N1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_N1KeyPressed
        if(evt.getKeyCode()!=10) return;
        ((User)userPanel.current).setLastName(N1.getText());
        userPanel.updateAction(evt);
    }//GEN-LAST:event_N1KeyPressed

    private void N2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_N2KeyPressed
        if(evt.getKeyCode()!=10) return;
        ((User)userPanel.current).setFirstName(N2.getText());
        userPanel.updateAction(evt);
    }//GEN-LAST:event_N2KeyPressed

    private void N3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_N3KeyPressed
        if(evt.getKeyCode()!=10) return;
        ((User)userPanel.current).setMiddleName(N3.getText());
        userPanel.updateAction(evt);
    }//GEN-LAST:event_N3KeyPressed

    private void POSTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_POSTKeyPressed
        if(evt.getKeyCode()!=10) return;
        ((User)userPanel.current).setPost(POST.getText());
        userPanel.updateAction(evt);
    }//GEN-LAST:event_POSTKeyPressed

    private void PHONEKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PHONEKeyPressed
        if(evt.getKeyCode()!=10) return;
        Phone pp = new Phone(PHONE.getText());
        if (!pp.valid()){
            System.out.println("Формат номера телефона: "+PHONE.getText());
            PHONE.setText("???");
            }
        else{
            ((User)userPanel.current).setPhone(pp);
            userPanel.updateAction(evt);
            }
    }//GEN-LAST:event_PHONEKeyPressed

    private void MAILKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MAILKeyPressed
       if(evt.getKeyCode()!=10) return;
            Mail pp = new Mail(MAIL.getText());
            if (!pp.valid()){
                System.out.println("Формат E-mail: "+MAIL.getText());
                MAIL.setText("???");
                }
            else{
                ((User)userPanel.current).setMail(pp);
                userPanel.updateAction(evt);
                }
    }//GEN-LAST:event_MAILKeyPressed

    private void LoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LoginKeyPressed
        if(evt.getKeyCode()!=10) return;
        ((User)userPanel.current).setLogin(Login.getText());
        userPanel.updateAction(evt);
    }//GEN-LAST:event_LoginKeyPressed

    private void PHONEMKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PHONEMKKeyPressed
        if(evt.getKeyCode()!=10) return;
        Phone pp = new Phone(PHONEMK.getText());
        if (!pp.valid()){
            System.out.println("Формат номера телефона: "+PHONEMK.getText());
            PHONEMK.setText("???");            }
        else{
            ((User)userPanel.current).setLoginPhone(PHONEMK.getText());
            userPanel.updateAction(evt);
        }
    }//GEN-LAST:event_PHONEMKKeyPressed

    private void PASSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PASSKeyPressed
        if(evt.getKeyCode()!=10) return;
        ((User)userPanel.current).setPassword(PASS.getText());
        userPanel.updateAction(evt);
    }//GEN-LAST:event_PASSKeyPressed


    private void ViewPhotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ViewPhotoActionPerformed
        main.showImageArtifact(((User)userPanel.current).getPhoto().getRef());
    }//GEN-LAST:event_ViewPhotoActionPerformed

    private void UpploadPhotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpploadPhotoActionPerformed
        new UploadPanel(400, 300, main, new I_OK() {
            @Override
            public void onOK(Entity ent) {
                ((User)userPanel.current).getPhoto().setOidRef((Artifact) ent);
                userPanel.updateAction(null);
            }
        });
    }//GEN-LAST:event_UpploadPhotoActionPerformed

    private void CardICCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CardICCKeyPressed
        if(evt.getKeyCode()!=10) return;
        ((User)userPanel.current).setSimCardICC(CardICC.getText());
        userPanel.updateAction(evt);
    }//GEN-LAST:event_CardICCKeyPressed

    private void UserTypeSelectorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_UserTypeSelectorItemStateChanged
        userPanel.getAllEvent();
        userPanel.getById();
    }//GEN-LAST:event_UserTypeSelectorItemStateChanged

    @Override
    public void refresh() {
        //userPanel.getAllEvent();
        //userPanel.getById();
        }

    @Override
    public void eventPanel(int code, int par1, long par2, String par3,Object oo) {
        }

    @Override
    public void shutDown() {
        }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField CardICC;
    private javax.swing.JTextField Login;
    private javax.swing.JTextField MAIL;
    private javax.swing.JTextField N1;
    private javax.swing.JTextField N2;
    private javax.swing.JTextField N3;
    private javax.swing.JPasswordField PASS;
    private javax.swing.JTextField PHONE;
    private javax.swing.JTextField PHONEMK;
    private javax.swing.JTextField POST;
    private javax.swing.JTextField Type;
    private javax.swing.JButton UpploadPhoto;
    private java.awt.Choice UserTypeSelector;
    private javax.swing.JButton ViewPhoto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
