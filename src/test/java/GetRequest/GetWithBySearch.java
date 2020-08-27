package GetRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetWithBySearch {

	/*
	 * CreateURLBySearch fonksiyonu http://www.omdbapi.com/ adresinde "By Search"
	 * tablosundaki parametreleri baz alarak istenilen response'u almak amacıyla URL
	 * oluşturur.
	 */
	public static String CreateURLBySearch(String param1, String param2, String param3, String param4, String param5,
			String param6, String param7) {

		String BaseUrl = "http://www.omdbapi.com/?apikey=e4f0fcbe";
		String s = param1;
		String type = param2;
		String y = param3;
		String r = param4;
		String page = param5;
		String callback = param6;
		String v = param7;
		String finalUrl = BaseUrl;
		try {
			if (s != null)
				finalUrl = finalUrl + "&s=" + s;
			if (type != null)
				finalUrl = finalUrl + "&type=" + type;
			if (y != null)
				finalUrl = finalUrl + "&y=" + y;
			if (r != null)
				finalUrl = finalUrl + "&r=" + r;
			if (page != null)
				finalUrl = finalUrl + "&page=" + page;
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
	 * SearchResponse fonksiyonu string formattaki response değeri için
	 * (jsonarray,jsonobject kütüphanelerinden yararlanarak) istenilen alanda
	 * filtreleme yapar, bulunan kaydın ID değerini döner.
	 */
	public static String SearchResponse(String response, String searchedArea, String wantedValue) {

		JSONObject jsonObject = new JSONObject(response);
		JSONArray jsonArray = jsonObject.getJSONArray("Search");
		JSONObject jsonObject1 = new JSONObject();
		String field = "";
		String ID = "";
		try {
			for (int j = 0; j < jsonArray.length(); j++) {
				jsonObject1 = jsonArray.getJSONObject(j);
				field = jsonObject1.getString(searchedArea);
				if (field.contains(wantedValue)) {
					ID = jsonObject1.getString("imdbID");
				}
			}
		} catch (Exception e) {
			System.out.println("Response search fonksiyonu düzgün çalışmıyor." + e.getMessage());
		}
		return ID;
	}

	/*
	 * Gelen response içinde 0.indexte bulunan datanın title, year alanlarının gelip
	 * gelmediğini kontrol eder.
	 */
	public static void ResponseTest(String response) {
		try {
			JSONObject jsonObject = new JSONObject(response);
			JSONArray jsonArray = jsonObject.getJSONArray("Search");
			JSONObject jsonObject1 = jsonArray.getJSONObject(0);

			String title = jsonObject1.getString("Title");
			String year = jsonObject1.getString("Year");
			// String released = jsonObject1.getString(Released");
			/*
			 * bySearch response kısmında released alanı mevcut değil. GetByID sayfasındaki
			 * fonksiyonda mevcut.
			 */
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
		} catch (Exception e) {
			System.out.println("Response test fonksiyonu düzgün çalışmıyor." + e.getMessage());
		}
	}

	@Test
	public void TestGetWithBySearch() {

		try {
			String FinalUrl = CreateURLBySearch("Harry Potter", null, null, null, null, null, null);
			Response response = RestAssured.get(FinalUrl);
			System.out.println("Harry Potter and the Sorcerer's Stone Filmi ID: "
					+ SearchResponse(response.asString(), "Title", "Harry Potter and the Sorcerer's Stone"));

			if (response.getStatusCode() == 200) {
				System.out.println("Status Code:" + response.getStatusCode());
			} else {
				System.out.println("Hata:" + response.getStatusCode());
			}
			ResponseTest(response.asString());
		} catch (Exception e) {
			System.out.println("Hata" + e.getMessage());
		}
	}
}
