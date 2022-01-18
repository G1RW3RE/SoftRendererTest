package com.glrwere.g3d.core;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GSimpleEngineMain extends JPanel {

    public GSimpleEngineMain() {

    }

    @Override
    public void paint(Graphics gRaw) {
        Graphics2D g = (Graphics2D) gRaw;
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
