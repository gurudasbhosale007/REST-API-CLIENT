package weather;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
public class WeatherApp 
{
public static void main(String[] args) 
{
String apiKey = "22c7032751d5e11dd9cc8f212b59ba41"; // Replace with your OpenWeatherMap API key
String city ;
Scanner sc=new Scanner(System.in);
System.out.println("Enter the city");
city=sc.next();
String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";
try {
URL url = new URL(apiUrl);
HttpURLConnection conn = (HttpURLConnection) url.openConnection();
conn.setRequestMethod("GET");
int responseCode = conn.getResponseCode();
if (responseCode == HttpURLConnection.HTTP_OK) 
{
BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
String inputLine;
StringBuilder response = new StringBuilder();
while ((inputLine = in.readLine()) != null) 
{
response.append(inputLine);
}
in.close();
JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
String weather = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
double temperature = jsonObject.getAsJsonObject("main").get("temp").getAsDouble();
System.out.println("City: " + city);
System.out.println("Weather: " + weather);
System.out.println("Temperature: " + temperature + "°C");
} 
else 
{
System.out.println("GET request failed. Response Code: " + responseCode);
}
} 
catch (Exception e) 
{
e.printStackTrace();
}
}
}
