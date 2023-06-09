package kr.co.infopub.chapter.s190;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import kr.co.infopub.chapter.s190.util.PTS;

public class MainHrFXController {
	
	@FXML
	private MenuItem menuDepart;
	@FXML
	private MenuItem menuManage;
	@FXML
	private MenuItem menuSearch;
	@FXML
	private MenuItem menuUpdate;
	@FXML
	private MenuItem menuChart;
	
	@FXML
	private TabPane mainTabPane;
	
	@FXML
	private Tab tab1;
	
	@FXML
	private Tab tab2;
	
	@FXML
	private Tab tab3;
	
	@FXML
	private Tab tab4;
	
	@FXML
	private Tab tab5;
	
	@FXML
	private BorderPane searchTabBorder;
	
	@FXML
	private BorderPane empTabBorder;
	
	@FXML
	private BorderPane depChartBorder;
	
	@FXML
	private BorderPane debTabBorder;
	
	@FXML
	private BorderPane updateTabBorder;
	
	String systemver = "HR Information System ver.1.0";
	PieChartController piecon;
	
	BorderPane departView;
	BorderPane emptvView;
	BorderPane empsearchView;
	BorderPane empupdateView;
	BorderPane chartView;
	@FXML
	public void initialize() {
		tab1.setOnSelectionChanged(eee -> {
			if(tab1.isSelected()) {
				System.out.println("tab1---------->"+((Tab)eee.getSource()).getId());
				debTabBorder.setCenter(departView);
			}
		});
		
		tab2.setOnSelectionChanged(eee -> {
			if(tab2.isSelected()) {
				System.out.println("tab2---------->"+((Tab)eee.getSource()).getId());
				empTabBorder.setCenter(emptvView);
			}
		});
		
		tab3.setOnSelectionChanged(eee -> {
			if(tab3.isSelected()) {
				System.out.println("tab3---------->"+((Tab)eee.getSource()).getId());
				searchTabBorder.setCenter(empsearchView);
			}
		});
		
		tab4.setOnSelectionChanged(eee -> {
			if(tab4.isSelected()) {
				System.out.println("tab4---------->"+((Tab)eee.getSource()).getId());
				updateTabBorder.setCenter(empupdateView);
			}
		});
		
		tab5.setOnSelectionChanged(eee->{
			if(tab5.isSelected()) {
				System.out.println("tab5---------->"+((Tab)eee.getSource()).getId());
				depChartBorder.setCenter(chartView);
				piecon.refresh();
			}
		});
		
	
	}
	
	@FXML
	void onStartAction(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(systemver);
		alert.setHeaderText("인사관리 시스템 " + PTS.toDate3(new Date()));
		alert.setContentText("인사관리시스템은 부서관리, 관리자관리, 인사에 관련된 입력/수정을 하는 시스템입니다.");
		alert.show();
		mainTabPane.setVisible(true);
	}
	
	@FXML
	void onExitAction(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(systemver);
		alert.setHeaderText("인사관리 시스템("+ PTS.toDate3(new Date())+")을 끝내시겠습니까?");
		alert.setContentText("정말 끝내시겠습니까?");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			Platform.exit();
			//System.exit(0);
		} else return;
	}

	
	@FXML
	void onHelpAction(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(systemver);
		alert.setHeaderText("인사관리 시스템 " +PTS.toDate3(new Date()));
		alert.setContentText("안녕하세요 " + systemver + "입니다. "
				+"\n인사관리시스템은 부서관리, 관리자관리, 인사에 관련된 입력/수정을 하는 시스템입니다."
				+"\n시작은 시작메뉴를 선택하십시오.");
		alert.show();
	}
	
	
	@FXML
	void onMenuction(ActionEvent event) {
		if(event.getSource()==menuDepart) {
			System.out.println("tab1---------------------->");
			mainTabPane.getSelectionModel().select(tab1);
			debTabBorder.setCenter(departView);
		}else if(event.getSource()==menuManage) {
			System.out.println("tab2---------------------->");
			mainTabPane.getSelectionModel().select(tab2);
			empTabBorder.setCenter(emptvView);
		}else if(event.getSource()==menuSearch) {
			System.out.println("tab3---------------------->");
			mainTabPane.getSelectionModel().select(tab3);
			searchTabBorder.setCenter(empsearchView);
		}else if(event.getSource()==menuUpdate) {
			System.out.println("tab4---------------------->");
			mainTabPane.getSelectionModel().select(tab4);
			updateTabBorder.setCenter(empupdateView);
		}else if(event.getSource()==menuChart) {
			System.out.println("tab5---------------------->");
			mainTabPane.getSelectionModel().select(tab5);
			depChartBorder.setCenter(chartView);
			piecon.refresh();
		}
	}

	//메인페인에 각 뷰를 붙인다.
	public void setView1(BorderPane departView) {
		this.departView = departView;
		//첫 화면을 반영한다. 
		debTabBorder.setCenter(departView);
	}
	public void setView2(BorderPane emptvView) {
		this.emptvView = emptvView;
	}
	public void setView(BorderPane empsearchView) {
		this.empsearchView = empsearchView;
	}
	public void setView3(BorderPane empupdateView) {
		this.empupdateView = empupdateView;
	}
	public void setView4(FXMLLoader loader5) {
		try {
			chartView = (BorderPane) loader5.load();
			piecon = loader5.getController();
		}catch(IOException e) {
			System.out.println("setView4 --------------- fail ----------------------");
		}
	}
}
