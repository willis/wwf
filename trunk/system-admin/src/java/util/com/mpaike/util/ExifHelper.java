package com.mpaike.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifDirectory;
import com.mpaike.image.model.Picture;

public class ExifHelper{
	private final static Log log = LogFactory.getLog(ExifHelper.class);

	/**
	 * 解析图片，返回JSON格式字符串
	 * 
	 * @param 文件
	 * @return JSON字符串
	 */
	public static String prase2JSON(File file) {
		Picture picture = getPicture(file);
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

	/**
	 * 返回封装EXIF等信息的Picture对象
	 * 
	 * @param 文件
	 * @return Picture对象
	 */
	public static Picture getPicture(File jpegFile) {
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
			} catch (JpegProcessingException e2) {
				e2.printStackTrace();
			} catch (MetadataException e2) {
				e2.printStackTrace();
			}

			picture.setApertureFNumber(apertureFNumber == null ? ""
					: apertureFNumber);
			picture.setApertureValue(apertureValue == null ? ""
							: apertureValue);
			picture.setArtist(artist == null ? "" : artist);
			picture.setColorSpace(colorSpace == null ? "" : colorSpace);
			picture.setComponentsConfiguration(componentsConfiguration == null ? ""
							: componentsConfiguration);
			picture.setCompressedBitsPerPixel(compressedBitsPerPixel == null ? ""
							: compressedBitsPerPixel);
			picture.setComputedHeight(computedHeight == null ? ""
					: computedHeight);
			picture.setComputedWidth(computedWidth == null ? ""
							: computedWidth);
			picture.setCopyright(copyright == null ? "" : copyright);
			picture.setCopyrightOfEditor(copyrightOfEditor == null ? ""
					: copyrightOfEditor);
			picture.setCopyrightOfPhotographer(copyrightOfPhotographer == null ? ""
							: copyrightOfPhotographer);
			picture.setDateTime(dateTime == null ? "" : dateTime);
			picture.setDateTimeDigitized(dateTimeDigitized == null ? ""
					: dateTimeDigitized);
			picture.setDateTimeOriginal(dateTimeOriginal == null ? ""
					: dateTimeOriginal);
			picture.setExifImageLength(exifImageLength == null ? ""
					: exifImageLength);
			picture.setExifImageWidth(exifImageWidth == null ? ""
					: exifImageWidth);
			picture.setExifVersion(exifVersion == null ? "" : exifVersion);
			picture.setExposureBiasValue(exposureBiasValue == null ? ""
					: exposureBiasValue);
			picture.setExposureMode(exposureMode == null ? "" : exposureMode);
			picture.setExposureProgram(exposureProgram == null ? ""
					: exposureProgram);
			picture.setExposureTime(exposureTime == null ? "" : exposureTime);
			picture.setFileDataTime(fileDataTime == null ? "" : fileDataTime);
			picture.setFilename(filename);
			picture.setFileSize(fileSize == null ? "" : fileSize);
			picture.setFileSource(fileSource == null ? "" : fileSource);
			picture.setFileType(fileType == null ? "" : fileType);
			picture.setFlash(flash == null ? "" : flash);
			picture.setFlashPixVersion(flashPixVersion == null ? ""
					: flashPixVersion);
			picture.setFNumber(fNumber == null ? "" : fNumber);
			picture.setFocalLength(focalLength == null ? "" : focalLength);
			picture.setFocalLengthIn35mmFilm(focalLengthIn35mmFilm == null ? ""
					: focalLengthIn35mmFilm);
			picture.setFocusDistance(focusDistance == null ? ""
							: focusDistance);
			picture.setImageDescription(imageDescription == null ? ""
					: imageDescription);
			picture.setIsoSpeedRatings(isoSpeedRatings == null ? ""
					: isoSpeedRatings);
			picture.setLightSource(lightSource == null ? "" : lightSource);
			picture.setMake(make == null ? "" : make);
			picture.setMaxApertureValue(maxApertureValue == null ? ""
					: maxApertureValue);
			picture.setMeteringMode(meteringMode == null ? "" : meteringMode);
			picture.setMimeType(mimeType == null ? "" : mimeType);
			picture.setModel(model == null ? "" : model);
			picture.setOrientation(orientation == null ? "" : orientation);
			picture.setSceneType(sceneType == null ? "" : sceneType);
			picture.setShutterSpeedValue(shutterSpeedValue == null ? ""
					: shutterSpeedValue);
			picture.setSoftware(software == null ? "" : software);
			picture.setThumbnailFileType(thumbnailFileType == null ? ""
					: thumbnailFileType);
			picture.setThumbnailMimeType(thumbnailMimeType == null ? ""
					: thumbnailMimeType);
			picture.setUserComment(userComment == null ? "" : userComment);
			picture.setUserCommentEncoding(userCommentEncoding == null ? ""
					: userCommentEncoding);
			picture.setWhiteBalance(whiteBalance == null ? "" : whiteBalance);
			picture.setXResolution(xResolution == null ? "" : xResolution);
			picture.setYCbCrPositioning(yCbCrPositioning == null ? ""
					: yCbCrPositioning);
			picture.setYResolution(yResolution == null ? "" : yResolution);
			picture.setSrcHeight(srcHeight);
			picture.setSrcWidth(srcWidth);
		} else {
			return null;
		}
		return picture;
	}
	
	/**
	 * 返回封装EXIF等信息的Picture对象
	 * 
	 * @param 文件
	 * @return Picture对象
	 */
	public static Picture getPicture(String filename,byte[] bytes) {
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
			} catch (JpegProcessingException e2) {
				e2.printStackTrace();
			} catch (MetadataException e2) {
				e2.printStackTrace();
			}

			picture.setApertureFNumber(apertureFNumber == null ? ""
					: apertureFNumber);
			picture.setApertureValue(apertureValue == null ? ""
							: apertureValue);
			picture.setArtist(artist == null ? "" : artist);
			picture.setColorSpace(colorSpace == null ? "" : colorSpace);
			picture.setComponentsConfiguration(componentsConfiguration == null ? ""
							: componentsConfiguration);
			picture.setCompressedBitsPerPixel(compressedBitsPerPixel == null ? ""
							: compressedBitsPerPixel);
			picture.setComputedHeight(computedHeight == null ? ""
					: computedHeight);
			picture.setComputedWidth(computedWidth == null ? ""
							: computedWidth);
			picture.setCopyright(copyright == null ? "" : copyright);
			picture.setCopyrightOfEditor(copyrightOfEditor == null ? ""
					: copyrightOfEditor);
			picture.setCopyrightOfPhotographer(copyrightOfPhotographer == null ? ""
							: copyrightOfPhotographer);
			picture.setDateTime(dateTime == null ? "" : dateTime);
			picture.setDateTimeDigitized(dateTimeDigitized == null ? ""
					: dateTimeDigitized);
			picture.setDateTimeOriginal(dateTimeOriginal == null ? ""
					: dateTimeOriginal);
			picture.setExifImageLength(exifImageLength == null ? ""
					: exifImageLength);
			picture.setExifImageWidth(exifImageWidth == null ? ""
					: exifImageWidth);
			picture.setExifVersion(exifVersion == null ? "" : exifVersion);
			picture.setExposureBiasValue(exposureBiasValue == null ? ""
					: exposureBiasValue);
			picture.setExposureMode(exposureMode == null ? "" : exposureMode);
			picture.setExposureProgram(exposureProgram == null ? ""
					: exposureProgram);
			picture.setExposureTime(exposureTime == null ? "" : exposureTime);
			picture.setFileDataTime(fileDataTime == null ? "" : fileDataTime);
			picture.setFilename(filename);
			picture.setFileSize(fileSize == null ? "" : fileSize);
			picture.setFileSource(fileSource == null ? "" : fileSource);
			picture.setFileType(fileType == null ? "" : fileType);
			picture.setFlash(flash == null ? "" : flash);
			picture.setFlashPixVersion(flashPixVersion == null ? ""
					: flashPixVersion);
			picture.setFNumber(fNumber == null ? "" : fNumber);
			picture.setFocalLength(focalLength == null ? "" : focalLength);
			picture.setFocalLengthIn35mmFilm(focalLengthIn35mmFilm == null ? ""
					: focalLengthIn35mmFilm);
			picture.setFocusDistance(focusDistance == null ? ""
							: focusDistance);
			picture.setImageDescription(imageDescription == null ? ""
					: imageDescription);
			picture.setIsoSpeedRatings(isoSpeedRatings == null ? ""
					: isoSpeedRatings);
			picture.setLightSource(lightSource == null ? "" : lightSource);
			picture.setMake(make == null ? "" : make);
			picture.setMaxApertureValue(maxApertureValue == null ? ""
					: maxApertureValue);
			picture.setMeteringMode(meteringMode == null ? "" : meteringMode);
			picture.setMimeType(mimeType == null ? "" : mimeType);
			picture.setModel(model == null ? "" : model);
			picture.setOrientation(orientation == null ? "" : orientation);
			picture.setSceneType(sceneType == null ? "" : sceneType);
			picture.setShutterSpeedValue(shutterSpeedValue == null ? ""
					: shutterSpeedValue);
			picture.setSoftware(software == null ? "" : software);
			picture.setThumbnailFileType(thumbnailFileType == null ? ""
					: thumbnailFileType);
			picture.setThumbnailMimeType(thumbnailMimeType == null ? ""
					: thumbnailMimeType);
			picture.setUserComment(userComment == null ? "" : userComment);
			picture.setUserCommentEncoding(userCommentEncoding == null ? ""
					: userCommentEncoding);
			picture.setWhiteBalance(whiteBalance == null ? "" : whiteBalance);
			picture.setXResolution(xResolution == null ? "" : xResolution);
			picture.setYCbCrPositioning(yCbCrPositioning == null ? ""
					: yCbCrPositioning);
			picture.setYResolution(yResolution == null ? "" : yResolution);
			picture.setSrcHeight(srcHeight);
			picture.setSrcWidth(srcWidth);

		return picture;
	}
}
