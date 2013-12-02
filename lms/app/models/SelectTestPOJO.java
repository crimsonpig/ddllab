package models;

public class SelectTestPOJO {

	public SelectTestPOJO(int testNumber, String testName,
			String sampleType, long numberOfCases) {
		super();
		this.testNumber = testNumber;
		this.testName = testName;
		this.sampleType = sampleType;
		this.numberOfCases = numberOfCases;
	}

	private int testNumber;
	private String testName;
	private String sampleType;
	private long numberOfCases;
	
	public SelectTestPOJO(){
		
	}
	
	public int getTestNumber() {
		return testNumber;
	}

	public String getTestName() {
		return testName;
	}

	public String getTypeOfSample() {
		return sampleType;
	}

	public long getNumberOfCases() {
		return numberOfCases;
	}

	public void setTestNumber(int testNumber) {
		this.testNumber = testNumber;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public void setTypeOfSample(String sampleType) {
		this.sampleType = sampleType;
	}

	public void setNumberOfCases(long numberOfCases) {
		this.numberOfCases = numberOfCases;
	}

}
