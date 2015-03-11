package com.bwinparty.sogei.pgda.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mcipri on 28/08/14.
 */
public abstract class CsvFileProcessorAbs {

    protected CSVFormat format = null;
    protected CSVPrinter printer = null;
    protected CSVParser parser = null;
    protected String[] fields = null;

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    protected String prefix = "";
    protected String urlEndPoint;

    abstract protected void process(CliCmdParser cmd) throws Exception;


    public void  setUrlEndPoint(String urlEndPoint) throws Exception {
        this.urlEndPoint = urlEndPoint;

    }

    public void  setParser(String filePath, char separator, String[] fields) throws Exception {
        format = CSVFormat.RFC4180.withHeader().withDelimiter(separator);
        parser = new CSVParser(new FileReader(filePath), format);
        this.fields = fields;

    }

    public void setCsvPrinter(String filePath) throws Exception {
        //printer = new CSVPrinter(System.out, format.withDelimiter(','));
        printer = new CSVPrinter(new FileWriter(filePath), CSVFormat.TDF.withHeader().withDelimiter(';'));

    }

    public void trace(String esitoRichiesta, String idTransazione, String timeStampResponse) throws Exception {
        List<String> empData = new ArrayList<String>();
        empData.add(esitoRichiesta);
        empData.add(idTransazione);
        empData.add(timeStampResponse);
        empData.add(new Date().toString());
        printer.printRecord(empData);
    }

    public void close() throws Exception {
        //close the printer
        parser.close();
        printer.close();
    }
}
