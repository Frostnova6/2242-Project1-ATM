// Keypad.java
// Represents the keypad of the ATM
import java.util.Scanner; // program uses Scanner to obtain user input

public class Keypad
{
   private Scanner input; // reads data from the command line
                         
   // no-argument constructor initializes the Scanner
   public Keypad()
   {
      input = new Scanner( System.in );    
   } // end no-argument Keypad constructor

   // return an integer value entered by user 
   public int getInput()
   {
      String inputString;
      int inputInteger = 0;
      while(true){
            inputString = input.nextLine(); // read input values
            try{
                // cast string into integer
                inputInteger = Integer.parseInt(inputString);
                if (inputInteger < 0){
                    throw new IllegalArgumentException("Value must be positive"
                          +" integer.");
                }
            } catch (NumberFormatException e){ // if not input in integer format
                // display warning message and prompt for input again
                System.out.print(" Only integers are accepted. Please enter again: ");
                continue;
            } catch (IllegalArgumentException i){ // if not a positive integer
                // display warning message and prompt for input again
                System.out.print("Negative numbers are not accepted. Please enter again: ");
                continue;
            }
            return inputInteger; // return the integer input
        } // end while
    } // end method getInput
    
    public double getdouble() {
    String inputString;
    double inputDouble = 0.0;
    while (true) {
        inputString = input.nextLine(); // read input values
        try {
            // cast string into double
            inputDouble = Double.parseDouble(inputString);
            if (inputDouble < 0) {
                throw new IllegalArgumentException("Value must be positive.");
            }
        } catch (NumberFormatException e) { // if not input in double format
            // display warning message and prompt for input again
            System.out.println(inputString + " is not a valid double value. Please enter again.");
            continue;
        } catch (IllegalArgumentException i) { // if not a positive double
            // display warning message and prompt for input again
            System.out.println(inputDouble + " is not a positive double value. Please enter again.");
            continue;
        }
        return inputDouble; // return the double input
       } // end while
   } // end method getDouble
} // end class Keypad  



/**************************************************************************
 * (C) Copyright 1992-2007 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/