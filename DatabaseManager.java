import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseManager {
  private List<Person> externalEmployees;
  private List<Person> internalEmployees;
  private String externalDirectoryPath;
  private String internalDirectoryPath;

  public DatabaseManager(List<Person> externalEmployees, List<Person> internalEmployees, String externalDirectoryPath, String internalDirectoryPath) {
    this.externalEmployees = externalEmployees;
    this.internalEmployees = internalEmployees;
    this.externalDirectoryPath = externalDirectoryPath;
    this.internalDirectoryPath = internalDirectoryPath;
  }

  public void create(EmployeeType type, Person person) {

    List<Person> employees = type == EmployeeType.EXTERNAL ? externalEmployees : internalEmployees;
    employees.add(person);

    String directoryPath =
        type == EmployeeType.EXTERNAL ? externalDirectoryPath : internalDirectoryPath;
    String fileName = type.toString().toLowerCase() + "_employee_" + person.getPersonId() + ".xml";
    String filePath = directoryPath + File.separator + fileName;
    try {
      FileWriter fileWriter = new FileWriter(filePath);
      fileWriter.write("<Employee>\n");
      fileWriter.write("    <PersonalId>" + person.getPersonId() + "</PersonalId>\n");
      fileWriter.write("    <FirstName>" + person.getFirstName() + "</FirstName>\n");
      fileWriter.write("    <LastName>" + person.getLastName() + "</LastName>\n");
      fileWriter.write("    <Mobile>" + person.getMobile() + "</Mobile>\n");
      fileWriter.write("    <Email>" + person.getEmail() + "</Email>\n");
      fileWriter.write("    <PESEL>" + person.getPesel() + "</PESEL>\n");
      fileWriter.write("</Employee>");
      fileWriter.close();
    } catch (IOException e) {
      System.err.println("Błąd podczas tworzenia pliku XML dla nowego pracownika.");
      e.printStackTrace();
    }
  }
  public List<Person> find(EmployeeType type, String firstName, String lastName, String mobile, String pesel) {
    List<Person> employees = type == EmployeeType.EXTERNAL ? externalEmployees : internalEmployees;

    return employees.stream()
        .filter(employee ->
            (firstName == null || firstName.equals(employee.getFirstName())) &&
                (lastName == null || lastName.equals(employee.getLastName())) &&
                (mobile == null || mobile.equals(employee.getMobile())) &&
                (pesel == null || pesel.equals(employee.getPesel()))
        )
        .collect(Collectors.toList());
  }

  public boolean remove(String personId) {
    boolean removed = externalEmployees.removeIf(employee -> employee.getPersonId().equals(personId));
    if (!removed) {
      removed = internalEmployees.removeIf(employee -> employee.getPersonId().equals(personId));
    }
    if (removed) {
      String externalFilePath = externalDirectoryPath + File.separator + "external_employee_" + personId + ".xml";
      String internalFilePath = internalDirectoryPath + File.separator + "internal_employee_" + personId + ".xml";

      System.out.println(externalFilePath);
      File externalFile = new File(externalFilePath);
      File internalFile = new File(internalFilePath);
      if (externalFile.exists()) {
        if (!externalFile.delete()) {
          System.err.println("Błąd podczas usuwania pliku XML pracownika zewnętrznego.");
        }
      }
      if (internalFile.exists()) {
        if (!internalFile.delete()) {
          System.err.println("Błąd podczas usuwania pliku XML pracownika wewnętrznego.");
        }
      }
    }
    return removed;
  }

  public boolean modify(String personId, Person modifiedPerson) {

    if (!externalEmployees.stream().anyMatch(employee -> employee.getPersonId().equals(personId)) &&
        !internalEmployees.stream().anyMatch(employee -> employee.getPersonId().equals(personId))) {
      System.err.println("Pracownik o ID " + personId + " nie istnieje.");
      return false;
    }

    Person employee = findEmployeeById(personId);

    if (employee != null) {

      employee.setFirstName(modifiedPerson.getFirstName());
      employee.setLastName(modifiedPerson.getLastName());
      employee.setMobile(modifiedPerson.getMobile());
      employee.setEmail(modifiedPerson.getEmail());
      employee.setPesel(modifiedPerson.getPesel());
      employee.setType(modifiedPerson.getType());

      String directoryPath = employee.getType() == EmployeeType.EXTERNAL ? externalDirectoryPath : internalDirectoryPath;
      String fileName = employee.getType().toString().toLowerCase() + "_employee_" + employee.getPersonId() + ".xml";
      String filePath = directoryPath + File.separator + fileName;
      try {
        FileWriter fileWriter = new FileWriter(filePath);

        fileWriter.write("<Employee>\n");
        fileWriter.write("    <PersonalId>" + employee.getPersonId() + "</PersonalId>\n");
        fileWriter.write("    <FirstName>" + employee.getFirstName() + "</FirstName>\n");
        fileWriter.write("    <LastName>" + employee.getLastName() + "</LastName>\n");
        fileWriter.write("    <Mobile>" + employee.getMobile() + "</Mobile>\n");
        fileWriter.write("    <Email>" + employee.getEmail() + "</Email>\n");
        fileWriter.write("    <PESEL>" + employee.getPesel() + "</PESEL>\n");
        fileWriter.write("</Employee>");
        fileWriter.close();
        return true;
      } catch (IOException e) {
        System.err.println("Błąd podczas aktualizacji pliku XML pracownika.");
        e.printStackTrace();
        return false;
      }
    }
    return false;
  }

  public Person findEmployeeById(String personId) {
    Person foundEmployee = externalEmployees.stream()
        .filter(employee -> employee.getPersonId().equals(personId))
        .findFirst()
        .orElse(null);

    if (foundEmployee == null) {
      foundEmployee = internalEmployees.stream()
          .filter(employee -> employee.getPersonId().equals(personId))
          .findFirst()
          .orElse(null);
    }

    return foundEmployee;
  }

  public List<Person> getExternalEmployees() {
    return externalEmployees;
  }

  public List<Person> getInternalEmployees() {
    return internalEmployees;
  }


}
