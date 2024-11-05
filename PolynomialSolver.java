import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class PolynomialSolver {
    
    // Class to represent a root with x, y, and base
    static class Root {
        int x;
        String y;
        int base;

        Root(int x, String y, int base) {
            this.x = x;
            this.y = y;
            this.base = base;
        }

        // Method to decode y value based on its base
        int decodeY() {
            return Integer.parseInt(this.y, this.base);
        }
    }

    public static void main(String[] args) {
        // Read roots from JSON
        List<Root> roots = readRootsFromJson("polynomial_roots.json");

        if (roots == null || roots.isEmpty()) {
            System.out.println("No roots found in the JSON file.");
            return;
        }

        int m = roots.size() - 1;
        int k = m + 1;
        
        if (roots.size() < k) {
            System.out.println("Not enough roots to determine polynomial coefficients.");
            return;
        }

        // Decode y values and calculate the secret
        List<Integer> decodedYValues = new ArrayList<>();
        for (Root root : roots) {
            decodedYValues.add(root.decodeY());
        }

        // Example - Calculate the secret using decoded y values
        int secret = calculateSecret(decodedYValues);
        System.out.println("The secret is: " + secret);
    }

    // Reads roots from a JSON file and returns a list of Root objects
    private static List<Root> readRootsFromJson(String filePath) {
        List<Root> roots = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(filePath));
            JSONArray rootsArray = (JSONArray) jsonObject.get("roots");

            for (Object obj : rootsArray) {
                JSONObject rootObj = (JSONObject) obj;
                int x = ((Long) rootObj.get("x")).intValue();
                String y = (String) rootObj.get("y");
                int base = ((Long) rootObj.get("base")).intValue();
                
                roots.add(new Root(x, y, base));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return roots;
    }

    // Method to calculate the secret using decoded y values
    private static int calculateSecret(List<Integer> decodedYValues) {
        // Implement your calculation logic here.
        // Example: Sum of decoded values
        int secret = 0;
        for (int y : decodedYValues) {
            secret += y;
        }
        return secret;
    }
}
