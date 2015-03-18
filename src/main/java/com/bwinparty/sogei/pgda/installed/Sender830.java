package com.bwinparty.sogei.pgda.installed;

import com.bwinparty.sogei.pgda.utils.CliCmdParser;
import com.bwinparty.sogei.pgda.utils.CsvFileProcessorAbs;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mcipri on 27/01/15.
 */
public class Sender830 extends CsvFileProcessorAbs {


    public void process(CliCmdParser cmd) throws Exception {


        System.out.println(" starting InstalledSoftware");
        //Create the CSVFormat object
        String fields [] = new String[]{"type","code","id","hash"} ;

        this.setParser(cmd.getInput(), ';', fields);
        setCsvPrinter(cmd.getOutput());

        try {
            for (CSVRecord record : parser) {
                System.out.println("--------------------------------------");
                System.out.println("status  idPgad  " + record.get(fields[0]) + " ; " + record.get(fields[1])
                        + ";" + record.get(fields[2]));
                System.out.println("--------------------------------------");

                List<String> empData = new ArrayList<String>();
                String reg = "((<result>)|(</result>))";
                String response = send(cmd.getUrl(), record.get(fields[0]), record.get(fields[1]), record.get(fields[2]), record.get(fields[3]));
                empData.add(record.get(fields[0]));
                empData.add(record.get(fields[1]));
                empData.add(record.get(fields[2]));
                empData.add(record.get(fields[3]));
                String[] result = response.split(reg);
                empData.add(result[1]);
                empData.add(response);

                this.printer.print(empData);
                this.printer.println();
                this.printer.flush();

            }
            close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String  send(String url, String type, String code, String id, String hash ) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String response = "";
        try {
            //String url = "http://10.20.12.25:8282/scc-protocol-bo/cashgame/SoftwareConfigurationDetailsMessage";
            // System.out.println("url: " + url);
            HttpPost httppost = new HttpPost(url);

            //File file = new File(args[0]);

            //InputStreamEntity reqEntity = new InputStreamEntity(
            //        new FileInputStream(file), -1, ContentType.APPLICATION_OCTET_STREAM);
            //reqEntity.setChunked(true);
            // It may be more appropriate to use FileEntity class in this particular
            // instance but we are using a more generic InputStreamEntity to demonstrate
            // the capability to stream out data from any arbitrary source
            //
            // FileEntity entity = new FileEntity(file, "binary/octet-stream");






                httppost.setHeader("Host", "accounts.google.com");
                httppost.setHeader("User-Agent", "testAgent");
                httppost.setHeader("Accept",
                        "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
                httppost.setHeader("Accept-Language", "en-US,en;q=0.5");
                httppost.setHeader("Cookie", "test");
                httppost.setHeader("Connection", "keep-alive");
                httppost.setHeader("Content-Type", "application/json");
                httppost.setEntity(new StringEntity("{\"serviceConcessionaireCode\":\"37\",\"senderConcessionaireCode\":\"15028\",\"proposerConcessionaireCode\":\"15028\",\"gameCode\":\"0\",\"gameType\":\"0\",\"transactionCode\":\"" + new Date().getTime() + "\",\"softwareModuleBeans\":[{\"code\":\""
                        + code + "\",\"type\":\"" + type + "\",\"id\":\""
                        + id + "\",\"hash\":\"" + hash + "\"}]}"));
                //System.out.println("Executing request: " + httppost.getRequestLine());
                CloseableHttpResponse httpResponse = httpclient.execute(httppost);
                try {
                    //System.out.println("----------------------------------------");
                    //System.out.println(response.getStatusLine());



                    BufferedReader rd = new BufferedReader(
                            new InputStreamReader(httpResponse.getEntity().getContent()));

                    StringBuffer result = new StringBuffer();
                    String lineR = "";
                    while ((lineR = rd.readLine()) != null) {
                        result.append(lineR);



                    }

                    response = result.toString();

                    //String[] partsResult = result.toString().split("result");
                    //System.out.println(" result : " + result);

                    //System.out.println(country[0]+";"+country[1]+";"+country[2]+";"+country[3]+";"+ parts[1].replaceAll("<", "").replaceAll(">", "").replaceAll("/", ""));
                    //System.out.println( partsResult[1].replaceAll("<", "").replaceAll(">", "").replaceAll("/", ""));

                    //String[] partsTransactionId = result.toString().split("result");



                    EntityUtils.consume(httpResponse.getEntity());
                } finally {
                    httpResponse.close();
                }

        } finally {
            httpclient.close();
        }
        return response;
    }


}

