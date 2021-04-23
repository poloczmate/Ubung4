package newsanalyzer.ui;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Stream;

import newsanalyzer.ctrl.Controller;
import newsapi.NewsApi;
import newsapi.NewsApiBuilder;
import newsapi.NewsApiException;
import newsapi.enums.Category;
import newsapi.enums.Country;
import newsapi.enums.Endpoint;

import static org.json.JSONObject.getNames;

public class UserInterface 
{

	private Controller ctrl = new Controller();

	public void getDataFromCtrl1(){
		ctrl.process(Endpoint.TOP_HEADLINES, "Fußball", Country.de, Category.sports);
	}

	public void getDataFromCtrl2(){
		ctrl.process(Endpoint.TOP_HEADLINES, "", Country.fr, Category.general);
	}

	public void getDataFromCtrl3(){
		ctrl.process(Endpoint.TOP_HEADLINES, "korona", Country.hu, Category.health);
	}
	
	public void getDataForCustomInput() {
		Country country;
		Category category;
		Scanner scan = new Scanner(System.in);
		try {
			System.out.print("Wählen Sie ein Land (2 Char Code):");
			String choice = scan.nextLine();
			choice = choice.toLowerCase(Locale.ROOT);
			if (choice.length() != 2) throw new NewsApiException("No country like that!");
			String[] names = Country.names();
			if (!Arrays.asList(names).contains(choice)) throw new NewsApiException("No country like that!");
			country = Country.valueOf(choice);

			System.out.print("Wählen Sie eine Kategorie: (health, business, entertainment, general, science, sports, technology)");
			choice = scan.nextLine();
			choice = choice.toLowerCase(Locale.ROOT);
			names = Category.names();
			if (!Arrays.asList(names).contains(choice)) throw new NewsApiException("No category like that!");
			category = Category.valueOf(choice);

			System.out.print("Geben Sie ein Keyword ein:");
			choice = scan.nextLine();

			ctrl.process(Endpoint.TOP_HEADLINES, choice, country, category);

		}catch (NewsApiException e){
			System.out.println(e.getMessage());
		}

	}


	public void start() {
		Menu<Runnable> menu = new Menu<>("User Interface");
		menu.setTitel("Wählen Sie aus:");
		menu.insert("a", "Fußball Nachrichten aus Deutschland", this::getDataFromCtrl1);
		menu.insert("b", "Algemeine Nachrichten aus Frankreich", this::getDataFromCtrl2);
		menu.insert("c", "Corona Nachrichten aus Ungarn", this::getDataFromCtrl3);
		menu.insert("d", "Choice User Imput:",this::getDataForCustomInput);
		menu.insert("q", "Quit", null);
		Runnable choice;
		while ((choice = menu.exec()) != null) {
			 choice.run();
		}
		System.out.println("Program finished");
	}


    protected String readLine() {
		String value = "\0";
		BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			value = inReader.readLine();
        } catch (IOException ignored) {
		}
		return value.trim();
	}

	protected Double readDouble(int lowerlimit, int upperlimit) 	{
		Double number = null;
        while (number == null) {
			String str = this.readLine();
			try {
				number = Double.parseDouble(str);
            } catch (NumberFormatException e) {
                number = null;
				System.out.println("Please enter a valid number:");
				continue;
			}
            if (number < lowerlimit) {
				System.out.println("Please enter a higher number:");
                number = null;
            } else if (number > upperlimit) {
				System.out.println("Please enter a lower number:");
                number = null;
			}
		}
		return number;
	}
}
