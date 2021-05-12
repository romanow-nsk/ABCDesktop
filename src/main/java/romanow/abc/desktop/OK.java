/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop;

import java.awt.*;

/**
 *
 * @author romanow
 */
public class OK extends ABCBaseView {
    private I_Button ok;
    /**
     * Creates new form OK
     */
    public OK(int x,int y,String title) {
        this(x,y,title,null);
        }
    public OK(int x,int y,String title, I_Button ok0) {
        super(350,70);
        setUndecorated(true);
        initComponents();
        ok = ok0;
        OK.setFont(new Font("Arial Cyr", Font.PLAIN, 14));
        UtilsDesktop.setButtonText(OK,title,35);
        //setBounds(x+20,y+20,330,120);
        OK.setBounds(10, 5, 270, 60);
        positionOn(x+20,y+20);
        delayIt();
        //setVisible(true);
        }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        OK = new javax.swing.JButton();
        Canсel = new javax.swing.JButton();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        OK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKActionPerformed(evt);
            }
        });
        getContentPane().add(OK);
        OK.setBounds(10, 0, 270, 50);

        Canсel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/remove.png"))); // NOI18N
        Canсel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CanсelActionPerformed(evt);
            }
        });
        getContentPane().add(Canсel);
        Canсel.setBounds(290, 10, 50, 50);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void OKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKActionPerformed
        closeView();
        if (ok!=null)
            ok.onPush();
    }//GEN-LAST:event_OKActionPerformed

    private void CanсelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CanсelActionPerformed
        closeView();
    }//GEN-LAST:event_CanсelActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Canсel;
    private javax.swing.JButton OK;
    // End of variables declaration//GEN-END:variables
}
