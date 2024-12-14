package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyHandler extends KeyAdapter {

    public boolean wPressed, aPressed, sPressed, dPressed;
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) wPressed = true;
        if (code == KeyEvent.VK_A) aPressed = true;
        if (code == KeyEvent.VK_S) sPressed = true;
        if (code == KeyEvent.VK_D) dPressed = true;

        if (code == KeyEvent.VK_UP) upPressed = true;
        if (code == KeyEvent.VK_DOWN) downPressed = true;
        if (code == KeyEvent.VK_LEFT) leftPressed = true;
        if (code == KeyEvent.VK_RIGHT) rightPressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) wPressed = false;
        if (code == KeyEvent.VK_A) aPressed = false;
        if (code == KeyEvent.VK_S) sPressed = false;
        if (code == KeyEvent.VK_D) dPressed = false;

        if (code == KeyEvent.VK_UP) upPressed = false;
        if (code == KeyEvent.VK_DOWN) downPressed = false;
        if (code == KeyEvent.VK_LEFT) leftPressed = false;
        if (code == KeyEvent.VK_RIGHT) rightPressed = false;
    }
}
