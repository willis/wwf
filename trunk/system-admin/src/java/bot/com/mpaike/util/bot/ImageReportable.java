package com.mpaike.util.bot;


public class ImageReportable implements ISpiderReportable{

	@Override
	public boolean foundInternalLink(String url) {
		System.out.println("foundInternalLink:"+url);
		return false;
	}

	@Override
	public boolean foundExternalLink(String url) {
		System.out.println("foundExternalLink:"+url);
		return false;
	}

	@Override
	public boolean foundOtherLink(String url) {
		System.out.println("foundOtherLink:"+url);
		return false;
	}

	@Override
	public void processPage(HTTP page) {
		System.out.println("processPage:"+page.getURL());
		
	}

	@Override
	public void completePage(HTTP page, boolean error) {
		System.out.println("completePage:"+page.getURL());
	}

	@Override
	public boolean getRemoveQuery() {
		System.out.println("getRemoveQuery:");
		return false;
	}

	@Override
	public void spiderComplete() {
		System.out.println("spiderComplete:");
		
	}

}
