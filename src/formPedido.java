
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

public class formPedido extends javax.swing.JFrame {

    private Statement sentencia;
    private Statement sentencia2;
    /*puedo ocupar sentencias de control sql*/
    private Connection conexion;
    /*objeto que permite conectar mi base datos con programa*/
    private String nomBD = "bussandwicheria";
    private String usuario = "root";
    private String password = "informatica";
    private String msj;
    private TableRowSorter trsFiltro;
    private int SelectDetalle = -1;
    private int swStockInsuficiente = 0;
    
    private int id_llevar = 0;

    public formPedido() {
        initComponents();
        conectar();
        this.setLocationRelativeTo(null);
        DefaultTableModel modelo = (DefaultTableModel) tblproductos.getModel();
        DefaultTableModel modeloI = (DefaultTableModel) tbldetalle.getModel();
    }

    //constructor pedido servir
    public formPedido(String fecha, int numMesa, int numPedido, String tipPedi, String stat, String turn) {
        initComponents();
        conectar();
        //llenarTablaP();
        //llenarTablaI();
        this.setLocationRelativeTo(null);
        llenarTablaP();
        String states = "stat  " + stat;
        lblMesa.setVisible(true);
        txtMesa.setVisible(true);
        txtMesa.setText(String.valueOf(numMesa));
        

        System.out.println("pedido servir, stat" + stat);
        if (stat.equals("N")) {
            //JOptionPane.showMessageDialog(null, "registrar pedido", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
            RegistrarPedido(fecha, numPedido, tipPedi, turn);
            //JOptionPane.showMessageDialog(null, "ocupar mesa", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
            OcuparMesa(numMesa);
            //JOptionPane.showMessageDialog(null, "registrar detalle mesa", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
            RegistrarDetalleMesa(numMesa, numPedido, fecha);
        }

        if (stat.equals("A") || stat.equals("P")) {
            //JOptionPane.showMessageDialog(null, "llenar detalle", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
            LlenarDetalle(numPedido, fecha);
        }

        btnVuelto.setEnabled(false);
        txtVuelto.setEnabled(false);
        txtNum.setText(Integer.toString(numPedido));
        txtTurno.setText(turn);
        txtFecha.setText(fecha);
        System.out.println(tipPedi);
        this.getContentPane().setBackground(Color.orange);
        setIcon();
    }

    //constructor pedido llevar
    public formPedido(String fecha, int numPedido, String tipPedi, String stat, String turn, int idLLevar) {
        initComponents();
        conectar();
        //llenarTablaP();
        //llenarTablaI();
        lblMesa.setVisible(false);
        txtMesa.setVisible(false);
        txtMesa.setText("");
        id_llevar = idLLevar;
        
        this.setLocationRelativeTo(null);
        llenarTablaP();

        if (stat.equals("N")) {
            registrarPedido_llevar(idLLevar, numPedido);
            RegistrarPedido(fecha, numPedido, tipPedi, turn);
            registrarEstado_llevar(idLLevar);
        }
        
        if (stat.equals("A") || stat.equals("P")) {
            //JOptionPane.showMessageDialog(null, "llenar detalle", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
            LlenarDetalle(numPedido, fecha);
        }

        btnVuelto.setEnabled(false);
        txtVuelto.setEnabled(false);
        txtNum.setText(Integer.toString(numPedido));
        txtTurno.setText(turn);
        txtFecha.setText(fecha);
        System.out.println(tipPedi);
        System.out.println("pedido llevar, stat" + stat);

        this.getContentPane().setBackground(Color.orange);
        setIcon();
    }
    
    public void registrarPedido_llevar(int v_numLLevar, int v_numped){
        String sql = "UPDATE llevar set num_ped = " + v_numped + " WHERE id_llevar= " + v_numLLevar;
        insertar(sql);
    }
    
    public void registrarEstado_llevar(int v_numLLevar){
        String sql = "UPDATE llevar set estado_llevar = 1 WHERE id_llevar=" + v_numLLevar;
        insertar(sql);
    }

    public void RegistrarPedido(String valFecha, int valNumPedido, String valtipPedi, String turn) {
        String sql = "INSERT INTO pedido VALUES (" + valNumPedido + ",'" + valFecha + "'," + 0 + ",'" + turn + "','" + valtipPedi + "','" + "" + "'," + 0 + ",'" + "A" + "')";
        //System.out.println(sql);
        insertar(sql);
    }

    public void RegistrarDetalleMesa(int varNumMesa, int varNumPedido, String varFecha) {
        String sql = "INSERT INTO detalle_mesa VALUES (" + varNumMesa + "," + varNumPedido + ",'" + varFecha + "')";
        System.out.println(sql);
        insertar(sql);

    }

    public void LlenarDetalle(int valNumePedido, String valFecha) {
        int auxCodP = 0;
        int auxCant = 0;
        int auxPre = 0;
        String auxEst = "";

        try {
            System.out.println("llenar tabala detalle");
            //JOptionPane.showMessageDialog(null, "llenar tabla detalle", "llenar tabla detalle", JOptionPane.INFORMATION_MESSAGE);
            sentencia = (Statement) conexion.createStatement();
            String query = "SELECT * FROM detalle_pedido where numero_pedido = " + valNumePedido + " and fecha_venta = '" + valFecha + "'";
            //JOptionPane.showMessageDialog(null, query, "llenar tabla detalle", JOptionPane.INFORMATION_MESSAGE);
            ResultSet lista = sentencia.executeQuery(query);
            //ResultSet lista = sentencia.executeQuery("SELECT * FROM detalle_pedido where numero_pedido =" + valNumePedido + "and fecha_venta ='" + valFecha + "'");

            int cont = 0;

            while (lista.next()) {
                //JOptionPane.showMessageDialog(null, "while 1", "llenar tabla detalle", JOptionPane.INFORMATION_MESSAGE);
                auxCodP = (lista.getInt("cod_producto"));//  System.out.println(auxCod);
                auxCant = (lista.getInt("cantidad")); // System.out.println(auxNom);
                auxPre = (lista.getInt("precio"));
                auxEst = (lista.getString("estado"));
                String auxNom = "";

                try {
                    //JOptionPane.showMessageDialog(null, "try 2", "llenar tabla detalle", JOptionPane.INFORMATION_MESSAGE);
                    sentencia = (Statement) conexion.createStatement();
                    ResultSet lista1 = sentencia.executeQuery("SELECT * FROM producto where cod_producto =" + auxCodP);

                    while (lista1.next()) {
                        //JOptionPane.showMessageDialog(null, "while 2", "llenar tabla detalle", JOptionPane.INFORMATION_MESSAGE);
                        auxNom = (lista1.getString("nombre_producto"));
                        //JOptionPane.showMessageDialog(null, auxNom, "llenar tabla detalle", JOptionPane.INFORMATION_MESSAGE);
                    }

                } catch (SQLException e) {
                    msj = "no se pudo seleccionar";
                }
                //System.out.println(auxCodP);
                //JOptionPane.showMessageDialog(null, auxCodP, "llenar tabla detalle", JOptionPane.INFORMATION_MESSAGE);
                //System.out.println(auxNom);
                //JOptionPane.showMessageDialog(null, auxNom, "llenar tabla detalle", JOptionPane.INFORMATION_MESSAGE);

                DefaultTableModel modeloDetalle = (DefaultTableModel) tbldetalle.getModel();
                modeloDetalle.addRow(new Object[]{auxCodP, auxNom, auxPre, auxCant, auxEst});
                cont = cont + 1;

            }
        } catch (SQLException e) {
            msj = "no se pudo seleccionar";
        }
    }

    /*
    public String ConvertirFecha(String valFecha){
        String fecha = DATE_FORMAT(valFecha, "%d/%m/%Y");
        
        return fecha;
    
    } */
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
            //JOptionPane.showMessageDialog(null, msj, "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
            // lblEstado.setText(msj);
        } catch (SQLException e) {
            System.out.print(e.getMessage());
            msj = "no ingreso";
        }
    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagen/icono.png")));
    }

    public void llenarTablaP() {
        String auxCod = "";
        String auxNom = "";
        String auxPre = "";
        String auxStock = "";

        try {
            sentencia = (Statement) conexion.createStatement();
            ResultSet lista = sentencia.executeQuery("SELECT * FROM producto");
            int cont = 0;
            while (lista.next()) {
                auxCod = (lista.getString("cod_producto"));  //System.out.println(auxCod);
                auxNom = (lista.getString("nombre_producto")); // System.out.println(auxNom);
                auxPre = (lista.getString("precio")); // System.out.println(auxPre);                    
                auxStock = calcularStockVirtual(auxCod);
                DefaultTableModel modelo = (DefaultTableModel) tblproductos.getModel();
                modelo.addRow(new Object[]{auxCod, auxNom, auxPre,auxStock});
                cont = cont + 1;
            }
        } catch (SQLException e) {
            msj = "no se pudo seleccionar";
        }
    }
    
    public void limpiarTablaP() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) tblproductos.getModel();
            int filas = tblproductos.getRowCount();
            for (int i = 0; filas > i; i++) {
                modelo.removeRow(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
        }
    }
    
    //lenar tabla detalle
    public void llenarTablaDP(){
        String auxCod = "";
        String auxCant = "";
        String auxPre = "";
        String auxState = "";
        String auxNom = "";
        String auxFec = txtFecha.getText();
        String auxNum = txtNum.getText();
        int auxiNum = Integer.parseInt(auxNum);
        try {
            sentencia = (Statement) conexion.createStatement();
            ResultSet lista = sentencia.executeQuery("SELECT * FROM detalle_pedido where fecha_venta = '" + auxFec + "' and numero_pedido = " + auxiNum + "");
            int cont = 0;
            while (lista.next()) {
                auxCod = (lista.getString("cod_producto"));  //System.out.println(auxCod);
                auxCant = (lista.getString("cantidad")); // System.out.println(auxNom);
                auxPre = (lista.getString("precio")); // System.out.println(auxPre);                    
                auxState = (lista.getString("precio"));
                
                DefaultTableModel modelo = (DefaultTableModel) tbldetalle.getModel();
                modelo.addRow(new Object[]{auxCod, auxCant, auxPre,auxState});
                cont = cont + 1;
            }
        } catch (SQLException e) {
            msj = "no se pudo seleccionar";
        }
    }
    
    public void limpiarTablaDP(){
        try {
            DefaultTableModel modelo = (DefaultTableModel) tbldetalle.getModel();
            int filas = tbldetalle.getRowCount();
            for (int i = 0; filas > i; i++) {
                modelo.removeRow(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
        }
    }
    
    public void CalcularTotal() {
        int total = 0;
        int largo = tbldetalle.getRowCount();
        System.out.println("el largo es: " + largo);
        for (int i = 0; i < largo; i++) {
            Object valor = tbldetalle.getValueAt(i, 3);
            int auxValor = Integer.parseInt(valor.toString());

            total = total + auxValor;
        }
    }

    public void OcuparMesa(int valMesa) {
        String sql = "UPDATE mesas set estado='O' WHERE numero_mesa=" + valMesa;
        insertar(sql);
    }

    public void limpiarCampos() {
        txtcod.setText("");
        txtnom.setText("");
        txtprec.setText("");
        spinCant.setValue(1);
    }

    public String calcularStockVirtual(String paramCodProd) {
        String returnStockVirtual = "000";
        String auxCodInsumox = "111";
        String auxCodProd = paramCodProd;
        float valorMinimo = 0;
        float auxCantDetalle = 0;
        Integer auxCantIns = 0;
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
        //obtiene valor entero minimo
        int retorno = (int)valorMinimo ;
        returnStockVirtual = Integer.toString(retorno);
        
        return returnStockVirtual;
    }

    public void ActualizarStockAuto(int v_codProd, int v_cant, int v_tipoAct) {
        String auxCodIns = "";
        float auxCant = 0;
        try {
            System.out.println("actualizar stock");
            sentencia = (Statement) conexion.createStatement();
            ResultSet lista = sentencia.executeQuery("SELECT * FROM detalle_producto WHERE cod_producto= " + v_codProd + "");
            int cont = 0;

            while (lista.next()) {
                auxCodIns = lista.getString("cod_insumo");
                auxCant = (Float.parseFloat(lista.getString("cantidad"))) * v_cant;

                if (v_tipoAct == 1) {
                    descontarAumentarInsumo(auxCodIns, v_tipoAct, auxCant);
                }
                if (v_tipoAct == 2) {
                    descontarAumentarInsumo(auxCodIns, v_tipoAct, auxCant);
                }
                /*recordar siempre el param2... si es 1 es ingreso y 2 perdida*/
            }
        } catch (SQLException e) {
            msj = "no se pudo seleccionar";
        }
    }

    public void descontarAumentarInsumo(String paramCodIns, Integer accion, float cantidad) {
        int switchNoNegativo = 0;

        String auxCodIns = paramCodIns;
        float auxMontoBD = 0;
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
                    /*si la var accion = 2 , descontara stock*/
                    auxMontoNuevo = auxMontoBD + auxMontoForm;
                }

                if (accion == 2) {
                    /*si la var accion = 2 , descontara stock*/
                    auxMontoNuevo = auxMontoBD - auxMontoForm;
                    System.out.println(auxMontoNuevo + "=" + auxMontoBD + "-" + auxMontoForm);
                }

                if (auxMontoNuevo < 0) {
                    switchNoNegativo = 1;
                    swStockInsuficiente = 1;
                    JOptionPane.showMessageDialog(null, "Error! Cantidad de insumos solicitados NO disponible", "ERROR", JOptionPane.INFORMATION_MESSAGE);
                }
                cont = cont + 1;
            }
        } catch (SQLException e) {
            msj = "no se pudo seleccionar";
        }

        //IMPORTANTE : AHORA QUE YA TENEMOS EL VALOR NUEVO A AGREGAR
        //VENDRIA LA PARTE DE AGREGAR DICHO MONTO
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
                        msj = "no ingreso";
                        JOptionPane.showMessageDialog(null, "Datos no actualizados", "Error", JOptionPane.INFORMATION_MESSAGE);
                    }

                    msj = "Modificado";
                    /* JOptionPane.showMessageDialog(null,msj,"INFORMACION",JOptionPane.INFORMATION_MESSAGE); */
                } else {
                    msj = "Insumo seleccionado no existe";
                    JOptionPane.showMessageDialog(null, msj, "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {
                msj = "No existe persona, no se puede modificar";
            }

            //limpiarTabla();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup2 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblproductos = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbldetalle = new javax.swing.JTable();
        btnAgregar = new javax.swing.JButton();
        txtcod = new javax.swing.JTextField();
        txtnom = new javax.swing.JTextField();
        rbtnEfectivo = new javax.swing.JRadioButton();
        rbtnTarjeta = new javax.swing.JRadioButton();
        rbtnCredito = new javax.swing.JRadioButton();
        spinCant = new javax.swing.JSpinner();
        jSeparator1 = new javax.swing.JSeparator();
        txtTotal = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtPago = new javax.swing.JTextField();
        btnVuelto = new javax.swing.JButton();
        txtVuelto = new javax.swing.JTextField();
        btnAtras = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        btnCancelar = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        btnPago = new javax.swing.JButton();
        btnFinalizar = new javax.swing.JButton();
        txtDescuento = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        txtTurno = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtNum = new javax.swing.JTextField();
        txtprec = new javax.swing.JTextField();
        btnTotal = new javax.swing.JButton();
        txtFecha = new javax.swing.JTextField();
        btnRegDetalle = new javax.swing.JButton();
        lblMesa = new javax.swing.JLabel();
        txtMesa = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cmbFiltro = new javax.swing.JComboBox<>();
        txtFiltro = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tblproductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Precio", "Stock Disponible"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblproductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblproductosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tblproductosMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(tblproductos);
        if (tblproductos.getColumnModel().getColumnCount() > 0) {
            tblproductos.getColumnModel().getColumn(0).setResizable(false);
            tblproductos.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblproductos.getColumnModel().getColumn(1).setResizable(false);
            tblproductos.getColumnModel().getColumn(1).setPreferredWidth(120);
            tblproductos.getColumnModel().getColumn(2).setResizable(false);
            tblproductos.getColumnModel().getColumn(2).setPreferredWidth(40);
            tblproductos.getColumnModel().getColumn(3).setResizable(false);
            tblproductos.getColumnModel().getColumn(3).setPreferredWidth(120);
        }

        tbldetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Precio", "Cantidad", "Estado"
            }
        ));
        tbldetalle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbldetalleMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbldetalle);

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        buttonGroup2.add(rbtnEfectivo);
        rbtnEfectivo.setText("Efectivo");
        rbtnEfectivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnEfectivoActionPerformed(evt);
            }
        });

        buttonGroup2.add(rbtnTarjeta);
        rbtnTarjeta.setText("Tarjeta");
        rbtnTarjeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnTarjetaActionPerformed(evt);
            }
        });

        buttonGroup2.add(rbtnCredito);
        rbtnCredito.setText("Crédito");
        rbtnCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnCreditoActionPerformed(evt);
            }
        });

        spinCant.setModel(new javax.swing.SpinnerNumberModel(1, 1, 50, 1));
        spinCant.setToolTipText("");

        txtTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalActionPerformed(evt);
            }
        });

        jLabel2.setText("Pago:");

        txtPago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPagoKeyTyped(evt);
            }
        });

        btnVuelto.setText("Vuelto");
        btnVuelto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVueltoActionPerformed(evt);
            }
        });

        txtVuelto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtVueltoActionPerformed(evt);
            }
        });

        btnAtras.setText("Atrás");
        btnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasActionPerformed(evt);
            }
        });

        jLabel3.setText("Pedido");

        jLabel4.setText("Fecha:");

        jLabel5.setText("Seleccione medio de pago:");

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnPago.setText("Confirmar Pago");
        btnPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagoActionPerformed(evt);
            }
        });

        btnFinalizar.setText("Finalizar Pedido");
        btnFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizarActionPerformed(evt);
            }
        });

        txtDescuento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescuentoActionPerformed(evt);
            }
        });
        txtDescuento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescuentoKeyTyped(evt);
            }
        });

        jLabel6.setText("Descuento:");

        jLabel7.setText("Listado de productos");

        jLabel8.setText("Detalle de pedido");

        jLabel9.setText("Turno:");

        txtTurno.setEditable(false);

        jLabel10.setText("N° Pedido:");

        txtNum.setEditable(false);

        btnTotal.setText("Calcular total");
        btnTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTotalActionPerformed(evt);
            }
        });

        txtFecha.setEditable(false);

        btnRegDetalle.setText("Registrar Detalle");
        btnRegDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegDetalleActionPerformed(evt);
            }
        });

        lblMesa.setText("Mesa:");

        txtMesa.setEditable(false);

        jLabel1.setText("Buscar por:");

        cmbFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Código", "Nombre", "Precio" }));

        txtFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFiltroActionPerformed(evt);
            }
        });
        txtFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFiltroKeyTyped(evt);
            }
        });

        jButton1.setText("Eliminar Detalle");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator3)
                            .addComponent(jSeparator1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(57, 57, 57)
                                        .addComponent(btnAgregar)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnCancelar))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(1, 1, 1)
                                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jButton1))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel2)
                                            .addComponent(btnVuelto)
                                            .addComponent(btnTotal))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtPago, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                                            .addComponent(txtDescuento, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtTotal, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtVuelto)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(13, 13, 13)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(rbtnTarjeta)
                                                    .addComponent(rbtnEfectivo)
                                                    .addComponent(rbtnCredito)))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 46, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnRegDetalle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnFinalizar)
                                .addGap(18, 18, 18)
                                .addComponent(btnPago))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNum, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(27, 27, 27)
                                        .addComponent(lblMesa)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtMesa, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(cmbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtcod, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(txtnom)
                                .addGap(18, 18, 18)
                                .addComponent(txtprec, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(spinCant, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(238, 238, 238)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(176, 176, 176)
                        .addComponent(btnAtras))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel3)
                        .addGap(51, 51, 51))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAtras)
                            .addComponent(jLabel4)
                            .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtTurno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMesa)
                            .addComponent(txtMesa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(txtFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtcod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtnom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinCant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtprec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAgregar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnCancelar))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbtnEfectivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbtnTarjeta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbtnCredito)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTotal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtVuelto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnVuelto))
                    .addComponent(jButton1))
                .addGap(20, 20, 20)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFinalizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPago, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRegDetalle)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rbtnTarjetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnTarjetaActionPerformed
        btnVuelto.setEnabled(false);
        txtVuelto.setEnabled(false);
    }//GEN-LAST:event_rbtnTarjetaActionPerformed

    private void btnVueltoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVueltoActionPerformed
        int tot = Integer.parseInt(txtTotal.getText());
        int des = 0;
        if (txtDescuento.getText().equals("")) {
            des = 0;
        } else {
            des = Integer.parseInt(txtDescuento.getText());
        }

        int vuelto = 0;
        int pag = Integer.parseInt(txtPago.getText());
        if ((pag - tot - des) > 0) {
            vuelto = pag - tot - des;
        }

        txtVuelto.setText(Integer.toString(vuelto));
    }//GEN-LAST:event_btnVueltoActionPerformed

    private void txtVueltoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVueltoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtVueltoActionPerformed

    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        //new fLogin().setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btnAtrasActionPerformed

    private void rbtnCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnCreditoActionPerformed
        btnVuelto.setEnabled(false);
        txtVuelto.setEnabled(false);
    }//GEN-LAST:event_rbtnCreditoActionPerformed

    private void btnPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagoActionPerformed
        String numPedido = txtNum.getText();
        String numTotal = txtTotal.getText();
        String fech = txtFecha.getText();

        if (numTotal.equals("")) {
            JOptionPane.showMessageDialog(null, "ERROR! Campo -Total- vacio ", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
        } else {
            if (txtPago.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "ERROR! Campo -Pago- vacio ", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);

            } else {
                if (Integer.parseInt(txtPago.getText()) < Integer.parseInt(numTotal)) {
                    JOptionPane.showMessageDialog(null, "ERROR! Pago Insuficiente ", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    byte sw = 0;

                    if (rbtnEfectivo.isSelected()) {
                        String sql1 = "UPDATE pedido set tipo_pago='E' WHERE numero_pedido = " + Integer.parseInt(numPedido) + " and fecha_venta = '" + fech + "'";
                        insertar(sql1);
                        sw = 1;
                    }
                    if (rbtnCredito.isSelected()) {
                        if (Integer.parseInt(txtPago.getText()) > Integer.parseInt(numTotal)) {
                            JOptionPane.showMessageDialog(null, "Pago Excesivo \n Esta opción no permite vuelto", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
                            sw = 0;
                        } else {
                            String sql1 = "UPDATE pedido set tipo_pago='C' WHERE numero_pedido = " + Integer.parseInt(numPedido) + " and fecha_venta = '" + fech + "'";
                            insertar(sql1);
                            sw = 1;
                            formDeuda deud = new formDeuda(numPedido, numTotal);
                            //this.dispose();
                            //this.setVisible(false);
                            deud.setVisible(true);
                        }
                    }

                    if (rbtnTarjeta.isSelected()) {
                        if (Integer.parseInt(txtPago.getText()) > Integer.parseInt(numTotal)) {
                            JOptionPane.showMessageDialog(null, "Pago Excesivo \n Esta opción no permite vuelto", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
                            sw = 0;
                        } else {
                            String sql1 = "UPDATE pedido set tipo_pago='T' WHERE numero_pedido = " + Integer.parseInt(numPedido) + " and fecha_venta = '" + fech + "'";
                            insertar(sql1);
                            sw = 1;
                        }
                    }

                    int pago = Integer.parseInt(txtTotal.getText()) - Integer.parseInt(txtDescuento.getText());
                    //String pagoX = String.valueOf(pago);

                    if (sw == 1) {
                        String sql = "UPDATE pedido set estado_pedido='P' WHERE numero_pedido = " + Integer.parseInt(numPedido) + " and fecha_venta = '" + fech + "'";
                        //JOptionPane.showMessageDialog(null, sql, "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
                        insertar(sql);

                        if (rbtnCredito.isSelected() == false) {
                            JOptionPane.showMessageDialog(null, "Pago Registrado", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
                        }
                        String sqlX = "UPDATE pedido set valor_neto_pedido= " + pago + " WHERE numero_pedido = " + Integer.parseInt(numPedido) + " and fecha_venta = '" + fech + "'";
                        insertar(sqlX);
                    }
                }
            }
            ///System.out.println("efectivo/tarjeta");
        }


    }//GEN-LAST:event_btnPagoActionPerformed

    private void tblproductosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblproductosMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tblproductosMouseEntered

    private void tblproductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblproductosMouseClicked
        if (tblproductos.getRowCount() > 0) {
            int sel = tblproductos.getSelectedRow();
            Object cod = tblproductos.getValueAt(sel, 0);
            String auxCode = cod.toString();
            //tblproductos = auxCode;

            Object nom = tblproductos.getValueAt(sel, 1);
            String auxNom = nom.toString();

            Object pre = tblproductos.getValueAt(sel, 2);
            String auxPre = pre.toString();

            txtcod.setText(auxCode);
            txtnom.setText(auxNom);
            txtprec.setText(auxPre);
        }
    }//GEN-LAST:event_tblproductosMouseClicked

    private void rbtnEfectivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnEfectivoActionPerformed
        btnVuelto.setEnabled(true);
        txtVuelto.setEnabled(true);
    }//GEN-LAST:event_rbtnEfectivoActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        String code = txtcod.getText();
        String nom = txtnom.getText();
        String pre = txtprec.getText();
        String cant = spinCant.getValue().toString();

        DefaultTableModel modelodet = (DefaultTableModel) tbldetalle.getModel();
        modelodet.addRow(new Object[]{code, nom, pre, cant, "P"});

        limpiarCampos();

    }//GEN-LAST:event_btnAgregarActionPerformed

    private void txtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalActionPerformed

    private void btnTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTotalActionPerformed
        int largo = tbldetalle.getRowCount();
        System.out.println("el largo es: " + largo);

        int total = 0;
        txtDescuento.setText("0");

        for (int i = 0; i < largo; i++) {
            Object pre = tbldetalle.getValueAt(i, 2);
            int calPrecio = Integer.parseInt(pre.toString());

            Object cant = tbldetalle.getValueAt(i, 3);
            int calCant = Integer.parseInt(cant.toString());

            int subtotal = calPrecio * calCant;

            total = total + subtotal;
        }

        txtTotal.setText(Integer.toString(total));
    }//GEN-LAST:event_btnTotalActionPerformed

    private void txtPagoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPagoKeyTyped
        int lar = 5;

        if (txtPago.getText().length() > lar) {
            evt.consume();
        }

        char c = evt.getKeyChar();

        if (c < '0' || c > '9') {
            evt.consume();
        }
    }//GEN-LAST:event_txtPagoKeyTyped

    private void txtDescuentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoKeyTyped
        int lar = 4;

        if (txtDescuento.getText().length() > lar) {
            evt.consume();
        }

        char c = evt.getKeyChar();

        if (c < '0' || c > '9') {
            evt.consume();
        }
    }//GEN-LAST:event_txtDescuentoKeyTyped

    private void btnRegDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegDetalleActionPerformed
        //JOptionPane.showMessageDialog(null, "ETRO registrar detalle", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
        int NumPed = Integer.parseInt(txtNum.getText());
        String Fech = txtFecha.getText();
        String query = "";
        ResultSet existe;
        System.out.println("Fecha: " + Fech);
        swStockInsuficiente = 0;

        try {
            //JOptionPane.showMessageDialog(null, "ETRO try", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
            sentencia = (Statement) conexion.createStatement();
            query = "SELECT * detalle_pedido WHERE numero_pedido = " + NumPed + " and fecha_venta ='" + Fech + "'";
            existe = sentencia.executeQuery(query);

            if (existe.next() == true) {
                String sql = ("Delete from detalle_pedido WHERE numero_pedido = " + NumPed + " and fecha_venta = '" + Fech + "'");
                insertar(sql);
            }
            /* else {
                try {                    
                    sentencia = (Statement) conexion.createStatement();
                    ResultSet lista = sentencia.executeQuery("SELECT estado_pedido FROM pedido where numero_pedido = " + pedido);
                    
                    while (lista.next()) {
                        state = (lista.getString("estado_pedido"));
                    }
                } catch (SQLException e) {
                }

            } */
        } catch (SQLException e) {
            //JOptionPane.showMessageDialog(null, "ETRO catch", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
            String sql = ("Delete from detalle_pedido WHERE numero_pedido = " + NumPed + " and fecha_venta = '" + Fech + "'");
            insertar(sql);
        }

        //JOptionPane.showMessageDialog(null, "antes leer tabla", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
        int largo = tbldetalle.getRowCount();
        System.out.println("el largo es: " + largo);
        for (int i = 0; i < largo; i++) {
            //Object fec = tbldetalle.getValueAt(i, 0);
            String auxFec = txtFecha.getText();
            System.out.println(auxFec);

            int auxNumM = Integer.parseInt(txtNum.getText());

            Object codp = tbldetalle.getValueAt(i, 0);
            int auxCodP = Integer.parseInt(codp.toString());

            Object pre = tbldetalle.getValueAt(i, 2);
            int auxPre = Integer.parseInt(pre.toString());

            Object cant = tbldetalle.getValueAt(i, 3);
            int auxCant = Integer.parseInt(cant.toString());
            
            Object est = tbldetalle.getValueAt(i, 4);
            String auxEst = est.toString();
            //Posibles Estados
            //P = Pendiente, aun no se registra
            //R = Registrado, ya registrado en la BD, ya esta descontado del stock
            //E = Enviado, ya se entrego al cliente. 
            if (auxEst.equals("P")){
                ActualizarStockAuto(auxCodP, auxCant, 2);
                auxEst =  "R";
            }
            
            //ActualizarStockAuto(auxCodP, auxCant, 2);
            //if (swStockInsuficiente == 0){}

            //JOptionPane.showMessageDialog(null, "antes insertar en tabla", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
            //int codpr = Integer.parseInt(txtCodP.getText());
            String sql2 = "INSERT INTO detalle_pedido VALUES ('" + auxFec + "'," + auxNumM + "," + auxCodP + "," + auxCant + "," + auxPre + ",'" + auxEst + "')";
            //System.out.println(sql2);
            insertar(sql2);

        }
        JOptionPane.showMessageDialog(null, "Detalle registrado", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
        limpiarTablaP();
        llenarTablaP();
        limpiarTablaDP();
        LlenarDetalle(NumPed, Fech);
        //llenarTablaDP();
    }//GEN-LAST:event_btnRegDetalleActionPerformed

    private void btnFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarActionPerformed
        int num_pedido = Integer.parseInt(txtNum.getText());
        String fech = txtFecha.getText();

        String sql = "UPDATE pedido set estado_pedido='F'  WHERE numero_pedido= " + num_pedido + " and fecha_venta= '" + fech + "'";
        System.out.println(sql);
        insertar(sql);
        String mesa = txtMesa.getText();
        if (mesa.equals("")) {
            System.out.println("Pedido para llevar");
            String sqlestado = "UPDATE llevar set estado_llevar= 0 WHERE id_llevar=" + id_llevar;
            insertar(sqlestado);
            String sqlpedido = "UPDATE llevar set num_ped = NULL WHERE id_llevar=" + id_llevar;
            insertar(sqlpedido);
        } else {
            int mesita = Integer.parseInt(mesa);
            String sqlx = "UPDATE mesas set estado='L' WHERE numero_mesa=" + mesita;
            insertar(sqlx);
        }
        JOptionPane.showMessageDialog(null, "Pedido finalizado", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);


    }//GEN-LAST:event_btnFinalizarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtDescuentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescuentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescuentoActionPerformed

    //funcion filtro
    public void filtro() {
        int columnaABuscar = 0;
        if (cmbFiltro.getSelectedItem() == "Código") {
            columnaABuscar = 0;
        }
        if (cmbFiltro.getSelectedItem().toString() == "Nombre") {
            columnaABuscar = 1;
        }
        if (cmbFiltro.getSelectedItem() == "Precio") {
            columnaABuscar = 2;
        }
        trsFiltro.setRowFilter(RowFilter.regexFilter(txtFiltro.getText(), columnaABuscar));
    }

    private void txtFiltroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltroKeyTyped
        txtFiltro.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                String cadena = (txtFiltro.getText());
                txtFiltro.setText(cadena);
                repaint();
                filtro();
            }
        });
        trsFiltro = new TableRowSorter(tblproductos.getModel());
        tblproductos.setRowSorter(trsFiltro);
    }//GEN-LAST:event_txtFiltroKeyTyped

    private void tbldetalleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbldetalleMouseClicked
        if (tbldetalle.getRowCount() > 0) {
            SelectDetalle = tbldetalle.getSelectedRow();
        }
    }//GEN-LAST:event_tbldetalleMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        DefaultTableModel ModeloDet = (DefaultTableModel) tbldetalle.getModel();
        int auxNumPed = 0;
        
        if (SelectDetalle < 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento de la lista.", "ADVERTECIA!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            try {
                Object cod = tbldetalle.getValueAt(SelectDetalle, 0);
                int auxCodProd = Integer.parseInt(cod.toString());

                Object cant = tbldetalle.getValueAt(SelectDetalle, 3);
                int auxCant = Integer.parseInt(cant.toString());

                String auxFec = txtFecha.getText();
                auxNumPed = Integer.parseInt(txtNum.getText());

                ActualizarStockAuto(auxCodProd, auxCant, 1);

                String sqlDD = "Delete from detalle_pedido WHERE fecha_venta = '" + auxFec + "' and numero_pedido = " + auxNumPed + " and cod_producto = " + auxCodProd + "";
                System.out.println(sqlDD);
                sentencia.executeUpdate(sqlDD);

                ModeloDet.removeRow(SelectDetalle);
            } catch (SQLException ex) {
                Logger.getLogger(formPedido.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        limpiarTablaDP();
        //llenarTablaDP();
        LlenarDetalle(auxNumPed, txtFecha.getText());
        limpiarTablaP();
        llenarTablaP();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFiltroActionPerformed

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
            java.util.logging.Logger.getLogger(formPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formPedido().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAtras;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnFinalizar;
    private javax.swing.JButton btnPago;
    private javax.swing.JButton btnRegDetalle;
    private javax.swing.JButton btnTotal;
    private javax.swing.JButton btnVuelto;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cmbFiltro;
    private javax.swing.JButton jButton1;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel lblMesa;
    private javax.swing.JRadioButton rbtnCredito;
    private javax.swing.JRadioButton rbtnEfectivo;
    private javax.swing.JRadioButton rbtnTarjeta;
    private javax.swing.JSpinner spinCant;
    private javax.swing.JTable tbldetalle;
    private javax.swing.JTable tblproductos;
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtFiltro;
    private javax.swing.JTextField txtMesa;
    private javax.swing.JTextField txtNum;
    private javax.swing.JTextField txtPago;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtTurno;
    private javax.swing.JTextField txtVuelto;
    private javax.swing.JTextField txtcod;
    private javax.swing.JTextField txtnom;
    private javax.swing.JTextField txtprec;
    // End of variables declaration//GEN-END:variables
}
