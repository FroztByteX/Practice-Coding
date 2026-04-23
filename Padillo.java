package padillo;

import javax.swing.*;
import java.io.*;
import java.awt.*;

public class Padillo extends JFrame {

	static JTextField rcptNum, strName, totCost, tax12, finalAmt;

	Padillo() {

		JLabel head = new JLabel("EXPENSE TRACKER");
		head.setHorizontalAlignment(JLabel.CENTER);
		head.setFont(new Font("Calibri (Body)", Font.BOLD, 20));
		add(head).setBounds(0, 10, 390, 30);

		JLabel lblNum = new JLabel("Receipt Number:");
		add(lblNum).setBounds(30, 50, 100, 25);

		rcptNum = new JTextField();
		add(rcptNum).setBounds(140, 50, 200, 25);

		JLabel lblStrName = new JLabel("Store Name:");
		add(lblStrName).setBounds(30, 90, 100, 25);

		strName = new JTextField();
		add(strName).setBounds(140, 90, 200, 25);

		JLabel lblTotCost = new JLabel("Total Cost:");
		add(lblTotCost).setBounds(30, 130, 100, 25);

		totCost = new JTextField();
		add(totCost).setBounds(140, 130, 200, 25);

		JLabel lblTax = new JLabel("Tax (12%):");
		add(lblTax).setBounds(30, 190, 100, 25);

		tax12 = new JTextField("₱");
		tax12.setEditable(false);
		add(tax12).setBounds(140, 190, 200, 25);

		JLabel lblFinal = new JLabel("Final Amount:");
		add(lblFinal).setBounds(30, 230, 100, 25);

		finalAmt = new JTextField("₱");
		finalAmt.setEditable(false);

		add(finalAmt).setBounds(140, 230, 200, 25);

		JButton btnRecord = new JButton("Record");
		btnRecord.setForeground(Color.WHITE);
		btnRecord.setBackground(Color.BLUE);
		add(btnRecord).setBounds(80, 270, 100, 30);

		btnRecord.addActionListener(e -> {
			record();
		});

		JButton btnClear = new JButton("Clear");
		btnClear.setForeground(Color.WHITE);
		btnClear.setBackground(Color.RED);
		add(btnClear).setBounds(210, 270, 100, 30);

		btnClear.addActionListener(e -> {
			clear();
		});

		setTitle("Expense Tracker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400);
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void record() {
		try {
			String number = rcptNum.getText();
			String name = strName.getText();
			String cost = totCost.getText();
			
			double num = Double.parseDouble(cost);
			double taxAmt = num * 0.12;
			String strTax = String.format("%,.2f", taxAmt);
			tax12.setText("₱ " + strTax);

			double total = num + taxAmt;
			String strAmt = String.format("%,.2f", total);
			finalAmt.setText("₱ " + strAmt);

			FileWriter fw = new FileWriter("data.txt", true);
			fw.write("Receipt Number: " + number + "\n");
			fw.write("Store Name: " + name + "\n");
			fw.write("Total Cost: ₱ " + String.format("%,.2f%n", num));
			fw.write("\nTax (12%): ₱ " + strTax + "\n");
			fw.write("Final Amount: ₱ " + strAmt + "\n");
			fw.write("===================================\n");
			fw.close();

			JOptionPane.showMessageDialog(null, "Data Recorded Successfully!", "Saved to File",
					JOptionPane.PLAIN_MESSAGE);

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Saving Failed!", "Error", JOptionPane.ERROR_MESSAGE);
			System.err.println("ERROR" + e);
		}
	}

	public static void clear() {
		rcptNum.setText("");
		strName.setText("");
		totCost.setText("");
		tax12.setText("₱");
		finalAmt.setText("₱");
	}

	public static void main(String[] args) {
		new Padillo();
	}

}
