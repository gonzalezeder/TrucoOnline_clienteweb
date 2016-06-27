package view;

import interfaces.TDAManejoDatos;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

@ManagedBean
public class AltaJugadorView {

	TDAManejoDatos manejoJugadores;

	private static final long serialVersionUID = 5443351151396868724L;

	private String mail;

	private String apodo;

	private String password;

	public String getApodo() {
		return apodo;
	}

	public void setApodo(String apodo) {
		this.apodo = apodo;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String username) {
		this.mail = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getStub() {

		try {
			manejoJugadores = (TDAManejoDatos) Naming
					.lookup("//localhost/GestionJugadores");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void login(ActionEvent event) {
		RequestContext context = RequestContext.getCurrentInstance();
		FacesMessage message = null;
		boolean loggedIn = false;

		if (getStub()) {

			if (mail != null && password != null) {

				try {
					loggedIn = manejoJugadores.validarLogin(mail, password);
				} catch (RemoteException e1) {
					loggedIn = false;
					message = new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Loggin Errors", "Invalid credentials");
				}
				message = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Bienvenido:", mail);
				try {
					FacesContext context2 = FacesContext.getCurrentInstance();
					context2.getExternalContext().redirect("home.xhtml");
					context2.getExternalContext().getFlash()
							.setKeepMessages(true);
				} catch (IOException e) {

					e.printStackTrace();
				}
			} else {
				loggedIn = false;
				message = new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Loggin Errors", "Invalid credentials");
			}

			FacesContext.getCurrentInstance().addMessage(null, message);
			context.addCallbackParam("loggedIn", loggedIn);
		}
	}
}