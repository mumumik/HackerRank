import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieTitle {

	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }

	  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
	    InputStream is = new URL(url).openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONObject json = new JSONObject(jsonText);
	      return json;
	    } finally {
	      is.close();
	    }
	  }
	  
	  public static String[] getMovieTitle(String substr) throws IOException, JSONException {
		  String url = "https://jsonmock.hackerrank.com/api/movies/search/?Title="+substr;
		  JSONObject json = readJsonFromUrl(url);
		  Integer total = json.getInt("total");
		  Integer total_pages = json.getInt("total_pages");
		  String[] titles = new String[total];
		  Integer j = 0;
		  Integer k = 0;
		  for(int i = 1; i<=total_pages;i++) {
			  String url2 = "https://jsonmock.hackerrank.com/api/movies/search/?Title="+substr+"&page="+i;
			  JSONObject json2 = readJsonFromUrl(url2);
			  JSONArray dataJsonArray = json2.getJSONArray("data");
			  k = 0;
			  while(k<dataJsonArray.length()) {
				  JSONObject dataObj = dataJsonArray.getJSONObject(k);
			      titles[j] = dataObj.get("Title").toString();
			      j++;
			      k++;
			  }
		  }
		  Arrays.sort(titles);
		  return titles;
	  }

	  public static void main(String[] args) throws IOException, JSONException {
		String[] tes = MovieTitle.getMovieTitle("spiderman");
	    for(String movietitle : tes) {
	    	System.out.println(movietitle);
	    }
	    
	  }

}