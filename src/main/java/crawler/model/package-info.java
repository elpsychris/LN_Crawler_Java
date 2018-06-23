@XmlSchema(
        elementFormDefault = XmlNsForm.QUALIFIED,
        namespace = "http://t3.com/2018/project-page",
        xmlns = {@XmlNs(prefix = "t",
                namespaceURI = "http://t3.com/2018/xml"),
                @XmlNs(prefix = "pr",
                        namespaceURI = "http://t3.com/2018/project"),
                @XmlNs(prefix = "up",
                        namespaceURI = "http://t3.com/2018/update")}
)
package crawler.model;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;
