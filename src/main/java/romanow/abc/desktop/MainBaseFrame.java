/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop;

import com.google.gson.Gson;
import romanow.abc.core.*;
import romanow.abc.core.API.RestAPIBase;
import romanow.abc.core.constants.ConstList;
import romanow.abc.core.constants.ConstValue;
import romanow.abc.core.constants.ValuesBase;
import romanow.abc.core.entity.Entity;
import romanow.abc.core.entity.EntityList;
import romanow.abc.core.entity.artifacts.Artifact;
import romanow.abc.core.entity.base.WorkSettingsBase;
import romanow.abc.core.entity.baseentityes.JEmpty;
import romanow.abc.core.entity.baseentityes.JString;
import romanow.abc.core.entity.users.User;
import romanow.abc.core.utils.FileNameExt;
import romanow.abc.core.utils.OwnDateTime;
import romanow.abc.core.utils.Pair;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import romanow.abc.dataserver.DataServer;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static romanow.abc.core.constants.ValuesBase.*;

/**
 *
 * @author romanow
 */
public class MainBaseFrame extends JFrame implements I_Important {
    protected WorkSettingsBase workSettings=new WorkSettingsBase();
    protected DataServer dataServer;
    protected ConstList constList;
    protected ArrayList<ConstValue> homeTypes;
    protected ArrayList<ConstValue> officeTypes;
    protected ArrayList<ConstValue> cityTypes;
    protected ArrayList<ConstValue> streetTypes;
    protected ArrayList<ConstValue> userTypes;
    private User loginUser=new User();
    private boolean localUser=false;
    protected String debugToken="";
    protected boolean refreshMode=false;
    protected Gson gson = new Gson();
    //---------------------------------------------------------------------------
    protected boolean serverOn=false;
    protected RestAPIBase service=null;                                   // Тип интерфейса
    protected String gblEncoding="";
    protected boolean utf8;
    private MESContext mesContext = null;
    //private TextArea MES=null;
    //private JTextField MESShort=null;
    //private JFrame logFrame=null;
    StringBuffer str = new StringBuffer();
    private int lineCount=0;
    //------------------------------------------------------------------------------------------------------------------
    public void logOff(){}
    //--------------------------------Ленивая загрузка------------------------------------------------------------------
    public EntityList<Entity> getList(String name, int mode,int level){
        EntityList<Entity> out = new EntityList<>();
        try {
            Response<ArrayList<DBRequest>> res = service.getEntityList(debugToken, name, mode,level).execute();
            if (!res.isSuccessful()){
                System.out.println("Ошибка " + Utils.httpError(res));
                }
            else{
                for (DBRequest rr : res.body()){
                    out.add(rr.get(gson));
                    }
                }
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            }
        return out;
        }
    public void restoreContext(){
        log = new LogStream(utf8, new I_String() {
            @Override
            public void onEvent(String zz) {
                String zz2 = zz.length() > 70 ? zz.substring(0,70)+"..." : zz;
                if (mesContext.MES!=null)
                    mesContext.MES.append(zz+"\n");
                if (mesContext.MESShort!=null)
                    mesContext.MESShort.setText(zz);
                if (mesContext.logFrame!=null)
                    sendPopupMessage(mesContext.logFrame,20,mesContext.logFrame.getHeight()-50,zz2);
                dataServer.addToLog(zz);
                }
            });
        System.setOut(new PrintStream(log));
        System.setErr(new PrintStream(log));
        }
    private LogStream log;
    //------------------------------------------------------------------------------------------------------------------
    public MainBaseFrame() {
        this(true);
        }
    public MainBaseFrame(boolean setLog) {
        initComponents();
        gblEncoding = System.getProperty("file.encoding");
        utf8 = gblEncoding.equals("UTF-8");
        if (setLog)
            restoreContext();
        }
    public final static int X0=100;
    public final static int Y0=100;
    private String serverIP="";
    private String serverPort="";
    public String getServerIP() {
        return serverIP; }
    public String getServerPort() {
        return serverPort; }
    public URL createURLForArtifact(Artifact art){
        String path = art.createArtifactServerPath();
        String ss = "http://"+serverIP+":"+serverPort+"/file/"+path;
        URL url=null;
        try {
            url = new URL(ss);
        } catch (MalformedURLException e) {
            System.out.println(e.toString());
            return null;
            }
        return url;
        }
    public void onRightButton(JFrame parent, Container ct, MouseEvent evt, String mes){
        onRightButton(parent,ct,0,evt,mes);
        }
    public void onRightButton(JFrame parent, Container ct,int dy, MouseEvent evt, String mes){
        if (evt.getButton()==3){
            JPopupMenu menu = new JPopupMenu();
            menu.add(mes);
            Point pp = evt.getLocationOnScreen();
            menu.show(parent,ct.getBounds().x+60,ct.getBounds().y+60+dy);
            }
        }

    public static void viewUpdate(final KeyEvent evt, boolean good){
        if (evt==null){
            System.out.println("Изменения приняты");
            return;
            }
        evt.getComponent().setBackground(good ? Color.green : Color.yellow);
        delayInGUI(2, new Runnable() {
            @Override
            public void run() {
                evt.getComponent().setBackground(Color.white);
            }
        });
    }

    public static void delayInGUI(final int sec,final Runnable code){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000*sec);
                    java.awt.EventQueue.invokeLater(code);
                    } catch (InterruptedException e) {}
                }
            }).start();
        }


    public void sendPopupMessage(JFrame parent, Container ct, String mes){
        sendPopupMessage(parent,ct.getBounds().x+60,ct.getBounds().y+60,mes);
        }
    public void sendPopupMessage(JFrame parent, int x0, int y0, String mes){
        final JPopupMenu menu = new JPopupMenu();
        menu.add(mes);
        menu.show(parent,x0,y0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    } catch (InterruptedException e) {}
                menu.setVisible(false);
                }
            }).start();
        }
    public void setMES(TextArea mes0){
        setMES(mes0,null,null);
        }
    public void setMES(JFrame ff){
        setMES(null,ff,null);
    }
    public void setMES(JTextField ff){ setMES(null,null,ff); }
    public void setMES(TextArea mes0,JFrame  frame,JTextField ff){
        mesContext = new MESContext(mes0,frame,ff);
        }
    public MESContext getMesContext() {
        return mesContext; }
    public void setMesContext(MESContext mesContext) {
        this.mesContext = mesContext; }
    //-----------------------------------------------------------------------------------------------
    public Pair<RestAPIBase,String> startOneClient(String ip, String port) throws UniException {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(ValuesBase.HTTPTimeOut, TimeUnit.SECONDS)
                .connectTimeout(ValuesBase.HTTPTimeOut, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://"+ip+":"+port)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        localUser = ip.equals("localhost") || ip.equals("127.0.0.1");
        RestAPIBase service = (RestAPIBase)retrofit.create(RestAPIBase.class);
        JString ss = new APICall2<JString>(){
            @Override
            public Call<JString> apiFun() {
                return service.debugToken(ValuesBase.DebugTokenPass);
                }
            }.call(this);
        return new Pair<>(service,ss.getValue());
        }
    public void loadConstants() throws UniException{
        constList = new APICall2<ConstList>(){
            @Override
            public Call<ConstList> apiFun() {
                return service.getConstAll(debugToken);
            }
        }.call(this);
        homeTypes = constList.getValuesList("HomeType");
        officeTypes = constList.getValuesList("OfficeType");
        streetTypes = constList.getValuesList("StreetType");
        cityTypes = constList.getValuesList("TownType");
        userTypes = constList.getValuesList("User");
        }

    public boolean startClient(String ip, String port){
        serverIP=ip;
        serverPort=port;
        try {
            Pair<RestAPIBase,String> res = startOneClient(ip,port);
            service = res.o1;
            debugToken = res.o2;
            loadConstants();
            } catch (UniException e) {
                System.out.println("Ошибка ключа отладки "+e.toString());
                return false;
                }
        return true;
        }
    public void showImageArtifact(Artifact art){
        if (art.type()!=ValuesBase.ArtifactImageType)
            System.out.println("Это не изображение");
        else{
            FileNameExt fname = new FileNameExt("temp."+art.getOriginalExt());
            loadFile(art, fname, new I_Success() {
                @Override
                public void onSuccess() {
                    new ImageView(art.getTitle(),200,200,400,fname);
                }
            });
            }
        }
    public void showVideoArtifact(Artifact art){
        if (art.type()!=ValuesBase.ArtifactVideoType)
            System.out.println("Это не видео");
        else{
            FileNameExt fname = new FileNameExt("temp."+art.getOriginalExt());
            loadFile(art, fname, new I_Success() {
                @Override
                public void onSuccess() {
                    new VideoPanel(art.getTitle(),200,200,640,480,new File("").getAbsolutePath()+"/"+fname.fullName());
                }
            });
        }
    }
    public void loadFile(Artifact art, FileNameExt ff, I_Success back){
        if (ff==null){
            ff = getOutputFileName("Загрузка файла",art.getOriginalExt(),art.getOriginalName());
            if (ff==null) return;
            }
        loadFile(art, ff.fullName(), new I_DownLoad() {
            @Override
            public void onSuccess() {
                back.onSuccess();
                }
            @Override
            public void onError(String mes) {
                System.out.println(mes);
            }
        });
        }
    public void loadFile(Artifact art){
        FileNameExt ff = getOutputFileName("Загрузка файла",art.getOriginalExt(),art.getOriginalName());
        if (ff==null)
            return;
        loadFile(art, ff.fullName(),null);
        }
    public void loadFileAndDelete(Artifact art){
        final FileNameExt ff = getOutputFileName("Загрузка файла",art.getOriginalExt(),art.getOriginalName());
        if (ff==null)
            return;
        loadFile(art, ff.fullName(), new I_DownLoad() {
            @Override
            public void onSuccess() {
                System.out.println("Файл загружен: "+ff.fileName());
                new APICall<JEmpty>(MainBaseFrame.this){
                    @Override
                    public Call<JEmpty> apiFun() {
                        return service.removeArtifact(debugToken,art.getOid());
                    }
                    @Override
                    public void onSucess(JEmpty oo) {}
                    };
                }

            @Override
            public void onError(String mes) {
                System.out.println(mes);
            }
        });
        }

    public void loadFile(Artifact art, String fspec, final I_DownLoad back){
        Call<ResponseBody> call2 = service.downLoad(debugToken,art.getOid());
        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    long fileSize = body.contentLength();
                    InputStream in = body.byteStream();
                    try {
                        FileOutputStream out = new FileOutputStream(fspec);
                        while (fileSize-- != 0)
                            out.write(in.read());
                        in.close();
                        out.flush();
                        out.close();
                        if (back!=null)
                            back.onSuccess();
                        else
                            System.out.println("Файл загружен "+fspec);
                        } catch (IOException ee) {
                        String mes = Utils.createFatalMessage(ee);
                            if (back!=null)
                                back.onError(mes);
                            else
                                System.out.println(mes);
                        }
                    }
                else{
                    String mes = Utils.httpError(response);
                    if (back!=null)
                        back.onError(mes);
                    else
                        System.out.println(mes);
                    }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String mes = Utils.createFatalMessage(t);
                if (back!=null)
                    back.onError(mes);
                else
                    System.out.println(mes);
                }
            });
        }

    public void loadFile(String folder, String fname){
        int idx = fname.lastIndexOf(".");
        String ext = idx==-1 ? "" : fname.substring(idx+1);
        FileNameExt ff = getOutputFileName("Загрузка файла",ext,fname);
        if (ff==null)
            return;
        loadFileByName(ff.fullName(),folder,fname,null);
        }
    public void loadFileByName(final String outName, String folder, final String fname, final I_DownLoad back){
        Call<ResponseBody> call2 = service.downLoadByName(debugToken,folder+"/"+fname,false);
        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    long fileSize = body.contentLength();
                    InputStream in = body.byteStream();
                    try {
                        FileOutputStream out = new FileOutputStream(outName);
                        while (fileSize-- != 0)
                            out.write(in.read());
                        in.close();
                        out.flush();
                        out.close();
                        if (back!=null)
                            back.onSuccess();
                        else
                            System.out.println("Файл загружен "+fname);
                    } catch (IOException ee) {
                        String mes = Utils.createFatalMessage(ee);
                        if (back!=null)
                            back.onError(mes);
                        else
                            System.out.println(mes);
                    }
                }
                else{
                    String mes = Utils.httpError(response);
                    if (back!=null)
                        back.onError(mes);
                    else
                        System.out.println(mes);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String mes = Utils.createFatalMessage(t);
                if (back!=null)
                    back.onError(mes);
                else
                    System.out.println(mes);
            }
        });
    }

    public void viewCalendarPeriod(I_Period fun){
        new CalendarView("Начало периода",new I_CalendarTime() {
            @Override
            public void onSelect(final OwnDateTime time1) {
                new CalendarView("Начало периода",new I_CalendarTime() {
                    @Override
                    public void onSelect(OwnDateTime time2) {
                        fun.onSelect(time1,time2);
                    }
                });
                }
            });
        }
    //-------------------------------------------------------------------------------------------------------------
    public boolean tryToStart(){
        I_Important frame = AppData.ctx().tryToStart(this);
        if (frame!=null){
            ((MainBaseFrame)frame).toFront();
            dispose();
            return false;
        }
        else{
            setVisible(true);
            return true;
        }
    }
    public void onClose(){
        AppData.ctx().onClose(this);
        dispose();
    }
    public void sendEvent(int code,boolean on, int value, String name){
        AppData.ctx().sendEvent(code,on,value,name);
    }
    @Override
    synchronized public void onEvent(int code,boolean on, int value, String name) {
        //System.out.println(getClass().getSimpleName()+" "+code+" "+on+" "+value+" "+name);
    }
    public AppData ctx(){ return AppData.ctx(); }
    public FileNameExt getInputFileName(String title, final String defName, String defDir){
        FileDialog dlg=new FileDialog(this,title,FileDialog.LOAD);
        if (defDir!=null){
            dlg.setDirectory(defDir);
        }
        if (defName.indexOf(".")==-1)
            dlg.setFile("*."+defName);
        else
            dlg.setFile(defName);
        dlg.show();
        String fname=dlg.getDirectory();
        if (fname==null) return null;
        FileNameExt out = new FileNameExt(fname,dlg.getFile());
        return out;
    }

    public FileNameExt getOutputFileName(String title, final String defName, String srcName){
        FileDialog dlg=new FileDialog(this,title,FileDialog.SAVE);
        dlg.setFile(srcName);
        dlg.setFilenameFilter(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith("."+defName);
            }
        });
        dlg.show();
        if (dlg.getDirectory()==null) return null;
        String fname = dlg.getFile();
        if (!fname.endsWith("."+defName))
            fname+="."+defName;
        FileNameExt out = new FileNameExt(dlg.getDirectory(),fname);
        return out;
        }
    public User loginUser() {
        return loginUser; }
    public void loginUser(User loginUser) {
        this.loginUser = loginUser;
        }
    public boolean isLocalUser() {
        return localUser; }
    public void setLocalUser(boolean localUser) {
        this.localUser = localUser; }
    //------------------------------ Всякий код -------------------------------------------------------------------
    public void sendEvent(int code, long par2){}
    public void sendEventPanel(int code, int par1, long par2, String par3){}
    public void panelToFront(BasePanel pp){}
    public void popup(String ss){}
    public void refresh(){}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(MainBaseFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainBaseFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainBaseFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainBaseFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainBaseFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
