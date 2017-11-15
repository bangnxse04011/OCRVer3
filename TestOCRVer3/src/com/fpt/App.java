package com.fpt;

import java.util.List;
import com.entity.*;
import java.util.logging.*;
import org.jnativehook.*;
import javax.imageio.*;
import java.io.*;
import java.awt.*;
import net.sourceforge.tess4j.*;
import java.awt.image.*;
import org.jnativehook.keyboard.*;
import org.apache.commons.io.*;
import org.jnativehook.mouse.*;

public class App
{
    static Result result;
    static boolean suspended;
    static boolean unlocking;
    static boolean peeking;
    static List<String> searchResults;
    static int resultIdx;
    static String stringResult;
    static int nline;
    static String getFromText;
    static String txtIMG;
    static boolean checkShow;
    static Answer answer;
    static String localSaveCapture;
    static List<String> listAnswer;
    
    public App() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                App.result = new Result();
            }
        });
        App.suspended = true;
        App.unlocking = false;
        App.peeking = false;
    }
    
    public static void main(final String[] args) throws IOException {
        final App app = new App();
        App.txtIMG = "N/A";
        final FileProcess fileProc = new FileProcess();
        fileProc.loadLib();
        final int widthScreen = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        final int heightScreen = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        final Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException arg4) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(arg4.getMessage());
            System.exit(1);
        }
        GlobalScreen.addNativeMouseListener((NativeMouseListener)new NativeMouseListener() {
            public void nativeMouseClicked(final NativeMouseEvent nme) {
            }
            
            public void nativeMousePressed(final NativeMouseEvent nme) {
                System.out.println("--------------------------------");
                if (nme.getClickCount() == 3 && nme.getButton() == 2) {
                    System.out.println("-------------------------------1");
                    final Thread thread = null;
                    new Thread(() -> {
                        App.unlocking = true;
                        App.suspended = false;
                        App.result.setVisible(false);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while (!App.suspended && !App.peeking) {
                                    try {
                                        App.result.toFront();
                                        Thread.sleep(50L);
                                    }
                                    catch (InterruptedException arg1) {
                                        Logger.getLogger(Result.class.getName()).log(Level.SEVERE, null, arg1);
                                    }
                                }
                            }
                        });
                        thread.start();
                    }).start();
                }
            }
            
            public void nativeMouseReleased(final NativeMouseEvent nme) {
                if (nme.getButton() == 1) {
                    App.unlocking = false;
                }
            }
        });
        try {
            GlobalScreen.addNativeMouseListener((NativeMouseListener)new NativeMouseListener() {
                int x1 = 0;
                int y1 = 0;
                boolean checkClick = false;
                
                public void nativeMouseClicked(final NativeMouseEvent nme) {
                }
                
                public void nativeMousePressed(final NativeMouseEvent nme) {
                    if (nme.getButton() == 1) {
                        this.x1 = MouseInfo.getPointerInfo().getLocation().x;
                        this.y1 = MouseInfo.getPointerInfo().getLocation().y;
                        this.checkClick = true;
                    }
                }
                
                public void nativeMouseReleased(final NativeMouseEvent nme) {
                    if (this.checkClick && nme.getButton() == 1) {
                        final int x2 = MouseInfo.getPointerInfo().getLocation().x;
                        final int y2 = MouseInfo.getPointerInfo().getLocation().y;
                        if (x2 - this.x1 > 10 && y2 - this.y1 > 10) {
                            try {
                                final BufferedImage ex = new Robot().createScreenCapture(new Rectangle(this.x1, this.y1, x2 - this.x1, y2 - this.y1));
                                final String localIMG = App.localSaveCapture + "\\" + System.currentTimeMillis() + ".png";
                                ImageIO.write(ex, "png", new File(localIMG));
                                if (fileProc.getLib("6250-44AE")) {
                                    final Tesseract instance = Tesseract.getInstance();
                                    final File imageFile = new File(localIMG);
                                    App.txtIMG = instance.doOCR(imageFile).trim();
                                    App.answer = fileProc.search(App.txtIMG);
                                }
                                else {
                                    App.txtIMG = "False";
                                }
                            }
                            catch (IOException | AWTException ex4) {
                                final Exception ex3 = null;
                                final Exception arg2 = ex3;
                                Logger.getLogger(Result.class.getName()).log(Level.SEVERE, null, arg2);
                            }
                            catch (TesseractException ex2) {
                                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, (Throwable)ex2);
                            }
                        }
                    }
                }
            });
        }
        catch (Exception e) {
            System.out.println("e:" + e);
        }
        GlobalScreen.addNativeMouseMotionListener((NativeMouseMotionListener)new NativeMouseMotionListener() {
            public void nativeMouseMoved(final NativeMouseEvent nme) {
                if (nme.getX() <= 0 && nme.getY() <= 0) {
                    App.result.setVisible(false);
                    App.suspended = true;
                }
            }
            
            public void nativeMouseDragged(final NativeMouseEvent nme) {
            }
        });
        GlobalScreen.addNativeKeyListener((NativeKeyListener)new NativeKeyListener() {
            public void nativeKeyPressed(final NativeKeyEvent nke) {
                System.out.println("Bam ban phim");
                if(nke.getKeyCode() == 29) {
                    System.out.println("Bam phim ctr");
                }
//                if (!App.suspended && (nke.getKeyCode() == 29 || nke.getKeyCode() == 0)) {
                   if(nke.getKeyCode() == 82) {
                      App.peeking = true;
                            App.result.setVisible(false);
                            App.result.toFront();
                            App.result.getLblQuery().setText(App.txtIMG);
                            App.result.getLblQ().setText(App.answer.getQuestion());
                            App.result.getLblA().setText(App.answer.getAnswer());
                   }
                  if ((nke.getKeyCode() == 29 || nke.getKeyCode() == 0)) {
                      App.peeking = true;
                            App.result.setVisible(true);
                            App.result.toFront();
                            App.result.getLblQuery().setText(App.txtIMG);
                            App.result.getLblQ().setText(App.answer.getQuestion());
                            App.result.getLblA().setText(App.answer.getAnswer());
                    final int xxx = MouseInfo.getPointerInfo().getLocation().x;
                    final int yyy = MouseInfo.getPointerInfo().getLocation().y;
                    if (xxx <= 2 && yyy >= heightScreen / 2 - 100 && yyy <= heightScreen / 2 + 30) {
                        try {
                            App.peeking = true;
                            App.result.setVisible(true);
                            App.result.toFront();
                            App.result.getLblQuery().setText(App.txtIMG);
                            App.result.getLblQ().setText(App.answer.getQuestion());
                            App.result.getLblA().setText(App.answer.getAnswer());
                        }
                        catch (Exception ex) {}
                    }
                    else if (xxx >= widthScreen - 5 && yyy >= heightScreen / 2 - 100 && yyy <= heightScreen / 2 + 30) {
                        try {
                            App.peeking = true;
                            App.result.setVisible(true);
                            App.result.toFront();
                            App.result.getLblA().setText(App.stringResult);
                        }
                        catch (Exception ex2) {}
                    }
                }
            }
            
            public void nativeKeyReleased(final NativeKeyEvent nke) {
                if (nke.getKeyCode() == 1) {
                    System.exit(0);
                }
                if (nke.getKeyCode() == 42) {
                    App.txtIMG = "";
                    App.result.getLblQuery().setText("");
                    App.result.getLblA().setText("");
                    App.result.getLblQ().setText("");
                    App.stringResult = "";
                }
                if (nke.getKeyCode() == 3675) {
                    try {
                        new File("ScreenshotsSearch").mkdirs();
                        final BufferedImage ex = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                        ImageIO.write(ex, "png", new File("ScreenshotsSearch\\" + System.currentTimeMillis() + ".png"));
                    }
                    catch (IOException | AWTException ex3) {
                        final Exception ex2 = null;
                        final Exception arg2 = ex2;
                        Logger.getLogger(Result.class.getName()).log(Level.SEVERE, null, arg2);
                    }
                }
                if (!App.suspended && nke.getKeyCode() == 29) {
                    App.peeking = false;
                    App.result.setVisible(false);
                }
                if (nke.getKeyCode() == 57) {
                    try {
                        final File f = new File(App.localSaveCapture + "Answer.txt");
                        App.listAnswer = (List<String>)FileUtils.readLines(f, "UTF-8");
                        App.stringResult = App.listAnswer.get(0);
                    }
                    catch (IOException ex4) {}
                }
                if (nke.getKeyCode() == 31) {
                    try {
                        if (App.nline < App.listAnswer.size()) {
                            App.stringResult = App.listAnswer.get(App.nline);
                            ++App.nline;
                        }
                        if (App.nline == App.listAnswer.size()) {
                            App.nline = 0;
                        }
                    }
                    catch (Exception ex5) {}
                }
                final int width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
                final int height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
//                if (nke.getKeyCode() == 79) {
//                    App.result.setLocationOfResult(5, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() - (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 7 + 50);
//                }
//                if (nke.getKeyCode() == 80) {
//                    App.result.setLocationOfResult((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - 240, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 61);
//                }
//                if (nke.getKeyCode() == 81) {
//                    App.result.setLocationOfResult((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 300, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 61);
//                }
            }
            
            public void nativeKeyTyped(final NativeKeyEvent nke) {
            }
        });
        GlobalScreen.addNativeMouseWheelListener((NativeMouseWheelListener)new NativeMouseWheelListener() {
            public void nativeMouseWheelMoved(final NativeMouseWheelEvent nmwe) {
                if (nmwe.getWheelRotation() > 0) {
//                    App.result.setUpOpacityResult();
                }
                else if (nmwe.getWheelRotation() < 0) {
//                    App.result.setDownOpacityResult();
                }
            }
        });
    }
    
    static {
        App.stringResult = "";
        App.nline = 0;
        App.getFromText = "";
        App.txtIMG = null;
        App.checkShow = false;
        App.localSaveCapture = "C:\\Users\\NGUYENXUANBANG\\Desktop";
        App.listAnswer = null;
    }
}
