/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.Statement;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
//import javax.swing.table.DefaultTableModel;
import javax.swing.table.*;
import java.util.logging.*;

/**
 *
 * @author NicoD
 */
public class formDeuda extends javax.swing.JFrame {

    private Statement sentencia;
    /*puedo ocupar sentencias de control sql*/
    private Connection conexion;
    /*objeto que permite conectar mi base datos con programa*/
    private String nomBD = "bussandwicheria";
    private String usuario = "root";
    private String password = "informatica";
    private String msj;

    /*
    private String nomBD="nicolas.huerta";
    private String usuario="nicolas.huerta";
    private String password="inf123pass";
     */ /*
     private String nomBD="bussandwicheria";
    private String usuario="bussandwicheria";
    private String password="bussandwicheria"; */

    /**
     * Creates new form formDeuda
     */
    public formDeuda() {
        initComponents();
        conectar();
        llenarTabla();
        this.setLocationRelativeTo(null);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        txtFecha.setText(dtf.format(localDate));
        setIcon();
        this.getContentPane().setBackground(Color.orange);

    }
    

    public formDeuda(String varPedido, String varTotal) {
        initComponents();
        conectar();
        txtPedido.setText(varPedido);
        txtMonto.setText(varTotal);
        llenarTabla();
        this.setLocationRelativeTo(null);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        txtFecha.setText(dtf.format(localDate));
        setIcon();
        this.getContentPane().setBackground(Color.orange);
    }

    //CONECTAR
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

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagen/icono.png")));
    }

    //LLENAR TABLA
    public void llenarTabla() {
        Integer auxRut = 0;
        String auxNom = "";
        String auxAp = "";
        Integer auxMonto = 0;

        String query = "";

        try {
            sentencia = (Statement) conexion.createStatement();

            ResultSet lista = sentencia.executeQuery("SELECT * FROM cliente_frecuente");
            //JOptionPane.showMessageDialog(null, msj, "despue del primer select", JOptionPane.INFORMATION_MESSAGE);
            int cont = 0;
            while (lista.next()) {
                auxRut = Integer.parseInt((lista.getString("rut_cliente")));
                auxNom = (lista.getString("nombre_cliente"));
                auxAp = (lista.getString("ap_paterno"));
                auxMonto = Integer.parseInt((lista.getString("monto_deuda")));
                //JOptionPane.showMessageDialog(null, msj, "DESPUES DE LOS GETSTRING", JOptionPane.INFORMATION_MESSAGE);

                DefaultTableModel modelo = (DefaultTableModel) tblClientes.getModel();
                modelo.addRow(new Object[]{auxRut, auxNom, auxAp, auxMonto});
                cont = cont + 1;

                /*
                Object datos[] = {auxRut,auxNom,auxApp,auxApm,auxDir,auxFono,auxMail};
                tblProveedores.addRow(datos); */
            }
        } catch (SQLException e) {
            msj = "no se pudo seleccionar";
        }

    }

    public void limpiarcampos() {
        txtRut.setText("");
        txtMonto.setText("");
        txtPedido.setText("");

    }

    public void limpiarTabla() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) tblClientes.getModel();
            int filas = tblClientes.getRowCount();
            for (int i = 0; filas > i; i++) {
                modelo.removeRow(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtPedido = new javax.swing.JTextField();
        txtMonto = new javax.swing.JTextField();
        txtRut = new javax.swing.JTextField();
        lblFecha = new javax.swing.JLabel();
        lblNumer = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        lblRut = new javax.swing.JLabel();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnRegresar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        txtFecha = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registro de Deuda");

        txtPedido.setEditable(false);

        txtMonto.setEditable(false);

        txtRut.setEditable(false);

        lblFecha.setText("Fecha");

        lblNumer.setText("Numero Pedido:");

        lblTotal.setText("Total Neto:");

        lblRut.setText("Rut Cliente:");

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");

        btnRegresar.setText("Atras");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        jLabel5.setText("Registrar Deuda");

        jLabel1.setText("Listado de Clientes");

        jLabel2.setText("Ingreso de Datos");

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Rut", "Nombre", "Ap. Paterno", "Monto Deuda"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblClientes);
        if (tblClientes.getColumnModel().getColumnCount() > 0) {
            tblClientes.getColumnModel().getColumn(0).setResizable(false);
            tblClientes.getColumnModel().getColumn(1).setResizable(false);
            tblClientes.getColumnModel().getColumn(2).setResizable(false);
            tblClientes.getColumnModel().getColumn(3).setResizable(false);
        }

        txtFecha.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblFecha)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnRegresar))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(btnAceptar)
                        .addGap(67, 67, 67)
                        .addComponent(btnCancelar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(176, 176, 176)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblNumer)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtPedido))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblRut)
                                            .addComponent(lblTotal))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtMonto)
                                            .addComponent(txtRut, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator2)))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblFecha)
                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnRegresar))
                .addGap(1, 1, 1)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNumer))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRut))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(4, 4, 4))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        //new fLogin().setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        if (tblClientes.getRowCount() > 0) {
            int sel = tblClientes.getSelectedRow();
            Object cod = tblClientes.getValueAt(sel, 0);
            String auxRut = cod.toString();

            Object cat = tblClientes.getValueAt(sel, 3);
            String auxMonto = cat.toString();

            //NICO R, $%$%$y DIEGO JARA :V V:%&$· TODOS LOS DERECHOS RESERVADOS//
            txtRut.setText(auxRut);

            //txtMonto.setText(auxMonto);
            /* PD : MONTO = DEUDA EN ESTE FORMULARIO */
        }
    }//GEN-LAST:event_tblClientesMouseClicked

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        //JOptionPane.showMessageDialog(null, msj, "TOCO EL BOTON ACEPTAR", JOptionPane.INFORMATION_MESSAGE);
        String auxRut = txtRut.getText();
        Integer auxMontoBD = 0;
        Integer auxMontoForm = Integer.parseInt(txtMonto.getText());
        Integer auxMontoNuevo = 0;
        String query = "";

        if (auxRut.equals("")) {
            JOptionPane.showMessageDialog(null, "'Rut Cliente' Vacio \n Seleccione cliente del listado.", "ADVERTENCIA!", JOptionPane.INFORMATION_MESSAGE);
        } else {

            try {
                sentencia = (Statement) conexion.createStatement();

                ResultSet lista = sentencia.executeQuery("SELECT * FROM cliente_frecuente where rut_cliente ='" + auxRut + "'");
                //JOptionPane.showMessageDialog(null, msj, "despue del primer select", JOptionPane.INFORMATION_MESSAGE);
                int cont = 0;
                while (lista.next()) {
                    auxMontoBD = Integer.parseInt((lista.getString("monto_deuda")));

                    cont = cont + 1;
                }
            } catch (SQLException e) {
                msj = "no se pudo seleccionar";
            }

            //IMPORTANTE : AHORA QUE YA TENEMOS EL VALOR NUEVO A AGREGAR
            //VENDRIA LA PARTE DE AGREGAR DICHO MONTO
            ResultSet existe;
            auxMontoNuevo = auxMontoBD + auxMontoForm;
            try {
                sentencia = (Statement) conexion.createStatement();
                query = "SELECT * from cliente_frecuente WHERE rut_cliente='" + auxRut + "'";
                existe = sentencia.executeQuery(query);
                if (existe.next() == true) {
                    String sql = "UPDATE cliente_frecuente SET monto_deuda='" + auxMontoNuevo + "'WHERE rut_cliente ='" + auxRut + "'";

                    try {
                        sentencia.executeUpdate(sql);
                        msj = "Datos guardados";
                        JOptionPane.showMessageDialog(null, msj, "DATOS ACTUALIZADOS XDXDXDXDXDXD", JOptionPane.INFORMATION_MESSAGE);
                        // lblEstado.setText(msj);
                    } catch (SQLException e) {
                        msj = "no ingreso";
                        JOptionPane.showMessageDialog(null, msj, "MAAAAL", JOptionPane.INFORMATION_MESSAGE);
                    }

                    msj = "Modificado";
                    JOptionPane.showMessageDialog(null, msj, "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    msj = "No existe la persona no se puede modificar";
                    JOptionPane.showMessageDialog(null, msj, "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {
                msj = "No existe persona, no se puede modificar";
            }

            limpiarTabla();
            llenarTabla();
            limpiarcampos();
        }
        // TODO add your handling code here:   // TODO add your handling code here:
    }//GEN-LAST:event_btnAceptarActionPerformed

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
            java.util.logging.Logger.getLogger(formDeuda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formDeuda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formDeuda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formDeuda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formDeuda().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblNumer;
    private javax.swing.JLabel lblRut;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtMonto;
    private javax.swing.JTextField txtPedido;
    private javax.swing.JTextField txtRut;
    // End of variables declaration//GEN-END:variables
}
