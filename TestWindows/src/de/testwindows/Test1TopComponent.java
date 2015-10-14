/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.testwindows;

import java.awt.BorderLayout;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//de.testwindows//Test1//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "Test1TopComponent",
        iconBase = "de/testwindows/resources/one.png", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "de.testwindows.Test1TopComponent")
@ActionReferences({
    @ActionReference(path = "Menu/Window", position = 0 ),
    @ActionReference(path = "Toolbars/Window", position = 0)
})
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_Test1Action",
        preferredID = "Test1TopComponent"
)
@Messages({
    "CTL_Test1Action=Test1",
    "CTL_Test1TopComponent=Test1 Window",
    "HINT_Test1TopComponent=This is a Test1 window"
})
public final class Test1TopComponent extends TopComponent {
    
    private final ArdorPanel ardorPanel;

    public Test1TopComponent() {
        initComponents();
        setName(Bundle.CTL_Test1TopComponent());
        setToolTipText(Bundle.HINT_Test1TopComponent());
        ardorPanel = new ArdorPanel(new MyScene());
        add(ardorPanel, BorderLayout.CENTER);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        ardorPanel.panelOpened();
    }

    @Override
    public void componentClosed() {
        ardorPanel.panelClosed();
    }

    @Override
    protected void componentHidden() {
        ardorPanel.panelHidden();
        super.componentHidden(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void componentShowing() {
        super.componentShowing(); //To change body of generated methods, choose Tools | Templates.
        ardorPanel.panelShowing();
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
}
