package com.ebs.rental.general;

@SuppressWarnings("ALL")
class MailCheck {
	private String mail = null;
	private boolean selected = false;

	public MailCheck(String mail, boolean selected) {
		super();
		this.mail = mail;
		this.selected = selected;

	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
