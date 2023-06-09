package kr.co.infopub.chapter.s190;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import kr.co.infopub.chapter.s190.dto.EmpConvert;
import kr.co.infopub.chapter.s190.dto.Employee;
import kr.co.infopub.chapter.s190.dto.EmployeeDto;
import kr.co.infopub.chapter.s190.model.EmployeeDAO;
import kr.co.infopub.chapter.s190.util.EmpUtil;

public class ManagerFxController {

	@FXML
	private Button searchEmpsBtn;
	@FXML
	private Label lbhello;
	
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
	private TreeView tvEmp;
	
	@FXML
	private Button latesEmpsBtn;
	
	private final Node rootIcon2 = new ImageView(
			new Image(getClass().getResourceAsStream("image/book2.png")));
	private final Node rootIcon3 = new ImageView(
			new Image(getClass().getResourceAsStream("image/book3.png")));
	private Executor exec;
	
	EmployeeDAO employeeDAO = new EmployeeDAO();
	
	
	@FXML
	private void initialize() {
		
		loadTreeItems();
		
		exec = Executors.newCachedThreadPool((runnable)->{
			Thread t = new Thread(runnable);
			t.setDaemon(true);
			return t;
		});
		
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
		
		empHireDateColumn.setCellValueFactory(new Callback<CellDataFeatures<Employee, Date>, ObservableValue<Date>>(){

			@Override
			public ObservableValue<Date> call(CellDataFeatures<Employee, Date> cellData) {
				return cellData.getValue().hireDateProperty();
			}
			
		});
		
		employeeTable.setOnMouseClicked(e -> {
			if(employeeTable.getSelectionModel().getSelectedItem()!=null) {
				Employee user = (Employee) employeeTable.getSelectionModel().getSelectedItem();
				System.out.println(user.getEmployeeId()+" "+ user.getFirstName()+" "+user.getLastName());
				lbhello.setText(user.getEmployeeId()+" "+user.getFirstName()+" "+user.getLastName());
			}
		});
		
		
	}
	
	@FXML
	private void onLatesEmpsBtn(ActionEvent actionEvent) throws SQLException{
		loadTreeItems();
	}
	
	@SuppressWarnings("unchecked")
	public void loadTreeItems() {
		TreeItem<String> root = new TreeItem<String>("매니저와 직원",rootIcon2);
		root.setExpanded(true);
		try {
			List<EmployeeDto> bdlists = employeeDAO.findTreeManagerInEmployee();
			List<Employee> dlists = EmpConvert.toObservProFromDto(bdlists);
			
			int max = 0;
			max = employeeDAO.getTreeMaxLevel();
			
			System.out.println("111111111111111111111111--------------------------------"+max);
			makeEmpTree(root, dlists, "",1,max);
			
			tvEmp.setRoot(root);
			tvEmp.getSelectionModel().selectedItemProperty().addListener(
					(observable, oldValue, newValue)->{
						ObservableList<Employee> empData = null;
//						System.out.println("Selected Department_id : " + ((TreeItem<String>)newValue).getValue());
						try {
							String val = "";
							if(newValue!=null && newValue instanceof TreeItem<?>) {
								val = EmpUtil.bfstr(((TreeItem<String>)newValue).getValue());
							}
							System.out.println("Selected Manager_id : "+val);
							List<EmployeeDto> elists = employeeDAO.findEmployeesByEmpId(val); //100
							empData = EmpConvert.toObservProFromDto(elists);
							//----단순 인원을 구하기 위한것.
							String stsf = val + "와(과) 직원:";
							showLabel(stsf+empData.size()+" 명");
							//---------------------------
							showToTableEmployees(empData);
						}catch(SQLException e) {
						}
			});
			
			root.addEventHandler(TreeItem.branchExpandedEvent(), eh -> {
				System.out.println("expanded-------------------------------->");
				root.setGraphic(rootIcon2);
				tvEmp.refresh();
				
				//loadTreeItems2();   //무한루프
			});
			root.addEventHandler(TreeItem.childrenModificationEvent(), eh -> {
				System.out.println("------------childrenModificationEvent-------------------->");
			});
			root.addEventHandler(TreeItem.branchCollapsedEvent(), eh -> {
				System.out.println("collapsed=================================>");
				root.setGraphic(rootIcon3);
			});
		}catch(SQLException e) {
			System.out.println(" emp tv :" + e);
		}
		
		
		empIdColumn.setCellValueFactory(cellData -> cellData.getValue().employeeIdProperty().asObject());
		empNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		empLastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
		empEmailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
		empPhoneNumberColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());;
		empHireDateColumn.setCellValueFactory(cellData -> cellData.getValue().hireDateProperty());;
		
		employeeTable.setOnMouseClicked(e -> {
			if(employeeTable.getSelectionModel().getSelectedItem()!=null) {
				Employee user = (Employee) employeeTable.getSelectionModel().getSelectedItem();
				System.out.println(user.getEmployeeId()+" "+user.getFirstName()+" "+user.getLastName());
				showLabel(user.getEmployeeId()+" "+user.getFirstName()+" "+user.getLastName());
			}
		});
	}
	
	void showLabel(String msg) {
		lbhello.setText(msg);
		//lbhello.setStyle(null);
		String value =
				"-fx-font-size: 12px;              "
				+ "-fx-font-family: 'Arial Black';               "
				+ "-fx-fill: #818181;                    "
				+ "-fx-effect: innershadow( three-pass-box, "
				+ "rgba(0,50,255,0.7), 6, 0.0, 0, 2);";
		lbhello.setStyle("\t"+value);
	}
	
	public void makeEmpTree(TreeItem<String> front, List<Employee> dlists, String key, int index, int max) {
		if(index>max) {return;}   //max를 넘으면 끝
		for(Employee emp : dlists) {  //개수가 있으니 끝날 것이다.
			if(EmpUtil.level(emp)!=index) { //원하는 레벨만 찾음
				continue;
			}else if(index > 1 && EmpUtil.level(emp)==index){    //2부터는 1의 키값과 비교
				if(EmpUtil.level(emp,index-1).equals(key)) {
					TreeItem<String> aa = new TreeItem<String>(EmpUtil.tname(emp,index));
					
					front.getChildren().add(aa);
					makeEmpTree(aa,dlists,EmpUtil.level(emp,index),index+1,max);
				}else {
					continue;
				}
			}else if(EmpUtil.level(emp)==1) {  //1의 키값이 없기 때문
				TreeItem<String> aa = new TreeItem<String>(EmpUtil.tname(emp, 1));
				front.getChildren().add(aa);
				makeEmpTree(aa, dlists, EmpUtil.level(emp, 1),2,max);
			}
		}
	}
	
	@FXML
	private void showToTableEmployees(ObservableList<Employee> empData) {
		employeeTable.setItems(empData);
	}
	
	@FXML
	private void searchEmployees(ActionEvent actionevent) throws SQLException{
		try {
			List<EmployeeDto> elists = employeeDAO.findAllEmployees();
			ObservableList<Employee> empData = EmpConvert.toObservProFromDto(elists);
			
			showToTableEmployees(empData);
			showLabel("인원 " + empData.size()+"명");
		}catch(SQLException e) {
			System.out.println("Error occured while getting employees information from DB.\n" + e);
			throw e;
		}
	}
}
