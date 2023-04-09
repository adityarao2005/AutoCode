package com.raos.autocode.core.ds;

import java.lang.reflect.Array;
import java.util.List;

public class Table {
	private Class<?>[] template;
	private List<Tuple> rows;
	
	private Table(Class<?>[] template) {
		this.template = template;
	}
	
	public static Table createTable(Class<?>... classes) {
		return new Table(classes);
	}
	
	public Tuple getRow(int row) {
		return rows.get(row);
	}
	
	public void addRow(Object...objects) {
		// First security check
		if (template.length != objects.length)
			throw new RuntimeException("Incompatible Lengths");
		
		// Second security check, type check
		for (int i = 0; i < template.length; i++) {
			if (!template[i].isInstance(objects[i])) {
				throw new RuntimeException(String.format("Incompatible Types: %s, %s", template[i].getClass(), objects[i].getClass()));
			}
		}
		
		rows.add(new Tuple(objects));
	}
	
	public void addRow(Tuple tuple) {
		// First security check
		if (template.length != tuple.getLength())
			throw new RuntimeException("Incompatible Lengths");
		
		// Second security check, type check
		for (int i = 0; i < template.length; i++) {
			if (!template[i].isInstance(tuple.get(i))) {
				throw new RuntimeException(String.format("Incompatible Types: %s, %s", template[i].getClass(), tuple.get(i).getClass()));
			}
		}
		
		rows.add(tuple);
	}
	
	public void setRow(Tuple tuple, int row) {
		// First security check
		if (template.length != tuple.getLength())
			throw new RuntimeException("Incompatible Lengths");
		
		// Second security check, type check
		for (int i = 0; i < template.length; i++) {
			if (!template[i].isInstance(tuple.get(i))) {
				throw new RuntimeException(String.format("Incompatible Types: %s, %s", template[i].getClass(), tuple.get(i).getClass()));
			}
		}
		
		rows.set(row, tuple);
	}
	
	public void removeRow(int row) {
		rows.remove(row);
	}
	
	@SuppressWarnings("unchecked")
	public <E> E[] getColumn(int column) {
		return rows.stream().map(t -> t.get(column)).toArray(i -> (E[]) Array.newInstance(template[column], i));
	}
	
	
}
