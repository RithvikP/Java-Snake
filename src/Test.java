import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Test {

	public static void main(String[] args) throws Exception {
		BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
		
		String line = br.readLine();
		
		br.close();
			
		StringTokenizer t = new StringTokenizer(line);
		
		System.out.println(Integer.parseInt(t.nextToken())+Integer.parseInt(t.nextToken()));
	}

}
