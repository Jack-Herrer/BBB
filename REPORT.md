# Report
# Budget Bitch

Michiel van der List (10363521)

###App
Budget Bitch is een app gemaakt voor mensen die te maken krijgen met buitenlandse valuta. Het probleem bij veel van deze valuta, is dat men veel problemen ervaart bij hun uitgaven, omdat men geen gevoel heeft bij de betreffende valuta. Budget Bitch helpt hierbij, doordat de gebruiken een budget vast kan stellen. Het banksaldo van de gebruiker wordt in de eigen valuta, evenals de vreemde valuta weergeven. Daarnaast krijgt de gebruiker een tussenstatus van zijn budget - wederom in beide valuta - te zien. Als klap op de vuurpijl, geeft deze app een visuele weergave van het nog te besteden bedrag. De gebruiker dient voordat hij gaat pinnen het gewenste bedrag in te voeren. Hierbij zal de gebruiker zien hoeveel dit bedrag is in de eigen valuta. Ook krijgt de gebruiker visueel voorgeschoteld hoeveel dit bedrag ten aanzien van het resterende banksaldo is, dit alles om een daadwerkelijk gevoel te krijgen bij een valuta die de gebruiker niet eigen is. Bovendien is de app in staat een geschiedenis te weergeven van de transacties die de gebruiker gedaan heeft.
	Een bijzonder aspect van deze app, is dat hij zowel offline als online te gebruiken is. Wanneer de gebruiker geen internetverbinding heeft, zal de gebruier hier in het gebruik niets van merken. Dit is relevant, daar de app hoofdzakelijk in het buitenland gebruikt zal worden. Zodra de gebruiker internetverbinding heeft, zal de data op internet geplaatst worden. Hierdoor kunnen meerder gebruikers hetzelfde toestel gebruiken en kan de app cross-platform gebruikt worden. Wanneer de gebruiker offline is geweest, kan de gebruiker kiezen om de locale data te behouden, of de data uit de cloud te gebruiken.

###Ontwerpproces
Het uiteindelijke ontwerp van de app berust op een onderdeel van het originele idee. Het originele idee was om een complete budgetmonitor te maken die in twee valuta werkte. Het probleem hierbij, is dat de gebruiker iedere kleine uitgave in dient te voeren voor een correct functionerende app. Hiedroo is besloten om dit idee te handhaven, maar de focus te leggen op pin transacties. Het idee blijft in stand, maar de gebruiker dient niet tientallen keren per week zijn data bij te houden.

Het eerste probleem waar dit project tegenaan liep, was de naamgeveving. Vooral backpackers reizen op budget, waardoor de orginele naam Backpacker's Budget Boss was. Door het woord 'backpacker' in de naam te voegen, ontstaat de kans dat andere potentiele gebruikers zich niet verwelkomd voelen. Daarna is er gekozen voor de naam Budget Boss. Echter, deze naam bleek reeds in gebruik. Aan het eind is de naam veranderd in Budget Boss Baby!. Deze naam is echter te lang om correct weergeven te worden wanneer de app geïnstalleerd was op een android toestel. De uiteindelijke naam is Budget Bitch geworden, niet in de laatste plaats omdat shockeren niet de minst efficiënte manier is om aandacht te verwerven. Zoals hier misschien al opvalt, stond bij aanvang vast, dat een alliteratie ten grondslag moest liggen aan de naamgeving van deze app.

In de eerste week van de ontwikkeling, toen langzaam maar zeker duidelijk werd wat het project uiteindelijk zou moeten gaan behelzen, is er gekozen om de weergave van de transactiegeschiedenis een prominente plek toe te kennen binnen de app. Dit ontziet de gebruiker namelijk van het bijhouden - het zij middels het oude 'huishoudboekje', dan wel in het daadwerkelijk herinneren - van de transacties. Voor de weergave is gekozen voor de vreemde valuta, hoewel de app ruimte laat voor het weergeven van de transactie in beide valuta. Dit is absoluut één van de verbeterpunten van de app vooralsnog, die zijn gebrek te wijten heeft aan het tekkort van tijd. Een deel van deze functie is geïmplementeerd, zodat dit later gemakkelijk opgepakt kan worden.

Voor het opslaan van datasets is gekozen om dit in Json bestanden te doen. Deze bestanden lenen zich uitstekend voor deze app, omdat ze als String opgeslagen kunnen worden. Hierdoor zijn ze gemakkelijk op te slaan in zowel het lokale geheugen van het toestel, als de online database in hetzelfe bestandtype. Tevens worden de valuta vanaf het internet opgehaald in een Json bestand. 
   
parse

offline online

float 

actionbar

knoppen zelfde plek

###Technisch design


###API's/SDK's

- De gehele app is ontwikkeld binnen het Android Studios framework
- Parse wordt gebruikt als online database voor de gebruikers

