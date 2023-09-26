package romanow.abc.desktop;

import romanow.abc.core.I_EmptyEvent;

import java.awt.*;

public class GUITimer {
    private Thread thread=null;
    private boolean stop=false;
    public synchronized void start(final int delay, final I_EmptyEvent back){
        stop=false;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(delay*1000);
                    if (stop)
                        return;
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            synchronized (GUITimer.this){
                                if (stop)
                                    return;
                                }
                            back.onEvent();
                        }
                    });
                } catch (InterruptedException e) {}
            }
        });
        thread.start();
    }
    public synchronized void cancel(){
        if (thread!=null)
            thread.interrupt();
        stop = true;
    }
//------------------------------------------------------------------
public static void trace(Component component, int delay, Color clr) {
    final Color color = component.getBackground();
    component.setBackground(clr);
    new GUITimer().start(delay, new I_EmptyEvent() {
        @Override
        public void onEvent() {
            component.setBackground(color);
            }
        });
    }
}
