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

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDataFrame() throws ParseException {
		//				A		B		C
		// 01-01-2015	1.1		3.3		5.5
		// 01-02-2015	2.2		4.4		6.6
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		Date date1 = sdf.parse("01-01-2015");
		Date date2 = sdf.parse("01-02-2015");
		List<Date> dateIndex = new ArrayList<Date>();
		dateIndex.add(date1);
		dateIndex.add(date2);
		DoubleMatrix values = new DoubleMatrix(2, 3, 1.1, 2.2, 3.3, 4.4, 5.5, 6.6);
		List<String> labels = new ArrayList<String>();
		labels.add("A");
		labels.add("B");
		labels.add("C");
		DoubleDataFrame<Date> df = new DoubleDataFrame<Date>(dateIndex, values, labels);
		Double val = df.get(date1, "A");
		assertEquals(1.1d, val, 0);
		System.out.println("val: " + val);
		
		double[] rowVals = df.getRowValues(date1);
		System.out.println("rowVals: " + printArray(rowVals));
		
		double[] colVals = df.getColumnValues("A");
		System.out.println("colVals: " + printArray(colVals));
		
		List<Date> indexes = df.getIndexes();
		System.out.println("indexes: " + indexes);
		
		List<String> colLabels = df.getLabels();
		System.out.println("colLabels: " + colLabels);
		
		DoubleMatrix vals = df.getValues();
		System.out.println("values: " + vals);
	}

	private String printArray(double[] vals) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < vals.length; i++) {
			if (i < vals.length - 1) {
				sb.append(vals[i] + ", ");
			} else {
				sb.append(vals[i]);
			}
		}
		sb.append("]");
		return sb.toString();
	}

}
