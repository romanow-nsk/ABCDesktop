/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.ValuesBase;

/**
 *
 * @author romanow
 */
public class MainBase extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    public MainBase() {
        initComponents();
        setBounds(200,200,180,110);
        }
    public static void runGUIClass(final int appType){
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ValuesBase.env().applicationObject(appType);
                    } catch (UniException ee){
                        System.out.println("Ошибка запуска класса GUI: "+ee.toString());
                        }
                }
            });
        }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        ClientX = new javax.swing.JButton();
        ServerX = new javax.swing.JButton();

        jButton2.setText("jButton2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource(ValuesBase.env().applicationName(ValuesBase.AppNameIconPath)))); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        getContentPane().add(jButton1);
        jButton1.setBounds(10, 10, 50, 50);

        ClientX.setText("Client");
        ClientX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClientXActionPerformed(evt);
            }
        });
        getContentPane().add(ClientX);
        ClientX.setBounds(80, 10, 70, 23);

        ServerX.setText("Server");
        ServerX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ServerXActionPerformed(evt);
            }
        });
        getContentPane().add(ServerX);
        ServerX.setBounds(80, 40, 73, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ClientXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClientXActionPerformed
        runGUIClass(ValuesBase.ClassNameClient);
        //Client.main(null);
        dispose();
    }//GEN-LAST:event_ClientXActionPerformed

    private void ServerXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ServerXActionPerformed
        runGUIClass(ValuesBase.ClassNameCabinet);
        dispose();
    }//GEN-LAST:event_ServerXActionPerformed

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
            java.util.logging.Logger.getLogger(MainBase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainBase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainBase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainBase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainBase().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ClientX;
    private javax.swing.JButton ServerX;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    // End of variables declaration//GEN-END:variables
}
