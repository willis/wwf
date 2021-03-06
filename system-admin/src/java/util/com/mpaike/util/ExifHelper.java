package com.mpaike.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.lang.Rational;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifDirectory;
import com.drew.metadata.exif.GpsDirectory;
import com.mpaike.core.util.date.DateTimeUtil;
import com.mpaike.image.model.Picture;

public class ExifHelper{
	private final static Log log = LogFactory.getLog(ExifHelper.class);

	/**
	 * 解析图片，返回JSON格式字符串
	 * 
	 * @param 文件
	 * @return JSON字符串
	 */
	public static String prase2JSON(String enname,File file) {
		Picture picture = getPicture(enname,file);
		if (picture != null) {
			JSONObject jsonObject = JSONObject.fromObject(picture);
			JSONObject j = new JSONObject();
			j.put("data", jsonObject);
			// 返回错误代码0，即正常返回
			j.put("error", "0");
			return j.toString();
		} else {
			// 返回错误代码1，即无返回结果
			return "{'data':{},'error':'1'}";
		}
	}
	public static void main(String args[]){
		File file  = new File("/Users/ChenH/Downloads/gps/IMG_0010.JPG");
		System.out.println(prase2JSON("mac.jps",file));
	}
	/**
	 * 返回封装EXIF等信息的Picture对象
	 * 
	 * @param 文件
	 * @return Picture对象
	 */
	public static Picture getPicture(String enname,File jpegFile) {
		Picture picture = new Picture();
		if (jpegFile.exists()) {
			// 文件信息 -----------------------------
			// 获取文件大小，begin
			String fileSize = "";
			RandomAccessFile raFile = null;
			try {
				raFile = new RandomAccessFile(jpegFile, "r");
				fileSize = raFile.length() + "";
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally
			{
				if(raFile != null)
				{
					try {
						raFile.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			// 获取文件大小，end

			// 获取图片类型，begin
			String fileType = "";
			ImageInputStream iis = null;
			try {
				iis = ImageIO.createImageInputStream(jpegFile);
				Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
				ImageReader reader = iter.next();
				fileType = reader.getFormatName();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			finally
			{
				if(iis != null)
				{
					try {
						iis.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			// 获取图片类型，end

			// 获取图片MIME类型，begin
			MimetypesFileTypeMap mftm = new MimetypesFileTypeMap();
			String mimeType = mftm.getContentType(jpegFile);// 文件格式
			// 获取图片MIME类型，end
			
			int srcHeight = 0;//图像高度（像素）
			int srcWidth = 0;//图像宽度（像素）
			
			try {
				BufferedImage bufferedImage = ImageIO.read(jpegFile);   
				srcWidth = bufferedImage.getWidth();   
				srcHeight = bufferedImage.getHeight();
				if(srcWidth>srcHeight){
					picture.setPut(Picture.SCREEN_V);
				}else if(srcWidth<srcHeight){
					picture.setPut(Picture.SCREEN_H);
				}else{
					picture.setPut(Picture.SCREEN_T);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} 
			
			String filename = jpegFile.getName();// 文件名
			String fileDataTime = "";// 时间戳
			// 图像信息 -----------------------------
			String imageDescription = "";
			String make = "";
			String model = "";
			String orientation = "";
			String xResolution = "";
			String yResolution = "";
			String software = "";
			String dateTime = "";
			String artist = "";
			String yCbCrPositioning = "";
			String copyright = "";
			String copyrightOfPhotographer = "";
			String copyrightOfEditor = "";
			// 拍摄信息 -----------------------------
			String exifVersion = "";
			String flashPixVersion = "";
			String dateTimeOriginal = "";
			String dateTimeDigitized = "";
			String computedHeight = "";
			String computedWidth = "";
			String apertureValue = "";
			String shutterSpeedValue = "";
			String apertureFNumber = "";
			String maxApertureValue = "";
			String exposureTime = "";
			String fNumber = "";
			String meteringMode = "";
			String lightSource = "";
			String flash = "";
			String exposureMode = "";
			String whiteBalance = "";
			String exposureProgram = "";
			String exposureBiasValue = "";
			String isoSpeedRatings = "";
			String componentsConfiguration = "";
			String compressedBitsPerPixel = "";
			String focusDistance = "";
			String focalLength = "";
			String focalLengthIn35mmFilm = "";
			String userCommentEncoding = "";
			String userComment = "";
			String colorSpace = "";
			String exifImageLength = "";
			String exifImageWidth = "";
			String fileSource = "";
			String sceneType = "";
			String thumbnailFileType = "";
			String thumbnailMimeType = "";
			try {
	            // 第三方类库，获取图片EXIF信息
				
				Metadata metadata = JpegMetadataReader.readMetadata(jpegFile);
				Directory exif = metadata.getDirectory(ExifDirectory.class);
				fileDataTime = exif.getDescription(ExifDirectory.TAG_DATETIME);
				imageDescription = exif
						.getDescription(ExifDirectory.TAG_IMAGE_DESCRIPTION);
				make = exif.getDescription(ExifDirectory.TAG_MAKE);
				model = exif.getDescription(ExifDirectory.TAG_MODEL);
				orientation = exif
						.getDescription(ExifDirectory.TAG_ORIENTATION);
				xResolution = exif
						.getDescription(ExifDirectory.TAG_X_RESOLUTION);
				yResolution = exif
						.getDescription(ExifDirectory.TAG_Y_RESOLUTION);
				software = exif.getDescription(ExifDirectory.TAG_SOFTWARE);
				dateTime = exif.getDescription(ExifDirectory.TAG_DATETIME);
				artist = exif.getDescription(ExifDirectory.TAG_ARTIST);
				yCbCrPositioning = exif
						.getDescription(ExifDirectory.TAG_YCBCR_POSITIONING);
				copyright = exif.getDescription(ExifDirectory.TAG_COPYRIGHT);
				copyrightOfPhotographer = "";
				copyrightOfEditor = "";
				exifVersion = exif
						.getDescription(ExifDirectory.TAG_EXIF_VERSION);
				flashPixVersion = exif
						.getDescription(ExifDirectory.TAG_FLASHPIX_VERSION);
				dateTimeOriginal = exif
						.getDescription(ExifDirectory.TAG_DATETIME_ORIGINAL);
				dateTimeDigitized = exif
						.getDescription(ExifDirectory.TAG_DATETIME_ORIGINAL);
				computedHeight = "";
				computedWidth = "";
				apertureValue = exif.getDescription(ExifDirectory.TAG_APERTURE);
				shutterSpeedValue = exif
						.getDescription(ExifDirectory.TAG_SHUTTER_SPEED);
				apertureFNumber = "";
				maxApertureValue = exif
						.getDescription(ExifDirectory.TAG_MAX_APERTURE);
				exposureTime = exif
						.getDescription(ExifDirectory.TAG_EXPOSURE_TIME);
				fNumber = exif.getDescription(ExifDirectory.TAG_FNUMBER);
				meteringMode = exif
						.getDescription(ExifDirectory.TAG_METERING_MODE);
				lightSource = exif
						.getDescription(ExifDirectory.TAG_LIGHT_SOURCE);
				flash = exif.getDescription(ExifDirectory.TAG_FLASH);
				exposureMode = exif
						.getDescription(ExifDirectory.TAG_EXPOSURE_MODE);
				whiteBalance = exif
						.getDescription(ExifDirectory.TAG_WHITE_BALANCE);
				exposureProgram = exif
						.getDescription(ExifDirectory.TAG_EXPOSURE_PROGRAM);
				exposureBiasValue = exif
						.getDescription(ExifDirectory.TAG_EXPOSURE_BIAS);
				isoSpeedRatings = exif
						.getDescription(ExifDirectory.TAG_ISO_EQUIVALENT);
				componentsConfiguration = exif
						.getDescription(ExifDirectory.TAG_COMPONENTS_CONFIGURATION);
				compressedBitsPerPixel = "";
				focusDistance = exif
						.getDescription(ExifDirectory.TAG_SUBJECT_DISTANCE);
				focalLength = exif
						.getDescription(ExifDirectory.TAG_FOCAL_LENGTH);
				focalLengthIn35mmFilm = exif
						.getDescription(ExifDirectory.TAG_35MM_FILM_EQUIV_FOCAL_LENGTH);
				userCommentEncoding = "";
				userComment = exif
						.getDescription(ExifDirectory.TAG_USER_COMMENT);
				colorSpace = exif.getDescription(ExifDirectory.TAG_COLOR_SPACE);
				exifImageLength = exif
						.getDescription(ExifDirectory.TAG_EXIF_IMAGE_WIDTH);
				exifImageWidth = exif
						.getDescription(ExifDirectory.TAG_EXIF_IMAGE_HEIGHT);
				fileSource = exif.getDescription(ExifDirectory.TAG_FILE_SOURCE);
				sceneType = exif.getDescription(ExifDirectory.TAG_SCENE_TYPE);
				thumbnailFileType = "";
				thumbnailMimeType = "";
				
				// 第三方类库，获取图片GPS信息
				Directory gps = metadata.getDirectory(GpsDirectory.class);
				Rational latpart[] = gps.getRationalArray(GpsDirectory.TAG_GPS_LATITUDE);
				Rational lonpart[] = gps.getRationalArray(GpsDirectory.TAG_GPS_LONGITUDE);
				String northing = gps.getString(GpsDirectory.TAG_GPS_LATITUDE_REF);
				String easting = gps.getString(GpsDirectory.TAG_GPS_LONGITUDE_REF);
				double alt = 0.0;
				double lat = 0.0;
				double lon = 0.0;
				try {
					alt = gps.getDouble(GpsDirectory.TAG_GPS_ALTITUDE);
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				double latsign = 1.0d; if (northing.equalsIgnoreCase("S")) latsign = -1.0d;
				double lonsign = 1.0d; if (easting.equalsIgnoreCase("W")) lonsign = -1.0d;
				lat = (Math.abs(latpart[0].doubleValue()) + latpart[1].doubleValue()/60.0d + latpart[2].doubleValue()/3600.0d)*latsign;
			    lon = (Math.abs(lonpart[0].doubleValue()) + lonpart[1].doubleValue()/60.0d + lonpart[2].doubleValue()/3600.0d)*lonsign;
				if (Double.isNaN(lat) || Double.isNaN(lon))
					picture.setGpsinfo(false);
				else
					picture.setGpsinfo(true);
				picture.setLat(lat);
		    	picture.setLon(lon);
		    	picture.setAlt(alt);
			    	
			} catch (JpegProcessingException e2) {
				e2.printStackTrace();
			} catch (MetadataException e2) {
				e2.printStackTrace();
			}

			picture.setApertureFNumber(formatString(apertureFNumber));
			picture.setApertureValue(formatString(apertureValue));
			picture.setArtist(formatString(artist));
			picture.setColorSpace(formatString(colorSpace));
			picture.setComponentsConfiguration(formatString(componentsConfiguration));
			picture.setCompressedBitsPerPixel(formatString(compressedBitsPerPixel));
			picture.setComputedHeight(formatString(computedHeight));
			picture.setComputedWidth(formatString(computedWidth));
			picture.setCopyright(formatString(copyright));
			picture.setCopyrightOfEditor(formatString(copyrightOfEditor));
			picture.setCopyrightOfPhotographer(formatString(copyrightOfPhotographer));
			picture.setDateTime(formatString(dateTime));
			picture.setDateTimeDigitized(formatString(dateTimeDigitized));
			picture.setDateTimeOriginal(formatString(dateTimeOriginal));
			picture.setExifImageLength(formatString(exifImageLength));
			picture.setExifImageWidth(formatString(exifImageWidth));
			picture.setExifVersion(formatString(exifVersion));
			picture.setExposureBiasValue(formatString(exposureBiasValue ));
			picture.setExposureMode(formatString(exposureMode ));
			picture.setExposureProgram(formatString(exposureProgram));
			picture.setExposureTime(formatString(exposureTime));
			picture.setFileDataTime(formatString(fileDataTime));
			picture.setStoreDate(new Date(jpegFile.lastModified()));
			picture.setPath(enname+"/"+DateTimeUtil.getTime(picture.getStoreDate().getTime()));
			picture.setFilename(jpegFile.getName());
			picture.setFileSize(formatString(fileSize));
			picture.setFileSource(formatString(fileSource ));
			picture.setFileType(formatString(fileType));
			picture.setFlash(formatString(flash));
			picture.setFlashPixVersion(formatString(flashPixVersion));
			picture.setFNumber(formatString(fNumber));
			picture.setFocalLength(formatString(focalLength));
			picture.setFocalLengthIn35mmFilm(formatString(focalLengthIn35mmFilm));
			picture.setFocusDistance(formatString(focusDistance ));
			picture.setImageDescription(formatString(imageDescription));
			picture.setIsoSpeedRatings(formatString(isoSpeedRatings ));
			picture.setLightSource(formatString(lightSource));
			picture.setMake(formatString(make));
			picture.setMaxApertureValue(formatString(maxApertureValue));
			picture.setMeteringMode(formatString(meteringMode));
			picture.setMimeType(formatString(mimeType));
			picture.setModel(formatString(model));
			picture.setOrientation(formatString(orientation ));
			picture.setSceneType(formatString(sceneType ));
			picture.setShutterSpeedValue(formatString(shutterSpeedValue));
			picture.setSoftware(formatString(software ));
			picture.setThumbnailFileType(formatString(thumbnailFileType));
			picture.setThumbnailMimeType(formatString(thumbnailMimeType));
			picture.setUserComment(formatString(userComment));
			picture.setUserCommentEncoding(formatString(userCommentEncoding));
			picture.setWhiteBalance(formatString(whiteBalance));
			picture.setXResolution(formatString(xResolution));
			picture.setYCbCrPositioning(formatString(yCbCrPositioning));
			picture.setYResolution(formatString(yResolution));
			picture.setSrcHeight(srcHeight);
			picture.setSrcWidth(srcWidth);
		} else {
			return null;
		}
		return picture;
	}
	 /**
	  * DMS形式のGPS情報からDD形式のGPS情報を取得します
	  * 
	  * @param strDms
	  *            DMS形式のGPS情報
	  * @return DD形式のGPS情報
	  */
	 private String getDDFromDMS(String strDms) {
	  BigDecimal bidDd = new BigDecimal(0);
	  BigDecimal bidPart = new BigDecimal(0);
	  String[] strParts = strDms.split(" ");
	  for (int i = 0, n = strParts.length; i < n; i++) {
	   if (i == 0) {
	    bidPart = getValue(strParts[i]);
	   } else {
	    bidPart = getValue(strParts[i]).divide(
	      new BigDecimal(Math.pow(60, i)), 6,
	      BigDecimal.ROUND_HALF_UP);
	   }
	   bidDd = bidDd.add(bidPart);
	  }
	  return String.valueOf(bidDd);
	 }

	 /**
	  * 取得したデータからGPS情報を取得します
	  * 
	  * @param strObj
	  *            取得したデータ
	  * @return GPS情報
	  */
	 private BigDecimal getValue(String strObj) {
	  BigDecimal bidValue = new BigDecimal(0);
	  String[] strParts = strObj.split("/");
	  if (strParts.length == 0) {
	   return bidValue;
	  } else {
	   return (new BigDecimal(strParts[0]).divide(new BigDecimal(
	     strParts[1]), 6, BigDecimal.ROUND_HALF_UP));
	  }
	 }
	/**
	 * 返回封装EXIF等信息的Picture对象
	 * 
	 * @param 文件
	 * @return Picture对象
	 */
	public static Picture getPicture(String url,String path,String filename,Date date, byte[] bytes) {
		Picture picture = new Picture();
        // 文件信息 -----------------------------
		// 获取文件大小，begin
		String fileSize = bytes.length + "";
		// 获取文件大小，end

			// 获取图片类型，begin
			String fileType = "";
			ImageInputStream iis = null;
			try {
				iis = ImageIO.createImageInputStream(new ByteArrayInputStream(bytes));
				Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
				ImageReader reader = iter.next();
				fileType = reader.getFormatName();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			finally
			{
				if(iis != null)
				{
					try {
						iis.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			// 获取图片类型，end

			// 获取图片MIME类型，begin
			MimetypesFileTypeMap mftm = new MimetypesFileTypeMap();
			String mimeType = mftm.getContentType(fileType);// 文件格式
			// 获取图片MIME类型，end
			
			int srcHeight = 0;//图像高度（像素）
			int srcWidth = 0;//图像宽度（像素）
			try {
				BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes));   
				srcWidth = bufferedImage.getWidth();   
				srcHeight = bufferedImage.getHeight();
				if(srcWidth>srcHeight){
					picture.setPut(Picture.SCREEN_V);
				}else if(srcWidth<srcHeight){
					picture.setPut(Picture.SCREEN_H);
				}else{
					picture.setPut(Picture.SCREEN_T);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} 
			
			String fileDataTime = "";// 时间戳
			// 图像信息 -----------------------------
			String imageDescription = "";
			String make = "";
			String model = "";
			String orientation = "";
			String xResolution = "";
			String yResolution = "";
			String software = "";
			String dateTime = "";
			String artist = "";
			String yCbCrPositioning = "";
			String copyright = "";
			String copyrightOfPhotographer = "";
			String copyrightOfEditor = "";
			// 拍摄信息 -----------------------------
			String exifVersion = "";
			String flashPixVersion = "";
			String dateTimeOriginal = "";
			String dateTimeDigitized = "";
			String computedHeight = "";
			String computedWidth = "";
			String apertureValue = "";
			String shutterSpeedValue = "";
			String apertureFNumber = "";
			String maxApertureValue = "";
			String exposureTime = "";
			String fNumber = "";
			String meteringMode = "";
			String lightSource = "";
			String flash = "";
			String exposureMode = "";
			String whiteBalance = "";
			String exposureProgram = "";
			String exposureBiasValue = "";
			String isoSpeedRatings = "";
			String componentsConfiguration = "";
			String compressedBitsPerPixel = "";
			String focusDistance = "";
			String focalLength = "";
			String focalLengthIn35mmFilm = "";
			String userCommentEncoding = "";
			String userComment = "";
			String colorSpace = "";
			String exifImageLength = "";
			String exifImageWidth = "";
			String fileSource = "";
			String sceneType = "";
			String thumbnailFileType = "";
			String thumbnailMimeType = "";
			try {
	            // 第三方类库，获取图片EXIF信息
				Metadata metadata = JpegMetadataReader.readMetadata(new ByteArrayInputStream(bytes));
				Directory exif = metadata.getDirectory(ExifDirectory.class);
				
				if(exif.containsTag(ExifDirectory.TAG_DATETIME)){
					fileDataTime = exif.getDescription(ExifDirectory.TAG_DATETIME);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_IMAGE_DESCRIPTION)){
					imageDescription = exif.getDescription(ExifDirectory.TAG_IMAGE_DESCRIPTION);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_IMAGE_DESCRIPTION)){
					make = exif.getDescription(ExifDirectory.TAG_MAKE);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_MODEL)){
					model = exif.getDescription(ExifDirectory.TAG_MODEL);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_ORIENTATION)){
					orientation = exif.getDescription(ExifDirectory.TAG_ORIENTATION);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_X_RESOLUTION)){
					xResolution = exif.getDescription(ExifDirectory.TAG_X_RESOLUTION);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_Y_RESOLUTION)){
					yResolution = exif.getDescription(ExifDirectory.TAG_Y_RESOLUTION);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_SOFTWARE)){
					software = exif.getDescription(ExifDirectory.TAG_SOFTWARE);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_DATETIME)){
					dateTime = exif.getDescription(ExifDirectory.TAG_DATETIME);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_ARTIST)){
					artist = exif.getDescription(ExifDirectory.TAG_ARTIST);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_YCBCR_POSITIONING)){
					yCbCrPositioning = exif.getDescription(ExifDirectory.TAG_YCBCR_POSITIONING);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_COPYRIGHT)){
					copyright = exif.getDescription(ExifDirectory.TAG_COPYRIGHT);
				}

				copyrightOfPhotographer = "";
				copyrightOfEditor = "";
				
				if(exif.containsTag(ExifDirectory.TAG_EXIF_VERSION)){
					exifVersion = exif.getDescription(ExifDirectory.TAG_EXIF_VERSION);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_FLASHPIX_VERSION)){
					flashPixVersion = exif.getDescription(ExifDirectory.TAG_FLASHPIX_VERSION);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_DATETIME_ORIGINAL)){
					dateTimeOriginal = exif.getDescription(ExifDirectory.TAG_DATETIME_ORIGINAL);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_DATETIME_ORIGINAL)){
					dateTimeDigitized = exif.getDescription(ExifDirectory.TAG_DATETIME_DIGITIZED);
				}
				computedHeight = "";
				computedWidth = "";
				
				if(exif.containsTag(ExifDirectory.TAG_APERTURE)){
					apertureValue = exif.getDescription(ExifDirectory.TAG_APERTURE);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_SHUTTER_SPEED)){
					shutterSpeedValue = exif.getDescription(ExifDirectory.TAG_SHUTTER_SPEED);
				}
				apertureFNumber = "";
				
				if(exif.containsTag(ExifDirectory.TAG_MAX_APERTURE)){
					maxApertureValue = exif.getDescription(ExifDirectory.TAG_MAX_APERTURE);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_EXPOSURE_TIME)){
					exposureTime = exif.getDescription(ExifDirectory.TAG_EXPOSURE_TIME);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_FNUMBER)){
					fNumber = exif.getDescription(ExifDirectory.TAG_FNUMBER);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_METERING_MODE)){
					meteringMode = exif.getDescription(ExifDirectory.TAG_METERING_MODE);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_LIGHT_SOURCE)){
					lightSource = exif.getDescription(ExifDirectory.TAG_LIGHT_SOURCE);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_LIGHT_SOURCE)){
					flash = exif.getDescription(ExifDirectory.TAG_FLASH);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_EXPOSURE_MODE)){
					exposureMode = exif.getDescription(ExifDirectory.TAG_EXPOSURE_MODE);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_WHITE_BALANCE)){
					whiteBalance = exif.getDescription(ExifDirectory.TAG_WHITE_BALANCE);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_EXPOSURE_PROGRAM)){
					exposureProgram = exif.getDescription(ExifDirectory.TAG_EXPOSURE_PROGRAM);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_EXPOSURE_BIAS)){
					exposureBiasValue = exif.getDescription(ExifDirectory.TAG_EXPOSURE_BIAS);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_ISO_EQUIVALENT)){
					isoSpeedRatings = exif.getDescription(ExifDirectory.TAG_ISO_EQUIVALENT);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_COMPONENTS_CONFIGURATION)){
					componentsConfiguration = exif.getDescription(ExifDirectory.TAG_COMPONENTS_CONFIGURATION);
				}
				compressedBitsPerPixel = "";
				
				if(exif.containsTag(ExifDirectory.TAG_SUBJECT_DISTANCE)){
					focusDistance = exif.getDescription(ExifDirectory.TAG_SUBJECT_DISTANCE);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_FOCAL_LENGTH)){
					focalLength = exif.getDescription(ExifDirectory.TAG_FOCAL_LENGTH);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_35MM_FILM_EQUIV_FOCAL_LENGTH)){
					focalLengthIn35mmFilm = exif.getDescription(ExifDirectory.TAG_35MM_FILM_EQUIV_FOCAL_LENGTH);
				}
				userCommentEncoding = "";
				
				if(exif.containsTag(ExifDirectory.TAG_USER_COMMENT)){
					userComment = exif.getDescription(ExifDirectory.TAG_USER_COMMENT);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_COLOR_SPACE)){
					colorSpace = exif.getDescription(ExifDirectory.TAG_COLOR_SPACE);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_EXIF_IMAGE_WIDTH)){
					exifImageLength = exif.getDescription(ExifDirectory.TAG_EXIF_IMAGE_WIDTH);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_EXIF_IMAGE_HEIGHT)){
					exifImageWidth = exif.getDescription(ExifDirectory.TAG_EXIF_IMAGE_HEIGHT);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_FILE_SOURCE)){
					fileSource = exif.getDescription(ExifDirectory.TAG_FILE_SOURCE);
				}
				
				if(exif.containsTag(ExifDirectory.TAG_SCENE_TYPE)){
					sceneType = exif.getDescription(ExifDirectory.TAG_SCENE_TYPE);
				}
				// 第三方类库，获取图片GPS信息
				Directory gps = metadata.getDirectory(GpsDirectory.class);
				Rational latpart[] = null;
				Rational lonpart[] = null;
				if(gps.containsTag(GpsDirectory.TAG_GPS_LATITUDE)){
					latpart = gps.getRationalArray(GpsDirectory.TAG_GPS_LATITUDE);
				}
				if(gps.containsTag(GpsDirectory.TAG_GPS_LONGITUDE)){
					lonpart = gps.getRationalArray(GpsDirectory.TAG_GPS_LONGITUDE);
				}
				
				String northing = "";
				String easting = "";
				if(gps.containsTag(GpsDirectory.TAG_GPS_LATITUDE_REF)){
					northing = gps.getString(GpsDirectory.TAG_GPS_LATITUDE_REF);
				}
				if(gps.containsTag(GpsDirectory.TAG_GPS_LONGITUDE_REF)){
					easting = gps.getString(GpsDirectory.TAG_GPS_LONGITUDE_REF);
				}
				
				double alt = 0.0;
				double lat = 0.0;
				double lon = 0.0;
				try {
					if(gps.containsTag(GpsDirectory.TAG_GPS_ALTITUDE)){
						
						alt = gps.getDouble(GpsDirectory.TAG_GPS_ALTITUDE);
					}
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				double latsign = 1.0d; if (northing.equalsIgnoreCase("S")) latsign = -1.0d;
				double lonsign = 1.0d; if (easting.equalsIgnoreCase("W")) lonsign = -1.0d;
				if(latpart!=null)
				lat = (Math.abs(latpart[0].doubleValue()) + latpart[1].doubleValue()/60.0d + latpart[2].doubleValue()/3600.0d)*latsign;
				if(lonpart!=null)
			    lon = (Math.abs(lonpart[0].doubleValue()) + lonpart[1].doubleValue()/60.0d + lonpart[2].doubleValue()/3600.0d)*lonsign;
			    if (Double.isNaN(lat) || Double.isNaN(lon))
					picture.setGpsinfo(true);
				else
					picture.setGpsinfo(false);
				picture.setLat(lat);
		    	picture.setLon(lon);
		    	picture.setAlt(alt);
				thumbnailFileType = "";
				thumbnailMimeType = "";
			} catch (JpegProcessingException e2) {
				e2.printStackTrace();
			} catch (MetadataException e2) {
				e2.printStackTrace();
			}
			picture.setUrl(url);
			picture.setApertureFNumber(formatString(apertureFNumber));
			picture.setApertureValue(formatString(apertureValue));
			picture.setArtist(formatString(artist));
			picture.setColorSpace(formatString(colorSpace));
			picture.setComponentsConfiguration(formatString(componentsConfiguration));
			picture.setCompressedBitsPerPixel(formatString(compressedBitsPerPixel));
			picture.setComputedHeight(formatString(computedHeight));
			picture.setComputedWidth(formatString(computedWidth));
			picture.setCopyright(formatString(copyright));
			picture.setCopyrightOfEditor(formatString(copyrightOfEditor));
			picture.setCopyrightOfPhotographer(formatString(copyrightOfPhotographer));
			picture.setDateTime(formatString(dateTime));
			picture.setDateTimeDigitized(formatString(dateTimeDigitized));
			picture.setDateTimeOriginal(formatString(dateTimeOriginal));
			picture.setExifImageLength(formatString(exifImageLength));
			picture.setExifImageWidth(formatString(exifImageWidth));
			picture.setExifVersion(formatString(exifVersion));
			picture.setExposureBiasValue(formatString(exposureBiasValue ));
			picture.setExposureMode(formatString(exposureMode ));
			picture.setExposureProgram(formatString(exposureProgram));
			picture.setExposureTime(formatString(exposureTime));
			picture.setFileDataTime(formatString(fileDataTime));
			picture.setStoreDate(date);
			picture.setFilename(path);
			picture.setFilename(filename);
			picture.setFileSize(formatString(fileSize));
			picture.setFileSource(formatString(fileSource ));
			picture.setFileType(formatString(fileType));
			picture.setFlash(formatString(flash));
			picture.setFlashPixVersion(formatString(flashPixVersion));
			picture.setFNumber(formatString(fNumber));
			picture.setFocalLength(formatString(focalLength));
			picture.setFocalLengthIn35mmFilm(formatString(focalLengthIn35mmFilm));
			picture.setFocusDistance(formatString(focusDistance ));
			picture.setImageDescription(formatString(imageDescription));
			picture.setIsoSpeedRatings(formatString(isoSpeedRatings ));
			picture.setLightSource(formatString(lightSource));
			picture.setMake(formatString(make));
			picture.setMaxApertureValue(formatString(maxApertureValue));
			picture.setMeteringMode(formatString(meteringMode));
			picture.setMimeType(formatString(mimeType));
			picture.setModel(formatString(model));
			picture.setOrientation(formatString(orientation ));
			picture.setSceneType(formatString(sceneType ));
			picture.setShutterSpeedValue(formatString(shutterSpeedValue));
			picture.setSoftware(formatString(software ));
			picture.setThumbnailFileType(formatString(thumbnailFileType));
			picture.setThumbnailMimeType(formatString(thumbnailMimeType));
			picture.setUserComment(formatString(userComment));
			picture.setUserCommentEncoding(formatString(userCommentEncoding));
			picture.setWhiteBalance(formatString(whiteBalance));
			picture.setXResolution(formatString(xResolution));
			picture.setYCbCrPositioning(formatString(yCbCrPositioning));
			picture.setYResolution(formatString(yResolution));
			picture.setSrcHeight(srcHeight);
			picture.setSrcWidth(srcWidth);

		return picture;
	}
	
	private static  String formatString(String str){
		if(StringUtils.isNotBlank(str))
			return str;
		else
			return "";
		
	}
}
