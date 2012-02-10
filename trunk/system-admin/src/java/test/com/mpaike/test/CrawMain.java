package com.mpaike.test;

import java.io.IOException;
import java.io.StringReader;
import java.util.Vector;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;

import org.apache.commons.configuration.ConfigurationException;

import com.crawljax.browser.EmbeddedBrowser;
import com.crawljax.browser.EmbeddedBrowser.BrowserType;
import com.crawljax.core.CrawlSession;
import com.crawljax.core.CrawljaxController;
import com.crawljax.core.CrawljaxException;
import com.crawljax.core.configuration.CrawlSpecification;
import com.crawljax.core.configuration.CrawljaxConfiguration;
import com.crawljax.core.plugin.OnNewStatePlugin;
import com.crawljax.core.plugin.OnUrlLoadPlugin;
import com.crawljax.core.plugin.PostCrawlingPlugin;
import com.mpaike.util.bot.Attribute;
import com.mpaike.util.bot.AttributeList;
import com.mpaike.util.bot.HTMLForm;

import com.mpaike.util.bot.HTMLParse;
import com.mpaike.util.bot.Link;
import com.mpaike.util.bot.URLUtility;

public class CrawMain {

	  protected static Vector links = new Vector();
	  
	  protected static Vector images = new Vector();
	  
	  protected static String base;
	  
	/**
	 * @param args
	 * @throws ConfigurationException 
	 * @throws CrawljaxException 
	 */
	public static void main(String[] args) throws ConfigurationException, CrawljaxException {
		// TODO Auto-generated method stub
		base = "http://www.duitang.com/";
		
		CrawlSpecification crawler = new CrawlSpecification(base);
		crawler.click("a").withAttribute("class", "a");
		crawler.dontClick("img");
		crawler.setDepth(1);
		crawler.setMaximumStates(Integer.MAX_VALUE);
		crawler.setWaitTimeAfterReloadUrl(1000);
		
		CrawljaxConfiguration config = new CrawljaxConfiguration();
		config.setCrawlSpecification(crawler);
		config.setBrowser(BrowserType.chrome);
		config.setOutputFolder("/Users/tozhangwj/system-admin/craw");
		config.addPlugin(new MirrorGenerator());
		config.addPlugin(new UrlGenerator());

		CrawljaxController crawljax = new CrawljaxController(config);
		
		crawljax.run();
	}
	

	  


}

class MirrorGenerator implements OnNewStatePlugin {

	@Override
	public void onNewState(CrawlSession session) {
		try {
			String dom = session.getBrowser().getDom();
			String fileName = session.getCurrentState().getName();
			System.out.println("fileName = "+fileName);
			processPage(dom);
			System.out.println("links:"+CrawMain.links.size());
			System.out.println("images:"+CrawMain.images.size());
			/* last parameter is false because we don't want to append if the file exists */
			//FileWriter fw = new FileWriter(fileName, false);
			//fw.write(dom);
			//fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	  protected void processPage(String body)
			  throws IOException
			  {
			    StringReader r = new StringReader(body);
			    HTMLEditorKit.Parser parse = new HTMLParse().getParser();

			   Parser p= new Parser();
			     parse.parse(r,p,true);
	 }
}

class UrlGenerator implements OnUrlLoadPlugin {

	@Override
	public void onUrlLoad(EmbeddedBrowser browser) {
		System.out.println(browser.getCurrentUrl());
	}


}

class PostGenerator implements PostCrawlingPlugin {

	@Override
	public void postCrawling(CrawlSession session) {
		// TODO Auto-generated method stub
		System.out.println("PostCrawlingPlugin:"+session.getCurrentState().getName());
	}



}

class Parser extends HTMLEditorKit.ParserCallback {

	/**
	 * Used to build up data for an HTML form.
	 */
	protected HTMLForm tempForm;

	/**
	 * Used to build up options for an HTML form.
	 */
	protected AttributeList tempOptions;

	/**
	 * Used to build up options for an HTML form.
	 */
	protected Attribute tempElement = new Attribute();

	/**
	 * Holds the prompt text(just before or after a control.
	 */
	protected String tempPrompt = "";

	/**
	 * Holds the link till the end link is found
	 */
	protected Link tempLink;

	/**
	 * Called to handle comments.
	 * 
	 * @param data
	 *            The comment.
	 * @param pos
	 *            The position.
	 */
	public void handleComment(char[] data, int pos) {
	}

	/**
	 * Called to handle an ending tag.
	 * 
	 * @param t
	 *            The ending tag.
	 * @param pos
	 *            The position.
	 */
	public void handleEndTag(HTML.Tag t, int pos) {
		if (t == HTML.Tag.OPTION) {
			if (tempElement != null) {
				tempElement.setName(tempPrompt);
				tempOptions.add(tempElement);
				tempPrompt = "";
			}
			tempElement = null;
		} else if (t == HTML.Tag.A) {
			if (tempLink != null)
				tempLink.setPrompt(tempPrompt);
			tempPrompt = "";
		}

	}

	/**
	 * Called to handle an error. Not used.
	 * 
	 * @param errorMsg
	 *            The error.
	 * @param pos
	 *            The position.
	 */
	public void handleError(String errorMsg, int pos) {
	}

	/**
	 * Called to handle a simple tag.
	 * 
	 * @param t
	 *            The simple tag.
	 * @param a
	 *            The attribute list.
	 * @param pos
	 *            The position.
	 */
	public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos) {
		handleStartTag(t, a, pos);
	}

	/**
	 * Called to handle a starting tag.
	 * 
	 * @param t
	 *            The starting tag.
	 * @param a
	 *            The attribute list.
	 * @param pos
	 *            The position.
	 */
	public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
		String type = "";

		// is it some sort of a link
		String href = (String) a.getAttribute(HTML.Attribute.HREF);

		if ((href != null) && (t != HTML.Tag.BASE)) {
			String alt = (String) a.getAttribute(HTML.Attribute.ALT);
			Link link = new Link(alt, URLUtility.resolveBase(CrawMain.base, href), null);
			System.out.println("link:"+link.getHREF());
			CrawMain.links.addElement(tempLink = link);
		} else if (t == HTML.Tag.OPTION) {
			tempElement = new Attribute();
			tempElement.setName("");
			tempElement.setValue((String) a.getAttribute(HTML.Attribute.VALUE));
		} else if (t == HTML.Tag.SELECT) {
			if (tempForm == null)
				return;

			tempOptions = new AttributeList();
			tempForm.addInput((String) a.getAttribute(HTML.Attribute.NAME),
					null, "select", tempPrompt, tempOptions);
			tempPrompt = "";
		} else if (t == HTML.Tag.TEXTAREA) {
			if (tempForm == null)
				return;

			tempForm.addInput((String) a.getAttribute(HTML.Attribute.NAME),
					null, "textarea", tempPrompt, null);
			tempPrompt = "";
		} else if (t == HTML.Tag.INPUT) {
			if (tempForm == null)
				return;
			if (t != HTML.Tag.INPUT) {
				type = (String) a.getAttribute(HTML.Attribute.TYPE);
				if (type == null)
					return;
			} else
				type = "select";

			if (type.equalsIgnoreCase("text") || type.equalsIgnoreCase("edit")
					|| type.equalsIgnoreCase("password")
					|| type.equalsIgnoreCase("select")
					|| type.equalsIgnoreCase("hidden")) {
				tempForm.addInput((String) a.getAttribute(HTML.Attribute.NAME),
						(String) a.getAttribute(HTML.Attribute.VALUE), type,
						tempPrompt, null);
				tempOptions = new AttributeList();
			}
		} else if (t == HTML.Tag.BASE) {
			href = (String) a.getAttribute(HTML.Attribute.HREF);
			if (href != null)
				CrawMain.base = href;
		} else if (t == HTML.Tag.IMG) {
			String src = (String) a.getAttribute(HTML.Attribute.SRC);
			if (src != null){
				System.out.println("images:"+src);
				CrawMain.images.add(src);
			}
		}

	}

	/**
	 * Called to handle text.
	 * 
	 * @param data
	 *            The text.
	 * @param pos
	 *            The position.
	 */
	public void handleText(char[] data, int pos) {
		tempPrompt += new String(data) + " ";
	}

}


