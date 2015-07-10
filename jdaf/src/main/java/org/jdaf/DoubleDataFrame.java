package org.jdaf;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jblas.DoubleMatrix;

/**
 * 
 * @author Mike Dayacap
 * */
public class DoubleDataFrame<I> {

	public int columns;

	public int rows;

	private List<I> indexes;

	private List<String> labels;

	private DoubleMatrix values;

	public DoubleDataFrame(List<I> index, DoubleMatrix values,
			List<String> labels) {
		TreeSet<I> set = new TreeSet<I>();
		set.addAll(index);
		this.indexes = new ArrayList<I>(set);
		setFields(index, values, labels);
	}

	public DoubleDataFrame(List<I> indexes, DoubleMatrix values) {
		TreeSet<I> set = new TreeSet<I>();
		set.addAll(indexes);
		this.indexes = new ArrayList<I>(set);

		this.values = values;
		
		// Build default column labels
		int cols = values.columns;
		List<String> labels = new ArrayList<String>();
		for (int i = 0; i < cols; i++) {
			labels.add(Integer.toString(i));
		}
		this.labels = labels;
	}

	public DoubleDataFrame<I> dup() {
		DoubleDataFrame<I> dupDf = new DoubleDataFrame<I>(new ArrayList<I>(
				indexes), values.dup(), new ArrayList<String>(labels));
		return dupDf;
	}

	@SuppressWarnings(value = { "unchecked" })
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o == this) {
			return true;
		}
		if (o.getClass() != getClass()) {
			return false;
		}

		DoubleDataFrame<I> f = (DoubleDataFrame<I>) o;
		return new EqualsBuilder().append(this.indexes, f.getIndexes())
				.append(this.labels, f.getLabels())
				.append(this.values, f.getValues()).isEquals();
	}

	public void fillNAN(double fillValue) {
		int rows = values.rows;
		int cols = values.columns;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				double v = values.get(r, c);
				if (Double.isNaN(v)) {
					values.put(r, c, fillValue);
				}
			}
		}
	}

	public Double get(I index, String label) {
		int row = indexes.indexOf(index);
		int column = labels.indexOf(label);
		return values.get(row, column);
	}

	public DoubleMatrix getColumnValues(String label) {
		int c = labels.indexOf(label);
		DoubleMatrix result = new DoubleMatrix(rows, 1);
		values.getColumn(c, result);
		return result;
	}

	public List<I> getIndexes() {
		return indexes;
	}

	public List<String> getLabels() {
		return labels;
	}

	public DoubleMatrix getRowValues(I index) {
		int r = indexes.indexOf(index);
		DoubleMatrix result = new DoubleMatrix(columns, 1);
		values.getRow(r, result);
		return result;
	}

	public DoubleMatrix getValues() {
		return values;
	}

	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(values).append(labels)
				.append(indexes).hashCode();
	}

	public void muli(double value) {
		values.muli(value);
	}

	public void putRowValue(int start, int end, double value) {
		// Put value in a vector
		int len = labels.size();
		DoubleMatrix v = new DoubleMatrix(len);
		v.fill(value);
		
		for (int i = start; i < end; i++) {
			values.putRow(i, v);
		}
	}

	public void removeColumn(String label) {
		DoubleMatrix newM = new DoubleMatrix(rows, columns - 1);
		int removeColIndex = labels.indexOf(label);
		for (int i = 0, j = 0; i < columns; i++) {
			if (removeColIndex != i) {
				// Skip column to be removed
				// Copy column to new Matrix
				DoubleMatrix vCol = values.getColumn(i);
				newM.putColumn(j, vCol);
				j++;
			}
		}

		// Update fields
		labels.remove(label);
		setFields(indexes, newM, labels);
	}

	public void set(String label, I index, double value) {
		int rowIndex = indexes.indexOf(index);
		int columnIndex = labels.indexOf(label);
		values.put(rowIndex, columnIndex, value);
	}

	
	private void setFields(List<I> index, DoubleMatrix values,
			List<String> labels) {
		this.labels = labels;
		this.values = values;
		this.rows = index.size();
		this.columns = labels.size();
	}

	public double get(int rowIndex, int columnIndex) {
		return values.get(rowIndex, columnIndex);
	}

}
