package com.caliban;

import com.caliban.gui.MainInterface;

public class App 
{
    public static void main( String[] args )
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainInterface mainInterface = new MainInterface();
                mainInterface.setVisible(true);
            }
        });
    }
}
