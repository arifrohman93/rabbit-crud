import java.util.HashMap;
import java.util.Map;

public class CrudService {
    private static Map<Integer, String> database = new HashMap<>();
    private static int currentId = 1;

    // Create
    public static int create(String value) {
        database.put(currentId, value);
        return currentId++;
    }

    // Read
    public static String read(int id) {
        return database.get(id);
    }

    // Update
    public static void update(int id, String value) {
        database.put(id, value);
    }

    // Delete
    public static void delete(int id) {
        database.remove(id);
    }

    public static void main(String[] args) {
        int id1 = create("value1");
        System.out.println("Created ID " + id1 + " with value: " + read(id1));

        update(id1, "newValue1");
        System.out.println("Updated ID " + id1 + " with value: " + read(id1));

        delete(id1);
        System.out.println("Deleted ID " + id1 + ", current value: " + read(id1));
    }
}
