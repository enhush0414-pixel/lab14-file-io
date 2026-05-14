
import java.io.*;
import java.util.*;

public class SaveManager {

    /**
     * Core: Нэг баатрын мэдээллийг CSV хэлбэрээр хадгалах
     */
    public static void save(Character hero, String path) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            String record = String.format("%s,%d,%d,%d",
                    hero.getName(), hero.getHp(), hero.getMp(), hero.getGold());
            writer.write(record);
            writer.newLine();
        }
    }

    /**
     * Core: CSV-ээс баатрын мэдээллийг сэргээх
     */
    public static Character load(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String dataLine = reader.readLine();
            if (dataLine == null) throw new IOException("Save file is empty.");

            String[] fields = dataLine.split(",");
            return new Character(
                    fields[0],
                    Integer.parseInt(fields[1]),
                    Integer.parseInt(fields[2]),
                    Integer.parseInt(fields[3])
            );
        }
    }

    /**
     * Stretch: Бүх багийг (Party) мөр мөрөөр хадгалах
     */
    public static void saveParty(List<Character> squad, String path) throws IOException {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(path))) {
            for (Character member : squad) {
                out.write(member.getName() + "," + member.getHp() + "," +
                        member.getMp() + "," + member.getGold());
                out.newLine();
            }
        }
    }

    /**
     * Stretch: Файлаас багийг уншиж List үүсгэх
     */
    public static List<Character> loadParty(String path) throws IOException {
        List<Character> team = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(path))) {
            String row;
            while ((row = in.readLine()) != null) {
                if (row.isBlank()) continue;
                String[] info = row.split(",");
                team.add(new Character(info[0], Integer.parseInt(info[1]),
                        Integer.parseInt(info[2]), Integer.parseInt(info[3])));
            }
        }
        return team;
    }

    /**
     * Stretch: Inventory-г (ItemName, Count) хадгалах
     */
    public static void saveInventory(Map<String, Integer> storage, String path) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (var entry : storage.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        }
    }

    /**
     * Stretch: Inventory-г файлаас Map-д унших
     */
    public static Map<String, Integer> loadInventory(String path) throws IOException {
        Map<String, Integer> itemsMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] entry = line.split(",");
                itemsMap.put(entry[0], Integer.parseInt(entry[1]));
            }
        }
        return itemsMap;
    }

    /**
     * Bonus: Баатрыг JSON форматтай текст файлд хадгалах
     */
    public static void saveJson(Character c, String path) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            String jsonOutput = String.format(
                    "{\"name\":\"%s\",\"hp\":%d,\"mp\":%d,\"gold\":%d}",
                    c.getName(), c.getHp(), c.getMp(), c.getGold()
            );
            writer.write(jsonOutput);
        }
    }
}