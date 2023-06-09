package kr.co.infopub.chapter.s190;


import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import kr.co.infopub.chapter.s190.util.EmpUtil;
import kr.co.infopub.chapter.s190.dto.DepConvert;
import kr.co.infopub.chapter.s190.dto.Department;
import kr.co.infopub.chapter.s190.dto.DepartmentDto;
import kr.co.infopub.chapter.s190.dto.EmpConvert;
import kr.co.infopub.chapter.s190.dto.Employee;
import kr.co.infopub.chapter.s190.dto.EmployeeDto;
import kr.co.infopub.chapter.s190.model.EmployeeDAO;

public class DepartmentController {
	
	@FXML
	private TableView<Employee> employeeTable;
	@FXML
	private TableColumn<Employee, Integer> empIdColumn;
	@FXML
	private TableColumn<Employee, String> empNameColumn;
	@FXML
	private TableColumn<Employee, String> empLastNameColumn;
	@FXML
	private TableColumn<Employee, String> empEmailColumn;
	@FXML
	private TableColumn<Employee, String> empPhoneNumberColumn;
	@FXML
	private TableColumn<Employee, Date> empHireDateColumn;
	
	@FXML
	private Button searchEmpsBtn;
	
	@FXML
	private Label lbhello;
	
	
	@FXML
	private TreeView tvEmp;
	
	//For MultiThreading
	//private Executor exec;
	
	EmployeeDAO employeeDAO = new EmployeeDAO();
	//Initializing the controller class.
	//This method is automatically called after the fxml file has been loaded.
	
	@FXML
	private BorderPane SearchTabBorder;
	
	@FXML
	private BorderPane emptvBorder;
	
	@FXML
	private BorderPane UpdateTabBorder;
	
	private final Node rootIcon0 = new ImageView(
			new Image(getClass().getResourceAsStream("image/book0.png")));
	private final Node rootIcon1 = new ImageView(
			new Image(getClass().getResourceAsStream("image/book1.png")));
	
	@FXML
	public void initialize() {
		loadTreeItems();
		
		empIdColumn.setCellValueFactory(cellData -> 
			cellData.getValue().employeeIdProperty().asObject());
		empNameColumn.setCellValueFactory(cellData -> 
			cellData.getValue().firstNameProperty());
		empLastNameColumn.setCellValueFactory(cellData -> 
			cellData.getValue().lastNameProperty());
		empEmailColumn.setCellValueFactory(cellData -> 
			cellData.getValue().emailProperty());
		empPhoneNumberColumn.setCellValueFactory(cellData ->
			cellData.getValue().phoneNumberProperty());
		
		//람다를 사용할 때.
//		empHireDateColumn.setCellValueFactory(cellData -> 
//			cellData.getValue().hireDateProperty());
		empHireDateColumn.setCellValueFactory(new Callback<CellDataFeatures<Employee, Date>, ObservableValue<Date>>(){

			@Override
			public ObservableValue<Date> call(CellDataFeatures<Employee, Date> cellData) {
				return cellData.getValue().hireDateProperty();
			}
		});
		
		employeeTable.setOnMouseClicked(e -> {
			if(employeeTable.getSelectionModel().getSelectedItem()!=null) {
				Employee user = (Employee) employeeTable.getSelectionModel().getSelectedItem();
				//System.out.println(user.getEmployeeId()+" "+user.getFirstName()+" "+user.getLastName());
				showLabel(user.getEmployeeId()+" "+user.getFirstName()+" "+user.getLastName());
			}
		});
	}
	
	public void makeDepTree(TreeItem<String> front, List<Department> dlists) {
		for(Department dep: dlists) {
			TreeItem<String> troots = new TreeItem<String>(EmpUtil.tname(dep));
			front.getChildren().add(troots);
		}
	}
	
	void showLabel(String msg) {
		lbhello.setText(msg);
		//lbhello.setStyle(null);
		String value = 
				"-fx-font-size: 12px;           "
				+ "-fx-font-family: 'Arial Black';             "
				+ "-fx-fill: #818181;                 "
				+ "-fx-effect : innershadow( three-pass-box , "
				+ "rgba(0,50,255,0.7), 6, 0.0, 0, 2 );";
		lbhello.setStyle("\t"+value);
	}
	void showViewError(String msg) {
		lbhello.setText(msg);
		//lbhello.setStyle(null);
		String value = 
				"-fx-font-size: 12px;                  "
				+ "-fx-font-family: 'Arial Black';             "
				+ "-fx-fill: #818181;                    "
				+ "-fx-effect : innershadow( three-pass-box , "
				+ " rgba(255,0,0,0.7), 6, 0.0, 0, 2);";
		lbhello.setStyle("\t"+value);
	}
	@SuppressWarnings("unchecked")
	public void loadTreeItems() {
		ObservableList<Department> dlists = null;
		TreeItem<String> root = new TreeItem<String>("부서별 직원",rootIcon0);
		root.setExpanded(true);
		try {
			//dlists = employeeDAO.findAllDepartments();
			List<DepartmentDto> blist = employeeDAO.findAllDepartments();
			dlists = DepConvert.toObservProFromDto(blist);
			makeDepTree(root,dlists);
			tvEmp.setRoot(root);
			
			tvEmp.getSelectionModel().selectedItemProperty()
			.addListener((observable, oldValue, newValue) -> {
				ObservableList<Employee> empData = null;
				try {
					String val = "";
					if(newValue != null && newValue instanceof TreeItem<?>) {
						val= EmpUtil.dep(((TreeItem<String>)newValue).getValue());
					}
					List<EmployeeDto> emplists = employeeDAO.findEmployeesByDepartName(val);
					empData = EmpConvert.toObservProFromDto(emplists);
					//------------------- 필요 없다면
					String stsf = val + " 부서직원수: ";
					showLabel(stsf + empData.size()+" 명");
					//-------------------
					showToTableEmployees(empData);
					
				}catch(SQLException e) {
					
				}
			});
			
			root.addEventHandler(TreeItem.branchExpandedEvent(), eh->{
				System.out.println("expanded");
				//tvEmp.setRoot(null);
				root.setGraphic(rootIcon0);
				loadTreeItems();
			});
			
			root.addEventHandler(TreeItem.branchCollapsedEvent(), eh->{
				System.out.println("collapsed");
				root.setGraphic(rootIcon1);
			});
		} catch(SQLException e) {
			//System.out.println(" emp tv : "+e);
		}
	}
	//----------------------
	
	//Search all employees
	@FXML
	private void searchEmployees(ActionEvent actionEvent) throws SQLException{
		try {
			//ObservableList<Employee> empData = employeeDAO.findAllEmployees();
			List<EmployeeDto> blist = employeeDAO.findAllEmployees();
			ObservableList<Employee> empData = EmpConvert.toObservProFromDto(blist);
			//Populate Employees on TableView
			showToTableEmployees(empData);
			showLabel("Employees  총 :" + empData.size() + " 명");
		} catch(SQLException e) {
			System.out.println("employeeDAO.findAllEmployees()에서 예외 발생.\n"+e);
			//throw e;
		}
	}
	public void showToTableEmployees(ObservableList<Employee> empData) {
		employeeTable.setItems(empData);
	}
}
