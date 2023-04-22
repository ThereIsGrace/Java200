package kr.co.infopub.chapter.s183.util;
import kr.co.infopub.chapter.s183.dto.DepCountDto;
import kr.co.infopub.chapter.s183.dto.DepartmentDto;

public class EmpUtil {
	// (10)
	public static String q(String msg) {
		return "(" + msg + ")";
	}
	// Administration(10)
	public static String tname(DepartmentDto ee) {
		return ee.getDepartment_name() + q(ee.getDepartment_id()+"");
	}
	public static String tname(DepCountDto ee) {
		return ee.getDepartment_name() + q(ee.getDepartment_id()+"");
	}
	// /101/102/103 -> /의 개수 -1 -> 2
	public static int level(String msg) {
		String [] sp = msg.split("/");
		return sp.length -1;
	}
	// /101/102/103,2 -> 102
	public static String levl(String msg, int index) {
		String [] sp = msg.split("/");
		return sp[index];
	}
	// helloHRD -> loHRD
	public static String str(String msg) {
		return msg.substring(3);
	}
	// helloHRD -> hel
	public static String bfstr(String msg) {
		return msg.substring(0,3);
	}
	// 100(sdfs sfsfs asdfasdf) -> 100
	public static String dep(String msg) {
		String ss= "";
		if(msg.indexOf("(")!=-1)
			ss=msg.substring(0, msg.indexOf("("));
		else
			ss = msg.trim();
		return ss.trim();
	}
}
