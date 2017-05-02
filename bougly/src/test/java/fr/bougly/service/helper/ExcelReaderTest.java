package fr.bougly.service.helper;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.InputStream;
import java.util.List;


import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
public class ExcelReaderTest {

	@Test
	public void testCreateAccountFromExcelFile() throws Exception {
		
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream file = classloader.getResourceAsStream("excel/listStudents.xls");
		//List createAccountFromExcelFile = ExcelReader.createAccountFromExcelFile(file);
		
		//System.out.println(createAccountFromExcelFile);
		
		//assertThat(createAccountFromExcelFile).isNotNull();
		
		
	}

}
