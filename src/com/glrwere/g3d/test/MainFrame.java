package com.glrwere.g3d.test;

import com.glrwere.g3d.core.GSimpleEngineMain;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

    GSimpleEngineMain gsEngine;

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

    public MainFrame() {
        setTitle("GSEngine Test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        gsEngine = new GSimpleEngineMain();
        setContentPane(gsEngine);
    }
}
