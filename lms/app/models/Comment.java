package models;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the COMMENTS database table.
 * 
 */
@Entity
@Table(name="COMMENTS")
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="comment_code", unique=true, nullable=false)
	private int commentCode;

	@Column(name="comment_text", nullable=false, length=800)
	private String commentText;

	//bi-directional many-to-one association to CaseEntityObject
	@OneToMany(mappedBy="caseNote")
	private List<CaseEntityObject> cases;

	//bi-directional many-to-one association to Test
	@ManyToOne
	@JoinColumn(name="test_number")
	private TestEntityObject associatedTest;

	//bi-directional many-to-one association to Test
	@OneToMany(mappedBy="defaultComment")
	private List<TestEntityObject> testsUsingThisAsDefault;

	public Comment() {
	}

	public int getCommentCode() {
		return this.commentCode;
	}

	public void setCommentCode(int commentCode) {
		this.commentCode = commentCode;
	}

	public String getCommentText() {
		return this.commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public List<CaseEntityObject> getCases() {
		return this.cases;
	}

	public void setCases(List<CaseEntityObject> cases) {
		this.cases = cases;
	}

	public TestEntityObject getAssociatedTest() {
		return this.associatedTest;
	}

	public void setAssociatedTest(TestEntityObject associatedTest) {
		this.associatedTest = associatedTest;
	}

	public List<TestEntityObject> getTestsUsingThisAsDefault() {
		return this.testsUsingThisAsDefault;
	}

	public void setTestsUsingThisAsDefault(List<TestEntityObject> testsUsingThisAsDefault) {
		this.testsUsingThisAsDefault = testsUsingThisAsDefault;
	}


}
