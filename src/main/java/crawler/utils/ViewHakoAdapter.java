package crawler.utils;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Date;

public class ViewHakoAdapter extends XmlAdapter<String, Integer> {


    @Override
    public Integer unmarshal(String v) throws Exception {
        String formatted = v.replaceAll(",","");
        return Integer.parseInt(formatted);
    }

    @Override
    public String marshal(Integer v) throws Exception {
        return v.toString();
    }
}
