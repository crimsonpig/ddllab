package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;

@Entity
public class Task extends Model {
	@Id
	public Long id;
	public String title;
	public boolean done = false;
	public Date dueDate;
	//A task is assigned to only one person, but that person can have many tasks. 
	@ManyToOne
	public User assignedTo;
	public String folder;
	//A task is part of only one project, but a project can have many tasks.
	@ManyToOne
	public Project project;

	public static Model.Finder<Long,Task> find = new Model.Finder<Long,Task>(
		Long.class, Task.class
	);

	public static List<Task> findTodoInvolving(String user){
		return find.fetch("project").where()
			.eq("done",false)
			.eq("project.members.email",user)
			.findList();
	}

	public static Task create(Task task, Long project, String folder){
		task.project = Project.find.ref(project);
		task.folder = folder;
		task.save();
		return task;
	}
}
