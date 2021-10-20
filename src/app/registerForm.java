package app;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.*;
import java.util.regex.Pattern;
import javax.naming.spi.DirStateFactory.Result;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 *
 * @author Earl Appiah - eea7
 */
public class registerForm extends javax.swing.JFrame {

    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    //Array of componenets
    Component[] comps;
    HashMap<Component, String> map = new HashMap<>();

    /**
     * Creates new form registerForm
     */
    public registerForm() {
        initComponents();
        Connect();

        // populate the comps
        comps = jPanelRegister.getComponents();

        //FOr loop for components hashmap
        for (Component comp : comps) {

            if (comp instanceof JTextField) {
                JTextField textField = (JTextField) comp;
                map.put(textField, textField.getText());
            } else if (comp instanceof JPasswordField) {
                JPasswordField passField = (JPasswordField) comp;
                map.put(passField, String.valueOf(passField.getPassword()));
            }

        }

        addFocusEvent();
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
    // function to generate a random string of length n 

    static String getAlphaNumericString(int n) {

        // chose a Character random from this String 
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString 
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between 
            // 0 to AlphaNumericString variable length 
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb 
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    public void addFocusEvent() {
        for (Component comp : comps) {

            if (comp instanceof JTextField) {
                JTextField textField = (JTextField) comp;

                textField.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {

                        focusIn(textField, null, map.get(textField));

                    }

                    @Override
                    public void focusLost(FocusEvent e) {

                        focusOut(textField, null, map.get(textField));

                    }
                });
            } else if (comp instanceof JPasswordField) {
                JPasswordField passField = (JPasswordField) comp;

                passField.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {

                        focusIn(passField, null, map.get(passField));

                    }

                    @Override
                    public void focusLost(FocusEvent e) {

                        focusOut(passField, null, map.get(passField));

                    }
                });
            }

        }
    }

    // create a function for the focus in
    public void focusIn(JTextField textField, JPasswordField passField, String originalText) {
        // for the jpassword
        if (passField != null) {
            String passValue = String.valueOf(passField.getPassword());

            if (passValue.equals(originalText)) {
                passField.setText("");
                passField.setForeground(Color.black);
            }

        } // for the jtextfield
        else {
            String textFieldValue = textField.getText().trim().toLowerCase();

            if (textFieldValue.equals(originalText)) {
                textField.setText("");
                textField.setForeground(Color.black);
            }
        }

    }

    // create a function for the focus out
    public void focusOut(JTextField textField, JPasswordField passField, String originalText) {
        // for the jpassword
        if (passField != null) {
            String passValue = String.valueOf(passField.getPassword());

            if (passValue.equals(originalText) || passValue.equals("")) {
                passField.setText(originalText);
                passField.setForeground(new Color(153, 153, 153));
            }

        } // for the jtextfield
        else {
            String textFieldValue = textField.getText().trim().toLowerCase();

            if (textFieldValue.equals(originalText) || textFieldValue.equals("")) {
                textField.setText(originalText);
                textField.setForeground(new Color(153, 153, 153));
            }
        }
    }

    public boolean verifyFields() {
        String uname = txtRegUsername.getText();
        String fname = txtName.getText();
        String eplynumber = txtEmployeeNum.getText();
        String mail = txtEmail.getText();
        String pnumber = txtNumber.getText();
        String pass1 = String.valueOf(txtPass.getPassword());
        String pass2 = String.valueOf(txtPassConfirm.getPassword());

        if (uname.equals("enter here...") || fname.equals("enter here...") || eplynumber.equals("enter here...")
                || mail.equals("enter here...") || pnumber.equals("enter here...") || pass1.equals("password") || pass2.equals("password")) {
            JOptionPane.showMessageDialog(null, "One or more fields are empty, check again!", "Empty fields", 2);
            return false;

        } else if (!(pnumber.length() == 11)) {
            JOptionPane.showMessageDialog(null, "Phone number is not valid, please change", "Invalid entry", 2);
            return false;
        } else if (!(eplynumber.length() == 4)) {
            JOptionPane.showMessageDialog(null, "Employee number is not valid, please change", "Invalid entry", 2);
            return false;

        } else if (!(Pattern.matches("^[a-zA-Z0-9]+[@]{1}+[a-zA-Z0-9]+[.]{1}+[a-zA-Z0-9]+$", mail)
                || Pattern.matches("^[a-zA-Z0-9]+[@]{1}+[a-zA-Z0-9]+[.]{1}+[a-zA-Z0-9]+[.]{1}+[a-zA-Z0-9]+$", mail))) {
            JOptionPane.showMessageDialog(null, "Email is not valid, please change", "Invalid entry", 2);
            return false;

        } else if (!(pass1.equals(pass2))) {
            JOptionPane.showMessageDialog(null, "Passwords do not match", "Confirm password", 2);
            return false;
        } else {
            return true;
        }
    }

    public boolean checkUsername(String username) {
        PreparedStatement st;
        ResultSet rs;
        boolean username_exist = false;

        String query = "SELECT * FROM details WHERE username=? ";

        try {
            st = con.prepareStatement(query);
            st.setString(1, username);
            rs = st.executeQuery();

            if (rs.next()) {
                username_exist = true;
                JOptionPane.showMessageDialog(null, "This username exist already", "Please Choose another", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(registerForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return username_exist;
    }
    public boolean checkEmail(String email) {
        PreparedStatement st;
        ResultSet rs;
        boolean email_exist = false;

        String query = "SELECT * FROM details WHERE email=? ";

        try {
            st = con.prepareStatement(query);
            st.setString(1, email);
            rs = st.executeQuery();

            if (rs.next()) {
                email_exist = true;
                JOptionPane.showMessageDialog(null, "This email is already associated with an account", "Please Choose another", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(registerForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return email_exist;
    }
    public boolean checkEnum(String enumber) {
        PreparedStatement st;
        ResultSet rs;
        boolean enum_exist = false;

        String query = "SELECT * FROM details WHERE enumber=? ";

        try {
            st = con.prepareStatement(query);
            st.setString(1, enumber);
            rs = st.executeQuery();

            if (rs.next()) {
                enum_exist = true;
                JOptionPane.showMessageDialog(null, "This employee number already has an account, contact admin", "error", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(registerForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return enum_exist;
    }

    public boolean checkID(String str) {
        PreparedStatement st;
        ResultSet rs;
        boolean id_exist = false;

        String query = "SELECT * FROM details WHERE id=? ";

        try {
            st = con.prepareStatement(query);
            st.setString(1, str);
            rs = st.executeQuery();

            if (rs.next()) {
                id_exist = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(registerForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id_exist;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JPanel();
        header = new javax.swing.JPanel();
        notesIcon = new javax.swing.JLabel();
        title = new javax.swing.JLabel();
        jPanelRegister = new javax.swing.JPanel();
        jLabelUsername2 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabelUsername3 = new javax.swing.JLabel();
        jLabelUsername4 = new javax.swing.JLabel();
        jLabelUsername1 = new javax.swing.JLabel();
        txtNumber = new javax.swing.JTextField();
        txtRegUsername = new javax.swing.JTextField();
        jLabelUsername5 = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        txtPassConfirm = new javax.swing.JPasswordField();
        confirmBut = new javax.swing.JButton();
        jLabelUsername6 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabelUsername7 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtEmployeeNum = new javax.swing.JTextField();
        cancelBut = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        background.setBackground(new java.awt.Color(204, 204, 204));

        header.setBackground(new java.awt.Color(51, 51, 51));
        header.setPreferredSize(new java.awt.Dimension(550, 77));

        notesIcon.setBackground(new java.awt.Color(0, 51, 255));
        notesIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        notesIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/notes.png"))); // NOI18N

        title.setBackground(new java.awt.Color(0, 51, 255));
        title.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        title.setForeground(new java.awt.Color(255, 255, 255));
        title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title.setText("REGISTER FORM");

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addComponent(notesIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(74, Short.MAX_VALUE))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(title, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(notesIcon, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabelUsername2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelUsername2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelUsername2.setText("Full-Name:*");
        jLabelUsername2.setToolTipText("");

        txtEmail.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtEmail.setForeground(new java.awt.Color(153, 153, 153));
        txtEmail.setText("enter here...");
        txtEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEmailFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEmailFocusLost(evt);
            }
        });
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        jLabelUsername3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelUsername3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelUsername3.setText("Email:*");
        jLabelUsername3.setToolTipText("");

        jLabelUsername4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelUsername4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelUsername4.setText("Employ Num:*");
        jLabelUsername4.setToolTipText("");

        jLabelUsername1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelUsername1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelUsername1.setText("Username:*");
        jLabelUsername1.setToolTipText("");

        txtNumber.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtNumber.setForeground(new java.awt.Color(153, 153, 153));
        txtNumber.setText("enter here...");
        txtNumber.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumberFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumberFocusLost(evt);
            }
        });
        txtNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumberActionPerformed(evt);
            }
        });
        txtNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumberKeyTyped(evt);
            }
        });

        txtRegUsername.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtRegUsername.setForeground(new java.awt.Color(153, 153, 153));
        txtRegUsername.setText("enter here...");
        txtRegUsername.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtRegUsernameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtRegUsernameFocusLost(evt);
            }
        });
        txtRegUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRegUsernameActionPerformed(evt);
            }
        });

        jLabelUsername5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelUsername5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelUsername5.setText("Number:*");
        jLabelUsername5.setToolTipText("");

        txtPass.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtPass.setForeground(new java.awt.Color(153, 153, 153));
        txtPass.setText("password");
        txtPass.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPassFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPassFocusLost(evt);
            }
        });
        txtPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPassActionPerformed(evt);
            }
        });

        txtPassConfirm.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtPassConfirm.setForeground(new java.awt.Color(153, 153, 153));
        txtPassConfirm.setText("wordpass");
        txtPassConfirm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPassConfirmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPassConfirmFocusLost(evt);
            }
        });
        txtPassConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPassConfirmActionPerformed(evt);
            }
        });

        confirmBut.setBackground(new java.awt.Color(255, 204, 0));
        confirmBut.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        confirmBut.setText("CONFIRM");
        confirmBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmButActionPerformed(evt);
            }
        });

        jLabelUsername6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelUsername6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelUsername6.setText("Password:*");
        jLabelUsername6.setToolTipText("");

        jLabel12.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(153, 153, 153));
        jLabel12.setText("*sign means esstential so, please fill in your information");

        jLabelUsername7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelUsername7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelUsername7.setText("Confirm:*");
        jLabelUsername7.setToolTipText("");

        txtName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtName.setForeground(new java.awt.Color(153, 153, 153));
        txtName.setText("enter here...");
        txtName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNameFocusLost(evt);
            }
        });
        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });

        txtEmployeeNum.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtEmployeeNum.setForeground(new java.awt.Color(153, 153, 153));
        txtEmployeeNum.setText("enter here...");
        txtEmployeeNum.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEmployeeNumFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEmployeeNumFocusLost(evt);
            }
        });
        txtEmployeeNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmployeeNumActionPerformed(evt);
            }
        });
        txtEmployeeNum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEmployeeNumKeyTyped(evt);
            }
        });

        cancelBut.setBackground(new java.awt.Color(255, 0, 0));
        cancelBut.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        cancelBut.setText("CANCEL");
        cancelBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelRegisterLayout = new javax.swing.GroupLayout(jPanelRegister);
        jPanelRegister.setLayout(jPanelRegisterLayout);
        jPanelRegisterLayout.setHorizontalGroup(
            jPanelRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRegisterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRegisterLayout.createSequentialGroup()
                        .addGroup(jPanelRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanelRegisterLayout.createSequentialGroup()
                                .addGroup(jPanelRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelUsername5, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelUsername6, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelUsername7, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addGroup(jPanelRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPass, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtNumber)
                                    .addComponent(txtPassConfirm)))
                            .addGroup(jPanelRegisterLayout.createSequentialGroup()
                                .addGroup(jPanelRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelUsername1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelUsername2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelUsername3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addGroup(jPanelRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtName, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtEmail)
                                    .addComponent(txtRegUsername)))
                            .addGroup(jPanelRegisterLayout.createSequentialGroup()
                                .addGap(0, 20, Short.MAX_VALUE)
                                .addComponent(cancelBut, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(confirmBut, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(26, 26, 26))
                    .addGroup(jPanelRegisterLayout.createSequentialGroup()
                        .addComponent(jLabelUsername4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtEmployeeNum)
                        .addGap(28, 28, 28))
                    .addGroup(jPanelRegisterLayout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanelRegisterLayout.setVerticalGroup(
            jPanelRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRegisterLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRegUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelUsername1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelUsername2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelUsername3))
                .addGap(14, 14, 14)
                .addGroup(jPanelRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelUsername4)
                    .addComponent(txtEmployeeNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelUsername5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelUsername6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPassConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelUsername7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanelRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirmBut, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelBut, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelRegister, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelRegister, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtEmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailFocusGained

    private void txtEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailFocusLost

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtNumberFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumberFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumberFocusGained

    private void txtNumberFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumberFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumberFocusLost

    private void txtNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumberActionPerformed

    private void txtRegUsernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRegUsernameFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRegUsernameFocusGained

    private void txtRegUsernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRegUsernameFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRegUsernameFocusLost

    private void txtRegUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRegUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRegUsernameActionPerformed

    private void txtPassFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPassFocusGained

    private void txtPassFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPassFocusLost

    private void txtPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPassActionPerformed

    private void txtPassConfirmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassConfirmFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPassConfirmFocusGained

    private void txtPassConfirmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassConfirmFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPassConfirmFocusLost

    private void txtPassConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPassConfirmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPassConfirmActionPerformed

    private void confirmButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmButActionPerformed
        String newID = getAlphaNumericString(10);
        String Usname = txtRegUsername.getText();
        String fname = txtName.getText();
        String email = txtEmail.getText();
        String employeeNum = txtEmployeeNum.getText();
        String num = txtNumber.getText();
        String pass = String.valueOf(txtPass.getPassword());
        String pass2 = String.valueOf(txtPassConfirm.getPassword());

        while (checkID(newID)) {
            newID = getAlphaNumericString(10);
        }
        
        if (verifyFields() && !checkUsername(Usname) && !checkEmail(email) && !checkEnum(employeeNum)) {

            PreparedStatement pst;
            String query = "INSERT INTO `details`(`id` ,`username`, `fullname`, `email`, `enumber`, `number`, `password`) VALUES (?,?,?,?,?,?,?)";

            try {
                pst = con.prepareStatement(query);
                pst.setString(1, newID);
                pst.setString(2, Usname);
                pst.setString(3, fname);
                pst.setString(4, email);
                pst.setString(5, employeeNum);
                pst.setString(6, num);
                pst.setString(7, pass);

                if (pst.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "New User Added");
                    login a = new login();
                    a.setVisible(true);
                    this.dispose();
                }
            } catch (SQLException ex) {
                Logger.getLogger(registerForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_confirmButActionPerformed

    private void txtNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNameFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameFocusGained

    private void txtNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNameFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameFocusLost

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void txtEmployeeNumFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmployeeNumFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmployeeNumFocusGained

    private void txtEmployeeNumFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmployeeNumFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmployeeNumFocusLost

    private void txtEmployeeNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmployeeNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmployeeNumActionPerformed

    private void cancelButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButActionPerformed
        login a = new login();
        a.setVisible(true);
        this.dispose();
        
    }//GEN-LAST:event_cancelButActionPerformed

    private void txtNumberKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumberKeyTyped
        //Only allow numbers
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNumberKeyTyped

    private void txtEmployeeNumKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmployeeNumKeyTyped
        //Only allow numbers
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_txtEmployeeNumKeyTyped

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
            java.util.logging.Logger.getLogger(registerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(registerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(registerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(registerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new registerForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel background;
    private javax.swing.JButton cancelBut;
    private javax.swing.JButton confirmBut;
    private javax.swing.JPanel header;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabelUsername1;
    private javax.swing.JLabel jLabelUsername2;
    private javax.swing.JLabel jLabelUsername3;
    private javax.swing.JLabel jLabelUsername4;
    private javax.swing.JLabel jLabelUsername5;
    private javax.swing.JLabel jLabelUsername6;
    private javax.swing.JLabel jLabelUsername7;
    private javax.swing.JPanel jPanelRegister;
    private javax.swing.JLabel notesIcon;
    private javax.swing.JLabel title;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEmployeeNum;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNumber;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JPasswordField txtPassConfirm;
    private javax.swing.JTextField txtRegUsername;
    // End of variables declaration//GEN-END:variables
}
