package motorph.ui.panels;

public class UnderMaintenance extends javax.swing.JPanel {

    public void setFeatureName(String featureName) {
        lblFeatureName.setText(featureName + " is under maintenance.\n Please check back later.");
    }

    public UnderMaintenance() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblFeatureName = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1054, 720));
        setMinimumSize(new java.awt.Dimension(1054, 720));
        setLayout(new java.awt.BorderLayout());

        lblFeatureName.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblFeatureName.setForeground(new java.awt.Color(102, 102, 102));
        lblFeatureName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFeatureName.setText("Under Maintenance");
        add(lblFeatureName, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblFeatureName;
    // End of variables declaration//GEN-END:variables
}
