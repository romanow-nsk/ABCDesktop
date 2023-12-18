/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop;

import com.google.gson.Gson;
import com.mongodb.DB;
import lombok.Getter;
import lombok.Setter;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import romanow.abc.core.API.RestAPIBase;
import romanow.abc.core.DBRequest;
import romanow.abc.core.UniException;
import romanow.abc.core.Utils;
import romanow.abc.core.constants.ValuesBase;
import romanow.abc.core.entity.base.WorkSettingsBase;
import romanow.abc.core.entity.baseentityes.JEmpty;
import romanow.abc.core.entity.users.User;
import retrofit2.Call;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static romanow.abc.core.constants.ValuesBase.*;

/**
 *
 * @author romanow
 */
public class Client extends MainBaseFrame {
    public final static int PanelOffsetY=60;
    public final static int RatioW=4;
    public final static int RatioH=3;
    public final static int PanelH= ScreenDesktopHeight+20;
    public final static int PanelW=ScreenDesktopWidth+20;
    public final static int MesW=600;
    public final static int ShortView=PanelW+30;
    public final static int ViewHight = PanelH+80;
    private LogView logView = new LogView();
    @Getter @Setter private LogPanel logPanel;
    private Login loginForm=null;
    private I_OK disposeBack = null;
    @Getter @Setter private ArrayList<I_PanelEvent> panels = new ArrayList();
    private boolean secondForm=false;
    //----------------------------------------------------------------
    public final ArrayList<PanelDescriptor> panelDescList=new ArrayList<>();
    public void setLoginName(String name){
        loginForm.setLoginName(name);
        }
    public void setPassword(String name){
        loginForm.setPassword(name);
        }
    public void addIP(String ss){ loginForm.addIP(ss); }
    public void initComponentsPublic(){
        initComponents();
        }
    public void initPanels(){
        panelDescList.add(new PanelDescriptor("Трассировка", LogPanel.class,null));
        //---------- <0 - readOnly Mode
        panelDescList.add(new PanelDescriptor("Пользователи", UserPanelBase.class,new int[]
                {UserSuperAdminType, UserAdminType}));
        //panelDescList.add(new PanelDescriptor("Отчеты/Уведомления", ReportsPanelBase.class,new int[]
        //        {UserSuperAdminType, UserAdminType}));
        panelDescList.add(new PanelDescriptor("Сервер",ServerPanel.class,new int[]
                {UserSuperAdminType}));
        //panelDescList.add(new PanelDescriptor("Помощь",HelpPanel.class,new int[]
        //        {UserSuperAdminType, UserAdminType}));
        panelDescList.add(new PanelDescriptor("Артефакты",ArtifactPanel.class,new int[]
                {UserSuperAdminType, UserAdminType}));
        panelDescList.add(new PanelDescriptor("Настройки сервера",WorkSettingsPanel.class,new int[]
                {UserSuperAdminType}));
        }

    private void login(){
        setVisible(false);
        if (loginForm!=null)
            loginForm.setVisible(true);
        else {
            loginForm = new Login(getClientContext(), new I_LoginBack() {
                @Override
                public void onPush() {
                    startUser();
                    Client.this.setVisible(true);
                    }
                @Override
                public void onLoginSuccess(String passWord) {
                    Client.this.onLoginSuccess();
                    }
                @Override
                public void sendPopupMessage(JFrame parent, Container button, String text) {
                    Client.this.sendPopupMessage(parent,button,text);
                    }
            });
            setMES(loginForm.getLogPanel());
            }
        }

    public Client(boolean setLog0){
        this(setLog0,false);
        }
    public Client() {
        this(true,false);
        }
    public Client(boolean setLog, boolean offline0) {
        super(setLog);
        offline = offline0;
        secondForm=false;
        initComponents();
        setMES(loginForm);
        initPanels();
        if (!offline)
            login();
        else
            onLoginSuccess();
        }

    public Client(RestAPIBase service0, User user0, I_OK disposeBack0) throws UniException{
        ValuesBase.init();
        initComponents();
        disposeBack = disposeBack0;
        secondForm=true;
        setService(service0);
        loginUser(user0);
        setDebugToken(user0.getSessionToken());
        loadConstants();
        startUser();
        }
    public void startUser(){
        try {
            getClientContext().getLoginUser().setLogin(loginForm.getLoginName());
            setTitle(ValuesBase.env().applicationName(AppNameTitle)+": "+loginUser().getHeader());
            setDebugToken(loginUser().getSessionToken());
            setBounds(ScreenDesktopX0, ScreenDesktopY0, ShortView, ViewHight);
            PanelList.setBounds(10,10,PanelW,PanelH);
            ShowLog.setSelected(false);
            PanelList.removeAll();
            panels.clear();
            DBRequest ws = new APICall2<DBRequest>(){
                @Override
                public Call<DBRequest> apiFun() {
                    return getService().workSettings(getDebugToken());
                    }
                }.call(this);
            WorkSettingsBase base = (WorkSettingsBase)ws.get(new Gson());
            boolean mainMode = base.isMainServer();
            for(PanelDescriptor pp : panelDescList){
                boolean bb=false;
                boolean editMode = true;
                if (pp.userTypes==null){
                    bb=true;
                    editMode=false;
                    }
                else{
                    if (loginUser().getTypeId()== UserSuperAdminType){
                        bb=true;
                        editMode=true;
                        }
                    else{
                        for(int vv : pp.userTypes)
                            if (Math.abs(vv)==loginUser().getTypeId()){
                                bb=true;
                                editMode = vv > 0;
                                break;
                                }
                        }
                    }
                if (bb){
                    BasePanel panel = (BasePanel) pp.view.newInstance();
                    if (panel instanceof LogPanel){
                        logPanel = (LogPanel)panel;
                        setMES(logPanel.mes(),logView,MESLOC);
                        }
                    if (mainMode && panel.isMainMode() || !mainMode && panel.isESSMode()){
                        panel.editMode = editMode;
                    try {
                        panel.initPanel(this);
                        panels.add(panel);
                        PanelList.add(pp.name, panel);
                        } catch (Exception ee){
                            System.out.println("Ошибка открытия панели "+pp.name+"\n"+ee.toString());
                            }
                        }
                    }
                }
            setMES(logPanel.mes(),logView,MESLOC);
            BasePanel pn;
            refresh();
            } catch(Exception ee){
                System.out.println(ee.toString());
                ee.printStackTrace();
                }
        }
    @Override
    public void refresh(){
        refreshMode=true;
        for(I_PanelEvent xx : panels)
            xx.refresh();
        refreshMode=false;
        }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelList = new javax.swing.JTabbedPane();
        ShowLog = new javax.swing.JCheckBox();
        MESLOC = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        PanelList.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(PanelList);
        PanelList.setBounds(10, 10, 850, 700);

        ShowLog.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        ShowLog.setText("log");
        ShowLog.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ShowLogItemStateChanged(evt);
            }
        });
        getContentPane().add(ShowLog);
        ShowLog.setBounds(820, 750, 60, 21);
        getContentPane().add(MESLOC);
        MESLOC.setBounds(10, 750, 800, 25);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    @Override
    public void logOff(){
        ShowLog.setSelected(false);
        if (secondForm){
            dispose();
            return;
            }
        for(I_PanelEvent xx : panels)
            xx.shutDown();
        try {
            Response<JEmpty> res = getService().logoff(getDebugToken()).execute();
            if (!res.isSuccessful()){
                System.out.println("Ошибка сервера: "+ Utils.httpError(res));
                }
            loginForm.disConnect();
            setMES(loginForm);
            login();
            }catch (Exception ee){
                System.out.println("Ошибка сервера: "+ee.toString());
                loginForm.disConnect();
                login();
                }
        }
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (offline) {
            dispose();
            return;
            }
        logOff();
        if (disposeBack!=null)
            disposeBack.onOK(null);
    }//GEN-LAST:event_formWindowClosing

    private void ShowLogItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ShowLogItemStateChanged
        logView.setVisible(ShowLog.isSelected());
    }//GEN-LAST:event_ShowLogItemStateChanged
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void sendEvent(int code, long par2){
        sendEventPanel(code,0,par2,"");
        }
    @Override
    public void sendEventPanel(int code, int par1, long par2, String par3,Object oo){
        if (refreshMode) return;
        for(I_PanelEvent xx : panels)
            xx.eventPanel(code, par1,par2,par3,oo);
        }
    @Override
    public void panelToFront(BasePanel pp){
        for (int i=0;i<panels.size();i++)
            if (panels.get(i) == pp) {
                PanelList.setSelectedIndex(i);
                break;
            }
        }
    @Override
    public void popup(String ss){
        System.out.println(ss);
        new Message(300,300,ss, ValuesBase.PopupMessageDelay);
        }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Client();
            }
        });
    }

    public LogView getLogView() {
        return logView;}
    public JTextField getMESLOC() {
        return MESLOC;}
    public JTabbedPane getPanelList() {
        return PanelList;}
    public JCheckBox getShowLog() {
        return ShowLog;}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField MESLOC;
    private javax.swing.JTabbedPane PanelList;
    private javax.swing.JCheckBox ShowLog;

    // End of variables declaration//GEN-END:variables
}
