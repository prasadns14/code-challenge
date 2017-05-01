import Data.Data;
import Events.Event;
import JSONParser.ParseJSON;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

public class SampleLTVApplication {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("input/input.txt"));
        String str = br.readLine();
        Data myData = new Data();
        ParseJSON parseJSON = new ParseJSON();
        while(str != null) {
            JSONObject jsonObject = new JSONObject(str);
            Event e = parseJSON.parseJson(jsonObject);
            myData.IngestData(e);
            str = br.readLine();
        }
        br.close();

        BufferedWriter bw = new BufferedWriter(new FileWriter("output/output.txt"));
        List<String> result = myData.TopXSimpleLTVCustomers(10); // Fetch top 10 LTV customers
        for(int i = 0; i < result.size(); i++) {
            bw.write(result.get(i));
            bw.write("\n");
        }
        bw.close();
    }

}
