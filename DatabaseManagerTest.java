import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class DatabaseManagerTest {

  private String externalDirectoryPath;
  private String internalDirectoryPath;
  private List<Person> externalEmployees;
  private List<Person> internalEmployees;
  private DatabaseManager dbManager;

  @BeforeEach
  public void setUp() {
    externalDirectoryPath = "external_employees";
    internalDirectoryPath = "internal_employees";
    externalEmployees = new ArrayList<>();
    internalEmployees = new ArrayList<>();
    dbManager = new DatabaseManager(externalEmployees, internalEmployees, "external", "internal");
  }

  @Test
  public void testCreateAndFind() {
    Person newExternalEmployee = new Person("1", "John", "Doe", "123456789", "john.doe@example.com", "12345678901", EmployeeType.EXTERNAL);
    dbManager.create(EmployeeType.EXTERNAL, newExternalEmployee);

    List<Person> foundEmployees = dbManager.find(EmployeeType.EXTERNAL, "John", "Doe", "123456789", "12345678901");
    assertEquals(1, foundEmployees.size());
    assertEquals(newExternalEmployee, foundEmployees.get(0));
  }

  @Test
  public void testRemove() {
    Person newExternalEmployee = new Person("1", "John", "Doe", "123456789", "john.doe@example.com", "12345678901", EmployeeType.EXTERNAL);
    dbManager.create(EmployeeType.EXTERNAL, newExternalEmployee);
    boolean removed = dbManager.remove("1");
    assertTrue(removed);

    List<Person> foundEmployees = dbManager.find(EmployeeType.EXTERNAL, "John", "Doe", "123456789", "12345678901");
    assertTrue(foundEmployees.isEmpty());
  }

  @Test
  void modifyNonExistingEmployee() {
    Person modifiedEmployee = new Person("123", "Jane", "Doe", "987654321", "jane.doe@example.com", "98765432101", EmployeeType.EXTERNAL);
    boolean modified = dbManager.modify("123", modifiedEmployee);

    assertFalse(modified);
  }

  @Test
  void modifyEmployeeInvalidDirectory() throws IOException {
    File file = new File(externalDirectoryPath);
    file.createNewFile();

    Person modifiedEmployee = new Person("123", "Jane", "Doe", "987654321", "jane.doe@example.com", "98765432101", EmployeeType.EXTERNAL);
    boolean modified = dbManager.modify("123", modifiedEmployee);

    assertFalse(modified);

    Files.deleteIfExists(file.toPath());
  }

  @Test
  void modifyExistingEmployee(){
  }
}
