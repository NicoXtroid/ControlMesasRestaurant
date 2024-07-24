
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.Statement;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
//import javax.swing.table.DefaultTableModel;
import javax.swing.table.*;
import java.util.logging.*;
import javax.swing.RowFilter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class formInsumos extends javax.swing.JFrame {

    private Statement sentencia;
    /*puedo ocupar sentencias de control sql*/
    private Connection conexion;
    /*objeto que permite conectar mi base datos con programa*/
    private String nomBD = "bussandwicheria";
    private String usuario = "root";
    private String password = "informatica";
    private String msj;
    private TableRowSorter trsFiltro;

    /*
    private String nomBD="nicolas.huerta";
    private String usuario="nicolas.huerta";
    private String password="inf123pass";
     */ /* 
     private String nomBD="bussandwicheria";
    private String usuario="bussandwicheria";
    private String password="bussandwicheria"; */

    public formInsumos() {
        initComponents();
        conectar();
        this.setLocationRelativeTo(null);
        DefaultTableModel modelo = (DefaultTableModel) tblInsumo.getModel();
        this.getContentPane().setBackground(Color.orange);
    }

    public formInsumos(String ID, String Pass) {
        initComponents();
        conectar();
        llenarTabla();
        this.setLocationRelativeTo(null);
        //test.setText("USUARIO: " + ID);
        llenarCombo();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        txtFecha.setText(dtf.format(localDate));
        this.getContentPane().setBackground(Color.orange);

        setIcon();
    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagen/icono.png")));
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

    //INSERTAR
    public void insertar(String sql) {
        try {
            sentencia.executeUpdate(sql);
            msj = "Datos guardados";
            JOptionPane.showMessageDialog(null, msj, "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
            // lblEstado.setText(msj);
        } catch (SQLException e) {
            msj = "no ingreso";
        }
    }

    //ACTUALIZAR
    //LLENAR TABLA
    public void llenarTabla() {
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
        int cat;

        try {
            sentencia = (Statement) conexion.createStatement();
            ResultSet lista = sentencia.executeQuery("SELECT * FROM insumos");
            int cont = 0;
            while (lista.next()) {
                auxCod = (lista.getString("cod_insumo")); // System.out.println(auxCod);
                auxStoA = (lista.getString("stock_actual")); //  System.out.println(auxStoA);
                auxStoC = (lista.getString("stock_critico"));  //System.out.println(auxStoC);
                tipo = (lista.getString("tipo_insumo"));  //System.out.println(auxTipo);
                auxNom = (lista.getString("nombre_insumo"));  //System.out.println(auxNom);
                cat = (lista.getInt("cod_categoria"));  //System.out.println(auxCat);
                auxDes = (lista.getString("descripcion"));
                uni = (lista.getString("uni_medida"));

                switch (tipo) {
                    case "M":
                        auxTipo = "Materia prima";
                        break;
                    case "P":
                        auxTipo = "Producto";
                        break;
                }

                switch (uni) {
                    case "Un":
                        auxUni = "Unidad";
                        break;
                    case "Kl":
                        auxUni = "Kilo";
                        break;
                    case "Lt":
                        auxUni = "Litro";
                        break;
                }

                try {
                    sentencia = (Statement) conexion.createStatement();
                    String queryz = "SELECT * from categoria";
                    ResultSet listaz = sentencia.executeQuery(queryz);
                    while (listaz.next() == true) {
                        String nomCat = (listaz.getString("nombre_categoria"));
                        //int cate = Integer.parseInt(cat);
                        int idCat = (listaz.getInt("cod_categoria"));

                        if (cat == idCat) {
                            auxCat = nomCat;
                        }
                    }
                } catch (Exception e) {

                }

                /*
                switch (cat) {
                    case "1":
                        auxCat = "Vegetales";
                        break;
                    case "2":
                        auxCat = "Carnes";
                        break;
                    case "3":
                        auxCat = "Fiambreria";
                        break;
                    case "4":
                        auxCat = "Lacteos";
                        break;
                    case "5":
                        auxCat = "Bebestibles";
                        break;
                    case "6":
                        auxCat = "Abarrotes";
                        break;
                } */

 /*               
                tblProd[cont][1].addItem(auxCod);
                cmbNom.addItem(lista.getString("nombre"));                
                String[] fila = {auxCod,auxNom,auxPre};
                tblProd.addRow(fila); */
                DefaultTableModel modelo = (DefaultTableModel) tblInsumo.getModel();
                modelo.addRow(new Object[]{auxCod, auxNom, auxDes, auxTipo, auxUni, auxStoA, auxStoC, auxCat});
                cont = cont + 1;

                /*
                Object datos[] = {auxRut,auxNom,auxApp,auxApm,auxDir,auxFono,auxMail};
                tblProveedores.addRow(datos); */
            }
        } catch (SQLException e) {
            msj = "no se pudo seleccionar";
        }
    }

    public void limpiarTabla() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) tblInsumo.getModel();
            int filas = tblInsumo.getRowCount();
            for (int i = 0; filas > i; i++) {
                modelo.removeRow(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
        }
    }

    public void llenarCombo() {
        cboxCategoria.removeAllItems();
        String query = "";
        ResultSet lista;
        String nomCat;
        try {
            sentencia = (Statement) conexion.createStatement();
            query = "SELECT * from categoria";
            lista = sentencia.executeQuery(query);
            while (lista.next() == true) {
                nomCat = (lista.getString("nombre_categoria"));
                cboxCategoria.addItem(nomCat);
            }
        } catch (SQLException e) {
        }

    }

    public void limpiarcampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtStockA.setText("");
        txtStockC.setText("");
        cboxUni.setSelectedIndex(0);
        cboxCategoria.setSelectedIndex(0);
        cboxTipo.setSelectedIndex(0);
    }

    public void filtro() {
        int columnaABuscar = 0;
        if (cmbFiltro.getSelectedItem() == "Código") {
            columnaABuscar = 0;
        }
        if (cmbFiltro.getSelectedItem().toString() == "Nombre") {
            columnaABuscar = 1;
        }
        if (cmbFiltro.getSelectedItem() == "Tipo Insumo") {
            columnaABuscar = 3;
        }
        if (cmbFiltro.getSelectedItem().toString() == "Categoria") {
            columnaABuscar = 7;
        }
        trsFiltro.setRowFilter(RowFilter.regexFilter(txtFiltro.getText(), columnaABuscar));
    }

    /*-----------AQUI TERMINAN LAS FUNCIONES Y SUPROCESOS QUE USAREMOS DE APOYO------------------- */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtStockA = new javax.swing.JTextField();
        txtStockC = new javax.swing.JTextField();
        lblStockA = new javax.swing.JLabel();
        lblStockC = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cboxUni = new javax.swing.JComboBox<>();
        cboxCategoria = new javax.swing.JComboBox<>();
        lblTipoSto = new javax.swing.JLabel();
        lblCategoria = new javax.swing.JLabel();
        btnAgregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblInsumo = new javax.swing.JTable();
        btnVolver = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        txtDescripcion = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cboxTipo = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cmbFiltro = new javax.swing.JComboBox<>();
        txtFiltro = new javax.swing.JTextField();
        txtFecha = new javax.swing.JTextField();

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
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblCodigo.setText("Código:");

        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoKeyTyped(evt);
            }
        });

        lblNombre.setText("Nombre:");

        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

        txtStockA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtStockAKeyTyped(evt);
            }
        });

        txtStockC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtStockCKeyTyped(evt);
            }
        });

        lblStockA.setText("Stock Actual:");

        lblStockC.setText("Stock Crítico:");

        jLabel5.setText("INGRESO INSUMO");

        cboxUni.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Unidad", "Kilo", "Litro" }));

        cboxCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vegetales", "Carnes", "Fiambreria", "Lacteos", "Bebestibles", "Abarrotes" }));

        lblTipoSto.setText("Unidad de medida:");

        lblCategoria.setText("Categoría:");

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

        tblInsumo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Descripción", "Tipo Insumo", "U. Medida", "Stock Act.", "Stock Crt.", "Categoría"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblInsumo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblInsumoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblInsumo);
        if (tblInsumo.getColumnModel().getColumnCount() > 0) {
            tblInsumo.getColumnModel().getColumn(0).setResizable(false);
            tblInsumo.getColumnModel().getColumn(0).setPreferredWidth(80);
            tblInsumo.getColumnModel().getColumn(1).setResizable(false);
            tblInsumo.getColumnModel().getColumn(1).setPreferredWidth(180);
            tblInsumo.getColumnModel().getColumn(2).setResizable(false);
            tblInsumo.getColumnModel().getColumn(2).setPreferredWidth(250);
            tblInsumo.getColumnModel().getColumn(3).setResizable(false);
            tblInsumo.getColumnModel().getColumn(3).setPreferredWidth(100);
            tblInsumo.getColumnModel().getColumn(4).setResizable(false);
            tblInsumo.getColumnModel().getColumn(5).setResizable(false);
            tblInsumo.getColumnModel().getColumn(6).setResizable(false);
            tblInsumo.getColumnModel().getColumn(7).setResizable(false);
            tblInsumo.getColumnModel().getColumn(7).setPreferredWidth(80);
        }

        btnVolver.setText("Atrás");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        jLabel1.setText("Fecha:");

        jLabel2.setText("Listado de Insumos");

        jLabel3.setText("Ingreso de datos");

        jLabel4.setText("Descripción:");

        cboxTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Materia prima", "Producto" }));

        jLabel6.setText("Tipo Insumo:");

        jLabel7.setText("Buscar Por:");

        cmbFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Código", "Nombre", "Tipo Insumo", "Categoria" }));

        txtFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFiltroKeyTyped(evt);
            }
        });

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
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnVolver))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(191, 191, 191)
                                            .addComponent(jLabel5))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(jLabel3)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblCodigo)
                                                        .addComponent(jLabel4))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createSequentialGroup()
                                                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addGap(33, 33, 33)
                                                            .addComponent(lblNombre)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                            .addComponent(lblStockA)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                            .addComponent(txtStockA, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(lblStockC)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                            .addComponent(txtStockC, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                            .addComponent(jLabel6)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                            .addComponent(cboxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addGap(30, 30, 30)
                                                            .addComponent(lblTipoSto)))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(cboxUni, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(lblCategoria)
                                                    .addGap(26, 26, 26)
                                                    .addComponent(cboxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18)
                                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 236, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(83, 83, 83)
                                .addComponent(btnAgregar)
                                .addGap(18, 18, 18)
                                .addComponent(btnModificar)
                                .addGap(18, 18, 18)
                                .addComponent(btnEliminar)
                                .addGap(18, 18, 18)
                                .addComponent(btnCancelar))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)
                                .addComponent(txtFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVolver)
                    .addComponent(jLabel1)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblTipoSto)
                        .addComponent(cboxUni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(cboxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStockA)
                    .addComponent(txtStockA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStockC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStockC))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCategoria))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar)
                    .addComponent(btnCancelar))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cmbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        //new fLogin().setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        String cod = txtCodigo.getText();
        String stocka = txtStockA.getText();
        String stockc = txtStockC.getText();
        String unit = (String) cboxUni.getSelectedItem();
        String nom = txtNombre.getText();
        String cate = (String) cboxCategoria.getSelectedItem();
        String des = txtDescripcion.getText();
        String tipe = (String) cboxTipo.getSelectedItem();

        int cat = 0;

        try {
            sentencia = (Statement) conexion.createStatement();
            String query = "SELECT * from categoria";
            ResultSet lista = sentencia.executeQuery(query);
            while (lista.next() == true) {
                String nomCat = (lista.getString("nombre_categoria"));
                int idCat = (lista.getInt("cod_categoria"));

                if (cate.equals(nomCat)) {
                    cat = idCat;
                }
            }
        } catch (Exception e) {

        }

        /*switch (cate) {
            case "Vegetales":
                cat = 1;
                break;
            case "Carnes":
                cat = 2;
                break;
            case "Fiambreria":
                cat = 3;
                break;
            case "Lacteos":
                cat = 4;
                break;
            case "Bebestibles":
                cat = 5;
                break;
            case "Abarrotes":
                cat = 6;
                break;
        }*/
        String tipo = "";
        switch (tipe) {
            case "Materia prima":
                tipo = "M";
                break;
            case "Producto":
                tipo = "P";
                break;
        }
        String uni = "";
        switch (unit) {
            case "Unidad":
                uni = "Un";
                break;
            case "Kilo":
                uni = "Kl";
                break;
            case "Litro":
                uni = "Lt";
                break;
        }
        int sw = 0;

        if (txtNombre.getText().isEmpty() || txtCodigo.getText().isEmpty() || txtStockA.getText().isEmpty() || txtStockA.getText().isEmpty()) {
            System.out.println("Una de las casillas vacías error ");
            JOptionPane.showMessageDialog(null, "ERROR! Campos vacios.", "¡ADVRTENCIA!", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {
            if (Integer.parseInt(txtCodigo.getText()) <= 0 || Float.parseFloat(txtStockA.getText()) <= 0 || Float.parseFloat(txtStockC.getText()) <= 0) {
                JOptionPane.showMessageDialog(null, "ERROR! Campo invalido.", "¡ADVRTENCIA", JOptionPane.INFORMATION_MESSAGE);
                sw = 1;
            }
        }

        if (sw == 0) {
            /*
            String sql="INSERT INTO insumos VALUES (" + Integer.parseInt(cod) + "," + Integer.parseInt(stocka) + "," + Integer.parseInt(stockc)  +  ",'" + tiposto + "','" + nom + "'," + cat +  ")";
            //System.out.println(sql);
            insertar(sql);  
             */
            int codE = Integer.parseInt(cod);
            String query = "";
            ResultSet existe;

            try {
                sentencia = (Statement) conexion.createStatement();
                query = "SELECT * from insumos WHERE cod_insumo=" + codE;
                existe = sentencia.executeQuery(query);
                if (existe.next() == false) {
                    //sentencia.executeUpdate("Delete from insumos WHERE cod_insumo =" + codE);

                    System.out.println(nom);

                    String sql = "INSERT INTO insumos VALUES (" + codE + ",'" + nom + "','" + des + "','" + tipo + "','" + uni + "'," + Float.parseFloat(stocka) + "," + Float.parseFloat(stockc) + "," + cat + ")";
                    System.out.println(sql);
                    insertar(sql);

                    //msj="Modificado";
                    //JOptionPane.showMessageDialog(null,msj,"INFORMACION",JOptionPane.INFORMATION_MESSAGE);            
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
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        /*
        txtCodigo.setText("");
        txtNombre.setText("");
        txtStockA.setText("");
        txtStockC.setText("");
        cboxTipoSto.setSelectedIndex(0);
        cboxCategoria.setSelectedIndex(0);
         */
        limpiarcampos();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed

        int sw = 0;
        if (txtNombre.getText().isEmpty() || txtCodigo.getText().isEmpty() || txtStockA.getText().isEmpty() || txtStockA.getText().isEmpty()) {
            System.out.println("Una de las casillas vacías error ");
            JOptionPane.showMessageDialog(null, "ERROR! Casillas vacias.", "¡ADVRTENCIA", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {
            if (Integer.parseInt(txtCodigo.getText()) <= 0 || Float.parseFloat(txtStockA.getText()) <= 0 || Float.parseFloat(txtStockC.getText()) <= 0) {
                JOptionPane.showMessageDialog(null, "ERROR! Campo invalido.", "¡ADVRTENCIA", JOptionPane.INFORMATION_MESSAGE);
                sw = 1;
            }
        }

        if (sw == 0) {
            String cod = txtCodigo.getText();
            String stocka = txtStockA.getText();
            String stockc = txtStockC.getText();
            String unit = (String) cboxUni.getSelectedItem();
            String nom = txtNombre.getText();
            String cate = (String) cboxCategoria.getSelectedItem();
            String des = txtDescripcion.getText();
            String tipe = (String) cboxTipo.getSelectedItem();

            int codE = Integer.parseInt(cod);
            String query = "";
            ResultSet existe;

            int cat = 0;
            try {
                sentencia = (Statement) conexion.createStatement();
                String queryx = "SELECT * from categoria";
                ResultSet lista = sentencia.executeQuery(queryx);
                while (lista.next() == true) {
                    String nomCat = (lista.getString("nombre_categoria"));
                    int idCat = (lista.getInt("cod_categoria"));

                    if (cate.equals(nomCat)) {
                        cat = idCat;
                    }
                }
            } catch (Exception e) {

            }

            /*
            switch (cate) {
                case "Vegetales":
                    cat = 1;
                    break;
                case "Carnes":
                    cat = 2;
                    break;
                case "Fiambreria":
                    cat = 3;
                    break;
                case "Lacteos":
                    cat = 4;
                    break;
                case "Bebestibles":
                    cat = 5;
                    break;
                case "Abarrotes":
                    cat = 6;
                    break;
            } */
            String tipo = "";
            switch (tipe) {
                case "Materia prima":
                    tipo = "M";
                    break;
                case "Producto":
                    tipo = "P";
                    break;
            }
            String uni = "";
            switch (unit) {
                case "Unidad":
                    uni = "Un";
                    break;
                case "Kilo":
                    uni = "Kl";
                    break;
                case "Litro":
                    uni = "Lt";
                    break;
            }

            try {
                sentencia = (Statement) conexion.createStatement();
                query = "SELECT * from insumos WHERE cod_insumo=" + codE;
                existe = sentencia.executeQuery(query);
                if (existe.next() == true) {
                    sentencia.executeUpdate("Delete from insumos WHERE cod_insumo =" + codE);

                    String sql = "INSERT INTO insumos VALUES (" + codE + ",'" + nom + "','" + des + "','" + tipo + "','" + uni + "'," + Float.parseFloat(stocka) + "," + Float.parseFloat(stockc) + "," + cat + ")";
                    insertar(sql);

                    msj = "Modificado";
                    JOptionPane.showMessageDialog(null, msj, "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
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
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        int sw = 0;
        if (txtNombre.getText().isEmpty() || txtCodigo.getText().isEmpty() || txtStockA.getText().isEmpty() || txtStockA.getText().isEmpty()) {
            System.out.println("Una de las casillas vacías error ");
            JOptionPane.showMessageDialog(null, "ERROR! Casillas vacias.", "¡ADVRTENCIA", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        } else {
            if (Integer.parseInt(txtCodigo.getText()) <= 0 || Float.parseFloat(txtStockA.getText()) <= 0 || Float.parseFloat(txtStockC.getText()) <= 0) {
                JOptionPane.showMessageDialog(null, "ERROR! Codigo invalido.", "¡ADVRTENCIA", JOptionPane.INFORMATION_MESSAGE);
                sw = 1;
            }
        }

        if (sw == 0) {

            String codi = txtCodigo.getText();
            String query = "";
            ResultSet existe;
            int codE = Integer.parseInt(codi);

            System.out.println(codE);
            try {
                sentencia = (Statement) conexion.createStatement();
                query = "SELECT * from insumos WHERE cod_insumo =" + codE;
                System.out.println(query);
                existe = sentencia.executeQuery(query);
                System.out.println(existe);
                if (existe.next() == true) {

                    sentencia.executeUpdate("Delete from insumos WHERE cod_insumo =" + codE);

                    msj = "ELIMINADO";
                    JOptionPane.showMessageDialog(null, msj, "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    msj = "No existe insumo";
                    JOptionPane.showMessageDialog(null, msj, "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {
                msj = "No existe persona, no se puede eliminar";
            }
            limpiarTabla();
            llenarTabla();
            limpiarcampos();

        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tblInsumoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblInsumoMouseClicked
        if (tblInsumo.getRowCount() > 0) {
            int sel = tblInsumo.getSelectedRow();
            Object cod = tblInsumo.getValueAt(sel, 0);
            String auxCode = cod.toString();

            Object nom = tblInsumo.getValueAt(sel, 1);
            String auxNom = nom.toString();

            Object des = tblInsumo.getValueAt(sel, 2);
            String auxDes = des.toString();

            Object tipoS = tblInsumo.getValueAt(sel, 3);
            String auxTipo = tipoS.toString();

            Object uni = tblInsumo.getValueAt(sel, 4);
            String auxUni = uni.toString();

            Object stoA = tblInsumo.getValueAt(sel, 5);
            String auxAct = stoA.toString();

            Object stoC = tblInsumo.getValueAt(sel, 6);
            String auxCri = stoC.toString();

            Object cat = tblInsumo.getValueAt(sel, 7);
            String auxCat = cat.toString();

            //NICO R, $%$%$y DIEGO JARA :V V:%&$· TODOS LOS DERECHOS RESERVADOS//
            txtCodigo.setText(auxCode);
            txtNombre.setText(auxNom);
            txtStockA.setText(auxAct);
            txtStockC.setText(auxCri);
            txtDescripcion.setText(auxDes);
            
            int cate = 0;

            try {
                sentencia = (Statement) conexion.createStatement();
                String query = "SELECT * from categoria";
                ResultSet lista = sentencia.executeQuery(query);
                while (lista.next() == true) {
                    String nomCat = (lista.getString("nombre_categoria"));
                    int idCat = (lista.getInt("cod_categoria"));
                    
                    if (auxCat.equals(nomCat)) {
                        cate = idCat;
                        System.out.println(auxCat);
                        System.out.println(nomCat + " " + idCat);
                    }
                }
            } catch (Exception e) {

            }
            cboxCategoria.setSelectedIndex(cate-1);
            /*
            switch (auxCat) {
                case "1":
                    cboxCategoria.setSelectedIndex(0);
                    break;
                case "2":
                    cboxCategoria.setSelectedIndex(1);
                    break;
                case "3":
                    cboxCategoria.setSelectedIndex(2);
                    break;
                case "4":
                    cboxCategoria.setSelectedIndex(3);
                    break;
                case "5":
                    cboxCategoria.setSelectedIndex(4);
                    break;
                case "6":
                    cboxCategoria.setSelectedIndex(5);
                    break;
            }
            */
            switch (auxUni) {
                case "Unidad":
                    cboxUni.setSelectedIndex(0);
                    break;
                case "Kilo":
                    cboxUni.setSelectedIndex(1);
                    break;
                case "Litro":
                    cboxUni.setSelectedIndex(2);
                    break;
            }

            switch (auxTipo) {
                case "Materia Prima":
                    cboxTipo.setSelectedIndex(0);
                    break;
                case "Producto":
                    cboxTipo.setSelectedIndex(1);
                    break;
            }

        }
    }//GEN-LAST:event_tblInsumoMouseClicked

    private void txtCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyTyped
        int lar = 4;

        if (txtCodigo.getText().length() > lar) {
            evt.consume();
        }

        char c = evt.getKeyChar();

        if (c < '0' || c > '9') {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoKeyTyped

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        int lar = 29;

        if (txtNombre.getText().length() > lar) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtStockAKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStockAKeyTyped
        int lar = 5;

        if (txtStockA.getText().length() > lar) {
            evt.consume();
        }

        char c = evt.getKeyChar();

        if ((c < '0' || c > '9') && (c != '.')) {
            evt.consume();
        }     
        
    }//GEN-LAST:event_txtStockAKeyTyped

    private void txtStockCKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStockCKeyTyped
        int lar = 3;

        if (txtStockC.getText().length() > lar) {
            evt.consume();
        }

        char c = evt.getKeyChar();

        if ((c < '0' || c > '9') && (c != '.')) {
            evt.consume();
        }
    }//GEN-LAST:event_txtStockCKeyTyped

    private void txtFiltroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltroKeyTyped
        txtFiltro.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (txtFiltro.getText());
                txtFiltro.setText(cadena);
                repaint();
                filtro();
            }
        });
        trsFiltro = new TableRowSorter(tblInsumo.getModel());
        tblInsumo.setRowSorter(trsFiltro);
    }//GEN-LAST:event_txtFiltroKeyTyped

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
            java.util.logging.Logger.getLogger(formInsumos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formInsumos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formInsumos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formInsumos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formInsumos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> cboxCategoria;
    private javax.swing.JComboBox<String> cboxTipo;
    private javax.swing.JComboBox<String> cboxUni;
    private javax.swing.JComboBox<String> cmbFiltro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblCategoria;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblStockA;
    private javax.swing.JLabel lblStockC;
    private javax.swing.JLabel lblTipoSto;
    private javax.swing.JTable tblInsumo;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtFiltro;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtStockA;
    private javax.swing.JTextField txtStockC;
    // End of variables declaration//GEN-END:variables
}
