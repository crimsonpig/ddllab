package models;

import java.io.Serializable;
import play.data.validation.Constraints.*;


public class TestPOJO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Required
	private int testNumber;

	@MaxLength(10)
	private String controlResultLine;

	@MaxLength(500)
	private String controlText;
	
	@MaxLength(500)
	private String resultText;

	@MaxLength(8)
	private String respicture;

	@MaxLength(30)
	@Required
	private String testName;

	//Acceptable values = L or C
	@MaxLength(1)
	@Required
	private String testType;

	@MaxLength(10)
	private String typeOfSample;

	@MaxLength(10)
	private String units;

	@MaxLength(800)
	private String defaultComment;

	public TestPOJO() {
	}

	public int getTestNumber() {
		return testNumber;
	}

	public String getControlResultLine() {
		return controlResultLine;
	}

	public String getControlText() {
		return controlText;
	}

	public String getResultText() {
		return resultText;
	}

	public String getRespicture() {
		return respicture;
	}

	public String getTestName() {
		return testName;
	}

	public String getTestType() {
		return testType;
	}

	public String getTypeOfSample() {
		return typeOfSample;
	}

	public String getUnits() {
		return units;
	}

	public String getDefaultComment() {
		return defaultComment;
	}

	public void setTestNumber(int testNumber) {
		this.testNumber = testNumber;
	}

	public void setControlResultLine(String controlResultLine) {
		this.controlResultLine = controlResultLine;
	}

	public void setControlText(String controlText) {
		this.controlText = controlText;
	}

	public void setResultText(String resultText) {
		this.resultText = resultText;
	}

	public void setRespicture(String respicture) {
		this.respicture = respicture;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public void setTypeOfSample(String typeOfSample) {
		this.typeOfSample = typeOfSample;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public void setDefaultComment(String defaultComment) {
		this.defaultComment = defaultComment;
	}

	

}
