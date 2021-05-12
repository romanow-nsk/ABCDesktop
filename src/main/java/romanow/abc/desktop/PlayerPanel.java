/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop;

import romanow.abc.desktop.audioplayer.AudioPlayer;
import romanow.abc.desktop.audioplayer.PlayerCallBack;

import java.net.URL;

/**
 *
 * @author romanow
 */
public class PlayerPanel extends javax.swing.JPanel {
    private AudioPlayer player = new AudioPlayer();
    private URL url=null;
    private boolean isPlaying=false;
    private boolean isPause=false;
    private boolean isBusy=false;
    private int voiceSize=0;
    private PlayerCallBack back = new PlayerCallBack() {
        @Override
        public void onPosition(int pos) {
            int pp = VoicePosition.getValue();
            if (Math.abs(pp-pos)>2){
                player.resume(pp);
                }
            else
                VoicePosition.setValue(pos);
            }

        @Override
        public void onEnd() {
            stop();
        }
    };
    public void setURL(URL url0){
        url = url0;
        player.stop();
        }
    public void stop(){
        if (isBusy) return;
        player.stop();
        isPlaying=false;
        isPause=false;
        VoicePlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/media_play.png")));
        VoicePosition.setValue(0);
        }
    /**
     * Creates new form PlayerPanel
     */
    public PlayerPanel() {
        initComponents();
        setBounds(0,0,180,50);
        setVisible(true);
        VoicePosition.setValue(0);
        }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        VoicePlay = new javax.swing.JButton();
        VoicePosition = new javax.swing.JSlider();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setLayout(null);

        VoicePlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/media_play.png"))); // NOI18N
        VoicePlay.setContentAreaFilled(false);
        VoicePlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VoicePlayActionPerformed(evt);
            }
        });
        add(VoicePlay);
        VoicePlay.setBounds(10, 10, 33, 30);
        add(VoicePosition);
        VoicePosition.setBounds(60, 10, 110, 23);
    }// </editor-fold>//GEN-END:initComponents

    private Runnable ff = new Runnable() {
        @Override
        public void run() {
            final boolean bb = player.startToPlay(url, back);
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    if (bb){
                        VoicePosition.setMaximum(player.getTotalPlayTime());
                        VoicePosition.setValue(0);
                        isPlaying=true;
                        isPause=false;
                        }
                    else {
                        VoicePlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/media_play.png")));
                    }
                    isBusy=false;
                }
            });
        }};

    private void VoicePlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VoicePlayActionPerformed
        if (url==null)
            return;
        if (isBusy) return;
        if (!isPlaying){
            player.stop();
            VoicePlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/media_pause.png")));
            isBusy=true;
            new Thread(ff).start();
            }
        else{
            if (!isPause){
                player.pause();
                VoicePlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/media_play.png")));
                }
            else{
                player.resume(VoicePosition.getValue());
                VoicePlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/media_pause.png")));
                }
            isPause = !isPause;
            }
    }//GEN-LAST:event_VoicePlayActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton VoicePlay;
    private javax.swing.JSlider VoicePosition;
    // End of variables declaration//GEN-END:variables
}
