package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.StudentManagementView;

public class Controller implements ActionListener {
	private StudentManagementView view;

	public Controller(StudentManagementView view) {
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String event = e.getActionCommand();
		switch (event) {
		case "Add":
			this.view.addStudent();
			break;
//		case "Save":
//			this.view.addStudent();
//			break;
		case "Delete":
			this.view.removeStudent();
			break;
//		case "Update":
//			this.view.updateStudent();
//			break;
//		case "Show":
//			this.view.showStudentInformationSeledted();
//			break;
		case "Search":
			this.view.search();
			break;
		case "CancelSearch":
			this.view.loadDataTable();
			break;
//		case "Cancel":
//			this.view.clearForm();
//			break;
//		case "About":
//			this.view.about();
//			break;
//		case "Exit":
//			this.view.exit();
//			break;
		case "SortFromHToL":
			this.view.sortFromHighestToLowest();
			break;
		case "SortFromLToH":
			this.view.sortFromLowestToHighest();
			break;
		case "SaveFile":
			this.view.saveFileOperation();
			break;
		case "OpenFile":
			this.view.openFileOperation();
			break;
//		case "ClearAll":
//			this.view.clearAll();
//			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + event);
		}
	}

}
