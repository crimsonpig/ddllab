package models;
import play.data.validation.Constraints.*;
public class EnterResultsPOJO {

	private long caseTestPK;
	
	@MaxLength(8)
	private String results;
	@MaxLength(800)
	private String actualComment;
	@MaxLength(800)
	private String informationalComment;
	
	public EnterResultsPOJO() {
	
	}

	public long getCaseTestPK() {
		return caseTestPK;
	}

	public String getResults() {
		return results;
	}

	public String getActualComment() {
		return actualComment;
	}

	public String getInformationalComment() {
		return informationalComment;
	}

	public void setCaseTestPK(long caseTestPK) {
		this.caseTestPK = caseTestPK;
	}

	public void setResults(String results) {
		this.results = results;
	}

	public void setActualComment(String actualComment) {
		this.actualComment = actualComment;
	}

	public void setInformationalComment(String informationalComment) {
		this.informationalComment = informationalComment;
	}

	
	
}
