package kr.co.infopub.chapter.s186.dto;
import java.sql.SQLException;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kr.co.infopub.chapter.s185.dto.EmpConvert;
import kr.co.infopub.chapter.s185.dto.Employee;
import kr.co.infopub.chapter.s185.dto.EmployeeDto;
import kr.co.infopub.chapter.s185.model.EmployeeDAO;
import kr.co.infopub.chapter.s185.util.EmpUtil;
public class EmpTreeFx extends Application {
    public static void main(String[] args) {
    	launch(args);
    }
    EmployeeDAO employeeDAO = new EmployeeDAO();
    // 트리 계층 구조 만들기
    public void makeEmpTree(TreeItem<String> front, List<Employee> dlists,
    		String key, int index, int max) {
    	if(index>max) {return;}     //max를 넘으면 끝
    	for(Employee emp:dlists) {      //개수가 있으니 끝날 것이다.
    		if(EmpUtil.level(emp)!=index) {  //원하는 레벨만 찾음
    			continue;
    		}else if(index > 1 && EmpUtil.level(emp)==index) {    //2부터는 1의 키값과 비교
    			if(EmpUtil.level(emp, index-1).equals(key)){
    				TreeItem<String> aa = new TreeItem<String>(EmpUtil.tname(emp, index));
    				front.getChildren().add(aa);
    				makeEmpTree(aa,dlists,EmpUtil.level(emp, index), index+1, max);
    			}else {
    				continue;
    			}
    		}else if(EmpUtil.level(emp)==1) {
    			TreeItem<String> aa = new TreeItem<String>(EmpUtil.tname(emp,1));
    			front.getChildren().add(aa);
    			makeEmpTree(aa,dlists,EmpUtil.level(emp, 1),2,max);
    		}
    	}
    }
	@Override
	public void start(Stage stage) {
		BorderPane vbox = new BorderPane();
		vbox.setPadding(new Insets(10,10,10,10));
		int max = 0;
		try {
			//가장 큰 레벨을 얻는다.
			max = employeeDAO.getTreeMaxLevel();
			// 관리자/부서원을 트리 관계를 얻는다. 계층구조다
			List<EmployeeDto> blists = employeeDAO.findTreeManagerInEmployee();
			//JavaFX 트리뷰용 리스트로 변환한다. 
			List<Employee> dlists = EmpConvert.toObservProFromDto(blists);
			// "매니저와 직원" 트리 아이템을 만든다. 
			TreeItem<String> root = new TreeItem<String>("매니저와 직원");
			// "매니저와 직원" 트리 아이템에 계층 구조 트리 아이템을 붙인다. 
			makeEmpTree(root, dlists, "",1,max);
			// 트리뷰에 "매니저와 직원" 트리아이템을 붙인다. 
			TreeView<String> treeView = new TreeView<String>(root);
			root.setExpanded(true);
			vbox.setCenter(treeView);
			Label label = new Label("                        ");
			vbox.setBottom(label);
			Scene scene = new Scene(vbox,400,800);
			stage.setScene(scene);
			stage.setTitle("Human Resource Management System ver. 0.3");
			stage.show();
			treeView.getSelectionModel().selectedItemProperty()
			.addListener((observable, oldValue, newValue) -> {
				String name = newValue.getValue();
				label.setText(EmpUtil.dep(name));
			});
		} catch(SQLException e) {
			System.out.println(e);
		}
	}
}
