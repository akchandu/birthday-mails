package org.study.gmail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PrepareGreetingMail {

	private static final String fromAddress = "chandu.addanki@gmail.com";
	private static final String greetingFile = "/home/kaddanki/Desktop/birthday-list.xlsx";

	List<Person> listPersons = new ArrayList<Person>();

	public static void main(String[] args) throws MessagingException, IOException {
		PrepareGreetingMail obj = new PrepareGreetingMail();
		//obj.readFile(greetingFile);
		obj.getBirthdayPeople();
		for(Person p : obj.listPersons) {
			obj.sendMail(p.getName(), p.getEmailAddress(), p.getOccasion(), p.isPersonElder());
		}
	}

	public void sendMail(String recepient, String recepientEmailAddress, String occasion, 
			boolean isPersonElder) throws MessagingException, IOException {
		String emailSubject = "Happy " + occasion + "!";
		String bodyText = getGreetingMessage(recepient, occasion, isPersonElder);
		SendGreetingFromGmail.send(recepientEmailAddress, fromAddress, emailSubject, bodyText);
	}

	private String getGreetingMessage(String recepient, String occasion, 
			boolean isPersonElder) {
		String message = "";
		String salutationMessage = "Hello " + recepient + "," + "\n\n";
		String greetingMessage = "Wish you many many happy returns of the day!!" + "\n" + "Happy " + 
				occasion + " to you :-)" + "\n\n\n";
		String signature = "Regards," + "\n" + "Krishna Chandu";

		if (isPersonElder) {
			salutationMessage = salutationMessage + "Namasthe!" + "\n\n";
		}
		message = salutationMessage + greetingMessage + signature;
		return message;
	}

	private void readFile(String greetingFile) {
		try {
			FileInputStream file = new FileInputStream(new File(greetingFile));
			//Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			//Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);
			//Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("MMMM:d");
			String todayDate = sdf.format(date);
			while (rowIterator.hasNext())
			{
				Row row = rowIterator.next();
				//For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext())
				{
					Cell cell = cellIterator.next();
					if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
						if(todayDate.equalsIgnoreCase(cell.getStringCellValue())){
							Person person = new Person();
							Cell name = row.getCell(1);
							Cell email = row.getCell(2);
							Cell occasion = row.getCell(3);
							Cell elderPerson = row.getCell(4);

							person.setName(name.getStringCellValue());
							person.setEmailAddress(email.getStringCellValue());
							person.setOccasion(occasion.getStringCellValue());
							person.setPersonElder(false);
							if (elderPerson != null) {
								person.setPersonElder(true);
							}
							listPersons.add(person);
						}
					}
				}
			}
			file.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getBirthdayPeople() throws IOException {
		List<List<Object>> values = BirthdayListGoogleSheet.getBirthdayData();
		if (values == null || values.size() == 0) {
			System.out.println("No data found.");
		} else {
			String todayDate = Utility.getTodayDate();
			for (List row : values) {
				String currentEntry = (String) row.get(0);
				if (currentEntry.equalsIgnoreCase(todayDate)) {
					Person person = new Person();
					String name = (String) row.get(1);
					String email = (String) row.get(2);
					String occasion = (String) row.get(3);
					String salutation = (String) row.get(4);

					person.setName(name);
					person.setEmailAddress(email);
					person.setOccasion(occasion);
					person.setPersonElder(false);
					if (salutation.equalsIgnoreCase("yes")) {
						person.setPersonElder(true);
					}
					listPersons.add(person);
				}
			}
		}
	}
}
