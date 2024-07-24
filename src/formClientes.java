
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

public class formClientes extends javax.swing.JFrame {

    private Statement sentencia;
    /*puedo ocupar sentencias de control sql*/
    private Connection conexion;
  
    private String nomBD = "bussandwicheria";
    private String usuario = "root";
    private String password = "informatica";
      private String msj;
    /* */ 
    /*
    private String nomBD="bussandwicheria";
    private String usuario="bussandwicheria";
    private String password="bussandwicheria";

  */

    public formClientes() {
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

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagen/icono.png")));
    }

    public void conectar() {
        msj = "datos actualizados";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/" + this.nomBD;
            this.conexion = (Connection) DriverManager.getConnection(url, this.usuario, this.password);
            this.sentencia = (Statement) this.conexion.createStatement();
        } catch (Exception e) {
            msj = "error al conectar";
        }
    }

    public void llenarTabla() {
        Integer auxRut = 0;
        String auxNombre = "";
        String auxApPaterno = "";
        String auxApMaterno = "";
        String auxFonoCliente = "";
        Integer auxMonto = 0;

        try {
            sentencia = (Statement) conexion.createStatement();
            ResultSet lista = sentencia.executeQuery("SELECT * FROM cliente_frecuente");
            int cont = 0;
            while (lista.next()) {

                
                auxRut = Integer.parseInt((lista.getString("rut_cliente")));         
                auxNombre = (lista.getString("nombre_cliente"));          
                auxApPaterno = (lista.getString("ap_paterno"));
                auxApMaterno = (lista.getString("ap_materno"));
                auxFonoCliente = (lista.getString("fono_cliente"));
                auxMonto = Integer.parseInt((lista.getString("monto_deuda")));
             

                DefaultTableModel modelo = (DefaultTableModel) tblClientes.getModel();
                modelo.addRow(new Object[]{auxRut, auxNombre, auxApPaterno, auxApMaterno, auxFonoCliente, auxMonto});
                cont = cont + 1;

                
            }
        } catch (SQLException e) {
           
        }

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

    //INSERTAR
    public void insertar(String sql) {
        try {
            sentencia.executeUpdate(sql);
            msj = "Datos guardados";
            JOptionPane.showMessageDialog(null, msj, "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
       
        } catch (SQLException e) {
            msj = "no ingreso";
        }
    }

    public void limpiarcampos() {
        txtRut.setText("");
        txtNombre.setText("");
        txtApP.setText("");
        txtApM.setText("");
        txtFono.setText("");
        txtDeuda.setText("");

    }

    public Integer validarBtnAgregar() {
        Integer swAceptar = 0;

        /*VALIDACION */
        Integer sw = 0;

       
       if (txtRut.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo rut vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }
        if (txtNombre.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo nombre vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }
         if (txtApP.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo apellido paterno vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }
          if (txtApM.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo apellido materno vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }
           if (txtFono.getText().isEmpty()) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo fono vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } 
          if (txtDeuda.getText().isEmpty()) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo deuda vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } 
       
        
        
        
        
        if (sw == 0) {

            /*en esta segunda validacion necesitamos que exista el rutprov por lo que debe eser 0*/
 /*creamos variables necesarias para el prog y resto de validaciones*/
            Integer auxRutProveedor = Integer.parseInt(txtRut.getText());

            String query = "";
            ResultSet existe;

            if (auxRutProveedor == 0) {

                JOptionPane.showMessageDialog(null, "ERROR!Rut no puede ser 0.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
                sw = 1;
            }

            try {
                sentencia = (Statement) conexion.createStatement();
                query = "SELECT * FROM cliente_frecuente WHERE rut_cliente = " + auxRutProveedor;

                existe = sentencia.executeQuery(query);

                if (existe.next() == false) {

                  
                } else {
                    JOptionPane.showMessageDialog(null, "ERROR!YA EXISTE el cliente.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
                    sw = 1;
                }
            } catch (SQLException e) {

                sw = 1;
            }
        }

        /*FIN VALIDACION*/
        swAceptar = sw;
        return swAceptar;
    }

    ;
     
     
     
     
     public Integer validarBtnModificar() {
        Integer swAceptar = 0;

        /*VALIDACION */
        Integer sw = 0;
        DefaultTableModel modelo = (DefaultTableModel) tblClientes.getModel();
        int largo = modelo.getRowCount();
        if (largo <= 0) {

            JOptionPane.showMessageDialog(null, "Tabla vacia .", "¡ERROR!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        };

       
       if (txtRut.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo rut vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }
        if (txtNombre.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo nombre vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }
         if (txtApP.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo apellido paterno vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }
          if (txtApM.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo apellido materno vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }
           if (txtFono.getText().isEmpty()) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo fono vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }
          if (txtDeuda.getText().isEmpty()) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo deuda vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }
       
        if (sw == 0) {

            /*en esta segunda validacion necesitamos que exista el rutprov por lo que debe eser 0*/
 /*creamos variables necesarias para el prog y resto de validaciones*/
            Integer auxRutProveedor = Integer.parseInt(txtRut.getText());

            String query = "";
            ResultSet existe;

            if (auxRutProveedor == 0) {

                JOptionPane.showMessageDialog(null, "ERROR!Rut no puede ser 0.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
                sw = 1;
            }

            try {
                sentencia = (Statement) conexion.createStatement();
                query = "SELECT * FROM cliente_frecuente WHERE rut_cliente = " + auxRutProveedor;

                existe = sentencia.executeQuery(query);

                if (existe.next() == false) {

                    JOptionPane.showMessageDialog(null, "Error no existe el cliente .", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
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

    public Integer validarBtnEliminar() {
        Integer swAceptar = 0;

      
        Integer sw = 0;
        DefaultTableModel modelo = (DefaultTableModel) tblClientes.getModel();
        int largo = modelo.getRowCount();
        if (largo <= 0) {

            JOptionPane.showMessageDialog(null, "Tabla vacia .", "¡ERROR!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        };

        if (txtRut.getText().isEmpty()) {
           
            JOptionPane.showMessageDialog(null, "ERROR! Campos vacios.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }

        if (sw == 0) {

            /*en esta segunda validacion necesitamos que exista el rutprov por lo que debe eser 0*/
            /*creamos variables necesarias para el prog y resto de validaciones*/
            Integer auxRutProveedor = Integer.parseInt(txtRut.getText());

            String query = "";
            ResultSet existe;

            if (auxRutProveedor == 0) {

                JOptionPane.showMessageDialog(null, "ERROR!Rut no puede ser 0.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
                sw = 1;
            }

            try {
                sentencia = (Statement) conexion.createStatement();
                query = "SELECT * FROM cliente_frecuente WHERE rut_cliente = " + auxRutProveedor;

                existe = sentencia.executeQuery(query);

                if (existe.next() == false) {

                    JOptionPane.showMessageDialog(null, "Error no existe el cliente .", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
                    sw = 1;
                } else {
                   
                }
            } catch (SQLException e) {

                sw = 1;
            }
        }

        /*FIN VALIDACION*/
        swAceptar = sw;
        return swAceptar;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblDeuda = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        txtFono = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtDeuda = new javax.swing.JTextField();
        lblApP = new javax.swing.JLabel();
        btnAgregar = new javax.swing.JButton();
        txtNombre = new javax.swing.JTextField();
        btnModificar = new javax.swing.JButton();
        txtApP = new javax.swing.JTextField();
        btnEliminar = new javax.swing.JButton();
        lblApM = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        txtApM = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblFono = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtRut = new javax.swing.JTextField();
        lblRut = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblDeuda.setText("Deuda:");

        jSeparator2.setMaximumSize(new java.awt.Dimension(32767, 12));

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Rut", "Nombre", "Ap. Paterno", "Ap. Materno", "Fono", "Monto Deuda"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, true
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

        txtFono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFonoKeyTyped(evt);
            }
        });

        lblNombre.setText("Nombre:");

        txtDeuda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDeudaKeyTyped(evt);
            }
        });

        lblApP.setText("Apellido Paterno:");

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        txtApP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApPKeyTyped(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        lblApM.setText("Apellido Materno:");

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        txtApM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApMKeyTyped(evt);
            }
        });

        jLabel2.setText("Inserción de datos");

        lblFono.setText("Fono:");

        jLabel3.setText("Lista de clientes");

        txtRut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRutKeyTyped(evt);
            }
        });

        lblRut.setText("Rut:");

        jLabel1.setText("Fecha");

        btnRegresar.setText("Atras");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        jLabel4.setText("Registro de Clientes");

        txtFecha.setEditable(false);
        txtFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAgregar)
                        .addGap(44, 44, 44)
                        .addComponent(btnModificar)
                        .addGap(42, 42, 42)
                        .addComponent(btnEliminar)
                        .addGap(47, 47, 47)
                        .addComponent(btnCancelar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblFono)
                        .addGap(10, 10, 10)
                        .addComponent(txtFono, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69)
                        .addComponent(lblDeuda)
                        .addGap(18, 18, 18)
                        .addComponent(txtDeuda)))
                .addGap(19, 19, 19))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRegresar)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblRut)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtRut, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(22, 22, 22)
                                                .addComponent(lblNombre)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtNombre))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblApP)
                                                .addGap(10, 10, 10)
                                                .addComponent(txtApP, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(30, 30, 30)
                                                .addComponent(lblApM)
                                                .addGap(10, 10, 10)
                                                .addComponent(txtApM, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18)
                                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(1, 1, 1))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(177, 177, 177)
                        .addComponent(jLabel4)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnRegresar)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRut))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblApP)
                    .addComponent(txtApP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblApM)
                    .addComponent(txtApM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFono)
                    .addComponent(lblDeuda)
                    .addComponent(txtFono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDeuda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar)
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

            Object stoA = tblClientes.getValueAt(sel, 1);
            String auxNombre = stoA.toString();

            Object stoC = tblClientes.getValueAt(sel, 2);
            String auxApPaterno = stoC.toString();

            Object tipoS = tblClientes.getValueAt(sel, 3);
            String auxApMaterno = tipoS.toString();

            Object nom = tblClientes.getValueAt(sel, 4);
            String auxFono = nom.toString();

            Object cat = tblClientes.getValueAt(sel, 5);
            String auxMonto = cat.toString();
           txtRut.setText(auxRut);
            txtNombre.setText(auxNombre);
            txtApP.setText(auxApPaterno);
            txtApM.setText(auxApMaterno);
            txtFono.setText(auxFono);
            txtDeuda.setText(auxMonto);
            /* PD : MONTO = DEUDA EN ESTE FORMULARIO */

        }

    }//GEN-LAST:event_tblClientesMouseClicked

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        Integer sw = validarBtnAgregar();
        if (sw == 0) {

            Integer rut = Integer.parseInt(txtRut.getText());
            String auxNombre = txtNombre.getText();
            String auxApP = txtApP.getText();
            String auxApM = txtApM.getText();
            String auxFono = txtFono.getText();
            Integer auxDeuda = Integer.parseInt(txtDeuda.getText());

            sw = 0;
                 
            if (txtNombre.getText().isEmpty() || txtRut.getText().isEmpty() || txtNombre.getText().isEmpty() || txtApP.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "ERROR! Campos vacios.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
                sw = 1;
            } else {
                if (Integer.parseInt(txtRut.getText()) <= 0 || Integer.parseInt(txtDeuda.getText()) <= 0) {
                    JOptionPane.showMessageDialog(null, "ERROR! Campo invalido.", "¡ADVRTENCIA", JOptionPane.INFORMATION_MESSAGE);
                    sw = 1;
                }
            }

            if (sw == 0) {

             
                int codE = rut;
                String query = "";
                ResultSet existe;

                try {
                    sentencia = (Statement) conexion.createStatement();
                    query = "SELECT * from cliente_frecuente WHERE rut_cliente=" + codE;
                    existe = sentencia.executeQuery(query);
                    if (existe.next() == false) {
                        String sql = "INSERT INTO cliente_frecuente VALUES (" + rut + "," + auxNombre + "," + auxApP + ",'" + auxApM + "','" + auxFono + "'," + auxDeuda + ")";
                        insertar(sql);
                    } else {
                        msj = "Ya existe insumo";
                        JOptionPane.showMessageDialog(null, msj, "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException e) {
                    msj = "No existe persona, no se puede modificar";
                }

            }
            limpiarTabla();
            llenarTabla();
            limpiarcampos();
        }// TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        Integer sw = validarBtnModificar();
        if (sw == 0) {

            sw = 0;
            if (txtNombre.getText().isEmpty() || txtRut.getText().isEmpty() || txtApP.getText().isEmpty() || txtApM.getText().isEmpty() || txtDeuda.getText().isEmpty() || txtFono.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "ERROR! Casillas vacias.", "¡ADVRTENCIA", JOptionPane.INFORMATION_MESSAGE);
                sw = 1;
            } else {
                if (Integer.parseInt(txtRut.getText()) <= 0 || Integer.parseInt(txtDeuda.getText()) <= 0) {
                    JOptionPane.showMessageDialog(null, "ERROR! Campo invalido.", "¡ADVRTENCIA", JOptionPane.INFORMATION_MESSAGE);
                    sw = 1;
                }
            }

            if (sw == 0) {

                Integer rut = Integer.parseInt(txtRut.getText());
                String auxNombre = txtNombre.getText();
                String auxApP = txtApP.getText();
                Integer auxDeuda = Integer.parseInt(txtDeuda.getText());
                String auxFono = txtFono.getText();
                String auxApM = txtApM.getText();

                int codE = rut;
                String query = "";
                ResultSet existe;

                try {
                    sentencia = (Statement) conexion.createStatement();
                    query = "SELECT * from cliente_frecuente WHERE rut_cliente=" + codE;
                    existe = sentencia.executeQuery(query);
                    if (existe.next() == true) {
                         String sql = "UPDATE cliente_frecuente SET nombre_cliente='" +  auxNombre + "'WHERE rut_cliente ='" +  codE + "'";
                         sentencia.executeUpdate(sql);
                          sql = "UPDATE cliente_frecuente SET ap_paterno='" +  auxApP+ "'WHERE rut_cliente ='" +  codE + "'";
                         sentencia.executeUpdate(sql);
                           sql = "UPDATE cliente_frecuente SET ap_materno='" +  auxApM + "'WHERE rut_cliente ='" +  codE + "'";
                         sentencia.executeUpdate(sql);
                           sql = "UPDATE cliente_frecuente SET fono_cliente='" +  auxFono  + "'WHERE rut_cliente ='" +  codE + "'";
                         sentencia.executeUpdate(sql);
                           sql = "UPDATE cliente_frecuente SET monto_deuda='" +  auxDeuda + "'WHERE rut_cliente ='" +  codE + "'";
                         sentencia.executeUpdate(sql);
              
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
            }        // TODO add your handling code here:
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        Integer sw = validarBtnEliminar();

        if (sw == 0) {
            sw = 0;
            if (txtRut.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "ERROR! Casillas vacias.", "¡ADVRTENCIA", JOptionPane.INFORMATION_MESSAGE);
                sw = 1;
            } else {
                if (Integer.parseInt(txtRut.getText()) <= 0) {
                    JOptionPane.showMessageDialog(null, "ERROR! Codigo invalido.", "¡ADVRTENCIA", JOptionPane.INFORMATION_MESSAGE);
                    sw = 1;
                }
            }

            if (sw == 0) {
                String rut = txtRut.getText();
                String query = "";
                ResultSet existe;
                int codE = Integer.parseInt(rut);
 try {
                    sentencia = (Statement) conexion.createStatement(); 
                    query = "SELECT * from cliente_frecuente WHERE rut_cliente=" + codE;                 
                    existe = sentencia.executeQuery(query);         
                    if (existe.next() == true) {
                        
                        sentencia.executeUpdate("Delete from cliente_frecuente WHERE rut_cliente =" + codE);

                        msj = "ELIMINADO";
                        JOptionPane.showMessageDialog(null, msj, "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        msj = "No existe insumo";
                        JOptionPane.showMessageDialog(null, msj, "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException e) {
                    msj = "No se puede eliminar el cliente por integridad referencial";
                }
                limpiarTabla();
                llenarTabla();
                limpiarcampos();

            }
        }// TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void txtRutKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRutKeyTyped
        int lar = 9;

        if (txtRut.getText().length() >= lar) {
            evt.consume();
        }

        char c = evt.getKeyChar();

        if (c < '0' || c > '9') {
            evt.consume();
        }     // TODO add your handling code here:
    }//GEN-LAST:event_txtRutKeyTyped

    private void txtDeudaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDeudaKeyTyped
      int lar = 6;

        if (txtDeuda.getText().length() >= lar) {
            evt.consume();
        }

        char c = evt.getKeyChar();

        if (c < '0' || c > '9') {
            evt.consume();
        }     // TODO add your handling code here:     // TODO add your handling code here:
    }//GEN-LAST:event_txtDeudaKeyTyped

    private void txtFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
 limpiarcampos();        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        int lar = 30;

        if (txtNombre.getText().length() >= lar) {
            evt.consume();
        }

            // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtApPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApPKeyTyped
      int lar = 15;

        if (txtApP.getText().length() >= lar) {
            evt.consume();
        }
   // TODO add your handling code here:
    }//GEN-LAST:event_txtApPKeyTyped

    private void txtApMKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApMKeyTyped
     int lar = 15;

        if (txtApM.getText().length() >= lar) {
            evt.consume();
        }   // TODO add your handling code here:
    }//GEN-LAST:event_txtApMKeyTyped

    private void txtFonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFonoKeyTyped
  int lar = 12;

        if (txtFono.getText().length() >= lar) {
            evt.consume();
        }      // TODO add your handling code here:
    }//GEN-LAST:event_txtFonoKeyTyped

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
            java.util.logging.Logger.getLogger(formClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formClientes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblApM;
    private javax.swing.JLabel lblApP;
    private javax.swing.JLabel lblDeuda;
    private javax.swing.JLabel lblFono;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblRut;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtApM;
    private javax.swing.JTextField txtApP;
    private javax.swing.JTextField txtDeuda;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtFono;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtRut;
    // End of variables declaration//GEN-END:variables
}
