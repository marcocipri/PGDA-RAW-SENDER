package com.bwinparty.sogei.pgda;

import com.bwinparty.sogei.pgda.installed.Sender830;
import com.bwinparty.sogei.pgda.utils.CliCmdParser;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * Created by mcipri on 04/03/15.
 */
public class Main {

    /**
     * 1) parses the command line in order to define operation, http service url, input csvFile, output csvFile
     *
     * @param args
     * @throws Exception
     */

    public static void main(String[] args) throws Exception {
        CliCmdParser cmd = new CliCmdParser(args);
        try {
            System.out.println("m -" + Integer.parseInt(cmd.getMessageType().trim()) + "-\n input " + cmd.getInput()
                    + "\n outPut " + cmd.getOutput()
                    + "\n url " + cmd.getUrl());

            switch (Integer.parseInt(cmd.getMessageType().trim())){
                case 420 : System.out.println(" processing messages 420 ");
                    break;
                case 620 : System.out.println(" processing messages 620 ");
                    break;
                case 830 : System.out.println(" processing messages 830 ");
                    new Sender830().process(cmd);
                    break;
                default : System.out.println("unsuported message type");
                    break;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
