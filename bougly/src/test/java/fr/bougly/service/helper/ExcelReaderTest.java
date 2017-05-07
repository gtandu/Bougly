package fr.bougly.service.helper;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.InputStream;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import fr.bougly.web.dtos.AccountDto;

@ContextConfiguration
@RunWith(MockitoJUnitRunner.class)
public class ExcelReaderTest {
	
	@InjectMocks
	private ExcelReader excelReader;

	@Test
	public void testCreateAccountFromExcelFile() throws Exception {
		
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream file = classloader.getResourceAsStream("excel/listStudents3.xls");
		ArrayList<AccountDto> createAccountFromExcelFile = excelReader.createAccountFromExcelFile(file);
		
		System.out.println(createAccountFromExcelFile);
		
		assertThat(createAccountFromExcelFile).isNotNull();
		
		
	}

}
