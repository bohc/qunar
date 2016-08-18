package com.bhc.util;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class TableControl {
	public static Map<String, Object> analisyTableColunsInfo(String tname) {

		Map<String,Object> tcmap=new HashMap();
		Map<String, ColumnsDesc> mcds = new HashMap<String, ColumnsDesc>();
		SAXReader reader = new SAXReader();
		Document doc = null;

		try {
//			String f=ClassLoaderUtil.getExtendResource("tabledescs.xml").toString();
			String f=System.getProperty("user.dir")+"/tabledescs.xml";
			System.out.println(f);
//			f=f.replace("file:/", "");
			doc = reader.read(new File(f));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		List list = doc.getRootElement().elements();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				Element s = (Element) list.get(i);
				if (s.attributeValue("name").equals(tname)) {
					List ts = s.elements();
					if (ts.size() > 0) {
						final Map<String, String> lmap = new HashMap<String, String>();
						String[] cp=new String[ts.size()];
						String[] fields = new String[ts.size()];
						for (int j = 0; j < ts.size(); j++) {
							Element ec = (Element) ts.get(j);
							ColumnsDesc cdc = new ColumnsDesc();
							boolean isshow=Boolean.parseBoolean(ec.attributeValue("cisshow"));
							if(!isshow){
								cdc.setCwidth("0");
							}else{
								cdc.setCwidth(ec.attributeValue("cwidth"));
							}
							cdc.setCcode(ec.attributeValue("ccode"));
							cdc.setCname(ec.attributeValue("cname"));
							cdc.setCisshow(ec.attributeValue("cisshow"));
							cdc.setCtype(ec.attributeValue("ctype"));
							cdc.setCtitle(ec.attributeValue("ctitle"));
							cdc.setCisedit(ec.attributeValue("cisedit"));
							mcds.put(cdc.getCcode(), cdc);
							
							cp[j]=cdc.getCcode();
							fields[j]=cdc.getCname();
							lmap.put(cdc.getCcode(), cdc.getCname());
						}
						tcmap.put("mcds", mcds);
						tcmap.put("lmap", lmap);
						tcmap.put("cp", cp);
						tcmap.put("fields", fields);
					}
				}
			}
		}
		return tcmap;
	}
}
