package utility;

import java.util.Random;

public class RandomGeneration {
		
		public static Long randomNumberNotNULL(int len) 	
		{
		   String AB = "123456789";
		   return Long.parseLong(randomizer(len, AB));
		}

		public static String randomizer(int len, String AB)
		{
			Random rnd = new Random();
			StringBuilder sb = new StringBuilder(len);
			for( int i = 0; i < len; i++ ) {
				sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
			}
			return sb.toString();
		}

}
