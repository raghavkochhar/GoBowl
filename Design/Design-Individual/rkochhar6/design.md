Bowling Alley Design Document

Explanation of the Classes and their member variables and methods:

1. Manager

addCustomer() takes the name and email from customer and builds the unique ID and then calls printCard()

2. CardPrinter
printCard() takes all the info Manager passes it and prints the physical card with a QR code

3. VideoCam

scanCard() parses the QR code and encodes the unique ID into it with the encodeQRCode() function


4. Screen
This is the interface between Customer and Lane Requesting and Checkout processing.

getFreeLane() gets the first free lane number available for use from Lane Requester

displayLaneAssignment () shows the lane number to the Customer

checkoutLane() sends the Checkout Processor the request to start checking out a lane number

saveScore()  allows customers to save scores from their game played. This data is stored in their Customer class object


5. Lane Requester
Keeps track of the availability of all lanes with lanesStatus[] 

requestGroupSize() requests the group size for the customers next game

authorizeCards() gets the group to scan their cards to make sure only registered players can play

assignLane() assigns a lane to the group once all the cards have been successfully autorized

6. Checkout Processor

getTotalHoursPlayed() gets the customers hours played and in what time periods on which days 

calculateTotalBill() uses the previous function to calculate the total bill

splitCostCalc() divides the total cost by the split count requested by customer

6. Bowling Alley (there can be multiple objects)

Maintains meta data about the current status of the lane

returnTimePlayed() returns the hours played since checkin

clearData() clears out the meta data after a checkout is complete


External Utility Classes:

1. CardPrinter: Simply prints the card with the info provided by the customer and the system generated unique ID

2. VideoCam: Scans the physical card and connects with the customer class to check if that scan exists or encodes a new card

3. CreditCardReader: Scans the credit card and connects to the external payment processor to authorize the payment
