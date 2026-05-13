package padillo;

import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

public class StudentForm extends JFrame {

	JTextField tfName, tfCourse, tfSection;
	JTable table;
	DefaultTableModel model;
	JScrollPane scrollPane;

	StudentForm() {
		setTitle("Student Information Form");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(445, 420);
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		
		// LABEL COMPONENTS
		add(new JLabel("Name")).setBounds(10, 5, 100, 25);
		add(new JLabel("Course")).setBounds(150, 5, 100, 25);
		add(new JLabel("Section")).setBounds(290, 5, 100, 25);
		
		// TEXTFIELDS COMPONENTS
		add(tfName = new JTextField()).setBounds(10, 30, 130, 25);
		add(tfCourse = new JTextField()).setBounds(150, 30, 130, 25);
		add(tfSection = new JTextField()).setBounds(290, 30, 130, 25);
		
		// BUTTON COMPONENTS
		JButton btnAdd = new JButton("Add");
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setBackground(Color.BLUE);
		add(btnAdd).setBounds(20, 65, 90, 25);

		JButton btnUpd = new JButton("Update");
		btnUpd.setForeground(Color.BLUE);
		btnUpd.setBackground(Color.WHITE);
		add(btnUpd).setBounds(120, 65, 90, 25);

		JButton btnDel = new JButton("Delete");
		btnDel.setForeground(Color.WHITE);
		btnDel.setBackground(Color.RED);
		add(btnDel).setBounds(220, 65, 90, 25);

		JButton btnClr = new JButton("Clear txt");
		btnClr.setForeground(Color.RED);
		btnClr.setBackground(Color.WHITE);
		add(btnClr).setBounds(320, 65, 90, 25);
		
		// TABLE
		String[] columns = { "No.", "Name", "Course", "Section" };
		model = new DefaultTableModel(columns, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable(model);
		scrollPane = new JScrollPane(table);
		add(scrollPane).setBounds(10, 100, 410, 270);

		refreshTable();

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();

				if (row != -1) {
					tfName.setText(model.getValueAt(row, 1).toString());
					tfCourse.setText(model.getValueAt(row, 2).toString());
					tfSection.setText(model.getValueAt(row, 3).toString());
				}
			}
		});
		
		// BUTTON FUNCTIONS
		btnAdd.addActionListener(e -> AddFunction());
		btnUpd.addActionListener(e -> UpdateFunction());
		btnDel.addActionListener(e -> DeleteFuction());
		btnClr.addActionListener(e -> clear());

		setVisible(true);
	}

	void AddFunction() {
		if (tfName.getText().trim().isEmpty() || tfCourse.getText().trim().isEmpty()
				|| tfSection.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please fill in all fields.");
			return;
		}

		String name = tfName.getText();
		String course = tfCourse.getText();
		String section = tfSection.getText();

		try (BufferedWriter bw = new BufferedWriter(new FileWriter("records.txt", true))) {
			bw.write(name + "#" + course + "#" + section + "\n");
			bw.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "ERROR saving file", "ERROR 101", JOptionPane.ERROR_MESSAGE);
			return;
		}

		clear();
		refreshTable();

		JOptionPane.showMessageDialog(this, "Student Information added successfully!", "Add Seccess",
				JOptionPane.PLAIN_MESSAGE);
	}

	void UpdateFunction() {
		int slctdRow = table.getSelectedRow();
		if (slctdRow == -1) {
			JOptionPane.showMessageDialog(this, "Please select a record to update.", "Update Record",
					JOptionPane.PLAIN_MESSAGE);
			return;
		}

		if (tfName.getText().trim().isEmpty() || tfCourse.getText().trim().isEmpty()
				|| tfSection.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please fill in all fields.");
			return;
		}

		int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to update this record",
				"Confirm Update", JOptionPane.YES_NO_OPTION);
		if (confirm != JOptionPane.YES_OPTION)
			return;

		ArrayList<String> data = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader("records.txt"))) {
			String line;
			int row = 0;

			while ((line = br.readLine()) != null) {
				if (row == slctdRow) {
					String update = tfName.getText() + "#" + tfCourse.getText() + "#" + tfSection.getText();
					data.add(update);
				} else {
					data.add(line);
				}
				row++;
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error loading data!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try (BufferedWriter bw = new BufferedWriter(new FileWriter("records.txt"))) {
			for (String record : data) {
				bw.write(record + "\n");
			}
			bw.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "ERROR saving file", "ERROR 101", JOptionPane.ERROR_MESSAGE);
			return;
		}

		clear();
		refreshTable();

		JOptionPane.showMessageDialog(this, "Student Information updated successfully!", "Update Seccess",
				JOptionPane.PLAIN_MESSAGE);
	}

	void DeleteFuction() {
		int slctdRow = table.getSelectedRow();
		if (slctdRow == -1) {
			JOptionPane.showMessageDialog(this, "Please select a record to delete.", "Delete Record",
					JOptionPane.PLAIN_MESSAGE);
			return;
		}

		if (tfName.getText().trim().isEmpty() || tfCourse.getText().trim().isEmpty()
				|| tfSection.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please fill in all fields.");
			return;
		}

		int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this record",
				"Confirm Delete", JOptionPane.YES_NO_OPTION);
		if (confirm != JOptionPane.YES_OPTION)
			return;

		ArrayList<String> data = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader("records.txt"))) {
			String line;
			int row = 0;

			while ((line = br.readLine()) != null) {
				if (row != slctdRow) {
					data.add(line);
				}
				row++;
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error loading data!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try (BufferedWriter bw = new BufferedWriter(new FileWriter("records.txt"))) {
			for (String record : data) {
				bw.write(record + "\n");
			}
			bw.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "ERROR saving file", "ERROR 101", JOptionPane.ERROR_MESSAGE);
			return;
		}

		clear();
		refreshTable();

		JOptionPane.showMessageDialog(this, "Student Information deleted successfully!", "Delete Seccess",
				JOptionPane.PLAIN_MESSAGE);
	}

	void refreshTable() {
		model.setRowCount(0);
		try (BufferedReader br = new BufferedReader(new FileReader("records.txt"))) {
			String line;
			int num = 1;

			while ((line = br.readLine()) != null) {
				String[] data = line.split("#");
				Object[] row = new Object[4];
				row[0] = num;

				for (int x = 0; x < data.length; x++) {
					row[x + 1] = data[x];
				}
				model.addRow(row);
				num++;
			}
			br.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error loading data!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	void clear() {
		tfName.setText("");
		tfCourse.setText("");
		tfSection.setText("");
	}

	public static void main(String[] args) {
		new StudentForm();
	}

}
