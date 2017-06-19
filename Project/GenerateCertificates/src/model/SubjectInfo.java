package model;


public class SubjectInfo {

	private String ime;
	private String prezime;
	private String email;
	private String kompanija;
	private String days;
	private String alias;
	private String password;
	private String ca;
	
	public SubjectInfo(String ime, String prezime, String email,
			String kompanija, String days, String alias,
			char[] password,String ca) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.kompanija = kompanija;
		this.days = days;
		this.alias = alias;
		this.password = String.valueOf(password);
		this.ca=ca;
	}
	
	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}
	
	public String getCa() {
		return ca;
	}

	public void setCa(String ca) {
		this.ca = ca;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getKompanija() {
		return kompanija;
	}

	public void setKompanija(String kompanija) {
		this.kompanija = kompanija;
	}


	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}