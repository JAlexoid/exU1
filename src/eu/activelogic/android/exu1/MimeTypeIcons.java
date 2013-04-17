package eu.activelogic.android.exu1;

import java.util.HashMap;
import java.util.Map;

public class MimeTypeIcons {

    public static Map<String, String> EXT_MAP = new HashMap<String, String>();

    static {
	EXT_MAP.put(".doc", "application-msword.png");
	EXT_MAP.put(".docm", "application-msword.png");
	EXT_MAP.put(".docx", "application-msword.png");

	EXT_MAP.put(".pdf", "application-pdf.png");

	EXT_MAP.put(".rss", "application-rss+xml.png");

	EXT_MAP.put(".xls", "application-vnd.ms-excel.png");
	EXT_MAP.put(".xlsx", "application-vnd.ms-excel.png");

	EXT_MAP.put(".ppt", "application-vnd.ms-powerpoint.png");
	EXT_MAP.put(".pptx", "application-vnd.ms-powerpoint.png");

	EXT_MAP.put(".sla", "application-vnd.scribus.png");
	EXT_MAP.put(".sla.gz", "application-vnd.scribus.png");

	EXT_MAP.put(".7z", "application-x-7zip.png");

	EXT_MAP.put(".ace", "application-x-ace.png");

	EXT_MAP.put(".tirrent", "application-x-bittorrent.png");

	EXT_MAP.put(".iso", "application-x-cd-image.png");

	EXT_MAP.put(".cue", "application-x-cue.png");

	EXT_MAP.put(".bin", "application-x-executable.png");

	EXT_MAP.put(".flv", "application-x-flash-video.png");

	EXT_MAP.put(".gz", "application-x-gzip.png");

	EXT_MAP.put(".jar", "application-x-jar.png");

	EXT_MAP.put(".exe", "application-x-ms-dos-executable.png");

	EXT_MAP.put(".php", "application-x-php.png");

	EXT_MAP.put(".rar", "application-x-rar.png");

	EXT_MAP.put(".rb", "application-x-ruby.png");

	EXT_MAP.put(".sln", "application-x-sln.png");

	EXT_MAP.put(".tar", "application-x-tar.png");

	EXT_MAP.put(".zip", "application-x-zip.png");

	EXT_MAP.put(".au", "audio-x-generic.png");
	EXT_MAP.put(".pcm", "audio-x-generic.png");
	EXT_MAP.put(".flac", "audio-x-generic.png");

	EXT_MAP.put(".pls", "audio-x-mp3-playlist.png");

	EXT_MAP.put(".aac", "audio-x-mpeg.png");
	EXT_MAP.put(".mp3", "audio-x-mpeg.png");
	EXT_MAP.put(".mpa", "audio-x-mpeg.png");

	EXT_MAP.put(".wma", "audio-x-ms-wma.png");

	EXT_MAP.put(".ogg", "audio-x-vorbis+ogg.png");

	EXT_MAP.put(".waw", "audio-x-wav.png");

	EXT_MAP.put(".deb", "deb.png");

	EXT_MAP.put(".gpg", "encrypted.png");

	EXT_MAP.put(".dll", "extension.png");
	EXT_MAP.put(".crx", "extension.png");

	EXT_MAP.put(".ttf", "font-x-generic.png");
	EXT_MAP.put(".otf", "font-x-generic.png");
	EXT_MAP.put(".afm", "font-x-generic.png");
	EXT_MAP.put(".pfb", "font-x-generic.png");
	EXT_MAP.put(".pcf", "font-x-generic.png");
	EXT_MAP.put(".pcf.gz", "font-x-generic.png");

	EXT_MAP.put(".bmp", "image-bmp.png");

	EXT_MAP.put(".gif", "image-gif.png");

	EXT_MAP.put(".jpg", "image-jpeg.png");
	EXT_MAP.put(".jpeg", "image-jpeg.png");

	EXT_MAP.put(".png", "image-png.png");

	EXT_MAP.put(".tiff", "image-tiff.png");
	EXT_MAP.put(".tif", "image-tiff.png");

	EXT_MAP.put(".eps", "image-x-eps.png");
	EXT_MAP.put(".ps", "image-x-eps.png");

	EXT_MAP.put(".raw", "image-x-generic.png");

	EXT_MAP.put(".ico", "image-x-ico.png");

	EXT_MAP.put(".psd", "image-x-psd.png");

	EXT_MAP.put(".xcf", "image-x-xcf.png");

	EXT_MAP.put(".msg", "message.png");
	EXT_MAP.put(".eml", "message.png");

	EXT_MAP.put(".rpm", "rpm.png");

	EXT_MAP.put(".css", "text-css.png");

	EXT_MAP.put(".html", " text-html.png");
	EXT_MAP.put(".htm", "text-html.png");

	EXT_MAP.put(".txt", "text-plain.png");
	EXT_MAP.put(".text", "text-plain.png");
	EXT_MAP.put(".md", "text-plain.png");
	EXT_MAP.put("INFO", "text-plain.png");

	EXT_MAP.put(".rtf", "text-richtext.png");

	EXT_MAP.put(".bak", "text-x-bak.png");
	EXT_MAP.put("~", "text-x-bak.png");

	EXT_MAP.put(".bibtex", "text-x-bibtex.png");

	EXT_MAP.put("CHANGELOG", "text-x-changelog.png");

	EXT_MAP.put(".hpp", "text-x-c++hdr.png");

	EXT_MAP.put(".h", "text-x-chdr.png");

	EXT_MAP.put("COPYING", "text-x-copying.png");
	EXT_MAP.put("LICENSE", "text-x-copying.png");

	EXT_MAP.put(".c", "text-x-c.png");

	EXT_MAP.put(".cpp", "text-x-c++.png");

	EXT_MAP.put(".tpl", "text-x-generic-template.png");

	EXT_MAP.put(".xhtml", "text-xhtml+xml.png");

	EXT_MAP.put("INSTALLING", "text-x-install.png");
	EXT_MAP.put(".msi", "text-x-install.png");
	EXT_MAP.put("setup.exe", "text-x-install.png");
	EXT_MAP.put("setup.py", "text-x-install.png");

	EXT_MAP.put(".java", "text-x-java.png");

	EXT_MAP.put(".js", "text-x-javascript.png");

	EXT_MAP.put("Makefile", "text-x-makefile.png");
	EXT_MAP.put(".mk", "text-x-makefile.png");
	EXT_MAP.put("build.xml", "text-x-makefile.png");

	EXT_MAP.put(".xml", "text-xml.png");

	EXT_MAP.put(".py", "text-x-python.png");
	EXT_MAP.put(".pyc", "text-x-python.png");

	EXT_MAP.put("README", "text-x-readme.png");

	EXT_MAP.put(".sh", "text-x-script.png");

	EXT_MAP.put(".asm", "text-x-source.png");
	EXT_MAP.put(".pl", "text-x-source.png");
	EXT_MAP.put(".inc", "text-x-source.png");
	EXT_MAP.put(".pm", "text-x-source.png");
	EXT_MAP.put(".rc", "text-x-source.png");
	EXT_MAP.put(".qml", "text-x-source.png");

	EXT_MAP.put(".sql", "text-x-sql.png");

	EXT_MAP.put(".tex", "text-x-tex.png");

	EXT_MAP.put(".vcal", "vcalendar.png");
	EXT_MAP.put(".vcl", "vcalendar.png");

	EXT_MAP.put(".mp4", "video-x-generic.png");
	EXT_MAP.put(".xvid", "video-x-generic.png");
	EXT_MAP.put(".avi", "video-x-generic.png");
	EXT_MAP.put(".divx", "video-x-generic.png");
	EXT_MAP.put(".mpg", "video-x-generic.png");
	EXT_MAP.put(".mpeg", "video-x-generic.png");
	EXT_MAP.put(".mkv", "video-x-generic.png");
	EXT_MAP.put(".webm", "video-x-generic.png");
	EXT_MAP.put(".ogm", "video-x-generic.png");

	EXT_MAP.put(".wmv", "video-x-generic.png");

	EXT_MAP.put(".dia", "x-dia-diagram.png");

	EXT_MAP.put(".vcf", "x-office-address-book.png");
	EXT_MAP.put(".vcard", "x-office-address-book.png");

	EXT_MAP.put(".odt", "x-office-document.png");
	EXT_MAP.put(".sdt", "x-office-document.png");

	EXT_MAP.put(".odg", "x-office-drawing.png");

	EXT_MAP.put(".odp", "x-office-presentation.png");

	EXT_MAP.put(".ods", "x-office-spreadsheet.png");

    }

    public static String findIconForFile(String path) {
	if (path.charAt(path.length() - 1) == '/') {
	    return null;
	}

	String extension = path.lastIndexOf('.') > 0 && path.lastIndexOf('.') < path.length() - 1 ? path.substring(path.lastIndexOf('.')) : "";
	extension = extension.toLowerCase();
	String name = path.lastIndexOf('/') > 0 ? path.substring(path.lastIndexOf('/')) : path;

	if (EXT_MAP.containsKey(name)) {
	    return EXT_MAP.get(name);
	}

	if (EXT_MAP.containsKey(extension)) {
	    return EXT_MAP.get(extension);
	}

	return null;
    }

}
