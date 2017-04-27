package fr.bougly.service.helper;

import java.io.InputStream;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
public class ExcelReaderTest {

	@Test
	public void testCreateAccountFromExcelFile() throws Exception {
		
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream file = classloader.getResourceAsStream("excel/listStudents.xls");
		ExcelReader.createAccountFromExcelFile(file);
	}

}
