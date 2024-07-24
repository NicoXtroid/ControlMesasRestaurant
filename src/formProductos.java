
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.table.*;
import java.util.logging.*;
import javax.swing.RowFilter;

public class formProductos extends javax.swing.JFrame {

    private Statement sentencia;
    /*puedo ocupar sentencias de control sql*/
    private Connection conexion;
    /*objeto que permite conectar mi base datos con programa*/
    private String nomBD = "bussandwicheria";
    private String usuario = "root";
    private String password = "informatica";
    private String msj;
    private TableRowSorter trsFiltro;
    private TableRowSorter trsFiltro2;

    public formProductos() {
        initComponents();
        conectar();
        this.setLocationRelativeTo(null);
        DefaultTableModel modelo = (DefaultTableModel) tblProd.getModel();
        DefaultTableModel modeloI = (DefaultTableModel) tblIns.getModel();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        txtFecha.setText(dtf.format(localDate));
        this.getContentPane().setBackground(Color.orange);
        setIcon();
    }

    public formProductos(String ID) {
        initComponents();
        conectar();
        llenarTablaP();
        llenarTablaI();
        this.setLocationRelativeTo(null);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        txtFecha.setText(dtf.format(localDate));
        this.getContentPane().setBackground(Color.orange);
        setIcon();
    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagen/icono.png")));
    }

    //conectar
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
            msj = "Datos guardados";
            System.out.print(msj);
        } catch (SQLException e) {
            System.out.print(e.getMessage());
            msj = "no ingreso";
        }
    }

    public void llenarTablaP() {
        String auxCod = "";
        String auxNom = "";
        String auxPre = "";

        try {
            sentencia = (Statement) conexion.createStatement();
            ResultSet lista = sentencia.executeQuery("SELECT * FROM producto");
            int cont = 0;
            while (lista.next()) {
                auxCod = (lista.getString("cod_producto"));  
                auxNom = (lista.getString("nombre_producto")); 
                auxPre = (lista.getString("precio"));                     

                DefaultTableModel modelo = (DefaultTableModel) tblProd.getModel();
                modelo.addRow(new Object[]{auxCod, auxNom, auxPre});
                cont = cont + 1;

            }
        } catch (SQLException e) {
            msj = "no se pudo seleccionar";
        }
    }

    public void limpiarTablaP() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) tblProd.getModel();
            int filas = tblProd.getRowCount();
            for (int i = 0; filas > i; i++) {
                modelo.removeRow(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
        }
    }

    //llenar tabla insumos
    public void llenarTablaI() {
        String auxCod = "";
        String auxNom = "";
        String tipo = "";
        String auxTipo = "";
        String auxCat = "";

        try {
            System.out.println("llenar tabala");
            sentencia = (Statement) conexion.createStatement();
            ResultSet lista = sentencia.executeQuery("SELECT * FROM insumos");
            int cont = 0;
            while (lista.next()) {
                auxCod = (lista.getString("cod_insumo"));
                auxNom = (lista.getString("nombre_insumo"));
                tipo = (lista.getString("tipo_insumo"));  
                String cate = (lista.getString("cod_categoria")); 

                switch (tipo) {
                    case "M":
                        auxTipo = "Materia prima";
                        break;
                    case "P":
                        auxTipo = "Producto";
                        break;
                }
                
                int cat = Integer.parseInt(cate);
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

                DefaultTableModel modeloI = (DefaultTableModel) tblIns.getModel();
                modeloI.addRow(new Object[]{auxCod, auxNom, auxTipo, auxCat});
                cont = cont + 1;
            }
        } catch (SQLException e) {
            msj = "no se pudo seleccionar";
        }
    }

    public void vaciarTablaPI() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) tblPI.getModel();
            int filas = tblPI.getRowCount();
            for (int i = 0; filas > i; i++) {
                modelo.removeRow(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
        }
    }

    public void limpiarCampos1() {
        txtCodP.setText("");
        txtNomP.setText("");
        txtPreP.setText("");
    }

    public void limpiarCampos2() {
        txtCodI.setText("");
        txtCantI.setText("");
    }
    
    public void filtro() {
        int columnaABuscar = 0;
        if (cmbFiltro1.getSelectedItem() == "Código") {
            columnaABuscar = 0;
        }
        if (cmbFiltro1.getSelectedItem().toString() == "Nombre") {
            columnaABuscar = 1;
        }        
        trsFiltro.setRowFilter(RowFilter.regexFilter(txtFiltro1.getText(), columnaABuscar));
    }
    
    public void filtro2() {
        int columnaABuscar = 0;
        if (cmbFiltro2.getSelectedItem() == "Código") {
            columnaABuscar = 0;
        }
        if (cmbFiltro2.getSelectedItem().toString() == "Nombre") {
            columnaABuscar = 1;
        }
        if (cmbFiltro2.getSelectedItem() == "Tipo Insumo") {
            columnaABuscar = 2;
        }
        if (cmbFiltro2.getSelectedItem().toString() == "Categoria") {
            columnaABuscar = 3;
        }
        trsFiltro2.setRowFilter(RowFilter.regexFilter(txtFiltro2.getText(), columnaABuscar));
    }

    //////////////////////////// eventos ////////////////////////////////////
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtCodP = new javax.swing.JTextField();
        txtNomP = new javax.swing.JTextField();
        txtPreP = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPI = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblIns = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblProd = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnAgregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtCodI = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtCantI = new javax.swing.JTextField();
        btnAceptarI = new javax.swing.JButton();
        btnCancelarI = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        btnEliminarTodos = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        cmbFiltro1 = new javax.swing.JComboBox<>();
        txtFiltro1 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cmbFiltro2 = new javax.swing.JComboBox<>();
        txtFiltro2 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registro de Productos");

        txtCodP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodPActionPerformed(evt);
            }
        });
        txtCodP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodPKeyTyped(evt);
            }
        });

        txtNomP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNomPKeyTyped(evt);
            }
        });

        txtPreP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrePKeyTyped(evt);
            }
        });

        tblPI.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código Insumo", "Cantidad"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblPI);
        if (tblPI.getColumnModel().getColumnCount() > 0) {
            tblPI.getColumnModel().getColumn(0).setResizable(false);
            tblPI.getColumnModel().getColumn(1).setResizable(false);
        }

        tblIns.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código Insmo", "Nombre", "Tipo Insumo", "Categoría"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblIns.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblInsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblIns);
        if (tblIns.getColumnModel().getColumnCount() > 0) {
            tblIns.getColumnModel().getColumn(0).setResizable(false);
            tblIns.getColumnModel().getColumn(1).setResizable(false);
            tblIns.getColumnModel().getColumn(2).setResizable(false);
            tblIns.getColumnModel().getColumn(3).setResizable(false);
        }

        jLabel1.setText("Código:");

        jLabel2.setText("Nombre:");

        jLabel3.setText("Precio:");

        jLabel4.setText("Ingresar Producto");

        btnRegresar.setText("Atrás");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        tblProd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Precio"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProdMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblProd);
        if (tblProd.getColumnModel().getColumnCount() > 0) {
            tblProd.getColumnModel().getColumn(0).setResizable(false);
            tblProd.getColumnModel().getColumn(1).setResizable(false);
            tblProd.getColumnModel().getColumn(2).setResizable(false);
        }

        jLabel5.setText("Listado de Productos");

        jLabel6.setText("Ingreso de datos");

        jLabel7.setText("Listado de Insumos");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

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

        jLabel8.setText("Código:");

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

        jLabel9.setText("Cantidad:");

        txtCantI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantIKeyTyped(evt);
            }
        });

        btnAceptarI.setText("Aceptar");
        btnAceptarI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarIActionPerformed(evt);
            }
        });

        btnCancelarI.setText("Cancelar");
        btnCancelarI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarIActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel10.setText("Fecha:");

        btnEliminarTodos.setText("Limpiar Detalle");
        btnEliminarTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarTodosActionPerformed(evt);
            }
        });

        jLabel11.setText("Detalle Producto");

        txtFecha.setEditable(false);

        jLabel12.setText("Buscar por:");

        cmbFiltro1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Código", "Nombre" }));

        txtFiltro1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFiltro1KeyTyped(evt);
            }
        });

        jLabel13.setText("Buscar por:");

        cmbFiltro2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Código", "Nombre", "Tipo Insumo", "Categoria" }));

        txtFiltro2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFiltro2KeyTyped(evt);
            }
        });

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
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtCodP)
                                    .addComponent(txtNomP)
                                    .addComponent(txtPreP, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbFiltro1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(txtFiltro1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(42, 42, 42)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel11)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCodI, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCantI, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnAceptarI)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnCancelarI)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btnEliminarTodos)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(16, 16, 16)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel13)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cmbFiltro2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtFiltro2))
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(322, 322, 322)
                        .addComponent(btnRegresar))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addComponent(btnAgregar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(330, 330, 330)
                        .addComponent(jLabel4)))
                .addContainerGap(324, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnRegresar)
                        .addGap(11, 11, 11)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel13)
                                            .addComponent(cmbFiltro2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtFiltro2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel8)
                                            .addComponent(txtCodI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel9)
                                            .addComponent(txtCantI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnAceptarI)
                                            .addComponent(btnCancelarI))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnEliminarTodos))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(cmbFiltro1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtFiltro1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27)
                                .addComponent(jLabel6)
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCodP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNomP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtPreP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))))
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAgregar)
                            .addComponent(btnModificar)
                            .addComponent(btnEliminar)
                            .addComponent(btnCancelar))
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodPActionPerformed

    private void txtCodIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodIActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
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

            Object tipoS = tblIns.getValueAt(sel, 2);
            String auxTipo = tipoS.toString();

            Object cat = tblIns.getValueAt(sel, 3);
            String auxCat = cat.toString();

            txtCodI.setText(auxCode);
        }
    }//GEN-LAST:event_tblInsMouseClicked

    private void btnAceptarIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarIActionPerformed
        String code = txtCodI.getText();
        String cant = txtCantI.getText();

        DefaultTableModel modeloPI = (DefaultTableModel) tblPI.getModel();
        modeloPI.addRow(new Object[]{code, cant});
        limpiarCampos2();
    }//GEN-LAST:event_btnAceptarIActionPerformed

    private void btnCancelarIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarIActionPerformed
        limpiarCampos2();
    }//GEN-LAST:event_btnCancelarIActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        limpiarCampos1();
        limpiarCampos2();
        vaciarTablaPI();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        String codp = txtCodP.getText();
        String nomp = txtNomP.getText();
        String prep = txtPreP.getText();

        int sw = 0;
        //ingreso a tabla productos        
        if (sw == 0) {
            int codE = Integer.parseInt(codp);
            String query = "";
            ResultSet existe;

            try {
                sentencia = (Statement) conexion.createStatement();
                query = "SELECT * from producto WHERE cod_producto=" + codE;
                existe = sentencia.executeQuery(query);
                if (existe.next() == false) {                    
                    String sql = "INSERT INTO producto VALUES (" + codE + ",'" + nomp + "'," + Integer.parseInt(prep) + ")";
                    System.out.println(sql);
                    insertar(sql);

                    int largo = tblPI.getRowCount();
                    System.out.println("el largo es: " + largo);
                    for (int i = 0; i < largo; i++) {
                        Object cod = tblPI.getValueAt(i, 0);
                        int auxCode = Integer.parseInt(cod.toString());

                        Object cant = tblPI.getValueAt(i, 1);
                        float auxCant = Float.parseFloat(cant.toString());
                        int codpr = Integer.parseInt(txtCodP.getText());

                        String sql1 = "INSERT INTO detalle_producto VALUES (" + codpr + "," + auxCode + "," + auxCant + ")";
                        System.out.println(sql1);
                        insertar(sql1);
                    }

                    msj = "Producto Registrado";
                    JOptionPane.showMessageDialog(null, msj, "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    msj = "Ya existe producto";
                    JOptionPane.showMessageDialog(null, msj, "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {
                msj = "No existe persona, no se puede modificar";
            }

            System.out.println("salida for");
            limpiarTablaP();
            llenarTablaP();
            limpiarCampos1();
            limpiarCampos2();
            vaciarTablaPI();
        }
    }//GEN-LAST:event_btnAgregarActionPerformed


    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        String codp = txtCodP.getText();
        String nomp = txtNomP.getText();
        String prep = txtPreP.getText();
        int codE = Integer.parseInt(codp);
        int sw = 0;

        // borrar datos primero
        if (sw == 0) {
            String query = "";
            String query2 = "";
            ResultSet existe;
            ResultSet existe2;

            try {
                sentencia = (Statement) conexion.createStatement();
                query = "SELECT * from producto WHERE cod_producto =" + codE;                
                existe = sentencia.executeQuery(query);
                if (existe.next() == true) {

                    query2 = "SELECT * from detalle_producto WHERE cod_producto =" + codE;
                    existe2 = sentencia.executeQuery(query2);
                    if (existe2.next() == true) {
                        sentencia.executeUpdate("Delete from detalle_producto WHERE cod_producto =" + codE);
                        System.out.println("Detalle eliminado");
                    }   
                } else {
                    msj = "No existe detalle de producto";
                    JOptionPane.showMessageDialog(null, msj, "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {
                msj = "No existe producto, no se puede eliminar";
            }

            // REINGRESAR VALORES////////
            String query3 = "";
            ResultSet existe3;

            try {
                sentencia = (Statement) conexion.createStatement();
                query3 = "SELECT * from producto WHERE cod_producto=" + codE;
                existe3 = sentencia.executeQuery(query3);
                if (existe3.next() == true) {                    
                    String sql3 = "UPDATE producto SET nombre_producto='" +  nomp + "'WHERE cod_producto ='" +  codE + "'";
                    sentencia.executeUpdate(sql3);                    
                    sql3 = "UPDATE producto SET precio= " +  Integer.parseInt(prep) + " WHERE cod_producto ='" +  codE + "'";
                    sentencia.executeUpdate(sql3);                    
                    System.out.println(sql3);
                    
                    int largo = tblPI.getRowCount();                    
                    for (int i = 0; i < largo; i++) {
                        Object cod = tblPI.getValueAt(i, 0);
                        int auxCode = Integer.parseInt(cod.toString());
                        Object cant = tblPI.getValueAt(i, 1);
                        float auxCant = Float.parseFloat(cant.toString());
                        int codpr = Integer.parseInt(txtCodP.getText());
                        String sql4 = "INSERT INTO detalle_producto VALUES (" + codpr + "," + auxCode + "," + auxCant + ")";                        
                        sentencia.executeUpdate(sql4);
                    }
                    msj = "Producto Registrado";
                    JOptionPane.showMessageDialog(null, msj, "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    msj = "Ya existe producto";
                    JOptionPane.showMessageDialog(null, msj, "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {
                msj = "Error de entidad referencial";

            }
        }
        System.out.println("salida for");
        limpiarTablaP();
        llenarTablaP();
        limpiarCampos1();
        limpiarCampos2();
        vaciarTablaPI();
    }//GEN-LAST:event_btnModificarActionPerformed

    ////////// BOTON ELIMINAR //////////
    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int sw = 0;

        if (sw == 0) {
            String codi = txtCodP.getText();
            String query = "";
            String query2 = "";
            ResultSet existe;
            ResultSet existe2;
            int codE = Integer.parseInt(codi);
            try {
                sentencia = (Statement) conexion.createStatement();
                query = "SELECT * from producto WHERE cod_producto =" + codE;
                existe = sentencia.executeQuery(query);
                if (existe.next() == true) {

                    query2 = "SELECT * from detalle_producto WHERE cod_producto =" + codE;
                    existe2 = sentencia.executeQuery(query2);
                    if (existe2.next() == true) {
                        sentencia.executeUpdate("Delete from detalle_producto WHERE cod_producto =" + codE);
                        System.out.println("Detalle eliminado");
                    }
                    
                    sentencia.executeUpdate("Delete from producto WHERE cod_producto =" + codE);

                    msj = "ELIMINADO";
                    JOptionPane.showMessageDialog(null, msj, "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    msj = "No existe detalle de producto";
                    JOptionPane.showMessageDialog(null, msj, "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {
                msj = "No se puede eliminar producto por actividad referencial";
                JOptionPane.showMessageDialog(null, msj, "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
            }

            limpiarTablaP();
            llenarTablaP();
            limpiarCampos1();
            limpiarCampos2();
            vaciarTablaPI();
        }


    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tblProdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProdMouseClicked
        String codEX = "";

        if (tblProd.getRowCount() > 0) {
            int sel = tblProd.getSelectedRow();
            Object cod = tblProd.getValueAt(sel, 0);
            String auxCode = cod.toString();
            codEX = auxCode;

            Object nom = tblProd.getValueAt(sel, 1);
            String auxNom = nom.toString();

            Object pre = tblProd.getValueAt(sel, 2);
            String auxPre = pre.toString();

            txtCodP.setText(auxCode);
            txtNomP.setText(auxNom);
            txtPreP.setText(auxPre);
        }

        /////
        int auxCodPI = 0;
        float auxCant = 0;
        vaciarTablaPI();
        try {
            System.out.println("llenar tabala PI");
            sentencia = (Statement) conexion.createStatement();
            ResultSet lista = sentencia.executeQuery("SELECT * FROM detalle_producto where cod_producto =" + codEX);
            int cont = 0;
            while (lista.next()) {
                auxCodPI = (lista.getInt("cod_insumo"));
                auxCant = (lista.getFloat("cantidad")); 
                DefaultTableModel modeloPI = (DefaultTableModel) tblPI.getModel();
                modeloPI.addRow(new Object[]{auxCodPI, auxCant});
                cont = cont + 1;

            }
        } catch (SQLException e) {
            msj = "no se pudo seleccionar";
        }

    }//GEN-LAST:event_tblProdMouseClicked

    private void btnEliminarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarTodosActionPerformed
        vaciarTablaPI();
    }//GEN-LAST:event_btnEliminarTodosActionPerformed

    private void txtCodPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodPKeyTyped
        int lar = 4;

        if (txtCodP.getText().length() > lar) {
            evt.consume();
        }

        char c = evt.getKeyChar();

        if (c < '0' || c > '9') {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodPKeyTyped

    private void txtCantIKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantIKeyTyped
        int lar = 3;
        if (txtCantI.getText().length() > lar) {
            evt.consume();
        }

        char c = evt.getKeyChar();
        if ((c < '0' || c > '9') && (c != '.')) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCantIKeyTyped

    private void txtPrePKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrePKeyTyped
        int lar = 4;
        if (txtPreP.getText().length() > lar) {
            evt.consume();
        }

        char c = evt.getKeyChar();
        if (c < '0' || c > '9'){
            evt.consume();
        }
    }//GEN-LAST:event_txtPrePKeyTyped

    private void txtCodIKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodIKeyTyped
        int lar = 4;
        if (txtCodI.getText().length() > lar) {
            evt.consume();
        }

        char c = evt.getKeyChar();
        if (c < '0' || c > '9'){
            evt.consume();
        }
    
    }//GEN-LAST:event_txtCodIKeyTyped

    private void txtNomPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomPKeyTyped
        int lar = 29;
        if (txtNomP.getText().length() > lar) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNomPKeyTyped

    private void txtFiltro1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltro1KeyTyped
        txtFiltro1.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (txtFiltro1.getText());
                txtFiltro1.setText(cadena);
                repaint();
                filtro();
            }
        });
        trsFiltro = new TableRowSorter(tblProd.getModel());
        tblProd.setRowSorter(trsFiltro);
    }//GEN-LAST:event_txtFiltro1KeyTyped

    private void txtFiltro2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltro2KeyTyped
        txtFiltro2.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (txtFiltro2.getText());
                txtFiltro2.setText(cadena);
                repaint();
                filtro2();
            }
        });
        trsFiltro2 = new TableRowSorter(tblIns.getModel());
        tblIns.setRowSorter(trsFiltro2);
    }//GEN-LAST:event_txtFiltro2KeyTyped

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
            java.util.logging.Logger.getLogger(formProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formProductos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptarI;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCancelarI;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminarTodos;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<String> cmbFiltro1;
    private javax.swing.JComboBox<String> cmbFiltro2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tblIns;
    private javax.swing.JTable tblPI;
    private javax.swing.JTable tblProd;
    private javax.swing.JTextField txtCantI;
    private javax.swing.JTextField txtCodI;
    private javax.swing.JTextField txtCodP;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtFiltro1;
    private javax.swing.JTextField txtFiltro2;
    private javax.swing.JTextField txtNomP;
    private javax.swing.JTextField txtPreP;
    // End of variables declaration//GEN-END:variables
}
