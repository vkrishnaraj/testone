package com.bagnet.nettracer.reporting;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.fill.JRFileVirtualizer;

import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerProperties;

public class PPLCReport {
	
	public static String createReport(IncidentForm theform, ServletContext sc,
			HttpServletRequest request, String language) throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		parameters.put("paxName", "Noah Rawlins");
		
		parameters.put("REPORT_RESOURCE_BUNDLE", ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(language)));
		File logo = new File(sc.getRealPath("/") + "reports/images/logo.gif");
		if (logo.exists()) {
			parameters.put("logo", logo.getAbsolutePath());
		}
		
		File grid1 = new File(sc.getRealPath("/") + "reports/images/grid1.gif");
		if (grid1.exists()) {
			parameters.put("grid1", grid1.getAbsolutePath());
		}
		
		File grid2 = new File(sc.getRealPath("/") + "reports/images/grid2.gif");
		if (grid2.exists()) {
			parameters.put("grid2", grid2.getAbsolutePath());
		}
		
		File grid3 = new File(sc.getRealPath("/") + "reports/images/grid3.gif");
		if (grid3.exists()) {
			parameters.put("grid3", grid3.getAbsolutePath());
		}
		
		List<JasperPrint> jPrints = new ArrayList<JasperPrint>();
		
		ReportBMO rbmo = new ReportBMO(request);
		String rootPath = sc.getRealPath("/");

		jPrints.add(rbmo.getJasperPrint(parameters, "PPLC1", rootPath, null));
		jPrints.add(rbmo.getJasperPrint(parameters, "PPLC2", rootPath, null));
		
		String outfile = "PPLC" + "_" + (new SimpleDateFormat("MMddyyyyhhmmss").format(TracerDateTime.getGMTDate()));
		
		int outputtype;
		
		if (!TracerProperties.isTrue(TracerProperties.SUPPRESSION_PRINTING_NONHTML)) {
			outputtype = TracingConstants.REPORT_OUTPUT_PDF;
			outfile += ".pdf";
		} else {
			outfile += ".html";
			outputtype = TracingConstants.REPORT_OUTPUT_HTML;
			request.setAttribute("outputtype", Integer.toString(TracingConstants.REPORT_OUTPUT_HTML));
		}
		
		String outputpath = rootPath + ReportingConstants.REPORT_TMP_PATH + outfile;
		JRExporter exporter = null;
		
		if (outputtype == TracingConstants.REPORT_OUTPUT_PDF) {
			//JasperExportManager.exportReportToPdfFile(jPrints.get(0), outputpath);
			exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jPrints);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputpath);
			exporter.exportReport();
		}
		else {
			exporter = new JRHtmlExporter();

			Map imagesMap = new HashMap();
			
			exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML, "<div style=\"page-break-after: always\" border=\"0\">&nbsp;</div>");
			request.getSession().setAttribute("IMAGES_MAP", imagesMap);
			exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP, imagesMap);
			exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "image?image=");

			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jPrints);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputpath);
			//exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
			// Boolean.TRUE);
			exporter.exportReport();
		}
		return outfile;
		
	}

}
