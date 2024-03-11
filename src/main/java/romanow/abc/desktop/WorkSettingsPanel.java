/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop;

import com.google.gson.Gson;
import org.apache.poi.ss.formula.functions.T;
import romanow.abc.core.DBRequest;
import romanow.abc.core.UniException;
import romanow.abc.core.constants.ConstList;
import romanow.abc.core.constants.ConstValue;
import romanow.abc.core.constants.ValuesBase;
import romanow.abc.core.entity.base.WorkSettingsBase;
import romanow.abc.core.entity.baseentityes.JEmpty;
import retrofit2.Response;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 *
 * @author romanow
 */
public class WorkSettingsPanel extends BasePanel{
    private WorkSettingsBase ws;
    private ConstList traceLevel;
    public WorkSettingsPanel() {
        initComponents();
        }
    public void initPanel(MainBaseFrame main0){
        super.initPanel(main0);
        traceLevel = ValuesBase.constMap().getValuesList("TraceLevel");
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel15 = new javax.swing.JLabel();
        ServerFileDirectory = new javax.swing.JTextField();
        ServerFileDirectoryDefault = new javax.swing.JCheckBox();
        ConvertArtifact = new javax.swing.JCheckBox();
        MailSendTo = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        MailHost = new javax.swing.JTextField();
        MailPort = new javax.swing.JTextField();
        MailBox = new javax.swing.JTextField();
        MailPass = new javax.swing.JTextField();
        MailSequrity = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        MailNotification = new javax.swing.JCheckBox();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        Refresh = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        TraceLevel = new java.awt.Choice();
        jLabel34 = new javax.swing.JLabel();
        LogDepthInDay = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();

        setLayout(null);

        jLabel15.setText("Каталог артефактов");
        add(jLabel15);
        jLabel15.setBounds(20, 40, 130, 16);

        ServerFileDirectory.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ServerFileDirectoryKeyPressed(evt);
            }
        });
        add(ServerFileDirectory);
        ServerFileDirectory.setBounds(140, 40, 240, 25);

        ServerFileDirectoryDefault.setSelected(true);
        ServerFileDirectoryDefault.setText("текущий");
        ServerFileDirectoryDefault.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ServerFileDirectoryDefaultItemStateChanged(evt);
            }
        });
        add(ServerFileDirectoryDefault);
        ServerFileDirectoryDefault.setBounds(20, 70, 110, 20);

        ConvertArtifact.setText("Конвертировать форматы");
        ConvertArtifact.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ConvertArtifactItemStateChanged(evt);
            }
        });
        add(ConvertArtifact);
        ConvertArtifact.setBounds(140, 70, 200, 20);

        MailSendTo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MailSendToKeyPressed(evt);
            }
        });
        add(MailSendTo);
        MailSendTo.setBounds(140, 270, 170, 25);

        jLabel12.setText("Порт");
        add(jLabel12);
        jLabel12.setBounds(30, 160, 60, 16);

        jLabel28.setText("Ящик");
        add(jLabel28);
        jLabel28.setBounds(30, 190, 40, 16);

        jLabel29.setText("Сервер ");
        add(jLabel29);
        jLabel29.setBounds(30, 130, 70, 16);

        jLabel30.setText("Пароль");
        add(jLabel30);
        jLabel30.setBounds(30, 220, 50, 16);

        jLabel31.setText("трассировки");
        add(jLabel31);
        jLabel31.setBounds(30, 340, 100, 16);

        MailHost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MailHostKeyPressed(evt);
            }
        });
        add(MailHost);
        MailHost.setBounds(140, 120, 170, 25);

        MailPort.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MailPortKeyPressed(evt);
            }
        });
        add(MailPort);
        MailPort.setBounds(140, 150, 50, 25);

        MailBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MailBoxKeyPressed(evt);
            }
        });
        add(MailBox);
        MailBox.setBounds(140, 180, 170, 25);

        MailPass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MailPassKeyPressed(evt);
            }
        });
        add(MailPass);
        MailPass.setBounds(140, 210, 90, 25);

        MailSequrity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MailSequrityKeyPressed(evt);
            }
        });
        add(MailSequrity);
        MailSequrity.setBounds(140, 240, 90, 25);

        jLabel32.setText("Безопасность");
        add(jLabel32);
        jLabel32.setBounds(30, 250, 90, 16);

        MailNotification.setText("Уведомления по Mail");
        MailNotification.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MailNotificationItemStateChanged(evt);
            }
        });
        add(MailNotification);
        MailNotification.setBounds(140, 300, 170, 20);
        add(jSeparator3);
        jSeparator3.setBounds(20, 110, 190, 10);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Уведомления");
        add(jLabel5);
        jLabel5.setBounds(220, 100, 90, 14);

        Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshActionPerformed(evt);
            }
        });
        add(Refresh);
        Refresh.setBounds(340, 80, 40, 40);

        jLabel33.setText("Получатель");
        add(jLabel33);
        jLabel33.setBounds(30, 280, 90, 16);

        TraceLevel.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TraceLevelItemStateChanged(evt);
            }
        });
        add(TraceLevel);
        TraceLevel.setBounds(140, 330, 90, 20);

        jLabel34.setText("логов (дней)");
        add(jLabel34);
        jLabel34.setBounds(30, 375, 90, 16);

        LogDepthInDay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LogDepthInDayKeyPressed(evt);
            }
        });
        add(LogDepthInDay);
        LogDepthInDay.setBounds(140, 360, 50, 25);

        jLabel35.setText("Уровень ");
        add(jLabel35);
        jLabel35.setBounds(30, 325, 90, 16);

        jLabel37.setText("Хранение");
        add(jLabel37);
        jLabel37.setBounds(30, 360, 90, 16);
    }// </editor-fold>//GEN-END:initComponents

    private void ServerFileDirectoryKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ServerFileDirectoryKeyPressed
        procPressedString(evt, ServerFileDirectory,"dataServerFileDir");
    }//GEN-LAST:event_ServerFileDirectoryKeyPressed

    private void ServerFileDirectoryDefaultItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ServerFileDirectoryDefaultItemStateChanged
        procPressedBoolean(ServerFileDirectoryDefault,"dataServerFileDirDefault");
    }//GEN-LAST:event_ServerFileDirectoryDefaultItemStateChanged

    private void ConvertArtifactItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ConvertArtifactItemStateChanged
        procPressedBoolean(ConvertArtifact,"convertAtrifact");
    }//GEN-LAST:event_ConvertArtifactItemStateChanged

    private void MailSendToKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MailSendToKeyPressed
        procPressedString(evt, MailSendTo,"mailToSend");
    }//GEN-LAST:event_MailSendToKeyPressed

    private void MailHostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MailHostKeyPressed
        procPressedString(evt, MailHost,"mailHost");
    }//GEN-LAST:event_MailHostKeyPressed

    private void MailPortKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MailPortKeyPressed
        procPressedInt(evt, MailPort,"mailPort");
    }//GEN-LAST:event_MailPortKeyPressed

    private void MailBoxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MailBoxKeyPressed
        procPressedString(evt, MailBox,"mailBox");
    }//GEN-LAST:event_MailBoxKeyPressed

    private void MailPassKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MailPassKeyPressed
        procPressedString(evt, MailPass,"mailPass");
    }//GEN-LAST:event_MailPassKeyPressed

    private void MailSequrityKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MailSequrityKeyPressed
        procPressedString(evt, MailSequrity,"mailSequr");
    }//GEN-LAST:event_MailSequrityKeyPressed

    private void MailNotificationItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MailNotificationItemStateChanged
        ws.setMailNotifycation(MailNotification.isSelected());
        updateSettings();
    }//GEN-LAST:event_MailNotificationItemStateChanged


    private void RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshActionPerformed
        refresh();
    }//GEN-LAST:event_RefreshActionPerformed

    private void TraceLevelItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TraceLevelItemStateChanged
        updateSettings(null,"traceLevel",TraceLevel.getSelectedIndex());
        refresh();
    }//GEN-LAST:event_TraceLevelItemStateChanged

    private void LogDepthInDayKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LogDepthInDayKeyPressed
        procPressedInt(evt, LogDepthInDay,"logDepthInDay");
    }//GEN-LAST:event_LogDepthInDayKeyPressed

    private void procPressedInt(KeyEvent evt, JTextField text, String name){
        if(evt.getKeyCode()!=10) return;
        int vv=0;
        try {
            vv = Integer.parseInt(text.getText());
        } catch (Exception ee){
            popup("Недопустимый формат целого");
            return;
        }
        updateSettings(evt,name,vv);
        refresh();
        }
    private void procPressedString(KeyEvent evt, JTextField text, String name){
        if(evt.getKeyCode()!=10) return;
        updateSettings(evt,name,text.getText());
        refresh();
        }
    private void procPressedBoolean(JCheckBox box, String name){
        updateSettings(null,name,box.isSelected());
        refresh();
        }

    @Override
    public void refresh() {
        try {
            String ss = main.getWorkSettings();
            if (ss!=null){
                System.out.println();
                return;
                }
            WorkSettingsBase ws = main.workSettings();
            ServerFileDirectory.setText(ws.getDataServerFileDir());
            ServerFileDirectoryDefault.setSelected(ws.isDataServerFileDirDefault());
            ConvertArtifact.setSelected(ws.isConvertAtrifact());
            MailHost.setText(ws.getMailHost());
            MailPass.setText(ws.getMailPass());
            MailSequrity.setText(ws.getMailSecur());
            MailBox.setText(ws.getMailBox());
            MailSendTo.setText(ws.getMailToSend());
            MailPort.setText(""+ws.getMailPort());
            MailNotification.setSelected(ws.isMailNotifycation());
            LogDepthInDay.setText(""+ws.getLogDepthInDay());
            TraceLevel.removeAll();
            for(ConstValue  trace : traceLevel)
                TraceLevel.add(trace.title());
            TraceLevel.select(ws.getTraceLevel());
            } catch (Exception e) { popup(e.toString()); }
    }

    @Override
    public void eventPanel(int code, int par1, long par2, String par3,Object oo) {
        if (code==EventRefreshSettings){
            refresh();
            main.sendEventPanel(EventRefreshSettingsDone,0,0,"",oo);
            }
        }

    @Override
    public void shutDown() {
    }

    private void updateSettings(){
        updateSettings(null);
        }
    private void updateSettings(KeyEvent evt){
        Response<JEmpty> wsr = null;
        try {
            wsr = main.getService().updateWorkSettings(main.getDebugToken(),new DBRequest(ws,new Gson())).execute();
            if (!wsr.isSuccessful()){
                popup("Ошибка обновления настроек  " + httpError(wsr));
                return;
                }
            popup("Настройки обновлены");
            if (evt!=null)
                main.viewUpdate(evt,true);
            main.sendEventPanel(EventRefreshSettings,0,0,"");
            } catch (IOException e) {
                main.viewUpdate(evt,false);
                popup(e.toString());
                }
        }
    private void updateSettings(KeyEvent evt, String name, int val){
        Response<JEmpty> wsr = null;
        try {
            wsr = main.getService().updateWorkSettings(main.getDebugToken(),name,val).execute();
            if (!wsr.isSuccessful()){
                popup("Ошибка обновления настроек  " + httpError(wsr));
                return;
                }
            popup("Настройки обновлены");
            if (evt!=null)
                main.viewUpdate(evt,true);
            main.sendEventPanel(EventRefreshSettings,0,0,"");
            } catch (IOException e) {
                main.viewUpdate(evt,false);
                popup(e.toString());
                }
        }
    private void updateSettings(KeyEvent evt, String name, boolean val){
        Response<JEmpty> wsr = null;
        try {
            wsr = main.getService().updateWorkSettings(main.getDebugToken(),name,val).execute();
            if (!wsr.isSuccessful()){
                popup("Ошибка обновления настроек  " + httpError(wsr));
                return;
            }
            popup("Настройки обновлены");
            if (evt!=null)
                main.viewUpdate(evt,true);
            main.sendEventPanel(EventRefreshSettings,0,0,"");
            } catch (IOException e) {
                main.viewUpdate(evt,false);
                popup(e.toString());
            }
        }
    private void updateSettings(KeyEvent evt, String name, String val){
        Response<JEmpty> wsr = null;
        try {
            wsr = main.getService().updateWorkSettings(main.getDebugToken(),name,val).execute();
            if (!wsr.isSuccessful()){
                popup("Ошибка обновления настроек  " + httpError(wsr));
                return;
                }
            popup("Настройки обновлены");
            if (evt!=null)
                main.viewUpdate(evt,true);
            main.sendEventPanel(EventRefreshSettings,0,0,"");
        } catch (IOException e) {
            main.viewUpdate(evt,false);
            popup(e.toString());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox ConvertArtifact;
    private javax.swing.JTextField LogDepthInDay;
    private javax.swing.JTextField MailBox;
    private javax.swing.JTextField MailHost;
    private javax.swing.JCheckBox MailNotification;
    private javax.swing.JTextField MailPass;
    private javax.swing.JTextField MailPort;
    private javax.swing.JTextField MailSendTo;
    private javax.swing.JTextField MailSequrity;
    private javax.swing.JButton Refresh;
    private javax.swing.JTextField ServerFileDirectory;
    private javax.swing.JCheckBox ServerFileDirectoryDefault;
    private java.awt.Choice TraceLevel;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSeparator jSeparator3;
    // End of variables declaration//GEN-END:variables
}
