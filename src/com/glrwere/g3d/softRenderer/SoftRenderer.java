package com.glrwere.g3d.softRenderer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 * 简单软渲染工具，练手用
 * */
public class SoftRenderer extends JFrame {

    RenderPanel renderPanel;

    private Thread updateThread;

    public static void main(String[] args) {
        SoftRenderer softRenderer = new SoftRenderer();
        softRenderer.setVisible(true);
    }

    public SoftRenderer() {
        super("Tiny Renderer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        renderPanel = new RenderPanel();
        setContentPane(renderPanel);

        updateThread = new Thread(() -> {
            while (true) {
                renderPanel.repaint();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        updateThread.start();

        testDrawLine();
    }

    public void testDrawLine() {
        for (int i = 0; i < 100; i++) {
            int x1 = ((int) (Math.random() * 784));
            int y1 = ((int) (Math.random() * 561));
            int x2 = ((int) (Math.random() * 784));
            int y2 = ((int) (Math.random() * 561));
            Color randColor = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
            renderPanel.drawLine2D(new Point(x1, y1), new Point(x2, y2), randColor);
        }
    }

    private static class RenderPanel extends JPanel {

        private final BufferedImage canvas;

        private final Graphics2D gx;

        private final WritableRaster raster;

        public RenderPanel() {
            canvas = new BufferedImage(784, 561, BufferedImage.TYPE_INT_ARGB);
            gx = canvas.createGraphics();
            gx.setBackground(Color.BLACK);
            gx.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            raster = canvas.getRaster();
        }

        @Override
        public void paint(Graphics gRaw) {
            Graphics2D g = (Graphics2D) gRaw;
            g.drawImage(canvas, 0, 0, this);
        }

        // 线段视口裁剪方法
        private int[] cohenSutherlandLineClip(Point v1, Point v2) {

        }

        private int lineEncode() {
            return 0;
        }

        // ===== 绘图方法 =====
        public void drawLine2D(Point v1, Point v2, Color c) {
            float[] colorARGB = new float[] {c.getAlpha(), c.getRed(), c.getGreen(), c.getBlue()};

            int x1 = v1.x;
            int y1 = v1.y;
            int x2 = v2.x;
            int y2 = v2.y;

            int x, y, inc;

            if (x1 == x2 && y1 == y2) {
                raster.setPixel(x1, y1, colorARGB);
            }
            else if (x1 == x2) {
                // horizontal line
                inc = (y2 >= y1) ? 1 : -1;
                for (y = y1; y != y2; y += inc) {
                    raster.setPixel(x1, y, colorARGB);
                }
            }
            else if (y1 == y2) {
                // vertical line
                inc = (x2 >= x1) ? 1 : -1;
                for (x = x1; x != x2; x += inc) {
                    raster.setPixel(x, y1, colorARGB);
                }
            }
            else {
                int deltaX = Math.abs(x2 - x1);
                int deltaY = Math.abs(y2 - y1);
                int dx = (x2 >= x1) ? 1 : -1;
                int dy = (y2 >= y1) ? 1 : -1;
                int err = (deltaX > deltaY ? deltaX : -deltaY) / 2;
                int temp;
                x = x1;
                y = y1;

                while (true) {
                    raster.setPixel(x, y, colorARGB);
                    if (x == x2 && y == y2) {
                        break;
                    } else {
                        temp = err;
                        if (temp > -deltaX) {
                            err -= deltaY;
                            x += dx;
                        }
                        if (temp < deltaY) {
                            err += deltaX;
                            y += dy;
                        }
                    }
                }
            }
        }
    }
}
