package com.bhc.net;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.bhc.bean.Day;
import com.bhc.bean.Line;
import com.bhc.bean.Team;

public class ParseXmlDoNew {
	private Line line;
	private Day day;
	private Team team;

	/**
	 * ��ȡURLָ������ҳ����
	 * 
	 * @throws IOException
	 */
	public void getString(String hurl, StringBuffer sb) throws IOException {
		URL url = new URL(hurl);
		Reader reader = new InputStreamReader(new BufferedInputStream(url.openStream()), "utf8");
		int c;
		while ((c = reader.read()) != -1) {
			sb.append((char) c);
		}
		reader.close();
	}

	public void pxml(String str) {
		SAXReader reader = new SAXReader();
		Document doc = null;

		try {
			doc = reader.read(new ByteArrayInputStream(str.getBytes("utf8")));
		} catch (Exception e) {
			e.printStackTrace();
		}

		Element root = doc.getRootElement();
		Element route = root.element("route");
		Element images = route.element("images");
		// Element teams = route.element("teams");
		Element dailytrips = route.element("daily_trips");
		Element sights = route.element("sights");

		try {
			line.getSummary().setTitle(route.elementText("title") + ",���źţ�" + route.elementText("teamno"));
			line.getSummary().setRecommendation(route.elementText("promotion"));
			String teamno = route.elementText("teamno");
			line.getSummary().setTeamno(teamno);
			// line.getSummary().setResourceid(summary.element("resourceid").getText());
			line.getSummary().setPfunction(route.element("pfunction").getText());
			line.getSummary().setPcomposition(route.element("pcomposition").getText());

			line.getSummary().setDay(route.element("day_num").getText());
			line.getSummary().setAdvanceday(route.element("advanceday").getText());
			line.getSummary().setAdvancedaytype(route.element("advancedaytype").getText());
			line.getSummary().setDeparture(route.element("departure").getText());

			StringBuffer sbarrive = new StringBuffer("");
			int f = 0;
			for (Iterator teamData = route.element("cities").elementIterator("city"); teamData.hasNext();) {
				Element ecity = (Element) teamData.next();
				sbarrive.append(ecity.getText()).append(",");
				f++;
			}
			if (f > 0) {
				line.getSummary().setArrive(sbarrive.toString());
			} else {
				sbarrive.setLength(0);
				f = 0;
				for (Iterator sightData = route.element("sights").elementIterator("sight"); sightData.hasNext();) {
					Element sight = (Element) sightData.next();
					for (Iterator sis = sight.elementIterator("sight_alias"); sis.hasNext();) {
						Element si = (Element) sis.next();
						sbarrive.append(si.getText()).append(",");
					}
					f++;
				}
				if (f > 0) {
					line.getSummary().setArrive(sbarrive.toString());
				} else {
					line.getSummary().setArrive(route.elementText("arrive"));
				}
			}

			String ttype = "";
			for (Iterator featureData = route.element("countries").elementIterator("country"); featureData.hasNext();) {
				Element feature = (Element) featureData.next();
				if (feature != null && feature.getText() != null) {
					if (feature.getText().equals("�й�")) {
						ttype = "������";
					} else if (feature.getText().equals("�۰�̨")) {
						ttype = "�۰�̨";
					} else {
						ttype = "������";
					}
				} else {
					ttype = "������";
				}
			}
			line.getSummary().setArrivetype(ttype);

			if (teamno.trim().startsWith("D")) {
				line.getSummary().setDistancetype("��;");
			} else if (teamno.trim().startsWith("F")) {
				line.getSummary().setDistancetype("��;");
			} else {
				line.getSummary().setDistancetype("��;");
			}

			String totraffic = route.elementText("to_traffic");
			if (totraffic == null || totraffic.equals("") || totraffic.indexOf("���") != -1) {
				totraffic = "����";
			}
			line.getSummary().setFreetriptotraffic(totraffic);
			String backtraffic = route.elementText("back_traffic");
			if (backtraffic == null || backtraffic.equals("") || backtraffic.indexOf("���") != -1) {
				backtraffic = "����";
			}
			line.getSummary().setFreetripbacktraffic(backtraffic);

			List<Element> es = images.elements();
			if (es != null && es.size() > 0) {
				StringBuffer sbs = new StringBuffer();
				for (Element e : es) {
					sbs.append(e.getText()).append(",");
				}
				if (sbs.length() > 0) {
					sbs.delete(sbs.length() - 1, sbs.length());
				}
				line.getSummary().setImage(sbs.toString());
			}

			for (Iterator featureData = route.element("features").elementIterator("feature"); featureData.hasNext();) {
				Element feature = (Element) featureData.next();
				line.getSummary().getFeature().add(feature.getText());
			}
			for (Iterator feedata = route.element("fee_includes").elementIterator("fee_include"); feedata.hasNext();) {
				Element fee = (Element) feedata.next();
				line.getSummary().setFeeinclude(fee.getText());
			}
			for (Iterator feedata = route.element("fee_excludes").elementIterator("fee_exclude"); feedata.hasNext();) {
				Element fee = (Element) feedata.next();
				line.getSummary().setFeeexclude(fee.getText());
			}
			for (Iterator attentiondata = route.element("booking_terms").elementIterator("booking_term"); attentiondata.hasNext();) {
				Element attention = (Element) attentiondata.next();
				line.getSummary().setAttention(attention.getText());
			}
			// line.getSummary().setAttention(summary.element("attention").getText());
			try {
				for (Iterator tipsdata = route.element("tips").elementIterator("tip"); tipsdata.hasNext();) {
					Element tip = (Element) tipsdata.next();
					line.getSummary().setTip(tip.getText());
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}

			for (Iterator itData = dailytrips.elementIterator("daily_trip"); itData.hasNext();) {
				Element eday = (Element) itData.next();
				day = new Day();
				day.setDaytitle(eday.elementText("title"));
				day.setDaynum(eday.elementText("day"));
				day.setSightimage(eday.elementText("sightimage"));
				day.setDaytraffic(eday.elementText("traffic"));

				String daydesc = eday.elementText("desc");
				if (daydesc != null) {
					while (true) {
						if (daydesc.endsWith("<br/>")) {
							daydesc = daydesc.substring(0, daydesc.lastIndexOf("<br/>"));
						} else {
							break;
						}
					}
					while (true) {
						if (daydesc.endsWith("<br />&nbsp;&nbsp;")) {
							daydesc = daydesc.substring(0, daydesc.lastIndexOf("<br />&nbsp;&nbsp;"));
						} else {
							break;
						}
					}
				}
				day.setDaydescription(daydesc);

				String hotelinfo = eday.elementText("hotel_name");
				if (hotelinfo != null) {
					if (hotelinfo.indexOf("|") != -1) {
						day.setStardesc(hotelinfo.substring(0, hotelinfo.indexOf("|")));
					} else {
						day.setStardesc(hotelinfo);
					}
					if (hotelinfo.contains("����") || hotelinfo.contains("����")) {
						day.setStarname("���ǻ�ͬ�ȾƵ�");
					} else if (hotelinfo.contains("����")) {
						day.setStarname("���ǻ�ͬ�ȾƵ�");
					} else if (hotelinfo.contains("����")) {
						day.setStarname("���ǻ�ͬ�ȾƵ�");
					} else if (hotelinfo.contains("����")) {
						day.setStarname("���ǻ�ͬ�ȾƵ�");
					} else if (hotelinfo.contains("����")) {
						day.setStarname("���ǻ�ͬ�ȾƵ�");
					} else if (hotelinfo.contains("����")) {
						day.setStarname("���ǻ�ͬ�ȾƵ�");
					} else if (hotelinfo.contains("����")) {
						day.setStarname("���ǻ�ͬ�ȾƵ�");
					} else if (hotelinfo.contains("��ջ")) {
						day.setStarname("��ջ");
					} else if (hotelinfo.contains("ũ��Ժ")) {
						day.setStarname("ũ��Ժ");
					} else if (hotelinfo.contains("�Ƶ�ת��ס��")) {
						day.setStarname("�Ƶ�ת��ס��");
					} else if (hotelinfo.contains("��") || hotelinfo.contains("����") || hotelinfo.contains("�ɻ�") || hotelinfo.contains("�ִ�") || hotelinfo.contains("���")) {
						day.setStarname("ס�ڽ�ͨ������");
					} else if (hotelinfo.contains("����")) {
						day.setStarname("����");
					} else {
						day.setStarname("����");
					}
				} else {
					day.setStardesc("����");
					day.setStarname("����");
				}

				String meal = eday.element("beverage").getText();
				if (meal.length() > 10) {
					meal = meal.substring(meal.indexOf("�òͣ�") + "�òͣ�".length());
					String[] ms = meal.split(" ");
					if (ms != null && ms.length == 3) {
						day.setBreakfirst("���");
						day.setBreakfirstdesc(ms[0].split("��")[1]);
						day.setLunch("�в�");
						day.setLunchdesc(ms[1].split("��")[1]);
						day.setSupper("���");
						day.setSupperdesc(ms[2].split("��")[1]);
					}
				}

				line.getDays().add(day);
			}

			for (Iterator teamData = route.element("route_dates").elementIterator("route_date"); teamData.hasNext();) {
				Element eteam = (Element) teamData.next();
				team = new Team();
				int mprice = Integer.parseInt(eteam.elementText("price"));
				team.setPricedesc(eteam.elementText("price_desc"));
				team.setAvailablecount(eteam.elementText("stock"));
				team.setRoomnum(eteam.elementText("roomnum"));
				team.setChildprice(eteam.elementText("child_price"));
				team.setAdultprice(String.valueOf(mprice));

				int tprice = Integer.parseInt(eteam.elementText("marketprice"));
				tprice = tprice + (new Random().nextInt(10) * 10);
				team.setMarketprice(String.valueOf(tprice));

				team.setRoomsendprice(eteam.elementText("price_diff"));
				team.setTakeoffdate(eteam.elementText("date"));
				line.getTeams().add(team);
			}
			for (Iterator teamData = route.element("booking_terms").elementIterator("booking_term"); teamData.hasNext();) {
				Element eteam = (Element) teamData.next();
				line.setFreetrippickupairinfo(eteam.getText());
			}
			// line.setAgelimit(route.element("agelimit").getText());
			// try {
			// line.setSoldout(route.element("soldout").getText());
			// } catch (Exception e) {
			// System.err.println(e.getMessage());
			// }
			// line.setEditime(route.element("editime").getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getHurl(String turl) {
		StringBuffer sb = new StringBuffer();
		String url = turl;// "http://www.gayosite.com/kuxun_api/CityLineToXmlQnTTL.asp?q=1&f=1&t=3001&id=747";
		line = new Line();
		try {
			getString(url, sb);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (sb.length() > 0) {
			pxml(sb.toString());
		}
	}

	public static void main(String args[]) {
		ParseXmlDoNew ts = new ParseXmlDoNew();
		ts.getHurl("http://www.qly.cc/api/kuxun_api_show.aspx?id=601");
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

}
