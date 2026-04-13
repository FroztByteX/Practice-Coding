package padillo;

import javax.swing.*;

public class Javax2DHotelReservation {

	public static void main(String[] args) {
		JOptionPane.showMessageDialog(null, "HOTEL RESERVATION SYSTEM", "GRAND P's Hotel",
				JOptionPane.INFORMATION_MESSAGE);

		int hotel[][] = new int[7][5];

		String menu = "MENU:" + "\n[1] View Rooms" + "\n[2] Check In" + "\n[3] Check Out" + "\n[4] Exit"
				+ "\n\nEnter choice: ";

		String choice;

		do {
			choice = JOptionPane.showInputDialog(null, menu, "GRAND P's Hotel", JOptionPane.QUESTION_MESSAGE);

			switch (choice) {
			case "1":
				StringBuilder display = new StringBuilder(
						"===== ROOM STATUS =====\n\n" + "[0] = Available | [1] = Occupied\n\n");

				for (int x = hotel.length - 1; x >= 0; x--) {
					display.append("Floor ").append(x + 1).append(": ");
					for (int y = 0; y < 5; y++) {
						display.append("[" + hotel[x][y] + "]");
					}
					display.append("\n");
				}

				JOptionPane.showMessageDialog(null, display.toString(), "GRAND P's Hotel",
						JOptionPane.INFORMATION_MESSAGE);
				break;

			case "2":
				boolean repeat = false;

				do {
					boolean valid = true;

					int floor = 0;
					while (valid) {
						String floorIn = JOptionPane.showInputDialog(null, "Enter Floor (1-7):", "CHECK-IN (Floor)",
								JOptionPane.QUESTION_MESSAGE);
						floor = Integer.parseInt(floorIn);

						if (floor < 1 || floor > 7) {
							JOptionPane.showMessageDialog(null, "Invalid NUMBER! Please enter a number from 1-7.",
									"Error", JOptionPane.ERROR_MESSAGE);
						} else {
							valid = false;
						}
					}

					boolean valid2 = true;

					int room = 0;
					while (valid2) {
						String roomIn = JOptionPane.showInputDialog(null, "Enter Room (1-5):", "CHECK-IN (Room)",
								JOptionPane.QUESTION_MESSAGE);
						room = Integer.parseInt(roomIn);

						if (room >= 1 && room <= 5) {
							if (hotel[floor - 1][room - 1] == 0) {
								hotel[floor - 1][room - 1] = 1;
								JOptionPane.showMessageDialog(null,
										"Check-In successful!\nFloor " + floor + ", Room " + room + " is now occupied.",
										"GRAND P's Hotel", JOptionPane.INFORMATION_MESSAGE);
								valid2 = false;
								repeat = true;
							} else {
								JOptionPane.showMessageDialog(null,
										"Room already occupied!\nPlease select another one!", "Room Occupied",
										JOptionPane.WARNING_MESSAGE);
								valid2 = false;
							}
						} else {
							JOptionPane.showMessageDialog(null, "Invalid NUMBER! Please enter a number from 1-5.",
									"Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				} while (!repeat);

				break;

			case "3":
				boolean repeat2 = false;

				do {
					boolean valid = true;

					int floor = 0;
					while (valid) {
						String floorOut = JOptionPane.showInputDialog(null, "Enter Floor (1-7):", "CHECK-OUT (Floor)",
								JOptionPane.QUESTION_MESSAGE);
						floor = Integer.parseInt(floorOut);

						if (floor < 1 || floor > 7) {
							JOptionPane.showMessageDialog(null, "Invalid NUMBER! Please enter a number from 1-7.",
									"Error", JOptionPane.ERROR_MESSAGE);
						} else {
							valid = false;
						}
					}

					boolean valid2 = true;

					int room = 0;
					while (valid2) {
						String roomOut = JOptionPane.showInputDialog(null, "Enter Room (1-5):", "CHECK-OUT (Room)",
								JOptionPane.QUESTION_MESSAGE);
						room = Integer.parseInt(roomOut);

						if (room >= 1 && room <= 5) {
							if (hotel[floor - 1][room - 1] == 1) {
								hotel[floor - 1][room - 1] = 0;
								JOptionPane.showMessageDialog(null,
										"Check-Out successful!\nFloor " + floor + ", Room " + room + " is now empty.",
										"GRAND P's Hotel", JOptionPane.INFORMATION_MESSAGE);
								valid2 = false;
								repeat2 = true;
							} else {
								JOptionPane.showMessageDialog(null, "Room already empty!\nPlease select another one!",
										"Room Empty", JOptionPane.WARNING_MESSAGE);
								valid2 = false;
							}
						} else {
							JOptionPane.showMessageDialog(null, "Invalid NUMBER! Please enter a number from 1-5.",
									"Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				} while (!repeat2);

				break;

			case "4":
				JOptionPane.showMessageDialog(null, "Thank you for booking with us!\nENJOY YOUR STAY!",
						"GRAND P's Hotel", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
				break;

			default:
				JOptionPane.showMessageDialog(null, "Invalid INPUT!\nPlease enter a number from 1-4.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		} while (choice != "4");

	}

}
