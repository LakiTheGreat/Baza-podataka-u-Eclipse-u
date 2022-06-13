package ispit;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Firma {

	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
	private String nazivFirme = "\"Zika i sin softveri\"";
	private String webAdresa = "www.zikicisoft.com";
	private String brTelefona = "063 / 554-666-3";

	private ArrayList<Zahtev> listaZahteva = new ArrayList<>();

	public Firma() {
		super();
	}

	public Firma(String nazivFirme, String webAdresa, String brTelefona, ArrayList<Zahtev> listaZahteav) {
		super();
		this.nazivFirme = nazivFirme;
		this.webAdresa = webAdresa;
		this.brTelefona = brTelefona;
		this.listaZahteva = listaZahteav;
	}

	public String getNazivFirme() {
		return nazivFirme;
	}

	public void setNazivFirme(String nazivFirme) {
		this.nazivFirme = nazivFirme;
	}

	public String getWebAdresa() {
		return webAdresa;
	}

	public void setWebAdresa(String webAdresa) {
		this.webAdresa = webAdresa;
	}

	public String getBrTelefona() {
		return brTelefona;
	}

	public void setBrTelefona(String brTelefona) {
		this.brTelefona = brTelefona;
	}

	public ArrayList<Zahtev> getListaZahteav() {
		return listaZahteva;
	}

	public void setListaZahteav(ArrayList<Zahtev> listaZahteav) {
		this.listaZahteva = listaZahteav;
	}

	public void heder() {
		System.out.printf("%5s %13s %13s %15s %7s %10s %17s\n", "Id:", "Vreme rada:", "Datum rada:", "Ime programera:",
				"Prezime programera:", "Softver:", "Tip softvera:");
		System.out.println(
				"----------------------------------------------------------------------------------------------------");
	}

	public void ispisZahteva() {
		heder();
		for (int i = 0; i < this.listaZahteva.size(); i++) {
			System.out.println(this.listaZahteva.get(i));
		}
	}

	public boolean idPostoji(String privremeniId) {
		int broj = Integer.parseInt(privremeniId);
		for (int i = 0; i < this.listaZahteva.size(); i++) {
			if (this.listaZahteva.get(i).getId() == broj) {
				System.out.println("Vec postoji zahtev sa unetim ID-om.");
				return false;
			}
		}
		return true;
	}

	public void unosZahteva(Zahtev zahtev) {
		this.listaZahteva.add(zahtev);

	}

	public boolean idNePostoji(String privremeniId) {
		int broj = Integer.parseInt(privremeniId);
		for (int i = 0; i < this.listaZahteva.size(); i++) {
			if (this.listaZahteva.get(i).getId() == broj) {
				return true;
			}
		}
		System.out.println("Ne postoji zahtev sa unetim ID-om.");
		return false;
	}

	public void izmeniZahtev(Zahtev zahtev) {
		for (int i = 0; i < this.listaZahteva.size(); i++) {
			if (this.listaZahteva.get(i).getId() == zahtev.getId()) {
				this.listaZahteva.set(i, zahtev);
			}
		}

	}

	public void obrisiZahtev(int id) {
		for (int i = 0; i < this.listaZahteva.size(); i++) {
			if (this.listaZahteva.get(i).getId() == id) {
				this.listaZahteva.remove(i);
				System.out.println("Zahtev \"ID: " + id + "\" je uspesno obrisan.");
			}
		}

	}

	public void ispisPretragePoDeluImenaIliPrezimena(String deoZaPretragu) {
		heder();
		for (int i = 0; i < this.listaZahteva.size(); i++) {
			if (this.listaZahteva.get(i).getImeProgramera().contains(deoZaPretragu)
					|| this.listaZahteva.get(i).getPrezimeProgramera().contains(deoZaPretragu)) {
				System.out.println(this.listaZahteva.get(i));
			}
		}

	}

	public void ispisSlozenaPretraga(String privremeniTip, String nazivSoftvera, LocalDate min, LocalDate max) {
		heder();
		for (int i = 0; i < this.listaZahteva.size(); i++) {
			if (this.listaZahteva.get(i).getTipZahteva().equalsIgnoreCase(privremeniTip)
					&& this.listaZahteva.get(i).getNazivSoftvera().equalsIgnoreCase(nazivSoftvera)
					&& this.listaZahteva.get(i).getDatumRada().compareTo(min) >= 0
					&& this.listaZahteva.get(i).getDatumRada().compareTo(max) <= 0) {
				System.out.println(this.listaZahteva.get(i));
			}
		}

	}

	public double prosekUPeriodu(LocalDate min, LocalDate max) {
		double prosek = 0;
		double zbir = 0;
		int brojac = 0;

		for (int i = 0; i < this.listaZahteva.size(); i++) {
			if (this.listaZahteva.get(i).getDatumRada().compareTo(min) >= 0
					&& this.listaZahteva.get(i).getDatumRada().compareTo(max) <= 0) {

				zbir += this.listaZahteva.get(i).getSatiRealizacije();
				brojac++;
			}
		}

		if (brojac > 0) {
			prosek = zbir / brojac;
		} else {
			System.out.println("Ne postoje zahtevi u navedenom periodu: " + dtf.format(min) + "-" + dtf.format(max));
		}
		return prosek;

	}
	
	public void ispisProseka(LocalDate min, LocalDate max) {
		double prosek = prosekUPeriodu(min, max);
		

		System.out.println("Prosek za navedeni period je " + prosek + " sati.");
		
	}

	public void ispisProsekaPoPeriodima(LocalDate minPrvi, LocalDate maxPrvi, LocalDate minDrugi, LocalDate maxDrugi) {
		double prosekPrvogPerioda = prosekUPeriodu(minPrvi, maxPrvi);
		double prosekDrugogPerioda = prosekUPeriodu(minDrugi, maxDrugi);
		double prosekUkupni;

		if (prosekPrvogPerioda != 0 && prosekDrugogPerioda != 0) {
			prosekUkupni = (prosekPrvogPerioda + prosekDrugogPerioda) / 2;
		} else {
			prosekUkupni = prosekPrvogPerioda + prosekDrugogPerioda;
		}

		System.out.println("Prosek za navedene periode je " + prosekUkupni + " sati.");

	}

	public void ispisNatprosecnihZahteva(LocalDate min, LocalDate max) {
		double prosek = prosekUPeriodu(min, max);

		heder();
		for (int i = 0; i < this.listaZahteva.size(); i++) {
			if (this.listaZahteva.get(i).getSatiRealizacije() > prosek
					&& this.listaZahteva.get(i).getDatumRada().compareTo(min) >= 0
					&& this.listaZahteva.get(i).getDatumRada().compareTo(max) <= 0) {
				System.out.println(this.listaZahteva.get(i));
			}
		}

	}

	public void ispisPodatakaOFirmi() {
		int brojTipovaTehnicki = 0;
		int brojTipovaFunkcionalni = 0;
		int brojTipovaTestiranje = 0;
		int brojTipovaSpajkovi = 0;

		int brojSatiTehnicki = 0;
		int brojSatiFunkcionalni = 0;
		int brojSatiTestiranje = 0;
		int brojSatiSpajkvi = 0;

		for (int i = 0; i < this.listaZahteva.size(); i++) {
			if (this.listaZahteva.get(i).getTipZahteva().equalsIgnoreCase("tehnicki")) {
				brojTipovaTehnicki++;
				brojSatiTehnicki += this.listaZahteva.get(i).getSatiRealizacije();
			}
			if (this.listaZahteva.get(i).getTipZahteva().equalsIgnoreCase("funkcionalni")) {
				brojTipovaFunkcionalni++;
				brojSatiFunkcionalni += this.listaZahteva.get(i).getSatiRealizacije();
			}
			if (this.listaZahteva.get(i).getTipZahteva().equalsIgnoreCase("testiranje")) {
				brojTipovaTestiranje++;
				brojSatiTestiranje += this.listaZahteva.get(i).getSatiRealizacije();
			}
			if (this.listaZahteva.get(i).getTipZahteva().equalsIgnoreCase("spike")) {
				brojTipovaSpajkovi++;
				brojSatiSpajkvi += this.listaZahteva.get(i).getSatiRealizacije();
			}
		}

		System.out.println("****************************************");
		System.out.println("Naziv firme: " + this.nazivFirme);
		System.out.println("Web adresa: " + this.webAdresa);
		System.out.println("Telefon: " + this.brTelefona);
		System.out.println("----------------------------------------");
		System.out.println("ZAHTEVI: ");
		System.out.println("*Tehnicki*: ");
		System.out.println("Broj: " + brojTipovaTehnicki);
		System.out.println("Ukupno vreme rada u satima: " + brojSatiTehnicki);
		System.out.println(" ");
		System.out.println("*Funkcionalni*: ");
		System.out.println("Broj: " + brojTipovaFunkcionalni);
		System.out.println("Ukupno vreme rada u satima: " + brojSatiFunkcionalni);
		System.out.println(" ");
		System.out.println("*Testiranje*: ");
		System.out.println("Broj: " + brojTipovaTestiranje);
		System.out.println("Ukupno vreme rada u satima: " + brojSatiTestiranje);
		System.out.println(" ");
		System.out.println("*Spike*: ");
		System.out.println("Broj: " + brojTipovaSpajkovi);
		System.out.println("Ukupno vreme rada u satima: " + brojSatiSpajkvi);

	}

	public void save(String path) {

		ArrayList<String> lines = new ArrayList<String>();
		for (int i = 0; i < this.listaZahteva.size(); i++) {
			Zahtev zahtev = this.listaZahteva.get(i);

			int id = zahtev.getId();
			int satiRealizacije = zahtev.getSatiRealizacije();
			String formatiraniDatum = dtf.format(zahtev.getDatumRada());
			String imeProgramera = zahtev.getImeProgramera();
			String prezimeProgramera = zahtev.getPrezimeProgramera();
			String nazivSoftvera = zahtev.getNazivSoftvera();
			String tipZahteva = zahtev.getTipZahteva();

			String line = id + ";" + satiRealizacije + ";" + formatiraniDatum + ";" + imeProgramera + ";"
					+ prezimeProgramera + ";" + nazivSoftvera + ";" + tipZahteva;
			lines.add(line);
		}

		try {
			Files.write(Paths.get(path), lines, Charset.defaultCharset(), StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
		} catch (java.io.IOException e) {
			System.out.println("Datoteka " + path + " nije pronađena.");
		}
	}

	public void load(String path) {

		this.listaZahteva = new ArrayList<Zahtev>();

		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get(path), Charset.defaultCharset());
			for (String line : lines) {
				String[] attributes = line.split(";");

				int id = Integer.parseInt(attributes[0]);
				int satiRealizacije = Integer.parseInt(attributes[1]);
				LocalDate datum = LocalDate.parse(attributes[2], dtf);
				String imeProgramera = attributes[3];
				String prezimeProgramera = attributes[4];
				String nazivSoftvera = attributes[5];
				String tipZahteva = attributes[6];

				Zahtev zahtev = new Zahtev(id, satiRealizacije, datum, imeProgramera, prezimeProgramera, nazivSoftvera,
						tipZahteva);
				this.listaZahteva.add(zahtev);

			}
		} catch (java.io.IOException e) {
			System.out.println("Datoteka " + path + " nije pronađena.");
		} catch (NumberFormatException e) {
			System.out.println("Desila se greska pri konverziji broja.");
		} catch (DateTimeParseException e) {
			System.out.println("Desila se greska pri konverziji datuma.");
		} catch (Exception e) {
			System.out.println("Desila se greska.");
		}

	}

	
}
