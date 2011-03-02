package cn.com.icore.util.pager;

   
   
/**   
 * struts2版的分页标签   
 *    
 */   
@SuppressWarnings("unchecked")
public class Pager {    
   
	private int reCount;
	private int page;
	private int pageSize;
	private int rePages;
	private String path;

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public Pager()
	{
	}

	public Pager(int reCount, int page, int pageSize)
	{
		this.reCount = reCount;
		this.page = page;
		this.pageSize = pageSize;
		if (reCount <= 0)
			reCount = 0;
		account();
	}

	public void setPage(int page)
	{
		this.page = page;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}

	public void setReCount(int reCount)
	{
		this.reCount = reCount;
	}

	public void setRePages(int rePages)
	{
		this.rePages = rePages;
	}

	public void account()
	{
		int temp_i = reCount % pageSize != 0 ? 1 : 0;
		rePages = reCount / pageSize + temp_i;
		if (page > rePages)
			page = rePages;
		if (page < 1)
			page = 1;
	}

	public int getReCount()
	{
		return reCount;
	}

	public int getPage()
	{
		return page;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public int getRePages()
	{
		return rePages;
	}

	public int getRestat()
	{
		return (page - 1) * pageSize - 1;
	}

	public boolean atFirstPage()
	{
		return page == 1;
	}

	public boolean atLastPage()
	{
		return page == rePages;
	}

	public int getFirstItemIndex()
	{
		return (page - 1) * pageSize + 1;
	}

	public int getLastItemIndex()
	{
		int nCurrentIndex = (page - 1) * pageSize + pageSize;
		if (nCurrentIndex > reCount)
			return reCount;
		else
			return nCurrentIndex;
	}

	public int getPrePageIndex()
	{
		int nRet = page - 1;
		if (nRet < 1)
			nRet = 1;
		return nRet;
	}

	public int getNextPageIndex()
	{
		int nRet = page + 1;
		if (nRet > rePages)
			nRet = rePages;
		return nRet;
	}

	public String getDetails(String p_name, String param)
	{
		int disp = 6;
		return getDetails(p_name, param, disp);
	}

	public String getDetails(String p_name, String param, int disp)
	{
		if (param == null)
			param = "";
		StringBuffer href = new StringBuffer();
		if (path == null)
			href.append("?");
		else
			href.append(path);
		if (href.toString().endsWith("?"))
			href.append(param);
		else
		if (href.indexOf("?") != -1 && param.trim().length() > 0)
			href.append("&").append(param);
		else
		if (href.indexOf("?") == -1 && param.trim().length() > 0)
			href.append("?").append(param);
		if (!href.toString().endsWith("?"))
			href.append("&");
		StringBuffer strOut = new StringBuffer("");
		if (href.indexOf("pageSize") == -1)
			href.append("pageSize=").append(pageSize).append("&");
		if (page > 1)
		{
			strOut.append((new StringBuilder("<a href=\"")).append(href).append(p_name).append("=").append(1).append("\">首页</a>&nbsp;").toString());
			strOut.append((new StringBuilder("<a href=\"")).append(href).append(p_name).append("=").append(page - 1).append("\">上一页</a>").toString());
		}
		if (page < disp - 1)
		{
			for (int i = 1; i <= rePages; i++)
			{
				if (i == disp)
					break;
				String temp = (new StringBuilder("<a href=\"")).append(href).append(p_name).append("=").append(i).append("\">").append(i).append("</a>").toString();
				if (i == page)
					temp = (new StringBuilder("<b><font color='red'>")).append(i).append("</font></b>").toString();
				strOut.append((new StringBuilder(String.valueOf(temp))).append("&nbsp;").toString());
			}

		} else
		{
			int begin_i = 1;
			if (page < rePages)
				begin_i = page - (disp / 2 - 1);
			else
				begin_i = page - disp / 2;
			int j = 0;
			for (int i = begin_i; i <= rePages; i++)
			{
				if (j == 5)
					break;
				j++;
				String temp = (new StringBuilder("<a href=\"")).append(href).append(p_name).append("=").append(i).append("\">").append(i).append("</a>").toString();
				if (i == page)
					temp = (new StringBuilder("<b><font color='red'>")).append(i).append("</font></b>").toString();
				strOut.append((new StringBuilder(String.valueOf(temp))).append("&nbsp;").toString());
			}

		}
		if (rePages > page)
		{
			strOut.append((new StringBuilder("<a href=\"")).append(href).append(p_name).append("=").append(page + 1).append("\">下一页</a>").toString());
			strOut.append((new StringBuilder("&nbsp;<a href=\"")).append(href).append(p_name).append("=").append(rePages).append("\">尾页</a>&nbsp;").toString());
		}
		return strOut.toString();
	}

	public String getCmsDetails(int disp)
	{
		StringBuffer strOut = new StringBuffer();
		strOut.append("<ul>");
		if (page > 1)
		{
			strOut.append("<li class=\"page-frist\"><a class=\"page-frist\" href=\"index.html\"></a></li>");
			strOut.append((new StringBuilder("<li class=\"page-prev\"><a class=\"page-prev\" href=\"index_")).append(page - 1).append(".html\"></a></li>").toString());
		}
		if (page < disp - 1)
		{
			for (int i = 1; i <= rePages; i++)
			{
				if (i == disp)
					break;
				String temp = (new StringBuilder("<li><a href=\"index_")).append(i).append(".html\">").append(i).append("</a></li>").toString();
				if (i == page)
					temp = (new StringBuilder("<li><span>")).append(i).append("</span></li>").toString();
				strOut.append(temp);
			}

		} else
		{
			int begin_i = 1;
			if (page < rePages)
				begin_i = page - (disp / 2 - 1);
			else
				begin_i = page - disp / 2;
			int j = 0;
			for (int i = begin_i; i <= rePages; i++)
			{
				if (j == 5)
					break;
				j++;
				String temp = (new StringBuilder("<li><a href=\"index_")).append(i).append(".html\">").append(i).append("</a></li>").toString();
				if (i == page)
					temp = (new StringBuilder("<li><span>")).append(i).append("</span></li>").toString();
				strOut.append(temp);
			}

		}
		if (rePages > page)
		{
			strOut.append((new StringBuilder("<li class=\"page-next\"><a class=\"page-next\" href=\"index_")).append(page + 1).append(".html\"></a></li>").toString());
			strOut.append((new StringBuilder("<li class=\"page-last\"><a class=\"page-last\" href=\"index_")).append(rePages).append(".html\"></a></li>").toString());
		}
		strOut.append((new StringBuilder("<li class=\"ccc\">共")).append(rePages).append("页</li>").toString());
		strOut.append((new StringBuilder("<li class=\"ccc\">共")).append(reCount).append("条记录</li>").toString());
		strOut.append("</ul>");
		return strOut.toString();
	}

	public String getJsDetails(String p_name, String url, String param, String jsMethod, int disp)
	{
		StringBuffer strOut = new StringBuffer("");
		String ljf = "&";
		if (param == null)
			param = "";
		if (param == null || "".equals(param))
			ljf = "";
		if (page > 1)
		{
			strOut.append((new StringBuilder("<span style=\"cursor:pointer\" onclick=\"")).append(jsMethod).append("('").append(url).append("','").append(param).append(ljf).append(p_name).append("=").append(1).append("')\">首页</span>&nbsp;").toString());
			strOut.append((new StringBuilder("<span style=\"cursor:pointer\" onclick=\"")).append(jsMethod).append("('").append(url).append("','").append(param).append(ljf).append(p_name).append("=").append(page - 1).append("')\">上一页</span>").toString());
		}
		if (page < disp - 1)
		{
			for (int i = 1; i <= rePages; i++)
			{
				if (i == disp)
					break;
				String temp = (new StringBuilder("<span style=\"cursor:pointer\" onclick=\"")).append(jsMethod).append("('").append(url).append("','").append(param).append(ljf).append(p_name).append("=").append(i).append("')\">").append(i).append("</span>&nbsp;").toString();
				if (i == page)
					temp = (new StringBuilder("<b><font color='red'>")).append(i).append("</font></b>").toString();
				strOut.append((new StringBuilder(String.valueOf(temp))).append("&nbsp;").toString());
			}

		} else
		{
			int begin_i = 1;
			if (page < rePages)
				begin_i = page - (disp / 2 - 1);
			else
				begin_i = page - disp / 2;
			int j = 0;
			for (int i = begin_i; i <= rePages; i++)
			{
				if (j == disp)
					break;
				j++;
				String temp = (new StringBuilder("<span style=\"cursor:pointer\" onclick=\"")).append(jsMethod).append("('").append(url).append("','").append(param).append(ljf).append(p_name).append("=").append(i).append("')\">").append(i).append("</span>&nbsp;").toString();
				if (i == page)
					temp = (new StringBuilder("<b><font color='red'>")).append(i).append("</font></b>").toString();
				strOut.append((new StringBuilder(String.valueOf(temp))).append("&nbsp;").toString());
			}

		}
		if (rePages > page)
		{
			strOut.append((new StringBuilder("<span style=\"cursor:pointer\" onclick=\"")).append(jsMethod).append("('").append(url).append("','").append(param).append(ljf).append(p_name).append("=").append(page + 1).append("')\">下一页</span>").toString());
			strOut.append((new StringBuilder("&nbsp;<span style=\"cursor:pointer\" onclick=\"")).append(jsMethod).append("('").append(url).append("','").append(param).append(ljf).append(p_name).append("=").append(rePages).append("')\">尾页</span>").toString());
		}
		return strOut.toString();
	}

	public String getFormJump(String param)
	{
		if (param == null)
			param = "";
		StringBuffer href = new StringBuffer();
		if (path == null)
			href.append("?");
		else
			href.append(path);
		if (href.toString().endsWith("?"))
			href.append(param);
		else
		if (href.indexOf("?") != -1 && param.trim().length() > 0)
			href.append("&").append(param);
		else
		if (href.indexOf("?") == -1 && param.trim().length() > 0)
			href.append("?").append(param);
		StringBuffer strOut = new StringBuffer();
		strOut.append((new StringBuilder("<input type='text' name='pageSize' value='")).append(pageSize).append("' style='width:20px'  onblur='window.location.href=\"").append(href).append("&pageSize=\"+this.value'>").toString());
		return strOut.toString();
	}

	public String getDetails()
	{
		String strOut = (new StringBuilder("<font color=red>")).append(reCount).append("</font>条记录，每页<font color=red>").append(pageSize).append("</font>条，共<font color=red>").append(rePages).append("</font>页").toString();
		return strOut;
	}

	public static void main(String args[])
	{
		Pager page = new Pager(54, 1, 2);
		System.out.println(page.getDetails("p_name", ""));
		System.out.println(page.getFormJump("p_name"));
		System.out.println(page.getCmsDetails(6));
	}
}    