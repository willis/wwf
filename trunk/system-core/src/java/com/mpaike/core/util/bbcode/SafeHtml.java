package com.mpaike.core.util.bbcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import org.htmlparser.Attribute;
import org.htmlparser.Node;
import org.htmlparser.Tag;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.nodes.TextNode;

import com.mpaike.core.util.StrUtils;

public class SafeHtml {
	/**
	 * 允许的TAG
	 */
	private static final Set<String> welcomeTags = new HashSet<String>(Arrays
			.asList(new String[] { "u", "a", "img", "i", "u", "li", "ul",
					"font", "br", "p", "b", "hr" }));
	/**
	 * 允许的属性
	 */
	private static final Set<String> welcomeAttributes = new HashSet<String>(
			Arrays.asList(new String[] { "src", "href", "size", "face",
					"color", "target", "rel" }));
	/**
	 * 允许的协议
	 */
	private static final Set<String> allowedProtocols = new HashSet<String>(
			Arrays.asList(new String[] { "http://", "https://", "mailto:",
					"ftp://" }));
	/**
	 * 是否允许链接的相对路径
	 */
	private static final boolean LINK_RELATIVE = false;

	/**
	 * Given an input, analyze each HTML tag and remove unsecure attributes from
	 * them.
	 * 
	 * @param contents
	 *            The content to verify
	 * @return the content, secure.
	 */
	public String ensureAllAttributesAreSafe(String contents) {
		StringBuffer sb = new StringBuffer(contents.length());

		try {
			Lexer lexer = new Lexer(contents);
			Node node;

			while ((node = lexer.nextNode()) != null) {
				if (node instanceof Tag) {
					Tag tag = (Tag) node;

					this.checkAndValidateAttributes(tag, false);

					sb.append(tag.toHtml());
				} else {
					sb.append(node.toHtml());
				}
			}
		} catch (Exception e) {
			throw new ForumException("Problems while parsing HTML: " + e, e);
		}

		return sb.toString();
	}

	/**
	 * Given an input, makes it safe for HTML displaying. Removes any not
	 * allowed HTML tag or attribute, as well unwanted Javascript statements
	 * inside the tags.
	 * 
	 * @param contents
	 *            the input to analyze
	 * @return the modified and safe string
	 */
	public String makeSafe(String contents) {
		if (contents == null || contents.length() == 0) {
			return contents;
		}

		StringBuffer sb = new StringBuffer(contents.length());

		try {
			Lexer lexer = new Lexer(contents);
			Node node;

			while ((node = lexer.nextNode()) != null) {
				boolean isTextNode = node instanceof TextNode;

				if (isTextNode) {
					// Text nodes are raw data, so we just
					// strip off all possible html content
					String text = node.toHtml();

					if (text.indexOf('>') > -1 || text.indexOf('<') > -1) {
						StringBuilder tmp = new StringBuilder(text);

						StrUtils.replace(tmp, "<", "&lt;");
						StrUtils.replace(tmp, ">", "&gt;");
						StrUtils.replace(tmp, "\"", "&quot;");

						node.setText(tmp.toString());
					}
				}

				if (isTextNode
						|| (node instanceof Tag && this.isTagWelcome(node))) {
					sb.append(node.toHtml());
				} else {
					StringBuilder tmp = new StringBuilder(node.toHtml());

					StrUtils.replace(tmp, "<", "&lt;");
					StrUtils.replace(tmp, ">", "&gt;");

					sb.append(tmp.toString());
				}
			}
		} catch (Exception e) {
			throw new ForumException("Error while parsing HTML: " + e, e);
		}

		return sb.toString();
	}

	/**
	 * Returns true if a given tag is allowed. Also, it checks and removes any
	 * unwanted attribute the tag may contain.
	 * 
	 * @param node
	 *            The tag node to analyze
	 * @return true if it is a valid tag.
	 */
	private boolean isTagWelcome(Node node) {
		Tag tag = (Tag) node;

		if (!welcomeTags.contains(tag.getTagName())) {
			return false;
		}

		this.checkAndValidateAttributes(tag, true);

		return true;
	}

	/**
	 * Given a tag, check its attributes, removing those unwanted or not secure
	 * 
	 * @param tag
	 *            The tag to analyze
	 * @param checkIfAttributeIsWelcome
	 *            true if the attribute name should be matched against the list
	 *            of welcome attributes, set in the main configuration file.
	 */
	@SuppressWarnings("unchecked")
	private void checkAndValidateAttributes(Tag tag,
			boolean checkIfAttributeIsWelcome) {
		Vector<Attribute> newAttributes = new Vector<Attribute>();

		for (Iterator<Attribute> iter = tag.getAttributesEx().iterator(); iter
				.hasNext();) {
			Attribute a = (Attribute) iter.next();

			String name = a.getName();

			if (name == null) {
				newAttributes.add(a);
			} else {
				name = name.toUpperCase();

				if (a.getValue() == null) {
					newAttributes.add(a);
					continue;
				}

				String value = a.getValue().toLowerCase();

				if (checkIfAttributeIsWelcome && !this.isAttributeWelcome(name)) {
					continue;
				}

				if (!this.isAttributeSafe(name, value)) {
					continue;
				}

				if (a.getValue().indexOf("&#") > -1) {
					a.setValue(a.getValue().replaceAll("&#", "&amp;#"));
				}

				newAttributes.add(a);
			}
		}

		tag.setAttributesEx(newAttributes);
	}

	/**
	 * Check if the given attribute name is in the list of allowed attributes
	 * 
	 * @param name
	 *            the attribute name
	 * @return true if it is an allowed attribute name
	 */
	private boolean isAttributeWelcome(String name) {
		return welcomeAttributes.contains(name);
	}

	/**
	 * Check if the attribute is safe, checking either its name and value.
	 * 
	 * @param name
	 *            the attribute name
	 * @param value
	 *            the attribute value
	 * @return true if it is a safe attribute
	 */
	private boolean isAttributeSafe(String name, String value) {
		if (name.length() >= 2 && name.charAt(0) == 'O'
				&& name.charAt(1) == 'N') {
			return false;
		}

		if (value.indexOf('\n') > -1 || value.indexOf('\r') > -1
				|| value.indexOf('\0') > -1) {
			return false;
		}

		if (("HREF".equals(name) || "SRC".equals(name))) {
			if (!this.isHrefValid(value)) {
				return false;
			}
		} else if ("STYLE".equals(name)) {
			// It is much more a try to not allow constructions
			// like style="background-color: url(javascript:xxxx)" than anything
			// else
			if (value.indexOf('(') > -1) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Checks if a given address is valid
	 * 
	 * @param href
	 *            The address to check
	 * @return true if it is valid
	 */
	private boolean isHrefValid(String href) {
		if (LINK_RELATIVE && href.length() > 0 && href.charAt(0) == '/') {
			return true;
		}

		for (Iterator<String> iter = allowedProtocols.iterator(); iter
				.hasNext();) {
			String protocol = iter.next().toString().toLowerCase();

			if (href.startsWith(protocol)) {
				return true;
			}
		}

		return false;
	}
}