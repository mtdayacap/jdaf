package org.jdaf;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.jblas.DoubleMatrix;

/**
 * DoubleDataFrame in Java
 * 
 * @author Mike Dayacap
 * */
public class DoubleDataFrame<T> {

	public int columns;

	public int rows;

	private List<T> indexes;
	
	private List<String> labels;
	
	private DoubleMatrix values;
	
	public DoubleDataFrame(List<T> index, DoubleMatrix values,
			List<String> labels) {
		TreeSet<T> set = new TreeSet<T>();
		set.addAll(index);
		this.indexes = new ArrayList<T>(set);
		this.labels = labels;
		this.values = values;
		
		rows = index.size();
		columns = labels.size();
	}

	public Double get(T index, String label) {
		int row = indexes.indexOf(index);
		int column = labels.indexOf(label);
		return values.get(row, column);
	}

	public double[] getRowValues(T index) {
		int r = indexes.indexOf(index);
		DoubleMatrix result = new DoubleMatrix(columns, 1);
		values.getRow(r, result);
		double[] rowValues = new double[columns];
		for (int i = 0; i < columns; i++) {
			rowValues[i] = result.get(i, 0);
		}
		return rowValues;
	}

	public double[] getColumnValues(String label) {
		int c = labels.indexOf(label);
		DoubleMatrix result = new DoubleMatrix(rows, 1);
		values.getColumn(c, result);
		double[] colValues = new double[rows];
		for (int i = 0; i < rows; i++) {
			colValues[i] = result.get(i, 0);
		}
		return colValues;
	}

	public List<T> getIndexes() {
		return indexes;
	}

	public List<String> getLabels() {
		return labels;
	}

	public DoubleMatrix getValues() {
		return values;
	}

	public DoubleDataFrame<T> dup() {
		DoubleDataFrame<T> dupDf = new DoubleDataFrame<T>(indexes, values.dup(), labels);
		return dupDf;
	}
}
