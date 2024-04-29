Zadanie:

Firma posiada bazę danych opartą o pliki XML. Każdy z plików XML zawiera osobne dane pracownika.
Każdy pracownik posiada: Identyfikator, Imię, Nazwisko, numer telefonu, email, PESEL
Dane pracowników znajdują się w dwóch katalogach: External (pracownicy zewnętrzni) i Internal; (pracownicy wewnętrzni) i dzielą ich na dwa typy pracowników.

Należy stworzyć kod potrafiący obsługiwać opisaną bazę danych oraz przykładowe Unit testy

Możliwe operacje do wykonania to:

Znajdź pracownika po dowolnym z atrybutów: identyfikatorze, typie, nazwisku, imieniu, numerze telefonu, numerze PESEL (lub dowolnej ich kombinacji)
Dodaj nowego pracownika
Usuń pracownika
Zmień dane pracownika
 

Przykładowe funkcje:

Person find (Type type, String firstName, String lastName, String mobile)
void create (Person person)
boolean remove (String personId)
void modify (Person person)
 

Typ danych:

Person {
String personId
String firstName
String lastName
String mobile
String email
String pesel
}
 
