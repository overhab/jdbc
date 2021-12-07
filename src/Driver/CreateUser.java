package Driver;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreateUser {
	public static List<Object> generateUser() {
		boolean flag = true;
		int age = 0;
		Scanner scanner = new Scanner(System.in);
	
		System.out.println("Enter your name and surname: ");
		String name = scanner.nextLine();
		System.out.println("Enter your nickname: ");
		String nickname = scanner.nextLine();
		while (flag)
		{
			System.out.println("Enter your age: ");
			if (scanner.hasNextInt())
			{
				age = scanner.nextInt();
				flag = false;
			}
			else
			{
				System.out.println("***Error: wrong age format!***");
				scanner.nextLine();
			}
		}
		System.out.println("Enter your email: ");
		String email = scanner.next();
		scanner.close();
		
		List<Object> newUser = List.of(name, nickname, age, email);

		System.out.println("User generated.");
		return newUser;
	}
}
