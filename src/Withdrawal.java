// Withdrawal.java
// Represents a withdrawal ATM transaction

public class Withdrawal extends Transaction
{
   private int amount; // amount to withdraw
   private Keypad keypad; // reference to keypad
   private CashDispenser cashDispenser; // reference to cash dispenser
   private int withdrawRMB;

   // constant corresponding to menu option to cancel
   private final static int CANCELED = 7;

   // Withdrawal constructor
   public Withdrawal( int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase, Keypad atmKeypad, 
      CashDispenser atmCashDispenser )
   {
      // initialize superclass variables
      super( userAccountNumber, atmScreen, atmBankDatabase);
      
      // initialize references to keypad and cash dispenser
      keypad = atmKeypad;
      cashDispenser = atmCashDispenser;
   } // end Withdrawal constructor

   // perform transaction
   public void execute()
   {
      boolean cashDispensed = false; // cash was not dispensed yet
      boolean availableBalance = false; // amount available for withdrawal

      // get references to bank database and screen
      BankDatabase bankDatabase = getBankDatabase(); 
      Screen screen = getScreen();

      // loop until cash is dispensed or the user cancels
      do
      {
         withdrawRMB = getCashType();
         if ( withdrawRMB != CANCELED ){
            screen.displayMessageLine( "\nCanceling transaction..." );
            return; // return to main menu because user canceled
         }
         
         // obtain a chosen withdrawal amount from the user 
         amount = displayMenuOfAmounts();
         
         // check whether user chose a withdrawal amount or canceled
         if ( amount != CANCELED )
         {
            // check whether the user has enough money in the account
             if (withdrawRMB == 2){
                availableBalance = bankDatabase.isSufficientCashAvailable(getAccountNumber(), amount*1.15);
             } else {
                availableBalance = bankDatabase.isSufficientCashAvailable(getAccountNumber(), amount);
             }
      
            if ( availableBalance )
            {   
               // check whether the cash dispenser has enough money
               if ( cashDispenser.isSufficientCashAvailable( amount ) )
               {
                  // update the account involved to reflect withdrawal
                  bankDatabase.debit( getAccountNumber(), amount );
                  
                  cashDispenser.dispenseCash( amount ); // dispense cash
                  cashDispensed = true; // cash was dispensed

                  // instruct user to take cash
                  screen.displayMessageLine( 
                     "\nPlease take your cash now." );
                  screen.displayMessageLine( "Withdrawal completed." );
                  screen.displayMessageLine( "Available balance: " 
                          + bankDatabase.getTotalBalance(getAccountNumber()) );
                  screen.displayMessageLine( "\nBack to main menu..." );
               } // end if
               else // cash dispenser does not have enough cash
                  screen.displayMessageLine( 
                     "\nInsufficient cash available in the ATM." +
                     "\n\nPlease choose a smaller amount." );
            } // end if
            else // not enough money available in user's account
            {
               screen.displayMessageLine( 
                  "\nInsufficient funds in your account." +
                  "\n\nPlease choose a smaller amount." );
            } // end else
         } // end if
         else // user chose cancel menu option 
         {
            screen.displayMessageLine( "\nCanceling transaction..." );
            return; // return to main menu because user canceled
         } // end else
      } while ( !cashDispensed );

   } // end method execute
   
   private int getCashType() {
      Screen screen = getScreen();
      int userChoice = 0; // local variable to store return value
      // loop while no valid choice has been made
      while ( userChoice == 0 )
      {
         // display the menu
         screen.displayMessageLine( "\nWithdrawal Menu:" );
         screen.displayMessageLine( "1 - HKD" );
         screen.displayMessageLine( "2 - RMB (1 RMB = 1.15 HKD)" );
         screen.displayMessageLine( "3 - Cancel transaction" );
         screen.displayMessage( "\nChoose the withdrawal cash type: " );


         int input = keypad.getInput(); // get user input through keypad

         // determine how to proceed based on the input value
         switch ( input )
         {
            case 1: // if the user chose withdrawal cash type
            case 2: // (i.e., chose option 1, 2, 3, 4 or 5)
               return userChoice;
            case 3: // the user chose to cancel
               userChoice = CANCELED; // save user's choice
               return userChoice;
            default: // the user did not enter a value from 1-6
               screen.displayMessageLine( 
                  "\nIvalid selection. Try again." );
         } // end switch
      } // end while

      return userChoice; // return withdrawal cash type or CANCELED
   }
   
   private int getCustomAmount() {
      Screen screen = getScreen();
      int customAmount;

      do {
         // prompt for withdrawal amount
         screen.displayMessage("\nEnter the custom withdrawal amount (multiple of 100): ");
         customAmount = keypad.getInput();

         // check whether the user canceled or entered a valid amount
         if (customAmount % 100 != 0) {
            screen.displayMessageLine("Amount must be a multiple of 100. Please enter again.");
         } else {
            // check whether the user has enough money in the account
            // get available balance from the bank database
            double availableBalance = getBankDatabase().getAvailableBalance(getAccountNumber());
            if (customAmount > availableBalance) {
               screen.displayMessageLine("Insufficient funds in your account. Please enter a smaller amount.");
            } else {
               return customAmount;
            }
         }
      } while (true);
   }

   // display a menu of withdrawal amounts and the option to cancel;
   // return the chosen amount or 0 if the user chooses to cancel
   private int displayMenuOfAmounts()
   {
      int userChoice = 0; // local variable to store return value

      Screen screen = getScreen(); // get screen reference
      
      // array of amounts to correspond to menu numbers
      int amounts[] = { 0, 100, 500, 1000, 2000, 5000 };

      // loop while no valid choice has been made
      while ( userChoice == 0 )
      {
         // display the menu
         screen.displayMessageLine( "\nWithdrawal Amount:" );
         screen.displayMessageLine( "1 - $100" );
         screen.displayMessageLine( "2 - $500" );
         screen.displayMessageLine( "3 - $1000" );
         screen.displayMessageLine( "4 - $2000" );
         screen.displayMessageLine( "5 - $5000" );
         screen.displayMessageLine( "6 - Other amount");
         screen.displayMessageLine( "7 - Cancel transaction" );
         screen.displayMessage( "\nChoose a withdrawal amount: " );


         int input = keypad.getInput(); // get user input through keypad

         // determine how to proceed based on the input value
         switch ( input )
         {
            case 1: // if the user chose a withdrawal amount 
            case 2: // (i.e., chose option 1, 2, 3, 4 or 5), return the
            case 3: // corresponding amount from amounts array
            case 4:
            case 5:
               userChoice = amounts[ input ]; // save user's choice
               break;
            case 6 : // if the user chose other amount
               userChoice = getCustomAmount();
               break;
            case CANCELED: // the user chose to cancel
               userChoice = CANCELED; // save user's choice
               break;

            default: // the user did not enter a value from 1-6
               screen.displayMessageLine( 
                  "\nIvalid selection. Try again." );
         } // end switch
      } // end while

      return userChoice; // return withdrawal amount or CANCELED
   } // end method displayMenuOfAmounts
} // end class Withdrawal



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