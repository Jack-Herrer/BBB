# day 1

Create and finish app idea

# day 2

Proposal and desigh sketches

# day 3

Basic interface development

## Design choices

Start with ATM function for simple prototype

Target more users by changing project into Budget Boss instead of Backpacker's Budget Boss, so other people like students or people in debt are not excluded

Budget Boss name is already taken

Idea for first version: app will focus on bank transactions and visiualise how much of the budget is left while automatically converting currencies, using exchange rate api

# day 4

Advanced interface development

functionalities for prototype included

## Design choices

App idea changed. App will be a helper to give all relevant information for atm transactions in foreighn countries instead of a budget monitor. Bacause of this, the app will be a lot more simplistic and more relevant than the original idea.

Listener implemented to edittext. User can see changes as the user is typing, no need to press an extra button to see effect

for sake of prototype main screen will visualise current balance compared to starting balance, atm function visualises proposed withdrawal ammount compared to current balance, not tot start balance

Withdrawal history will be added

## Design problems

### double datatype inaccuracy
### collect data from users?
### forced updates from user?
### transaction costs?

# Day 5

Change README.md to match new target of project

Design document

Bug fixes

## Design Choices

New class will be made to contain all common methods. Methods will be static wherefore no new instances must be created every time.

View (left) button will get transaction history screen with listview

## Design problems

### using accounts yes or no? This is a big crack in user comfort, but a huge leap in the value of data collection.
### how will android's retrun button behave?

# Day 6

Start with history view
History view will be list view

##Design choices

Optional login will be added later for cross platform use. App will still be usable without login thoug.

# Day 7 

Standard "to do list" history view does not work for this platform

##Design choices

Parse api database for history for cross platform will be used

User accounts required for the latter

currency updates will be implemented first

# Day 8

parse user accounts implemented

##Design choice

useraccounts will be impelmented first in order not to re-implement too much

# Day 9 

### Laptop breakdown, laptop does not receive any power

# day 10

### getting new laptop, installing os and re-installing software

#day 11

All rates can be received as json object and be extracted

# day 12

user accounts can save information including full json with exchange rates

# day 13

all rates in shared prefs automatically changed through setExchangeRate method

# day 14

rounding of doubles
simple history view impelemted


# day 15

ill

# day 16

missing action bars added
tansaction costs taken into account
history item [0] not shown


# day 17

fetching all relevant data from parse.com

# day 18

problems: background problem, eur to eur = 0.99999,






