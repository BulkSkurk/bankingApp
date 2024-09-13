
public class Main {
    public static void main(String[] args){

        UserInterface userInterface = new UserInterface();
        userInterface.startUserInterface(args);
        //Great progress today 03/09. Keep at it like this.
        //Inhuman progress at 08/09. Keep at it like this. Almost done.
    }
}
//TODO - Kom ihåg kriterierna!

//Kvar att göra:
//TODO - Koppla på Save-knappen (Save fungerar, bara inte knappen)
//TODO - Remove Transaction funktionen
//TODO - Filter Funktion (Inkomst vs Utgifter, lägg till filter på existerande filterfunktioner)
//TODO - Gör Unit-tests
//TODO - Snygga till UI med lite färg
//DONE - Sedan klar. 🥂

//Krav för G:
//
//- Följ instruktionerna i beskrivningen
//- Formatera koden: små misstag ignoreras men större fel ger IG (om du blir osäker så kan du fråga läraren)
//
//Krav för VG (gör minst en av följande punkter):
//
//- Spara och ladda all data till och från fil
//
//- Bygg applikationen med ett user-interface (UI)
//
//- Skriv fem unit-tester
//Beskrivning
//Bygg en personal-finance applikation som fungerar i terminalen (eller med UI för VG). I applikationen skall man kunna göra följande:
//
//- Lägga in transaktioner (manuellt; när du exempelvis har köpt något eller fått lön)
//- Radera transaktioner (manuellt)
//- Se nuvarande kontobalans
//- Se pengar spenderade årsvis, månadsvis, veckovis och dagvis
//- Se inkomst årsvis, månadsvis, veckovis och dagvis
//Om du inte går för VG så behöver ingen data sparas. Det betyder att du måste börja om med att lägga in transaktioner och annat varje gång applikationen startas om.
