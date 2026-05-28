package Practice;

import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;

public class SPMS extends JFrame {

	public static void main(String[] args) {
		new SPMS();
	}

	JPanel mainPanel, tblPanel;

	JTextField tfProdId, tfFlavor, tfName, tfStock, tfPrice, tfDate;
	JButton btnAdd, btnUpd, btnDel, btnClr, btnExit;
	JComboBox<String> cbStatus;

	DefaultTableModel model;
	JTable table;
	TableRowSorter<DefaultTableModel> sorter;
	JScrollPane scrlPane;

	private String filename = "siopao_data.txt";
	private String d = "#"; // Delimiter

	private Color bgColor = new Color(0xFDCFFA);
	private Color addBtnColor = new Color(0x4E56C0);
	private Color btnColor = new Color(0xD78FEE);
	private Color btnTxtColor = new Color(0xFFFFFF);
	private Color tableTxtColor = new Color(0xFFFFFF);
	private Color tblHeadColor = new Color(0x9B5DE0);

	SPMS() {

		// +++++++++++++++ WINDOW SETTINGS +++++++++++++++
		setTitle("Siopao Product Management System");
		setSize(1080, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		// +++++++++++++++ MAIN PANEL +++++++++++++++
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(bgColor);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

		JLabel head = new JLabel("Siopao Product Management System");
		head.setFont(new Font("Tahoma", Font.BOLD, 22));
		head.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		mainPanel.add(head, BorderLayout.NORTH);

		add(mainPanel, BorderLayout.SOUTH);

		// +++++++++++++++ LABELS & FIELDS +++++++++++++++
		JPanel tfPanel = new JPanel(new GridLayout(1, 7, 10, 10));
		tfPanel.setBackground(bgColor);

		tfProdId = new JTextField();
		tfFlavor = new JTextField();
		tfName = new JTextField();
		tfStock = new JTextField();
		tfPrice = new JTextField();
		tfDate = new JTextField();
		cbStatus = new JComboBox<>(new String[] { "", "Available", "Low Stock", "Out of Stock" });

		tfPanel.add(panel("Product ID:", tfProdId));
		tfPanel.add(panel("Siopao Flavor:", tfFlavor));
		tfPanel.add(panel("Product Name:", tfName));
		tfPanel.add(panel("Stock Quantity:", tfStock));
		tfPanel.add(panel("Price:", tfPrice));
		tfPanel.add(panel("Production Date:", tfDate));
		tfPanel.add(panel("Product Status:", cbStatus));

		mainPanel.add(tfPanel, BorderLayout.CENTER);

		// +++++++++++++++ BUTTON +++++++++++++++
		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING, 10, 20));
		btnPanel.setBackground(bgColor);

		btnAdd = new JButton("Add");
		btnAdd.setForeground(Color.white);
		btnAdd.setBackground(addBtnColor);

		btnUpd = new JButton("Update");
		btnUpd.setForeground(btnTxtColor);
		btnUpd.setBackground(btnColor);

		btnDel = new JButton("Delete");
		btnDel.setForeground(btnTxtColor);
		btnDel.setBackground(btnColor);

		btnClr = new JButton("Clear");
		btnClr.setForeground(btnTxtColor);
		btnClr.setBackground(btnColor);

		btnExit = new JButton("Exit");
		btnExit.setForeground(Color.red);
		btnExit.setBackground(bgColor);

		btnPanel.add(btnAdd);
		btnPanel.add(btnUpd);
		btnPanel.add(btnDel);
		btnPanel.add(btnClr);
		btnPanel.add(btnExit);

		mainPanel.add(btnPanel, BorderLayout.SOUTH);

		// +++++++++++++++ TABLE +++++++++++++++
		String[] columns = { "Product ID", "Siopao Flavor", "Product Name", "Stock Quantity", "Price",
				"Production Date", "Product Status" };
		model = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable(model);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
		table.getTableHeader().setForeground(tableTxtColor);
		table.getTableHeader().setBackground(tblHeadColor);

		// TABLE SORTER
		sorter = new TableRowSorter<>(model);
		table.setRowSorter(sorter);
		scrlPane = new JScrollPane(table);

		// TABLE PANEL
		tblPanel = new JPanel(new BorderLayout());
		tblPanel.setBackground(bgColor);
		tblPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		tblPanel.add(scrlPane, BorderLayout.CENTER);

		add(tblPanel, BorderLayout.CENTER);

		refreshTable();

		// +++++++++++++++ TABLE CLICK +++++++++++++++
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();

				if (row != -1) {
					tfProdId.setText(model.getValueAt(row, 0).toString());
					tfFlavor.setText(model.getValueAt(row, 1).toString());
					tfName.setText(model.getValueAt(row, 2).toString());
					tfStock.setText(model.getValueAt(row, 3).toString());
					tfPrice.setText(model.getValueAt(row, 4).toString());
					tfDate.setText(model.getValueAt(row, 5).toString());
					cbStatus.setSelectedItem(model.getValueAt(row, 6).toString());
				}
			}
		});

		// +++++++++++++++ BUTTON CLICK +++++++++++++++
		btnAdd.addActionListener(e -> addFunc());
		btnUpd.addActionListener(e -> updFunc());
		btnDel.addActionListener(e -> delFunc());
		btnClr.addActionListener(e -> clearFields());
		btnExit.addActionListener(e -> {
			int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit? ;((", "Confirm",
					JOptionPane.YES_NO_OPTION);
			if (confirm != JOptionPane.YES_OPTION)
				return;
			else
				System.exit(0);
		});

		setVisible(true);
	}

	// +++++++++++++++ USER METHODS +++++++++++++++

	private JPanel panel(String txt, Component field) {

		JPanel panel = new JPanel(new GridLayout(2, 1));
		panel.setBackground(bgColor);
		panel.add(new JLabel(txt));
		panel.add(field);

		return panel;
	}

	// STRING DATA +++++++++++++++
	private String data() {
		String id = tfProdId.getText();
		String flavor = tfFlavor.getText();
		String name = tfName.getText();
		String stock = tfStock.getText();
		double dbPrice = Double.parseDouble(tfPrice.getText());
		String price = String.format("%.2f", dbPrice);
		String date = tfDate.getText();
		String status = cbStatus.getSelectedItem().toString();

		return id + d + flavor + d + name + d + stock + d + price + d + date + d + status;
	}

	// ADD BUTTON FUNCTION +++++++++++++++
	void addFunc() {
		if (validation() && checkDup()) {

			try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
				bw.write(data() + "\n");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Error writing to file: " + e.getMessage(), "File Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			clearFields();
			refreshTable();

			JOptionPane.showMessageDialog(this, "Record was ADDED successfully!");
		}
	}

	// UPDATE BUTTON FUNCTION +++++++++++++++
	void updFunc() {
		int row = table.getSelectedRow();

		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Please select a row to update.");
			return;
		}

		if (!(row >= 0 && validation()))
			return;

		// Get the original ID before editing
		String oldID = model.getValueAt(row, 0).toString();

		// Check duplicates while ignoring old ID
		if (!checkDup(oldID))
			return;

		ArrayList<String> lines = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			int index = 0;

			while ((line = br.readLine()) != null) {
				if (index == row) {
					lines.add(data());
				} else {
					lines.add(line);
				}
				index++;
			}

		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error reading to file: " + e.getMessage(), "File Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		writetoFile(lines);

		clearFields();
		refreshTable();

		JOptionPane.showMessageDialog(this, "Record was UPDATED successfully!");
	}

	// DELETE BUTTON FUNCTION +++++++++++++++
	void delFunc() {
		int row = table.getSelectedRow();

		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Please select a row to update.");
			return;
		}

		int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to DELETE this record? ;((", "Confirm",
				JOptionPane.YES_NO_OPTION);
		if (confirm != JOptionPane.YES_OPTION)
			return;

		ArrayList<String> lines = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			int index = 0;

			while ((line = br.readLine()) != null) {
				if (index != row) {
					lines.add(line);
				}
				index++;
			}

		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error reading to file: " + e.getMessage(), "File Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		writetoFile(lines);

		clearFields();
		refreshTable();

		JOptionPane.showMessageDialog(this, "Record was DELETED successfully!");

	}

	// WRITES BACK TO FILE FOR UPDATE & DELETE +++++++++++++++
	void writetoFile(ArrayList<String> lines) {

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
			for (String record : lines) {
				bw.write(record + "\n");
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error writing to file: " + e.getMessage(), "File Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	// CLEAR BUTTON FUNCTION +++++++++++++++
	void clearFields() {
		tfProdId.setText("");
		tfFlavor.setText("");
		tfName.setText("");
		tfStock.setText("");
		tfPrice.setText("");
		tfDate.setText("");
		cbStatus.setSelectedIndex(0);

		table.clearSelection();
	}

	// RELOADS THE TABLE +++++++++++++++
	void refreshTable() {
		model.setRowCount(0);

		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;

			while ((line = br.readLine()) != null) {
				String[] data = line.split(d);
				model.addRow(data);
			}
		} catch (IOException e) {
			File file = new File(filename);
			try {
				file.createNewFile();
			} catch (IOException x) {
				JOptionPane.showMessageDialog(this, "Cannot make file: " + x.getMessage(), "File Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	// CHECKS THE INPUTS +++++++++++++++
	private boolean validation() {
		try {
			// CHECKS EMPTY FIELDS
			if (tfProdId.getText().isBlank() || tfFlavor.getText().isBlank() || tfName.getText().isBlank()
					|| tfDate.getText().isBlank()) {
				JOptionPane.showMessageDialog(this, "Please fill in all fields. Thank you!", "Empty Fields",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}

			// CHECKS NUMBER
			Integer.parseInt(tfStock.getText());
			Double.parseDouble(tfPrice.getText());

			// CHECKS DATE FORMAT
			if (!tfDate.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
				JOptionPane.showMessageDialog(this, "Date format: YYYY-MM-DD", "Date Format Error",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}

			// CHECKS IF STATUS IS SELECTED
			String stat = cbStatus.getSelectedItem().toString();
			if (stat.equals("")) {
				JOptionPane.showMessageDialog(this, "Please select a Product Status. Thank you!");
				return false;
			}

			return true;
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Stock Quantity/Price must be a numeric/double value.", "Num Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	// CHECKS DUPLICATE PRODUCT ID / DATA +++++++++++++++
	private boolean checkDup() {
		return checkDup(null);
	}

	private boolean checkDup(String oldID) {

		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;

			while ((line = br.readLine()) != null) {
				String[] data = line.split(d);

				String inputID = tfProdId.getText();
				String existingID = data[0];

				// Ignore the original record during update
				if (oldID != null && existingID.equals(oldID)) {
					continue;
				}

				if (inputID.equals(existingID)) {
					JOptionPane.showMessageDialog(this, "The Product ID already exists.\nPlease enter a different ID.",
							"Duplicate ID", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}

			return true;

		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error reading to file: " + e.getMessage(), "File Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
}
