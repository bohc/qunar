package com.bhc.net;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.cookie.Cookie;
import org.apache.http.util.EntityUtils;

public class FetchFly {
	public static String cookie = "";
	public static List<Cookie> cookies = new ArrayList<Cookie>();

	public static void main(String[] args) throws ClientProtocolException, IOException {
		Map<String, String> parameters = new HashMap<String, String>();
		Result ri = null;
		
		ri = getLine3(parameters, "http://www.qunar.com/twell/cookie/allocateCookie.jsp");
		if (ri.getStatusCode() == 200) {
			cookie = ri.getCookie();
			for (Cookie c : ri.getCookies()) {
				cookies.add(c);
			}
		}

		ri = getLine3(parameters, "http://user.qunar.com/passport/addICK.jsp");
		if (ri.getStatusCode() == 200 || ri.getStatusCode() == 302) {
			cookie = ri.getCookie();
			for (Cookie c : ri.getCookies()) {
				cookies.add(c);
			}
		}
		
		ri = getLine3(parameters, "http://flight.qunar.com/twell/longwell?&http%3A%2F%2Fwww.travelco.com%2FsearchArrivalAirport=%E6%98%86%E6%98%8E&http%3A%2F%2Fwww.travelco.com%2FsearchDepartureAirport=%E5%8C%97%E4%BA%AC&http%3A%2F%2Fwww.travelco.com%2FsearchDepartureTime=2015-04-19&http%3A%2F%2Fwww.travelco.com%2FsearchReturnTime=2015-04-19&locale=zh&nextNDays=0&searchLangs=zh&searchType=OneWayFlight&tags=1&mergeFlag=0&xd=f1429247780000&wyf=yvh4HT2KdK5%2BQq1HWc5%2BaqQKyhx49TxHyZh%2BK0sWvax4K08H%7C1429241193340&from=flight_dom_search&_token=87911");
		if (ri.getStatusCode() == 200 || ri.getStatusCode() == 302) {
			cookie = ri.getCookie();
			for (Cookie c : ri.getCookies()) {
				cookies.add(c);
			}
		}

//		ri = getLine4(parameters, "http://ws.qunar.com/lowerPrice.jcp?&callback=DomesticLowPriceHome.showLowPrice");
//		if (ri.getStatusCode() == 200 || ri.getStatusCode() == 302) {
//			cookie = ri.getCookie();
//			for (Cookie c : ri.getCookies()) {
//				cookies.add(c);
//			}
//		}
//		
//		ri = getLine4(parameters, "http://ws.qunar.com/ips.jcp?callback=QNR.ips.callback&_="+System.currentTimeMillis());
//		if (ri.getStatusCode() == 200 || ri.getStatusCode() == 302) {
//			cookie = ri.getCookie();
//			for (Cookie c : ri.getCookies()) {
//				cookies.add(c);
//			}
//		}
//		
//		ri = getLine4(parameters, "http://ws.qunar.com/lpisearchd?callback=jQuery1720786908564487641_1429240117904&from=&to=Hot&month=All&cachefrom=&cacheto=&type=ow&source=fhome&_="+System.currentTimeMillis());
//		if (ri.getStatusCode() == 200 || ri.getStatusCode() == 302) {
//			cookie = ri.getCookie();
//			for (Cookie c : ri.getCookies()) {
//				cookies.add(c);
//			}
//		}
//		
//		ri = getLine4(parameters, "http://ws.qunar.com/airCoPromote.jsp?airCoList=CA,SC,CZ&num=5&callback=AirLineDeals._parseData");
//		if (ri.getStatusCode() == 200 || ri.getStatusCode() == 302) {
//			cookie = ri.getCookie();
//			for (Cookie c : ri.getCookies()) {
//				cookies.add(c);
//			}
//		}
		
		parameters.clear();
		parameters.put("callback", "jQuery172044801428948638994_1428984159619");
		parameters.put("depCity", "北京");
		parameters.put("_", System.currentTimeMillis() + "");

		while (true) {
			ri = getLine3(parameters, null);
			if (ri.getCookie() != null && ri.getCookie().indexOf("JSESSIONID") != -1) {
				if (ri.getStatusCode() == 200 || ri.getStatusCode()==302 ) {
					cookie = ri.getCookie();
					// cookies.clear();
					for (Cookie c : ri.getCookies()) {
						cookies.add(c);
					}
				}
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		
		//http://flight.qunar.com/twell/flight/tags/onewayflight_groupdata.jsp?&departureCity=北京&arrivalCity=昆明&departureDate=2015-04-19&returnDate=2015-04-19&nextNDays=0&searchType=OneWayFlight&searchLangs=zh&locale=zh&from=flight_dom_search&queryID=10.86.213.159:d672e2a:14cc59e3772:491a&serverIP=7yZ694Thvg8evM6tfmeo4bL69tc9ZCehtct2m5vOjtQSBpuEVQgsmA==&status=1429247796429&_token=74087&deduce=true
		parameters.clear();
		parameters.put("departureCity", "北京");
		parameters.put("arrivalCity", "昆明");
		parameters.put("departureDate", "2015-04-16");
		parameters.put("returnDate", "2015-04-16");
		parameters.put("nextNDays", "0");
		parameters.put("searchType", "OneWayFlight");
		parameters.put("searchLangs", "zh");
		parameters.put("locale", "zh");
		parameters.put("from", "flight_dom_search");
		parameters.put("queryID", "10.86.213.174:15987531:14cb637326d:559e");
		parameters.put("serverIP", "BfA6IagvfM/6F3JLRUGy5LcZtD0WwMmvphiyJtC52aL1kAn1N+onuQ==");
		parameters.put("status", "1428988700767");
		parameters.put("_token", "74314");
		ri = getLine4(parameters, "http://flight.qunar.com/twell/flight/tags/onewayflight_groupdata.jsp");
		if (ri.getStatusCode() == 200 || ri.getStatusCode() == 302) {
			cookie = ri.getCookie();
			// cookies.clear();
			for (Cookie c : ri.getCookies()) {
				cookies.add(c);
			}
		}
		System.out.println(cookie);
	}

	public static void getLine() {
		// http://flight.qunar.com/twell/flight/OneWayFlight_Info.jsp?&departureCity=北京&arrivalCity=昆明&departureDate=2015-04-16&returnDate=2015-04-16&nextNDays=0
		// &searchType=OneWayFlight&searchLangs=zh&locale=zh&from=flight_dom_search&queryID=10.86.213.174:15987531:14cb637326d:559e&serverIP=BfA6IagvfM/6F3JLRUGy5LcZtD0WwMmvphiyJtC52aL1kAn1N+onuQ==&status=1428988700767&_token=74314
		Map<String, String> parameters = new HashMap<String, String>();
		

		String requrl = "http://flight.qunar.com/twell/flight/OneWayFlight_Info.jsp";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Cookie", cookie);
		try {
			Result r = SendRequest.sendPost(requrl, headers, parameters, "utf-8");
			HttpEntity entity = r.getHttpEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				try {
					int i = -1;
					byte[] b = new byte[1024];
					StringBuffer sb = new StringBuffer();
					while ((i = instream.read(b)) != -1) {
						sb.append(new String(b, 0, i, "utf-8"));
					}
					String content = sb.toString();
					System.out.println(content);
				} finally {
					instream.close();
				}
			}
			EntityUtils.consume(entity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void getLine2() {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("http://www.travelco.com/searchDepartureAirport", "北京");
		parameters.put("http://www.travelco.com/searchArrivalAirport", "昆明");
		parameters.put("http://www.travelco.com/searchDepartureTime", "2015-04-16");
		parameters.put("http://www.travelco.com/searchReturnTime", "2015-04-16");
		parameters.put("nextNDays", "0");
		parameters.put("searchType", "OneWayFlight");
		parameters.put("searchLangs", "zh");
		parameters.put("locale", "zh");
		parameters.put("tags", "1");
		parameters.put("mergeFlag", "0");
		parameters.put("xd", "f1428988700000");
		parameters.put("wyf", "KBx+WUuOKUuOMQVMQ4lFMQYZKCaOMUuxKWbORlUpRuB+Rl7x|1428987988451");
		parameters.put("from", "flight_dom_search");
		parameters.put("_token", "88640");

		String requrl = "http://flight.qunar.com/twell/longwell";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Cookie", cookie);
		try {
			Result r = SendRequest.sendPost(requrl, headers, parameters, "utf-8");
			HttpEntity entity = r.getHttpEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				try {
					int i = -1;
					byte[] b = new byte[1024];
					StringBuffer sb = new StringBuffer();
					while ((i = instream.read(b)) != -1) {
						sb.append(new String(b, 0, i, "utf-8"));
					}
					String content = sb.toString();
					System.out.println(content);
				} finally {
					instream.close();
				}
			}
			EntityUtils.consume(entity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Result getLine3(Map<String, String> parameters, String url) {
		String requrl = "http://flight.qunar.com/twell/flight/localDate.jsp";
		if (url == null) {
			url = requrl;
		}
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Cookie", cookie);
		if (cookies != null && cookies.size() > 0) {
			for (Cookie c : cookies) {
				headers.put(c.getName(), c.getValue());
			}
		}
		Result r = null;
		try {
			r = SendRequest.sendPost(url, headers, parameters, "utf-8");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return r;
	}

	public static Result getLine4(Map<String, String> parameters, String url) {
		// http://flight.qunar.com/twelli/flight/localDate.jsp?&depCity=北京&callback=XQScript_8
		// Map<String, String> parameters = new HashMap<String, String>();
		// parameters.put("depCity", "北京");
		// parameters.put("callback", "XQScript_8");

		String requrl = "http://flight.qunar.com/twelli/flight/localDate.jsp";

		if (url == null) {
			url = requrl;
		}
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Cookie", cookie);
		if (cookies != null && cookies.size() > 0) {
			for (Cookie c : cookies) {
				headers.put(c.getName(), c.getValue());
			}
		}
		Result r = null;
		try {
			r = SendRequest.sendPost(url, headers, parameters, "utf-8");
			HttpEntity entity = r.getHttpEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				try {
					int i = -1;
					byte[] b = new byte[1024];
					StringBuffer sb = new StringBuffer();
					while ((i = instream.read(b)) != -1) {
						sb.append(new String(b, 0, i, "utf-8"));
					}
					String content = sb.toString();
					System.out.println(content);
				} finally {
					instream.close();
				}
			}
			EntityUtils.consume(entity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return r;
	}
}
