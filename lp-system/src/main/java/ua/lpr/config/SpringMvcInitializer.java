package ua.lpr.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SpringMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	private int maxUploadSizeInMb;

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { AppConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}


	@Override
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {

		// upload temp file will put here
		File uploadDirectory = new File(System.getProperty("java.io.tmpdir"));

		try{
			InputStream is = getClass().getClassLoader()
					.getResourceAsStream("application-local.properties");
			Properties properties = new Properties();
			properties.load(is);
			maxUploadSizeInMb = Integer.parseInt(properties.getProperty("setting.files.maxImageSize"));
			maxUploadSizeInMb *= 1048576;
		}catch (IOException | NumberFormatException ex){
			maxUploadSizeInMb = 5 * 1024 * 1024; // 5Mb
		}

		// register a MultipartConfigElement
		MultipartConfigElement multipartConfigElement =
				new MultipartConfigElement(uploadDirectory.getAbsolutePath(),
						maxUploadSizeInMb, maxUploadSizeInMb * 2, maxUploadSizeInMb / 2);

		registration.setMultipartConfig(multipartConfigElement);

	}
	
}