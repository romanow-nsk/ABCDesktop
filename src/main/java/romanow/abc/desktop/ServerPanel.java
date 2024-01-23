/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop;

import romanow.abc.core.*;
import romanow.abc.core.API.RestAPICommon;
import romanow.abc.core.constants.ConstValue;
import romanow.abc.core.constants.TableItem;
import romanow.abc.core.constants.ValuesBase;
import romanow.abc.core.entity.Entity;
import romanow.abc.core.entity.EntityList;
import romanow.abc.core.entity.artifacts.Artifact;
import romanow.abc.core.entity.base.BugMessage;
import romanow.abc.core.entity.base.StringList;
import romanow.abc.core.entity.baseentityes.JBoolean;
import romanow.abc.core.entity.baseentityes.JEmpty;
import romanow.abc.core.entity.baseentityes.JLong;
import romanow.abc.core.entity.baseentityes.JString;
import romanow.abc.core.ftp.AsyncTaskBack;
import romanow.abc.core.ftp.ClientFileWriter;
import romanow.abc.core.utils.FileNameExt;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Response;
import romanow.abc.core.utils.Pair;
import romanow.abc.dataserver.DBExample;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author romanow
 */
public class ServerPanel extends BasePanel{
    private final static int StateLoopDelay=10;
    private boolean isShown=false;
    private boolean onFront=false;
    private boolean isDown=false;
    private MainBaseFrame client;
    private boolean onBusy=false;
    private Thread stateLoopThread;
    private EntityPanel bugPanel;
    private EntityList<BugMessage> bugList = new EntityList<>();
    private ServerState serverState=new ServerState();
    private EntityList<?> records = new EntityList<>();
    private ArrayList<TableItem> classes;
    private String entityClassName="";
    private ArrayList<ConstValue> operList;
    private StringList folder=new StringList();
    private GUITimer logPollingTimer = new GUITimer();                  // Таймер опроса лога
    private long logPollingLastNum=0;
    private String folderName="";
    public ServerPanel() {
        initComponents();
        EntityNames.removeAll();
        classes = ValuesBase.EntityFactory().classList();
        classes.sort(new Comparator<TableItem>() {
            @Override
            public int compare(TableItem o1, TableItem o2) {
                return o1.clazz.getSimpleName().compareTo(o2.clazz.getSimpleName());
                }
            });
        for(TableItem ss : classes){
            EntityNames.add(ss.clazz.getSimpleName()+" ("+ss.name+")");
            }
        for(int i=0;i<=10;i++)
            Level.add(""+i);
        Mode.add("Актуальные");
        Mode.add("Все");
        Mode.add("Удаленные");
        TestNumber.add("200 Нормальное завершение");
        TestNumber.add("500 NullPointerException");
        TestNumber.add("404 Not Found");
        TestNumber.add("400 Request error - имя параметра");
        TestNumber.add("400 Request error - значение параметра");
        }
    private void setMonitorState(boolean state){
        Monitor.setSelected(state);
        isShown=state;
        if (state)
            stateLoopThread.interrupt();
        }
    private Runnable stateLoop = new Runnable() {
        @Override
        public void run() {
            while (!isDown){
                if (isShown && onFront)
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            refresh();
                        }
                    });
                try {
                    Thread.sleep(1000*StateLoopDelay);
                    } catch (InterruptedException e) {}
            }
        }
    };
    public void initPanel(MainBaseFrame main0){
        super.initPanel(main0);
        DBPort.add("4567");
        DBPort.add("4569");
        DBPort.add("4571");
        DBPort.add("4573");
        DBPort.add("4575");
        DBPort.add("5001");
        String pass = main.loginUser().getAccount().getPassword();
        Password.setText(pass);
        client = main0;
        operList = main.filter(main.constList(),"DBOperation");
        Operation.removeAll();
        for(ConstValue ss:operList)
            Operation.add(ss.title());
        ArtifactTypes.removeAll();
        for(ConstValue ss : ValuesBase.title("ArtifactType"))
            ArtifactTypes.add(ss.title());
        ServerVersion.setText(""+client.getServerIP()+" : "+client.getServerPort());
        //----------------------------------------------------------------------------------------------
        bugPanel = new EntityPanel(20,580,bugList,"BugMessage",main,true,false){
            @Override
            public Response apiFunGetAll() throws IOException {
                return  main.getService().getBugList(main.getDebugToken(), ValuesBase.GetAllModeActual).execute();
            }
            @Override
            public Response apiFunGetById() throws IOException {
                return  main.getService().getBug(main.getDebugToken(),data.get(listBox().getSelectedIndex()).getOid()).execute();
            }
            @Override
            public Response apiFunAdd() throws IOException {
                return null;
            }
            @Override
            public Response apiFunUpdate() throws IOException {
                return null;
            }
            @Override
            public void showRecord() {
                System.out.println("-------------------------------------------------------"+current.toString());
            }
            @Override
            public void updateRecord() {}
            };
        add(bugPanel);
        stateLoopThread = new Thread(stateLoop);
        stateLoopThread.start();
        }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        ImportGZ = new javax.swing.JButton();
        ServerLog = new javax.swing.JButton();
        Reload = new javax.swing.JButton();
        ExportGZ = new javax.swing.JButton();
        TargetDB = new javax.swing.JButton();
        UploadServer = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        LogSize = new javax.swing.JTextField();
        Shutdown = new javax.swing.JButton();
        ClearTable1 = new javax.swing.JButton();
        ClearDB = new javax.swing.JButton();
        EntityNames = new java.awt.Choice();
        Password = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        TCount = new javax.swing.JTextField();
        TMin = new javax.swing.JTextField();
        TMax = new javax.swing.JTextField();
        TMid = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        PID = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        Level = new java.awt.Choice();
        Mode = new java.awt.Choice();
        jLabel11 = new javax.swing.JLabel();
        RecordsRefresh = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        ReleaseNum = new javax.swing.JTextField();
        FTP = new javax.swing.JCheckBox();
        Execute = new javax.swing.JButton();
        CommandLine = new javax.swing.JTextField();
        TestNumber = new java.awt.Choice();
        OperationButton = new javax.swing.JButton();
        TestCall = new javax.swing.JButton();
        Operation = new java.awt.Choice();
        SeccionCount = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        ServerLocked = new javax.swing.JCheckBox();
        RequestCount = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        LockServer = new javax.swing.JButton();
        CashMode = new javax.swing.JCheckBox();
        CashPercent = new javax.swing.JTextField();
        CashCount = new javax.swing.JTextField();
        Monitor = new javax.swing.JCheckBox();
        ExportAtrifacts = new javax.swing.JButton();
        Record = new java.awt.Choice();
        RecordDelete = new javax.swing.JButton();
        RecordEdit = new javax.swing.JButton();
        ServerVersion = new javax.swing.JLabel();
        LogFileReopen = new javax.swing.JButton();
        LogFolder = new javax.swing.JButton();
        ArtifactFolder = new javax.swing.JButton();
        ArtifactTypes = new java.awt.Choice();
        FolderList = new java.awt.Choice();
        DownLoadFile = new javax.swing.JButton();
        ImportArtifact = new javax.swing.JButton();
        XLSX = new javax.swing.JCheckBox();
        BlockSize = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        MB = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        FTPDBImport = new javax.swing.JCheckBox();
        ExportXLS = new javax.swing.JButton();
        ImportXLS = new javax.swing.JButton();
        DBPort = new java.awt.Choice();
        DBImport = new javax.swing.JButton();
        DBExport = new javax.swing.JButton();
        RecordAdd = new javax.swing.JButton();
        LogPolling = new javax.swing.JCheckBox();
        CashPercent1 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        TotalCount = new javax.swing.JTextField();
        RecordRemove = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        ClearDB1 = new javax.swing.JButton();
        ServerIPPort = new javax.swing.JLabel();

        jButton1.setText("jButton1");

        jButton2.setText("jButton2");

        jCheckBox1.setText("jCheckBox1");

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        setLayout(null);

        ImportGZ.setText("Импорт (gzip)");
        ImportGZ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImportGZActionPerformed(evt);
            }
        });
        add(ImportGZ);
        ImportGZ.setBounds(20, 520, 120, 22);

        ServerLog.setText("Лог сервера");
        ServerLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ServerLogActionPerformed(evt);
            }
        });
        add(ServerLog);
        ServerLog.setBounds(20, 110, 120, 22);

        Reload.setText("Рестарт");
        Reload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReloadActionPerformed(evt);
            }
        });
        add(Reload);
        Reload.setBounds(20, 310, 120, 22);

        ExportGZ.setText("Экспорт (gzip)");
        ExportGZ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportGZActionPerformed(evt);
            }
        });
        add(ExportGZ);
        ExportGZ.setBounds(20, 170, 120, 22);

        TargetDB.setText("Тестовая БД");
        TargetDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TargetDBActionPerformed(evt);
            }
        });
        add(TargetDB);
        TargetDB.setBounds(20, 400, 120, 22);

        UploadServer.setText("Обновить сервер");
        UploadServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UploadServerActionPerformed(evt);
            }
        });
        add(UploadServer);
        UploadServer.setBounds(20, 260, 140, 22);

        jLabel1.setText("Память (Мб)");
        add(jLabel1);
        jLabel1.setBounds(90, 290, 80, 16);

        LogSize.setText("200");
        add(LogSize);
        LogSize.setBounds(150, 110, 40, 25);

        Shutdown.setText("Завершение");
        Shutdown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShutdownActionPerformed(evt);
            }
        });
        add(Shutdown);
        Shutdown.setBounds(20, 340, 120, 22);

        ClearTable1.setText("Очистить");
        ClearTable1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearTable1ActionPerformed(evt);
            }
        });
        add(ClearTable1);
        ClearTable1.setBounds(500, 400, 120, 22);

        ClearDB.setText("Удаление файлов");
        ClearDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearDBActionPerformed(evt);
            }
        });
        add(ClearDB);
        ClearDB.setBounds(20, 430, 140, 22);
        add(EntityNames);
        EntityNames.setBounds(220, 400, 260, 30);

        Password.setText("pi31415926");
        add(Password);
        Password.setBounds(20, 230, 120, 25);

        jLabel3.setText("Записей в блоке");
        add(jLabel3);
        jLabel3.setBounds(150, 170, 120, 16);
        add(jSeparator1);
        jSeparator1.setBounds(10, 247, 230, 0);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Задержки (мс)");
        add(jLabel4);
        jLabel4.setBounds(260, 40, 130, 14);

        jLabel5.setText("PID");
        add(jLabel5);
        jLabel5.setBounds(270, 205, 60, 16);

        jLabel6.setText("Мин.");
        add(jLabel6);
        jLabel6.setBounds(270, 75, 70, 16);

        jLabel7.setText("Макс.");
        add(jLabel7);
        jLabel7.setBounds(270, 105, 70, 16);

        jLabel8.setText("Среднее");
        add(jLabel8);
        jLabel8.setBounds(270, 135, 70, 16);

        TCount.setEnabled(false);
        add(TCount);
        TCount.setBounds(330, 160, 70, 25);

        TMin.setEnabled(false);
        add(TMin);
        TMin.setBounds(330, 70, 70, 25);

        TMax.setEnabled(false);
        add(TMax);
        TMax.setBounds(330, 100, 70, 25);

        TMid.setEnabled(false);
        add(TMid);
        TMid.setBounds(330, 130, 70, 25);
        add(jSeparator2);
        jSeparator2.setBounds(270, 190, 140, 3);

        PID.setEnabled(false);
        add(PID);
        PID.setBounds(330, 200, 70, 25);

        jLabel9.setText("Кол-во");
        add(jLabel9);
        jLabel9.setBounds(270, 165, 60, 16);

        jLabel10.setText("Таблица");
        add(jLabel10);
        jLabel10.setBounds(160, 405, 60, 16);

        Level.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                LevelItemStateChanged(evt);
            }
        });
        add(Level);
        Level.setBounds(280, 440, 60, 20);
        add(Mode);
        Mode.setBounds(360, 440, 130, 20);

        jLabel11.setText("Уровень");
        add(jLabel11);
        jLabel11.setBounds(220, 440, 60, 16);

        RecordsRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/refresh.png"))); // NOI18N
        RecordsRefresh.setBorderPainted(false);
        RecordsRefresh.setContentAreaFilled(false);
        RecordsRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RecordsRefreshActionPerformed(evt);
            }
        });
        add(RecordsRefresh);
        RecordsRefresh.setBounds(500, 430, 40, 30);

        jLabel12.setText("Копирование порт-порт");
        add(jLabel12);
        jLabel12.setBounds(420, 180, 190, 16);

        ReleaseNum.setEnabled(false);
        add(ReleaseNum);
        ReleaseNum.setBounds(330, 230, 70, 25);

        FTP.setText("через TCP");
        add(FTP);
        FTP.setBounds(170, 260, 90, 20);

        Execute.setText("Ком.строка");
        Execute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExecuteActionPerformed(evt);
            }
        });
        add(Execute);
        Execute.setBounds(20, 550, 120, 22);

        CommandLine.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CommandLineKeyPressed(evt);
            }
        });
        add(CommandLine);
        CommandLine.setBounds(160, 550, 460, 25);
        add(TestNumber);
        TestNumber.setBounds(330, 490, 290, 20);

        OperationButton.setText("Операция");
        OperationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OperationButtonActionPerformed(evt);
            }
        });
        add(OperationButton);
        OperationButton.setBounds(500, 370, 120, 22);

        TestCall.setText("Тест");
        TestCall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TestCallActionPerformed(evt);
            }
        });
        add(TestCall);
        TestCall.setBounds(630, 490, 60, 22);
        add(Operation);
        Operation.setBounds(220, 370, 260, 20);

        SeccionCount.setEnabled(false);
        add(SeccionCount);
        SeccionCount.setBounds(330, 260, 70, 25);

        jLabel13.setText("Сборка");
        add(jLabel13);
        jLabel13.setBounds(270, 235, 60, 16);

        ServerLocked.setText("Блокировка");
        ServerLocked.setEnabled(false);
        add(ServerLocked);
        ServerLocked.setBounds(150, 340, 90, 20);

        RequestCount.setEnabled(false);
        add(RequestCount);
        RequestCount.setBounds(330, 290, 70, 25);

        jLabel14.setText("Сессий");
        add(jLabel14);
        jLabel14.setBounds(270, 265, 60, 16);

        LockServer.setText("<-");
        LockServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LockServerActionPerformed(evt);
            }
        });
        add(LockServer);
        LockServer.setBounds(270, 340, 50, 22);

        CashMode.setText("Кэширование сервера");
        CashMode.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CashModeItemStateChanged(evt);
            }
        });
        add(CashMode);
        CashMode.setBounds(410, 70, 160, 20);

        CashPercent.setEditable(false);
        CashPercent.setEnabled(false);
        add(CashPercent);
        CashPercent.setBounds(480, 130, 60, 25);

        CashCount.setEditable(false);
        CashCount.setEnabled(false);
        add(CashCount);
        CashCount.setBounds(410, 100, 60, 25);

        Monitor.setText("Мониторинг состояния");
        Monitor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MonitorItemStateChanged(evt);
            }
        });
        add(Monitor);
        Monitor.setBounds(20, 40, 190, 20);

        ExportAtrifacts.setText("Экспорт файлов");
        ExportAtrifacts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportAtrifactsActionPerformed(evt);
            }
        });
        add(ExportAtrifacts);
        ExportAtrifacts.setBounds(20, 200, 120, 22);

        Record.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RecordItemStateChanged(evt);
            }
        });
        add(Record);
        Record.setBounds(160, 465, 460, 20);

        RecordDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/remove.png"))); // NOI18N
        RecordDelete.setBorderPainted(false);
        RecordDelete.setContentAreaFilled(false);
        RecordDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RecordDeleteActionPerformed(evt);
            }
        });
        add(RecordDelete);
        RecordDelete.setBounds(660, 455, 30, 30);

        RecordEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/no_problem.png"))); // NOI18N
        RecordEdit.setBorderPainted(false);
        RecordEdit.setContentAreaFilled(false);
        RecordEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RecordEditActionPerformed(evt);
            }
        });
        add(RecordEdit);
        RecordEdit.setBounds(620, 450, 40, 40);

        ServerVersion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ServerVersion.setText("...");
        add(ServerVersion);
        ServerVersion.setBounds(320, 10, 440, 20);

        LogFileReopen.setText("Обновить log");
        LogFileReopen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogFileReopenActionPerformed(evt);
            }
        });
        add(LogFileReopen);
        LogFileReopen.setBounds(540, 260, 110, 22);

        LogFolder.setText("Список логов");
        LogFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogFolderActionPerformed(evt);
            }
        });
        add(LogFolder);
        LogFolder.setBounds(410, 260, 120, 22);

        ArtifactFolder.setText("Список файлов");
        ArtifactFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ArtifactFolderActionPerformed(evt);
            }
        });
        add(ArtifactFolder);
        ArtifactFolder.setBounds(410, 290, 120, 22);
        add(ArtifactTypes);
        ArtifactTypes.setBounds(540, 290, 110, 20);
        add(FolderList);
        FolderList.setBounds(330, 340, 360, 20);

        DownLoadFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/download.png"))); // NOI18N
        DownLoadFile.setBorderPainted(false);
        DownLoadFile.setContentAreaFilled(false);
        DownLoadFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DownLoadFileActionPerformed(evt);
            }
        });
        add(DownLoadFile);
        DownLoadFile.setBounds(650, 280, 40, 30);

        ImportArtifact.setText("Импорт файлов");
        ImportArtifact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImportArtifactActionPerformed(evt);
            }
        });
        add(ImportArtifact);
        ImportArtifact.setBounds(20, 460, 120, 22);

        XLSX.setText("xlsx");
        add(XLSX);
        XLSX.setBounds(150, 140, 43, 20);

        BlockSize.setText("0");
        BlockSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BlockSizeActionPerformed(evt);
            }
        });
        add(BlockSize);
        BlockSize.setBounds(200, 140, 60, 25);

        jLabel15.setText("строк");
        add(jLabel15);
        jLabel15.setBounds(200, 110, 50, 16);

        MB.setText("0");
        add(MB);
        MB.setBounds(170, 290, 60, 25);

        jLabel2.setText("пароль операции ");
        add(jLabel2);
        jLabel2.setBounds(150, 230, 110, 16);

        FTPDBImport.setText("через TCP");
        add(FTPDBImport);
        FTPDBImport.setBounds(160, 490, 90, 20);

        ExportXLS.setText("Экспорт (xls)");
        ExportXLS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportXLSActionPerformed(evt);
            }
        });
        add(ExportXLS);
        ExportXLS.setBounds(20, 140, 120, 22);

        ImportXLS.setText("Импорт (xls)");
        ImportXLS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImportXLSActionPerformed(evt);
            }
        });
        add(ImportXLS);
        ImportXLS.setBounds(20, 490, 120, 22);
        add(DBPort);
        DBPort.setBounds(540, 200, 110, 20);

        DBImport.setText("Импорт  БД");
        DBImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DBImportActionPerformed(evt);
            }
        });
        add(DBImport);
        DBImport.setBounds(410, 200, 120, 22);

        DBExport.setText("Экспорт БД");
        DBExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DBExportActionPerformed(evt);
            }
        });
        add(DBExport);
        DBExport.setBounds(410, 230, 120, 22);

        RecordAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/add.png"))); // NOI18N
        RecordAdd.setBorderPainted(false);
        RecordAdd.setContentAreaFilled(false);
        RecordAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RecordAddActionPerformed(evt);
            }
        });
        add(RecordAdd);
        RecordAdd.setBounds(550, 430, 30, 30);

        LogPolling.setText("Опрос");
        LogPolling.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                LogPollingItemStateChanged(evt);
            }
        });
        add(LogPolling);
        LogPolling.setBounds(150, 80, 90, 20);

        CashPercent1.setText("% кэширования");
        add(CashPercent1);
        CashPercent1.setBounds(480, 110, 90, 16);

        jLabel16.setText("Запросов");
        add(jLabel16);
        jLabel16.setBounds(270, 295, 60, 16);

        TotalCount.setEditable(false);
        TotalCount.setEnabled(false);
        add(TotalCount);
        TotalCount.setBounds(410, 130, 60, 25);

        RecordRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/remove.png"))); // NOI18N
        RecordRemove.setBorderPainted(false);
        RecordRemove.setContentAreaFilled(false);
        RecordRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RecordRemoveActionPerformed(evt);
            }
        });
        add(RecordRemove);
        RecordRemove.setBounds(700, 455, 30, 30);

        jLabel17.setText("soft         hard");
        add(jLabel17);
        jLabel17.setBounds(660, 430, 90, 16);

        ClearDB1.setText("Очистка БД");
        ClearDB1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearDB1ActionPerformed(evt);
            }
        });
        add(ClearDB1);
        ClearDB1.setBounds(20, 370, 120, 22);

        ServerIPPort.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ServerIPPort.setText("...");
        add(ServerIPPort);
        ServerIPPort.setBounds(30, 10, 270, 20);
    }// </editor-fold>//GEN-END:initComponents

    private void ServerLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ServerLogActionPerformed
        final int logSize = Integer.parseInt(LogSize.getText());
        new APICall<StringList>(main){
            @Override
            public Call<StringList> apiFun() {
                return main.getService().getConsoleLog(main.getDebugToken(),logSize);
                }
            @Override
            public void onSucess(StringList oo) {
                String ss = "лог сервера ="+logSize+" ["+oo.size()+"]-------------------------------------------\n";
                System.out.println(ss);
                for(int i=oo.size()-1; i>=0; i--)
                    System.out.println(oo.get(i));
                }
            };
    }//GEN-LAST:event_ServerLogActionPerformed

    private void ReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReloadActionPerformed
        new OK(200, 200, "Рестарт сервера", new I_Button() {
            @Override
            public void onPush() {
                setMonitorState(false);
                restartServer();
            }
        });
    }//GEN-LAST:event_ReloadActionPerformed

    private void ExportGZActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportGZActionPerformed
        new APICall<Artifact>(main){
            @Override
            public Call<Artifact> apiFun() {
                return main.getService().dump(main.getDebugToken());
                }
            @Override
            public void onSucess(Artifact oo) {
                main.loadFileAndDelete(oo);
                }
            };
    }//GEN-LAST:event_ExportGZActionPerformed

    private void restartServer(){
        new APICall<JEmpty>(main){
            @Override
            public Call<JEmpty> apiFun() {
                return main.getService().rebootServer(main.getDebugToken(),Password.getText());
            }
            @Override
            public void onSucess(JEmpty oo) {
                client.logOff();
                }
            };
        }
    private void shutdownServer(){
        new APICall<JString>(main){
            @Override
            public Call<JString> apiFun() {
                return main.getService().shutdown(main.getDebugToken(),Password.getText());
                }
            @Override
            public void onSucess(JString oo) {
                client.logOff();
                }
            };
        }
    private void ImportGZActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImportGZActionPerformed
        FileNameExt fname = main.getInputFileName("Импорт БД","mongo*.gz",null);
        if (FTPDBImport.isSelected())
            importDBFTP(fname,true);
        else
            importDBHttp(fname,true);
            }
    private void importDBFTP(FileNameExt fname, final boolean gzip){
        File ff = new File(fname.fullName());
        new APICall<Artifact>(main){
            @Override
            public Call<Artifact> apiFun() {
                return main.getService().createArtifact(main.getDebugToken(),"DB-import",fname.fileName(),ff.length());
                }
            @Override
            public void onSucess(final Artifact oo) {
                String path = oo.createArtifactServerPath();
                new ClientFileWriter(fname.fullName(), path, client.getServerIP(), Integer.parseInt(client.getServerPort()), Password.getText(), new AsyncTaskBack() {
                    @Override
                    public void runInGUI(Runnable run) {
                        java.awt.EventQueue.invokeLater(run); }
                    @Override
                    public void onError(String mes) {
                        System.out.println(mes); }
                    @Override
                    public void onMessage(String mes) {
                        System.out.println(mes); }
                    @Override
                    public void onFinish(boolean result) {
                        System.out.println("Файл " +oo.getOriginalName() + " выгружен");
                        new APICall<JString>(main){
                            @Override
                            public Call<JString> apiFun() {
                                if (gzip)
                                    return main.getService().restore(main.getDebugToken(),Password.getText(),oo.getOid());
                                else
                                    return main.getService().importDBxls(main.getDebugToken(),Password.getText(),oo.getOid());
                                }
                            @Override
                            public void onSucess(JString ss) {
                                setMonitorState(false);
                                System.out.print(ss);
                                System.out.println("Рестарт сервера после импорта");
                                restartServer();
                            }
                        };
                    }
                });
            }
        };
    }


    private void importDBHttp(FileNameExt fname, final boolean gzip){
        final MultipartBody.Part body = RestAPICommon.createMultipartBody(fname);
        new APICall<Artifact>(main){
            @Override
            public Call<Artifact> apiFun() {
                return main.getService().upload(main.getDebugToken(),"DB-import",fname.fileName(),body);
                }
            @Override
            public void onSucess(final Artifact oo) {
                new APICall<JString>(main){
                    @Override
                    public Call<JString> apiFun() {
                        if (gzip)
                            return main.getService().restore(main.getDebugToken(),Password.getText(),oo.getOid());
                        else
                            return main.getService().importDBxls(main.getDebugToken(),Password.getText(),oo.getOid());
                        }
                    @Override
                    public void onSucess(JString oo) {
                        setMonitorState(false);
                        System.out.print(oo);
                        System.out.println("Рестарт сервера после импорта");
                        restartServer();
                        }
                    };
                }
            };
    }//GEN-LAST:event_ImportGZActionPerformed

    private void ShutdownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ShutdownActionPerformed
        new OK(200, 200, "Завершение работы сервера", new I_Button() {
            @Override
            public void onPush() {
                setMonitorState(false);
                shutdownServer();
            }
        });
    }//GEN-LAST:event_ShutdownActionPerformed

    private void ClearTable1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearTable1ActionPerformed
        String name = classes.get(EntityNames.getSelectedIndex()).name;
        new OK(200, 200, "Очистить  таблицу "+name, new I_Button() {
            @Override
            public void onPush() {
                new APICall<JString>(main){
                    @Override
                    public Call<JString> apiFun() {
                        return main.getService().clearTable(main.getDebugToken(),name,Password.getText());
                    }
                    @Override
                    public void onSucess(JString oo) {
                        System.out.println(oo.getValue());
                    }
                };
            }
        });
    }//GEN-LAST:event_ClearTable1ActionPerformed

    private void uploadMKByAPI(){
        FileNameExt fname = main.getInputFileName("Выгрузить МК", ValuesBase.env().applicationName(ValuesBase.AppNameAPK),null);
        final MultipartBody.Part body2 = RestAPICommon.createMultipartBody(fname);
        new APICall<JEmpty>(main){
            @Override
            public Call<JEmpty> apiFun() {
                return main.getService().uploadByName(main.getDebugToken(),ValuesBase.env().applicationName(ValuesBase.AppNameAPK),body2,false);
            }
            @Override
            public void onSucess(JEmpty oo) {
                System.out.println("Файл выгружен");
            }
        };
        }

    private void deploy(){
        new APICall<JString>(main){
            @Override
            public Call<JString> apiFun() {
                return main.getService().deployMB(main.getDebugToken(),Password.getText(),Integer.parseInt(MB.getText()));
            }
            @Override
            public void onSucess(JString oo) {
                System.out.println(oo.getValue());
                main.delayInGUI(3,new Runnable(){
                    @Override
                    public void run() {
                        client.logOff();
                        }
                    });
                }
            };
        }

    private void uploadMKByFTP(){
        FileNameExt fname = main.getInputFileName("Выгрузить МК",ValuesBase.env().applicationName(ValuesBase.AppNameAPK),null);
        new ClientFileWriter(fname.fullName(), ValuesBase.env().applicationName(ValuesBase.AppNameAPK), client.getServerIP(), Integer.parseInt(client.getServerPort()), Password.getText(), new AsyncTaskBack() {
            @Override
            public void runInGUI(Runnable run) {
                java.awt.EventQueue.invokeLater(run);
            }
            @Override
            public void onError(String mes) {
                System.out.println(mes);
            }
            @Override
            public void onMessage(String mes) {
                System.out.println(mes);
            }
            @Override
            public void onFinish(boolean result) {
                System.out.println("Файл " +ValuesBase.env().applicationName(ValuesBase.AppNameAPK)+ " выгружен");
                }
            });
        }

    private void uploadServerByAPI(){
        FileNameExt fname = main.getInputFileName("Выгрузить сервер",ValuesBase.env().applicationName(ValuesBase.AppNameServerJar),null);
        final MultipartBody.Part body2 = RestAPICommon.createMultipartBody(fname);
        new APICall<JEmpty>(main){
            @Override
            public Call<JEmpty> apiFun() {
                return main.getService().uploadByName(main.getDebugToken(),ValuesBase.env().applicationName(ValuesBase.AppNameServerJar),body2,false);
            }
            @Override
            public void onSucess(JEmpty oo) {
                System.out.println("Файл " +ValuesBase.env().applicationName(ValuesBase.AppNameServerJar)+ " выгружен");
                deploy();
                }
            };
        }

    private void uploadServerByFTP(){
        FileNameExt fname = main.getInputFileName("Выгрузить сервер",ValuesBase.env().applicationName(ValuesBase.AppNameServerJar),null);
        new ClientFileWriter(fname.fullName(), ValuesBase.env().applicationName(ValuesBase.AppNameServerJar), client.getServerIP(), Integer.parseInt(client.getServerPort()), Password.getText(), new AsyncTaskBack() {
            @Override
            public void runInGUI(Runnable run) {
                java.awt.EventQueue.invokeLater(run);
                }
            @Override
            public void onError(String mes) {
                System.out.println(mes);
                }
            @Override
            public void onMessage(String mes) {
                System.out.println(mes);
                }
            @Override
            public void onFinish(boolean result) {
                System.out.println("Файл " +ValuesBase.env().applicationName(ValuesBase.AppNameServerJar)+ " выгружен");
                deploy();
                }
            });
        }

    private void UploadServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UploadServerActionPerformed
        new OK(200, 200, "Выгрузить сервер", new I_Button() {
            @Override
            public void onPush() {
                setMonitorState(false);
                if (FTP.isSelected())
                    uploadServerByFTP();
                else
                    uploadServerByAPI();

            }
        });
    }//GEN-LAST:event_UploadServerActionPerformed

    public void refreshEntityList(){
        entityClassName = classes.get(EntityNames.getSelectedIndex()).clazz.getSimpleName();
        records = main.getList(entityClassName,Mode.getSelectedIndex(),0);
        Record.removeAll();
        records.sortById();
        Record.add("...");
        for(Entity ent : records){
            String title="";
            try {
                title = ent.getTitle();
                } catch (Exception ee){ }
            Record.add("[" + ent.getOid() + "] " + title);
            }
        Record.select(0);
        }
    
    private void RecordsRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RecordsRefreshActionPerformed
        refreshEntityList();
    }//GEN-LAST:event_RecordsRefreshActionPerformed

    private void ClearDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearDBActionPerformed
        new OK(200, 200, "Удалить файлы", new I_Button() {
            @Override
            public void onPush() {
                new APICall<JString>(main){
                    @Override
                    public Call<JString> apiFun() {
                        return main.getService().clearFiles(main.getDebugToken(),Password.getText());
                        }
                    @Override
                    public void onSucess(JString oo) {
                        System.out.println(oo.getValue());
                        }
                    };
                }
            });
    }//GEN-LAST:event_ClearDBActionPerformed

    private void TargetDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TargetDBActionPerformed
        new OK(200, 200, "Тестовая БД", new I_Button() {
            @Override
            public void onPush() {
                new DBExample().createAll(main.getService(),Password.getText());
                client.logOff();
                }
            });
    }//GEN-LAST:event_TargetDBActionPerformed
    private void execute(){
        new OK(200, 200, "Командная строка", new I_Button() {
            @Override
            public void onPush() {
                new APICall<JString>(main){
                    @Override
                    public Call<JString> apiFun() {
                        return main.getService().execute(main.getDebugToken(),Password.getText(),CommandLine.getText());
                        }
                    @Override
                    public void onSucess(JString oo) {
                        System.out.println(oo.getValue());
                    }
                };
            }
        });
    }
    private void ExecuteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExecuteActionPerformed
        execute();
    }//GEN-LAST:event_ExecuteActionPerformed

    private void CommandLineKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CommandLineKeyPressed
        if(evt.getKeyCode()!=10) return;
        execute();
    }//GEN-LAST:event_CommandLineKeyPressed

    private void OperationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OperationButtonActionPerformed
        new OK(200, 200, "Операция над БД: "+Operation.getSelectedItem(), new I_Button() {
            @Override
            public void onPush() {
                new APICall<ErrorList>(main){
                    @Override
                    public Call<ErrorList> apiFun() {
                        int value = operList.get(Operation.getSelectedIndex()).value();
                        return main.getService().prepareDB(main.getDebugToken(),value,Password.getText());
                        }
                    @Override
                    public void onSucess(ErrorList oo) {
                        System.out.println(oo.toString());
                        if (!oo.valid())
                            return;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                int count = ValuesBase.BackgroundOperationMaxDelay/ValuesBase.HTTPTimeOut*2;
                                longPolling(count);
                                }
                            }).start();
                        }
                    };
                }
            });
    }//GEN-LAST:event_OperationButtonActionPerformed

    private void TestCallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TestCallActionPerformed
                new APICall<JString>(main){
                    @Override
                    public Call<JString> apiFun() {
                        return main.getService().testCall(main.getDebugToken(),TestNumber.getSelectedIndex(),"abcd");
                        }
                    @Override
                    public void onSucess(JString oo) {
                        System.out.println(oo.getValue());
                        }
                    };

    }//GEN-LAST:event_TestCallActionPerformed


    private void longPolling(final int count){
        new APICall<ErrorList>(main){
            @Override
            public Call<ErrorList> apiFun() {
                return main.getService().longPolling(main.getDebugToken(),Password.getText());
                }
            @Override
            public void onSucess(ErrorList oo) {
                if (count<=0 && oo.isEmpty()){
                    System.out.println("Операция не завершилась, продолжается в фоне");
                    return;
                    }
                if (!oo.isEmpty())
                    System.out.println(oo);
                else{
                    System.out.println("Операция продолжается, ждите");
                    longPolling(count-1);
                    }
            }
        };
    }
    private void LockServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LockServerActionPerformed
        boolean locked = serverState.isLocked();
        new OK(200, 200, (locked ? "Деб" : "Б")+"локировать сервер", new I_Button() {
            @Override
            public void onPush() {
                new APICall<JEmpty>(main){
                    @Override
                    public Call<JEmpty> apiFun() {
                        return main.getService().lock(main.getDebugToken(),Password.getText(),!locked);
                    }
                    @Override
                    public void onSucess(JEmpty oo) {
                        refresh();
                    }
                };
            }
        });
    }//GEN-LAST:event_LockServerActionPerformed

    private void CashModeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CashModeItemStateChanged
        if (onBusy) return;
        new APICall<JEmpty>(main){
            @Override
            public Call<JEmpty> apiFun() {
                return main.getService().setCashMode(main.getDebugToken(),CashMode.isSelected(),Password.getText());
                }
            @Override
            public void onSucess(JEmpty oo) {}
        };
    }//GEN-LAST:event_CashModeItemStateChanged

    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
        onFront=false;
    }//GEN-LAST:event_formComponentHidden

    private void formComponentShown(java.awt.event.ComponentEvent evt) {                                    
        onFront=true;
    }                                  

    private void MonitorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MonitorItemStateChanged
        setMonitorState(Monitor.isSelected());
    }//GEN-LAST:event_MonitorItemStateChanged

    private EntityList<Artifact> loadList = new EntityList<>();
    private String loadDirectory="";
    private int cntTotal=0;
    private int cntLost=0;
    private int cntError=0;
    private int cntSuccess=0;
    private int cntPresent=0;
    private int loadIdx=0;
    private void loadOne(){
        while(true){
            if (loadIdx==cntTotal){
                System.out.println("Всего "+cntTotal+"\nНет на сервере "+cntLost+"\nОшибка загрузки "+cntError+"\nЗагружен ранее "+cntPresent+"\nЗагружено "+cntSuccess);
                return;
                }
            Artifact art = loadList.get(loadIdx);
            String fname = art.createArtifactServerPath();
            if (art.isFileLost()){
                System.out.println("Файл на сервере отсутствует "+ fname);
                cntLost++;
                loadIdx++;
                continue;
                }
            String path = loadDirectory+"/"+art.type()+"_"+art.directoryName();
            String fullName = loadDirectory+"/"+fname;
            File dir = new File(path);
            if (!dir.exists()){
                if (!dir.mkdir()){
                    System.out.println("Не удается создать каталог "+ path);
                    cntError++;
                    loadIdx++;
                    continue;
                    }
                }
            if (!dir.isDirectory()){
                System.out.println("Это не  каталог "+ path);
                cntError++;
                loadIdx++;
                continue;
                }
            File file = new File(fullName);
            if (file.exists()){
                if (file.length()==art.getFileSize()){
                    //System.out.println("Файл уже скачан "+ fname);
                    cntPresent++;
                    loadIdx++;
                    continue;
                    }
                else
                    System.out.println("Не совпадает размер, загрузка "+ fname+" "+file.length()+"/"+art.getFileSize());
                }
            main.loadFile(art, fullName, new I_DownLoad() {
                @Override
                public void onSuccess() {
                    System.out.println("Файл загружен "+ fname);
                    cntSuccess++;
                    loadIdx++;
                    loadOne();
                    }
                @Override
                public void onError(String mes) {
                    System.out.println("Ошибка загрузки "+ fname+"\n"+mes);
                    cntError++;
                    loadIdx++;
                    loadOne();
                    }
                });
            return;
            }
        }
    //--------------------------------------------------------------------------------------------
    private boolean uploadByName(String clientName, String ext, String serverName){
        try {
            MultipartBody.Part body2 = RestAPICommon.createMultipartBody(clientName,ext);
            Response<JEmpty> call3 = main.getService().uploadByName(main.getDebugToken(),serverName,body2,false).execute();
            if (!call3.isSuccessful()){
                 System.out.println("Ошибка выгрузки файла  "+ Utils.httpError(call3));
                 return false;
                 }
            System.out.println("Файл выгружен: "+serverName);
            return true;
            } catch (Exception e) {
                System.out.println("Ошибка сервера: "+e.toString());
                return false;
                }
        }
    private void updateArtifactField(String fld, DBRequest request){
        new APICall<JEmpty>(main){
            @Override
            public Call<JEmpty> apiFun() {
                return main.getService().updateEntityField(main.getDebugToken(),fld,request);
                }
            @Override
            public void onSucess(JEmpty oo) {}
            };
        }
    private void saveOne(){
        while(true){
            if (loadIdx==cntTotal){
                System.out.println("Всего "+cntTotal+"\nНет на клиенте "+cntLost+"\nОшибка выгрузки "+cntError+"\nЕсть на сервере "+cntPresent+"\nВыгружено "+cntSuccess);
                return;
                }
            Artifact art = loadList.get(loadIdx);
            String fname = art.createArtifactServerPath();
            if (!art.isFileLost()){
                cntPresent++;
                loadIdx++;
                continue;
                }
            String path = loadDirectory+"/"+art.type()+"_"+art.directoryName();
            String fullName = loadDirectory+fname;
            fullName = fullName.replace("\\","/");
            File dir = new File(path);
            if (!dir.exists()){
                cntLost++;
                loadIdx++;
                continue;
                }
            if (!dir.isDirectory()) {
                cntLost++;
                loadIdx++;
                continue;
                }
            File file = new File(fullName);
            if (!file.exists()){
                System.out.println("Нет на клиенте "+ fname+" "+file.length()+"/"+art.getFileSize());
                loadIdx++;
                cntLost++;
                continue;
                }
            if (uploadByName(fullName,art.getOriginalExt(),fname)){
                cntSuccess++;
                art.setFileLost(false);
                art.setFileSize(file.length());
                DBRequest request = new DBRequest(art,main.gson);
                updateArtifactField("fileLost",request);
                updateArtifactField("fileSize",request);
                }
            else
                cntError++;
            loadIdx++;
            }
    }
    private void ExportAtrifactsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportAtrifactsActionPerformed
        new OK(200, 200, "Экспорт артефактов", new I_Button() {
            @Override
            public void onPush() {
                FileNameExt ff = main.getOutputFileName("Каталог экпорта","aaa","aaa");
                loadDirectory = ff.getPath();
                new APICall<ArrayList<DBRequest>>(main){
                    @Override
                    public Call<ArrayList<DBRequest>> apiFun() {
                        return main.getService().getEntityList(main.getDebugToken(),"Artifact",ValuesBase.GetAllModeActual,0);
                        }
                    @Override
                    public void onSucess(ArrayList<DBRequest> oo) {
                        try {
                            loadList.load(oo);
                            cntTotal = loadList.size();
                            cntError = cntSuccess = cntLost = cntPresent = 0;
                            loadIdx=0;
                            loadOne();
                            } catch (UniException e) {  Utils.printFatalMessage(e); }
                        }
                    };
            }
        });
    }//GEN-LAST:event_ExportAtrifactsActionPerformed

    private void RecordDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RecordDeleteActionPerformed
        if (records.size()==0)
            return;
        final Entity entity = records.get(Record.getSelectedIndex()-1);
        new OK(200, 200, "Удалить: [" + entity.getOid() + "] " + entity.getTitle(), new I_Button() {
            @Override
            public void onPush() {
                new APICall<JBoolean>(main){
                    @Override
                    public Call<JBoolean> apiFun() {
                        return main.getService().deleteById(main.getDebugToken(),entityClassName,entity.getOid());
                        }
                    @Override
                    public void onSucess(JBoolean oo) {
                        refreshEntityList();
                    }
                };
            }
        });
    }//GEN-LAST:event_RecordDeleteActionPerformed

    private void RecordEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RecordEditActionPerformed
        if (records.size()==0)
            return;
        int idx = Record.getSelectedIndex();
        if (idx==0)
            return;
        final Entity entity = records.get(idx-1);
        new EntityEditPanel(main,entity);
    }//GEN-LAST:event_RecordEditActionPerformed

    private void LogFileReopenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogFileReopenActionPerformed
        new OK(200, 200, "Создать новый log-файл", new I_Button() {
            @Override
            public void onPush() {
                new APICall<JEmpty>(main){
                    @Override
                    public Call<JEmpty> apiFun() {
                        return main.getService().reopenLogFile(main.getDebugToken(),Password.getText());
                    }
                    @Override
                    public void onSucess(JEmpty dd) {
                    }
                };
            }
        });
    }//GEN-LAST:event_LogFileReopenActionPerformed


    private void loadFileList(final String dir){
        new APICall<StringList>(main){
            @Override
            public Call<StringList> apiFun() {
                return main.getService().getFolder(main.getDebugToken(),Password.getText(),dir);
            }
            @Override
            public void onSucess(StringList oo) {
                folder = oo;
                folderName = dir;
                FolderList.removeAll();
                folder.sort();
                System.out.println(dir+": файлов: "+oo.size());
                for(String ss : oo){
                    FolderList.add(ss);
                    //System.out.println(ss);
                    }
            }
        };
        }

    private void LogFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogFolderActionPerformed
        loadFileList("log");
    }//GEN-LAST:event_LogFolderActionPerformed

    private void ArtifactFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ArtifactFolderActionPerformed
        if (ArtifactTypes.getSelectedIndex()==0)
            return;
        int idx = ArtifactTypes.getSelectedIndex();
        String zz = ""+idx+"_"+ValuesBase.ArtifactDirNames[idx];
        loadFileList(zz);
    }//GEN-LAST:event_ArtifactFolderActionPerformed

    private void DownLoadFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DownLoadFileActionPerformed
        if (folder.size()==0)
            return;
        String fname = folder.get(FolderList.getSelectedIndex());
        main.loadFile(folderName,fname);
    }//GEN-LAST:event_DownLoadFileActionPerformed

    private void ImportArtifactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImportArtifactActionPerformed
        new OK(200, 200, "Импорт артефактов", new I_Button() {
            @Override
            public void onPush() {
                FileNameExt ff = main.getOutputFileName("Каталог импорта","aaa","aaa");
                loadDirectory = ff.getPath();
                new APICall<ArrayList<DBRequest>>(main){
                    @Override
                    public Call<ArrayList<DBRequest>> apiFun() {
                        return main.getService().getEntityList(main.getDebugToken(),"Artifact",ValuesBase.GetAllModeActual,0);
                    }
                    @Override
                    public void onSucess(ArrayList<DBRequest> oo) {
                        try {
                            loadList.load(oo);
                            cntTotal = loadList.size();
                            cntError = cntSuccess = cntLost = cntPresent = 0;
                            loadIdx=0;
                            saveOne();
                        } catch (UniException e) {  Utils.printFatalMessage(e); }
                    }
                };
            }
        });
    }//GEN-LAST:event_ImportArtifactActionPerformed

    private void BlockSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BlockSizeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BlockSizeActionPerformed

    private void ExportXLSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportXLSActionPerformed
        new APICall<Artifact>(main){
            @Override
            public Call<Artifact> apiFun() {
                return main.getService().exportDBxlsx(main.getDebugToken(),XLSX.isSelected(),Integer.parseInt(BlockSize.getText()));
            }
            @Override
            public void onSucess(Artifact oo) {
                main.loadFileAndDelete(oo);
            }
        };
    }//GEN-LAST:event_ExportXLSActionPerformed

    private void ImportXLSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImportXLSActionPerformed
        FileNameExt fname = main.getInputFileName("Импорт БД","mongo*.xls*",null);
        if (FTPDBImport.isSelected())
            importDBFTP(fname,false);
        else
            importDBHttp(fname,false);
    }//GEN-LAST:event_ImportXLSActionPerformed

    private void DBImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DBImportActionPerformed
        int port = Integer.parseInt(DBPort.getSelectedItem());
        new OK(200, 200, "Импорт БД в "+main.getServerPort()+" из "+port, new I_Button() {
            @Override
            public void onPush() {
                new APICall<ErrorList>(main) {
                    @Override
                    public Call<ErrorList> apiFun() {
                        return main.getService().copyDBFrom(main.getDebugToken(), Password.getText(),port);
                    }
                    @Override
                    public void onSucess(ErrorList oo) {
                        System.out.println(oo);
                        if (!oo.valid())
                            return;
                        main.delayInGUI(3,new Runnable(){
                            @Override
                            public void run() {
                                client.logOff();
                            }
                        });
                    }

                };
            }
        });

    }//GEN-LAST:event_DBImportActionPerformed

    private void DBExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DBExportActionPerformed
        int port = Integer.parseInt(DBPort.getSelectedItem());
        new OK(200, 200, "Экспорт БД из "+main.getServerPort()+" в "+port, new I_Button() {
            @Override
            public void onPush() {
                new APICall<ErrorList>(main) {
                    @Override
                    public Call<ErrorList> apiFun() {
                        return main.getService().copyDBTo(main.getDebugToken(), Password.getText(),port);
                      }
                    @Override
                    public void onSucess(ErrorList oo) {
                        System.out.println(oo);
                    }

                };
            }
        });
    }//GEN-LAST:event_DBExportActionPerformed

    private void RecordAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RecordAddActionPerformed
        Class cls = classes.get(EntityNames.getSelectedIndex()).clazz;
        Entity entity=null;
        try {
            entity = (Entity) cls.newInstance();
            } catch (Exception ee) {
                System.out.println("Ошибка создания объекта: "+cls.getSimpleName()+"\n"+ee.toString());
                return;
                }
        final Entity entity2=entity;
        new OK(200, 200, "Добавить объект "+cls.getSimpleName(), new I_Button() {
            @Override
            public void onPush() {
                new APICall<JLong>(main) {
                    @Override
                    public Call<JLong> apiFun() {
                        return main.getService().addEntity(main.getDebugToken(),new DBRequest(entity2,main.gson),0);
                        }
                    @Override
                    public void onSucess(JLong oo) {
                        System.out.println("Доюавлен объект oid= "+oo.getValue());
                        refreshEntityList();
                        }
                    };
                }
            });
    }//GEN-LAST:event_RecordAddActionPerformed

    private I_EmptyEvent onLogPolling = new I_EmptyEvent(){
        @Override
        public void onEvent() {
            logPolling();
            if (LogPolling.isSelected())
                logPollingTimer.start(5,onLogPolling);
            }
        };

    private void logPolling(){
        new APICall<Pair<Long,StringList>>(main) {
            @Override
            public Call<Pair<Long,StringList>> apiFun() {
                return main.getService().getConsoleLogPolling(main.getDebugToken(),logPollingLastNum);
            }
            @Override
            public void onSucess(Pair<Long,StringList> oo) {
                logPollingLastNum = oo.o1;
                for(String ss : oo.o2)
                    System.out.println(ss);
                }
            };
        }

    private void LogPollingItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_LogPollingItemStateChanged
        if (LogPolling.isSelected()){
            logPolling();
            logPollingTimer.start(5, onLogPolling);
            }
        else{
            logPollingTimer.cancel();
            }
    }//GEN-LAST:event_LogPollingItemStateChanged

    private void showRecord(){
        if (records.size()<=1)
            return;
        int idx = Record.getSelectedIndex();
        if (idx==0)
            return;
        long oid =  records.get(idx-1).getOid();
        new APICall<DBRequest>(main){
            @Override
            public Call<DBRequest> apiFun() {
                return main.getService().getEntity(main.getDebugToken(),entityClassName,oid,Level.getSelectedIndex());
            }
            @Override
            public void onSucess(DBRequest oo) {
                try {
                    Entity entity = (Entity) oo.get(main.gson);
                    System.out.println("---------------------------------------------\nОбъект уровня "
                            +Level.getSelectedIndex()+":\n"+entity.getTitle()+"\n"+entity.toString());
                } catch (Exception ee){
                    System.out.println("Ошибка десериализации объекта класса "+entityClassName+"["+oid+"]: "+ee.toString());
                    }
                }
            };
        }

    private void RecordItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RecordItemStateChanged
        showRecord();
    }//GEN-LAST:event_RecordItemStateChanged

    private void LevelItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_LevelItemStateChanged
        showRecord();
    }//GEN-LAST:event_LevelItemStateChanged

    private void RecordRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RecordRemoveActionPerformed
        if (records.size()==0)
            return;
        final Entity entity = records.get(Record.getSelectedIndex()-1);
        new OK(200, 200, "Удалить: [" + entity.getOid() + "] " + entity.getTitle(), new I_Button() {
            @Override
            public void onPush() {
                new APICall<JBoolean>(main){
                    @Override
                    public Call<JBoolean> apiFun() {
                        return main.getService().removeEntity(main.getDebugToken(),entityClassName,entity.getOid());
                        }
                    @Override
                    public void onSucess(JBoolean oo) {
                        refreshEntityList();
                    }
                };
            }
        });
    }//GEN-LAST:event_RecordRemoveActionPerformed

    private void ClearDB1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearDB1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ClearDB1ActionPerformed

    private void showState(){
        onBusy=true;
        TMid.setText(""+serverState.getTimeMiddle());
        TMin.setText(""+serverState.getTimeMin());
        TMax.setText(""+serverState.getTimeMax());
        TCount.setText(""+serverState.getTimeCount());
        PID.setText(""+serverState.getPid());
        ServerVersion.setText(serverState.getVersion());
        ServerIPPort.setText(""+client.getServerIP()+" : "+client.getServerPort());
        ReleaseNum.setText(""+serverState.getReleaseNumber());
        SeccionCount.setText(""+serverState.getSessionCount());
        ServerLocked.setSelected(serverState.isLocked());
        RequestCount.setText(""+serverState.getRequestNum());
        CashMode.setSelected(serverState.isСashEnabled());
        if (!serverState.isСashEnabled() || serverState.getTotalGetCount()==0) {
            CashPercent.setText("");
            CashCount.setText("");
            CashPercent.setText("");
            }
        else {
            CashPercent.setText(""+serverState.getTotalGetCount());
            CashCount.setText(""+serverState.getCashGetCount());
            CashPercent.setText("" + serverState.getCashGetCount() * 100 / serverState.getTotalGetCount()+" %");
            }
        onBusy=false;
        }
    @Override
    public void refresh() {
        new APICall<ServerState>(main){
            @Override
            public Call<ServerState> apiFun() {
                return main.getService().serverState(main.getDebugToken());
                }
            @Override
            public void onSucess(ServerState oo) {
                serverState = oo;
                showState();
                }
            };
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
        isDown=true;
        stateLoopThread.interrupt();
        logPollingTimer.cancel();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ArtifactFolder;
    private java.awt.Choice ArtifactTypes;
    private javax.swing.JTextField BlockSize;
    private javax.swing.JTextField CashCount;
    private javax.swing.JCheckBox CashMode;
    private javax.swing.JTextField CashPercent;
    private javax.swing.JLabel CashPercent1;
    private javax.swing.JButton ClearDB;
    private javax.swing.JButton ClearDB1;
    private javax.swing.JButton ClearTable1;
    private javax.swing.JTextField CommandLine;
    private javax.swing.JButton DBExport;
    private javax.swing.JButton DBImport;
    private java.awt.Choice DBPort;
    private javax.swing.JButton DownLoadFile;
    private java.awt.Choice EntityNames;
    private javax.swing.JButton Execute;
    private javax.swing.JButton ExportAtrifacts;
    private javax.swing.JButton ExportGZ;
    private javax.swing.JButton ExportXLS;
    private javax.swing.JCheckBox FTP;
    private javax.swing.JCheckBox FTPDBImport;
    private java.awt.Choice FolderList;
    private javax.swing.JButton ImportArtifact;
    private javax.swing.JButton ImportGZ;
    private javax.swing.JButton ImportXLS;
    private java.awt.Choice Level;
    private javax.swing.JButton LockServer;
    private javax.swing.JButton LogFileReopen;
    private javax.swing.JButton LogFolder;
    private javax.swing.JCheckBox LogPolling;
    private javax.swing.JTextField LogSize;
    private javax.swing.JTextField MB;
    private java.awt.Choice Mode;
    private javax.swing.JCheckBox Monitor;
    private java.awt.Choice Operation;
    private javax.swing.JButton OperationButton;
    private javax.swing.JTextField PID;
    private javax.swing.JPasswordField Password;
    private java.awt.Choice Record;
    private javax.swing.JButton RecordAdd;
    private javax.swing.JButton RecordDelete;
    private javax.swing.JButton RecordEdit;
    private javax.swing.JButton RecordRemove;
    private javax.swing.JButton RecordsRefresh;
    private javax.swing.JTextField ReleaseNum;
    private javax.swing.JButton Reload;
    private javax.swing.JTextField RequestCount;
    private javax.swing.JTextField SeccionCount;
    private javax.swing.JLabel ServerIPPort;
    private javax.swing.JCheckBox ServerLocked;
    private javax.swing.JButton ServerLog;
    private javax.swing.JLabel ServerVersion;
    private javax.swing.JButton Shutdown;
    private javax.swing.JTextField TCount;
    private javax.swing.JTextField TMax;
    private javax.swing.JTextField TMid;
    private javax.swing.JTextField TMin;
    private javax.swing.JButton TargetDB;
    private javax.swing.JButton TestCall;
    private java.awt.Choice TestNumber;
    private javax.swing.JTextField TotalCount;
    private javax.swing.JButton UploadServer;
    private javax.swing.JCheckBox XLSX;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    // End of variables declaration//GEN-END:variables
}
