package org.jdaf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jblas.DoubleMatrix;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DoubleDataFrameTest {
	private Date date1;

	private Date date2;

	private List<String> labels;

	private DoubleMatrix values;

	private List<Date> dateIndex;

	private DoubleDataFrame<Date> df = null;

	@Before
	/**
	 * Set-up this data frame.				
	 *				A		B		C
	 * 01-01-2015	1.1		3.3		5.5
	 * 01-02-2015	2.2		4.4		6.6
	 */
	public void setUp() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		date1 = sdf.parse("01-01-2015");
		date2 = sdf.parse("01-02-2015");
		dateIndex = new ArrayList<Date>();
		dateIndex.add(date1);
		dateIndex.add(date2);
		values = new DoubleMatrix(2, 3, 1.1, 2.2, 3.3, 4.4, 5.5, 6.6);
		labels = new ArrayList<String>();
		labels.add("A");
		labels.add("B");
		labels.add("C");
		df = new DoubleDataFrame<Date>(dateIndex, values, labels);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetValueByIndexAndLabel() {
		Double val = df.get(date1, "A");
		assertEquals(1.1d, val, 0);
	}

	@Test
	public void testGetRowValuesByIndex() {
		DoubleMatrix rows = df.getRowValues(date1);
		DoubleMatrix expRows = new DoubleMatrix(
				new double[] { 1.1d, 3.3d, 5.5d });
		assertEquals(true, rows.isColumnVector());
		assertEquals(rows, expRows);
	}

	@Test
	public void testGetColumnValuesByLabel() {
		DoubleMatrix cols = df.getColumnValues("A");
		DoubleMatrix expCols = new DoubleMatrix(new double[] { 1.1d, 2.2d });
		assertEquals(true, cols.isColumnVector());
		assertEquals(cols, expCols);
	}

	@Test
	public void testGetIndexes() {
		List<Date> indexes = df.getIndexes();
		// Date index1 = indexes.get(0);
		// Date index2 = indexes.get(1);
		// assertEquals(date1, index1);
		// assertEquals(date2, index2);
		assertEquals(dateIndex, indexes);

	}

	@Test
	public void testGetLabels() {
		List<String> stringLabels = df.getLabels();
		assertEquals(labels, stringLabels);
	}

	@Test
	public void testValuesInDoubleMatrix() throws ParseException {
		DoubleMatrix vals = df.getValues();
		assertEquals(values, vals);
	}

	@Test
	public void testEqualityWithEqualObjects() {
		DoubleMatrix values = new DoubleMatrix(new double[][] { { 1, 2, 3 },
				{ 4, 5, 6 } });
		List<Integer> indexes = new ArrayList<Integer>(2);
		indexes.add(1);
		indexes.add(2);
		List<String> labels = new ArrayList<String>(3);
		labels.add("one");
		labels.add("two");
		labels.add("three");

		DoubleMatrix values2 = new DoubleMatrix(new double[][] { { 1, 2, 3 },
				{ 4, 5, 6 } });
		List<Integer> indexes2 = new ArrayList<Integer>(2);
		indexes2.add(1);
		indexes2.add(2);
		List<String> labels2 = new ArrayList<String>(3);
		labels2.add("one");
		labels2.add("two");
		labels2.add("three");

		DoubleDataFrame<Integer> f1 = new DoubleDataFrame<Integer>(indexes,
				values, labels);
		DoubleDataFrame<Integer> f2 = new DoubleDataFrame<Integer>(indexes2,
				values2, labels2);

		assertEquals(f1, f2);
	}

	@Test
	public void testEqualityWithValuesNotEqualObjects() {
		DoubleMatrix values = new DoubleMatrix(new double[][] { { 1, 2, 3 },
				{ 4, 5, 6 } });
		List<Integer> indexes = new ArrayList<Integer>(2);
		indexes.add(1);
		indexes.add(2);
		List<String> labels = new ArrayList<String>(3);
		labels.add("one");
		labels.add("two");
		labels.add("three");

		DoubleDataFrame<Integer> f1 = new DoubleDataFrame<Integer>(indexes,
				values, labels);

		DoubleMatrix values2 = new DoubleMatrix(new double[][] { { 1, 9, 3 },
				{ 4, 7, 6 } });
		List<Integer> indexes2 = new ArrayList<Integer>(2);
		indexes2.add(1);
		indexes2.add(2);
		List<String> labels2 = new ArrayList<String>(3);
		labels2.add("one");
		labels2.add("two");
		labels2.add("three");

		DoubleDataFrame<Integer> f2 = new DoubleDataFrame<Integer>(indexes2,
				values2, labels2);

		assertNotEquals(f1, f2);
	}

	@Test
	public void testEqualityWithLabelsNotEqual() {
		DoubleMatrix values = new DoubleMatrix(new double[][] { { 1, 2, 3 },
				{ 4, 5, 6 } });
		List<Integer> indexes = new ArrayList<Integer>(2);
		indexes.add(1);
		indexes.add(2);
		List<String> labels = new ArrayList<String>(3);
		labels.add("one");
		labels.add("two");
		labels.add("three");

		DoubleDataFrame<Integer> f1 = new DoubleDataFrame<Integer>(indexes,
				values, labels);

		DoubleMatrix values2 = new DoubleMatrix(new double[][] { { 1, 2, 3 },
				{ 4, 5, 6 } });
		List<Integer> indexes2 = new ArrayList<Integer>(2);
		indexes2.add(1);
		indexes2.add(2);
		List<String> labels2 = new ArrayList<String>(3);
		labels2.add("one");
		labels2.add("two");
		labels2.add("four"); // Different

		DoubleDataFrame<Integer> f2 = new DoubleDataFrame<Integer>(indexes2,
				values2, labels2);

		assertNotEquals(f1, f2);

	}

	@Test
	public void testEqualityWithIndexesNotEqual() {
		DoubleMatrix values = new DoubleMatrix(new double[][] { { 1, 2, 3 },
				{ 4, 5, 6 } });
		List<Integer> indexes = new ArrayList<Integer>(2);
		indexes.add(1);
		indexes.add(2);
		List<String> labels = new ArrayList<String>(3);
		labels.add("one");
		labels.add("two");
		labels.add("three");

		DoubleDataFrame<Integer> f1 = new DoubleDataFrame<Integer>(indexes,
				values, labels);

		DoubleMatrix values2 = new DoubleMatrix(new double[][] { { 1, 2, 3 },
				{ 4, 5, 6 } });
		List<Integer> indexes2 = new ArrayList<Integer>(2);
		indexes2.add(1);
		indexes2.add(3);
		List<String> labels2 = new ArrayList<String>(3);
		labels2.add("one");
		labels2.add("two");
		labels2.add("three"); // Different

		DoubleDataFrame<Integer> f2 = new DoubleDataFrame<Integer>(indexes2,
				values2, labels2);

		assertNotEquals(f1, f2);

	}

	@Test
	public void testEqualHashCodeWithEqualsEqual() {
		DoubleMatrix values = new DoubleMatrix(new double[][] { { 1, 2, 3 },
				{ 4, 5, 6 } });
		List<Integer> indexes = new ArrayList<Integer>(2);
		indexes.add(1);
		indexes.add(2);
		List<String> labels = new ArrayList<String>(3);
		labels.add("one");
		labels.add("two");
		labels.add("three");

		DoubleMatrix values2 = new DoubleMatrix(new double[][] { { 1, 2, 3 },
				{ 4, 5, 6 } });
		List<Integer> indexes2 = new ArrayList<Integer>(2);
		indexes2.add(1);
		indexes2.add(2);
		List<String> labels2 = new ArrayList<String>(3);
		labels2.add("one");
		labels2.add("two");
		labels2.add("three");

		DoubleDataFrame<Integer> f1 = new DoubleDataFrame<Integer>(indexes,
				values, labels);
		DoubleDataFrame<Integer> f2 = new DoubleDataFrame<Integer>(indexes2,
				values2, labels2);

		// If equals() are equal then hashcode()s are equal
		assertEquals(f1, f2);
		assertEquals(f1.hashCode(), f2.hashCode());
	}

	@Test
	public void testPutValueInRowRange() {
		int firstIndex = 0;
		int lastIndex = 2;
		double value = 9;
		List<Integer> index = new ArrayList<Integer>(3);
		index.add(1);
		index.add(2);
		index.add(3);
		DoubleMatrix values = new DoubleMatrix(new double[][] { { 1, 2, 3 },
				{ 4, 5, 6 }, { 7, 8, 9 } });
		List<String> labels = new ArrayList<String>(3);
		labels.add("one");
		labels.add("two");
		labels.add("three");
		DoubleDataFrame<Integer> df = new DoubleDataFrame<Integer>(index,
				values, labels);
		
		// Expected
		DoubleMatrix expValues = new DoubleMatrix(new double[][] { { 9, 9, 9 },
				{ 9, 9, 9 }, { 7, 8, 9 } });
		DoubleDataFrame<Integer> expDf = new DoubleDataFrame<Integer>(index, expValues, labels);
		
		// Method under test
		df.putRowValue(firstIndex, lastIndex, value);
		
		assertEquals(expDf, df);
	}
}