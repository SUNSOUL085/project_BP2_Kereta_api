/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keretaapi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author koyan39
 */
public class kereta extends javax.swing.JFrame {

    Statement st;
    ResultSet rs;
    koneksi.koneksi koneksi;
    Object header[] = {"ID_KERETA ", "NAMA_KERETA","JUMLAH GERBONG","KAPASITAS"
        };
    DefaultTableModel data = new DefaultTableModel(null, header);
    int jumlah_gerbong;
    public kereta() {
        initComponents();
        koneksi = new koneksi.koneksi();
        load_data();
        id_kereta_oto();
        jTable1.setModel(data);
        disableGerbong(false);
        disableKereta(true);
    }
    private void gBlank(){
        jTextField4.setText(null);
        jTextField5.setText(null);
        jTextField6.setText(null);
       
    }
    private void kBlank(){
         jTextField2.setText(null);
        jTextField3.setText(null);
    }
    private void disableGerbong(boolean b){
        jTextField4.setEnabled(b);
        jTextField5.setEnabled(b);
        jTextField6.setEnabled(b);
        jButton1.setEnabled(b);
    }
    private void disableKereta(boolean b){
        jTextField1.setEditable(b);
        jTextField2.setEnabled(b);
        jTextField3.setEnabled(b);
        Bsimpan.setEnabled(b);
        Bhapus.setEnabled(b);
        Bedit.setEnabled(b);
        Breset.setEnabled(b);
        Bout.setEnabled(b);
    }
    public void id_gerbong_oto(){
        try {
            st = koneksi.con.createStatement();
            String sql = "SELECT * FROM gerbong order by id_gerbong desc";
            rs = st.executeQuery(sql);
            if (rs.next()) {
                String ID = rs.getString("id_gerbong").substring(6);
                
                String NO = "" +(Integer.parseInt(ID) + 1);
                String Nol = "";
                if (NO.length() == 1) {
                    Nol = "000";
                } else if (NO.length() == 2) {
                    Nol = "00";
                }
                jTextField4.setText("GB" + Nol + NO );
            } else {
                jTextField4.setText("GB0001" );
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public void id_kereta_oto(){
        try {
            st = koneksi.con.createStatement();
            String sql = "SELECT * FROM kereta order by id_kereta desc";
            rs = st.executeQuery(sql);
            if (rs.next()) {
                String ID = rs.getString("id_kereta").substring(3);
                String NO = "" +(Integer.parseInt(ID) + 1);
                String Nol = "";
                if (NO.length() == 1) {
                    Nol = "000";
                } else if (NO.length() == 2) {
                    Nol = "00";
                }
                
                jTextField1.setText("KAI" + Nol + NO);
            } else {
                jTextField1.setText("KAI0001");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public void blank(){
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
    }
    
    private void load_data(){
        data.getDataVector().removeAllElements();
        String sql = "SELECT *FROM kereta";
        try {
            st = koneksi.con.createStatement();
            rs = st.executeQuery(sql);
            String k4 = "0";
            while (rs.next()) {
                String k1 = rs.getString(1);
                String k2 = rs.getString(2);
                String k3 = rs.getString(3);
                
                String sql2 = "SELECT sum(kapasitas) FROM gerbong Where id_kereta = '"+k1+"'";
                st = koneksi.con.createStatement();
                ResultSet c = st.executeQuery(sql2);
                if(c.next()){
                    k4 = c.getString(1);
                }
                

                String k[] = {k1, k2, k3,k4};
                data.addRow(k);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
    private void save_data(){
        try {
            st = koneksi.con.createStatement();        

            String sql = "INSERT INTO kereta values('" + jTextField1.getText()
                    + "','" + jTextField2.getText()
                    + "','" + jTextField3.getText()  
                    + "')";
            
                st.execute(sql);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void save_data_gerbong(){
        try {
            st = koneksi.con.createStatement();        

            String sql = "INSERT INTO gerbong values('" + jTextField4.getText()
                    + "','" + combo_kelas.getSelectedItem()
                    + "','" + jTextField5.getText()
                    + "','" + jTextField6.getText()  
                    + "','" + jTextField1.getText()  
                    + "')";
            
                st.execute(sql);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void edit_data(){
        try {

            st = koneksi.con.createStatement();
            

            String sql_edit = "UPDATE kereta SET "
                    
                    + "nama_kereta = '" + jTextField2.getText() + "',"                    
                    + "gerbong = '" + jTextField3.getText() + "',"
                    + "WHERE id_kereta = '" + jTextField1.getText() + "'";
            st.executeUpdate(sql_edit);
            JOptionPane.showMessageDialog(null, "Data Berhasil Di Edit!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void hapus_data(){
        try{
            st=koneksi.con.createStatement();
            String sql_delete="Delete FROM kereta WHERE id_kereta='"+jTextField1.getText()+"'";
            st.execute(sql_delete);
            blank();
            JOptionPane.showMessageDialog(null, "Data Berhasil Di Hapus!");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void ambil_data(){
        jTextField1.setText(data.getValueAt(jTable1.getSelectedRow(), 0).toString());
        jTextField2.setText(data.getValueAt(jTable1.getSelectedRow(), 1).toString());
        jTextField3.setText(data.getValueAt(jTable1.getSelectedRow(), 2).toString());
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        Breset = new javax.swing.JButton();
        Bout = new javax.swing.JButton();
        Bhapus = new javax.swing.JButton();
        Bedit = new javax.swing.JButton();
        Bsimpan = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        combo_kelas = new javax.swing.JComboBox();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Tabel Kereta");

        jLabel2.setText("Id_kereta");

        jLabel3.setText("Nama_kereta");

        jLabel4.setText("Jumlah gerbong");

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        Breset.setText("reset");
        Breset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BresetActionPerformed(evt);
            }
        });

        Bout.setText("keluar");
        Bout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoutActionPerformed(evt);
            }
        });

        Bhapus.setText("hapus");
        Bhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BhapusActionPerformed(evt);
            }
        });

        Bedit.setText("update");
        Bedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BeditActionPerformed(evt);
            }
        });

        Bsimpan.setText("simpan");
        Bsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BsimpanActionPerformed(evt);
            }
        });

        jLabel5.setText("Id gerbong");

        jLabel6.setText("Kelas Gerbong");

        jLabel7.setText("kapasitas");

        jLabel8.setText("harga++");

        combo_kelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "---PILIH KELAS---", "EKSEKUTIF", "BISNIS", "EKONOMI" }));

        jButton1.setText("masuk");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel8))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(Bsimpan)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Bedit)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Bhapus))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(237, 237, 237)
                                        .addComponent(Breset)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Bout))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel2)
                                                .addComponent(jLabel3))
                                            .addGap(27, 27, 27)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jTextField2)
                                                .addComponent(jTextField1)))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel4)
                                                .addComponent(jLabel5))
                                            .addGap(18, 18, 18)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                                                .addComponent(jTextField4)
                                                .addComponent(combo_kelas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jTextField5)
                                                .addComponent(jTextField6, javax.swing.GroupLayout.Alignment.TRAILING))))
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addComponent(jButton1)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
            .addGroup(layout.createSequentialGroup()
                .addGap(437, 437, 437)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(combo_kelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Bsimpan)
                            .addComponent(Bedit)
                            .addComponent(Bhapus)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(Breset)
                                .addComponent(Bout)))
                        .addGap(19, 19, 19))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void BresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BresetActionPerformed
        // TODO add your handling code here:
        blank();
        id_kereta_oto();
        Bsimpan.setEnabled(true);
    }//GEN-LAST:event_BresetActionPerformed

    private void BoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoutActionPerformed
        // TODO add your handling code here:
        new menu().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BoutActionPerformed

    private void BhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BhapusActionPerformed
        // TODO add your handling code here:
        hapus_data();
        blank();
        load_data();
        id_kereta_oto();
    }//GEN-LAST:event_BhapusActionPerformed

    private void BeditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BeditActionPerformed
        // TODO add your handling code here:
        edit_data();
        blank();
        load_data();
        id_kereta_oto();
    }//GEN-LAST:event_BeditActionPerformed

    private void BsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BsimpanActionPerformed
        // TODO add your handling code here:
        int opsi = JOptionPane.showOptionDialog(null, "Apakah Data Sudah Benar? LANJUTKAN?",
                "Simpan Data", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (opsi == JOptionPane.YES_OPTION) {
            save_data();
           JOptionPane.showMessageDialog(null, "Jangan lupa tambahkan data gerbongnya gerbongnya");
            disableGerbong(true);
            disableKereta(false);
            id_gerbong_oto();
        }
        
        load_data();

    }//GEN-LAST:event_BsimpanActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        jumlah_gerbong = Integer.parseInt(jTextField3.getText());
       if(jumlah_gerbong<2){
            disableGerbong(false);
            disableKereta(true); 
            gBlank();
            kBlank();
            id_kereta_oto();
         }else {
           save_data_gerbong();
           jumlah_gerbong--;
            jTextField3.setText(String.valueOf(jumlah_gerbong));
            load_data();
            id_gerbong_oto();
       }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        ambil_data();
    }//GEN-LAST:event_jTable1MouseClicked

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
            java.util.logging.Logger.getLogger(kereta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(kereta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(kereta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(kereta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new kereta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Bedit;
    private javax.swing.JButton Bhapus;
    private javax.swing.JButton Bout;
    private javax.swing.JButton Breset;
    private javax.swing.JButton Bsimpan;
    private javax.swing.JComboBox combo_kelas;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
