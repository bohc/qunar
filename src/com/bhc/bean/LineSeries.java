package com.bhc.bean;

public class LineSeries {
	// btc1:bt_c1, btc2:bt_c2,limitcountchk:limitcountchk, payck,
	// btc2ac:bt_c2_activity, btc2acc:bt_c2_act_cancel;
	// lbc:lbc, ldc:ldc, lpc:lpc, loc:loc, lic:lic, c1up:bt_c1_up,
	// c1nomain:bt_c1_noeditmainimg, c1noimg:bt_c1_noeditimgs;
	// c2up:bt_c2_up, c2down:bt_c2_down, c2del:bt_c2_del,
	// c2dn:bt_c2_del_dub_new;
	// limitcount:limitcount

	private boolean btc1, btc2, limitcountchk, btc2ac, btc2acc;
	private boolean lbc, ldc, lpc, loc, lic, c1up, c1nomain, c1noimg;
	private boolean c2up, c2down, c2del, c2dn;
	private boolean global_ck;
	private int limitcount;

	public boolean isBtc1() {
		return btc1;
	}

	public void setBtc1(boolean btc1) {
		this.btc1 = btc1;
	}

	public boolean isBtc2() {
		return btc2;
	}

	public void setBtc2(boolean btc2) {
		this.btc2 = btc2;
	}

	public boolean isLimitcountchk() {
		return limitcountchk;
	}

	public void setLimitcountchk(boolean limitcountchk) {
		this.limitcountchk = limitcountchk;
	}

	public boolean isBtc2ac() {
		return btc2ac;
	}

	public void setBtc2ac(boolean btc2ac) {
		this.btc2ac = btc2ac;
	}

	public boolean isBtc2acc() {
		return btc2acc;
	}

	public void setBtc2acc(boolean btc2acc) {
		this.btc2acc = btc2acc;
	}

	public boolean isLbc() {
		return lbc;
	}

	public void setLbc(boolean lbc) {
		this.lbc = lbc;
	}

	public boolean isLdc() {
		return ldc;
	}

	public void setLdc(boolean ldc) {
		this.ldc = ldc;
	}

	public boolean isLpc() {
		return lpc;
	}

	public void setLpc(boolean lpc) {
		this.lpc = lpc;
	}

	public boolean isLoc() {
		return loc;
	}

	public void setLoc(boolean loc) {
		this.loc = loc;
	}

	public boolean isLic() {
		return lic;
	}

	public void setLic(boolean lic) {
		this.lic = lic;
	}

	public boolean isC1up() {
		return c1up;
	}

	public void setC1up(boolean c1up) {
		this.c1up = c1up;
	}

	public boolean isC1nomain() {
		return c1nomain;
	}

	public void setC1nomain(boolean c1nomain) {
		this.c1nomain = c1nomain;
	}

	public boolean isC1noimg() {
		return c1noimg;
	}

	public void setC1noimg(boolean c1noimg) {
		this.c1noimg = c1noimg;
	}

	public boolean isC2up() {
		return c2up;
	}

	public void setC2up(boolean c2up) {
		this.c2up = c2up;
	}

	public boolean isC2down() {
		return c2down;
	}

	public void setC2down(boolean c2down) {
		this.c2down = c2down;
	}

	public boolean isC2del() {
		return c2del;
	}

	public void setC2del(boolean c2del) {
		this.c2del = c2del;
	}

	public boolean isC2dn() {
		return c2dn;
	}

	public void setC2dn(boolean c2dn) {
		this.c2dn = c2dn;
	}

	public int getLimitcount() {
		return limitcount;
	}

	public void setLimitcount(int limitcount) {
		this.limitcount = limitcount;
	}

	public boolean isGlobal_ck() {
		return global_ck;
	}

	public void setGlobal_ck(boolean global_ck) {
		this.global_ck = global_ck;
	}

}
