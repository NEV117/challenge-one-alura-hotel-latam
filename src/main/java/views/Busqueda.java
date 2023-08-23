package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.hibernate.mapping.Component;

import com.latam.alura.hotel.dao.HuespedesDao;
import com.latam.alura.hotel.dao.ReservasDao;
import com.latam.alura.hotel.models.Huesped;
import com.latam.alura.hotel.models.Reserva;
import com.latam.alura.hotel.utils.JPAUtils;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.persistence.EntityManager;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {
	
	private DefaultTableModel modeloReservas;
	private DefaultTableModel modeloHuespedes;
	private Map<JTable, DefaultTableModel> modelosDeTabla = new HashMap<>();
	private JTabbedPane tabbedPane;
	private int currentTab = 0; // Índice de la primera pestaña por defecto


	



	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHuesped;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Busqueda() {
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		
		
		
		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(331, 62, 280, 42);
		contentPane.add(lblNewLabel_4);
		
		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);

		
		
		
		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloReservas = new DefaultTableModel();
		modeloReservas.addColumn("Numero de Reserva");
		modeloReservas.addColumn("Fecha Check In");
		modeloReservas.addColumn("Fecha Check Out");
		modeloReservas.addColumn("Valor");
		modeloReservas.addColumn("Forma de Pago");
		tbReservas.setModel(modeloReservas);
		modelosDeTabla.put(tbReservas, modeloReservas); // Agrega el modelo al mapa
		

		JScrollPane scroll_table = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), scroll_table, null);
		scroll_table.setVisible(true);

		GetAllDataReservas(); // Llena el modelo de reservas con los datos al cargar la interfaz

		
		
		tbHuespedes = new JTable();
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHuespedes = new DefaultTableModel();
		modeloHuespedes.addColumn("Número de Huesped");
		modeloHuespedes.addColumn("Nombre");
		modeloHuespedes.addColumn("Apellido");
		modeloHuespedes.addColumn("Fecha de Nacimiento");
		modeloHuespedes.addColumn("Nacionalidad");
		modeloHuespedes.addColumn("Telefono");
		modeloHuespedes.addColumn("Número de Reserva");
		tbHuespedes.setModel(modeloHuespedes);
		modelosDeTabla.put(tbHuespedes, modeloHuesped); // Agrega el modelo al mapa
		
		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);
		

		GetAllDataHuespedes(); // Llena el modelo de reservas con los datos al cargar la interfaz
		
		panel.addChangeListener(e -> {
		    currentTab = panel.getSelectedIndex();
		});
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);
		
		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);
			     
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);
		
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(Color.white);
			     labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		
		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) { //Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { //Al usuario quitar el mouse por el botón este volverá al estado original
				 btnexit.setBackground(Color.white);
			     labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);
		
		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);
		
		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        if (currentTab == 0) {
		            filterReservas();
		        } else if (currentTab == 1) {
		            filterHuespedes();
		        }
		    }
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);
		
		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		JPanel btnEditar = new JPanel();
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);
		
		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (currentTab == 0) {
					System.out.print("En reservas");
					int selectedRow = tbReservas.getSelectedRow();
			        if (selectedRow != -1) { // Make sure a row is selected
			            //updateHuesped(selectedRow);
			        	updateReserva(selectedRow);
			        }
					
		        } else if (currentTab == 1) {
		        	System.out.print("En huespedes");
		        	int selectedRow = tbHuespedes.getSelectedRow();
			        if (selectedRow != -1) { // Make sure a row is selected
			            updateHuesped(selectedRow);
			        }
		        }
			}
		});
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);
		
		JPanel btnEliminar = new JPanel();
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);
		
		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (currentTab == 0) {
					System.out.print("En reservas");
					int selectedRow = tbReservas.getSelectedRow();
			        if (selectedRow != -1) { 
			        	deleteReserva(selectedRow);
			        }
					
		        } else if (currentTab == 1) {
		        	System.out.print("En huespedes");
		        	int selectedRow = tbHuespedes.getSelectedRow();
			        if (selectedRow != -1) { // Make sure a row is selected
			        	deleteHuesped(selectedRow);
			        }
		        }
			}
		});
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
	}
	
//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
	 private void headerMousePressed(java.awt.event.MouseEvent evt) {
	        xMouse = evt.getX();
	        yMouse = evt.getY();
	    }

	    private void headerMouseDragged(java.awt.event.MouseEvent evt) {
	        int x = evt.getXOnScreen();
	        int y = evt.getYOnScreen();
	        this.setLocation(x - xMouse, y - yMouse);
	    }
	    
	    public void GetAllDataReservas() {
	        EntityManager em = JPAUtils.getEntityManager();
	        
	        em.getTransaction().begin();
	        
	        ReservasDao reservasDao = new ReservasDao(em);
	        
	        List<Reserva> reservas = reservasDao.getAll();
	        
	        for (Reserva reserva : reservas) {
	            Object[] rowData = {
	                reserva.getId(),
	                reserva.getFechaEntrada().toString(), 
	                reserva.getFechaSalida().toString(),  
	                reserva.getValor(),
	                reserva.getFormaPago()
	            };
	            modeloReservas.addRow(rowData); // Agrega datos al modelo de reservas
	        }

	        em.getTransaction().commit();
	        em.close();
	    }
	    
	    public void GetAllDataHuespedes() {
	        EntityManager em = JPAUtils.getEntityManager();
	        
	        em.getTransaction().begin();
	        
	        HuespedesDao HuespedesDao = new HuespedesDao(em);
	        
	        List<Huesped> huespedes = HuespedesDao.getAll();
	        
	        for (Huesped huesped : huespedes) {
	            Object[] rowData = {
		            huesped.getId(),
	            	huesped.getNombre(),
	            	huesped.getApellido(),
	            	huesped.getFechaNacimiento().toString(),
	            	huesped.getNacionalidad(),
	            	huesped.getTelefono(),
	            	huesped.getIdReserva(),
	            };
	            modeloHuespedes.addRow(rowData); // Agrega datos al modelo de reservas
	        }

	        em.getTransaction().commit();
	        em.close();
	    }

	    private void filterHuespedes() {
	        String nombreBusqueda = txtBuscar.getText(); // Obtiene el texto de búsqueda

	        EntityManager em = JPAUtils.getEntityManager();
	        em.getTransaction().begin();

	        HuespedesDao HuespedesDao = new HuespedesDao(em);
	        List<Huesped> huespedesFiltrados;

	        if (nombreBusqueda == null || nombreBusqueda.isEmpty()) {
	            // Si el valor de búsqueda es nulo o vacío, carga todos los datos
	            huespedesFiltrados = HuespedesDao.getAll();
	        } else {
	            // Realiza la búsqueda por nombre
	            huespedesFiltrados = HuespedesDao.consultaPorColumnas(nombreBusqueda);
	        }

	        modeloHuespedes.setRowCount(0); // Limpia los datos actuales en el modelo

	        for (Huesped huesped : huespedesFiltrados) {
	            Object[] rowData = {
	                huesped.getId(),
	                huesped.getNombre(),
	                huesped.getApellido(),
	                huesped.getFechaNacimiento(),
	                huesped.getNacionalidad(),
	                huesped.getTelefono(),
	                huesped.getIdReserva()
	            };
	            modeloHuespedes.addRow(rowData); // Agrega los datos filtrados al modelo
	        }

	        em.getTransaction().commit();
	        em.close();
	    }
	    
	    private void filterReservas() {
	        String textoBusqueda = txtBuscar.getText(); // Obtiene el texto de búsqueda

	        EntityManager em = JPAUtils.getEntityManager();
	        em.getTransaction().begin();

	        ReservasDao reservasDao = new ReservasDao(em);
	        List<Reserva> reservasFiltradas;

	        if (textoBusqueda == null || textoBusqueda.isEmpty()) {
	            // Si el valor de búsqueda es nulo o vacío, carga todos los datos
	            reservasFiltradas = reservasDao.getAll();
	        } else {
	            // Realiza la búsqueda por las columnas especificadas
	            reservasFiltradas = reservasDao.consultaPorBusqueda(textoBusqueda);
	        }

	        modeloReservas.setRowCount(0); // Limpia los datos actuales en el modelo

	        for (Reserva reserva : reservasFiltradas) {
	            Object[] rowData = {
	                reserva.getId(),
	                reserva.getFechaEntrada().toString(),
	                reserva.getFechaSalida().toString(),
	                reserva.getValor(),
	                reserva.getFormaPago()
	            };
	            modeloReservas.addRow(rowData); // Agrega los datos filtrados al modelo
	        }

	        em.getTransaction().commit();
	        em.close();
	    }
	    public void updateHuesped(int rowIndex) {
	        EntityManager em = JPAUtils.getEntityManager();
	        try {
	            em.getTransaction().begin();
	            HuespedesDao huespedesDao = new HuespedesDao(em);

	            // Get the data from the selected row in the table
	            int huespedId = (int) tbHuespedes.getValueAt(rowIndex, 0);
	            String updatedNombre = (String) tbHuespedes.getValueAt(rowIndex, 1);
	            String updatedApellido = (String) tbHuespedes.getValueAt(rowIndex, 2);
	            String updatedNacionalidad = (String) tbHuespedes.getValueAt(rowIndex, 4);
	            String updatedTelefono = (String) tbHuespedes.getValueAt(rowIndex, 5);

	            // Retrieve the existing Huesped from the database
	            Huesped existingHuesped = huespedesDao.buscarPorId(huespedId);

	            // Update the relevant fields in the entity
	            existingHuesped.setNombre(updatedNombre);
	            existingHuesped.setApellido(updatedApellido);
	            existingHuesped.setNacionalidad(updatedNacionalidad);
	            existingHuesped.setTelefono(updatedTelefono);

	            // Update the table row with the new values
	            tbHuespedes.setValueAt(updatedNombre, rowIndex, 1);
	            tbHuespedes.setValueAt(updatedApellido, rowIndex, 2);
	            tbHuespedes.setValueAt(updatedNacionalidad, rowIndex, 4);
	            tbHuespedes.setValueAt(updatedTelefono, rowIndex, 5);

	            // Call the update method to save changes to the database
	            huespedesDao.update(existingHuesped);

	            // Commit the transaction
	            em.getTransaction().commit();
	            
	            //Succes Panel
		        Exito exitoFrame = new Exito();
		        exitoFrame.setVisible(true);
		        
	        } catch (Exception e) {
	            // Rollback the transaction in case of an exception
	            if (em.getTransaction().isActive()) {
	                em.getTransaction().rollback();
	            }

	         // Extract the error message from the exception
	            String errorMessage = extractErrorMessage(e);

	            // Display the customized error message using JOptionPane
	            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);

	            e.printStackTrace();
	        } finally {
	            em.close();
	        }
	    }
	    
	    private String extractErrorMessage(Exception e) {
	        String errorMessage = "Algo a Salido Mal :c\n\n Verfica he intenta de nuevo";

	        // Check if the exception is an SQL exception
	        if (e instanceof SQLException) {
	            SQLException sqlException = (SQLException) e;
	            errorMessage = sqlException.getMessage();
	        }

	        return errorMessage;
	    }
	    
	    public void updateReserva(int rowIndex) {
	        EntityManager em = JPAUtils.getEntityManager();
	        try {
	            em.getTransaction().begin();
	            ReservasDao reservaDao = new ReservasDao(em);

	            // Get the data from the selected row in the table
	            int reservaId = (int) tbReservas.getValueAt(rowIndex, 0);
	            String updatedFechaEntradaStr = (String) tbReservas.getValueAt(rowIndex, 1); // Assuming column 1 is fechaEntrada
	            String updatedFechaSalidaStr = (String) tbReservas.getValueAt(rowIndex, 2); // Assuming column 2 is fechaSalida
	            String updatedValorStr = (String) tbReservas.getValueAt(rowIndex, 3); // Assuming column 3 is valor
	            String updatedFormaPago = (String) tbReservas.getValueAt(rowIndex, 4); // Assuming column 4 is formaPago

	            // Convert string dates to Date objects
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust format as needed
	            Date updatedFechaEntrada = null;
	            Date updatedFechaSalida = null;
	            try {
	                updatedFechaEntrada = new Date(dateFormat.parse(updatedFechaEntradaStr).getTime());
	                updatedFechaSalida = new Date(dateFormat.parse(updatedFechaSalidaStr).getTime());
	            } catch (ParseException e) {
	                e.printStackTrace();
	            }

	            // Convert string value to BigDecimal
	            BigDecimal updatedValor = new BigDecimal(updatedValorStr);

	            // Retrieve the existing Reserva from the database
	            Reserva existingReserva = reservaDao.buscarPorId(reservaId);

	            // Update the relevant fields in the entity
	            existingReserva.setFechaEntrada(updatedFechaEntrada);
	            existingReserva.setFechaSalida(updatedFechaSalida);
	            existingReserva.setValor(updatedValor);
	            existingReserva.setFormaPago(updatedFormaPago);

	            // Update the table row with the new values
	            tbReservas.setValueAt(updatedFechaEntrada, rowIndex, 1);
	            tbReservas.setValueAt(updatedFechaSalida, rowIndex, 2);
	            tbReservas.setValueAt(updatedValor, rowIndex, 3);
	            tbReservas.setValueAt(updatedFormaPago, rowIndex, 4);

	            // Call the update method to save changes to the database
	            reservaDao.update(existingReserva);

	            // Commit the transaction
	            em.getTransaction().commit();

	            // Display the Exito view
	            Exito exitoFrame = new Exito();
	            exitoFrame.setVisible(true);

	        } catch (Exception e) {
	            // Rollback the transaction in case of an exception
	            if (em.getTransaction().isActive()) {
	                em.getTransaction().rollback();
	            }

	            // Extract and customize the error message from the exception
	            String errorMessage = extractErrorMessage(e);

	            // Display the customized error message using JOptionPane
	            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);

	            e.printStackTrace();
	        } finally {
	            em.close();
	        }
	    }
	    
	    public void deleteReserva(int rowIndex) {
	        EntityManager em = JPAUtils.getEntityManager();
	        try {		        
	            em.getTransaction().begin();
	            ReservasDao reservaDao = new ReservasDao(em);

	            // Get the ID of the reservation to delete
	            int reservaId = (int) tbReservas.getValueAt(rowIndex, 0);

	            // Retrieve the existing Reserva from the database
	            Reserva existingReserva = reservaDao.buscarPorId(reservaId);

	         // Confirm deletion with a dialog
	            int confirmChoice = JOptionPane.showConfirmDialog(
	                null,
	                "Estas Seguro de que quieres eliminar esta reserva?",
	                "Confirmar Eliminacion",
	                JOptionPane.YES_NO_OPTION
	            );

	            if (confirmChoice == JOptionPane.YES_OPTION) {
	                // Delete the Reserva from the database
	                reservaDao.eliminar(existingReserva);

	                // Commit the transaction
	                em.getTransaction().commit();

	                // Remove the deleted row from the table model
	                modeloReservas.removeRow(rowIndex);

	            }

	        } catch (Exception e) {
	            // Rollback the transaction in case of an exception
	            if (em.getTransaction().isActive()) {
	                em.getTransaction().rollback();
	            }

	            // Extract and customize the error message from the exception
	            String errorMessage = extractErrorMessage(e);

	            // Display the customized error message using JOptionPane
	            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);

	            e.printStackTrace();
	        } finally {
	            em.close();
	        }
	    }
	    
	    public void deleteHuesped(int rowIndex) {
	        EntityManager em = JPAUtils.getEntityManager();
	        try {
	            em.getTransaction().begin();
	            HuespedesDao huespedesDao = new HuespedesDao(em);

	            // Get the ID of the guest to delete
	            int huespedId = (int) tbHuespedes.getValueAt(rowIndex, 0);

	            // Retrieve the existing Huesped from the database
	            Huesped existingHuesped = huespedesDao.buscarPorId(huespedId);

	            // Confirm deletion with a dialog
	            int confirmChoice = JOptionPane.showConfirmDialog(
	                null,
	                "Estas Seguro de que quieres eliminar este huesped?",
	                "Confirmar Eliminacion",
	                JOptionPane.YES_NO_OPTION
	            );

	            if (confirmChoice == JOptionPane.YES_OPTION) {
	                // Delete the Reserva from the database
	            	huespedesDao.eliminar(existingHuesped);

	                // Commit the transaction
	                em.getTransaction().commit();

	                // Remove the deleted row from the table model
	                modeloHuespedes.removeRow(rowIndex);

	            }

	        } catch (Exception e) {
	            // Rollback the transaction in case of an exception
	            if (em.getTransaction().isActive()) {
	                em.getTransaction().rollback();
	            }

	            // Extract and customize the error message from the exception
	            String errorMessage = extractErrorMessage(e);

	            // Display the customized error message using JOptionPane
	            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);

	            e.printStackTrace();
	        } finally {
	            em.close();
	        }
	    }









}
