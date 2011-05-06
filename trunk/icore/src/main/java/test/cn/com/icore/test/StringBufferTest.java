package cn.com.icore.test;

public class StringBufferTest {
	
	public static void  main(String args[]){
		String pdffile = "/usr/local/apache-tomcat-7.0.11/webapps/viva2/upload/e3eb3d96-6a1c-4977-933a-2a242fb01358.pdf";
		String projectpath = new StringBuffer("/upload/bc8dd422-84ed-4976-a1f7-bef905454cc9.pdf".substring(0,
				"/upload/bc8dd422-84ed-4976-a1f7-bef905454cc9.pdf".lastIndexOf(".")))
				.toString();
		String dstpath = new StringBuffer(pdffile.substring(0,
				pdffile.lastIndexOf(".")))
				.toString();
		String dstpathfile = new StringBuffer(pdffile.substring(pdffile.lastIndexOf("/"),
				pdffile.lastIndexOf(".")))
				.toString();
		StringBuffer xmlpath = new StringBuffer(
				pdffile.substring(0, pdffile.lastIndexOf(".")))
				.append("p");
		System.out.println(dstpath);
		System.out.println(dstpathfile);
		System.out.println(xmlpath);
	}
}
