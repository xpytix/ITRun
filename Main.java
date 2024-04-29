import java.io.File;
import java.util.List;
import java.util.UUID;

public class Main {

  public static void printEmployeesDetails(List<Person> employees) {
    for (Person employee : employees) {
      System.out.println("PersonalId: " + employee.getPersonId());
      System.out.println("Imię: " + employee.getFirstName());
      System.out.println("Nazwisko: " + employee.getLastName());
      System.out.println("Telefon: " + employee.getMobile());
      System.out.println("Email: " + employee.getEmail());
      System.out.println("PESEL: " + employee.getPesel());
      System.out.println();
    }
  }
  public static void main(String[] args) {

    // Ścieżka do katalogu employees - zdefiniowana statycznie
    String employeesDirectoryPath = "employees";
    String externalDirectoryPath = employeesDirectoryPath + File.separator + "external";
    String internalDirectoryPath = employeesDirectoryPath + File.separator + "internal";

    // Inicjalizacja parserów XML
    XMLParser xmlParser = new XMLParser();
    ExternalEmployeeXMLParser externalParser = new ExternalEmployeeXMLParser(xmlParser);
    InternalEmployeeXMLParser internalParser = new InternalEmployeeXMLParser(xmlParser);

    // Parsowanie plików XML z katalogów external i internal
    List<Person> externalEmployees = externalParser.parse(externalDirectoryPath);
    List<Person> internalEmployees = internalParser.parse(internalDirectoryPath);

    DatabaseManager dbManager = new DatabaseManager(externalEmployees, internalEmployees, externalDirectoryPath, internalDirectoryPath);

    System.out.println("External Employees");
    printEmployeesDetails(dbManager.getExternalEmployees());
    System.out.println("Internal Employees");
    printEmployeesDetails(dbManager.getInternalEmployees());

    // Przykładowe dodanie nowego pracownika zewnętrznego
    Person newExternalEmployee = new Person(UUID.randomUUID().toString(), "Jan", "Kowalski", "123456789", "jan.kowalski@example.com", "12345678901", EmployeeType.EXTERNAL);
    dbManager.create(EmployeeType.EXTERNAL, newExternalEmployee);

    // Przykładowe dodanie nowego pracownika wewnętrznego
    Person newInternalEmployee = new Person(UUID.randomUUID().toString(), "Anna", "Nowak", "987654321", "anna.nowak@example.com", "98765432101", EmployeeType.INTERNAL);
    dbManager.create(EmployeeType.INTERNAL, newInternalEmployee);

    System.out.println("External Employees after create");
    printEmployeesDetails(dbManager.getExternalEmployees());
    System.out.println("Internal Employees after create");
    printEmployeesDetails(dbManager.getInternalEmployees());

    System.out.println("Przykładowe wyszukiwanie pracownika");
    List<Person> foundEmployees = dbManager.find(EmployeeType.EXTERNAL, null, null, null, "12345678901");
    printEmployeesDetails(foundEmployees);


    dbManager.remove("a2c63b0f-40a2-426b-b137-90f94a3bf5eb");
    System.out.println("external Employees after remove");
    printEmployeesDetails(dbManager.getExternalEmployees());

    Person modifiedEmployee = new Person(UUID.randomUUID().toString(), "Karol", "Nowak", "987654321", "janusz.nowak@example.com", "98765432102", EmployeeType.EXTERNAL);
    boolean modified = dbManager.modify("0aef8a6c-1690-4a92-83c9-47f80761f6b1", modifiedEmployee);
    if (modified) {
      System.out.println("Pracownik o ID " + "0aef8a6c-1690-4a92-83c9-47f80761f6b1" + " został pomyślnie zaktualizowany.");
    } else {
      System.out.println("Nie udało się zaktualizować pracownika o ID " + "0aef8a6c-1690-4a92-83c9-47f80761f6b1" + ".");
    }
  }


}
