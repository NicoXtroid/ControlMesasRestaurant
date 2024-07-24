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

import javax.swing.table.*;
import java.util.logging.*;

/**
 *
 * @author NicoD
 */
public class formRCompra extends javax.swing.JFrame {

    /**
     * Creates new form formRCompra
     */
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

    public formRCompra() {
        initComponents();
        conectar();
        llenarTablaInsumos();
        llenarTablaProveedores();
        DefaultTableModel modelo = (DefaultTableModel) tblIns.getModel();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        lblFecha21.setText(dtf.format(localDate));
               
        txtFecha.setText(dtf.format(localDate));

        this.setLocationRelativeTo(null);

        this.getContentPane().setBackground(Color.orange);

        setIcon();
    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagen/icono.png")));
    }

  public void limpiarcampos(){
        txtRut.setText("");
        txtCodI.setText("");
      txtCantidad.setText("");
      txtPrecio.setText("");
    }
    //conectar
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

    public Integer validarBtnAceptar() {
        Integer swAceptar = 0;

        Integer sw = 0;
        DefaultTableModel modelo = (DefaultTableModel) tblRegCompra.getModel();
        int largo = modelo.getRowCount();
        if (largo <= 0) {

            JOptionPane.showMessageDialog(null, "Tabla vacia .", "¡ERROR!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        };

        if (txtRut.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "ERROR! Campo rut vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }

        if (sw == 0) {
          Integer auxRutProveedor = Integer.parseInt(txtRut.getText());
           Float auxTotal = Float.parseFloat(lblTotalPut.getText());
            String query = "";
            ResultSet existe;

            try {
                sentencia = (Statement) conexion.createStatement();
                query = "SELECT * FROM `proveedores` WHERE rut_proveedor = " + auxRutProveedor;

                existe = sentencia.executeQuery(query);

                if (existe.next() == false) {
                    JOptionPane.showMessageDialog(null, "ERROR!NO EXISTE proveedor.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
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

    
      
    
    public Integer  comprobarExistencia(){
          int swComprobacion = 0;
       
            DefaultTableModel modelo = (DefaultTableModel) tblRegCompra.getModel();
        
            int fils = modelo.getRowCount();
            int contA = 0;
            int contB = 0;
          
           
             
            while (contA < fils) {
                   
                String auxNomI = "zzz";
               int auxCodInsA = ((Integer) modelo.getValueAt(contA, 1)).intValue();
                int auxCcdInsB =  Integer.parseInt(txtCodI.getText());
              
                if (auxCodInsA == auxCcdInsB){
               
               swComprobacion = 1;
               
          
              float    auxValorNetoNuevo = ((Float.parseFloat(txtPrecio.getText())) * (Float.parseFloat(txtCantidad.getText())) )   + ((Float) modelo.getValueAt(contA, 3)).floatValue() ;             
              float    auxCantNueva =  (((Float) modelo.getValueAt(contA, 0)).floatValue() ) + (Float.parseFloat(txtCantidad.getText()));
              
             
               try {
                 
             
                sentencia = (Statement) conexion.createStatement();
                ResultSet lista = sentencia.executeQuery("SELECT * FROM insumos WHERE cod_insumo=" + auxCodInsA);
                while (lista.next()) {
                    auxNomI = (lista.getString("nombre_insumo"));
                }
            } catch (SQLException e) {
              
                
            JOptionPane.showMessageDialog(null, "Error grave  .", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
           
            }   
               
               modelo.removeRow(contA);              
               modelo.addRow(new Object[]{auxCantNueva, auxCodInsA, auxNomI, auxValorNetoNuevo});
             
               }
               
               
                contA = contA +1;
            }
    return  swComprobacion;
    }
    public boolean esDecimal(String cad)
 {
 try
 {
   Double.parseDouble(cad);
   return true;
 }
 catch(NumberFormatException nfe)
 {
   return false;
 }
 }
    public Integer validarAgregar() {
        Integer sw = 0;
        
        if (esDecimal( txtCantidad.getText())==false){
      JOptionPane.showMessageDialog(null, "ERROR! Formato float erroneo.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        }
        else{   
        
         if (txtCodI.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo codigo insumo vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } 
        if (txtCantidad.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo cantidad vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } 
         if (txtPrecio.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo precio vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } 
        if (txtCodI.getText().isEmpty() || txtCantidad.getText().isEmpty() || txtPrecio.getText().isEmpty()) {
           sw = 1;
        } else {

            Integer auxCodI = Integer.parseInt(txtCodI.getText());
            Float auxCant = Float.parseFloat(txtCantidad.getText());
            String auxNomI = "zzzz";
            Integer auxPrecio = Integer.parseInt(txtPrecio.getText());
            String query = "";
            ResultSet existe;
           
            if (auxCodI == 0) {

                JOptionPane.showMessageDialog(null, "Error codigo insumo no puede ser 0", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
                sw = 1;
            }
            if (auxCant == 0) {

                JOptionPane.showMessageDialog(null, "Error,cantidad no puede ser 0", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
                sw = 1;
            }
            if (auxPrecio == 0) {
                sw = 1;

                JOptionPane.showMessageDialog(null, "Error codigo precio no puede ser 0", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);

            }

            try {
                sentencia = (Statement) conexion.createStatement();
                query = "SELECT * from insumos WHERE cod_insumo=" + auxCodI;
                existe = sentencia.executeQuery(query);
                if (existe.next() == false) {
                    JOptionPane.showMessageDialog(null, "ERROR!NO EXISTE INSUMO.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
                    sw = 1;
                } 
            } catch (SQLException e) {
                msj = "No existe insumo";
                  JOptionPane.showMessageDialog(null, "ERROR!NO EXISTE INSUMO.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
                  
            }

            /*FIN VALIDACION*/
        }
        }
        return sw;
    }

    public void limpiarDetalleTabla() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) tblRegCompra.getModel();
            int filas = tblRegCompra.getRowCount();
            for (int i = 0; filas > i; i++) {
                modelo.removeRow(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
        }

        txtCodI.setText("");
        txtCantidad.setText("");
        txtPrecio.setText("");

        txtRut.setText("");
        lblTotalPut.setText("");
    }

    
    public void llenarTablaInsumos() {
        String auxCod = "";
        String auxStoA = "";
        String auxStoC = "";
        String auxTipo = "";
        String auxNom = "";
        String auxCat = "";
        String auxDes = "";
        String auxUni = "";
        String query = "";
        String uni = "";
        String tipo = "";
        String cat = "";

        try {
            sentencia = (Statement) conexion.createStatement();
            ResultSet lista = sentencia.executeQuery("SELECT * FROM insumos");
            int cont = 0;
            while (lista.next()) {
                auxCod = (lista.getString("cod_insumo")); 
                auxStoA = (lista.getString("stock_actual")); 
                auxStoC = (lista.getString("stock_critico")); 
                tipo = (lista.getString("tipo_insumo")); 
                auxNom = (lista.getString("nombre_insumo"));  
                cat = (lista.getString("cod_categoria")); 
                auxDes = (lista.getString("descripcion"));
                uni = (lista.getString("uni_medida"));

               

              
                DefaultTableModel modelo = (DefaultTableModel) tblIns.getModel();
                modelo.addRow(new Object[]{auxCod, auxNom, auxDes, auxTipo, auxUni, auxStoA, auxStoC, auxCat});
                cont = cont + 1;

            }
        } catch (SQLException e) {
            msj = "no se pudo seleccionar";
        }
    }

    public void llenarTablaProveedores() {
        String auxRut = "";
        String auxNom = "";
        String auxApp = "";
        String auxApm = "";
        String auxDir = "";
        String auxFono = "";
        String auxMail = "";
        String query = "";

        try {
            sentencia = (Statement) conexion.createStatement();
            ResultSet lista = sentencia.executeQuery("SELECT * FROM proveedores");
            int cont = 0;
            while (lista.next()) {
                auxRut = (lista.getString("rut_proveedor"));            
                auxNom = (lista.getString("nombre_proveedor"));           
                auxApp = (lista.getString("ap_paterno"));                
                auxApm = (lista.getString("ap_materno"));            
                auxDir = (lista.getString("direccion"));           
                auxFono = (lista.getString("fono"));              
                auxMail = (lista.getString("email"));
              DefaultTableModel modelo = (DefaultTableModel) tblProveedores.getModel();
                modelo.addRow(new Object[]{auxRut, auxNom, auxApp, auxApm, auxDir, auxFono, auxMail});
                cont = cont + 1;

                
            }
        } catch (SQLException e) {
            msj = "no se pudo seleccionar";
        }

    }

    public void insertar(String sql) {
        try {
            sentencia.executeUpdate(sql);
         
        } catch (SQLException e) {
            msj = "no ingreso";
        }
    }

    public Integer calcCodCompra() {
        int cod_final = 0;
        try {
            sentencia = (Statement) conexion.createStatement();

            ResultSet lista = sentencia.executeQuery("SELECT * FROM registro_compra ");

          
            while (lista.next()) {
                cod_final = Integer.parseInt((lista.getString("cod_compra")));
                cod_final = cod_final + 1;
            }
        } catch (SQLException e) {

        }
        if (cod_final == 0) {
            cod_final = 1;
        }

        return cod_final;
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtRut = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnRegresar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblIns = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };

        ;
        jScrollPane4 = new javax.swing.JScrollPane();
        tblRegCompra = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };

        ;
        txtCodI = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        lblCant = new javax.swing.JLabel();
        lblCodInsumo = new javax.swing.JLabel();
        btnAgregar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProveedores =  new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };

        ;
        lblTotalPut = new javax.swing.JLabel();
        lblFecha21 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registro de Compra de Insumos");
        setResizable(false);

        jLabel2.setText("Rut Proveedor:");

        jLabel3.setText("Fecha");

        txtRut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRutKeyTyped(evt);
            }
        });

        jLabel5.setText("Total:");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("REGISTRO COMPRA");

        jButton1.setText("Aceptar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnRegresar.setText("Atras");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        tblIns.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre"
            }
        ));
        tblIns.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblInsMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblIns);

        tblRegCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cantidad", "Codigo", "Nombre", "ValorNeto"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tblRegCompra);

        txtCodI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodIActionPerformed(evt);
            }
        });
        txtCodI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodIKeyTyped(evt);
            }
        });

        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });

        lblCant.setText("Cantidad");

        lblCodInsumo.setText("Codigo Insumo");

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        jLabel1.setText("Precio compra");

        txtPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioActionPerformed(evt);
            }
        });
        txtPrecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPrecioKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioKeyTyped(evt);
            }
        });

        tblProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Rut", "Nombre", "Ap Paterno", "Ap Materno", "Direccion", "Fono", "Email"
            }
        ));
        tblProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProveedoresMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblProveedores);

        lblTotalPut.setText("0000");

        lblFecha21.setText("00000");

        jLabel4.setText("Seleccione Proveedor:");

        jLabel7.setText("Seleccione Insumo:");

        jLabel8.setText("Detalle Compra:");

        txtFecha.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addComponent(txtRut, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel7)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblCodInsumo)
                                            .addComponent(lblCant))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txtCodI, javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(txtCantidad, javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(txtPrecio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(btnAgregar)))))
                            .addComponent(jLabel8)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblTotalPut, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(144, 144, 144)
                                .addComponent(jLabel6))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(345, 345, 345)
                                .addComponent(btnRegresar))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblFecha21, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(103, 103, 103)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRegresar)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(7, 7, 7)
                .addComponent(jLabel6)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtRut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblCodInsumo)
                                    .addComponent(txtCodI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCant))
                                .addGap(9, 9, 9)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAgregar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(lblTotalPut)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(lblFecha21)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        limpiarDetalleTabla();
        limpiarcampos();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        //new fLogin().setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void tblInsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblInsMouseClicked
        if (tblIns.getRowCount() > 0) {
            int sel = tblIns.getSelectedRow();
            Object cod = tblIns.getValueAt(sel, 0);
            String auxCode = cod.toString();

            Object nom = tblIns.getValueAt(sel, 1);
            String auxNom = nom.toString();
            txtCodI.setText(auxCode);
        }      // TODO add your handling code here:
    }//GEN-LAST:event_tblInsMouseClicked
public void noRepetido(){        
            
    int auxCodI = Integer.parseInt(txtCodI.getText());
            float auxCant =  Float.parseFloat(txtCantidad.getText());
            String auxNomI = "zzzz";

            try {
                sentencia = (Statement) conexion.createStatement();
                ResultSet lista = sentencia.executeQuery("SELECT * FROM insumos WHERE cod_insumo=" + auxCodI);
                while (lista.next()) {
                    auxNomI = (lista.getString("nombre_insumo"));
                }
            } catch (SQLException e) {
                msj = "no se pudo seleccionar";
            }

            Float valorNeto = auxCant * Float.parseFloat((txtPrecio.getText()));

            DefaultTableModel modelo = (DefaultTableModel) tblRegCompra.getModel();
            modelo.addRow(new Object[]{auxCant, auxCodI, auxNomI, valorNeto});

            int fils = modelo.getRowCount();
            int cont = 0;
            float total = 0;
           float auxCantidad = 0;
            
           
           while (cont < fils) {

                float x = ((Float) modelo.getValueAt(cont, 3)).floatValue();
                total = total + x;

                cont = cont + 1;
            }

            lblTotalPut.setText(Float.toString(total));
        }
    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed

       
         Integer sw = validarAgregar();
     
        if (sw == 0) {
            
            Integer swExistencia = comprobarExistencia();
            
          if (swExistencia==0){
          noRepetido();
           
              }/*este parentesis cierra el if compr existencia*/
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void txtCodIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodIActionPerformed

    private void txtPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      
        Integer sw = validarBtnAceptar();
                         
        if (sw == 0) {
                     
            Integer auxCodCompra2 = calcCodCompra();
         
            DefaultTableModel modelo = (DefaultTableModel) tblRegCompra.getModel();
        
            int fils = modelo.getRowCount();
            int cont = 0;

            Integer auxRutProveedor = Integer.parseInt(txtRut.getText());
            Float auxTotal = Float.parseFloat(lblTotalPut.getText());
         
            String auxFecha = lblFecha21.getText();
           
            String sql = "INSERT INTO registro_compra VALUES (" + auxCodCompra2 + "," + auxRutProveedor + "," + auxTotal + ",'" + auxFecha + "')";
                
            insertar(sql);
                while (cont < fils) {
                    
           
                float auxCant = ((Float) modelo.getValueAt(cont, 0)).floatValue();
                 int auxCodI = ((Integer) modelo.getValueAt(cont, 1)).intValue();
               
                float auxValorNeto = ((Float) modelo.getValueAt(cont, 3)).floatValue();

                sql = "INSERT INTO detalle_registro_compra VALUES (" + auxCodI + "," + auxCodCompra2 + ",'" + auxFecha + "'," + auxCant + "," + auxValorNeto + ")";
                  insertar(sql);

                 String auxCodIns = String.valueOf(auxCodI);
           
                float auxMontoBD = 0;
                float auxMontoForm = auxCant;
                float auxMontoNuevo = 0;
         
                try {
                    sentencia = (Statement) conexion.createStatement();
 
                    ResultSet lista = sentencia.executeQuery("SELECT * FROM insumos where cod_insumo ='" + auxCodIns + "'");
      int contIns = 0;
                   
                    while (lista.next()) {
                        
                         auxMontoBD = Float.parseFloat((lista.getString("stock_actual")));

                    
                        auxMontoNuevo = auxMontoBD + auxMontoForm;

                    
                        contIns = contIns + 1;

                        sql = "UPDATE insumos SET stock_actual='" + auxMontoNuevo + "'WHERE cod_insumo ='" + auxCodI + "'";
                        
                        try {
                          
                            sentencia.executeUpdate(sql);
                         
                        } catch (SQLException e) {
                            msj = "Error no se modifico el stock del insumo";
                            JOptionPane.showMessageDialog(null, msj, "ERROR", JOptionPane.INFORMATION_MESSAGE);
                        }

                       
              
                    }
                 
                    
                    
                    
                } catch (SQLException f) {
                 
                }
  
                cont = cont + 1;
            }
            
            limpiarDetalleTabla();
               limpiarcampos();
msj = "Datos guardados";
                            JOptionPane.showMessageDialog(null, msj, "DATOS ACTUALIZADOS XDXDXDXDXDXD", JOptionPane.INFORMATION_MESSAGE);
                         
        }
// TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblProveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProveedoresMouseClicked
        if (tblProveedores.getRowCount() > 0) {
            int sel = tblProveedores.getSelectedRow();
            Object cod = tblProveedores.getValueAt(sel, 0);
            String auxRut = cod.toString();
            txtRut.setText(auxRut);

        }   // TODO add your handling code here:
    }//GEN-LAST:event_tblProveedoresMouseClicked

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        int lar = 4;

        if (txtCantidad.getText().length() > lar) {
            evt.consume();
        }

        char c = evt.getKeyChar();

        if  ((c < '0' || c > '9') && (c != '.' )) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadActionPerformed

    private void txtPrecioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioKeyTyped
        int lar = 8;

        if (txtPrecio.getText().length() > lar) {
            evt.consume();
        }

        char c = evt.getKeyChar();

        if (c < '0' || c > '9') {
            evt.consume();
        }      // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioKeyTyped

    private void txtPrecioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioKeyPressed

    }//GEN-LAST:event_txtPrecioKeyPressed

    private void txtCodIKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodIKeyTyped
        int lar = 10;

        if (txtCodI.getText().length() > lar) {
            evt.consume();
        }

        char c = evt.getKeyChar();

        if ((c < '0') || (c > '9') ) {
            evt.consume();
        }      // TODO add your handling code here:
    }//GEN-LAST:event_txtCodIKeyTyped

    private void txtRutKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRutKeyTyped
        int lar = 10;

        if (txtRut.getText().length() > lar) {
            evt.consume();
        }

        char c = evt.getKeyChar();

        if (c < '0' || c > '9') {
            evt.consume();
        }       // TODO add your handling code here:
    }//GEN-LAST:event_txtRutKeyTyped

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
            java.util.logging.Logger.getLogger(formRCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formRCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formRCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formRCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formRCompra().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblCant;
    private javax.swing.JLabel lblCodInsumo;
    private javax.swing.JLabel lblFecha21;
    private javax.swing.JLabel lblTotalPut;
    private javax.swing.JTable tblIns;
    private javax.swing.JTable tblProveedores;
    private javax.swing.JTable tblRegCompra;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodI;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtRut;
    // End of variables declaration//GEN-END:variables
}
