package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import model.BirthDate;
import model.Province;
import model.Student;
import model.StudentMangement;

public class StudentManagementView extends JFrame {

	private static final long serialVersionUID = 1L;
	private StudentMangement list;
	private JTable table;
	private JTextField textField_ID;
	private JTextField textField_Name;
	private JTextField textField_Date;
	private JTextField textField_Sub1;
	private JTextField textField_Sub2;
	private JTextField textField_Sub3;
	private DefaultTableModel model;
	private JCheckBox cb_Male;
	private JCheckBox cb_Female;
	private JButton btn_Add;
	private JButton btn_Delete;
	private JButton btn_SortFromLowestToHighest;
	private JButton btn_SortFromHighestToLowest;
	private ButtonGroup bg_Gender;
	private JTextField textField_StudentID_Search;
	private JButton btn_Search;
	private JComboBox<String> comboBox_Province_Search;
	private JComboBox<String> comboBox_Province;
	private ArrayList<Province> list_Province;
	private Controller listener = new Controller(this);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new StudentManagementView("Student Management");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public StudentManagementView(String name) {
		super(name);
		initialize();
		this.setVisible(true);

	}

	public void saveFile(String path) {
		try {
			this.list.setFilename(path);
			FileOutputStream fos = new FileOutputStream(path);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			for (Student ts : this.list.getList_students()) {
				oos.writeObject(ts);
			}
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveFileOperation() {
		try {
			if (this.list.getFilename().length() > 0) {
				saveFile(this.list.getFilename());
			} else {
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showSaveDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					saveFile(file.getAbsolutePath());
				}
				loadDataTable();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void openFile(File file) {
		ArrayList<Student> ds = new ArrayList<Student>();
		try {
			this.list.setFilename(file.getAbsolutePath());
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Student ts = null;
			while ((ts = (Student) ois.readObject()) != null) {
				ds.add(ts);
			}
			ois.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
		this.list.setList_students(ds);
	}

	public void openFileOperation() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				openFile(file);
			}
			loadDataTable();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void clearForm() {
		this.textField_ID.setText("");
		this.textField_Name.setText("");
		this.textField_Date.setText("");
		this.textField_Sub1.setText("");
		this.textField_Sub2.setText("");
		this.textField_Sub3.setText("");
		this.bg_Gender.clearSelection();
		this.comboBox_Province.setSelectedIndex(1);
	}

	public void removeStudent() {

		try {
			Student student = getStudentForm();
			if (list.hasStudent(student)) {
				int i = JOptionPane.showConfirmDialog(null, "Bạn có muốn xoá học sinh này ?", "",
						JOptionPane.YES_NO_OPTION);
				if (i == JOptionPane.YES_OPTION) {
					this.list.removeFromList(student);
					loadDataTable();
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Không có ID trong danh sách!", "Failed", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void addStudent() {
		try {
			warningEmptyEvent();
			Student student = getStudentForm();
			if (student == null) {
				return;
			} else if (list.hasStudent(student)) {
				int choice = JOptionPane.showConfirmDialog(this, "Bạn muốn ghi đè thông tin ?", "Warning",
						JOptionPane.YES_NO_OPTION);

				if (choice == JOptionPane.YES_OPTION) {
					int cols = table.getColumnCount();
					int selectedRow = table.getSelectedRow();
					for (int i = 0; i < cols; i++) {
						table.setValueAt(student.getStudent_id() + "", selectedRow, i);
						table.setValueAt(student.getStudent_name() + "", selectedRow, i);
						table.setValueAt(student.getProvince().getProvince_name() + "", selectedRow, i);
						table.setValueAt(student.getDate().toString(), selectedRow, i);
						table.setValueAt(student.getStudent_id() + "", selectedRow, i);
						String gender = "";
						if (student.isGender()) {
							gender = "Nam";
						} else {
							gender = "Nữ";
						}
						table.setValueAt(gender, selectedRow, i);
						table.setValueAt(student.getSubject1() + "", selectedRow, i);
						table.setValueAt(student.getSubject2() + "", selectedRow, i);
						table.setValueAt(student.getSubject3() + "", selectedRow, i);

						this.list.update(student);
						clearForm();
					}
				}
			} else {
				this.addStudentToTable(student);
				this.list.addToList(student);
				clearForm();
				System.out.println(list.toString());
			}
			loadDataTable();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Thiếu thông tin!", "Failed", JOptionPane.ERROR_MESSAGE);
		}

	}

	private void warningEmptyEvent() {
		if (textField_ID.getText().isEmpty()) {
			textField_ID.setBorder(new LineBorder(Color.red));
		} else {
			textField_ID.setBorder(new LineBorder(Color.gray));
		}

		if (textField_Name.getText().isEmpty()) {
			textField_Name.setBorder(new LineBorder(Color.red));
		} else {
			textField_Name.setBorder(new LineBorder(Color.gray));
		}

		if (textField_Date.getText().isEmpty()) {
			textField_Date.setBorder(new LineBorder(Color.red));
		} else {
			textField_Date.setBorder(new LineBorder(Color.gray));
		}

		if (textField_Sub1.getText().isEmpty()) {
			textField_Sub1.setBorder(new LineBorder(Color.red));
		} else {
			textField_Sub1.setBorder(new LineBorder(Color.gray));
		}

		if (textField_Sub2.getText().isEmpty()) {
			textField_Sub2.setBorder(new LineBorder(Color.red));
		} else {
			textField_Sub2.setBorder(new LineBorder(Color.gray));
		}

		if (textField_Sub3.getText().isEmpty()) {
			textField_Sub3.setBorder(new LineBorder(Color.red));
		} else {
			textField_Sub3.setBorder(new LineBorder(Color.gray));
		}

	}

	private Student getStudentForm() {
		int ID = Integer.valueOf(this.textField_ID.getText());
		String name = this.textField_Name.getText();
		int province_ID = this.comboBox_Province.getSelectedIndex();
		Province province = Province.getProvinceById(province_ID);
		BirthDate date = new BirthDate(textField_Date.getText().substring(0, 2),
				textField_Date.getText().substring(3, 5), textField_Date.getText().substring(6, 10));
		boolean gender = false;
		if (cb_Male.isSelected()) {
			gender = true;
		} else {
			gender = false;
		}
		Float sub1 = Float.valueOf(this.textField_Sub1.getText());
		Float sub2 = Float.valueOf(this.textField_Sub2.getText());
		Float sub3 = Float.valueOf(this.textField_Sub3.getText());
		Student student = new Student(name, ID, province, date, gender, sub1, sub2, sub3);
		return student;

	}

	private void addStudentToTable(Student student) {
		String ID = String.valueOf(student.getStudent_id());
		String name = student.getStudent_name();
		String date = student.getDate().toString();
		String provincename = student.getProvince().getProvince_name();
		String gender = student.isGender() ? "Nam" : "Nữ";
		String subject1 = student.getSubject1() + "";
		String subject2 = student.getSubject2() + "";
		String subject3 = student.getSubject3() + "";

		String[] data = new String[] { ID, name, provincename, date, gender, subject1, subject2, subject3 };
		model.addRow(data);
		table.setModel(model);
	}

	public void loadDataTable() {
		ArrayList<Student> list_students = list.getList_students();
		while (true) {
			int row = table.getRowCount();
			if (row == 0) {
				break;
			} else {
				try {
					model.removeRow(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		for (Student o : list_students) {
			addStudentToTable(o);
		}
	}

	public void search() {
		try {
			loadDataTable();

			int province_ID = this.comboBox_Province_Search.getSelectedIndex() + 1;
			Province province = Province.getProvinceByID(province_ID);
			String province_name_Search = province.getProvince_name();
			int student_ID_Search = Integer.parseInt(this.textField_StudentID_Search.getText());
			int rowCounts = table.getRowCount();
			Set<Integer> searchdList = new TreeSet<Integer>();

			if (province_ID > 0) {
				for (int i = 0; i < rowCounts; i++) {
					String ID = model.getValueAt(i, 0) + "";
					String provincename = model.getValueAt(i, 2) + "";
					if (!provincename.equals(province_name_Search)) {
						searchdList.add(Integer.valueOf(ID));
					}
				}
			}

			if (student_ID_Search > 0) {
				for (int i = 0; i < rowCounts; i++) {
					String ID = model.getValueAt(i, 0) + "";
					if (!Integer.valueOf(ID).equals(student_ID_Search)) {
						searchdList.add(Integer.valueOf(ID));
					}
				}
			}

			for (Integer oID : searchdList) {
				for (int i = 0; i < rowCounts; i++) {
					String ID = model.getValueAt(i, 0) + "";
					if (oID.equals(Integer.parseInt(ID))) {
						try {
							model.removeRow(i);
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;

					}

				}
			}
		} catch (Exception e) {
			if (textField_StudentID_Search.getText().isBlank()) {
				textField_StudentID_Search.setBorder(new LineBorder(Color.red));
			}
			JOptionPane.showMessageDialog(this, "Điền đầy đủ thông tin!", "Failed", JOptionPane.WARNING_MESSAGE);

		}

	}

	public void sortFromHighestToLowest() {
		try {
			this.list.sortScoreFromHighestToLowest();
			loadDataTable();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "List rỗng", "Failed", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void sortFromLowestToHighest() {
		try {
			this.list.sortScoreFromLowestToHighest();
			loadDataTable();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "List rỗng", "Failed", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void initialize() {
		this.getContentPane().setLayout(null);
		this.setBounds(100, 100, 815, 688);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);

		this.list = new StudentMangement();

		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		JMenu menu_file = new JMenu("File");
		menu_file.setBackground(new Color(255, 128, 255));
		menu_file.setFont(new Font("Segoe UI Black", Font.BOLD, 14));
		menuBar.add(menu_file);

		JMenuItem menu_open = new JMenuItem("Open");
		menu_open.setHorizontalAlignment(SwingConstants.LEFT);
		menu_open.addActionListener(listener);
		menu_open.setActionCommand("OpenFile");
		menu_file.add(menu_open);

		JSeparator separator = new JSeparator();
		menu_file.add(separator);

		JMenuItem menuitem_save = new JMenuItem("Save");
		menuitem_save.setHorizontalAlignment(SwingConstants.LEFT);
		menuitem_save.addActionListener(listener);
		menuitem_save.setActionCommand("SaveFile");
		menu_file.add(menuitem_save);

		JMenuItem menuitem_exit = new JMenuItem("Exit");
		menuitem_exit.setHorizontalAlignment(SwingConstants.LEFT);
		menuitem_exit.setForeground(Color.red);
		menuitem_exit.addActionListener(listener);
		menuitem_exit.setActionCommand("Exit");
		menu_file.add(menuitem_exit);

		JMenu menu_about = new JMenu("About");
		menu_about.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
		menuBar.add(menu_about);

		JMenuItem menuitem_about = new JMenuItem("ABOUT ME");
		menuitem_about.setHorizontalAlignment(SwingConstants.TRAILING);
		menuitem_about.setActionCommand("About");
		menuitem_about.setForeground(Color.blue);
		menuitem_about.addActionListener(listener);
		menu_about.add(menuitem_about);

		JLabel lb_Province_Search = new JLabel("Quê Quán");
		lb_Province_Search.setFont(new Font("Tahoma", Font.BOLD, 16));
		lb_Province_Search.setBounds(10, 11, 83, 25);
		this.getContentPane().add(lb_Province_Search);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(678, 30, -678, 2);
		this.getContentPane().add(separator_1);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 44, 659, -8);
		this.getContentPane().add(separator_2);

		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(0, 57, 833, 2);
		this.getContentPane().add(separator_3);

		comboBox_Province_Search = new JComboBox<String>();
		comboBox_Province_Search.setBackground(new Color(128, 255, 128));
		comboBox_Province_Search.setBounds(103, 11, 170, 35);
		list_Province = Province.getListProvinces();
		for (Province name : list_Province) {
			comboBox_Province_Search.addItem(name.getProvince_name());
		}

		this.getContentPane().add(comboBox_Province_Search);

		JLabel lb_Student_Search = new JLabel("Mã Thí Sinh");
		lb_Student_Search.setFont(new Font("Tahoma", Font.BOLD, 16));
		lb_Student_Search.setBounds(297, 11, 99, 25);
		this.getContentPane().add(lb_Student_Search);

		textField_StudentID_Search = new JTextField();
		textField_StudentID_Search.setBackground(new Color(128, 255, 128));
		textField_StudentID_Search.setBounds(406, 11, 141, 35);
		this.getContentPane().add(textField_StudentID_Search);

		btn_Search = new JButton("Tìm kiếm");
		btn_Search.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_Search.addActionListener(listener);
		btn_Search.setActionCommand("Search");
		btn_Search.setBounds(582, 12, 99, 20);
		this.getContentPane().add(btn_Search);

		JButton btn_CancelSearch = new JButton("Hủy tìm");
		btn_CancelSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_CancelSearch.setBounds(699, 12, 83, 20);
		btn_CancelSearch.setActionCommand("CancelSearch");
		btn_CancelSearch.addActionListener(listener);
		this.getContentPane().add(btn_CancelSearch);

		JLabel lb_StudentList = new JLabel("DANH SÁCH THÍ SINH ");
		lb_StudentList.setFont(new Font("Yu Mincho", Font.PLAIN, 17));
		lb_StudentList.setBounds(10, 62, 220, 35);
		this.getContentPane().add(lb_StudentList);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		scrollPane.setBounds(10, 106, 779, 236);
		this.getContentPane().add(scrollPane);

		table = new JTable();
		table.setRowHeight(20);
		table.setGridColor(Color.blue);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table.getTableHeader().setReorderingAllowed(false);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					textField_ID.setText(table.getValueAt(selectedRow, 0) + "");
					textField_Name.setText(table.getValueAt(selectedRow, 1) + "");
					Province province = Province.getProvinceByName(table.getValueAt(selectedRow, 2).toString());
					comboBox_Province.setSelectedIndex(province.getProvince_id() - 1);
					textField_Date.setText(table.getValueAt(selectedRow, 3) + "");
					String gender = table.getValueAt(selectedRow, 4) + "";
					if (gender.equalsIgnoreCase("Nam")) {
						cb_Male.setSelected(true);
					} else {
						cb_Female.setSelected(true);
					}
					textField_Sub1.setText(table.getValueAt(selectedRow, 5) + "");
					textField_Sub2.setText(table.getValueAt(selectedRow, 6) + "");
					textField_Sub3.setText(table.getValueAt(selectedRow, 7) + "");
				}
			}
		});

		String[] cols = new String[] { "Mã thí sinh", "Họ và tên", "Quê quán", "Ngày sinh", "Giới tính", "Môn 1",
				"Môn 2", "Môn 3" };
		model = new DefaultTableModel(new String[][] {}, cols) {
			private static final long serialVersionUID = -6314950092452154629L;
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		table.setModel(model);

//		  public DefaultTableModel(Object[][] data, Object[] columnNames) {
//		        setDataVector(data, columnNames);
//		   }

		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(180);
		table.getColumnModel().getColumn(3).setPreferredWidth(110);
		table.getColumnModel().getColumn(4).setPreferredWidth(80);
		table.getColumnModel().getColumn(5).setPreferredWidth(60);
		table.getColumnModel().getColumn(6).setPreferredWidth(60);
		table.getColumnModel().getColumn(7).setPreferredWidth(60);
		scrollPane.setViewportView(table);

		JLabel lb_StudentInfor = new JLabel("THÔNG TIN THÍ SINH");
		lb_StudentInfor.setFont(new Font("Yu Mincho", Font.PLAIN, 17));
		lb_StudentInfor.setBounds(10, 345, 200, 44);
		this.getContentPane().add(lb_StudentInfor);

		JLabel lb_StudentID = new JLabel("Mã thí sinh");
		lb_StudentID.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lb_StudentID.setBounds(10, 400, 83, 25);
		this.getContentPane().add(lb_StudentID);

		JLabel lb_Name = new JLabel("Họ và tên");
		lb_Name.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lb_Name.setBounds(10, 450, 83, 36);
		this.getContentPane().add(lb_Name);

		JLabel lb_Province = new JLabel("Quê quán");
		lb_Province.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lb_Province.setBounds(10, 507, 83, 35);
		this.getContentPane().add(lb_Province);

		JLabel lb_Sub1 = new JLabel("Môn 1");
		lb_Sub1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lb_Sub1.setBounds(306, 403, 57, 21);
		this.getContentPane().add(lb_Sub1);

		JLabel lb_Sub2 = new JLabel("Môn 2");
		lb_Sub2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lb_Sub2.setBounds(306, 453, 57, 25);
		this.getContentPane().add(lb_Sub2);

		JLabel lb_Sub3 = new JLabel("Môn 3");
		lb_Sub3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lb_Sub3.setBounds(306, 510, 57, 35);
		this.getContentPane().add(lb_Sub3);

		JLabel lb_Date = new JLabel("Ngày sinh");
		lb_Date.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lb_Date.setBounds(10, 571, 83, 25);
		this.getContentPane().add(lb_Date);

		textField_ID = new JTextField();
		textField_ID.setBounds(103, 401, 162, 33);
		this.getContentPane().add(textField_ID);
		textField_ID.setColumns(10);

		textField_Name = new JTextField();
		textField_Name.setBounds(103, 453, 162, 35);
		this.getContentPane().add(textField_Name);
		textField_Name.setColumns(10);

		textField_Date = new JTextField();
		textField_Date.setBounds(103, 571, 162, 34);
		this.getContentPane().add(textField_Date);
		textField_Date.setColumns(10);

		textField_Sub1 = new JTextField();
		textField_Sub1.setBounds(373, 401, 86, 33);
		this.getContentPane().add(textField_Sub1);
		textField_Sub1.setColumns(10);

		JLabel lb_Gender = new JLabel("Giới tính");
		lb_Gender.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lb_Gender.setBounds(306, 570, 67, 35);
		this.getContentPane().add(lb_Gender);

		cb_Male = new JCheckBox("Nam");
		cb_Male.setBounds(380, 576, 57, 23);
		this.getContentPane().add(cb_Male);

		cb_Female = new JCheckBox("Nữ");
		cb_Female.setBounds(441, 576, 57, 23);
		this.getContentPane().add(cb_Female);

		bg_Gender = new ButtonGroup();
		bg_Gender.add(cb_Male);
		bg_Gender.add(cb_Female);

		textField_Sub2 = new JTextField();
		textField_Sub2.setColumns(10);
		textField_Sub2.setBounds(373, 453, 86, 31);
		this.getContentPane().add(textField_Sub2);

		textField_Sub3 = new JTextField();
		textField_Sub3.setColumns(10);
		textField_Sub3.setBounds(373, 512, 86, 31);
		this.getContentPane().add(textField_Sub3);

		btn_Add = new JButton("THÊM");
		btn_Add.setBackground(new Color(0, 128, 255));
		btn_Add.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_Add.setBounds(540, 421, 108, 23);
		btn_Add.addActionListener(listener);
		btn_Add.setActionCommand("Add");
		this.getContentPane().add(btn_Add);

		comboBox_Province = new JComboBox<String>();
		comboBox_Province.setBounds(103, 512, 162, 35);
		for (Province name : list_Province) {
			comboBox_Province.addItem(name.getProvince_name());
		}
		this.getContentPane().add(comboBox_Province);

		btn_Delete = new JButton("XOÁ BỎ");
		btn_Delete.setBackground(new Color(0, 128, 255));
		btn_Delete.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_Delete.setBounds(678, 421, 108, 23);
		btn_Delete.setActionCommand("Delete");
		btn_Delete.addActionListener(listener);
		this.getContentPane().add(btn_Delete);

		btn_SortFromLowestToHighest = new JButton("ID ↑");
		btn_SortFromLowestToHighest.addActionListener(listener);
		btn_SortFromLowestToHighest.setBackground(new Color(0, 128, 255));
		btn_SortFromLowestToHighest.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_SortFromLowestToHighest.setBounds(540, 484, 108, 23);
		btn_SortFromLowestToHighest.addActionListener(listener);
		btn_SortFromLowestToHighest.setActionCommand("SortFromLToH");
		this.getContentPane().add(btn_SortFromLowestToHighest);

		btn_SortFromHighestToLowest = new JButton("ID ↓");
		btn_SortFromHighestToLowest.setBackground(new Color(0, 128, 255));
		btn_SortFromHighestToLowest.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_SortFromHighestToLowest.setBounds(681, 484, 108, 23);
		btn_SortFromHighestToLowest.addActionListener(listener);
		btn_SortFromHighestToLowest.setActionCommand("SortFromHToL");
		this.getContentPane().add(btn_SortFromHighestToLowest);

		JButton btn_Cancel = new JButton("HUỶ ĐIỀN");
		btn_Cancel.setBackground(new Color(0, 128, 255));
		btn_Cancel.addActionListener(listener);
		btn_Cancel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_Cancel.setActionCommand("Cancel");
		btn_Cancel.addActionListener(listener);
		btn_Cancel.setBounds(678, 556, 108, 23);
		this.getContentPane().add(btn_Cancel);

		JButton btn_ClerAll = new JButton("XÓA LIST");
		btn_ClerAll.setBackground(new Color(0, 128, 255));
		btn_ClerAll.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btn_ClerAll.setActionCommand("ClearAll");
		btn_ClerAll.setBounds(544, 556, 104, 23);
		btn_ClerAll.addActionListener(listener);
		getContentPane().add(btn_ClerAll);
	}

}
