package romanow.abc.desktop;

import romanow.abc.core.reports.TableData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

public class TableWindow {
    final static int cellSize=25;
    final static int rCnt=15;
    final static int cCnt=20;
    final static int base=15;
    private I_TableBack back=null;
    private ScrollTableView xx=null;
    private JFrame jf=null;
    private TableData data;
    private int headColIdx;
    public TableWindow(TableData data0,int headColIdx0, I_TableBack back0){
        data = data0;
        back = back0;
        headColIdx = headColIdx0;
        new Thread(rr).start();
        }
    //--------------------------------------------------------------------------
        Runnable rr=new Runnable(){
        public void run(){
            jf=new JFrame();
            jf.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            jf.addWindowListener(new WindowListener(){
                public void windowOpened(WindowEvent e) {}
                public void windowClosing(WindowEvent e) {
                        back.onClose();
                    }
                public void windowClosed(WindowEvent e) {}
                public void windowIconified(WindowEvent e) {}
                public void windowDeiconified(WindowEvent e) {}
                public void windowActivated(WindowEvent e) {}
                public void windowDeactivated(WindowEvent e) {}
                });
            jf.getContentPane().setLayout(null);
            jf.pack();
            jf.setTitle(data.getTitle());
                xx=new ScrollTableView(cellSize,rCnt,cCnt,new I_TableBack(){
                    @Override
                    public void rowSelected(int row) {}
                    @Override
                    public void colSelected(int col) {}
                    @Override
                    public void cellSelected(int row, int col) {
                        if (back==null) return;
                        jf.setState(JFrame.ICONIFIED);
                        back.cellSelected(row, col);
                        }
                    @Override
                    public void onClose() {
                        jf.dispose();
                        back.onClose();
                        }
                    });
                Dimension dd=xx.setData(data, headColIdx);
                jf.add(xx);
                jf.setBounds(300,50,dd.width+base+50,dd.height+base+75);
                xx.setBounds(base, base, dd.width,dd.height);
                jf.setVisible(true);
                jf.show();
            }};
        //--------------------------------------------------------------------------
        public void closeTable(){
            if (jf!=null) {
                jf.dispose();
                back.onClose();
                }
            }
        public void refreshTable(){
            xx.repaint();
            jf.setState(JFrame.NORMAL);
            }
        }
