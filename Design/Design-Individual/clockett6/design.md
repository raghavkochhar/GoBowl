#Manager

##Add Customer

The manager class addCustomer method takes the name and email address and creates a new customer object.    The constructor for the customer object checks whether the customer exists or not.  If the customer is new, it generates the unique customerID and creates a new session object with a bill.  After creating the new customer object or retrieving the existing one, it checks the VIP status for updates and adds the customer to the specified customerGroup.  It checks whether the customer hasVIPStatus and sets the customerGroup applyVIPDiscount() method if hasVIPStatus = TRUE.  It then prints a CustomerCard for that customer using the customerID returned from addCustomer.  

I introduced a CustomerGroup and a group ID so collections of customers can be identified and kept together until checkout.  Assuming customers involved in check in (add customer), lane request, and check out could be different people in the group so identifying the group is useful.

##Edit Customer

The manager class updateCustomer method doesn’t have explicit requirements, but does have implied requirements.  Customers can be retrieved and information modified such as their names and email addresses.

##Print Customer Card

The manager class has a method printCustomerCard that invokes the same named method on the CardPrinterLibrary utility class which is why both classes have the same method.  The system GUI would in theory invoke the class on the manager object when the manager is logged in and selects this option.

I opted to not model a CustomerCard class because the printer utility is assumed to be a wrapper that I use that passes in the customerID and generates a qrCode.  It has the customerID and qrCode attributes that the customer card object would have had when it takes the customerID and uses that to print the card with the qrCode on it so the additional class is unnecessary.  

In the CardPrinter utility, I opted to include attributes for the customerID and qrCode so it would be obvious that the CardPrinter would be handling converting the customerID to the qrCode.

#Customer

##Lane Request

When the customer invokes the lane request, the customer scans his/her card and if valid, the system asks for the number of players and requests to scan each of their cards. Next, the customer class sends a request to the lane manager to assign an available lane.  The lane manager checks each lane for availability and sends the first available assignment to the customer based on the schedule of lane costs and lane availability.  The customer object copies the lane assignment to the customer group object so if additional members are added to the group and they request lanes, those requests can be denied because the group already has an assigned lane easily.  The assigned lane is displayed on the screen.

##Checkout

When the customer invokes checkout, the system prompts for information on saving scores for each player.  If desired, the player types in the scores and each score along with the date is saved with each customer for that bowling session.  

The system prompts for the number of people paying the bill per customer/player then provides an opportunity to enter a number from 1 to the number of customers in the customer group.  Though the bill is associated with a single customer (the one who requested the lane), the bill is divided and each paying customer can swipe their credit card for payment.  The lane requester gets credit for the bill and every payer contributes to paying for the bill.  

