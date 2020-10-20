package juegocartas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class Mazo {
	
	private ArrayList<Carta> cartas;
	
	public Mazo() {
		this.cartas = new ArrayList<>();
	}
	
	public void addCarta(Carta c) {
		this.cartas.add(c);
	}
	
	public Carta repartirCarta() {
		return this.cartas.remove((int)Math.random()*cartas.size());
	}
	
	public void crearMazo(String jsonFile) {
		File jsonInputFile = new File(jsonFile);
        InputStream is;
        try {
            is = new FileInputStream(jsonInputFile);
            // Creo el objeto JsonReader de Json.
            JsonReader reader = Json.createReader(is);
            // Obtenemos el JsonObject a partir del JsonReader.
            JsonArray cartas = (JsonArray) reader.readObject().getJsonArray("cartas");
            for (JsonObject carta : cartas.getValuesAs(JsonObject.class)) {
                String nombreCarta = carta.getString("nombre");
                Carta c = new Carta(nombreCarta);
                JsonObject atributos = (JsonObject) carta.getJsonObject("atributos");
                String atributosStr = "";
                for (String nombreAtributo:atributos.keySet()){
                	atributosStr = atributosStr + nombreAtributo + ": " + atributos.getInt(nombreAtributo) + "; ";
                	 Atributo a = new Atributo(nombreAtributo, atributos.getInt(nombreAtributo));
                	 c.addAtributos(a);
                }
                System.out.println(nombreCarta+"\t\t\t"+atributosStr);
                this.addCarta(c);
            }

            reader.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
}
