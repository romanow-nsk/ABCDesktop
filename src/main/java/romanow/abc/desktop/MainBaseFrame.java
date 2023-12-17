/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import okhttp3.MultipartBody;
import romanow.abc.core.*;
import romanow.abc.core.API.RestAPIBase;
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
import romanow.abc.core.utils.StringFIFO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static romanow.abc.core.Utils.httpError;

/**
 *
 * @author romanow
 */
public class MainBaseFrame extends JFrame implements I_Important {
    private ClientContext clientContext = new ClientContext();
    public RestAPIBase getService() {
        return clientContext.getService();}
    public User loginUser() {
        return clientContext.getLoginUser(); }
    public void loginUser(User loginUser) {
        clientContext.setLoginUser(loginUser);
        }
    public boolean isLocalUser() {
        return clientContext.isLocalUser(); }
    public void setLocalUser(boolean localUser) {
        clientContext.setLocalUser(localUser); }
    public String getServerIP(){
        return clientContext.getServerIP(); }
    public String getServerPort(){
        return clientContext.getServerPort(); }
    public void loadConstants() throws UniException {
        clientContext.loadConstants(); }
    public boolean startClient(String ip, String port) {
        return clientContext.startClient(ip,port); }
    public URL createURLForArtifact(Artifact art) {
        return clientContext.createURLForArtifact(art);}
    public ArrayList<ConstValue> homeTypes(){
        return clientContext.getHomeTypes(); }
    public ArrayList<ConstValue> officeTypes(){
        return clientContext.getOfficeTypes(); }
    public ArrayList<ConstValue> userTypes(){
        return clientContext.getUserTypes(); }
    public ArrayList<ConstValue> constList(){
        return clientContext.getConstList(); }
    public ArrayList<ConstValue> filter(ArrayList<ConstValue> src, String filter){
        return clientContext.filter(src,filter); }
    public WorkSettingsBase workSettings(){
        return clientContext.workSettings(); }
    public String getWorkSettings(){
        return clientContext.getWorkSettings(); }
    public String getDebugToken(){
        return clientContext.getDebugToken(); }
    public Pair<RestAPIBase, String> startOneClient(String ip, String port) throws UniException {
        return clientContext.startOneClient(ip,port); }
    public void updateArtifactFromString(Artifact art, String text, final I_OK ok) {//GEN-FIRST:event_SaveActionPerformed
        clientContext.updateArtifactFromString(art,text,ok);}
    public void setLoginUser(User user){
        clientContext.setLoginUser(user);}
    public void setWorkSettins(WorkSettingsBase ws){
        clientContext.setWorkSettings(ws); }
    public void loadFile(long artOid, String fspec, final I_DownLoad back) {
        clientContext.loadFile(artOid,fspec,back);}
    public void loadFile(Artifact art, String fspec, final I_DownLoad back){
        clientContext.loadFile(art,fspec,back);}
    public void loadFileByName(final String outName, String folder, final String fname, final I_DownLoad back){
        clientContext.loadFileByName(outName,folder,fname,back);}
    public void loadFileAsString(Artifact art,final I_DownLoadString back) {
        clientContext.loadFileAsString(art,back);}
    public Pair<String,String> loadFileAsStringSync(Artifact art) {
        return clientContext.loadFileAsStringSync(art); }
    public void loadFileGroup(final String dir,final ArrayList<Artifact> artifacts, final int idx) {
        clientContext.loadFileGroup(dir,artifacts,idx);}
    public void loadFileGroup(final String dir, final ArrayList<String> outTitles, final ArrayList<Artifact> artifacts, final int idx) {
        clientContext.loadFileGroup(dir,outTitles,artifacts,idx); }
    public void setDebugToken(String debugToken) {
        clientContext.setDebugToken(debugToken);}
    public void setService(RestAPIBase base){
        clientContext.setService(base); }
    public ArrayList<String> getServerEnvironment(){
        return clientContext.getServerEnvironment();}
    public void setServerEnvironment( ArrayList<String> ss){
        clientContext.setServerEnvironment(ss); }
    //------------------------------------------------------------------------------------------------------------------
    protected boolean offline=false;              // АВТОНОМНЫЙ клиент
    //protected WorkSettingsBase workSettings=null;
    //protected ArrayList<ConstValue> constList;
    //protected ArrayList<ConstValue> homeTypes;
    //protected ArrayList<ConstValue> officeTypes;
    //protected ArrayList<ConstValue> cityTypes;
    //protected ArrayList<ConstValue> streetTypes;
    //protected ArrayList<ConstValue> userTypes;
    //protected User loginUser=new User();
    //protected boolean localUser=false;
    //protected String debugToken="";
    //protected ArrayList<String> serverEnvironment;
    //public ArrayList<ConstValue> getConstList() {
    //    return constList;}
    //public String getDebugToken() {
    //    return debugToken;}
    //---------------------------------------------------------------------------
    private StringFIFO externalFIFO=null;
    protected boolean serverOn=false;
    //protected RestAPIBase service=null;                                   // Тип интерфейса
    protected boolean refreshMode=false;
    protected Gson gson = new Gson();
    protected String gblEncoding="";
    protected boolean utf8;
    private MESContext mesContext = null;
    //------------------------------------------------------------------------------------------------------------------
    public void logOff(){}
    //--------------------------------Ленивая загрузка------------------------------------------------------------------
    public EntityList<Entity> getList(String name, int mode,int level){
        EntityList<Entity> out = new EntityList<>();
        try {
            Response<ArrayList<DBRequest>> res = getService().getEntityList(getDebugToken(), name, mode,level).execute();
            if (!res.isSuccessful()){
                System.out.println("Ошибка " + httpError(res));
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

    public void setExternalFIFO(StringFIFO externalFIFO) {
        log.setStringFIFO(externalFIFO);
        }
    public void restoreContext(){
        log = new LogStream(utf8, null, new I_String() {
            @Override
            public void onEvent(final String zz) {
                final String zz2 = zz.length() > 70 ? zz.substring(0,70)+"..." : zz;
                if (mesContext.logFrame!=null)
                    mesContext.logFrame.getLogArea().append(zz+"\n");
                if (mesContext.MES!=null || mesContext.MESShort!=null)
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            if (mesContext.MES!=null)
                                mesContext.MES.append(zz+"\n");
                            if (mesContext.MESShort!=null)
                                mesContext.MESShort.setText(zz);
                            //if (mesContext.MES==null && mesContext.MESShort==null && mesContext.logFrame==null)
                            //    sendPopupMessage(mesContext.logFrame,20,mesContext.logFrame.getHeight()-50,zz);
                        }
                    });
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
    //private String serverIP="";
    //private String serverPort="";
    //public String getServerIP() {
    //    return serverIP; }
    //public String getServerPort() {
    //    return serverPort; }
    /*
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
     */
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

    public static void viewUpdate(final Component evt, boolean good){
        if (evt==null){
            System.out.println("Изменения приняты");
            return;
        }
        evt.setBackground(good ? Color.green : Color.yellow);
        delayInGUI(2, new Runnable() {
            @Override
            public void run() {
                evt.setBackground(Color.white);
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
    private ArrayList<String> messages = new ArrayList<>();
    private synchronized void onePopup(final JFrame parent, final int x0, final int y0){
        final JPopupMenu menu = new JPopupMenu();
        menu.add(messages.get(0));
        //menu.setPopupSize(400,50);
        menu.show(parent,x0,y0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {}
                menu.setVisible(false);
                synchronized (MainBaseFrame.this){
                    messages.remove(0);
                    if (messages.size()!=0)
                        onePopup(parent,x0,y0);
                    }
                }
            }).start();
        }
    synchronized public void sendPopupMessage(JFrame parent, int x0, int y0, String mes){
        messages.add(mes);
        if (messages.size()>1)
            return;
        onePopup(parent,x0,y0);
        }
    public void setMES(TextArea mes0){
        setMES(mes0,null,null);
        }
    public void setMES(I_LogArea ff){
        setMES(null,ff,null);
    }
    public void setMES(JTextField ff){ setMES(null,null,ff); }
    public void setMES(TextArea mes0,I_LogArea  frame,JTextField ff){
        mesContext = new MESContext(mes0,frame,ff);
        }
    public MESContext getMesContext() {
        return mesContext; }
    public void setMesContext(MESContext mesContext) {
        this.mesContext = mesContext; }
    //-----------------------------------------------------------------------------------------------
    public void onLoginSuccess(){}
    /*
    public Pair<RestAPIBase,String> startOneClient(String ip, String port) throws UniException {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(ValuesBase.HTTPTimeOut, TimeUnit.SECONDS)
                .connectTimeout(ValuesBase.HTTPTimeOut, TimeUnit.SECONDS)
                .build();
        System.out.println("API: пул потоков="+okHttpClient.dispatcher().getMaxRequests());
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
        loadConstants(false);
        }
    public void loadConstants(boolean local) throws UniException{
        if (local)
            constList = ValuesBase.constMap().getConstList();
        else{
            constList = new APICall2<ArrayList<ConstValue>>(){
                @Override
                public Call<ArrayList<ConstValue>> apiFun() {
                    return service.getConstAll(debugToken);
                    }
                }.call(this);
            }
        homeTypes = filter(constList,"HomeType");
        officeTypes = filter(constList,"OfficeType");
        streetTypes = filter(constList,"StreetType");
        cityTypes = filter(constList,"TownType");
        userTypes = filter(constList,"User");
        }
    ArrayList<ConstValue> filter(ArrayList<ConstValue> src,String filter){
        ArrayList<ConstValue> out = new ArrayList<>();
        for(ConstValue cc : src)
            if (cc.groupName().equals(filter))
                out.add(cc);
        return out;
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
     */
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
            ff = getOutputFileName("Экспорт файла",art.getOriginalExt(),art.getOriginalName());
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
        FileNameExt ff = getOutputFileName("Экспорт файла",art.getOriginalExt(),art.getOriginalName());
        if (ff==null)
            return;
        loadFile(art, ff.fullName(),null);
        }
    public void loadFileAndDelete(Artifact art){
        final FileNameExt ff = getOutputFileName("Экспорт файла",art.getOriginalExt(),art.getOriginalName());
        if (ff==null)
            return;
        loadFile(art, ff.fullName(), new I_DownLoad() {
            @Override
            public void onSuccess() {
                System.out.println("Файл загружен: "+ff.fileName());
                new APICall<JEmpty>(MainBaseFrame.this){
                    @Override
                    public Call<JEmpty> apiFun() {
                        return getService().removeArtifact(getDebugToken(),art.getOid());
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
    /*
    public void loadFileGroup(final String dir,final ArrayList<Artifact> artifacts, final int idx){
        loadFileGroup(dir,null,artifacts,idx);
        }
    public void loadFileGroup(final String dir, final ArrayList<String> outTitles, final ArrayList<Artifact> artifacts, final int idx){
        if (idx>=artifacts.size())
            return;
        final Artifact art = artifacts.get(idx);
        String outSpec = outTitles==null ? art.getOriginalName() : outTitles.get(idx)+"."+art.getOriginal().getExt();
        loadFile(art, dir + "/" + outSpec, new I_DownLoad() {
            @Override
            public void onSuccess() {
                System.out.println("Экспорт файла выполнен "+art.getOriginalName());
                loadFileGroup(dir,outTitles,artifacts,idx+1);
                }
            @Override
            public void onError(String mes) {
                System.out.println("Ошибка экспорта "+art.getOriginalName()+"\n"+mes);
                loadFileGroup(dir,outTitles, artifacts,idx+1);
                }
            });
        }
    public void loadFile(Artifact art, String fspec, final I_DownLoad back){
        loadFile(art.getOid(),fspec,back);
        }
    public void loadFile(long artOid, String fspec, final I_DownLoad back){
        Call<ResponseBody> call2 = service.downLoad(debugToken,artOid);
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
                            System.out.println("Экспорт файла выполнен "+fspec);
                        } catch (IOException ee) {
                        String mes = Utils.createFatalMessage(ee);
                            if (back!=null)
                                back.onError(mes);
                            else
                                System.out.println(mes);
                        }
                    }
                else{
                    String mes = httpError(response);
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
    */
    public void loadFile(String folder, String fname){
        int idx = fname.lastIndexOf(".");
        String ext = idx==-1 ? "" : fname.substring(idx+1);
        FileNameExt ff = getOutputFileName("Экспорт файла",ext,fname);
        if (ff==null)
            return;
        loadFileByName(ff.fullName(),folder,fname,null);
        }
    /*
    //------------------------------------------------------------------------------------------------------------------
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
                            System.out.println("Экспорт файла выполнен "+fname);
                    } catch (IOException ee) {
                        String mes = Utils.createFatalMessage(ee);
                        if (back!=null)
                            back.onError(mes);
                        else
                            System.out.println(mes);
                    }
                }
                else{
                    String mes = httpError(response);
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
    //------------------------------------------------------------------------------------------------------------------
    public void loadFileAsString(Artifact art,final I_DownLoadString back){
        Call<ResponseBody> call2 = service.downLoad(debugToken,art.getOid());
        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    long fileSize = body.contentLength();
                    InputStream in = body.byteStream();
                    try {
                        InputStreamReader reader = new InputStreamReader(in,"UTF8");
                        StringBuffer buffer = new StringBuffer();
                        int cc;
                        while ((cc=reader.read())!=-1){
                            buffer.append((char) cc);
                            }
                        reader.close();
                        if (back!=null)
                            back.onSuccess(buffer.toString());
                        else
                            System.out.println("Текст загружен\n"+buffer.toString());
                    } catch (IOException ee) {
                        String mes = Utils.createFatalMessage(ee);
                        if (back!=null)
                            back.onError(mes);
                        else
                            System.out.println(mes);
                    }
                }
                else{
                    String mes = httpError(response);
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
    //----------------------------------------------------------------------------------------------------------
    public Pair<String,String> loadFileAsStringSync(Artifact art){
        Call<ResponseBody> call2 = service.downLoad(debugToken,art.getOid());
        try {
            Response<ResponseBody> bbody = call2.execute();
            if (!bbody.isSuccessful()) {
                String mes = httpError(bbody);
                return new Pair<>(mes, null);
               }
            ResponseBody body = bbody.body();
            long fileSize = body.contentLength();
            InputStream in = body.byteStream();
            InputStreamReader reader = new InputStreamReader(in,"UTF8");
            StringBuffer buffer = new StringBuffer();
            int cc;
            while ((cc=reader.read())!=-1){
                buffer.append((char) cc);
                }
            reader.close();
            return new Pair<>(null,buffer.toString());
            } catch (IOException ee) {
                String mes = Utils.createFatalMessage(ee);
                return new Pair<>(mes,null);
                }
            }
     */
    //------------------------------------------------------------------------------------------------------------------
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
    public String saveFile(String title, final String extName, String srcName, String text){
        FileDialog dlg=new FileDialog(this,title,FileDialog.SAVE);
        dlg.setFile(srcName);
        dlg.setFilenameFilter(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith("."+extName);
            }
            });
        dlg.show();
        if (dlg.getDirectory()==null) return "Не выбран каталог";
        String fname = dlg.getFile();
        if (!fname.endsWith("."+extName))
            fname+="."+extName;
        try {
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(dlg.getDirectory() + "/" + fname), "UTF-8");
            out.write(text);
            out.flush();
            out.close();
            } catch (Exception ee){
                return "Ошибка сохранения файла: "+ee.toString();
                }
        return null;
        }
    //------------------------------ Всякий код -------------------------------------------------------------------
    public void sendEvent(int code, long par2){}
    public void sendEventPanel(int code, int par1, long par2, String par3, Object oo){}
    public void sendEventPanel(int code, int par1, long par2, String par3){
        sendEventPanel(code,par1,par2,par3,null);
        }
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
