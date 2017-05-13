package fr.bougly.service.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import fr.bougly.model.enumeration.RoleAccountEnum;
import fr.bougly.web.dtos.AccountDto;

@Service
public class ExcelReader {

	public ArrayList<AccountDto> createAccountFromExcelFile(InputStream excelFile) throws IOException {

		Workbook workbook = new HSSFWorkbook(excelFile);
		Sheet datatypeSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = datatypeSheet.iterator();
		AccountDtoListFromExcel accountDtoListFromExcel = new AccountDtoListFromExcel();
		while (iterator.hasNext()) {

			Row currentRow = iterator.next();
			Iterator<Cell> cellIterator = currentRow.iterator();
			AccountDto account = new AccountDto();
			while (cellIterator.hasNext()) {
				Cell currentCell = cellIterator.next();
				if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
					currentCell = cellIterator.next();
					account.setLastName(currentCell.getStringCellValue());
					currentCell = cellIterator.next();
					account.setFirstName(currentCell.getStringCellValue());
					currentCell = cellIterator.next();
					account.setStudentNumber(String.valueOf((int) currentCell.getNumericCellValue()));
					currentCell = cellIterator.next();
					account.setMail(currentCell.getStringCellValue());
					account.setRole(RoleAccountEnum.Student.getRole());
					accountDtoListFromExcel.add(account);
				}
			}

		}
		workbook.close();
		return accountDtoListFromExcel.getListAccountDto();
	}

}
