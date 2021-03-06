package app;

import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.PriorityQueue;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Earl Appiah - eea7
 */
public class adminForm extends javax.swing.JFrame {

    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    int rowCount;
    String newStatus = "";
    String reportID = "";
    String adminDetails = "";

    /**
     * Creates new form adminForm
     */
    public adminForm() {
        initComponents();
        Connect();
        getReports();
        confirmButton.setEnabled(false);
        textAreaTxt.setEditable(false);
        adminDetails = login.getFullName()+ ":" + login.getEnum();
        signedTxt.setText("by: "+ adminDetails);
        
                    reportIdTxt.setText("Report ID: ");
                    employeeNumTxt.setText("Employee Number: ");
                    typeTxt.setText("Type: ");
                    textAreaTxt.setText("");
                    dateTxt.setText("Date: ");
                    priorityTxt.setText("Priority: ");
                    statusTxt.setText("Status: " );
    }

    public void Connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/maintain", "root", "");

        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getRedReports() {
        try {
            pst = con.prepareStatement("select * from reports order by date");
            rs = pst.executeQuery();

            ResultSetMetaData rsd = rs.getMetaData();
            int c = rsd.getColumnCount();

            DefaultTableModel d = (DefaultTableModel) reportList.getModel();
            d.setRowCount(rowCount);

            while (rs.next()) {
                Vector v = new Vector();
                if (rs.getString("status").equals("Unconfirmed") && rs.getString("colour").equals("Red")) {
                    for (int i = 0; i <= c; i++) {
                        v.add(rs.getString("id"));
                    }
                    d.addRow(v);
                    rowCount++;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(adminForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getYellowReports() {
        try {
            pst = con.prepareStatement("select * from reports order by date");
            rs = pst.executeQuery();

            ResultSetMetaData rsd = rs.getMetaData();
            int c = rsd.getColumnCount();
            DefaultTableModel d = (DefaultTableModel) reportList.getModel();
            d.setRowCount(rowCount);

            while (rs.next()) {
                Vector v = new Vector();
                if (rs.getString("status").equals("Unconfirmed") && rs.getString("colour").equals("Yellow")) {
                    for (int i = 0; i <= c; i++) {
                        v.add(rs.getString("id"));
                    }
                    d.addRow(v);
                    rowCount++;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(adminForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getWhiteReports() {
        try {
            pst = con.prepareStatement("select * from reports order by date");
            rs = pst.executeQuery();

            ResultSetMetaData rsd = rs.getMetaData();
            int c = rsd.getColumnCount();
            DefaultTableModel d = (DefaultTableModel) reportList.getModel();
            d.setRowCount(rowCount);

            while (rs.next()) {
                Vector v = new Vector();
                if (rs.getString("status").equals("Unconfirmed") && rs.getString("colour").equals("White")) {
                    for (int i = 0; i <= c; i++) {
                        v.add(rs.getString("id"));
                    }
                    d.addRow(v);
                    rowCount++;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(adminForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getReports() {
        rowCount = 0;
        getRedReports();
        getYellowReports();
        getWhiteReports();
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backgroundPanel = new javax.swing.JPanel();
        Header = new javax.swing.JPanel();
        logo = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        title = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        Body = new javax.swing.JPanel();
        layoutBody = new javax.swing.JPanel();
        mainBody = new javax.swing.JPanel();
        reportIdTxt = new javax.swing.JLabel();
        priorityTxt = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        textAreaTxt = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        employeeNumTxt = new javax.swing.JLabel();
        dateTxt = new javax.swing.JLabel();
        statusTxt = new javax.swing.JLabel();
        pictureFrame = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        typeTxt = new javax.swing.JLabel();
        confirmButton = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        signedTxt = new javax.swing.JLabel();
        List = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        reportList = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        backgroundPanel.setBackground(new java.awt.Color(204, 102, 0));

        Header.setBackground(new java.awt.Color(51, 51, 51));
        Header.setPreferredSize(new java.awt.Dimension(550, 77));
        Header.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logo.setBackground(new java.awt.Color(102, 102, 102));
        logo.setLayout(new java.awt.BorderLayout());

        jLabel4.setBackground(new java.awt.Color(0, 51, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bug.png"))); // NOI18N
        logo.add(jLabel4, java.awt.BorderLayout.CENTER);

        Header.add(logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 90));

        title.setBackground(new java.awt.Color(51, 51, 51));
        title.setLayout(new java.awt.BorderLayout());

        jLabel5.setBackground(new java.awt.Color(0, 51, 255));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("ADMIN FORM");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel5.setPreferredSize(new java.awt.Dimension(155, 100));
        title.add(jLabel5, java.awt.BorderLayout.CENTER);

        Header.add(title, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 0, 860, 90));

        Body.setBackground(new java.awt.Color(0, 255, 153));
        Body.setLayout(new java.awt.BorderLayout());

        mainBody.setBackground(new java.awt.Color(255, 255, 255));

        reportIdTxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        reportIdTxt.setText("Report ID: \"Number\"");

        priorityTxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        priorityTxt.setText("Priority: \"Colour\"");

        textAreaTxt.setColumns(20);
        textAreaTxt.setLineWrap(true);
        textAreaTxt.setRows(5);
        jScrollPane2.setViewportView(textAreaTxt);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Description:");

        employeeNumTxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        employeeNumTxt.setText("Employee Number: \"Number\"");

        dateTxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dateTxt.setText("Data: DD-MM-YY");

        statusTxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        statusTxt.setText("Status: \"Un/Confirmed\"");

        pictureFrame.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pictureFrame.setText("Picture");
        pictureFrame.setOpaque(true);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Attachment:");

        typeTxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        typeTxt.setText("Type: \"Type\"");

        confirmButton.setBackground(new java.awt.Color(51, 255, 0));
        confirmButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        confirmButton.setText("Confirmed");
        confirmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmButtonActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("*When confirmed is pressed this will be signed");

        signedTxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        signedTxt.setText(" by:");

        javax.swing.GroupLayout mainBodyLayout = new javax.swing.GroupLayout(mainBody);
        mainBody.setLayout(mainBodyLayout);
        mainBodyLayout.setHorizontalGroup(
            mainBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainBodyLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pictureFrame, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainBodyLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(mainBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(signedTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(mainBodyLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(mainBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainBodyLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(mainBodyLayout.createSequentialGroup()
                        .addGroup(mainBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(mainBodyLayout.createSequentialGroup()
                                .addGroup(mainBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(reportIdTxt, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(typeTxt, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(employeeNumTxt, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(mainBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(statusTxt)
                                    .addComponent(dateTxt)
                                    .addComponent(priorityTxt)))
                            .addComponent(jScrollPane2))
                        .addGap(31, 31, 31))))
        );
        mainBodyLayout.setVerticalGroup(
            mainBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainBodyLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(mainBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(reportIdTxt)
                    .addComponent(priorityTxt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateTxt)
                    .addComponent(employeeNumTxt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(statusTxt)
                    .addComponent(typeTxt))
                .addGap(14, 14, 14)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(mainBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainBodyLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addGap(39, 39, 39))
                    .addGroup(mainBodyLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(signedTxt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)))
                .addGroup(mainBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pictureFrame, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layoutBodyLayout = new javax.swing.GroupLayout(layoutBody);
        layoutBody.setLayout(layoutBodyLayout);
        layoutBodyLayout.setHorizontalGroup(
            layoutBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layoutBodyLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainBody, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layoutBodyLayout.setVerticalGroup(
            layoutBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layoutBodyLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainBody, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Body.add(layoutBody, java.awt.BorderLayout.CENTER);

        List.setVerifyInputWhenFocusTarget(false);
        List.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBackground(new java.awt.Color(0, 255, 102));
        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(162, 427));

        reportList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Report ID's"
            }
        )
        {
            public boolean isCellEditable(int row, int column){
                return false;
            }
        }
    );
    reportList.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
    reportList.setDoubleBuffered(true);
    reportList.setDragEnabled(true);
    reportList.setPreferredSize(new java.awt.Dimension(162, 658));
    reportList.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            reportListMouseClicked(evt);
        }
    });
    jScrollPane1.setViewportView(reportList);

    List.add(jScrollPane1, java.awt.BorderLayout.CENTER);

    javax.swing.GroupLayout backgroundPanelLayout = new javax.swing.GroupLayout(backgroundPanel);
    backgroundPanel.setLayout(backgroundPanelLayout);
    backgroundPanelLayout.setHorizontalGroup(
        backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(Header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(backgroundPanelLayout.createSequentialGroup()
            .addComponent(List, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(Body, javax.swing.GroupLayout.PREFERRED_SIZE, 860, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(26, 26, 26))
    );
    backgroundPanelLayout.setVerticalGroup(
        backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(backgroundPanelLayout.createSequentialGroup()
            .addComponent(Header, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(10, 10, 10)
            .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(List, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
    );

    getContentPane().add(backgroundPanel, java.awt.BorderLayout.CENTER);

    pack();
    setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void confirmButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmButtonActionPerformed
        int p = JOptionPane.showConfirmDialog(null, "Do you want to confirm report done?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (p == 0) {
            try {
                pst = con.prepareStatement("update reports set status=? where id =? ");
                pst.setString(1, "Confirmed by " +adminDetails);
                pst.setString(2, reportID);

                int k = pst.executeUpdate();

                if (k == 1) {
                    JOptionPane.showMessageDialog(this, "Confirmation updated");
                   getReports();
                } else {
                    JOptionPane.showMessageDialog(this, "Confirmation Failed");
                }
                
                confirmButton.setEnabled(false);
            } catch (SQLException ex) {
                Logger.getLogger(adminForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_confirmButtonActionPerformed

    private void reportListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportListMouseClicked

        DefaultTableModel d = (DefaultTableModel) reportList.getModel();
        int selectIndex = reportList.getSelectedRow();
        String selectedId = d.getValueAt(selectIndex, NORMAL).toString();

        try {
            pst = con.prepareStatement("select * from reports where id = ?");
            pst.setString(1, selectedId);
            rs = pst.executeQuery();

            if (rs.next()) {
                if (rs.getString("id").equals(selectedId)) {
                    reportID = rs.getString("id");
                    reportIdTxt.setText("Report ID: " + rs.getString("id"));
                    employeeNumTxt.setText("Employee Number: " + rs.getString("enum"));
                    typeTxt.setText("Type: " + rs.getString("type"));
                    textAreaTxt.setText(rs.getString("descrip"));
                    dateTxt.setText("Date: " + rs.getString("date"));
                    priorityTxt.setText("Priority: " + rs.getString("colour"));
                    newStatus = rs.getString("status");
                    statusTxt.setText("Status: " + rs.getString("status"));
                    confirmButton.setEnabled(true);

                    byte[] img = rs.getBytes("picture");
                    if (img == null) {
                        pictureFrame.setText("No image to display");
                        pictureFrame.setIcon(null);
                    } else {
                        ImageIcon image = new ImageIcon(img);
                        Image Im = image.getImage();
                        Image myImg = Im.getScaledInstance(pictureFrame.getWidth(), pictureFrame.getHeight(), Image.SCALE_SMOOTH);
                        ImageIcon newImage = new ImageIcon(myImg);
                        pictureFrame.setIcon(newImage);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Record not found");
            }

        } catch (SQLException ex) {
            Logger.getLogger(adminForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_reportListMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(adminForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(adminForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(adminForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(adminForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new adminForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Body;
    private javax.swing.JPanel Header;
    private javax.swing.JPanel List;
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JButton confirmButton;
    private javax.swing.JLabel dateTxt;
    private javax.swing.JLabel employeeNumTxt;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel layoutBody;
    private javax.swing.JPanel logo;
    private javax.swing.JPanel mainBody;
    private javax.swing.JLabel pictureFrame;
    private javax.swing.JLabel priorityTxt;
    private javax.swing.JLabel reportIdTxt;
    private javax.swing.JTable reportList;
    private javax.swing.JLabel signedTxt;
    private javax.swing.JLabel statusTxt;
    private javax.swing.JTextArea textAreaTxt;
    private javax.swing.JPanel title;
    private javax.swing.JLabel typeTxt;
    // End of variables declaration//GEN-END:variables
}
