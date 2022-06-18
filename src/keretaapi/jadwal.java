/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keretaapi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DateFormatter;

/**
 *
 * @author koyan39
 */
public class jadwal extends javax.swing.JFrame {

    Statement st;
    ResultSet rs;
    koneksi.koneksi koneksi;
    Object header[] = {"ID", "ID KERETA","NAMA KERETA","STASIUN AWAL", "STASIUN TUJUAN","KEDATANGAN","KEBERANGKATAN","TARIF"
        };
    DefaultTableModel data = new DefaultTableModel(null, header);
    public jadwal() {
        initComponents();
       
        koneksi = new koneksi.koneksi();
        load_data();
        tampil_kereta();
        jTable1.setModel(data);
        set_jam();
        jTextField1.setEditable(false);
    }
    private void set_jam(){
        SimpleDateFormat now = new SimpleDateFormat("hh:MM");
        Date tm = new Date();
        jFormattedTextField1.setValue(now.format(tm));
    }
    private void tampil_kereta(){
         try{
            String sql = "SELECT * FROM `kereta`";

            st = koneksi.con.createStatement();
            rs = st.executeQuery(sql);
               
           while(rs.next()){
               jComboBox1.addItem(rs.getString("nama_kereta"));
              
            }
           rs.last();
           int jumlahdata = rs.getRow();
           rs.first();
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    private String ambil_kereta(){
        String id_kereta = "1";
    try{
        String sql = "SELECT id_kereta FROM kereta WHERE nama_kereta = '"+jComboBox1.getSelectedItem()+"'";
        rs = st.executeQuery(sql);
        if(rs.next()){
            id_kereta = rs.getString("id_kereta");
        }
    }catch(SQLException e){
            JOptionPane.showMessageDialog(null,e);
        }
    return id_kereta;
    }
    private void IDOtomatis(){
        try {
            st = koneksi.con.createStatement();
            String sql = "SELECT * FROM jadwal order by id_jadwal desc";
            rs = st.executeQuery(sql);
            if (rs.next()) {
                String ID = rs.getString("id_jadwal");
                String NO = "" +(Integer.parseInt(ID) + 1);
                
                jTextField1.setText(NO);
            } else {
                jTextField1.setText("1");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void load_data(){
        data.getDataVector().removeAllElements();
        String sql = "SELECT jadwal.id_jadwal,jadwal.id_kereta,kereta.nama_kereta,jadwal.stasiun_awal,jadwal.stasiun_tujuan,jadwal.kedatangan,jadwal.keberangkatan,jadwal.HARGA_TIKET "
                + "FROM jadwal join kereta ON jadwal.id_kereta = kereta.id_kereta";
        try {
            st = koneksi.con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                String k1 = rs.getString(1);
                String k2 = rs.getString(2);
                String k3 = rs.getString(3);
                String k4 = rs.getString(4);
                String k5 = rs.getString(5);
                String k6 = rs.getString(6);
                String k7 = rs.getString(7);
                String k8 = rs.getString(8);
                

                String k[] = {k1, k2, k3, k4, k5,k6,k7,k8};
                data.addRow(k);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        IDOtomatis();
    }
     

    public void save_data() {
        try {
           String idk = ambil_kereta();
            st = koneksi.con.createStatement();        

            String sql = "INSERT INTO jadwal values('" + jTextField1.getText()
                    + "','" + idk
                    + "','" + jTextField2.getText()
                    + "','" + jTextField3.getText()
                    + "','" + jFormattedTextField1.getText()
                    + "','" + jFormattedTextField2.getText()
                    + "','" + jTextField4.getText()
                    + "')";
            
                st.execute(sql);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void edit_data() {
        try {
            String idk = ambil_kereta();

            st = koneksi.con.createStatement();
            

            String sql_edit = "UPDATE jadwal SET "
                    + "id_kereta = '"+idk + "',"
                    + "stasiun_awal = '" + jTextField2.getText() + "',"                    
                    + "stasiun_tujuan = '" + jTextField3.getText() + "',"
                    + "kedatangan = '" + jFormattedTextField1.getText() + "',"
                    + "keberangkatan = '" + jFormattedTextField2.getText() + "',"
                    + "harga_tiket = '" + jTextField4.getText() + "'"
                    + "WHERE id_jadwal = '" + jTextField1.getText() + "'";
            st.executeUpdate(sql_edit);
            blank();
            JOptionPane.showMessageDialog(null, "Data Berhasil Di Edit!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void hapus_data(){
        try{
            st=koneksi.con.createStatement();
            String sql_delete="Delete FROM jadwal WHERE id_jadwal='"+jTextField1.getText()+"'";
            st.execute(sql_delete);
            blank();
            JOptionPane.showMessageDialog(null, "Data Berhasil Di Hapus!");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void blank() {
//        jTextField1.setText("");
        jTextField2.setText("");
        jComboBox1.setSelectedIndex(0);
        jTextField3.setText("");
        jTextField4.setText("");
        jFormattedTextField1.setText("");
        jFormattedTextField2.setText("");
    }
    public void ambil_data(){
    
    jTextField1.setText(data.getValueAt(jTable1.getSelectedRow(), 0).toString());
    jComboBox1.setSelectedItem(data.getValueAt(jTable1.getSelectedRow(), 2).toString());
        jTextField2.setText(data.getValueAt(jTable1.getSelectedRow(), 3).toString());
        jTextField3.setText(data.getValueAt(jTable1.getSelectedRow(), 4).toString());
       
        
        jTextField4.setText(data.getValueAt(jTable1.getSelectedRow(), 7).toString());
        jFormattedTextField2.setText(data.getValueAt(jTable1.getSelectedRow(), 6).toString());
        jFormattedTextField1.setText(data.getValueAt(jTable1.getSelectedRow(), 5).toString());
        
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
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jTextField4 = new javax.swing.JTextField();
        Bsimpan = new javax.swing.JButton();
        Bedit = new javax.swing.JButton();
        Bhapus = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        Breset = new javax.swing.JButton();

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

        jLabel1.setText("FORM TABEL JADWAL");

        jLabel2.setText("ID JADWAL");

        jLabel3.setText("NAMA KERETA");

        jLabel4.setText("STASIUN TUJUAN");

        jLabel5.setText("STASIUN AWAL");

        jLabel6.setText("KEBERANGKATAN");

        jLabel7.setText("KEDATANGAN");

        jLabel8.setText("TARIF");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "----pilih kereta----" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jFormattedTextField1.setText("00:00");
        jFormattedTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField1ActionPerformed(evt);
            }
        });

        jFormattedTextField2.setText("00:00");

        Bsimpan.setText("simpan");
        Bsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BsimpanActionPerformed(evt);
            }
        });

        Bedit.setText("update");
        Bedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BeditActionPerformed(evt);
            }
        });

        Bhapus.setText("hapus");
        Bhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BhapusActionPerformed(evt);
            }
        });

        jButton4.setText("keluar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        Breset.setText("reset");
        Breset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BresetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel2)
                                .addComponent(jLabel5)
                                .addComponent(jLabel4)
                                .addComponent(jLabel7))
                            .addGap(27, 27, 27)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jComboBox1, 0, 176, Short.MAX_VALUE)
                                .addComponent(jTextField1)
                                .addComponent(jTextField2)
                                .addComponent(jTextField3)
                                .addComponent(jFormattedTextField1)))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel6)
                                .addComponent(jLabel8))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jFormattedTextField2)
                                .addComponent(jTextField4))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Bsimpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Bedit)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Bhapus))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(237, 237, 237)
                                .addComponent(Breset)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(359, 359, 359)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(58, 58, 58)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Bsimpan)
                            .addComponent(Bedit)
                            .addComponent(Bhapus)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(Breset)
                                .addComponent(jButton4)))))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        new menu().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jFormattedTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField1ActionPerformed

    private void BsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BsimpanActionPerformed
        // TODO add your handling code here:
        save_data();
        blank();
        load_data();
        
    }//GEN-LAST:event_BsimpanActionPerformed

    private void BeditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BeditActionPerformed
        // TODO add your handling code here:
         edit_data();
        blank();
        load_data();
    }//GEN-LAST:event_BeditActionPerformed

    private void BhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BhapusActionPerformed
        // TODO add your handling code here:
        hapus_data();
        blank();
        load_data();
    }//GEN-LAST:event_BhapusActionPerformed

    private void BresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BresetActionPerformed
        // TODO add your handling code here:
        blank();
        IDOtomatis();
        Bsimpan.setEnabled(true);
    }//GEN-LAST:event_BresetActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        ambil_data();
        Bsimpan.setEnabled(false);
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
            java.util.logging.Logger.getLogger(jadwal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jadwal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jadwal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jadwal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new jadwal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Bedit;
    private javax.swing.JButton Bhapus;
    private javax.swing.JButton Breset;
    private javax.swing.JButton Bsimpan;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
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
    // End of variables declaration//GEN-END:variables
}
