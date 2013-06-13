package ajou.ds.project.util;

import java.awt.TextArea;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class SearchUtil {

	private static final int NORMAL_SEARCH = 0;
	private static final int START_SEARCH = 1;
	private static final int MIDDLE_SEARCH = 2;
	private static final int END_SEARCH = 3;
	
	private int mode = NORMAL_SEARCH;
	private TextArea resultArea;
	
	public SearchUtil(TextArea resultArea) {
		this.resultArea = resultArea;
	}

	public void searchWord(Hashtable<String, ArrayList<Integer>> table,
			String word) {
		ArrayList<Integer> list = table.get(word);
		search(list, word);
	}
	
	public void searchPartWord(Hashtable<String, ArrayList<Integer>> table, String word) {
		List<String> keyList = Collections.list(table.keys());
		Iterator<String> iterator = keyList.iterator();
		
		if(word.startsWith("*")) {
			if(word.endsWith("*")) {
				this.mode = MIDDLE_SEARCH;
			} else {
				this.mode = START_SEARCH;
			}
			word = word.split("\\*")[1];
		} else if(word.endsWith("*")) {
			this.mode = END_SEARCH;
			word = word.split("\\*")[0];
		} else {
			resultArea.append("Cannot search that word.");
			return ;
		}
		
		while (iterator.hasNext()) {
			String key = iterator.next();
			
			switch (mode) {
			case START_SEARCH:
				if(key.endsWith(word)) {
					search(table.get(key), key);
				}
				break;
			case MIDDLE_SEARCH:
				if(key.contains(word)) {
					search(table.get(key), key);
				}
				break;
			case END_SEARCH:
				if(key.startsWith(word)) {
					search(table.get(key), key);
				}
				break;
			default:
				break;
			}
		}
	}
	
	protected void search(ArrayList<Integer> list, String word) {
		if (list != null) {
			resultArea.append("<" + word + "> is founded at..\n");
			Iterator<Integer> iterator = list.iterator();
			int prevNum = -1;
			int count = 0;
			while (iterator.hasNext()) {
				int num = iterator.next();
				if (num == prevNum || prevNum == -1) {
					count++;
				} else {
					resultArea.append("Doc " + prevNum + " : " + count + " times\n");
					count = 1;
				}
				prevNum = num;
			}
			resultArea.append("Doc " + prevNum + " : " + count + " times\n");
		} else {
			resultArea.append(word + " is not found.");
		}
	}
}
