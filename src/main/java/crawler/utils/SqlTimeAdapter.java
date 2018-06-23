package crawler.utils;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.sql.Timestamp;
import java.util.Date;

public class SqlDateTimeAdapter extends XmlAdapter<Date, Timestamp> {
    @Override
    public java.sql.Date unmarshal(Date v) throws Exception {
        return v == null ? null : new java.sql.Date(v.getTime());
    }

    @Override
    public Date marshal(java.sql.Date v) throws Exception {
        return v == null ? null : new Date(v.getTime());
    }
}
