package ispit;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Zahtev {

	private int id = 0;
	private int satiRealizacije = 0;
	private LocalDate datumRada = LocalDate.now();
	private String imeProgramera = "Nepoznato ime";
	private String prezimeProgramera = "Nepoznato prezime";
	private String nazivSoftvera = "Nepoznati softver";
	private String tipZahteva = "Nepoznati tip";

	public Zahtev() {
		super();
	}

	public Zahtev(int id, int satiRealizacije, LocalDate datumRada, String imeProgramera, String prezimeProgramera,
			String nazivSoftvera, String tipZahteva) {
		super();
		this.id = id;
		this.satiRealizacije = satiRealizacije;
		this.datumRada = datumRada;
		this.imeProgramera = imeProgramera;
		this.prezimeProgramera = prezimeProgramera;
		this.nazivSoftvera = nazivSoftvera;
		this.tipZahteva = tipZahteva;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSatiRealizacije() {
		return satiRealizacije;
	}

	public void setSatiRealizacije(int satiRealizacije) {
		this.satiRealizacije = satiRealizacije;
	}

	public LocalDate getDatumRada() {
		return datumRada;
	}

	public void setDatumRada(LocalDate datumRada) {
		this.datumRada = datumRada;
	}

	public String getImeProgramera() {
		return imeProgramera;
	}

	public void setImeProgramera(String imeProgramera) {
		this.imeProgramera = imeProgramera;
	}

	public String getPrezimeProgramera() {
		return prezimeProgramera;
	}

	public void setPrezimeProgramera(String prezimeProgramera) {
		this.prezimeProgramera = prezimeProgramera;
	}

	public String getNazivSoftvera() {
		return nazivSoftvera;
	}

	public void setNazivSoftvera(String nazivSoftvera) {
		this.nazivSoftvera = nazivSoftvera;
	}

	public String getTipZahteva() {
		return tipZahteva;
	}

	public void setTipZahteva(String tipZahteva) {
		this.tipZahteva = tipZahteva;
	}

	public String toString() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
		return String.format("%5s %9s %18s %-15s %-20s %-13s %-10s", this.id, this.satiRealizacije,
				dtf.format(this.datumRada), this.imeProgramera, this.prezimeProgramera, this.nazivSoftvera,
				this.tipZahteva);
	}

}
