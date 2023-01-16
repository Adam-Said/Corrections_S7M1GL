package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


import commons.Animal;
import commons.CabinetVeterinaire;

public class Interface extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField nomAnimal;
	private JTextField nomMaitre;
	private JTextField nomRace;
	private JTextField etatSante;
	private DefaultTableModel model;
	private JLabel lblNomDesAnimaux;
	private JLabel lbltaillecabinet;
	JComboBox<String> comboBox;
	ArrayList<Animal> cab;
	Registry registry = LocateRegistry.getRegistry();
	CabinetVeterinaire cabinet = (CabinetVeterinaire) registry.lookup("Cabinet");
	String header[] = new String[] {"Nom animal", "Nom maitre"};
	int row, col;
	String nomEspece;
	private JTable table;
	/**
	 * Launch the application.
	 * @throws RemoteException 
	 * @throws NotBoundException 
	 */
	public static void main(String[] args) throws RemoteException, NotBoundException {
	
		
		// On suppose le cabinet déjà créer
			// On demande d'ajouter un animal 
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface frame = new Interface();
					frame.setVisible(true);
					Toolkit toolkit = Toolkit.getDefaultToolkit();  
					Dimension screenSize = toolkit.getScreenSize();  
					int x = (screenSize.width - frame.getWidth()) / 2;  
					int y = (screenSize.height - frame.getHeight()) / 2; 
					frame.setLocation(x, y); 
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws RemoteException 
	 * @throws NotBoundException 
	 */
	public Interface() throws RemoteException, NotBoundException {
	
		
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100,100,1200,600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel lblNomAnimal = new JLabel("Nom animal : ");
		lblNomAnimal.setBounds(12, 28, 144, 15);
		contentPane.add(lblNomAnimal);
		
		JLabel lblNomAnimal_1 = new JLabel("Nom maître : ");
		lblNomAnimal_1.setBounds(538, 28, 144, 15);
		contentPane.add(lblNomAnimal_1);
		
		JLabel lblNomAnimal_1_1 = new JLabel("Espèce : ");
		lblNomAnimal_1_1.setBounds(12, 63, 144, 15);
		contentPane.add(lblNomAnimal_1_1);
		
		JLabel lblNomAnimal_1_1_1 = new JLabel("Race : ");
		lblNomAnimal_1_1_1.setBounds(538, 63, 144, 15);
		contentPane.add(lblNomAnimal_1_1_1);
		
		JLabel lblNomAnimal_1_1_1_1 = new JLabel("Etat de santé : ");
		lblNomAnimal_1_1_1_1.setBounds(538, 103, 144, 15);
		contentPane.add(lblNomAnimal_1_1_1_1);
		
		nomAnimal = new JTextField();
		nomAnimal.addActionListener(this);
		nomAnimal.setBounds(130, 26, 144, 19);
		contentPane.add(nomAnimal);
		nomAnimal.setColumns(10);
		
		nomMaitre = new JTextField();
		nomMaitre.addActionListener(this);
		nomMaitre.setColumns(10);
		nomMaitre.setBounds(723, 26, 144, 19);
		contentPane.add(nomMaitre);
		
		nomRace = new JTextField();
		nomRace.addActionListener(this);
		nomRace.setColumns(10);
		nomRace.setBounds(723, 61, 144, 19);
		contentPane.add(nomRace);
		
		etatSante = new JTextField();
		etatSante.addActionListener(this);
		etatSante.setColumns(10);
		etatSante.setBounds(723, 90, 206, 42);
		contentPane.add(etatSante);
		
	
		                                  
		
		JToggleButton tglbtnSupprimer = new JToggleButton("Supprimez");
		tglbtnSupprimer.addActionListener(this);
		tglbtnSupprimer.setBounds(150, 182, 123, 25);
		contentPane.add(tglbtnSupprimer);
		
		
		lblNomDesAnimaux = new JLabel("Nom des animaux présents");
		lblNomDesAnimaux.setBounds(45, 293, 206, 31);
		contentPane.add(lblNomDesAnimaux);
	
		
		
		comboBox = new JComboBox<String>();
		comboBox.addItem("");
		comboBox.addItem("Chien");
		comboBox.addItem("Chat");
		comboBox.setBounds(130, 58, 144, 24);
//		comboBox.addItemListener(this);
		contentPane.add(comboBox);
		
		JToggleButton tglbtnValidez_1 = new JToggleButton("Validez");
		tglbtnValidez_1.addActionListener(this);
		tglbtnValidez_1.setBounds(31, 182, 86, 25);
		contentPane.add(tglbtnValidez_1);
		
		
		
		
		model = new DefaultTableModel(header,0);
		
		table = new JTable(model) {
			private static final long serialVersionUID = 1L;

	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	        };	
		};
	
	
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setVisible(true);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(185, 349, 731, 187);
		contentPane.add(scrollPane);
		
		lbltaillecabinet= new JLabel("Aucun patient dans le cabinet");
		lbltaillecabinet.setBounds(514, 241, 255, 15);
		contentPane.add(lbltaillecabinet);
		if (cabinet.size() > 0) {
			lbltaillecabinet.setText("Nombre d'animaux : " + cabinet.size());
			for (int i = 0; i < cabinet.size(); i++) {
				model.addRow(new Object[] { cabinet.getCabinet().get(i).getNom(), cabinet.getCabinet().get(i).getMaitre() });
			}
			if(cabinet.size() > 30) {
				lbltaillecabinet.setForeground(Color.red);
			}
		}
		
		
		JToggleButton tglbtnaddnpatients = new JToggleButton("Ajoutez 10 patients");
		tglbtnaddnpatients.addActionListener(this);
		tglbtnaddnpatients.setBounds(303, 182, 172, 25);
		contentPane.add(tglbtnaddnpatients);
		
		
	
	
	}

	// Je récupère la valeur des buttons
	
	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand(); 
		if(command.equals("Validez")) {
			
			try {
				
				String espece = (String) comboBox.getSelectedItem();
				
				if(!nomAnimal.getText().equals("") && !nomMaitre.getText().equals("") && !espece.equals("") && !nomRace.getText().equals("") && !etatSante.getText().equals("") && !cabinet.animalExists(nomAnimal.getText(), nomMaitre.getText())) {
				cabinet.addAnimal(nomAnimal.getText(), nomMaitre.getText(), espece, nomRace.getText(), etatSante.getText());
				
				model.addRow(new Object[] { nomAnimal.getText(), nomMaitre.getText()});
				
				
				lbltaillecabinet.setText("Nombre d'animaux : " + cabinet.size());
				JOptionPane.showMessageDialog(null, "Vous venez d'ajouter un animal");
				if(cabinet.size() > 30) {
					lbltaillecabinet.setForeground(Color.red);
					JOptionPane.showMessageDialog(null, "Votre cabinet contient plus de 30 animaux");
				}
				}
				else{
					JOptionPane.showMessageDialog(null, "Vous n'avez pas saisis tous les champs ou vous avez voulu insérer un animal déjà existant");
				}
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		if(command.equals("Supprimez")) {
			
			try {
				int column = 0;
				int row = table.getSelectedRow();
				if(row != -1) {
				int sizebefore = cabinet.size(); 
				cabinet.removeAnimal(table.getModel().getValueAt(row, 0).toString(),table.getModel().getValueAt(row, 1).toString());
				lbltaillecabinet.setText("Nombre d'animaux : " + cabinet.size());
				
				model.removeRow(row);
				JOptionPane.showMessageDialog(null, "Vous venez de supprimer un animal");
				if(sizebefore == 30 && cabinet.size() == 29) {
					JOptionPane.showMessageDialog(null, "Votre cabinet est repassé sous le seuil de 30 animaux");
				}
				if(cabinet.size() < 30) {
					lbltaillecabinet.setForeground(Color.black);
				}
				
				}
				else {
					JOptionPane.showMessageDialog(null, "Veuillez sélectionner la ligne que vous voulez supprimer");
				}
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(command.equals("Ajoutez 10 patients")) {
			
			try {
				
				for (int i = 0; i < 10; i++) {
					cabinet.addAnimal("Toto", "MaitreToto", "Chien", "Labrador", "Bonne santé");
					model.addRow(new Object[] { "Toto", "MaitreToto"});
				}
				lbltaillecabinet.setText("Nombre d'animaux : " + cabinet.size());
				if(cabinet.size() >= 30) {
					lbltaillecabinet.setForeground(Color.red);
					JOptionPane.showMessageDialog(null, "Votre cabinet contient plus de 30 animaux");
				}
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
	}
}
