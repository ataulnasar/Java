package parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Stack;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TextToXMLConverterTest {

	private TextToXMLConverter textToXMLconverter;
	private Stack<String> list;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		list = new Stack<String>();
		textToXMLconverter = new TextToXMLConverter();
		list = textToXMLconverter.readFile("file.txt");
	}

	@After
	public void tearDown() throws Exception {
		list.clear();
	}

	@Test
	public void testReadFile() {
		assertTrue(!list.isEmpty());
	}

	@Test
	public void testParser() {
		assertTrue(textToXMLconverter.parser(list));
	}

	@Test
	public void testTextToXML() {
		assertEquals(textToXMLconverter.textToXML(list, "text.xml"), "XML File saved");
	}

}
