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
	Map<Character,ArrayList<Integer>> first; 

	public First(char ch) {
		this.first=new HashMap<Character, ArrayList<Integer>>();
		
		first.put('B', new ArrayList<>(Arrays.asList(22,9,25,14,15,28)));
		first.put('F', new ArrayList<>(Arrays.asList()));
		first.put('B', new ArrayList<>(Arrays.asList(22,9,25,14,15,28)));
		first.put('B', new ArrayList<>(Arrays.asList(22,9,25,14,15,28)));
		first.put('B', new ArrayList<>(Arrays.asList(22,9,25,14,15,28)));
		first.put('B', new ArrayList<>(Arrays.asList(22,9,25,14,15,28)));
		
	}
	public static <Integer> ArrayList<Integer> unirArrayLists(ArrayList<Integer> lista1, ArrayList<Integer> lista2) {
        ArrayList<Integer> resultado = new ArrayList<>(lista1);  // Copiamos todos los elementos de lista1
        resultado.addAll(lista2);  // Agregamos todos los elementos de lista2
        return resultado;
    }
	
}
