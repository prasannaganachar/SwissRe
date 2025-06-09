import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Employee {
    String id;
    String firstName;
    String lastName;
    double salary;
    String managerId;
    Employee manager;
    List<Employee> subordinates = new ArrayList<>();

    public Employee(String id, String firstName, String lastName, double salary, String managerId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.managerId = managerId;
    }

    void addSubordinate(Employee e) {
        subordinates.add(e);
        e.manager = this;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}

public class BigCompanyAnalysis {
    public static void main(String[] args) throws IOException {
        Map<String, Employee> employees = new HashMap<>();
        List<String[]> rows = new ArrayList<>();

        // Read CSV
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Dev\\personalRepo\\SwissRe\\SwissRe\\src\\employees.csv"))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                rows.add(parts);
                employees.put(parts[0], new Employee(
                        parts[0], parts[1], parts[2], Double.parseDouble(parts[3]), parts.length > 4 ? parts[4] : null
                ));
            }
        }

        // Link managers and subordinates
        Employee ceo = null;
        for (String[] row : rows) {
            String id = row[0];
            String managerId = row.length > 4 ? row[4] : null;
            Employee emp = employees.get(id);
            if (managerId == null || managerId.isEmpty()) {
                ceo = emp;
            } else {
                Employee mgr = employees.get(managerId);
                if (mgr != null) {
                    mgr.addSubordinate(emp);
                }
            }
        }

        // Analysis as before
        System.out.println("Managers with salary constraint violations:");
        checkSalaryConstraints(ceo);

        System.out.println("\nEmployees with more than 4 managers between them and CEO:");
        findDeepEmployees(ceo, 0);
    }

    static void checkSalaryConstraints(Employee emp) {
        if (!emp.subordinates.isEmpty()) {
            double avg = emp.subordinates.stream().mapToDouble(e -> e.salary).average().orElse(0);
            double min = avg * 1.2;
            double max = avg * 1.5;
            if (emp.salary < min || emp.salary > max) {
                System.out.printf("%s: salary %.2f, avg sub salary %.2f (should be between %.2f and %.2f)%n",
                        emp.getFullName(), emp.salary, avg, min, max);
            }
            for (Employee sub : emp.subordinates) {
                checkSalaryConstraints(sub);
            }
        }
    }

    static void findDeepEmployees(Employee emp, int depth) {
        if (emp.subordinates.isEmpty() && depth > 4) {
            System.out.printf("%s (depth: %d)%n", emp.getFullName(), depth);
        }
        for (Employee sub : emp.subordinates) {
            findDeepEmployees(sub, depth + 1);
        }
    }
}
