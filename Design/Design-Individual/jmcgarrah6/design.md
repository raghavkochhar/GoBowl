# Design Analysis

## Class Manager
>This class is the starting point for all activity in the system. The member functions for creating and editing customers are here.
>Triggers for the Card Printer class come from one or both of the customer change functions. The choice of making the printCustomerCard() a private function was to make it only accessible to the add or edit functions but break it out. The updateVIP() function is in support of the yearly check for the running_bill in the Customer Class for setting or unsetting the VIP flag each year.

> **Note**: There is ambiguity in what triggers the print of a card.

## Class Customer
>This class contains all the customer specific data such as the customer identifier, name, and email.
>The addition of a VIP status and a running total of payments were added to support that functionality.
>The functions for requesting a lane and checking out start here.

> **Note**: The Customer ID is dramatically too small for a production system but is per specificiation. Would recommend a re-evaluation by customer of their hex id.

## Class Players
>The Player class brings together the single individual or group of players and retains the lane, the time of purchase and calls the Lane registration class. Only one lane is allowed for a group of Players. Adding the Customer to the Player list requires scanning the Card and is reflected in the call to the Video Camera Class.

## Class Scores
>This class holds score data for each customer as requested by a customer during checkout. An extra function to retrieve the scores is included from convenience.

## Class Lane
>This class simplistically tracks if a lane is available for use and allow for getting or setting the status of the lane.

## Class Lane Cost
>This class consolidates the information from Players and Customer classes to produce the appropriate costs based on the time/date,  requesting customer and the VIP status. It then returns that value to the Bill class from further processing.

## Class Bill
>This class calls the supporting Lane Cost and divides up the costs per the count provided during checkout process. It is the second part of the Checkout processes. It calls the chain of Credit Card processing classes looping over the number of payers against the divided lane cost. 

## utility Class Card Printer
>The Card Printer is called on the adding of a new customer. In the current design, it is also be called when a customer is updated to have the most current card available to a customer.

## utility Class Video Camera
>Simple class that returns the Customer ID from the QR code scanned. Called by the Player class to get the customer ID for the lane requestor and other players.

## utility Class Credit Card Scanner
>This class scans for the four major parts of the credit card transaction then calls the CC Processor Class to execute the charge.

## utility Class Credit Card Processor
>A blank class that charges a credit card. I left this as the end point to the credit card process. In a real system, there is more to do here.

