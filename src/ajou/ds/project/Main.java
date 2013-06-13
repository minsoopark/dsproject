package ajou.ds.project;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import ajou.ds.project.util.FileUtil;
import ajou.ds.project.util.SearchUtil;

public class Main {
	Frame frame;
	Panel panel;
	TextField wordField;
	Button searchButton;
	CheckboxGroup checkBoxGroup;
	Checkbox andCheckbox;
	Checkbox orCheckbox;
	TextArea resultArea;
	
	static Hashtable<String, ArrayList<Integer>> wordTable;
	
	SearchUtil searchUtil;

	public Main() {
		frame = new Frame("Search Engine");
		panel = new Panel();
		wordField = new TextField(20);
		searchButton = new Button("Search");
		checkBoxGroup = new CheckboxGroup();
		orCheckbox = new Checkbox("OR", true, checkBoxGroup);
		andCheckbox = new Checkbox("AND", false, checkBoxGroup);
		resultArea = new TextArea(20, 27);

		searchUtil = new SearchUtil(resultArea);

		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent ae) {
				if(ae.getSource() == searchButton) {
					resultArea.setText("");
					String word = wordField.getText();
					if(word.contains(" ")) {
						String[] words = word.split("\\s");
						if(orCheckbox.getState()) {			//TODO: OR
							for(String splitted:words) {
								search(splitted);
							}
						} else {							//TODO: AND
							
						}
					} else {
						search(word);
					}
				}
			}
		});
		
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.add(wordField, "North");
		panel.add(searchButton, "North");
		panel.add(orCheckbox);
		panel.add(andCheckbox);
		panel.add(resultArea, "Center");

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		
		frame.add(panel);
		frame.setSize(300, 438);
		frame.setVisible(true);
	}
	
	private void search(String word) {
		if(word.contains("*")) {
			searchUtil.searchPartWord(wordTable, word);
		} else {
			searchUtil.searchWord(wordTable, word);
		}
	}

	public static void main(String args[]) throws IOException {

		wordTable = new Hashtable<String, ArrayList<Integer>>();
		String pathFormat = "./data/cranfield00%02d";

		try {
			for (int i = 1; i <= 50; i++) {
				FileUtil.readFile(i, String.format(pathFormat, i), wordTable);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		new Main();
		
	}
}
