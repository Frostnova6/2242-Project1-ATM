// Transfer.java
// Represents a transfer ATM transaction

public class Transfer extends Transaction
{
   private double amount; // amount to transfer
   private Keypad keypad; // reference to keypad
   private final static int CANCELED = 0; // constant for cancel option
   private int recipientAcoountNumber;
   private boolean isValidUser = false;
   private boolean isSufficientCashAvailable = false;
   // Transfer constructor
   public Transfer( int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase, Keypad atmKeypad )
   {
      // initialize superclass variables
      super( userAccountNumber, atmScreen, atmBankDatabase);

      // initialize references to keypad
      keypad = atmKeypad;
   } // end Transfer constructor

   // perform transaction

   public void execute()
   {
      BankDatabase bankDatabase = getBankDatabase(); // get reference
      Screen screen = getScreen(); // get reference
      while(!(isValidUser)){
          // get recipient account number
          recipientAcoountNumber = promptForRecipientAccountNumber(); 
          if (recipientAcoountNumber == super.getAccountNumber()){
              screen.displayMessageLine( "You cannot transfer money to your" 
              + " own account. Please enter again.");
              continue;
          }
          isValidUser = bankDatabase.validateUser(recipientAcoountNumber);
          if (!(isValidUser))
              screen.displayMessageLine( "Account " +  recipientAcoountNumber 
                      + " does not exist. Please enter again." );
      }
      
      amount = promptForTransferAmount(); // get transfer amount from user

      // check whether user entered a transfer amount or canceled
      if ( amount != CANCELED )
      {
            // debit current account to reflect the transfer
            bankDatabase.debit( getAccountNumber(), amount ); 
            // credit recipient account to reflect the transfer
            bankDatabase.credit( recipientAcoountNumber , amount ); 
            screen.displayMessageLine( "Transaction completed." );
            screen.displayMessageLine( "Available balance: " 
                    + bankDatabase.getTotalBalance(getAccountNumber()) );
            screen.displayMessageLine( "\nBack to main menu..." );
         } // end if
         else // transfer envelope not received
         {
            screen.displayMessageLine( "\nCanceling transaction..." );
      } // end else
   } // end method execute

   // prompt user to enter recipient account number
   private int promptForRecipientAccountNumber()
   {
        Screen screen = getScreen(); // get reference to screen
        boolean isConfirmed = false;
        int selection = 0;
        int input = 0;
        while (!(isConfirmed)){
            screen.displayMessage( "\nPlease enter the recipient's account number: " );
            input = keypad.getInput(); // receive input of transfer amount
            screen.displayMessageLine( "\nThe recipient's account number is: " 
                    + input);
            // ask the user double check the recipient account number
            screen.displayMessageLine( "\nAre you sure " + input + " is the" 
                   + " correct recipient account number?" );
            screen.displayMessageLine(" 1 - Yes");
            screen.displayMessageLine(" 2 - No");
            screen.displayMessage( "Enter a choice: " );
            selection = keypad.getInput(); // return user's selection
            switch(selection){
                case 1:
                    isConfirmed = true;
                    break;
                case 2:
                    screen.displayMessageLine( "\nCancel input. " 
                            + "Please re-enter the account number.");
                    continue;
                default:
                    screen.displayMessageLine( "\nYou did not enter a valid"
                        + "selection. Please try again." );
            } // end switch
        } // end while
        return input; // return recipient account number
   } // end method promptForRecipientAcoountNumber
           
   // prompt user to enter a transfer amount in cents 
   private double promptForTransferAmount()
   {
      BankDatabase bankDatabase = getBankDatabase(); // get reference
      Screen screen = getScreen(); // get reference to screen
      double input = 0;
      
      while(!(isSufficientCashAvailable)){
          
          // display the prompt
          screen.displayMessage( "\nPlease enter the transfer amount " + 
             "(or 0 to cancel): " );
          input = keypad.getdouble(); // receive input of transfer amount
          
          // check whether the user canceled or entered a valid amount
          if ( (int) input == CANCELED ) 
             return CANCELED; // exit input
          else
          {
             isSufficientCashAvailable = 
                 bankDatabase.isSufficientCashAvailable(getAccountNumber()
                      , input );
             if (isSufficientCashAvailable)
                 break; // exit input when there is sufficient cash
             else
                 screen.displayMessageLine( "\nInsufficient cash!");
          } // end else
      } // end while
      return input; // return dollar amount 
   } // end method promptForTransferAmount
} // end class Transfer



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