/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DeckPaket;
import KartenPaket.*;
import SpielerPaket.*;
import java.io.*;
import java.util.*;
import org.json.*;
/**
 *
 * @author Lennart
 */
public class Deck implements IDeckText
{
    private Stack<AbstractSpielKarte> deckliste = new Stack<>();
    private byte kartenZahl;
    /**
     * Erstellt eine Instanz vom Deck. Bei der Erzeugung des Decks wird das Deck
     * aus der JSON Bibliothek nach dem uebergebenen name Parameter rausgesucht.
     * Dieses rausgesuchte Deck wird auf vorhandensein von BeschwoerunsKarten,
     * GegenstandsKarten und ZauberKarten durchsucht und falls sie vorhanden sind
     * dem Deck, welches als Stack implementiert wurde, hinzugefuegt. Wenn alle 
     * Karten ausgelesen worden sind wird das Deck einmal gemischt.
     * @param name Legt fest nach welchem Deck in der JSON-Bibliothek gesucht wird.
     * @param spieler Legt fest welchem Spieler die Karten im Deck gehoeren.
     * @throws JSONException
     * @throws IOException 
     *                      die Exceptions werden weitergeworfen, weil sie
     *                      nur auftreten, wenn es einen Fehler mit der Daten-
     *                      bank gibt und diese Fehler koennen intern im 
     *                      Programm nicht behandelt werden.
     */
    public Deck(String name, Spieler spieler) throws JSONException, IOException
    {
        kartenZahl = 0;
        JSONObject object = EigenesJSON.JSONLesegeraet.getDeckJSONObject (name);        
        JSONArray figurenarray = object.getJSONArray (figurenkartenlisteText);
        if(figurenarray !=null)
        {
            for ( int i = 0 ; i < figurenarray.length () ; i++)
            {     
                String kartenname = (String)(figurenarray.get(i));
                karteZufuegen(new BeschwoerungsKarte(kartenname, spieler));                 
            }
        }
        JSONArray gegenstandsarray = object.getJSONArray (gegenstandskartenlisteText);
        if (gegenstandsarray != null)
        {
            for ( int i = 0 ; i < gegenstandsarray.length () ; i++)
            {
                String kartenname = (String)(gegenstandsarray.get(i));
                karteZufuegen(new GegenstandsKarte(kartenname));
            }
        }
        JSONArray zauberarray = object.getJSONArray (zauberkartenlisteText);
        if (zauberarray != null)
        {
            for ( int i = 0 ; i < zauberarray.length () ; i++)
            {
                String kartenname = (String)(zauberarray.get(i));
                karteZufuegen(new ZauberKarte(kartenname));
            }
        }
        deckMischen();
    }
    /**
    * Gibt aus dem Stack deckliste des Decks die oberste Karte raus.
    * @return Gibt aus dem Stack die oberste Karte raus. Diese Karte ist vom Typ
    * AbstactSpielKarte
    */
    public AbstractSpielKarte karteZiehen()
    {
        if(deckliste.empty())
        {
            return null;
        }
        kartenZahlVerringern();
        return deckliste.pop();
    }
    /**
     * Legt oben auf den Stack deckliste des Decks eine Karte vom Typ 
     * AbstractSpielKarte und erhoet das Attribut kartenZahl um 1.
     * @param karte 
     */
    public void karteZufuegen(AbstractSpielKarte karte)
    {
        deckliste.add(karte);
        kartenZahl++;
    }
    /**
     * Verringert das Attribut kartenzahl um 1.
     */
    private void kartenZahlVerringern()
    {
        kartenZahl--;
    }
    /**
     * Gibt die karten Zahl wieder.
     * @return Gibt die karten Zahl als Bytewert wieder.
     */
    public byte getKartenAnzahl()
    {
        return kartenZahl;
    }
    /**
     * Mischt den Stack deckliste des Decks.
     */
    public void deckMischen()
    {
        Collections.shuffle(deckliste);
    }

}
