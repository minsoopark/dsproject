package ajou.ds.project.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.regex.Pattern;

public class FileUtil {
	public static void readFile(int docNum, String path,
			Hashtable<String, ArrayList<Integer>> table) throws IOException {
		Scanner scanner = new Scanner(new File(path));
		scanner.useDelimiter(Pattern.compile("[^a-zA-Z]"));
		while (scanner.hasNext()) {
			String word = scanner.next();
			if (table.get(word) == null) {
				table.put(word, new ArrayList<Integer>());
			}
			table.get(word).add(docNum);
		}
		scanner.close();
	}
}
