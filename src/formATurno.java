
import java.awt.Color;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class formATurno extends javax.swing.JFrame {

    private Statement sentencia;
    private Connection conexion;
    private String msj;

    private String nomBD = "bussandwicheria";
    private String usuario = "root";
    private String password = "informatica";

    public formATurno() {
        initComponents();
        this.setLocationRelativeTo(null);
        setIcon();
        conectar();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        txtFecha.setText(dtf.format(localDate));
        this.getContentPane().setBackground(Color.orange);
    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagen/icono.png")));
    }

    /*
    private String nomBD="bussandwicheria";
    private String usuario="root";
    private String password="informatica";  */
    formATurno(String text, String text0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/" + this.nomBD;

            //String url="jdbc:mysql://tupuedes.ml/"+this.nomBD;  //esto nope            
            //String url="jdbc:mysql://192.168.238:3306/"+this.nomBD;            
            this.conexion = (Connection) DriverManager.getConnection(url, this.usuario, this.password);
            this.sentencia = (Statement) this.conexion.createStatement();
        } catch (Exception e) {
            msj = "error al conectar";
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        btnRegresar = new javax.swing.JButton();
        lblFecha = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMontoCajaI = new javax.swing.JTextField();
        rbtnDiurno = new javax.swing.JRadioButton();
        rbtnVespertino = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtTotalEfectivo = new javax.swing.JTextField();
        txtTotalTarjeta = new javax.swing.JTextField();
        txtTotalCredito = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtTotalVenta = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtMontoCajaF = new javax.swing.JTextField();
        brnCuadrar = new javax.swing.JButton();
        txtFecha = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Arqueo de Caja");

        btnRegresar.setText("Atras");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        lblFecha.setText("Fecha");

        jLabel1.setText("Arqueo Caja");

        jLabel2.setText("Turno:");

        jLabel3.setText("Monto Apertura de Caja:");

        txtMontoCajaI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoCajaIActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtnDiurno);
        rbtnDiurno.setText("Diurno");
        rbtnDiurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnDiurnoActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtnVespertino);
        rbtnVespertino.setText("Vespertino");
        rbtnVespertino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnVespertinoActionPerformed(evt);
            }
        });

        jLabel4.setText("Ventas");

        jLabel5.setText("Total ventas en efectivo:");

        jLabel6.setText("Total ventas via tarjeta:");

        jLabel7.setText("Total ventas a crédito:");

        txtTotalEfectivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalEfectivoActionPerformed(evt);
            }
        });

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel8.setText("Cierre Caja");

        jLabel9.setText("Total ventas:");

        jLabel10.setText("Monto total en caja:");

        brnCuadrar.setText("Cuadrar Caja");
        brnCuadrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brnCuadrarActionPerformed(evt);
            }
        });

        txtFecha.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblFecha)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(92, 92, 92)))
                        .addComponent(btnRegresar))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rbtnDiurno)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rbtnVespertino))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMontoCajaI, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTotalCredito, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                                    .addComponent(txtTotalTarjeta, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                                    .addComponent(txtTotalEfectivo))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(brnCuadrar, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(txtMontoCajaF, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel9)
                                        .addComponent(jLabel10)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(10, 10, 10)
                                            .addComponent(txtTotalVenta))))))
                        .addContainerGap(40, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRegresar)
                            .addComponent(lblFecha)
                            .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 31, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(rbtnDiurno)
                            .addComponent(rbtnVespertino))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtMontoCajaI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtTotalEfectivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtTotalTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtTotalCredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel9)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtTotalVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel10)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtMontoCajaF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(brnCuadrar))
                        .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 38, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTotalEfectivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalEfectivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalEfectivoActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        //new fLogin().setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void rbtnDiurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnDiurnoActionPerformed
        int efec = CalcularTarjeta("D");
        int tarje = CalcularEfectivo("D");
        int cred = CalcularCredito("D");

        String efecTot = String.valueOf(efec);
        txtTotalEfectivo.setText(efecTot);

        String tarjeTot = String.valueOf(tarje);
        txtTotalTarjeta.setText(tarjeTot);

        String credTot = String.valueOf(cred);
        txtTotalCredito.setText(credTot);
    }//GEN-LAST:event_rbtnDiurnoActionPerformed

    private void brnCuadrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brnCuadrarActionPerformed

        if ((txtMontoCajaI.getText()).equals("")) {
            JOptionPane.showMessageDialog(null, "Monto inicial en caja VACIO", "ADVERTENCIA", JOptionPane.INFORMATION_MESSAGE);
        } else {
            int efectivo = Integer.parseInt(txtTotalEfectivo.getText());
            int tarjeta = Integer.parseInt(txtTotalTarjeta.getText());
            int credito = Integer.parseInt(txtTotalCredito.getText());

            int caja = Integer.parseInt(txtMontoCajaI.getText());
            
            int totalVenta = efectivo + tarjeta + credito;
            int totalCaja = caja + efectivo;
            
            String TotalVenta = String.valueOf(totalVenta);
            String TotalCaja = String.valueOf(totalCaja);
            
            txtTotalVenta.setText(TotalVenta);
            txtMontoCajaF.setText(TotalCaja);
        }
    }//GEN-LAST:event_brnCuadrarActionPerformed
    public void limpiarcasillas(){
        txtTotalEfectivo.setText("");
        txtTotalVenta.setText("");
        txtMontoCajaF.setText("");
        txtTotalTarjeta.setText("");
        txtTotalCredito.setText("");                
    }
    private void rbtnVespertinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnVespertinoActionPerformed
        int efec = CalcularTarjeta("V");
        int tarje = CalcularEfectivo("V");
        int cred = CalcularCredito("V");

        String efecTot = String.valueOf(efec);
        txtTotalEfectivo.setText(efecTot);

        String tarjeTot = String.valueOf(tarje);
        txtTotalTarjeta.setText(tarjeTot);

        String credTot = String.valueOf(cred);
        txtTotalCredito.setText(credTot);
    }//GEN-LAST:event_rbtnVespertinoActionPerformed

    private void txtMontoCajaIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoCajaIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoCajaIActionPerformed

    private int CalcularEfectivo(String valTurno) {
        String fec = txtFecha.getText();
        int totEfec = 0;
        try {
            //JOptionPane.showMessageDialog(null, "try 1", "llnar ingresos", JOptionPane.INFORMATION_MESSAGE);
            sentencia = (Statement) conexion.createStatement();
            //ResultSet lista = sentencia.executeQuery("SELECT * FROM detalle_pedido WHERE fecha_ajuste BETWEEN '2017/12/01' AND '2017/12/04'");
            ResultSet lista = sentencia.executeQuery("SELECT * FROM pedido WHERE fecha_venta = '" + fec + "' AND turno='" + valTurno + "' AND tipo_pago = 'E'");
            int cont = 0;
            while (lista.next()) {
                //JOptionPane.showMessageDialog(null, "while", "llnar ingresos", JOptionPane.INFORMATION_MESSAGE);
                //int auxCodPro = (lista.getInt("cod_producto")); // System.out.println(auxCod);               
                int auxValor = (lista.getInt("valor_neto_pedido"));
                //JOptionPane.showMessageDialog(null, auxValor, "neto", JOptionPane.INFORMATION_MESSAGE);
                totEfec = totEfec + auxValor;
            }
        } catch (SQLException e) {
            msj = "no se pudo seleccionar";
        }
        return totEfec;
    }

    private int CalcularTarjeta(String valTurno) {
        String fec = txtFecha.getText();
        int totTar = 0;
        try {
            //JOptionPane.showMessageDialog(null, "try 1", "llnar ingresos", JOptionPane.INFORMATION_MESSAGE);
            sentencia = (Statement) conexion.createStatement();
            //ResultSet lista = sentencia.executeQuery("SELECT * FROM detalle_pedido WHERE fecha_ajuste BETWEEN '2017/12/01' AND '2017/12/04'");
            ResultSet lista = sentencia.executeQuery("SELECT * FROM pedido WHERE fecha_venta = '" + fec + "' AND turno='" + valTurno + "' AND tipo_pago = 'T'");
            int cont = 0;
            while (lista.next()) {
                //JOptionPane.showMessageDialog(null, "while", "llnar ingresos", JOptionPane.INFORMATION_MESSAGE);
                //int auxCodPro = (lista.getInt("cod_producto")); // System.out.println(auxCod);               
                int auxValor = (lista.getInt("valor_neto_pedido"));
                //JOptionPane.showMessageDialog(null, auxValor, "neto", JOptionPane.INFORMATION_MESSAGE);
                totTar = totTar + auxValor;
            }
        } catch (SQLException e) {
            msj = "no se pudo seleccionar";
        }
        return totTar;
    }

    private int CalcularCredito(String valTurno) {
        String fec = txtFecha.getText();
        int totCred = 0;
        try {
            //JOptionPane.showMessageDialog(null, "try 1", "llnar ingresos", JOptionPane.INFORMATION_MESSAGE);
            sentencia = (Statement) conexion.createStatement();
            //ResultSet lista = sentencia.executeQuery("SELECT * FROM detalle_pedido WHERE fecha_ajuste BETWEEN '2017/12/01' AND '2017/12/04'");
            ResultSet lista = sentencia.executeQuery("SELECT * FROM pedido WHERE fecha_venta = '" + fec + "' AND turno='" + valTurno + "' AND tipo_pago = 'C'");
            int cont = 0;
            while (lista.next()) {
                //JOptionPane.showMessageDialog(null, "while", "llnar ingresos", JOptionPane.INFORMATION_MESSAGE);
                //int auxCodPro = (lista.getInt("cod_producto")); // System.out.println(auxCod);               
                int auxValor = (lista.getInt("valor_neto_pedido"));
                //JOptionPane.showMessageDialog(null, auxValor, "neto", JOptionPane.INFORMATION_MESSAGE);
                totCred = totCred + auxValor;
            }
        } catch (SQLException e) {
            msj = "no se pudo seleccionar";
        }
        return totCred;
    }

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
            java.util.logging.Logger.getLogger(formATurno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formATurno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formATurno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formATurno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formATurno().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton brnCuadrar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.ButtonGroup buttonGroup1;
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
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JRadioButton rbtnDiurno;
    private javax.swing.JRadioButton rbtnVespertino;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtMontoCajaF;
    private javax.swing.JTextField txtMontoCajaI;
    private javax.swing.JTextField txtTotalCredito;
    private javax.swing.JTextField txtTotalEfectivo;
    private javax.swing.JTextField txtTotalTarjeta;
    private javax.swing.JTextField txtTotalVenta;
    // End of variables declaration//GEN-END:variables
}
