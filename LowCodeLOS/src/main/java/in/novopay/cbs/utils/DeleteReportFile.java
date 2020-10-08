package in.novopay.cbs.utils;

import java.io.File;

import org.testng.annotations.Test;

public class DeleteReportFile {
	@Test
	public void deleteReport(){
		File file = new File("./reports/TestReport.xlsx");
		if(file.exists()){
			file.delete();
		}
	}
}
