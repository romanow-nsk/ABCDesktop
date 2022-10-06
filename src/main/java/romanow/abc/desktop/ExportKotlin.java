package romanow.abc.desktop;

import romanow.abc.core.ErrorList;
import romanow.abc.core.constants.ValuesBase;
import romanow.abc.core.mongo.KotlinJSConverter;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class ExportKotlin {
    public static void main(String aa[]){
        ValuesBase.init();
        ErrorList errorList = new ErrorList();
        KotlinJSConverter.createKotlinClassSources(errorList);
        try {
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("ExportKotlinErrors.kt"), "UTF-8");
            out.write(errorList.toString());
            out.flush();
            out.close();
            } catch (Exception ee){
                System.out.println("ExportKotlinErrors.kt: "+ee.toString());
                }

        }
}
