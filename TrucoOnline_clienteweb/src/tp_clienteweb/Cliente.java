package tp_clienteweb;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.Set;

import dtos.JugadorDTO;

import interfaces.TDAManejoDatos;

public class Cliente {
	TDAManejoDatos manejoJugadores;
	JugadorDTO jugador;
	
	public static void main(String[] args)
	{
		new Cliente();
	}
	
public boolean getStub() {
    	
    	try {
			manejoJugadores = (TDAManejoDatos)Naming.lookup ("//localhost/GestionJugadores");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
    }
    
    public Cliente(){
    	if(getStub()) 
    	{
    		try {
    			/*Busco por número de jugador*/
    			jugador = manejoJugadores.obtengoJugador(1);
    			System.out.println(jugador.getApodo());
    			/*Listo todos los jugadores*/
    			System.out.println("\nListo Todos los Jugadores.");
    			Set<JugadorDTO> jugadores = manejoJugadores.obtengoJugadores();
    			for(Iterator<JugadorDTO> i=jugadores.iterator();i.hasNext();)
    			{
    				System.out.println(i.next().toString());
    			}
    			/*Agrego un jugador*/
    			System.out.println("\nAgrego un jugador.");
    			jugador = new JugadorDTO(11, "Claudio");
    			manejoJugadores.envioJugador(jugador);
    			/*Listo todos los jugadores con el agregado*/
    			System.out.println("\nListo todos los jugadores.");
    			jugadores = manejoJugadores.obtengoJugadores();
    			for(Iterator<JugadorDTO> i=jugadores.iterator();i.hasNext();)
    			{
    				System.out.println(i.next().toString());
    			}
    			/*Obtengo la cantidad de jugadores*/
    			System.out.println("\nCantidad de Jugadores " + manejoJugadores.cantidadJugadores());
    		} catch (RemoteException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	} 
    }
	
}
