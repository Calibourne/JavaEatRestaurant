package Utils;

public enum Neighberhood {
	Neve_Shanan("Neve Shaanan"), Kiriat_Haim("Kiriat Haim"),
	DownTown("Down Town"), Vardia("Vardia"),
	Bat_Galim("Bat Galim"), Merkaz_Karmel("Merkaz Karmel"),
	Denya("Denya"), Kiriat_Eliezer("Kiriat_Eliezer"),
	Hadar("Hadar"), Romema("Romema"),
	German_Colony("German Colony"), Vadi_Nisnas("Vadi Nisnas"),
	Vadi_Saliv("Vadi Saliv"), Neot_Peres("Neot Peres"),
	Kababir("Kababir"), Neve_David("Neve David"),
	Karmelia("Karmelia"), Halisa("Halisa"),
	French_Karmel("French Karmel"), Ramat_Hanasi("Ramat Hanasi"),
	Neve_Yosef("Neve Yosef"), Ramat_Almogi("Ramat Almogi"),
	Other("Other");

	private final String name;
	Neighberhood(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
