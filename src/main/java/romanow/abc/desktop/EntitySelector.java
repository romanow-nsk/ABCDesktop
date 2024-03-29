/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop;

import romanow.abc.core.Utils;
import romanow.abc.core.entity.Entity;
import romanow.abc.core.entity.EntityList;
import romanow.abc.core.entity.I_Compare;
import retrofit2.Response;

import java.io.IOException;

/**
 *
 * @author romanow
 */
public abstract class EntitySelector<T extends Entity> extends javax.swing.JFrame {
    EntityList<T> flist;
    PopupList popup;
    String token;
    I_OK ok;
    public EntitySelector(int x0, int y0,String title, Client main, I_OK ok0) {
        initComponents();
        setTitle(title);
        ok = ok0;
        token = main.getDebugToken();
        setBounds(x0,y0,470,150);
        setVisible(true);
        try {
            Response<EntityList<T>> res = getList();
            if (!res.isSuccessful()) {
                System.out.println("Ошибка выбора  " + Utils.httpError(res));
                return;
                }
            flist = res.body();
            flist.sort(new I_Compare() {
                @Override
                public int compare(Entity one, Entity two) {
                    return 0;
                }
            },false);
            FList.removeAll();
            for(T ff : flist)
                FList.add(ff.getTitle());
            popup = new PopupList(main, this, 100, 60, 290, 25, flist, new I_ListSelectedFull() {
                @Override
                public void onSelect(long idx,String name) {
                    FList.select((int)idx);
                    popup.setVisible(false);
                    }
                @Override
                public void onCancel() {
                    popup.setVisible(false);
                    }
                });
            add(popup);
            } catch (IOException e) { System.out.println(e.toString()); }
        }
    abstract Response<EntityList<T>> getList() throws IOException;
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        FList = new java.awt.Choice();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().add(FList);
        FList.setBounds(19, 21, 370, 25);

        jButton1.setText("ОК");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(20, 60, 50, 23);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("->");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(80, 65, 34, 14);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ok.onOK(flist.get(FList.getSelectedIndex()));
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Choice FList;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
