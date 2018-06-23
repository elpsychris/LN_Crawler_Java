package crawler.utils;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SqlTimeAdapter extends XmlAdapter<String, Timestamp> {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    @Override
    public Timestamp unmarshal(String v) throws Exception {
        Date date = formatter.parse(v);
        return v == null ? null : new Timestamp(date.getTime());
    }

    @Override
    public String marshal(Timestamp v) throws Exception {
        return v == null ? null : formatter.format(new Date(v.getTime()));
    }
}
