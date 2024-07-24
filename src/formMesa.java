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

public class formMesa extends javax.swing.JFrame {

    private Statement sentencia;
    private Connection conexion;
    private String msj;
    
    private String nomBD = "bussandwicheria";
    private String usuario = "root";
    private String password = "informatica";
   

    /*
    private String nomBD="nicolas.huerta";
    private String usuario="nicolas.huerta";
    private String password="inf123pass";
     
     private String nomBD="bussandwicheria";
    private String usuario="bussandwicheria";
    private String password="bussandwicheria";  
*/ 
    /**
     * Creates new form formMesa
     */
    public formMesa() {
        initComponents();
        conectar();

        llenarTabla();
          asignarFecha();
        /*this.setLocationRelativeTo(null);
        LocalDate localDate = LocalDate.now();
        txtFecha.setText(dtf.format(localDate)); */
        this.getContentPane().setBackground(Color.orange);
        this.setLocationRelativeTo(null);
        setIcon();
    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagen/icono.png")));
    }
 public void asignarFecha(){
     this.setLocationRelativeTo(null);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        txtFecha.setText(dtf.format(localDate));
        setIcon();

    
    
    }
    public void conectar() {
         try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/" + this.nomBD;
             this.conexion = (Connection) DriverManager.getConnection(url, this.usuario, this.password);
            this.sentencia = (Statement) this.conexion.createStatement();
        } catch (Exception e) {
            msj = "error al conectar";
        }
    }

    public Integer validarBtnEliminar() {
        Integer swAceptar = 0;

        Integer sw = 0;
        DefaultTableModel modelo = (DefaultTableModel) tblMesas.getModel();
        int largo = modelo.getRowCount();
        if (largo <= 0) {

            JOptionPane.showMessageDialog(null, "Tabla vacia .", "¡ERROR!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        };
        
        if (txtNum.getText().isEmpty()) {
           
            JOptionPane.showMessageDialog(null, "ERROR! Campos vacios.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }

        if (sw == 0) {
         Integer auxRutProveedor = Integer.parseInt(txtNum.getText());

            String query = "";
            ResultSet existe;
            if (auxRutProveedor < 0) {

                JOptionPane.showMessageDialog(null, "ERROR!Rut no puede ser menor a0.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
                sw = 1;
            }


            try {
                sentencia = (Statement) conexion.createStatement();
                query = "SELECT * FROM `mesas` WHERE  numero_mesa = " + auxRutProveedor;

                existe = sentencia.executeQuery(query);

                if (existe.next() == false) {

                    JOptionPane.showMessageDialog(null, "Error no existe la mesa .", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
                    sw = 1;
                } else {
                  
                }
            } catch (SQLException e) {

                sw = 1;
            }
        }
         swAceptar = sw;
        return swAceptar;

    }

    public Integer validarBtnModificar() {
        Integer swAceptar = 0;
         Integer sw = 0;
        DefaultTableModel modelo = (DefaultTableModel) tblMesas.getModel();
        int largo = modelo.getRowCount();
        if (largo <= 0) {

            JOptionPane.showMessageDialog(null, "Tabla vacia .", "¡ERROR!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        };

         if (txtNum.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo numero vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } 
         if (txtCap.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo capacidad vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        }

        if (sw == 0) {

           Integer auxRutProveedor = Integer.parseInt(txtNum.getText());

            String query = "";
            ResultSet existe;

            if (auxRutProveedor < 0) {

                JOptionPane.showMessageDialog(null, "ERROR!Rut no puede ser menor a0.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
                sw = 1;
            }

            try {
                sentencia = (Statement) conexion.createStatement();
                query = "SELECT * FROM `mesas` WHERE  numero_mesa = " + auxRutProveedor;

                existe = sentencia.executeQuery(query);

                if (existe.next() == false) {

                    JOptionPane.showMessageDialog(null, "Error no existe el proveedor .", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
                    sw = 1;
                } else {
                  
                }
            } catch (SQLException e) {

                sw = 1;
            }
        }

       swAceptar = sw;
        return swAceptar;

    }

    public Integer validarBtnAgregar() {
        Integer swAceptar = 0;
        Integer sw = 0;

         if (txtNum.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo numero vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }
         if (txtCap.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo capacidad vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }
        if (sw == 0) {
            Integer auxRutProveedor = Integer.parseInt(txtNum.getText());

            String query = "";
            ResultSet existe;

            if (auxRutProveedor < 0) {

                JOptionPane.showMessageDialog(null, "ERROR!Rut no puede ser menor a 0.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
                sw = 1;
            }

            try {
                sentencia = (Statement) conexion.createStatement();
                query = "SELECT * FROM `mesas` WHERE  numero_mesa = " + auxRutProveedor;

                existe = sentencia.executeQuery(query);

                if (existe.next() == false) {

                  
                } else {
                    JOptionPane.showMessageDialog(null, "ERROR!YA EXISTE mesa.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
                    sw = 1;
                }
            } catch (SQLException e) {

                sw = 1;
            }
        }

        swAceptar = sw;
        return swAceptar;
    }

    ;
       
  public void llenarTabla() {
        String auxNum = "";
        String auxCap = "";
        String auxEst = "";

        try {
            sentencia = (Statement) conexion.createStatement();
            ResultSet lista = sentencia.executeQuery("SELECT * FROM mesas");
            int cont = 0;
            while (lista.next()) {

                msj = "entro a esto";

                auxNum = (lista.getString("numero_mesa"));
                auxCap = (lista.getString("capacidad"));
                auxEst = (lista.getString("estado"));

                DefaultTableModel modelo = (DefaultTableModel) tblMesas.getModel();
                modelo.addRow(new Object[]{auxNum, auxCap, auxEst});
                cont = cont + 1;

               }
        } catch (SQLException e) {
            msj = "no se pudo seleccionar";
        }

    }

    public void limpiarTabla() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) tblMesas.getModel();
            int filas = tblMesas.getRowCount();
            for (int i = 0; filas > i; i++) {
                modelo.removeRow(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
        }
    }

    public void insertar(String sql) {
        try {
            sentencia.executeUpdate(sql);
            msj = "datos guardados";
           } catch (SQLException e) {
            msj = "no ingreso";
        }
    }

    public void limpiarcampos() {
        txtNum.setText("");
        txtCap.setText("");

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollBar1 = new javax.swing.JScrollBar();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtNum = new javax.swing.JTextField();
        txtCap = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMesas = new javax.swing.JTable()
        {
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }}
            ;
            btnAgregar = new javax.swing.JButton();
            btnModificar = new javax.swing.JButton();
            btnEliminar = new javax.swing.JButton();
            btnCancelar = new javax.swing.JButton();
            jLabel5 = new javax.swing.JLabel();
            jSeparator1 = new javax.swing.JSeparator();
            txtFecha = new javax.swing.JTextField();

            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

            jLabel1.setText("Fecha");

            jButton1.setText("Atras");
            jButton1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton1ActionPerformed(evt);
                }
            });

            jLabel2.setText("Ingreso Mesa");

            txtNum.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    txtNumActionPerformed(evt);
                }
            });
            txtNum.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent evt) {
                    txtNumKeyTyped(evt);
                }
            });

            txtCap.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent evt) {
                    txtCapKeyTyped(evt);
                }
            });

            jLabel3.setText("Numero:");

            jLabel4.setText("Capacidad:");

            tblMesas.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                    "Numero", "Capacidad", "Estado"
                }
            ));
            tblMesas.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblMesasMouseClicked(evt);
                }
            });
            tblMesas.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyTyped(java.awt.event.KeyEvent evt) {
                    tblMesasKeyTyped(evt);
                }
            });
            jScrollPane1.setViewportView(tblMesas);

            btnAgregar.setText("Agregar");
            btnAgregar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnAgregarActionPerformed(evt);
                }
            });

            btnModificar.setText("Modificar");
            btnModificar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnModificarActionPerformed(evt);
                }
            });

            btnEliminar.setText("Eliminar");
            btnEliminar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnEliminarActionPerformed(evt);
                }
            });

            btnCancelar.setText("Cancelar");
            btnCancelar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnCancelarActionPerformed(evt);
                }
            });

            jLabel5.setText("Ingreso de datos");

            txtFecha.setEditable(false);

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jSeparator1))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel3)
                                            .addGap(18, 18, 18)
                                            .addComponent(txtNum))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel4)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtCap, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnAgregar)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnModificar)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnEliminar)
                            .addGap(14, 14, 14)
                            .addComponent(btnCancelar))
                        .addComponent(jButton1))
                    .addContainerGap())
                .addGroup(layout.createSequentialGroup()
                    .addGap(116, 116, 116)
                    .addComponent(jLabel2)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1))
                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(27, 27, 27)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAgregar)
                        .addComponent(btnModificar)
                        .addComponent(btnEliminar)
                        .addComponent(btnCancelar))
                    .addGap(21, 21, 21))
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void txtNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblMesasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMesasMouseClicked

    }//GEN-LAST:event_tblMesasMouseClicked

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed

        int sw = validarBtnAgregar();

        if (sw == 0) {

            sw = 0;
            if (txtNum.getText().isEmpty() || txtCap.getText().isEmpty()) {
                System.out.println("Una de las casillas vacías error ");
                JOptionPane.showMessageDialog(null, "ERROR! Casillas vacias.", "¡ADVRTENCIA", JOptionPane.INFORMATION_MESSAGE);
                sw = 1;
            } else {
                if ((Integer.parseInt(txtNum.getText()) < 0) || (Integer.parseInt(txtCap.getText()) <= 0)) {
                    JOptionPane.showMessageDialog(null, "ERROR! Campo campo numero no puede ser 0", "¡ADVRTENCIA", JOptionPane.INFORMATION_MESSAGE);
                    sw = 1;
                }
            }

            if (sw == 0) {
                Integer auxNum, auxCap;
                String estado = "L";

                auxNum = Integer.parseInt(txtNum.getText());
                auxCap = Integer.parseInt(txtCap.getText());

                 String sql = "INSERT INTO mesas VALUES (" + auxNum + "," + auxCap + ",'" + estado + "')";
               
                insertar(sql);
    JOptionPane.showMessageDialog(null, msj, "Inserto correcttamente", JOptionPane.INFORMATION_MESSAGE);
          
                limpiarTabla();
                llenarTabla();
                // TODO add your handling code here:
            }
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int sw = validarBtnEliminar();

        if (sw == 0) {

            sw = 0;
            if (txtNum.getText().isEmpty()) {
                System.out.println("Una de las casillas vacías error ");
                JOptionPane.showMessageDialog(null, "ERROR! Casillas vacias.", "¡ADVRTENCIA", JOptionPane.INFORMATION_MESSAGE);
                sw = 1;
            } else {
                if (Integer.parseInt(txtNum.getText()) <= 0) {
                    JOptionPane.showMessageDialog(null, "ERROR! Codigo invalido.", "¡ADVRTENCIA", JOptionPane.INFORMATION_MESSAGE);
                    sw = 1;
                }
            }

            if (sw == 0) {

                String codi = txtNum.getText();
                String query = "";
                ResultSet existe;
                int codE = Integer.parseInt(codi);

                 try {
                    sentencia = (Statement) conexion.createStatement();
                    query = "SELECT * from mesas WHERE numero_mesa =" + codE;
                    System.out.println(query);
                    existe = sentencia.executeQuery(query);
                    System.out.println(existe);
                    if (existe.next() == true) {
                        sentencia.executeUpdate("Delete from mesas WHERE numero_mesa =" + codE);

                        msj = "ELIMINADO";
                        JOptionPane.showMessageDialog(null, msj, "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        msj = "No existe insumo";
                        JOptionPane.showMessageDialog(null, msj, "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "No se puede eliminar la mesa por estar en un pedido", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);

                }
                limpiarTabla();              
                llenarTabla();             
                limpiarcampos();

            }
        }  // TODO add your handling code here:

        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        int sw = validarBtnModificar();

        if (sw == 0) {
            sw = 0;
            
        
            
            
            
            if (txtNum.getText().isEmpty() || txtCap.getText().isEmpty()) {
               sw = 1;
            } else {
                if (Integer.parseInt(txtNum.getText()) <= 0) {
                    JOptionPane.showMessageDialog(null, "ERROR! Campo numero no puede ser 0", "¡ADVRTENCIA", JOptionPane.INFORMATION_MESSAGE);
                    sw = 1;
                }
            }

            if (sw == 0) {

                Integer auxNum = Integer.parseInt(txtNum.getText());
                Integer auxCap = Integer.parseInt(txtCap.getText());
                String estado = "L";

                int codE = auxNum;
                String query = "";
                ResultSet existe;

                try {
                    sentencia = (Statement) conexion.createStatement();
                    query = "SELECT * from mesas WHERE numero_mesa =" + codE;
                    existe = sentencia.executeQuery(query);
                    if (existe.next() == true) {

                        msj = "modifico correctamente";
                       JOptionPane.showMessageDialog(null, msj, "Mensaje", JOptionPane.INFORMATION_MESSAGE);

                        String sql = "UPDATE  mesas SET capacidad ='" + auxCap + "' WHERE numero_mesa = '" + codE + "' ";
                        insertar(sql);

                    } else {
                        msj = "No existe insumo";
                        JOptionPane.showMessageDialog(null, msj, "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException e) {
                    msj = "No existe persona, no se puede modificar";
                }

                limpiarTabla();
                llenarTabla();
                limpiarcampos();

            }
        }  //      // TODO add your handling code here:
    }//GEN-LAST:event_btnModificarActionPerformed

    private void txtNumKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumKeyTyped
        int lar = 2;

        if (txtNum.getText().length() >= lar) {
            evt.consume();
        }

        char c = evt.getKeyChar();

        if (c < '0' || c > '9') {
            evt.consume();
        }    // TODO add your handling code here:
    }//GEN-LAST:event_txtNumKeyTyped

    private void txtCapKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCapKeyTyped
        int lar = 2;

        if (txtCap.getText().length() >= lar) {
            evt.consume();
        }

        char c = evt.getKeyChar();

        if (c < '0' || c > '9') {
            evt.consume();
        }     // TODO add your handling code here:
    }//GEN-LAST:event_txtCapKeyTyped

    private void tblMesasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblMesasKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_tblMesasKeyTyped

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
 limpiarcampos();        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarActionPerformed

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
            java.util.logging.Logger.getLogger(formMesa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formMesa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formMesa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formMesa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formMesa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tblMesas;
    private javax.swing.JTextField txtCap;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtNum;
    // End of variables declaration//GEN-END:variables
}
