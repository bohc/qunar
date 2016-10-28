package com.bhc.net;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.bhc.bean.Day;
import com.bhc.bean.Line;
import com.bhc.bean.Team;
import com.bhc.bean.Vehicle;

public class ParseXmlDo {
	private Line line;
	private Day day;
	private Team team;
	private Vehicle vehicle;

	/**
	 * 读取URL指定的网页内容
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void pxml(String str) {
		SAXReader reader = new SAXReader();
		Document doc = null;

		try {
			doc = reader.read(new ByteArrayInputStream(str.getBytes("utf8")));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if (doc == null) {
			return;
		}
		Element route = doc.getRootElement();
		Element summary = route.element("summary");
		Element teams = route.element("teams");
		Element days = route.element("days");
		Element vehicles = route.element("flights");

		try {
			line.getSummary().setTitle(summary.elementText("title"));
			line.getSummary().setRecommendation(summary.elementText("title"));
			line.getSummary().setTeamno(summary.element("teamno").getText());
			line.getSummary().setResourceid(summary.element("resourceid").getText());
			line.getSummary().setPfunction(summary.element("pfunction").getText());
			line.getSummary().setPcomposition(summary.element("pcomposition").getText());
			line.getSummary().setDay(summary.element("day").getText());
			line.getSummary().setAdvanceday(summary.element("advanceday").getText());
			line.getSummary().setAdvancedaytype(summary.element("advancedaytype").getText());
			line.getSummary().setDeparture(summary.element("departure").getText());
			line.getSummary().setArrive(summary.element("arrive").getText());
			line.getSummary().setArrivetype(summary.element("arrivetype").getText());
			line.getSummary().setDistancetype(summary.element("distancetype").getText());
			line.getSummary().setFreetriptotraffic(summary.element("freetriptotraffic").getText());
			line.getSummary().setFreetripbacktraffic(summary.element("freetripbacktraffic").getText());
			line.getSummary().setImage(summary.element("image").getText());

			Element fun = summary.element("sub_function");
			if (fun != null) {
				line.getSummary().setSubfunction(fun.getText());
			}
			fun = summary.element("ext_function");
			if (fun != null) {
				line.getSummary().setExtfunction(fun.getText());
			}

			for (Iterator featureData = summary.elementIterator("feature"); featureData.hasNext();) {
				Element feature = (Element) featureData.next();
				line.getSummary().getFeature().add(feature.getText());
			}

			line.getSummary().setFeeinclude(summary.element("feeinclude").getText());
			line.getSummary().setFeeexclude(summary.element("feeexclude").getText());
			line.getSummary().setAttention(summary.element("attention").getText());
			try {
				line.getSummary().setTip(summary.element("tip").getText());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			if (days != null) {
				for (Iterator itData = days.elementIterator("day"); itData.hasNext();) {
					Element eday = (Element) itData.next();
					day = new Day();
					day.setDaytitle(eday.attributeValue("daytitle"));
					day.setDaynum(eday.attributeValue("daynum"));
					day.setSightimage(eday.elementText("sightimage"));
					day.setDaydescription(eday.elementText("daydescription"));
					day.setDaytraffic(eday.elementText("daytraffic"));
					day.setStardesc(eday.element("dayhotelstar").attributeValue("stardesc"));
					day.setStarname(eday.element("dayhotelstar").attributeValue("starname"));

					for (Iterator dayData = eday.element("meals").elementIterator("meal"); dayData.hasNext();) {
						Element emeal = (Element) dayData.next();
						if (emeal.attributeValue("mealtype").trim().equals("早")) {
							day.setBreakfirst("早餐");
							day.setBreakfirstdesc(emeal.attributeValue("mealdesc"));
						} else if (emeal.attributeValue("mealtype").trim().equals("中")) {
							day.setLunch("中餐");
							day.setLunchdesc(emeal.attributeValue("mealdesc"));
						} else {
							day.setSupper("晚餐");
							day.setSupperdesc(emeal.attributeValue("mealdesc"));
						}
					}
					line.getDays().add(day);
				}
			}

			if (teams != null) {
				for (Iterator teamData = teams.elementIterator("team"); teamData.hasNext();) {
					Element eteam = (Element) teamData.next();
					team = new Team();
					team.setPricedesc(eteam.attributeValue("pricedesc"));
					team.setAvailablecount(eteam.attributeValue("availablecount"));
					team.setRoomsendprice(eteam.attributeValue("roomsendprice"));
					team.setRoomnum(eteam.attributeValue("roomnum"));
					team.setChildprice(eteam.attributeValue("childprice"));
					team.setAdultprice(eteam.attributeValue("adultprice"));
					team.setQunarprice(eteam.attributeValue("qunarprice"));
					team.setMarketprice(eteam.attributeValue("marketprice"));
					team.setTakeoffdate(eteam.attributeValue("takeoffdate"));
					line.getTeams().add(team);
				}
			}

			try {
				if (vehicles != null) {
					for (Iterator teamData = vehicles.elementIterator("flight"); teamData.hasNext();) {
						Element eteam = (Element) teamData.next();
						vehicle = new Vehicle();
						vehicle.setArrtime(eteam.attributeValue("arrtime"));
						vehicle.setDeptime(eteam.attributeValue("deptime"));
						vehicle.setArrcity(eteam.attributeValue("arrcity"));
						vehicle.setDepcity(eteam.attributeValue("depcity"));
						vehicle.setArrairport(eteam.attributeValue("arrairport"));
						vehicle.setDepairport(eteam.attributeValue("depairport"));
						vehicle.setFlightno(eteam.attributeValue("flightno"));
						vehicle.setFlightype(eteam.attributeValue("flightype"));
						line.getVehicles().add(vehicle);
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			try {
				line.setFreetrippickupairinfo(route.element("freetrippickupairinfo").getText());
				line.setAgelimit(route.element("agelimit").getText());
				line.setSoldout(route.element("soldout").getText());
				line.setEditime(route.element("editime").getText());
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
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
		ParseXmlDo ts = new ParseXmlDo();
		// ts.getHurl("http://s-58391.gotocdn.com/kuxun_api/CityLineToXmlQnTTL.asp?q=1&f=1&t=3001&id=747");
		ts.getHurl("http://211.149.235.153:8080/qly/px!lxml.do?id=84&linecode=86530100FMFFY08&pname=86530100");
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

}
