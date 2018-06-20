package crawler.utils;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Date;

public class SqlDateAdapter extends XmlAdapter<Date, java.sql.Date> {
    @Override
    public java.sql.Date unmarshal(Date v) throws Exception {
        return v == null ? null : new java.sql.Date(v.getTime());
    }

    @Override
    public Date marshal(java.sql.Date v) throws Exception {
        return v == null ? null : new Date(v.getTime());
    }
}
