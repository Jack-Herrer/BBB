#Design

###MVP

visualisation to help the user get feeling of their transaction
making calculations before user even thinks of it
all currencies available
auto updating currencies
converter
transaction history

###optional

language support
gps support
data collection of transactions for analysis
userexperience data collection for app improval
forced updates
advertisements

###Classes:

Main status view activity
- gives you relevant information of balance and budget
- inculdes converter

ATM function activity
- shows relevant information for transaction
- updates balance


Update activity
- make changes which you will make ocassionally 
- allows you to change balance manually
- allows you to set budget
- allows you to change home currency and forgeighn currency

Settings activity
- allows you to change settings which hardly ever change
- language 
- auto or manual update currencies

Help activity
- contains manual

Methods class
- contains methods to convert doubles to save in shared prefs
- will contain other methods common to all classes

Currency updater classes
- contains all methods to update currency

menu handler classes
- contains 

currency updater class
- contains all methods to update currency

menu listner class
- handles actionbar clicking

###Design problems

'double' datatype inaccuracy


###Interface

![Alt text](/doc/atm1.png) ![Alt text](/doc/atm2.png)
![Alt text](/doc/main.png) ![Alt text](/doc/update.png)

###API's and extra's

currencylayer API or yahoo finance API
API for collecting user experience data
sqlite database for currencies and history





