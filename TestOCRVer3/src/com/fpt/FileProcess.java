/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt;


import com.entity.Answer;
import static com.fpt.App.getFromText;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JOptionPane;

/**
 *
 * @author BN
 */
public class FileProcess {

    public String txtLib = "";

    public void loadLib() {
        FileInputStream fis = null;
        BufferedReader reader = null;
        try {
            fis = new FileInputStream("lib.txt");
            reader = new BufferedReader(new InputStreamReader(fis));
            String line = reader.readLine();

            while (line != null) {
                txtLib += line + "\n";
                line = reader.readLine();
            }
            System.out.println("==========================================");
            System.out.println(txtLib + "------------------------");
            System.out.println("==========================================");
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        } finally {
            try {
                reader.close();
                fis.close();
            } catch (IOException ex) {
            }
        }
    }

    public Answer search(String txtSearch) {
        Answer an = new Answer();
        
        String[] libs = txtLib.split("\n");
        int length = libs.length;
        System.out.println(txtSearch);
        boolean checkFind = false;
        for (int i = 0; i < length; i++) {

            if (libs[i].contains(txtSearch)) {
                for (int j = 0; j < libs[i].length(); j++) {
                    if (libs[i].charAt(j) == '|') {
                        String question = libs[i].substring(0, j);
                        String answer = libs[i].substring(j + 1, libs[i].length());
                        an.setQuestion(question);
                        an.setAnswer(answer);
                        return an;
                    }
                }
            }
            
        }
        an.setQuestion("Not Found.!!!");
        return an;
    }

    boolean getLib(String key) {
        if(key.equalsIgnoreCase("6250-44AE"))
            return true;
        return false;
    }
}
