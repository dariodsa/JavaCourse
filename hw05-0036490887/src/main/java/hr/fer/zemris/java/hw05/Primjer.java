package hr.fer.zemris.java.hw05;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

public class Primjer {
	public static void main(String[] args) {
		
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		
		for(SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
		}
		
		for(SimpleHashtable.TableEntry<String, Integer> pair1 : examMarks) {
			for(SimpleHashtable.TableEntry<String, Integer> pair2 : examMarks) {
				System.out.printf("(%s => %d) - (%s => %d)%n",
						pair1.getKey(),pair1.getValue(),
						pair2.getKey(),pair2.getValue());
			}
		}
	}
}
