package padillo;

import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;

public class HotelReservation extends JFrame {
	
	JTextField guestName, roomType, inDate, outDate;
	JTable table;
	DefaultTableModel model;
	JScrollPane scrollPane;

	HotelReservation() {
		setTitle("Pete's Hotel Reservation System");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(700, 730);
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JLabel record = new JLabel("Reservation Records");
		record.setFont(new Font("Arial", Font.BOLD, 16));
		add(record).setBounds(10, 0, 200, 20);
		
		String[] columns = {"No.", "Guest Name", "Room Type", "Check-in Date", "Check-out Date" };
		model = new DefaultTableModel(columns, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable(model);
		scrollPane = new JScrollPane(table);
		add(scrollPane).setBounds(10, 25, 665, 500);
		
		// LABELS
		add(new JLabel("Guest Name")).setBounds(10, 530, 150, 30);
		add(new JLabel("Room Type")).setBounds(180, 530, 150, 30);
		add(new JLabel("Check-in Date")).setBounds(350, 530, 150, 30);
		add(new JLabel("Check-out Date")).setBounds(520, 530, 150, 30);
		
		// TEXTFIELDS
		add(guestName = new JTextField()).setBounds(10, 560, 150, 25);
		add(roomType = new JTextField()).setBounds(180, 560, 150, 25);
		add(inDate = new JTextField()).setBounds(350, 560, 150, 25);
		add(outDate = new JTextField()).setBounds(520, 560, 150, 25);
		
		// BUTTONS
		JButton btnAdd = new JButton("Add");
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setBackground(Color.BLUE);
		add(btnAdd).setBounds(540,600, 130, 23);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setForeground(Color.BLUE);
		btnUpdate.setBackground(Color.WHITE);
		add(btnUpdate).setBounds(540, 630, 130, 23);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setForeground(Color.RED);
		btnDelete.setBackground(Color.WHITE);
		add(btnDelete).setBounds(540, 660, 130, 23);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setForeground(Color.WHITE);
		btnExit.setBackground(Color.RED);
		add(btnExit).setBounds(10, 660, 100, 23);
		
		refreshTable();
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				
				if (row != -1) {
					guestName.setText(model.getValueAt(row, 1).toString());
					roomType.setText(model.getValueAt(row, 2).toString());
					inDate.setText(model.getValueAt(row, 3).toString());
					outDate.setText(model.getValueAt(row, 4).toString());
				}
			}
		});
		
		// Buttons functions
		btnAdd.addActionListener(e -> addRecord());
		btnUpdate.addActionListener(e -> updateRecord());
		btnDelete.addActionListener(e -> deleteRecord());
		btnExit.addActionListener(e -> {
			int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Confirmation", JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) System.exit(0);
		});
		
		setVisible(true);
	}
	
	public void addRecord() {
		if (guestName.getText().trim().isEmpty() || roomType.getText().trim().isEmpty() ||
				inDate.getText().trim().isEmpty() || outDate.getText().trim().isEmpty()) {
			
			JOptionPane.showMessageDialog(this, "Please fill in all fields.");
			return;
		}
		
		String name = guestName.getText();
		String room = roomType.getText();
		String in = inDate.getText();
		String out = outDate.getText();
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("reservations.txt", true))) {
			bw.write(name + "#" + room + "#" + in + "#" + out + "\n");
			bw.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Saving Failed!", "Error", JOptionPane.ERROR_MESSAGE);
			System.err.println("ERROR" + e);
		}
		
		clear();
		refreshTable();
		
		JOptionPane.showMessageDialog(this, "Reservation added Successfully!", "Add Reservation", JOptionPane.PLAIN_MESSAGE);
	}
	
	public void updateRecord() {
		int slctdRow = table.getSelectedRow();
		if (slctdRow == -1) {
			JOptionPane.showMessageDialog(this, "Please select a record to update.", "Update Reservation", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		
		if (guestName.getText().trim().isEmpty() || roomType.getText().trim().isEmpty() ||
				inDate.getText().trim().isEmpty() || outDate.getText().trim().isEmpty()) {
			
			JOptionPane.showMessageDialog(this, "Please fill in all fields.");
			return;
		}
		
		int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to update this record?", "Confirmation", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.NO_OPTION) return;
		
		ArrayList<String> lines = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader("reservations.txt"))) {
			String line;
			int index = 0;
			
			while((line = br.readLine()) != null) {
				if (index == slctdRow) {
					String update = guestName.getText() + "#" + roomType.getText() + "#" + inDate.getText() + "#" + outDate.getText();
					lines.add(update);
				} else {
					lines.add(line);
				}
				index++;
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error loading data!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("reservations.txt"))) {
			for (String record : lines) {
				bw.write(record + "\n");
			}
			bw.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Saving Failed!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		clear();
		refreshTable();
		
		JOptionPane.showMessageDialog(this, "Reservation updated Successfully!", "Update Reservation", JOptionPane.PLAIN_MESSAGE);
	}
	
	public void deleteRecord() {
		int slctdRow = table.getSelectedRow();
		if (slctdRow == -1) {
			JOptionPane.showMessageDialog(this, "Please select a record to delete.", "Delete Reservation", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		
		int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this record?", "Confirmation", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.NO_OPTION) return;
		
		ArrayList<String> lines = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader("reservations.txt"))) {
			String line;
			int index = 0;
			
			while((line = br.readLine()) != null) {
				if (index != slctdRow) {
					lines.add(line);
				}
				index++;
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error loading data!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("reservations.txt"))) {
			for (String record : lines) {
				bw.write(record + "\n");
			}
			bw.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Saving Failed!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		clear();
		refreshTable();
		
		JOptionPane.showMessageDialog(this, "Reservation deleted Successfully!", "Update Reservation", JOptionPane.PLAIN_MESSAGE);
	}
	
	public void refreshTable() {
		model.setRowCount(0);
		try (BufferedReader br = new BufferedReader(new FileReader("reservations.txt"))) {
			String line;
			int num = 1;
			
			while ((line = br.readLine()) != null) {
				String[] data = line.split("#");
				Object[] row = new Object[5];
				row[0] = num;
				
				for (int x = 0; x < data.length; x++) {
					row[x+1] = data[x];
				}
				model.addRow(row);
				num++;
			}
			br.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error loading data!", "Error", JOptionPane.ERROR_MESSAGE);
			System.err.println("ERROR" + e);
		}
	}
	
	public void clear() {
		guestName.setText("");
		roomType.setText("");
		inDate.setText("");
		outDate.setText("");
	}

	public static void main(String[] args) {
		new HotelReservation();
	}

}
