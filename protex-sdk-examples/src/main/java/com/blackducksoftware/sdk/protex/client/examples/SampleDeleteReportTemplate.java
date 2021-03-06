package com.blackducksoftware.sdk.protex.client.examples;

import java.util.ArrayList;
import java.util.List;

import com.blackducksoftware.sdk.fault.SdkFault;
import com.blackducksoftware.sdk.protex.client.util.ProtexServerProxy;
import com.blackducksoftware.sdk.protex.report.ReportApi;
import com.blackducksoftware.sdk.protex.report.ReportTemplate;

/**
 * This sample deletes a report template
 *
 * It demonstrates:
 * - How to delete a report template
 */
public class SampleDeleteReportTemplate extends BDProtexSample {

    private static ReportApi reportApi = null;

    /**
     * Output usage information for this sample
     */
    private static void usage() {
        String className = SampleDeleteReportTemplate.class.getSimpleName();

        List<String> parameters = new ArrayList<String>(getDefaultUsageParameters());
        parameters.add("<report template title>");

        List<String> paramDescriptions = new ArrayList<String>(getDefaultUsageParameterDetails());
        paramDescriptions.add(formatUsageDetail("report template title", "The title of the template to delete, i.e. \"A Report Template\""));

        outputUsageDetails(className, parameters, paramDescriptions);
    }

    public static void main(String[] args) throws Exception {
        // check and save parameters
        if (args.length < 4) {
            System.err.println("Not enough parameters!");
            usage();
            System.exit(-1);
        }

        String serverUri = args[0];
        String username = args[1];
        String password = args[2];
        String reportTitle = args[3];

        Long connectionTimeout = 120 * 1000L;

        ProtexServerProxy myProtexServer = null;

        try {
            try {
                myProtexServer = new ProtexServerProxy(serverUri, username, password, connectionTimeout);

                reportApi = myProtexServer.getReportApi();
            } catch (RuntimeException e) {
                System.err.println("Connection to server '" + serverUri + "' failed: " + e.getMessage());
                throw e;
            }

            // Find the report template
            ReportTemplate reportTemplate = reportApi.getReportTemplateByTitle(reportTitle);

            // Call the Api
            try {
                reportApi.deleteReportTemplate(reportTemplate.getReportTemplateId());
            } catch (SdkFault e) {
                System.err.println("deleteReportTemplate() failed: " + e.getMessage());
                throw new RuntimeException(e);
            }

            System.out.println("Report Template Id: " + reportTemplate.getReportTemplateId());
        } catch (Exception e) {
            System.err.println("SampleDeleteReportTemplate failed");
            e.printStackTrace(System.err);
            System.exit(-1);
        } finally {
            // This is optional - it causes the proxy to overwrite the stored password with null characters, increasing
            // security
            if (myProtexServer != null) {
                myProtexServer.close();
            }
        }
    }

}
