package BuisnessLogic;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

/// clasa folosita pentru a loga mesaje

public class Logger {

    private static Logger instance;
    private static final String LOG_FILE = "log.txt";
    private boolean writeToConsole = true;
    private boolean writeToFile = true;
    private JTextArea textArea;

    private Logger() {

    }

    public static synchronized Logger getInstance()  {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }


    public void log(String message) {
        if (writeToConsole) {
            System.out.println(message);
        }

        if (writeToFile) {
            try (FileWriter fw = new FileWriter(LOG_FILE, true);
                 PrintWriter pw = new PrintWriter(fw)) {
                pw.println(message);
            } catch (IOException e) {
                System.out.println("Eroare la scriere in log: " + e.getMessage());
            }
        }

        if(textArea != null) {
            textArea.append(message+"\n");
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }


    }

    public void setWriteToConsole(boolean writeToConsole) {
        this.writeToConsole = writeToConsole;
    }

    public void setWriteToFile(boolean writeToFile) {
        this.writeToFile = writeToFile;
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }
}
