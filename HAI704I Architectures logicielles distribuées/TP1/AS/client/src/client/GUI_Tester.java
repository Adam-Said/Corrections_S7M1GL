package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import common.IAnimal;
import javax.swing.SwingConstants;
import javax.swing.JScrollBar;

public class GUI_Tester extends JFrame {

	private JPanel Page;
	private JFrame frame;
	private JTextField animalNameInput;
	private JTextField handlerNameInput;
	private JTextField animalSpeciesInput;
	private JTextField animalRaceInput;
	private JTextField displayAddedClient;
	private JTextField animalSearchInput;
	private JTextField getAnimalState;
	private JTextField autoInsertedAnimals;
	private JTextField animalStateInput;
	private JTextField nInsertedAnimals;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_Tester frame = new GUI_Tester();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws ParseException 
	 */
	public GUI_Tester() throws ParseException {
		Client client = null;
		try {
			client = new Client();
		} catch (RemoteException e2) {
			e2.printStackTrace();
		}
		
		try {
			client.startClient();
		} catch (RemoteException e2) {
			e2.printStackTrace();
		}
		
		setTitle("RMI TESTER GUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 778, 504);
		Page = new JPanel();
		Page.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(Page);
		Page.setLayout(null);
		
		JTextArea terminalDisplay = new JTextArea();
		terminalDisplay.setFont(new Font("Dialog", Font.PLAIN, 11));
		terminalDisplay.setBounds(20, 314, 592, 129);
		terminalDisplay.setEditable(false);
		//Page.add(terminalDisplay);
		
		JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(20, 314, 592, 129);
		scrollPane.add(terminalDisplay);
		scrollPane.setViewportView(terminalDisplay);
		Page.add(scrollPane);
		
		JButton insertAnimalsBtn = new JButton("Insérer 5 patients avec leurs propriétaires");
		insertAnimalsBtn.setFont(new Font("Dialog", Font.BOLD, 10));
		insertAnimalsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Client client = null;
				try {
					client = new Client();
				} catch (RemoteException e2) {
					e2.printStackTrace();
				}
				
				try {
					client.startClient();
				} catch (RemoteException e2) {
					e2.printStackTrace();
				}
				
				try {
					client.addAnimal("Cookie", "Arnaud", "Chien", "YorkShire", "Bonne Santé");
					client.addAnimal("Felix", "Adam", "Chien", "Golden Retriever");
					client.addAnimal("Alex", "Jeremie", "Peroquet", "Electus");
					client.addAnimal("Didou", "Marc", "Chat", "Maine Coon", "Bonne Santé");
					client.addAnimal("Lilo", "Stéphane", "Chien", "Bulldog Français", "Nécessite Surveillance");
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
								
				autoInsertedAnimals.setEditable(false);
				autoInsertedAnimals.setForeground(Color.BLUE);
				autoInsertedAnimals.setText("Création des clients terminée ...");
				
				try {
					terminalDisplay.setText("");
					terminalDisplay.setText(client.outputCabinet() + client.getAlertGui());
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});
		insertAnimalsBtn.setBounds(10, 21, 285, 30);
		Page.add(insertAnimalsBtn);
		
		JButton quitBtn = new JButton("Quitter");
		quitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		quitBtn.setForeground(new Color(255, 0, 0));
		quitBtn.setBounds(658, 427, 96, 30);
		Page.add(quitBtn);
		
		animalNameInput = new JTextField();
		animalNameInput.setToolTipText("Nom de l'animal");
		animalNameInput.setBounds(10, 157, 96, 19);
		Page.add(animalNameInput);
		animalNameInput.setColumns(10);
		
		handlerNameInput = new JTextField();
		handlerNameInput.setToolTipText("Nom du maître");
		handlerNameInput.setColumns(10);
		handlerNameInput.setBounds(116, 157, 96, 19);
		Page.add(handlerNameInput);
		
		animalSpeciesInput = new JTextField();
		animalSpeciesInput.setToolTipText("Espèce");
		animalSpeciesInput.setColumns(10);
		animalSpeciesInput.setBounds(222, 157, 96, 19);
		Page.add(animalSpeciesInput);
		
		animalRaceInput = new JTextField();
		animalRaceInput.setToolTipText("Race");
		animalRaceInput.setColumns(10);
		animalRaceInput.setBounds(328, 157, 96, 19);
		Page.add(animalRaceInput);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(203, 225, 327, 20);
		Page.add(separator);
		
		JButton addClientBtn = new JButton("Ajouter");
		addClientBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				Client client = null;
				try {
					client = new Client();
				} catch (RemoteException e2) {
					e2.printStackTrace();
				}
				
				try {
					client.startClient();
				} catch (RemoteException e2) {
					e2.printStackTrace();
				}
				displayAddedClient.setEditable(false);
				
				if(animalStateInput.getText() == null) {
					try { 
						client.addAnimal(animalNameInput.getText(), handlerNameInput.getText(), animalSpeciesInput.getText(), animalRaceInput.getText(), "Non renseigné");		
						displayAddedClient.setText(client.displayAnimal(animalNameInput.getText()).getString());
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				} else {
					try {
						client.addAnimal(animalNameInput.getText(), handlerNameInput.getText(), animalSpeciesInput.getText(), animalRaceInput.getText(), animalStateInput.getText());		
						displayAddedClient.setText(client.displayAnimal(animalNameInput.getText()).getString());
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}	
				
				try {
					terminalDisplay.setText("");
					terminalDisplay.setText(client.outputCabinet());
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}

			}
		});
		addClientBtn.setBounds(546, 151, 96, 30);
		Page.add(addClientBtn);
		
		JLabel newClientTitle = new JLabel("Ajouter un Patient");
		newClientTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		newClientTitle.setBounds(10, 90, 158, 46);
		Page.add(newClientTitle);
		
		displayAddedClient = new JTextField();
		displayAddedClient.setEditable(false);
		displayAddedClient.setToolTipText("Nom du maître");
		displayAddedClient.setColumns(10);
		displayAddedClient.setBounds(97, 186, 272, 19);
		Page.add(displayAddedClient);
		
		JButton clearBtn = new JButton("Clear");
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				animalNameInput.setText("");
				handlerNameInput.setText("");
				animalSpeciesInput.setText("");
				animalRaceInput.setText("");
				displayAddedClient.setText("");
				animalStateInput.setText("");
			}
		});
		clearBtn.setBounds(392, 186, 65, 19);
		Page.add(clearBtn);
		
		JLabel lblObtenirLtatDun = new JLabel("Obtenir l'état d'un patient");
		lblObtenirLtatDun.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblObtenirLtatDun.setBounds(10, 204, 278, 46);
		Page.add(lblObtenirLtatDun);
		
		animalSearchInput = new JTextField();
		animalSearchInput.setToolTipText("Indiquez l'état");
		animalSearchInput.setColumns(10);
		animalSearchInput.setBounds(175, 255, 113, 19);
		Page.add(animalSearchInput);
		
		JLabel lblNewLabel = new JLabel("Nom du patient (animal) :");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 254, 202, 19);
		Page.add(lblNewLabel);
		
		JLabel lblEtatDuPatient = new JLabel("Etat du patient :");
		lblEtatDuPatient.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEtatDuPatient.setBounds(10, 283, 147, 19);
		Page.add(lblEtatDuPatient);
		
		getAnimalState = new JTextField();
		getAnimalState.setHorizontalAlignment(SwingConstants.CENTER);
		getAnimalState.setToolTipText("Indiquez l'état");
		getAnimalState.setColumns(10);
		getAnimalState.setBounds(116, 283, 202, 19);
		Page.add(getAnimalState);
		getAnimalState.setEditable(false);

		
		JButton animalSearch = new JButton("Rechercher");
		animalSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Client client = null;
				try {
					client = new Client();
				} catch (RemoteException e2) {
					e2.printStackTrace();
				}
				
				try {
					client.startClient();
				} catch (RemoteException e2) {
					e2.printStackTrace();
				}
				
				String animalName = animalSearchInput.getText();
				IAnimal animalExist = null;
				try {
					animalExist = client.displayAnimal(animalName);
				} catch (RemoteException e2) {
					e2.printStackTrace();
				} 
				
				if(animalExist != null) {
					try {
						getAnimalState.setForeground(Color.BLACK);
						getAnimalState.setText(client.displayAnimal(animalName).getDossier().getEtat());
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				} else {
					getAnimalState.setForeground(Color.RED);
					getAnimalState.setText("Animal Inexistant");
				}
				
				
				getAnimalState.setEditable(false);
			}
		});
		animalSearch.setBounds(313, 249, 126, 30);
		Page.add(animalSearch);
		
		autoInsertedAnimals = new JTextField();
		autoInsertedAnimals.setBounds(305, 27, 252, 19);
		Page.add(autoInsertedAnimals);
		autoInsertedAnimals.setColumns(10);
		autoInsertedAnimals.setEditable(false);
		
		animalStateInput = new JTextField();
		animalStateInput.setToolTipText("Etat de l'animal");
		animalStateInput.setColumns(10);
		animalStateInput.setBounds(434, 157, 96, 19);
		Page.add(animalStateInput);
		
		JButton btnInsrerClients = new JButton("Insérer 100 patients");
		btnInsrerClients.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Client client = null;
				try {
					client = new Client();
				} catch (RemoteException e2) {
					e2.printStackTrace();
				}
				
				try {
					client.startClient();
				} catch (RemoteException e2) {
					e2.printStackTrace();
				}
									
				try {
					for(int i = 0; i < 100; i++) {
						client.addAnimal( String.valueOf(i), "maitre " + String.valueOf(i), "Chien", "Doge", "Tranquille");
					}
					
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
								
				nInsertedAnimals.setEditable(false);
				nInsertedAnimals.setForeground(Color.BLUE);
				nInsertedAnimals.setText("Insertion réalisée avec succès");
				
				try {
					terminalDisplay.setText("");
					terminalDisplay.setText(client.outputCabinet() + "\n" + client.getAlertGui());
					
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});
		
		btnInsrerClients.setBounds(176, 66, 170, 30);
		Page.add(btnInsrerClients);
		
		nInsertedAnimals = new JTextField();
		nInsertedAnimals.setColumns(10);
		nInsertedAnimals.setBounds(356, 72, 252, 19);
		Page.add(nInsertedAnimals);
		nInsertedAnimals.setEditable(false);
		
		JLabel lblInsertionDeClients = new JLabel("Insertion de patients");
		lblInsertionDeClients.setFont(new Font("Dialog", Font.BOLD, 14));
		lblInsertionDeClients.setBounds(10, 57, 278, 46);
		Page.add(lblInsertionDeClients);
		
		JButton btnVoirLeCabinet = new JButton("Voir le cabinet");
		btnVoirLeCabinet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Client client = null;
				try {
					client = new Client();
				} catch (RemoteException e2) {
					e2.printStackTrace();
				}
				
				try {
					client.startClient();
				} catch (RemoteException e2) {
					e2.printStackTrace();
				}
				
				try {
					terminalDisplay.setText(client.outputCabinet() + "\n" + client.getAlertGui());
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
									
			}
		});
		btnVoirLeCabinet.setBounds(584, 214, 170, 30);
		Page.add(btnVoirLeCabinet);
		
		JButton btnClearTerminal = new JButton("Clear Terminal");
		btnClearTerminal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				terminalDisplay.setText("");
			}
		});
		btnClearTerminal.setBounds(584, 253, 170, 30);
		Page.add(btnClearTerminal);
		
		JLabel lblNomanimal = new JLabel("Nom animal");
		lblNomanimal.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNomanimal.setBounds(33, 132, 60, 19);
		Page.add(lblNomanimal);
		
		JLabel lblNomMatre = new JLabel("Nom maître");
		lblNomMatre.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNomMatre.setBounds(141, 132, 60, 19);
		Page.add(lblNomMatre);
		
		JLabel lblEspece = new JLabel("Espece");
		lblEspece.setHorizontalAlignment(SwingConstants.CENTER);
		lblEspece.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblEspece.setBounds(240, 132, 60, 19);
		Page.add(lblEspece);
		
		JLabel lblRace = new JLabel("Race");
		lblRace.setHorizontalAlignment(SwingConstants.CENTER);
		lblRace.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblRace.setBounds(345, 132, 60, 19);
		Page.add(lblRace);
		
		JLabel lblEtat = new JLabel("Etat");
		lblEtat.setHorizontalAlignment(SwingConstants.CENTER);
		lblEtat.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblEtat.setBounds(454, 132, 60, 19);
		Page.add(lblEtat);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(178, 113, 458, 20);
		Page.add(separator_1);
		
		JLabel lblClientCre = new JLabel("Patient créée : ");
		lblClientCre.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblClientCre.setBounds(10, 186, 96, 19);
		Page.add(lblClientCre);
		
		} 
}
