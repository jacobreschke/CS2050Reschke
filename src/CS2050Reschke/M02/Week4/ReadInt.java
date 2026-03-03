package CS2050Reschke.M02.Week4;

import java.util.Scanner;

public class ReadInt {

    public static int readIntInRange(String promptMessage, Scanner scanner, int minimumValue, int maximumValue){
        int validatedNumber = 0;
        boolean inputIsValid = false;
        while (!inputIsValid){
            System.out.print(promptMessage);
            if (scanner.hasNextInt()){
                int userValue = scanner.nextInt();
                scanner.nextLine(); // consume leftover newline
                if (userValue >= minimumValue && userValue <= maximumValue){
                    validatedNumber = userValue;
                    inputIsValid = true;
                } else{
                    System.out.println("Error: ");
                }
            } else{
                System.out.println("Error: Not an integer.");
                scanner.nextLine(); // consume invalid input
            }
        }
        return validatedNumber;
    }


}

