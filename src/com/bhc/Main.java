package com.bhc;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import com.bhc.bean.Line;
import com.bhc.bean.LineSeries;
import com.bhc.custom.widget.ButtonAction;
import com.bhc.custom.widget.ComboAction;
import com.bhc.net.HttpLogin;
import com.bhc.provider.ContentProvider;
import com.bhc.provider.ListLabelProvider;
import com.bhc.util.BaseIni;
import com.bhc.util.XmlPersistence;

import org.eclipse.core.databinding.beans.PojoProperties;

public class Main extends ApplicationWindow {
	private DataBindingContext m_bindingContext;
	private Text username;
	private Text password;
	private Text vcode;
	private HttpLogin hl;
	// private HttpLoginTn hltn;
	public static Text log_txt;
	private Text title;
	private Text recommendation;
	private Text advanceday;
	private Text txttitle;
	private Text includefee;
	private Text excludefee;
	private Text kidsprice;
	private Text kidspricedesc;
	private Text storenum;
	private Text minnumtime;
	private Text maxnumtime;
	private Text marketprice;
	private Text adultprice;
	private Text singledistance;
	private Text confeature;
	private Text txtfeeinclude;
	private Text txtfeeexclude;
	private Text txtattention;
	private Text txttip;
	private Text txtviewimgdir;
	private Text txthotelimgdir;
	private Text txtfoodimgdir;
	private Text txtdiscription;
	private Label label;
	private Text b_url;
	private Browser browser;
	public Display display;
	private Table table;
	private Button button_21;
	private Label load_show;
	private Label mainshow;
	private Combo server_phone_com;
	private Composite com_show_phono_txt;
	private Label lab_show_phono_txt;
	private ButtonAction uploadaction, stopuploadaction, saveuploadaction, loaduploadaction;
	private Combo scombo;
	private ComboAction cselect;

	private ArrayList myListeners = new ArrayList();
	// private WritableList input;
	// private TableViewer tableViewer;
	// private CheckboxTableViewer ctv;

	private boolean globalisover = false;
	private int postnum = 0;
	private int selectnum = 0;
	private Text spic1;
	private Text spic2;
	private Text spic3;
	private Text spic4;
	private Text spic5;
	private Text spic6;
	private Group group_5, group_1;
	private Text mupath, lupath;
	private ArrayList<String> plist = new ArrayList<String>();

	private Button bt_c1, bt_c2;
	private Button bt_c1_up, bt_c1_noeditmainimg, bt_c1_noeditimgs, l_bc, l_dc, l_pc, l_oc;
	private Button bt_c2_up, bt_c2_down, bt_c2_del, bt_c2_del_dub_new;

	public Line line;
	public LineSeries ls = new LineSeries();
	private Text teamno;
	private Button btnCheckButton;
	private Text favorabledes;
	private Label loginstatus, loginstatustn;
	private Composite price_c, composite_29;
	private DateTime pricedate;
	private org.eclipse.swt.widgets.List d_list;
	private Button daysimgcheck;
	private Button viewdir, hotelimgdir, foodimgdir;
	private Button singlelist;
	private Button mutilist;
	private Combo advancedaytype;
	private Button ad_ck;
	private Button pay_ck;
	private Button payway1;
	private Button payway2;
	private Text price_rate;
	private boolean isdo = false;
	private Group group_2;
	private Text text_2;
	private Text earlybookday;
	private Text earlydiscount;
	private DateTime favorabledisplaystart, favorabledisplayend;
	private Combo combo_1;
	private DateTime disbegindate;
	private DateTime disenddate;
	private Combo discount;
	private Button reduceprice;
	private Button earlybookcheck;
	private DateTime earlystartdate;
	private DateTime earlyenddate;
	private Button favorabledisplaytype1;
	private Button favorabledisplaytype2;
	private Button favorabledisplaytype3;
	private Button bt_c2_activity;
	private Button favorabledisplaycheck;

	private Text txtfeaturepath;
	private Button featurechecked;
	private Text dkeytxt;
	private Button dkeycheck;
	private Text dkeystyle;
	private Button bt_c2_act_cancel;
	private Button payway3;

	private Label stamp1, stamp2, stamp3, stamp4, stamp5, stamp6;
	private DateTime specialstarttime;
	private DateTime specialendtime;
	private Text price_rate_date;
	private Button changespecial;
	private Button http_model;
	private Button https_model;
	private Button down_date_check2;
	private DateTime down_date_begin;
	private DateTime down_date_end;
	private Button down_date_check1;
	private Text limitcount;
	private Button limitcountchk;
	private Button changespecial2;
	private Label label_13;
	private Label label_14;
	private DateTime specialstarttime2;
	private DateTime specialendtime2;
	private Label label_16;
	private Label label_22;
	private Button water_ck;
	private Button bpic1;
	private Button bpic2;
	private Button bpic3;
	private Button bpic4;
	private Button bpic5;
	private Button bpic6;
	private Text advance_hour;
	private Text advance_minute;
	private Text advanceDesc;
	private Text price_rate_date3;
	private Button changespecial3;
	private DateTime specialstarttime3;
	private DateTime specialendtime3;
	private Label label_1;
	private Label label_31;
	private Text mainpictxt;
	private Button mainpicselect;
	private Button mainpicselectbtn;
	private Button flash_show_use;
	private CTabItem tabItem_16;
	private Composite composite_43;
	private Composite composite_44;
	private Text tn_uname;
	private Text tn_pwd;
	private Text tn_rand;
	private Label tn_rerand_img;
	private Composite composite_45;
	private Composite composite_48;
	private Text assembly;
	private Button group_method0;
	private Button group_method1;
	private Composite composite_49;
	private Composite composite_22;
	private Button img_publish_style1;
	private Button img_publish_style2;
	private Button img_publish_style3;
	private Composite composite_50;
	private Button promise_ironclad_group;
	private Button promise_no_self_pay;
	private Button promise_no_shopping;
	private Button promise_truthful_description;
	private Composite composite_51;
	private Button promise_guarantee_go;
	private Button promise_booking_current_day;
	private Composite composite_52;
	private Label label_37;
	private Button promise_truthful_description_free;
	private Button promise_refund_anytime_not_consume;
	private Composite composite_53;
	private Group group_23;
	private Button l_ic;
	private TabFolder tabFolder_3;
	private TabItem tbtmNewItem_4;
	private TabItem tbtmNewItem_5;
	private Composite composite_54;

	private ListViewer listViewer;
	private org.eclipse.swt.widgets.List seriesline;
	private Button btnNewButton_3;
	private Button global_ck;
	private Label label_38;
	private Button changespecial4;
	private Composite composite_56;
	private Text special_date;
	private Label label_39;
	private Text special_price;
	private Composite composite_57;
	private CTabItem tabItem_17;
	private CTabItem tabItem_18;
	private Label label_40;
	private Text send_wait_begin;
	private Label label_41;
	private Text send_wait_end;
	private Label label_42;
	private Text txt_failinterrupt;
	private Composite composite_10;
	private Text gather_spot;
	private DateTime gather_time;

	/**
	 * Create the application window.
	 */
	public Main(Display display) {
		super(null);
		this.display = display;
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
		line = new Line();
		hl = new HttpLogin(this);
		// hltn=new HttpLoginTn(this);

		// hl.getVimage();
		// hltn.getVimage();

		line.setViewimgdir(System.getProperty("user.dir"));
		line.setHotelimgdir(System.getProperty("user.dir"));
		line.setFoodimgdir(System.getProperty("user.dir"));
		line.setTxtdiscription("{title}");
		line.getSummary().setTitle("{title}");
		line.getSummary().setRecommendation("{title}");
		line.getSummary().setTeamno("{title}");
		line.getSummary().setDeparture("{title}");
		// line.setKidsprice("0");
		// line.setKidspricedesc("0");
		// line.setIncludefee("0");
		// line.setExcludefee("0");
		// line.setAdultprice("0");
		// line.setMarketprice("0");
		// line.setSigledistance("0");
		// line.setStorenum("0");
		// line.setMinnumtime("0");
		// line.setMaxnumtime("0");
		line.setAgelimit("{title}");
		line.getSummary().setFeatures("{title}");
		line.getSummary().setFeeinclude("{title}");
		line.getSummary().setFeeexclude("{title}");
		line.getSummary().setAttention("{title}");
		line.getSummary().setTip("{title}");
		line.setBtc1(true);
		line.setBtc2(false);
		line.setC1up(true);
		line.setC1nomain(true);
		line.setC1noimg(true);
		line.setLbc(true);
		line.setLdc(true);
		line.setLoc(true);
		line.setLpc(true);
		line.setPrate("0");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cd = Calendar.getInstance();
		cd.setTime(new Date());

		line.setDisbegindate(sdf.format(cd.getTime()));
		line.setEarlystartdate(sdf.format(cd.getTime()));
		line.setFavorabledisplaystart(sdf.format(cd.getTime()));

		cd.add(Calendar.DAY_OF_MONTH, 50);

		line.setDisenddate(sdf.format(cd.getTime()));
		line.setEarlyenddate(sdf.format(cd.getTime()));
		line.setFavorabledisplayend(sdf.format(cd.getTime()));
		line.getSummary().setConfeature("{title}{特色图片}");
		line.setSpecialsatrttime(new Date());
		line.setSpecialendtime(new Date());
		line.setHttpmodel(true);
		line.setUsername("vieye");
		line.setPassword("zln4310526");
		line.setTnuname("11218");
		line.setTnpwd("123456");
	}

	/**
	 * Create contents of the application window.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		container.setLayout(new FillLayout(SWT.HORIZONTAL));

		SashForm sashForm_1 = new SashForm(container, SWT.VERTICAL);

		Composite composite = new Composite(sashForm_1, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));

		Button button_9 = stopuploadaction.getButton();
		button_9.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				isdo = isdo ? false : true;
				((Button) e.getSource()).setText(isdo ? "继续上传" : "暂停上传");
			}
		});

		Button btnNewButton_11 = saveuploadaction.getButton();
		btnNewButton_11.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (line != null) {
					String fpath = System.getProperty("user.dir") + "/data/";
					FileDialog dialog = new FileDialog(Main.this.getShell(), SWT.SAVE);
					dialog.setFilterNames(new String[] { "xml", "All Files (*.*)" });
					dialog.setFilterExtensions(new String[] { "*.xml", "*.*" });
					dialog.setFilterPath(fpath); // Windows path
					dialog.setFileName(line.getSummary().getTeamno() + ".xml");

					String rpath = dialog.open();
					if (rpath == null) {
						return;
					}
					File f = new File(rpath);
					if (f.exists()) {
						f.delete();
					} else {
						f.mkdirs();
						f.delete();
					}
					XmlPersistence<Line> persistCmp = new XmlPersistence<Line>(rpath);
					persistCmp.add(line);
					hl.updateUI("保存完成");
				}
			}
		});

		Button button_15 = loaduploadaction.getButton();
		button_15.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String fpath = fileSelect(Main.this.getShell(), new String[] { "*.xml", "所有文件" }, new String[] { "*.xml", "*.*" }, System.getProperty("user.dir") + "/data/");
				loadLine(fpath);
			}
		});

		CTabFolder tabFolder = new CTabFolder(composite, SWT.BORDER);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tabFolder.setSize(572, 349);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));

		CTabItem tabItem_6 = new CTabItem(tabFolder, SWT.NONE);
		tabItem_6.setText("\u6570\u636E\u67E5\u770B");

		Composite composite_23 = new Composite(tabFolder, SWT.NONE);
		tabItem_6.setControl(composite_23);
		composite_23.setLayout(new GridLayout(5, false));

		b_url = new Text(composite_23, SWT.BORDER);
		b_url.setText("http://tb2cadmin.qunar.com/home.jsp");
		b_url.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button button_23 = new Button(composite_23, SWT.NONE);
		button_23.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				browser.setUrl(b_url.getText());
			}
		});
		button_23.setText("\u8F6C\u5230");

		Button button_24 = new Button(composite_23, SWT.NONE);
		button_24.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				browser.setUrl("http://tb2cadmin.qunar.com/supplier/product/product_warehouse.jsp");
				b_url.setText(browser.getUrl());
			}
		});
		button_24.setText("\u4ED3\u5E93");

		Button btnNewButton = new Button(composite_23, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				browser.setUrl("http://tb2cadmin.qunar.com/supplier/product/product_sell.jsp");
				b_url.setText(browser.getUrl());
			}
		});
		btnNewButton.setText("\u51FA\u552E");

		Button button_25 = new Button(composite_23, SWT.NONE);
		button_25.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				browser.setUrl("http://tb2cadmin.qunar.com/home.jsp");
				b_url.setText(browser.getUrl());
			}
		});
		button_25.setText("\u9996\u9875");

		browser = new Browser(composite_23, SWT.NONE);
		browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 5, 1));

		tabItem_16 = new CTabItem(tabFolder, SWT.NONE);
		tabItem_16.setText("\u7528\u6237\u767B\u5F55");

		composite_43 = new Composite(tabFolder, SWT.NONE);
		tabItem_16.setControl(composite_43);
		composite_43.setLayout(new GridLayout(3, false));

		Composite composite_1 = new Composite(composite_43, SWT.BORDER);
		composite_1.setLayout(new GridLayout(1, false));

		Composite composite_34 = new Composite(composite_1, SWT.NONE);
		GridData gd_composite_34 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_composite_34.heightHint = 30;
		composite_34.setLayoutData(gd_composite_34);
		composite_34.setLayout(new GridLayout(2, false));

		http_model = new Button(composite_34, SWT.RADIO);
		http_model.setSelection(true);
		http_model.setText("\u6A21\u5F0F1");

		https_model = new Button(composite_34, SWT.RADIO);
		https_model.setBounds(0, 0, 97, 17);
		https_model.setText("\u6A21\u5F0F2");

		username = new Text(composite_1, SWT.BORDER);
		username.setText("vieye");
		username.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		password = new Text(composite_1, SWT.BORDER);
		password.setText("zln4310526");
		password.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		vcode = new Text(composite_1, SWT.BORDER);
		vcode.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		label = new Label(composite_1, SWT.NONE);
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// hl.getVimage();
				Image oimg = new Image(display, hl.getValidatecode());
				label.setImage(oimg);
			}
		});
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label.setText("\u9A8C\u8BC1\u7801");
		// label.setImage(new Image(display, hl.getValidatecode()));

		Button button = new Button(composite_1, SWT.NONE);
		button.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// if (vcode.getText().equals("")) {
				// log_txt.append("\r\n验证码不能为空");
				// return;
				// }

				// hl.setValCode(vcode.getText());
				try {
					hl.loginForm3();
				} catch (Exception e1) {
					e1.printStackTrace();
					loginstatus.setText("获取验证码失败");
					return;
				}
				// hl.loginRedirect();
				// if (!hl.getLoginstatus()) {
				// loginstatus.setText("登录失败");
				// log_txt.append("\r\n登录失败");
				// return;
				// }
				loginstatus.setText("登录成功");
				browser.setUrl("http://tb2cadmin.qunar.com/");
				// browser.setUrl("http://dujia.pro.qunar.com");

				// 取得服务电话号码
				hl.getPhontoForServer(lab_show_phono_txt, server_phone_com);

			}
		});
		button.setText("\u767B\u5F55");

		loginstatus = new Label(composite_1, SWT.NONE);
		loginstatus.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		loginstatus.setText("\u767B\u5F55\u72B6\u6001");

		composite_44 = new Composite(composite_43, SWT.BORDER);
		composite_44.setLayout(new GridLayout(2, false));
		composite_44.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));

		Label label_15 = new Label(composite_44, SWT.NONE);
		GridData gd_label_15 = new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1);
		gd_label_15.heightHint = 30;
		label_15.setLayoutData(gd_label_15);
		label_15.setText("\u9014\u725B\u5E73\u53F0");

		Label label_33 = new Label(composite_44, SWT.NONE);
		label_33.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_33.setText("\u7528\u6237\u540D\uFF1A");

		tn_uname = new Text(composite_44, SWT.BORDER);

		Label label_34 = new Label(composite_44, SWT.NONE);
		label_34.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_34.setText("\u5BC6\u7801\uFF1A");

		tn_pwd = new Text(composite_44, SWT.BORDER);
		tn_pwd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label label_35 = new Label(composite_44, SWT.NONE);
		label_35.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_35.setText("\u9A8C\u8BC1\u7801\uFF1A");

		tn_rand = new Text(composite_44, SWT.BORDER);
		tn_rand.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		tn_rerand_img = new Label(composite_44, SWT.NONE);
		tn_rerand_img.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// hltn.getVimage();
				// Image oimg = new Image(display, hltn.getValidatecode());
				// tn_rerand_img.setImage(oimg);
			}
		});
		GridData gd_tn_rerand_img = new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1);
		gd_tn_rerand_img.widthHint = 126;
		tn_rerand_img.setLayoutData(gd_tn_rerand_img);
		tn_rerand_img.setText("New Label");
		// tn_rerand_img.setImage(new Image(display, hltn.getValidatecode()));

		Button button_1 = new Button(composite_44, SWT.NONE);
		button_1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				// restful/login/logon
				if (tn_rand.getText().equals("")) {
					log_txt.append("\r\n验证码不能为空");
					return;
				}

				// hltn.loginForm();
				// hl.loginRedirect();
				// if (!hltn.getLoginstatus()) {
				// loginstatustn.setText("登录失败");
				// log_txt.append("\r\n登录失败");
				// return;
				// }
				loginstatustn.setText("登录成功");
				// browser.setUrl("http://tb2cadmin.qunar.com/");

				// 取得服务电话号码
				// hl.getPhontoForServer(lab_show_phono_txt, server_phone_com);

			}
		});
		button_1.setText("\u767B\u5F55");

		loginstatustn = new Label(composite_44, SWT.NONE);
		loginstatustn.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
		loginstatustn.setText("\u767B\u5F55\u72B6\u6001");

		composite_50 = new Composite(composite_43, SWT.NONE);
		composite_50.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		composite_50.setLayout(new GridLayout(1, false));

		Button btnNewButton_2 = new Button(composite_50, SWT.NONE);
		btnNewButton_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				browser.setUrl("http://tb2cadmin.qunar.com/home.jsp");
				hl.setLoginstatus(true);
				log_txt.append(Browser.getCookie("QN25", "http://tb2cadmin.qunar.com") + "\r\n");
				log_txt.append(Browser.getCookie("QN42", "http://tb2cadmin.qunar.com") + "\r\n");
				log_txt.append(Browser.getCookie("QN43", "http://tb2cadmin.qunar.com") + "\r\n");
				log_txt.append(Browser.getCookie("_q", "http://tb2cadmin.qunar.com") + "\r\n");
				log_txt.append(Browser.getCookie("_t", "http://tb2cadmin.qunar.com") + "\r\n");
				log_txt.append(Browser.getCookie("csrfToken", ".qunar.com") + "\r\n");
				log_txt.append(Browser.getCookie("_v", ".qunar.com") + "\r\n");
				log_txt.append(Browser.getCookie("_vi", ".qunar.com") + "\r\n");
				hl.cookie = "QN25=" + Browser.getCookie("QN25", "http://tb2cadmin.qunar.com");
				hl.getPhontoForServer(lab_show_phono_txt, server_phone_com);
			}
		});
		btnNewButton_2.setText("\u6D4B\u8BD5");

		composite_49 = new Composite(composite_43, SWT.NONE);
		composite_49.setLayout(new GridLayout(1, false));
		composite_49.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		load_show = new Label(composite_49, SWT.NONE);
		load_show.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		load_show.setSize(19, 17);
		load_show.setAlignment(SWT.CENTER);
		load_show.setText("0/0");
		new Label(composite_43, SWT.NONE);
		new Label(composite_43, SWT.NONE);

		CTabItem tabItem_7 = new CTabItem(tabFolder, SWT.NONE);
		tabItem_7.setText("\u4E0A\u4F20\u8BBE\u7F6E");

		Composite composite_24 = new Composite(tabFolder, SWT.NONE);
		tabItem_7.setControl(composite_24);
		composite_24.setLayout(new GridLayout(1, false));

		tabFolder_3 = new TabFolder(composite_24, SWT.NONE);
		tabFolder_3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		tbtmNewItem_5 = new TabItem(tabFolder_3, SWT.NONE);
		tbtmNewItem_5.setText("\u6240\u6709\u7CFB\u5217\u7EBF\u8DEF\u5217\u8868");

		composite_54 = new Composite(tabFolder_3, SWT.NONE);
		tbtmNewItem_5.setControl(composite_54);
		composite_54.setLayout(new GridLayout(1, false));

		listViewer = new ListViewer(composite_54, SWT.BORDER | SWT.V_SCROLL);
		seriesline = listViewer.getList();
		seriesline.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				String sitem = seriesline.getItem(seriesline.getSelectionIndex());
				listViewer.remove(sitem);
			}
		});
		seriesline.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		seriesline.setBounds(0, 0, 88, 68);
		listViewer.setLabelProvider(new ListLabelProvider());
		listViewer.setContentProvider(new ContentProvider());
		listViewer.setInput(new Object());

		Composite composite_55 = new Composite(composite_54, SWT.NONE);
		composite_55.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		composite_55.setBounds(0, 0, 64, 64);
		GridLayout gl_composite_55 = new GridLayout(8, false);
		composite_55.setLayout(gl_composite_55);

		Button btnNewButton_1 = new Button(composite_55, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileselect = fileSelectMul(Main.this.getShell(), new String[] { "*.xml", "所有文件" }, new String[] { "*.xml", "*.*" }, System.getProperty("user.dir") + "/data/");
				for (int i = 0; i < fileselect.getFileNames().length; i++) {
					listViewer.add(fileselect.getFilterPath() + "/" + fileselect.getFileNames()[i]);
				}
				// ls.getSeries().add(mpath);
			}
		});
		btnNewButton_1.setBounds(0, 0, 80, 27);
		btnNewButton_1.setText("\u52A0\u8F7D");

		btnNewButton_3 = new Button(composite_55, SWT.NONE);
		btnNewButton_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				listViewer.getList().removeAll();
			}
		});
		btnNewButton_3.setText("\u6E05\u7A7A");

		label_40 = new Label(composite_55, SWT.NONE);
		label_40.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_40.setText("\u4E0A\u4F20\u95F4\u9694");

		send_wait_begin = new Text(composite_55, SWT.BORDER);
		send_wait_begin.setText("2");
		GridData gd_send_wait_begin = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_send_wait_begin.widthHint = 50;
		send_wait_begin.setLayoutData(gd_send_wait_begin);

		label_41 = new Label(composite_55, SWT.NONE);
		label_41.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_41.setText("\u81F3");

		send_wait_end = new Text(composite_55, SWT.BORDER);
		send_wait_end.setText("4");
		GridData gd_send_wait_end = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_send_wait_end.widthHint = 50;
		send_wait_end.setLayoutData(gd_send_wait_end);

		label_42 = new Label(composite_55, SWT.NONE);
		label_42.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_42.setText("\u5931\u8D25\u7EED\u4F20\u95F4\u9694");

		txt_failinterrupt = new Text(composite_55, SWT.BORDER);
		txt_failinterrupt.setText("30");
		GridData gd_txt_failinterrupt = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txt_failinterrupt.widthHint = 50;
		txt_failinterrupt.setLayoutData(gd_txt_failinterrupt);

		tbtmNewItem_4 = new TabItem(tabFolder_3, SWT.NONE);
		tbtmNewItem_4.setText("\u6BCF\u4E00\u7CFB\u5217\u7EBF\u8DEF\u8BE6\u7EC6\u5217\u8868");

		Composite composite_25 = new Composite(tabFolder_3, SWT.NONE);
		tbtmNewItem_4.setControl(composite_25);
		composite_25.setLayout(new GridLayout(3, false));

		Label label_23 = new Label(composite_25, SWT.NONE);
		label_23.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_23.setText("\u591A\u6761");

		mupath = new Text(composite_25, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		mupath.setText("http://www.gayosite.com/kuxun_api/CityLineToXmlQnTTL.asp?q=1&f=1&t=3001&id=747");
		GridData gd_mupath = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_mupath.heightHint = 49;
		mupath.setLayoutData(gd_mupath);

		singlelist = new Button(composite_25, SWT.CHECK);
		singlelist.setText("\u9009\u4E2D");

		Label label_24 = new Label(composite_25, SWT.NONE);
		label_24.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, true, 1, 1));
		label_24.setText("\u5217\u8868");

		lupath = new Text(composite_25, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL);
		lupath.setText("http://yn.gayosite.com/kuxun_api/CityLineToXmlQnList.asp?f=2501&f=2601&f=2701&f=2801&f=2901&f=3101&f=3102&f=3103&f=1608&t=3001&y=1&ttl=1&LineID=963");
		GridData gd_text_23 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_text_23.heightHint = 159;
		lupath.setLayoutData(gd_text_23);

		mutilist = new Button(composite_25, SWT.CHECK);
		mutilist.setText("\u9009\u4E2D");
		new Label(composite_25, SWT.NONE);

		Composite composite_27 = new Composite(composite_25, SWT.NONE);
		composite_27.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		composite_27.setLayout(new GridLayout(3, false));
		new Label(composite_27, SWT.NONE);

		bt_c1 = new Button(composite_27, SWT.RADIO);
		bt_c1.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		bt_c1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				group_1.setEnabled(true);
				group_5.setEnabled(false);
				Control[] cts = group_1.getChildren();
				for (Control c : cts) {
					c.setEnabled(((Button) e.getSource()).getSelection());
				}
				cts = group_2.getChildren();
				for (Control c : cts) {
					c.setEnabled(((Button) e.getSource()).getSelection());
				}
			}
		});
		bt_c1.setSelection(true);
		bt_c1.setText("\u9009\u62E9");

		bt_c2 = new Button(composite_27, SWT.RADIO);
		bt_c2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				group_1.setEnabled(false);
				group_5.setEnabled(true);
				Control[] cts = group_5.getChildren();
				for (Control c : cts) {
					c.setEnabled(((Button) e.getSource()).getSelection());
				}
			}
		});
		bt_c2.setText("\u9009\u62E9");

		global_ck = new Button(composite_27, SWT.CHECK);
		global_ck.setSelection(true);
		global_ck.setText("\u5168\u5C40\u5171\u7528");

		group_1 = new Group(composite_27, SWT.NONE);
		group_1.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		group_1.setLayout(new GridLayout(1, false));

		bt_c1_up = new Button(group_1, SWT.CHECK);
		bt_c1_up.setSelection(true);
		bt_c1_up.setText("\u4E0A\u4F20\u540E\u540C\u65F6\u4E0A\u67B6\uFF08\u5426\u5219\u53EA\u4F1A\u4E0A\u4F20\u5230\u4ED3\u5E93\uFF09");

		bt_c1_noeditmainimg = new Button(group_1, SWT.CHECK);
		bt_c1_noeditmainimg.setSelection(true);
		bt_c1_noeditmainimg.setText("\u4FEE\u6539\u65F6\uFF0C\u4FEE\u6539\u57FA\u7840\u4FE1\u606F\u4E2D\u7684\u56FE\u7247");

		bt_c1_noeditimgs = new Button(group_1, SWT.CHECK);
		bt_c1_noeditimgs.setSelection(true);
		bt_c1_noeditimgs.setText("\u4FEE\u6539\u65F6\uFF0C\u4FEE\u6539\u884C\u7A0B\u4E2D\u7684\u56FE\u7247");

		// tableViewer = new TableViewer(composite_25, SWT.BORDER | SWT.CHECK
		// | SWT.FULL_SELECTION | SWT.V_SCROLL);
		// createColumns(tableViewer);

		group_2 = new Group(group_1, SWT.NONE);
		group_2.setLayout(new GridLayout(4, false));
		group_2.setText("\u4E0A\u4F20\u90E8\u5206\u9009\u62E9");

		l_bc = new Button(group_2, SWT.CHECK);
		l_bc.setSelection(true);
		l_bc.setText("\u57FA\u672C\u4FE1\u606F");

		l_dc = new Button(group_2, SWT.CHECK);
		l_dc.setSelection(true);
		l_dc.setText("\u884C\u7A0B\u53C2\u8003");

		l_pc = new Button(group_2, SWT.CHECK);
		l_pc.setSelection(true);
		l_pc.setText("\u4EF7\u683C\u5E93\u5B58");

		l_oc = new Button(group_2, SWT.CHECK);
		l_oc.setSelection(true);
		l_oc.setText("\u5176\u5B83\u4FE1\u606F");

		l_ic = new Button(group_2, SWT.CHECK);
		l_ic.setSelection(true);
		l_ic.setText("\u4EA7\u54C1\u8BE6\u60C5");
		new Label(group_2, SWT.NONE);
		new Label(group_2, SWT.NONE);
		new Label(group_2, SWT.NONE);

		group_5 = new Group(composite_27, SWT.NONE);
		group_5.setEnabled(false);
		group_5.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		group_5.setLayout(new GridLayout(2, false));

		bt_c2_up = new Button(group_5, SWT.RADIO);
		bt_c2_up.setText("\u4EC5\u5C06\u4E0A\u9762\u7684\u4EA7\u54C1\u4F5C\u4E3A\u67E5\u8BE2\u6761\u4EF6\uFF0C\u5BF9\u4ED3\u5E93\u4E2D\u7684\u4EA7\u54C1\u4E0A\u67B6");

		limitcountchk = new Button(group_5, SWT.RADIO);
		limitcountchk.setText("\u8BA2\u5355\u9650\u989D");

		bt_c2_down = new Button(group_5, SWT.RADIO);
		bt_c2_down.setText("\u4EC5\u5C06\u4E0A\u9762\u7684\u4EA7\u54C1\u4F5C\u4E3A\u67E5\u8BE2\u6761\u4EF6\uFF0C\u5BF9\u51FA\u552E\u4E2D\u7684\u4EA7\u54C1\u4E0B\u67B6");

		limitcount = new Text(group_5, SWT.BORDER);
		limitcount.setEnabled(true);

		bt_c2_del = new Button(group_5, SWT.RADIO);
		bt_c2_del.setText("\u4EC5\u5C06\u4E0A\u9762\u7684\u4EA7\u54C1\u4F5C\u4E3A\u67E5\u8BE2\u6761\u4EF6\uFF0C\u5BF9\u5B58\u5728\u7684\u4EA7\u54C1\u5220\u9664");
		new Label(group_5, SWT.NONE);

		bt_c2_del_dub_new = new Button(group_5, SWT.RADIO);
		bt_c2_del_dub_new.setText("\u5BF9\u67E5\u5230\u7684\u540C\u56E2\u53F7\u91CD\u590D\u4EA7\u54C1\uFF0C\u5220\u9664\u65F6\u95F4\u65B0\u7684");
		new Label(group_5, SWT.NONE);

		bt_c2_activity = new Button(group_5, SWT.RADIO);
		bt_c2_activity.setText("\u5BF9\u67E5\u5230\u7684\u540C\u56E2\u53F7\u7684\u4EA7\u54C1\u6DFB\u52A0\u4F18\u60E0\u4FE1\u606F");
		new Label(group_5, SWT.NONE);

		bt_c2_act_cancel = new Button(group_5, SWT.RADIO);
		bt_c2_act_cancel.setText("\u5BF9\u67E5\u5230\u7684\u540C\u56E2\u53F7\u7684\u4EA7\u54C1\u53D6\u6D88\u4F18\u60E0\u4FE1\u606F");
		new Label(group_5, SWT.NONE);

		CTabItem tbtmKidsprice = new CTabItem(tabFolder, SWT.NONE);
		tbtmKidsprice.setText("\u4EA7\u54C1\u6807\u9898\u53CA\u5176\u5B83");

		Composite composite_2 = new Composite(tabFolder, SWT.H_SCROLL);
		tbtmKidsprice.setControl(composite_2);
		composite_2.setLayout(new GridLayout(1, false));

		composite_57 = new Composite(composite_2, SWT.NONE);
		composite_57.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		composite_57.setLayout(new GridLayout(1, false));

		CTabFolder tabFolder_2 = new CTabFolder(composite_57, SWT.BORDER);
		tabFolder_2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tabFolder_2.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));

		tabItem_17 = new CTabItem(tabFolder_2, SWT.NONE);
		tabItem_17.setText("\u4EA7\u54C1\u6807\u9898");
		Composite composite_58 = new Composite(tabFolder_2, SWT.NONE);
		tabItem_17.setControl(composite_58);
		composite_58.setLayout(new GridLayout(1, false));
		Composite composite_4 = new Composite(composite_58, SWT.NONE);
		composite_4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		composite_4.setLayout(new GridLayout(2, false));

		Label lblNewLabel_2 = new Label(composite_4, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_2.setText("\u4EA7\u54C1\u6807\u9898\uFF1A");

		title = new Text(composite_4, SWT.BORDER);
		title.setText("{title}");
		title.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblNewLabel_3 = new Label(composite_4, SWT.NONE);
		lblNewLabel_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_3.setText("\u4EA7\u54C1\u526F\u6807\u9898\uFF1A");

		recommendation = new Text(composite_4, SWT.BORDER);
		recommendation.setText("{title}");
		recommendation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Composite composite_6 = new Composite(composite_58, SWT.NONE);
		composite_6.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		composite_6.setSize(735, 33);
		composite_6.setLayout(new GridLayout(4, false));

		Label label_25 = new Label(composite_6, SWT.NONE);
		label_25.setText("\u4EA7\u54C1\u7F16\u53F7\uFF1A");

		teamno = new Text(composite_6, SWT.BORDER);
		GridData gd_teamno = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_teamno.widthHint = 100;
		teamno.setLayoutData(gd_teamno);
		teamno.setText("{title}");

		Label label_2 = new Label(composite_6, SWT.NONE);
		label_2.setText("\u51FA\u53D1\u57CE\u5E02\uFF1A");

		txttitle = new Text(composite_6, SWT.BORDER);
		GridData gd_txttitle = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txttitle.widthHint = 100;
		txttitle.setLayoutData(gd_txttitle);
		txttitle.setText("{title}");

		CTabItem tabItem_8 = new CTabItem(tabFolder_2, SWT.NONE);
		tabItem_8.setText("\u63D0\u524D\u62A5\u540D\u548C\u670D\u52A1\u7535\u8BDD");

		Composite comp = new Composite(tabFolder_2, SWT.NONE);
		comp.setLayout(new GridLayout(1, false));
		tabItem_8.setControl(comp);

		Group group_21 = new Group(comp, SWT.NONE);
		group_21.setText("\u63D0\u524D\u9884\u8BA2\u8BBE\u7F6E\uFF08\u53EA\u5BF9\u8DDF\u56E2\u6E38\u6709\u7528\uFF09");
		group_21.setLayout(new GridLayout(2, false));
		group_21.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		ad_ck = new Button(group_21, SWT.CHECK);
		ad_ck.setText("\u542F\u7528\u8BBE\u7F6E");
		new Label(group_21, SWT.NONE);

		Composite composite_37 = new Composite(group_21, SWT.NONE);
		composite_37.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		composite_37.setLayout(new GridLayout(8, false));

		Label label_26 = new Label(composite_37, SWT.NONE);
		label_26.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_26.setText("\u63D0\u524D\u62A5\u540D\uFF1A");

		advanceday = new Text(composite_37, SWT.BORDER);
		GridData gd_advanceday = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_advanceday.widthHint = 20;
		advanceday.setLayoutData(gd_advanceday);
		advanceday.setText("3");

		advancedaytype = new Combo(composite_37, SWT.NONE);
		advancedaytype.setItems(new String[] { "\u81EA\u7136\u65E5", "\u5DE5\u4F5C\u65E5" });
		advancedaytype.select(0);

		Label label_27 = new Label(composite_37, SWT.NONE);
		label_27.setText("\u5929\u7684");

		advance_hour = new Text(composite_37, SWT.BORDER);
		advance_hour.setText("23");

		Label label_28 = new Label(composite_37, SWT.NONE);
		label_28.setText("\u70B9");

		advance_minute = new Text(composite_37, SWT.BORDER);
		advance_minute.setText("59");

		Label label_29 = new Label(composite_37, SWT.NONE);
		label_29.setText("\u5206\u524D\u9884\u8BA2");

		com_show_phono_txt = new Composite(group_21, SWT.NONE);
		com_show_phono_txt.setLayout(new GridLayout(1, false));
		GridData gd_com_show_phono_txt = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3);
		gd_com_show_phono_txt.widthHint = 300;
		gd_com_show_phono_txt.minimumWidth = 200;
		com_show_phono_txt.setLayoutData(gd_com_show_phono_txt);

		lab_show_phono_txt = new Label(com_show_phono_txt, SWT.WRAP);
		lab_show_phono_txt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		Composite composite_38 = new Composite(group_21, SWT.NONE);
		composite_38.setLayout(new GridLayout(2, false));
		composite_38.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		Label label_30 = new Label(composite_38, SWT.NONE);
		label_30.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, true, 1, 1));
		label_30.setText("\u63D0\u524D\u9884\u8BA2\u63CF\u8FF0");

		advanceDesc = new Text(composite_38, SWT.BORDER);
		GridData gd_advanceDesc = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_advanceDesc.heightHint = 50;
		advanceDesc.setLayoutData(gd_advanceDesc);

		Composite composite_42 = new Composite(group_21, SWT.NONE);
		composite_42.setLayout(new GridLayout(2, false));
		composite_42.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label label_32 = new Label(composite_42, SWT.NONE);
		GridData gd_label_32 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label_32.widthHint = 170;
		label_32.setLayoutData(gd_label_32);
		label_32.setText("\u5BA2\u670D\u70ED\u7EBF:\u8BF7\u770B\u540E\u9762\u7684\u8BF4\u660E\u9009\u62E9");

		server_phone_com = new Combo(composite_42, SWT.NONE);
		server_phone_com.setItems(new String[] { "2  2", "3  3" });
		server_phone_com.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		tabItem_18 = new CTabItem(tabFolder_2, SWT.NONE);
		tabItem_18.setText("\u7EBF\u8DEF\u4FDD\u8BC1");

		composite_53 = new Composite(tabFolder_2, SWT.NONE);
		tabItem_18.setControl(composite_53);
		composite_53.setLayout(new GridLayout(2, false));

		Group group_12 = new Group(composite_53, SWT.NONE);
		group_12.setSize(643, 156);
		group_12.setLayout(new GridLayout(1, false));
		group_12.setText("\u8DDF\u56E2\u6E38\u6709\u6548");

		composite_45 = new Composite(group_12, SWT.NONE);
		composite_45.setLayout(new GridLayout(2, false));

		Composite composite_47 = new Composite(composite_45, SWT.BORDER);
		composite_47.setBounds(0, 0, 91, 27);
		composite_47.setLayout(new GridLayout(2, false));

		group_method0 = new Button(composite_47, SWT.RADIO);
		group_method0.setText("\u51FA\u53D1\u5730\u53C2\u56E2");

		group_method1 = new Button(composite_47, SWT.RADIO);
		group_method1.setSelection(true);
		group_method1.setText("\u76EE\u7684\u5730\u53C2\u56E2");

		Composite composite_46 = new Composite(composite_45, SWT.BORDER);
		composite_46.setLayout(new GridLayout(4, false));

		promise_ironclad_group = new Button(composite_46, SWT.CHECK);
		promise_ironclad_group.setText("\u94C1\u5B9A\u6210\u56E2");

		promise_no_self_pay = new Button(composite_46, SWT.CHECK);
		promise_no_self_pay.setText("\u65E0\u81EA\u8D39");

		promise_no_shopping = new Button(composite_46, SWT.CHECK);
		promise_no_shopping.setText("\u65E0\u8D2D\u7269");

		promise_truthful_description = new Button(composite_46, SWT.CHECK);
		promise_truthful_description.setText("\u5982\u5B9E\u63CF\u8FF0");

		composite_48 = new Composite(group_12, SWT.BORDER);
		composite_48.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		composite_48.setLayout(new GridLayout(2, false));

		composite_10 = new Composite(composite_48, SWT.NONE);
		composite_10.setLayout(new GridLayout(4, false));
		GridData gd_composite_10 = new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1);
		gd_composite_10.widthHint = 282;
		composite_10.setLayoutData(gd_composite_10);

		Label lblNewLabel = new Label(composite_10, SWT.NONE);
		lblNewLabel.setText("\u96C6\u5408\u65F6\u95F4");

		gather_time = new DateTime(composite_10, SWT.BORDER | SWT.TIME);

		Label label_43 = new Label(composite_10, SWT.NONE);
		label_43.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_43.setText("\u96C6\u5408\u5730\u70B9");

		gather_spot = new Text(composite_10, SWT.BORDER);
		gather_spot.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label label_36 = new Label(composite_48, SWT.NONE);
		label_36.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_36.setText("\u96C6\u5408\r\n\u5730\u4FE1\r\n\u606F\uFF1A");

		assembly = new Text(composite_48, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_assembly = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_assembly.heightHint = 60;
		assembly.setLayoutData(gd_assembly);

		group_23 = new Group(composite_53, SWT.NONE);
		group_23.setText("\u81EA\u7531\u884C\u6709\u6548");
		group_23.setLayout(new GridLayout(1, false));

		composite_51 = new Composite(group_23, SWT.BORDER);
		composite_51.setLayout(new GridLayout(1, false));

		label_37 = new Label(composite_51, SWT.NONE);
		label_37.setText("\u81EA\u7531\u884C");

		composite_52 = new Composite(composite_51, SWT.NONE);
		composite_52.setLayout(new GridLayout(1, false));
		composite_52.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, true, 1, 1));

		promise_guarantee_go = new Button(composite_52, SWT.CHECK);
		promise_guarantee_go.setText("\u51FA\u884C\u4FDD\u969C");

		promise_booking_current_day = new Button(composite_52, SWT.CHECK);
		promise_booking_current_day.setText("\u5F53\u65E5\u53EF\u5B9A");

		promise_truthful_description_free = new Button(composite_52, SWT.CHECK);
		promise_truthful_description_free.setText("\u5982\u5B9E\u63CF\u8FF0");

		promise_refund_anytime_not_consume = new Button(composite_52, SWT.CHECK);
		promise_refund_anytime_not_consume.setText("\u672A\u9A8C\u8BC1\u6D88\u8D39 \u968F\u65F6\u9000");

		CTabItem tabItem_15 = new CTabItem(tabFolder_2, SWT.NONE);
		tabItem_15.setText("\u4E3B\u56FE\u7247");

		Composite composite_40 = new Composite(tabFolder_2, SWT.NONE);
		tabItem_15.setControl(composite_40);
		composite_40.setLayout(new GridLayout(3, false));

		mainpicselect = new Button(composite_40, SWT.CHECK);
		mainpicselect.setText("\u6307\u5B9A\u4E3B\u56FE");

		mainpictxt = new Text(composite_40, SWT.BORDER);
		mainpictxt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		mainpicselectbtn = new Button(composite_40, SWT.NONE);
		mainpicselectbtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String mpath = dirSelect(Main.this.getShell());
				mainpictxt.setText(mpath);
				try {
					Image oimg = new Image(display, mpath);
					mainshow.setImage(oimg);
				} catch (Exception ex) {

				}
			}
		});
		mainpicselectbtn.setText("\u9009\u62E9");

		Composite composite_41 = new Composite(composite_40, SWT.NONE);
		composite_41.setLayout(new RowLayout(SWT.HORIZONTAL));
		composite_41.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));

		mainshow = new Label(composite_41, SWT.CENTER);
		mainshow.setAlignment(SWT.CENTER);
		mainshow.setText("\u56FE\u7247\u663E\u793A");
		mainshow.setLayoutData(new RowData(200, 200));
		new Label(composite_40, SWT.NONE);

		CTabItem tabItem_9 = new CTabItem(tabFolder_2, SWT.NONE);
		tabItem_9.setText("\u4E0B\u67B6\u65E5\u671F");

		Group group_9 = new Group(tabFolder_2, SWT.NONE);
		tabItem_9.setControl(group_9);
		group_9.setLayout(new GridLayout(2, false));
		group_9.setText("\u4E0B\u67B6\u65E5\u671F");

		down_date_check1 = new Button(group_9, SWT.RADIO);
		down_date_check1.setSelection(true);
		down_date_check1.setText("\u53D1\u56E2\u65E5\u524D\u4E0B\u67B6");

		down_date_check2 = new Button(group_9, SWT.RADIO);
		down_date_check2.setSize(105, 17);
		down_date_check2.setText("\u6307\u8FD9\u5B9A\u65E5\u671F\u4E0B\u67B6");

		down_date_begin = new DateTime(group_9, SWT.BORDER);

		down_date_end = new DateTime(group_9, SWT.BORDER);

		CTabItem tabItem_10 = new CTabItem(tabFolder_2, SWT.NONE);
		tabItem_10.setText("\u7279\u6B8A\u65E5\u671F\u4EF7\u683C\u8BBE\u7F6E");

		Group grpxml_1 = new Group(tabFolder_2, SWT.NONE);
		tabItem_10.setControl(grpxml_1);
		grpxml_1.setText("\u53EA\u5904\u7406\u4E0B\u9762\u5217\u8868\u4E2D\u65E5\u671F\u7684\u4EF7\u683C\u5E93\u5B58");
		grpxml_1.setLayout(new GridLayout(2, false));

		Composite composite_9 = new Composite(grpxml_1, SWT.NONE);
		composite_9.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		composite_9.setLayout(new GridLayout(6, false));

		changespecial = new Button(composite_9, SWT.CHECK);
		changespecial.setText("\u6307\u5B9A\u65E5\u671F\u8C03\u5047");

		price_rate_date = new Text(composite_9, SWT.BORDER);
		price_rate_date.setEnabled(false);
		GridData gd_price_rate_date = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_price_rate_date.widthHint = 100;
		price_rate_date.setLayoutData(gd_price_rate_date);

		label_16 = new Label(composite_9, SWT.NONE);
		label_16.setEnabled(false);
		label_16.setText("\u4ECE");

		specialstarttime = new DateTime(composite_9, SWT.BORDER);
		specialstarttime.setEnabled(false);

		label_22 = new Label(composite_9, SWT.NONE);
		label_22.setEnabled(false);
		label_22.setText("\u81F3");

		specialendtime = new DateTime(composite_9, SWT.BORDER);
		specialendtime.setEnabled(false);

		Composite composite_36 = new Composite(grpxml_1, SWT.NONE);
		composite_36.setLayout(new GridLayout(6, false));
		composite_36.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));

		changespecial2 = new Button(composite_36, SWT.CHECK);
		changespecial2.setText("\u6307\u5B9A\u65E5\u671F\u8C03\u4EF7");

		price_rate = new Text(composite_36, SWT.BORDER);
		price_rate.setEnabled(false);
		GridData gd_price_rate = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_price_rate.widthHint = 100;
		price_rate.setLayoutData(gd_price_rate);

		label_13 = new Label(composite_36, SWT.NONE);
		label_13.setEnabled(false);
		label_13.setText("\u4ECE");

		specialstarttime2 = new DateTime(composite_36, SWT.BORDER);
		specialstarttime2.setEnabled(false);

		label_14 = new Label(composite_36, SWT.NONE);
		label_14.setEnabled(false);
		label_14.setText("\u81F3");

		specialendtime2 = new DateTime(composite_36, SWT.BORDER);
		specialendtime2.setEnabled(false);

		Composite composite_39 = new Composite(grpxml_1, SWT.NONE);
		composite_39.setLayout(new GridLayout(7, false));
		composite_39.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));

		changespecial3 = new Button(composite_39, SWT.CHECK);
		changespecial3.setText("\u6307\u5B9A\u65E5\u671F\u8C03\u4EF7");

		price_rate_date3 = new Text(composite_39, SWT.BORDER);
		price_rate_date3.setEnabled(false);
		GridData gd_price_rate_date3 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_price_rate_date3.widthHint = 100;
		price_rate_date3.setLayoutData(gd_price_rate_date3);

		label_1 = new Label(composite_39, SWT.NONE);
		label_1.setEnabled(false);
		label_1.setText("\u4ECE");

		specialstarttime3 = new DateTime(composite_39, SWT.BORDER);
		specialstarttime3.setEnabled(false);

		label_31 = new Label(composite_39, SWT.NONE);
		label_31.setEnabled(false);
		label_31.setText("\u81F3");

		specialendtime3 = new DateTime(composite_39, SWT.BORDER);
		specialendtime3.setEnabled(false);

		label_38 = new Label(composite_39, SWT.NONE);
		label_38.setText("\u8FD9\u513F\u8C03\u6574\u7684\u662F\u5355\u623F\u5DEE\uFF0C\u8D77\u5982\u65E5\u671F\u662F\u5927\u4E8E\u7B49\u4E8E\uFF0C\u7ED3\u675F\u65E5\u671F\u662F\u5C0F\u4E8E");

		composite_56 = new Composite(grpxml_1, SWT.NONE);
		composite_56.setLayout(new GridLayout(4, false));

		changespecial4 = new Button(composite_56, SWT.CHECK);
		changespecial4.setText("\u4EE5\u5F53\u524D\u65E5\u671F\u4E3A\u51C6\u8FDB\u884C\u540E");

		special_date = new Text(composite_56, SWT.BORDER);
		GridData gd_special_date = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_special_date.widthHint = 60;
		special_date.setLayoutData(gd_special_date);

		label_39 = new Label(composite_56, SWT.NONE);
		label_39.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_39.setText("\u5929\u7684\u8C03\u4EF7");

		special_price = new Text(composite_56, SWT.BORDER);
		GridData gd_special_price = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_special_price.widthHint = 60;
		special_price.setLayoutData(gd_special_price);
		new Label(grpxml_1, SWT.NONE);

		pricedate = new DateTime(grpxml_1, SWT.BORDER);
		pricedate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		Composite composite_5 = new Composite(grpxml_1, SWT.NONE);
		composite_5.setLayout(new GridLayout(1, false));
		composite_5.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 2));

		Button btn_add = new Button(composite_5, SWT.NONE);
		btn_add.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String s;
				try {
					s = sdf.format(sdf.parse(pricedate.getYear() + "-" + (pricedate.getMonth() + 1) + "-" + pricedate.getDay()));
					int i = 0;
					for (String si : d_list.getItems()) {
						if (si.trim().equals(s)) {
							i++;
							line.getPricedates().add(si);
						}
					}
					if (i == 0) {
						d_list.add(s);
						line.getPricedates().add(s);
					}
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_add.setText("<<<\u589E\u52A0");

		Button button_20 = new Button(composite_5, SWT.NONE);
		button_20.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (d_list.getItemCount() > 0 && d_list.getSelectionIndices().length > 0) {
					for (int i = 0; i < d_list.getSelectionIndices().length; i++) {
						line.getPricedates().remove(d_list.getItem(d_list.getSelectionIndices()[i]));
					}
					d_list.remove(d_list.getSelectionIndices());
				}
			}
		});
		button_20.setText("\u5220\u9664\u9009\u4E2D");

		Button button_22 = new Button(composite_5, SWT.NONE);
		button_22.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				d_list.removeAll();
				line.getPricedates().clear();
			}
		});
		button_22.setText("\u6E05\u7A7A\u6240\u6709");

		d_list = new org.eclipse.swt.widgets.List(grpxml_1, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_d_list = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_d_list.heightHint = 154;
		d_list.setLayoutData(gd_d_list);
		d_list.setItems(new String[] {});

		CTabItem tabItem_11 = new CTabItem(tabFolder_2, SWT.NONE);
		tabItem_11.setText("\u4ED8\u6B3E\u65B9\u5F0F");

		Group group_10 = new Group(tabFolder_2, SWT.NONE);
		tabItem_11.setControl(group_10);
		group_10.setLayout(new GridLayout(3, false));
		group_10.setText("\u4ED8\u6B3E\u65B9\u5F0F");

		payway1 = new Button(group_10, SWT.RADIO);
		payway1.setText("\u53CA\u65F6\u4ED8\u6B3E");

		payway2 = new Button(group_10, SWT.RADIO);
		payway2.setSelection(true);
		payway2.setText("\u786E\u8BA4\u540E\u4ED8\u6B3E");

		payway3 = new Button(group_10, SWT.RADIO);
		payway3.setText("\u62C5\u4FDD\u4ED8\u6B3E");

		pay_ck = new Button(group_10, SWT.CHECK);
		pay_ck.setText("\u542F\u7528\u8BBE\u7F6E");
		new Label(group_10, SWT.NONE);
		new Label(group_10, SWT.NONE);

		CTabItem tabItem_12 = new CTabItem(tabFolder_2, SWT.NONE);
		tabItem_12.setText("\u4EF7\u683C\u8BBE\u7F6E");

		Composite composite_8 = new Composite(tabFolder_2, SWT.BORDER);
		tabItem_12.setControl(composite_8);
		composite_8.setLayout(new GridLayout(2, false));

		price_c = new Composite(composite_8, SWT.NONE);
		price_c.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		price_c.setLayout(new GridLayout(2, false));
		new Label(price_c, SWT.NONE);

		btnCheckButton = new Button(price_c, SWT.CHECK);
		btnCheckButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Control[] cts = composite_29.getChildren();
				for (Control c : cts) {
					c.setEnabled(((Button) e.getSource()).getSelection());
				}
			}
		});
		btnCheckButton.setText("\u4F7F\u7528\u754C\u9762\u8BBE\u7F6E\u5E93\u5B58\u4EF7\u683C");

		Composite composite_7 = new Composite(price_c, SWT.NONE);
		composite_7.setLayout(new GridLayout(1, false));
		composite_7.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));

		Group group_6 = new Group(composite_7, SWT.NONE);
		group_6.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		group_6.setText("\u513F\u7AE5\u4EF7\u8BF4\u660E");
		group_6.setLayout(new FillLayout(SWT.HORIZONTAL));

		kidspricedesc = new Text(group_6, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		kidspricedesc.setText("{title}");

		Group group_7 = new Group(composite_7, SWT.NONE);
		group_7.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		group_7.setLayout(new FillLayout(SWT.HORIZONTAL));
		group_7.setText("\u8D77\u4EF7\u8BF4\u660E\uFF0C\u5305\u542B\u673A\u7968");

		includefee = new Text(group_7, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		includefee.setText("{title}");

		Group group_8 = new Group(composite_7, SWT.NONE);
		group_8.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		group_8.setLayout(new FillLayout(SWT.HORIZONTAL));
		group_8.setText("\u8D77\u4EF7\u8BF4\u660E\uFF0C\u4E0D\u5305\u542B\u673A\u7968");

		excludefee = new Text(group_8, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		excludefee.setText("{title}");

		composite_29 = new Composite(price_c, SWT.NONE);
		composite_29.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		composite_29.setLayout(new GridLayout(2, false));

		Label label_11 = new Label(composite_29, SWT.NONE);
		label_11.setText("\u6210\u4EBA\u4EF7\uFF1A");

		adultprice = new Text(composite_29, SWT.BORDER);
		adultprice.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		adultprice.setText("{title}");

		Label label_5 = new Label(composite_29, SWT.NONE);
		label_5.setText("\u513F\u7AE5\u4EF7\uFF1A");

		kidsprice = new Text(composite_29, SWT.BORDER);
		kidsprice.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		kidsprice.setText("{title}");

		Label label_10 = new Label(composite_29, SWT.NONE);
		label_10.setText("\u5E02\u573A\u4EF7\uFF1A");

		marketprice = new Text(composite_29, SWT.BORDER);
		marketprice.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		marketprice.setText("{title}");

		Label label_12 = new Label(composite_29, SWT.NONE);
		label_12.setText("\u5355\u623F\u5DEE\uFF1A");

		singledistance = new Text(composite_29, SWT.BORDER);
		singledistance.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		singledistance.setText("{title}");

		Composite composite_33 = new Composite(price_c, SWT.NONE);
		composite_33.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		composite_33.setLayout(new GridLayout(2, false));

		Label lblNewLabel_6 = new Label(composite_33, SWT.NONE);
		lblNewLabel_6.setText("\u5E93\u5B58\uFF1A");

		storenum = new Text(composite_33, SWT.BORDER);
		storenum.setText("{title}");

		Label label_8 = new Label(composite_33, SWT.NONE);
		label_8.setText("\u4E00\u6B21\u6700\u5C11");

		minnumtime = new Text(composite_33, SWT.BORDER);
		minnumtime.setText("{title}");

		Label label_9 = new Label(composite_33, SWT.NONE);
		label_9.setText("\u4E00\u6B21\u6700\u591A");

		maxnumtime = new Text(composite_33, SWT.BORDER);
		maxnumtime.setText("{title}");

		CTabItem tabItem_13 = new CTabItem(tabFolder_2, SWT.NONE);
		tabItem_13.setText("\u7EBF\u8DEF\u4F18\u60E0");

		Composite composite_11 = new Composite(tabFolder_2, SWT.NONE);
		tabItem_13.setControl(composite_11);
		composite_11.setLayout(new GridLayout(1, false));

		CTabFolder tabFolder_1 = new CTabFolder(composite_11, SWT.BORDER);
		tabFolder_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tabFolder_1.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));

		CTabItem tbtmNewItem = new CTabItem(tabFolder_1, SWT.NONE);
		tbtmNewItem.setText("\u591A\u4EBA\u7ACB\u51CF");

		Composite composite_21 = new Composite(tabFolder_1, SWT.NONE);
		tbtmNewItem.setControl(composite_21);
		composite_21.setLayout(new GridLayout(1, false));

		reduceprice = new Button(composite_21, SWT.CHECK);
		reduceprice.setText("\u542F\u7528\u4F18\u60E0");

		Group group_3 = new Group(composite_21, SWT.NONE);
		group_3.setLayout(new GridLayout(2, false));
		group_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		group_3.setText("\u6D3B\u52A8\u65F6\u95F4");

		disbegindate = new DateTime(group_3, SWT.BORDER | SWT.DROP_DOWN);

		disenddate = new DateTime(group_3, SWT.BORDER | SWT.DROP_DOWN);

		Group group_15 = new Group(composite_21, SWT.NONE);
		group_15.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		group_15.setText("\u53CC\u4EBA\u51CF\u514D");
		group_15.setLayout(new GridLayout(1, false));

		discount = new Combo(group_15, SWT.NONE | SWT.DROP_DOWN);
		discount.setItems(new String[] { "请选择", "双人出行减免300元", "双人出行减免500元", "双人出行减免1200元", "双人出行第二人打九折", "双人出行减免100元", "双人出行减免200元", "双人出行减免400元", "双人出行减免600元", "双人出行减免700元", "双人出行减免800元", "双人出行减免900元", "双人出行第二人打八折",
				"双人出行减免1000元" });
		discount.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		discount.select(0);

		CTabItem tbtmNewItem_3 = new CTabItem(tabFolder_1, SWT.NONE);
		tbtmNewItem_3.setText("\u65E9\u8BA2\u4F18\u60E0");

		Composite composite_13 = new Composite(tabFolder_1, SWT.NONE);
		tbtmNewItem_3.setControl(composite_13);
		composite_13.setLayout(new GridLayout(1, false));

		earlybookcheck = new Button(composite_13, SWT.CHECK);
		earlybookcheck.setText("\u542F\u7528\u4F18\u60E0");

		Group group_16 = new Group(composite_13, SWT.NONE);
		group_16.setLayout(new GridLayout(2, false));
		group_16.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		group_16.setText("\u6D3B\u52A8\u65F6\u95F4");

		earlystartdate = new DateTime(group_16, SWT.BORDER);

		earlyenddate = new DateTime(group_16, SWT.BORDER);

		Group group_17 = new Group(composite_13, SWT.NONE);
		group_17.setLayout(new GridLayout(3, false));
		group_17.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		Label label_3 = new Label(group_17, SWT.NONE);
		label_3.setText("\u63D0\u524D\u9884\u5B9A\u5929\u6570");

		Label label_4 = new Label(group_17, SWT.NONE);
		label_4.setText("\u6BCF\u4EBA\u51CF\u514D\u91D1\u989D");
		new Label(group_17, SWT.NONE);

		combo_1 = new Combo(group_17, SWT.NONE);
		combo_1.setItems(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" });
		combo_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		text_2 = new Text(group_17, SWT.BORDER);
		text_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button button_10 = new Button(group_17, SWT.NONE);
		button_10.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!combo_1.getText().equals("") && !text_2.getText().equals("")) {
					earlybookday.append(combo_1.getItem(combo_1.getSelectionIndex()) + "\r\n");
					earlydiscount.append(text_2.getText() + "\r\n");
				}
			}
		});
		button_10.setText("\u6DFB\u52A0");

		Composite composite_28 = new Composite(composite_13, SWT.NONE);
		composite_28.setLayout(new GridLayout(2, false));
		composite_28.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		earlybookday = new Text(composite_28, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		earlybookday.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		earlydiscount = new Text(composite_28, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		earlydiscount.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		CTabItem tabItem_5 = new CTabItem(tabFolder_1, SWT.NONE);
		tabItem_5.setText("\u4F18\u60E0\u4FC3\u9500");

		Composite composite_12 = new Composite(tabFolder_1, SWT.NONE);
		tabItem_5.setControl(composite_12);
		composite_12.setLayout(new GridLayout(1, false));

		favorabledisplaycheck = new Button(composite_12, SWT.CHECK);
		favorabledisplaycheck.setText("\u542F\u7528\u4F18\u60E0");

		Group group_11 = new Group(composite_12, SWT.NONE);
		group_11.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		group_11.setSize(187, 119);
		group_11.setLayout(new GridLayout(3, false));
		group_11.setText("\u4F18\u60E0\u65F6\u6BB5");

		favorabledisplaytype1 = new Button(group_11, SWT.RADIO);
		favorabledisplaytype1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				favorabledisplaystart.setEnabled(false);
				favorabledisplayend.setEnabled(false);
			}
		});
		favorabledisplaytype1.setSelection(true);
		favorabledisplaytype1.setText("\u59CB\u7EC8\u663E\u793A");

		favorabledisplaytype3 = new Button(group_11, SWT.RADIO);
		favorabledisplaytype3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				favorabledisplaystart.setEnabled(false);
				favorabledisplayend.setEnabled(false);
			}
		});
		favorabledisplaytype3.setText("\u4E0D\u663E\u793A");
		new Label(group_11, SWT.NONE);

		favorabledisplaytype2 = new Button(group_11, SWT.RADIO);
		favorabledisplaytype2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				favorabledisplaystart.setEnabled(((Button) e.getSource()).getSelection());
				favorabledisplayend.setEnabled(((Button) e.getSource()).getSelection());
			}
		});
		favorabledisplaytype2.setText("\u6307\u5B9A\u65F6\u6BB5\u663E\u793A");

		favorabledisplaystart = new DateTime(group_11, SWT.BORDER);
		favorabledisplaystart.setEnabled(false);

		favorabledisplayend = new DateTime(group_11, SWT.BORDER);
		favorabledisplayend.setEnabled(false);

		Group group_13 = new Group(composite_12, SWT.NONE);
		group_13.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		group_13.setSize(194, 53);
		group_13.setText("\u4F18\u60E0\u63CF\u8FF0");
		group_13.setLayout(new GridLayout(1, false));

		favorabledes = new Text(group_13, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		favorabledes.setText("{agelimit}");
		GridData gd_favorabledes = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_favorabledes.widthHint = 150;
		favorabledes.setLayoutData(gd_favorabledes);
		tabFolder_1.setSelection(0);

		CTabItem tabItem_14 = new CTabItem(tabFolder_2, SWT.NONE);
		tabItem_14.setText("\u623F\u95F4\u6570");

		Group group_14 = new Group(tabFolder_2, SWT.NONE);
		tabItem_14.setControl(group_14);
		group_14.setText("\u623F\u95F4\u6570");
		group_14.setLayout(new GridLayout(6, false));

		Button button_3 = new Button(group_14, SWT.RADIO);
		button_3.setText("1");

		Button button_4 = new Button(group_14, SWT.RADIO);
		button_4.setSelection(true);
		button_4.setText("2");

		Button button_5 = new Button(group_14, SWT.RADIO);
		button_5.setText("3");

		Button button_6 = new Button(group_14, SWT.RADIO);
		button_6.setText("4");

		Button button_7 = new Button(group_14, SWT.RADIO);
		button_7.setText("5");

		Button button_8 = new Button(group_14, SWT.RADIO);
		button_8.setText("6");
		tabFolder_2.setSelection(0);

		// ctv.addCheckStateListener(new ICheckStateListener() {
		// @Override
		// public void checkStateChanged(CheckStateChangedEvent event) {
		// UrlBean o = (UrlBean) event.getElement();
		// o.setIsselected(event.getChecked());
		// if (o.isIsselected()) {
		// selectnum++;
		// } else {
		// selectnum--;
		// }
		// showProcess();
		// }
		// });

		CTabItem tbtmNewItem_1 = new CTabItem(tabFolder, SWT.NONE);
		tbtmNewItem_1.setText("\u4EA7\u54C1\u7279\u8272\u6A21\u677F");

		Composite composite_14 = new Composite(tabFolder, SWT.NONE);
		tbtmNewItem_1.setControl(composite_14);
		composite_14.setLayout(new FillLayout(SWT.HORIZONTAL));

		Group group_19 = new Group(composite_14, SWT.NONE);
		group_19.setText("\u4EA7\u54C1\u7279\u8272");
		group_19.setLayout(new GridLayout(1, false));

		featurechecked = new Button(group_19, SWT.CHECK);
		featurechecked.setText("\u4F7F\u7528\u4EA7\u54C1\u7279\u8272\u56FE\u7247");

		Composite composite_31 = new Composite(group_19, SWT.NONE);
		composite_31.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		composite_31.setLayout(new GridLayout(2, false));

		Button button_12 = new Button(composite_31, SWT.NONE);
		button_12.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String spath = dirSelect(Main.this.getShell());
				txtfeaturepath.setText(spath);
			}
		});
		button_12.setText("\u56FE\u7247\u76EE\u5F55");

		txtfeaturepath = new Text(composite_31, SWT.BORDER);
		txtfeaturepath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		confeature = new Text(composite_31, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		confeature.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		confeature.setText("{title}");

		Label label_6 = new Label(composite_31, SWT.NONE);
		label_6.setText("\u6807\u7B7E\uFF1A");

		Label lbltitle = new Label(composite_31, SWT.NONE);
		lbltitle.setText("{title}{\u7279\u8272\u56FE\u7247}");

		CTabItem tbtmNewItem_2 = new CTabItem(tabFolder, SWT.NONE);
		tbtmNewItem_2.setText("\u8D39\u7528\u5305\u542B");

		Composite composite_15 = new Composite(tabFolder, SWT.NONE);
		tbtmNewItem_2.setControl(composite_15);
		composite_15.setLayout(new FillLayout(SWT.HORIZONTAL));

		txtfeeinclude = new Text(composite_15, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txtfeeinclude.setText("{title}");

		CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
		tabItem.setText("\u8D39\u7528\u4E0D\u5305\u542B");

		Composite composite_16 = new Composite(tabFolder, SWT.NONE);
		tabItem.setControl(composite_16);
		composite_16.setLayout(new FillLayout(SWT.HORIZONTAL));

		txtfeeexclude = new Text(composite_16, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txtfeeexclude.setText("{title}");

		CTabItem tabItem_1 = new CTabItem(tabFolder, SWT.NONE);
		tabItem_1.setText("\u91CD\u8981\u63D0\u793A");

		Composite composite_17 = new Composite(tabFolder, SWT.NONE);
		tabItem_1.setControl(composite_17);
		composite_17.setLayout(new FillLayout(SWT.HORIZONTAL));

		txtattention = new Text(composite_17, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txtattention.setText("{title}");

		CTabItem tabItem_2 = new CTabItem(tabFolder, SWT.NONE);
		tabItem_2.setText("\u53CB\u60C5\u63D0\u793A");

		Composite composite_18 = new Composite(tabFolder, SWT.NONE);
		tabItem_2.setControl(composite_18);
		composite_18.setLayout(new FillLayout(SWT.HORIZONTAL));

		txttip = new Text(composite_18, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txttip.setText("{title}");

		CTabItem tabItem_3 = new CTabItem(tabFolder, SWT.NONE);
		tabItem_3.setText("\u6BCF\u65E5\u884C\u7A0B\u56FE\u7247");

		Composite composite_19 = new Composite(tabFolder, SWT.NONE);
		tabItem_3.setControl(composite_19);
		composite_19.setLayout(new GridLayout(2, false));

		Group group_18 = new Group(composite_19, SWT.NONE);
		group_18.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		group_18.setText("\u884C\u7A0B\u56FE\u7247");
		group_18.setLayout(new GridLayout(1, false));

		composite_22 = new Composite(group_18, SWT.NONE);
		composite_22.setLayout(new GridLayout(2, false));

		Group group = new Group(composite_22, SWT.NONE);
		group.setLayout(new GridLayout(1, false));

		daysimgcheck = new Button(group, SWT.CHECK);
		daysimgcheck.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!daysimgcheck.getSelection()) {
					flash_show_use.setSelection(false);
					txtdiscription.setText("{title}");
				}
			}
		});
		daysimgcheck.setText("\u884C\u7A0B\u53C2\u8003\u662F\u5426\u4F7F\u7528\u672C\u5730\u56FE\u7247\u4E0A\u4F20");

		flash_show_use = new Button(group, SWT.CHECK);
		flash_show_use.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (flash_show_use.getSelection()) {
					daysimgcheck.setSelection(true);
					txtdiscription.setText("{title}{随机景点图片}{随机酒店图片}{随机美食图片}\n<object classid=\"clsid:d27cdb6e-ae6d-11cf-96b8-444553540000\" "
							+ "codebase=\"http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0\" width=\"737\" height=\"988\"> "
							+ "<param name=\"allowScriptAccess\" value=\"sameDomain\"> " + "<param name=\"movie\" value=\"http://www.gayosite.com/flash_new/pixviewer.swf\"> " + "<param name=\"quality\" value=\"heigh\"> "
							+ "<param name=\"bgcolor\" value=\"#FFFFFF\"> " + "<param name=\"menu\" value=\"false\"> " + "<param name=\"wmode\" value=\"opaque\"> "
							+ "<param name=\"FlashVars\" value=\"pics={fimg}&amp;links=&amp;texts=&amp;borderwidth=737&amp;borderheight=988\">" + "</object> ");
				} else {
					txtdiscription.setText("{title}");
				}
			}
		});
		flash_show_use.setText("\u542F\u7528flash\u63A7\u4EF6\u663E\u793A\u56FE\u7247");

		Group group_22 = new Group(composite_22, SWT.NONE);
		group_22.setLayout(new GridLayout(2, false));

		img_publish_style1 = new Button(group_22, SWT.RADIO);
		img_publish_style1.setText("\u53BB\u54EA\u513F\u6837\u5F0F");

		img_publish_style3 = new Button(group_22, SWT.RADIO);
		img_publish_style3.setText("\u4E00\u5217\u6837\u5F0F");

		img_publish_style2 = new Button(group_22, SWT.RADIO);
		img_publish_style2.setText("\u4E24\u5217\u6837\u5F0F");
		new Label(group_22, SWT.NONE);

		Composite composite_30 = new Composite(group_18, SWT.NONE);
		composite_30.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		composite_30.setLayout(new GridLayout(2, false));

		viewdir = new Button(composite_30, SWT.NONE);
		viewdir.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String spath = dirSelect(Main.this.getShell());
				txtviewimgdir.setText(spath);
			}
		});
		viewdir.setText("\u666F\u70B9\u76EE\u5F55");

		txtviewimgdir = new Text(composite_30, SWT.BORDER);
		txtviewimgdir.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		hotelimgdir = new Button(composite_30, SWT.NONE);
		hotelimgdir.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String spath = dirSelect(Main.this.getShell());
				txthotelimgdir.setText(spath);
			}
		});
		hotelimgdir.setText("\u9152\u5E97\u76EE\u5F55");

		txthotelimgdir = new Text(composite_30, SWT.BORDER);
		txthotelimgdir.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		foodimgdir = new Button(composite_30, SWT.NONE);
		foodimgdir.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String spath = dirSelect(Main.this.getShell());
				txtfoodimgdir.setText(spath);
			}
		});
		foodimgdir.setText("\u7F8E\u98DF\u76EE\u5F55");

		txtfoodimgdir = new Text(composite_30, SWT.BORDER);
		txtfoodimgdir.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		txtdiscription = new Text(composite_30, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		txtdiscription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		txtdiscription.setText("{title}");

		Label lblimgposition = new Label(composite_30, SWT.NONE);
		lblimgposition.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblimgposition.setText("\u81EA\u5B9A\u4E49\u6807\u7B7E\uFF1A{\u968F\u673A\u666F\u70B9\u56FE\u7247}{\u968F\u673A\u9152\u5E97\u56FE\u7247}{\u968F\u673A\u7F8E\u98DF\u56FE\u7247}{imgposition}");

		Group group_20 = new Group(composite_19, SWT.NONE);
		group_20.setLayout(new GridLayout(1, false));
		GridData gd_group_20 = new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 1);
		gd_group_20.widthHint = 375;
		group_20.setLayoutData(gd_group_20);
		group_20.setText("\u6BCF\u65E5\u884C\u7A0B\u5173\u952E\u5B57");

		dkeycheck = new Button(group_20, SWT.CHECK);
		dkeycheck.setText("\u542F\u7528\u6BCF\u65E5\u884C\u7A0B\u5173\u952E\u5B57\u7740\u8272");

		dkeystyle = new Text(group_20, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		dkeystyle.setText("color:red;font-weight:bold");
		GridData gd_dkeystyle = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_dkeystyle.heightHint = 224;
		dkeystyle.setLayoutData(gd_dkeystyle);

		Label lblNewLabel_1 = new Label(group_20, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("<span>{tag}</span>");

		Composite composite_32 = new Composite(group_20, SWT.NONE);
		composite_32.setLayout(new FillLayout(SWT.HORIZONTAL));
		composite_32.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));

		dkeytxt = new Text(composite_32, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);

		Label label_7 = new Label(group_20, SWT.NONE);
		label_7.setText("\u5173\u952E\u5B57\u7528\u82F1\u6587\",\"\u53F7\u9694\u5F00");

		CTabItem tabItem_4 = new CTabItem(tabFolder, SWT.NONE);
		tabItem_4.setText("\u6C34\u5370");

		Composite composite_20 = new Composite(tabFolder, SWT.NONE);
		tabItem_4.setControl(composite_20);
		composite_20.setLayout(new GridLayout(3, false));

		bpic1 = new Button(composite_20, SWT.NONE);
		bpic1.setEnabled(false);
		bpic1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String mpath = fileSelect(Main.this.getShell(), new String[] { "*.jpg", "*.png", "*.jif", "所有文件" }, new String[] { "*.jpg", "*.png", "*.jif", "*.*" }, System.getProperty("user.dir") + "/");
				spic1.setText(mpath);
				try {
					Image oimg = new Image(display, mpath);
					stamp1.setImage(oimg);
				} catch (Exception ex) {

				}
			}
		});
		bpic1.setText("\u6C34\u5370\u4E00");

		spic1 = new Text(composite_20, SWT.BORDER);
		spic1.setEnabled(false);
		spic1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Composite composite_26 = new Composite(composite_20, SWT.NONE);
		composite_26.setLayout(new GridLayout(3, false));
		composite_26.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 6));

		water_ck = new Button(composite_26, SWT.CHECK);
		water_ck.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 1));
		water_ck.setText("\u4F7F\u7528\u6C34\u5370");
		new Label(composite_26, SWT.NONE);
		new Label(composite_26, SWT.NONE);

		Button btnRadioButton = new Button(composite_26, SWT.RADIO);
		btnRadioButton.setText("\u5DE6\u4E0A");

		Button btnRadioButton_1 = new Button(composite_26, SWT.RADIO);
		btnRadioButton_1.setText("\u6B63\u4E0A");

		Button btnRadioButton_2 = new Button(composite_26, SWT.RADIO);
		btnRadioButton_2.setText("\u53F3\u4E0A");

		Button btnRadioButton_3 = new Button(composite_26, SWT.RADIO);
		btnRadioButton_3.setText("\u5DE6\u65B9");

		Button btnRadioButton_4 = new Button(composite_26, SWT.RADIO);
		btnRadioButton_4.setText("\u6B63\u4E2D");

		Button btnRadioButton_5 = new Button(composite_26, SWT.RADIO);
		btnRadioButton_5.setText("\u53F3\u8FB9");

		Button btnRadioButton_6 = new Button(composite_26, SWT.RADIO);
		btnRadioButton_6.setText("\u5DE6\u4E0B");

		Button btnRadioButton_7 = new Button(composite_26, SWT.RADIO);
		btnRadioButton_7.setText("\u6B63\u4E0B");

		Button btnRadioButton_8 = new Button(composite_26, SWT.RADIO);
		btnRadioButton_8.setText("\u53F3\u4E0B");

		bpic2 = new Button(composite_20, SWT.NONE);
		bpic2.setEnabled(false);
		bpic2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String mpath = fileSelect(Main.this.getShell(), new String[] { "*.jpg", "*.png", "*.jif", "所有文件" }, new String[] { "*.jpg", "*.png", "*.jif", "*.*" }, System.getProperty("user.dir") + "/");
				spic2.setText(mpath);
				try {
					Image oimg = new Image(display, mpath);
					stamp2.setImage(oimg);
				} catch (Exception ex) {

				}
			}
		});
		bpic2.setText("\u6C34\u5370\u4E8C");

		spic2 = new Text(composite_20, SWT.BORDER);
		spic2.setEnabled(false);
		spic2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		bpic3 = new Button(composite_20, SWT.NONE);
		bpic3.setEnabled(false);
		bpic3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String mpath = fileSelect(Main.this.getShell(), new String[] { "*.jpg", "*.png", "*.jif", "所有文件" }, new String[] { "*.jpg", "*.png", "*.jif", "*.*" }, System.getProperty("user.dir") + "/");
				spic3.setText(mpath);
				try {
					Image oimg = new Image(display, mpath);
					stamp3.setImage(oimg);
				} catch (Exception ex) {

				}
			}
		});
		bpic3.setText("\u6C34\u5370\u4E09");

		spic3 = new Text(composite_20, SWT.BORDER);
		spic3.setEnabled(false);
		spic3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		bpic4 = new Button(composite_20, SWT.NONE);
		bpic4.setEnabled(false);
		bpic4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String mpath = fileSelect(Main.this.getShell(), new String[] { "*.jpg", "*.png", "*.jif", "所有文件" }, new String[] { "*.jpg", "*.png", "*.jif", "*.*" }, System.getProperty("user.dir") + "/");
				spic4.setText(mpath);
				try {
					Image oimg = new Image(display, mpath);
					stamp4.setImage(oimg);
				} catch (Exception ex) {

				}
			}
		});
		bpic4.setText("\u6C34\u5370\u56DB");

		spic4 = new Text(composite_20, SWT.BORDER);
		spic4.setEnabled(false);
		spic4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		bpic5 = new Button(composite_20, SWT.NONE);
		bpic5.setEnabled(false);
		bpic5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String mpath = fileSelect(Main.this.getShell(), new String[] { "*.jpg", "*.png", "*.jif", "所有文件" }, new String[] { "*.jpg", "*.png", "*.jif", "*.*" }, System.getProperty("user.dir") + "/");
				spic5.setText(mpath);
				try {
					Image oimg = new Image(display, mpath);
					stamp5.setImage(oimg);
				} catch (Exception ex) {

				}
			}
		});
		bpic5.setText("\u6C34\u5370\u4E94");

		spic5 = new Text(composite_20, SWT.BORDER);
		spic5.setEnabled(false);
		spic5.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		bpic6 = new Button(composite_20, SWT.NONE);
		bpic6.setEnabled(false);
		bpic6.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String mpath = fileSelect(Main.this.getShell(), new String[] { "*.jpg", "*.png", "*.jif", "所有文件" }, new String[] { "*.jpg", "*.png", "*.jif", "*.*" }, System.getProperty("user.dir") + "/");
				spic6.setText(mpath);
				try {
					Image oimg = new Image(display, mpath);
					stamp6.setImage(oimg);
				} catch (Exception ex) {

				}
			}
		});
		bpic6.setText("\u6C34\u5370\u516D");

		spic6 = new Text(composite_20, SWT.BORDER);
		spic6.setEnabled(false);
		spic6.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Group group_4 = new Group(composite_20, SWT.NONE);
		group_4.setText("\u56FE\u7247\u9884\u89C8");
		group_4.setLayout(new GridLayout(6, false));
		group_4.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));

		Label lblNewLabel_7 = new Label(group_4, SWT.NONE);
		lblNewLabel_7.setText("\u56FE\u7247\u4E00");

		Label label_17 = new Label(group_4, SWT.NONE);
		label_17.setText("\u56FE\u7247\u4E8C");

		Label label_18 = new Label(group_4, SWT.NONE);
		label_18.setText("\u56FE\u7247\u4E09");

		Label label_19 = new Label(group_4, SWT.NONE);
		label_19.setText("\u56FE\u7247\u56DB");

		Label label_20 = new Label(group_4, SWT.NONE);
		label_20.setText("\u56FE\u7247\u4E94");

		Label label_21 = new Label(group_4, SWT.NONE);
		label_21.setText("\u56FE\u7247\u516D");

		stamp1 = new Label(group_4, SWT.NONE);
		GridData gd_stamp1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_stamp1.minimumHeight = 40;
		gd_stamp1.widthHint = 40;
		stamp1.setLayoutData(gd_stamp1);
		stamp1.setText("New Label");

		stamp2 = new Label(group_4, SWT.NONE);
		stamp2.setText("New Label");

		stamp3 = new Label(group_4, SWT.NONE);
		stamp3.setText("New Label");

		stamp4 = new Label(group_4, SWT.NONE);
		stamp4.setText("New Label");

		stamp5 = new Label(group_4, SWT.NONE);
		stamp5.setText("New Label");

		stamp6 = new Label(group_4, SWT.NONE);
		stamp6.setText("New Label");

		Composite composite_3 = new Composite(sashForm_1, SWT.NONE);
		composite_3.setSize(250, 23);
		composite_3.setLayout(new FillLayout(SWT.HORIZONTAL));

		log_txt = new Text(composite_3, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);

		tabFolder.setSelection(0);
		sashForm_1.setWeights(new int[] { 467, 180 });
		// hl.getVimage();
		m_bindingContext = initDataBindings();

		System.out.println("container");

		initButton_21();
		initCcombo();
		return container;
	}

	/**
	 * 文件选择
	 * 
	 * @param shell
	 * @return
	 */
	public String fileSelect(Shell shell, String[] filetype, String[] alias, String spath) {
		FileDialog fileselect = new FileDialog(shell, SWT.SINGLE | SWT.CENTER);
		if (spath == null)
			spath = System.getProperty("user.dir");
		fileselect.setFilterPath(spath);
		fileselect.setFilterNames(filetype);
		fileselect.setFilterExtensions(alias);
		String url = "";
		url = fileselect.open();
		System.out.println(url);
		return url;
	}

	/**
	 * 文件选择
	 * 
	 * @param shell
	 * @return
	 */
	public FileDialog fileSelectMul(Shell shell, String[] filetype, String[] alias, String spath) {
		FileDialog fileselect = new FileDialog(shell, SWT.MULTI | SWT.CENTER);
		if (spath == null)
			spath = System.getProperty("user.dir") + "/data/";
		fileselect.setFilterPath(spath);
		fileselect.setFilterNames(filetype);
		fileselect.setFilterExtensions(alias);
		String url = "";
		fileselect.open();
		return fileselect;
	}

	public void showProcess() {
		Runnable runnable = new Runnable() {
			public void run() {
				Main.this.display.asyncExec(new Runnable() {
					public void run() {
						load_show.setText(postnum + "/" + selectnum);
					}
				});
			}
		};
		new Thread(runnable).start();
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Create the menu manager.
	 * 
	 * @return the menu manager
	 */
	@Override
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager("menu");
		return menuManager;
	}

	/**
	 * Create the toolbar manager.
	 * 
	 * @return the toolbar manager
	 */
	@Override
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolBarManager = new ToolBarManager(style);
		Action deleteAction = new Action() {
			public void run() {
				// textSysInfo.setText("");
			}
		};
		deleteAction.setEnabled(false);
		deleteAction.setText(" ");
		// deleteAction.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
		toolBarManager.add(deleteAction);

		cselect = new ComboAction("31");
		cselect.setItems(new String[] { "\u65B0\u7F51\u7AD9", "\u65E7\u7F51\u7AD9", "qly\u7F51\u7AD9" });
		toolBarManager.add(cselect);

		uploadaction = new ButtonAction("32");
		uploadaction.setName("上传数据");
		toolBarManager.add(uploadaction);

		stopuploadaction = new ButtonAction((String) "33");
		stopuploadaction.setName("暂停上传");
		toolBarManager.add(stopuploadaction);

		saveuploadaction = new ButtonAction((String) "34");
		saveuploadaction.setName("保存配置");
		toolBarManager.add(saveuploadaction);

		loaduploadaction = new ButtonAction((String) "35");
		loaduploadaction.setName("加载配置");
		toolBarManager.add(loaduploadaction);

		System.out.println("toolBarManager");
		return toolBarManager;
	}

	/**
	 * Create the status line manager.
	 * 
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		return statusLineManager;
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		Display display = Display.getDefault();
		Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
			public void run() {
				try {
					Main window = new Main(Display.getDefault());
					window.setBlockOnOpen(true);
					window.open();
					Display.getCurrent().dispose();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Configure the shell.
	 * 
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("New Application");
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(974, 764);
	}

	/**
	 * 文件保存路径选择
	 * 
	 * @param shell
	 * @return
	 */
	public String dirSelect(Shell shell) {
		DirectoryDialog dirDlg = new DirectoryDialog(shell);
		dirDlg.setText("请选择路径");
		dirDlg.setMessage("这里的路径是文件生成的路径");
		dirDlg.setFilterPath(System.getProperty("user.dir"));
		String dir = dirDlg.open();
		return dir;
	}

	private void initCcombo() {
		scombo = cselect.getCombo();
		scombo.select(1);
		scombo.setToolTipText("请选择网站");
		scombo.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Main.this.line.setCselect(scombo.getItem(scombo.getSelectionIndex()));
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				widgetSelected(arg0);
			}
		});
	}

	private void loadLine(String fpath) {
		if (fpath != null) {
			// 2.读取过程
			XmlPersistence<Line> persistCmp = new XmlPersistence<Line>(fpath);
			java.util.List<Line> ls = persistCmp.loadAll();
			for (Line lt : ls) {
				line.getSummary().setTitle(lt.getSummary().getTitle());
				line.getSummary().setTeamno(lt.getSummary().getTeamno());
				line.getSummary().setRecommendation(lt.getSummary().getRecommendation());
				line.getSummary().setAdvanceday(lt.getSummary().getAdvanceday());
				line.getSummary().setAdvancedaytype(lt.getSummary().getAdvancedaytype());
				line.setBtcprice(lt.isBtcprice());
				line.setExcludefee(lt.getExcludefee());
				line.setIncludefee(lt.getIncludefee());
				line.setKidspricedesc(lt.getKidspricedesc());
				line.setKidsprice(lt.getKidsprice());
				line.setAdultprice(lt.getAdultprice());
				line.setMarketprice(lt.getMarketprice());
				line.setSigledistance(lt.getSigledistance());
				line.setStorenum(lt.getStorenum());
				line.setMinnumtime(lt.getMinnumtime());
				line.setMaxnumtime(lt.getMaxnumtime());
				line.getSummary().setFeatures(lt.getSummary().getFeatures());
				line.getSummary().setFeeinclude(lt.getSummary().getFeeinclude());
				line.getSummary().setFeeexclude(lt.getSummary().getFeeexclude());
				line.getSummary().setAttention(lt.getSummary().getAttention());
				line.getSummary().setTip(lt.getSummary().getTip());
				line.setDaysimgcheck(lt.isDaysimgcheck());
				line.setViewimgdir(lt.getViewimgdir());
				line.setHotelimgdir(lt.getHotelimgdir());
				line.setFoodimgdir(lt.getFoodimgdir());
				line.setTxtdiscription(lt.getTxtdiscription());
				line.setSinglelist(lt.isSinglelist());
				line.setMultilist(lt.isMultilist());
				try {
					String sp = lt.getLupath();
					while (true) {
						int in = sp.indexOf(" ");
						if (in == -1) {
							break;
						}
						sp = sp.substring(0, in) + "\r\n" + sp.substring(in + 1);
					}
					line.setLupath(sp);
				} catch (Exception eq) {
					hl.updateUI(eq.getMessage());
				}
				try {
					String sp = lt.getMupath();
					while (true) {
						int in = sp.indexOf(" ");
						if (in == -1) {
							break;
						}
						sp = sp.substring(0, in) + "\r\n" + sp.substring(in + 1);
					}
					line.setMupath(sp);
				} catch (Exception eq) {
					hl.updateUI(eq.getMessage());
				}
				hl.updateUI(line.getMupath());
				line.setBtc1(lt.isBtc1());
				line.setBtc2(lt.isBtc2());
				line.setAdck(lt.isAdck());
				line.setC1up(lt.isC1up());
				line.setC1nomain(lt.isC1nomain());
				line.setC1noimg(lt.isC1noimg());
				line.setLbc(lt.isLbc());
				line.setLdc(lt.isLdc());
				line.setLoc(lt.isLoc());
				line.setLpc(lt.isLpc());
				line.setPrate(lt.getPrate());
				line.setDisbegindate(lt.getDisbegindate());
				line.setDisenddate(lt.getDisenddate());
				line.setDiscount(lt.getDiscount());
				line.setReduceprice(lt.isReduceprice());
				line.setEarlybookcheck(lt.isEarlybookcheck());
				line.setFavorabledisplaycheck(lt.isFavorabledisplaycheck());
				line.setEarlystartdate(lt.getEarlystartdate());
				line.setEarlyenddate(lt.getEarlyenddate());
				try {
					String sp = lt.getEarlybookday();
					while (true) {
						int in = sp.indexOf(" ");
						if (in == -1) {
							break;
						}
						sp = sp.substring(0, in) + "\r\n" + sp.substring(in + 1);
					}
					line.setEarlybookday(sp);
				} catch (Exception eq) {
					hl.updateUI(eq.getMessage());
				}
				try {
					String sp = lt.getEarlydiscount();
					while (true) {
						int in = sp.indexOf(" ");
						if (in == -1) {
							break;
						}
						sp = sp.substring(0, in) + "\r\n" + sp.substring(in + 1);
					}
					line.setEarlydiscount(sp);
				} catch (Exception eq) {
					hl.updateUI(eq.getMessage());
				}
				line.setFavorabledisplaytype1(lt.isFavorabledisplaytype1());
				line.setFavorabledisplaytype2(lt.isFavorabledisplaytype2());
				line.setFavorabledisplaytype3(lt.isFavorabledisplaytype3());
				line.setFavorabledisplaystart(lt.getFavorabledisplaystart());
				line.setFavorabledisplayend(lt.getFavorabledisplayend());
				line.setFavorabledes(lt.getFavorabledes());
				line.getSummary().setFeaturechecked(lt.getSummary().isFeaturechecked());
				line.getSummary().setTxtfeaturepath(lt.getSummary().getTxtfeaturepath());
				line.getSummary().setConfeature(lt.getSummary().getConfeature());
				line.setDkeycheck(lt.isDkeycheck());
				line.setDkeytxt(lt.getDkeytxt());
				line.setDkeystyle(lt.getDkeystyle());
				line.setBtc2acc(lt.isBtc2acc());
				line.setSpic1(lt.getSpic1());
				line.setSpic2(lt.getSpic2());
				line.setSpic3(lt.getSpic3());
				line.setSpic4(lt.getSpic4());
				line.setSpic5(lt.getSpic5());
				line.setSpic6(lt.getSpic6());
				line.setChangespecial(lt.isChangespecial());
				line.setSpecialsatrttime(lt.getSpecialsatrttime());
				line.setSpecialendtime(lt.getSpecialendtime());
				line.setChangespecial2(lt.isChangespecial2());
				line.setSpecialsatrttime2(lt.getSpecialsatrttime2());
				line.setSpecialendtime2(lt.getSpecialendtime2());
				line.setChangespecial3(lt.isChangespecial3());
				line.setSpecialsatrttime3(lt.getSpecialsatrttime3());
				line.setSpecialendtime3(lt.getSpecialendtime3());
				line.setChangespecial4(lt.isChangespecial4());
				line.setSpecialdate(lt.getSpecialdate());
				line.setSpecealprice(lt.getSpecealprice());
				line.setHttpmodel(lt.isHttpmodel());
				line.setHttpsmodel(lt.isHttpsmodel());

				line.setPromise_guarantee_go(lt.isPromise_guarantee_go());
				line.setPromise_booking_current_day(lt.isPromise_booking_current_day());
				line.setPromise_truthful_description_free(lt.isPromise_truthful_description_free());
				line.setPromise_refund_anytime_not_consume(lt.isPromise_refund_anytime_not_consume());

				line.setImg_publish_style1(lt.isImg_publish_style1());
				line.setImg_publish_style2(lt.isImg_publish_style2());
				line.setImg_publish_style3(lt.isImg_publish_style3());

				line.setUsername(lt.getUsername());
				line.setPassword(lt.getPassword());
				line.setDowndatecheck1(lt.isDowndatecheck1());
				line.setDowndatecheck2(lt.isDowndatecheck2());
				line.setDown_date_begin(lt.getDown_date_begin());
				line.setDown_date_end(lt.getDown_date_end());

				line.setPricedate(lt.getPricedate());
				line.setPriceratedate(lt.getPriceratedate());
				if (lt.getPricedates() != null) {
					line.setPricedates(lt.getPricedates());
				}
				// 这儿要手工加载
				Main.this.display.syncExec(new Runnable() {
					@Override
					public void run() {
						d_list.removeAll();
						if (line.getPricedates().size() > 0) {
							for (String s : line.getPricedates()) {
								d_list.add(s);
							}
						}
					}
				});

				line.setLimitcount(lt.getLimitcount());
				line.setLimitcountchk(lt.isLimitcountchk());
				line.setPayck(lt.isPayck());
				line.setWaterck(lt.isWaterck());
				line.getSummary().setAdvancehour(lt.getSummary().getAdvancehour());
				line.getSummary().setAdvanceminute(lt.getSummary().getAdvanceminute());
				line.getSummary().setAdvancedesc(lt.getSummary().getAdvancedesc());
				line.getSummary().setPayway1(lt.getSummary().isPayway1());
				line.getSummary().setPayway2(lt.getSummary().isPayway2());
				line.getSummary().setPayway3(lt.getSummary().isPayway3());

				line.setSpecialsatrttime3(lt.getSpecialsatrttime3());
				line.setChangespecial3(lt.isChangespecial3());
				line.setPriceratedate3(lt.getPriceratedate3());
				line.setSpecialendtime3(lt.getSpecialendtime3());

				line.setMainpicselect(lt.isMainpicselect());
				line.setMainpictxt(lt.getMainpictxt());
				try {
					Image oimg = new Image(display, lt.getMainpictxt());
					mainshow.setImage(oimg);
				} catch (Exception ex) {

				}
				line.setPhonenum(lt.getPhonenum());
				line.setPromise_ironclad_group(lt.isPromise_ironclad_group());
				line.setPromise_no_self_pay(lt.isPromise_no_self_pay());
				line.setPromise_no_shopping(lt.isPromise_no_shopping());
				line.setPromise_truthful_description(lt.isPromise_truthful_description());
				line.setGroup_method0(lt.isGroup_method0());
				line.setGroup_method1(lt.isGroup_method1());
				line.setAssembly(lt.getAssembly());
				line.setGathertime(lt.getGathertime());
				line.setGatherspot(lt.getGatherspot());
			}
		}
	}

	private void initButton_21() {
		button_21 = uploadaction.getButton();
		button_21.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				globalisover = true;
				initLineSeries();// 保存公共配置
				final String[] paths = listViewer.getList().getItems();
				postnum = 0;
				selectnum = 0;
				if (ls.isGlobal_ck()) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							// 清除失败数据据
							BaseIni.nvslink.clear();
							for (final String fpath : paths) {
								loadLine(fpath);// 加载线路
								loadLineSeries();// 加载公共配置
								// postLine();
								if (!hl.getLoginstatus()) {
									hl.updateUI("请先登录");
									return;
								}

								if (line.getCselect() == null || line.getCselect().equals("")) {
									hl.updateUI("请选择一个网站");
									return;
								}

								if (!line.isSinglelist() && !line.isMultilist()) {
									hl.updateUI("没有数据处理");
									return;
								}

								String mu = line.getMupath();
								String lu = line.getLupath();
								if (mu.equals("") && lu.equals("")) {
									hl.updateUI("没有数据处理");
									return;
								}

								if (line.isSinglelist()) {
									if (!mu.equals("")) {
										String[] tem = mu.split("\r\n");
										for (String s : tem) {
											plist.add(s);
										}
									}
								}

								if (line.isMultilist()) {
									if (!lu.equals("")) {
										String[] tem = lu.split("\r\n");
										for (String s : tem) {
											try {
												System.out.println(s);
												hl.readLink(hl.getHtml(s), plist);
											} catch (Exception e1) {
												e1.printStackTrace();
											}
										}
									}
								}
								selectnum += plist.size();
								showProcess();
								Main.this.display.asyncExec(new Runnable() {
									public void run() {
										button_21.setEnabled(false);
									}
								});
								for (int i = 0; i < plist.size(); i++) {
									String furl = (String) plist.get(i);
									hl.getSourceLine(furl);
									hl.postLine();
									postnum++;
									showProcess();
									try {
										Thread.sleep(1000);
									} catch (InterruptedException e2) {
										e2.printStackTrace();
									}
								}
								plist.clear();
							}
							
							// 上传出错的价格数据
							hl.postErrorPrice();

							hl.updateUI("数据上传完成");
							globalisover = true;
							Main.this.display.asyncExec(new Runnable() {
								public void run() {
									button_21.setEnabled(true);
								}
							});
						}
					}).start();
				} else {
					hl.updateUI("没有选中全局数据");
				}
			}
		});
	}

	private void initLineSeries() {
		ls.setBtc1(bt_c1.getSelection());
		ls.setBtc2(bt_c2.getSelection());
		ls.setBtc2ac(bt_c2_activity.getSelection());
		ls.setBtc2acc(bt_c2_act_cancel.getSelection());
		ls.setC1noimg(bt_c1_noeditimgs.getSelection());
		ls.setC1nomain(bt_c1_noeditmainimg.getSelection());
		ls.setC1up(bt_c1_up.getSelection());
		ls.setC2del(bt_c2_del.getSelection());
		ls.setC2dn(bt_c2_del_dub_new.getSelection());
		ls.setC2down(bt_c2_down.getSelection());
		ls.setC2up(bt_c2_up.getSelection());
		ls.setLbc(l_bc.getSelection());
		ls.setLdc(l_dc.getSelection());
		ls.setLpc(l_pc.getSelection());
		ls.setLoc(l_oc.getSelection());
		ls.setLic(l_ic.getSelection());
		try {
			ls.setLimitcount(Integer.parseInt(limitcount.getText()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		ls.setLimitcountchk(limitcountchk.getSelection());
	}

	private void loadLineSeries() {
		line.setBtc1(ls.isBtc1());
		line.setBtc2(ls.isBtc2());
		line.setBtc2ac(ls.isBtc2ac());
		line.setBtc2acc(ls.isBtc2acc());
		line.setC1noimg(ls.isC1noimg());
		line.setC1nomain(ls.isC1nomain());
		line.setC1up(ls.isC1up());
		line.setC2del(ls.isC2del());
		line.setC2dn(ls.isC2dn());
		line.setC2down(ls.isC2down());
		line.setC2up(ls.isC2up());
		line.setLbc(ls.isLbc());
		line.setLdc(ls.isLdc());
		line.setLpc(ls.isLpc());
		line.setLoc(ls.isLoc());
		line.setLic(ls.isLic());
		try {
			line.setLimitcount(ls.getLimitcount());
		} catch (Exception e) {
			e.printStackTrace();
		}
		line.setLimitcountchk(ls.isLimitcountchk());
	}

	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue titleObserveTextObserveWidget = SWTObservables.observeText(title, SWT.Modify);
		IObservableValue linegetSummaryTitleObserveValue = BeansObservables.observeValue(line.getSummary(), "title");
		bindingContext.bindValue(titleObserveTextObserveWidget, linegetSummaryTitleObserveValue, null, null);
		//
		IObservableValue teamnoObserveTextObserveWidget = SWTObservables.observeText(teamno, SWT.Modify);
		IObservableValue linegetSummaryTeamnoObserveValue = BeansObservables.observeValue(line.getSummary(), "teamno");
		bindingContext.bindValue(teamnoObserveTextObserveWidget, linegetSummaryTeamnoObserveValue, null, null);
		//
		IObservableValue recommendationObserveTextObserveWidget = SWTObservables.observeText(recommendation, SWT.Modify);
		IObservableValue linegetSummaryRecommendationObserveValue = BeansObservables.observeValue(line.getSummary(), "recommendation");
		bindingContext.bindValue(recommendationObserveTextObserveWidget, linegetSummaryRecommendationObserveValue, null, null);
		//
		IObservableValue includefeeObserveTextObserveWidget = SWTObservables.observeText(includefee, SWT.Modify);
		IObservableValue lineIncludefeeObserveValue = BeansObservables.observeValue(line, "includefee");
		bindingContext.bindValue(includefeeObserveTextObserveWidget, lineIncludefeeObserveValue, null, null);
		//
		IObservableValue excludefeeObserveTextObserveWidget = SWTObservables.observeText(excludefee, SWT.Modify);
		IObservableValue lineExcludefeeObserveValue = BeansObservables.observeValue(line, "excludefee");
		bindingContext.bindValue(excludefeeObserveTextObserveWidget, lineExcludefeeObserveValue, null, null);
		//
		IObservableValue btnCheckButtonObserveSelectionObserveWidget = SWTObservables.observeSelection(btnCheckButton);
		IObservableValue lineBtcpriceObserveValue = BeansObservables.observeValue(line, "btcprice");
		bindingContext.bindValue(btnCheckButtonObserveSelectionObserveWidget, lineBtcpriceObserveValue, null, null);
		//
		IObservableValue kidspricedescObserveTextObserveWidget = SWTObservables.observeText(kidspricedesc, SWT.Modify);
		IObservableValue lineKidspricedescObserveValue = BeansObservables.observeValue(line, "kidspricedesc");
		bindingContext.bindValue(kidspricedescObserveTextObserveWidget, lineKidspricedescObserveValue, null, null);
		//
		IObservableValue kidspriceObserveTextObserveWidget = SWTObservables.observeText(kidsprice, SWT.Modify);
		IObservableValue lineKidspriceObserveValue = BeansObservables.observeValue(line, "kidsprice");
		bindingContext.bindValue(kidspriceObserveTextObserveWidget, lineKidspriceObserveValue, null, null);
		//
		IObservableValue adultpriceObserveTextObserveWidget = SWTObservables.observeText(adultprice, SWT.Modify);
		IObservableValue lineAdultpriceObserveValue = BeansObservables.observeValue(line, "adultprice");
		bindingContext.bindValue(adultpriceObserveTextObserveWidget, lineAdultpriceObserveValue, null, null);
		//
		IObservableValue marketpriceObserveTextObserveWidget = SWTObservables.observeText(marketprice, SWT.Modify);
		IObservableValue lineMarketpriceObserveValue = BeansObservables.observeValue(line, "marketprice");
		bindingContext.bindValue(marketpriceObserveTextObserveWidget, lineMarketpriceObserveValue, null, null);
		//
		IObservableValue singledistanceObserveTextObserveWidget = SWTObservables.observeText(singledistance, SWT.Modify);
		IObservableValue lineSigledistanceObserveValue = BeansObservables.observeValue(line, "sigledistance");
		bindingContext.bindValue(singledistanceObserveTextObserveWidget, lineSigledistanceObserveValue, null, null);
		//
		IObservableValue storenumObserveTextObserveWidget = SWTObservables.observeText(storenum, SWT.Modify);
		IObservableValue lineStorenumObserveValue = BeansObservables.observeValue(line, "storenum");
		bindingContext.bindValue(storenumObserveTextObserveWidget, lineStorenumObserveValue, null, null);
		//
		IObservableValue minnumtimeObserveTextObserveWidget = SWTObservables.observeText(minnumtime, SWT.Modify);
		IObservableValue lineMinnumtimeObserveValue = BeansObservables.observeValue(line, "minnumtime");
		bindingContext.bindValue(minnumtimeObserveTextObserveWidget, lineMinnumtimeObserveValue, null, null);
		//
		IObservableValue maxnumtimeObserveTextObserveWidget = SWTObservables.observeText(maxnumtime, SWT.Modify);
		IObservableValue lineMaxnumtimeObserveValue = BeansObservables.observeValue(line, "maxnumtime");
		bindingContext.bindValue(maxnumtimeObserveTextObserveWidget, lineMaxnumtimeObserveValue, null, null);
		//
		IObservableValue txtfeaturelistObserveTextObserveWidget = SWTObservables.observeText(confeature, SWT.Modify);
		IObservableValue linegetSummaryFeaturesObserveValue = BeansObservables.observeValue(line.getSummary(), "features");
		bindingContext.bindValue(txtfeaturelistObserveTextObserveWidget, linegetSummaryFeaturesObserveValue, null, null);
		//
		IObservableValue txtfeeincludeObserveTextObserveWidget = SWTObservables.observeText(txtfeeinclude, SWT.Modify);
		IObservableValue linegetSummaryFeeincludeObserveValue = BeansObservables.observeValue(line.getSummary(), "feeinclude");
		bindingContext.bindValue(txtfeeincludeObserveTextObserveWidget, linegetSummaryFeeincludeObserveValue, null, null);
		//
		IObservableValue txtfeeexcludeObserveTextObserveWidget = SWTObservables.observeText(txtfeeexclude, SWT.Modify);
		IObservableValue linegetSummaryFeeexcludeObserveValue = BeansObservables.observeValue(line.getSummary(), "feeexclude");
		bindingContext.bindValue(txtfeeexcludeObserveTextObserveWidget, linegetSummaryFeeexcludeObserveValue, null, null);
		//
		IObservableValue txtattentionObserveTextObserveWidget = SWTObservables.observeText(txtattention, SWT.Modify);
		IObservableValue linegetSummaryAttentionObserveValue = BeansObservables.observeValue(line.getSummary(), "attention");
		bindingContext.bindValue(txtattentionObserveTextObserveWidget, linegetSummaryAttentionObserveValue, null, null);
		//
		IObservableValue txttipObserveTextObserveWidget = SWTObservables.observeText(txttip, SWT.Modify);
		IObservableValue linegetSummaryTipObserveValue = BeansObservables.observeValue(line.getSummary(), "tip");
		bindingContext.bindValue(txttipObserveTextObserveWidget, linegetSummaryTipObserveValue, null, null);
		//
		IObservableValue daysimgcheckObserveSelectionObserveWidget = SWTObservables.observeSelection(daysimgcheck);
		IObservableValue lineDaysimgcheckObserveValue = BeansObservables.observeValue(line, "daysimgcheck");
		bindingContext.bindValue(daysimgcheckObserveSelectionObserveWidget, lineDaysimgcheckObserveValue, null, null);
		//
		IObservableValue txtviewimgdirObserveTextObserveWidget = SWTObservables.observeText(txtviewimgdir, SWT.Modify);
		IObservableValue lineViewimgdirObserveValue = BeansObservables.observeValue(line, "viewimgdir");
		bindingContext.bindValue(txtviewimgdirObserveTextObserveWidget, lineViewimgdirObserveValue, null, null);
		//
		IObservableValue txthotelimgdirObserveTextObserveWidget = SWTObservables.observeText(txthotelimgdir, SWT.Modify);
		IObservableValue lineHotelimgdirObserveValue = BeansObservables.observeValue(line, "hotelimgdir");
		bindingContext.bindValue(txthotelimgdirObserveTextObserveWidget, lineHotelimgdirObserveValue, null, null);
		//
		IObservableValue txtfoodimgdirObserveTextObserveWidget = SWTObservables.observeText(txtfoodimgdir, SWT.Modify);
		IObservableValue lineFoodimgdirObserveValue = BeansObservables.observeValue(line, "foodimgdir");
		bindingContext.bindValue(txtfoodimgdirObserveTextObserveWidget, lineFoodimgdirObserveValue, null, null);
		//
		IObservableValue txtdiscriptionObserveTextObserveWidget = SWTObservables.observeText(txtdiscription, SWT.Modify);
		IObservableValue lineTxtdiscriptionObserveValue = BeansObservables.observeValue(line, "txtdiscription");
		bindingContext.bindValue(txtdiscriptionObserveTextObserveWidget, lineTxtdiscriptionObserveValue, null, null);
		//
		IObservableValue singlelistObserveSelectionObserveWidget = SWTObservables.observeSelection(singlelist);
		IObservableValue lineSinglelistObserveValue = BeansObservables.observeValue(line, "singlelist");
		bindingContext.bindValue(singlelistObserveSelectionObserveWidget, lineSinglelistObserveValue, null, null);
		//
		IObservableValue mutilistObserveSelectionObserveWidget = SWTObservables.observeSelection(mutilist);
		IObservableValue lineMultilistObserveValue = BeansObservables.observeValue(line, "multilist");
		bindingContext.bindValue(mutilistObserveSelectionObserveWidget, lineMultilistObserveValue, null, null);
		//
		IObservableValue bt_c1ObserveSelectionObserveWidget = SWTObservables.observeSelection(bt_c1);
		IObservableValue lineBtc1ObserveValue = BeansObservables.observeValue(line, "btc1");
		bindingContext.bindValue(bt_c1ObserveSelectionObserveWidget, lineBtc1ObserveValue, null, null);
		//
		IObservableValue bt_c2ObserveSelectionObserveWidget = SWTObservables.observeSelection(bt_c2);
		IObservableValue lineBtc2ObserveValue = BeansObservables.observeValue(line, "btc2");
		bindingContext.bindValue(bt_c2ObserveSelectionObserveWidget, lineBtc2ObserveValue, null, null);
		//
		IObservableValue bt_c1_upObserveSelectionObserveWidget = SWTObservables.observeSelection(bt_c1_up);
		IObservableValue lineC1upObserveValue = BeansObservables.observeValue(line, "c1up");
		bindingContext.bindValue(bt_c1_upObserveSelectionObserveWidget, lineC1upObserveValue, null, null);
		//
		IObservableValue bt_c1_noeditmainimgObserveSelectionObserveWidget = SWTObservables.observeSelection(bt_c1_noeditmainimg);
		IObservableValue lineC1nomainObserveValue = BeansObservables.observeValue(line, "c1nomain");
		bindingContext.bindValue(bt_c1_noeditmainimgObserveSelectionObserveWidget, lineC1nomainObserveValue, null, null);
		//
		IObservableValue bt_c1_noeditimgsObserveSelectionObserveWidget = SWTObservables.observeSelection(bt_c1_noeditimgs);
		IObservableValue lineC1noimgObserveValue = BeansObservables.observeValue(line, "c1noimg");
		bindingContext.bindValue(bt_c1_noeditimgsObserveSelectionObserveWidget, lineC1noimgObserveValue, null, null);
		//
		IObservableValue l_bcObserveSelectionObserveWidget = SWTObservables.observeSelection(l_bc);
		IObservableValue lineLbcObserveValue = BeansObservables.observeValue(line, "lbc");
		bindingContext.bindValue(l_bcObserveSelectionObserveWidget, lineLbcObserveValue, null, null);
		//
		IObservableValue l_dcObserveSelectionObserveWidget = SWTObservables.observeSelection(l_dc);
		IObservableValue lineLdcObserveValue = BeansObservables.observeValue(line, "ldc");
		bindingContext.bindValue(l_dcObserveSelectionObserveWidget, lineLdcObserveValue, null, null);
		//
		IObservableValue l_pcObserveSelectionObserveWidget = SWTObservables.observeSelection(l_pc);
		IObservableValue lineLpcObserveValue = BeansObservables.observeValue(line, "lpc");
		bindingContext.bindValue(l_pcObserveSelectionObserveWidget, lineLpcObserveValue, null, null);
		//
		IObservableValue l_ocObserveSelectionObserveWidget = SWTObservables.observeSelection(l_oc);
		IObservableValue lineLocObserveValue = BeansObservables.observeValue(line, "loc");
		bindingContext.bindValue(l_ocObserveSelectionObserveWidget, lineLocObserveValue, null, null);
		//
		IObservableValue bt_c2_upObserveSelectionObserveWidget = SWTObservables.observeSelection(bt_c2_up);
		IObservableValue lineC2upObserveValue = BeansObservables.observeValue(line, "c2up");
		bindingContext.bindValue(bt_c2_upObserveSelectionObserveWidget, lineC2upObserveValue, null, null);
		//
		IObservableValue bt_c2_downObserveSelectionObserveWidget = SWTObservables.observeSelection(bt_c2_down);
		IObservableValue lineC2downObserveValue = BeansObservables.observeValue(line, "c2down");
		bindingContext.bindValue(bt_c2_downObserveSelectionObserveWidget, lineC2downObserveValue, null, null);
		//
		IObservableValue bt_c2_delObserveSelectionObserveWidget = SWTObservables.observeSelection(bt_c2_del);
		IObservableValue lineC2delObserveValue = BeansObservables.observeValue(line, "c2del");
		bindingContext.bindValue(bt_c2_delObserveSelectionObserveWidget, lineC2delObserveValue, null, null);
		//
		IObservableValue bt_c2_del_dub_newObserveSelectionObserveWidget = SWTObservables.observeSelection(bt_c2_del_dub_new);
		IObservableValue lineC2dnObserveValue = BeansObservables.observeValue(line, "c2dn");
		bindingContext.bindValue(bt_c2_del_dub_newObserveSelectionObserveWidget, lineC2dnObserveValue, null, null);
		//
		IObservableValue advancedayObserveTextObserveWidget = SWTObservables.observeText(advanceday, SWT.Modify);
		IObservableValue linegetSummaryAdvancedayObserveValue = BeansObservables.observeValue(line.getSummary(), "advanceday");
		bindingContext.bindValue(advancedayObserveTextObserveWidget, linegetSummaryAdvancedayObserveValue, null, null);
		//
		IObservableValue advancedaytypeObserveTextObserveWidget = SWTObservables.observeText(advancedaytype);
		IObservableValue linegetSummaryAdvancedaytypeObserveValue = BeansObservables.observeValue(line.getSummary(), "advancedaytype");
		bindingContext.bindValue(advancedaytypeObserveTextObserveWidget, linegetSummaryAdvancedaytypeObserveValue, null, null);
		//
		IObservableValue ad_ckObserveSelectionObserveWidget = SWTObservables.observeSelection(ad_ck);
		IObservableValue lineAdckObserveValue = BeansObservables.observeValue(line, "adck");
		bindingContext.bindValue(ad_ckObserveSelectionObserveWidget, lineAdckObserveValue, null, null);
		//
		IObservableValue pay_ckObserveSelectionObserveWidget = SWTObservables.observeSelection(pay_ck);
		IObservableValue linePayckObserveValue = BeansObservables.observeValue(line, "payck");
		bindingContext.bindValue(pay_ckObserveSelectionObserveWidget, linePayckObserveValue, null, null);
		//
		IObservableValue payway1ObserveSelectionObserveWidget = SWTObservables.observeSelection(payway1);
		IObservableValue linegetSummaryPayway1ObserveValue = BeansObservables.observeValue(line.getSummary(), "payway1");
		bindingContext.bindValue(payway1ObserveSelectionObserveWidget, linegetSummaryPayway1ObserveValue, null, null);
		//
		IObservableValue payway2ObserveSelectionObserveWidget = SWTObservables.observeSelection(payway2);
		IObservableValue linegetSummaryPayway2ObserveValue = BeansObservables.observeValue(line.getSummary(), "payway2");
		bindingContext.bindValue(payway2ObserveSelectionObserveWidget, linegetSummaryPayway2ObserveValue, null, null);
		//
		IObservableValue price_rateObserveTextObserveWidget = SWTObservables.observeText(price_rate, SWT.Modify);
		IObservableValue linePrateObserveValue = BeansObservables.observeValue(line, "prate");
		bindingContext.bindValue(price_rateObserveTextObserveWidget, linePrateObserveValue, null, null);
		//
		IObservableValue lupathObserveTextObserveWidget = SWTObservables.observeText(lupath, SWT.Modify);
		IObservableValue lineLupathObserveValue = BeansObservables.observeValue(line, "lupath");
		bindingContext.bindValue(lupathObserveTextObserveWidget, lineLupathObserveValue, null, null);
		//
		IObservableValue mupathObserveTextObserveWidget = SWTObservables.observeText(mupath, SWT.Modify);
		IObservableValue lineMupathObserveValue = BeansObservables.observeValue(line, "mupath");
		bindingContext.bindValue(mupathObserveTextObserveWidget, lineMupathObserveValue, null, null);
		//
		IObservableValue disbegindateObserveSelectionObserveWidget = SWTObservables.observeSelection(disbegindate);
		IObservableValue lineDisbegindateObserveValue = BeansObservables.observeValue(line, "disbegindate");
		bindingContext.bindValue(disbegindateObserveSelectionObserveWidget, lineDisbegindateObserveValue, null, null);
		//
		IObservableValue disenddateObserveSelectionObserveWidget = SWTObservables.observeSelection(disenddate);
		IObservableValue lineDisenddateObserveValue = BeansObservables.observeValue(line, "disenddate");
		bindingContext.bindValue(disenddateObserveSelectionObserveWidget, lineDisenddateObserveValue, null, null);
		//
		IObservableValue discountObserveSingleSelectionIndexObserveWidget = SWTObservables.observeSingleSelectionIndex(discount);
		IObservableValue lineDiscountObserveValue = BeansObservables.observeValue(line, "discount");
		bindingContext.bindValue(discountObserveSingleSelectionIndexObserveWidget, lineDiscountObserveValue, null, null);
		//
		IObservableValue reducepriceObserveSelectionObserveWidget = SWTObservables.observeSelection(reduceprice);
		IObservableValue lineReducepriceObserveValue = BeansObservables.observeValue(line, "reduceprice");
		bindingContext.bindValue(reducepriceObserveSelectionObserveWidget, lineReducepriceObserveValue, null, null);
		//
		IObservableValue earlybookcheckObserveSelectionObserveWidget = SWTObservables.observeSelection(earlybookcheck);
		IObservableValue lineEarlybookcheckObserveValue = BeansObservables.observeValue(line, "earlybookcheck");
		bindingContext.bindValue(earlybookcheckObserveSelectionObserveWidget, lineEarlybookcheckObserveValue, null, null);
		//
		IObservableValue earlystartdateObserveSelectionObserveWidget = SWTObservables.observeSelection(earlystartdate);
		IObservableValue lineEarlystartdateObserveValue = BeansObservables.observeValue(line, "earlystartdate");
		bindingContext.bindValue(earlystartdateObserveSelectionObserveWidget, lineEarlystartdateObserveValue, null, null);
		//
		IObservableValue earlyenddateObserveSelectionObserveWidget = SWTObservables.observeSelection(earlyenddate);
		IObservableValue lineEarlyenddateObserveValue = BeansObservables.observeValue(line, "earlyenddate");
		bindingContext.bindValue(earlyenddateObserveSelectionObserveWidget, lineEarlyenddateObserveValue, null, null);
		//
		IObservableValue earlybookdayObserveTextObserveWidget = SWTObservables.observeText(earlybookday, SWT.Modify);
		IObservableValue lineEarlybookdayObserveValue = BeansObservables.observeValue(line, "earlybookday");
		bindingContext.bindValue(earlybookdayObserveTextObserveWidget, lineEarlybookdayObserveValue, null, null);
		//
		IObservableValue earlydiscountObserveTextObserveWidget = SWTObservables.observeText(earlydiscount, SWT.Modify);
		IObservableValue lineEarlydiscountObserveValue = BeansObservables.observeValue(line, "earlydiscount");
		bindingContext.bindValue(earlydiscountObserveTextObserveWidget, lineEarlydiscountObserveValue, null, null);
		//
		IObservableValue favorabledisplaytype1ObserveSelectionObserveWidget = SWTObservables.observeSelection(favorabledisplaytype1);
		IObservableValue lineFavorabledisplaytype1ObserveValue = BeansObservables.observeValue(line, "favorabledisplaytype1");
		bindingContext.bindValue(favorabledisplaytype1ObserveSelectionObserveWidget, lineFavorabledisplaytype1ObserveValue, null, null);
		//
		IObservableValue favorabledisplaytype2ObserveSelectionObserveWidget = SWTObservables.observeSelection(favorabledisplaytype2);
		IObservableValue lineFavorabledisplaytype2ObserveValue = BeansObservables.observeValue(line, "favorabledisplaytype2");
		bindingContext.bindValue(favorabledisplaytype2ObserveSelectionObserveWidget, lineFavorabledisplaytype2ObserveValue, null, null);
		//
		IObservableValue favorabledisplaytype3ObserveSelectionObserveWidget = SWTObservables.observeSelection(favorabledisplaytype3);
		IObservableValue lineFavorabledisplaytype3ObserveValue = BeansObservables.observeValue(line, "favorabledisplaytype3");
		bindingContext.bindValue(favorabledisplaytype3ObserveSelectionObserveWidget, lineFavorabledisplaytype3ObserveValue, null, null);
		//
		IObservableValue favorabledisplaystartObserveSelectionObserveWidget = SWTObservables.observeSelection(favorabledisplaystart);
		IObservableValue lineFavorabledisplaystartObserveValue = BeansObservables.observeValue(line, "favorabledisplaystart");
		bindingContext.bindValue(favorabledisplaystartObserveSelectionObserveWidget, lineFavorabledisplaystartObserveValue, null, null);
		//
		IObservableValue favorabledisplayendObserveSelectionObserveWidget = SWTObservables.observeSelection(favorabledisplayend);
		IObservableValue lineFavorabledisplayendObserveValue = BeansObservables.observeValue(line, "favorabledisplayend");
		bindingContext.bindValue(favorabledisplayendObserveSelectionObserveWidget, lineFavorabledisplayendObserveValue, null, null);
		//
		IObservableValue favorabledesObserveTextObserveWidget = SWTObservables.observeText(favorabledes, SWT.Modify);
		IObservableValue lineFavorabledesObserveValue = BeansObservables.observeValue(line, "favorabledes");
		bindingContext.bindValue(favorabledesObserveTextObserveWidget, lineFavorabledesObserveValue, null, null);
		//
		IObservableValue bt_c2_activityObserveSelectionObserveWidget = SWTObservables.observeSelection(bt_c2_activity);
		IObservableValue lineBtc2acObserveValue = BeansObservables.observeValue(line, "btc2ac");
		bindingContext.bindValue(bt_c2_activityObserveSelectionObserveWidget, lineBtc2acObserveValue, null, null);
		//
		IObservableValue favorabledisplaycheckObserveSelectionObserveWidget = SWTObservables.observeSelection(favorabledisplaycheck);
		IObservableValue lineFavorabledisplaycheckObserveValue = BeansObservables.observeValue(line, "favorabledisplaycheck");
		bindingContext.bindValue(favorabledisplaycheckObserveSelectionObserveWidget, lineFavorabledisplaycheckObserveValue, null, null);
		//
		IObservableValue featurecheckedObserveSelectionObserveWidget = SWTObservables.observeSelection(featurechecked);
		IObservableValue linegetSummaryFeaturecheckedObserveValue = BeansObservables.observeValue(line.getSummary(), "featurechecked");
		bindingContext.bindValue(featurecheckedObserveSelectionObserveWidget, linegetSummaryFeaturecheckedObserveValue, null, null);
		//
		IObservableValue txtfeaturepathObserveTextObserveWidget = SWTObservables.observeText(txtfeaturepath, SWT.Modify);
		IObservableValue linegetSummaryTxtfeaturepathObserveValue = BeansObservables.observeValue(line.getSummary(), "txtfeaturepath");
		bindingContext.bindValue(txtfeaturepathObserveTextObserveWidget, linegetSummaryTxtfeaturepathObserveValue, null, null);
		//
		IObservableValue confeatureObserveTextObserveWidget = SWTObservables.observeText(confeature, SWT.Modify);
		IObservableValue linegetSummaryConfeatureObserveValue = BeansObservables.observeValue(line.getSummary(), "confeature");
		bindingContext.bindValue(confeatureObserveTextObserveWidget, linegetSummaryConfeatureObserveValue, null, null);
		//
		IObservableValue txttitleObserveTextObserveWidget = SWTObservables.observeText(txttitle, SWT.Modify);
		IObservableValue linegetSummaryDepartureObserveValue = BeansObservables.observeValue(line.getSummary(), "departure");
		bindingContext.bindValue(txttitleObserveTextObserveWidget, linegetSummaryDepartureObserveValue, null, null);
		//
		IObservableValue dkeytxtObserveTextObserveWidget = SWTObservables.observeText(dkeytxt, SWT.Modify);
		IObservableValue lineDkeytxtObserveValue = BeansObservables.observeValue(line, "dkeytxt");
		bindingContext.bindValue(dkeytxtObserveTextObserveWidget, lineDkeytxtObserveValue, null, null);
		//
		IObservableValue dkeycheckObserveSelectionObserveWidget = SWTObservables.observeSelection(dkeycheck);
		IObservableValue lineDkeycheckObserveValue = BeansObservables.observeValue(line, "dkeycheck");
		bindingContext.bindValue(dkeycheckObserveSelectionObserveWidget, lineDkeycheckObserveValue, null, null);
		//
		IObservableValue dkeystyleObserveTextObserveWidget = SWTObservables.observeText(dkeystyle, SWT.Modify);
		IObservableValue lineDkeystyleObserveValue = BeansObservables.observeValue(line, "dkeystyle");
		bindingContext.bindValue(dkeystyleObserveTextObserveWidget, lineDkeystyleObserveValue, null, null);
		//
		IObservableValue bt_c2_act_cancelObserveSelectionObserveWidget = SWTObservables.observeSelection(bt_c2_act_cancel);
		IObservableValue lineBtc2accObserveValue = BeansObservables.observeValue(line, "btc2acc");
		bindingContext.bindValue(bt_c2_act_cancelObserveSelectionObserveWidget, lineBtc2accObserveValue, null, null);
		//
		IObservableValue payway3ObserveSelectionObserveWidget = SWTObservables.observeSelection(payway3);
		IObservableValue linegetSummaryPayway3ObserveValue = BeansObservables.observeValue(line.getSummary(), "payway3");
		bindingContext.bindValue(payway3ObserveSelectionObserveWidget, linegetSummaryPayway3ObserveValue, null, null);
		//
		IObservableValue spic1ObserveTextObserveWidget = SWTObservables.observeText(spic1, SWT.Modify);
		IObservableValue lineSpic1ObserveValue = BeansObservables.observeValue(line, "spic1");
		bindingContext.bindValue(spic1ObserveTextObserveWidget, lineSpic1ObserveValue, null, null);
		//
		IObservableValue spic2ObserveTextObserveWidget = SWTObservables.observeText(spic2, SWT.Modify);
		IObservableValue lineSpic2ObserveValue = BeansObservables.observeValue(line, "spic2");
		bindingContext.bindValue(spic2ObserveTextObserveWidget, lineSpic2ObserveValue, null, null);
		//
		IObservableValue spic3ObserveTextObserveWidget = SWTObservables.observeText(spic3, SWT.Modify);
		IObservableValue lineSpic3ObserveValue = BeansObservables.observeValue(line, "spic3");
		bindingContext.bindValue(spic3ObserveTextObserveWidget, lineSpic3ObserveValue, null, null);
		//
		IObservableValue spic4ObserveTextObserveWidget = SWTObservables.observeText(spic4, SWT.Modify);
		IObservableValue lineSpic4ObserveValue = BeansObservables.observeValue(line, "spic4");
		bindingContext.bindValue(spic4ObserveTextObserveWidget, lineSpic4ObserveValue, null, null);
		//
		IObservableValue spic5ObserveTextObserveWidget = SWTObservables.observeText(spic5, SWT.Modify);
		IObservableValue lineSpic5ObserveValue = BeansObservables.observeValue(line, "spic5");
		bindingContext.bindValue(spic5ObserveTextObserveWidget, lineSpic5ObserveValue, null, null);
		//
		IObservableValue spic6ObserveTextObserveWidget = SWTObservables.observeText(spic6, SWT.Modify);
		IObservableValue lineSpic6ObserveValue = BeansObservables.observeValue(line, "spic6");
		bindingContext.bindValue(spic6ObserveTextObserveWidget, lineSpic6ObserveValue, null, null);
		//
		IObservableValue stamp1ObserveImageObserveWidget = SWTObservables.observeImage(stamp1);
		bindingContext.bindValue(stamp1ObserveImageObserveWidget, lineSpic1ObserveValue, null, null);
		//
		IObservableValue stamp2ObserveImageObserveWidget = SWTObservables.observeImage(stamp2);
		bindingContext.bindValue(stamp2ObserveImageObserveWidget, lineSpic2ObserveValue, null, null);
		//
		IObservableValue specialstarttimeObserveSelectionObserveWidget = SWTObservables.observeSelection(specialstarttime);
		IObservableValue lineSpecialsatrttimeObserveValue = BeansObservables.observeValue(line, "specialsatrttime");
		bindingContext.bindValue(specialstarttimeObserveSelectionObserveWidget, lineSpecialsatrttimeObserveValue, null, null);
		//
		IObservableValue specialendtimeObserveSelectionObserveWidget = SWTObservables.observeSelection(specialendtime);
		IObservableValue lineSpecialendtimeObserveValue = BeansObservables.observeValue(line, "specialendtime");
		bindingContext.bindValue(specialendtimeObserveSelectionObserveWidget, lineSpecialendtimeObserveValue, null, null);
		//
		IObservableValue changespecialObserveSelectionObserveWidget = SWTObservables.observeSelection(changespecial);
		IObservableValue lineChangespecialObserveValue = BeansObservables.observeValue(line, "changespecial");
		bindingContext.bindValue(changespecialObserveSelectionObserveWidget, lineChangespecialObserveValue, null, null);
		//
		IObservableValue price_rate_dateObserveTextObserveWidget = SWTObservables.observeText(price_rate_date, SWT.Modify);
		IObservableValue linePriceratedateObserveValue = BeansObservables.observeValue(line, "priceratedate");
		bindingContext.bindValue(price_rate_dateObserveTextObserveWidget, linePriceratedateObserveValue, null, null);
		//
		IObservableValue changespecialObserveSelectionObserveWidget_1 = SWTObservables.observeSelection(changespecial);
		IObservableValue price_rate_dateObserveEnabledObserveWidget = SWTObservables.observeEnabled(price_rate_date);
		bindingContext.bindValue(changespecialObserveSelectionObserveWidget_1, price_rate_dateObserveEnabledObserveWidget, null, null);
		//
		IObservableValue changespecialObserveSelectionObserveWidget_2 = SWTObservables.observeSelection(changespecial);
		IObservableValue specialstarttimeObserveEnabledObserveWidget = SWTObservables.observeEnabled(specialstarttime);
		bindingContext.bindValue(changespecialObserveSelectionObserveWidget_2, specialstarttimeObserveEnabledObserveWidget, null, null);
		//
		IObservableValue changespecialObserveSelectionObserveWidget_3 = SWTObservables.observeSelection(changespecial);
		IObservableValue specialendtimeObserveEnabledObserveWidget = SWTObservables.observeEnabled(specialendtime);
		bindingContext.bindValue(changespecialObserveSelectionObserveWidget_3, specialendtimeObserveEnabledObserveWidget, null, null);
		//
		IObservableValue http_modelObserveSelectionObserveWidget = SWTObservables.observeSelection(http_model);
		IObservableValue lineHttpmodelObserveValue = BeansObservables.observeValue(line, "httpmodel");
		bindingContext.bindValue(http_modelObserveSelectionObserveWidget, lineHttpmodelObserveValue, null, null);
		//
		IObservableValue https_modelObserveSelectionObserveWidget = SWTObservables.observeSelection(https_model);
		IObservableValue lineHttpsmodelObserveValue = BeansObservables.observeValue(line, "httpsmodel");
		bindingContext.bindValue(https_modelObserveSelectionObserveWidget, lineHttpsmodelObserveValue, null, null);
		//
		IObservableValue usernameObserveTextObserveWidget = SWTObservables.observeText(username, SWT.Modify);
		IObservableValue lineUsernameObserveValue = BeansObservables.observeValue(line, "username");
		bindingContext.bindValue(usernameObserveTextObserveWidget, lineUsernameObserveValue, null, null);
		//
		IObservableValue passwordObserveTextObserveWidget = SWTObservables.observeText(password, SWT.Modify);
		IObservableValue linePasswordObserveValue = BeansObservables.observeValue(line, "password");
		bindingContext.bindValue(passwordObserveTextObserveWidget, linePasswordObserveValue, null, null);
		//
		IObservableValue advancedaytypeObserveSelectionObserveWidget = SWTObservables.observeSelection(advancedaytype);
		bindingContext.bindValue(advancedaytypeObserveSelectionObserveWidget, linegetSummaryAdvancedaytypeObserveValue, null, null);
		//
		IObservableValue pay_ckObserveSelectionObserveWidget_1 = SWTObservables.observeSelection(pay_ck);
		IObservableValue payway1ObserveEnabledObserveWidget = SWTObservables.observeEnabled(payway1);
		bindingContext.bindValue(pay_ckObserveSelectionObserveWidget_1, payway1ObserveEnabledObserveWidget, null, null);
		//
		IObservableValue pay_ckObserveSelectionObserveWidget_2 = SWTObservables.observeSelection(pay_ck);
		IObservableValue payway2ObserveEnabledObserveWidget = SWTObservables.observeEnabled(payway2);
		bindingContext.bindValue(pay_ckObserveSelectionObserveWidget_2, payway2ObserveEnabledObserveWidget, null, null);
		//
		IObservableValue pay_ckObserveSelectionObserveWidget_3 = SWTObservables.observeSelection(pay_ck);
		IObservableValue payway3ObserveEnabledObserveWidget = SWTObservables.observeEnabled(payway3);
		bindingContext.bindValue(pay_ckObserveSelectionObserveWidget_3, payway3ObserveEnabledObserveWidget, null, null);
		//
		IObservableValue down_date_check2ObserveSelectionObserveWidget = SWTObservables.observeSelection(down_date_check2);
		IObservableValue down_date_beginObserveEnabledObserveWidget = SWTObservables.observeEnabled(down_date_begin);
		bindingContext.bindValue(down_date_check2ObserveSelectionObserveWidget, down_date_beginObserveEnabledObserveWidget, null, null);
		//
		IObservableValue down_date_check2ObserveSelectionObserveWidget_1 = SWTObservables.observeSelection(down_date_check2);
		IObservableValue down_date_endObserveEnabledObserveWidget = SWTObservables.observeEnabled(down_date_end);
		bindingContext.bindValue(down_date_check2ObserveSelectionObserveWidget_1, down_date_endObserveEnabledObserveWidget, null, null);
		//
		IObservableValue down_date_check1ObserveSelectionObserveWidget = SWTObservables.observeSelection(down_date_check1);
		IObservableValue lineDowndatecheck1ObserveValue = BeansObservables.observeValue(line, "downdatecheck1");
		bindingContext.bindValue(down_date_check1ObserveSelectionObserveWidget, lineDowndatecheck1ObserveValue, null, null);
		//
		IObservableValue down_date_check2ObserveSelectionObserveWidget_2 = SWTObservables.observeSelection(down_date_check2);
		IObservableValue lineDowndatecheck2ObserveValue = BeansObservables.observeValue(line, "downdatecheck2");
		bindingContext.bindValue(down_date_check2ObserveSelectionObserveWidget_2, lineDowndatecheck2ObserveValue, null, null);
		//
		IObservableValue down_date_beginObserveSelectionObserveWidget = SWTObservables.observeSelection(down_date_begin);
		IObservableValue lineDown_date_beginObserveValue = BeansObservables.observeValue(line, "down_date_begin");
		bindingContext.bindValue(down_date_beginObserveSelectionObserveWidget, lineDown_date_beginObserveValue, null, null);
		//
		IObservableValue down_date_endObserveSelectionObserveWidget = SWTObservables.observeSelection(down_date_end);
		IObservableValue lineDown_date_endObserveValue = BeansObservables.observeValue(line, "down_date_end");
		bindingContext.bindValue(down_date_endObserveSelectionObserveWidget, lineDown_date_endObserveValue, null, null);
		//
		IObservableValue ad_ckObserveSelectionObserveWidget_1 = SWTObservables.observeSelection(ad_ck);
		IObservableValue advancedayObserveEnabledObserveWidget = SWTObservables.observeEnabled(advanceday);
		bindingContext.bindValue(ad_ckObserveSelectionObserveWidget_1, advancedayObserveEnabledObserveWidget, null, null);
		//
		IObservableValue ad_ckObserveSelectionObserveWidget_2 = SWTObservables.observeSelection(ad_ck);
		IObservableValue advancedaytypeObserveEnabledObserveWidget = SWTObservables.observeEnabled(advancedaytype);
		bindingContext.bindValue(ad_ckObserveSelectionObserveWidget_2, advancedaytypeObserveEnabledObserveWidget, null, null);
		//
		IObservableValue pricedateObserveSelectionObserveWidget = SWTObservables.observeSelection(pricedate);
		IObservableValue linePricedateObserveValue = BeansObservables.observeValue(line, "pricedate");
		bindingContext.bindValue(pricedateObserveSelectionObserveWidget, linePricedateObserveValue, null, null);
		//
		IObservableList d_listObserveItemsObserveListWidget = SWTObservables.observeItems(d_list);
		IObservableList linePricedatesObserveList = BeansObservables.observeList(Realm.getDefault(), line, "pricedates");
		bindingContext.bindList(d_listObserveItemsObserveListWidget, linePricedatesObserveList, null, null);
		//
		IObservableValue limitcountObserveTextObserveWidget = SWTObservables.observeText(limitcount, SWT.Modify);
		IObservableValue lineLimitcountObserveValue = BeansObservables.observeValue(line, "limitcount");
		bindingContext.bindValue(limitcountObserveTextObserveWidget, lineLimitcountObserveValue, null, null);
		//
		IObservableValue limitcountchkObserveSelectionObserveWidget = SWTObservables.observeSelection(limitcountchk);
		IObservableValue lineLimitcountchkObserveValue = BeansObservables.observeValue(line, "limitcountchk");
		bindingContext.bindValue(limitcountchkObserveSelectionObserveWidget, lineLimitcountchkObserveValue, null, null);
		//
		IObservableValue limitcountchkObserveEnabledObserveWidget = SWTObservables.observeEnabled(limitcountchk);
		IObservableValue bt_c2ObserveSelectionObserveWidget_1 = SWTObservables.observeSelection(bt_c2);
		bindingContext.bindValue(limitcountchkObserveEnabledObserveWidget, bt_c2ObserveSelectionObserveWidget_1, null, null);
		//
		IObservableValue limitcountObserveEnabledObserveWidget = SWTObservables.observeEnabled(limitcount);
		IObservableValue bt_c2ObserveSelectionObserveWidget_2 = SWTObservables.observeSelection(bt_c2);
		bindingContext.bindValue(limitcountObserveEnabledObserveWidget, bt_c2ObserveSelectionObserveWidget_2, null, null);
		//
		IObservableValue changespecial2ObserveSelectionObserveWidget = SWTObservables.observeSelection(changespecial2);
		IObservableValue label_13ObserveEnabledObserveWidget = SWTObservables.observeEnabled(label_13);
		bindingContext.bindValue(changespecial2ObserveSelectionObserveWidget, label_13ObserveEnabledObserveWidget, null, null);
		//
		IObservableValue changespecial2ObserveSelectionObserveWidget_1 = SWTObservables.observeSelection(changespecial2);
		IObservableValue label_14ObserveEnabledObserveWidget = SWTObservables.observeEnabled(label_14);
		bindingContext.bindValue(changespecial2ObserveSelectionObserveWidget_1, label_14ObserveEnabledObserveWidget, null, null);
		//
		IObservableValue changespecial2ObserveSelectionObserveWidget_2 = SWTObservables.observeSelection(changespecial2);
		IObservableValue specialstarttime2ObserveEnabledObserveWidget = SWTObservables.observeEnabled(specialstarttime2);
		bindingContext.bindValue(changespecial2ObserveSelectionObserveWidget_2, specialstarttime2ObserveEnabledObserveWidget, null, null);
		//
		IObservableValue changespecial2ObserveSelectionObserveWidget_3 = SWTObservables.observeSelection(changespecial2);
		IObservableValue specialendtime2ObserveEnabledObserveWidget = SWTObservables.observeEnabled(specialendtime2);
		bindingContext.bindValue(changespecial2ObserveSelectionObserveWidget_3, specialendtime2ObserveEnabledObserveWidget, null, null);
		//
		IObservableValue changespecialObserveSelectionObserveWidget_4 = SWTObservables.observeSelection(changespecial);
		IObservableValue label_16ObserveEnabledObserveWidget = SWTObservables.observeEnabled(label_16);
		bindingContext.bindValue(changespecialObserveSelectionObserveWidget_4, label_16ObserveEnabledObserveWidget, null, null);
		//
		IObservableValue changespecialObserveSelectionObserveWidget_5 = SWTObservables.observeSelection(changespecial);
		IObservableValue label_16ObserveEnabledObserveWidget_1 = SWTObservables.observeEnabled(label_16);
		bindingContext.bindValue(changespecialObserveSelectionObserveWidget_5, label_16ObserveEnabledObserveWidget_1, null, null);
		//
		IObservableValue changespecialObserveSelectionObserveWidget_6 = SWTObservables.observeSelection(changespecial);
		IObservableValue label_22ObserveEnabledObserveWidget = SWTObservables.observeEnabled(label_22);
		bindingContext.bindValue(changespecialObserveSelectionObserveWidget_6, label_22ObserveEnabledObserveWidget, null, null);
		//
		IObservableValue changespecial2ObserveSelectionObserveWidget_4 = SWTObservables.observeSelection(changespecial2);
		IObservableValue price_rateObserveEnabledObserveWidget = SWTObservables.observeEnabled(price_rate);
		bindingContext.bindValue(changespecial2ObserveSelectionObserveWidget_4, price_rateObserveEnabledObserveWidget, null, null);
		//
		IObservableValue changespecial2ObserveSelectionObserveWidget_5 = SWTObservables.observeSelection(changespecial2);
		IObservableValue lineChangespecial2ObserveValue = BeansObservables.observeValue(line, "changespecial2");
		bindingContext.bindValue(changespecial2ObserveSelectionObserveWidget_5, lineChangespecial2ObserveValue, null, null);
		//
		IObservableValue specialstarttime2ObserveSelectionObserveWidget = SWTObservables.observeSelection(specialstarttime2);
		IObservableValue lineSpecialsatrttime2ObserveValue = BeansObservables.observeValue(line, "specialsatrttime2");
		bindingContext.bindValue(specialstarttime2ObserveSelectionObserveWidget, lineSpecialsatrttime2ObserveValue, null, null);
		//
		IObservableValue specialendtime2ObserveSelectionObserveWidget = SWTObservables.observeSelection(specialendtime2);
		IObservableValue lineSpecialendtime2ObserveValue = BeansObservables.observeValue(line, "specialendtime2");
		bindingContext.bindValue(specialendtime2ObserveSelectionObserveWidget, lineSpecialendtime2ObserveValue, null, null);
		//
		IObservableValue water_ckObserveSelectionObserveWidget = SWTObservables.observeSelection(water_ck);
		IObservableValue spic1ObserveEnabledObserveWidget = SWTObservables.observeEnabled(spic1);
		bindingContext.bindValue(water_ckObserveSelectionObserveWidget, spic1ObserveEnabledObserveWidget, null, null);
		//
		IObservableValue water_ckObserveSelectionObserveWidget_1 = SWTObservables.observeSelection(water_ck);
		IObservableValue spic2ObserveEnabledObserveWidget = SWTObservables.observeEnabled(spic2);
		bindingContext.bindValue(water_ckObserveSelectionObserveWidget_1, spic2ObserveEnabledObserveWidget, null, null);
		//
		IObservableValue water_ckObserveSelectionObserveWidget_2 = SWTObservables.observeSelection(water_ck);
		IObservableValue spic3ObserveEnabledObserveWidget = SWTObservables.observeEnabled(spic3);
		bindingContext.bindValue(water_ckObserveSelectionObserveWidget_2, spic3ObserveEnabledObserveWidget, null, null);
		//
		IObservableValue water_ckObserveSelectionObserveWidget_3 = SWTObservables.observeSelection(water_ck);
		IObservableValue spic4ObserveEnabledObserveWidget = SWTObservables.observeEnabled(spic4);
		bindingContext.bindValue(water_ckObserveSelectionObserveWidget_3, spic4ObserveEnabledObserveWidget, null, null);
		//
		IObservableValue water_ckObserveSelectionObserveWidget_6 = SWTObservables.observeSelection(water_ck);
		IObservableValue spic5ObserveEnabledObserveWidget = SWTObservables.observeEnabled(spic5);
		bindingContext.bindValue(water_ckObserveSelectionObserveWidget_6, spic5ObserveEnabledObserveWidget, null, null);
		//
		IObservableValue water_ckObserveSelectionObserveWidget_5 = SWTObservables.observeSelection(water_ck);
		IObservableValue spic6ObserveEnabledObserveWidget = SWTObservables.observeEnabled(spic6);
		bindingContext.bindValue(water_ckObserveSelectionObserveWidget_5, spic6ObserveEnabledObserveWidget, null, null);
		//
		IObservableValue water_ckObserveSelectionObserveWidget_4 = SWTObservables.observeSelection(water_ck);
		IObservableValue bpic1ObserveEnabledObserveWidget = SWTObservables.observeEnabled(bpic1);
		bindingContext.bindValue(water_ckObserveSelectionObserveWidget_4, bpic1ObserveEnabledObserveWidget, null, null);
		//
		IObservableValue water_ckObserveSelectionObserveWidget_7 = SWTObservables.observeSelection(water_ck);
		IObservableValue bpic2ObserveEnabledObserveWidget = SWTObservables.observeEnabled(bpic2);
		bindingContext.bindValue(water_ckObserveSelectionObserveWidget_7, bpic2ObserveEnabledObserveWidget, null, null);
		//
		IObservableValue water_ckObserveSelectionObserveWidget_8 = SWTObservables.observeSelection(water_ck);
		IObservableValue bpic3ObserveEnabledObserveWidget = SWTObservables.observeEnabled(bpic3);
		bindingContext.bindValue(water_ckObserveSelectionObserveWidget_8, bpic3ObserveEnabledObserveWidget, null, null);
		//
		IObservableValue water_ckObserveSelectionObserveWidget_9 = SWTObservables.observeSelection(water_ck);
		IObservableValue bpic4ObserveEnabledObserveWidget = SWTObservables.observeEnabled(bpic4);
		bindingContext.bindValue(water_ckObserveSelectionObserveWidget_9, bpic4ObserveEnabledObserveWidget, null, null);
		//
		IObservableValue water_ckObserveSelectionObserveWidget_10 = SWTObservables.observeSelection(water_ck);
		IObservableValue bpic5ObserveEnabledObserveWidget = SWTObservables.observeEnabled(bpic5);
		bindingContext.bindValue(water_ckObserveSelectionObserveWidget_10, bpic5ObserveEnabledObserveWidget, null, null);
		//
		IObservableValue water_ckObserveSelectionObserveWidget_11 = SWTObservables.observeSelection(water_ck);
		IObservableValue bpic6ObserveEnabledObserveWidget = SWTObservables.observeEnabled(bpic6);
		bindingContext.bindValue(water_ckObserveSelectionObserveWidget_11, bpic6ObserveEnabledObserveWidget, null, null);
		//
		IObservableValue water_ckObserveSelectionObserveWidget_12 = SWTObservables.observeSelection(water_ck);
		IObservableValue lineWaterckObserveValue = BeansObservables.observeValue(line, "waterck");
		bindingContext.bindValue(water_ckObserveSelectionObserveWidget_12, lineWaterckObserveValue, null, null);
		//
		IObservableValue advance_hourObserveTextObserveWidget = SWTObservables.observeText(advance_hour, SWT.Modify);
		IObservableValue linegetSummaryAdvancehourObserveValue = BeansObservables.observeValue(line.getSummary(), "advancehour");
		bindingContext.bindValue(advance_hourObserveTextObserveWidget, linegetSummaryAdvancehourObserveValue, null, null);
		//
		IObservableValue advance_minuteObserveTextObserveWidget = SWTObservables.observeText(advance_minute, SWT.Modify);
		IObservableValue linegetSummaryAdvanceminuteObserveValue = BeansObservables.observeValue(line.getSummary(), "advanceminute");
		bindingContext.bindValue(advance_minuteObserveTextObserveWidget, linegetSummaryAdvanceminuteObserveValue, null, null);
		//
		IObservableValue advanceDescObserveTextObserveWidget = SWTObservables.observeText(advanceDesc, SWT.Modify);
		IObservableValue linegetSummaryAdvancedescObserveValue = BeansObservables.observeValue(line.getSummary(), "advancedesc");
		bindingContext.bindValue(advanceDescObserveTextObserveWidget, linegetSummaryAdvancedescObserveValue, null, null);
		//
		IObservableValue changespecial3ObserveSelectionObserveWidget = SWTObservables.observeSelection(changespecial3);
		IObservableValue specialstarttime3ObserveEnabledObserveWidget = SWTObservables.observeEnabled(specialstarttime3);
		bindingContext.bindValue(changespecial3ObserveSelectionObserveWidget, specialstarttime3ObserveEnabledObserveWidget, null, null);
		//
		IObservableValue changespecial3ObserveSelectionObserveWidget_1 = SWTObservables.observeSelection(changespecial3);
		IObservableValue specialendtime3ObserveEnabledObserveWidget = SWTObservables.observeEnabled(specialendtime3);
		bindingContext.bindValue(changespecial3ObserveSelectionObserveWidget_1, specialendtime3ObserveEnabledObserveWidget, null, null);
		//
		IObservableValue changespecial3ObserveSelectionObserveWidget_2 = SWTObservables.observeSelection(changespecial3);
		IObservableValue price_rate_date3ObserveEnabledObserveWidget = SWTObservables.observeEnabled(price_rate_date3);
		bindingContext.bindValue(changespecial3ObserveSelectionObserveWidget_2, price_rate_date3ObserveEnabledObserveWidget, null, null);
		//
		IObservableValue changespecial3ObserveSelectionObserveWidget_3 = SWTObservables.observeSelection(changespecial3);
		IObservableValue label_1ObserveEnabledObserveWidget = SWTObservables.observeEnabled(label_1);
		bindingContext.bindValue(changespecial3ObserveSelectionObserveWidget_3, label_1ObserveEnabledObserveWidget, null, null);
		//
		IObservableValue changespecial3ObserveSelectionObserveWidget_4 = SWTObservables.observeSelection(changespecial3);
		IObservableValue label_31ObserveEnabledObserveWidget = SWTObservables.observeEnabled(label_31);
		bindingContext.bindValue(changespecial3ObserveSelectionObserveWidget_4, label_31ObserveEnabledObserveWidget, null, null);
		//
		IObservableValue price_rate_date3ObserveTextObserveWidget = SWTObservables.observeText(price_rate_date3, SWT.Modify);
		IObservableValue linePriceratedate3ObserveValue = BeansObservables.observeValue(line, "priceratedate3");
		bindingContext.bindValue(price_rate_date3ObserveTextObserveWidget, linePriceratedate3ObserveValue, null, null);
		//
		IObservableValue specialstarttime3ObserveSelectionObserveWidget = SWTObservables.observeSelection(specialstarttime3);
		IObservableValue lineSpecialsatrttime3ObserveValue = BeansObservables.observeValue(line, "specialsatrttime3");
		bindingContext.bindValue(specialstarttime3ObserveSelectionObserveWidget, lineSpecialsatrttime3ObserveValue, null, null);
		//
		IObservableValue specialendtime3ObserveSelectionObserveWidget = SWTObservables.observeSelection(specialendtime3);
		IObservableValue lineSpecialendtime3ObserveValue = BeansObservables.observeValue(line, "specialendtime3");
		bindingContext.bindValue(specialendtime3ObserveSelectionObserveWidget, lineSpecialendtime3ObserveValue, null, null);
		//
		IObservableValue changespecial3ObserveSelectionObserveWidget_5 = SWTObservables.observeSelection(changespecial3);
		IObservableValue lineChangespecial3ObserveValue = BeansObservables.observeValue(line, "changespecial3");
		bindingContext.bindValue(changespecial3ObserveSelectionObserveWidget_5, lineChangespecial3ObserveValue, null, null);
		//
		IObservableValue mainpicselectObserveSelectionObserveWidget = SWTObservables.observeSelection(mainpicselect);
		IObservableValue mainpictxtObserveEnabledObserveWidget = SWTObservables.observeEnabled(mainpictxt);
		bindingContext.bindValue(mainpicselectObserveSelectionObserveWidget, mainpictxtObserveEnabledObserveWidget, null, null);
		//
		IObservableValue mainpicselectObserveSelectionObserveWidget_1 = SWTObservables.observeSelection(mainpicselect);
		IObservableValue mainpicselectbtnObserveEnabledObserveWidget = SWTObservables.observeEnabled(mainpicselectbtn);
		bindingContext.bindValue(mainpicselectObserveSelectionObserveWidget_1, mainpicselectbtnObserveEnabledObserveWidget, null, null);
		//
		IObservableValue mainpicselectObserveSelectionObserveWidget_2 = SWTObservables.observeSelection(mainpicselect);
		IObservableValue mainshowObserveEnabledObserveWidget = SWTObservables.observeEnabled(mainshow);
		bindingContext.bindValue(mainpicselectObserveSelectionObserveWidget_2, mainshowObserveEnabledObserveWidget, null, null);
		//
		IObservableValue mainpicselectObserveSelectionObserveWidget_3 = SWTObservables.observeSelection(mainpicselect);
		IObservableValue lineMainpicselectObserveValue = BeansObservables.observeValue(line, "mainpicselect");
		bindingContext.bindValue(mainpicselectObserveSelectionObserveWidget_3, lineMainpicselectObserveValue, null, null);
		//
		IObservableValue mainpictxtObserveTextObserveWidget = SWTObservables.observeText(mainpictxt, SWT.Modify);
		IObservableValue lineMainpictxtObserveValue = BeansObservables.observeValue(line, "mainpictxt");
		bindingContext.bindValue(mainpictxtObserveTextObserveWidget, lineMainpictxtObserveValue, null, null);
		//
		IObservableValue server_phone_comObserveSelectionObserveWidget = SWTObservables.observeSelection(server_phone_com);
		IObservableValue linePhonenumObserveValue = BeansObservables.observeValue(line, "phonenum");
		bindingContext.bindValue(server_phone_comObserveSelectionObserveWidget, linePhonenumObserveValue, null, null);
		//
		IObservableValue flash_show_useObserveSelectionObserveWidget = SWTObservables.observeSelection(flash_show_use);
		IObservableValue lineFlashshowuseObserveValue = BeansObservables.observeValue(line, "flashshowuse");
		bindingContext.bindValue(flash_show_useObserveSelectionObserveWidget, lineFlashshowuseObserveValue, null, null);
		//
		IObservableValue observeTextTn_unameObserveWidget = WidgetProperties.text(new int[] { SWT.Modify, SWT.DefaultSelection }).observe(tn_uname);
		IObservableValue tnunameLineObserveValue = BeanProperties.value("tnuname").observe(line);
		bindingContext.bindValue(observeTextTn_unameObserveWidget, tnunameLineObserveValue, null, null);
		//
		IObservableValue observeTextTn_pwdObserveWidget = WidgetProperties.text(new int[] { SWT.Modify, SWT.DefaultSelection }).observe(tn_pwd);
		IObservableValue tnpwdLineObserveValue = BeanProperties.value("tnpwd").observe(line);
		bindingContext.bindValue(observeTextTn_pwdObserveWidget, tnpwdLineObserveValue, null, null);
		//
		IObservableValue observeTextTn_randObserveWidget = WidgetProperties.text(new int[] { SWT.Modify, SWT.DefaultSelection }).observe(tn_rand);
		IObservableValue tnrandLineObserveValue = BeanProperties.value("tnrand").observe(line);
		bindingContext.bindValue(observeTextTn_randObserveWidget, tnrandLineObserveValue, null, null);
		//
		IObservableValue observeTextAssemblyObserveWidget = WidgetProperties.text(new int[] { SWT.Modify, SWT.DefaultSelection }).observe(assembly);
		IObservableValue assemblyLineObserveValue = BeanProperties.value("assembly").observe(line);
		bindingContext.bindValue(observeTextAssemblyObserveWidget, assemblyLineObserveValue, null, null);
		//
		IObservableValue observeSelectionGroup_method0ObserveWidget = WidgetProperties.selection().observe(group_method0);
		IObservableValue group_method0LineObserveValue = BeanProperties.value("group_method0").observe(line);
		bindingContext.bindValue(observeSelectionGroup_method0ObserveWidget, group_method0LineObserveValue, null, null);
		//
		IObservableValue observeSelectionGroup_method1ObserveWidget = WidgetProperties.selection().observe(group_method1);
		IObservableValue group_method1LineObserveValue = BeanProperties.value("group_method1").observe(line);
		bindingContext.bindValue(observeSelectionGroup_method1ObserveWidget, group_method1LineObserveValue, null, null);
		//
		IObservableValue observeSelectionImg_publish_style1ObserveWidget = WidgetProperties.selection().observe(img_publish_style1);
		IObservableValue img_publish_style1LineObserveValue = BeanProperties.value("img_publish_style1").observe(line);
		bindingContext.bindValue(observeSelectionImg_publish_style1ObserveWidget, img_publish_style1LineObserveValue, null, null);
		//
		IObservableValue observeSelectionImg_publish_style2ObserveWidget = WidgetProperties.selection().observe(img_publish_style2);
		IObservableValue img_publish_style2LineObserveValue = BeanProperties.value("img_publish_style2").observe(line);
		bindingContext.bindValue(observeSelectionImg_publish_style2ObserveWidget, img_publish_style2LineObserveValue, null, null);
		//
		IObservableValue observeSelectionImg_publish_style3ObserveWidget = WidgetProperties.selection().observe(img_publish_style3);
		IObservableValue img_publish_style3LineObserveValue = BeanProperties.value("img_publish_style3").observe(line);
		bindingContext.bindValue(observeSelectionImg_publish_style3ObserveWidget, img_publish_style3LineObserveValue, null, null);
		//
		IObservableValue observeSelectionPromise_ironclad_groupObserveWidget = WidgetProperties.selection().observe(promise_ironclad_group);
		IObservableValue promise_ironclad_groupLineObserveValue = BeanProperties.value("promise_ironclad_group").observe(line);
		bindingContext.bindValue(observeSelectionPromise_ironclad_groupObserveWidget, promise_ironclad_groupLineObserveValue, null, null);
		//
		IObservableValue observeSelectionPromise_no_self_payObserveWidget = WidgetProperties.selection().observe(promise_no_self_pay);
		IObservableValue promise_no_self_payLineObserveValue = BeanProperties.value("promise_no_self_pay").observe(line);
		bindingContext.bindValue(observeSelectionPromise_no_self_payObserveWidget, promise_no_self_payLineObserveValue, null, null);
		//
		IObservableValue observeSelectionPromise_no_shoppingObserveWidget = WidgetProperties.selection().observe(promise_no_shopping);
		IObservableValue promise_no_shoppingLineObserveValue = BeanProperties.value("promise_no_shopping").observe(line);
		bindingContext.bindValue(observeSelectionPromise_no_shoppingObserveWidget, promise_no_shoppingLineObserveValue, null, null);
		//
		IObservableValue observeSelectionPromise_truthful_descriptionObserveWidget = WidgetProperties.selection().observe(promise_truthful_description);
		IObservableValue promise_truthful_descriptionLineObserveValue = BeanProperties.value("promise_truthful_description").observe(line);
		bindingContext.bindValue(observeSelectionPromise_truthful_descriptionObserveWidget, promise_truthful_descriptionLineObserveValue, null, null);
		//
		IObservableValue observeSelectionPromise_refund_anytime_not_consumeObserveWidget = WidgetProperties.selection().observe(promise_refund_anytime_not_consume);
		IObservableValue promise_refund_anytime_not_consumeLineObserveValue = BeanProperties.value("promise_refund_anytime_not_consume").observe(line);
		bindingContext.bindValue(observeSelectionPromise_refund_anytime_not_consumeObserveWidget, promise_refund_anytime_not_consumeLineObserveValue, null, null);
		//
		IObservableValue observeSelectionPromise_truthful_description_freeObserveWidget = WidgetProperties.selection().observe(promise_truthful_description_free);
		IObservableValue promise_truthful_description_freeLineObserveValue = BeanProperties.value("promise_truthful_description_free").observe(line);
		bindingContext.bindValue(observeSelectionPromise_truthful_description_freeObserveWidget, promise_truthful_description_freeLineObserveValue, null, null);
		//
		IObservableValue observeSelectionPromise_booking_current_dayObserveWidget = WidgetProperties.selection().observe(promise_booking_current_day);
		IObservableValue promise_booking_current_dayLineObserveValue = BeanProperties.value("promise_booking_current_day").observe(line);
		bindingContext.bindValue(observeSelectionPromise_booking_current_dayObserveWidget, promise_booking_current_dayLineObserveValue, null, null);
		//
		IObservableValue observeSelectionPromise_guarantee_goObserveWidget = WidgetProperties.selection().observe(promise_guarantee_go);
		IObservableValue promise_guarantee_goLineObserveValue = BeanProperties.value("promise_guarantee_go").observe(line);
		bindingContext.bindValue(observeSelectionPromise_guarantee_goObserveWidget, promise_guarantee_goLineObserveValue, null, null);
		//
		IObservableValue observeSelectionL_icObserveWidget = WidgetProperties.selection().observe(l_ic);
		IObservableValue licLineObserveValue = BeanProperties.value("lic").observe(line);
		bindingContext.bindValue(observeSelectionL_icObserveWidget, licLineObserveValue, null, null);
		//
		IObservableList itemsListObserveWidget = WidgetProperties.items().observe(seriesline);
		IObservableList seriesLsObserveList = PojoProperties.list("series").observe(ls);
		bindingContext.bindList(itemsListObserveWidget, seriesLsObserveList, null, null);
		//
		IObservableValue observeSelectionGlobal_ckObserveWidget = WidgetProperties.selection().observe(global_ck);
		IObservableValue global_ckLsObserveValue = PojoProperties.value("global_ck").observe(ls);
		bindingContext.bindValue(observeSelectionGlobal_ckObserveWidget, global_ckLsObserveValue, null, null);
		//
		IObservableValue observeSelectionChangespecial4ObserveWidget = WidgetProperties.selection().observe(changespecial4);
		IObservableValue changespecial4LineObserveValue = BeanProperties.value("changespecial4").observe(line);
		bindingContext.bindValue(observeSelectionChangespecial4ObserveWidget, changespecial4LineObserveValue, null, null);
		//
		IObservableValue observeTextSpecial_dateObserveWidget = WidgetProperties.text(SWT.Modify).observe(special_date);
		IObservableValue specialdateLineObserveValue = BeanProperties.value("specialdate").observe(line);
		bindingContext.bindValue(observeTextSpecial_dateObserveWidget, specialdateLineObserveValue, null, null);
		//
		IObservableValue observeTextSpecial_priceObserveWidget = WidgetProperties.text(SWT.Modify).observe(special_price);
		IObservableValue specealpriceLineObserveValue = BeanProperties.value("specealprice").observe(line);
		bindingContext.bindValue(observeTextSpecial_priceObserveWidget, specealpriceLineObserveValue, null, null);
		//
		IObservableValue observeTextSend_wait_beginObserveWidget = WidgetProperties.text(SWT.Modify).observe(send_wait_begin);
		IObservableValue sendwaitbeginLineObserveValue = BeanProperties.value("sendwaitbegin").observe(line);
		bindingContext.bindValue(observeTextSend_wait_beginObserveWidget, sendwaitbeginLineObserveValue, null, null);
		//
		IObservableValue observeTextSend_wait_endObserveWidget = WidgetProperties.text(SWT.Modify).observe(send_wait_end);
		IObservableValue sendwaitendLineObserveValue = BeanProperties.value("sendwaitend").observe(line);
		bindingContext.bindValue(observeTextSend_wait_endObserveWidget, sendwaitendLineObserveValue, null, null);
		//
		IObservableValue observeTextTxt_failinterruptObserveWidget = WidgetProperties.text(SWT.Modify).observe(txt_failinterrupt);
		IObservableValue failinterruptLineObserveValue = BeanProperties.value("failinterrupt").observe(line);
		bindingContext.bindValue(observeTextTxt_failinterruptObserveWidget, failinterruptLineObserveValue, null, null);
		//
		IObservableValue observeSelectionGather_timeObserveWidget = WidgetProperties.selection().observe(gather_time);
		IObservableValue gathertimeLineObserveValue = BeanProperties.value("gathertime").observe(line);
		bindingContext.bindValue(observeSelectionGather_timeObserveWidget, gathertimeLineObserveValue, null, null);
		//
		IObservableValue observeTextGather_spotObserveWidget = WidgetProperties.text(SWT.Modify).observe(gather_spot);
		IObservableValue gatherspotLineObserveValue = BeanProperties.value("gatherspot").observe(line);
		bindingContext.bindValue(observeTextGather_spotObserveWidget, gatherspotLineObserveValue, null, null);
		//
		return bindingContext;
	}
}
