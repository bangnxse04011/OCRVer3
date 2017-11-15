package com.fpt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.Window.Type;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JLabel;

public class Result
        extends Frame {

    public final int width = 300;
    public final int height = 60;
    private JLabel lblA;
    private JLabel lblQ;
    private JLabel lblQuery;

    public Result() {
        initComponents();
    }

    public JLabel getLblA() {
        return this.lblA;
    }

    public void setLblA(JLabel lblA) {
        this.lblA = lblA;
    }

    public JLabel getLblQ() {
        return this.lblQ;
    }

    public void setLblQ(JLabel lblQ) {
        this.lblQ = lblQ;
    }

    public JLabel getLblQuery() {
        return this.lblQuery;
    }

    public void setLblQuery(JLabel lblQuery) {
        this.lblQuery = lblQuery;
    }

    private void initComponents() {
        this.lblQuery = new JLabel();
        this.lblA = new JLabel();
        this.lblQ = new JLabel();
        setAlwaysOnTop(true);
        setBackground(new Color(240, 240, 240));
        setBounds(new Rectangle((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - 150, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 60, 300, 60));
        setExtendedState(0);
        setLocation(new Point((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - 150, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 60));
        setMaximizedBounds(new Rectangle((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - 150, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 60, 300, 60));
        setMaximumSize(new Dimension(300, 60));
        setMinimumSize(new Dimension(300, 60));
        setUndecorated(true);
        setOpacity(0.4F);
        setPreferredSize(new Dimension(300, 60));
        setSize(new Dimension(300, 60));
        setState(0);
        setType(Window.Type.UTILITY);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                Result.this.exitForm(evt);
            }
        });
        this.lblQuery.setPreferredSize(new Dimension(300, 20));
        add(this.lblQuery, "North");
        this.lblA.setPreferredSize(new Dimension(300, 20));
        add(this.lblA, "South");
        this.lblQ.setPreferredSize(new Dimension(300, 20));
        add(this.lblQ, "Center");
        pack();
    }

    private void exitForm(WindowEvent evt) {
        System.exit(0);
    }

    public void refresh() {
        dispose();
        this.lblQuery.setText("");
        this.lblA.setText("");
        this.lblQ.setText("");
        setAlwaysOnTop(true);
        setBackground(new Color(240, 240, 240));
        setBounds(new Rectangle((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - 150, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 60, 300, 60));
        setExtendedState(0);
        setLocation(new Point((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - 150, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 60));
        setMaximizedBounds(new Rectangle((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - 150, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 60, 300, 60));
        setMaximumSize(new Dimension(300, 60));
        setMinimumSize(new Dimension(300, 60));
        setUndecorated(true);
        setOpacity(0.4F);
        setPreferredSize(new Dimension(300, 60));
        setSize(new Dimension(300, 60));
        setState(0);
        setType(Window.Type.UTILITY);
        this.lblQuery.setPreferredSize(new Dimension(300, 20));
        this.lblA.setPreferredSize(new Dimension(300, 20));
        this.lblQ.setPreferredSize(new Dimension(300, 20));
        pack();
    }
}
