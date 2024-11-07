package pdl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class First {
	Map<String,ArrayList<Integer>> first; 
	Map<String,ArrayList<Integer>> follow;
	public First() {
		this.first=new HashMap<String, ArrayList<Integer>>();
		this.follow = new HashMap<>();
		follow.put("E'", new ArrayList<>(Arrays.asList(17,18,19)));
		follow.put("R'", new ArrayList<>(Arrays.asList(6,17,18,19)));
		follow.put("U'", new ArrayList<>(Arrays.asList(6,17,18,19,5)));
		follow.put("V'", new ArrayList<>(Arrays.asList(6,17,4,18,19,5)));
		follow.put("X", new ArrayList<>(Arrays.asList(19)));
		follow.put("L", new ArrayList<>(Arrays.asList(17)));
		follow.put("Q", new ArrayList<>(Arrays.asList(17)));
		follow.put("C", new ArrayList<>(Arrays.asList(17,21)));
		follow.put("K", new ArrayList<>(Arrays.asList(17)));
		
		
		first.put("B", new ArrayList<>(Arrays.asList(25,1,22,14,15,28,9)));
		first.put("F", new ArrayList<>(Arrays.asList(27)));
		first.put("P", new ArrayList<>(Arrays.asList(22,9,25,1,14,15,28,27)));
		first.put("E", new ArrayList<>(Arrays.asList(1,16,2,3)));
		first.put("E'", new ArrayList<>(Arrays.asList(6)));
		first.put("R", new ArrayList<>(Arrays.asList(1,16,2,3)));
		first.put("R'", new ArrayList<>(Arrays.asList(5)));
		first.put("U", new ArrayList<>(Arrays.asList(1,16,2,3)));
		first.put("U'", new ArrayList<>(Arrays.asList(4)));
		first.put("V", new ArrayList<>(Arrays.asList(1,16,2,3)));
		first.put("V'", new ArrayList<>(Arrays.asList(16)));
		first.put("S", new ArrayList<>(Arrays.asList(1,14,15,28)));
		first.put("S'", new ArrayList<>(Arrays.asList(16,7,8)));
		first.put("X", new ArrayList<>(Arrays.asList(1,16,2,3)));
		first.put("L", new ArrayList<>(Arrays.asList(1,16,2,3)));
		first.put("Q", new ArrayList<>(Arrays.asList(18)));
		first.put("T", new ArrayList<>(Arrays.asList(10,11,12))); 
		first.put("H", new ArrayList<>(Arrays.asList(10,11,12,13)));
		first.put("A", new ArrayList<>(Arrays.asList(10,11,12,13))); 
		first.put("K", new ArrayList<>(Arrays.asList(18)));
		first.put("C", new ArrayList<>(Arrays.asList(25,1,22,14,15,28,9)));

	}

}
