import Driver.App;

public class Main {
	public static void main(String[] args) throws Exception {
		App mysqlCon = new App();

		mysqlCon.readDataBase();


/* 		System.out.println("");
		mysqlCon.FillDataBaseFromFile();
		System.out.println(""); */

/* 		mysqlCon.readDataBase();
		mysqlCon.addUser();
		mysqlCon.readDataBase(); */
		//mysqlCon.updateUser();

		//mysqlCon.deteleUser();
	}
}
