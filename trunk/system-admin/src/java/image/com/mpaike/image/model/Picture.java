package com.mpaike.image.model;

import java.util.Date;

import com.mpaike.core.database.hibernate.AnnotationObjectKey;


public class Picture implements  java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static int STATUS_NORMAL = 1;//图片显示状态
	public final static String SCREEN_H = "H";//横horizontal 
	public final static String SCREEN_V = "V";//竖 vertical
	public final static String SCREEN_T = "T";//平铺 tiled
	@AnnotationObjectKey
	private Long id;
	private Long userId;
	private Long hotNum;//点击数
	private Long resourceType;//资源等级
	private String photoMd5;
	private String sourceName;
	private String gene;
	private String type;
	private String put;//摆放 横horizontal 竖 vertical
	//文件信息	-----------------------------
	private String filename;//文件名
	private String fileType;//文件类型
	private String mimeType;//文件格式
	private String fileSize;//文件大小
	private String fileDataTime;//时间戳
	private Integer srcHeight;//图像高度（像素）
	private Integer srcWidth;//图像宽度（像素）
	private Integer status;//照片状态
	private String path;//路径
	private String url;//源路径
	//图像信息	-----------------------------
    private String imageDescription;//图片说明
    private String make;//制造商 
    private String model;//型号
    private String orientation;//方向
    private String xResolution;//水平分辨率
    private String yResolution;//垂直分辨率
    private String software;//创建软件
    private String dateTime;//修改时间
    private String artist;//作者
    private String yCbCrPositioning;//YCbCr位置控制
    private String copyright;//版权
    private String copyrightOfPhotographer;//摄影版权
    private String copyrightOfEditor;//编辑版权
    //拍摄信息	-----------------------------
    private String exifVersion;//Exif版本
    private String flashPixVersion;//FlashPix版本
    private String dateTimeOriginal;//拍摄时间
    private String dateTimeDigitized;//数字化时间
    private String computedHeight;//拍摄分辨率高
    private String computedWidth;//拍摄分辨率宽
    private String apertureValue;//光圈
    private String shutterSpeedValue;//快门速度
    private String apertureFNumber;//快门光圈
    private String maxApertureValue;//最大光圈值
    private String exposureTime;//曝光时间
    private String fNumber;//F-Number
    private String meteringMode;//测光模式
    private String lightSource;//光源
    private String flash;//闪光灯
    private String exposureMode;//曝光模式
    private String whiteBalance;//白平衡
    private String exposureProgram;//曝光程序
    private String exposureBiasValue;//曝光补偿
    private String isoSpeedRatings;//ISO感光度
    private String componentsConfiguration;//分量配置
    private String compressedBitsPerPixel;//ͼ图像压缩率
    private String focusDistance;//对焦距离
    private String focalLength;//焦距
    private String focalLengthIn35mmFilm;//等价35mm焦距
    private String userCommentEncoding;//用户注释编码
    private String userComment;//用户注释
    private String colorSpace;//色彩空间
    private String exifImageLength;//Exif图像宽度
    private String exifImageWidth;//Exif图像高度
    private String fileSource;//文件来源
    private String sceneType;//场景类型
    private String thumbnailFileType;//缩略图文件格式
    private String thumbnailMimeType;//缩略图Mime格式
    private Date storeDate;	//存储时间
    //gps信息	-----------------------------
    private boolean gpsinfo;
    private double lat = 0.0;//纬度
    private double lon = 0.0;//经度
    private double alt = 0.0;
    
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileDataTime() {
		return fileDataTime;
	}
	public void setFileDataTime(String fileDataTime) {
		this.fileDataTime = fileDataTime;
	}
	public String getImageDescription() {
		return imageDescription;
	}
	public void setImageDescription(String imageDescription) {
		this.imageDescription = imageDescription;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getOrientation() {
		return orientation;
	}
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	public String getXResolution() {
		return xResolution;
	}
	public void setXResolution(String resolution) {
		xResolution = resolution;
	}
	public String getYResolution() {
		return yResolution;
	}
	public void setYResolution(String resolution) {
		yResolution = resolution;
	}
	public String getSoftware() {
		return software;
	}
	public void setSoftware(String software) {
		this.software = software;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getYCbCrPositioning() {
		return yCbCrPositioning;
	}
	public void setYCbCrPositioning(String cbCrPositioning) {
		yCbCrPositioning = cbCrPositioning;
	}
	public String getCopyright() {
		return copyright;
	}
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	public String getCopyrightOfPhotographer() {
		return copyrightOfPhotographer;
	}
	public void setCopyrightOfPhotographer(String copyrightOfPhotographer) {
		this.copyrightOfPhotographer = copyrightOfPhotographer;
	}
	public String getCopyrightOfEditor() {
		return copyrightOfEditor;
	}
	public void setCopyrightOfEditor(String copyrightOfEditor) {
		this.copyrightOfEditor = copyrightOfEditor;
	}
	public String getExifVersion() {
		return exifVersion;
	}
	public void setExifVersion(String exifVersion) {
		this.exifVersion = exifVersion;
	}
	public String getFlashPixVersion() {
		return flashPixVersion;
	}
	public void setFlashPixVersion(String flashPixVersion) {
		this.flashPixVersion = flashPixVersion;
	}
	public String getDateTimeOriginal() {
		return dateTimeOriginal;
	}
	public void setDateTimeOriginal(String dateTimeOriginal) {
		this.dateTimeOriginal = dateTimeOriginal;
	}
	public String getDateTimeDigitized() {
		return dateTimeDigitized;
	}
	public void setDateTimeDigitized(String dateTimeDigitized) {
		this.dateTimeDigitized = dateTimeDigitized;
	}
	public String getComputedHeight() {
		return computedHeight;
	}
	public void setComputedHeight(String computedHeight) {
		this.computedHeight = computedHeight;
	}
	public String getComputedWidth() {
		return computedWidth;
	}
	public void setComputedWidth(String computedWidth) {
		this.computedWidth = computedWidth;
	}
	public String getApertureValue() {
		return apertureValue;
	}
	public void setApertureValue(String apertureValue) {
		this.apertureValue = apertureValue;
	}
	public String getShutterSpeedValue() {
		return shutterSpeedValue;
	}
	public void setShutterSpeedValue(String shutterSpeedValue) {
		this.shutterSpeedValue = shutterSpeedValue;
	}
	public String getApertureFNumber() {
		return apertureFNumber;
	}
	public void setApertureFNumber(String apertureFNumber) {
		this.apertureFNumber = apertureFNumber;
	}
	public String getMaxApertureValue() {
		return maxApertureValue;
	}
	public void setMaxApertureValue(String maxApertureValue) {
		this.maxApertureValue = maxApertureValue;
	}
	public String getExposureTime() {
		return exposureTime;
	}
	public void setExposureTime(String exposureTime) {
		this.exposureTime = exposureTime;
	}
	public String getFNumber() {
		return fNumber;
	}
	public void setFNumber(String number) {
		fNumber = number;
	}
	public String getMeteringMode() {
		return meteringMode;
	}
	public void setMeteringMode(String meteringMode) {
		this.meteringMode = meteringMode;
	}
	public String getLightSource() {
		return lightSource;
	}
	public void setLightSource(String lightSource) {
		this.lightSource = lightSource;
	}
	public String getFlash() {
		return flash;
	}
	public void setFlash(String flash) {
		this.flash = flash;
	}
	public String getExposureMode() {
		return exposureMode;
	}
	public void setExposureMode(String exposureMode) {
		this.exposureMode = exposureMode;
	}
	public String getWhiteBalance() {
		return whiteBalance;
	}
	public void setWhiteBalance(String whiteBalance) {
		this.whiteBalance = whiteBalance;
	}
	public String getExposureProgram() {
		return exposureProgram;
	}
	public void setExposureProgram(String exposureProgram) {
		this.exposureProgram = exposureProgram;
	}
	public String getExposureBiasValue() {
		return exposureBiasValue;
	}
	public void setExposureBiasValue(String exposureBiasValue) {
		this.exposureBiasValue = exposureBiasValue;
	}
	public String getIsoSpeedRatings() {
		return isoSpeedRatings;
	}
	public void setIsoSpeedRatings(String isoSpeedRatings) {
		this.isoSpeedRatings = isoSpeedRatings;
	}
	public String getComponentsConfiguration() {
		return componentsConfiguration;
	}
	public void setComponentsConfiguration(String componentsConfiguration) {
		this.componentsConfiguration = componentsConfiguration;
	}
	public String getCompressedBitsPerPixel() {
		return compressedBitsPerPixel;
	}
	public void setCompressedBitsPerPixel(String compressedBitsPerPixel) {
		this.compressedBitsPerPixel = compressedBitsPerPixel;
	}
	public String getFocusDistance() {
		return focusDistance;
	}
	public void setFocusDistance(String focusDistance) {
		this.focusDistance = focusDistance;
	}
	public String getFocalLength() {
		return focalLength;
	}
	public void setFocalLength(String focalLength) {
		this.focalLength = focalLength;
	}
	public String getFocalLengthIn35mmFilm() {
		return focalLengthIn35mmFilm;
	}
	public void setFocalLengthIn35mmFilm(String focalLengthIn35mmFilm) {
		this.focalLengthIn35mmFilm = focalLengthIn35mmFilm;
	}
	public String getUserCommentEncoding() {
		return userCommentEncoding;
	}
	public void setUserCommentEncoding(String userCommentEncoding) {
		this.userCommentEncoding = userCommentEncoding;
	}
	public String getUserComment() {
		return userComment;
	}
	public void setUserComment(String userComment) {
		this.userComment = userComment;
	}
	public String getColorSpace() {
		return colorSpace;
	}
	public void setColorSpace(String colorSpace) {
		this.colorSpace = colorSpace;
	}
	public String getExifImageLength() {
		return exifImageLength;
	}
	public void setExifImageLength(String exifImageLength) {
		this.exifImageLength = exifImageLength;
	}
	public String getExifImageWidth() {
		return exifImageWidth;
	}
	public void setExifImageWidth(String exifImageWidth) {
		this.exifImageWidth = exifImageWidth;
	}
	public String getFileSource() {
		return fileSource;
	}
	public void setFileSource(String fileSource) {
		this.fileSource = fileSource;
	}
	public String getSceneType() {
		return sceneType;
	}
	public void setSceneType(String sceneType) {
		this.sceneType = sceneType;
	}
	public String getThumbnailFileType() {
		return thumbnailFileType;
	}
	public void setThumbnailFileType(String thumbnailFileType) {
		this.thumbnailFileType = thumbnailFileType;
	}
	public String getThumbnailMimeType() {
		return thumbnailMimeType;
	}
	public void setThumbnailMimeType(String thumbnailMimeType) {
		this.thumbnailMimeType = thumbnailMimeType;
	}
	public Integer getSrcHeight() {
		return srcHeight;
	}
	public void setSrcHeight(Integer srcHeight) {
		this.srcHeight = srcHeight;
	}
	public Integer getSrcWidth() {
		return srcWidth;
	}
	public void setSrcWidth(Integer srcWidth) {
		this.srcWidth = srcWidth;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getPhotoMd5() {
		return photoMd5;
	}
	public void setPhotoMd5(String photoMd5) {
		this.photoMd5 = photoMd5;
	}
	public String getxResolution() {
		return xResolution;
	}
	public void setxResolution(String xResolution) {
		this.xResolution = xResolution;
	}
	public String getyResolution() {
		return yResolution;
	}
	public void setyResolution(String yResolution) {
		this.yResolution = yResolution;
	}
	public String getyCbCrPositioning() {
		return yCbCrPositioning;
	}
	public void setyCbCrPositioning(String yCbCrPositioning) {
		this.yCbCrPositioning = yCbCrPositioning;
	}
	public String getfNumber() {
		return fNumber;
	}
	public void setfNumber(String fNumber) {
		this.fNumber = fNumber;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public String getGene() {
		return gene;
	}
	public void setGene(String gene) {
		this.gene = gene;
	}
	public Long getHotNum() {
		return hotNum;
	}
	public void setHotNum(Long hotNum) {
		this.hotNum = hotNum;
	}
	public Long getResourceType() {
		return resourceType;
	}
	public void setResourceType(Long resourceType) {
		this.resourceType = resourceType;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Date getStoreDate() {
		return storeDate;
	}
	public void setStoreDate(Date storeDate) {
		this.storeDate = storeDate;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPut() {
		return put;
	}
	public void setPut(String put) {
		this.put = put;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public double getAlt() {
		return alt;
	}
	public void setAlt(double alt) {
		this.alt = alt;
	}
	public boolean isGpsinfo() {
		return gpsinfo;
	}
	public void setGpsinfo(boolean gpsinfo) {
		this.gpsinfo = gpsinfo;
	}
	
	
}