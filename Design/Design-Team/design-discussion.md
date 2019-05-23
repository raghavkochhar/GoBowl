# Group Project 1: Team 25

## Design 1

![jmmcgarrah6 design](https://github.gatech.edu/gt-omscs-softeng/6300Summer16Team25/blob/master/Project2/Design-Individual/jmcgarrah6/design.png)

### Pros:

1. Players() encapsulated the customer grouping for a lane
2. A separated Scores() object handles that for the Customer()
3. Detailed credit card processing (maybe too much)

### Cons: 

1. Functionality for Checkout() is separated and could have been merged under a Checkout Class.
  1. Scores() and Billing() could be under a Checkout() class 
1. Player() is not as generic as it could be as a group of customers
1. Lane() could have had functionality for managing the lane actions
1. A separate object to handle the LaneCosts and the associated information about costs
1. Screen() object is completely missing from the implementation
2. Naming conventions could be improved

## Design 2

![clockett6 design](https://github.gatech.edu/gt-omscs-softeng/6300Summer16Team25/blob/master/Project2/Design-Individual/clockett6/design.png)

### Pros:

1. Very good naming conventions (to be adopted into team version)
2. Well designed LaneManager() classes manage the bowling lanes
3. CustomerGroup() manages players activities 
4. VIPStatus manages the VIP flag separate from Customer
5. Session processing concept can be expanded to include bill and score
6. Bill attributes should be methods

### Cons:

1. Session() may not be necessary depending on direction
  1. if Session removed then connect Bill() to Customer/CustomerGroup
3. Screen() is very minimal placeholder
4. Add a connection from Manager() to Printer() to print the card

## Design 3

![rkochhar6 design](https://github.gatech.edu/gt-omscs-softeng/6300Summer16Team25/blob/master/Project2/Design-Individual/rkochhar6/design.png)

### Pros:

1. Has a well developed Screen() missing in both other designs.
2. Well developed processes for Lane Request and Checkout
3. Input/Output paths are very clear between processes
4. Well developed Interfaces and Attributes
5. Methods are succinct

### Cons:

1. Screen() could have include two views for manager and customer
2. CardPrinter() to VideoCamera() connection unnecessary
3. Managing group of players less developed
4. Manager() should have a connector to the Screen

## Design 4

Unavailable at this time.

## Team Design

### Team 25

* Raghav Kochhar
* Cheryl Lockett
* J. Michael McGarrah
* Thomas Keller (absent)

Our team consists of Raghav, Cheryl and Michael. Thomas was added to our Github repository and the Slack, Piazza, and Gmail discussions. Thomas responded to the Gmail discussion that began before the first group deliverable was released. He also added himself to the Slack discussion group soon after but never posted. We have attempted to contact him via multiple avenues and GaTech email several times with no response. We hope he will rejoin the team for the next deliverable.

### UML Model

![team design](https://github.gatech.edu/gt-omscs-softeng/6300Summer16Team25/blob/master/Project2/Design-Team/design-team.png)

### Improvements

Discusses the main commonalities and differences between the team design and the individual ones, and concisely justify the main design decisions. To summarize, what did we do right by merging the three individual models?

The individual designs from each team member had different advantages that we incorporated into the team design. Raghav had a well defined process driven design that brought structure to the Checkout and LaneRequests processes along with significant thought about the interfaces and attributes of the classes. Cheryl brought a well thought through LaneManager, VIPStatus and Session processing for Checkout including a very logical naming convention that was used by the team's final version. Michael had a Scores, CreditCard and a different view on groups of customers as Players that influenced the final CustomerGroup design.

The individual designs diverged significantly even with the same specifications. As an example, the grouping of customers and handling those groups for lane assignment, score saving and billing each had very different approaches. The same occurred for managing Lanes with a very simple lane assignment versus a LaneManager with integrated cost assignment. As a team we rapidly converged on the best solutions often merging the ideas or just copying the best one.

Many of the differences between the designs are mentioned in the Pro/Con sections above. The Con sections often mention areas that the other designs excelled. We highlighted in the Pro sections where a specific design excelled.

The Final Team Design incorporated the best concepts from each of the three individual designs. These include:
* Lane & LaneManager that depends on CustomerGroup(the players)
* Session that manages saving Scores and Billing to CreditCard
* VIPStatus falls under the Customer but with separated year end handling
* Scores breaks out the score information from Customer and allows for expansion later

## Summary

The team by reviewing the divergent individual designs had their view of the problem expanded to include new areas and approaches to the overall design. As mentioned above, in reviewing the individual designs we found each had advantages that when merged into the final team design improve the final product.

Working with a team brings more ideas and experiences to the problem. The requirements often are viewed with our existing world views or prior experience in a problem domain limiting our ability to expand to new solutions. Having a team to *bounce* ideas off, allows us to try new ways of solving the problem.

Each of member of the team could see areas that their individual design could be improved after looking at the other members solutions to the problem. Sometime this improvement came by merging the concept and sometimes the improvement was just voted as the best solution wholesale. We had an equal distribution of merge and copy.

We have each worked professionally and knew going in that working on a product with a team often produces a better outcome. We were not surprised but this exercise clearly illustrates that many eyes on a problem makes for a better solution.
