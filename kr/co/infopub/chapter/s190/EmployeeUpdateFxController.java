package kr.co.infopub.chapter.s190;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import kr.co.infopub.chapter.s190.dto.DepConvert;
import kr.co.infopub.chapter.s190.dto.Department;
import kr.co.infopub.chapter.s190.dto.DepartmentDto;
import kr.co.infopub.chapter.s190.dto.EmpConvert;
import kr.co.infopub.chapter.s190.dto.Employee;
import kr.co.infopub.chapter.s190.dto.EmployeeDto;
import kr.co.infopub.chapter.s190.model.EmployeeDAO;
import kr.co.infopub.chapter.s190.util.PTS;
/**HR 관리자 프로그램의 update를 실행하는 컨트롤러입니다.
 * oracle DB로 구현을 하였으며, TRIGGER를 사용하지 않아 제약사항때문에 일부 항목은 업데이트 가능하지 않습니다.
 * oracle XE가 제공하는 기본 테이블들은 차후에도 계속 사용해야 하므로 나중에 새로 테이블을 만들고 트리거를 이용하여 전체를 
 * 수정 가능한 관리자 프로그램을 만들 예정입니다.
 * @author 정재은
 * 새로 구현할 기능 : Trigger를 사용해 부모테이블부터 고치고 자식 테이블 수정/삭제 가능하게 하기
 * 오류 : datepicker 편집 불가능하게 하기
 */
public class EmployeeUpdateFxController {
	    @FXML
	    private TextField tfphone;

	    @FXML
	    private TextField tfdepartid;

	    @FXML
	    private AnchorPane bottomsplit;

	    @FXML
	    private Label lbhiredate;

	    @FXML
	    private TextField tffirstname;

	    @FXML
	    private Label lbjobid;

	    @FXML
	    private TextField tflastname;

	    @FXML
	    private TextField tfemail;

	    @FXML
	    private TextField tfjobid;

	    @FXML
	    private Label lbcommis;

	    @FXML
	    private Label lbmanagerid;

	    @FXML
	    private Label lbphone;



	    @FXML
	    private TextField tfempid;

	    @FXML
	    private AnchorPane topslplit;

	    @FXML
	    private Label lbfirstname;

	    @FXML
	    private SplitPane leftsplit;

	    @FXML
	    private Label lbdepartid;

	    @FXML
	    private Label lbsalary;

	    @FXML
	    private Label lblastname;

	    @FXML
	    private TextField tfcommis;

	    @FXML
	    private DatePicker tfhiredate;

	    @FXML
	    private TextField tfmanagerid;

	    @FXML
	    private Label lbempid;

	    @FXML
	    private TableView<Employee> employeeTable;
	    @FXML
	    private TableColumn<Employee, Integer>  empIdColumn;
	    @FXML
	    private TableColumn<Employee, String>  empNameColumn;
	    @FXML
	    private TableColumn<Employee, String> empLastNameColumn;
	    @FXML
	    private TableColumn<Employee, String> empEmailColumn;
	    @FXML
	    private TableColumn<Employee, String> empPhoneNumberColumn;
	    @FXML
	    private TableColumn<Employee, Date> empHireDateColumn;
	    
	    
	    @FXML
	    private TextField tfsalary;

	    @FXML
	    private Button btnAllEmployee;

	    @FXML
	    private Label lbemail;
	    
	    @FXML
	    private Button btnAddEmployee;
	  
	    
	    private Executor exec;
	    
	    EmployeeDAO employeeDAO=new EmployeeDAO();
	    
	    //년월일로 보이기
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy. MM. dd");
	    
	    @FXML
	    private Label lbjobid2;
	    @FXML
	    private Label lbdepartid2;
	    
	    @FXML
	    private TextField tfupdatemanager;
	    
	    @FXML
	    private ComboBox<String> cbJobid;

	    @FXML
	    private ComboBox<String> cbdepartid;
	    
	    
	    @FXML
	    private Button btnAddAfEmployee;
	    
	    @FXML
	    private TableView<Employee> tvSearchManager;
	    @FXML
	    private TableColumn<Employee, Integer>  empIdColumn3;
	    @FXML
	    private TableColumn<Employee, String>  empNameColumn3;
	    @FXML
	    private TableColumn<Employee, String> empLastNameColumn3;
	    @FXML
	    private TableColumn<Employee, String> empEmailColumn3;
	    
	    @FXML
	    private Button btnSearchManager  ;
	    
	    @FXML
	    private TextField tfsearchmanager  ;
	    
	    
	    
	    
	    private void clear(){
//	    	tfempid.setText("");
//			tffirstname.setText("");
//			tflastname.setText("");
//			tfemail.setText("");
//			tfhiredate.getEditor().setText("");
//			tfphone.setText("");
//			tfcommis.setText("");
//			tfsalary.setText("");
//			tfdepartid.setText("");
//			tfjobid.setText("");
//			tfmanagerid.setText("");
//			tfsearchmanager.setText("");
	    }
	    //정보 수정/삭제 준비 버튼 누르기 전까지 수정이 가능함, 그러나 입력을 해서 새로운 인스턴스를 생성하진 않음 
	    private void edit(boolean b){ 
	    	tfempid.setEditable(false);
			tffirstname.setEditable(b);
			tflastname.setEditable(b);
			tfemail.setEditable(b);
			tfhiredate.setEditable(false);
			tfphone.setEditable(b);
			tfcommis.setEditable(b);
			tfsalary.setEditable(b);
			tfdepartid.setEditable(b);
			tfjobid.setEditable(b);
			tfmanagerid.setEditable(b);
	    }
	    //무결성 제약조건으로 인해 사용할 수 없는 텍스트필드는 확인용으로만 사용
	    private void edit2(boolean b){
	    	tfempid.setEditable(false);
			tffirstname.setEditable(b);
			tflastname.setEditable(b);
			tfemail.setEditable(b);
			tfhiredate.getEditor().setEditable(false);
			tfphone.setEditable(b);
			tfcommis.setEditable(b);
			tfsalary.setEditable(b);
			
			tfsearchmanager.setEditable(b);
			tfdepartid.setEditable(false);
			tfjobid.setEditable(false);
			tfmanagerid.setEditable(false);
			if(b){
				tffirstname.setStyle("-fx-background-color: #0000ff");
				tflastname.setStyle("-fx-background-color: #0000ff");
				tfemail.setStyle("-fx-background-color: #0000ff");
				tfhiredate.setStyle("-fx-background-color: #cccccc");
				tfphone.setStyle("-fx-background-color: #0000ff");
				tfcommis.setStyle("-fx-background-color: #cccccc");
				tfsalary.setStyle("-fx-background-color: #cccccc");
				tfdepartid.setStyle("-fx-background-color: #cccccc");
				tfjobid.setStyle("-fx-background-color: #cccccc");
				tfmanagerid.setStyle("-fx-background-color: #cccccc");
			}else{
				tffirstname.setStyle("-fx-background-color: #ffffff");
				tflastname.setStyle("-fx-background-color: #ffffff");
				tfemail.setStyle("-fx-background-color: #ffffff");
				tfhiredate.setStyle("-fx-background-color: #ffffff");
				tfphone.setStyle("-fx-background-color: #ffffff");
				tfcommis.setStyle("-fx-background-color: #ffffff");
				tfsalary.setStyle("-fx-background-color: #ffffff");
				tfdepartid.setStyle("-fx-background-color: #ffffff");
				tfjobid.setStyle("-fx-background-color: #ffffff");
				tfmanagerid.setStyle("-fx-background-color: #ffffff");
			}
			
	    }

	    @FXML
	    void initialize() {
	    	 exec = Executors.newCachedThreadPool((runnable) -> {
	             Thread t = new Thread (runnable);
	             t.setDaemon(true);
	             return t;
	         });
	         empIdColumn.setCellValueFactory(cellData -> cellData.getValue().employeeIdProperty().asObject());
	         empNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
	         empLastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
	         empEmailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
	         empPhoneNumberColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
	         empHireDateColumn.setCellValueFactory(cellData -> cellData.getValue().hireDateProperty());

	         empIdColumn3.setCellValueFactory(cellData -> cellData.getValue().employeeIdProperty().asObject());
	         empNameColumn3.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
	         empLastNameColumn3.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
	         empEmailColumn3.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
	         
	         
	         employeeTable.setOnMouseClicked(e ->{
	         	if(employeeTable.getSelectionModel().getSelectedItem()!=null ){
	         		 edit(false);
	         		 edit2(true);
			    	 clear();
			    	 //-----
			    	 btnAddAfEmployee.setVisible(true);
			    	 
	         		Employee user = (Employee)employeeTable.getSelectionModel().getSelectedItem();
	         		try {
	         			//DB에서 찾아서
	         			EmployeeDto edto=employeeDAO.findEmployeeById(user.getEmployeeId()+"");
						Employee emp=EmpConvert.toPro(edto);
						//현재 정보를 보인다.
						tfempid.setText(emp.getEmployeeId()+"");
						tffirstname.setText(emp.getFirstName());
						tflastname.setText(emp.getLastName());
						tfemail.setText(emp.getEmail());
						//달력으로 바꿈
						tfhiredate.getEditor().setText(sdf.format((java.util.Date)(emp.getHireDate())));
						tfphone.setText(emp.getPhoneNumber());
						tfcommis.setText(emp.getCommissionPct()+"");
						tfsalary.setText(emp.getSalary()+"");
						tfdepartid.setText(emp.getDepartmentId()+"");
						tfjobid.setText(emp.getJobId()+"");
						tfmanagerid.setText(emp.getManagerId()+"");
						System.out.println("------->"+emp.getDepartmentId()+"");
					} catch (SQLException e1) {
						System.out.println("DB에서 직원 상세정보를 가져오지 못했습니다.");
					}
	             	//System.out.println(user.getEmployeeId()+"  "+user.getFirstName()+" "+user.getLastName());
	             	//lbhello.setText(user.getEmployeeId()+"  "+user.getFirstName()+" "+user.getLastName());
	         	}
	           }
	         );
	         btnAddAfEmployee.setVisible(false);
	         tvSearchManager.setOnMouseClicked(e ->{
		         	if(tvSearchManager.getSelectionModel().getSelectedItem()!=null ){
		         		Employee user = (Employee)tvSearchManager.getSelectionModel().getSelectedItem();
		         		//tfmanagerid.setText(user.getFirstName()+" "+user.getLastName()+"("+user.getEmployeeId()+")");
		         		System.out.println(user.getFirstName() + " " + user.getLastName());
		         	}
		     });
	    }
	    
	    @FXML
	    void actionAllEmployee(ActionEvent event) {
	    	 try {
	    		 
	    		 String updatemanager=tfupdatemanager.getText();
	    		 List<EmployeeDto> uempdto= employeeDAO.findManagersByName(updatemanager);
	 	    	 ObservableList<Employee> updatemanagerList= EmpConvert.toObservProFromDto(uempdto);
	 	    	 employeeTable.setItems(updatemanagerList);
	 	    	 tfupdatemanager.setText("");
	    		 		    	 
	         } catch (SQLException e){
	             System.out.println("Error occurred " + e);
	         }
	    	 btnAddAfEmployee.setVisible(false);
	    	 
	    }
	    @FXML
	    private void showEmpoyeeTable (ObservableList<Employee> empData)  {
	    	employeeTable.setItems(empData);
	    }
	    //버튼을 클릭하여 직원추가 준비 
	    @FXML
	    void actionReadyAddEmployee(ActionEvent event) {
	    	clear();
	    	edit(false);
	    	edit2(true);
	    	cbJobid.setItems(null);
	    	cbdepartid.setItems(null);
	    	leftsplit.setDividerPositions(0.6);
	    	employeeTable.setItems(null);
	    	
	    	 try {
	    		    //모든 잡을 가져와 잡콤보에 넣기----------------
	    		    List<String> sjob=employeeDAO.findAllJobs();
			   		ObservableList<String> jobs=EmpConvert.strList(sjob);
			        shoeJobsTable(jobs);
			        //-----------------------------------
			        //모든 부서를 가져와 콤보에 넣기-----------------
			        ObservableList<String> dpnames = FXCollections.observableArrayList();
			        List<DepartmentDto> ndlist=employeeDAO.findAllDepartments2 ();
			        ObservableList<Department> dlists = DepConvert.toObservProFromDto(ndlist);
			        for (Department dd:dlists) {
			        	dpnames.add(String.format("%s(%d)", dd.getDepartment_name(),dd.getDepartment_id()));
					}
	    			cbdepartid.setItems(dpnames);
	    	        //--------------------------------------
		        } catch (SQLException e){
		            System.out.println("Error occurred actionReadyAddEmployee " + e);
		        }
	    	 btnAddAfEmployee.setVisible(true);
	    }
        //잡콤보를 선택하면 잡아이디를 tf에 넣기
	    @FXML
	    void actionJobClicked(ActionEvent event) {
	    	if(cbJobid.getSelectionModel().getSelectedItem()!=null){
	    		String coms =  cbJobid.getSelectionModel().getSelectedItem().toString();    
		    	 edit2(true);
		    	 System.out.println("잡아이디를 선택==========>"+coms);
	    	}
	    }
	    //depart(59) ==> 59
	    private String depid(String msg){
	    	String ss="";
	    	if(msg.indexOf("(")!=-1 && msg.indexOf(")")!=-1 ){
	    		ss=msg.substring(msg.indexOf("(")+1);
	    		ss=ss.substring(0,ss.indexOf(")"));
	    	}else{
	    		ss=msg.trim();
	    	}
	    	return ss.trim();
	    }
	  //depart(59) ==> depart
	    private String dep(String msg){
	    	String ss="";
	    	if(msg.indexOf("(")!=-1){
	    		ss=msg.substring(0, msg.indexOf("("));
	    	}else{
	    		ss=msg.trim();
	    	}
	    	return ss.trim();
	    }
	    //부서콤보에서 부서명으로 관리자 찾기
	    @FXML
	    void actionDepartClicked(ActionEvent event) {
	    	if(cbdepartid.getSelectionModel().getSelectedItem()!=null){
	    		String departid =  cbdepartid.getSelectionModel().getSelectedItem().toString();    
	    		edit2(true);
		    	 System.out.println("부서명(부서아이디)================>"+departid);
		    	 
		    	 try {
		    		 ObservableList<String> dpnames = FXCollections.observableArrayList();
		    		//부서명을 이용하여 관리자 정보를 찾음-------------
		    		 List<EmployeeDto> edtos=employeeDAO.findEmployeesByDepartName(dep(departid));//depart(59) ==> depart
					 ObservableList<Employee> employeess= EmpConvert.toObservProFromDto(edtos);
			        for (Employee dd:employeess) {
			        	dpnames.add(String.format("%s %s[%s](%d)", dd.getFirstName(),dd.getLastName(), dd.getEmail(),dd.getEmployeeId()));
					}
			        //관리자 콤보에 관리자 아이디를 넣기
	    			//cbmanager.setItems(dpnames);
	    			//-------------------------------------
				} catch (SQLException e) {
					System.out.println("actionDepartClicked : "+e);
				}
	    	}
	    }
	    
	    void shoeJobsTable(ObservableList<String>jobs){
	    	if(cbJobid!=null){
	    		cbJobid.setItems(jobs);
	    	}
	    }
	    //관리자 콤보를 선택하면 관리자 정보를 tf에 넣기
//	    @FXML
//	    void actionManagerClicked(ActionEvent event) {
//	    	if(cbmanager.getSelectionModel().getSelectedItem()!=null){
//	    		String manager =  cbmanager.getSelectionModel().getSelectedItem().toString();    
//		    	 tfmanagerid.setText(manager);
//		    	 System.out.println("========================>"+manager);
//	    	}
//	    }
	    
	    public void handleHelp() {
	        Alert alert = new Alert (Alert.AlertType.INFORMATION);
	        alert.setTitle("Employees 테이블 필드 수정");
	        alert.setHeaderText("필수요소확인");
	        alert.setContentText("파란색으로 된 요소만 수정할 수 있습니다.");
	        alert.show();
	    }
	    
	    
	    //emp 수정
	    @FXML
	    void actionUpdateEmployee(ActionEvent event) {
	    	Alert alert = new Alert(AlertType.CONFIRMATION);
	    	alert.setTitle("Confirmation Dialog");
	    	alert.setHeaderText("직원의 정보를 수정하시겠습니까?");
	    	alert.setContentText("정말 수정하시겠습니까?");

	    	Optional<ButtonType> result = alert.showAndWait();
	    	if (result.get() != ButtonType.OK){
	    	   return;  //작업 끝냄
	    	} 
	    	String empId=tfempid.getText();
	    	String empfn=tffirstname.getText();
	    	String empln=tflastname.getText();
	    	String empmail=tfemail.getText();
	    	String emphire=tfhiredate.getEditor().getText();
	    	//sqldate로 변환준비
	    	String empphone=tfphone.getText();
	    	//-----------------------------------------------
	    	String emppct=tfcommis.getText();
	    	String empsal=tfsalary.getText();
	    	String empdepid=tfdepartid.getText(); 
	    	String empjobid=tfjobid.getText();   
	    	String empmanid=tfmanagerid.getText();
	    	System.out.println(String.format("%s,%s,%s,%s,%s", empfn,empln,empmail,emphire,empphone));
	    	System.out.println(String.format("%s,%s,%s,%s,%s", emppct,empsal,depid(empdepid),empjobid,depid(empmanid)));
	    	
	    	if(empId.equals("") || empfn.equals("") || empln.equals("")|| empmail.equals("")||
	    			emphire.equals("")|| empjobid.equals("")|| empmanid.equals("")){
	    		handleHelp();
	    		return;
	    	}

	    	Date dhiredate= PTS.toDaeS(emphire);
	    	
	    	System.out.println("emphire-------------------"+emphire);
	    	System.out.println("dhiredate-------------------"+dhiredate);
	    	Employee emp=new Employee();
	    	if(!empId.equals("")){
	    		emp.setEmployeeId(Integer.parseInt(empId));
	    	}
	    	emp.setFirstName(empfn);
	    	emp.setLastName(empln);
	    	emp.setEmail(empmail);
	    	emp.setHireDate(dhiredate);
	    	emp.setPhoneNumber(empphone);
	    	if(!emppct.equals("")){
	    		emp.setCommissionPct(Double.parseDouble(emppct));
	    	}
	    	if(!empsal.equals("")){
	    		emp.setSalary(Double.parseDouble(empsal));
	    	}
	    	if(!empdepid.equals("")){
	    		emp.setDepartmentId(Integer.parseInt(depid(empdepid)));
	    	}
	    	emp.setJobId(empjobid);
	    	if(!empmanid.equals("")){
	    		emp.setManagerId(Integer.parseInt(depid(empmanid)));
	    	}
	    	EmployeeDto edot=EmpConvert.toDto(emp);
	    	try {
	    		System.out.println(" getFirstName------------------------------"+emp.getFirstName());
	    		System.out.println(" getLastName------------------------------"+emp.getLastName());
				boolean isS=employeeDAO.updateEmployee(edot);
				if(isS){
					 System.out.println(" updateEmployee -------------------------------"+empId);
		             ObservableList<Employee> empData=FXCollections.observableArrayList();
		             EmployeeDto eedot=employeeDAO.findEmployeeById(""+empId);  
		             Employee empDat = EmpConvert.toPro(eedot);
		             System.out.println(" actionUpdateEmployee 등록된 emp 찾기-------------------------------"+empId);
		             if(empDat!=null){
		            	 empData.add(empDat);
			             showEmpoyeeTable(empData);
		             }
		 	    	 edit(false);
		 	    	 edit2(false);
			    	 clear();
				}else{
					 System.out.println(" updateEmployee 실패------------------------------"+empId);
				}
			} catch (SQLException e) {
				System.out.println(" actionUpdateEmployee==>"+e);
			}
	    	 btnAddAfEmployee.setVisible(false);
	    }
		
	    @FXML
	    void actionSearchManager(ActionEvent event) {
	    	
	    	String manid=tfsearchmanager.getText();
	    	ObservableList<Employee> managers=null;
			try {
				List<EmployeeDto> edotmana=employeeDAO.findManagersByName(manid);
				managers = EmpConvert.toObservProFromDto(edotmana);
				tvSearchManager.setItems(managers);
		    	tfsearchmanager.setText("");
			} catch (SQLException e) {

			}
	    }
	    
	    @FXML
	    private Button btnCancelEmployee;
	    
	    @FXML
	    void actionCancelEmployee(ActionEvent event){
	    	leftsplit.setDividerPositions(0.98);
	    	edit(false);
 	    	edit2(false);
	    	clear();
	    }
}
