package com.mpaike.util;

import com.drew.metadata.*;
import com.drew.metadata.exif.*;
import com.drew.imaging.jpeg.*;
import com.drew.lang.*;
import java.io.*;
import java.util.*;

public class GPSIndex {

	class Geo {

		public double lat = 0.0;
		public double lon = 0.0;
		public double alt = 0.0;
		public boolean error = false;

		public Geo(String filename) {
			try {
				error = false;
				File jpegFile = new File(filename);
				Metadata metadata = JpegMetadataReader.readMetadata(jpegFile);

				GpsDirectory gpsdir = (GpsDirectory) metadata
						.getDirectory(GpsDirectory.class);
				Rational latpart[] = gpsdir
						.getRationalArray(GpsDirectory.TAG_GPS_LATITUDE);
				Rational lonpart[] = gpsdir
						.getRationalArray(GpsDirectory.TAG_GPS_LONGITUDE);
				String northing = gpsdir
						.getString(GpsDirectory.TAG_GPS_LATITUDE_REF);
				String easting = gpsdir
						.getString(GpsDirectory.TAG_GPS_LONGITUDE_REF);

				try {
					alt = gpsdir.getDouble(GpsDirectory.TAG_GPS_ALTITUDE);
				} catch (Exception ex) {
				}

				double latsign = 1.0d;
				if (northing.equalsIgnoreCase("S"))
					latsign = -1.0d;
				double lonsign = 1.0d;
				if (easting.equalsIgnoreCase("W"))
					lonsign = -1.0d;
				lat = (Math.abs(latpart[0].doubleValue())
						+ latpart[1].doubleValue() / 60.0d + latpart[2]
						.doubleValue() / 3600.0d) * latsign;
				lon = (Math.abs(lonpart[0].doubleValue())
						+ lonpart[1].doubleValue() / 60.0d + lonpart[2]
						.doubleValue() / 3600.0d) * lonsign;
				System.out.println(lat);
				System.out.println(lon);
				if (Double.isNaN(lat) || Double.isNaN(lon))
					error = true;
			} catch (Exception ex) {
				error = true;
			}

		}

	}

	class GeoTagged {
		double lat = 0.0d;
		double lon = 0.0d;
		double alt = 0.0d;

		Vector paths = new Vector();
	}

	Hashtable locations = new Hashtable();

	String rootpath = "";
	Geo lastgeo = null;
	int width = 800;
	int height = 600;

	public static void main(String args[]) throws Exception {

		GPSIndex bi = new GPSIndex("/Users/ChenH/Downloads/gps/");
		bi.go();

	}

	public GPSIndex(String filepath) {
		rootpath = filepath;
	}

	public void go() throws Exception {
		String outputpath = rootpath + "/index.html";
		System.out.println("writing to: " + outputpath);
		FileOutputStream fos = new FileOutputStream(outputpath);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		PrintStream ps = new PrintStream(bos);

		ps.println("<html>");
		ps.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");

		ps.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		ps.println("<head>");
		ps.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
		ps.println("<title>Places</title>");

		// ps.println("<script src=\"http://gmaps-utility-library.googlecode.com/svn/trunk/markermanager/release/src/markermanager.js\"></script>");
		ps.println("<script src=\"http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAJN9j4K4-BJ7jtEvgbQ01qxRKVBd-30HdSGKG0Ytc1P6pL8J8chQtEhThBbDVGXl5rhQopUfGzbQlkA\" type=\"text/javascript\"></script>");
		ps.println("<script type=\"text/javascript\">");
		ps.println("<!--");
		ps.println("function load() {");
		ps.println("if (GBrowserIsCompatible()) {");
		ps.println("var loc;");
		ps.println("var lon;");
		ps.println("var marker;");
		ps.println("map=new GMap2(document.getElementById(\"container\"));");

		go(0, ps, rootpath);

		// locations now contains all the locations and paths... now let's
		// output the code
		Enumeration e = locations.elements();
		while (e.hasMoreElements()) {
			GeoTagged gt = (GeoTagged) e.nextElement();
			ps.println("loc = new GLatLng(" + gt.lat + "," + gt.lon + ")");
			ps.println("marker = new GMarker(loc);");
			ps.println("GEvent.addListener(marker, \"click\", function() {");
			ps.println(" var thisloc = new GLatLng(" + gt.lat + "," + gt.lon
					+ ");");
			ps.println(" var myHtml = \"\"");
			for (int i = 0; i < gt.paths.size(); ++i) {
				String a = "<a href=\\\"file:///"
						+ ((String) gt.paths.elementAt(i)).replace("\\", "/")
						+ "\\\" target=info>Pic " + i + "</a><br>";
				ps.println(" +\"" + a + "\"");
			}
			ps.println(";");
			ps.println(" map.openInfoWindowHtml(thisloc,myHtml);");
			ps.println("});");
			ps.println("map.addOverlay(marker);");
		}
		ps.println("loc=new GLatLng(" + lastgeo.lat + "," + lastgeo.lon + ")");
		ps.println("map.addControl(new GSmallMapControl());");
		ps.println("map.addControl(new GMapTypeControl());");
		ps.println("map.setCenter(loc, 16, G_SATELLITE_MAP);");
		ps.println("}");
		ps.println("}");
		ps.println("//-->");
		ps.println("</script>");
		ps.println("</head>");
		ps.println("<body onload=\"load()\" unload=\"GUnload()\">");
		ps.println("<div id='container' style='width: " + width
				+ "px; height: " + height + "px;'></div>");
		ps.println("</body>");
		ps.println("</html>");
		bos.close();
		fos.close();
		ps.close();

	}

	void go(int level, PrintStream ps, String dir) throws Exception {

		File root = new File(dir);
		System.out.println("Directory: " + dir);

		File list[] = root.listFiles();

		Geo geo = null;
		for (int i = 0; i < list.length; ++i) {

			String path = list[i].getPath();
			if (list[i].isDirectory()) {
				go(level + 1, ps, path);
			} else if (path.toUpperCase().endsWith(".JPG")) {
				// THIS IS A JPEG... WE GOTTA PROCESS IT

				// Is there GEOTAG information?
				geo = new Geo(path);
				if (geo.error == false) {
					lastgeo = geo;

					String key = "" + geo.lat + "" + geo.lon;
					GeoTagged tag = (GeoTagged) locations.get(key);
					if (tag == null) {
						// This location has never been seen
						tag = new GeoTagged();
						tag.lat = geo.lat;
						tag.lon = geo.lon;
						locations.put(key, tag);
					}
					tag.paths.addElement(path);

					// Yes, it becomes part of our output
					System.out.println("" + level + ") " + path);

				}
			}
		}
	}
}