package GetRequest;

import org.json.JSONObject;
import org.junit.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetByID {

	/*
	 * CreateURLByID fonksiyonu http://www.omdbapi.com/ adresinde "By ID or Title"
	 * tablosundaki parametreleri baz alarak istenilen response'u almak amacıyla URL
	 * oluşturur.
	 */
	public static String CreateURLByID(String param1, String param2, String param3, String param4, String param5,
			String param6, String param7, String param8) {

		String BaseUrl = "http://www.omdbapi.com/?apikey=e4f0fcbe";
		String i = param1;
		String t = param2;
		String type = param3;
		String y = param4;
		String plot = param5;
		String r = param6;
		String callback = param7;
		String v = param8;
		String finalUrl = BaseUrl;
		try {
			if (i != null)
				finalUrl = finalUrl + "&i=" + i;
			if (t != null)
				finalUrl = finalUrl + "&t=" + t;
			if (type != null)
				finalUrl = finalUrl + "&type=" + type;
			if (y != null)
				finalUrl = finalUrl + "&y=" + y;
			if (plot != null)
				finalUrl = finalUrl + "&plot=" + plot;
			if (r != null)
				finalUrl = finalUrl + "&r=" + r;
			if (callback != null)
				finalUrl = finalUrl + "&callback=" + callback;
			if (v != null)
				finalUrl = finalUrl + "&v=" + v;
		} catch (Exception e) {
			System.out.println("Create URL fonksiyonu düzgün çalışmıyor." + e.getMessage());
		}
		return finalUrl;
	}

	/*
	 * SearchResponse2 fonksiyonu string formattaki response değeri için (jsonobject
	 * kütüphanesinden yararlanarak)istenilen alanda arama yaparak film adını döner.
	 */
	public static String SearchResponse2(String response, String searchedArea) {
		String data = "";
		try {
			JSONObject jsonObject = new JSONObject(response);
			data = jsonObject.getString(searchedArea);
		} catch (Exception e) {
			System.out.println("Response search 2 fonksiyonu düzgün çalışmıyor." + e.getMessage());
		}
		return data;
	}

	/*
	 * Gelen response içinde 0.indexte bulunan datanın title, year, released
	 * alanlarının gelip gelmediğini kontrol eder.
	 */
	public static void ResponseTest2(String response) {
		try {
			JSONObject jsonObject = new JSONObject(response);
			String title = jsonObject.getString("Title");
			String year = jsonObject.getString("Year");
			String released = jsonObject.getString("Released");

			if (title == null) {
				System.out.println("Title alanı null!");
			} else {
				System.out.println("Title alanı mevcut");
			}
			if (year == null) {
				System.out.println("Year alanı null!");
			} else {
				System.out.println("Year alanı mevcut");
			}
			if (released == null) {
				System.out.println("Released alanı null!");
			} else {
				System.out.println("Released alanı mevcut");
			}
		} catch (Exception e) {
			System.out.println("Response test 2 fonksiyonu düzgün çalışmıyor." + e.getMessage());
		}
	}

	@Test
	public void TestGetByID() {

		try {
			String finalUrl = CreateURLByID("tt0241527", null, null, null, null, null, null, null);
			Response response = RestAssured.get(finalUrl);
			System.out.println(SearchResponse2(response.asString(), "Title"));

			if (response.getStatusCode() == 200) {
				System.out.println("Status Code:" + response.getStatusCode());
			} else {
				System.out.println("Hata:" + response.getStatusCode());
			}

			ResponseTest2(response.asString());
		} catch (Exception e) {
			System.out.println("Hata" + e.getMessage());
		}

	}

}
