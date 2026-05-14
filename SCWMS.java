package padillo;

import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;

public class SCWMS extends JFrame {

	JTextField tfName, tfCourse, tfYear, tfBalance, tfStatus;
	JButton btnAdd, btnUpd, btnDel, btnClr;
	DefaultTableModel model;
	JTable table;
	JScrollPane scrollPane;

	SCWMS() {
		setTitle("School Canteen Wallet Management System");
		setSize(900, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);

		// LABELS
		add(new JLabel("Full Name")).setBounds(20, 260, 200, 20);
		add(new JLabel("Course/Department")).setBounds(20, 310, 200, 20);
		add(new JLabel("Year Level")).setBounds(20, 360, 200, 20);
		add(new JLabel("Wallet Balance")).setBounds(20, 410, 200, 20);
		add(new JLabel("Status (Active or Suspended)")).setBounds(20, 460, 200, 20);

		// TEXTFIELDS
		add(tfName = new JTextField()).setBounds(20, 280, 200, 20);
		add(tfCourse = new JTextField()).setBounds(20, 330, 200, 20);
		add(tfYear = new JTextField()).setBounds(20, 380, 200, 20);
		add(tfBalance = new JTextField()).setBounds(20, 430, 200, 20);
		add(tfStatus = new JTextField()).setBounds(20, 480, 200, 20);

		// TABLE
		String[] columns = { "No.", "Full Name", "Course/Dept", "Year Level", "Wallet Balance", "Status" };
		model = new DefaultTableModel(columns, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable(model);
		scrollPane = new JScrollPane(table);
		add(scrollPane).setBounds(240, 10, 630, 490);

		// BUTTONS
		btnAdd = new JButton("Add");
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setBackground(Color.BLUE);
		add(btnAdd).setBounds(100, 515, 120, 25);

		btnClr = new JButton("Clear Fields");
		btnClr.setForeground(Color.RED);
		btnClr.setBackground(Color.WHITE);
		add(btnClr).setBounds(240, 515, 120, 25);

		btnUpd = new JButton("Update Record");
		btnUpd.setForeground(Color.BLUE);
		btnUpd.setBackground(Color.WHITE);
		add(btnUpd).setBounds(620, 515, 120, 25);

		btnDel = new JButton("Delete Record");
		btnDel.setForeground(Color.WHITE);
		btnDel.setBackground(Color.RED);
		add(btnDel).setBounds(750, 515, 120, 25);

		refreshTable();

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();

				if (row != -1) {
					tfName.setText(model.getValueAt(row, 1).toString());
					tfCourse.setText(model.getValueAt(row, 2).toString());
					tfYear.setText(model.getValueAt(row, 3).toString());
					tfBalance.setText(model.getValueAt(row, 4).toString());
					tfStatus.setText(model.getValueAt(row, 5).toString());
				}
			}
		});

		btnAdd.addActionListener(e -> addFunc());
		btnClr.addActionListener(e -> clear());
		btnUpd.addActionListener(e -> updFunc());
		btnDel.addActionListener(e -> delFunc());

		setVisible(true);
	}

	void addFunc() {
		if (validation()) {
			String name = tfName.getText();
			String course = tfCourse.getText();
			String year = tfYear.getText();
			String balance = tfBalance.getText();
			String status = tfStatus.getText();

			try (BufferedWriter bw = new BufferedWriter(new FileWriter("records.txt", true))) {
				bw.write(name + "#" + course + "#" + year + "#" + balance + "#" + status + "\n");
				bw.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Saving Failed!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			clear();
			refreshTable();

			JOptionPane.showMessageDialog(this, "Record added Successfully!", "Add Record", JOptionPane.PLAIN_MESSAGE);
		}
	}

	void updFunc() {
		int row = table.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Please select a record to update.", "Update Record", JOptionPane.PLAIN_MESSAGE);
			return;
		}

		if (row >= 0 && validation()) {
			ArrayList<String> lines = new ArrayList<>();

			try (BufferedReader br = new BufferedReader(new FileReader("records.txt"))) {
				String line;
				int index = 0;

				while ((line = br.readLine()) != null) {
					if (index == row) {
						String update = tfName.getText() + "#" + tfCourse.getText() + "#" + tfYear.getText() 
								+ "#" + tfBalance.getText() + "#" + tfStatus.getText();
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

			try (BufferedWriter bw = new BufferedWriter(new FileWriter("records.txt"))) {
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

			JOptionPane.showMessageDialog(this, "Record updated successfully!", "Update Record", JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	void delFunc() {
		int row = table.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Please select a record to delete.", "Delete Record", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		
		int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this record?", "Confirmation", JOptionPane.YES_NO_OPTION);
		if (confirm != JOptionPane.YES_OPTION) return;
		
		ArrayList<String> lines = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader("records.txt"))) {
			String line;
			int index = 0;

			while ((line = br.readLine()) != null) {
				if (index != row) {
					lines.add(line);
				}
				index++;
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error loading data!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try (BufferedWriter bw = new BufferedWriter(new FileWriter("records.txt"))) {
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

		JOptionPane.showMessageDialog(this, "Record deleted successfully!", "Delete Record", JOptionPane.PLAIN_MESSAGE);
	}

	void refreshTable() {
		model.setRowCount(0);
		try (BufferedReader br = new BufferedReader(new FileReader("records.txt"))) {
			String line;
			int num = 1;

			while ((line = br.readLine()) != null) {
				String[] data = line.split("#");
				Object[] rows = new Object[6];
				rows[0] = num;

				for (int x = 0; x < data.length; x++) {
					rows[x + 1] = data[x];
				}
				model.addRow(rows);
				num++;
			}
			br.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error loading data!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	boolean validation() {
		try {
			if (tfName.getText().isEmpty() || tfCourse.getText().isEmpty()) {
				throw new Exception("Please fill in Full Name or Course field");
			}

			int num =Integer.parseInt(tfYear.getText());
			double bal = Double.parseDouble(tfBalance.getText());
			String s = tfStatus.getText().trim().toUpperCase();

			if (!s.equals("ACTIVE") && !s.equals("SUSPENDED")) {
				throw new Exception("Status must be Active or Suspended.");
			}
			return true;
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Year Level and Wallet Balance must be numeric/decimal.");
			return false;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return false;
		}
	}
	
	// ++++++++++++++++++++++ CLEAR FIELDS ++++++++++++++++++++++
	void clear() {
		tfName.setText("");
		tfCourse.setText("");
		tfYear.setText("");
		tfBalance.setText("");
		tfStatus.setText("");
	}
	
	// ++++++++++++++++++++++ MAIN METHOD ++++++++++++++++++++++
	public static void main(String[] args) {
		new SCWMS();
	}

}
