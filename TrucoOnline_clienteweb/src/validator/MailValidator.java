package validator;

import java.rmi.Naming;
import java.rmi.RemoteException;

import interfaces.TDAManejoDatos;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("mailValidator")
public class MailValidator implements Validator {

	TDAManejoDatos manejoJugadores;

	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		String mail = (String) value;
		boolean existeMail = false;
		if (mail == null) {

			return; // Just ignore and let required="true" do its job.
		} else {
			getStub();

			try {
				existeMail = manejoJugadores.validteMail(mail);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			if (existeMail) {
				throw new ValidatorException(new FacesMessage(
						"Ya existe este apodo para Jugador"));
			}

		}

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




}
