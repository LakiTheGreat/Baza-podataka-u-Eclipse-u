package ispit;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Test {

	public static boolean validId(String privremeniId) {
		try {
			int broj = Integer.parseInt(privremeniId);
			if (broj < 1) {
				System.out.println("ID mora biti veci od 1.");
				return false;
			}
			return true;
		} catch (Exception e) {
			System.out.println("ID mora biti broj.");
			return false;
		}
	}

	public static boolean validHours(String privremeniSati) {
		try {
			int broj = Integer.parseInt(privremeniSati);
			if (broj > 0 && broj < 11) {
				return true;
			}
			System.out.println("Raspon sati mora biti od 1 do 10.");
			return false;
		} catch (Exception e) {
			System.out.println("Niste uneli broj.");
			return false;
		}
	}

	public static boolean isValidDate(String privremeniDatum) {
		try {
			LocalDate datum = LocalDate.parse(privremeniDatum, dtf);
			if (datum.compareTo(LocalDate.of(2022, 1, 1)) <= 0) {
				System.out.println("Datum mora biti manji od 01.01.2022.");
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean validSoftverTipe(String privremeniTip) {
		if (privremeniTip.equalsIgnoreCase("tehnicki") || privremeniTip.equalsIgnoreCase("funkcionalni")
				|| privremeniTip.equalsIgnoreCase("testiranje") || privremeniTip.equalsIgnoreCase("spike")) {
			return true;
		}
		return false;
	}

	public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
	public static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {

		Firma firma = new Firma();
		firma.load("softverskaFirma.txt");

		String opcija;

		do {

			System.out.println("********************* MENI *******************");
			System.out.println("1. Unos podataka o novoj firmi");
			System.out.println("2. Unos novog zahteva");
			System.out.println("3. Ispis podataka o svim zahtevima");
			System.out.println("4. Izmena zahteva");
			System.out.println("5. Brisanje zahteva");
			System.out.println("6. Pretraga po delu imena ili prezimena programera");
			System.out.println("7. Pretraga po tipu, za odredjeni softver u periodu vremena");
			System.out.println("8. Prikaz prosecnog vremena rada u periodu");
			System.out.println("9. Prikaz prosecnog vremena rada u dva perioda");
			System.out.println("10. Prikaz zahteva sa natprosecnim trajanjem u navedenom periodu");
			System.out.println("11. Ispis podataka o firmi");

			System.out.println("x. Izlaz");
			System.out.print("Unesite opciju: ");

			opcija = in.nextLine();
			switch (opcija) {

			case "1":
				unesiFirmu(firma);
				break;
			case "2":
				unosNovogZahteva(firma);
				break;
			case "3":
				firma.ispisZahteva();
				break;
			case "4":
				izmenaZahteva(firma);
				break;
			case "5":
				brisanjeZahteva(firma);
				break;
			case "6":
				pretragaPoDeluImenaIliPrezimena(firma);
				break;
			case "7":
				slozenaPretraga(firma);
				break;
			case "8":
				pretragaProseka(firma);
				break;
			case "9":
				pretragaProsekaPoPeriodima(firma);
				break;
			case "10":
				natprosecniProsekUPeriodu(firma);
				break;
			case "11":
				firma.ispisPodatakaOFirmi();
				break;
			case "x":
				break;
			default:
				System.out.println("Pogrešan izbor opcije. Pokušajte ponovo.");
			}

		} while (!opcija.equals("x"));
		firma.save("softverskaFirma.txt");
		in.close();
	}

	

	private static void natprosecniProsekUPeriodu(Firma firma) {
		String privremeniMin;
		do {
			System.out.print("Datum OD: ");
			privremeniMin = in.nextLine();
		} while (!isValidDate(privremeniMin));
		LocalDate min = LocalDate.parse(privremeniMin, dtf);

		String privremeniMax;
		do {
			System.out.print("Datum DO: ");
			privremeniMax = in.nextLine();
		} while (!isValidDate(privremeniMax));
		LocalDate max = LocalDate.parse(privremeniMax, dtf);

		firma.ispisNatprosecnihZahteva(min, max);

	}

	public static void unesiFirmu(Firma firma) {
		System.out.print("Naziv firme: ");
		firma.setNazivFirme(in.nextLine());

		System.out.print("Web adresa: ");
		firma.setWebAdresa(in.nextLine());

		System.out.println("Br.telefona: ");
		firma.setBrTelefona(in.nextLine());

	}

	public static void unosNovogZahteva(Firma firma) {

		String privremeniId;
		do {
			System.out.print("ID: ");
			privremeniId = in.nextLine();
		} while (!validId(privremeniId) || !firma.idPostoji(privremeniId));
		int id = Integer.parseInt(privremeniId);

		String privremeniSati;
		do {
			System.out.print("Trajanje realizacie (u satima): ");
			privremeniSati = in.nextLine();
		} while (!validHours(privremeniSati));
		int satiRealizacije = Integer.parseInt(privremeniSati);

		String privremeniDatum;
		do {
			System.out.print("Datum rada: ");
			privremeniDatum = in.nextLine();
		} while (!isValidDate(privremeniDatum));
		LocalDate datum = LocalDate.parse(privremeniDatum, dtf);

		System.out.print("Ime programera: ");
		String imeProgramera = in.nextLine();

		System.out.print("Prezime programera: ");
		String prezimeProgramera = in.nextLine();

		System.out.print("Naziv softvera: ");
		String nazivSoftvera = in.nextLine();

		String privremeniTip;
		do {
			System.out.print("Tip softvera (tehnicki, funkcionalni, testiranje, spike): ");
			privremeniTip = in.nextLine();
		} while (!validSoftverTipe(privremeniTip));

		Zahtev zahtev = new Zahtev(id, satiRealizacije, datum, imeProgramera, prezimeProgramera, nazivSoftvera,
				privremeniTip);
		firma.unosZahteva(zahtev);
		System.out.println("Zahtev je uspesno unet. ");

	}

	public static void izmenaZahteva(Firma firma) {

		String privremeniId;
		do {
			System.out.print("ID: ");
			privremeniId = in.nextLine();
		} while (!validId(privremeniId) || !firma.idNePostoji(privremeniId));
		int id = Integer.parseInt(privremeniId);

		String privremeniSati;
		do {
			System.out.print("Trajanje realizacie (u satima): ");
			privremeniSati = in.nextLine();
		} while (!validHours(privremeniSati));
		int satiRealizacije = Integer.parseInt(privremeniSati);

		String privremeniDatum;
		do {
			System.out.print("Datum rada: ");
			privremeniDatum = in.nextLine();
		} while (!isValidDate(privremeniDatum));
		LocalDate datum = LocalDate.parse(privremeniDatum, dtf);

		System.out.print("Ime programera: ");
		String imeProgramera = in.nextLine();

		System.out.print("Prezime programera: ");
		String prezimeProgramera = in.nextLine();

		System.out.print("Naziv softvera: ");
		String nazivSoftvera = in.nextLine();

		String privremeniTip;
		do {
			System.out.print("Tip softvera (tehnicki, funkcionalni, testiranje, spike): ");
			privremeniTip = in.nextLine();
		} while (!validSoftverTipe(privremeniTip));

		Zahtev zahtev = new Zahtev(id, satiRealizacije, datum, imeProgramera, prezimeProgramera, nazivSoftvera,
				privremeniTip);
		firma.izmeniZahtev(zahtev);
		System.out.println("Zahtev je uspesno izmenjen.");

	}

	public static void brisanjeZahteva(Firma firma) {
		String privremeniId;
		do {
			System.out.print("ID: ");
			privremeniId = in.nextLine();
		} while (!validId(privremeniId) || !firma.idNePostoji(privremeniId));
		int id = Integer.parseInt(privremeniId);

		firma.obrisiZahtev(id);

	}

	public static void pretragaPoDeluImenaIliPrezimena(Firma firma) {
		System.out.print("Unesite deo imena ili prezimena programera: ");
		String deoZaPretragu = in.nextLine();

		firma.ispisPretragePoDeluImenaIliPrezimena(deoZaPretragu);

	}

	public static void slozenaPretraga(Firma firma) {
		String privremeniTip;
		do {
			System.out.print("Tip softvera (tehnicki, funkcionalni, testiranje, spike): ");
			privremeniTip = in.nextLine();
		} while (!validSoftverTipe(privremeniTip));

		System.out.print("Naziv softvera: ");
		String nazivSoftvera = in.nextLine();

		String privremeniMin;
		do {
			System.out.print("Datum OD: ");
			privremeniMin = in.nextLine();
		} while (!isValidDate(privremeniMin));
		LocalDate min = LocalDate.parse(privremeniMin, dtf);

		String privremeniMax;
		do {
			System.out.print("Datum DO: ");
			privremeniMax = in.nextLine();
		} while (!isValidDate(privremeniMax));
		LocalDate max = LocalDate.parse(privremeniMax, dtf);

		firma.ispisSlozenaPretraga(privremeniTip, nazivSoftvera, min, max);
	}
	
	public static void pretragaProseka(Firma firma) {
		String privremeniMin;
		do {
			System.out.print("Datum OD: ");
			privremeniMin = in.nextLine();
		} while (!isValidDate(privremeniMin));
		LocalDate min = LocalDate.parse(privremeniMin, dtf);

		String privremeniMax;
		do {
			System.out.print("Datum DO: ");
			privremeniMax = in.nextLine();
		} while (!isValidDate(privremeniMax));
		LocalDate max = LocalDate.parse(privremeniMax, dtf);
		
		firma.ispisProseka (min, max);
		
	}
	
	public static void pretragaProsekaPoPeriodima(Firma firma) {
		String privremeniMinPrvi;
		do {
			System.out.print("Pocetak prvog perioda: ");
			privremeniMinPrvi = in.nextLine();
		} while (!isValidDate(privremeniMinPrvi));
		LocalDate minPrvi = LocalDate.parse(privremeniMinPrvi, dtf);

		String privremeniMaxPrvi;
		do {
			System.out.print("Kraj prvog perioda: ");
			privremeniMaxPrvi = in.nextLine();
		} while (!isValidDate(privremeniMaxPrvi));
		LocalDate maxPrvi = LocalDate.parse(privremeniMaxPrvi, dtf);

		String privremeniMinDrugi;
		do {
			System.out.print("Pocetak drugog perioda: ");
			privremeniMinDrugi = in.nextLine();
		} while (!isValidDate(privremeniMinDrugi));
		LocalDate minDrugi = LocalDate.parse(privremeniMinDrugi, dtf);

		String privremeniMaxDrugi;
		do {
			System.out.print("Kraj drugog perioda: ");
			privremeniMaxDrugi = in.nextLine();
		} while (!isValidDate(privremeniMaxDrugi));
		LocalDate maxDrugi = LocalDate.parse(privremeniMaxDrugi, dtf);

		firma.ispisProsekaPoPeriodima(minPrvi, maxPrvi, minDrugi, maxDrugi);

	}

	public static void natprosecnoTrajanje(Firma firma) {

		String privremeniMin;
		do {
			System.out.print("Datum OD: ");
			privremeniMin = in.nextLine();
		} while (!isValidDate(privremeniMin));
		LocalDate min = LocalDate.parse(privremeniMin, dtf);

		String privremeniMax;
		do {
			System.out.print("Datum DO: ");
			privremeniMax = in.nextLine();
		} while (!isValidDate(privremeniMax));
		LocalDate max = LocalDate.parse(privremeniMax, dtf);

		firma.ispisNatprosecnihZahteva(min, max);

	}

}
