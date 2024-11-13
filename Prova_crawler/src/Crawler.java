import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Crawler {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Inserisci il link: ");
        String scelta = sc.next();
        String link = "";
        if(scelta.contains("http") || scelta.contains("https")){
           link  = scelta;
        }
        else{
            link = "http://" + scelta;
        }
        List<String> siti = new ArrayList<>();

        crawl(1, link, siti);



    }


    private static Document request(String url){
        Document doc = null;
        try{

            doc = Jsoup.connect(url).get();

        }
        catch(IOException e ){
            System.out.println(e.getMessage());
        }
        return doc;
    }


    private static void crawl(int lvl, String url, List<String> visited){
        System.out.println(lvl);

        Document doc = request(url);

        if(lvl > 3){

            return;
        }
        for(Element link :  doc.select("a[href]")){
            String next_link = link.absUrl("href");
            if(visited.contains(next_link)){
                return;
            }
            else{
                System.out.println("Title: " + doc.title());
                System.out.println("Link: " + next_link);

                visited.add(next_link);
                crawl(lvl+1, next_link, visited);
            }

        }

    }
}
