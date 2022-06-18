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

/**
 *
 * @author koyan39
 */
public class tambahReservasi extends javax.swing.JFrame {

    Statement st;
    ResultSet rs;
    koneksi.koneksi koneksi;
    Object header[] = {"ID", "ID KERETA","NAMA KERETA","STASIUN AWAL", "STASIUN TUJUAN","KEDATANGAN","KEBERANGKATAN","TARIF"
        };
    DefaultTableModel data = new DefaultTableModel(null, header);
    
    String id_gerbong;
    
    public tambahReservasi() {
        initComponents();
        koneksi = new koneksi.koneksi();
        load_data();
        jTable1.setModel(data);
        no_edit();
        set_tgl_skr();
        
    }
    public void no_edit(){
        id_rsv.setEditable(false);
        nama_ker.setEditable(false);
        
    }
    private void set_tgl_skr(){
        Date dNow = new Date();
      tgl_pesan.setDate(dNow);
    }
    private void IDOtomatis(){
        try {
            st = koneksi.con.createStatement();
            String sql = "SELECT * FROM reservasi order by id_reservasi desc";
            rs = st.executeQuery(sql);
            if (rs.next()) {
                String ID = rs.getString("id_reservasi").substring(3);
                String NO = "" +(Integer.parseInt(ID) + 1);
                String Nol = "";
                if (NO.length() == 1) {
                    Nol = "000";
                } else if (NO.length() == 2) {
                    Nol = "00";
                }
                
                id_rsv.setText("RSV" + Nol + NO);
            } else {
                id_rsv.setText("RSV0001");
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
    public String getGerbong(){
       String id ="GB1001";
        try{
        st = koneksi.con.createStatement();
            String sql = "SELECT id_gerbong FROM gerbong WHERE id_kereta = '"+id_ker.getText()+"' AND kelas_gerbong = '"+combo_kelas.getSelectedItem()+"'";
            rs = st.executeQuery(sql);
             if (rs.next()) {
                 id = rs.getString("id_gerbong");
             }
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return id;
    }
    public void save_data(){
        try {
            String id_gerbong = getGerbong();

            st = koneksi.con.createStatement();
            String sql = "INSERT INTO reservasi values('" + id_rsv.getText()
                    + "','" + id_pen.getText()
                    + "','" + id_jad.getText()
                    + "','" + id_gerbong
                    + "','" + new SimpleDateFormat("yyyy-MM-dd").format(tgl_pesan.getDate())
                    + "','" + new SimpleDateFormat("yyyy-MM-dd").format(tgl_berangkat.getDate())
                    
                   
                    + "')";
            
                st.execute(sql);
                
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public void hapus_data(String ID){
        try{
            st=koneksi.con.createStatement();
            String sql_delete="Delete FROM reservasi WHERE id_reservasi='"+ID+"'";
            st.execute(sql_delete);
            JOptionPane.showMessageDialog(null, "data berhasil dihapus");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        load_data();
        
    }
    public void ambil_data(){
        String nama_jur = data.getValueAt(jTable1.getSelectedRow(), 3).toString()+"-"+data.getValueAt(jTable1.getSelectedRow(), 4).toString();
    
        id_jad.setText(data.getValueAt(jTable1.getSelectedRow(), 0).toString());
        id_ker.setText(data.getValueAt(jTable1.getSelectedRow(), 1).toString());
        nama_ker.setText(data.getValueAt(jTable1.getSelectedRow(), 2).toString());
        jurusan.setText(nama_jur);
        keberangkatan.setText(data.getValueAt(jTable1.getSelectedRow(), 6).toString());
              
    }
    public void cari_penumpang_by_name(){
        String nama = nama_pen.getText();
         try{
            String sql = "SELECT id_penumpang FROM `penumpang` WHERE nama_penumpang = '" + nama+"'";

            st = koneksi.con.createStatement();
            rs = st.executeQuery(sql);
               
           if(rs.next()){
               id_pen.setText(rs.getString("id_penumpang"));
            }
           
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    public void cari_penumpang_by_id(){
        String id = id_pen.getText();
         try{
            String sql = "SELECT nama_penumpang FROM `penumpang` WHERE id_penumpang = '" + id+"'";

            st = koneksi.con.createStatement();
            rs = st.executeQuery(sql);
               
           if(rs.next()){
               nama_pen.setText(rs.getString("nama_penumpang"));
            }
           
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    public void blank(){
        id_rsv.setText(null);
        id_jad.setText(null);
        id_ker.setText(null);
        id_pen.setText(null);
        nama_pen.setText(null);
        tgl_berangkat.setDate(null);
        nama_ker.setText(null);
        keberangkatan.setText(null);
        jurusan.setText(null);
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
        tgl_berangkat = new com.toedter.calendar.JDateChooser();
        tgl_pesan = new com.toedter.calendar.JDateChooser();
        tanggal_pesan = new javax.swing.JLabel();
        tanggal_pesan1 = new javax.swing.JLabel();
        combo_kelas = new javax.swing.JComboBox();
        nama_ker = new javax.swing.JTextField();
        keberangkatan = new javax.swing.JTextField();
        jurusan = new javax.swing.JTextField();
        nama_pen = new javax.swing.JTextField();
        id_rsv = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        id_jad = new javax.swing.JTextField();
        id_pen = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        id_ker = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        Bsimpan = new javax.swing.JButton();
        Bedit = new javax.swing.JButton();
        Bhapus = new javax.swing.JButton();
        Breset = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

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

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setText("form reservasi");

        jLabel2.setText("ID RESERVASI");

        jLabel3.setText("ID JADWAL");

        jLabel4.setText("JURUSAN");

        jLabel5.setText("KEBERANGKATAN");

        jLabel6.setText("NAMA KERETA");

        jLabel7.setText("KELAS");

        tgl_berangkat.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tgl_berangkatPropertyChange(evt);
            }
        });

        tgl_pesan.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tgl_pesanPropertyChange(evt);
            }
        });

        tanggal_pesan.setText("Tanggal Berangkat");

        tanggal_pesan1.setText("Tanggal Pesan");

        combo_kelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "---PILIH KELAS---", "EKSEKUTIF", "BISNIS", "EKONOMI" }));

        id_rsv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                id_rsvActionPerformed(evt);
            }
        });

        jLabel9.setText("ID PENUMPANG");

        jButton1.setText("CARI");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel10.setText("NAMA PENUMPANG");

        jLabel8.setText("ID KERETA");

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

        Breset.setText("reset");
        Breset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BresetActionPerformed(evt);
            }
        });

        jButton4.setText("keluar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(tanggal_pesan1)
                            .addComponent(tanggal_pesan)
                            .addComponent(jLabel8))
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(keberangkatan, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                                .addComponent(jurusan, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                                .addComponent(id_rsv, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                                .addComponent(id_jad, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                                .addComponent(tgl_berangkat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tgl_pesan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(combo_kelas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(nama_ker)
                                .addComponent(id_ker, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                                .addComponent(nama_pen))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(id_pen, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))))
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
                .addGap(100, 100, 100)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1272, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(440, 440, 440)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(id_rsv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(id_pen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nama_pen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(id_jad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jurusan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(keberangkatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(id_ker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(nama_ker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(combo_kelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tgl_pesan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tanggal_pesan1))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tanggal_pesan)
                            .addComponent(tgl_berangkat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Bsimpan)
                            .addComponent(Bedit)
                            .addComponent(Bhapus)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(Breset)
                                .addComponent(jButton4)))))
                .addGap(75, 75, 75))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tgl_berangkatPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tgl_berangkatPropertyChange
//        if (tgl_berangkat.getDate() != null) {
//            SimpleDateFormat FormatTanggal = new SimpleDateFormat("dd MMMM yyyy");
//            pinjam = FormatTanggal.format(tgl_berangkat.getDate());
//        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tgl_berangkatPropertyChange

    private void tgl_pesanPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tgl_pesanPropertyChange
//        if (tgl_berangkat.getDate() != null) {
//            SimpleDateFormat FormatTanggal = new SimpleDateFormat("dd MMMM yyyy");
//            pinjam = FormatTanggal.format(tgl_berangkat.getDate());
//        }
        // TODO add your handling code here:
    }//GEN-LAST:event_tgl_pesanPropertyChange

    private void id_rsvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_id_rsvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_id_rsvActionPerformed

    private void BsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BsimpanActionPerformed
        // TODO add your handling code here:
//        save_data();
        int opsi = JOptionPane.showOptionDialog(null, "Apakah Data Sudah Benar? LANJUTKAN?",
                "Simpan Data", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (opsi == JOptionPane.YES_OPTION) {
            save_data();
                        transaksi t = new transaksi();
            t.setRsv(id_rsv.getText());
            t.setVisible(true);

        }
    }//GEN-LAST:event_BsimpanActionPerformed

    private void BeditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BeditActionPerformed
        // TODO add your handling code here:
//        edit_data();
//        blank();
//        load_data();
    }//GEN-LAST:event_BeditActionPerformed

    private void BhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BhapusActionPerformed
        // TODO add your handling code here:
//        hapus_data();
//        blank();
//        load_data();
    }//GEN-LAST:event_BhapusActionPerformed

    private void BresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BresetActionPerformed
        // TODO add your handling code here:
        blank();
        IDOtomatis();
        Bsimpan.setEnabled(true);
    }//GEN-LAST:event_BresetActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        ambil_data();
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        cari_penumpang_by_id();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(tambahReservasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(tambahReservasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(tambahReservasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(tambahReservasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new tambahReservasi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Bedit;
    private javax.swing.JButton Bhapus;
    private javax.swing.JButton Breset;
    private javax.swing.JButton Bsimpan;
    private javax.swing.JComboBox combo_kelas;
    private javax.swing.JTextField id_jad;
    private javax.swing.JTextField id_ker;
    private javax.swing.JTextField id_pen;
    private javax.swing.JTextField id_rsv;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jurusan;
    private javax.swing.JTextField keberangkatan;
    private javax.swing.JTextField nama_ker;
    private javax.swing.JTextField nama_pen;
    private javax.swing.JLabel tanggal_pesan;
    private javax.swing.JLabel tanggal_pesan1;
    private com.toedter.calendar.JDateChooser tgl_berangkat;
    private com.toedter.calendar.JDateChooser tgl_pesan;
    // End of variables declaration//GEN-END:variables
}
