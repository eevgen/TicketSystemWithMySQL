package org.example;

import java.sql.*;
import java.util.Scanner;
public class Main {

    private static final String URL = "jdbc:mysql://localhost:3306/transactions";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "12345";

    public static void main(String[] args){
        int row = enterSomeIntInformation("Enter please a number of the row");
        int sittingPlace = enterSomeIntInformation("Then enter please a number of the sitting place");

        String sql = "insert into tickets (`row`, `sitting`) values (?, ?);";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {


            statement.setInt(1, row);
            statement.setInt(2, sittingPlace);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet resultSet = statement.executeQuery("select * from tickets;");
                if (resultSet.next()) {
                    System.out.println(informationGetter(row, sittingPlace));
                } else {
                    System.err.println("There aren't any rows");
                }
            } else {
                System.err.println("Data insertion failed.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int enterSomeIntInformation(String phrase) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(phrase + ": ");
        if(scanner.hasNextInt()) {
            return scanner.nextInt();
        } else {
            System.err.println("Please enter a number!!!");
            return -1;
        }
    }

    public static String informationGetter(int row, int sittingNumber) {
        return String.format("You have ordered sitting place number %d in row %d",
                sittingNumber,
                row);
    }
}