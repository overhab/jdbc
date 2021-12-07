package Driver;

import java.net.ConnectException;
import java.sql.*;
import java.util.EnumMap;
import java.util.List;
import java.util.Scanner;

public class App {
	private Connection con = null;
	private Statement my_Statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public void addUser() throws Exception {
		List<Object> newUser = CreateUser.generateUser();

		try {
			//Class.forName("com.mysql.cj.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "", "");
			// getConnection(url - mysql database url, user, password)

			preparedStatement = con.prepareStatement("insert into users " + "(name, nickname, age, email) "
			+ "values (?, ?, ?, ?)");

			preparedStatement.setString(1, (String) newUser.get(0));
			preparedStatement.setString(2, (String) newUser.get(1));
			preparedStatement.setInt(3, (int) newUser.get(2));
			preparedStatement.setString(4, (String) newUser.get(3));

			preparedStatement.execute();
/* 			String sql = "insert into users " + "(name, nickname, age, email) "
						+ "values (\'" + newUser.get(0) + "\', \'" + newUser.get(1) + "\', \'" + newUser.get(2) + "\', \'" + newUser.get(3) + "\')";
			my_Statement.execute(sql); */

			System.out.println("Insert complete.");
		}
		catch (Exception exc) {
			exc.printStackTrace();
			close_connection();
		}
		close_connection();
	}

	public void updateUser() throws Exception {
		Scanner scanner = new Scanner(System.in);
		int id = 0;
		boolean flag = true;
		while (flag)
		{
			System.out.println("Enter id of a user to change its email: ");
			if (scanner.hasNextInt())
			{
				id = scanner.nextInt();
				flag = false;
			}
			else
			{
				System.out.println("***Error: wrong id format!***");
				scanner.nextLine();
			}
		}
		scanner.close();

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "", "");
			// getConnection(url - mysql database url, user, password)

			my_Statement = con.createStatement();

			String sql = "update users set email='acxbot@gmail.com' where id=" + id;

			//System.out.println(sql);

			my_Statement.execute(sql);

			System.out.println("Update complete.");
		}
		catch (Exception exc) {
			exc.printStackTrace();
			close_connection();
		}
		close_connection();
	}

    public void readDataBase() throws Exception {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "", "");
			// getConnection(url - mysql database url, user, password)

			my_Statement = con.createStatement();

			resultSet = my_Statement.executeQuery("select * from users");

			while (resultSet.next())
				System.out.println(resultSet.getString("id") + ": " 
						+ resultSet.getString("name") + ", " + resultSet.getString("nickname") + ", " 
						+ resultSet.getString("age") + ", " + resultSet.getString("email"));

		}
		catch (Exception exc) {
			exc.printStackTrace();
			close_connection();
		}
		close_connection();
    }

	public void deteleUser() throws Exception {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "", "");
			// getConnection(url - mysql database url, user, password)

			my_Statement = con.createStatement();

			String sql = "delete from users where name='alex'";

			//System.out.println(sql);

			my_Statement.execute(sql);

			System.out.println("Deleted.");
		}
		catch (Exception exc) {
			exc.printStackTrace();
			close_connection();
		}
		close_connection();
	}

	private void close_connection() {
		try {
			if (resultSet != null)
				resultSet.close();
			if (my_Statement != null)
				my_Statement.close();
			if (con != null)
				con.close();
		}
		catch (Exception exc) {
		}
	}
}
