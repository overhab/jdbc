package Driver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class App {
	private Connection con = null;
	private Statement my_Statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private String	jdbcUrl = System.getenv("JDBC_URL");
	//private String	mysqlUser = System.getenv("MYSQL_U");
	//private String	mysqlPass = System.getenv("MYSQL_P");

	public App(String user, String pass) throws Exception {
		try {
			con = DriverManager.getConnection(jdbcUrl, user, pass);
		} catch (Exception exc) {
			System.out.println(exc);
			throw new Exception("Connection failed");
		}
		System.out.println("Connected.");
	}
	
	private boolean checkForDuplicate(String email) {

		try {

			my_Statement = con.createStatement();

			resultSet = my_Statement.executeQuery("select * from users");

			while (resultSet.next()) {
				if (resultSet.getString("email").compareTo(email) == 0) {
					close_connection(0);
					return true;
				}
			}
			close_connection(0);
			return false;

		} catch (Exception exc) {
			exc.printStackTrace();
			close_connection(0);
			return false;
		}

	}

	public void addUser() throws Exception {
		List<Object> newUser = CreateUser.generateUser();

		try {

			preparedStatement = con.prepareStatement("insert into users " + "(name, nickname, age, email) "
			+ "values (?, ?, ?, ?)");

			preparedStatement.setString(1, (String) newUser.get(0));
			preparedStatement.setString(2, (String) newUser.get(1));
			preparedStatement.setInt(3, (int) newUser.get(2));
			preparedStatement.setString(4, (String) newUser.get(3));

			if (checkForDuplicate((String) newUser.get(3))) {
				System.out.println("Email \'" + (String) newUser.get(3) + "\' already in a database");
				close_connection(0);
				return ;
			}

			preparedStatement.execute();

			System.out.println("Insert complete.");
		}
		catch (Exception exc) {
			exc.printStackTrace();
			close_connection(0);
		}
		close_connection(0);
	}

	public void addUser(List<Object> newUser) throws Exception {
		try {

			preparedStatement = con.prepareStatement("insert into users " + "(name, nickname, age, email) "
			+ "values (?, ?, ?, ?)");

			preparedStatement.setString(1, (String) newUser.get(0));
			preparedStatement.setString(2, (String) newUser.get(1));
			preparedStatement.setInt(3, (int) newUser.get(2));
			preparedStatement.setString(4, (String) newUser.get(3));

			if (checkForDuplicate((String) newUser.get(3))) {
				System.out.println("Email \'" + (String) newUser.get(3) + "\' already in a database");
				close_connection(0);
				return ;
			}

			preparedStatement.execute();

			System.out.println("Insert complete.");
		}
		catch (Exception exc) {
			exc.printStackTrace();
			close_connection(0);
		}
		close_connection(0);
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
			
			my_Statement = con.createStatement();

			String sql = "update users set email='acxbot@gmail.com' where id=" + id;

			my_Statement.execute(sql);

			System.out.println("Update complete.");
		}
		catch (Exception exc) {
			exc.printStackTrace();
			close_connection(0);
		}
		close_connection(0);
	}

    public void readDataBase() throws Exception {
		try {
			
			my_Statement = con.createStatement();

			resultSet = my_Statement.executeQuery("select * from users");

			while (resultSet.next())
				System.out.println(resultSet.getString("id") + ": " 
						+ resultSet.getString("name") + ", " + resultSet.getString("nickname") + ", " 
						+ resultSet.getString("age") + ", " + resultSet.getString("email"));

		}
		catch (Exception exc) {
			exc.printStackTrace();
			close_connection(0);
		}
		close_connection(0);
    }

	public void deteleUserByEmail(String email) throws Exception {
		try {

			my_Statement = con.createStatement();

			String sql = "delete from users where email = \'" + email + "\'";

			my_Statement.execute(sql);

			System.out.println("Deleted.");
		}
		catch (Exception exc) {
			exc.printStackTrace();
			close_connection(0);
		}
		close_connection(0);
	}

	public void close_connection(int flag) {
		try {
			if (resultSet != null)
				resultSet.close();
			if (my_Statement != null)
				my_Statement.close();
			if (flag == 1 && con != null)
				con.close();
		}
		catch (Exception exc) {
		}
	}

	public void FillDataBaseFromFile() throws Exception {
		Reader in = null;
		try {
			in = new FileReader("src/Driver/resources/database.txt");
		} catch (Exception exc) {
			System.out.println("File not found");
			return ;
		}
		BufferedReader buff = new BufferedReader(in);
		String newUser;
		String[] user;

		while ((newUser = buff.readLine()) != null) {
			user = newUser.split(",");
			List<Object> userList = List.of(user[0], user[1], Integer.parseInt(user[2]), user[3]);
			addUser(userList);
		}
		buff.close();
	}
}
