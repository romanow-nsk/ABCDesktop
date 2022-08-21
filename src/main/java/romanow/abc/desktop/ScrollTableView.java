package romanow.abc.desktop;

import romanow.abc.core.reports.TableData;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ScrollTableView  extends JPanel {
    I_TableBack lsn=null;
    private int colorBack=0xE0E0E0;
    private int colorSelect=0xA0A0A0;
    private int hsymSize=7; // Размерность символа по горизонтали
    private int vsymSize=14;// Размерность символа по вертикали
    private int yy,xx;
    private int yt0,xt0;
    private int csize;      // Размерность ячейки
    private int hsize;      // Длина гориз. заголовка
    private int vsize;      // Длина вертик. заголовка
    private int hcnt0,hcnt; // Количество ячеек в гориз. списке.
    private int vcnt0,vcnt; // Количество ячеек в вертик. списке.
    private int i0=0;       // Начальные индексы рисования
    private int j0=0;
    private JPanel HList;
    private JScrollBar HListScroll;
    private JPanel VList;
    private JScrollBar VListScroll;
    private JPanel TableArea;
    private JTextField H[]=null;
    private JTextArea V[]=null;
    private JTextField T[][]=null;
    private Border border=null;
    private int maxRow,maxCol;
    private TableData data=null;
    private int headColIdx;
    //--------------------------------------------------------------------------
    private int cols(){
        return data.cols()-headColIdx-1;
        }
    private void correct(){
        if (i0>data.rows()-hcnt) i0=data.rows()-hcnt;
        if (i0<0) i0=0;
        if (j0>cols()-vcnt) j0=cols()-vcnt;
        if (j0<0) j0=0;
    }
    public void toFront(){
        if (!dataValid()) return;
        i0=0; j0=0; repaint();
    }
    public void toEnd(){
        if (!dataValid()) return;
        i0=data.rows()-hcnt;
        if (i0<0) i0=0;
        j0=cols()-vcnt;
        if (j0<0) j0=0;
        repaint();
    }
    private String toVertical(String ss){
        char cc[]=ss.toCharArray();
        char out[]=new char[ss.length()*3];
        for (int i=0;i<cc.length;i++){
            out[3*i]=' ';
            out[3*i+1]=cc[i];
            out[3*i+2]='\n';
            }
        return new String(out);
    }
    public void repaint(){
        super.repaint();
        if (!dataValid()) return;
        correct();
        for(int i=0;i<hcnt;i++){
            if (i0+i>=data.rows())
                H[i].setText("");
            else
                H[i].setText(" "+data.data().get(i0+i).get(headColIdx).getValue());
            }
        for(int j=0;j<vcnt;j++){
            if (j0+j>=cols())
                V[j].setText("");
            else
                V[j].setText(toVertical(data.columns().get(headColIdx+1+j0+j).getName()));
            }
        for(int i=0;i<hcnt;i++){
            for(int j=0;j<vcnt;j++){
                if (i0+i>=data.rows() || j0+j>=cols())
                    T[i][j].setText("");
                else
                    T[i][j].setText(data.data().get(i0+i).get(headColIdx+1+j0+j).getValue());
            }
        }
    }
    public boolean dataValid(){
        return data!=null;
        }
    public Dimension setData(TableData data0,int headColIdx0){
        headColIdx = headColIdx0;
        data=data0;
        maxRow=0;
        hcnt=(hcnt0<data.rows() ? hcnt0 : data.rows());
        vcnt=(vcnt0<cols() ? vcnt0 : cols());
        for(int j=0;j<data.rows();j++){
            int k=data.data().get(j).get(headColIdx).getValue().length();
            if (k>maxRow) maxRow=k;
        }
        maxCol=0;
        for(int j=headColIdx0+1;j<data.cols();j++){
            int k=data.columns().get(j).getName().length();
            if (k>maxCol) maxCol=k;
            }
        hsize=(maxRow+1)*hsymSize;
        vsize=(maxCol+1)*vsymSize;
        yy=vsize+(hcnt+1)*csize;
        xx=hsize+(vcnt+1)*csize;
        Dimension dd=new Dimension();
        dd.height=yy;
        dd.width=xx;
        xt0=csize+hsize;
        yt0=csize+vsize;
        HListScroll.setOrientation(JScrollBar.VERTICAL);
        HListScroll.setBounds(0,yt0, csize, yy-yt0);
        VListScroll.setOrientation(JScrollBar.HORIZONTAL);
        VListScroll.setBounds(xt0,0, xx-xt0,csize);
        HList.setBounds(csize, csize+vsize, hsize, yy-yt0);
        VList.setBounds(csize+hsize, csize, xx-xt0, vsize);
        TableArea.setBounds(xt0, yt0, xx-xt0, yy-yt0);
        H=new JTextField[hcnt];
        for(int i=0;i<hcnt;i++){
            H[i]=new JTextField();
            setCell(H[i]);
            H[i].setBounds(0,i*csize,hsize,csize);
            HList.add(H[i]);
        }
        V=new JTextArea[vcnt];
        for(int i=0;i<vcnt;i++){
            V[i]=new JTextArea();
            setCell(V[i]);
            V[i].setBounds(i*csize,0,csize,vsize);
            VList.add(V[i]);
        }
        T=new JTextField[hcnt][];
        for(int i=0;i<hcnt;i++){
            T[i]=new JTextField[vcnt];
            for(int j=0;j<vcnt;j++){
                final int ii=i;
                final int jj=j;
                T[i][j]=new JTextField();
                setCell(T[i][j]);
                T[i][j].setBounds(j*csize,i*csize,csize,csize);
                TableArea.add(T[i][j]);
                T[i][j].addMouseListener(new MouseListener(){
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (dataValid() && lsn!=null)
                            lsn.cellSelected(i0+ii, j0+jj);
                        }
                    @Override
                    public void mousePressed(MouseEvent e) {
                    }
                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }
                    @Override
                    public void mouseEntered(MouseEvent e) {
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                    }
                    });
                }
            }
        HListScroll.setMaximum(data.rows());    // Сразу вызывает событие
        VListScroll.setMaximum(cols());
        return dd;
    }
    private void setCell(JComponent cell){
        cell.setBackground(new Color(colorBack));
        cell.setForeground(Color.black);
        if (cell instanceof JTextArea) ((JTextArea)cell).setEditable(false);
        else ((JTextField)cell).setEditable(false);
        cell.setFont(new Font("Arial",0,12));
        cell.setBorder(border);
    }
    public ScrollTableView(int size0, int h0, int v0, I_TableBack lsn0){
        lsn=lsn0;
        csize=size0;
        hcnt0=hcnt=h0;
        vcnt0=vcnt=v0;
        this.setLayout(null);
        TableArea = new javax.swing.JPanel();
        VList = new javax.swing.JPanel();
        HList = new javax.swing.JPanel();
        HListScroll = new javax.swing.JScrollBar();
        VListScroll = new javax.swing.JScrollBar();
        border=BorderFactory.createLineBorder(Color.black);
        TableArea.setBorder(border);
        VList.setBorder(border);
        HList.setBorder(border);
        TableArea.setLayout(null);
        VList.setLayout(null);
        HList.setLayout(null);
        VListScroll.setBorder(border);
        HListScroll.setBorder(border);
        add(TableArea);
        add(VList);
        add(HList);
        add(VListScroll);
        add(HListScroll);
        HListScroll.addAdjustmentListener(new AdjustmentListener(){
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                if (!dataValid()) return;
                i0=e.getValue();
                repaint();
                System.out.println(e.getValue());
            }});
        VListScroll.addAdjustmentListener(new AdjustmentListener(){
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                if (!dataValid()) return;
                j0=e.getValue();
                repaint();
                System.out.println(e.getValue());
            }});
    }
    public static void main(String[] args) {
        // TODO code application logic here
    }
}