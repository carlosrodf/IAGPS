package usac.tesis.com.tesis.utils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by root on 23/04/16.
 */
public class WSCaller {

    private final String URL = "http://ec2-54-186-237-148.us-west-2.compute.amazonaws.com:8080/ws_tesis/tesis?WSDL";
    private final String NAMESPACE = "http://paquete1/";
    private String METHOD_NAME;
    private String SOAP_ACTION;

    private SoapObject request;

    public WSCaller(String METHOD_NAME){
        this.METHOD_NAME = METHOD_NAME;
        this.SOAP_ACTION = this.NAMESPACE + this.METHOD_NAME;
        this.request = new SoapObject(this.NAMESPACE,METHOD_NAME);
    }

    public void addStringParam(String name, String value){
        PropertyInfo p = new PropertyInfo();
        p.name = name;
        p.type = PropertyInfo.STRING_CLASS;
        p.setValue(value);
        this.request.addProperty(p);
    }

    public void addIntParam(String name, int value){
        PropertyInfo p = new PropertyInfo();
        p.name = name;
        p.type = PropertyInfo.INTEGER_CLASS;
        p.setValue(value);
        this.request.addProperty(p);
    }

    public String call(){
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(this.request);
        HttpTransportSE transportSE = new HttpTransportSE(this.URL);

        try {
            transportSE.call(this.SOAP_ACTION,envelope);
            SoapPrimitive result = (SoapPrimitive)envelope.getResponse();
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "#Error";
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return "#Error";
        }
    }
}
