package romanow.abc.desktop;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import romanow.abc.core.API.RestAPIBase;
import romanow.abc.core.DBRequest;
import romanow.abc.core.LogStream;
import romanow.abc.core.UniException;
import romanow.abc.core.Utils;
import romanow.abc.core.constants.ConstValue;
import romanow.abc.core.constants.ValuesBase;
import romanow.abc.core.entity.artifacts.Artifact;
import romanow.abc.core.entity.base.WorkSettingsBase;
import romanow.abc.core.entity.baseentityes.JEmpty;
import romanow.abc.core.entity.baseentityes.JString;
import romanow.abc.core.entity.users.User;
import romanow.abc.core.utils.FileNameExt;
import romanow.abc.core.utils.OwnDateTime;
import romanow.abc.core.utils.Pair;
import romanow.abc.core.utils.StringFIFO;

import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static romanow.abc.core.Utils.httpError;

//-------------- Вынесение функциональности клиента из MainBaseFrame
public class ClientContext {
    @Getter @Setter private boolean offline = false;              // АВТОНОМНЫЙ клиент
    @Setter private WorkSettingsBase workSettings = null;
    @Getter @Setter private ArrayList<ConstValue> constList;
    @Getter @Setter private ArrayList<ConstValue> homeTypes;
    @Getter @Setter private ArrayList<ConstValue> officeTypes;
    @Getter @Setter private ArrayList<ConstValue> cityTypes;
    @Getter @Setter private ArrayList<ConstValue> streetTypes;
    @Getter @Setter private ArrayList<ConstValue> userTypes;
    @Getter @Setter private User loginUser = new User();
    @Getter @Setter private boolean localUser = false;
    @Getter @Setter private String debugToken = "";
    @Getter @Setter private boolean refreshMode = false;
    @Getter @Setter private Gson gson = new Gson();
    @Getter @Setter private ArrayList<String> serverEnvironment;
    @Getter @Setter private RestAPIBase service = null;                                   // Тип интерфейса
    //@Getter @Setter private StringFIFO externalFIFO = null;
    //@Getter @Setter private String gblEncoding = "";
    //@Getter @Setter private boolean utf8;
    //@Getter @Setter private MESContext mesContext = null;
    //@Getter @Setter StringBuffer str = new StringBuffer();
    //@Getter @Setter private int lineCount = 0;
    @Getter @Setter private String serverIP = "";
    @Getter @Setter private String serverPort = "";
    //@Getter @Setter private LogStream log;
    public WorkSettingsBase workSettings(){
        return workSettings; }
    //-------------------------------------------------------------------------------------------------------------------
    public URL createURLForArtifact(Artifact art) {
        int timeZoneHour = workSettings().getTimeZoneHours();
        String path = art.createArtifactServerPath(-timeZoneHour);
        String ss = "http://" + serverIP + ":" + serverPort + "/file/" + path;
        URL url = null;
        try {
            url = new URL(ss);
        } catch (MalformedURLException e) {
            System.out.println(e.toString());
            return null;
        }
        return url;
    }

    //------------------------------------------------------------------------------------------------------------------
    public boolean startClient(String ip, String port) {
        serverIP = ip;
        serverPort = port;
        try {
            Pair<RestAPIBase, String> res = startOneClient(ip, port);
            service = res.o1;
            debugToken = res.o2;
            loadConstants();
        } catch (UniException e) {
            System.out.println("Ошибка ключа отладки " + e.toString());
            return false;
        }
        return true;
    }

    //------------------------------------------------------------------------------------------------------------------
    public Pair<RestAPIBase, String> startOneClient(String ip, String port) throws UniException {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(ValuesBase.HTTPTimeOut, TimeUnit.SECONDS)
                .connectTimeout(ValuesBase.HTTPTimeOut, TimeUnit.SECONDS)
                .build();
        System.out.println("API: пул потоков=" + okHttpClient.dispatcher().getMaxRequests());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + ip + ":" + port)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        localUser = ip.equals("localhost") || ip.equals("127.0.0.1");
        RestAPIBase service = (RestAPIBase) retrofit.create(RestAPIBase.class);
        JString ss = new APICall2<JString>() {
            @Override
            public Call<JString> apiFun() {
                return service.debugToken(ValuesBase.DebugTokenPass);
            }
        }.call();
        return new Pair<>(service, ss.getValue());
    }

    public void loadConstants() throws UniException {
        loadConstants(false);
    }

    //------------------------------------------------------------------------------------------------------------------
    public void loadConstants(boolean local) throws UniException {
        if (local)
            constList = ValuesBase.constMap().getConstList();
        else {
            constList = new APICall2<ArrayList<ConstValue>>() {
                @Override
                public Call<ArrayList<ConstValue>> apiFun() {
                    return service.getConstAll(debugToken);
                    }
                }.call();
            }
        homeTypes = filter(constList, "HomeType");
        officeTypes = filter(constList, "OfficeType");
        streetTypes = filter(constList, "StreetType");
        cityTypes = filter(constList, "TownType");
        userTypes = filter(constList, "User");
        }
    public ArrayList<ConstValue> filter(ArrayList<ConstValue> src, String filter) {
        ArrayList<ConstValue> out = new ArrayList<>();
        for (ConstValue cc : src)
            if (cc.groupName().equals(filter))
                out.add(cc);
        return out;
        }
    //------------------------------------------------------------------------------------------------------------------
    public Object startSecondClient(String ip, String port, Class apiClass) throws UniException {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(ValuesBase.HTTPTimeOut, TimeUnit.SECONDS)
                .connectTimeout(ValuesBase.HTTPTimeOut, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + ip + ":" + port)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(apiClass);
    }

    //------------------------------------------------------------------------------------------------------------------
    public void updateArtifactFromString(Artifact art, String text, final I_OK ok) {//GEN-FIRST:event_SaveActionPerformed
        try {
            MultipartBody.Part body2 = MultipartBody.Part.createFormData("file", text);
            Call<Artifact> call3 = service.update(debugToken, art.getOid(), body2);
            call3.enqueue(new Callback<Artifact>() {
                @Override
                public void onResponse(Call<Artifact> call, Response<Artifact> response) {
                    if (!response.isSuccessful()) {
                        System.out.println("Ошибка выгрузки файла  " + Utils.httpError(response));
                    } else {
                        java.awt.EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                ok.onOK(response.body());
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<Artifact> call, Throwable ee) {
                    System.out.println("Ошибка сервера: " + ee.toString());
                }
            });
        } catch (Exception e) {
            System.out.println("Ошибка сервера: " + e.toString());
            }
       }
    //--------------------------------------------------------------------------------------------------------------------
    public String getWorkSettings(){
        try {
            Response<DBRequest> wsr = service.workSettings(debugToken).execute();
            if (!wsr.isSuccessful()){
                return "Ошибка чтения настроек  " + httpError(wsr);
                }
            workSettings = (WorkSettingsBase) wsr.body().get(new Gson());
        } catch (Exception e) {
            return "Ошибка чтения настроек  " + e.toString();
            }
        return null;
    }
    //------------------------------------------------------------------------------------------------------------------
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
    public Pair<String,byte[]> loadFileAsBinSync(long oid){
        Call<ResponseBody> call2 = service.downLoad(debugToken,oid);
        try {
            Response<ResponseBody> bbody = call2.execute();
            if (!bbody.isSuccessful()) {
                String mes = httpError(bbody);
                return new Pair<>(mes, null);
            }
            ResponseBody body = bbody.body();
            long fileSize = body.contentLength();
            InputStream in = body.byteStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int cc=0;
            while ((cc=in.read())!=-1){
                out.write(cc);
                }
            in.close();
            out.flush();
            return new Pair<>(null,out.toByteArray());
        } catch (IOException ee) {
            String mes = Utils.createFatalMessage(ee);
            return new Pair<>(mes,null);
        }
    }
    //----------------------------------------------------------------------------------------------------------
    public Pair<String,String> loadFileAsStringSync(long oid){
        Call<ResponseBody> call2 = service.downLoad(debugToken,oid);
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
    public Pair<String,String> loadFileAsStringSync(Artifact art){
        return loadFileAsStringSync(art.getOid());
        }
}