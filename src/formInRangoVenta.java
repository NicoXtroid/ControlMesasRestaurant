
import java.awt.Color;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class formInRangoVenta extends javax.swing.JFrame {

    private Statement sentencia;
    private Connection conexion;
    private String msj;

    private String nomBD = "bussandwicheria";
    private String usuario = "root";
    private String password = "informatica";

    public formInRangoVenta() {
        initComponents();
        this.setLocationRelativeTo(null);
        setIcon();
        conectar();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        txtFecha.setText(dtf.format(localDate));

        lblDesde.setVisible(false);
        lblHasta.setVisible(false);
        dcDesde.setVisible(false);
        dcHasta.setVisible(false);
        btnAceptar.setVisible(false);
        btnCancelar.setVisible(false);

        jLabel12.setVisible(false);

        this.getContentPane().setBackground(Color.orange);

        rbtnDiurno.setVisible(false);

        rbtnVespertino.setVisible(false);
        rbtnAmbos.setVisible(false);

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

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagen/icono.png")));
    }

    public void vaciarListadoI() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) tblIng.getModel();
            int filas = tblIng.getRowCount();
            for (int i = 0; filas > i; i++) {
                modelo.removeRow(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
        }
    }

    public void vaciarListadoE() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) tblEg.getModel();
            int filas = tblEg.getRowCount();
            for (int i = 0; filas > i; i++) {
                modelo.removeRow(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
        }
    }

    private String CalcularPrimero() {
        String primero = "";

        Calendar c1 = Calendar.getInstance();
        String mes = Integer.toString(c1.get(Calendar.MONTH));
        String anno = Integer.toString(c1.get(Calendar.YEAR));
        System.out.println("mes " + mes + " agno " + anno);
        primero = anno + "/" + mes + "/" + "01";
        System.out.println(primero);
        return primero;
    }

    private void llenarEgresos(String fechaA, String fechaB) {
        try {
            //JOptionPane.showMessageDialog(null, "try 1", "llnar engresos", JOptionPane.INFORMATION_MESSAGE);
            sentencia = (Statement) conexion.createStatement();
            //ResultSet lista = sentencia.executeQuery("SELECT * FROM detalle_pedido WHERE fecha_ajuste BETWEEN '2017/12/01' AND '2017/12/04'");
            ResultSet lista = sentencia.executeQuery("SELECT DISTINCT(cod_producto_insumo),valor_neto FROM detalle_registro_compra WHERE fecha_registro_compra BETWEEN '" + fechaA + "' AND '" + fechaB + "'");
            int cont = 0;
            while (lista.next()) {
                //JOptionPane.showMessageDialog(null, "while", "llnar ingresos", JOptionPane.INFORMATION_MESSAGE);
                int auxCodPro = (lista.getInt("cod_producto_insumo")); // System.out.println(auxCod);
                int auxPre = (lista.getInt("valor_neto"));
                int auxCant = 0;
                String auxNom = "";
                //JOptionPane.showMessageDialog(null, auxCodPro, "llnar ingresos", JOptionPane.INFORMATION_MESSAGE);

                try {
                    //JOptionPane.showMessageDialog(null, "try 2", "llnar ingresos", JOptionPane.INFORMATION_MESSAGE);
                    sentencia = (Statement) conexion.createStatement();
                    String queryz = "SELECT * from insumos";
                    ResultSet listaz = sentencia.executeQuery(queryz);
                    while (listaz.next() == true) {
                        String nomIns = (listaz.getString("nombre_insumo"));
                        //int cate = Integer.parseInt(cat);
                        int idIns = (listaz.getInt("cod_insumo"));

                        if (auxCodPro == idIns) {
                            auxNom = nomIns;
                        }
                    }
                } catch (Exception e) {
                }

                try {
                    //JOptionPane.showMessageDialog(null, "try 3", "llnar ingresos", JOptionPane.INFORMATION_MESSAGE);
                    sentencia = (Statement) conexion.createStatement();
                    String queryx = "SELECT * FROM detalle_registro_compra WHERE cod_producto_insumo =" + auxCodPro;
                    //JOptionPane.showMessageDialog(null, queryx, "llnar ingresos", JOptionPane.INFORMATION_MESSAGE);
                    int cantidad = 0;
                    ResultSet listax = sentencia.executeQuery(queryx);
                    while (listax.next()) {
                        int CI = (listax.getInt("cod_producto_insumo"));
                        int canti = (listax.getInt("cantidad"));

                        //JOptionPane.showMessageDialog(null, "while 3", "llnar ingresos", JOptionPane.INFORMATION_MESSAGE);
                        //JOptionPane.showMessageDialog(null, canti, "llnar ingresos", JOptionPane.INFORMATION_MESSAGE);
                        if (auxCodPro == CI) {
                            cantidad = cantidad + canti;
                        }
                    }
                    auxCant = cantidad;
                } catch (Exception e) {
                }

                System.out.println(auxCodPro + " " + auxNom + " " + auxPre + " " + auxCant);
                DefaultTableModel modelo = (DefaultTableModel) tblEg.getModel();
                modelo.addRow(new Object[]{auxCodPro, auxNom, auxPre, auxCant});
                cont = cont + 1;

            }
        } catch (SQLException e) {
            msj = "no se pudo seleccionar";
        }

    }

    private void llenarIngresos(String fechaA, String fechaB, String jornada) {
        //JOptionPane.showMessageDialog(null, "entro", "llnar ingresos", JOptionPane.INFORMATION_MESSAGE);
        //JOptionPane.showMessageDialog(null, jornada, "llnar ingresos", JOptionPane.INFORMATION_MESSAGE);
        if (jornada.equals("A")) {
            try {
                //JOptionPane.showMessageDialog(null, "try 1", "llnar ingresos", JOptionPane.INFORMATION_MESSAGE);
                sentencia = (Statement) conexion.createStatement();
                //ResultSet lista = sentencia.executeQuery("SELECT * FROM detalle_pedido WHERE fecha_ajuste BETWEEN '2017/12/01' AND '2017/12/04'");
                ResultSet lista = sentencia.executeQuery("SELECT DISTINCT(cod_producto),precio FROM detalle_pedido WHERE fecha_venta BETWEEN '" + fechaA + "' AND '" + fechaB + "'");
                int cont = 0;
                while (lista.next()) {
                    //JOptionPane.showMessageDialog(null, "while", "llnar ingresos", JOptionPane.INFORMATION_MESSAGE);
                    int auxCodPro = (lista.getInt("cod_producto")); // System.out.println(auxCod);
                    int auxPre = (lista.getInt("precio"));
                    int auxCant = 0;
                    String auxNom = "";
                    //JOptionPane.showMessageDialog(null, auxCodPro, "llnar ingresos", JOptionPane.INFORMATION_MESSAGE);

                    try {
                        //JOptionPane.showMessageDialog(null, "try 2", "llnar ingresos", JOptionPane.INFORMATION_MESSAGE);
                        sentencia = (Statement) conexion.createStatement();
                        String queryz = "SELECT * from producto";
                        ResultSet listaz = sentencia.executeQuery(queryz);
                        while (listaz.next() == true) {
                            String nomIns = (listaz.getString("nombre_producto"));
                            //int cate = Integer.parseInt(cat);
                            int idIns = (listaz.getInt("cod_producto"));

                            if (auxCodPro == idIns) {
                                auxNom = nomIns;
                            }
                        }
                    } catch (Exception e) {
                    }

                    try {
                        //JOptionPane.showMessageDialog(null, "try 3", "llnar ingresos", JOptionPane.INFORMATION_MESSAGE);
                        sentencia = (Statement) conexion.createStatement();
                        String queryx = "SELECT * FROM detalle_pedido WHERE cod_producto =" + auxCodPro;
                        //JOptionPane.showMessageDialog(null, queryx, "llnar ingresos", JOptionPane.INFORMATION_MESSAGE);
                        int cantidad = 0;
                        ResultSet listax = sentencia.executeQuery(queryx);
                        while (listax.next()) {
                            int CI = (listax.getInt("cod_producto"));
                            int canti = (listax.getInt("cantidad"));

                            //JOptionPane.showMessageDialog(null, "while 3", "llnar ingresos", JOptionPane.INFORMATION_MESSAGE);
                            //JOptionPane.showMessageDialog(null, canti, "llnar ingresos", JOptionPane.INFORMATION_MESSAGE);
                            if (auxCodPro == CI) {
                                cantidad = cantidad + canti;
                            }
                        }
                        auxCant = cantidad;
                    } catch (Exception e) {
                    }

                    System.out.println(auxCodPro + " " + auxNom + " " + auxPre + " " + auxCant);
                    DefaultTableModel modelo = (DefaultTableModel) tblIng.getModel();
                    modelo.addRow(new Object[]{auxCodPro, auxNom, auxPre, auxCant});
                    cont = cont + 1;

                }
            } catch (SQLException e) {
                msj = "no se pudo seleccionar";
            }

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
        buttonGroup2 = new javax.swing.ButtonGroup();
        dcDesde = new com.toedter.calendar.JDateChooser();
        dcHasta = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        btnAtras = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblIng = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblEg = new javax.swing.JTable();
        txtTotalIng = new javax.swing.JTextField();
        txtTotalEg = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        rbtnMes = new javax.swing.JRadioButton();
        rbtnRango = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        lblDesde = new javax.swing.JLabel();
        lblHasta = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        txtGanancia = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        rbtnDiurno = new javax.swing.JRadioButton();
        rbtnVespertino = new javax.swing.JRadioButton();
        rbtnAmbos = new javax.swing.JRadioButton();
        txtFecha = new javax.swing.JTextField();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Informe de Ganancias");

        dcDesde.setDateFormatString("yyyy/MM/dd");

        dcHasta.setDateFormatString("yyyy/MM/dd");

        jLabel1.setText("Informe de Ganancia");

        btnAtras.setText("Atrás");
        btnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasActionPerformed(evt);
            }
        });

        jLabel2.setText("Fecha:");

        tblIng.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Precio", "Cantidad"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblIng);
        if (tblIng.getColumnModel().getColumnCount() > 0) {
            tblIng.getColumnModel().getColumn(0).setResizable(false);
            tblIng.getColumnModel().getColumn(1).setResizable(false);
            tblIng.getColumnModel().getColumn(2).setResizable(false);
            tblIng.getColumnModel().getColumn(3).setResizable(false);
        }

        tblEg.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Precio", "Cantidad"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblEg);
        if (tblEg.getColumnModel().getColumnCount() > 0) {
            tblEg.getColumnModel().getColumn(0).setResizable(false);
            tblEg.getColumnModel().getColumn(1).setResizable(false);
            tblEg.getColumnModel().getColumn(2).setResizable(false);
            tblEg.getColumnModel().getColumn(3).setResizable(false);
        }

        jLabel5.setText("Listado de Ingresos");

        jLabel6.setText("Listado de Egresos");

        buttonGroup1.add(rbtnMes);
        rbtnMes.setText("Mes Actual");
        rbtnMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnMesActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtnRango);
        rbtnRango.setText("Rango de fechas");
        rbtnRango.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnRangoActionPerformed(evt);
            }
        });

        jLabel7.setText("Seleccione fecha");

        lblDesde.setText("Desde");

        lblHasta.setText("Hasta");

        jLabel10.setText("Detalle");

        jLabel12.setText("Seleccione jornada");

        buttonGroup2.add(rbtnDiurno);
        rbtnDiurno.setText("Diurno");
        rbtnDiurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnDiurnoActionPerformed(evt);
            }
        });

        buttonGroup2.add(rbtnVespertino);
        rbtnVespertino.setText("Vespertino");

        buttonGroup2.add(rbtnAmbos);
        rbtnAmbos.setText("Ambos");

        txtFecha.setEditable(false);

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");

        jButton1.setText("Total Neto:");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Total Neto:");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Ganancia:");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAtras)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(222, 222, 222))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(txtGanancia, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jSeparator3))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jButton1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtTotalIng, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(3, 3, 3)
                                    .addComponent(jLabel5)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addGap(167, 167, 167))
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jButton2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtTotalEg, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addComponent(jSeparator2)))
                .addContainerGap(22, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(rbtnDiurno)
                        .addGap(45, 45, 45)
                        .addComponent(rbtnVespertino)
                        .addGap(50, 50, 50)
                        .addComponent(rbtnAmbos))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addComponent(lblDesde)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(dcDesde, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnAceptar)
                                        .addGap(6, 6, 6))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(rbtnMes)
                                            .addComponent(rbtnRango))
                                        .addGap(92, 92, 92)))))
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblHasta)
                                .addGap(18, 18, 18)
                                .addComponent(dcHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnCancelar)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnAtras))
                .addGap(7, 7, 7)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtnDiurno)
                    .addComponent(rbtnVespertino)
                    .addComponent(rbtnAmbos))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbtnMes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbtnRango)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblDesde)
                            .addComponent(dcDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblHasta)))
                    .addComponent(dcHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTotalIng, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTotalEg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2)))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGanancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        //new fLogin().setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnAtrasActionPerformed

    private void rbtnMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnMesActionPerformed
        lblDesde.setVisible(false);
        lblHasta.setVisible(false);
        dcDesde.setVisible(false);
        dcHasta.setVisible(false);
        btnAceptar.setVisible(false);
        btnCancelar.setVisible(false);
        vaciarListadoI();
        vaciarListadoE();

        String jor = "";
        String Afecha = CalcularPrimero();
        String BFecha = txtFecha.getText();

        if (rbtnDiurno.isSelected()) {
            jor = "D";
        }
        if (rbtnVespertino.isSelected()) {
            jor = "V";
        }
        if (rbtnAmbos.isSelected()) {
            jor = "A";
        }

        System.out.println("mes" + Afecha + " " + BFecha + " " + jor);
        llenarIngresos(Afecha, BFecha, "A");
        llenarEgresos(Afecha, BFecha);
    }//GEN-LAST:event_rbtnMesActionPerformed

    private void rbtnDiurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnDiurnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtnDiurnoActionPerformed

    private void rbtnRangoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnRangoActionPerformed
        lblDesde.setVisible(true);
        lblHasta.setVisible(true);
        dcDesde.setVisible(true);
        dcHasta.setVisible(true);
        btnAceptar.setVisible(true);
        btnCancelar.setVisible(true);
    }//GEN-LAST:event_rbtnRangoActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        String hoy = txtFecha.getText();
        String fecha1 = "";
        int sw = 0;

        String jor = "";
        if (rbtnDiurno.isSelected()) {
            jor = "D";
        }
        if (rbtnVespertino.isSelected()) {
            jor = "V";
        }
        if (rbtnAmbos.isSelected()) {
            jor = "A";
        }

        vaciarListadoI();
        vaciarListadoE();

        try {
            String formato = "yyyy/MM/dd";
            //Formato
            Date date = dcDesde.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat(formato);
            fecha1 = (sdf.format(date));
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(this, "Al menos selecciona una fecha válida!", "Error!", JOptionPane.INFORMATION_MESSAGE);
        }

        String fecha2 = "";
        try {
            String formato = "yyyy/MM/dd";
            //Formato
            Date date = dcHasta.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat(formato);
            fecha2 = (sdf.format(date));
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(this, "Al menos selecciona una fecha válida!", "Error!", JOptionPane.INFORMATION_MESSAGE);
        }

        int mayor = (fecha1.compareTo(fecha2));
        if (mayor > 0) {
            JOptionPane.showMessageDialog(null, "'Fecha inicial' supuera a 'Fecha final'", "ADVERTENCIA", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        }

        int hoyM = (fecha1.compareTo(hoy));
        if (hoyM > 0) {
            JOptionPane.showMessageDialog(null, "'Fecha inicial' supuera a Fecha actual", "ADVERTENCIA", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        }

        int hoyMF = (fecha2.compareTo(hoy));
        if (hoyMF > 0) {
            JOptionPane.showMessageDialog(null, "'Fecha final' supuera a Fecha actual", "ADVERTENCIA", JOptionPane.INFORMATION_MESSAGE);
            sw = 1;
        }
        /*
        if (sw == 0) {
            if (jor.equals("")) {
                JOptionPane.showMessageDialog(null, "Seleccione jornada", "ADVERTENCIA", JOptionPane.INFORMATION_MESSAGE);
            } else {
                llenarIngresos(fecha1, fecha2, "A");
                llenarEgresos(fecha1, fecha2);
            }
        } */

        llenarIngresos(fecha1, fecha2, "A");
        llenarEgresos(fecha1, fecha2);

    }//GEN-LAST:event_btnAceptarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int largo = tblIng.getRowCount();
        System.out.println("el largo es: " + largo);
        int total = 0;

        for (int i = 0; i < largo; i++) {
            Object pre = tblIng.getValueAt(i, 2);
            int calPrecio = Integer.parseInt(pre.toString());

            Object cant = tblIng.getValueAt(i, 3);
            int calCant = Integer.parseInt(cant.toString());

            int subtotal = calPrecio * calCant;

            total = total + subtotal;
        }

        txtTotalIng.setText(Integer.toString(total));
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int largo = tblEg.getRowCount();
        System.out.println("el largo es: " + largo);
        int total = 0;

        for (int i = 0; i < largo; i++) {
            Object pre = tblEg.getValueAt(i, 2);
            int calPrecio = Integer.parseInt(pre.toString());

            Object cant = tblEg.getValueAt(i, 3);
            int calCant = Integer.parseInt(cant.toString());

            int subtotal = calPrecio * calCant;

            total = total + subtotal;
        }

        txtTotalEg.setText(Integer.toString(total));
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        String ingre = txtTotalIng.getText();
        String egre = txtTotalEg.getText();

        if (ingre.equals("") || egre.equals("")) {
            if (ingre.equals("")) {
                JOptionPane.showMessageDialog(null, "Campo 'Total Ingresos' Vacio", "ADVERTENCIA", JOptionPane.INFORMATION_MESSAGE);
            }
            if (egre.equals("")) {
                JOptionPane.showMessageDialog(null, "Campo 'Total Egresos' Vacio", "ADVERTENCIA", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            int ing = Integer.parseInt(txtTotalIng.getText());
            int eg = Integer.parseInt(txtTotalEg.getText());

            int ganancia = ing - eg;

            txtGanancia.setText(Integer.toString(ganancia));
        }
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(formInRangoVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formInRangoVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formInRangoVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formInRangoVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formInRangoVenta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnAtras;
    private javax.swing.JButton btnCancelar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private com.toedter.calendar.JDateChooser dcDesde;
    private com.toedter.calendar.JDateChooser dcHasta;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lblDesde;
    private javax.swing.JLabel lblHasta;
    private javax.swing.JRadioButton rbtnAmbos;
    private javax.swing.JRadioButton rbtnDiurno;
    private javax.swing.JRadioButton rbtnMes;
    private javax.swing.JRadioButton rbtnRango;
    private javax.swing.JRadioButton rbtnVespertino;
    private javax.swing.JTable tblEg;
    private javax.swing.JTable tblIng;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtGanancia;
    private javax.swing.JTextField txtTotalEg;
    private javax.swing.JTextField txtTotalIng;
    // End of variables declaration//GEN-END:variables
}
