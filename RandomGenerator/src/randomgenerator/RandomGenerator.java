/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package randomgenerator;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author aksha
 */
public class RandomGenerator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{
        // TODO code application logic here
        List<Integer> randomNumbers = getRandomNumbers(10, 0, 2147, 10); 
        System.out.println(randomNumbers);
        System.out.println("Do you want to generate an RGB image?(Y/N)");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String ans = br.readLine();
        if(ans.equalsIgnoreCase("Y"))
        {
            randomNumbers = getRandomNumbers(8192, 0, 255, 10);
            for(int i=0;i<5;i++)
            {
                List<Integer> rand2 = getRandomNumbers(8192,0,255,10);
                randomNumbers.addAll(rand2);
            }
            System.out.println("Enter the file name for the image");
            String filename = br.readLine();
            createRGBIMAGE(randomNumbers, filename);
        
        }
    }
    
    public static List<Integer> getRandomNumbers(int numRandom, int min, int max, int base)
    {
       HttpURLConnection connection = null;
       List<Integer> res = new ArrayList<Integer>();
       String targetURL = "https://www.random.org/integers/?num="+numRandom+"&min="+min+"&max="+max+"&col=1&base="+base+"&format=plain&rnd=new";
       try
       {
           URL url = new URL(targetURL);
           connection = (HttpURLConnection)url.openConnection();
           connection.setRequestMethod("GET");
           connection.setUseCaches(false);
           InputStream is = connection.getInputStream();
           BufferedReader rs = new BufferedReader(new InputStreamReader(is));
           String line = null;
           while((line=rs.readLine())!=null)
           {
               res.add(Integer.parseInt(line)); 
           }
           rs.close();
           return res;
       }
       catch(Exception e)
       {
           e.printStackTrace();
           return null;
       }
       finally
       {
           if(connection!=null)
               connection.disconnect();
       }
    }
    
    public static void createRGBIMAGE(List<Integer> pixels,String filename) throws IOException
    {
        BufferedImage img = new BufferedImage(128,128,BufferedImage.TYPE_INT_RGB);
        int i=0;
        
        for(int x=0;x<128;x++)
        {
            for(int y=0;y<128;y++)
            {
                int r = pixels.get(i++);
                int g = pixels.get(i++);
                int b = pixels.get(i++);
                int color = (r << 16) | (g << 8 ) | (b);
                img.setRGB(x, y, color);
            }
        }
        File f = new File(filename+".jpg");
        ImageIO.write(img,"JPEG",f);
       
    }
    
}
