package org.jdaf;

import static org.junit.Assert.assertEquals;

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
}