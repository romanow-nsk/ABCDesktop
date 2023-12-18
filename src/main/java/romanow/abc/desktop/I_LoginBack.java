package romanow.abc.desktop;

import javax.swing.*;
import java.awt.*;

public interface I_LoginBack {
    public void onPush();
    public void onLoginSuccess(String passWord);
    public void sendPopupMessage(JFrame parent, Container button, String text);
}
