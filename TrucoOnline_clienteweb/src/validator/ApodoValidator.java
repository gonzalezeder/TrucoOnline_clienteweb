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

@FacesValidator("apodoValidator")
public class ApodoValidator implements Validator {

	TDAManejoDatos manejoJugadores;

	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		String apodo = (String) value;
		boolean existeApodo = false;
		if (apodo == null) {

			return; // Just ignore and let required="true" do its job.
		} else {
			getStub();

			try {
				existeApodo = manejoJugadores.validteApodo(apodo);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (existeApodo) {
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
