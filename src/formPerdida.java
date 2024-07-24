import java.awt.Color;
import java.awt.Toolkit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author tuinf
 */
public class formPerdida extends javax.swing.JFrame {

    private Statement sentencia;
    private Statement sentencia2;
    /*puedo ocupar sentencias de control sql*/
    private Connection conexion;
  
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
    private String msj;
    private TableRowSorter trsFiltro;
    
    private int swAntiRepet = 0;/*con este switch no se reptie el mensj de datos act*/
  
    /**
     * Creates new form formPerdida
     */
    public formPerdida() {
        initComponents();
        conectar();
        chkInsumo.setSelected(true);
        chkIngreso.setSelected(true);
        limpiarTabla();
        llenarColI();
        llenarTablaI();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        lblFecha2.setText(dtf.format(localDate));

        this.setLocationRelativeTo(null);
        setIcon();
        this.getContentPane().setBackground(Color.orange);
    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagen/icono.png")));
    }
    
    public void filtro() {
        int columnaABuscar = 0;
        if (cmbFiltro.getSelectedItem() == "Código") {
            columnaABuscar = 0;
        }
        if (cmbFiltro.getSelectedItem().toString() == "Nombre") {
            columnaABuscar = 1;
        }
        if (cmbFiltro.getSelectedItem() == "Stock Actual") {
            columnaABuscar = 2;
        }
       
        trsFiltro.setRowFilter(RowFilter.regexFilter(txtFiltro.getText(), columnaABuscar));
    }

  
    
    
    public Integer validarProdNegativo(Integer paramCodProdix) {
        /*En esta parte del codigo ,se realiza en el caso de que el chk del producto este activado */
        int swMaestro = 0;
        /*valida que el prod sea positivio*/
        int sw = 0;
 
        if (chkProducto.isSelected()) {

            if (sw == 0) {
                if (chkIngreso.isSelected() || chkPerdida.isSelected()) {

                    Integer auxCodProd = Integer.parseInt(txtCodIns.getText());
                    String auxCodIns = "";
                    float auxCant = 0;

                    try {
                       
                        sentencia = (Statement) conexion.createStatement();
                        ResultSet lista = sentencia.executeQuery("SELECT * FROM detalle_producto WHERE cod_producto='" + auxCodProd + "'");
                        int cont = 0;

                        while (lista.next()) {
                            auxCodIns = lista.getString("cod_insumo");
                            auxCant = (Float.parseFloat(lista.getString("cantidad"))) * (Float.parseFloat(txtCantidad.getText()));
                       int switchNoNegativo = 0;

                            float auxMontoBD = 0;
                           float auxMontoForm = auxCant;
                            float auxMontoNuevo = 0;
                            String query = "";

                            String auxTipoAjuste = "X";
                            String auxObs = "Nada";
                            try {
                                sentencia = (Statement) conexion.createStatement();

                                ResultSet lista2 = sentencia.executeQuery("SELECT * FROM insumos where cod_insumo ='" + auxCodIns + "'");

                                cont = 0;
                                while (lista2.next()) {
                                    auxMontoBD = Float.parseFloat((lista2.getString("stock_actual")));
                                auxMontoNuevo = auxMontoBD - auxMontoForm;

                                    if (auxMontoNuevo < 0 ) {
                                       
                                        if (chkPerdida.isSelected()){
                                        swMaestro = 1;
                                        switchNoNegativo = 1;
                                        JOptionPane.showMessageDialog(null, "Error no se puede eliminar una cantidad que no dispone", "ERROR", JOptionPane.INFORMATION_MESSAGE);
                                        }
                                        
                                        
                                    }

                                    /* JOptionPane.showMessageDialog(null,msj,"el aux monto nuevo NUEVO VALE " +   auxMontoNuevo,JOptionPane.INFORMATION_MESSAGE);*/
                                    cont = cont + 1;

                               }
                            } catch (SQLException e) {
                                msj = "no se pudo seleccionar";
                            }

                              }
                    } catch (SQLException e) {
                        msj = "no se pudo seleccionar";
                    }

                } else {
                    JOptionPane.showMessageDialog(null, msj, "ERROR NO HAY CHK ACTIVADOS", JOptionPane.INFORMATION_MESSAGE);

                }

            }
        }

        return swMaestro;
    }

    public Integer calcCodAjuste() {
        int cod_final = 0;
        try {
            sentencia = (Statement) conexion.createStatement();

            ResultSet lista = sentencia.executeQuery("SELECT * FROM ajuste_stock ");
            while (lista.next()) {
                cod_final = Integer.parseInt((lista.getString("cod_ajuste")));
                cod_final = cod_final + 1;
                  }
        } catch (SQLException e) {

        }
        if (cod_final == 0) {
            cod_final = 1;
        }

        return cod_final;
    }

    public String calcularStockVirtual(String paramCodProd) {
        String returnStockVirtual = "000";
        String auxCodInsumox = "111";
        String auxCodProd = paramCodProd;
        float valorMinimo = 0;
        float auxCantDetalle = 0;
        float auxCantIns = 0;
        float auxStockVirtual = 0;
       float auxStockInsumo = 0;
        Integer swValorMin = 1;
        /*switch para determinar el valor min*/

        try {
            sentencia = (Statement) conexion.createStatement();

            ResultSet lista = sentencia.executeQuery("SELECT * FROM detalle_producto where cod_producto ='" + auxCodProd + "'");
            int cont = 0;
            while (lista.next()) {
                auxCodInsumox = (lista.getString("cod_insumo"));
                auxCantDetalle = Float.parseFloat((lista.getString("cantidad")));
                sentencia2 = (Statement) conexion.createStatement();
                ResultSet lista2 = sentencia2.executeQuery("SELECT * FROM insumos where cod_insumo ='" + auxCodInsumox + "'");

                while (lista2.next()) {

                    auxStockInsumo = Float.parseFloat((lista2.getString("stock_actual")));

                }
               
                auxStockVirtual = auxStockInsumo / auxCantDetalle;
                
                if (swValorMin == 1) {
                    valorMinimo = auxStockVirtual;
                    swValorMin = 2;
                }
                if (swValorMin == 2) {
                    if (auxStockVirtual < valorMinimo) {
                        valorMinimo = auxStockVirtual;
                    }
                }

            }
        } catch (SQLException e) {
            msj = "no se pudo seleccionar";
        }

        returnStockVirtual = Float.toString(valorMinimo);

        return returnStockVirtual;
    }

    public void llenarColI() {
        /*esto va aqui por que si lo pongo en la funcion
         , recibiria un valor nulo al ralizar calculos*/
        TableColumn tcCod = new TableColumn(0);
        TableColumn tcNom = new TableColumn(1);
        TableColumn tcStockA = new TableColumn(2);
        tblInsProd.addColumn(tcCod);
        tblInsProd.addColumn(tcNom);
        tblInsProd.addColumn(tcStockA);
    }

    public void llenarColP() {
        TableColumn tcCod = new TableColumn(0);
        TableColumn tcNom = new TableColumn(1);
        TableColumn tcStockVirtual = new TableColumn(3);
        tblInsProd.addColumn(tcCod);
        tblInsProd.addColumn(tcNom);
        tblInsProd.addColumn(tcStockVirtual);

    }

    public void llenarTablaI() {
        String auxCod = "";
        String auxStoA = "";
        String auxNom = "";
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
                auxNom = (lista.getString("nombre_insumo"));               
                DefaultTableModel modelo = (DefaultTableModel) tblInsProd.getModel();
                modelo.addRow(new Object[]{auxCod, auxNom, auxStoA});
                cont = cont + 1;           
            }
        } catch (SQLException e) {
            msj = "no se pudo seleccionar";
        }
    }

    public void llenarTablaP() {

        String auxCod = "";
        String auxNom = "";
        String auxPre = "";
        String auxStockVirtual = "";
        try {
            
            sentencia = (Statement) conexion.createStatement();
            ResultSet lista = sentencia.executeQuery("SELECT * FROM producto");
            int cont = 0;
            while (lista.next()) {
                auxCod = (lista.getString("cod_producto"));        
                auxNom = (lista.getString("nombre_producto")); 
                auxPre = (lista.getString("precio"));         
                auxStockVirtual = calcularStockVirtual(auxCod);
            
                DefaultTableModel modelo = (DefaultTableModel) tblInsProd.getModel();
                modelo.addRow(new Object[]{auxCod, auxNom, "vaciox", auxStockVirtual});
                cont = cont + 1;

            }
        } catch (SQLException e) {
            msj = "no se pudo seleccionar";
            
        }
        
        
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

    //INSERTAR
    public void insertar(String sql) {
        try {
            sentencia.executeUpdate(sql);
        } catch (SQLException e) {
            msj = "ERROR numero demasiado grande para el programa";
             JOptionPane.showMessageDialog(null, "Numero demasiado grande para el programa", "ERROR", JOptionPane.INFORMATION_MESSAGE);

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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        btnRegresar = new javax.swing.JButton();
        lblFecha = new javax.swing.JLabel();
        lblCodigoIns = new javax.swing.JLabel();
        txtFiltro = new javax.swing.JTextField();
        lblCantidad = new javax.swing.JLabel();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblInsProd = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        chkIngreso = new javax.swing.JCheckBox();
        chkPerdida = new javax.swing.JCheckBox();
        txtCodIns = new javax.swing.JTextField();
        chkProducto = new javax.swing.JCheckBox();
        chkInsumo = new javax.swing.JCheckBox();
        lblFecha2 = new javax.swing.JTextField();
        lblBuscarPor = new javax.swing.JLabel();
        cmbFiltro = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Seleccione");

        jLabel2.setText("Insumo:");

        txtCantidad.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });

        btnRegresar.setText("Atrás");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        lblFecha.setText("Fecha:");

        lblCodigoIns.setText("Codigo:");

        txtFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFiltroKeyTyped(evt);
            }
        });

        lblCantidad.setText("Cantidad:");

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel3.setText("Control Stock");

        tblInsProd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Stock Act.", "Stock Virt"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblInsProd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblInsProdMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblInsProd);

        chkIngreso.setText("Ingreso / Donación");
        chkIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkIngresoActionPerformed(evt);
            }
        });

        chkPerdida.setText("Pérdida / Merma");
        chkPerdida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPerdidaActionPerformed(evt);
            }
        });

        txtCodIns.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtCodIns.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodInsActionPerformed(evt);
            }
        });
        txtCodIns.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodInsKeyTyped(evt);
            }
        });

        chkProducto.setText("Tabla Producto");
        chkProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkProductoActionPerformed(evt);
            }
        });

        chkInsumo.setText("Tabla Insumo");
        chkInsumo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkInsumoActionPerformed(evt);
            }
        });

        lblFecha2.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        lblFecha2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblFecha2ActionPerformed(evt);
            }
        });
        lblFecha2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lblFecha2KeyTyped(evt);
            }
        });

        lblBuscarPor.setText("Buscar por:");

        cmbFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Código", "Nombre", "Stock" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblFecha)
                        .addGap(18, 18, 18)
                        .addComponent(lblFecha2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRegresar))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblBuscarPor)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chkIngreso)
                            .addComponent(chkPerdida)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(chkProducto)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(chkInsumo))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblCodigoIns)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCodIns, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblCantidad)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnAceptar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelar)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFecha)
                    .addComponent(btnRegresar)
                    .addComponent(jLabel3)
                    .addComponent(lblFecha2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addComponent(chkIngreso)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkPerdida)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(chkProducto)
                    .addComponent(chkInsumo))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigoIns)
                    .addComponent(txtCodIns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCantidad)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBuscarPor)
                    .addComponent(cmbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        //new fLogin().setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed
    public Integer validarBtnAceptarIns() {
        Integer swAceptar = 0;    
        Integer sw = 0;    
         if (txtCodIns.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo codigo vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } 
        if (txtCantidad.getText().isEmpty() ) {
      
            JOptionPane.showMessageDialog(null, "ERROR! Campo cantidad vacio.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        }
        

        if (sw == 0) {

            /*en esta segunda validacion necesitamos que exista el rutprov por lo que debe eser 0*/
           /*creamos variables necesarias para el prog y resto de validaciones*/
            Integer auxCodIns = Integer.parseInt(txtCodIns.getText());

            String query = "";
            ResultSet existe;

            try {
                sentencia = (Statement) conexion.createStatement();
                query = "SELECT * FROM insumos WHERE cod_insumo = " + auxCodIns;       
                existe = sentencia.executeQuery(query);

                if (existe.next() == false) {
                    JOptionPane.showMessageDialog(null, "ERROR!NO EXISTE insumo.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
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

    
      
public Integer validarBtnAceptarPro() {
        Integer swAceptar = 0;

       
        Integer sw = 0;

        if (txtCodIns.getText().isEmpty() || txtCantidad.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "ERROR! Campos vacios.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {

        }

        if (sw == 0) {

            /*en esta segunda validacion necesitamos que exista el rutprov por lo que debe eser 0*/
           /*creamos variables necesarias para el prog y resto de validaciones*/
            Integer auxCodIns = Integer.parseInt(txtCodIns.getText());

            String query = "";
            ResultSet existe;

            try {
                sentencia = (Statement) conexion.createStatement();
                query = "SELECT * FROM producto WHERE cod_producto = " + auxCodIns;
             
                existe = sentencia.executeQuery(query);

                if (existe.next() == false) {
                    JOptionPane.showMessageDialog(null, "ERROR!NO EXISTE producto.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
                    sw = 1;
                } else {

                    JOptionPane.showMessageDialog(null, "FELICIDADES EXISTE el producto .", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {

                sw = 1;
            }
        }

        
        swAceptar = sw;
        return swAceptar;
    }

    ;







    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        Integer sw = 0;
  swAntiRepet = 0;
        if (chkInsumo.isSelected()) {
        
            sw = validarBtnAceptarIns();
            
            if (sw == 0) {
                if (chkIngreso.isSelected()) {
                    descontarAumentarInsumo(txtCodIns.getText(), 1, Float.parseFloat(txtCantidad.getText()));
                }
                if (chkPerdida.isSelected()) {
                    descontarAumentarInsumo(txtCodIns.getText(), 2, Float.parseFloat(txtCantidad.getText()));
                
                }
                if (chkIngreso.isSelected() || chkPerdida.isSelected()) {
                } else {
                 }
                limpiarCampos();
              
               
                limpiarTabla();
                llenarColI();
                llenarTablaI();
               
            }
        }

       if (chkProducto.isSelected()) {
            Integer auxCodProd2 = Integer.parseInt(txtCodIns.getText());
            sw = validarProdNegativo(auxCodProd2);
            
            if (sw == 0) {
                
                if (chkIngreso.isSelected() || chkPerdida.isSelected()) {
                    
                    Integer auxCodProd = Integer.parseInt(txtCodIns.getText());
                    String auxCodIns = "";
                    float auxCant = 0;
                 
                    if (chkIngreso.isSelected()) {
                    descontarAumentarInsumosAsociadoAProducto(auxCodIns, 1, auxCant);
                    }
                      if (chkPerdida.isSelected()) {
                    descontarAumentarInsumosAsociadoAProducto(auxCodIns, 2, auxCant);
                    }
                      
              
                } else {
                    JOptionPane.showMessageDialog(null, msj, "ERROR NO HAY CHK ACTIVADOS", JOptionPane.INFORMATION_MESSAGE);

                }
                 
               
                limpiarTabla();
                llenarColP();
                llenarTablaP();
                 limpiarCampos();
            }
        }
        
        
        // TODO add your handling code here:        // TODO add your handling code here:
    }//GEN-LAST:event_btnAceptarActionPerformed

    public void limpiarTabla() {
       
        try {
            DefaultTableModel modelo = (DefaultTableModel) tblInsProd.getModel();
            int filas = tblInsProd.getRowCount();
            for (int i = 0; filas > i; i++) {
                modelo.removeRow(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
        }

        TableColumnModel tcm = tblInsProd.getColumnModel();

        int filas = tblInsProd.getColumnCount();
        for (int i = 0; filas > i; i++) {
            TableColumn columnaABorrar = tcm.getColumn(0);
            tblInsProd.removeColumn(columnaABorrar);
        }

    }

    public void limpiarCampos() {
        txtCodIns.setText("");
        txtFiltro.setText("");
        txtCantidad.setText("");

    }


public void descontarAumentarInsumosAsociadoAProducto(String paramCodPro, Integer accionx, float cantidadx){
                    Integer auxCodProd = Integer.parseInt(txtCodIns.getText());
                    String auxCodIns = "";
                    float auxCant = cantidadx;
                 
                    try {   
                    

                        sentencia = (Statement) conexion.createStatement();
                        ResultSet lista = sentencia.executeQuery("SELECT * FROM detalle_producto WHERE cod_producto='" + auxCodProd + "'");
                        int cont = 0;
                        while (lista.next()) {
                            auxCodIns = lista.getString("cod_insumo");
                            auxCant = (Float.parseFloat(lista.getString("cantidad"))) * (Float.parseFloat(txtCantidad.getText()));
                            descontarAumentarInsumo(auxCodIns, accionx, auxCant);
                     }                       
                    } catch (SQLException e) {
                        if (swAntiRepet ==0 ){
                          msj = "ERROR numero demasiado alto para ser ingresado en el programa";
                        JOptionPane.showMessageDialog(null, msj, "INFORMACION", JOptionPane.INFORMATION_MESSAGE);       
                         swAntiRepet = 1;
                        }
                    }

    }

    
    
    public void descontarAumentarInsumo(String paramCodIns, Integer accion, float cantidad) {
         int switchNoNegativo = 0;
        String auxCodIns = paramCodIns;
        float  auxMontoBD = 0;
        float auxMontoForm = cantidad;
        float auxMontoNuevo = 0;
       
        String query = "";
        String auxTipoAjuste = "X";
        String auxObs = "Nada";
        
        try {
            sentencia = (Statement) conexion.createStatement();

            ResultSet lista = sentencia.executeQuery("SELECT * FROM insumos where cod_insumo ='" + auxCodIns + "'");

            int cont = 0;
            while (lista.next()) {
                auxMontoBD = Float.parseFloat((lista.getString("stock_actual")));
             if (accion == 1) {
                  
                    auxMontoNuevo = auxMontoBD + auxMontoForm;
                    auxTipoAjuste = "D";
                    auxObs = "Donacion";
                }

                if (accion == 2) {
                  
                    auxMontoNuevo = auxMontoBD - auxMontoForm;
                    auxTipoAjuste = "M";
                    auxObs = "Merma";
                }

                if (auxMontoNuevo < 0) {

                    switchNoNegativo = 1;
                    JOptionPane.showMessageDialog(null, "Error no se puede eliminar una cantidad que no dispone", "ERROR", JOptionPane.INFORMATION_MESSAGE);

                }
 
                cont = cont + 1;

                }
        } catch (SQLException e) {
              if (swAntiRepet ==0 ){
                          msj = "ERROR numero demasiado alto para el programa";
                        JOptionPane.showMessageDialog(null, msj, "INFORMACION", JOptionPane.INFORMATION_MESSAGE);       
                         swAntiRepet = 1;
                        }
        }

        if (switchNoNegativo == 0) {

            ResultSet existe;

            try {

                query = "SELECT * FROM insumos where cod_insumo ='" + auxCodIns + "'";
                existe = sentencia.executeQuery(query);
                if (existe.next() == true) {
                String sql = "UPDATE insumos SET stock_actual='" + auxMontoNuevo + "'WHERE cod_insumo ='" + auxCodIns + "'";
                  
                    try {
                        sentencia.executeUpdate(sql);
                            } catch (SQLException e) {
                          if (swAntiRepet ==0 ){
                            msj = "ERROR numero demasiado alto para el programa";
                        JOptionPane.showMessageDialog(null, msj, "INFORMACION", JOptionPane.INFORMATION_MESSAGE);       
                         swAntiRepet = 1;
                        }   }

                    msj = "Modificado";
                 } else {
                    msj = "Error , ingreso una cantidad que no puede existir";
                    JOptionPane.showMessageDialog(null, msj, "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {
                 if (swAntiRepet ==0 ){
                             msj = "ERROR numero demasiado alto para el programa";
                        JOptionPane.showMessageDialog(null, msj, "INFORMACION", JOptionPane.INFORMATION_MESSAGE);       
                         swAntiRepet = 1;
                        }
            }

            
            Integer auxCodAjuste = calcCodAjuste();
            String auxFechaAjuste = lblFecha2.getText();
          
            String sql = "INSERT INTO ajuste_stock VALUES (" + auxCodAjuste + "," + auxCodIns + "," + auxMontoForm + ",'" + auxFechaAjuste + "','" + auxTipoAjuste + "','" + auxObs + "')";
            insertar(sql);
         
               
            limpiarTabla();
            
            if (swAntiRepet==0){
              msj = "Datos guardados";
              JOptionPane.showMessageDialog(null, msj, "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
         
            swAntiRepet = 1;
            }
            
        }
        
    }


    private void tblInsProdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblInsProdMouseClicked
        if (tblInsProd.getRowCount() > 0) {
            int sel = tblInsProd.getSelectedRow();
            Object cod = tblInsProd.getValueAt(sel, 0);
            String auxCode = cod.toString();

            txtCodIns.setText(auxCode);

        }
    }//GEN-LAST:event_tblInsProdMouseClicked

    private void txtCodInsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodInsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodInsActionPerformed

    private void chkProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkProductoActionPerformed
        if (chkInsumo.isSelected()) {
            chkInsumo.setSelected(false);
        }
         if (chkProducto.isSelected()) {
            chkProducto.setSelected(true);
        }
         else{
              chkProducto.setSelected(true);
         }
       
        limpiarTabla();
        llenarColP();
        llenarTablaP();
        // TODO add your handling code here:
    }//GEN-LAST:event_chkProductoActionPerformed

    private void chkInsumoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkInsumoActionPerformed
        if (chkProducto.isSelected()) {
            chkProducto.setSelected(false);
        }
        if (chkInsumo.isSelected()) {
            chkInsumo.setSelected(true);
        }
        else{
        chkInsumo.setSelected(true);
        }

        limpiarTabla();
        TableColumn tcCod = new TableColumn(0);
        TableColumn tcNom = new TableColumn(1);
        TableColumn tcStockA = new TableColumn(2);

        tblInsProd.addColumn(tcCod);
        tblInsProd.addColumn(tcNom);
        tblInsProd.addColumn(tcStockA);
        llenarTablaI();     // TODO add your handling code here:
    }//GEN-LAST:event_chkInsumoActionPerformed

    private void chkIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkIngresoActionPerformed
        if (chkPerdida.isSelected()) {
            chkPerdida.setSelected(false);
        }        // TODO add your handling code here:
         if (chkIngreso.isSelected()) {
           chkIngreso.setSelected(true);
        } 
         else{
              chkIngreso.setSelected(true);
         }
        
    }//GEN-LAST:event_chkIngresoActionPerformed

    private void chkPerdidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPerdidaActionPerformed
        if (chkIngreso.isSelected()) {
            chkIngreso.setSelected(false);
        }  
        if (chkPerdida.isSelected()) {
            chkPerdida.setSelected(true);
        }  
        else{
            chkPerdida.setSelected(true);
        }
        
        // TODO add your handling code here:
    }//GEN-LAST:event_chkPerdidaActionPerformed

    private void txtCodInsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodInsKeyTyped
        int lar = 5;

        if (txtCodIns.getText().length() >= lar) {
            evt.consume();
        }

        char c = evt.getKeyChar();

        if (c < '0' || c > '9') {
            evt.consume();
        }            // TODO add your handling code here:
    }//GEN-LAST:event_txtCodInsKeyTyped

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        int lar = 4;

        if (txtCantidad.getText().length() > lar) {
            evt.consume();
        }

        char c = evt.getKeyChar();

        if  ((c < '0' || c > '9') && (c != '.' )) {
            evt.consume();
        }   // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void lblFecha2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblFecha2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblFecha2ActionPerformed

    private void lblFecha2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblFecha2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_lblFecha2KeyTyped

    private void txtFiltroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltroKeyTyped
 if (chkInsumo.isSelected()) {
          
        
        txtFiltro.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (txtFiltro.getText());
                txtFiltro.setText(cadena);
                repaint();
                filtro();
            }
        });
        trsFiltro = new TableRowSorter(tblInsProd.getModel());
     tblInsProd.setRowSorter(trsFiltro);   
 }// TODO add your handling code here:
    }//GEN-LAST:event_txtFiltroKeyTyped

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
 limpiarCampos();        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(formPerdida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formPerdida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formPerdida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formPerdida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formPerdida().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox chkIngreso;
    private javax.swing.JCheckBox chkInsumo;
    private javax.swing.JCheckBox chkPerdida;
    private javax.swing.JCheckBox chkProducto;
    private javax.swing.JComboBox<String> cmbFiltro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblBuscarPor;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblCodigoIns;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JTextField lblFecha2;
    private javax.swing.JTable tblInsProd;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodIns;
    private javax.swing.JTextField txtFiltro;
    // End of variables declaration//GEN-END:variables
}
