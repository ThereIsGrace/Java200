package kr.co.infopub.chapter.s190.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.infopub.chapter.s190.dto.DepartmentDto;
import kr.co.infopub.chapter.s190.dto.EmployeeDto;
import kr.co.infopub.chapter.s190.dto.DepCountDto;

public class EmployeeDAO extends DataBase{
	public int getEmployeesTotal() throws SQLException{
		String SQL = " SELECT COUNT(*) FROM EMPLOYEES ";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(SQL);
			log("3/6 getEmployeesTotal Success!!!");
			log(SQL, "getEmployeesTotal");
			rs = psmt.executeQuery();
			log("4/6 getEmployeesTotal Success!!!");
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
			log("5/6 getEmployeesTotal Success!!!");
		}catch(SQLException e) {
			log(" getEmployeesTotal Error!!!",e);
			throw e;
		}finally {
			close(conn, psmt, rs);
		}
		return count;
	}
	
	public List<DepartmentDto> findAllDepartments() throws SQLException{
		
		String SQL = "" + 
//		+ " SELECT NVL(E.DEPARTMENT_ID,0) DEPARTMENT_ID,                  "
//		+ " NVL(D.DEPARTMENT_NAME,'NOTYET') DEPARTMENT_NAME               "
//		+ " FROM EMPLOYEES E, DEPARTMENTS D                              "
//		+ " WHERE E.DEPARTMENT_ID = D.DEPARTMENT_ID(+)                    "
//		+ " GROUP BY E.DEPARTMENT_ID, D.DEPARTMENT_NAME                   "
//		+ " ORDER BY E.DEPARTMENT_ID                                   ";
		" SELECT NVL(D.DEPARTMENT_ID,0) DEPARTMENT_ID,                 "
		+ " NVL(D.DEPARTMENT_NAME,'NOTYET') DEPARTMENT_NAME              "
		+ " FROM EMPLOYEES E FULL OUTER JOIN DEPARTMENTS D               "
		+ " ON E.DEPARTMENT_ID = D.DEPARTMENT_ID                         "
		+ " GROUP BY D.DEPARTMENT_ID, D.DEPARTMENT_NAME                  "
		+ " ORDER BY D.DEPARTMENT_ID                                     ";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		List<DepartmentDto> empList = new ArrayList<>();
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(SQL);
			log(SQL,"findAllDepartments");
			log("3/6 findAllDepartments Success!!!");
			rs = psmt.executeQuery();
			log("4/6 findAllDepartments Success!!!");
			while(rs.next()) {
				DepartmentDto emp = new DepartmentDto();
				emp.setDepartment_id(rs.getInt("DEPARTMENT_ID"));
				emp.setDepartment_name(rs.getString("DEPARTMENT_NAME"));
				empList.add(emp);
			}
			log("5/6 findAllDepartments Success!!!");
		}catch(SQLException e) {
			log(" findAllDepartments Error!!!",e);
			throw e;
		}finally {
			close(conn, psmt, rs);
		}
		return empList;
	}
	public List<DepartmentDto> findAllDepartments2() throws SQLException{
		String SQL = ""+
//				"SELECT NVL(E.DEPARTMENT_ID,0) DEPARTMENT_ID,                       "
//				+ "NVL(D.DEPARTMENT_NAME,'NOTYET') DEPARTMENT_NAME                      "
//				+ "FROM EMPLOYEES E, DEPARTMENTS D                          "
//				+ "WHERE E.DEPARTMENT_ID = D.DEPARTMENT_ID(+)                    "
//				+ " GROUP BY E.DEPARTMENT_ID, D.DEPARTMENT_NAME                    "
//				+ " ORDER BY E.DEPARTMENT_ID                           ";
			" SELECT NVL(E.DEPARTMENT_ID,0) DEPARTMENT_ID,                         "
			+ " NVL(D.DEPARTMENT_NAME,'NOTYET') DEPARTMENT_NAME                     "
			+ " FROM EMPLOYEES E FULL OUTER JOIN DEPARTMENTS D                                      "
			+ " ON E.DEPARTMENT_ID = D.DEPARTMENT_ID                              "
			+ " GROUP BY E.DEPARTMENT_ID, D.DEPARTMENT_NAME                      "
			+ " ORDER BY E.DEPARTMENT_ID                                ";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		List<DepartmentDto> empList = new ArrayList<>();
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(SQL);	
			log(SQL, "findAllDepartments");
			log("3/6 findAllDepartments Success!!!");
			rs = psmt.executeQuery();
			log("4/6 findAllDepartments Success!!!");
			while(rs.next()) {
				DepartmentDto bp = new DepartmentDto();
				bp.setDepartment_name(rs.getString("DEPARTMENT_NAME"));
				bp.setDepartment_id(rs.getInt("DEPARTMENT_ID"));
				empList.add(bp);
			}
			log("5/6 findAllDepartments Success!!!");
		}catch(SQLException e) {
			log("findAllDepartments Error!!!",e);
			e.printStackTrace();
		}finally {
			close(conn, psmt, rs);
		}
		return empList;
	}
	public List<DepCountDto> findAllDepCounts() throws SQLException{
		String SQL = ""
		+ " SELECT COUNT(*) COUNT, NVL(E.DEPARTMENT_ID,0) DEPARTMENT_ID,                 "
		+ " NVL(D.DEPARTMENT_NAME,'NOTYET') DEPARTMENT_NAME                             "
		+ " FROM EMPLOYEES E, DEPARTMENTS D                                              "
		+ " WHERE E.DEPARTMENT_ID = D.DEPARTMENT_ID(+)                                   "
		+ " GROUP BY E.DEPARTMENT_ID, D.DEPARTMENT_NAME                                  "
		+ " ORDER BY COUNT DESC, E.DEPARTMENT_ID ASC                                     ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		List<DepCountDto> empList = new ArrayList<>();
		//List<DepCount> empList = FXCollections.observableArrayList();
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(SQL);
			log("3/6 findAllDepCounts Success!!!");
			log(SQL, "findAllDepCounts");
			rs = psmt.executeQuery();
			log("4/6 findAllDepCounts Success!!!");
			while(rs.next()) {
				DepCountDto emp = new DepCountDto();
				emp.setCount(rs.getInt("COUNT"));
				emp.setDepartment_id(rs.getInt("DEPARTMENT_ID"));
				emp.setDepartment_name(rs.getString("DEPARTMENT_NAME"));
				empList.add(emp);
			}
			log("5/6 findAllDepCounts Success!!!");
		}catch(SQLException e) {
			log(" findAllDepCounts Error !!!",e);
			throw e;
		}finally {
			close(conn, psmt, rs);
		}
		return empList;
	}
	
	
	//---------------------------------emp
	public List<EmployeeDto> findAllEmployees() throws SQLException{
		
		String SQL = " SELECT EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL,                 "
		+ "PHONE_NUMBER, HIRE_DATE, JOB_ID, SALARY, COMMISSION_PCT,                  "
		+ " MANAGER_ID, DEPARTMENT_ID FROM EMPLOYEES ORDER BY EMPLOYEE_ID                  ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		List<EmployeeDto> empList = new ArrayList<>();
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(SQL);
			log("3/6 findAllEmployees Success!!!");
			log(SQL, "findAllEmployees");
			rs = psmt.executeQuery();
			log("4/6 findAllEmployees Success!!!");
			while(rs.next()) {
				EmployeeDto emp = new EmployeeDto();
				emp.setEmployee_id(rs.getInt("EMPLOYEE_ID"));
				emp.setFirst_name(rs.getString("FIRST_NAME"));
				emp.setLast_name(rs.getString("LAST_NAME"));
				emp.setEmail(rs.getString("EMAIL"));
				emp.setPhone_number(rs.getString("PHONE_NUMBER"));
				emp.setHire_date(rs.getDate("HIRE_DATE"));
				emp.setJob_id(rs.getString("JOB_ID"));
				emp.setSalary(rs.getInt("SALARY"));
				emp.setCommission_pct(rs.getDouble("COMMISSION_PCT"));
				emp.setManager_id(rs.getInt("MANAGER_ID"));
				emp.setDepartment_id(rs.getInt("DEPARTMENT_ID"));
				empList.add(emp);
			}
			log("5/6 findAllEmployees Success!!!");
		} catch(SQLException e) {
			log("5/6 findAllEmployees Error !!!",e);
			throw e;
		}finally {
			close(conn, psmt, rs);
		}
		return empList;
	}
	public List<EmployeeDto> findTreeManagerInEmployee() throws SQLException{
		String SQL = ""
		+ " SELECT                      "
		+ " EMPLOYEE_ID,                "
		+ " MANAGER_ID,                 "
		+ " FIRST_NAME,                 "
		+ " LAST_NAME, DEPARTMENT_ID, ORDER2                    "
		+ " FROM (  SELECT                               "
		+ "                    EMPLOYEE_ID,                 "
		+ "                    MANAGER_ID,                   "
		+ "                    FIRST_NAME,                   "
		+ "                    LAST_NAME, DEPARTMENT_ID, LEVEL,           "
		+ "                    SYS_CONNECT_BY_PATH(TO_CHAR(LEVEL,'FM000')||EMPLOYEE_ID,'/') ORDER2           "
		+ "                          FROM EMPLOYEES                    "
		+ "                          START WITH MANAGER_ID IS NULL                "
		+ "                    CONNECT BY PRIOR EMPLOYEE_ID = MANAGER_ID)          "
		+ "                    ORDER BY ORDER2                ";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		List<EmployeeDto> empList = new ArrayList<>();
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(SQL);
			log("3/6 findTreeManagerInEmployee Success!!!");
			log(SQL,"findTreeManagerInEmployee");
			rs = psmt.executeQuery();
			log("4/6 findTreeManagerInEmployee Success!!!!");
			while(rs.next()) {
				EmployeeDto emp = new EmployeeDto();
				emp.setEmployee_id(rs.getInt("EMPLOYEE_ID"));
				emp.setManager_id(rs.getInt("MANAGER_ID"));
				emp.setFirst_name(rs.getString("FIRST_NAME"));
				emp.setLast_name(rs.getString("LAST_NAME"));
				emp.setDepartment_id(rs.getInt("DEPARTMENT_ID"));
				emp.setOrder2(rs.getString("ORDER2"));
				empList.add(emp);
			}
			log("5/6 findTreeManagerInEmployee Success!!!");
		}catch(SQLException e) {
			log(" findTreeManagerInEmployee Error!!!",e);
			throw e;
		}finally {
			close(conn, psmt, rs);
		}
		return empList;
	}
	public int getTreeMaxLevel() throws SQLException{
		String SQL = ""
		+ " SELECT MAX(LEVEL)                      "
		+ " FROM EMPLOYEES                         "
		+ " START WITH MANAGER_ID IS NULL                "
		+ " CONNECT BY PRIOR EMPLOYEE_ID = MANAGER_ID";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(SQL);
			log("3/6 getTreeMaxLevel Success!!!");
			log(SQL,"getTreeMaxLevel");
			rs = psmt.executeQuery();
			log("4/6 getTreeMaxLevel Success!!!");
			if(rs.next()) {
				count = rs.getInt(1);
			}
			log("5/6 getTreeMaxLevel Success!!!");
		}catch(SQLException e) {
			log(" getTreeMaxLevel Error!!!",e);
			throw e;
		}finally {
			close(conn, psmt, rs);
		}
		return count;
	}
	public List<EmployeeDto> findEmployeesByManagerId(String empid) throws SQLException{
		String SQL = " SELECT "
		+ " E.EMPLOYEE_ID EMPLOYEE_ID, D.EMPLOYEE_ID MANAGER_ID,                "
		+ " E.FIRST_NAME,                            "
		+ " E.LAST_NAME, E.EMAIL, E.PHONE_NUMBER, E.HIRE_DATE                      "
		+ " FROM EMPLOYEES E, EMPLOYEES D                      "
		+ " WHERE D.EMPLOYEE_ID = E.MANAGER_ID AND E.MANAGER_ID = ?                 ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		List<EmployeeDto> empList = new ArrayList<>();
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(SQL);
			psmt.setString(1, empid);
			log("3/6 findEmployeesByManagerId Success!!!");
			log(SQL,"findEmployeesByManagerId");
			rs = psmt.executeQuery();
			log("4/6 findEmployeesByManagerId Success!!!");
			while(rs.next()) {
				EmployeeDto emp = new EmployeeDto();
				emp.setEmployee_id(rs.getInt("EMPLOYEE_ID"));
				emp.setFirst_name(rs.getString("FIRST_NAME"));
				emp.setLast_name(rs.getString("LAST_NAME"));
				emp.setEmail(rs.getString("EMAIL"));
				emp.setPhone_number(rs.getString("PHONE_NUMBER"));
				emp.setHire_date(rs.getDate("HIRE_DATE"));
				empList.add(emp);
			}
			log("5/6 findEmployeesByManagerId Success!!!");
		}catch(SQLException e) {
			log("5/6 findEmployeesByManagerId Error!!!",e);
			throw e;
		}finally {
			close(conn, psmt, rs);
		}
		return empList;
	}
	
	
	public List<String> findAllJobs() throws SQLException{
		String SQL = " SELECT JOB_ID, JOB_TITLE FROM JOBS                     ";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		List<String> jobList = new ArrayList<>();
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(SQL);
			log(SQL, "findAllJobs");
			log("3/6 findAllJobs Success!!!");
			rs = psmt.executeQuery();
			log("4/6 findAllJobs Success!!!");
			while(rs.next()) {
				//jobList.add(String.format("%s(%s)",rs.getString("JOB_ID"),rs.getString("JOB_TITLE")));
				jobList.add(String.format("%s",rs.getString("JOB_ID")));
			}
			log("5/6 findAllJobs Success!!!");
		}catch(SQLException e) {
			log(" findAllJobs Error!!!",e);
		}finally {
			close(conn, psmt, rs);
		}
		return jobList;
	}
	
	public List<EmployeeDto> findEmployeesByDepartName(String department_name) throws SQLException{
		List<EmployeeDto> empList = new ArrayList<>();
		//ObservableList<Employee> empList = FXCollections.observableArrayList();
		String SQL = 
				" SELECT * FROM EMPLOYEES              "
				+ " WHERE DEPARTMENT_ID =  "
				+ " (SELECT DEPARTMENT_ID FROM DEPARTMENTS WHERE      "
				+ " DEPARTMENT_NAME = ?)                  ";
		String SQL2 = 
				" SELECT * FROM EMPLOYEES               "
				+ " WHERE DEPARTMENT_ID IS NULL ";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			if(department_name == null || department_name.equalsIgnoreCase("NOTYET")) {
				psmt = conn.prepareStatement(SQL2);
				log(SQL2,"findEmployeesByDepartName");
			}else {
				psmt = conn.prepareStatement(SQL);
				psmt.setString(1,department_name.trim());
				log(SQL,"findEmployeesByDepartName", department_name);
			}
			log("3/6 findEmployeesByDepartName Success!!!");
			rs = psmt.executeQuery();
			log("4/6 findEmployeesByDepartName Success!!!");
			while(rs.next()) {
				EmployeeDto emp = new EmployeeDto();
				emp.setEmployee_id(rs.getInt("EMPLOYEE_ID"));
				emp.setFirst_name(rs.getString("FIRST_NAME"));
				emp.setLast_name(rs.getString("LAST_NAME"));
				emp.setEmail(rs.getString("EMAIL"));
				emp.setPhone_number(rs.getString("PHONE_NUMBER"));
				emp.setHire_date(rs.getDate("HIRE_DATE"));
				emp.setJob_id(rs.getString("JOB_ID"));
				emp.setSalary(rs.getDouble("SALARY"));
				emp.setCommission_pct(rs.getDouble("COMMISSION_PCT"));
				emp.setManager_id(rs.getInt("MANAGER_ID"));
				emp.setDepartment_id(rs.getInt("DEPARTMENT_ID"));
				empList.add(emp);
			}
			log("5/6 findEmployeesByDepartName Success!!!");
		}catch(SQLException e) {
			log(" findEmployeesByDepartName Error!!!",e);
		}finally {
			close(conn,psmt,rs);
		}
		return empList;
	}
	public List<EmployeeDto> findEmployeesByEmpId(String empid) throws SQLException{
		String SQL = ""
			+ " SELECT EMPLOYEE_ID, FIRST_NAME, LAST_NAME,                    "
			+ " EMAIL, PHONE_NUMBER, HIRE_DATE FROM EMPLOYEES                 "
			+ " START WITH EMPLOYEE_ID = ?                                    "
			+ " CONNECT BY PRIOR EMPLOYEE_ID = MANAGER_ID                     ";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		List<EmployeeDto> empList = new ArrayList<>();
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(SQL);
			psmt.setString(1, empid);
			log("3/6 findEmployeesByEmpId Success!!!");
			log(SQL, "findEmployeesByEmpId");
			rs = psmt.executeQuery();
			log("4/6 findEmployeesByEmpId Success!!!");
			while(rs.next()) {
				EmployeeDto emp = new EmployeeDto();
				emp.setEmployee_id(rs.getInt("EMPLOYEE_ID"));
				emp.setFirst_name(rs.getString("FIRST_NAME"));
				emp.setLast_name(rs.getString("LAST_NAME"));
				emp.setEmail(rs.getString("EMAIL"));
				emp.setPhone_number(rs.getString("PHONE_NUMBER"));
				emp.setHire_date(rs.getDate("HIRE_DATE"));
				empList.add(emp);
			}
			log("5/6 findEmployeesByEmpId Success!!!");
		}catch(SQLException e) {
			log(" findEmployeesByEmpId Error!!!",e);
		}finally {
			close(conn, psmt, rs);
		}
		return empList;
	}
	public EmployeeDto findEmployeeById(String empId) throws SQLException{
		String SQL = " SELECT * FROM EMPLOYEES WHERE EMPLOYEE_ID=" + empId;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		EmployeeDto emp = null;
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(SQL);
			log("3/6 findEmployeeById Success!!!");
			log(SQL,"findEmployeeById",empId);
			rs = psmt.executeQuery();
			log("4/6 findEmployeesById Success!!!");
			
			if(rs.next()) {
				emp = new EmployeeDto();
				emp.setEmployee_id(rs.getInt("EMPLOYEE_ID"));
				emp.setFirst_name(rs.getString("FIRST_NAME"));
				emp.setLast_name(rs.getString("LAST_NAME"));
				emp.setEmail(rs.getString("EMAIL"));
				emp.setPhone_number(rs.getString("PHONE_NUMBER"));
				emp.setHire_date(rs.getDate("HIRE_DATE"));
				emp.setJob_id(rs.getString("JOB_ID"));
				emp.setSalary(rs.getInt("SALARY"));
				emp.setCommission_pct(rs.getDouble("COMMISSION_PCT"));
				emp.setManager_id(rs.getInt("MANAGER_ID"));
				emp.setDepartment_id(rs.getInt("DEPARTMENT_ID"));	
			}
			log("5/6 findEmployeesById Success!!!");
		}catch(SQLException e) {
			log(" findEmployeesById Error!!!",e);
		}finally {
			close(conn, psmt, rs);
		}
		return emp;
	}
	//대소문자 구별없이 서치 가능
	public List<EmployeeDto> findManagersByName(String searchManagerId) throws SQLException{
		String SQL = ""
		+ " SELECT EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, HIRE_DATE                    "
		+ " FROM EMPLOYEES                        "
		+ " WHERE (UPPER(LAST_NAME) LIKE"+"'%"+searchManagerId.toUpperCase()+"%'  )                  "
		+ " OR (UPPER(FIRST_NAME) LIKE                 "
		+ "'%" + searchManagerId.toUpperCase()+"%'    )                 "
		+ "ORDER BY EMPLOYEE_ID                         ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		List<EmployeeDto> empList = new ArrayList<>();
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(SQL);
			log("3/6 findManagersByName Success!!!");
			log(SQL,"findManagersByName");
			rs = psmt.executeQuery();
			log("4/6 findManagersByName Success!!!");
			while(rs.next()) {
				EmployeeDto emp = new EmployeeDto();
				emp.setEmployee_id(rs.getInt("EMPLOYEE_ID"));
				emp.setFirst_name(rs.getString("FIRST_NAME"));
				emp.setLast_name(rs.getString("LAST_NAME"));
				emp.setEmail(rs.getString("EMAIL"));
				emp.setPhone_number(rs.getString("PHONE_NUMBER"));
				emp.setHire_date(rs.getDate("HIRE_DATE"));
				empList.add(emp);
			}
			log("5/6 findManagersByName Success!!!");
		}catch(SQLException e) {
			log(" findManagersByName Success!!!",e);
		}finally {
			close(conn, psmt, rs);
		}
		return empList;	
	}
	@SuppressWarnings("resource")
	public  int addEmployee (EmployeeDto emp) throws SQLException {
		 String SQL =""+
                 "INSERT INTO EMPLOYEES	          " 
                 + " (EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL,          "
                 + " PHONE_NUMBER,  HIRE_DATE, JOB_ID, SALARY,          "
                 + " COMMISSION_PCT,MANAGER_ID,DEPARTMENT_ID )	          " 
                 + " VALUES(EMPLOYEES_SEQ.NEXTVAL,?,?,?,"
                 + " ?,?,?"
                 + " ,"+quots(emp.getSalary()+"")
                 + " ,"+quots(emp.getCommission_pct()+"")
                 + " ,"+quoti(emp.getManager_id()+"")
                 + " , "+quoti(emp.getDepartment_id()+"")
                 + ") " ; 
		  String SQL2=" SELECT EMPLOYEES_SEQ.CURRVAL FROM EMPLOYEES ";
		  
		  Connection conn=null;
		  PreparedStatement psmt=null;
		  ResultSet rs=null;
		  int count=0;
		  try {
			  conn=getConnection();
			  conn.setAutoCommit(false);      //TRX
	          psmt=conn.prepareStatement(SQL);
	          int i=1;
	          //아이디는 시퀀스 -> 자동입력						//EMPLOYEE_ID-> SEQUENCE_EMPLOYEE.NEXTVAL
	          psmt.setString(i++,emp.getFirst_name());   //FIRST_NAME
	          psmt.setString(i++,emp.getLast_name());    //LAST_NAME
	          psmt.setString(i++,emp.getEmail());		//EMAIL
	          psmt.setString(i++,emp.getPhone_number()); //PHONE_NUMBER
	          psmt.setDate(i++, (java.sql.Date)emp.getHire_date());//HIRE_DATE
	          psmt.setString(i++,emp.getJob_id());		//JOB_ID
	         
	          log(SQL,"addEmployee",emp);
	          log("3/6 addEmployee Success 1!!!");
	          psmt.executeUpdate();
	          log("4/6 addEmployee Success 1!!!");
	          log("3/6 addEmployee Success 2!!!");
	          psmt=conn.prepareStatement(SQL2);
	          rs=psmt.executeQuery();
	          if(rs.next()){
	        	  count=rs.getInt(1);
	          }
	          log("4/6 addEmployee Success 2 !!!");
	          conn.commit();
		  } catch (SQLException e) {
			  log(" addEmployee Error!!!",e);
			  e.printStackTrace();
			  conn.rollback();
			  throw e;
		  }finally{
			  conn.setAutoCommit(true);
	      	  close(conn, psmt, rs);
	      }
	      return count;
		}
	
	
	public boolean updateEmployee(EmployeeDto emp) throws SQLException{
		String SQL = ""
		+ "UPDATE EMPLOYEES SET                         "
		+ " FIRST_NAME = ?, LAST_NAME = ?, EMAIL = ?,                         "
		+ " PHONE_NUMBER = ?                     "
		+ " WHERE EMPLOYEE_ID = ?                           ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(SQL);
			int i = 1;
			psmt.setString(i++, emp.getFirst_name());
			psmt.setString(i++, emp.getLast_name());
			psmt.setString(i++, emp.getEmail());
			psmt.setString(i++, emp.getPhone_number());
			psmt.setInt(i++, emp.getEmployee_id());
			//SALARY
			//COMMISSION_PCT
			//MANAGER_ID
			//DEPARTMENT_ID
			log(SQL,"updateEmployee",emp);    //EMPLOYEE_ID
			log("3/6 updateEmployee Success 1!!!");
			count = psmt.executeUpdate();
			log("4/6 updateEmployee Success 2 !!!");
		}catch(SQLException e) {
			log(" updateEmployee Error!!!",e);
			throw e;
		}finally {
			close(conn, psmt, rs);
		}
		return count>0?true:false;
	}
	
	public boolean updateJobHistory(EmployeeDto emp) throws SQLException{
		String SQL = ""
		+ " UPDATE EMPLOYEES SET                             "
		+ " JOB_ID=?, DEPARTMENT_ID=?                        "
		+ " WHERE EMPLOYEE_ID=?                              ";
		
		String sql = String.format(SQL,quotd(emp.getSalary()+""),quoti(emp.getCommission_pct()+""),quoti(emp.getManager_id()+""),quoti(emp.getDepartment_id()+""));
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(sql);
			int i = 1;
			//psmt.setDate(i++, (java.sql.Date)emp.getHireDate());  //HIRE_DATE
			psmt.setString(i++, emp.getJob_id());
			psmt.setInt(i++, emp.getDepartment_id());
			psmt.setInt(i++, emp.getEmployee_id());
			
			log(sql, "updateJobHistory", emp);    //EMPLOYEE_ID
			log("3/6 updateJobHistory Success 1!!!");
			count = psmt.executeUpdate();
			log("4/6 updateJobHistory Success 2 !!!");
		}catch(SQLException e) {
			log(" updateJobHistory Error!!!",e);
			throw e;
		}finally {
			close(conn, psmt, rs);
		}
		return count >= 0? true: false;
	}
/*	public boolean deleteEmployee (EmployeeDto emp) throws SQLException{
		String SQL = ""
				+ " DELETE FROM EMPLOYEES                   "
				+ " WHERE EMPLOYEE_ID = ?                   ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(SQL);
			psmt.setInt(1, emp.getEmployee_id());
			log(SQL, "deleteEmployee Success 1!!!");
			count = psmt.executeUpdate();
			log("4/6 deleteEmployee Success 2 !!!");
		}catch(SQLException e) {
			log(" deleteEmployee Error!!!",e);
		}finally {
			close(conn, psmt, rs);
		}
		return count>0?true:false;
	}
	*/
}
